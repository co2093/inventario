package sv.ues.fia.eisi.pdm115proyecto1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuPrincipalActivityDos extends AppCompatActivity {


    Button btnMenuPrincipalEquipo;
    Button btnMenuPrincipalDocumentos;

    Button btnMenuPrincipalInventario;
    Button btnMenuPrincipalEvento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal_dos);

        btnMenuPrincipalEquipo = findViewById(R.id.btnMenuEquipo);
        btnMenuPrincipalDocumentos = findViewById(R.id.btnMenuDocumentos);

        btnMenuPrincipalInventario = findViewById(R.id.btnMenuInventario);
        btnMenuPrincipalEvento = findViewById(R.id.btnMenuEventos);

        btnMenuPrincipalEquipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MenuPrincipalEquipoInformatico.class);
                startActivityForResult(intent, 0);
            }
        });

        btnMenuPrincipalDocumentos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MenuPrincipalDocumentos.class);
                startActivityForResult(intent, 0);
            }
        });


        btnMenuPrincipalInventario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MenuPrincipalInventario.class);
                startActivityForResult(intent, 0);
            }
        });

        btnMenuPrincipalEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MenuPrincipalEvento.class);
                startActivityForResult(intent, 0);
            }
        });


    }
}




