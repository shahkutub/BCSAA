package com.bcsaa;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bcsaa.model.AdminLeaveApprovalInfoResponse;
import com.bcsaa.model.AttendanceStoreResponse;
import com.bcsaa.model.CommonResponse;
import com.bcsaa.model.EmployeeLeaveListResponse;
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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LeaveApprovalActivity extends AppCompatActivity {


    Context context;
    private TextView tvApplicantName,tvDept,tvDesignation,tvLeaveSub,tvLeaveType,tvNumberOfDays,tvLeaveRemain;
    private SearchableSpinner spnApprova,spnDesignation,spnHigherAuth;
    AdminLeaveApprovalInfoResponse adminLeaveApprovalInfoResponse;

    private List<Leave_substitute_post> listLeaveSubstitite = new ArrayList<>();
    private String postId;
    LeaveSubstituteEmployeeResponse leaveSubstituteEmployeeResponse;
    private String employeeId;
    List<SpinnerModel> leaveTypeList = new ArrayList<>();
    private String approveTypeId;
    ImageView imgCancel;
    private LinearLayout linHigherAuthDesignation,linHigherAuth,linReject;
    private AppCompatButton btnSubmitApproval;
    private String reject_reson = "";
    private EditText etRejectReason;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_leave_approval);
        context = this;

        initUi();
    }

    private void initUi() {

        imgCancel = (ImageView)findViewById(R.id.imgCancel);
        imgCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tvApplicantName = (TextView)findViewById(R.id.tvApplicantName);
        tvDept = (TextView)findViewById(R.id.tvDept);
        tvDesignation = (TextView)findViewById(R.id.tvDesignation);
        tvLeaveSub = (TextView)findViewById(R.id.tvLeaveSub);
        tvLeaveType = (TextView)findViewById(R.id.tvLeaveType);
        tvNumberOfDays = (TextView)findViewById(R.id.tvNumberOfDays);
        tvLeaveRemain = (TextView)findViewById(R.id.tvLeaveRemain);

        spnApprova = (SearchableSpinner)findViewById(R.id.spnApprova);
        spnDesignation = (SearchableSpinner)findViewById(R.id.spnDesignation);
        spnHigherAuth = (SearchableSpinner)findViewById(R.id.spnHigherAuth);

        linHigherAuthDesignation = (LinearLayout)findViewById(R.id.linHigherAuthDesignation);
        linHigherAuth = (LinearLayout)findViewById(R.id.linHigherAuth);
        linReject = (LinearLayout)findViewById(R.id.linReject);
        etRejectReason = (EditText)findViewById(R.id.etRejectReason);

        spnDesignation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        spnHigherAuth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                employeeId =  leaveSubstituteEmployeeResponse.getLeave_substitute().get(position).getLeave_substitute_id();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        SpinnerModel spinnerModel = new SpinnerModel();
        spinnerModel.setId("0");
        spinnerModel.setName("Select");
        leaveTypeList.add(spinnerModel);

        SpinnerModel spinnerModel2 = new SpinnerModel();
        spinnerModel2.setId("1");
        spinnerModel2.setName("Approved");
        leaveTypeList.add(spinnerModel2);

        SpinnerModel spinnerModel3 = new SpinnerModel();
        spinnerModel3.setId("2");
        spinnerModel3.setName("Reject");
        leaveTypeList.add(spinnerModel3);

        SpinnerModel spinnerModel4 = new SpinnerModel();
        spinnerModel4.setId("3");
        spinnerModel4.setName("Forward To");
        leaveTypeList.add(spinnerModel4);

        CustomSpinnerAdapter spinneradapter = new CustomSpinnerAdapter(LeaveApprovalActivity.this,
                R.layout.spinner_item, R.id.title, leaveTypeList);
        spnApprova.setAdapter(spinneradapter);
        spnApprova.setTitle("Select Item");
        spnApprova.setPositiveButton("OK");

        spnApprova.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position!=0){
                    approveTypeId = leaveTypeList.get(position).getId();

                    if(approveTypeId.equalsIgnoreCase("3")){
                        linHigherAuthDesignation.setVisibility(View.VISIBLE);
                        linHigherAuth.setVisibility(View.VISIBLE);
                        linReject.setVisibility(View.GONE);
                    }else if(approveTypeId.equalsIgnoreCase("2")){
                        linHigherAuthDesignation.setVisibility(View.GONE);
                        linHigherAuth.setVisibility(View.GONE);
                        linReject.setVisibility(View.VISIBLE);
                    }else {
                        linHigherAuthDesignation.setVisibility(View.GONE);
                        linHigherAuth.setVisibility(View.GONE);
                        linReject.setVisibility(View.GONE);
                    }
                }else {
                    linHigherAuthDesignation.setVisibility(View.GONE);
                    linHigherAuth.setVisibility(View.GONE);
                    linReject.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnSubmitApproval = (AppCompatButton)findViewById(R.id.btnSubmitApproval);
        btnSubmitApproval.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String auth = "{\"logged_session_data\": "+ AppConstant.getLogged_session_data(context)+"}";
                reject_reson = etRejectReason.getText().toString();
                adminLeaveApprovalStore(auth,AppConstant.leaveAction,reject_reson,approveTypeId,employeeId);
            }
        });


        employeeLeaveList();

    }


    private void employeeLeaveList() {

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
        Call<AdminLeaveApprovalInfoResponse> userCall = api.admin_leave_approval_info("{\"logged_session_data\": "+ AppConstant.getLogged_session_data(context)+"}",AppConstant.leaveAction);
        userCall.enqueue(new Callback<AdminLeaveApprovalInfoResponse>() {
            @Override
            public void onResponse(Call<AdminLeaveApprovalInfoResponse> call, Response<AdminLeaveApprovalInfoResponse> response) {
                pd.dismiss();
                adminLeaveApprovalInfoResponse =response.body();
                if(adminLeaveApprovalInfoResponse!=null){

                    if(adminLeaveApprovalInfoResponse.getData()!=null){
                        tvApplicantName.setText(adminLeaveApprovalInfoResponse.getData().getApplicant_name());
                        tvDept.setText(adminLeaveApprovalInfoResponse.getData().getDepartment());
                        tvDesignation.setText(adminLeaveApprovalInfoResponse.getData().getDesignation());
                        tvLeaveRemain.setText(adminLeaveApprovalInfoResponse.getData().getLeave_remaining());
                        tvLeaveSub.setText(adminLeaveApprovalInfoResponse.getData().getLeave_substitute());
                        tvNumberOfDays.setText(adminLeaveApprovalInfoResponse.getData().getNumber_of_days());
                        tvLeaveType.setText(adminLeaveApprovalInfoResponse.getData().getLeave_type());

                        Leave_substitute_post select = new Leave_substitute_post();
                        select.setDesignation_name_en("Select post");

                        listLeaveSubstitite = adminLeaveApprovalInfoResponse.getData().getLeave_substitute_post();
                        listLeaveSubstitite.add(0,select);
                        CustomSpinnerAdapterLeaveSubstitute spinneradapter = new CustomSpinnerAdapterLeaveSubstitute(LeaveApprovalActivity.this,
                                R.layout.spinner_item, R.id.title, listLeaveSubstitite);
                        spnDesignation.setAdapter(spinneradapter);
                        spnDesignation.setTitle("Select Item");
                        spnDesignation.setPositiveButton("OK");
                    }


                }





            }

            @Override
            public void onFailure(Call<AdminLeaveApprovalInfoResponse> call, Throwable t) {
                pd.dismiss();
            }
        });

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

                        CustomSpinnerAdapterEmployee spinneradapter = new CustomSpinnerAdapterEmployee(LeaveApprovalActivity.this,
                                R.layout.spinner_item, R.id.title, leaveSubstituteEmployeeResponse.getLeave_substitute());
                        spnHigherAuth.setAdapter(spinneradapter);
                        spnHigherAuth.setTitle("Select Item");
                        spnHigherAuth.setPositiveButton("OK");

                    }
                }

            }

            @Override
            public void onFailure(Call<LeaveSubstituteEmployeeResponse> call, Throwable t) {
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


    private void adminLeaveApprovalStore(String auth_data,String leave_application_id, String reject_reson, String approval_status, String heigher_auth_id) {

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
        Call<CommonResponse> userCall = api.adminLeaveApprovalStore(auth_data,leave_application_id,reject_reson,approval_status,heigher_auth_id);
        userCall.enqueue(new Callback<CommonResponse>() {
            @Override
            public void onResponse(Call<CommonResponse> call, Response<CommonResponse> response) {
                pd.dismiss();
                CommonResponse   commonResponse =response.body();

                Toast.makeText(context, "submitted", Toast.LENGTH_SHORT).show();
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

}
