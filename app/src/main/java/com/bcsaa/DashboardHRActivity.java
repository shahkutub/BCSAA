package com.bcsaa;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bcsaa.utils.AppConstant;
import com.bcsaa.utils.PersistData;
import com.bcsaa.utils.PersistentUser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DashboardHRActivity extends AppCompatActivity {

    Context context;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private LinearLayout linMyApplication,linManageLeave;
    private RelativeLayout relNotifiView;
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
        LinearLayout linOfficer = (LinearLayout) findViewById(R.id.linOfficer);
        LinearLayout linStaf = (LinearLayout) findViewById(R.id.linStaf);
        LinearLayout linNewJoined = (LinearLayout) findViewById(R.id.linNewJoined);
        LinearLayout linOfficeNotice = (LinearLayout) findViewById(R.id.linOfficeNotice);
        LinearLayout linAttendance = (LinearLayout) findViewById(R.id.linAttendance);
        LinearLayout linleave = (LinearLayout) findViewById(R.id.linleave);
        LinearLayout linPromotion = (LinearLayout) findViewById(R.id.linPromotion);
        LinearLayout linTransfer = (LinearLayout) findViewById(R.id.linTransfer);
        LinearLayout linTraining = (LinearLayout) findViewById(R.id.linTraining);


        linMyApplication = (LinearLayout) findViewById(R.id.linMyApplication);
        linManageLeave = (LinearLayout)findViewById(R.id.linManageLeave);
        relNotifiView = (RelativeLayout) findViewById(R.id.relNotifiView);

        relNotifiView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoginDialog();
            }
        });

        linOfficer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConstant.activityName = "Officers List";
                startActivity(new Intent(context,OfficerListActivity.class));
            }
        });


        linStaf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConstant.activityName = "Staff List";
                startActivity(new Intent(context,OfficerListActivity.class));
            }
        });



        linNewJoined.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppConstant.activityName = "New Joined";
                startActivity(new Intent(context,OfficerListActivity.class));
            }
        });

        linOfficeNotice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppConstant.activityName = "Office Notice";
                    startActivity(new Intent(context,OfficerListActivity.class));
                }
            });


        linAttendance.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppConstant.activityName = "Attendance";
                    startActivity(new Intent(context,OfficerListActivity.class));
                }
            });


        linleave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppConstant.activityName = "Leave";
                    startActivity(new Intent(context,OfficerListActivity.class));
                }
            });


        linPromotion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppConstant.activityName = "Promotion";
                    startActivity(new Intent(context,OfficerListActivity.class));
                }
            });


        linTransfer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppConstant.activityName = "Transfer";
                    startActivity(new Intent(context,OfficerListActivity.class));
                }
            });


        linTraining.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppConstant.activityName = "Training";
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


    public void onClickApplyLeaveCL(View v){
        startActivity(new Intent(context,CasualLeaveApplicatonForm.class));
    }


    public void onClickApplyLeave(View v){
        startActivity(new Intent(context,LeaveApplicatonForm.class));
    }


    public void onClickApplyLeaveList(View v){
        startActivity(new Intent(context,EmployeeLeaveListActivity.class));
    }


    public void onClickManageLeaveList(View v){
        startActivity(new Intent(context,ManageEmployeeLeaveListActivity.class));
    }


    public void onClickApplication(View v){
        linManageLeave.setVisibility(View.GONE);
        if(linMyApplication.getVisibility()==View.GONE){
            linMyApplication.setVisibility(View.VISIBLE);
        } else if(linMyApplication.getVisibility()==View.VISIBLE){
            linMyApplication.setVisibility(View.GONE);
        }
    }


    public void onClickManageApplication(View v){
        linMyApplication.setVisibility(View.GONE);

        if(linManageLeave.getVisibility()==View.GONE){
            linManageLeave.setVisibility(View.VISIBLE);
        } else if(linManageLeave.getVisibility()==View.VISIBLE){
            linManageLeave.setVisibility(View.GONE);
        }
    }



    public void onClickLogOutKortipokko(View v){
        PersistentUser.logOut(context);
        finish();
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


}
