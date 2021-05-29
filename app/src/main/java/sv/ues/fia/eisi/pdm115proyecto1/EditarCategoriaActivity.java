package sv.ues.fia.eisi.pdm115proyecto1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.UnicodeSetSpanner;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class EditarCategoriaActivity extends AppCompatActivity {

    ImageButton btnAgregar;
    ControlDB helper;
    EditText editNombre;
    EditText editID;
    ImageButton btnRegresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_categoria);

        btnRegresar = findViewById(R.id.btnRegresarCatEdit);

        helper = new ControlDB(this);
        btnAgregar = findViewById(R.id.btnAgregarCatEdit);
        editNombre =findViewById(R.id.editCategoriaEdit);
        editID = findViewById(R.id.editTextCategoriaIDEdit);

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CategoriaActivity.class);
                startActivityForResult(intent,0);
            }
        });
    }

    public void actualizarCategoria(View V) {

        if (editID.getText().toString().isEmpty() || editNombre.getText().toString().isEmpty()) {
            Toast.makeText(EditarCategoriaActivity.this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
        } else {
            Categoria categoria = new Categoria();
            categoria.setId_categoria(Integer.valueOf(editID.getText().toString()));
            categoria.setNombre_categoria(editNombre.getText().toString());
            helper.abrir();
            String estado = helper.actualizar(categoria);
            helper.cerrar();

            Toast.makeText(this, estado, Toast.LENGTH_LONG).show();


        }

    }
}