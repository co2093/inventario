package sv.ues.fia.eisi.pdm115proyecto1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MenuPrincipalActivity extends AppCompatActivity {


    Button btnMenuPrincipalEquipo;
    Button btnMenuPrincipalDocumentos;
    Button btnMenuPrincipalPersonas;
    Button btnMenuPrincipalInventario;
    Button btnMenuPrincipalEvento;
    Button btnWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        btnMenuPrincipalEquipo = findViewById(R.id.btnMenuEquipo);
        btnMenuPrincipalDocumentos = findViewById(R.id.btnMenuDocumentos);
        btnMenuPrincipalPersonas = findViewById(R.id.btnMenuPersonas);
        btnMenuPrincipalInventario = findViewById(R.id.btnMenuInventario);
        btnMenuPrincipalEvento = findViewById(R.id.btnMenuEventos);
        btnWeb = findViewById(R.id.btnMenuServiciosWeb);

        btnWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MenuServiciosWeb.class);
                startActivityForResult(intent,0);
            }
        });


        btnMenuPrincipalEquipo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MenuPrincipalEquipoInformatico.class);
                startActivityForResult(intent,0);
            }
        });

        btnMenuPrincipalDocumentos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MenuPrincipalDocumentos.class);
                startActivityForResult(intent,0);
            }
        });

        btnMenuPrincipalPersonas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MenuPrincipalPersonas.class);
                startActivityForResult(intent,0);
            }
        });

        btnMenuPrincipalInventario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MenuPrincipalInventario.class);
                startActivityForResult(intent,0);
            }
        });

        btnMenuPrincipalEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MenuPrincipalEvento.class);
                startActivityForResult(intent,0);
            }
        });



    }




}