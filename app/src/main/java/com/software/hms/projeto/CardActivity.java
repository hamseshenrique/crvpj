package com.software.hms.projeto;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.mercadopago.adapters.ErrorHandlingCallAdapter;
import com.mercadopago.adapters.IdentificationTypesAdapter;
import com.mercadopago.core.MercadoPago;
import com.mercadopago.model.ApiException;
import com.mercadopago.model.CardToken;
import com.mercadopago.model.IdentificationType;
import com.mercadopago.model.Payment;
import com.mercadopago.model.PaymentMethod;
import com.mercadopago.model.Token;
import com.mercadopago.util.ApiUtil;
import com.mercadopago.util.JsonUtil;
import com.mercadopago.util.LayoutUtil;
import com.mercadopago.util.MercadoPagoUtil;
import com.software.hms.projeto.async.PagamentoAsync;
import com.software.hms.projeto.componentes.HmsMask;
import com.software.hms.projeto.componentes.HmsStatics;
import com.software.hms.projeto.dto.PagamentoDTO;
import com.software.hms.projeto.dto.PayerDTO;
import com.software.hms.projeto.dto.RetornoDTO;
import com.software.hms.projeto.interfaces.CruzVermelhaRest;
import com.software.hms.projeto.security.OkHttpBasicAuth;

