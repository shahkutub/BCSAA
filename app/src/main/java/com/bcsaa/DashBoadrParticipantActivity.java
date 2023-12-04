package com.bcsaa;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bcsaa.model.CmtInfo;
import com.bcsaa.model.DailyDchedule;
import com.bcsaa.model.Logged_session_data;
import com.bcsaa.model.ParticipantDashboardRespons;
import com.bcsaa.utils.AlertMessage;
import com.bcsaa.utils.Api;
import com.bcsaa.utils.AppConstant;
import com.bcsaa.utils.NetInfo;
import com.bcsaa.utils.PersistentUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DashBoadrParticipantActivity extends AppCompatActivity{
    Context context;

    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private TextView tvNameCourse,tvNameBach;
            //tvMaleDress,tvFemaleDress;
    ParticipantDashboardRespons prticipantDashboardRespons;
    Logged_session_data logged_session_data ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_participant);
        context = this;

        initDrawer();
        initUi();
    }

    private void initUi() {
        logged_session_data = AppConstant.getLoginUserdat(context);

        TextView tvUserName = (TextView)findViewById(R.id.tvUserName);

        if(AppConstant.getLoginUserdat(context)!=null){
            tvUserName.setText(""+logged_session_data.getName());
        }

        TextView tvDate = (TextView)findViewById(R.id.tvDate);
        tvNameCourse = (TextView)findViewById(R.id.tvNameCourse);
        tvNameBach = (TextView)findViewById(R.id.tvNameBach);
        RelativeLayout relNotifiView = (RelativeLayout)findViewById(R.id.relNotifiView);
        relNotifiView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context,NotificationListActivity.class));
            }
        });
