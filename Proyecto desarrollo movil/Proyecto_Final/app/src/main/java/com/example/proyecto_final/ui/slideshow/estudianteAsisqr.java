package com.example.proyecto_final.ui.slideshow;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


import com.example.proyecto_final.R;

import java.util.StringTokenizer;

public class estudianteAsisqr extends Fragment implements View.OnClickListener{


    private Button scan;
    private EditText usuario, correo, materia, curso, numerosilla, numerosalon, horario;
    public String usuarioEstu;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_estudiante_asisqr, container, false);

        usuario=view.findViewById(R.id.qrUsuario);
        materia=view.findViewById(R.id.qrMateria);
        curso=view.findViewById(R.id.qrCurso);
        numerosilla=view.findViewById(R.id.qrSilla);
        numerosalon=view.findViewById(R.id.qrSalon);
        horario=view.findViewById(R.id.qrHorario);
        usuarioEstu=getActivity().getIntent().getStringExtra("usuario_estudiante");

        disableEditText(usuario);
        disableEditText(materia);
        disableEditText(curso);
        disableEditText(numerosilla);
        disableEditText(numerosalon);
        disableEditText(horario);

        scan=view.findViewById(R.id.boton_escanear_qr);
        scan.setOnClickListener(this);


        return view;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            String scanContent = scanningResult.getContents();
            StringTokenizer t = new StringTokenizer(scanContent, "*");

            final String user = usuarioEstu;
            final String mat = t.nextToken();
            final String cur = t.nextToken();
            final String chair = t.nextToken();
            final String salo = t.nextToken();
            final String hora = t.nextToken();
            usuario.setText("" + user);
            materia.setText("" + mat);
            curso.setText("" + cur);
            numerosilla.setText("" + chair);
            numerosalon.setText("" + salo);
            horario.setText("" + hora);
            disableEditText(usuario);
            disableEditText(materia);
            disableEditText(curso);
            disableEditText(numerosilla);
            disableEditText(numerosalon);
            disableEditText(horario);

        } else {
            Toast toast = Toast.makeText(getContext(), "No se han recibido datos", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.boton_escanear_qr){
            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            scanIntegrator.initiateScan();
        }
    }

    private void disableEditText(EditText editText) {
        editText.setFocusable(false);
        editText.setEnabled(false);
        editText.setCursorVisible(false);
        editText.setKeyListener(null);
        editText.setBackgroundColor(Color.TRANSPARENT);
    }



}
