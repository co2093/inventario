package sv.ues.fia.eisi.pdm115proyecto1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

public class EditarTesisActivity extends AppCompatActivity {

    ImageButton btnAgregar, btnRegresar;
    ControlDB helper;
    EditText editTextTituloEdit, editTextFechaEdit,editTextIdiomaTesisEdit, editTextID;
    Spinner spinnerAutoresEdit, spinnerIdiomas;
    ArrayAdapter tesisArrayAdapter, idiomArrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_tesis);

        helper = new ControlDB(EditarTesisActivity.this);
        btnAgregar = findViewById(R.id.btnAgregarTesisEdit);
        btnRegresar = findViewById(R.id.btnRegresarTesisEdit);

        helper = new ControlDB(EditarTesisActivity.this);


        editTextTituloEdit = findViewById(R.id.editTextTituloEdit);
        editTextFechaEdit = findViewById(R.id.editTextFechaPubEdit);
        spinnerAutoresEdit = findViewById(R.id.spinnerAutoresTesisEdit);
      //  editTextIdiomaTesisEdit = findViewById(R.id.editTextIdiomaTesisEdit);
        editTextID = findViewById(R.id.editTextIDTesisEdit);
        spinnerIdiomas = findViewById(R.id.spinnerIdiomaTesisEdit);


        llenarSpinner(helper);
        llenarSpinner2(helper);

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarTesis(v);
            }
        });

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MenuTesisActivity.class);
                startActivityForResult(intent,0);
            }
        });
    }

    public void actualizarTesis(View v){


        if(editTextTituloEdit.getText().toString().isEmpty() || editTextID.getText().toString().isEmpty() ||editTextFechaEdit.getText().toString().isEmpty() || spinnerIdiomas.getSelectedItem() ==null ||  spinnerAutoresEdit.getSelectedItem()==null){
            Toast.makeText(EditarTesisActivity.this, R.string.todos, Toast.LENGTH_LONG).show();
        }else {

            String titulo = editTextTituloEdit.getText().toString();
            String autor = spinnerAutoresEdit.getSelectedItem().toString();
            String fechapub = editTextFechaEdit.getText().toString();
            String idioma = spinnerIdiomas.getSelectedItem().toString();
            int id = Integer.valueOf(editTextID.getText().toString());
            String regInsertados;

            Tesis tesis = new Tesis();
            tesis.setTitulo_tesis(titulo);
            tesis.setFecha_publicacion(fechapub);
            tesis.setId_autor(autor);
            tesis.setIdioma(idioma);
            tesis.setId_tesis(id);


            helper.abrir();
            regInsertados = helper.actualizar(tesis);
            helper.cerrar();
            Toast.makeText(this, regInsertados, Toast.LENGTH_LONG).show();

        }


    }

    public void llenarSpinner(ControlDB helper){
        tesisArrayAdapter= new ArrayAdapter<String>(EditarTesisActivity.this, android.R.layout.simple_expandable_list_item_1, helper.getAlumnosCarnet());
        spinnerAutoresEdit.setAdapter(tesisArrayAdapter);
    }

    public void llenarSpinner2(ControlDB helper){
        idiomArrayAdapter= new ArrayAdapter<String>(EditarTesisActivity.this, android.R.layout.simple_expandable_list_item_1, helper.getIdiomas());
        spinnerIdiomas.setAdapter(idiomArrayAdapter);
    }



}