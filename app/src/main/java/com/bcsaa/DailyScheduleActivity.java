package com.bcsaa;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;

public class DailyScheduleActivity extends AppCompatActivity {

    Context context;
    private ImageView imgBack;
    private SearchableSpinner spnCourseName,spnBachNo;
    private List<LectureUploadActivity.RowItem> rowItems = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daily_schedule);
        context =  this;

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

        spnCourseName = (SearchableSpinner)findViewById(R.id.spnCourseName);
        spnBachNo = (SearchableSpinner)findViewById(R.id.spnBachNo);

        LectureUploadActivity.RowItem ietmSelectCourse = new LectureUploadActivity.RowItem("Select Course Name");
        LectureUploadActivity.RowItem item1 = new LectureUploadActivity.RowItem("Course one");
        LectureUploadActivity.RowItem item2 = new LectureUploadActivity.RowItem("Course two");
        LectureUploadActivity.RowItem item3 = new LectureUploadActivity.RowItem("Course three");

        rowItems.add(0,ietmSelectCourse);
        rowItems.add(item1);
        rowItems.add(item2);
        rowItems.add(item3);

        LectureUploadActivity.CustomAdapter spinneradapter = new LectureUploadActivity.CustomAdapter(DailyScheduleActivity.this,
                R.layout.spinner_item, R.id.title, rowItems);

        spnCourseName.setAdapter(spinneradapter);
        spnCourseName.setTitle("Select Item");
        spnCourseName.setPositiveButton("OK");

        LectureUploadActivity.CustomAdapter spinneradapterBatch = new LectureUploadActivity.CustomAdapter(DailyScheduleActivity.this,
                R.layout.spinner_item, R.id.title, rowItems);

        spnBachNo.setAdapter(spinneradapterBatch);
        spnBachNo.setTitle("Select Item");
        spnBachNo.setPositiveButton("OK");

    }


}
