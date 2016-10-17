package com.software.hms.projeto.dto;

import java.io.Serializable;

/**
 * Created by hms on 11/10/16.
 */

public class PayerDTO implements Serializable{

    private String email;

    public PayerDTO(){}


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
