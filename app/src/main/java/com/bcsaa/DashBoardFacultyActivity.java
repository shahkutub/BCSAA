package com.bcsaa;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

class DashBoardFacultyActivity extends AppCompatActivity{
    Context context;

    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_faculty);
        context = this;

        initDrawer();
        initUi();
    }

    private void initUi() {

        TextView tvDate = (TextView)findViewById(R.id.tvDate);

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("EEE, MMM d, yy");
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

    public void onClickLectureUpload(View v){

        startActivity(new Intent(context,LectureUploadActivity.class));


    }


    public void onClickParticipantLeave(View v){

        startActivity(new Intent(context,PerticipantLeaveActivity.class));

    }


    public void onClickParticipantAttendance(View v){
        startActivity(new Intent(context,PerticipantAttendanceActivity.class));
    }

    public void onClickSpeakerList(View v){
        startActivity(new Intent(context,SpeakerListActivity.class));
        }

        public void onClickLogOutKortipokko(View v){
                finish();
                }


}
