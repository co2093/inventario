package sv.ues.fia.eisi.pdm115proyecto1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuPrincipalInventario extends AppCompatActivity {

    Button btnTraslado, btnPrestamo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal_inventario);

        btnTraslado = findViewById(R.id.btnMenuTraslados);
        btnPrestamo = findViewById(R.id.btnMenuPrestamos);

        btnPrestamo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MenuPrestamoActivity.class);
                startActivityForResult(intent,0);
            }
        });

        btnTraslado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MenuRazonActivity.class);
                startActivityForResult(intent,0);
            }
        });


    }
}