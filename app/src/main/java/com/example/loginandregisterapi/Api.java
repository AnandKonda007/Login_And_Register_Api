package com.example.loginandregisterapi;

import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface Api {
    String BASE_URL = "http://liveapi-vmart.softexer.com/api/";

    @POST
    Call<ResponseBody> LoginApi(@Body JsonObject jsonobject, @Url String url);

    @POST
    Call<ResponseBody> RegisterApi(@Body JsonObject jsonobject, @Url String url);

}
