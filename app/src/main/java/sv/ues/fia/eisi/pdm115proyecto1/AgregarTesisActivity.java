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


public class AgregarTesisActivity extends AppCompatActivity {

    ImageButton btnAgregar, btnRegresar;
    ControlDB helper;
    EditText editTextTitulo, editTextFecha,editTextIdiomaTesis;
    Spinner spinnerAutores, spinnerIdiomas;
    ArrayAdapter tesisArrayAdapter, idiomArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_tesis);


        helper = new ControlDB(AgregarTesisActivity.this);
        btnAgregar = findViewById(R.id.btnAgregarTesis);
        btnRegresar = findViewById(R.id.btnRegresarTesis);
        editTextTitulo = findViewById(R.id.editTextTitulo);
        editTextFecha = findViewById(R.id.editTextFechaPub);
        spinnerAutores = findViewById(R.id.spinnerAutoresTesis);
       // editTextIdiomaTesis = findViewById(R.id.editTextIdiomaTesis);
        spinnerIdiomas = findViewById(R.id.spinnerIdiomaTesis);


        llenarSpinner(helper);
        llenarSpinner2(helper);

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MenuTesisActivity.class);
                startActivityForResult(intent,0);
            }
        });

    }

    public void insertarTesis(View view){


        if(editTextTitulo.getText().toString().isEmpty() || editTextFecha.getText().toString().isEmpty() || spinnerIdiomas.getSelectedItem() == null ||  spinnerAutores.getSelectedItem()==null){
            Toast.makeText(AgregarTesisActivity.this, R.string.todos, Toast.LENGTH_LONG).show();
        }else {


            String titulo = editTextTitulo.getText().toString();
            String autor = spinnerAutores.getSelectedItem().toString();
            String fechapub = editTextFecha.getText().toString();
            String idioma = spinnerIdiomas.getSelectedItem().toString();
            String regInsertados;

            Tesis tesis = new Tesis();
            tesis.setTitulo_tesis(titulo);
            tesis.setFecha_publicacion(fechapub);
            tesis.setId_autor(autor);
            tesis.setIdioma(idioma);


            helper.abrir();
            regInsertados = helper.insertar(tesis);
            helper.cerrar();
            Toast.makeText(this, regInsertados, Toast.LENGTH_LONG).show();
        }


    }

    public void llenarSpinner(ControlDB helper){
        tesisArrayAdapter= new ArrayAdapter<String>(AgregarTesisActivity.this, android.R.layout.simple_expandable_list_item_1, helper.getAlumnosCarnet());
        spinnerAutores.setAdapter(tesisArrayAdapter);
    }

    public void llenarSpinner2(ControlDB helper){
        idiomArrayAdapter= new ArrayAdapter<String>(AgregarTesisActivity.this, android.R.layout.simple_expandable_list_item_1, helper.getIdiomas());
        spinnerIdiomas.setAdapter(idiomArrayAdapter);
    }


}