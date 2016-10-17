package com.software.hms.projeto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.mercadopago.PaymentMethodsActivity;
import com.mercadopago.adapters.ErrorHandlingCallAdapter;
import com.mercadopago.core.MercadoPago;
import com.mercadopago.model.ApiException;
import com.mercadopago.model.Card;
import com.mercadopago.model.PaymentMethod;
import com.mercadopago.util.JsonUtil;
import com.mercadopago.util.LayoutUtil;
import com.mercadopago.util.MercadoPagoUtil;
import com.software.hms.projeto.componentes.HmsStatics;
import com.software.hms.projeto.componentes.Rodape;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class AmigoActivity extends AppCompatActivity {

    private AmigoActivity amigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amigo);

        this.amigo = this;

        final Rodape rodape = new Rodape();
        rodape.onClickButtons(this);

        final CardView cardView = (CardView)findViewById(R.id.mensal);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Intent intent = new Intent(amigo,ValorDoacaoActivity.class);
                    amigo.startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        final CardView unica = (CardView)findViewById(R.id.unica);
        unica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Intent intent = new Intent(amigo,ValorDoacaoActivity.class);
                    amigo.startActivity(intent);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

    }
}
