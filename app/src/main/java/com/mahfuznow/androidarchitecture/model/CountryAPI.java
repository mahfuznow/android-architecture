package com.mahfuznow.androidarchitecture.model;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface CountryAPI {
    @GET("all")
    Single<List<Country>> getCountries();
}
