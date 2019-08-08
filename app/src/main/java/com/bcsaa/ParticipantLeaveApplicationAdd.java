package com.bcsaa;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

class ParticipantLeaveApplicationAdd extends AppCompatActivity{


    Context context;

    private TextView etStartDate,etEndDate,etStartTime,etEndTime;
    private EditText etPurpose;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leave_application_add);

        initUi();
    }

    private void initUi() {

        etStartDate = (TextView)findViewById(R.id.etStartDate);
        etEndDate = (TextView)findViewById(R.id.etEndDate);
        etStartTime = (TextView)findViewById(R.id.etStartTime);
        etEndTime = (TextView)findViewById(R.id.etEndTime);
        etPurpose = (EditText) findViewById(R.id.etPurpose);

        etStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDate(etStartDate);
            }
        });

        etEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDate(etEndDate);
            }
        });

        etStartTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    getTime(etStartTime);
                }
            });

        etEndTime.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getTime(etEndTime);
                    }
                });


    }

    private void getTime(final TextView etStartTime) {

        final Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
               // etStartTime.setText( selectedHour + ":" + selectedMinute);
                etStartTime.setText( new SimpleDateFormat("hh:mm a").format(mcurrentTime.getTime()));
            }
        }, hour, minute, false);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();

    }

    private void getDate(final TextView etStartDate) {
         Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog StartTime = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                String strDate= formatter.format(newDate.getTime());

                etStartDate.setText(strDate);
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        StartTime.show();
    }




}
