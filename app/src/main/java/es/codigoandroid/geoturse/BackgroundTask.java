package es.codigoandroid.geoturse;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by carme_000 on 27/09/2017.
 */

    public class BackgroundTask extends AsyncTask<String, Void, Bitmap> {

    private ImageView imag;

    protected Bitmap doInBackground(String... url){
        // download an image
        URL imageUrl = null;
        HttpURLConnection conn = null;


        try {

            imageUrl = new URL(url[0]);
            conn = (HttpURLConnection) imageUrl.openConnection();
            conn.connect();

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2; // el factor de escala a minimizar la imagen, siempre es potencia de 2

            Bitmap imag = BitmapFactory.decodeStream(conn.getInputStream(), new Rect(0, 0, 0, 0), options);
            return imag;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);

    }



}


