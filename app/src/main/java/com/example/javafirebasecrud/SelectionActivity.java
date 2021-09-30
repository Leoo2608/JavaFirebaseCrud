package com.example.javafirebasecrud;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class SelectionActivity extends AppCompatActivity {
    Button showEscuelas, showDocentes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selection);
        showEscuelas = (Button) findViewById(R.id.btn_esc);
        showDocentes = (Button) findViewById(R.id.btn_doc);

        showEscuelas.setOnClickListener(view ->{
            startActivity(new Intent(this, ListEscuelasActivity.class));
            finish();
        });

        showDocentes.setOnClickListener(view ->{
            startActivity(new Intent(this, ListDocentesActivity.class));
            finish();
        });
    }

    @Override
    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("¿Deseas cerrar sesión?")
                .setPositiveButton("Si", (dialog, i) -> {
                    FirebaseAuth.getInstance().signOut();
                    finish();
                    startActivity(new Intent(this, MainActivity.class));
                })
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_selection, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.quit_item) {
           onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}