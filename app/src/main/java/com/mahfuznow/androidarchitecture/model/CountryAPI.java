package com.mahfuznow.androidarchitecture.model;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;

/*
    Retrofit needs an API interface where all the Network request methods
    are declared. Like GET,POST,UPDATE etc.
    These method will be implemented into "CountryService.java" class

    Here, @GET("all") represents GET request to "https://restcountries.eu/rest/v2/all"

    We can also pass parameter to the interface functions and can use those parameter value
    in the annotation also as a query parameter.

    A Single is something like an Observable, but instead of emitting a series of values
    it always either emits one value or an error notification.

    In RxJava, Observables are the source which emits items to the Observers.
    An Observable is like a speaker that emits the value.It does some work and emits some values.
    An Observer gets those values by subscribing to it.

*/

public interface CountryAPI {
    @GET("all")
    Single<List<Country>> getCountries();
}
