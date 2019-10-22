package es.codigoandroid.geoturse;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


public class Adaptador_ViewPagerPrincipal extends FragmentPagerAdapter {


    private int numeroDeSecciones;  //nº de secciones
    private Context contexto;       //contexto


    //recibimos en el constructor el gestor de fragmentos, el nº de secciones después de haber creado
    //las tabs y el contexto de aplicación
    public Adaptador_ViewPagerPrincipal(FragmentManager fm, int numeroDeSecciones, Context contexto) {
        super(fm);
        this.numeroDeSecciones=numeroDeSecciones;
        this.contexto=contexto;

    }

    @Override
    public Fragment getItem(int position) {

        // recibimos la posición por parametro y en función de ella devolvemos el Fragment correspondiente a esa sección.
        switch (position) {

            case 0: // siempre empieza desde 0

                return new Fragment_seccion1();
            case 1:

                return new Fragment_seccion2();
            case 2:

                return new Fragment_seccion3();

            // si la posición recibida no se corresponde a ninguna sección
            default:
                return null;
        }

    }

    /*  getCount() devuelve el nº de secciones, dato que recibiremos cuando instanciemos el adaptador
        desde nuestra actividad principal */
    @Override
    public int getCount() {
        return numeroDeSecciones;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // recibimos la posición por parametro y en función de ella devolvemos el titulo correspondiente.
        switch (position) {

            case 0: // siempre empieza desde 0
                return "Mapa";
            case 1:
                return "Lista";
            case 2:
                return "Perfil";

            // si la posición recibida no se corresponde a ninguna sección
            default:
                return null;
        }

    }
}