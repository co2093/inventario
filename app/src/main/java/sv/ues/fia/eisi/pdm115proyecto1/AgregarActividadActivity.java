package sv.ues.fia.eisi.pdm115proyecto1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class AgregarActividadActivity extends AppCompatActivity {

    ImageButton btnAgregar;
    ControlDB helper;
    EditText editNombre;
    EditText editID;
    EditText editUbicacion;
    ImageButton btnRegresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_actividad);

        helper = new ControlDB(this);
        btnAgregar = findViewById(R.id.btnAgregarActividadN);
        editNombre =findViewById(R.id.editTextNombreActividadN);
        editID = findViewById(R.id.editTextActividadIDN);
        editUbicacion = findViewById(R.id.editTextUbicacionN);
        btnRegresar = findViewById(R.id.btnRegresarActividadN);


        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MenuActividadActivity.class);
                startActivityForResult(intent,0);
            }
        });

    }

    public void insertarActividad(View view){

        if(editID.getText().toString().isEmpty() || editNombre.getText().toString().isEmpty() || editUbicacion.getText().toString().isEmpty()){
            Toast.makeText(AgregarActividadActivity.this, R.string.todos, Toast.LENGTH_LONG).show();
        }else {

            String nombre = editNombre.getText().toString();
            String ubicacion = editUbicacion.getText().toString();
            Integer id = Integer.valueOf(editID.getText().toString());

            String regInsertados;

            Actividad actividad = new Actividad();
            actividad.setNombreActividad(nombre);
            actividad.setUbicacion(ubicacion);
            actividad.setIdActividad(id);
            helper.abrir();
            regInsertados = helper.insertar(actividad);
            helper.cerrar();
            Toast.makeText(this, regInsertados, Toast.LENGTH_LONG).show();
        }
    }

}