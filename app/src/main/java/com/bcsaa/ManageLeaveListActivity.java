package com.bcsaa;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bcsaa.model.CommonData;
import com.bcsaa.model.CommonResponse;
import com.bcsaa.model.LeaveInfo;
import com.bcsaa.model.ParticipantLeaveListResponse;
import com.bcsaa.utils.AlertMessage;
import com.bcsaa.utils.Api;
import com.bcsaa.utils.AppConstant;
import com.bcsaa.utils.NetInfo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

class ManageLeaveListActivity extends AppCompatActivity{
    Context context;
    private ImageView imgBack;
    private RecyclerView recyclerViewManageLeaveList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.manage_leave_list);

        context = this;
        initUi();


    }

    private void participantLeaveList() {

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
        Call<ParticipantLeaveListResponse> userCall = api.admin_participant_leave_list("{\"logged_session_data\": "+ AppConstant.getLogged_session_data(context)+"}");
        userCall.enqueue(new Callback<ParticipantLeaveListResponse>() {
            @Override
            public void onResponse(Call<ParticipantLeaveListResponse> call, Response<ParticipantLeaveListResponse> response) {
                pd.dismiss();

                ParticipantLeaveListResponse   participantLeaveListResponse =response.body();

                if(participantLeaveListResponse!=null){
                    if(participantLeaveListResponse.getData()!= null){
                        MyListAdapter adapter = new MyListAdapter(participantLeaveListResponse.getData());
                        recyclerViewManageLeaveList.setHasFixedSize(true);
                        recyclerViewManageLeaveList.setLayoutManager(new LinearLayoutManager(context));
                        recyclerViewManageLeaveList.setAdapter(adapter);
                    }
                }



            }

            @Override
            public void onFailure(Call<ParticipantLeaveListResponse> call, Throwable t) {
                pd.dismiss();
            }
        });

    }



    private void initUi() {
        imgBack = (ImageView)findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//        List<LeaveInfo> infoList = new ArrayList<>();
//        LeaveInfo info = new LeaveInfo();
//
//        for (int i = 0; i <10 ; i++) {
//            infoList.add(info);
//        }

        recyclerViewManageLeaveList = (RecyclerView)findViewById(R.id.recyclerViewManageLeaveList);


        participantLeaveList();

    }


    public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder>{
        private List<LeaveInfo> listdata = new ArrayList<>();

        // RecyclerView recyclerView;
        public MyListAdapter(List<LeaveInfo> listdata) {
            this.listdata = listdata;
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View listItem= layoutInflater.inflate(R.layout.item_manage_leave_list, parent, false);
            ViewHolder viewHolder = new ViewHolder(listItem);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final LeaveInfo myListData = listdata.get(position);

            holder.tvSyn.setText(position+1+"");
            holder.tvCourseName.setText(myListData.getCourse());
            holder.tvBachNo.setText(myListData.getBatch());
            holder.tvParticipantName.setText(myListData.getName());
            holder.tvFormDate.setText(myListData.getStart_date_time());
            holder.tvToDate.setText(myListData.getEnd_date_time());
            holder.tvPurpose.setText(myListData.getPurpose());
            holder.tvStatus.setText(myListData.getStatus());

            if(myListData.getAction()==null){
                holder.linApprove.setVisibility(View.GONE);
                holder.linReject.setVisibility(View.GONE);
            }else {
                holder.linApprove.setVisibility(View.VISIBLE);
                holder.linReject.setVisibility(View.VISIBLE);
            }

            holder.linApprove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    approveLeave(myListData.getAction());
                }
            });

            holder.linReject.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rejectLeave(myListData.getAction());
                }
            });


        }


        @Override
        public int getItemCount() {
            return listdata.size();
        }

        public  class ViewHolder extends RecyclerView.ViewHolder {
            public TextView tvSyn,tvCourseName,tvBachNo,tvParticipantName,tvFormDate,tvToDate,tvPurpose,tvStatus;
            public LinearLayout linApprove,linReject;
            public ViewHolder(View itemView) {
                super(itemView);
                this.tvSyn = (TextView) itemView.findViewById(R.id.tvSyn);
                this.tvCourseName = (TextView) itemView.findViewById(R.id.tvCourseName);
                this.tvBachNo = (TextView) itemView.findViewById(R.id.tvBachNo);
                this.tvParticipantName = (TextView) itemView.findViewById(R.id.tvParticipantName);
                this.tvFormDate = (TextView) itemView.findViewById(R.id.tvFormDate);
                this.tvToDate = (TextView) itemView.findViewById(R.id.tvToDate);
                this.tvPurpose = (TextView) itemView.findViewById(R.id.tvPurpose);
                this.tvStatus = (TextView) itemView.findViewById(R.id.tvStatus);
                this.linApprove = (LinearLayout) itemView.findViewById(R.id.linApprove);
                this.linReject = (LinearLayout) itemView.findViewById(R.id.linReject);
            }
        }
    }

    private void approveLeave(String action) {

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
        Call<CommonData> userCall = api.admin_participant_leave_approved("{\"logged_session_data\": "+ AppConstant.getLogged_session_data(context)+"}",action);
        userCall.enqueue(new Callback<CommonData>() {
            @Override
            public void onResponse(Call<CommonData> call, Response<CommonData> response) {
                pd.dismiss();

                CommonData   participantLeaveListResponse =response.body();

                if(participantLeaveListResponse!=null){

                    //if(participantLeaveListResponse.getData()!=null){
                    Toast.makeText(context, participantLeaveListResponse.getSuccessmsg(), Toast.LENGTH_SHORT).show();
                    participantLeaveList();
                    //}
                }



            }

            @Override
            public void onFailure(Call<CommonData> call, Throwable t) {
                pd.dismiss();
            }
        });

    }

    private void rejectLeave(String action) {

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
        Call<CommonData> userCall = api.admin_participant_leave_rejected("{\"logged_session_data\": "+ AppConstant.getLogged_session_data(context)+"}",action);
        userCall.enqueue(new Callback<CommonData>() {
            @Override
            public void onResponse(Call<CommonData> call, Response<CommonData> response) {
                pd.dismiss();

                CommonData   participantLeaveListResponse =response.body();

                if(participantLeaveListResponse!=null){

                    //if(participantLeaveListResponse.getData()!=null){
                    Toast.makeText(context, participantLeaveListResponse.getSuccessmsg(), Toast.LENGTH_SHORT).show();
                    participantLeaveList();
                    //}
                }



            }

            @Override
            public void onFailure(Call<CommonData> call, Throwable t) {
                pd.dismiss();
            }
        });

    }
}
