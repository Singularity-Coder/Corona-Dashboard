package com.singularitycoder.coronadashboard.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.singularitycoder.coronadashboard.helper.ApiIdlingResource;
import com.singularitycoder.coronadashboard.helper.StateMediator;
import com.singularitycoder.coronadashboard.helper.UiState;
import com.singularitycoder.coronadashboard.model.CoronaResponse;
import com.singularitycoder.coronadashboard.repository.CoronaStatisticsRepository;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public final class CoronaStatisticsViewModel extends AndroidViewModel {

    @NonNull
    private static final String TAG = "CoronaStatisticsViewMod";

    @NonNull
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @NonNull
    private CoronaStatisticsRepository coronaStatisticsRepository = CoronaStatisticsRepository.getInstance();

    public CoronaStatisticsViewModel(@NonNull Application application) {
        super(application);
        coronaStatisticsRepository = new CoronaStatisticsRepository(application);
    }

    // ROOM START______________________________________________________________

    public final void insertIntoRoomDbFromRepository(CoronaResponse coronaResponse) {
        coronaStatisticsRepository.insertIntoRoomDb(coronaResponse);
    }

    public final void updateInRoomDbFromRepository(CoronaResponse coronaResponse) {
        coronaStatisticsRepository.updateInRoomDb(coronaResponse);
    }

    public final void deleteFromRoomDbFromRepository(CoronaResponse coronaResponse) {
        coronaStatisticsRepository.deleteFromRoomDb(coronaResponse);
    }

    public final void deleteAllFromRoomDbFromRepository() {
        coronaStatisticsRepository.deleteAllFromRoomDb();
    }

    // ROOM END______________________________________________________________


    public final LiveData<StateMediator<Object, UiState, String, String>> getCoronaStatisticsFromRepository(
            @Nullable final String version,
            @NonNull final String disease,
            @NonNull final String quantity,
            @Nullable final ApiIdlingResource idlingResource) throws IllegalArgumentException {

        if (null != idlingResource) idlingResource.setIdleState(false);

        final StateMediator<Object, UiState, String, String> stateMediator = new StateMediator<>();
        final MutableLiveData<StateMediator<Object, UiState, String, String>> mutableLiveData = new MutableLiveData<>();

        stateMediator.set(null, UiState.LOADING, "Loading...", null);
        mutableLiveData.postValue(stateMediator);

        compositeDisposable.add(
                coronaStatisticsRepository.getCoronaStatisticsFromApi(version, disease, quantity)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver() {
                            @Override
                            public void onSuccess(Object o) {
                                Log.d(TAG, "onResponse: resp: " + o);
                                if (null != o) {
                                    stateMediator.set(o, UiState.SUCCESS, "Got Data!", "STATISTICS");
                                    mutableLiveData.postValue(stateMediator);
                                    if (null != idlingResource) idlingResource.setIdleState(true);
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                stateMediator.set(null, UiState.ERROR, e.getMessage(), null);
                                mutableLiveData.postValue(stateMediator);
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
