package es.codigoandroid.geoturse;

/**
 * Created by carme_000 on 14/06/2017.
 */
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.StrictMode;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class GalleryAdapter extends BaseAdapter
{
    Context context;
    Integer[] imagenes;
    String[] galeria;
    int background;
    //guardamos las imágenes reescaladas para mejorar el rendimiento ya que estas operaciones son costosas
    //se usa SparseArray siguiendo la recomendación de Android Lint
    SparseArray<Bitmap> imagenesEscaladas = new SparseArray<Bitmap>(7);

    public GalleryAdapter(Context context, Integer[] imagenes, String[] galeria)
    {
        super();
        this.imagenes = imagenes;
        this.context = context;
        this.galeria = galeria;

        //establecemos un marco para las imágenes (estilo por defecto proporcionado)
        //por android y definido en /values/attr.xml
        TypedArray typedArray = context.obtainStyledAttributes(R.styleable.Gallery1);
        background = typedArray.getResourceId(R.styleable.Gallery1_android_galleryItemBackground, 1);
        typedArray.recycle();
    }

    @Override
    public int getCount()
    {
        return galeria.length;
    }

    @Override
    public Object getItem(int position)
    {
        return position;
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        ImageView imagen = new ImageView(context);

        //reescalamos la imagen para evitar "java.lang.OutOfMemory" en el caso de imágenes de gran resolución
        //como es este ejemplo
        if (imagenesEscaladas.get(position) == null)
        {
            //Glide.with(context).load("http://www.andes.info.ec/sites/default/files/styles/large/public/field/image/salinas_1.jpg?itok=DZ7NxVqH").into(imagen);


                URL url = null;
                Bitmap image = null;
                try {
                    url = new URL(""+galeria[position]);

                try {
                    /*final BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeStream(url.openConnection().getInputStream(),null,options);

                    // Calculate inSampleSize
                    options.inSampleSize = calculateInSampleSize(options, 200, 0);

                    // Decode bitmap with inSampleSize set
                    options.inJustDecodeBounds = false;*/

                    //image = BitmapFactory.decodeStream(url.openConnection().getInputStream(),null,options);
                    image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    image = redimensionarImagenMaximo(image, 250, 200);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

            imagenesEscaladas.put(position, image);
            //Bitmap bitmap = BitmapUtils.decodeSampledBitmapFromResource(context.getResources(), imagenes[position], 120, 0);
            //imagenesEscaladas.put(position, bitmap);
        }
        imagen.setImageBitmap(imagenesEscaladas.get(position));
        //se aplica el estilo
        imagen.setBackgroundResource(background);

        return imagen;
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

}