package com.kimede.viper.Models;

import com.google.gson.annotations.Expose;

public class Categoria {
    @Expose
    private Long Id;
    @Expose
    private String Nome;

    public Categoria(String str) {
        this.Nome = str;
    }

    public Long getId() {
        return this.Id;
    }

    public String getNome() {
        return this.Nome;
    }

    public void setId(Long l) {
        this.Id = l;
    }

    public void setNome(String str) {
        this.Nome = str;
    }
}
