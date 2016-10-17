package com.software.hms.projeto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mercadopago.PaymentMethodsActivity;
import com.mercadopago.adapters.ErrorHandlingCallAdapter;
import com.mercadopago.adapters.PaymentMethodsAdapter;
import com.mercadopago.core.MercadoPago;
import com.mercadopago.decorations.DividerItemDecoration;
import com.mercadopago.model.ApiException;
import com.mercadopago.model.PaymentMethod;
import com.mercadopago.util.JsonUtil;
import com.mercadopago.util.LayoutUtil;
import com.software.hms.projeto.componentes.HmsStatics;

import java.util.List;

import retrofit2.Response;

public class FormaPagamentoActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private FormaPagamentoActivity activity;
    private String valor;
    private Integer parcelas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutUtil.showProgressLayout(this);
        setContentView(com.mercadopago.R.layout.activity_payment_methods);
        activity = this;
        this.mRecyclerView = (RecyclerView)this.findViewById(com.mercadopago.R.id.payment_methods_list);
        this.mRecyclerView.setHasFixedSize(true);
        this.mRecyclerView.addItemDecoration(new DividerItemDecoration(this, 1));
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        valor = this.getIntent().getStringExtra("valor");
        parcelas = this.getIntent().getIntExtra("parcela",0);

        MercadoPago mercadoPago = new MercadoPago.Builder()
        .setContext(this)
        .setPublicKey(HmsStatics.YOUR_TOKEN)
        .build();

        mercadoPago.getPaymentMethods().enqueue(new ErrorHandlingCallAdapter.MyCallback<List<PaymentMethod>>() {
            @Override
            public void success(Response<List<PaymentMethod>> response) {
                mRecyclerView.setAdapter(new PaymentMethodsAdapter(activity,
                        response.body(),new View.OnClickListener() {
                    public void onClick(View view) {
                        PaymentMethod selectedPaymentMethod = (PaymentMethod)view.getTag();
                        Intent intent = new Intent(activity,CardActivity.class);
                        intent.putExtra("paymentMethod",JsonUtil.getInstance().toJson(selectedPaymentMethod));
                        intent.putExtra("valor",valor);
                        intent.putExtra("parcela",parcelas);
                        activity.startActivity(intent);
                    }
                }));
                LayoutUtil.showRegularLayout(activity);
            }

            @Override
            public void failure(ApiException e) {
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }
}