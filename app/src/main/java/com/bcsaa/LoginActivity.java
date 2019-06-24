package com.bcsaa;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    Context context;
    private EditText etEmail,etPass;
    private String email,pass;

    private Button btnSignin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_form);

        context = this;

        initUi();
    }

    private void initUi() {

        etEmail = (EditText)findViewById(R.id.etEmail);
        etPass = (EditText)findViewById(R.id.etPass);
        btnSignin = (Button)findViewById(R.id.btnSignin);

        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = etEmail.getText().toString();
                pass = etPass.getText().toString();

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
                    if(email.equalsIgnoreCase("rumpa.sikder31@gmail.com")){
                        startActivity(new Intent(context,DashBoadrParticipantActivity.class));
                    }else {
                        startActivity(new Intent(context,DashBoardFacultyActivity.class));
                    }

                }


                //startActivity(new Intent(context,DashBoadrParticipantActivity.class));
            }
        });
    }
}
