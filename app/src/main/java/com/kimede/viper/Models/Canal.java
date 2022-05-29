package com.kimede.viper.Models;

import com.google.gson.annotations.Expose;
import java.io.Serializable;

public class Canal implements Serializable, Comparable<Canal> {
    @Expose
    public String Ano;
    @Expose
    public String Categoria;
    @Expose
    public String Desc;
    @Expose
    public Long Id;
    @Expose
    public String Imagem;
    @Expose
    public String Nome;

    @Expose
    public Integer Rank;
    @Expose
    public Boolean Status;

    public Canal(Long l, String str, Boolean bool, String str2, String str3, String str4, String str5, Integer num) {
        this.Id = l;
        this.Nome = str;
        this.Status = bool;
        this.Ano = str2;
        this.Categoria = str3;
        this.Imagem = str4;
        this.Desc = str5;
        this.Rank = num;
    }

    public Canal(){}

    public int compareTo(Canal canal) {
        return this.Rank.intValue() < canal.Rank.intValue() ? 1 : this.Rank.intValue() > canal.Rank.intValue() ? -1 : 0;
    }

    public String getAno() {
        return this.Ano;
    }

    public String getCategoria() {
        return this.Categoria;
    }

    public String getDesc() {
        return this.Desc;
    }

    public Long getId() {
        return this.Id;
    }

    public String getImagem() {
        return this.Imagem;
    }

    public String getNome() {
        return this.Nome;
    }

    public Integer getRank() {
        return Rank;
    }

    public Boolean getStatus() {
        return this.Status;
    }

    public void setAno(String str) {
        this.Ano = str;
    }

    public void setCategoria(String str) {
        this.Categoria = str;
    }

    public void setDesc(String str) {
        this.Desc = str;
    }

    public void setId(Long l) {
        this.Id = l;
    }

    public void setImagem(String str) {
        this.Imagem = str;
    }

    public void setNome(String str) {
        this.Nome = str;
    }

    public void setStatus(Boolean bool) {
        this.Status = bool;
    }
}
