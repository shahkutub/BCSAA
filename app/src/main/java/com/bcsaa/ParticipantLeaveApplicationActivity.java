package com.bcsaa;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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

class ParticipantLeaveApplicationActivity extends AppCompatActivity{


    Context context;
    private LinearLayout linAddLeave;
    private ImageView imgBack;
    private RecyclerView recyclerViewParticipantLeaveList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leave_application);
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

        linAddLeave = (LinearLayout)findViewById(R.id.linAddLeave);
        linAddLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConstant.perticipantLeaveInfo = null;
                startActivity(new Intent(context,ParticipantLeaveApplicationAdd.class));
            }
        });

        recyclerViewParticipantLeaveList = (RecyclerView)findViewById(R.id.recyclerViewParticipantLeaveList);
        participantLeaveList();



    }

    @Override
    protected void onResume() {
        super.onResume();
        participantLeaveList();
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
        Call<ParticipantLeaveListResponse> userCall = api.participant_leave_view("{\"logged_session_data\": "+ AppConstant.getLogged_session_data(context)+"}");
        userCall.enqueue(new Callback<ParticipantLeaveListResponse>() {
            @Override
            public void onResponse(Call<ParticipantLeaveListResponse> call, Response<ParticipantLeaveListResponse> response) {
                pd.dismiss();

                ParticipantLeaveListResponse   participantLeaveListResponse =response.body();

                if(participantLeaveListResponse!=null){
                    if(participantLeaveListResponse.getData()!= null){
                        MyListAdapter adapter = new MyListAdapter(participantLeaveListResponse.getData());
                        recyclerViewParticipantLeaveList.setHasFixedSize(true);
                        recyclerViewParticipantLeaveList.setLayoutManager(new LinearLayoutManager(context));
                        recyclerViewParticipantLeaveList.setAdapter(adapter);
                    }
                }



            }

            @Override
            public void onFailure(Call<ParticipantLeaveListResponse> call, Throwable t) {
                pd.dismiss();
            }
        });

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
            View listItem= layoutInflater.inflate(R.layout.item_participant_leave_list, parent, false);
            ViewHolder viewHolder = new ViewHolder(listItem);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final LeaveInfo myListData = listdata.get(position);
            holder.tvSyn.setText(position+1+"");
            holder.tvFormDate.setText(myListData.getStart_date());
            holder.tvToDate.setText(myListData.getEnd_date());
            holder.tvPurpose.setText(myListData.getPurpose());
            holder.tvStatus.setText(myListData.getStatus());

            holder.linView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppConstant.perticipantLeaveInfo = myListData;
                    startActivity(new Intent(context,ParticipantLeaveApplicationAdd.class));
                }
            });


        }


        @Override
        public int getItemCount() {
            return listdata.size();
        }

        public  class ViewHolder extends RecyclerView.ViewHolder {
            public TextView tvSyn,tvFormDate,tvToDate,tvPurpose,tvStatus;
            public LinearLayout linView;
            public ViewHolder(View itemView) {
                super(itemView);
                this.tvSyn = (TextView) itemView.findViewById(R.id.tvSyn);
                this.tvFormDate = (TextView) itemView.findViewById(R.id.tvFormDate);
                this.tvToDate = (TextView) itemView.findViewById(R.id.tvToDate);
                this.tvPurpose = (TextView) itemView.findViewById(R.id.tvPurpose);
                this.tvStatus = (TextView) itemView.findViewById(R.id.tvStatus);
                linView = (LinearLayout) itemView.findViewById(R.id.linView);
            }
        }
    }
}
