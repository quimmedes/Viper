package com.kimede.viper.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class Odata {
    @SerializedName("odata.count")
    @Expose
    private String odataCount;
    @SerializedName("odata.metadata")
    @Expose
    private String odataMetadata;
    @SerializedName("odata.nextLink")
    @Expose
    private String odataNextLink;
    @SerializedName("value")
    @Expose
    private List<Canal> value;

    public Odata() {
        this.value = new ArrayList();
    }

    public String getOdataCount() {
        return this.odataCount;
    }

    public String getOdataMetadata() {
        return this.odataMetadata;
    }

    public String getOdataNextLink() {
        return this.odataNextLink;
    }

    public List<Canal> getValue() {
        return this.value;
    }

    public void setOdataCount(String str) {
        this.odataCount = str;
    }

    public void setOdataMetadata(String str) {
        this.odataMetadata = str;
    }

    public void setOdataNextLink(String str) {
        this.odataNextLink = str;
    }

    public void setValue(List<Canal> list) {
        this.value = list;
    }
}
