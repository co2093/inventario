package sv.ues.fia.eisi.pdm115proyecto1;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.icu.text.UnicodeSetSpanner;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.os.Bundle;

public class EditarRazonActivity extends AppCompatActivity {

    ImageButton btnRegresar;
    ControlDB   helper;
    EditText editTextNombre, editTextDescripcion, editTextEquipo, editTextFechaT, editTextEstado, editTextID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_razon);

        btnRegresar = findViewById(R.id.btnRegresarEdit);


        editTextNombre =findViewById(R.id.editTextNombreRazonEdit);
        editTextDescripcion = findViewById(R.id.editTextEquipoEdit);
        editTextEquipo = findViewById(R.id.editTextEquipoEdit);
        editTextFechaT = findViewById(R.id.editTextFechaEdit);
        editTextEstado = findViewById(R.id.editTextEstadoEdit);
        editTextID = findViewById(R.id.editTextIdEdit);
        helper = new ControlDB(this);

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MenuRazonActivity.class);
                startActivityForResult(intent,0);
            }
        });

   }

   public void actualizarRazon(View view){


       Razon_Traslado rztado = new Razon_Traslado();
       rztado.setId_razon_traslado(Integer.valueOf(editTextID.getText().toString()));
       rztado.setNombre_razon_traslado(editTextNombre.getText().toString());
       rztado.setDescripcion_razon_traslado(editTextDescripcion.getText().toString());
       rztado.setFecha_traslado(editTextFechaT.getText().toString());
       rztado.setEquipo_informatico(editTextEquipo.getText().toString());
       rztado.setEstado(editTextEstado.getText().toString());

       helper.abrir();
       String s = helper.actualizar(rztado);
        helper.cerrar();
        Toast.makeText(EditarRazonActivity.this, s,Toast.LENGTH_LONG).show();
   }
}