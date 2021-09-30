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

import com.example.javafirebasecrud.adapter.ListDocAdapter;
import com.example.javafirebasecrud.model.Docente;
import com.example.javafirebasecrud.model.Escuela;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListDocentesActivity extends AppCompatActivity implements ListDocAdapter.OnCardListener {

    private List<Docente> docenteList;
    private RecyclerView recyclerViewDocentes;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private Docente docenteSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_docentes);
        recyclerViewDocentes = findViewById(R.id.recycler_view_doc);
        initFirebase();
        listarDatos();
    }

    private void listarDatos() {
        docenteList = new ArrayList<>();
        databaseReference.child("Docente").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                docenteList.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Docente docente =  dataSnapshot.getValue(Docente.class);
                    docenteList.add(docente);
                }
                ListDocAdapter listDocAdapter = new ListDocAdapter(docenteList, ListDocentesActivity.this, ListDocentesActivity.this);
                RecyclerView recyclerView = findViewById(R.id.recycler_view_doc);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(ListDocentesActivity.this));
                recyclerView.setAdapter(listDocAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initFirebase() {
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list_doc, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.item_add_doc) {
            finish();
            startActivity(new Intent(this, AddDocenteActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed(){
        finish();
        startActivity(new Intent(this, SelectionActivity.class));
    }

    @Override
    public void onCardClick(int position) {
        docenteSelected = docenteList.get(position);
        final CharSequence[] items = {
                "Editar",
                "Eliminar"
        };
        AlertDialog.Builder menu = new AlertDialog.Builder(this);
        menu.setTitle("Opciones");
        menu.setItems(items, (dialogInterface, i) -> {
            switch (i){
                case 0:
                    Intent intent = new Intent(ListDocentesActivity.this, EditDocenteActivity.class);
                    intent.putExtra("DOCENTE_ID", docenteSelected.getUid());
                    startActivity(intent);
                    break;
                case 1:
                    AlertDialog.Builder alert = new AlertDialog.Builder(ListDocentesActivity.this);
                    alert.setMessage("¿Desea eliminar este registro?");
                    alert.setPositiveButton("Si", (dialog, a)->{
                        Docente d = new Docente();
                        d.setUid(docenteSelected.getUid());
                        databaseReference.child("Docente").child(d.getUid()).removeValue();
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