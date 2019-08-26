package com.bcsaa;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bcsaa.model.EmployeeLeaveListResponse;
import com.bcsaa.model.EmplyeeLeaveInfo;
import com.bcsaa.utils.AlertMessage;
import com.bcsaa.utils.Api;
import com.bcsaa.utils.AppConstant;
import com.bcsaa.utils.NetInfo;
import com.bcsaa.utils.PersistData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ManageEmployeeLeaveListActivity extends AppCompatActivity {

    Context context;
    private ImageView imgBack;
    private RecyclerView recyclerViewLeaveList;
    EmployeeLeaveListResponse employeeLeaveListResponse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_employee_leave_list);
        context = this;
        initUi();

    }

    private void initUi() {
        imgBack = (ImageView)findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerViewLeaveList = (RecyclerView)findViewById(R.id.recyclerViewLeaveList);
        employeeLeaveList();

    }

    @Override
    protected void onResume() {
        super.onResume();
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
        Call<EmployeeLeaveListResponse> userCall = api.manageEmployeeLeaveList("{\"logged_session_data\": "+ AppConstant.getLogged_session_data(context)+"}");
        userCall.enqueue(new Callback<EmployeeLeaveListResponse>() {
            @Override
            public void onResponse(Call<EmployeeLeaveListResponse> call, Response<EmployeeLeaveListResponse> response) {
                pd.dismiss();
                employeeLeaveListResponse =response.body();

                if(employeeLeaveListResponse!=null){
                    if(employeeLeaveListResponse.getData()!= null){
                        MyListAdapter adapter = new MyListAdapter(employeeLeaveListResponse.getData());
                        recyclerViewLeaveList.setHasFixedSize(true);
                        recyclerViewLeaveList.setLayoutManager(new LinearLayoutManager(context));
                        recyclerViewLeaveList.setAdapter(adapter);
                    }
                }

            }

            @Override
            public void onFailure(Call<EmployeeLeaveListResponse> call, Throwable t) {
                pd.dismiss();
            }
        });

    }

    public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder>{
        private List<EmplyeeLeaveInfo> listdata = new ArrayList<>();

        // RecyclerView recyclerView;
        public MyListAdapter(List<EmplyeeLeaveInfo> listdata) {
            this.listdata = listdata;
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View listItem= layoutInflater.inflate(R.layout.item_manage_employee_leave_list, parent, false);
            ViewHolder viewHolder = new ViewHolder(listItem);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final EmplyeeLeaveInfo myListData = listdata.get(position);

            holder.tvEmployeeName.setText(myListData.getEmployee());
            holder.tvLeaveType.setText(myListData.getLeave_type());
            holder.tvFormDate.setText(myListData.getFrom_date());
            holder.tvToDate.setText(myListData.getTo_date());
            holder.tvNumberOfDays.setText(myListData.getNumber_of_days());
            holder.tvNumberOfDays.setText(myListData.getNumber_of_days());
            holder.tvRemainLeave.setText(myListData.getRemaining_leave());
            holder.tvStatus.setText(myListData.getStatus());
            holder.tvAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(myListData.getStatus().equalsIgnoreCase("pending")){
                        AppConstant.leaveAction = myListData.getAction_id();
                        startActivity(new Intent(context,LeaveApprovalActivity.class));
                    }

                }
            });

        }


        @Override
        public int getItemCount() {
            return listdata.size();
        }

        public  class ViewHolder extends RecyclerView.ViewHolder {
            public TextView tvEmployeeName,tvLeaveType,tvFormDate,tvToDate,tvNumberOfDays,tvRemainLeave,tvStatus,tvAction;
            public ViewHolder(View itemView) {
                super(itemView);
                this.tvEmployeeName = (TextView) itemView.findViewById(R.id.tvEmployeeName);
                this.tvLeaveType = (TextView) itemView.findViewById(R.id.tvLeaveType);
                this.tvFormDate = (TextView) itemView.findViewById(R.id.tvFormDate);
                this.tvToDate = (TextView) itemView.findViewById(R.id.tvToDate);
                this.tvNumberOfDays = (TextView) itemView.findViewById(R.id.tvNumberOfDays);
                this.tvRemainLeave = (TextView) itemView.findViewById(R.id.tvRemainLeave);
                this.tvStatus = (TextView) itemView.findViewById(R.id.tvStatus);
                this.tvAction = (TextView) itemView.findViewById(R.id.tvAction);
            }
        }
    }
}
