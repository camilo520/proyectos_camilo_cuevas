package com.example.proyecto_final.ui.slideshow;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.proyecto_final.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

public class administrarCurso extends Fragment {

    private Button b1,b2,b3,b4,b5,b6;
    private Spinner sp1,sp2;
    private EditText materia,curso;
    private TextView t1, t2;
    FirebaseFirestore db;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_administrar_curso, container, false);

        db=FirebaseFirestore.getInstance();

        b1 = (Button) view.findViewById(R.id.boton_agregar_espacio_admin);
        b3 = (Button) view.findViewById(R.id.boton_modificar_espacio_admin);
        b4 = (Button) view.findViewById(R.id.boton_eliminar_espacio_admin);
        b5 = (Button) view.findViewById(R.id.date_picker);
        b6 = (Button) view.findViewById(R.id.time_picker);

        t1 = (TextView)view.findViewById(R.id.textofecha);
        t2 = (TextView)view.findViewById(R.id.textohora);

        materia = (EditText) view.findViewById(R.id.edit_admin_materia_salon);
        curso = (EditText) view.findViewById(R.id.edit_admin_curso_salon);

        sp1 = (Spinner) view.findViewById(R.id.spinner_salon);
        sp2 = (Spinner) view.findViewById(R.id.spinner_hora);

        String[]opciones={"4201","4202","4203","4204","4205"};
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, opciones);
        sp1.setAdapter(adapter);

        String[]opciones2={"1:30","2:00","2:30","3:00","3:30","4:00"};
        ArrayAdapter<String> adapter2=new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, opciones2);
        sp2.setAdapter(adapter2);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    agregarEspacios(db);
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarEspacios();
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarEspacios();
            }
        });

        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                darFecha();
            }
        });

        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                darTiempo();
            }
        });

        return view;
    }

    private void agregarEspacios(FirebaseFirestore db) {

        String salonnumero=sp1.getSelectedItem().toString().trim();
        String mat=materia.getText().toString().trim();
        String cur=curso.getText().toString().trim();
        String horasclase=sp2.getSelectedItem().toString().trim();
        String fechaclase=t1.getText().toString().trim();
        String fechahora=t2.getText().toString().trim();

        if (TextUtils.isEmpty(mat)) {
            Toast.makeText(getContext(), "Falta ingresar la materia", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(cur)) {
            Toast.makeText(getContext(), "Falta ingresar el curso", Toast.LENGTH_LONG).show();
            return;
        }


        Map<String, Object> espacios = new HashMap<>();
        espacios.put("salonnum", salonnumero);
        espacios.put("materia", mat);
        espacios.put("curso", cur);
        espacios.put("horasclase", horasclase);
        espacios.put("fechaclase", fechaclase);
        espacios.put("horadia", fechahora);

        // Add a new document with a generated ID
        db.collection("Espacios")
                .add(espacios)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getContext(),"Se agrego con exito", Toast.LENGTH_LONG).show();
                        Log.d("Que dicen","DocumentSnapshot added with ID: " + documentReference.getId());
                        materia.setText("");
                        curso.setText("");
                        t1.setText("");
                        t2.setText("");

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

    private void consultarEspacios() {

        String user = materia.getText().toString();
        if(!user.isEmpty()) {
            db.collection("Espacios").whereEqualTo("materia", user).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            curso.setText(document.get("curso").toString());
                            Toast.makeText(getContext(), "Estos son los datos de la materia", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });
        }else{
            Toast.makeText(getContext(), "Ingrese la materia a consultar", Toast.LENGTH_LONG).show();
        }
    }

    private void eliminarEspacios() {
        String user = materia.getText().toString();
        if(!user.isEmpty()) {

            db.collection("Espacios").whereEqualTo("materia", user).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {

                            document.getReference().delete();
                            materia.setText("");
                            curso.setText("");
                            Log.d("Se elimino", "Se pudo eliminar sin problema");
                            Toast.makeText(getContext(), "Se elimino con exito", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });
        }else{
            Toast.makeText(getContext(), "Ingrese la materia a eliminar", Toast.LENGTH_LONG).show();
        }
    }

    private void actualizarEspacios() {
        String user = materia.getText().toString();
        if(!user.isEmpty()) {
            db.collection("Espacios").whereEqualTo("materia", user).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {

                            String salonnumero=sp1.getSelectedItem().toString().trim();
                            String mat=materia.getText().toString().trim();
                            String cur=curso.getText().toString().trim();
                            String horasclase=sp2.getSelectedItem().toString().trim();
                            String fechaclase=t1.getText().toString().trim();
                            String fechahora=t2.getText().toString().trim();

                            Map<String, Object> espacios = new HashMap<>();
                            espacios.put("salonnum", salonnumero);
                            espacios.put("materia", mat);
                            espacios.put("curso", cur);
                            espacios.put("horasclase", horasclase);
                            espacios.put("fechaclase", fechaclase);
                            espacios.put("horadia", fechahora);

                            document.getReference().update(espacios);
                            materia.setText("");
                            curso.setText("");
                            t1.setText("");
                            t2.setText("");
                            Log.d("Se actualizo", "Se pudo actualizar sin problema");
                            Toast.makeText(getContext(), "Se pudo actualizar sin problema", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });
        }else{
            Toast.makeText(getContext(), "Ingrese la materia a modificar", Toast.LENGTH_LONG).show();
        }
    }

    private void darFecha(){
        Calendar calendar = Calendar.getInstance();

        int YEAR =calendar.get(Calendar.YEAR);
        int MONTH =calendar.get(Calendar.MONTH);;
        int DATE =calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePicker = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int date) {
            int mes=month+1;
            String fecha = year + "/" + mes + "/" + date;
            t1.setText(fecha);
            }
        }, YEAR, MONTH, DATE);
        datePicker.show();
    }

    private void darTiempo(){

        Calendar calendar = Calendar.getInstance();
        int HOUR = calendar.get(Calendar.HOUR);
        int MINUTE = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePicker = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hour, int minute) {
                String time= hour + ":" + minute;
                t2.setText(time);
            }
        }, HOUR, MINUTE, true);

        timePicker.show();;

    }
}