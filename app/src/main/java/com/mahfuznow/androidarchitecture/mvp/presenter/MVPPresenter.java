package com.mahfuznow.androidarchitecture.mvp.presenter;


import com.mahfuznow.androidarchitecture.model.Country;
import com.mahfuznow.androidarchitecture.model.CountryService;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

/*

In MVC architecture,
We had a fixed view for a controller. We have defined the Class type in the constructor parameter
[ Ex: public MVCController(MVCActivity view) ]
Thus, we can not pass any other object rather than the MVCActivity class object.
That means Controller and View are strongly connected to each other. and this controller can be used by another view.

In MVP architecture,
We want the ability to pass any view into our Presenter. Thus, one presenter can be used with multiple views.
[ Ex: public MVPPresenter( MyView view) ]

We need to create an interface "MyView" which will be implemented by the views from where we initiate .

DIFFERENCES compared to the MVCController.java class:
    * Declare an Interface with necessary functions
    * Change the type of the view

Advantages::
✔ Presenter doesn't have a direct reference to any particular view
✔ One presenter can be used with multiple views.

Disadvantages::
✖ View has the direct reference to the presenter.
✖ Presenter will call function to update the UI as soon as it gets the data without respecting the UI state.
    This can be problematic (Ex: User is watching a video, in this time presenter call the update
    function [ setValues() ] in te UI. Then the UI will be changed automatically. To prevent this we need to handle
    this situation in the view which is quite difficult to do.
*/

public class MVPPresenter {
    MyView view;
    CountryService service;

    public MVPPresenter(MyView view) {
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
         In "onSuccess" method we are sending the data to our view from which this presenter is initialized
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

    // This is our custom interface which act like a bridge between the presenter and the view
    // Any view can implement this interface to connect with this presenter.
    public interface MyView {
        public void setValues(List<Country> countries);
        public void onError();
    }
}
