package sv.ues.fia.eisi.pdm115proyecto1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class MenuServiciosWeb extends AppCompatActivity {

    Button autoresServices;
    Button equipoServices;
    Button librosServices;
    Button alumnosServices;
    Button docentesServides;
    ImageButton inicio;
    Button actualizarDocentes, actualizarLibros, actualizarAutores;
    


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_servicios_web);

        equipoServices = findViewById(R.id.btnMenuEquipoSW);
        autoresServices = findViewById(R.id.btnMenuAutoresSW);
        librosServices = findViewById(R.id.btnMenuDocumentosSW);
        alumnosServices = findViewById(R.id.btnMenuAlumnosSW);
        docentesServides = findViewById(R.id.btnMenuDocentesSW);
        actualizarAutores = findViewById(R.id.btnMenuActualizarAutorSW);
        actualizarDocentes = findViewById(R.id.btnActualizarDocenteSW);
        actualizarLibros = findViewById(R.id.btnActualizarLibroSW);
        inicio = findViewById(R.id.imageButtonMenuInicio);

        inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MenuPrincipalActivity.class);
                startActivityForResult(intent,0);
            }
        });

        actualizarLibros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ServiciosWebActualizarLibro.class);
                startActivityForResult(intent, 0);
            }
        });

        actualizarDocentes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ServiciosWebActualizarDocente.class);
                startActivityForResult(intent,0);
            }
        });

        actualizarAutores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ServiciosWebActualizarAutor.class);
                startActivityForResult(intent,0);
            }
        });


        alumnosServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ServiciosWebAlumnoActivity.class);
                startActivityForResult(intent,0);
            }
        });

        docentesServides.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ServiciosWebDocentesActivity.class);
                startActivityForResult(intent,0);
            }
        });


        equipoServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ServiciosWebEquipoActivity.class);
                startActivityForResult(intent,0);
            }
        });

        autoresServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ServiciosWebAutoresActivity.class);
                startActivityForResult(intent,0);
            }
        });

        librosServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ServiciosWebLibrosActivity.class);
                startActivityForResult(intent,0);
            }
        });

    }
}