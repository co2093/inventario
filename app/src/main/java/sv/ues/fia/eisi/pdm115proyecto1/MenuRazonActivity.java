package sv.ues.fia.eisi.pdm115proyecto1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.KeyEvent;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

import java.util.List;

public class MenuRazonActivity extends AppCompatActivity {


    EditText editNombreRazonTraslado;
    GridView gridView;
    ImageButton btnAgregar, btnEditar, btnRegresar, regresarInicio;
    ListView listadoRazon;
    ControlDB DBhelper;
    ArrayAdapter razonArrayAdapter;
    ArrayAdapter busquedaAdapter;
    EditText editBusqueda;


    String[] values={
            "Facebook", "Youtube", "Twitter"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_razon);

        btnAgregar = findViewById(R.id.btnAgregar);
        btnEditar = findViewById(R.id.btnEditar);
        btnRegresar = findViewById(R.id.btnRegresar);
        listadoRazon = findViewById(R.id.listado_razon);
        editBusqueda = findViewById(R.id.editBuscar);
        regresarInicio = findViewById(R.id.imageButtonMenuInicio);

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EditarRazonActivity.class);
                startActivityForResult(intent,0);
            }
        });

        regresarInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MenuPrincipalActivity.class);
                startActivityForResult(intent,0);
            }
        });

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listaRazones(DBhelper);
            }
        });

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EnviarTrasladoActivity.class);
                startActivityForResult(intent,0);
            }
        });

        DBhelper = new ControlDB(MenuRazonActivity.this);
        List<Razon_Traslado> everyone = DBhelper.getEveryoneRazon();

        listaRazones(DBhelper);

        listadoRazon.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Razon_Traslado razon = (Razon_Traslado) parent.getItemAtPosition(position);

                AlertDialog.Builder dialogo = new AlertDialog.Builder(MenuRazonActivity.this);
                dialogo.setTitle("Eliminar Razon de Traslado");
                dialogo.setMessage("¿Desea eliminar la Razón de Traslado del Equipo?");
                dialogo.setCancelable(false);
                dialogo.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DBhelper.eliminar(razon);
                        listaRazones(DBhelper);

                        Toast.makeText(MenuRazonActivity.this, "Razón de Traslado, Eliminada " + razon.toString(), Toast.LENGTH_SHORT).show();

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

        TextView textView = (TextView) findViewById(R.id.textHome);
        SpannableString mitextoU = new SpannableString("Home");
        mitextoU.setSpan(new UnderlineSpan(), 0, mitextoU.length(), 0);
        textView.setText(mitextoU);

        editBusqueda.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if((event.getAction() == KeyEvent.ACTION_DOWN)&&(keyCode == KeyEvent.KEYCODE_ENTER)){

                    if(editBusqueda.getText().toString().isEmpty()){
                        Toast.makeText(MenuRazonActivity.this, "VACIO",  Toast.LENGTH_LONG).show();
                        return true;
                    }else{
                        Toast.makeText(MenuRazonActivity.this, editBusqueda.getText().toString(),  Toast.LENGTH_LONG).show();


                        busquedaAdapter = new ArrayAdapter<Razon_Traslado>(MenuRazonActivity.this, android.R.layout.simple_expandable_list_item_1, DBhelper.consultaRazon(Integer.valueOf(editBusqueda.getText().toString())));
                        listadoRazon.setAdapter(busquedaAdapter);

                        return true;
                    }

                }else{

                    return false;
                }
            }
        });


    }

    private void listaRazones(ControlDB helper) {
        razonArrayAdapter = new ArrayAdapter<Razon_Traslado>(MenuRazonActivity.this, android.R.layout.simple_expandable_list_item_1, helper.getEveryoneRazon());
        listadoRazon.setAdapter(razonArrayAdapter);
    }

    public void cancelar(){
        Toast.makeText(MenuRazonActivity.this, "Operacion cancelada", Toast.LENGTH_LONG).show();
    }


}