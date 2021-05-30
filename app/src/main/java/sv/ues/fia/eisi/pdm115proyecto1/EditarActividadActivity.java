package sv.ues.fia.eisi.pdm115proyecto1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.UnicodeSetSpanner;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class EditarActividadActivity extends AppCompatActivity {

    ImageButton btnAgregar;
    ControlDB helper;
    EditText editNombre;
    EditText editID;
    EditText editUbicacion;
    ImageButton btnRegresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_actividad);

        helper = new ControlDB(this);
        btnAgregar = findViewById(R.id.btnAgregarActividadEdit);
        editNombre =findViewById(R.id.editTextNombreActividadEdit);
        editID = findViewById(R.id.editTextActividadIDEdit);
        editUbicacion = findViewById(R.id.editTextUbicacionEdit);
        btnRegresar = findViewById(R.id.btnRegresarActividadEdit);

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MenuActividadActivity.class);
                startActivityForResult(intent,0);
            }
        });

    }

    public void actualizarActividad(View V){

        if(editID.getText().toString().isEmpty() || editNombre.getText().toString().isEmpty() || editUbicacion.getText().toString().isEmpty()){
            Toast.makeText(EditarActividadActivity.this, "Todos los campos son obligatorios", Toast.LENGTH_LONG).show();
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
            regInsertados = helper.actualizar(actividad);
            helper.cerrar();
            Toast.makeText(this, regInsertados, Toast.LENGTH_LONG).show();


        }
    }
}