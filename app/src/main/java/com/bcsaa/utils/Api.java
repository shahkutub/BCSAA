package com.bcsaa.utils;

import com.bcsaa.model.AdminLeaveApplicationCLresponse;
import com.bcsaa.model.AdminLeaveApprovalInfoResponse;
import com.bcsaa.model.AdminleaveapplicationelResponse;
import com.bcsaa.model.AttendanceStoreResponse;
import com.bcsaa.model.ClassRoutineDeatilseResponse;
import com.bcsaa.model.ClassRoutineResponse;
import com.bcsaa.model.CommonData;
import com.bcsaa.model.CommonResponse;
import com.bcsaa.model.EmployeeLeaveListResponse;
import com.bcsaa.model.LeaveSubstituteEmployeeResponse;
import com.bcsaa.model.Leave_substitute_post;
import com.bcsaa.model.LoginResponse;
import com.bcsaa.model.PartiSpeakerEvaluAddResponse;
import com.bcsaa.model.PartiSpeakerEvaluResponse;
import com.bcsaa.model.ParticipantCourseContentResponse;
import com.bcsaa.model.ParticipantDashboardRespons;
import com.bcsaa.model.ParticipantExamScheduleRespons;
import com.bcsaa.model.ParticipantLeaveListResponse;
import com.bcsaa.model.ParticipantMealAttendanceRespons;
import com.bcsaa.model.ParticipantWeeklyAttendancePlanViewRespons;

import java.io.File;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface Api {

    String BASE_URL = "http://192.168.0.124/bcsaa/";
    //String BASE_URL = "http://123.49.41.11/";


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
    @POST("api/participant-leave-view")
    Call<ParticipantLeaveListResponse> participant_leave_view(
            @Field("auth_data") String auth_data
    );


    @FormUrlEncoded
    @POST("api/admin-participant-leave-list")
    Call<ParticipantLeaveListResponse> admin_participant_leave_list(
            @Field("auth_data") String auth_data
    );

    @FormUrlEncoded
    @POST("api/admin-participant-leave-approved")
    Call<CommonData> admin_participant_leave_approved(
            @Field("auth_data") String auth_data,
            @Field("action") String action
    );


    @FormUrlEncoded
    @POST("api/admin-participant-leave-rejected")
    Call<CommonData> admin_participant_leave_rejected(
            @Field("auth_data") String auth_data,
            @Field("action") String action
    );

    @FormUrlEncoded
    @POST("api/participant-leave-store")
    Call<CommonResponse> participant_leave_store(
            @Field("auth_data") String auth_data,
            @Field("purpose") String purpose,
            @Field("start_date") String start_date,
            @Field("end_date") String end_date,
            @Field("start_time") String start_time,
            @Field("end_time") String end_time
    );


    @FormUrlEncoded
    @POST("api/participant-leave-update")
    Call<CommonResponse> participant_leave_update(
            @Field("action") String action,
            @Field("auth_data") String auth_data,
            @Field("purpose") String purpose,
            @Field("start_date") String start_date,
            @Field("end_date") String end_date,
            @Field("start_time") String start_time,
            @Field("end_time") String end_time
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
    @POST("api/admin-leave-application-cl")
    Call<AdminLeaveApplicationCLresponse> admin_leave_application_cl(
            @Field("auth_data") String auth_data
    );



    @FormUrlEncoded
    @POST("api/admin-leave-application-el")
    Call<AdminleaveapplicationelResponse> admin_leave_application_el(
            @Field("auth_data") String auth_data
    );


    @FormUrlEncoded
    @POST("api/admin-personal-leave-application-list")
    Call<EmployeeLeaveListResponse> employeeLeaveList(
            @Field("auth_data") String auth_data
    );

    @FormUrlEncoded
    @POST("api/admin-others-leave-application-list")
    Call<EmployeeLeaveListResponse> manageEmployeeLeaveList(
            @Field("auth_data") String auth_data
    );


    @FormUrlEncoded
    @POST("api/admin-leave-approval-info")
    Call<AdminLeaveApprovalInfoResponse> admin_leave_approval_info(
            @Field("auth_data") String auth_data,
            @Field("action_id") String action_id
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


    @FormUrlEncoded
    @POST("api/admin-leave-approval-store")
    Call<CommonResponse> adminLeaveApprovalStore(
            @Field("auth_data") String auth_data,
            @Field("leave_application_id") String leave_application_id,
            @Field("reject_reason") String reject_reason,
            @Field("approval_status") String approval_status,
            @Field("higher_authority_id") String higher_authority_id
    );




    //    "required_data": [
//        "leave_type_id",
//        "leave_remaining",
//        "leave_attachment",
//        "from_date",
//        "to_date",
//        "number_of_days",
//        "leave_substitute_post",
//        "leave_substitute_id",
//        "emergency_contact",
//        "purpose",
//        "leave_address"
//    ]

    @FormUrlEncoded
    @POST("api/admin-store-leave-application-cl")
    Call<CommonResponse> admin_store_leave_application(

            @Field("auth_data") String auth_data,
            @Field("leave_type_id") String leave_type_id,
            @Field("leave_remaining") String leave_remaining,
            @Field("from_date") String from_date,
            @Field("to_date") String to_date,
            @Field("number_of_days") String number_of_days,
            @Field("leave_substitute_post") String leave_substitute_post,
            @Field("leave_substitute_id") String leave_substitute_id,
            @Field("emergency_contact") String emergency_contact,
            @Field("purpose") String purpose,
            @Field("leave_address") String leave_address
    );



    @FormUrlEncoded
    @POST("api/admin-store-leave-application-el")
    Call<CommonResponse> admin_store_leave_applicationEl(
            @Field("auth_data") String auth_data,
            @Field("leave_type_id") String leave_type_id,
            @Field("total_earned") String total_earned,
            @Field("leave_remaining") String leave_remaining,
            @Field("total_days") String total_days,
            @Field("maternity_remain") String maternity_remain,
            @Field("from_date") String from_date,
            @Field("to_date") String to_date,
            @Field("number_of_days") String number_of_days,
            @Field("leave_substitute_post") String leave_substitute_post,
            @Field("leave_substitute_id") String leave_substitute_id,
            @Field("emergency_contact") String emergency_contact,
            @Field("purpose") String purpose,
            @Field("leave_address") String leave_address
    );



    @FormUrlEncoded
    @POST("api/participant-weekly-attendance-store")
    Call<LoginResponse> register(
            @Field("name") String name,
            @Field("mobile") String mobile,
            @Field("email") String email,
            @Field("password") String password);

    @GET
    public Call<LeaveSubstituteEmployeeResponse> getEmployee(
            @Url String url
    );
}
