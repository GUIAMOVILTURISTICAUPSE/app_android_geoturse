package es.codigoandroid.geoturse;

/**
 * Created by carme_000 on 30/10/2017.
 */

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.couchbase.lite.Database;

import java.util.List;
import java.util.concurrent.ExecutionException;

import es.codigoandroid.pojos.Recursos;


/**
 * Created by Ravi Tamada on 18/05/16.
 */
public class ListaVideosAdapter extends  RecyclerView.Adapter<ListaVideosAdapter.ListaRecursoViewHolder>{
       public List<Recursos> items;
    ListaRecursoViewHolder holder;
        private Database database;
        Fragment fragment;

    public ListaVideosAdapter(List<Recursos> items, Database database, Fragment fragment) {
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

          if (items.get(i).getImagenPrincipal()!=null)
            obtenerImagen(items.get(i).getImagenPrincipal().getUrl(),viewHolder);
        else
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


    private void obtenerImagen(String myfeed, ListaRecursoAdapter.ListaRecursoViewHolder viewHolder) {

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
