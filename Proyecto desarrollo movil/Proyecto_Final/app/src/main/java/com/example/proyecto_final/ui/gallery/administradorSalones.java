package com.example.proyecto_final.ui.gallery;

import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.proyecto_final.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class administradorSalones extends Fragment {


    private Spinner sp1, sp2, sp3, sp4, sp5,sp6;
    private EditText e1;
    private Button b1,b2;
    FirebaseFirestore db;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_administrar_salones, container, false);

        db=FirebaseFirestore.getInstance();

        b1 = (Button) view.findViewById(R.id.boton_agregar_espacio);
        b2 = (Button) view.findViewById(R.id.boton_eliminar_espacio);

        sp1 = (Spinner) view.findViewById(R.id.spinnerNumeroSalon);

        sp2 = (Spinner) view.findViewById(R.id.spinnerMateriaClase);
        sp3 = (Spinner) view.findViewById(R.id.spinnerHoraClase);
        sp4 = (Spinner) view.findViewById(R.id.spinnerDuracionClase);
        sp5 = (Spinner) view.findViewById(R.id.spinnerProfesorClase);
        sp6 = (Spinner) view.findViewById(R.id.spinnerEstudianteClase);

        spinnerNumeroSalon();

        e1 = (EditText) view.findViewById(R.id.id_espacio_clase);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }

    private void spinnerNumeroSalon(){
        db.collection("Espacios").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {

                        String[] salon = new String[2];
                        salon[0]=document.get("salonnum").toString();
                        Log.e("TAG", document.get("salonnum").toString());
                        String[]opciones={"4201","4202","4203","4204","4205"};
                        List<String> group = Arrays.asList(document.get("salonnum").toString());
                        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, opciones);
                        sp1.setAdapter(adapter);

                        }


                    }
                }

        });
    }

}
