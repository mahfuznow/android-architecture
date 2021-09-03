package com.mahfuznow.androidarchitecture.model;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/*
To work with Retrofit we basically need the following three classes:

* A model class which is used as a JSON model
* An interface that defines the HTTP operations needs to be performed
* Retrofit.Builder class:Instance which
    uses the interface defined above and the Builder API to allow
    defining the URL endpoint for the HTTP operations.
    It also takes the converters we provide to format the Response.
*/

public class CountryService implements CountryAPI{

    //public static final String BASE_URL = "https://restcountries.eu/rest/v2/";
    public static final String BASE_URL = "https://mahfuznow.com/api/countries/";

    CountryAPI api;

    public CountryService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
        api = retrofit.create(CountryAPI.class);
    }

    // As we are implementing the CountryAPI interface here, we need to define its functions.
    @Override
    public Single<List<Country>> getCountries() {
        return api.getCountries();
    }
}
