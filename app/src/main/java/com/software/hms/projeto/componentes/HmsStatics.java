package com.software.hms.projeto.componentes;

/**
 * Created by root on 28/08/16.
 */
public class HmsStatics {

    private static String email;

    public HmsStatics(){}

    public static String getEmail(){
        return email;
    }

    public static void setEmail(final String email){
        HmsStatics.email = email;
    }
}
