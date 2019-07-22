package com.bcsaa;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bcsaa.model.ParticipantCourseContentResponse;
import com.bcsaa.model.ParticipantExamScheduleData;
import com.bcsaa.model.ParticipantExamScheduleRespons;
import com.bcsaa.model.RoutineData;
import com.bcsaa.utils.AlertMessage;
import com.bcsaa.utils.Api;
import com.bcsaa.utils.AppConstant;
import com.bcsaa.utils.NetInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ExamRoutineActivity extends AppCompatActivity {
    Context context;
    private ImageView imgBack;

    ParticipantExamScheduleRespons participantExamScheduleRespons = new ParticipantExamScheduleRespons();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_routine);

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

        LinearLayout linDownload = (LinearLayout)findViewById(R.id.linDownload);
        linDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Api.BASE_URL+"api/participant-exam-schedule-pdf/"+
                        AppConstant.getLoginUserdat(context).getId()));
                startActivity(browserIntent);
            }
        });

        participant_exam_schedule();


    }
    public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder>{
        private List<ParticipantExamScheduleData> listdata = new ArrayList<>();

        // RecyclerView recyclerView;
        public MyListAdapter(List<ParticipantExamScheduleData> listdata) {
            this.listdata = listdata;
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View listItem= layoutInflater.inflate(R.layout.item_exam_routine, parent, false);
            ViewHolder viewHolder = new ViewHolder(listItem);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final ParticipantExamScheduleData data = listdata.get(position);

            holder.tvBuildingName.setText(data.getBuilding_name());
            holder.tvModule.setText(data.getModule_name());
            holder.tvTime.setText(data.getDateandtime());
            holder.tvTrainerName.setText(data.getSpeaker_name());
            holder.tvVenueName.setText(data.getVenue_name());

        }


        @Override
        public int getItemCount() {
            return listdata.size();
        }

        public  class ViewHolder extends RecyclerView.ViewHolder {
            public ImageView imageView;
            public TextView tvTime,tvModule,tvTrainerName,tvBuildingName,tvVenueName;
           // public LinearLayout linView;
            public ViewHolder(View itemView) {
                super(itemView);
//                this.imageView = (ImageView) itemView.findViewById(R.id.imageView);
                this.tvTime = (TextView) itemView.findViewById(R.id.tvTime);
                this.tvModule = (TextView) itemView.findViewById(R.id.tvModule);
                this.tvTrainerName = (TextView) itemView.findViewById(R.id.tvTrainerName);
                this.tvBuildingName = (TextView) itemView.findViewById(R.id.tvBuildingName);
                this.tvVenueName = (TextView) itemView.findViewById(R.id.tvVenueName);
                //linView = (LinearLayout) itemView.findViewById(R.id.linView);
            }
        }
    }


    private void participant_exam_schedule() {

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
        Call<ParticipantExamScheduleRespons> userCall = api.participant_exam_schedule("{\"logged_session_data\": "+ AppConstant.getLogged_session_data(context)+"}");
        userCall.enqueue(new Callback<ParticipantExamScheduleRespons>() {
            @Override
            public void onResponse(Call<ParticipantExamScheduleRespons> call, Response<ParticipantExamScheduleRespons> response) {
                pd.dismiss();

                participantExamScheduleRespons =response.body();

                if(participantExamScheduleRespons.getData()!= null){
                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
                    MyListAdapter adapter = new MyListAdapter(participantExamScheduleRespons.getData());
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    recyclerView.setAdapter(adapter);
                }


            }

            @Override
            public void onFailure(Call<ParticipantExamScheduleRespons> call, Throwable t) {
                pd.dismiss();
            }
        });

    }


}