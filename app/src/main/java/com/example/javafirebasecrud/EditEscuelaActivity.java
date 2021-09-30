package com.example.javafirebasecrud;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.javafirebasecrud.model.Escuela;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class EditEscuelaActivity extends AppCompatActivity {

    private String uidEscuela;
    EditText nombre, facultad;
    ImageButton updBtn;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_escuela);
        uidEscuela = getIntent().getStringExtra("ESCUELA_ID");
        nombre = findViewById(R.id.txt_edt_nomEsc);
        facultad = findViewById(R.id.txt_edt_facEsc);
        updBtn = findViewById(R.id.btn_edt_esc);
        initFirebase();
        overwriteValues();
        updBtn.setOnClickListener(view -> {
            String nomescuela = nombre.getText().toString();
            String nomfac = facultad.getText().toString();
            if( nomescuela == null || nomescuela.equals("")){
                nombre.setError("Required");
            }else if(nomfac == null || nomfac.equals("")){
                facultad.setError("Required");
            }else{
                Escuela e = new Escuela("","","");
                e.setUid(uidEscuela);
                e.setNombre(nomescuela.trim());
                e.setFacultad(nomfac.trim());
                databaseReference.child("Escuela").child(e.getUid()).setValue(e);
                Toast.makeText(
                        this,
                        "Registro actualizado!",
                        Toast.LENGTH_SHORT
                ).show();
                limpiarCajas();
                finish();
                startActivity(new Intent(this, ListEscuelasActivity.class));
            }
        });
    }
    private void overwriteValues() {
        databaseReference.child("Escuela").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot objSnapshot : snapshot.getChildren()){
                    Escuela escuela = objSnapshot.getValue(Escuela.class);
                    if(escuela.getUid().equals(uidEscuela)){
                        nombre.setText(escuela.getNombre());
                        facultad.setText(escuela.getFacultad());
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }

    private void initFirebase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance() ;
        databaseReference = firebaseDatabase.getReference();
    }
    @Override
    public void onBackPressed(){
        finish();
        startActivity(new Intent(this, ListEscuelasActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_esc, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.lista_escuelas) {
            finish();
            startActivity(new Intent(this, ListEscuelasActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
    private void limpiarCajas(){
        nombre.setText("");
        facultad.setText("");
    }
}