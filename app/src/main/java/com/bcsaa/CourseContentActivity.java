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

import com.bcsaa.model.ClassRoutineResponse;
import com.bcsaa.model.CourseContentData;
import com.bcsaa.model.ParticipantCourseContentResponse;
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

public class CourseContentActivity extends AppCompatActivity {

    Context context;
    private ImageView imgBack;
    private ParticipantCourseContentResponse participantCourseContentResponse = new ParticipantCourseContentResponse();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_content);

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
        participantcoursecontent();
    }


    private void participantcoursecontent() {

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
        Call<ParticipantCourseContentResponse> userCall = api.participant_course_content("{\"logged_session_data\": "+ AppConstant.getLogged_session_data(context)+"}");
        userCall.enqueue(new Callback<ParticipantCourseContentResponse>() {
            @Override
            public void onResponse(Call<ParticipantCourseContentResponse> call, Response<ParticipantCourseContentResponse> response) {
                pd.dismiss();

                participantCourseContentResponse =response.body();

                if(participantCourseContentResponse!=null){
                    if(participantCourseContentResponse.getData()!= null){
                        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
                        MyListAdapter adapter = new MyListAdapter(participantCourseContentResponse.getData());
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(context));
                        recyclerView.setAdapter(adapter);
                    }
                }



            }

            @Override
            public void onFailure(Call<ParticipantCourseContentResponse> call, Throwable t) {
                pd.dismiss();
            }
        });

    }

    public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder>{
        private List<CourseContentData> listdata = new ArrayList<>();

        // RecyclerView recyclerView;
        public MyListAdapter(List<CourseContentData> listdata) {
            this.listdata = listdata;
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View listItem= layoutInflater.inflate(R.layout.item_course_content, parent, false);
            ViewHolder viewHolder = new ViewHolder(listItem);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final CourseContentData myListData = listdata.get(position);

            holder.tvBachNo.setText(myListData.getBatch_no());
            holder.tvCourseName.setText(myListData.getCourse_name());
            holder.linView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   // new Intent(Intent.ACTION_VIEW, Uri.parse(Api.BASE_URL+"api/participant-course-content-pdf/"+myListData.getDetail_id()));

                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Api.BASE_URL+"api/participant-course-content-pdf/"+myListData.getDetail_id()));
                    startActivity(browserIntent);
                }

            });
        }


        @Override
        public int getItemCount() {
            return listdata.size();
        }

        public  class ViewHolder extends RecyclerView.ViewHolder {
            public TextView tvCourseName,tvBachNo;
            public LinearLayout linView;
            public ViewHolder(View itemView) {
                super(itemView);
                this.tvCourseName = (TextView) itemView.findViewById(R.id.tvCourseName);
                this.tvBachNo = (TextView) itemView.findViewById(R.id.tvBachNo);
                linView = (LinearLayout) itemView.findViewById(R.id.linView);
            }
        }
    }
}
