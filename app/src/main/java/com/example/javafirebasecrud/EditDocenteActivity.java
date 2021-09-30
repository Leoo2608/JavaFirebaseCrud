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

import com.example.javafirebasecrud.model.Docente;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditDocenteActivity extends AppCompatActivity {
    
    private String uidDocente;
    EditText nombres, codigo, dni, telefono, correo;
    ImageButton updBtn;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    String emailPattern2 = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+\\.+[a-z]+";
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_docente);
        uidDocente = getIntent().getStringExtra("DOCENTE_ID");
        nombres = findViewById(R.id.txt_edt_nombresDoc);
        codigo = findViewById(R.id.txt_edt_codigo);
        dni = findViewById(R.id.txt_edt_dni);
        telefono = findViewById(R.id.txt_edt_telefono);
        correo = findViewById(R.id.txt_edt_correo);
        updBtn = findViewById(R.id.btn_edt_doc);
        initFirebase();
        overwriteValues();
        updBtn.setOnClickListener(view -> {
            String vNombres = nombres.getText().toString().trim();
            String vCodigo = codigo.getText().toString().trim();
            String vDni = dni.getText().toString().trim();
            String vTelefono = telefono.getText().toString().trim();
            String vCorreo = correo.getText().toString().trim();
            if(vNombres == null || vNombres.equals("")){
                nombres.setError("Required");
            }else if(vCodigo == null || vCodigo.equals("")){
                codigo.setError("Required");
            }else if(vDni == null || vDni.equals("")){
                dni.setError("Required");
            }else if(vTelefono == null || vTelefono.equals("")){
                telefono.setError("Required");
            }else if(vCorreo == null || vCorreo.equals("")){
                correo.setError("Required");
            }else if(!vCorreo.matches(emailPattern) && !vCorreo.matches(emailPattern2)){
                correo.setError("Invalid email");
            }else{
                Docente d = new Docente();
                d.setUid(uidDocente);
                d.setNombres(vNombres.trim());
                d.setCodigo(vCodigo.trim());
                d.setDni(vDni.trim());
                d.setTelefono(vTelefono.trim());
                d.setCorreo(vCorreo.trim());
                databaseReference.child("Docente").child(d.getUid()).setValue(d);
                Toast.makeText(
                        this,
                        "Registro actualizado!",
                        Toast.LENGTH_SHORT
                ).show();
                limpiarCajas();
                finish();
                startActivity(new Intent(this, ListDocentesActivity.class));
            }
        });
    }

    private void overwriteValues() {
        databaseReference.child("Docente").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Docente docente = dataSnapshot.getValue(Docente.class);
                    if(docente.getUid().equals(uidDocente)){
                        nombres.setText(docente.getNombres());
                        codigo.setText(docente.getCodigo());
                        dni.setText(docente.getDni());
                        telefono.setText(docente.getTelefono());
                        correo.setText(docente.getCorreo());
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_esc, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.lista_escuelas) {
            finish();
            startActivity(new Intent(this, ListDocentesActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed(){
        finish();
        startActivity(new Intent(this, ListDocentesActivity.class));
    }
    private void limpiarCajas(){
        nombres.setText("");
        codigo.setText("");
        dni.setText("");
        telefono.setText("");
        correo.setText("");
    }
}