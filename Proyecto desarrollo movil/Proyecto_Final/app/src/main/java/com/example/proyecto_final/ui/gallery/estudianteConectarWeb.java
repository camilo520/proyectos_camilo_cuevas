package com.example.proyecto_final.ui.gallery;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.proyecto_final.DeveloperKey;
import com.google.android.youtube.player.*;

import com.example.proyecto_final.R;

public class estudianteConectarWeb extends Fragment {

    private Button b1;
    YouTubePlayer player;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_estudiante_conectarweb, container, false);


        b1 = (Button) view.findViewById(R.id.boton_streaming_estudiante);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getContext(), DeveloperKey.class);
                startActivity(i);
            }
        });

        return view;
    }

}