package sv.ues.fia.eisi.pdm115proyecto1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.view.View;
import android.widget.ImageButton;


public class MenuControlFisicoActivity extends AppCompatActivity {

    ListView listadoControl1;
    ControlDB DBhelper;
    ArrayAdapter busquedaAdapter;
    ArrayAdapter controlArrayAdapter;
    EditText editBusqueda;
    ImageButton  regresarInicio, actualizar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_control_fisico);

        listadoControl1 = findViewById(R.id.listado_control);
        editBusqueda = findViewById(R.id.editBuscarControl);
        regresarInicio = findViewById(R.id.imageButtonMenuInicio);
        actualizar = findViewById(R.id.btnActualizarControl);
        DBhelper = new ControlDB(MenuControlFisicoActivity.this);


        regresarInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MenuPrincipalActivity.class);
                startActivityForResult(intent,0);
            }
        });

        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listadoControl(DBhelper);
                editBusqueda.setText("");
            }
        });

        listadoControl(DBhelper);

        editBusqueda.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if((event.getAction() == KeyEvent.ACTION_DOWN)&&(keyCode == KeyEvent.KEYCODE_ENTER)){

                    if(editBusqueda.getText().toString().isEmpty()){
                        Toast.makeText(MenuControlFisicoActivity.this, "VACIO",  Toast.LENGTH_LONG).show();
                        return true;
                    }else{

                        busquedaAdapter = new ArrayAdapter<ControlFisico>(MenuControlFisicoActivity.this, android.R.layout.simple_expandable_list_item_1, DBhelper.consultaControlFisico(editBusqueda.getText().toString()));
                        listadoControl1.setAdapter(busquedaAdapter);

                        if(busquedaAdapter.isEmpty()) {
                            Toast.makeText(MenuControlFisicoActivity.this, "No se ha encontrado registros con ese Carnet", Toast.LENGTH_LONG).show();
                        }


                        return true;
                    }

                }else{

                    return false;
                }
            }
        });

    }

    public void listadoControl(ControlDB helper){
        controlArrayAdapter= new ArrayAdapter<ControlFisico>(MenuControlFisicoActivity.this, android.R.layout.simple_expandable_list_item_1, helper.getControlFisico());
        listadoControl1.setAdapter(controlArrayAdapter);
    }
}