package com.example.proyecto_final.ui.home;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.proyecto_final.Estudiante;
import com.example.proyecto_final.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.common.net.InternetDomainName;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class administradorEstudiantes extends Fragment {


    private Button b1,b2,b3,b4;
    DatabaseReference databaseReference;
    private EditText nombre, apellido, correo, usuario, contraseña, programa;

    FirebaseFirestore db = FirebaseFirestore.getInstance();


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.fragment_administrar_estudiantes, container, false);
        nombre = view.findViewById(R.id.edit_admin_nombre_estu);
        apellido = view.findViewById(R.id.edit_admin_apellido_estu);
        correo = view.findViewById(R.id.edit_admin_correo_estu);
        usuario = view.findViewById(R.id.edit_admin_nombreusu_estu);
        contraseña = view.findViewById(R.id.edit_admin_contraseña_estu);
        programa = view.findViewById(R.id.edit_admin_programa_estudiante);

        databaseReference= FirebaseDatabase.getInstance().getReference();

        b1 = (Button) view.findViewById(R.id.boton_agregar_estudiante_admin);
        b2 = (Button) view.findViewById(R.id.boton_consultar_estudiante_admin);
        b3 = (Button) view.findViewById(R.id.boton_modificar_estudiante_admin);
        b4 = (Button) view.findViewById(R.id.boton_eliminar_estudiante_admin);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarEstudiante(db);
            }

        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consultarEstudiante();
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarEstudiante();
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                eliminarEstudiante();


            }
        });


        return view;
    }

    private void actualizarEstudiante() {
        String user = usuario.getText().toString();
        if(!user.isEmpty()) {
            db.collection("Estudiantes").whereEqualTo("usuario", user).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {

                            String nom = nombre.getText().toString().trim();
                            String ape = apellido.getText().toString().trim();
                            String mail = correo.getText().toString().trim();
                            String user = usuario.getText().toString().trim();
                            String pass = contraseña.getText().toString().trim();
                            String pro = programa.getText().toString().trim();

                            Map<String, Object> estudiante = new HashMap<>();
                            estudiante.put("nombre", nom);
                            estudiante.put("apellido", ape);
                            estudiante.put("correo", mail);
                            estudiante.put("usuario", user);
                            estudiante.put("contraseña", pass);
                            estudiante.put("programa", pro);

                            document.getReference().update(estudiante);
                            nombre.setText("");
                            apellido.setText("");
                            correo.setText("");
                            usuario.setText("");
                            contraseña.setText("");
                            programa.setText("");
                            Log.d("Se actualizo", "Se pudo actualizar sin problema");
                            Toast.makeText(getContext(), "Se pudo actualizar sin problema", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });
        }else{
            Toast.makeText(getContext(), "Ingrese el usuario a modificar", Toast.LENGTH_LONG).show();
        }
    }

    private void eliminarEstudiante() {
        String user = usuario.getText().toString();
        if(!user.isEmpty()) {
            db.collection("Estudiantes").whereEqualTo("usuario", user).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {

                            document.getReference().delete();
                            nombre.setText("");
                            apellido.setText("");
                            correo.setText("");
                            usuario.setText("");
                            contraseña.setText("");
                            programa.setText("");
                            Log.d("Se elimino", "Se pudo eliminar sin problema");
                            Toast.makeText(getContext(), "Se elimino con exito", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });
        }else{
            Toast.makeText(getContext(), "Ingrese el usuario a eliminar", Toast.LENGTH_LONG).show();
        }
    }


    private void agregarEstudiante(FirebaseFirestore db) {
        String nom=nombre.getText().toString().trim();
        String ape=apellido.getText().toString().trim();
        String mail=correo.getText().toString().trim();
        String user=usuario.getText().toString().trim();
        String pass=contraseña.getText().toString().trim();
        String pro=programa.getText().toString().trim();

        if (TextUtils.isEmpty(nom)) {
            Toast.makeText(getContext(), "Falta ingresar el nombre", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(ape)) {
            Toast.makeText(getContext(), "Falta ingresar el apellido", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(mail)) {
            Toast.makeText(getContext(), "Falta ingresar el correo", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(user)) {
            Toast.makeText(getContext(), "Falta ingresar el usuario", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(pass)) {
            Toast.makeText(getContext(), "Falta ingresar la contraseña", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(pro)) {
            Toast.makeText(getContext(), "Falta ingresar el programa", Toast.LENGTH_LONG).show();
            return;
        }


        Map<String, Object> estudiante = new HashMap<>();
        estudiante.put("nombre", nom);
        estudiante.put("apellido", ape);
        estudiante.put("correo", mail);
        estudiante.put("usuario", user);
        estudiante.put("contraseña", pass);
        estudiante.put("programa", pro);

        // Add a new document with a generated ID
        db.collection("Estudiantes")
                .add(estudiante)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getContext(),"Se agrego con exito", Toast.LENGTH_LONG).show();
                        Log.d("Que dicen","DocumentSnapshot added with ID: " + documentReference.getId());
                        nombre.setText("");
                        apellido.setText("");
                        correo.setText("");
                        usuario.setText("");
                        contraseña.setText("");
                        programa.setText("");

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(),"No se pudo agregar", Toast.LENGTH_LONG).show();
                        Log.w("Error", "Error adding document", e);
                    }
                });

    }

    private void consultarEstudiante() {
        String user = usuario.getText().toString();
        if(!user.isEmpty()) {
            db.collection("Estudiantes").whereEqualTo("usuario", user).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Toast.makeText(getContext(), "Estos son los datos del usuario", Toast.LENGTH_LONG).show();
                            nombre.setText(document.get("nombre").toString());
                            apellido.setText(document.get("apellido").toString());
                            correo.setText(document.get("correo").toString());
                            programa.setText(document.get("programa").toString());
                            contraseña.setText(document.get("contraseña").toString());
                            Toast.makeText(getContext(), "Estos son los datos del estudiante", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });
        }else{
            Toast.makeText(getContext(), "Ingrese el usuario a consultar", Toast.LENGTH_LONG).show();
        }
    }

}