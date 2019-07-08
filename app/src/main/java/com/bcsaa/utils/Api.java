package com.bcsaa.utils;

import com.bcsaa.model.ClassRoutineDeatilseResponse;
import com.bcsaa.model.ClassRoutineResponse;
import com.bcsaa.model.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {

    String BASE_URL = "http://192.168.0.124/bcsaa/";


    @FormUrlEncoded
    @POST("api/login")
    Call<LoginResponse> login(
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("api/login")
    Call<LoginResponse> loginAuth(
            @Field("authentication_has") String authentication_has,
            @Field("email") String email,
            @Field("password") String password,
            @Field("oldauthenticate_code") String oldauthenticate_code,
            @Field("authentication_code") String authenticate_code
    );


    @FormUrlEncoded
    @POST("api/participant-class-routine-view")
    Call<ClassRoutineResponse> participant_class_routine_view(
            @Field("auth_data") String auth_data
    );


    @FormUrlEncoded
    @POST("api/participant-class-routine-detail-view")
    Call<ClassRoutineDeatilseResponse> participant_class_routine_detail_view(
            @Field("grouprandom") String grouprandom
    );


}
