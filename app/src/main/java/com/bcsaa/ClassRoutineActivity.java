package com.bcsaa;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bcsaa.model.ClassRoutineInfo;
import com.bcsaa.model.ClassRoutineResponse;
import com.bcsaa.model.LoginResponse;
import com.bcsaa.model.RoutineData;
import com.bcsaa.utils.AlertMessage;
import com.bcsaa.utils.Api;
import com.bcsaa.utils.AppConstant;
import com.bcsaa.utils.NetInfo;
import com.bcsaa.utils.PersistentUser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClassRoutineActivity extends AppCompatActivity {

    Context context;
    private ImageView imgBack;
    ClassRoutineResponse classRoutineResponse = new ClassRoutineResponse();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_routine);

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

        Log.e("auth","{\"logged_session_data\": "+AppConstant.getLogged_session_data(context)+"}");
        getRoutineList();


    }

    private void getRoutineList() {

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
        Call<ClassRoutineResponse> userCall = api.participant_class_routine_view("{\"logged_session_data\": "+AppConstant.getLogged_session_data(context)+"}");
        userCall.enqueue(new Callback<ClassRoutineResponse>() {
            @Override
            public void onResponse(Call<ClassRoutineResponse> call, Response<ClassRoutineResponse> response) {
                pd.dismiss();

                classRoutineResponse =response.body();

                if(classRoutineResponse!=null){
                    Collections.reverse(classRoutineResponse.getData());
                    if(classRoutineResponse.getData()!= null){
                        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
                        MyListAdapter adapter = new MyListAdapter(classRoutineResponse.getData());
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(context));
                        recyclerView.setAdapter(adapter);
                    }
                }



            }

            @Override
            public void onFailure(Call<ClassRoutineResponse> call, Throwable t) {
                pd.dismiss();
            }
        });

    }

    public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder>{
        private List<ClassRoutineInfo> listdata = new ArrayList<>();

        // RecyclerView recyclerView;
        public MyListAdapter(List<ClassRoutineInfo> listdata) {
            this.listdata = listdata;
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View listItem= layoutInflater.inflate(R.layout.item_class_routine, parent, false);
            ViewHolder viewHolder = new ViewHolder(listItem);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final ClassRoutineInfo myListData = listdata.get(position);

            holder.tvDate.setText(myListData.getDate());
            holder.tvStartTime.setText(myListData.getMin_time());
            holder.tvEndTime.setText(myListData.getMax_time());
            holder.linView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppConstant.routineDate = myListData.getDate();
                    AppConstant.grouprandomRutine = myListData.getGrouprandom();
                    Log.e("grouprandomRutine: ","grouprandomRutine: "+myListData.getGrouprandom());
                    startActivity(new Intent(context,RoutineDetailActivity.class));
                }
            });
        }

        @Override
        public int getItemCount() {
            return listdata.size();
        }
        public  class ViewHolder extends RecyclerView.ViewHolder {
            public TextView tvDate,tvStartTime,tvEndTime;
            public LinearLayout linView;
            public ViewHolder(View itemView) {
                super(itemView);
                this.tvDate = (TextView) itemView.findViewById(R.id.tvDate);
                this.tvStartTime = (TextView) itemView.findViewById(R.id.tvStartTime);
                this.tvEndTime = (TextView) itemView.findViewById(R.id.tvEndTime);
                linView = (LinearLayout) itemView.findViewById(R.id.linView);
            }
        }
    }
}
