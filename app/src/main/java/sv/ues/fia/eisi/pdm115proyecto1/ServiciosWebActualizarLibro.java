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

public class ServiciosWebActualizarLibro extends AppCompatActivity {

    ControlDB db;
    static List<Libro> listaLibros;
    static List<String> nombreLibros;

    ListView listViewLibros;
    ImageButton actualizar, inicio;
    private final String urlHostingGratuito = "https://proyecto1pdm115.000webhostapp.com/ws_actualizar_libro.php";

    @SuppressLint("NewApi")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicios_web_actualizar_libro);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        db = new ControlDB(this);

        listaLibros = new ArrayList<Libro>();
        nombreLibros = new ArrayList<String>();
        actualizar = findViewById(R.id.btnRegresarLibroSW);

        listViewLibros = findViewById(R.id.listado_libroSW);
        inicio = findViewById(R.id.imageButtonMenuInicio);

        inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MenuServiciosWeb.class);
                startActivityForResult(intent,0);
            }
        });

    }

    public void servicioPHP(View v) {


        String url = "";
        switch(v.getId()) {

            case R.id.buttonConsultar:
                // it was the second button
                url = urlHostingGratuito;
                break;

        }
        String librosExternos = Controlador.obtenerRespuestaPeticion(url, this);
        try {
            listaLibros.addAll(Controlador.obtenerLibroExterno(librosExternos, this));
            actualizarListView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void guardarL(View v) {
        db.abrir();
        for(int i=0; i < listaLibros.size();i++){
            Log.v("guardar",db.insertar(listaLibros.get(i)));
        }
        db.cerrar();
        Toast.makeText(this, "Guardado con exito", Toast.LENGTH_LONG).show();
        listaLibros.removeAll(listaLibros);
        actualizarListView();
    }

    private void actualizarListView() {
        String dato = "";
        nombreLibros.clear();
        for (int i = 0; i < listaLibros.size(); i++) {
            dato = listaLibros.get(i).getIsbn() + " "
                    + listaLibros.get(i).getNombreLibro() + " "
                    + listaLibros.get(i).getAutorId() + " "
                    + listaLibros.get(i).getEjemplar() + " "
                    + listaLibros.get(i).getEditorial() + " "
                    + listaLibros.get(i).getIdioma();
            nombreLibros.add(dato);
        }
        eliminarElementosDuplicados();
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, nombreLibros);
        listViewLibros.setAdapter(adaptador);
    }

    private void eliminarElementosDuplicados() {
        HashSet<Libro> conjuntoMateria = new HashSet<Libro>();
        conjuntoMateria.addAll(listaLibros);
        listaLibros.clear();
        listaLibros.addAll(conjuntoMateria);

        HashSet<String> conjuntoNombre = new HashSet<String>();
        conjuntoNombre.addAll(nombreLibros);
        nombreLibros.clear();
        nombreLibros.addAll(conjuntoNombre);
    }




}