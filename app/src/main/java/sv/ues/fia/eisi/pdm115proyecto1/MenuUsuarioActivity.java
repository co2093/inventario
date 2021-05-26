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
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import android.widget.ImageButton;
import android.app.AlertDialog;

import java.util.List;

public class MenuUsuarioActivity extends AppCompatActivity {

    ImageButton btnAgregarUsuario, btnEditarUsuario, btnActualizar;
    ImageButton regresarInicio;
    ListView listadoUsuario;
    ControlDB DBhelper;
    ArrayAdapter busquedaAdapter;
    ArrayAdapter usuarioArrayAdapter;
    EditText editBusqueda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_usuario);

        btnAgregarUsuario = findViewById(R.id.btnAgregarUsuario);
        btnEditarUsuario = findViewById(R.id.btnEditarUsuario);
        btnActualizar = findViewById(R.id.btnActualizarUsuario);
        listadoUsuario = findViewById(R.id.listado_usuario);
        editBusqueda = findViewById(R.id.editBuscar);
        regresarInicio = findViewById(R.id.imageButtonMenuInicio);

        DBhelper = new ControlDB(MenuUsuarioActivity.this);
        List<Usuario> usuariosListado = DBhelper.getUsuarios();

        listadoUsuario(DBhelper);

        regresarInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MenuPrincipalActivity.class);
                startActivityForResult(intent,0);
            }
        });

        btnAgregarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AgregarUsuarioActivity.class);
                startActivityForResult(intent,0);
            }
        });

        btnEditarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), EditarUsuarioActivity.class);
                startActivityForResult(intent,0);
            }
        });

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listadoUsuario(DBhelper);
                editBusqueda.setText("");
            }
        });



        listadoUsuario.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Usuario usuario = (Usuario) parent.getItemAtPosition(position);

                AlertDialog.Builder dialogo = new AlertDialog.Builder(MenuUsuarioActivity.this);
                dialogo.setTitle("Eliminar Usuario");
                dialogo.setMessage("Va a eliminar un usuario");
                dialogo.setCancelable(false);
                dialogo.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DBhelper.eliminarU(usuario.getCorreo());
                        listadoUsuario(DBhelper);

                        Toast.makeText(MenuUsuarioActivity.this, "Eliminado " + usuario.toString(), Toast.LENGTH_SHORT).show();

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
                        Toast.makeText(MenuUsuarioActivity.this, "VACIO",  Toast.LENGTH_LONG).show();
                        return true;
                    }else{

                        busquedaAdapter = new ArrayAdapter<Usuario>(MenuUsuarioActivity.this, android.R.layout.simple_expandable_list_item_1, DBhelper.consultaUsuario(editBusqueda.getText().toString()));
                        listadoUsuario.setAdapter(busquedaAdapter);

                        if(busquedaAdapter.isEmpty()) {
                            Toast.makeText(MenuUsuarioActivity.this, "No se ha encontrado registros con ese Correo", Toast.LENGTH_LONG).show();
                        }


                        return true;
                    }

                }else{

                    return false;
                }
            }
        });





    }

    public void listadoUsuario(ControlDB helper){
        usuarioArrayAdapter= new ArrayAdapter<Usuario>(MenuUsuarioActivity.this, android.R.layout.simple_expandable_list_item_1, helper.getUsuarios());
        listadoUsuario.setAdapter(usuarioArrayAdapter);
    }

    public void cancelar(){
        Toast.makeText(MenuUsuarioActivity.this, "Operacion cancelada", Toast.LENGTH_LONG).show();
    }


}