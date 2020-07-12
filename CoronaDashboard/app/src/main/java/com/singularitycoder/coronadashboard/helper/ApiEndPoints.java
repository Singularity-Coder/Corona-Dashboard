package com.singularitycoder.coronadashboard.helper;

import com.singularitycoder.coronadashboard.model.CoronaResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiEndPoints {

    @GET("/{ver}/{dis}/{qty}")
    Single<CoronaResponse> getCoronaStatistics(
            @Path(value = "ver", encoded = true) String version,
            @Path(value = "dis", encoded = true) String disease,
            @Path(value = "qty", encoded = true) String quantity
    );
}