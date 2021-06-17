package com.example.proyecto_final;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class menu_estudiante extends AppCompatActivity {

    public Button b1,b2;
    public EditText t1,t2;
    public Boolean e=false;
    public Boolean esverdad=false;
    public String usuario;
    public String contraseña;

    DatabaseReference databaseReference;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_estudiante);

        db=FirebaseFirestore.getInstance();

        b1=(Button)findViewById(R.id.continuar_estudiante);
        b2=(Button)findViewById(R.id.volver_estudiante);

        t1=(EditText) findViewById(R.id.txt_usuario_estudiante);
        t2=(EditText) findViewById(R.id.txt_contraseña_estudiantes);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validacionEstudiante();
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(menu_estudiante.this, MainActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_left, R.anim.slide_out_right);
            }
        });


    }

    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.slide_left, R.anim.slide_out_right);
    }

    private void validacionEstudiante(){
        usuario=t1.getText().toString().trim();
        contraseña=t2.getText().toString().trim();

        if (TextUtils.isEmpty(usuario)) {
            Toast.makeText(this, "Se debe ingresar un usuario", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(contraseña)) {
            Toast.makeText(this, "Falta ingresar la contraseña", Toast.LENGTH_LONG).show();
            return;
        }
        db.collection("Estudiantes").whereEqualTo("usuario",usuario).whereEqualTo("contraseña",contraseña).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d("Este es el Estudiante:", document.getId() + " => " + document.getData());
                        Toast.makeText(getApplicationContext(),"¡Bienvenido " + usuario.toString() + "!",Toast.LENGTH_LONG).show();
                        e=true;
                        Intent i = new Intent(menu_estudiante.this, Menu_Todo.class);
                        i.putExtra("estudiante", e);
                        i.putExtra("usuario_estudiante", usuario.toString() + "");
                        startActivity(i);
                        overridePendingTransition(R.anim.slide_right, R.anim.slide_out_left);
                    }
                } else {
                    Log.w("Error", "Error getting documents.", task.getException());
                    Toast.makeText(getApplicationContext(),"Usuario o Contraseña incorrecta",Toast.LENGTH_LONG).show();

                }
            }
        });
}
    }