package com.bcsaa;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bcsaa.model.ClassRoutineDeatilseResponse;
import com.bcsaa.model.ClassRoutineDetailseInfo;
import com.bcsaa.model.ClassRoutineResponse;
import com.bcsaa.model.RoutineData;
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

public class RoutineDetailActivity extends AppCompatActivity {
    Context context;
    private ImageView imgBack;
    private ClassRoutineDeatilseResponse classRoutineDeatilseResponse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine_detail);

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
        getRoutineDetailsList();
    }


    private void getRoutineDetailsList() {

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
        Call<ClassRoutineDeatilseResponse> userCall = api.participant_class_routine_detail_view(AppConstant.grouprandomRutine);
        userCall.enqueue(new Callback<ClassRoutineDeatilseResponse>() {
            @Override
            public void onResponse(Call<ClassRoutineDeatilseResponse> call, Response<ClassRoutineDeatilseResponse> response) {
                pd.dismiss();

                classRoutineDeatilseResponse =response.body();

                if(classRoutineDeatilseResponse.getData()!= null){
                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
                    MyListAdapter adapter = new MyListAdapter(classRoutineDeatilseResponse.getData());
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    recyclerView.setAdapter(adapter);
                }


            }

            @Override
            public void onFailure(Call<ClassRoutineDeatilseResponse> call, Throwable t) {
                pd.dismiss();
            }
        });

    }


    public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder>{
        private List<ClassRoutineDetailseInfo> listdata = new ArrayList<>();

        // RecyclerView recyclerView;
        public MyListAdapter(List<ClassRoutineDetailseInfo> listdata) {
            this.listdata = listdata;
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View listItem= layoutInflater.inflate(R.layout.item_class_routine_details, parent, false);
            ViewHolder viewHolder = new ViewHolder(listItem);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final ClassRoutineDetailseInfo myListData = listdata.get(position);

            holder.tvBuildingName.setText(myListData.getBuilding_name());
            holder.tvModule.setText(myListData.getModule_name());
            holder.tvSession.setText(myListData.getSession_name());
            holder.tvTime.setText(myListData.getTime());
            holder.tvTrainerName.setText(myListData.getTrainer_name());
            holder.tvVenueName.setText(myListData.getVenue_name());

        }


        @Override
        public int getItemCount() {
            return listdata.size();
        }

        public  class ViewHolder extends RecyclerView.ViewHolder {
            public TextView tvTime,tvModule,tvSession,tvTrainerName,tvBuildingName,tvVenueName;
           // public LinearLayout linView;
            public ViewHolder(View itemView) {
                super(itemView);
                this.tvTime = (TextView) itemView.findViewById(R.id.tvTime);
                this.tvModule = (TextView) itemView.findViewById(R.id.tvModule);
                this.tvSession = (TextView) itemView.findViewById(R.id.tvSession);
                this.tvTrainerName = (TextView) itemView.findViewById(R.id.tvTrainerName);
                this.tvBuildingName = (TextView) itemView.findViewById(R.id.tvBuildingName);
                this.tvVenueName = (TextView) itemView.findViewById(R.id.tvVenueName);
            }
        }
    }
}