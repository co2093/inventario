package sv.ues.fia.eisi.pdm115proyecto1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class AgregarCategoriaActivity extends AppCompatActivity {

    ControlDB helper;
    EditText editCategoria;
    ImageButton btn_guardar;
    ImageButton btn_inicio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_categoria);

        helper = new ControlDB(this);
        btn_inicio = findViewById(R.id.btnRegresar);
        btn_inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AgregarCategoriaActivity.this,CategoriaActivity.class);
                startActivity(i);
            }
        });

        helper = new ControlDB(this);
        editCategoria = (EditText) findViewById(R.id.editCategoria);
        btn_guardar = (ImageButton) findViewById(R.id.btnAgregar);
    }
    public void insertarCategoria(View v){
        if(editCategoria.getText() == null){
            Toast.makeText(AgregarCategoriaActivity.this,
                    R.string.todos,Toast.LENGTH_LONG).show();
        }else{
        String nom_categoria=editCategoria.getText().toString();
        String regInsertados;
        Categoria categoria = new Categoria();
        categoria.setNombre_categoria(nom_categoria);
        helper.abrir();
        regInsertados = helper.insertar(categoria);
        helper.cerrar();
        Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
    }}
}