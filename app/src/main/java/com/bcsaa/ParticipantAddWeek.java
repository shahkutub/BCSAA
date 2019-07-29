package com.bcsaa;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bcsaa.model.AttendanceStoreResponse;
import com.bcsaa.model.MealAttendanceInfo;
import com.bcsaa.model.MealData;
import com.bcsaa.model.MealTime;
import com.bcsaa.model.ParticipantMealAttendanceRespons;
import com.bcsaa.model.ParticipantWeeklyAttendancePlanViewData;
import com.bcsaa.model.ParticipantWeeklyAttendancePlanViewRespons;
import com.bcsaa.model.SpinnerModel;
import com.bcsaa.model.TableHead;
import com.bcsaa.utils.AlertMessage;
import com.bcsaa.utils.Api;
import com.bcsaa.utils.AppConstant;
import com.bcsaa.utils.CustomSpinnerAdapter;
import com.bcsaa.utils.NetInfo;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ParticipantAddWeek extends AppCompatActivity {

    Context context;
    SearchableSpinner spnWeek;
    ImageView imgBack;

    private ParticipantWeeklyAttendancePlanViewRespons participantWeeklyAttendancePlanViewRespons;
    private ParticipantMealAttendanceRespons participantMealAttendanceRespons;
    List<SpinnerModel> listWeek = new ArrayList<>();
    private String weekId;
    private LinearLayout linMealView,linWeak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_week);
        context =this;
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
        spnWeek = (SearchableSpinner)findViewById(R.id.spnWeek);
        linMealView = (LinearLayout) findViewById(R.id.linMealView);
        linWeak = (LinearLayout) findViewById(R.id.linWeak);

        if(AppConstant.activitiName.equalsIgnoreCase("edit")){
            Log.e("weekId",""+AppConstant.weekId);
            linWeak.setVisibility(View.GONE);
            participant_weekly_attendance_edit(AppConstant.weekId);
            linMealView.setVisibility(View.VISIBLE);
        }

        spnWeek.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0){

                    weekId = listWeek.get(position).getId();
                    Log.e("weekId",""+weekId);
                    participantweeklyattendanceplangetmonthmeal(weekId);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        participantWeeklyAttendancePlanList();
    }

    private void participantWeeklyAttendancePlanList() {

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
        Call<ParticipantWeeklyAttendancePlanViewRespons> userCall = api.participant_weekly_attendance_plan_add("{\"logged_session_data\": "+ AppConstant.getLogged_session_data(context)+"}");
        userCall.enqueue(new Callback<ParticipantWeeklyAttendancePlanViewRespons>() {
            @Override
            public void onResponse(Call<ParticipantWeeklyAttendancePlanViewRespons> call, Response<ParticipantWeeklyAttendancePlanViewRespons> response) {
                pd.dismiss();
                participantWeeklyAttendancePlanViewRespons =response.body();
                if(participantWeeklyAttendancePlanViewRespons.getData()!= null){



                    SpinnerModel select = new SpinnerModel();
                    select.setName("Select week");


                    listWeek.add(0,select);

                    for (ParticipantWeeklyAttendancePlanViewData data:participantWeeklyAttendancePlanViewRespons.getData()) {
                        SpinnerModel spinnerData = new SpinnerModel();
                        spinnerData.setName(data.getWeek_lenth());
                        spinnerData.setId(data.getWeek_id());
                        listWeek.add(spinnerData);

                    }

                    CustomSpinnerAdapter spinneradapter = new CustomSpinnerAdapter(ParticipantAddWeek.this,
                            R.layout.spinner_item, R.id.title, listWeek);
                    spnWeek.setAdapter(spinneradapter);
                    spnWeek.setTitle("Select Item");
                    spnWeek.setPositiveButton("OK");
                }

            }

            @Override
            public void onFailure(Call<ParticipantWeeklyAttendancePlanViewRespons> call, Throwable t) {
                pd.dismiss();
            }
        });

    }


    private void participantweeklyattendanceplangetmonthmeal(String weekId) {

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
        Call<ParticipantMealAttendanceRespons> userCall = api.participantweeklyattendanceplangetmonthmeal("{\"logged_session_data\": "+ AppConstant.getLogged_session_data(context)+"}", weekId);
        userCall.enqueue(new Callback<ParticipantMealAttendanceRespons>() {
            @Override
            public void onResponse(Call<ParticipantMealAttendanceRespons> call, Response<ParticipantMealAttendanceRespons> response) {
                pd.dismiss();
                participantMealAttendanceRespons =response.body();
                if(participantMealAttendanceRespons.getTable_head()!= null){
                    linMealView.setVisibility(View.VISIBLE);

                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewDaysHeader);
                    HeaderListAdapter adapter = new HeaderListAdapter(participantMealAttendanceRespons.getTable_head());
                    recyclerView.setHasFixedSize(true);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);


                }

                if(participantMealAttendanceRespons.getMeal_time()!=null){
                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewMealse);
                    MealNameListAdapter adapter = new MealNameListAdapter(participantMealAttendanceRespons.getMeal_time());
                    recyclerView.setHasFixedSize(true);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);



                    AttenendListAdapter adapterrAttend = new AttenendListAdapter(participantMealAttendanceRespons.getMeal_time());
                    RecyclerView recyclerViewAttend = (RecyclerView) findViewById(R.id.recyclerViewAttend);
                    recyclerViewAttend.setHasFixedSize(true);
                    LinearLayoutManager layoutManagerAttend = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                    recyclerViewAttend.setLayoutManager(layoutManagerAttend);
                    recyclerViewAttend.setAdapter(adapterrAttend);
                }




            }

            @Override
            public void onFailure(Call<ParticipantMealAttendanceRespons> call, Throwable t) {
                pd.dismiss();
            }
        });

    }

    private void participant_weekly_attendance_edit(String weekId) {

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
        Call<ParticipantMealAttendanceRespons> userCall = api.participant_weekly_attendance_edit("{\"logged_session_data\": "+ AppConstant.getLogged_session_data(context)+"}", weekId);
        userCall.enqueue(new Callback<ParticipantMealAttendanceRespons>() {
            @Override
            public void onResponse(Call<ParticipantMealAttendanceRespons> call, Response<ParticipantMealAttendanceRespons> response) {
                pd.dismiss();
                participantMealAttendanceRespons =response.body();
                if(participantMealAttendanceRespons.getTable_head()!= null){
                    linMealView.setVisibility(View.VISIBLE);

                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewDaysHeader);
                    HeaderListAdapter adapter = new HeaderListAdapter(participantMealAttendanceRespons.getTable_head());
                    recyclerView.setHasFixedSize(true);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);


                }

                if(participantMealAttendanceRespons.getMeal_time()!=null){
                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewMealse);
                    MealNameListAdapter adapter = new MealNameListAdapter(participantMealAttendanceRespons.getMeal_time());
                    recyclerView.setHasFixedSize(true);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(layoutManager);
                    recyclerView.setAdapter(adapter);



                    AttenendListAdapter adapterrAttend = new AttenendListAdapter(participantMealAttendanceRespons.getMeal_time());
                    RecyclerView recyclerViewAttend = (RecyclerView) findViewById(R.id.recyclerViewAttend);
                    recyclerViewAttend.setHasFixedSize(true);
                    LinearLayoutManager layoutManagerAttend = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
                    recyclerViewAttend.setLayoutManager(layoutManagerAttend);
                    recyclerViewAttend.setAdapter(adapterrAttend);
                }




            }

            @Override
            public void onFailure(Call<ParticipantMealAttendanceRespons> call, Throwable t) {
                pd.dismiss();
            }
        });

    }



    private void participantweeklyattendancestore(String auth_data,String meal_time_id, String date, String attend, String weekId) {

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
        Call<AttendanceStoreResponse> userCall = api.participantweeklyattendancestore(auth_data,meal_time_id,date,attend,weekId);
        userCall.enqueue(new Callback<AttendanceStoreResponse>() {
            @Override
            public void onResponse(Call<AttendanceStoreResponse> call, Response<AttendanceStoreResponse> response) {
                pd.dismiss();
                AttendanceStoreResponse  attendanceStoreResponse =response.body();
                if(attendanceStoreResponse.getData()!= null){
                    Toast.makeText(context, ""+attendanceStoreResponse.getSuccessmsg(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<AttendanceStoreResponse> call, Throwable t) {
                pd.dismiss();
            }
        });

    }


    public class HeaderListAdapter extends RecyclerView.Adapter<HeaderListAdapter.ViewHolder>{
        private List<TableHead> listdata = new ArrayList<>();

        // RecyclerView recyclerView;
        public HeaderListAdapter(List<TableHead> listdata) {
            this.listdata = listdata;
        }
        @Override
        public HeaderListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View listItem= layoutInflater.inflate(R.layout.item_day_header, parent, false);
            HeaderListAdapter.ViewHolder viewHolder = new HeaderListAdapter.ViewHolder(listItem);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(HeaderListAdapter.ViewHolder holder, int position) {
            final TableHead myListData = listdata.get(position);
            holder.tvHeader.setText(myListData.getDate_name());

        }


        @Override
        public int getItemCount() {
            return listdata.size();
        }

        public  class ViewHolder extends RecyclerView.ViewHolder {
            public TextView tvHeader;
            public ViewHolder(View itemView) {
                super(itemView);
                this.tvHeader = (TextView) itemView.findViewById(R.id.tvHeader);
            }
        }
    }

    public class MealNameListAdapter extends RecyclerView.Adapter<MealNameListAdapter.ViewHolder>{
        private List<MealTime> listdata = new ArrayList<>();

        // RecyclerView recyclerView;
        public MealNameListAdapter(List<MealTime> listdata) {
            this.listdata = listdata;
        }
        @Override
        public MealNameListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View listItem= layoutInflater.inflate(R.layout.item_meal, parent, false);
            MealNameListAdapter.ViewHolder viewHolder = new MealNameListAdapter.ViewHolder(listItem);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(MealNameListAdapter.ViewHolder holder, int position) {
            final MealTime myListData = listdata.get(position);
            holder.tvMealName.setText(myListData.getMeal().getMealtime_name());



        }


        @Override
        public int getItemCount() {
            return listdata.size();
        }

        public  class ViewHolder extends RecyclerView.ViewHolder {
            public TextView tvMealName;
            public ViewHolder(View itemView) {
                super(itemView);
                this.tvMealName = (TextView) itemView.findViewById(R.id.tvMealName);
            }
        }
    }


    public class AttenendListAdapter extends RecyclerView.Adapter<AttenendListAdapter.ViewHolder>{
        private List<MealTime> listdata = new ArrayList<>();

        // RecyclerView recyclerView;
        public AttenendListAdapter(List<MealTime> listdata) {
            this.listdata = listdata;
        }
        @Override
        public AttenendListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View listItem= layoutInflater.inflate(R.layout.item_meal_attendance_list, parent, false);
            AttenendListAdapter.ViewHolder viewHolder = new AttenendListAdapter.ViewHolder(listItem);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(AttenendListAdapter.ViewHolder holder, int position) {
            final MealTime myListData = listdata.get(position);

            AttenendListDataAdapter adapter = new AttenendListDataAdapter(myListData.getMeal().getMealtime_attend());
            holder.recyclerViewAttendData.setHasFixedSize(true);
            LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            holder.recyclerViewAttendData.setLayoutManager(layoutManager);
            holder.recyclerViewAttendData.setAdapter(adapter);

        }


        @Override
        public int getItemCount() {
            return listdata.size();
        }

        public  class ViewHolder extends RecyclerView.ViewHolder {
            public RecyclerView recyclerViewAttendData;
            public ViewHolder(View itemView) {
                super(itemView);
                this.recyclerViewAttendData = (RecyclerView) itemView.findViewById(R.id.recyclerViewAttendData);
            }
        }
    }

    public class AttenendListDataAdapter extends RecyclerView.Adapter<AttenendListDataAdapter.ViewHolder>{
        private List<MealAttendanceInfo> listdata = new ArrayList<>();

        // RecyclerView recyclerView;
        public AttenendListDataAdapter(List<MealAttendanceInfo> listdata) {
            this.listdata = listdata;
        }
        @Override
        public AttenendListDataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View listItem= layoutInflater.inflate(R.layout.item_meal_attendance, parent, false);
            AttenendListDataAdapter.ViewHolder viewHolder = new AttenendListDataAdapter.ViewHolder(listItem);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(AttenendListDataAdapter.ViewHolder holder, int position) {
            final MealAttendanceInfo data = listdata.get(position);

            if(data.getAttend().equalsIgnoreCase("yes")){
                holder.item_switch.setChecked(true);
            }

            holder.item_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    String auth = "{\"logged_session_data\": "+ AppConstant.getLogged_session_data(context)+"}" ;

                    if(isChecked){
                        participantweeklyattendancestore(auth,data.getMeal_time_id(),data.getDate(),"yes",weekId);
                    }else {
                        participantweeklyattendancestore(auth,data.getMeal_time_id(),data.getDate(),"no",weekId);
                    }

                }
            });


        }


        @Override
        public int getItemCount() {
            return listdata.size();
        }

        public  class ViewHolder extends RecyclerView.ViewHolder {
            public CheckBox item_switch;
            public ViewHolder(View itemView) {
                super(itemView);
                this.item_switch = (CheckBox) itemView.findViewById(R.id.item_switch);
            }
        }
    }


}
