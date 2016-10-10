package com.software.hms.projeto.componentes;

import android.app.Activity;
import android.widget.Toast;

import com.mercadopago.adapters.ErrorHandlingCallAdapter;
import com.mercadopago.core.MercadoPago;
import com.mercadopago.core.MerchantServer;
import com.mercadopago.model.ApiException;
import com.mercadopago.model.Discount;
import com.mercadopago.model.Item;
import com.mercadopago.model.MerchantPayment;
import com.mercadopago.model.Payment;
import com.mercadopago.model.PaymentMethod;
import com.mercadopago.util.LayoutUtil;

import java.math.BigDecimal;

import retrofit2.Response;

/**
 * Created by root on 28/08/16.
 */
public class HmsStatics {

    private static String email;
    private static String fotoUsu;
    public static final String SERVER = "http://ec2-54-244-216-207.us-west-2.compute.amazonaws.com:8080";
    //public static final String SERVER = "http://192.168.100.13:8080";
    private static final String DUMMY_MERCHANT_ACCESS_TOKEN_BR = "mlb-cards-data";

    public HmsStatics(){}

    public static String getEmail(){
        return email;
    }

    public static void setEmail(final String email){
        HmsStatics.email = email;
    }

    public static String getFotoUsu() {
        return fotoUsu;
    }

    public static void setFotoUsu(String fotoUsu) {
        HmsStatics.fotoUsu = fotoUsu;
    }


    public static void createPayment(final Activity activity, String token,
                                     Integer installments, Long cardIssuerId,
                                     final PaymentMethod paymentMethod, Discount discount,
                                     final BigDecimal valor) {

        if (paymentMethod != null) {

            LayoutUtil.showProgressLayout(activity);

            Item item = new Item("CRUZVERMELHA", 1,valor);

            String paymentMethodId = paymentMethod.getId();

            Long campaignId = (discount != null) ? discount.getId() : null;

            MerchantPayment payment = new MerchantPayment(item, installments, cardIssuerId,
                    token, paymentMethodId, campaignId, DUMMY_MERCHANT_ACCESS_TOKEN_BR);

            ErrorHandlingCallAdapter.MyCall<Payment> call = MerchantServer.createPayment(activity,
                    "https://www.mercadopago.com", "/v1/payments", payment);
            call.enqueue(new ErrorHandlingCallAdapter.MyCallback<Payment>() {
                @Override
                public void success(Response<Payment> response) {

                    new MercadoPago.StartActivityBuilder()
                            .setActivity(activity)
                            .setPayment(response.body())
                            .setPaymentMethod(paymentMethod)
                            .startCongratsActivity();
                }

                @Override
                public void failure(ApiException apiException) {

                    LayoutUtil.showRegularLayout(activity);
                    Toast.makeText(activity, apiException.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        } else {

            Toast.makeText(activity, "Metodo de Pagamamento Invalido", Toast.LENGTH_LONG).show();
        }
    }
}
