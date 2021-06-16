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

public class ServiciosWebLibrosActivity extends AppCompatActivity {

    ControlDB helper;
    EditText editTextIsbn, editTextNombre, editTextEjemplar, editTextEditorial, editTextIdiomaLibro;
    Spinner spinnerAutores, spinnerIdiomas, spinnerEditoriales;
    ImageButton regresar;
    ArrayAdapter libroArrayAdapter, idiomArrayAdapter, editorialArrayAdapter;

    private final String urlHostingGratuito = "https://proyecto1pdm115.000webhostapp.com/ws_libro_insert.php";

    @SuppressLint("NewApi")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicios_web_libros);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        regresar =  findViewById(R.id.btnRegresarLibroSW);
        helper = new ControlDB(ServiciosWebLibrosActivity.this);

        editTextIsbn = findViewById(R.id.editTextISBNSW);
        editTextNombre = findViewById(R.id.editTextNombreLibroSW);
        spinnerAutores = findViewById(R.id.spinnerAutoresLibroSW);
        editTextEjemplar = findViewById(R.id.editTextAutorEjemplarSW);
       // editTextEditorial = findViewById(R.id.editTextEditorialSW);
    //    editTextIdiomaLibro = findViewById(R.id.editTextIdiomaLibroSW);
        spinnerIdiomas = findViewById(R.id.spinnerIdiomaLibro);
        spinnerEditoriales = findViewById(R.id.spinnerEditorial);

        llenarSpinner(helper);
        llenarSpinner2(helper);
        llenarSpinner3(helper);

        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MenuServiciosWeb.class);
                startActivityForResult(intent,0);
            }
        });
    }

    public void insertarLibroSW(View v) {

        if(editTextIsbn.getText().toString().isEmpty() || editTextNombre.getText().toString().isEmpty() || editTextEjemplar.getText().toString().isEmpty() ||spinnerIdiomas.getSelectedItem() == null || spinnerEditoriales.getSelectedItem() == null || spinnerAutores.getSelectedItem()==null){
            Toast.makeText(ServiciosWebLibrosActivity.this, R.string.todos, Toast.LENGTH_LONG).show();
        }else {

            Integer isbn = Integer.valueOf(editTextIsbn.getText().toString());
            String nombre = editTextNombre.getText().toString();
            Integer autor = Integer.valueOf(spinnerAutores.getSelectedItem().toString());
            Integer ejemplar = Integer.valueOf(editTextEjemplar.getText().toString());
            String editorial = spinnerEditoriales.getSelectedItem().toString();
            String idioma = spinnerIdiomas.getSelectedItem().toString();

            String url = null;
            JSONObject datosNota = new JSONObject();
            JSONObject nota = new JSONObject();
            switch (v.getId()) {
                case R.id.btnAgregarLibroSW:
                    url = urlHostingGratuito+ "?isbn=" + isbn + "&nombre_libro="
                            + nombre + "&autor=" + autor + "&ejemplar=" + ejemplar + "&editorial=" + editorial + "&idioma=" + idioma;
                    Controlador.insertarLibroExterno(url, this);
                    Toast.makeText(ServiciosWebLibrosActivity.this, "OK", Toast.LENGTH_LONG).show();
                    break;
            }

        }

    }

    public void llenarSpinner(ControlDB helper){
        libroArrayAdapter= new ArrayAdapter<Integer>(ServiciosWebLibrosActivity.this, android.R.layout.simple_expandable_list_item_1, helper.getAutoresID());
        spinnerAutores.setAdapter(libroArrayAdapter);
    }

    public void llenarSpinner2(ControlDB helper){
        idiomArrayAdapter= new ArrayAdapter<String>(ServiciosWebLibrosActivity.this, android.R.layout.simple_expandable_list_item_1, helper.getIdiomas());
        spinnerIdiomas.setAdapter(idiomArrayAdapter);
    }

    public void llenarSpinner3(ControlDB helper){
        editorialArrayAdapter= new ArrayAdapter<String>(ServiciosWebLibrosActivity.this, android.R.layout.simple_expandable_list_item_1, helper.getEditorialNombres());
        spinnerEditoriales.setAdapter(editorialArrayAdapter);
    }
}