package com.kimede.viper.Models;

import com.google.gson.annotations.SerializedName;


import com.google.gson.annotations.Expose;

public class Link {

@SerializedName("Id")
@Expose
private Long id;
@SerializedName("Nome")
@Expose
private String nome;
@SerializedName("Endereco")
@Expose
private String endereco;
@SerializedName("EpisodioEx")
@Expose
private Object episodioEx;

public Long getId() {
return id;
 }

public void setId(Long id) {
this.id = id;
 }

public String getNome() {
return nome;
 }

public void setNome(String nome) {
this.nome = nome;
 }

public String getEndereco() {
return endereco;
 }

public void setEndereco(String endereco) {
this.endereco = endereco;
 }

public Object getEpisodioEx() {
return episodioEx;
 }

public void setEpisodioEx(Object episodioEx) {
this.episodioEx = episodioEx;
 }

}
