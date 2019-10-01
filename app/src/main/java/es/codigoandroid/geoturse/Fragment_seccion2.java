package es.codigoandroid.geoturse;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Query;
import com.couchbase.lite.Result;
import com.couchbase.lite.ResultSet;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import es.codigoandroid.pojos.Recursos;
import es.codigoandroid.pojos.TipoFiltro;
import es.codigoandroid.datamanager.CouchbaseLiteManager;


public class Fragment_seccion2 extends Fragment {
    private static final String TAG = "Fragment_seccion2";
    CouchbaseLiteManager<Recursos> dbaRecurso_f2;
    private ArrayList<Recursos> recursos_f2;
    private RecyclerView rvListaRecurso;
    private RadioButton rbCanton, rbParroquia, rbNombre, rbRNatural, rbMCultural ;
    private ViewGroup layout;
    private ImageButton btDefault;

    final ObjectMapper mapper = new ObjectMapper();

    View vista;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        vista = inflater.inflate(R.layout.fragment_seccion2, container, false);

        rbCanton = (RadioButton)vista.findViewById(R.id.rbCanton);
        rbParroquia = (RadioButton)vista.findViewById(R.id.rbParroquia);
        rbNombre = (RadioButton)vista.findViewById(R.id.rbNombre);
        layout = (ViewGroup) vista.findViewById(R.id.contentLayout);
        btDefault = (ImageButton) vista.findViewById(R.id.btDefault);

        rbCanton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
                addCanton_Parroquia(1);
                rbParroquia.setChecked(false);
                rbNombre.setChecked(false);
            }
        });
        rbParroquia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
                addCanton_Parroquia(2);
                rbCanton.setChecked(false);
                rbNombre.setChecked(false);
            }
        });
        rbNombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
                addNombre();
                rbCanton.setChecked(false);
                rbParroquia.setChecked(false);
              }
        });

        btDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
                rbCanton.setChecked(false);
                rbParroquia.setChecked(false);
                rbNombre.setChecked(false);
                inicializarDatos();
                inicializarAdaptador();
            }
        });

        dbaRecurso_f2 = new CouchbaseLiteManager<Recursos>(this.getActivity(), Recursos.class);

        rvListaRecurso = (RecyclerView) vista.findViewById(R.id.reciclador);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvListaRecurso.setLayoutManager(llm);

        inicializarDatos();
        inicializarAdaptador();

        return vista;
    }

    public void inicializarDatos(){
        recursos_f2 = new ArrayList<>();
        recursos_f2 = dbaRecurso_f2.queryForDocumentTypeAsArrayList();

/*        final ResultSet queryPlacesRs = dbaRecurso_f2.queryForDocumentType();

        for(Result r:queryPlacesRs.allResults())
        {
            Map<String, Object> map = r.toMap();
            Recursos recursoAlmacenado = mapper.convertValue(map.get("geoturse"), Recursos.class);
            recursos_f2.add(recursoAlmacenado);
        }*/
    }

    public void inicializarDatos(TipoFiltro filtro, String parametro){
        inicializarDatos();
        Log.d("Estoy aki", filtro + "," + parametro);

        for (Recursos r: recursos_f2) {
            if (filtro.toString().toLowerCase().equals("canton")) {
                if (r.getCanton() != null) {
                    if (r.getCanton().equals(parametro)) {
                        recursos_f2.add(r);
                    }
                }
            } else {
                if (filtro.toString().toLowerCase().equals("parroquia")) {
                    if (r.getParroquia() != null && r.getCanton().equals(parametro)) {
                        recursos_f2.add(r);
                    }
                } else {
                    if (filtro.toString().toLowerCase().equals("nombre")) {
                        if (r.getNombre() != null && r.getNombre().matches("" + parametro + ".*")) {
                            recursos_f2.add(r);
                        }
                    }
                }
            }

        }
        inicializarAdaptador();

    }

    public ListaRecursoAdapter adaptador;
    private void inicializarAdaptador(){
        adaptador = new ListaRecursoAdapter(recursos_f2, dbaRecurso_f2.getDbCouchbase(), this);
        rvListaRecurso.setAdapter(adaptador);
    }


    public void addNombre()
    {
        LayoutInflater inflater = LayoutInflater.from(vista.getContext());
        RelativeLayout relativeLayout = (RelativeLayout) inflater.inflate(R.layout.activity_por_nombre, null, false);

        final EditText ed = (EditText) relativeLayout.findViewById(R.id.editText);

        ed.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String item = ed.getText().toString();
                inicializarDatos(TipoFiltro.NOMBRE, item);
            }

            @Override
            public void afterTextChanged(Editable s) {
                String item = ed.getText().toString();
                inicializarDatos(TipoFiltro.NOMBRE, item);
            }
        });
        layout.addView(relativeLayout);
    }


    public void addCanton_Parroquia(int op)
    {
        LayoutInflater inflater = LayoutInflater.from(vista.getContext());
        RelativeLayout relativeLayout = (RelativeLayout) inflater.inflate(R.layout.activity_canton_parroquia, null, false);
        if (op==1){//lista de cantones
            Spinner spCP = (Spinner) relativeLayout.findViewById(R.id.spinnerCantonParroquia);
            //Creamos el adaptador
            ArrayAdapter adapter = ArrayAdapter.createFromResource(vista.getContext(),R.array.spinner_canton,android.R.layout.simple_spinner_item);
            //Añadimos el layout para el menú
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            //Le indicamos al spinner el adaptador a usar
            spCP.setAdapter(adapter);
            spCP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String item=parent.getItemAtPosition(position).toString();
                    inicializarDatos(TipoFiltro.CANTON, item);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }
        else {
            if (op == 2) {//lista de Parroquias
                Spinner spCP = (Spinner) relativeLayout.findViewById(R.id.spinnerCantonParroquia);
                //Creamos el adaptador
                ArrayAdapter adapter = ArrayAdapter.createFromResource(vista.getContext(), R.array.spinner_parroquia, android.R.layout.simple_spinner_item);
                //Añadimos el layout para el menú
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

               //Le indicamos al spinner el adaptador a usar
                spCP.setAdapter(adapter);
                spCP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String item=parent.getItemAtPosition(position).toString();
                        inicializarDatos(TipoFiltro.PARROQUIA, item);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            }
        }

        layout.addView(relativeLayout);
    }

    //limpiar el espacio se supone
    public void reset()
    {
        layout.removeAllViews();
    }


}
