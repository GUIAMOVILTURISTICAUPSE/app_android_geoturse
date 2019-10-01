package es.codigoandroid.geoturse;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubeBaseActivity;

import java.util.ArrayList;

import es.codigoandroid.pojos.Animacion;
import es.codigoandroid.pojos.Recursos;
import es.codigoandroid.pojos.TipoAnimacion;

public class Multimedia extends  YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener  {
    private VideoView videoView;
    private int position = 0;
    private ArrayList<Animacion> listaAnimacion;//para pruebas
    private Animacion animacion; //para pruebas
    private TipoAnimacion tipoAnimacion; //para pruebas
    private String[] listavideos ;

    public static final String Api_key= "AIzaSyB2iewTkSc6ldlbGo6Bt3YLDgyaQJ4c8nE";
    public String video_id; //= "dlnOz3v7U7U";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multimedia);
       // dbaRecurso = new CouchbaseManager<String, Recursos>(this, Recursos.class);
        final String link_video = getIntent().getExtras().getString("link");
        final String mostrarR = getIntent().getExtras().getString("nombre");

        separa(link_video);
        reproduce();

       }


    public void reproduce(){

           this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_USER);
           YouTubePlayerView ytpv = (YouTubePlayerView) findViewById(R.id.videoView);
           ytpv.initialize(Api_key,this);
       }



//-LJ0xkDLeE8
public void prueba(Recursos r){
    listaAnimacion=new ArrayList<Animacion>();

    animacion=new Animacion();
    animacion.setTitulo("Video_Prueba1");
    animacion.setDescripcion("Alabarrada Video_Prueba1");
    animacion.setUrl("https://www.youtube.com/watch?v=-LJ0xkDLeE8");
    tipoAnimacion= TipoAnimacion.VIDEO;
    animacion.setTipo(tipoAnimacion);
    listaAnimacion.add(animacion);
    r.setAnimaciones(listaAnimacion);

}


public void separa(String r){
            String[] parts;
    int op=0;
        for(int i=0;i<(r.length()-1);i++) {
            if (r.charAt(i) == '=') {
                op=1;
            }
        }
            if(op==1) {
                parts = r.split("=");
                video_id = parts[1];
            }else{
                    parts = r.split("embed/");
                     video_id = parts[1];
                 }
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


