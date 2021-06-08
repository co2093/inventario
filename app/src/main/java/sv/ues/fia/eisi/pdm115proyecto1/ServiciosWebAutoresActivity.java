package sv.ues.fia.eisi.pdm115proyecto1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import org.json.JSONObject;

@SuppressLint("NewApi")

public class ServiciosWebAutoresActivity extends AppCompatActivity {


    EditText editTextIdAutor;
    EditText editTextNombreAutor;
    ImageButton regresar;

    private final String urlHostingGratuito = "https://proyecto1pdm115.000webhostapp.com/ws_autor_insert.php";

    @SuppressLint("NewApi")


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicios_web_autores);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        editTextIdAutor = findViewById(R.id.editTextAutorIDSW);
        editTextNombreAutor = findViewById(R.id.editTextAutorSW);

        regresar =  findViewById(R.id.btnRegresarAutorSW);

        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MenuServiciosWeb.class);
                startActivityForResult(intent,0);
            }
        });


    }

    public void insertarAutoresWS(View v) {

        if(editTextIdAutor.getText().toString().isEmpty() || editTextNombreAutor.getText().toString().isEmpty()){
            Toast.makeText(ServiciosWebAutoresActivity.this, R.string.todos, Toast.LENGTH_LONG).show();
        }else {

            String id = editTextIdAutor.getText().toString();
            String nombre = editTextNombreAutor.getText().toString();


            String url = null;
            JSONObject datosNota = new JSONObject();
            JSONObject nota = new JSONObject();
            switch (v.getId()) {
                case R.id.btnAgregarAutorSW:
                    url = urlHostingGratuito+ "?id=" + id + "&nombre="
                            + nombre;
                    Controlador.insertarAutorExterno(url, this);
                    Toast.makeText(ServiciosWebAutoresActivity.this, R.string.completado, Toast.LENGTH_LONG).show();
                    break;
            }

        }

    }
}