package com.singularitycoder.coronadashboard;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public final class CoronaStatisticsViewModel extends ViewModel {

    @NonNull
    private static final String TAG = "CoronaStatisticsViewMod";

    @NonNull
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @NonNull
    private CoronaStatisticsRepository coronaStatisticsRepository = CoronaStatisticsRepository.getInstance();

    public final LiveData<RequestStateMediator<Object, UiState, String, String>> getCoronaStatisticsFromRepository(
            @Nullable final String version,
            @NonNull final String disease,
            @NonNull final String quantity,
            @Nullable final ApiIdlingResource idlingResource) throws IllegalArgumentException {

        if (null != idlingResource) idlingResource.setIdleState(false);

        final RequestStateMediator<Object, UiState, String, String> requestStateMediator = new RequestStateMediator<>();
        final MutableLiveData<RequestStateMediator<Object, UiState, String, String>> mutableLiveData = new MutableLiveData<>();

        requestStateMediator.set(null, UiState.LOADING, "Loading...", null);
        mutableLiveData.postValue(requestStateMediator);

        compositeDisposable.add(
                coronaStatisticsRepository.getCoronaStatisticsFromApi(version, disease, quantity)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver() {
                            @Override
                            public void onSuccess(Object o) {
                                Log.d(TAG, "onResponse: resp: " + o);
                                if (null != o) {
                                    requestStateMediator.set(o, UiState.SUCCESS, "Got Data!", "STATISTICS");
                                    mutableLiveData.postValue(requestStateMediator);
                                    if (null != idlingResource) idlingResource.setIdleState(true);
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                requestStateMediator.set(null, UiState.ERROR, e.getMessage(), null);
                                mutableLiveData.postValue(requestStateMediator);
                                if (null != idlingResource) idlingResource.setIdleState(true);
                            }
                        })
        );
        return mutableLiveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }
}
