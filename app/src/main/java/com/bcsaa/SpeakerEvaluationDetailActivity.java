package com.bcsaa;

import android.app.ProgressDialog;
import android.content.Context;
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

import com.bcsaa.model.Evaluation;
import com.bcsaa.model.PartiSpeakerEvaluAddResponse;
import com.bcsaa.model.PartiSpeakerEvaluResponse;
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

public class SpeakerEvaluationDetailActivity extends AppCompatActivity {
    Context context;
    private ImageView imgBack;
    private TextView tvCourseName,tvModuleName,tvSpeakerName,tvBachName,tvSessionName;
    private PartiSpeakerEvaluAddResponse partiSpeakerEvaluAddResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speaker_evaluation_detail);

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

        tvCourseName = (TextView)findViewById(R.id.tvCourseName);
        tvModuleName = (TextView)findViewById(R.id.tvModuleName);
        tvSpeakerName = (TextView)findViewById(R.id.tvSpeakerName);
        tvBachName = (TextView)findViewById(R.id.tvBachName);
        tvSessionName = (TextView)findViewById(R.id.tvSessionName);

        participant_exam_schedule();

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
        Call<PartiSpeakerEvaluAddResponse> userCall = api.participant_speaker_evaluation_add("{\"logged_session_data\": "+ AppConstant.getLogged_session_data(context)+"}",AppConstant.sessionId);
        userCall.enqueue(new Callback<PartiSpeakerEvaluAddResponse>() {
            @Override
            public void onResponse(Call<PartiSpeakerEvaluAddResponse> call, Response<PartiSpeakerEvaluAddResponse> response) {
                pd.dismiss();
                partiSpeakerEvaluAddResponse =response.body();

                if(partiSpeakerEvaluAddResponse!=null){
                    if(partiSpeakerEvaluAddResponse.getData()!= null){

                        tvBachName.setText(partiSpeakerEvaluAddResponse.getData().getBatch_no());
                        tvCourseName.setText(partiSpeakerEvaluAddResponse.getData().getCourse_name());
                        tvModuleName.setText(partiSpeakerEvaluAddResponse.getData().getModule_name());
                        tvSessionName.setText(partiSpeakerEvaluAddResponse.getData().getSession_name());
                        tvSpeakerName.setText(partiSpeakerEvaluAddResponse.getData().getSpeaker_name());

                        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
                        MyListAdapter adapter = new MyListAdapter(partiSpeakerEvaluAddResponse.getData().getEvaluation());
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(context));
                        recyclerView.setAdapter(adapter);
                    }
                }



            }

            @Override
            public void onFailure(Call<PartiSpeakerEvaluAddResponse> call, Throwable t) {
                pd.dismiss();
            }
        });

    }

    public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder>{
        private List<Evaluation> listdata = new ArrayList<>();

        // RecyclerView recyclerView;
        public MyListAdapter(List<Evaluation> listdata) {
            this.listdata = listdata;
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View listItem= layoutInflater.inflate(R.layout.iem_speaker_evaluation_details, parent, false);
            ViewHolder viewHolder = new ViewHolder(listItem);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final Evaluation myListData = listdata.get(position);

            holder.tvGetMark.setText(myListData.getTotal_marks());
            holder.tvTotalMark.setText(myListData.getTotal_marks());
            holder.tvParamName.setText(myListData.getParameter_name());

        }


        @Override
        public int getItemCount() {
            return listdata.size();
        }

        public  class ViewHolder extends RecyclerView.ViewHolder {
            public TextView tvParamName,tvTotalMark,tvGetMark;
            public ViewHolder(View itemView) {
                super(itemView);
                this.tvParamName = (TextView) itemView.findViewById(R.id.tvParamName);
                this.tvTotalMark = (TextView) itemView.findViewById(R.id.tvTotalMark);
                this.tvGetMark = (TextView) itemView.findViewById(R.id.tvGetMark);
            }
        }
    }
}