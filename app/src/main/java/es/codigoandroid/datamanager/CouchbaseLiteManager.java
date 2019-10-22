package es.codigoandroid.datamanager;

import com.couchbase.lite.CouchbaseLite;
import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.DatabaseConfiguration;
import com.couchbase.lite.Database;
import com.couchbase.lite.MutableDocument;
import com.couchbase.lite.Document;
import com.couchbase.lite.Query;
import com.couchbase.lite.QueryBuilder;
import com.couchbase.lite.DataSource;
import com.couchbase.lite.SelectResult;
import com.couchbase.lite.Expression;
import com.couchbase.lite.ResultSet;
import com.couchbase.lite.Endpoint;
import com.couchbase.lite.URLEndpoint;
import com.couchbase.lite.Replicator;
import com.couchbase.lite.ReplicatorConfiguration;
import com.couchbase.lite.BasicAuthenticator;
import com.couchbase.lite.Result;


import android.content.Context;
import android.util.Log;
import java.net.URI;
import java.net.URISyntaxException;

//For mapping Pojos
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;


import java.util.ArrayList;
import java.util.Map;

import es.codigoandroid.pojos.Recursos;
import es.codigoandroid.pojos.Usuario;


public class CouchbaseLiteManager<V> {

    private final Class<V> valueTypeParameterClass;
    public final String TAG ="CouchbaseLiteManager";
    public static Database database;
    private final String dataBaseName = "geoturse";
    final ObjectMapper mapper = new ObjectMapper();
    private Replicator replicator;

    public CouchbaseLiteManager(Context context, final Class<V> valueClass){

        //Para serializar
        this.valueTypeParameterClass = valueClass;

        configurarMapper();

        // Initialize the Couchbase Lite system
        CouchbaseLite.init(context);

        // Get the database (and create it if it doesn’t exist).
        try {
            DatabaseConfiguration config = new DatabaseConfiguration();
            database = new Database(dataBaseName, config);
            replicate();
        } catch (CouchbaseLiteException e) {
            Log.i(TAG, "Create Database Error::  " + e.getCode() + " Exception: " + e.toString());
            e.printStackTrace();
        }

    }

    public void query()
    {

        // Create a query to fetch documents of type SDK.
        Query query = QueryBuilder.select(SelectResult.all())
                .from(DataSource.database(database))
                .where(Expression.property("type").equalTo(Expression.string("SDK")));
        try {
            ResultSet result = query.execute();
            Log.i(TAG, "Number of rows ::  " + result.allResults().size());
        }catch (CouchbaseLiteException e) {
            Log.i(TAG, "Query Database Error::  " + e.getCode() + " Exception: " + e.toString());
            e.printStackTrace();
        }


    }

    public V get(String llave)
    {
        V res = null;
        if (llave != null)
        {
            Document doc = database.getDocument(llave);

            if (doc != null)
            {
                try {

                    //FIXME: El toMap no devuelve el Id del doc.
                    String key = doc.getId();
                    Map<String, Object> map = doc.toMap();
                    map.put("_id", key);


                    res = mapper.convertValue(map, valueTypeParameterClass);
                }catch (Exception e)
                {
                    e.printStackTrace();
                    Log.e("ErrorCouchbase", "Error de conversion de Map: " +
                            e.getMessage(), e);
                }
            }
        }
        return res;
    }

    /**
     * Persiste cualquier objeto serializable e
     * @param o
     */
    public void save(Object o)
    {

        Map<String, Object> props = mapper.convertValue(o, Map.class);
        String id = (String) props.get("_id");

        if(o!= null && o.getClass()== Usuario.class)
        {
            Usuario u = (Usuario) o;
            id = u.getEmail();
        }

        //Ponerle el tipo de Documento que estamos guardando para poder buscarlo con vistas luego
        props.put("documentClass", valueTypeParameterClass.toString());

        MutableDocument document = new MutableDocument(id);
        document.setData(props);
        try{

            database.save(document);

            Log.i(TAG, "Document ID :: " + document.getId());

        }catch(CouchbaseLiteException e)
        {
            e.printStackTrace();
            Log.i("CouchDB-Save", "Failed to write document to Couchbase database!: "+ e.getMessage());
        }
    }