import java.math.BigDecimal;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CardActivity extends AppCompatActivity {

    private MercadoPago mMercadoPago;
    private CardActivity cardActivity;
    protected String mKey;
    protected String mKeyType;
    protected PaymentMethod mPaymentMethod;
    protected Boolean mRequireSecurityCode = Boolean.TRUE;

    // Input controls
    protected EditText mCardHolderName;
    protected EditText mCardNumber;
    protected TextView mCVVDescriptor;
    protected ImageView mCVVImage;
    protected TextView mExpiryError;
    protected EditText mExpiryMonth;
    protected EditText mExpiryYear;
    protected EditText mIdentificationNumber;
    protected RelativeLayout mIdentificationLayout;
    protected Spinner mIdentificationType;
    protected RelativeLayout mSecurityCodeLayout;
    protected EditText mSecurityCode;
    private static final String OUTRO = "Outro Valor";
    private String valor;
    private RelativeLayout otrValor;
    private EditText txtOtrValor;
    private Integer parcelas;
    private Spinner cbParcelas;
    private Spinner cbValor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        this.cardActivity = this;

        mMercadoPago = new MercadoPago.Builder()
                .setContext(this)
                .setPublicKey(HmsStatics.YOUR_TOKEN)
                .build();

        final String paymentMethod = this.getIntent().getStringExtra("paymentMethod");
        if (paymentMethod == null) {
            Intent returnIntent = new Intent();
            setResult(RESULT_CANCELED, returnIntent);
            finish();
            return;
        }

        mPaymentMethod = JsonUtil.getInstance().fromJson(paymentMethod, PaymentMethod.class);

        // Set input controls
        mCardNumber = (EditText) findViewById(R.id.cardNumber);
        mCardHolderName = (EditText) findViewById(R.id.cardholderName);
        mIdentificationNumber = (EditText) findViewById(R.id.identificationNumber);
        mIdentificationType = (Spinner) findViewById(R.id.identificationType);
        mIdentificationLayout = (RelativeLayout) findViewById(R.id.identificationLayout);
        mSecurityCodeLayout = (RelativeLayout) findViewById(R.id.securityCodeLayout);
        mCVVImage = (ImageView) findViewById(R.id.cVVImage);
        mCVVDescriptor = (TextView) findViewById(R.id.cVVDescriptor);
        mSecurityCode = (EditText) findViewById(R.id.securityCode);
        mExpiryError = (TextView) findViewById(R.id.expiryError);
        mExpiryMonth = (EditText) findViewById(R.id.expiryMonth);
        mExpiryYear = (EditText) findViewById(R.id.expiryYear);
        otrValor = (RelativeLayout) findViewById(R.id.otrValor);

        txtOtrValor = (EditText) findViewById(R.id.txtOtrValor);
        txtOtrValor.addTextChangedListener(new HmsMask("##.###.###,##"));

        // Set identification type listener to control identification number keyboard
        setIdentificationNumberKeyboardBehavior();

        // Error text cleaning hack
        setErrorTextCleaner(mCardHolderName);

        // Get identification types
        getIdentificationTypesAsync();

        // Set payment method image
        if (mPaymentMethod.getId() != null) {
            ImageView pmImage = (ImageView) findViewById(R.id.pmImage);
            if (pmImage != null) {
                pmImage.setImageResource(MercadoPagoUtil.getPaymentMethodIcon(this, mPaymentMethod.getId()));
            }
        }

        // Set up expiry edit texts
        mExpiryMonth.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                mExpiryError.setError(null);
                return false;
            }
        });
        mExpiryYear.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                mExpiryError.setError(null);
                return false;
            }
        });

        // Set security code visibility
        setSecurityCodeLayout();
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.adapterValor, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cbValor = (Spinner)findViewById(R.id.cbValor);
        cbValor.setAdapter(adapter);
        cbValor.setSelection(0);
        cbValor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TextView textView = (TextView) view;
                valor = textView.getText().toString();
                if(valor.equals(OUTRO)){
                    otrValor.setVisibility(View.VISIBLE);
                    valor = "";
                }else{
                    otrValor.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ArrayAdapter<CharSequence> adapterParcelas = ArrayAdapter.createFromResource(this,
                R.array.adapterParcelas, android.R.layout.simple_spinner_item);
        adapterParcelas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cbParcelas = (Spinner) findViewById(R.id.cbParcelas);
        cbParcelas.setAdapter(adapterParcelas);
        cbParcelas.setSelection(0);
        cbParcelas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TextView textView = (TextView) view;
                if(!TextUtils.isEmpty(textView.getText().toString())){
                    parcelas = Integer.valueOf(textView.getText().toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    protected void setContentView() {

        setContentView(R.layout.activity_new_card);
    }

    @Override
    public void onBackPressed() {

        Intent returnIntent = new Intent();
        returnIntent.putExtra("backButtonPressed", true);
        setResult(RESULT_CANCELED, returnIntent);
        finish();
    }

    public void refreshLayout(View view) {

        getIdentificationTypesAsync();
    }

    public void submitForm(View view) {

        LayoutUtil.hideKeyboard(cardActivity);

        CardToken cardToken = new CardToken(getCardNumber(), getMonth(), getYear(), getSecurityCode(), getCardHolderName(),
                getIdentificationTypeId(getIdentificationType()), getIdentificationNumber());

        if (validateForm(cardToken)) {
            LayoutUtil.showProgressLayout(cardActivity);
            mMercadoPago.createToken(cardToken).enqueue(new ErrorHandlingCallAdapter.MyCallback<Token>() {
                @Override
                public void success(Response<Token> response) {

                    Token tokenMercado = response.body();

                    final SharedPreferences sharedPreferences = cardActivity.getSharedPreferences("CRUZHMSVERMELHA", Context.MODE_PRIVATE);
                    final String token = sharedPreferences.getString(HmsStatics.getEmail(),null);

                    final PagamentoDTO pagamentoDTO = new PagamentoDTO();
                    pagamentoDTO.setDescription("CRUZ VERMELHA");
                    pagamentoDTO.setInstallments(parcelas);
                    pagamentoDTO.setPayment_method_id(mPaymentMethod.getId());
                    pagamentoDTO.setToken(tokenMercado.getId());
                    pagamentoDTO.setTransaction_amount(new BigDecimal(valor.replace(".","").replace(",",".")).doubleValue());

//                    PagamentoAsync pagamentoAsync = new PagamentoAsync(cardActivity,token);
//                    pagamentoAsync.execute(pagamentoDTO);
//                    Payment payment = new Payment();
//                    payment.set
//                    new MercadoPago.StartActivityBuilder()
//                            .setActivity(cardActivity)
//                            .setPayment(response.body())
//                            .setPaymentMethod(mPaymentMethod)
//                            .startCongratsActivity();
                }

                @Override
                public void failure(ApiException e) {

                }
            });
        }
    }

    protected boolean validateForm(CardToken cardToken) {

        boolean result = true;
        boolean focusSet = false;

        try {
            validateCardNumber(cardToken);
            mCardNumber.setError(null);
        }
        catch (Exception ex) {
            mCardNumber.setError(ex.getMessage());
            mCardNumber.requestFocus();
            result = false;
            focusSet = true;
        }

        if (mRequireSecurityCode) {
            try {
                validateSecurityCode(cardToken);
                mSecurityCode.setError(null);
            } catch (Exception ex) {
                mSecurityCode.setError(ex.getMessage());
                if (!focusSet) {
                    mSecurityCode.requestFocus();
                    focusSet = true;
                }
                result = false;
            }
        }

        if (!cardToken.validateExpiryDate()) {
            mExpiryError.setVisibility(View.VISIBLE);
            mExpiryError.setError(getString(com.mercadopago.R.string.mpsdk_invalid_field));
            if (!focusSet) {
                mExpiryMonth.requestFocus();
                focusSet = true;
            }
            result = false;
        } else {
            mExpiryError.setError(null);
            mExpiryError.setVisibility(View.GONE);
        }

        if (!cardToken.validateCardholderName()) {
            mCardHolderName.setError(getString(R.string.mpsdk_invalid_field));
            if (!focusSet) {
                mCardHolderName.requestFocus();
                focusSet = true;
            }
            result = false;
        } else {
            mCardHolderName.setError(null);
        }

        if (getIdentificationType() != null) {
            if (!cardToken.validateIdentificationNumber(getIdentificationType())) {
                mIdentificationNumber.setError(getString(R.string.mpsdk_invalid_field));
                if (!focusSet) {
                    mIdentificationNumber.requestFocus();
                }
                result = false;
            } else {
                mIdentificationNumber.setError(null);
            }
        }

        if(TextUtils.isEmpty(valor)){
            cbValor.setBackgroundColor(Color.RED);
            result = false;
        }else if(valor.equals(OUTRO) && TextUtils.isEmpty(txtOtrValor.getText().toString())){
            txtOtrValor.setError("Informe um Valor");
            txtOtrValor.requestFocus();
            result = false;
        }else{
            txtOtrValor.setError(null);
        }

        if(parcelas == null){
            cbParcelas.setBackgroundColor(Color.RED);
            result = false;
        }

        return result;
    }

    protected void validateCardNumber(CardToken cardToken) throws Exception {

        cardToken.validateCardNumber(this, mPaymentMethod);
    }

    protected void validateSecurityCode(CardToken cardToken) throws Exception {

        cardToken.validateSecurityCode(this, mPaymentMethod);
    }

    protected void getIdentificationTypesAsync() {

        LayoutUtil.showProgressLayout(cardActivity);

        MercadoPago mercadoPago = new MercadoPago.Builder()
                .setContext(cardActivity)
                .setKey(HmsStatics.YOUR_TOKEN,"public_key")
                .build();

        ErrorHandlingCallAdapter.MyCall<List<IdentificationType>> call = mercadoPago.getIdentificationTypes();
        call.enqueue(new ErrorHandlingCallAdapter.MyCallback<List<IdentificationType>>() {
            @Override
            public void success(Response<List<IdentificationType>> response) {

                mIdentificationType.setAdapter(new IdentificationTypesAdapter(cardActivity, response.body()));

                if (mSecurityCodeLayout.getVisibility() == View.GONE) {
                    setFormGoButton(mIdentificationNumber);
                }

                LayoutUtil.showRegularLayout(cardActivity);
            }

            @Override
            public void failure(ApiException apiException) {

                if (apiException.getStatus() == 404) {

                    mIdentificationLayout.setVisibility(View.GONE);

                    if (mSecurityCodeLayout.getVisibility() == View.GONE) {
                        setFormGoButton(mCardHolderName);
                    }

                    LayoutUtil.showRegularLayout(cardActivity);

                } else {

                    ApiUtil.finishWithApiException(cardActivity, apiException);
                }
            }
        });
    }

    protected void setFormGoButton(final EditText editText) {

        editText.setOnKeyListener(new View.OnKeyListener() {

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    submitForm(v);
                    return true;
                }
                return false;
            }
        });
    }

    protected void setIdentificationNumberKeyboardBehavior() {

        mIdentificationType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                IdentificationType identificationType = getIdentificationType();
                if (identificationType != null) {
                    if (identificationType.getType().equals("number")) {
                        mIdentificationNumber.setInputType(InputType.TYPE_CLASS_NUMBER);
                    } else {
                        mIdentificationNumber.setInputType(InputType.TYPE_CLASS_TEXT);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    protected void setErrorTextCleaner(final EditText editText) {

        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void afterTextChanged(Editable edt) {
                if (editText.getText().length() > 0) {
                    editText.setError(null);
                }
            }
        });
    }

    protected void setSecurityCodeLayout() {

        if (mPaymentMethod != null) {

            if (mRequireSecurityCode) {

                mCVVDescriptor.setText(MercadoPagoUtil.getCVVDescriptor(this, mPaymentMethod));

                mCVVImage.setImageDrawable(getResources().getDrawable(MercadoPagoUtil.getCVVImageResource(this, mPaymentMethod)));

                mSecurityCodeLayout.setVisibility(View.VISIBLE);
                setFormGoButton(mSecurityCode);

                return;
            }
        }
        mSecurityCodeLayout.setVisibility(View.GONE);
    }

    protected String getCardNumber() {

        return this.mCardNumber.getText().toString();
    }

    protected String getSecurityCode() {

        return this.mSecurityCode.getText().toString();
    }

    private Integer getMonth() {

        Integer result;
        try {
            result = Integer.parseInt(this.mExpiryMonth.getText().toString());
        } catch (Exception ex) {
            result = null;
        }
        return result;
    }

    private Integer getYear() {

        Integer result;
        try {
            result = Integer.parseInt(this.mExpiryYear.getText().toString());
        } catch (Exception ex) {
            result = null;
        }
        return result;
    }

    protected String getCardHolderName() {

        return this.mCardHolderName.getText().toString();
    }

    protected IdentificationType getIdentificationType() {

        return (IdentificationType) mIdentificationType.getSelectedItem();
    }

    protected String getIdentificationTypeId(IdentificationType identificationType) {

        if (identificationType != null) {
            return identificationType.getId();
        } else {
            return null;
        }
    }

    protected String getIdentificationNumber() {

        if (!this.mIdentificationNumber.getText().toString().equals("")) {
            return this.mIdentificationNumber.getText().toString();
        } else {
            return null;
        }
    }
}
