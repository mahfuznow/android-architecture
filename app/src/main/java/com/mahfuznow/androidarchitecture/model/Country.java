package com.mahfuznow.androidarchitecture.model;

import com.google.gson.annotations.SerializedName;

public class Country {
    @SerializedName("name")
    public String CountryName;

    @SerializedName("capital")
    public String CapitalName;

    @SerializedName("flag")
    public String FlagUrl;
}
