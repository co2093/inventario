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

public class ServiciosWebAlumnoActivity extends AppCompatActivity {

    EditText editTextIdAlumno;
    EditText editTextNombreIdAlumno;
    EditText editTextApellidoAlumno;
    ImageButton regresar;

    private final String urlHostingGratuito = "https://proyecto1pdm115.000webhostapp.com/ws_alumno_insert.php";

    @SuppressLint("NewApi")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicios_web_alumno);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        editTextIdAlumno = findViewById(R.id.editTextAlumnoIdSW);
        editTextNombreIdAlumno = findViewById(R.id.editTextNombreAlSW);
        editTextApellidoAlumno = findViewById(R.id.editTextApellidoAlSW);
        regresar =  findViewById(R.id.btnRegresarAlSW);

        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MenuServiciosWeb.class);
                startActivityForResult(intent,0);
            }
        });
    }

    public void insertarAlumnoWSDos(View v) {

        if(editTextIdAlumno.getText().toString().isEmpty() || editTextApellidoAlumno.getText().toString().isEmpty() || editTextNombreIdAlumno.getText().toString().isEmpty()){
            Toast.makeText(ServiciosWebAlumnoActivity.this, "Todos los campos son obligatorios", Toast.LENGTH_LONG).show();
        }else {

            String id = editTextIdAlumno.getText().toString();
            String nombre = editTextNombreIdAlumno.getText().toString();
            String apellido = editTextApellidoAlumno.getText().toString();

            String url = null;
            JSONObject datosNota = new JSONObject();
            JSONObject nota = new JSONObject();
            switch (v.getId()) {
                case R.id.btnAgregarAlSW:
                    url = urlHostingGratuito+ "?carnet=" + id + "&nombre="
                            + nombre + "&apellido=" + apellido;
                    Controlador.insertarAlumnoExternoDos(url, this);
                   // Toast.makeText(ServiciosWebAlumnoActivity.this, "Registro ingresado", Toast.LENGTH_LONG).show();
                    break;
            }

        }

    }

}