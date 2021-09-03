package com.mahfuznow.androidarchitecture.mvvm.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mahfuznow.androidarchitecture.model.Country;
import com.mahfuznow.androidarchitecture.model.CountryService;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.observers.DisposableSingleObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;

/*
In MVVM architecture,
This viewModel has no reference to any View. So, It can't call any function in the View to update the UI.
Instead it updates it's local variable (Mutable live data) which can be observed from any View.

Live Data is a data holder class that follows observer pattern. It holds primitives and collection types
and notifies its observers when the data has changed

Thus Views are no longer bound to update it's UI as soon as the data is retrieved from the database.
It can observe the "Live Data" anytime and can get the latest data from it.


DIFFERENCES compared to MVP presenter::
* This class need to extend the "ViewModel" class from androidx.lifecycle
* Removes all view reference
* Mutable data need to be instantiated
* getter method for live data

ADVANTAGES::
 ✔ View can update it's UI according to it's need. (ex: user is watching a video in fullscreen, in the mean time we got
   our data about recommended video, but we might want to update the UI after user minimize the video)
 ✔ Data won't get destroyed if UI recreated. (ex: If we rotate our phone the UI will be recreated. Thus we can lose
   the data. But in MVVM the data kept intact in the viewModel.

*/

public class MVVMViewModel extends ViewModel {

    private final CountryService service;
    private final MutableLiveData<List<Country>> countriesLiveData;
    private final MutableLiveData<Boolean> isErrorLiveData;

    public MVVMViewModel() {
        service = new CountryService();
        //instantiate the mutable live data
        countriesLiveData = new MutableLiveData<>();
        isErrorLiveData = new MutableLiveData<>();
        fetchCountries();
    }

    /*
         Here in the fetchCountries() function,
         we calling getCountries method which returns a Single Observable.
         We are subscribing it on a new thread.
         And we will observe it in Android main thread.
         And we are passing an anonymous abstract class "DisposableSingleObserver" which
         overrides to methods "onSuccess" and "onError"
         In "onSuccess" method we are updating our Live data which can be observed from any view
         In "onError" method we are updating our Live data which can be observed from any view
    */
    private void fetchCountries() {
        service.getCountries()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<Country>>() {
                    @Override
                    public void onSuccess(@NonNull List<Country> countries) {
                        countriesLiveData.setValue(countries);
                        isErrorLiveData.setValue(false);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        isErrorLiveData.setValue(true);
                    }
                });
    }

    public MutableLiveData<List<Country>> getCountriesLiveData() {
        return countriesLiveData;
    }

    public MutableLiveData<Boolean> getIsErrorLiveData() {
        return isErrorLiveData;
    }
}
