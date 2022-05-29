package com.kimede.viper.Models;

import com.google.gson.annotations.Expose;
import java.io.Serializable;

public class Videos implements Serializable {
    @Expose
    private String Anime;
    @Expose
    private String Data;
    @Expose
    private Long Id;
    @Expose
    private String LinkHq;
    @Expose
    private String LinkMq;
    @Expose
    private String Nome;


    public String getAnime() {
        return this.Anime;
    }

    public String getData() {
        return this.Data;
    }

    public Long getId() {
        return this.Id;
    }

    public String getLinkHq() {
        return this.LinkHq;
    }

    public String getLinkMq() {
        return this.LinkMq;
    }

    public String getNome() {
        return this.Nome;
    }

    public void setAnime(String str) {
        this.Anime = str;
    }

    public void setData(String str) {
        this.Data = str;
    }

    public void setId(Long l) {
        this.Id = l;
    }

    public void setLinkHq(String str) {
        this.LinkHq = str;
    }

    public void setLinkMq(String str) {
        this.LinkMq = str;
    }

    public void setNome(String str) {
        this.Nome = str;
    }
}
