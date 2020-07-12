package com.singularitycoder.coronadashboard.repository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.singularitycoder.coronadashboard.helper.ApiEndPoints;
import com.singularitycoder.coronadashboard.helper.RetrofitService;
import com.singularitycoder.coronadashboard.model.CoronaResponse;

import io.reactivex.Single;

public final class CoronaStatisticsRepository {

    @NonNull
    private static final String TAG = "CoronaStatisticsRepository";

    @Nullable
    private static CoronaStatisticsRepository _instance;

    public CoronaStatisticsRepository() {
    }

    public static CoronaStatisticsRepository getInstance() {
        if (_instance == null) {
            _instance = new CoronaStatisticsRepository();
        }
        return _instance;
    }

    @Nullable
    public Single<CoronaResponse> getCoronaStatisticsFromApi(
            @Nullable final String version,
            @NonNull final String disease,
            @NonNull final String quantity) {
        ApiEndPoints apiService = RetrofitService.getRetrofitInstance().create(ApiEndPoints.class);
        Single<CoronaResponse> observer = apiService.getCoronaStatistics(version, disease, quantity);
        return observer;
    }
}