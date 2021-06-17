package com.example.proyecto_final.ui.home;

import android.os.Bundle;
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

import com.example.proyecto_final.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class profesorCursos extends Fragment {


    FirebaseFirestore db;
    private Button b1;
    private TextView t1,t2,t3,t4;
    private EditText e1;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.fragment_profesor_cursos, container, false);
        db=FirebaseFirestore.getInstance();

        b1 = (Button) view.findViewById(R.id.boton_consultar_curso_profesor);

        e1 = (EditText) view.findViewById(R.id.edit_profesor_curso);

        t1 = (TextView) view.findViewById(R.id.text_salon_profesor);
        t2 = (TextView) view.findViewById(R.id.text_horario_profesor);
        t3 = (TextView) view.findViewById(R.id.text_fecha_profesor);
        t4 = (TextView) view.findViewById(R.id.text_cantidad_profesor);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                conseguirCurso();
            }
        });

        return view;
    }

    private void conseguirCurso(){

        String user = e1.getText().toString();
        if(!user.isEmpty()) {
            db.collection("Espacios").whereEqualTo("materia", user).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            t1.setText(document.get("salonnum").toString());
                            t2.setText(document.get("horadia").toString());
                            t3.setText(document.get("fechaclase").toString());
                            t4.setText("50");
                            Toast.makeText(getContext(), "Estos son los datos de la materia", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });
        }else{
            Toast.makeText(getContext(), "Ingrese la materia a consultar", Toast.LENGTH_LONG).show();
        }
    }
}