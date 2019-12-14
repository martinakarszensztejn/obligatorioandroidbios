package com.martina.obligatoriov0_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.martina.obligatoriov0_1.constantes.Constantes;

import org.w3c.dom.Text;

public class Transportation_status_update_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transportation_status_update_activity);

        Button button = findViewById(R.id.boton_lindo);
        TextView tv = findViewById(R.id.texto_lindo);
        String current_Status = getIntent().getStringExtra(Constantes.TRANSPORTATION_UPDATE_CURRENT_STATUS);
        tv.append(" "+current_Status);

        if (current_Status != null) {
            if(current_Status.equals(Constantes.ESTADO_1)){
                button.setText(R.string.boton_update+" "+Constantes.ESTADO_2);
            }else if(current_Status.equals(Constantes.ESTADO_2)){
                button.setText(R.string.boton_update+" "+Constantes.ESTADO_3);
            }else if(current_Status.equals(Constantes.ESTADO_3)){
                button.setText(R.string.boton_update+" "+Constantes.ESTADO_4);
            }else if(current_Status.equals(Constantes.ESTADO_4)){
                button.setText(R.string.boton_update+" "+Constantes.ESTADO_5);
            }else if(current_Status.equals(Constantes.ESTADO_5)){
                button.setText(R.string.boton_update+" "+Constantes.ESTADO_6);
            }else if(current_Status.equals(Constantes.ESTADO_6)){
                button.setText(R.string.pedido_finalizado);
                button.setClickable(false);
                button.setEnabled(false);
            }

        }



    }
}
