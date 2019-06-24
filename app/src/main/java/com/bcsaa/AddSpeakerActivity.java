package com.bcsaa;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;

public class AddSpeakerActivity extends AppCompatActivity {

    Context context;
    private ImageView imgBack;
    private EditText etName,etDesignation,etWorkingPlace,etMobile,etPhone,etEmail,etPass,etConPass;
    private String nameType,name,designation,gender,workingPlace,mobile,phone,email,password,confirmpass;
//
//    private Button btnSignin;

    private SearchableSpinner spinnerNameType,spinnerGender;
    private List<String> listNameType = new ArrayList<>();
    private List<String> listGender = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form_add_speaker);

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

        spinnerNameType = (SearchableSpinner)findViewById(R.id.spinnerNameType);
        listNameType.add("Select Type");
        listNameType.add("Dr.");
        listNameType.add("Mr.");
        listNameType.add("Mrs.");
        listNameType.add("Miss.");
        ArrayAdapter<String> adpterType = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, listNameType);
        spinnerNameType.setAdapter(adpterType);
        spinnerNameType.setPositiveButton("Ok");
        spinnerNameType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0){
                    nameType = spinnerNameType.getSelectedItem().toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerGender = (SearchableSpinner)findViewById(R.id.spinnerGender);
        listGender.add("Select Gender");
        listGender.add("Male");
        listGender.add("Female");
        ArrayAdapter<String> adapterGender= new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, listGender);
        spinnerGender.setAdapter(adapterGender);
        spinnerGender.setPositiveButton("Ok");
        spinnerGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0){
                    gender = spinnerGender.getSelectedItem().toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        etName = (EditText)findViewById(R.id.etName);
        etDesignation = (EditText)findViewById(R.id.etDesignation);
        etWorkingPlace = (EditText)findViewById(R.id.etWorkingPlace);
        etMobile = (EditText)findViewById(R.id.etMobile);
        etPhone = (EditText)findViewById(R.id.etPhone);
        etEmail = (EditText)findViewById(R.id.etEmail);
        etPass = (EditText)findViewById(R.id.etPass);
        etConPass = (EditText)findViewById(R.id.etConPass);

        Button btnSubmit = (Button)findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name =etName.getText().toString();
                designation = etDesignation.getText().toString();
                workingPlace = etWorkingPlace.getText().toString();
                mobile = etMobile.getText().toString();
                phone = etPhone.getText().toString();
                email = etEmail.getText().toString();
                password = etPass.getText().toString();
                confirmpass = etConPass.getText().toString();

                if(TextUtils.isEmpty(nameType)){
                    Toast.makeText(context, "Select name type.", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(name)){
                    Toast.makeText(context, "Input name.", Toast.LENGTH_SHORT).show();
                    etName.requestFocus();
                }else if(TextUtils.isEmpty(gender)){
                    Toast.makeText(context, "Select gender.", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(designation)){
                    Toast.makeText(context, "Input designation.", Toast.LENGTH_SHORT).show();
                    etDesignation.requestFocus();
                }else if(TextUtils.isEmpty(workingPlace)){
                    Toast.makeText(context, "Input working place.", Toast.LENGTH_SHORT).show();
                    etWorkingPlace.requestFocus();
                }else if(TextUtils.isEmpty(mobile)){
                    Toast.makeText(context, "Input mobile number.", Toast.LENGTH_SHORT).show();
                    etMobile.requestFocus();
                }else if(TextUtils.isEmpty(phone)){
                    Toast.makeText(context, "Input office phone number.", Toast.LENGTH_SHORT).show();
                    etPhone.requestFocus();
                }else if(TextUtils.isEmpty(email)){
                    Toast.makeText(context, "Input email address.", Toast.LENGTH_SHORT).show();
                    etEmail.requestFocus();
                }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    Toast.makeText(context, "Input valid email address.", Toast.LENGTH_SHORT).show();
                    etEmail.requestFocus();
                }else if(TextUtils.isEmpty(password)){
                    Toast.makeText(context, "Input password.", Toast.LENGTH_SHORT).show();
                    etPass.requestFocus();
                }else if(TextUtils.isEmpty(confirmpass)){
                    Toast.makeText(context, "Input confirm password.", Toast.LENGTH_SHORT).show();
                    etPass.requestFocus();
                }else if(!password.equalsIgnoreCase(confirmpass)){
                    Toast.makeText(context, "Password & confirm password doesn't match.", Toast.LENGTH_SHORT).show();
                    etPass.requestFocus();
                }

            }
        });

    }
}
