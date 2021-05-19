package sv.ues.fia.eisi.pdm115proyecto1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuPrincipalEquipoInformatico extends AppCompatActivity {


    Button btnEquipo, btnCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal_equipo_informatico);

        btnEquipo = findViewById(R.id.btnMenuEquipo2);
        btnCategoria = findViewById(R.id.btnMenuCategoria);

        btnCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CategoriaActivity.class);
                startActivityForResult(intent,0);
            }
        });



    }
}