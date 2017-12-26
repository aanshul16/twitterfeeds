package com.tetraandroid.finalappexample.http;

import android.util.Base64;
import android.util.Log;

import com.tetraandroid.finalappexample.http.apimodel.Authenticated;
import com.tetraandroid.finalappexample.http.apimodel.ServerResponse;
import com.tetraandroid.finalappexample.tweets.BusProvider;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import dagger.Module;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class Communicator {

    private static final String TAG = "Communicator";
    final static String CONSUMER_KEY = "SWQ49sXSN5A9Udpqyj9mUuPW5";
    final static String CONSUMER_SECRET = "UYGWF3kNue55PbsUhMfNIH5mVGtXEIm3bDMWpuarXo4pkf3WHi";
    final static String TwitterTokenURL = "https://api.twitter.com/oauth2/token/";
    final static String TwitterStreamURL = "https://api.twitter.com/1.1/statuses/";
    private List<ServerResponse> dataArrayList;


    public void loginPost() {
        try {
            String urlApiKey = URLEncoder.encode(CONSUMER_KEY, "UTF-8");
            String urlApiSecret = URLEncoder.encode(CONSUMER_SECRET, "UTF-8");

            // Concatenate the encoded consumer key, a colon character, and the
            // encoded consumer secret
            String combined = urlApiKey + ":" + urlApiSecret;

            // Base64 encode the string
            final String base64Encoded = Base64.encodeToString(combined.getBytes(), Base64.NO_WRAP);

            //Here a logging interceptor is created
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            //The logging interceptor will be added to the http client
        /*OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);*/

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

            httpClient.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request().newBuilder()
                            .addHeader("Authorization", "Basic " + base64Encoded)
                            .addHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8")
                            .build();
                    return chain.proceed(request);
                }
            });
            Retrofit retrofit = new Retrofit.Builder()
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(TwitterTokenURL)
                    .build();

            String text = "grant_type=client_credentials";
            RequestBody body = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded;charset=UTF-8"),
                    text);

            MoreInfoApiService service = retrofit.create(MoreInfoApiService.class);
            Call<Authenticated> call = service.post(body);

            call.enqueue(new Callback<Authenticated>() {
                @Override
                public void onResponse(Call<Authenticated> call, retrofit2.Response<Authenticated> response) {
                    BusProvider.getInstance().post(new AuthenticateEvent(response.body()));
                    Log.e(TAG, "Success");
                }

                @Override
                public void onFailure(Call<Authenticated> call, Throwable t) {
                    BusProvider.getInstance().post(new ErrorEvent(-2, t.getMessage()));
                }
            });
        } catch (UnsupportedEncodingException e) {

        }
    }

    public void loginGet(final String accessToken) {
        //Here a logging interceptor is created
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer " + accessToken)
                        .addHeader("Content-Type", "application/json")
                        .build();
                return chain.proceed(request);
            }
        });

        //The Retrofit builder will have the client attached, in order to get connection logs
        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(TwitterStreamURL)
                .build();

        MoreInfoApiService service = retrofit.create(MoreInfoApiService.class);

        Call<List<ServerResponse>> call = service.get("reliancejio");

        call.enqueue(new Callback<List<ServerResponse>>() {
            @Override
            public void onResponse(Call<List<ServerResponse>> call, retrofit2.Response<List<ServerResponse>> response) {
                dataArrayList = response.body();
                BusProvider.getInstance().post(new ServerEvent(dataArrayList));
            }

            @Override
            public void onFailure(Call<List<ServerResponse>> call, Throwable t) {
                // handle execution failures like no internet connectivity
                BusProvider.getInstance().post(new ErrorEvent(-2, t.getMessage()));
            }
        });
    }

}