    public void createNewCouchbaseDocument()
    {
        try {
            // Create a new document (i.e. a record) in the database.
            MutableDocument mutableDoc = new MutableDocument()
                    .setFloat("version", 2.0F)
                    .setString("type", "SDK");
// Save it to the database.
            database.save(mutableDoc);
            // Log the document ID (generated by the database) and properties
            Document document = database.getDocument(mutableDoc.getId());
            Log.i(TAG, "Document ID :: " + document.getId());
            //Log.i(TAG, "Learning " + document.getString("language"));
        } catch (CouchbaseLiteException e) {
            Log.i(TAG, "Create New Error::  " + e.getCode() + " Exception: " + e.toString());

            e.printStackTrace();
        }

    }

    public void updateDocument()
    {
        try {
            MutableDocument mutableDoc = new MutableDocument();
            // Update a document.
            mutableDoc = database.getDocument(mutableDoc.getId()).toMutable();
            mutableDoc.setString("language", "Java");
            database.save(mutableDoc);
            Document document = database.getDocument(mutableDoc.getId());
            Log.i(TAG, "Document ID :: " + document.getId());
        } catch (CouchbaseLiteException e) {
            Log.i(TAG, "Create New Error::  " + e.getCode() + " Exception: " + e.toString());

            e.printStackTrace();
        }

    }

    public void replicate()
    {
        try {
            // Create replicators to push and pull changes to and from the cloud.
            Endpoint targetEndpoint = new URLEndpoint(new URI("ws://facsistel.upse.edu.ec:4984/db"));
            ReplicatorConfiguration replConfig = new ReplicatorConfiguration(database, targetEndpoint);
            replConfig.setReplicatorType(ReplicatorConfiguration.ReplicatorType.PUSH_AND_PULL);

// Add authentication.
            //replConfig.setAuthenticator(new BasicAuthenticator("john", "pass"));

// Create replicator (be sure to hold a reference somewhere that will prevent the Replicator from being GCed)
            Replicator replicator = new Replicator(replConfig);

// Listen to replicator change events.
            replicator.addChangeListener(change -> {
                if (change.getStatus().getError() != null) {
                    Log.i(TAG, "Error code ::  " + change.getStatus().getError().getCode());
                }
            });

// Start replication.
            replicator.start();
        } catch (URISyntaxException e) {
            Log.i(TAG, "URI Error::  " + e.getMessage() + " Reason: " + e.getReason() + " Exception: " + e.toString());

            e.printStackTrace();
        }
    }

    private void stopReplication() {
        if (replicator != null) {
            replicator.stop();
            Log.i(TAG, "Replicator Stop ::  " + replicator.getStatus());
        }
    }

    public Database getDbCouchbase() {
        return database;
    }


    public ResultSet queryForDocumentType(){

        //FIXME: Por alug
        String documentClass = valueTypeParameterClass.toString();
        System.out.println(documentClass);
        Query query = QueryBuilder
                .select(SelectResult.all())
                .from(DataSource.database(database))
                .where(Expression.property("documentClass").equalTo(Expression.string(valueTypeParameterClass.toString())));
                //.orderBy(Ordering.expression(Meta.id));

        try {
            ResultSet rs = query.execute();
            return rs;
            /*for (Result result : rs) {
                Log.i("Sample", String.format("hotel id -> %s", result.getString("id")));
                Log.i("Sample", String.format("hotel name -> %s", result.getString("name")));
            }*/
        } catch (CouchbaseLiteException e) {
            Log.e("Sample", e.getLocalizedMessage());
        }
        return null;
    }

    public ArrayList<V> queryForDocumentTypeAsArrayList()
    {
        ArrayList<V> listaDeDocumentos = new ArrayList<>();
        final ResultSet queryPlacesRs = queryForDocumentType();

        for(Result r:queryPlacesRs.allResults())
        {
            Map<String, Object> map = r.toMap();
            V objetoAlmacenado = mapper.convertValue(map.get("geoturse"),valueTypeParameterClass);
            // V objetoAlmacenado = mapper.convertValue(map, valueTypeParameterClass);
            listaDeDocumentos.add(objetoAlmacenado);
        }
        return  listaDeDocumentos;
    }

    private void configurarMapper()
    {
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false);
        // mapper.registerModule(new JavaTimeModule());
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

    }

/*    public View registerViews() {
        View placesView = dbCouchbase.getView("lista_recursos");
        placesView.setMap(new Mapper() {
            @Override
            public void map(Map<String, Object> document, Emitter emitter) {

                if (valueTypeParameterClass.toString().equals(document.get("documentClass"))) {
                    emitter.emit(document.get("_id"), document);
                }
            }
        }, "1");
        return placesView;
    }*/
}
