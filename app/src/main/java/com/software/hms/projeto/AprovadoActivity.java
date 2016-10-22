package com.software.hms.projeto;

import android.app.Activity;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.mercadopago.model.PaymentMethod;
import com.mercadopago.util.CurrenciesUtil;
import com.mercadopago.util.JsonUtil;
import com.mercadopago.util.MercadoPagoUtil;
import com.software.hms.projeto.componentes.HmsStatics;
import com.software.hms.projeto.dto.PagamentoDTO;

import java.math.BigDecimal;
import java.text.ParseException;

public class AprovadoActivity extends AppCompatActivity {

    private PaymentMethod mPaymentMethod;
    private String valor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aprovado);
        final Activity activity = this;
        try{
            PagamentoDTO pagamentoDTO = (PagamentoDTO) this.getIntent().getSerializableExtra("payment");
            String paymentMethodString = this.getIntent().getStringExtra("paymentMethod");
            valor = this.getIntent().getStringExtra("valor");
            mPaymentMethod = JsonUtil.getInstance().fromJson(paymentMethodString, PaymentMethod.class);

            setLayout(pagamentoDTO);

            final Button button = (Button) findViewById(R.id.continuar);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(activity,MenuActivity.class);
                    activity.startActivity(intent);

                }
            });

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private void setLayout(PagamentoDTO pagamentoDTO) throws ParseException {
        this.setTitle(pagamentoDTO);
        this.setIcon(pagamentoDTO);
        this.setDescription(pagamentoDTO);
        this.setAmount(pagamentoDTO);
        this.setPaymentId(pagamentoDTO);
        this.setPaymentMethodDescription();
        this.setDateCreated(pagamentoDTO);
    }

    private void setTitle(PagamentoDTO pagamentoDTO) {
        if(pagamentoDTO != null && pagamentoDTO.getResponse() != null) {
            if(pagamentoDTO.getResponse().getStatus().equals("approved")) {
                this.setTitle(this.getString(com.mercadopago.R.string.mpsdk_approved_title));
            } else if(pagamentoDTO.getResponse().getStatus().equals("pending")) {
                this.setTitle(this.getString(com.mercadopago.R.string.mpsdk_pending_title));
            } else if(pagamentoDTO.getResponse().getStatus().equals("in_process")) {
                this.setTitle(this.getString(com.mercadopago.R.string.mpsdk_in_process_title));
            } else if(pagamentoDTO.getResponse().getStatus().equals("rejected")) {
                this.setTitle(this.getString(com.mercadopago.R.string.mpsdk_rejected_title));
            }
        }

    }

    private void setIcon(PagamentoDTO pagamentoDTO) {
        if(pagamentoDTO != null && pagamentoDTO.getResponse() != null) {
            ImageView iconIV = (ImageView)this.findViewById(com.mercadopago.R.id.icon);
            if(pagamentoDTO.getResponse().getStatus().equals("approved")) {
                iconIV.setImageResource(com.mercadopago.R.drawable.ic_approved);
            } else if(pagamentoDTO.getResponse().getStatus().equals("pending")) {
                iconIV.setImageResource(com.mercadopago.R.drawable.ic_pending);
            } else if(pagamentoDTO.getResponse().getStatus().equals("in_process")) {
                iconIV.setImageResource(com.mercadopago.R.drawable.ic_pending);
            } else if(pagamentoDTO.getResponse().getStatus().equals("rejected")) {
                iconIV.setImageResource(com.mercadopago.R.drawable.ic_rejected);
            }
        }

    }

    private void setDescription(PagamentoDTO pagamentoDTO) {
        if(pagamentoDTO != null && pagamentoDTO.getResponse() != null) {
            TextView descriptionText = (TextView)this.findViewById(com.mercadopago.R.id.description);
            if(pagamentoDTO.getResponse().getStatus().equals("approved")) {
                descriptionText.setText(this.getString(com.mercadopago.R.string.mpsdk_approved_message));
            } else if(pagamentoDTO.getResponse().getStatus().equals("pending")) {
                descriptionText.setText(this.getString(com.mercadopago.R.string.mpsdk_pending_ticket_message));
            } else if(pagamentoDTO.getResponse().getStatus().equals("in_process")) {
                descriptionText.setText(this.getString(com.mercadopago.R.string.mpsdk_in_process_message));
            } else if(pagamentoDTO.getResponse().getStatus().equals("rejected")) {
                descriptionText.setText(this.getString(com.mercadopago.R.string.mpsdk_rejected_message));
            }
        }

    }

    private void setAmount(PagamentoDTO pagamentoDTO) {
        if(pagamentoDTO != null && pagamentoDTO.getResponse() != null) {
            String formattedAmount = CurrenciesUtil.formatNumber(new BigDecimal(valor),"BRL");
            if(formattedAmount != null) {
                TextView amount = (TextView)this.findViewById(com.mercadopago.R.id.amount);
                amount.setText(formattedAmount);
            }
        }
    }

    private void setPaymentId(PagamentoDTO pagamentoDTO) {
        if(pagamentoDTO != null && pagamentoDTO.getResponse() != null) {
            TextView paymentIdText = (TextView)this.findViewById(com.mercadopago.R.id.paymentId);
            paymentIdText.setText(Long.toString(pagamentoDTO.getResponse().getId().longValue()));
        }

    }

    private void setPaymentMethodDescription() {
        TextView paymentMethodText = (TextView)this.findViewById(com.mercadopago.R.id.paymentMethod);
        paymentMethodText.setText(this.mPaymentMethod.getName());
        paymentMethodText.setCompoundDrawablesWithIntrinsicBounds(MercadoPagoUtil.getPaymentMethodIcon(this, this.mPaymentMethod.getId()), 0, 0, 0);
    }

    private void setDateCreated(PagamentoDTO pagamentoDTO) throws ParseException {
        if(pagamentoDTO != null && pagamentoDTO.getResponse() != null) {
            TextView dateCreatedText = (TextView)this.findViewById(com.mercadopago.R.id.dateCreated);
            dateCreatedText.setText(MercadoPagoUtil.formatDate(this, HmsStatics.formatDate(pagamentoDTO.getResponse().getDate_created())));
        }
    }
}
