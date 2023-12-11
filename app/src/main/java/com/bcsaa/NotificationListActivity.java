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
import android.widget.TextView;

import com.bcsaa.model.EmplyeeInfo;
import com.bcsaa.model.NotificationResponse;
import com.bcsaa.model.NotificationResponseItem;
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

public class NotificationListActivity extends AppCompatActivity {

    Context context;

    private RecyclerView recyclerViewNotification;
    private ImageView imgBack;
    private ImageView imgContent;
    private TextView tvActivityName;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_activity);
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
        imgContent = (ImageView)findViewById(R.id.imgContent);

        recyclerViewNotification = (RecyclerView)findViewById(R.id.recyclerViewNotification);
        List<EmplyeeInfo> list = new ArrayList<>();
        EmplyeeInfo info = new EmplyeeInfo();
        info.setContact("123456789");
        info.setDepartment("HR");
        info.setEmail("a@mail.com");
        info.setName("Mr.Alom");
        info.setPost("HR Officer");
        info.setTime_period("12-12-18 present");
        list.add(info);

        EmplyeeInfo info1 = new EmplyeeInfo();
        info1.setContact("123456789");
        info1.setDepartment("HR");
        info1.setEmail("a@mail.com");
        info1.setName("Mr.Alom");
        info1.setPost("HR Officer");
        info1.setTime_period("12-12-18 present");
        list.add(info1);


//        MyListAdapter adapter = new MyListAdapter(list);
//        recyclerViewNotification.setHasFixedSize(true);
//        recyclerViewNotification.setLayoutManager(new LinearLayoutManager(context));
//        recyclerViewNotification.setAdapter(adapter);
        getParticipant_notification();
    }

    private void getParticipant_notification() {

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
        Call<NotificationResponse> userCall = api.participant_notifications("{\"logged_session_data\": "+ AppConstant.getLogged_session_data(context)+"}");
        userCall.enqueue(new Callback<NotificationResponse>() {
            @Override
            public void onResponse(Call<NotificationResponse> call, Response<NotificationResponse> response) {
                pd.dismiss();
                NotificationResponse  prticipantNotificatiton =response.body();

                if(prticipantNotificatiton!=null){
                    if(prticipantNotificatiton.size()>0 ){
                        MyListAdapter adapter = new MyListAdapter(prticipantNotificatiton);
                        recyclerViewNotification.setHasFixedSize(true);
                        recyclerViewNotification.setLayoutManager(new LinearLayoutManager(context));
                        recyclerViewNotification.setAdapter(adapter);
                        }
                    }
            }
            @Override
            public void onFailure(Call<NotificationResponse> call, Throwable t) {
                pd.dismiss();
            }
        });

    }
    private class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder>{
        private List<NotificationResponseItem> listdata = new ArrayList<>();

        // RecyclerView recyclerView;
        public MyListAdapter(List<NotificationResponseItem> listdata) {
            this.listdata = listdata;
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View listItem= layoutInflater.inflate(R.layout.notification_item, parent, false);
            ViewHolder viewHolder = new ViewHolder(listItem);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final NotificationResponseItem myListData = listdata.get(position);

            holder.tvTitle.setText(myListData.getData().getMessage());
            holder.tvContent.setText(""+myListData.getData().getUser_name());

        }


        @Override
        public int getItemCount() {
            return listdata.size();
        }

        public  class ViewHolder extends RecyclerView.ViewHolder {
            public TextView tvTitle,tvContent;
            // public LinearLayout linView;
            public ViewHolder(View itemView) {
                super(itemView);
                this.tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
                this.tvContent = (TextView) itemView.findViewById(R.id.tvContent);

            }
        }
    }
}
