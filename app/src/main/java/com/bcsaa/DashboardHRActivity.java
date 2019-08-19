package com.bcsaa;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bcsaa.utils.PersistentUser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DashboardHRActivity extends AppCompatActivity {

    Context context;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hr_dashboard);
        context = this;
        initDrawer();

        initUi();
    }

    private void initUi() {

        TextView tvDate = (TextView)findViewById(R.id.tvDate);
        LinearLayout tvOfficer = (LinearLayout) findViewById(R.id.tvOfficer);

        tvOfficer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context,OfficerListActivity.class));
            }
        });

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("EEE, d MMM , yy");
        String formattedDate = df.format(c);
        tvDate.setText(formattedDate);
    }

    private void initDrawer() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    public void onClickClassRoutine(View v){

        startActivity(new Intent(context,ClassRoutineActivity.class));

    }


    public void onClickCourseContent(View v){

        startActivity(new Intent(context,CourseContentActivity.class));

    }


    public void onClickWeeklyAttendance(View v){
        startActivity(new Intent(context,WeeklyAttendanceActivity.class));
    }

    public void onClickRoutine(View v){
        startActivity(new Intent(context,ExamRoutineActivity.class));
    }

    public void onClickSpeakerEvaluation(View v){
        startActivity(new Intent(context,SpekerEvalutionActivity.class));
    }


    public void onClickLeaveApplication(View v){
        startActivity(new Intent(context,ParticipantLeaveApplicationActivity.class));
    }



    public void onClickLogOutKortipokko(View v){
        PersistentUser.logOut(context);
        finish();
    }

}
