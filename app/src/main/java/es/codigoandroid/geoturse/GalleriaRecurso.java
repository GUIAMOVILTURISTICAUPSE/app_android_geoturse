package es.codigoandroid.geoturse;

/**
 * Created by carme_000 on 14/06/2017.
 */
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import es.codigoandroid.es.codigoandroid.datamanager.CouchbaseManager;
import es.codigoandroid.pojos.Imagen;
import es.codigoandroid.pojos.Recursos;


public class GalleriaRecurso extends AppCompatActivity {

    ImageView imagenSeleccionada;
    Gallery gallery;
    TextView tv_descripcion;
    private ArrayList<Imagen> imagen=new ArrayList<Imagen>();
    private  int tamanio;

    CouchbaseManager<String, Recursos> dbaRecurso;
    public Recursos recursoAlmacenado;

   // public Imagen recursoAlmacenado;
    private TextView descripcion;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galeria);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarContacto);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imagenSeleccionada = (ImageView) findViewById(R.id.seleccionada);


        dbaRecurso = new CouchbaseManager<String, Recursos>(this, Recursos.class);
        final String mostrarR = getIntent().getExtras().getString("recurso");
       recursoAlmacenado = dbaRecurso.get(mostrarR);

        final Integer[] imagenes = { R.drawable.a, R.drawable.b, R.drawable.c,
                R.drawable.d, R.drawable.e, R.drawable.f, R.drawable.g
        };

      /*  final String[] galeria = { "http://www.guiaempresarial.org/america/ecuador/directorio/santa-elena/santa-elena-ecuador.jpg",
                "http://www.eluniverso.com/sites/default/files/fotos/2015/12/pr05d301215photo01_0.jpg",
                "http://www.ecuadorextreme.com.ec/wp-content/uploads/2015/07/web-PANORAMCA-SALINAS-11-Cortesia-Ministerio-de-Turismo.jpg",
                "http://www.eluniverso.com/sites/default/files/styles/nota_ampliada_normal_foto/public/fotos/2016/01/peninsulaturismo14567752.jpg?itok=eFSu2wUZ",
                "http://4.bp.blogspot.com/-uFYcqNsVPZA/U-BVaZ1b7ZI/AAAAAAAAARI/gYs9IccKV5Y/s1600/ballena.jpg",
                "http://www.andes.info.ec/sites/default/files/styles/large/public/field/image/montac3b1ita.jpg?itok=4UWe5Rj6",
                "https://4.bp.blogspot.com/-mKwvK27-Pnw/Vk-lphMHitI/AAAAAAAAAC4/F4psXBQouW0TdDW7zcCKgHj0EUM-crFhgCKgB/s1600/Santa%2BElena.jpg"
        };
*/

        if(!recursoAlmacenado.getGaleria().isEmpty()) {
            imagen = recursoAlmacenado.getGaleria();
            tamanio = imagen.size();//tamaño
            final String[] galeria = new String[tamanio];

            for (Imagen im : imagen) {
                galeria[tamanio - 1] = im.getUrl();
                tamanio--;
            }


            gallery = (Gallery) findViewById(R.id.gallery);
            gallery.setAdapter(new GalleryAdapter(this, imagenes, galeria));
            //al seleccionar una imagen, la mostramos en el centro de la pantalla a mayor tamaño


            //con este listener, sólo se mostrarían las imágenes sobre las que se pulsa
            gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView parent, View v, int position, long id) {
                    URL url = null;
                    Bitmap image = null;
                    try {
                        url = new URL("" + galeria[position]);

                        try {

                            /*final BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inJustDecodeBounds = true;
                            BitmapFactory.decodeStream(url.openConnection().getInputStream(),null,options);

                            // Calculate inSampleSize
                            options.inSampleSize = calculateInSampleSize(options, 500, 0);

                            // Decode bitmap with inSampleSize set
                            options.inJustDecodeBounds = false;
                            //return BitmapFactory.decodeResource(res, resId, options);*/


                            //image = BitmapFactory.decodeStream(url.openConnection().getInputStream(),null,options);
                            image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                            image = redimensionarImagenMaximo(image, 800, 600);


                            tv_descripcion = (TextView) findViewById(R.id.tv_descripcion);
                            tv_descripcion.setText("Holisssss  " + mostrarR);


                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                    imagenSeleccionada.setImageBitmap(image);
                    //imagenSeleccionada.setImageBitmap(BitmapUtils.decodeSampledBitmapFromResource(getResources(), imagenes[position], 300, 0));
                }


            });
        }else{
            //no existe imagen en recurso


        }
    }

    public Bitmap redimensionarImagenMaximo(Bitmap mBitmap, float newWidth, float newHeigth){
        //Redimensionamos
        int width = mBitmap.getWidth();
        int height = mBitmap.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeigth) / height;
        // create a matrix for the manipulation
        Matrix matrix = new Matrix();
        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);
        // recreate the new Bitmap
        return Bitmap.createBitmap(mBitmap, 0, 0, width, height, matrix, false);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }


}