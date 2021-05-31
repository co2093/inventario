package sv.ues.fia.eisi.pdm115proyecto1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CategoriaActivity extends AppCompatActivity {

    ImageButton btn_agregar, btn_Actualizar, btnEditar, btn_Borrar, btn_inicio;
    EditText editbuscar;
    ListView listadoCategorias;
    ArrayAdapter busquedaAdapter;
    ArrayAdapter categoriaArrayAdapter;
    ControlDB DBHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);

        btnEditar = findViewById(R.id.btnRegresar);
        editbuscar = findViewById(R.id.editBuscar);
        listadoCategorias = findViewById(R.id.lista_consulta);
        btn_Actualizar = findViewById(R.id.btnEditar);
        btn_Borrar = findViewById(R.id.btnBorrar);
        btn_agregar = findViewById(R.id.btnAgregar);
        btn_inicio = findViewById(R.id.imageButtonMenuInicio);

        InputFilter[] editFilters = editbuscar.getFilters();
        InputFilter[] newFilters = new InputFilter[editFilters.length + 1];
        System.arraycopy(editFilters, 0, newFilters, 0, editFilters.length);
        newFilters[editFilters.length] = new InputFilter.AllCaps();
        editbuscar.setFilters(newFilters);

        btn_inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MenuPrincipalActivity.class);
                startActivityForResult(intent,0);
            }
        });

        btn_agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CategoriaActivity.this, AgregarCategoriaActivity.class);
                startActivity(i);
            }
        });

        btn_Actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EditarCategoriaActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listadoCategoria(DBHelper);
                editbuscar.setText("");
            }
        });

        DBHelper = new ControlDB(CategoriaActivity.this);
        List<Categoria> categoriaListados = DBHelper.getCategorias();

        listadoCategoria(DBHelper);

        listadoCategorias.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Categoria categoria = (Categoria) parent.getItemAtPosition(position);

                AlertDialog.Builder dialogo = new AlertDialog.Builder(CategoriaActivity.this);
                dialogo.setTitle("Eliminar Categoria");
                dialogo.setMessage("Va a eliminar una categoria");
                dialogo.setCancelable(false);
                dialogo.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DBHelper.eliminar(categoria);
                        listadoCategoria(DBHelper);

                        Toast.makeText(CategoriaActivity.this, "Operacion completada " + categoria.getNombre_categoria(), Toast.LENGTH_SHORT).show();

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


        editbuscar.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    if (editbuscar.getText().toString().isEmpty()) {
                        Toast.makeText(CategoriaActivity.this, "VACIO", Toast.LENGTH_LONG).show();
                        return true;
                    } else {

                        busquedaAdapter = new ArrayAdapter<Categoria>(CategoriaActivity.this, android.R.layout.simple_expandable_list_item_1, DBHelper.consultaCategoria(editbuscar.getText().toString()));
                        listadoCategorias.setAdapter(busquedaAdapter);

                        if (busquedaAdapter.isEmpty()) {
                            Toast.makeText(CategoriaActivity.this, "No se ha encontrado registros con ese Carnet", Toast.LENGTH_LONG).show();
                        }
                        return true;
                    }
                } else {
                    return false;
                }
            }
        });
    }
    public void cancelar(){
        Toast.makeText(CategoriaActivity.this, "Operacion cancelada", Toast.LENGTH_LONG).show();
    }

    public void listadoCategoria(ControlDB helper){
        categoriaArrayAdapter = new ArrayAdapter<Categoria>(CategoriaActivity.this, android.R.layout.simple_expandable_list_item_1,helper.getCategorias());
        listadoCategorias.setAdapter(categoriaArrayAdapter);
    }
}