package com.example.proyecto_final;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.proyecto_final.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

    public class DeveloperKey extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener{

        YouTubePlayer player;
        Button b1;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.streaming_estudiante_final);

            YouTubePlayerView playerView = (YouTubePlayerView)findViewById(R.id.playerView);
            playerView.initialize("AIzaSyDZIZny6bOC6ON7vgrC-pRSBLrCRYCQ-PE", this);

            b1=(Button)findViewById(R.id.boton_hd);

            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    player.setFullscreen(true);
                }
            });
        }

        @Override
        public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
            this.player=youTubePlayer;
            if(!b){
                player.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);
                player.loadVideo("a2XuwlQL5x0");

            }
        }

        @Override
        public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
            Toast.makeText(this, "Failured to Initialize!", Toast.LENGTH_LONG).show();
        }
}
