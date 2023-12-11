package com.bcsaa;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bcsaa.model.ClassRoutineInfo;
import com.bcsaa.model.ClassRoutineResponse;
import com.bcsaa.model.FeedbackListResponse;
import com.bcsaa.model.FeedbackListResponseItem;
import com.bcsaa.utils.AlertMessage;
import com.bcsaa.utils.Api;
import com.bcsaa.utils.AppConstant;
import com.bcsaa.utils.NetInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FeedbackListActivity extends AppCompatActivity {

    Context context;
    private ImageView imgBack;
    FeedbackListResponse classRoutineResponse = new FeedbackListResponse();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_list);

        context = this;
        initUi();
    }

    private void initUi() {

        imgBack = (ImageView)findViewById(R.id.imgBack);
        LinearLayout linAddFeed = (LinearLayout)findViewById(R.id.linAddFeed);
        linAddFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context,AddFeedbackActivity.class));
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Log.e("auth","{\"logged_session_data\": "+AppConstant.getLogged_session_data(context)+"}");
        getFeedbackList();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getFeedbackList();
    }

    private void getFeedbackList() {

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
        Call<FeedbackListResponse> userCall = api.participant_feedback_list("{\"logged_session_data\": "+AppConstant.getLogged_session_data(context)+"}");
        userCall.enqueue(new Callback<FeedbackListResponse>() {
            @Override
            public void onResponse(Call<FeedbackListResponse> call, Response<FeedbackListResponse> response) {
                pd.dismiss();
                classRoutineResponse =response.body();
                Log.e("FeedbackListResponse",""+classRoutineResponse);
                if(classRoutineResponse!=null){
                        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
                        MyListAdapter adapter = new MyListAdapter(classRoutineResponse);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(context));
                        recyclerView.setAdapter(adapter);
                }

            }

            @Override
            public void onFailure(Call<FeedbackListResponse> call, Throwable t) {
                pd.dismiss();
            }
        });

    }

    public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder>{
        private List<FeedbackListResponseItem> listdata = new ArrayList<>();

        // RecyclerView recyclerView;
        public MyListAdapter(List<FeedbackListResponseItem> listdata) {
            this.listdata = listdata;
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View listItem= layoutInflater.inflate(R.layout.item_feedback, parent, false);
            ViewHolder viewHolder = new ViewHolder(listItem);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final FeedbackListResponseItem myListData = listdata.get(position);

            holder.tvSubject.setText(myListData.getSubject());
            holder.tvDescription.setText(myListData.getDescription());
           // holder.tvReply.setText(myListData.getReply().toString());

        }


        @Override
        public int getItemCount() {
            return listdata.size();
        }

        public  class ViewHolder extends RecyclerView.ViewHolder {
            public TextView tvSubject,tvDescription,tvReply;
            public ViewHolder(View itemView) {
                super(itemView);
                this.tvSubject = (TextView) itemView.findViewById(R.id.tvSubject);
                this.tvDescription = (TextView) itemView.findViewById(R.id.tvDescription);
                this.tvReply = (TextView) itemView.findViewById(R.id.tvReply);
            }
        }
    }
}
