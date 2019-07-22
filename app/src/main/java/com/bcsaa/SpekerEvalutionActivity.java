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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bcsaa.model.PartiSpeakerEvaluResponse;
import com.bcsaa.model.ParticipantExamScheduleRespons;
import com.bcsaa.model.RoutineData;
import com.bcsaa.model.SpeakerEvaluData;
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

public class SpekerEvalutionActivity extends AppCompatActivity {
    Context context;
    private ImageView imgBack;
    private PartiSpeakerEvaluResponse partiSpeakerEvaluResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speaker_evaluation);

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
        participant_exam_schedule();

    }
    public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder>{
        private List<SpeakerEvaluData> listdata = new ArrayList<>();

        // RecyclerView recyclerView;
        public MyListAdapter(List<SpeakerEvaluData> listdata) {
            this.listdata = listdata;
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View listItem= layoutInflater.inflate(R.layout.item_speaker_evaluation, parent, false);
            ViewHolder viewHolder = new ViewHolder(listItem);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final SpeakerEvaluData myListData = listdata.get(position);
            holder.tvCourseName.setText(myListData.getCourse_name());
            holder.tvBach.setText(myListData.getBatch_name());
            holder.tvSession.setText(myListData.getSession_name());
            holder.tvSpeakerType.setText(myListData.getSpeakertype_name());
            holder.tvSpeakerName.setText(myListData.getUser_name());


            holder.linView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppConstant.sessionId = myListData.getSession_id();
                    startActivity(new Intent(context,SpeakerEvaluationDetailActivity.class));
                }
            });
        }


        @Override
        public int getItemCount() {
            return listdata.size();
        }

        public  class ViewHolder extends RecyclerView.ViewHolder {
            public TextView tvCourseName,tvBach,tvSession,tvSpeakerType,tvSpeakerName;
            public LinearLayout linView;
            public ViewHolder(View itemView) {
                super(itemView);
                this.tvCourseName = (TextView) itemView.findViewById(R.id.tvCourseName);
                this.tvBach = (TextView) itemView.findViewById(R.id.tvBach);
                this.tvSession = (TextView) itemView.findViewById(R.id.tvSession);
                this.tvSpeakerType = (TextView) itemView.findViewById(R.id.tvSpeakerType);
                this.tvSpeakerName = (TextView) itemView.findViewById(R.id.tvSpeakerName);
                linView = (LinearLayout) itemView.findViewById(R.id.linView);
            }
        }
    }

    private void participant_exam_schedule() {

        if(!NetInfo.isOnline(context)){
            AlertMessage.showMessage(context,"Alert!","No internet connection!");
        }

        final ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("Loading...");
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<PartiSpeakerEvaluResponse> userCall = api.participant_speaker_evaluation("{\"logged_session_data\": "+ AppConstant.getLogged_session_data(context)+"}");
        userCall.enqueue(new Callback<PartiSpeakerEvaluResponse>() {
            @Override
            public void onResponse(Call<PartiSpeakerEvaluResponse> call, Response<PartiSpeakerEvaluResponse> response) {
                pd.dismiss();
                partiSpeakerEvaluResponse =response.body();
                if(partiSpeakerEvaluResponse.getData()!= null){
                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
                    MyListAdapter adapter = new MyListAdapter(partiSpeakerEvaluResponse.getData());
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    recyclerView.setAdapter(adapter);
                }

            }

            @Override
            public void onFailure(Call<PartiSpeakerEvaluResponse> call, Throwable t) {
                pd.dismiss();
            }
        });

    }
}