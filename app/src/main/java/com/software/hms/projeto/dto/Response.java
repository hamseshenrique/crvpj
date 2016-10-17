package com.software.hms.projeto.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by hms on 16/10/16.
 */

public class Response implements Serializable{
    private Long id;
    private String date_created;
    private String date_approved;
    private String date_last_update;
    private String money_release_date;
    private Integer collector_id;
    private String status;

    public Response(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }

    public String getDate_approved() {
        return date_approved;
    }

    public void setDate_approved(String date_approved) {
        this.date_approved = date_approved;
    }

    public String getDate_last_update() {
        return date_last_update;
    }

    public void setDate_last_update(String date_last_update) {
        this.date_last_update = date_last_update;
    }

    public String getMoney_release_date() {
        return money_release_date;
    }

    public void setMoney_release_date(String money_release_date) {
        this.money_release_date = money_release_date;
    }

    public Integer getCollector_id() {
        return collector_id;
    }

    public void setCollector_id(Integer collector_id) {
        this.collector_id = collector_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
