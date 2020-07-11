package com.singularitycoder.coronadashboard;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiEndPoints {

    @GET("/{ver}/{dis}/{qty}")
    Single<CoronaStatisticItem> getCoronaStatistics(
            @Path("ver") String version,
            @Path("dis") String disease,
            @Path(value = "qty", encoded = true) String quantity
    );
}