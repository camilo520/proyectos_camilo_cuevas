package com.example.proyecto_final.ui.slideshow;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.proyecto_final.MainActivity;
import com.example.proyecto_final.MapsActivity;
import com.example.proyecto_final.R;
import com.example.proyecto_final.menu_administrador;

public class estudianteUbicacionClase extends Fragment {

    private Button b1;
    private Spinner sp;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_estudiante_ubicacion, container, false);

        b1=(Button)view.findViewById(R.id.button_ubicacion_estudiante);
        sp=(Spinner)view.findViewById(R.id.spinner_ubicacion_estudiante);

        String[]opciones={"Gestion","Fisica","Quimica"};
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, opciones);
        sp.setAdapter(adapter);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), MapsActivity.class);
                i.putExtra("valorMateria", sp.getSelectedItem().toString());
                startActivity(i);
            }
        });

        return view;

    }
}