package com.kimede.viper.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Configure {

    @SerializedName("Id")
    @Expose
    private int id;
    @SerializedName("Banner")
    @Expose
    private String banner;
    @SerializedName("Insterstial")
    @Expose
    private String insterstial;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getInsterstial() {
        return insterstial;
    }

    public void setInsterstial(String insterstial) {
        this.insterstial = insterstial;
    }

}