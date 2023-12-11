package com.bcsaa;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bcsaa.model.LoginResponse;
import com.bcsaa.utils.AlertMessage;
import com.bcsaa.utils.Api;
import com.bcsaa.utils.AppConstant;
import com.bcsaa.utils.NetInfo;
import com.bcsaa.utils.PersistData;
import com.bcsaa.utils.PersistentUser;
import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    Context context;
    private EditText etEmail,etPass;
    private String email,pass;

    private AppCompatButton btnSignin;
    LoginResponse loginrepons = new LoginResponse();

    //moinul35ac@gmai.com
    //as
    //ddpdbcsadminacademy.gov.bd
    //as

    LinearLayout logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_form);

        context = this;
        PersistData.setStringData(context,AppConstant.baseUrl,Api.BASE_URL);
        if(PersistentUser.isLogged(context)){
            if(AppConstant.getLoginUserdat(context).getUsertype()!=null){
                if(AppConstant.getLoginUserdat(context).getUsertype().equalsIgnoreCase("participant")){
                    startActivity(new Intent(context,DashBoadrParticipantActivity.class));
                    //startActivity(new Intent(context,DashboardHRActivity.class));
                    finish();
                }else {
                    startActivity(new Intent(context,DashBoadrParticipantActivity.class));
                    //startActivity(new Intent(context,DashBoardFacultyActivity.class));
                    finish();
                }
            }

        }else {
            initUi();
        }



    }

    private void initUi() {

        logo = (LinearLayout)findViewById(R.id.logo);

        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoginDialog();
            }
        });

        etEmail = (EditText)findViewById(R.id.etEmail);
        etPass = (EditText)findViewById(R.id.etPass);
        btnSignin = (AppCompatButton)findViewById(R.id.btnSignin);

        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //showAuthDialog();
                email = etEmail.getText().toString();
                pass = etPass.getText().toString();
                //startActivity(new Intent(context,DashBoardFacultyActivity.class));
               // startActivity(new Intent(context,DashBoadrParticipantActivity.class));
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(context, "Input email address.", Toast.LENGTH_SHORT).show();
                    etEmail.requestFocus();
                }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    Toast.makeText(context, "Input valid email address.", Toast.LENGTH_SHORT).show();
                    etEmail.requestFocus();
                }else if(TextUtils.isEmpty(pass)){
                    Toast.makeText(context, "Input password.", Toast.LENGTH_SHORT).show();
                    etPass.requestFocus();
                }else {

                    //startActivity(new Intent(context,DashBoardFacultyActivity.class));
                    login(email,pass);
                }


                //startActivity(new Intent(context,DashBoadrParticipantActivity.class));
            }
        });
    }

    private void login(String email,String password) {

        if(!NetInfo.isOnline(context)){
            AlertMessage.showMessage(context,"Alert!","No internet connection!");
        }

        final ProgressDialog pd = new ProgressDialog(context);
        pd.setMessage("Loading....");
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.show();


        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build();

//        Retrofit.Builder retrofit = new Retrofit.Builder()
//                .baseUrl("http://10.0.2.2:3000/")
//                .client(okHttpClient)
//                .addConverterFactory(GsonConverterFactory.create());


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PersistData.getStringData(context,AppConstant.baseUrl))
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<LoginResponse> userCall = api.login(email,password);
        userCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                pd.dismiss();

                loginrepons =response.body();
                Log.e("loginResponse: ", new Gson().toJson(loginrepons));
                if(loginrepons!=null){
                    if(loginrepons.getLogged_session_data()!=null){
                        Toast.makeText(context, ""+loginrepons.getSuccessmsg(), Toast.LENGTH_SHORT).show();

                        if(loginrepons.getLogged_session_data().getUsertype().equalsIgnoreCase("participant")){
                            PersistentUser.setLogin(context);
                            AppConstant.saveLoginUserdat(context,loginrepons.getLogged_session_data());
                            startActivity(new Intent(context,DashBoadrParticipantActivity.class));
                            //startActivity(new Intent(context,DashboardHRActivity.class));
                            finish();

                        }else if (loginrepons.getLogged_session_data().getUsertype().equalsIgnoreCase("1")){
                            PersistentUser.setLogin(context);
                            AppConstant.saveLoginUserdat(context,loginrepons.getLogged_session_data());
                            startActivity(new Intent(context,DashBoardFacultyActivity.class));
                            //startActivity(new Intent(context,DashboardHRActivity.class));
                            finish();
                        }
                    }else {
                        Toast.makeText(context, ""+loginrepons.getErrormsg(), Toast.LENGTH_SHORT).show();
                    }

                    if(loginrepons.getAuthentication_access().equalsIgnoreCase("yes")){
                        if(loginrepons.getAuthentication_info()!=null){
                            showAuthDialog();
                        }
                    }
                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                pd.dismiss();
            }
        });

    }

    private void showLoginDialog()
    {
        LayoutInflater li = LayoutInflater.from(this);
        View prompt = li.inflate(R.layout.login_dialog, null);
        final android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(this);
        alertDialogBuilder.setView(prompt);
        final EditText code = (EditText) prompt.findViewById(R.id.login_name);

        code.setText(PersistData.getStringData(context,AppConstant.baseUrl));


        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {
                        String url = code.getText().toString();
                        if(TextUtils.isEmpty(url)){
                            Toast.makeText(context, "input url", Toast.LENGTH_SHORT).show();
                        }else {
                            PersistData.setStringData(context,AppConstant.baseUrl,url);
                        }

                    }
                });

        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
                dialog.cancel();

            }
        });

        alertDialogBuilder.show();

    }


    private void showAuthDialog()
    {
//        Log.e("show","ok");
//        PersistData.setBooleanData(context,AppConstant.isShow,true);
        LayoutInflater li = LayoutInflater.from(this);
        View prompt = li.inflate(R.layout.auth_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setView(prompt);
        alertDialogBuilder.setCancelable(false);
        final EditText code = (EditText) prompt.findViewById(R.id.login_name);
        //user.setText(Login_USER); //login_USER and PASS are loaded from previous session (optional)
        //pass.setText(Login_PASS);
        alertDialogBuilder.setTitle("Input Authenticate Code");
        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id)
                    {
                        String codrAuth = code.getText().toString();
                        if(TextUtils.isEmpty(codrAuth)){
                            AlertMessage.showMessage(context,"Alert!","Input code from message");
                        }else {
                            postAuthtication("yes",
                                    loginrepons.getAuthentication_info().getEmail(),
                                    loginrepons.getAuthentication_info().getPassword(),
                                    loginrepons.getAuthentication_info().getOldauthenticate_code(),
                                    codrAuth
                            );
                        }

                    }
                });

        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id)
            {
                //PersistData.setBooleanData(context,AppConstant.isShow,false);
                dialog.cancel();

            }
        });

        alertDialogBuilder.show();

    }

    private void postAuthtication(String yes, String email, String password, String oldauthenticate_code, String codrAuth) {

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
        Call<LoginResponse> userCall = api.loginAuth(yes,email,password,oldauthenticate_code,codrAuth);
        userCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                pd.dismiss();

                loginrepons =response.body();

                if(loginrepons!=null){
                    if(loginrepons.getLogged_session_data()!=null){
                        Toast.makeText(context, ""+loginrepons.getSuccessmsg(), Toast.LENGTH_SHORT).show();

                        if(loginrepons.getLogged_session_data().getUsertype().equalsIgnoreCase("participant")){
                            PersistentUser.setLogin(context);
                            AppConstant.saveLoginUserdat(context,loginrepons.getLogged_session_data());
                            startActivity(new Intent(context,DashBoadrParticipantActivity.class));
                            finish();

                        }else {
                            PersistentUser.setLogin(context);
                            AppConstant.saveLoginUserdat(context,loginrepons.getLogged_session_data());
                            startActivity(new Intent(context,DashBoardFacultyActivity.class));
                            finish();
                        }

                    }else {
                        Toast.makeText(context, ""+loginrepons.getErrormsg(), Toast.LENGTH_SHORT).show();
                    }


                    if(loginrepons.getAuthentication_access().equalsIgnoreCase("yes")){
                        if(loginrepons.getAuthentication_info()!=null){
                            showAuthDialog();
                        }
                    }
                }



            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                pd.dismiss();
            }
        });

    }

}
