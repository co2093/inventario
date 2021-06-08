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

public class EditarLibroActivity extends AppCompatActivity {

    ImageButton btnAgregar, btnRegresar;
    ControlDB helper;
    EditText editTextIsbn, editTextNombre, editTextEjemplar, editTextEditorial, editTextIdioma;
    Spinner spinnerAutores;
    ArrayAdapter libroArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_libro);

        helper = new ControlDB(EditarLibroActivity.this);
        btnAgregar = findViewById(R.id.btnAgregarLibroEdit);
        btnRegresar = findViewById(R.id.btnRegresarLibroEdit);
        editTextIsbn = findViewById(R.id.editTextISBNEdit);
        editTextNombre = findViewById(R.id.editTextNombreLibroEdit);
        spinnerAutores = findViewById(R.id.spinnerAutoresLibroEdit);
        editTextEjemplar = findViewById(R.id.editTextAutorEjemplarEdit);
        editTextEditorial = findViewById(R.id.editTextEditorialEdit);
        editTextIdioma = findViewById(R.id.editTextIdiomaEdit);

        llenarSpinner(helper);

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MenuLibroActivity.class);
                startActivityForResult(intent,0);
            }
        });

    }


    public void actualizarLibro(View v){


        if(editTextIsbn.getText().toString().isEmpty() || editTextNombre.getText().toString().isEmpty() || editTextIdioma.getText().toString().isEmpty() ||editTextEjemplar.getText().toString().isEmpty() || editTextEditorial.getText().toString().isEmpty() || spinnerAutores.getSelectedItem()==null){
            Toast.makeText(EditarLibroActivity.this, R.string.todos, Toast.LENGTH_LONG).show();
        }else {

            Integer isbn = Integer.valueOf(editTextIsbn.getText().toString());
            String nombre = editTextNombre.getText().toString();
            Integer autor = Integer.valueOf(spinnerAutores.getSelectedItem().toString());
            Integer ejemplar = Integer.valueOf(editTextEjemplar.getText().toString());
            String editorial = editTextEditorial.getText().toString();
            String idioma = editTextIdioma.getText().toString();

            String regInsertados;

            Libro libro = new Libro();
            libro.setIsbn(isbn);
            libro.setNombreLibro(nombre);
            libro.setAutorId(autor);
            libro.setEjemplar(ejemplar);
            libro.setEditorial(editorial);
            libro.setIdioma(idioma);

            helper.abrir();
            regInsertados = helper.actualizar(libro);
            helper.cerrar();
            Toast.makeText(this, regInsertados, Toast.LENGTH_LONG).show();
        }


    }

    public void llenarSpinner(ControlDB helper){
        libroArrayAdapter= new ArrayAdapter<Integer>(EditarLibroActivity.this, android.R.layout.simple_expandable_list_item_1, helper.getAutoresID());
        spinnerAutores.setAdapter(libroArrayAdapter);
    }



}