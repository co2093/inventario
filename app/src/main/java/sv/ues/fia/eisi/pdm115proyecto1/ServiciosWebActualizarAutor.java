package sv.ues.fia.eisi.pdm115proyecto1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.StrictMode;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
@SuppressLint("NewApi")

public class ServiciosWebActualizarAutor extends AppCompatActivity {

    ControlDB db;
    static List<Autor> listaAutores;
    static List<String> nombreAutores;
    EditText buscador;
    ListView listViewAutores;
    ImageButton actualizar, inicio;
    private final String urlHostingGratuito = "https://proyecto1pdm115.000webhostapp.com/ws_actualizar_autor.php";

    @SuppressLint("NewApi")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicios_web_actualizar_autor);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        db = new ControlDB(this);

        listaAutores = new ArrayList<Autor>();
        nombreAutores = new ArrayList<String>();
        actualizar = findViewById(R.id.btnActualizarAcSW);
        buscador = findViewById(R.id.editBuscarAuSW);
        listViewAutores = findViewById(R.id.listado_autorSW);
        inicio = findViewById(R.id.imageButtonMenuInicio);

        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscador.setText("");
            }
        });

        inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MenuServiciosWeb.class);
                startActivityForResult(intent,0);
            }
        });



    }

    public void servicioPHP(View v) {

        if (buscador.getText().toString().isEmpty()) {
            Toast.makeText(this, "Ingrese ID a buscar", Toast.LENGTH_SHORT).show();
        } else{

            String id = buscador.getText().toString();
        String url = "";
        switch (v.getId()) {

            case R.id.buttonConsultar:
                // it was the second button
                url = urlHostingGratuito + "?id=" + id;
                break;

        }
        String autoresExternos = Controlador.obtenerRespuestaPeticion(url, this);
        try {
            listaAutores.addAll(Controlador.obtenerAutorExterno(autoresExternos, this));
            actualizarListView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    }


    public void guardar(View v) {
        db.abrir();
        for(int i=0; i < listaAutores.size();i++){
            Log.v("guardar",db.insertar(listaAutores.get(i)));
        }
        db.cerrar();
        Toast.makeText(this, "Guardado con exito", Toast.LENGTH_LONG).show();
        listaAutores.removeAll(listaAutores);
        actualizarListView();
    }

    private void actualizarListView() {
        String dato = "";
        nombreAutores.clear();
        for (int i = 0; i < listaAutores.size(); i++) {
            dato = listaAutores.get(i).getId() + "    "
                    + listaAutores.get(i).getNombreAutor();
            nombreAutores.add(dato);
        }
        eliminarElementosDuplicados();
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, nombreAutores);
        listViewAutores.setAdapter(adaptador);
    }

    private void eliminarElementosDuplicados() {
        HashSet<Autor> conjuntoMateria = new HashSet<Autor>();
        conjuntoMateria.addAll(listaAutores);
        listaAutores.clear();
        listaAutores.addAll(conjuntoMateria);

        HashSet<String> conjuntoNombre = new HashSet<String>();
        conjuntoNombre.addAll(nombreAutores);
        nombreAutores.clear();
        nombreAutores.addAll(conjuntoNombre);
    }



}