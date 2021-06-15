package sv.ues.fia.eisi.pdm115proyecto1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

public class AgregarEditorialActivity extends AppCompatActivity {

    ImageButton btnAgregar, btnRegresar;
    ControlDB helper;
    EditText editTextId, editTextNombre;
    Spinner spinnerPais;
    ArrayAdapter paisArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_editorial);

        helper = new ControlDB(AgregarEditorialActivity.this);
        btnAgregar = findViewById(R.id.btnAgregarEditorialN);
        btnRegresar = findViewById(R.id.btnRegresarEditorialN);
        editTextId = findViewById(R.id.editTextIDEditorialN);
        editTextNombre = findViewById(R.id.editTextNombreEditorialN);
        spinnerPais = findViewById(R.id.spinnerPaisAgregar);

        llenarSpinner(helper);

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MenuEditorialActivity.class);
                startActivityForResult(intent,0);
            }
        });


    }

    public void insertarEditorial(View view){


        if(editTextId.getText().toString().isEmpty() || editTextNombre.getText().toString().isEmpty() || spinnerPais.getSelectedItem() == null){
            Toast.makeText(AgregarEditorialActivity.this, R.string.todos, Toast.LENGTH_LONG).show();
        }else {

            Integer id = Integer.valueOf(editTextId.getText().toString());
            String nombre = editTextNombre.getText().toString();
            String pais = spinnerPais.getSelectedItem().toString();

            String regInsertados;

            Editorial editorial = new Editorial();
            editorial.setId(id);
            editorial.setNombre(nombre);
            editorial.setPais(pais);

            helper.abrir();
            regInsertados = helper.insertar(editorial);
            helper.cerrar();
            Toast.makeText(this, regInsertados, Toast.LENGTH_LONG).show();
        }


    }


    public void llenarSpinner(ControlDB helper){
        paisArrayAdapter= new ArrayAdapter<String>(AgregarEditorialActivity.this, android.R.layout.simple_expandable_list_item_1, helper.getPaises());
        spinnerPais.setAdapter(paisArrayAdapter);
    }
}