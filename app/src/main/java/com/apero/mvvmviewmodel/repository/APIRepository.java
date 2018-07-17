package com.apero.mvvmviewmodel.repository;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.databinding.ObservableInt;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.apero.mvvmviewmodel.Interface.ObserverInterface;
import com.apero.mvvmviewmodel.LoginViewInterface;
import com.apero.mvvmviewmodel.model.Login_Request;
import com.apero.mvvmviewmodel.model.Login_Response;
import com.apero.mvvmviewmodel.model.News;
import com.apero.mvvmviewmodel.viewmodel.ProductViewModel;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.apero.mvvmviewmodel.AppConstants.BASIC_URL;
import static com.apero.mvvmviewmodel.ProductActivity.accesstoken;

public class APIRepository {
    private ApiInterface apiInterface;
    private static APIRepository projectRepository;
    private static final String TAG = "APIRepository";
    public ObserverInterface observerInterface;

    private APIRepository(String access_token) {

        Log.e(TAG, "APIRepository: "+access_token );

        HostSelectionInterceptor hostSelectionInterceptor = new HostSelectionInterceptor();

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(hostSelectionInterceptor)
                .build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASIC_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .callFactory(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        apiInterface = retrofit.create(ApiInterface.class);
    }

     static final class HostSelectionInterceptor implements Interceptor {

        public HostSelectionInterceptor() {
        }

        @Override public okhttp3.Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            Request.Builder requestBuilder = request.newBuilder()
                    .header("Content-Type", "application/json") // <-- this is the important line
                    .header("version", "1")
                    .header("devicetype", "android")
                    .header("timezone","UTC")
                    .header("timesstamp","2017-12-14 11:59:59")
                    .header("os_version","lollipop");

            Log.e(TAG, "intercept: "+accesstoken);
            if(accesstoken != null)
            {
                requestBuilder.header("Authorization","Bearer "+accesstoken);
            }

//            Log.e(TAG, "bearerToken: "+bearerToken);
            request = requestBuilder.build();

            return chain.proceed(request);
        }
    }


    public synchronized static APIRepository getInstance(String access_token) {
        if (projectRepository == null) {
            if (projectRepository == null) {
                projectRepository = new APIRepository(access_token);
            }
        }
        return projectRepository;
    }

    public MutableLiveData<Login_Response> getLoginResponse(Login_Request source, final LoginViewInterface loginViewInterface, final ObservableInt isLoading) {
        final MutableLiveData<Login_Response> data = new MutableLiveData<>();
        apiInterface.postLoginResponse(source).enqueue(new Callback<Login_Response>() {
            @Override
            public void onResponse(Call<Login_Response> call, Response<Login_Response> response) {
                if(response != null)
                {
                    data.setValue(response.body());
                    loginViewInterface.openMainActivity(response.body());
                }
                isLoading.set(View.GONE);
            }

            @Override
            public void onFailure(Call<Login_Response> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;


    }


    public void getNews(final ProductViewModel viewModel) {

        apiInterface.getNews().enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                        Log.e(TAG, "onResponse: "+response.body());
                        Gson gson = new Gson();
                        News news1 = gson.fromJson(response.body().toString(), News.class);
                        viewModel.getObservableNews().setValue(news1);

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e(TAG, "onFailure: ",t);
            }
        });


    }

}
