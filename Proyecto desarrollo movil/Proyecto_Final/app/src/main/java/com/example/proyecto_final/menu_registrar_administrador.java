package com.example.proyecto_final;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class menu_registrar_administrador extends AppCompatActivity {

    public Button b1,b2;
    public EditText t1,t2,t3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_registrar_administrador);

        b1=(Button)findViewById(R.id.registrar_admin);
        b2=(Button)findViewById(R.id.volver_menu_admin);

        t1=(EditText) findViewById(R.id.txt_registrar_usuario);
        t2=(EditText) findViewById(R.id.txt_registrar_contraseña);
        t3=(EditText) findViewById(R.id.txt_registrar_correo);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(menu_registrar_administrador.this, menu_administrador.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_left, R.anim.slide_out_right);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(menu_registrar_administrador.this, menu_administrador.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_left, R.anim.slide_out_right);
            }
        });

    }
}