package com.example.proyecto_final;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Menu_Todo extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private String menuestudiante="Menu Estudiantes";
    private String menuprofesor="Menu Profesor";
    private String menuadministrador="Menu Administrador";
    private boolean estu=false;
    private boolean prof=false;
    private boolean admini=false;
    private MenuItem item;
    public TextView t1;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu__todo);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //FloatingActionButton fab = findViewById(R.id.fab);

        String hola=getIntent().getStringExtra("correoh");
        String usuarioEstu=getIntent().getStringExtra("usuario_estudiante");
        String usuarioPro=getIntent().getStringExtra("usuario_profesor");
        item=(MenuItem)findViewById(R.id.action_settings);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.titulo_menu_interface);
        TextView navUsername2 = (TextView) headerView.findViewById(R.id.subtitulo_menu_interface);


        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child("Usuarios").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(final DataSnapshot snapshot : dataSnapshot.getChildren()){

                    databaseReference.child(snapshot.getKey()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        //NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_administrarEstu, R.id.nav_administrarProf, R.id.nav_administrarCurso, R.id.nav_estudianteAsisqr,
                R.id.nav_estudianteUbi, R.id.nav_estudianteConect, R.id.nav_estudianteAsis, R.id.nav_profesorCurso, R.id.nav_profesorStreaming,
                R.id.nav_administrarEspacios)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        Boolean admin = getIntent().getBooleanExtra("administrador", false);
        Boolean student = getIntent().getBooleanExtra("estudiante", false);
        Boolean teacher = getIntent().getBooleanExtra("profesor", false);

        if(admin==true){

            NavigationView vie = findViewById(R.id.nav_view);
            Menu menuNav = vie.getMenu();
            MenuItem nav_item = menuNav.findItem(R.id.nav_estudianteAsis);
            MenuItem nav_item2 = menuNav.findItem(R.id.nav_estudianteAsisqr);
            MenuItem nav_item3 = menuNav.findItem(R.id.nav_estudianteConect);
            MenuItem nav_item4 = menuNav.findItem(R.id.nav_estudianteUbi);
            MenuItem nav_item5 = menuNav.findItem(R.id.nav_profesorCurso);
            MenuItem nav_item6 = menuNav.findItem(R.id.nav_profesorStreaming);
            nav_item.setVisible(false);
            nav_item.setEnabled(false);
            nav_item2.setEnabled(false);
            nav_item2.setVisible(false);
            nav_item3.setEnabled(false);
            nav_item3.setVisible(false);
            nav_item4.setEnabled(false);
            nav_item4.setVisible(false);
            nav_item5.setEnabled(false);
            nav_item5.setVisible(false);
            nav_item6.setEnabled(false);
            nav_item6.setVisible(false);
            navUsername.setText("Menu Administrador");
            navUsername2.setText(hola);

            admin=false;

        }else if(student==true){
            NavigationView vie = findViewById(R.id.nav_view);
            Menu menuNav = vie.getMenu();
            MenuItem nav_item = menuNav.findItem(R.id.nav_administrarCurso);
            MenuItem nav_item2 = menuNav.findItem(R.id.nav_administrarEstu);
            MenuItem nav_item3 = menuNav.findItem(R.id.nav_administrarProf);
            MenuItem nav_item4 = menuNav.findItem(R.id.nav_profesorStreaming);
            MenuItem nav_item5 = menuNav.findItem(R.id.nav_profesorCurso);
            MenuItem nav_item6 = menuNav.findItem(R.id.nav_administrarEspacios);
            nav_item.setVisible(false);
            nav_item.setEnabled(false);
            nav_item2.setEnabled(false);
            nav_item2.setVisible(false);
            nav_item3.setEnabled(false);
            nav_item3.setVisible(false);
            nav_item4.setEnabled(false);
            nav_item4.setVisible(false);
            nav_item5.setEnabled(false);
            nav_item5.setVisible(false);
            nav_item6.setEnabled(false);
            nav_item6.setVisible(false);
            navUsername.setText("Menu Estudiante");
            navUsername2.setText(usuarioEstu);

            student=false;

        }else if(teacher==true){
            NavigationView vie = findViewById(R.id.nav_view);
            Menu menuNav = vie.getMenu();
            MenuItem nav_item = menuNav.findItem(R.id.nav_administrarCurso);
            MenuItem nav_item2 = menuNav.findItem(R.id.nav_administrarEstu);
            MenuItem nav_item3 = menuNav.findItem(R.id.nav_administrarProf);
            MenuItem nav_item4 = menuNav.findItem(R.id.nav_estudianteAsis);
            MenuItem nav_item5 = menuNav.findItem(R.id.nav_estudianteAsisqr);
            MenuItem nav_item6 = menuNav.findItem(R.id.nav_estudianteConect);
            MenuItem nav_item7 = menuNav.findItem(R.id.nav_estudianteUbi);
            MenuItem nav_item8 = menuNav.findItem(R.id.nav_administrarEspacios);
            nav_item.setVisible(false);
            nav_item.setEnabled(false);
            nav_item2.setVisible(false);
            nav_item2.setEnabled(false);
            nav_item3.setVisible(false);
            nav_item3.setEnabled(false);
            nav_item4.setVisible(false);
            nav_item4.setEnabled(false);
            nav_item5.setVisible(false);
            nav_item5.setEnabled(false);
            nav_item6.setVisible(false);
            nav_item6.setEnabled(false);
            nav_item7.setVisible(false);
            nav_item7.setEnabled(false);
            nav_item8.setVisible(false);
            nav_item8.setEnabled(false);
            navUsername.setText("Menu Profesor");
            navUsername2.setText(usuarioPro);

            teacher=false;

        }




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu__todo, menu);

        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.slide_left, R.anim.slide_out_right);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent i = new Intent(Menu_Todo.this, MainActivity.class);
                startActivity(i);
                //overridePendingTransition(R.anim.slide_right, R.anim.slide_out_left);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

}