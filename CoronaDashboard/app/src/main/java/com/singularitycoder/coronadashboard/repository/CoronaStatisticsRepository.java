package com.singularitycoder.coronadashboard.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.singularitycoder.coronadashboard.helper.ApiEndPoints;
import com.singularitycoder.coronadashboard.helper.CoronaStatisticsRoomDatabase;
import com.singularitycoder.coronadashboard.helper.RetrofitService;
import com.singularitycoder.coronadashboard.model.CoronaResponse;
import com.singularitycoder.coronadashboard.roomdao.StatisticsDao;

import io.reactivex.Single;

public final class CoronaStatisticsRepository {

    @NonNull
    private static final String TAG = "CoronaStatisticsRepository";

    @Nullable
    private StatisticsDao statisticsDao;

    @Nullable
    private static CoronaStatisticsRepository _instance;

    public CoronaStatisticsRepository() {
    }

    public CoronaStatisticsRepository(Application application) {
        CoronaStatisticsRoomDatabase database = CoronaStatisticsRoomDatabase.getInstance(application);
        statisticsDao = database.statisticsDao();
    }

    public static CoronaStatisticsRepository getInstance() {
        if (_instance == null) {
            _instance = new CoronaStatisticsRepository();
        }
        return _instance;
    }

    // ROOM START______________________________________________________________

    public final void insertIntoRoomDb(CoronaResponse coronaResponse) {
        AsyncTask.SERIAL_EXECUTOR.execute(() -> statisticsDao.insertItem(coronaResponse));
    }

    public final void updateInRoomDb(CoronaResponse coronaResponse) {
        AsyncTask.SERIAL_EXECUTOR.execute(() -> statisticsDao.updateItem(coronaResponse));
    }

    public final void deleteFromRoomDb(CoronaResponse coronaResponse) {
        AsyncTask.SERIAL_EXECUTOR.execute(() -> statisticsDao.deleteItem(coronaResponse));
    }

    public final void deleteAllFromRoomDb() {
        AsyncTask.THREAD_POOL_EXECUTOR.execute(() -> statisticsDao.deleteAllItems());
    }

    // ROOM END______________________________________________________________

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
