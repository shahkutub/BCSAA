package com.bcsaa;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bcsaa.model.RoutineData;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;

public class SpeakerListActivity extends AppCompatActivity {

    Context context;
    private ImageView imgBack;
    private LinearLayout linUploadClick;
    private SearchableSpinner spnGender,spnDesignation;
    private List<String> listGender = new ArrayList<>();
    private List<String> listDesignation = new ArrayList<>();
    private Button btnSubmit;
    private String gender,designation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_speaker);

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

        linUploadClick = (LinearLayout)findViewById(R.id.linUploadClick);
        linUploadClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(context,AddSpeakerActivity.class));
            }
        });

        RoutineData routineData = new RoutineData();
        List<RoutineData> myListData = new ArrayList<>();
        for (int i = 0; i <10 ; i++) {

            myListData.add(routineData);
        }

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        MyListAdapter adapter = new MyListAdapter(myListData);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        spnDesignation = (SearchableSpinner)findViewById(R.id.spnDesignation);
        listDesignation.add("Select Designation");
        listDesignation.add("Associate Professor");
        listDesignation.add("Chairman");
        listDesignation.add("Joint Secretary (Budget & Development)");
        ArrayAdapter<String> adapterDesignation= new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, listDesignation);
        spnDesignation.setAdapter(adapterDesignation);
        spnDesignation.setPositiveButton("Ok");

        spnDesignation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0){
                    designation = spnDesignation.getSelectedItem().toString();
                }else{
                    designation = "";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spnGender = (SearchableSpinner)findViewById(R.id.spnGender);
        listGender.add("Select Gender");
        listGender.add("Male");
        listGender.add("Female");
        ArrayAdapter<String> adapterGender= new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, listGender);
        spnGender.setAdapter(adapterGender);
        spnGender.setPositiveButton("Ok");

        spnGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0){
                    gender = spnGender.getSelectedItem().toString();
                }else{
                    gender = "";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnSubmit = (Button)findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(TextUtils.isEmpty(gender)){
                    Toast.makeText(context, "Please select gender", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(designation)){
                    Toast.makeText(context, "Please select designation", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder>{
        private List<RoutineData> listdata = new ArrayList<>();

        // RecyclerView recyclerView;
        public MyListAdapter(List<RoutineData> listdata) {
            this.listdata = listdata;
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View listItem= layoutInflater.inflate(R.layout.item_speaker, parent, false);
            ViewHolder viewHolder = new ViewHolder(listItem);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final RoutineData myListData = listdata.get(position);

//            holder.linView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    startActivity(new Intent(context,RoutineDetailActivity.class));
//                }
//            });
        }


        @Override
        public int getItemCount() {
            return listdata.size();
        }

        public  class ViewHolder extends RecyclerView.ViewHolder {
            public ImageView imageView;
            public TextView textView;
           // public LinearLayout linView;
            public ViewHolder(View itemView) {
                super(itemView);
//                this.imageView = (ImageView) itemView.findViewById(R.id.imageView);
//                this.textView = (TextView) itemView.findViewById(R.id.textView);
               // linView = (LinearLayout) itemView.findViewById(R.id.linView);
            }
        }
    }
}
