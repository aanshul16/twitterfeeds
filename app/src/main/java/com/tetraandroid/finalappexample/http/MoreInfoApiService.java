package com.tetraandroid.finalappexample.http;

import com.tetraandroid.finalappexample.http.apimodel.Authenticated;
import com.tetraandroid.finalappexample.http.apimodel.ServerResponse;

import java.util.HashMap;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface MoreInfoApiService {
    @GET("user_timeline.json?")
    Call<List<ServerResponse>> get(@Query("screen_name") String companyName);

    @POST(".")
    Call<Authenticated> post(@Body RequestBody body);
}
