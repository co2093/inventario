package sv.ues.fia.eisi.pdm115proyecto1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuPrincipalPersonas extends AppCompatActivity {

    Button btnActivityDocentes;
    Button btnActivityAlumnos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal_personas);

        btnActivityAlumnos = findViewById(R.id.btnMenuAlumnos);
        btnActivityDocentes = findViewById(R.id.btnMenuDocentes);

        btnActivityDocentes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MenuDocenteActivity.class);
                startActivityForResult(intent,0);
            }
        });

        btnActivityAlumnos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MenuAlumnoActivity.class);
                startActivityForResult(intent,0);
            }
        });


    }
}