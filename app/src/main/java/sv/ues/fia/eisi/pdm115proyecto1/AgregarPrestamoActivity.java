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

import java.util.List;

public class AgregarPrestamoActivity extends AppCompatActivity {

    ImageButton btnAgregar, btnRegresar;
    ControlDB helper;
    EditText editTextId, editTextPrestamo, editTextDevolucion;
    Spinner spinnerDocentes, spinnerEquipo, spinnerActividad;
    ArrayAdapter docenteArrayAdapter, equipoArrayAdapter, actividadArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_prestamo);


        helper = new ControlDB(AgregarPrestamoActivity.this);
        btnAgregar = findViewById(R.id.btnAgregarPrestamoN);
        btnRegresar = findViewById(R.id.btnRegresarPrestamoN);
        editTextId = findViewById(R.id.editTextPrestamoIDN);
        editTextPrestamo = findViewById(R.id.editTextPrestamoN);
        editTextDevolucion = findViewById(R.id.editTextDevolucionN);
        spinnerDocentes = findViewById(R.id.spinnerDocentesPrestamoN);
        spinnerEquipo = findViewById(R.id.spinnerEquipoPrestamoN);
        spinnerActividad = findViewById(R.id.spinnerActividadPrestamoN);



        llenarSpinnerD(helper);
        llenarSpinnerE(helper);
        llenarSpinnerA(helper);


        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MenuPrestamoActivity.class);
                startActivityForResult(intent,0);
            }
        });
    }

    public void insertarPrestamo(View view){


        if(editTextId.getText().toString().isEmpty() || editTextPrestamo.getText().toString().isEmpty() || editTextDevolucion.getText().toString().isEmpty() || spinnerDocentes.getSelectedItem()==null || spinnerEquipo.getSelectedItem()==null || spinnerActividad.getSelectedItem()==null){
            Toast.makeText(AgregarPrestamoActivity.this, R.string.todos, Toast.LENGTH_LONG).show();
        }else {

            Integer id = Integer.valueOf(editTextId.getText().toString());
            String prestamo = editTextPrestamo.getText().toString();
            Integer actividad = Integer.valueOf(spinnerActividad.getSelectedItem().toString());
            Integer equipo = Integer.valueOf(spinnerEquipo.getSelectedItem().toString());
            String devolucion = editTextDevolucion.getText().toString();
            String responsable = spinnerDocentes.getSelectedItem().toString();
            String categoria = "";
            String regInsertados;

           categoria = helper.getCategoriaEquipo(equipo);


            Prestamo prestamo1 = new Prestamo();
            prestamo1.setIdPrestamo(id);
            prestamo1.setFechaPrestamo(prestamo);
            prestamo1.setFechaDevolucion(devolucion);
            prestamo1.setResponsable(responsable);
            prestamo1.setEquipo(equipo);
            prestamo1.setActividad(actividad);
            prestamo1.setCategoriaPrestamo(categoria);

            helper.abrir();
            regInsertados = helper.insertar(prestamo1);
            helper.cerrar();
            Toast.makeText(this, regInsertados, Toast.LENGTH_LONG).show();
        }


    }

    public void llenarSpinnerD(ControlDB helper){
        docenteArrayAdapter= new ArrayAdapter<String>(AgregarPrestamoActivity.this, android.R.layout.simple_expandable_list_item_1, helper.getDocentesNombre());
        spinnerDocentes.setAdapter(docenteArrayAdapter);
    }

    public void llenarSpinnerA(ControlDB helper){
        actividadArrayAdapter= new ArrayAdapter<Integer>(AgregarPrestamoActivity.this, android.R.layout.simple_expandable_list_item_1, helper.getActividadesID());
        spinnerActividad.setAdapter(actividadArrayAdapter);
    }

    public void llenarSpinnerE(ControlDB helper){
        equipoArrayAdapter= new ArrayAdapter<Integer>(AgregarPrestamoActivity.this, android.R.layout.simple_expandable_list_item_1, helper.getEquipoID());
        spinnerEquipo.setAdapter(equipoArrayAdapter);
    }

}