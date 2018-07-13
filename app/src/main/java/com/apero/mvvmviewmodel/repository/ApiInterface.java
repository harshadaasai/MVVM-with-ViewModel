package com.apero.mvvmviewmodel.repository;

import com.apero.mvvmviewmodel.model.Login_Request;
import com.apero.mvvmviewmodel.model.Login_Response;
import com.apero.mvvmviewmodel.model.News;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

interface ApiInterface {

    @POST("/api/v1/user/signin")
    Call<Login_Response> postLoginResponse(@Body Login_Request login_request);

    @GET("/api/v1/user/followers?page=0")
    Call<JsonObject> getNews();
//    io.reactivex.Observable<JsonObject> getNews();

}
