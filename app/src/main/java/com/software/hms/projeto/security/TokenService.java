package com.software.hms.projeto.security;

import android.util.Base64;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by root on 17/08/16.
 */
public class TokenService {

    private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";
    public static final String SECRET_KEY = "CruzVemelhaApiHms";
    private Mac hmac;

    public TokenService(){
        try {
            hmac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
            hmac.init(new SecretKeySpec(SECRET_KEY.getBytes(),HMAC_SHA1_ALGORITHM));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
    }

    public String criptSenha(String senha) {

        byte[] hash = createHmac(senha.getBytes());

        return toBase64(hash);
    }

    private String toBase64(byte[] content) {
        return Base64.encodeToString(content,Base64.NO_WRAP);
    }

    private synchronized byte[] createHmac(byte[] content) {
        return hmac.doFinal(content);
    }
}
