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

public class MenuEquipoInformaticoActivity extends AppCompatActivity {

    ImageButton btnAgregarEquipo, btnEditarEquipo, btnActualizar, regresarInicio;
    ListView listadoEquipo;
    ControlDB DBhelper;
    ArrayAdapter busquedaAdapter;
    ArrayAdapter equipoArrayAdapter;
    EditText editbuscar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_equipo_informatico);

        btnAgregarEquipo = findViewById(R.id.btnAgregarEquipo);
        btnEditarEquipo = findViewById(R.id.btnEditarEquipo);
        btnActualizar = findViewById(R.id.btnActualizarEquipo);
        listadoEquipo = findViewById(R.id.listado_equipo);
        editbuscar = findViewById(R.id.editBuscarEquipoMenu);
        regresarInicio = findViewById(R.id.imageButtonMenuInicio);

        DBhelper = new ControlDB(MenuEquipoInformaticoActivity.this);
        List<EquipoInformatico> equiposListado = DBhelper.getEquipos();
        listadoEquipo(DBhelper);

        btnAgregarEquipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AgregarEquipoInformaticoActivity.class);
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

        btnEditarEquipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EditarEquipoInformaticoActivity.class);
                startActivityForResult(intent,0);
            }
        });

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listadoEquipo(DBhelper);
                editbuscar.setText("");
            }
        });

        editbuscar.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    if (editbuscar.getText().toString().isEmpty()) {
                        Toast.makeText(MenuEquipoInformaticoActivity.this, "VACIO", Toast.LENGTH_LONG).show();
                        return true;
                    } else {

                        busquedaAdapter = new ArrayAdapter<EquipoInformatico>(MenuEquipoInformaticoActivity.this, android.R.layout.simple_expandable_list_item_1, DBhelper.consultaEquipo(editbuscar.getText().toString()));
                        listadoEquipo.setAdapter(busquedaAdapter);

                        if (busquedaAdapter.isEmpty()) {
                            Toast.makeText(MenuEquipoInformaticoActivity.this, "No se ha encontrado registros con ese nombre", Toast.LENGTH_LONG).show();
                        }
                        return true;
                    }
                } else {
                    return false;
                }
            }
        });


        listadoEquipo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EquipoInformatico equipoInformatico = (EquipoInformatico) parent.getItemAtPosition(position);

                AlertDialog.Builder dialogo = new AlertDialog.Builder(MenuEquipoInformaticoActivity.this);
                dialogo.setTitle("Eliminar Equipo");
                dialogo.setMessage("Va a eliminar un equipo");
                dialogo.setCancelable(false);
                dialogo.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        DBhelper.eliminar(equipoInformatico);
                        listadoEquipo(DBhelper);

                        Toast.makeText(MenuEquipoInformaticoActivity.this, "Eliminado " + equipoInformatico.toString(), Toast.LENGTH_SHORT).show();

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








    }

    public void listadoEquipo(ControlDB helper){
        equipoArrayAdapter= new ArrayAdapter<EquipoInformatico>(MenuEquipoInformaticoActivity.this, android.R.layout.simple_expandable_list_item_1, helper.getEquipos());
        listadoEquipo.setAdapter(equipoArrayAdapter);
    }

    public void cancelar(){
        Toast.makeText(MenuEquipoInformaticoActivity.this, "Operacion cancelada", Toast.LENGTH_LONG).show();
    }
}