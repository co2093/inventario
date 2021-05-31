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

public class EditarPrestamoActivity extends AppCompatActivity {

    ImageButton btnAgregar, btnRegresar;
    ControlDB helper;
    EditText editTextId, editTextPrestamo, editTextDevolucion;
    Spinner spinnerDocentes, spinnerEquipo, spinnerActividad;
    ArrayAdapter docenteArrayAdapter, equipoArrayAdapter, actividadArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_prestamo);


        helper = new ControlDB(EditarPrestamoActivity.this);
        btnAgregar = findViewById(R.id.btnAgregarPrestamoEdit);
        btnRegresar = findViewById(R.id.btnRegresarPrestamoEdit);
        editTextId = findViewById(R.id.editTextPrestamoIDEdit);
        editTextPrestamo = findViewById(R.id.editTextPrestamoEdit);
        editTextDevolucion = findViewById(R.id.editTextDevolucionEdit);
        spinnerDocentes = findViewById(R.id.spinnerDocentesPrestamoEdit);
        spinnerEquipo = findViewById(R.id.spinnerEquipoPrestamoEdit);
        spinnerActividad = findViewById(R.id.spinnerActividadPrestamoEdit);

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

    public void actualizarPrestamo(View view){


        if(editTextId.getText().toString().isEmpty() || editTextPrestamo.getText().toString().isEmpty() || editTextDevolucion.getText().toString().isEmpty() || spinnerDocentes.getSelectedItem()==null || spinnerEquipo.getSelectedItem()==null || spinnerActividad.getSelectedItem()==null){
            Toast.makeText(EditarPrestamoActivity.this, "Todos los campos son obligatorios", Toast.LENGTH_LONG).show();
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
            regInsertados = helper.actualizar(prestamo1);
            helper.cerrar();
            Toast.makeText(this, regInsertados, Toast.LENGTH_LONG).show();
        }


    }

    public void llenarSpinnerD(ControlDB helper){
        docenteArrayAdapter= new ArrayAdapter<String>(EditarPrestamoActivity.this, android.R.layout.simple_expandable_list_item_1, helper.getDocentesNombre());
        spinnerDocentes.setAdapter(docenteArrayAdapter);
    }

    public void llenarSpinnerA(ControlDB helper){
        actividadArrayAdapter= new ArrayAdapter<Integer>(EditarPrestamoActivity.this, android.R.layout.simple_expandable_list_item_1, helper.getActividadesID());
        spinnerActividad.setAdapter(actividadArrayAdapter);
    }

    public void llenarSpinnerE(ControlDB helper){
        equipoArrayAdapter= new ArrayAdapter<Integer>(EditarPrestamoActivity.this, android.R.layout.simple_expandable_list_item_1, helper.getEquipoID());
        spinnerEquipo.setAdapter(equipoArrayAdapter);
    }
}