//        tvMaleDress = (TextView)findViewById(R.id.tvMaleDress);
//        tvFemaleDress = (TextView)findViewById(R.id.tvFemaleDress);

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("EEE, d MMM , yy");
        String formattedDate = df.format(c);
        tvDate.setText(formattedDate);
        participant_dashboard();

    }

    private void initDrawer() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    public void onClickClassRoutine(View v){

        startActivity(new Intent(context,ClassRoutineActivity.class));

    }


    public void onClickCourseContent(View v){

        startActivity(new Intent(context,CourseContentActivity.class));

    }

    public void onClickWeeklyAttendance(View v){
        startActivity(new Intent(context,WeeklyAttendanceActivity.class));
    }

    public void onClickRoutine(View v){
        startActivity(new Intent(context,ExamRoutineActivity.class));
    }

     public void onClickSpeakerEvaluation(View v){
            startActivity(new Intent(context,SpekerEvalutionActivity.class));
        }

     public void onClickLeaveApplication(View v){
            startActivity(new Intent(context,ParticipantLeaveApplicationActivity.class));
        }



    public void onClickLogOutKortipokko(View v){
        PersistentUser.logOut(context);
        startActivity(new Intent(context,LoginActivity.class));
        finish();
    }


    private void participant_dashboard() {

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
        Call<ParticipantDashboardRespons> userCall = api.participant_dashboard("{\"logged_session_data\": "+ AppConstant.getLogged_session_data(context)+"}");
        userCall.enqueue(new Callback<ParticipantDashboardRespons>() {
            @Override
            public void onResponse(Call<ParticipantDashboardRespons> call, Response<ParticipantDashboardRespons> response) {
                pd.dismiss();
                prticipantDashboardRespons =response.body();

                if(prticipantDashboardRespons!=null){
                    if(prticipantDashboardRespons.getData()!= null){
                        tvNameCourse.setText(prticipantDashboardRespons.getData().getCourse());
                        tvNameBach.setText(prticipantDashboardRespons.getData().getBatch());
//                        tvMaleDress.setText(prticipantDashboardRespons.getData().getMale_dress());
//                        tvFemaleDress.setText(prticipantDashboardRespons.getData().getFemale_dress());

                        if(prticipantDashboardRespons.getData().getCmt()!=null){
                            RecyclerView recyclerViewCmt = (RecyclerView) findViewById(R.id.recyclerViewCmt);
                            CmtListAdapter adapter = new CmtListAdapter(prticipantDashboardRespons.getData().getCmt());
                            recyclerViewCmt.setHasFixedSize(true);
                            recyclerViewCmt.setLayoutManager(new LinearLayoutManager(context));
                            recyclerViewCmt.setAdapter(adapter);
                        }

//                        if(prticipantDashboardRespons.getData().getDaily_schedule()!=null){
//                            RecyclerView recyclerViewSchedule = (RecyclerView) findViewById(R.id.recyclerViewSchedule);
//                            RoutineListAdapter adapterSchedule = new RoutineListAdapter(prticipantDashboardRespons.getData().getDaily_schedule());
//                            recyclerViewSchedule.setHasFixedSize(true);
//                            recyclerViewSchedule.setLayoutManager(new LinearLayoutManager(context));
//                            recyclerViewSchedule.setAdapter(adapterSchedule);
//                        }

                    }
                }


            }
            @Override
            public void onFailure(Call<ParticipantDashboardRespons> call, Throwable t) {
                pd.dismiss();
            }
        });

    }

    public class CmtListAdapter extends RecyclerView.Adapter<CmtListAdapter.ViewHolder>{
        private List<CmtInfo> listdata = new ArrayList<>();

        // RecyclerView recyclerView;
        public CmtListAdapter(List<CmtInfo> listdata) {
            this.listdata = listdata;
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View listItem= layoutInflater.inflate(R.layout.item_cmt, parent, false);
            ViewHolder viewHolder = new ViewHolder(listItem);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final CmtInfo myListData = listdata.get(position);
            holder.tvteam_role_name.setText(myListData.getTeam_role_name()+" : ");
            holder.tvemployee.setText(myListData.getEmployee());
        }


        @Override
        public int getItemCount() {
            return listdata.size();
        }

        public  class ViewHolder extends RecyclerView.ViewHolder {
            public TextView tvteam_role_name,tvemployee;
            // public LinearLayout linView;
            public ViewHolder(View itemView) {
                super(itemView);
                this.tvteam_role_name = (TextView) itemView.findViewById(R.id.tvteam_role_name);
                this.tvemployee = (TextView) itemView.findViewById(R.id.tvemployee);
            }
        }
    }

    public class RoutineListAdapter extends RecyclerView.Adapter<RoutineListAdapter.ViewHolder>{
        private List<DailyDchedule> listdata = new ArrayList<>();

        // RecyclerView recyclerView;
        public RoutineListAdapter(List<DailyDchedule> listdata) {
            this.listdata = listdata;
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View listItem= layoutInflater.inflate(R.layout.item_schedule, parent, false);
            ViewHolder viewHolder = new ViewHolder(listItem);
            return viewHolder;
        }



        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final DailyDchedule myListData = listdata.get(position);
            holder.tvTime.setText(myListData.getTime());
            holder.tvModule.setText(myListData.getModule_name());
            holder.tvSession.setText(myListData.getSession_name());
            holder.tvTrainer.setText(myListData.getTriner_name());
            holder.tvBuilding.setText(myListData.getBuilding_name());
            holder.tvVenue.setText(myListData.getVenue_name());
        }


        @Override
        public int getItemCount() {
            return listdata.size();
        }

        public  class ViewHolder extends RecyclerView.ViewHolder {
            public TextView tvTime,tvModule,tvSession,tvTrainer,tvBuilding,tvVenue;
            // public LinearLayout linView;
            public ViewHolder(View itemView) {
                super(itemView);
                this.tvTime = (TextView) itemView.findViewById(R.id.tvTime);
                this.tvModule = (TextView) itemView.findViewById(R.id.tvModule);
                this.tvSession = (TextView) itemView.findViewById(R.id.tvSession);
                this.tvTrainer = (TextView) itemView.findViewById(R.id.tvTrainer);
                this.tvBuilding = (TextView) itemView.findViewById(R.id.tvBuilding);
                this.tvVenue = (TextView) itemView.findViewById(R.id.tvVenue);
            }
        }
    }

}
