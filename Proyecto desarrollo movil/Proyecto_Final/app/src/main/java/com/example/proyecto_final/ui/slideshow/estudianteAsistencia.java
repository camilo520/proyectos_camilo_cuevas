package com.example.proyecto_final.ui.slideshow;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.proyecto_final.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class estudianteAsistencia extends Fragment {

    private Button b1;
    private  Spinner sp1;
    private RadioButton radio1,radio2;
    public String usuarioEstu;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_estudiante_asis, container, false);

        b1 = (Button) view.findViewById(R.id.button_registrar_asis);

        sp1 = (Spinner) view.findViewById(R.id.spinner_asis);

        String[]opciones={"Gestion","Aplicaciones Moviles","Desarrollo Web"};
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, opciones);
        sp1.setAdapter(adapter);

        radio1 = (RadioButton) view.findViewById(R.id.radioVirtual);
        radio2 = (RadioButton) view.findViewById(R.id.radioPresencial);

        usuarioEstu=getActivity().getIntent().getStringExtra("usuario_estudiante");

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                asistencia();
            }
        });

        return view;
    }

    private void asistencia() {

        String materia=sp1.getSelectedItem().toString().trim();
        String modalidad="";
        final String user = usuarioEstu;

        if(radio1.isChecked()){
            modalidad="virtual";
        }else if(radio2.isChecked()){
            modalidad="presencial";
        }


        Map<String, Object> estudiante = new HashMap<>();
        estudiante.put("usuario", user);
        estudiante.put("materia", materia);
        estudiante.put("modalidad", modalidad);

        // Add a new document with a generated ID
        db.collection("Asistencia")
                .add(estudiante)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getContext(),"Se agrego con exito", Toast.LENGTH_LONG).show();
                        Log.d("Que dicen","DocumentSnapshot added with ID: " + documentReference.getId());


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
}
