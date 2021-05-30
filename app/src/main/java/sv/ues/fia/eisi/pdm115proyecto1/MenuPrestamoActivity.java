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

public class MenuPrestamoActivity extends AppCompatActivity {

    ImageButton btnAgregar, btnEditar, btnActualizar, regresarInicio;
    ListView listadoPrestamo;
    ControlDB DBhelper;
    ArrayAdapter busquedaAdapter;
    ArrayAdapter prestamoArrayAdapter;
    EditText editBusqueda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_prestamo);

        btnAgregar = findViewById(R.id.btnAgregarPrestamo);
        btnEditar = findViewById(R.id.btnEditarPrestamo);
        btnActualizar = findViewById(R.id.btnActualizarPrestamo);
        listadoPrestamo = findViewById(R.id.listado_prestamo);
        editBusqueda = findViewById(R.id.editBuscarPrestamo);
        regresarInicio = findViewById(R.id.imageButtonMenuInicio);

        DBhelper = new ControlDB(MenuPrestamoActivity.this);
        listadoPrestamo(DBhelper);

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AgregarPrestamoActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        regresarInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MenuPrincipalActivity.class);
                startActivityForResult(intent,0);
            }
        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EditarPrestamoActivity.class);
                startActivityForResult(intent,0);
            }
        });

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listadoPrestamo(DBhelper);
                editBusqueda.setText("");
            }
        });

        listadoPrestamo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Prestamo prestamo = (Prestamo) parent.getItemAtPosition(position);

                AlertDialog.Builder dialogo = new AlertDialog.Builder(MenuPrestamoActivity.this);
                dialogo.setTitle("Eliminar Prestamo");
                dialogo.setMessage("Va a eliminar un prestamo");
                dialogo.setCancelable(false);
                dialogo.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        DBhelper.eliminar(prestamo);
                        listadoPrestamo(DBhelper);

                        Toast.makeText(MenuPrestamoActivity.this, "Eliminado " + prestamo.toString(), Toast.LENGTH_SHORT).show();

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
                        Toast.makeText(MenuPrestamoActivity.this, "VACIO",  Toast.LENGTH_LONG).show();
                        return true;
                    }else{

                        busquedaAdapter = new ArrayAdapter<Prestamo>(MenuPrestamoActivity.this, android.R.layout.simple_expandable_list_item_1, DBhelper.consultaPrestamos(Integer.valueOf(editBusqueda.getText().toString())));
                        listadoPrestamo.setAdapter(busquedaAdapter);

                        if(busquedaAdapter.isEmpty()) {
                            Toast.makeText(MenuPrestamoActivity.this, "No se ha encontrado registros con ese ID", Toast.LENGTH_LONG).show();
                        }


                        return true;
                    }

                }else{

                    return false;
                }
            }
        });






    }

    public void listadoPrestamo(ControlDB helper){
        prestamoArrayAdapter= new ArrayAdapter<Prestamo>(MenuPrestamoActivity.this, android.R.layout.simple_expandable_list_item_1, helper.getPrestamo());
        listadoPrestamo.setAdapter(prestamoArrayAdapter);
    }

    public void cancelar(){
        Toast.makeText(MenuPrestamoActivity.this, "Operacion cancelada", Toast.LENGTH_LONG).show();
    }

}