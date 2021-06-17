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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class menu_profesores extends AppCompatActivity {

    public Button b1,b2;
    public EditText t1,t2;
    public Boolean p=false;
    FirebaseFirestore db;
    public String usuario;
    public String contraseña;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_profesores);

        db=FirebaseFirestore.getInstance();

        b1=(Button)findViewById(R.id.continuar_profesor);
        b2=(Button)findViewById(R.id.volver_profesor);

        t1=(EditText) findViewById(R.id.txt_usuario_profesor);
        t2=(EditText) findViewById(R.id.txt_contraseña_profesor);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validacionProfesor();

            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(menu_profesores.this, MainActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_left, R.anim.slide_out_right);
            }
        });

    }

    private void validacionProfesor() {

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



        db.collection("Profesores").whereEqualTo("usuario",usuario).whereEqualTo("contraseña",contraseña).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d("Este es el Profesor:", document.getId() + " => " + document.getData());
                        Toast.makeText(getApplicationContext(),"¡Bienvenido " + usuario.toString() + "!",Toast.LENGTH_LONG).show();
                        p=true;
                        Intent i = new Intent(menu_profesores.this, Menu_Todo.class);
                        i.putExtra("profesor", p);
                        i.putExtra("usuario_profesor", usuario.toString() + "");
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

    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.slide_left, R.anim.slide_out_right);
    }
}