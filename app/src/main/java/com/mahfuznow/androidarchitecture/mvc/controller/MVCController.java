package com.mahfuznow.androidarchitecture.mvc.controller;

import android.util.Log;

import com.mahfuznow.androidarchitecture.model.Country;
import com.mahfuznow.androidarchitecture.model.CountryService;
import com.mahfuznow.androidarchitecture.mvc.view.MVCActivity;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

/*

In MVC architecture,
The responsibility of controller is to implement the business logic (ex: How to get data)
and the responsibility of view is only to update the UI.

Here, The job of this controller is to call retrofit service in a new thread to retrieve data from remote API.
Once it get the data it passes the data into the view via a public function call (setValues).

Here this controller has direct connection/reference to the view (MVCActivity.java).
Instance of view is being passed while this controller is being initialized.

Application data is updated by the controller and View gets the data.
Since the Model component is separated, it could be tested independently of the UI.

Advantages::
✔ Increases the code testability
✔ Easier to implement new features as it highly supports the separation of concerns.
✔ Unit testing of Model and Controller is possible as they do not extend or use any Android class.

Disadvantages::
✖ Code layers depend on each other even if MVC is applied correctly.

*/

public class MVCController {
    MVCActivity view;
    CountryService service;

    public MVCController(MVCActivity view) {
        this.view = view;
        service = new CountryService();
        fetchCountries();
    }
/*
     Here in the fetchCountries() function,
     we calling getCountries method which returns a Single Observable.
     We are subscribing it on a new thread.
     And we will observe it in Android main thread.
     And we are passing an anonymous abstract class "DisposableSingleObserver" which
     overrides to methods "onSuccess" and "onError"
     In "onSuccess" method we are sending the data to our view "MVCActivity.java"
     In "onError" method we are notifying the view about the error.
*/
    private void fetchCountries() {
        service.getCountries()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<Country>>() {
                    @Override
                    public void onSuccess(@NonNull List<Country> countries) {
                        view.setValues(countries);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        view.onError();
                    }
                });
    }
}
