package sv.ues.fia.eisi.pdm115proyecto1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

public class AgregarUsuarioActivity extends AppCompatActivity {

    ImageButton btnAgregar, btnRegresar;
    ControlDB helper;
    EditText editTextNombre, editTextCorreo, editTextContrasena;
    Spinner spinnerRoles;
    ArrayAdapter rolArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_usuario);


        helper = new ControlDB(AgregarUsuarioActivity.this);
        btnAgregar = findViewById(R.id.btnAgregarUsuarioNuevo);
        btnRegresar = findViewById(R.id.btnRegresarUsuarioNuevo);
        editTextCorreo = findViewById(R.id.editTextCorreoNuevo);
        editTextNombre = findViewById(R.id.editTextNombreUsuarioNuevo);
        spinnerRoles = findViewById(R.id.spinnerRolesNuevo);
        editTextContrasena = findViewById(R.id.editTextContrasenaNuevo);



        llenarSpinner(helper);

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MenuUsuarioActivity.class);
                startActivityForResult(intent,0);
            }
        });


    }

    public void llenarSpinner(ControlDB helper){
        rolArrayAdapter= new ArrayAdapter<String>(AgregarUsuarioActivity.this, android.R.layout.simple_expandable_list_item_1, helper.getRoles());
        spinnerRoles.setAdapter(rolArrayAdapter);
    }

    public void insertarUsuario(View view){


       if(editTextCorreo.getText().toString().isEmpty() || editTextNombre.getText().toString().isEmpty() || editTextContrasena.getText().toString().isEmpty()){
            Toast.makeText(AgregarUsuarioActivity.this, R.string.todos, Toast.LENGTH_LONG).show();
       }else {

            String rol = spinnerRoles.getSelectedItem().toString();
            String nombre = editTextNombre.getText().toString();
            String correo = editTextCorreo.getText().toString();
            String contra = editTextContrasena.getText().toString();
            String regInsertados;

            Usuario usuario = new Usuario();

            usuario.setNombre(nombre);
            usuario.setCorreo(correo);
            usuario.setContrasena(contra);
            usuario.setRol(rol);


            helper.abrir();
            regInsertados = helper.insertar(usuario);
            helper.cerrar();
            Toast.makeText(this, regInsertados, Toast.LENGTH_LONG).show();
        }


    }




}

