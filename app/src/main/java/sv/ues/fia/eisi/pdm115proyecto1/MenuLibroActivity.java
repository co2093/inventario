package sv.ues.fia.eisi.pdm115proyecto1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentProvider;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.KeyEvent;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.widget.ImageButton;
import android.app.AlertDialog;

import java.util.List;

public class MenuLibroActivity extends AppCompatActivity {

    ImageButton btnAgregarLibro, btnEditarLibro, btnActualizar;
    ListView listadoLibro;
    ControlDB DBhelper;
    ArrayAdapter busquedaAdapter;
    ArrayAdapter libroArrayAdapter;
    EditText editBusqueda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_libro);

        btnAgregarLibro = findViewById(R.id.btnAgregarLibro);
        btnEditarLibro = findViewById(R.id.btnEditarLibro);
        btnActualizar = findViewById(R.id.btnActualizarLibro);
        listadoLibro = findViewById(R.id.listado_libro);
        editBusqueda = findViewById(R.id.editBuscarLibro);

        DBhelper = new ControlDB(MenuLibroActivity.this);
        List<Libro> librosListado = DBhelper.getLibros();

        listadoLibro(DBhelper);

        btnAgregarLibro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AgregarLibro.class);
                startActivityForResult(intent, 0);
            }
        });

        btnEditarLibro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EditarLibroActivity.class);
                startActivityForResult(intent,0);
            }
        });

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listadoLibro(DBhelper);
                editBusqueda.setText("");
            }
        });

        listadoLibro.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Libro libro = (Libro) parent.getItemAtPosition(position);

                AlertDialog.Builder dialogo = new AlertDialog.Builder(MenuLibroActivity.this);
                dialogo.setTitle("Eliminar Libro");
                dialogo.setMessage("Va a eliminar un libro");
                dialogo.setCancelable(false);
                dialogo.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        DBhelper.eliminar(libro);
                        listadoLibro(DBhelper);

                        Toast.makeText(MenuLibroActivity.this, "Eliminado " + libro.toString(), Toast.LENGTH_SHORT).show();

                    }
                });

                dialogo.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        cancelar();
                    }
                });

                dialogo.show();
            }
        });



        editBusqueda.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if((event.getAction() == KeyEvent.ACTION_DOWN)&&(keyCode == KeyEvent.KEYCODE_ENTER)){

                    if(editBusqueda.getText().toString().isEmpty()){
                        Toast.makeText(MenuLibroActivity.this, "VACIO",  Toast.LENGTH_LONG).show();
                        return true;
                    }else{

                        busquedaAdapter = new ArrayAdapter<Libro>(MenuLibroActivity.this, android.R.layout.simple_expandable_list_item_1, DBhelper.consultaLibro(Integer.valueOf(editBusqueda.getText().toString())));
                        listadoLibro.setAdapter(busquedaAdapter);

                        if(busquedaAdapter.isEmpty()) {
                            Toast.makeText(MenuLibroActivity.this, "No se ha encontrado registros con ese ISBN", Toast.LENGTH_LONG).show();
                        }


                        return true;
                    }

                }else{

                    return false;
                }
            }
        });


    }

    public void listadoLibro(ControlDB helper){
        libroArrayAdapter= new ArrayAdapter<Libro>(MenuLibroActivity.this, android.R.layout.simple_expandable_list_item_1, helper.getLibros());
        listadoLibro.setAdapter(libroArrayAdapter);
    }

    public void cancelar(){
        Toast.makeText(MenuLibroActivity.this, "Operacion cancelada", Toast.LENGTH_LONG).show();
    }
}