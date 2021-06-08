package sv.ues.fia.eisi.pdm115proyecto1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;
import org.json.JSONObject;

@SuppressLint("NewApi")


public class ServiciosWebEquipoActivity extends AppCompatActivity {

    ImageButton btnRegresar;
    ControlDB helper;
    EditText editTextId, editTextNombre, editTextModelo, editTextMarca, editTextEstado, editTextfecha;
    Spinner spinnerCategorias;
    ArrayAdapter categoriasArrayAdapter;

    private final String urlHostingGratuito = "https://proyecto1pdm115.000webhostapp.com/ws_equipo_insert.php";

    @SuppressLint("NewApi")


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicios_web_equipo);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        btnRegresar =  findViewById(R.id.btnRegresarEquipoSW);
        helper = new ControlDB(ServiciosWebEquipoActivity.this);


        editTextId = findViewById(R.id.editTextIdEquipoSW);
        editTextNombre = findViewById(R.id.editTextNombreEquipoSW);
        spinnerCategorias = findViewById(R.id.spinnerCategoriasEquipoSW);
        editTextModelo = findViewById(R.id.editTextModeloEquipoSW);
        editTextMarca = findViewById(R.id.editTextMarcaEquipoSW);
        editTextEstado = findViewById(R.id.editTextEstadoEquipoSW);
        editTextfecha = findViewById(R.id.editTextDateEquipoSW);

        editTextEstado.setText("Disponible");

        llenarSpinner(helper);

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MenuEquipoInformaticoActivity.class);
                startActivityForResult(intent,0);
            }
        });


    }

    public void insertarEquipoSW(View v) {

        if(editTextId.getText().toString().isEmpty() || editTextfecha.getText().toString().isEmpty() ||editTextNombre.getText().toString().isEmpty() || editTextEstado.getText().toString().isEmpty() || editTextMarca.getText().toString().isEmpty() ||editTextModelo.getText().toString().isEmpty() || spinnerCategorias.getSelectedItem()==null){
            Toast.makeText(ServiciosWebEquipoActivity.this, R.string.todos, Toast.LENGTH_LONG).show();
        }else {

            Integer id = Integer.valueOf(editTextId.getText().toString());
            String nombre = editTextNombre.getText().toString();
            String estado = editTextEstado.getText().toString();
            String marca = editTextMarca.getText().toString();
            String modelo = editTextModelo.getText().toString();
            String fecha = editTextfecha.getText().toString();
            String categoria = spinnerCategorias.getSelectedItem().toString();

            String url = null;
            JSONObject datosNota = new JSONObject();
            JSONObject nota = new JSONObject();
            switch (v.getId()) {
                case R.id.btnAgregarEquipoSW:
                    url = urlHostingGratuito+ "?id=" + id + "&nombre=" + nombre + "&modelo=" + modelo + "&marca=" + marca + "&estado=" + estado + "&categoria=" + categoria + "&fecha=" + fecha;
                    Controlador.insertarEquipoExternoSW(url, this);
                    Toast.makeText(ServiciosWebEquipoActivity.this, "OK", Toast.LENGTH_LONG).show();
                    break;
            }

        }

    }



    public void llenarSpinner(ControlDB helper){
        categoriasArrayAdapter= new ArrayAdapter<String>(ServiciosWebEquipoActivity.this, android.R.layout.simple_expandable_list_item_1, helper.getCatNombres());
        spinnerCategorias.setAdapter(categoriasArrayAdapter);
    }
}
