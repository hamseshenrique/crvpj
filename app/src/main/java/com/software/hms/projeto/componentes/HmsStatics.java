package com.software.hms.projeto.componentes;

import android.app.Activity;
import android.util.Log;
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
import com.mercadopago.util.ApiUtil;
import com.mercadopago.util.LayoutUtil;
import com.software.hms.projeto.dto.PagamentoDTO;
import com.software.hms.projeto.dto.RetornoDTO;
import com.software.hms.projeto.interfaces.CruzVermelhaRest;
import com.software.hms.projeto.security.OkHttpBasicAuth;

import java.io.IOException;
import java.math.BigDecimal;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by root on 28/08/16.
 */
public class HmsStatics {

    private static String email;
    private static String fotoUsu;
    public static final String SERVER = "http://ec2-54-244-216-207.us-west-2.compute.amazonaws.com:8080";
    //public static final String SERVER = "http://192.168.100.13:8080";
    //public static final String YOUR_TOKEN = "APP_USR-46294dde-90eb-4e0e-bd8f-437391748e9d";
    public static final String YOUR_TOKEN = "TEST-293b3f85-122c-4d38-b066-b22a21b98a0c";

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


    public static void createPayment(final PagamentoDTO pagamentoDTO,final String token) throws IOException {

        /*    ErrorHandlingCallAdapter.MyCall<Payment> call = MerchantServer.createPayment(activity,
                    "https://www.mercadopago.com", "v1/payments", payment);

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
            });*/
    }
}
