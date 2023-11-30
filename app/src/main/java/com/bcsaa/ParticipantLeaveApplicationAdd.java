package com.bcsaa;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bcsaa.model.CommonResponse;
import com.bcsaa.model.ParticipantLeaveListResponse;
import com.bcsaa.utils.AlertMessage;
import com.bcsaa.utils.Api;
import com.bcsaa.utils.AppConstant;
import com.bcsaa.utils.NetInfo;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ParticipantLeaveApplicationAdd extends AppCompatActivity{


    Context context;

    private TextView etStartDate,etEndDate,etStartTime,etEndTime;
    private EditText etPurpose;
    private AppCompatButton btnSubmitApplication;
    private ImageView imgBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leave_application_add);
        context = this;

        initUi();
    }

    private void initUi() {

        imgBack = (ImageView) findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        etStartDate = (TextView)findViewById(R.id.etStartDate);
        etEndDate = (TextView)findViewById(R.id.etEndDate);
        etStartTime = (TextView)findViewById(R.id.etStartTime);
        etEndTime = (TextView)findViewById(R.id.etEndTime);
        etPurpose = (EditText) findViewById(R.id.etPurpose);
        btnSubmitApplication = (AppCompatButton) findViewById(R.id.btnSubmitApplication);

        if(AppConstant.perticipantLeaveInfo!=null){

            if(AppConstant.perticipantLeaveInfo.getStart_date()!=null){
                String str =AppConstant.perticipantLeaveInfo.getStart_date();
                String[] arrOfStr = str.split("-", 4);

                etStartDate.setText(arrOfStr[0]+"-"+arrOfStr[1]+"-"+arrOfStr[2]);
                etStartTime.setText(arrOfStr[3]);
            }


            if(AppConstant.perticipantLeaveInfo.getEnd_date()!=null){
                String str1 =AppConstant.perticipantLeaveInfo.getEnd_date();
                String[] arrOfStr1 = str1.split("-", 4);

                etEndDate.setText(arrOfStr1[0]+"-"+arrOfStr1[1]+"-"+arrOfStr1[2]);
                etEndTime.setText(arrOfStr1[3]);
                etPurpose.setText(AppConstant.perticipantLeaveInfo.getPurpose());
            }


        }

        etStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDate(etStartDate);
            }
        });

        etEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDate(etEndDate);
            }
        });

        etStartTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getTime(etStartTime);
                }
            });

        etEndTime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getTime(etEndTime);
                    }
                });

        btnSubmitApplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String startDate= etStartDate.getText().toString();
                String endDate= etEndDate.getText().toString();
                String startTime= etStartTime.getText().toString();
                String endTime= etEndTime.getText().toString();
                String purpose= etPurpose.getText().toString();

                if(TextUtils.isEmpty(startDate)){
                    AlertMessage.showMessage(context,"Alert!","Enter start date.");
                }else if(TextUtils.isEmpty(endDate)){
                    AlertMessage.showMessage(context,"Alert!","Enter end date.");
                }else if(TextUtils.isEmpty(purpose)){
                    AlertMessage.showMessage(context,"Alert!","Enter purpose.");
                }else {

                    if(AppConstant.perticipantLeaveInfo!=null){
                        if (AppConstant.perticipantLeaveInfo.getAction()!=null){
                            leaveApplicationUpdate(AppConstant.perticipantLeaveInfo.getAction(),startDate,endDate,startTime,endTime,purpose);
                        }
                    }else {
                        submit_leaveApplication(startDate,endDate,startTime,endTime,purpose);
                    }

                }


            }
        });


    }

    private void submit_leaveApplication(String startDate, String endDate, String startTime, String endTime, String purpose) {


        if(!NetInfo.isOnline(context)){
            AlertMessage.showMessage(context,"Alert!","No internet connection!");
        }

        final ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("Loading....");
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.show();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<CommonResponse> userCall = api.participant_leave_store("{\"logged_session_data\": "+ AppConstant.getLogged_session_data(context)+"}",
                purpose,startDate,endDate,startTime,endTime);
        userCall.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                pd.dismiss();

                CommonResponse   participantLeaveListResponse =response.body();

                if(participantLeaveListResponse!=null){

                    if(participantLeaveListResponse.getData()!=null){
                        Toast.makeText(context, "Application submitted.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }



            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                pd.dismiss();
            }
        });


    }


    private void leaveApplicationUpdate(String action,String startDate, String endDate, String startTime, String endTime, String purpose) {


        if(!NetInfo.isOnline(context)){
            AlertMessage.showMessage(context,"Alert!","No internet connection!");
        }

        final ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("Loading....");
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.show();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<CommonResponse> userCall = api.participant_leave_update(action,"{\"logged_session_data\": "+ AppConstant.getLogged_session_data(context)+"}",
                purpose,startDate,endDate,startTime,endTime);
        userCall.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                pd.dismiss();

                CommonResponse   participantLeaveListResponse =response.body();

                if(participantLeaveListResponse!=null){

                    if(participantLeaveListResponse.getData()!=null){
                        Toast.makeText(context, "Application Updated.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }



            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                pd.dismiss();
            }
        });


    }



    private void getTime(final TextView etStartTime) {

        final Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
               // etStartTime.setText( selectedHour + ":" + selectedMinute);
                etStartTime.setText( new SimpleDateFormat("hh:mm a").format(mcurrentTime.getTime()));
            }
        }, hour, minute, false);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();

    }

    private void getDate(final TextView etStartDate) {
         Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog StartTime = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                String strDate= formatter.format(newDate.getTime());

                etStartDate.setText(strDate);
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        StartTime.show();
    }




}
