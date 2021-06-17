package com.example.proyecto_final;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    public ImageButton b1,b2,b3,b4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        b1=(ImageButton) findViewById(R.id.menu_administrador);
        b2=(ImageButton) findViewById(R.id.menu_estudiantes);
        b3=(ImageButton) findViewById(R.id.menu_profesores);
        b4=(ImageButton) findViewById(R.id.menu_salir);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this, menu_administrador.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_right, R.anim.slide_out_left);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this, menu_estudiante.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_right, R.anim.slide_out_left);
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this, menu_profesores.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_right, R.anim.slide_out_left);
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_MAIN);
                i.addCategory(Intent.CATEGORY_HOME);
                startActivity(i);

            }
        });
    }



    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.slide_left, R.anim.slide_out_right);
    }
}