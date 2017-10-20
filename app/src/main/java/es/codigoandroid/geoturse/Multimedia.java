package es.codigoandroid.geoturse;

import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayer.ErrorReason;
import com.google.android.youtube.player.YouTubePlayer.PlaybackEventListener;
import com.google.android.youtube.player.YouTubePlayer.PlayerStateChangeListener;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.google.android.youtube.player.YouTubePlayerView;

import es.codigoandroid.es.codigoandroid.datamanager.CouchbaseManager;
import es.codigoandroid.pojos.Recursos;

public class Multimedia extends  YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener  {
    private VideoView videoView;
    private int position = 0;


    public static final String Api_key= "AIzaSyB2iewTkSc6ldlbGo6Bt3YLDgyaQJ4c8nE";
    public String video_id= "dlnOz3v7U7U";

    CouchbaseManager<String, Recursos> dbaRecurso;
    public Recursos recursoAlmacenado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multimedia);
        dbaRecurso = new CouchbaseManager<String, Recursos>(this, Recursos.class);
        final String mostrarR = getIntent().getExtras().getString("recurso");
        recursoAlmacenado = dbaRecurso.get(mostrarR);

        separa(recursoAlmacenado);
        reproduce();

       }


       public void reproduce(){

           this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_USER);
           YouTubePlayerView ytpv = (YouTubePlayerView) findViewById(R.id.videoView);
           ytpv.initialize(Api_key,this);
       }



//-LJ0xkDLeE8
       public void separa(Recursos r){
           r.setUrl_video("https://www.youtube.com/watch?v=-LJ0xkDLeE8"); //hasta mientras q se implemente en el manager
           String string = r.getUrl_video();
           String[] parts = string.split("=");

           video_id=parts[1];
       }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        youTubePlayer.setPlayerStateChangeListener(playerStateChangeListener);
        youTubePlayer.setPlaybackEventListener(playbackEventListener);

        if(!b){
            youTubePlayer.cueVideo(video_id);
        }

    }
    private YouTubePlayer.PlaybackEventListener playbackEventListener = new YouTubePlayer.PlaybackEventListener() {
        @Override
        public void onBuffering(boolean arg0) {
        }
        @Override
        public void onPaused() {
        }
        @Override
        public void onPlaying() {
        }
        @Override
        public void onSeekTo(int arg0) {
        }
        @Override
        public void onStopped() {
        }
    };

    private YouTubePlayer.PlayerStateChangeListener playerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {
        @Override
        public void onAdStarted() {
        }
        @Override
        public void onError(YouTubePlayer.ErrorReason arg0) {
        }
        @Override
        public void onLoaded(String arg0) {
        }
        @Override
        public void onLoading() {
        }
        @Override
        public void onVideoEnded() {
        }
        @Override
        public void onVideoStarted() {
        }
    };

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(this,"Fallo en la inicializacion", Toast.LENGTH_LONG).show();
    }
}


