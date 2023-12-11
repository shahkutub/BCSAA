package com.bcsaa;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bcsaa.model.CommonResponse;
import com.bcsaa.model.FeedbackListResponse;
import com.bcsaa.model.FeedbackListResponseItem;
import com.bcsaa.utils.AlertMessage;
import com.bcsaa.utils.Api;
import com.bcsaa.utils.AppConstant;
import com.bcsaa.utils.NetInfo;

import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddFeedbackActivity extends AppCompatActivity {
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_feed_back);
        context = this;
        initUi();
    }

    private void initUi() {
        ImageView imgBack = (ImageView)findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        EditText etSubject = (EditText) findViewById(R.id.etSubject);
        EditText etDescription = (EditText) findViewById(R.id.etDescription);
        AppCompatButton btnSubmitFeedback = (AppCompatButton) findViewById(R.id.btnSubmitFeedback);

        btnSubmitFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String subject = etSubject.getText().toString();
                String description = etDescription.getText().toString();
                if (subject.isEmpty()){
                    Toast.makeText(context, "Enter subject", Toast.LENGTH_SHORT).show();
                }else if (description.isEmpty()){
                    Toast.makeText(context, "Enter description", Toast.LENGTH_SHORT).show();
                }else {
                    addFeedback( subject, description);
                }
            }
        });
    }


    private void addFeedback(String subject,String description) {

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
        Call<FeedbackListResponseItem> userCall = api.participant_feedback_add("{\"logged_session_data\": "+ AppConstant.getLogged_session_data(context)+"}",subject,description);
        userCall.enqueue(new Callback<FeedbackListResponseItem>() {
            @Override
            public void onResponse(Call<FeedbackListResponseItem> call, Response<FeedbackListResponseItem> response) {
                pd.dismiss();
                FeedbackListResponseItem responseapi =response.body();
                Log.e("FeedbackListResponse",""+responseapi);
                finish();
            }

            @Override
            public void onFailure(Call<FeedbackListResponseItem> call, Throwable t) {
                pd.dismiss();
            }
        });

    }
}
