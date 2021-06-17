package com.example.proyecto_final.ui.slideshow;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.proyecto_final.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class encuestaPandemia extends Fragment{

    FirebaseFirestore db;
    private EditText e1, e2, e3, e4;
    private Button b1;
    private CheckBox c1,c2,c3,c4,c5,c6,c7,c8,c9;
    private int contador=0;
    private RadioButton radio1, radio2;
    private RadioGroup group;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_encuesta_pandemia, container, false);

        e1 = (EditText) view.findViewById(R.id.edit_usuario_encuesta);
        e2 = (EditText) view.findViewById(R.id.edit_correo_encuesta);
        e3 = (EditText) view.findViewById(R.id.edit_telefono_encuesta);
        e4 = (EditText) view.findViewById(R.id.edit_temperatura_encuesta);

        radio1 = (RadioButton) view.findViewById(R.id.radio_estudiante);
        radio2 = (RadioButton) view.findViewById(R.id.radio_profesor);

        c1 = (CheckBox) view.findViewById(R.id.checkBox_fiebre);
        c2 = (CheckBox) view.findViewById(R.id.checkBox_garganta);
        c3 = (CheckBox) view.findViewById(R.id.checkBox_congestion);
        c4 = (CheckBox) view.findViewById(R.id.checkBox_tos);
        c5 = (CheckBox) view.findViewById(R.id.checkBox_respirar);
        c6 = (CheckBox) view.findViewById(R.id.checkBox_fatiga);
        c7 = (CheckBox) view.findViewById(R.id.checkBox_escalofrios);
        c8 = (CheckBox) view.findViewById(R.id.checkBox_muscular);
        c9 = (CheckBox) view.findViewById(R.id.checkBox_ninguno);

        b1 = (Button) view.findViewById(R.id.boton_enviar_encuesta);

        db=FirebaseFirestore.getInstance();

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                enviarEncuesta();

            }
        });

        return view;
    }


    private void validarChebox(){

        if(c1.isChecked()==true){
            contador+=1;
        }
        if(c2.isChecked()==true){
            contador+=1;
        }
        if(c3.isChecked()==true){
            contador+=1;
        }
        if(c4.isChecked()==true){
            contador+=1;
        }
        if(c5.isChecked()==true){
            contador+=1;
        }
        if(c6.isChecked()==true){
            contador+=1;
        }
        if(c7.isChecked()==true){
            contador+=1;
        }if(c8.isChecked()==true){
            contador+=1;
        }

    }

    private void enviarEncuesta(){
        validarChebox();

        String usuario=e1.getText().toString().trim();
        String correo=e2.getText().toString().trim();
        String telefono=e3.getText().toString().trim();
        String temperatura="";

        String covid="";
        String tipousuario="";

        if (TextUtils.isEmpty(usuario)) {
            Toast.makeText(getContext(), "Falta ingresar el usuario", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(correo)) {
            Toast.makeText(getContext(), "Falta ingresar el correo", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(telefono)) {
            Toast.makeText(getContext(), "Falta ingresar el telefono", Toast.LENGTH_LONG).show();
            return;
        }

    if(!e4.getText().toString().equals("")){
        int temp = Integer.valueOf(e4.getText().toString());
        if(radio2.isChecked() || radio1.isChecked()) {

            if (contador >= 2 && temp >= 37) {
                covid = "con sintomas";
                Toast.makeText(getContext(), "Tienes sintomas de Covid", Toast.LENGTH_LONG).show();
                contador = 0;
            } else if (contador < 2 && temp < 37) {
                covid = "sin sintomas";
                Toast.makeText(getContext(), "No tienes sintomas de Covid", Toast.LENGTH_LONG).show();
                contador = 0;
            }else if (contador >= 2  && temp < 37) {
                covid = "sin sintomas";
                Toast.makeText(getContext(), "No tienes sintomas de Covid", Toast.LENGTH_LONG).show();
                contador = 0;
            }

            if (radio1.isChecked() == true) {
                tipousuario = "estudiante";
            } else if (radio2.isChecked() == true) {
                tipousuario = "profesor";
            }

            temperatura = String.valueOf(temp);

            Map<String, Object> espacios = new HashMap<>();
            espacios.put("nombreusuario", usuario);
            espacios.put("correo", correo);
            espacios.put("telefono", telefono);
            espacios.put("temperatura", temperatura);
            espacios.put("covid", covid);
            espacios.put("tipousuario", tipousuario);

            // Add a new document with a generated ID
            db.collection("Encuesta")
                    .add(espacios)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(getContext(), "Se envio con exito", Toast.LENGTH_LONG).show();
                            Log.d("Que dicen", "DocumentSnapshot added with ID: " + documentReference.getId());
                            e1.setText("");
                            e2.setText("");
                            e3.setText("");
                            e4.setText("");

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(), "No se pudo enviar", Toast.LENGTH_LONG).show();
                            Log.w("Error", "Error adding document", e);
                        }
                    });
        }else{
            Toast.makeText(getContext(),"Indica que tipo de usuario eres", Toast.LENGTH_LONG).show();
        }
        }else{
        Toast.makeText(getContext(),"Llena el campo de temperatura", Toast.LENGTH_LONG).show();
    }
    }
}
