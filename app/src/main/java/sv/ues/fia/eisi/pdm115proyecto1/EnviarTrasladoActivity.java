package sv.ues.fia.eisi.pdm115proyecto1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EnviarTrasladoActivity extends AppCompatActivity {
    ImageButton btnAgregar;
    ImageButton btnRegresar;
    ControlDB helper;
    EditText nombre;
    EditText descripcion;
    EditText fecha;
    EditText estadoE;
    EditText equipo;

    @Override
    protected void onCreate(Bundle savedInstanceStace){
        super.onCreate(savedInstanceStace);
        setContentView(R.layout.enviar_sustitucion);

        estadoE = findViewById(R.id.editTextEstado);
        nombre = findViewById(R.id.editTextNombreRazon);
        descripcion = findViewById(R.id.editTextDescripcion);
        fecha = findViewById(R.id.editTextFecha);
        equipo = findViewById(R.id.editTextEquipo);



        helper = new ControlDB(this);
        btnAgregar = findViewById(R.id.btnAgregar);
        btnRegresar = findViewById(R.id.btnRegresar);





        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MenuRazonActivity.class);
                startActivityForResult(intent,0);
            }
        });



    }

    public void insertarSolicitud(View view){
        if(nombre.getText().toString().isEmpty() || descripcion.getText().toString().isEmpty() || fecha.getText().toString().isEmpty() || estadoE.getText().toString().isEmpty() || equipo.getText().toString().isEmpty()){
            Toast.makeText(EnviarTrasladoActivity.this, R.string.todos, Toast.LENGTH_SHORT).show();
        }else{
            String NombreRT = nombre.getText().toString();
            String DescripcionRT = descripcion.getText().toString();
            String fech = fecha.getText().toString();
            String equipo1 = equipo.getText().toString();
            String estado = estadoE.getText().toString();
            String regInsertados;

            Razon_Traslado rztado = new Razon_Traslado();
            rztado.setNombre_razon_traslado(NombreRT);
            rztado.setDescripcion_razon_traslado(DescripcionRT);
            rztado.setFecha_traslado(fech);
            rztado.setEquipo_informatico(equipo1);
            rztado.setEstado(estado);
            helper.abrir();
            regInsertados = helper.insertar(rztado);
            helper.cerrar();
            Toast.makeText(this, regInsertados, Toast.LENGTH_SHORT).show();
        }
    }



}
