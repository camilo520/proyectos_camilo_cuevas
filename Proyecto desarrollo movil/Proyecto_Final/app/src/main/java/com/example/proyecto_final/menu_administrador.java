package com.example.proyecto_final;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import java.util.zip.Inflater;

public class menu_administrador extends AppCompatActivity implements  View.OnClickListener{

    public Button b1,b2,b3;
    public EditText t1,t2;
    public Boolean b=false;

    private ProgressDialog progressDialog;

    private MenuItem item;

    //Declaramos un objeto firebaseAuth
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_administrador);

        firebaseAuth = FirebaseAuth.getInstance();

        b1 = (Button) findViewById(R.id.registrar_administrador);
        b2 = (Button) findViewById(R.id.continuar_administrador);
        b3 = (Button) findViewById(R.id.volver_administrador);

        t1 = (EditText) findViewById(R.id.txt_usuario_administrador);
        t2 = (EditText) findViewById(R.id.txt_contraseña_administrador);

        progressDialog = new ProgressDialog(this);

        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);

        /*
        (b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(menu_administrador.this, menu_registrar_administrador.class);
                startActivity(i);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                b=true;
                Intent i = new Intent(menu_administrador.this, Menu_Todo.class);
                i.putExtra("administrador", b);
                startActivity(i);
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(menu_administrador.this, MainActivity.class);
                startActivity(i);
            }
        });

    */
    }

    private void registrarUsuario() {

        //Obtenemos el email y la contraseña desde las cajas de texto
        String correo = t1.getText().toString().trim();
        String contraseña = t2.getText().toString().trim();

        //Verificamos que las cajas de texto no esten vacías
        if (TextUtils.isEmpty(correo)) {//(precio.equals(""))
            Toast.makeText(this, "Se debe ingresar un email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(contraseña)) {
            Toast.makeText(this, "Falta ingresar la contraseña", Toast.LENGTH_LONG).show();
            return;
        }


        progressDialog.setMessage("Realizando registro en linea...");
        progressDialog.show();

        //registramos un nuevo correo
        firebaseAuth.createUserWithEmailAndPassword(correo, contraseña)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if (task.isSuccessful()) {

                            Toast.makeText(menu_administrador.this, "Se ha registrado el correo con el email: " + t1.getText(), Toast.LENGTH_LONG).show();
                        } else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {//si se presenta una colisión
                                Toast.makeText(menu_administrador.this, "Ese correo ya existe ", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(menu_administrador.this, "No se pudo registrar el correo ", Toast.LENGTH_LONG).show();
                            }
                        }
                        progressDialog.dismiss();
                    }
                });

    }

    private void loguearUsuario() {
        //Obtenemos el email y la contraseña desde las cajas de texto

        final String email = t1.getText().toString().trim();
        String password = t2.getText().toString().trim();


        //Verificamos que las cajas de texto no esten vacías
        if (TextUtils.isEmpty(email)) {//(precio.equals(""))
            Toast.makeText(this, "Se debe ingresar un email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Falta ingresar la contraseña", Toast.LENGTH_LONG).show();
            return;
        }


        progressDialog.setMessage("Realizando consulta en linea...");
        progressDialog.show();

        //loguear usuario
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if (task.isSuccessful()) {
                            int pos = email.indexOf("@");
                            String user = email.substring(0, pos);
                            Toast.makeText(menu_administrador.this, "Bienvenido: " + t1.getText(), Toast.LENGTH_LONG).show();
                            Intent intencion = new Intent(getApplication(), Menu_Todo.class);
                            intencion.putExtra("correoh", t1.getText().toString() + "");
                            b=true;
                            intencion.putExtra("administrador", b);
                            startActivity(intencion);
                            overridePendingTransition(R.anim.slide_right, R.anim.slide_out_left);

                        } else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException) {//si se presenta una colisión
                                Toast.makeText(menu_administrador.this, "Ese usuario ya existe ", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(menu_administrador.this, "No se pudo registrar el usuario ", Toast.LENGTH_LONG).show();
                            }
                        }
                        progressDialog.dismiss();
                    }
                });


    }

    private void salirUsuario(){
        Intent i = new Intent(menu_administrador.this, MainActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_left, R.anim.slide_out_right);
    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.registrar_administrador:
                //Invocamos al método:
                registrarUsuario();
                break;
            case R.id.continuar_administrador:
                loguearUsuario();
                break;
            case R.id.volver_administrador:
                salirUsuario();
                break;
        }


    }

    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.slide_left, R.anim.slide_out_right);
    }

}