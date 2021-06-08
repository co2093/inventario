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

public class ServiciosWebDocentesActivity extends AppCompatActivity {

    EditText editTextIdDocente;
    EditText editTextNombreIdDocente;
    EditText editTextApellidoDocente;
    ImageButton regresar;

    private final String urlHostingGratuito = "https://proyecto1pdm115.000webhostapp.com/ws_docente_insert.php";

    @SuppressLint("NewApi")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicios_web_docente);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        editTextIdDocente = findViewById(R.id.editTextIDDocenteSW);
        editTextNombreIdDocente = findViewById(R.id.editTextNombreDocenteSW);
        editTextApellidoDocente = findViewById(R.id.editTextApellidoDocenteSW);
        regresar =  findViewById(R.id.btnRegresar);

        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MenuServiciosWeb.class);
                startActivityForResult(intent,0);
            }
        });

    }

    public void insertarDocenteWS(View v) {

        if(editTextIdDocente.getText().toString().isEmpty() || editTextNombreIdDocente.getText().toString().isEmpty() || editTextApellidoDocente.getText().toString().isEmpty()){
            Toast.makeText(ServiciosWebDocentesActivity.this, R.string.todos, Toast.LENGTH_LONG).show();
        }else {

            String id = editTextIdDocente.getText().toString();
            String nombre = editTextNombreIdDocente.getText().toString();
            String apellido = editTextApellidoDocente.getText().toString();

            String url = null;
            JSONObject datosNota = new JSONObject();
            JSONObject nota = new JSONObject();
            switch (v.getId()) {
                case R.id.btn_notaExterno:
                    url = urlHostingGratuito+ "?id=" + id + "&nombre="
                            + nombre + "&apellido=" + apellido;
                    Controlador.insertarDocenteExterno(url, this);
                    Toast.makeText(ServiciosWebDocentesActivity.this, R.string.completado, Toast.LENGTH_LONG).show();
                    break;
            }

        }

    }
}