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

public class MenuActividadActivity extends AppCompatActivity {

    ImageButton btnAgregar, btnEditar, btnActualizar, regresarInicio;
    ListView listadoActividad;
    ControlDB DBhelper;
    ArrayAdapter busquedaAdapter;
    ArrayAdapter actividadArrayAdapter;
    EditText editBusqueda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_actividad);

        btnAgregar = findViewById(R.id.btnAgregarActividad);
        btnEditar = findViewById(R.id.btnEditarActividad);
        btnActualizar = findViewById(R.id.btnActualizarActividad);
        listadoActividad = findViewById(R.id.listado_actividad);
        editBusqueda = findViewById(R.id.editBuscarActividad);
        regresarInicio = findViewById(R.id.imageButtonMenuInicio);

        DBhelper = new ControlDB(MenuActividadActivity.this);

        listadoActividad(DBhelper);


        regresarInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MenuPrincipalActivity.class);
                startActivityForResult(intent,0);
            }
        });

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AgregarActividadActivity.class);
                startActivityForResult(intent,0);
            }
        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EditarActividadActivity.class);
                startActivityForResult(intent,0);
            }
        });

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listadoActividad(DBhelper);
                editBusqueda.setText("");
            }
        });

        listadoActividad.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Actividad actividad = (Actividad) parent.getItemAtPosition(position);

                AlertDialog.Builder dialogo = new AlertDialog.Builder(MenuActividadActivity.this);
                dialogo.setTitle(R.string.eliminar);
                dialogo.setMessage(R.string.eliminar);
                dialogo.setCancelable(false);
                dialogo.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DBhelper.eliminar(actividad);
                        listadoActividad(DBhelper);

                        Toast.makeText(MenuActividadActivity.this, R.string.completado + actividad.toString(), Toast.LENGTH_SHORT).show();

                    }
                });

                dialogo.setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
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
                        Toast.makeText(MenuActividadActivity.this, R.string.vacio,  Toast.LENGTH_LONG).show();
                        return true;
                    }else{

                        busquedaAdapter = new ArrayAdapter<Actividad>(MenuActividadActivity.this, android.R.layout.simple_expandable_list_item_1, DBhelper.consultaActividad(Integer.valueOf(editBusqueda.getText().toString())));
                        listadoActividad.setAdapter(busquedaAdapter);

                        if(busquedaAdapter.isEmpty()) {
                            Toast.makeText(MenuActividadActivity.this, R.string.noexiste, Toast.LENGTH_LONG).show();
                        }


                        return true;
                    }

                }else{

                    return false;
                }
            }
        });



    }

    public void listadoActividad(ControlDB helper){
        actividadArrayAdapter= new ArrayAdapter<Actividad>(MenuActividadActivity.this, android.R.layout.simple_expandable_list_item_1, helper.getActividades());
        listadoActividad.setAdapter(actividadArrayAdapter);
    }

    public void cancelar(){
        Toast.makeText(MenuActividadActivity.this, "Operacion cancelada", Toast.LENGTH_LONG).show();
    }
}