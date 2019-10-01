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

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import es.codigoandroid.pojos.Imagen;
import es.codigoandroid.pojos.Recursos;

import es.codigoandroid.datamanager.CouchbaseLiteManager;

public class GalleriaRecurso extends AppCompatActivity {

    ImageView imagenSeleccionada;
   // Gallery gallery;
    private TextView  tv_descripcion;
    private ArrayList<Imagen> imagen=new ArrayList<Imagen>();

    CouchbaseLiteManager<Recursos> dbaRecurso;
    public Recursos recursoAlmacenado;


    String[] galeria;
    String[] autor;
    String[] descripcion;
    String[] fecha;
    String[] titulo;
    int[] votosFavor;
    int[] votosContra;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galeria);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarContacto);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imagenSeleccionada = (ImageView) findViewById(R.id.seleccionada);

        dbaRecurso = new CouchbaseLiteManager<Recursos>(this, Recursos.class);
        final String mostrarR = getIntent().getExtras().getString("recurso");
       recursoAlmacenado = dbaRecurso.get(mostrarR);

       final Integer[] imagenes = { R.drawable.a, R.drawable.b, R.drawable.c,
                R.drawable.d, R.drawable.e, R.drawable.f, R.drawable.g
        };

       // if(!recursoAlmacenado.getGaleria().isEmpty()){
            Gallery gallery;
            imagen = recursoAlmacenado.getGaleria();
           //int tamanio=imagen.size();//tamaño
            Datos(imagen.size());


           gallery = (Gallery) findViewById(R.id.gallery);
           gallery.setAdapter(new GalleryAdapter(this, imagenes, galeria));
            //al seleccionar una imagen, la mostramos en el centro de la pantalla a mayor tamaño
            MostrarGaleria(gallery);


       /* }else{
            //no existe imagen en recurso
            tv_descripcion = (TextView) findViewById(R.id.tv_descripcion);
            tv_descripcion.setText("No existe imagen de recurso seleccionado!");
        }*/
    }



   public void Datos(int tamanio){

        galeria = new String[tamanio];
        autor=new String[tamanio];
        descripcion=new String[tamanio];
        fecha=new String[tamanio];
        titulo=new String[tamanio];
        votosFavor=new int[tamanio];
        votosContra=new int[tamanio];

        //// FIXME: 12/09/2017 Validar que no sea null
        for (Imagen im : imagen) {
            if(im != null)
            {
                galeria[tamanio - 1] = im.getUrl();
                autor[tamanio - 1] = im.getAutor();
                descripcion[tamanio - 1] = im.getDescripcion();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                String fechaComoCadena = sdf.format(im.getFecha());
                fecha[tamanio - 1] = fechaComoCadena ;
                titulo[tamanio - 1] = im.getTitulo();
                votosFavor[tamanio - 1] = im.getVotosFavor();
                votosContra[tamanio - 1] = im.getVotosContra();
            }
            tamanio--;
        }
    }


public void mostrarInformacion(int position){
    tv_descripcion = (TextView) findViewById(R.id.tv_descripcion);
    tv_descripcion.setText("\nTitulo: "+titulo[position]+"\nDescripcion: "+descripcion[position]+
            "\nFecha: "+fecha[position]+ "\nAutor: "+autor[position]+
            "\nVotos a favor: "+votosFavor[position]+ "\nVotos a contra: "+votosContra[position]
    );

}

public void MostrarGaleria(Gallery gallery){
    //con este listener, sólo se mostrarían las imágenes sobre las que se pulsa
    gallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int position, long id) {
            URL url = null;
            Bitmap image = null;
            try {
                url = new URL("" + galeria[position]);
                // dato=galeria[position];
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

                    mostrarInformacion(position);


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