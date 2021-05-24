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

public class AgregarLibro extends AppCompatActivity {

    ImageButton btnAgregar, btnRegresar;
    ControlDB helper;
    EditText editTextIsbn, editTextNombre, editTextEjemplar, editTextEditorial;
    Spinner spinnerAutores;
    ArrayAdapter libroArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_libro);

        helper = new ControlDB(AgregarLibro.this);
        btnAgregar = findViewById(R.id.btnAgregarLibroN);
        btnRegresar = findViewById(R.id.btnRegresarLibroN);
        editTextIsbn = findViewById(R.id.editTextISBN);
        editTextNombre = findViewById(R.id.editTextNombreLibro);
        spinnerAutores = findViewById(R.id.spinnerAutoresLibro);
        editTextEjemplar = findViewById(R.id.editTextAutorEjemplar);
        editTextEditorial = findViewById(R.id.editTextEditorial);

        llenarSpinner(helper);

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MenuLibroActivity.class);
                startActivityForResult(intent,0);
            }
        });

    }

    public void insertarLibro(View view){


        if(editTextIsbn.getText().toString().isEmpty() || editTextNombre.getText().toString().isEmpty() || editTextEjemplar.getText().toString().isEmpty() || editTextEditorial.getText().toString().isEmpty() || spinnerAutores.getSelectedItem()==null){
            Toast.makeText(AgregarLibro.this, "Todos los campos son obligatorios", Toast.LENGTH_LONG).show();
        }else {

            Integer isbn = Integer.valueOf(editTextIsbn.getText().toString());
            String nombre = editTextNombre.getText().toString();
            Integer autor = Integer.valueOf(spinnerAutores.getSelectedItem().toString());
            Integer ejemplar = Integer.valueOf(editTextEjemplar.getText().toString());
            String editorial = editTextEditorial.getText().toString();

            String regInsertados;

            Libro libro = new Libro();
            libro.setIsbn(isbn);
            libro.setNombreLibro(nombre);
            libro.setAutorId(autor);
            libro.setEjemplar(ejemplar);
            libro.setEditorial(editorial);

            helper.abrir();
            regInsertados = helper.insertar(libro);
            helper.cerrar();
            Toast.makeText(this, regInsertados, Toast.LENGTH_LONG).show();
        }


    }

    public void llenarSpinner(ControlDB helper){
        libroArrayAdapter= new ArrayAdapter<Integer>(AgregarLibro.this, android.R.layout.simple_expandable_list_item_1, helper.getAutoresID());
        spinnerAutores.setAdapter(libroArrayAdapter);
    }


}