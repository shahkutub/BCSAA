package com.bcsaa;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bcsaa.model.RoutineData;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;

public class LectureUploadActivity extends AppCompatActivity {

    Context context;
    private ImageView imgBack;
    private LinearLayout linUploadClick,linUpload;
    private SearchableSpinner spnCourseName,spnBachNo,spnModuleName,spnSessionNo;
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

        linUploadClick = (LinearLayout)findViewById(R.id.linUploadClick);
        linUpload = (LinearLayout)findViewById(R.id.linUpload);
        linUploadClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linUpload.setVisibility(View.VISIBLE);
            }
        });

        spnCourseName = (SearchableSpinner)findViewById(R.id.spnCourseName);
        spnBachNo = (SearchableSpinner)findViewById(R.id.spnBachNo);
        spnModuleName = (SearchableSpinner)findViewById(R.id.spnModuleName);
        spnSessionNo = (SearchableSpinner)findViewById(R.id.spnSessionNo);


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
}