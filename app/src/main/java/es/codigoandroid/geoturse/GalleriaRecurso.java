package es.codigoandroid.geoturse;

/**
 * Created by carme_000 on 14/06/2017.
 */
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ImageView;


public class GalleriaRecurso extends Activity {

    ImageView imagenSeleccionada;
    Gallery gallery;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galeria);

        imagenSeleccionada = (ImageView) findViewById(R.id.seleccionada);

        final Integer[] imagenes = { R.drawable.a, R.drawable.b, R.drawable.c,
                R.drawable.d, R.drawable.e,R.drawable.f,R.drawable.g
        };

        gallery = (Gallery) findViewById(R.id.gallery);
        gallery.setAdapter(new GalleryAdapter(this, imagenes));
        //al seleccionar una imagen, la mostramos en el centro de la pantalla a mayor tamaño

        //con este listener, sólo se mostrarían las imágenes sobre las que se pulsa
        gallery.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            public void onItemClick(AdapterView parent, View v, int position, long id)
            {
                imagenSeleccionada.setImageBitmap(BitmapUtils.decodeSampledBitmapFromResource(getResources(), imagenes[position], 300, 0));
            }

        });

        //con este otro listener se mostraría la imagen seleccionada en la galería, esto es, la que se encuentre en el centro en cada momento
//		gallery.setOnItemSelectedListener(new OnItemSelectedListener() {
//
//			@Override
//			public void onItemSelected(AdapterView parent, View v, int position, long id)
//			{
//		        imagenSeleccionada.setImageBitmap(BitmapUtils.decodeSampledBitmapFromResource(getResources(), imagenes[position], 400, 0));
//			}
//
//			@Override
//			public void onNothingSelected(AdapterView<?> arg0)
//			{
//				// TODO Auto-generated method stub
//
//			}
//		});

    }



}