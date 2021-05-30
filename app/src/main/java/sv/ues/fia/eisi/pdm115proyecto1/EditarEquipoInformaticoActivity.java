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


public class EditarEquipoInformaticoActivity extends AppCompatActivity {

    ImageButton btnAgregar, btnRegresar;
    ControlDB helper;
    EditText editTextID, editTextNombre, editTextModelo, editTextMarca, editTextFecha, editTextEstado;
    Spinner spinnerCategorias;
    ArrayAdapter categoriasArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_equipo_informatico);

        helper = new ControlDB(EditarEquipoInformaticoActivity.this);
        btnAgregar = findViewById(R.id.btnAgregarEquipoEdit);
        btnRegresar = findViewById(R.id.btnRegresarEquipoEdit);
        editTextID = findViewById(R.id.editTextIdEquipoEdit);
        editTextNombre = findViewById(R.id.editTextNombreEquipoEdit);
        spinnerCategorias = findViewById(R.id.spinnerCategoriasEquipoEdit);
        editTextModelo = findViewById(R.id.editTextModeloEquipoEdit);
        editTextMarca = findViewById(R.id.editTextMarcaEquipoEdit);
        editTextEstado = findViewById(R.id.editTextEstadoEdit);
        editTextFecha = findViewById(R.id.editTextDateEquipoEdit);

        llenarSpinner(helper);


        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MenuEquipoInformaticoActivity.class);
                startActivityForResult(intent,0);
            }
        });
    }

    public void actualizarEquipo(View v) {


        if (editTextID.getText().toString().isEmpty() || editTextFecha.getText().toString().isEmpty() ||editTextNombre.getText().toString().isEmpty() || editTextEstado.getText().toString().isEmpty() || editTextMarca.getText().toString().isEmpty() ||editTextModelo.getText().toString().isEmpty() || spinnerCategorias.getSelectedItem()==null) {
            Toast.makeText(EditarEquipoInformaticoActivity.this, "Todos los campos son obligatorios", Toast.LENGTH_LONG).show();
        } else {


            Integer id = Integer.valueOf(editTextID.getText().toString());
            String nombre = editTextNombre.getText().toString();
            String estado = editTextEstado.getText().toString();
            String marca = editTextMarca.getText().toString();
            String modelo = editTextModelo.getText().toString();
            String fecha = editTextFecha.getText().toString();
            String categoria = spinnerCategorias.getSelectedItem().toString();

            String regInsertados;

            EquipoInformatico equipoInformatico = new EquipoInformatico();
            equipoInformatico.setId_equipo(id);
            equipoInformatico.setCategoriaEquipo(categoria);
            equipoInformatico.setEstado(estado);
            equipoInformatico.setMarcaEquipo(marca);
            equipoInformatico.setModeloEquipo(modelo);
            equipoInformatico.setNombreEquipo(nombre);
            equipoInformatico.setFechaEquipoAdquisicion(fecha);

            helper.abrir();
            regInsertados = helper.actualizar(equipoInformatico);
            helper.cerrar();
            Toast.makeText(this, regInsertados, Toast.LENGTH_LONG).show();
        }
    }



    public void llenarSpinner(ControlDB helper){
        categoriasArrayAdapter= new ArrayAdapter<String>(EditarEquipoInformaticoActivity.this, android.R.layout.simple_expandable_list_item_1, helper.getCatNombres());
        spinnerCategorias.setAdapter(categoriasArrayAdapter);
    }

}