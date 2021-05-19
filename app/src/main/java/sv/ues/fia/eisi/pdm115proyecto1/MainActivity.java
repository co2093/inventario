package sv.ues.fia.eisi.pdm115proyecto1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.UnicodeSetSpanner;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText editTextUsuario;
    EditText editTextContrasena;
    Button btnIngresar;
    ControlDB helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextUsuario = findViewById(R.id.editTextUsuario);
        editTextContrasena = findViewById(R.id.editTextContrasena);
        btnIngresar = findViewById(R.id.btnLogin);
        helper = new ControlDB(MainActivity.this);


        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(helper.inicio(editTextUsuario.getText().toString(), editTextContrasena.getText().toString())){

                    Intent intent = new Intent(v.getContext(), MenuPrincipalActivity.class);
                    startActivityForResult(intent,0);
                }else{
                    Toast.makeText(MainActivity.this, "Usuario no existe", Toast.LENGTH_LONG).show();
                }
            }
        });

        //Usuario de prueba
        helper.llenarUsuario();




    }




}