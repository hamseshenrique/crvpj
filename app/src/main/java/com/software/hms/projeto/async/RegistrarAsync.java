package com.software.hms.projeto.async;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.software.hms.projeto.MenuActivity;
import com.software.hms.projeto.R;
import com.software.hms.projeto.componentes.HmsStatics;
import com.software.hms.projeto.dto.RetornoDTO;
import com.software.hms.projeto.enuns.RetornoEnum;
import com.software.hms.projeto.dto.UsuarioDTO;
import com.software.hms.projeto.interfaces.Usuario;
import com.software.hms.projeto.security.TokenService;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by root on 14/08/16.
 */
public class RegistrarAsync extends AsyncTask<UsuarioDTO,Void,RetornoDTO>{

    private ProgressDialog dialog;
    private Context context;
    private static final String TOKEN = "TOKEN_CRUZ";
    private String name = "";
    private SharedPreferences.Editor editor;

    public RegistrarAsync(final Context context,final SharedPreferences.Editor editor){
        this.context = context;
        this.editor = editor;
    }

    @Override
    protected void onPreExecute() {
        dialog = new ProgressDialog(context);
        dialog.setMessage(context.getString(R.string.aguarde));
        dialog.show();
        super.onPreExecute();
    }

    @Override
    protected RetornoDTO doInBackground(UsuarioDTO... usuariotDTOs) {
        final UsuarioDTO usuarioDTO = usuariotDTOs[0];
        name = usuarioDTO.getEmail();

        final TokenService tokenService = new TokenService();
        usuarioDTO.setSenha(tokenService.criptSenha(usuarioDTO.getSenha()));
        RetornoDTO retornoDTO = new RetornoDTO(RetornoEnum.ERRO);

        if(usuarioDTO != null){
            try{
                final okhttp3.OkHttpClient okHttpClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        okhttp3.Request original = chain.request();

                        RetornoDTO retornoDTO = new RetornoDTO(RetornoEnum.ERRO);

                        Request request = original.newBuilder()
                                .header("Content-Type","application/json")
                                .method(original.method(), original.body())
                                .build();

                        return chain.proceed(request);
                    }
                }).readTimeout(80000, TimeUnit.MILLISECONDS).build();


                final Retrofit retrofit = new Retrofit.Builder().baseUrl(
                        HmsStatics.SERVER).client(okHttpClient).
                        addConverterFactory(GsonConverterFactory.create()).build();

                final Usuario usuario = retrofit.create(Usuario.class);

                Call<RetornoDTO> call = usuario.registrar(usuarioDTO);
                retrofit2.Response<RetornoDTO> response = call.execute();
                if(response.isSuccessful()){
                    retornoDTO = response.body();
                }

            }catch (Exception e){
                e.printStackTrace();
               // retornoDTO.setDescricao(e.getMessage());
            }
        }
        return retornoDTO;
    }

    @Override
    protected void onPostExecute(final RetornoDTO retornoDTO) {

        Log.i("CRUZ VERMELHA",retornoDTO.getRetornoEnum().getDescricao());
        dialog.dismiss();
        if(retornoDTO.getRetornoEnum().equals(RetornoEnum.SUCESSO)){
            editor.putString(name,retornoDTO.getToken());
            editor.putString("LOGADO",name);
            HmsStatics.setEmail(name);
            editor.commit();

            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setTitle(R.string.registrado);
            alertDialogBuilder.setPositiveButton(context.getString(R.string.ok),
                    new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(context,MenuActivity.class);
                    context.startActivity(intent);
                }
            });

            alertDialogBuilder.create().show();

            HmsStatics.setMensagemDTO(retornoDTO.getMensagemDTO());
            NotificacaoAsync notificacaoAsync = new NotificacaoAsync(context);
            notificacaoAsync.execute(HmsStatics.getMensagemDTO());

        }else if(retornoDTO.getRetornoEnum().equals(RetornoEnum.USUARIO_NAO_CADASTRADO)){
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setTitle(retornoDTO.getDescricao());
            alertDialogBuilder.setPositiveButton(context.getString(R.string.ok),
                    new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });

            alertDialogBuilder.create().show();
        }else{
            final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
            alertDialogBuilder.setTitle(retornoDTO.getDescricao());
            alertDialogBuilder.setPositiveButton(context.getString(R.string.erro_cadastro_usu),
                    new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });

            alertDialogBuilder.create().show();

        }
    }
}