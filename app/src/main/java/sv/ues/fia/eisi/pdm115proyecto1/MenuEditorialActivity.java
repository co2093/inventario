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

public class MenuEditorialActivity extends AppCompatActivity {

    ImageButton btnAgregarEditorial, btnEditarEditorial, btnActualizar, regresarInicio;
    ListView listadoEditorial;
    ControlDB DBhelper;
    ArrayAdapter busquedaAdapter;
    ArrayAdapter editorialArrayAdapter;
    EditText editBusqueda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_editorial);

        btnAgregarEditorial = findViewById(R.id.btnAgregarEditorial);
        btnEditarEditorial = findViewById(R.id.btnEditarEditorial);
        btnActualizar = findViewById(R.id.btnActualizarEditorial);
        listadoEditorial = findViewById(R.id.listado_editorial);
        editBusqueda = findViewById(R.id.editBuscarEditorial);
        regresarInicio = findViewById(R.id.imageButtonMenuInicio);

        DBhelper = new ControlDB(MenuEditorialActivity.this);
        List<Editorial> editorialListado = DBhelper.getEditorial();

        listadoEditorial(DBhelper);

        regresarInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MenuPrincipalActivity.class);
                startActivityForResult(intent,0);
            }
        });

        btnAgregarEditorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AgregarEditorialActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        btnEditarEditorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EditarEditorialActivity.class);
                startActivityForResult(intent,0);
            }
        });

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listadoEditorial(DBhelper);
                editBusqueda.setText("");
            }
        });

        listadoEditorial.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Editorial editorial = (Editorial) parent.getItemAtPosition(position);

                AlertDialog.Builder dialogo = new AlertDialog.Builder(MenuEditorialActivity.this);
                dialogo.setTitle(R.string.eliminar);
                dialogo.setMessage(R.string.eliminar);
                dialogo.setCancelable(false);
                dialogo.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        DBhelper.eliminar(editorial);
                        listadoEditorial(DBhelper);

                        Toast.makeText(MenuEditorialActivity.this, R.string.completado + editorial.toString(), Toast.LENGTH_SHORT).show();

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
                        Toast.makeText(MenuEditorialActivity.this, R.string.vacio,  Toast.LENGTH_LONG).show();
                        return true;
                    }else{

                        busquedaAdapter = new ArrayAdapter<Editorial>(MenuEditorialActivity.this, android.R.layout.simple_expandable_list_item_1, DBhelper.consultaEditorial(Integer.valueOf(editBusqueda.getText().toString())));
                        listadoEditorial.setAdapter(busquedaAdapter);

                        if(busquedaAdapter.isEmpty()) {
                            Toast.makeText(MenuEditorialActivity.this, R.string.noexiste, Toast.LENGTH_LONG).show();
                        }


                        return true;
                    }

                }else{

                    return false;
                }
            }
        });





    }



    public void listadoEditorial(ControlDB helper){
        editorialArrayAdapter= new ArrayAdapter<Editorial>(MenuEditorialActivity.this, android.R.layout.simple_expandable_list_item_1, helper.getEditorial());
        listadoEditorial.setAdapter(editorialArrayAdapter);
    }

    public void cancelar(){
        Toast.makeText(MenuEditorialActivity.this, R.string.completado, Toast.LENGTH_LONG).show();
    }
}