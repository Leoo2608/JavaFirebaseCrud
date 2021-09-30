package com.example.javafirebasecrud;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class AddEscuelaActivity extends AppCompatActivity {
    EditText nombre, facultad;
    ImageButton addBtn;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_escuela);

        nombre = findViewById(R.id.txt_add_nomEsc);
        facultad = findViewById(R.id.txt_add_facEsc);
        addBtn = findViewById(R.id.btn_add_esc);

        initFirebase(); // FIREBASE BEFORE ANY FUN

        addBtn.setOnClickListener(view -> {
            String nomescuela = nombre.getText().toString();
            String nomfac = facultad.getText().toString();
            if( nomescuela == null || nomescuela.equals("")){
                nombre.setError("Required");
            }else if(nomfac == null || nomfac.equals("")){
                facultad.setError("Required");
            }else{
                Escuela e = new Escuela();
                e.setUid(UUID.randomUUID().toString());
                e.setNombre(nomescuela);
                e.setFacultad(nomfac);
                databaseReference.child("Escuela").child(e.getUid()).setValue(e);
                Toast.makeText(
                        this,
                        nomescuela+" creado! ",
                        Toast.LENGTH_SHORT
                ).show();
                limpiarCajas();
            }
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

    private void limpiarCajas(){
        nombre.setText("");
        facultad.setText("");
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
}