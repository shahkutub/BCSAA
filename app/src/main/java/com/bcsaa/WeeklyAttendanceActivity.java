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

import com.bcsaa.model.PartiSpeakerEvaluAddResponse;
import com.bcsaa.model.ParticipantWeeklyAttendancePlanViewData;
import com.bcsaa.model.ParticipantWeeklyAttendancePlanViewRespons;
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

public class WeeklyAttendanceActivity extends AppCompatActivity {

    Context context;
    private ImageView imgBack;
    private LinearLayout linAdd;
    private ParticipantWeeklyAttendancePlanViewRespons participantWeeklyAttendancePlanViewRespons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weekly_attendance);

        context = this;
        initUi();
    }

    private void initUi() {

        imgBack = (ImageView)findViewById(R.id.imgBack);
        linAdd = (LinearLayout) findViewById(R.id.linAdd);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        linAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConstant.activitiName = "Add";
                startActivity(new Intent(context,ParticipantAddWeek.class));
            }
        });
        participantWeeklyAttendancePlanView();

    }
    public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder>{
        private List<ParticipantWeeklyAttendancePlanViewData> listdata = new ArrayList<>();

        // RecyclerView recyclerView;
        public MyListAdapter(List<ParticipantWeeklyAttendancePlanViewData> listdata) {
            this.listdata = listdata;
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View listItem= layoutInflater.inflate(R.layout.item_weekly_attendance, parent, false);
            ViewHolder viewHolder = new ViewHolder(listItem);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final ParticipantWeeklyAttendancePlanViewData myListData = listdata.get(position);

            holder.tvMonthYar.setText(myListData.getMonth_week());
            holder.linView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AppConstant.activitiName = "edit";
                    AppConstant.weekId = myListData.getWeek();
                    startActivity(new Intent(context,ParticipantAddWeek.class));
                }
            });
        }


        @Override
        public int getItemCount() {
            return listdata.size();
        }

        public  class ViewHolder extends RecyclerView.ViewHolder {
            public TextView tvMonthYar;
            public LinearLayout linView;
            public ViewHolder(View itemView) {
                super(itemView);
                this.tvMonthYar = (TextView) itemView.findViewById(R.id.tvMonthYar);
                linView = (LinearLayout) itemView.findViewById(R.id.linView);
            }
        }
    }

    private void participantWeeklyAttendancePlanView() {

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
        Call<ParticipantWeeklyAttendancePlanViewRespons> userCall = api.participant_weekly_attendance_plan_view("{\"logged_session_data\": "+ AppConstant.getLogged_session_data(context)+"}");
        userCall.enqueue(new Callback<ParticipantWeeklyAttendancePlanViewRespons>() {
            @Override
            public void onResponse(Call<ParticipantWeeklyAttendancePlanViewRespons> call, Response<ParticipantWeeklyAttendancePlanViewRespons> response) {
                pd.dismiss();
                participantWeeklyAttendancePlanViewRespons =response.body();
                if(participantWeeklyAttendancePlanViewRespons.getData()!= null){

                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
                    MyListAdapter adapter = new MyListAdapter(participantWeeklyAttendancePlanViewRespons.getData());
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    recyclerView.setAdapter(adapter);
                }

            }

            @Override
            public void onFailure(Call<ParticipantWeeklyAttendancePlanViewRespons> call, Throwable t) {
                pd.dismiss();
            }
        });

    }
}
