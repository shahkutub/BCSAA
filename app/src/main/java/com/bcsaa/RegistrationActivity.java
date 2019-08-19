package com.bcsaa;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bcsaa.model.LoginResponse;
import com.bcsaa.utils.AlertMessage;
import com.bcsaa.utils.Api;
import com.bcsaa.utils.AppConstant;
import com.bcsaa.utils.NetInfo;
import com.bcsaa.utils.PersistentUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistrationActivity extends Activity {

    Context context;
    private EditText etName,etMobile,etEmail,etPass,etConfirmPass;
    private AppCompatButton btnReg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        context = this;

        initUi();
    }

    private void initUi() {
        etName = (EditText)findViewById(R.id.etName);
        etMobile = (EditText)findViewById(R.id.etMobile);
        etEmail = (EditText)findViewById(R.id.etEmail);
        etPass = (EditText)findViewById(R.id.etPass);
        etConfirmPass = (EditText)findViewById(R.id.etConfirmPass);

        btnReg = (AppCompatButton)findViewById(R.id.btnReg);

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String mobile = etMobile.getText().toString();
                String email = etEmail.getText().toString();
                String pass = etPass.getText().toString();
                String conpass = etConfirmPass.getText().toString();

                if(TextUtils.isEmpty(name)){
                    Toast.makeText(context, "Input name.", Toast.LENGTH_SHORT).show();
                    etName.requestFocus();
                }
                else if(TextUtils.isEmpty(mobile)){
                    Toast.makeText(context, "Input mobile number.", Toast.LENGTH_SHORT).show();
                    etMobile.requestFocus();
                }
                else if(TextUtils.isEmpty(email)){
                    Toast.makeText(context, "Input email address.", Toast.LENGTH_SHORT).show();
                    etEmail.requestFocus();
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    Toast.makeText(context, "Input valid email address.", Toast.LENGTH_SHORT).show();
                    etEmail.requestFocus();
                }
                else if(TextUtils.isEmpty(pass)){
                    Toast.makeText(context, "Input password.", Toast.LENGTH_SHORT).show();
                    etPass.requestFocus();
                }
                else if(TextUtils.isEmpty(conpass)){
                    Toast.makeText(context, "Input confirm password.", Toast.LENGTH_SHORT).show();
                    etConfirmPass.requestFocus();
                }
                else if(!pass.equalsIgnoreCase(conpass)){
                    Toast.makeText(context, "Confirm password doesn't match with password.", Toast.LENGTH_SHORT).show();
                    etConfirmPass.requestFocus();
                }
                else {

                    postRegister(name,mobile,email,pass);
                }

            }
        });
    }


    private void postRegister(String name, String mobile,String email, String password) {

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
        Call<LoginResponse> userCall = api.register(name,mobile,email,password);
        userCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                pd.dismiss();

                LoginResponse  loginrepons =response.body();

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
