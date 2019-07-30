package com.bcsaa.utils;

import com.bcsaa.model.AttendanceStoreResponse;
import com.bcsaa.model.ClassRoutineDeatilseResponse;
import com.bcsaa.model.ClassRoutineResponse;
import com.bcsaa.model.LoginResponse;
import com.bcsaa.model.PartiSpeakerEvaluAddResponse;
import com.bcsaa.model.PartiSpeakerEvaluResponse;
import com.bcsaa.model.ParticipantCourseContentResponse;
import com.bcsaa.model.ParticipantDashboardRespons;
import com.bcsaa.model.ParticipantExamScheduleRespons;
import com.bcsaa.model.ParticipantMealAttendanceRespons;
import com.bcsaa.model.ParticipantWeeklyAttendancePlanViewRespons;

import java.io.File;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {

    //String BASE_URL = "http://192.168.0.124/bcsaa/";
    String BASE_URL = "http://123.49.41.11/";


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


    @FormUrlEncoded
    @POST("api/participant-course-content")
    Call<ParticipantCourseContentResponse> participant_course_content(
            @Field("auth_data") String auth_data
    );


    @FormUrlEncoded
    @POST("api/participant-exam-schedule")
    Call<ParticipantExamScheduleRespons> participant_exam_schedule(
            @Field("auth_data") String auth_data
    );


    @FormUrlEncoded
    @POST("api/dashboard")
    Call<ParticipantDashboardRespons> participant_dashboard(
            @Field("auth_data") String auth_data
    );


    @FormUrlEncoded
    @POST("api/participant-speaker-evaluation")
    Call<PartiSpeakerEvaluResponse> participant_speaker_evaluation(
            @Field("auth_data") String auth_data
    );

    @FormUrlEncoded
    @POST("api/participant-speaker-evaluation-add")
    Call<PartiSpeakerEvaluAddResponse> participant_speaker_evaluation_add(
            @Field("auth_data") String auth_data,
            @Field("session_id") String session_id
    );


    @FormUrlEncoded
    @POST("api/participant-weekly-attendance-plan-view")
    Call<ParticipantWeeklyAttendancePlanViewRespons> participant_weekly_attendance_plan_view(
            @Field("auth_data") String auth_data
    );


    @FormUrlEncoded
    @POST("api/participant-weekly-attendance-plan-add")
    Call<ParticipantWeeklyAttendancePlanViewRespons> participant_weekly_attendance_plan_add(
            @Field("auth_data") String auth_data
    );


    @FormUrlEncoded
    @POST("api/participant-weekly-attendance-plan-get-month-meal")
    Call<ParticipantMealAttendanceRespons> participantweeklyattendanceplangetmonthmeal(
            @Field("auth_data") String auth_data,
            @Field("week") String week
    );


    @FormUrlEncoded
    @POST("api/participant-weekly-attendance-edit")
    Call<ParticipantMealAttendanceRespons> participant_weekly_attendance_edit(
            @Field("auth_data") String auth_data,
            @Field("week") String week
    );



    @FormUrlEncoded
    @POST("api/participant-weekly-attendance-store")
    Call<AttendanceStoreResponse> participantweeklyattendancestore(
            @Field("auth_data") String auth_data,
            @Field("meal_time_id") String meal_time_id,
            @Field("date") String date,
            @Field("attend") String attend,
            @Field("week") String week
    );


}
