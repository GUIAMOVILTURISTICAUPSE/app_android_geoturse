package es.codigoandroid.geoturse;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
//import com.couchbase.lite.Attachment; //Probando Couchbaselite 2.6 donde no existe attachment sino blob
import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Document;

import java.io.InputStream;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import es.codigoandroid.pojos.Recursos;

/**
 * Created by Alvaro on 22/12/2016.
 */

public class ListaRecursoAdapter extends RecyclerView.Adapter<ListaRecursoAdapter.ListaRecursoViewHolder>{
    public List<Recursos> items;
    ListaRecursoViewHolder holder;
    private Database database;
    Fragment fragment;


    public ListaRecursoAdapter(List<Recursos> items, Database database, Fragment fragment) {
        this.items = items;
        this.database = database;
        this.fragment = fragment;
    }

    public static class ListaRecursoViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public ImageView imagen;
        public TextView nombre;
        public TextView direccion;
        public CardView cadrViewRecurso;

        public ListaRecursoViewHolder(View v) {
            super(v);
            imagen = (ImageView) v.findViewById(R.id.imagen);
            nombre = (TextView) v.findViewById(R.id.nombre);
            direccion = (TextView) v.findViewById(R.id.visitas);
            cadrViewRecurso = (CardView) v.findViewById(R.id.cardViewRecurso);

        }

    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public ListaRecursoViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_view_recurso, viewGroup, false);
         holder = new ListaRecursoViewHolder(v);
        return holder;
        //return new ListaRecursoViewHolder(v);
    }


    @Override
    public void onBindViewHolder(ListaRecursoViewHolder viewHolder, final int i) {
            /*Document document = database.getDocument(items.get(i).getNombre());
            Attachment attachment = null;
            attachment = document.getCurrentRevision().getAttachment("image");
            if (attachment != null) {
                InputStream is = null;
                try {
                    is = attachment.getContent();
                } catch (CouchbaseLiteException e) {
                    e.printStackTrace();
                }
                Drawable drawable = Drawable.createFromStream(is, "image");
                 if (drawable != null) {
                    Bitmap bitmapOrg = ((BitmapDrawable) drawable).getBitmap();
                    int width = bitmapOrg.getWidth();
                    int height = bitmapOrg.getHeight();
                    int newWidth = 350;
                    int newHeight = 350;
                    // calculate the scale
                    float scaleWidth = ((float) newWidth) / width;
                    float scaleHeight = ((float) newHeight) / height;
                    // create a matrix for the manipulation
                    Matrix matrix = new Matrix();
                    // resize the bit map
                    matrix.postScale(scaleWidth, scaleHeight);
                    Bitmap resizedBitmap = Bitmap.createBitmap(bitmapOrg, 0, 0,
                            width, height, matrix, true);
                    // make a Drawable from Bitmap to allow to set the BitMap
                    drawable = new BitmapDrawable(resizedBitmap);
                }

                viewHolder.imagen.setImageDrawable(drawable);
            }*/

        if (items.get(i).getImagenPrincipal()!=null) {
            //  obtenerImagen(items.get(i).getImagenPrincipal().getUrl(),viewHolder);
            //verificar si contiene la cadena de "googleusercontent"
            String aguja = "googleusercontent";             //palabra buscada
            String pajar = items.get(i).getImagenPrincipal().getUrl();    //texto

            //escapar y agregar limites de palabra completa - case-insensitive
            Pattern regex = Pattern.compile("\\b" + Pattern.quote(aguja) + "\\b", Pattern.CASE_INSENSITIVE);
            Matcher match = regex.matcher(pajar);

            //la palabra está en el texto??
            if (match.find()) {  //si se quiere encontrar todas las ocurrencias: cambiar el if por while
                Glide.with(fragment).load(items.get(i).getImagenPrincipal().getUrl() + "=s250").into(viewHolder.imagen);
                System.out.println("Encontrado: '" + match.group() + "' dentro de '" + pajar + "' en la posición " + match.start());
            } else {
                obtenerImagen(items.get(i).getImagenPrincipal().getUrl(),viewHolder);
                System.out.println("'" + aguja + "' NO está dentro de '" + pajar);
            }

        }else
        Glide.with(fragment).load("http://www.andes.info.ec/sites/default/files/styles/large/public/field/image/salinas_1.jpg?itok=DZ7NxVqH").into(viewHolder.imagen);


        //Glide.with(fragment).load(items.get(i).getImagenPrinc().getUrl()).into(viewHolder.imagen);
        //viewHolder.imagen.setImageDrawable(drawable);
          viewHolder.nombre.setText(items.get(i).getNombre());
        viewHolder.direccion.setText(items.get(i).getDireccion());
        final Recursos recursoEnviar = items.get(i);
        holder.cadrViewRecurso.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), RecursoDetalle.class);
               // Intent intent = new Intent(view.getContext(), RecursoActivity.class);
                intent.putExtra("recurso", recursoEnviar.getNombre());
                view.getContext().startActivity(intent);
            }

        });
    }

    private void obtenerImagen(String myfeed, ListaRecursoViewHolder viewHolder) {

        try {

            BackgroundTask task =new BackgroundTask();
            task.execute(myfeed);
            Bitmap imag= task.get();
            viewHolder.imagen.setImageBitmap(imag);
            // Toast.makeText(this, "Entro pero no funciona :( xD  " , Toast.LENGTH_LONG).show();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }




}