package sv.ues.fia.eisi.pdm115proyecto1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuPrincipalDocumentos extends AppCompatActivity {

    Button menuLibros;
    Button menuTesis;
    Button menuEditorial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal_documentos);

        menuLibros = findViewById(R.id.btnMenuLibros);
        menuTesis = findViewById(R.id.btnMenuTesis);
        menuEditorial = findViewById(R.id.btnMenuEditorial);

        menuLibros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MenuLibroActivity.class);
                startActivityForResult(intent,0);
            }
        });

        menuEditorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MenuEditorialActivity.class);
                startActivityForResult(intent,0);
            }
        });

       menuTesis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MenuTesisActivity.class);
                startActivityForResult(intent,0);
            }
        });

    }
}