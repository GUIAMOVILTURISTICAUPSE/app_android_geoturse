package es.codigoandroid.geoturse;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.couchbase.lite.Attachment;
import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Document;

import java.io.InputStream;
import java.util.List;

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
}