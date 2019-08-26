package com.bcsaa;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bcsaa.model.AdminLeaveApplicationCLresponse;
import com.bcsaa.model.AdminleaveapplicationelResponse;
import com.bcsaa.model.CommonResponse;
import com.bcsaa.model.LeaveSubstituteEmployeeResponse;
import com.bcsaa.model.Leave_substitute_employee;
import com.bcsaa.model.Leave_substitute_post;
import com.bcsaa.model.SpinnerModel;
import com.bcsaa.utils.AlertMessage;
import com.bcsaa.utils.Api;
import com.bcsaa.utils.AppConstant;
import com.bcsaa.utils.CustomSpinnerAdapter;
import com.bcsaa.utils.NetInfo;
import com.bcsaa.utils.PersistData;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LeaveApplicatonForm extends AppCompatActivity {

    Context context;
    private SearchableSpinner spnLeaveType,spnLeaveSubtitutePost,spnLeaveSubtituteEmployee;
    private Button btnChooseFile;
    private TextView tvTotalEarnedLeave,tvRemainingLeave,tvStartDate,tvEndDate,tvNumberOfDays,tvTotalMaternityLeave,tvRemainingMaternityLeave;
    private EditText etContact,etPurpose,etAddress;
    private ImageView imgBack;

    List<SpinnerModel> leaveTypeList = new ArrayList<>();
    private String leaveTypeId;
    private LinearLayout linEarnLeave,linMaternityLeave;
    AppCompatButton btnSubmitApplication;
    private AdminleaveapplicationelResponse adminleaveapplicationelResponse;
    private List<Leave_substitute_post> listLeaveSubstitite = new ArrayList<>();
    private String postId;
    LeaveSubstituteEmployeeResponse  leaveSubstituteEmployeeResponse;
    private String employeeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leave_application_form);

        context = this;
        initUi();

    }

    private void initUi() {

        Log.e("userlog",""+"{\"logged_session_data\": "+ AppConstant.getLogged_session_data(context)+"}");
        imgBack = (ImageView)findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnChooseFile = (Button) findViewById(R.id.btnChooseFile);

        linEarnLeave = (LinearLayout)findViewById(R.id.linEarnLeave);
        linMaternityLeave = (LinearLayout)findViewById(R.id.linMaternityLeave);

        tvTotalEarnedLeave = (TextView) findViewById(R.id.tvTotalEarnedLeave);
        tvRemainingLeave = (TextView) findViewById(R.id.tvRemainingLeave);
        tvStartDate = (TextView) findViewById(R.id.tvStartDate);
        tvEndDate = (TextView) findViewById(R.id.tvEndDate);
        tvNumberOfDays = (TextView) findViewById(R.id.tvNumberOfDays);
        tvTotalMaternityLeave = (TextView) findViewById(R.id.tvTotalMaternityLeave);
        tvRemainingMaternityLeave = (TextView) findViewById(R.id.tvRemainingMaternityLeave);
        etContact = (EditText) findViewById(R.id.etContact);
        etPurpose = (EditText) findViewById(R.id.etPurpose);
        etAddress = (EditText) findViewById(R.id.etAddress);

        spnLeaveType = (SearchableSpinner)findViewById(R.id.spnLeaveType);

        SpinnerModel spinnerModel = new SpinnerModel();
        spinnerModel.setId("0");
        spinnerModel.setName("Select Leave Type");
        leaveTypeList.add(spinnerModel);

        SpinnerModel spinnerModel2 = new SpinnerModel();
        spinnerModel2.setId("2");
        spinnerModel2.setName("Earn Leave");
        leaveTypeList.add(spinnerModel2);

        SpinnerModel spinnerModel3 = new SpinnerModel();
        spinnerModel3.setId("4");
        spinnerModel3.setName("Maternity Leave");
        leaveTypeList.add(spinnerModel3);

        SpinnerModel spinnerModel4 = new SpinnerModel();
        spinnerModel4.setId("5");
        spinnerModel4.setName("Medical Leave");
        leaveTypeList.add(spinnerModel4);

        CustomSpinnerAdapter spinneradapter = new CustomSpinnerAdapter(LeaveApplicatonForm.this,
                R.layout.spinner_item, R.id.title, leaveTypeList);
        spnLeaveType.setAdapter(spinneradapter);
        spnLeaveType.setTitle("Select Item");
        spnLeaveType.setPositiveButton("OK");

        spnLeaveType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0){
                    leaveTypeId = leaveTypeList.get(position).getId();

                    if(leaveTypeId.equalsIgnoreCase("2")){
                        linEarnLeave.setVisibility(View.VISIBLE);
                        linMaternityLeave.setVisibility(View.GONE);
                    }else if(leaveTypeId.equalsIgnoreCase("4")){
                        linEarnLeave.setVisibility(View.GONE);
                        linMaternityLeave.setVisibility(View.VISIBLE);
                    }else {
                        linEarnLeave.setVisibility(View.GONE);
                        linMaternityLeave.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spnLeaveSubtitutePost = (SearchableSpinner) findViewById(R.id.spnLeaveSubtitutePost);
        spnLeaveSubtituteEmployee = (SearchableSpinner) findViewById(R.id.spnLeaveSubtituteEmployee);


        spnLeaveSubtitutePost.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position!=0){
                    postId = listLeaveSubstitite.get(position).getAcademic_designation_id();
                    getEmployee("api/admin-get-employee-by-designation/"+postId);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnLeaveSubtituteEmployee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                employeeId =  leaveSubstituteEmployeeResponse.getLeave_substitute().get(position).getLeave_substitute_id();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        

        tvStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDate(tvStartDate);
            }
        });

        tvEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDate(tvEndDate);

            }
        });



        btnSubmitApplication = (AppCompatButton)findViewById(R.id.btnSubmitApplication);
        btnSubmitApplication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String leave_type_id = leaveTypeId;
                String total_earned = ""+adminleaveapplicationelResponse.getData().getEarn_Leave().getLeave_remaining();
                String leave_remaining = tvRemainingLeave.getText().toString();
                String total_days = ""+adminleaveapplicationelResponse.getData().getMaternity().getTotal_days();
                String maternity_remain = tvRemainingMaternityLeave.getText().toString();
                File leave_attachment = null;
                String from_date = tvStartDate.getText().toString();
                String to_date = tvEndDate.getText().toString();
                String number_of_days = tvNumberOfDays.getText().toString();
                String leave_substitute_post = postId;
                String leave_substitute_id = employeeId;
                String emergency_contact = etContact.getText().toString();
                String purpose = etPurpose.getText().toString();
                String leave_address = etAddress.getText().toString();


                submitLeaveApplication(leave_type_id, total_earned, leave_remaining, total_days,
                        maternity_remain, from_date, to_date, number_of_days, leave_substitute_post, leave_substitute_id,
                        emergency_contact,purpose,leave_address);
            }
        });


        participantWeeklyAttendancePlanList();
    }


    private void submitLeaveApplication(String leave_type_id,
                                        String total_earned,
                                        String leave_remaining,
                                        String total_days,
                                        String maternity_remain,
                                        final String from_date,
                                        final String to_date,
                                        String number_of_days,
                                        String leave_substitute_post,
                                        String leave_substitute_id,
                                        String emergency_contact,
                                        String purpose,
                                        String leave_address) {

        if(!NetInfo.isOnline(context)){
            AlertMessage.showMessage(context,"Alert!","No internet connection!");
        }

        final ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("Loading...");
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PersistData.getStringData(context,AppConstant.baseUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<CommonResponse> userCall = api.admin_store_leave_applicationEl("{\"logged_session_data\": "+ AppConstant.getLogged_session_data(context)+"}", leave_type_id, total_earned, leave_remaining, total_days,
                maternity_remain, from_date, to_date, number_of_days, leave_substitute_post, leave_substitute_id,
                emergency_contact,purpose,leave_address);
        userCall.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                pd.dismiss();
                CommonResponse   commonResponse =response.body();

                Toast.makeText(context, "Leave application submitted.", Toast.LENGTH_SHORT).show();
                Log.e("from_date",""+from_date +" ,"+to_date);
                finish();

//                if(commonResponse!=null){
//                    if(commonResponse.getData()!=null){
//                        Toast.makeText(context, commonResponse.getSuccessmsg(), Toast.LENGTH_SHORT).show();
//                        finish();
//                    }else {
//                        Toast.makeText(context, commonResponse.getErrormsg(), Toast.LENGTH_SHORT).show();
//                        finish();
//                    }
//                }


            }

            @Override
            public void onFailure(Call<CommonResponse> call, Throwable t) {
                pd.dismiss();
            }
        });
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

                if(!TextUtils.isEmpty(tvStartDate.getText().toString()) && !TextUtils.isEmpty(tvEndDate.getText().toString())){
                    dateDeference();
                }
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        StartTime.show();



    }

    private void dateDeference() {
        String dateStart = tvStartDate.getText().toString();
        String dateStop = tvEndDate.getText().toString();

        //HH converts hour in 24 hours format (0-23), day calculation
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

        Date d1 = null;
        Date d2 = null;

        try {
            d1 = format.parse(dateStart);
            d2 = format.parse(dateStop);

            //in milliseconds
            long diff = d2.getTime() - d1.getTime();

//            long diffSeconds = diff / 1000 % 60;
//            long diffMinutes = diff / (60 * 1000) % 60;
//            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);

            tvNumberOfDays.setText(""+diffDays+" Days");

            Log.e("total day",diffDays + " days, ");
//            System.out.print(diffHours + " hours, ");
//            System.out.print(diffMinutes + " minutes, ");
//            System.out.print(diffSeconds + " seconds.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void participantWeeklyAttendancePlanList() {

        if(!NetInfo.isOnline(context)){
            AlertMessage.showMessage(context,"Alert!","No internet connection!");
        }

        final ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("Loading...");
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PersistData.getStringData(context,AppConstant.baseUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<AdminleaveapplicationelResponse> userCall = api.admin_leave_application_el("{\"logged_session_data\": "+ AppConstant.getLogged_session_data(context)+"}");
        userCall.enqueue(new Callback<AdminleaveapplicationelResponse>() {
            @Override
            public void onResponse(Call<AdminleaveapplicationelResponse> call, Response<AdminleaveapplicationelResponse> response) {
                pd.dismiss();
                adminleaveapplicationelResponse =response.body();

                if(adminleaveapplicationelResponse!=null){
                    if(adminleaveapplicationelResponse.getData()!= null){

                        tvTotalEarnedLeave.setText(""+adminleaveapplicationelResponse.getData().getEarn_Leave().getTotal_earned());
                        tvRemainingLeave.setText(""+adminleaveapplicationelResponse.getData().getEarn_Leave().getLeave_remaining());

                        tvTotalMaternityLeave.setText(""+adminleaveapplicationelResponse.getData().getMaternity().getTotal_days());
                        tvRemainingMaternityLeave.setText(""+adminleaveapplicationelResponse.getData().getMaternity().getMaternity_remain());

                        Leave_substitute_post select = new Leave_substitute_post();
                        select.setDesignation_name_en("Select post");



                        listLeaveSubstitite = adminleaveapplicationelResponse.getData().getLeave_substitute_post();
                        listLeaveSubstitite.add(0,select);
                        CustomSpinnerAdapterLeaveSubstitute spinneradapter = new CustomSpinnerAdapterLeaveSubstitute(LeaveApplicatonForm.this,
                                R.layout.spinner_item, R.id.title, listLeaveSubstitite);
                        spnLeaveSubtitutePost.setAdapter(spinneradapter);
                        spnLeaveSubtitutePost.setTitle("Select Item");
                        spnLeaveSubtitutePost.setPositiveButton("OK");
                    }
                }

            }

            @Override
            public void onFailure(Call<AdminleaveapplicationelResponse> call, Throwable t) {
                pd.dismiss();
            }
        });

    }


    private class CustomSpinnerAdapterLeaveSubstitute extends ArrayAdapter<Leave_substitute_post> {

        LayoutInflater flater;
        List<Leave_substitute_post> list = new ArrayList<>();

        public CustomSpinnerAdapterLeaveSubstitute(Activity context, int resouceId, int textviewId, List<Leave_substitute_post> list){

            super(context,resouceId,textviewId, list);
            flater = context.getLayoutInflater();
            this.list = list;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            Leave_substitute_post rowItem = getItem(position);

            View rowview = flater.inflate(R.layout.spinner_item,null,true);

            TextView txtTitle = (TextView) rowview.findViewById(R.id.title);

            if(rowItem.getDesignation_name_en()!=null){
                txtTitle.setText(rowItem.getDesignation_name_en().toString());
            }


            return rowview;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                convertView = flater.inflate(R.layout.spinner_item,parent, false);
            }
            Leave_substitute_post rowItem = getItem(position);
            TextView txtTitle = (TextView) convertView.findViewById(R.id.title);
            if(rowItem.getDesignation_name_en()!=null){
                txtTitle.setText(rowItem.getDesignation_name_en().toString());
            }

            return convertView;
        }
    }


    private void getEmployee(String url) {

        if(!NetInfo.isOnline(context)){
            AlertMessage.showMessage(context,"Alert!","No internet connection!");
        }

        final ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("Loading...");
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PersistData.getStringData(context,AppConstant.baseUrl))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<LeaveSubstituteEmployeeResponse> userCall = api.getEmployee(url);
        userCall.enqueue(new Callback<LeaveSubstituteEmployeeResponse>() {
            @Override
            public void onResponse(Call<LeaveSubstituteEmployeeResponse> call, Response<LeaveSubstituteEmployeeResponse> response) {
                pd.dismiss();
                leaveSubstituteEmployeeResponse =response.body();

                if(leaveSubstituteEmployeeResponse!=null){
                    if(leaveSubstituteEmployeeResponse.getLeave_substitute()!= null){

                        CustomSpinnerAdapterEmployee spinneradapter = new CustomSpinnerAdapterEmployee(LeaveApplicatonForm.this,
                                R.layout.spinner_item, R.id.title, leaveSubstituteEmployeeResponse.getLeave_substitute());
                        spnLeaveSubtituteEmployee.setAdapter(spinneradapter);
                        spnLeaveSubtituteEmployee.setTitle("Select Item");
                        spnLeaveSubtituteEmployee.setPositiveButton("OK");

                    }
                }

            }

            @Override
            public void onFailure(Call<LeaveSubstituteEmployeeResponse> call, Throwable t) {
                pd.dismiss();
            }
        });

    }

    private class CustomSpinnerAdapterEmployee extends ArrayAdapter<Leave_substitute_employee> {

        LayoutInflater flater;
        List<Leave_substitute_employee> list = new ArrayList<>();

        public CustomSpinnerAdapterEmployee(Activity context, int resouceId, int textviewId, List<Leave_substitute_employee> list){

            super(context,resouceId,textviewId, list);
            flater = context.getLayoutInflater();
            this.list = list;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            Leave_substitute_employee rowItem = getItem(position);

            View rowview = flater.inflate(R.layout.spinner_item,null,true);

            TextView txtTitle = (TextView) rowview.findViewById(R.id.title);

            if(rowItem.getLeave_substitute_name()!=null){
                txtTitle.setText(rowItem.getLeave_substitute_name().toString());
            }


            return rowview;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                convertView = flater.inflate(R.layout.spinner_item,parent, false);
            }
            Leave_substitute_employee rowItem = getItem(position);
            TextView txtTitle = (TextView) convertView.findViewById(R.id.title);
            if(rowItem.getLeave_substitute_name()!=null){
                txtTitle.setText(rowItem.getLeave_substitute_name().toString());
            }

            return convertView;
        }
    }


}
