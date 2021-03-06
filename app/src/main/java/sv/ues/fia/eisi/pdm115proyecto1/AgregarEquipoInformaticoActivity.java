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

public class AgregarEquipoInformaticoActivity extends AppCompatActivity {

    ImageButton btnAgregar, btnRegresar;
    ControlDB helper;
    EditText editTextId, editTextNombre, editTextModelo, editTextMarca, editTextEstado, editTextfecha;
    Spinner spinnerCategorias;
    ArrayAdapter categoriasArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_equipo_informatico);

        helper = new ControlDB(AgregarEquipoInformaticoActivity.this);
        btnAgregar = findViewById(R.id.btnAgregarEquipoN);
        btnRegresar = findViewById(R.id.btnRegresarEquipoN);
        editTextId = findViewById(R.id.editTextIdEquipoN);
        editTextNombre = findViewById(R.id.editTextNombreEquipoN);
        spinnerCategorias = findViewById(R.id.spinnerCategoriasEquipo);
        editTextModelo = findViewById(R.id.editTextModeloEquipoN);
        editTextMarca = findViewById(R.id.editTextMarcaEquipoN);
        editTextEstado = findViewById(R.id.editTextEstadoEquipoN);
        editTextfecha = findViewById(R.id.editTextDateEquipoN);

        editTextEstado.setText("Disponible");

        llenarSpinner(helper);

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MenuEquipoInformaticoActivity.class);
                startActivityForResult(intent,0);
            }
        });
    }

    public void insertarEquipo(View view){


        if(editTextId.getText().toString().isEmpty() || editTextfecha.getText().toString().isEmpty() ||editTextNombre.getText().toString().isEmpty() || editTextEstado.getText().toString().isEmpty() || editTextMarca.getText().toString().isEmpty() ||editTextModelo.getText().toString().isEmpty() || spinnerCategorias.getSelectedItem()==null){
            Toast.makeText(AgregarEquipoInformaticoActivity.this, R.string.todos, Toast.LENGTH_LONG).show();
        }else {

            Integer id = Integer.valueOf(editTextId.getText().toString());
            String nombre = editTextNombre.getText().toString();
            String estado = editTextEstado.getText().toString();
            String marca = editTextMarca.getText().toString();
            String modelo = editTextModelo.getText().toString();
            String fecha = editTextfecha.getText().toString();
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
            regInsertados = helper.insertar(equipoInformatico);
            helper.cerrar();
            Toast.makeText(this, regInsertados, Toast.LENGTH_LONG).show();
        }


    }

    public void llenarSpinner(ControlDB helper){
        categoriasArrayAdapter= new ArrayAdapter<String>(AgregarEquipoInformaticoActivity.this, android.R.layout.simple_expandable_list_item_1, helper.getCatNombres());
        spinnerCategorias.setAdapter(categoriasArrayAdapter);
    }
}