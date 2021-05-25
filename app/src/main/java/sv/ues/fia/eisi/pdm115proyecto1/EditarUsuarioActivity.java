package sv.ues.fia.eisi.pdm115proyecto1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.UnicodeSetSpanner;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.InputFilter;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

public class EditarUsuarioActivity extends AppCompatActivity {

    ImageButton btnAgregar, btnRegresar, inicio;
    ControlDB helper;
    EditText editTextNombre, editTextCorreo, editTextContrasena;
    Spinner spinnerRoles;
    ArrayAdapter rolArrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_usuario);

        helper = new ControlDB(EditarUsuarioActivity.this);
        btnAgregar = findViewById(R.id.btnAgregarUsuarioEdit);
        btnRegresar = findViewById(R.id.btnRegresarUsuarioEdit);
        editTextCorreo = findViewById(R.id.editTextCorreoEdit);
        editTextNombre = findViewById(R.id.editTextNombreUsuarioEdit);
        spinnerRoles = findViewById(R.id.spinnerRolesEdit);
        editTextContrasena = findViewById(R.id.editTextContrasenaEdit);


        llenarSpinner(helper);



        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MenuUsuarioActivity.class);
                startActivityForResult(intent,0);

            }
        });



    }

    public void actualizarUsuario(View V){

       if(editTextCorreo.getText().toString().isEmpty() || editTextNombre.getText().toString().isEmpty() || editTextContrasena.getText().toString().isEmpty()){
           Toast.makeText(EditarUsuarioActivity.this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
       }else {

            String rol = spinnerRoles.getSelectedItem().toString();
            String nombre = editTextNombre.getText().toString();
            String correo = editTextCorreo.getText().toString();
            String contra = editTextContrasena.getText().toString();


            Usuario usuario = new Usuario();

            usuario.setNombre(nombre);
            usuario.setCorreo(correo);
            usuario.setContrasena(contra);
            usuario.setRol(rol);

            helper.abrir();
            String estado = helper.actualizar(usuario);
            helper.cerrar();

            Toast.makeText(this, estado, Toast.LENGTH_LONG).show();


       }
    }

    public void llenarSpinner(ControlDB helper){
        rolArrayAdapter= new ArrayAdapter<String>(EditarUsuarioActivity.this, android.R.layout.simple_expandable_list_item_1, helper.getRoles());
        spinnerRoles.setAdapter(rolArrayAdapter);
    }

}