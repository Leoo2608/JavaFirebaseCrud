package com.example.javafirebasecrud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.Toast;

import com.example.javafirebasecrud.adapter.ListAdapter;
import com.example.javafirebasecrud.model.Escuela;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListEscuelasActivity extends AppCompatActivity implements ListAdapter.OnNoteListener {

    private List<Escuela> elements;
    private RecyclerView listRecView_escuelas;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private Escuela escuelaSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_escuelas);
        listRecView_escuelas = findViewById(R.id.recycler_view_esc);
        initFirebase();
        listarDatos();
    }

    private void listarDatos() {
        elements = new ArrayList<>();
        databaseReference.child("Escuela").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                elements.clear();
                for (DataSnapshot objSnapshot : snapshot.getChildren()){
                    System.out.println(objSnapshot.getValue(Escuela.class));
                    Escuela escuela = objSnapshot.getValue(Escuela.class);
                    elements.add(escuela);
                }
                ListAdapter listAdapter = new ListAdapter(elements, ListEscuelasActivity.this, ListEscuelasActivity.this);
                RecyclerView recyclerView = findViewById(R.id.recycler_view_esc);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(ListEscuelasActivity.this));
                recyclerView.setAdapter(listAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }

    private void initFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list_esc, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item_add_esc) {
            finish();
            startActivity(new Intent(this, AddEscuelaActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed(){
        finish();
        startActivity(new Intent(this, SelectionActivity.class));
    }

    @Override
    public void onNoteClick(int position){
        escuelaSelected = elements.get(position);
        final CharSequence[] items = {
                "Editar",
                "Eliminar"
        };
        AlertDialog.Builder menu = new AlertDialog.Builder(this);
        menu.setTitle("Opciones");
        menu.setItems(items, (dialogInterface, i) -> {
            switch (i){
                case 0:
                    Intent intent = new Intent(ListEscuelasActivity.this, EditEscuelaActivity.class);
                    intent.putExtra("ESCUELA_ID", escuelaSelected.getUid());
                    startActivity(intent);
                    break;
                case 1:
                    AlertDialog.Builder alert = new AlertDialog.Builder(ListEscuelasActivity.this);
                    alert.setMessage("¿Desea eliminar este registro?");
                    alert.setPositiveButton("Si", (dialog, a)->{
                        Escuela e = new Escuela("","","");
                        e.setUid(escuelaSelected.getUid());
                        databaseReference.child("Escuela").child(e.getUid()).removeValue();
                        Toast.makeText(this, "Registro Eliminado", Toast.LENGTH_SHORT).show();
                    });
                    alert.setNegativeButton("No", (dialog, a)-> dialog.dismiss());
                    alert.show();
                    break;
            }
        });
        menu.show();
    }

}