package com.mahfuznow.androidarchitecture.model;

import com.google.gson.annotations.SerializedName;

/*
This is our custom data structure.
In this example we are dealing with country list.
Each country has its name, capital, flag etc.
We can use this Class as a template to represent all these data.

@SerializeName is a annotation from GSON
It is needed if our class variable name is different than the attribute name in JSON
It's not needed if we declare our class variable same as the attribute name in JSON

The JSON response is like ::
 [
   {
    "name": "Afghanistan",
    "capital": "Kabul",
    "flag": "https://restcountries.eu/data/afg.svg"
   },
   {
    "name": "Albania",
    "capital": "Tirana",
    "flag": "https://restcountries.eu/data/alb.svg"
   }
 ]
*/

public class Country {
    @SerializedName("name")
    public String CountryName;

    @SerializedName("capital")
    public String CapitalName;

    @SerializedName("flag")
    public String FlagUrl;
}
