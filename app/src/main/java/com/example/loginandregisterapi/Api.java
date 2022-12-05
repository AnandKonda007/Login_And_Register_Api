package com.example.loginandregisterapi;

import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Url;

public interface Api {
    String BASE_URL = "http://liveapi-vmart.softexer.com/api/";
    String tokenString = "vmart";

    @POST
    Call<ResponseBody> postMethodApi(@Body JsonObject jsonobject, @Url String url);

    @PUT
    Call<ResponseBody> putMethodApi(@Header(tokenString) String token, @Body JsonObject jsonobject, @Url String url);


}
