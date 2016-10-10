package com.software.hms.projeto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;

import com.mercadopago.core.MercadoPago;
import com.software.hms.projeto.componentes.Rodape;

import java.util.ArrayList;
import java.util.List;

public class AmigoActivity extends AppCompatActivity {

    private static final String yourPublicKey = "TEST-0478d966-6c35-42be-8978-54d9526a2e5a";
    protected List<String> mSupportedPaymentTypes = new ArrayList<String>(){{
        add("credit_card");
    }};

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
                    new MercadoPago.StartActivityBuilder()
                            .setActivity(amigo)
                            .setPublicKey(yourPublicKey)
                            .setSupportedPaymentTypes(mSupportedPaymentTypes)
                            .startPaymentMethodsActivity();
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == MercadoPago.PAYMENT_METHODS_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                /*new MercadoPago.StartActivityBuilder()
                        .setActivity(amigo)
                        .setPublicKey(yourPublicKey)
                        .setSupportedPaymentTypes(mSupportedPaymentTypes)
                        .setRequireSecurityCode(false)
                        .startNewCardActivity()*/
                Intent intent = new Intent(this,CardActivity.class);
                intent.putExtra("paymentMethod",data.getStringExtra("paymentMethod"));
                this.startActivity(intent);
            }
        }else if (requestCode == MercadoPago.CONGRATS_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

            }

        }
    }
}
