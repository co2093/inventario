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

public class ServiciosWebActualizarDocente extends AppCompatActivity {

    ControlDB db;
    static List<Docente> listaDocentes;
    static List<String> nombreDocentes;

    ListView listViewDocentes;
    ImageButton actualizar, inicio;
    private final String urlHostingGratuito = "https://proyecto1pdm115.000webhostapp.com/ws_actualizar_docente.php";

    @SuppressLint("NewApi")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicios_web_actualizar_docente);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        db = new ControlDB(this);

        listaDocentes = new ArrayList<Docente>();
        nombreDocentes = new ArrayList<String>();
        actualizar = findViewById(R.id.btnActualizarDocSW);

        listViewDocentes = findViewById(R.id.listado_docenteSW);
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
        String docentesExternos = Controlador.obtenerRespuestaPeticion(url, this);
        try {
            listaDocentes.addAll(Controlador.obtenerDocenteExterno(docentesExternos, this));
            actualizarListView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void guardarD(View v) {
        db.abrir();
        for(int i=0; i < listaDocentes.size();i++){
            Log.v("guardar",db.insertar(listaDocentes.get(i)));
        }
        db.cerrar();
        Toast.makeText(this, "Guardado con exito", Toast.LENGTH_LONG).show();
        listaDocentes.removeAll(listaDocentes);
        actualizarListView();
    }

    private void actualizarListView() {
        String dato = "";
        nombreDocentes.clear();
        for (int i = 0; i < listaDocentes.size(); i++) {
            dato = listaDocentes.get(i).getId() + "    "
                    + listaDocentes.get(i).getNombre() + "    "
                    + listaDocentes.get(i).getApellido();
            nombreDocentes.add(dato);
        }
        eliminarElementosDuplicados();
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, nombreDocentes);
        listViewDocentes.setAdapter(adaptador);
    }

    private void eliminarElementosDuplicados() {
        HashSet<Docente> conjuntoMateria = new HashSet<Docente>();
        conjuntoMateria.addAll(listaDocentes);
        listaDocentes.clear();
        listaDocentes.addAll(conjuntoMateria);

        HashSet<String> conjuntoNombre = new HashSet<String>();
        conjuntoNombre.addAll(nombreDocentes);
        nombreDocentes.clear();
        nombreDocentes.addAll(conjuntoNombre);
    }








}