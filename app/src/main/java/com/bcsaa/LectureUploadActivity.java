package com.bcsaa;

import android.app.Activity;
import android.content.Context;
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
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bcsaa.model.RoutineData;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;

public class LectureUploadActivity extends AppCompatActivity {

    Context context;
    private ImageView imgBack;
    private LinearLayout linUploadClick,linUpload,linSearch;
    private SearchableSpinner spnCourseName,spnBachNo,spnModuleName,spnSessionNo;
    private List<RowItem> rowItems = new ArrayList<>();
    private HorizontalScrollView horizontalScrollView;
    private String courseName,bachNo,moduleName,sessionNo;
    private Button btnSearch,btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture_upload);

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

        btnSearch = (Button)findViewById(R.id.btnSearch);
        btnSubmit = (Button)findViewById(R.id.btnSubmit);
        horizontalScrollView = (HorizontalScrollView) findViewById(R.id.horizontalScrollView);
        linUploadClick = (LinearLayout)findViewById(R.id.linUploadClick);
        linUpload = (LinearLayout)findViewById(R.id.linUpload);
        linSearch = (LinearLayout)findViewById(R.id.linSearch);
        linUploadClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linSearch.setVisibility(View.GONE);
                linUpload.setVisibility(View.VISIBLE);
                horizontalScrollView.setVisibility(View.GONE);
            }
        });

        spnCourseName = (SearchableSpinner)findViewById(R.id.spnCourseName);
        spnBachNo = (SearchableSpinner)findViewById(R.id.spnBachNo);
        spnModuleName = (SearchableSpinner)findViewById(R.id.spnModuleName);
        spnSessionNo = (SearchableSpinner)findViewById(R.id.spnSessionNo);

        spnCourseName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position>0){
                    courseName = spnCourseName.getSelectedItem().toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnBachNo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position>0){
                    bachNo = spnBachNo.getSelectedItem().toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spnModuleName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position>0){
                    moduleName = spnModuleName.getSelectedItem().toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spnSessionNo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(position>0){
                    sessionNo = spnSessionNo.getSelectedItem().toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        RowItem ietmSelectCourse = new RowItem("Select Course Name");
        RowItem item1 = new RowItem("Course one");
        RowItem item2 = new RowItem("Course two");
        RowItem item3 = new RowItem("Course three");

        rowItems.add(0,ietmSelectCourse);
        rowItems.add(item1);
        rowItems.add(item2);
        rowItems.add(item3);

        CustomAdapter spinneradapter = new CustomAdapter(LectureUploadActivity.this,
                R.layout.spinner_item, R.id.title, rowItems);

        spnCourseName.setAdapter(spinneradapter);
        spnCourseName.setTitle("Select Item");
        spnCourseName.setPositiveButton("OK");

        spnBachNo.setAdapter(spinneradapter);
        spnBachNo.setTitle("Select Item");
        spnBachNo.setPositiveButton("OK");

        spnModuleName.setAdapter(spinneradapter);
        spnModuleName.setTitle("Select Item");
        spnModuleName.setPositiveButton("OK");

        spnSessionNo.setAdapter(spinneradapter);
        spnSessionNo.setTitle("Select Item");
        spnSessionNo.setPositiveButton("OK");


        RoutineData routineData = new RoutineData();
        List<RoutineData> myListData = new ArrayList<>();
        for (int i = 0; i <5 ; i++) {

            myListData.add(routineData);
        }

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        MyListAdapter adapter = new MyListAdapter(myListData);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(courseName)){
                    Toast.makeText(context, "Please select course name", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(bachNo)){
                    Toast.makeText(context, "Please select bach no", Toast.LENGTH_SHORT).show();
                }else {
                    horizontalScrollView.setVisibility(View.VISIBLE);
                }
            }
        });


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(courseName)){
                    Toast.makeText(context, "Please select course name", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(bachNo)){
                    Toast.makeText(context, "Please select Batch no", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(moduleName)){
                    Toast.makeText(context, "Please select module name", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(sessionNo)){
                    Toast.makeText(context, "Please select session no", Toast.LENGTH_SHORT).show();
                }else {
                    //horizontalScrollView.setVisibility(View.VISIBLE);
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
            View listItem= layoutInflater.inflate(R.layout.item_lecture, parent, false);
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

    public class CustomAdapter extends ArrayAdapter<RowItem> {

        LayoutInflater flater;

        public CustomAdapter(Activity context, int resouceId, int textviewId, List<RowItem> list){

            super(context,resouceId,textviewId, list);
            flater = context.getLayoutInflater();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            RowItem rowItem = getItem(position);

            View rowview = flater.inflate(R.layout.spinner_item,null,true);

            TextView txtTitle = (TextView) rowview.findViewById(R.id.title);
            txtTitle.setText(rowItem.getTitle());



            return rowview;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                convertView = flater.inflate(R.layout.spinner_item,parent, false);
            }
            RowItem rowItem = getItem(position);
            TextView txtTitle = (TextView) convertView.findViewById(R.id.title);
            txtTitle.setText(rowItem.getTitle());

            return convertView;
        }
    }

    public class RowItem {

        private String Title;

        public RowItem(String Title){

            this.Title = Title;
        }

        public String getTitle(){

            return Title;
        }

        public void setTitle(String Title){

            this.Title = Title;
        }


        @Override
        public String toString() {
            return Title ;
        }
    }

}
