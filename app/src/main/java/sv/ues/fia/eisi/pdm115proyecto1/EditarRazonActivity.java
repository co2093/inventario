package sv.ues.fia.eisi.pdm115proyecto1;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.icu.text.UnicodeSetSpanner;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;
import android.os.Bundle;

public class EditarRazonActivity extends AppCompatActivity {

    ImageButton btnRegresar;
    ControlDB   helper;
    EditText editTextNombre, editTextDescripcion, editTextEquipo, editTextFechaT, editTextEstado, editTextID;
    Spinner spinnerEquipo, idrazon;
    ArrayAdapter  equipoArrayAdapter, idraz;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_razon);

        btnRegresar = findViewById(R.id.btnRegresarEdit);


        editTextNombre =findViewById(R.id.editTextNombreRazonEdit);
        editTextDescripcion = findViewById(R.id.editTextDescripcionEdit);
        spinnerEquipo = findViewById(R.id.spinnerEquipoPrestamoN);
        editTextFechaT = findViewById(R.id.editTextFechaEdit);

        editTextEstado = findViewById(R.id.editTextEstadoEdit);
        idrazon = findViewById(R.id.spinnerIDRazon);
        helper = new ControlDB(this);
        llenarSpinnerE(helper);
        llenarSpinner(helper);


        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MenuRazonActivity.class);
                startActivityForResult(intent,0);
            }
        });

   }

   public void actualizarRazon(View view){

        if(idrazon.getSelectedItem()==null || spinnerEquipo.getSelectedItem() == null || editTextNombre.getText().toString().isEmpty() || editTextDescripcion.getText().toString().isEmpty() || editTextFechaT.getText().toString().isEmpty() || editTextEstado.getText().toString().isEmpty()){
            Toast.makeText(EditarRazonActivity.this, R.string.todos, Toast.LENGTH_SHORT);
        }
        else{
           Razon_Traslado rztado = new Razon_Traslado();
           rztado.setId_razon_traslado(Integer.valueOf(idrazon.getSelectedItem().toString()));
           rztado.setNombre_razon_traslado(editTextNombre.getText().toString());
           rztado.setDescripcion_razon_traslado(editTextDescripcion.getText().toString());
           rztado.setFecha_traslado(editTextFechaT.getText().toString());
           rztado.setEquipo_informatico(spinnerEquipo.getSelectedItem().toString());
           rztado.setEstado(editTextEstado.getText().toString());


           helper.abrir();
           String s = helper.actualizar(rztado);
           helper.cerrar();
           Toast.makeText(EditarRazonActivity.this, s,Toast.LENGTH_LONG).show();
       }
   }

    public void llenarSpinnerE(ControlDB helper){
        equipoArrayAdapter= new ArrayAdapter<Integer>(EditarRazonActivity.this, android.R.layout.simple_expandable_list_item_1, helper.getEquipoID());
        spinnerEquipo.setAdapter(equipoArrayAdapter);
    }

    public void llenarSpinner(ControlDB helper){
        idraz= new ArrayAdapter<Integer>(EditarRazonActivity.this, android.R.layout.simple_expandable_list_item_1, helper.getRazonID());
        idrazon.setAdapter(idraz);
    }
}