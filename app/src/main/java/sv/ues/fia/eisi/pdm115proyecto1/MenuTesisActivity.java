package sv.ues.fia.eisi.pdm115proyecto1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MenuTesisActivity extends AppCompatActivity {

    ImageButton btnAgregarTesis, btnEditarTesis, btnActualizar, inicio;
    ListView listadoTesis;
    ControlDB DBhelper;
    ArrayAdapter busquedaAdapter;
    ArrayAdapter tesisArrayAdapter;
    EditText editBusqueda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_tesis);

        btnAgregarTesis = findViewById(R.id.btnAgregarTesis);
        btnEditarTesis = findViewById(R.id.btnEditarTesis);
        btnActualizar = findViewById(R.id.btnActualizar);
        listadoTesis = findViewById(R.id.listado_tesis);
        editBusqueda = findViewById(R.id.editBuscarTesis);
        inicio = findViewById(R.id.imageButtonMenuInicio);

        DBhelper = new ControlDB(MenuTesisActivity.this);
        List<Tesis> tesisListado = DBhelper.getTesis();

        listadoTesis(DBhelper);

        inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MenuPrincipalActivity.class);
                startActivityForResult(intent,0);
            }
        });

        btnAgregarTesis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AgregarTesisActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        btnEditarTesis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EditarTesisActivity.class);
                startActivityForResult(intent,0);
            }
        });

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listadoTesis(DBhelper);
                editBusqueda.setText("");
            }
        });

        listadoTesis.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Tesis tesis = (Tesis) parent.getItemAtPosition(position);

                AlertDialog.Builder dialogo = new AlertDialog.Builder(MenuTesisActivity.this);
                dialogo.setTitle(R.string.eliminar);
                dialogo.setMessage(R.string.eliminar);
                dialogo.setCancelable(false);
                dialogo.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        DBhelper.eliminar(tesis);
                        listadoTesis(DBhelper);

                        Toast.makeText(MenuTesisActivity.this, R.string.completado + tesis.toString(), Toast.LENGTH_SHORT).show();

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
                        Toast.makeText(MenuTesisActivity.this, R.string.vacio,  Toast.LENGTH_LONG).show();
                        return true;
                    }else{

                        busquedaAdapter = new ArrayAdapter<Tesis>(MenuTesisActivity.this, android.R.layout.simple_expandable_list_item_1, DBhelper.consultaTesis(editBusqueda.getText().toString()));
                        listadoTesis.setAdapter(busquedaAdapter);

                        if(busquedaAdapter.isEmpty()) {
                            Toast.makeText(MenuTesisActivity.this, R.string.vacio, Toast.LENGTH_LONG).show();
                        }


                        return true;
                    }

                }else{

                    return false;
                }
            }
        });

    }

    public void listadoTesis(ControlDB helper){
        tesisArrayAdapter= new ArrayAdapter<Tesis>(MenuTesisActivity.this, android.R.layout.simple_expandable_list_item_1, helper.getTesis());
        listadoTesis.setAdapter(tesisArrayAdapter);
    }

        public void cancelar(){
            Toast.makeText(MenuTesisActivity.this, R.string.completado, Toast.LENGTH_LONG).show();
        }
    }

