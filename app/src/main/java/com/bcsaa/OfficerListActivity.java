package com.bcsaa;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bcsaa.model.EmplyeeInfo;

import java.util.ArrayList;
import java.util.List;

public class OfficerListActivity extends AppCompatActivity {

    Context context;

    private RecyclerView recyclerViewOfficer;
    private ImageView imgBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_officer_list);
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

        recyclerViewOfficer = (RecyclerView)findViewById(R.id.recyclerViewOfficer);
        List<EmplyeeInfo> list = new ArrayList<>();
        EmplyeeInfo info = new EmplyeeInfo();
        info.setContact("123456789");
        info.setDepartment("HR");
        info.setEmail("a@mail.com");
        info.setName("Mr.Alom");
        info.setPost("HR Officer");
        info.setTime_period("12-12-18 present");
        list.add(info);

        EmplyeeInfo info1 = new EmplyeeInfo();
        info1.setContact("123456789");
        info1.setDepartment("HR");
        info1.setEmail("a@mail.com");
        info1.setName("Mr.Alom");
        info1.setPost("HR Officer");
        info1.setTime_period("12-12-18 present");
        list.add(info1);



//        for (int i = 0; i < 10; i++) {
//            list.add(info);
//        }

        MyListAdapter adapter = new MyListAdapter(list);
        recyclerViewOfficer.setHasFixedSize(true);
        recyclerViewOfficer.setLayoutManager(new LinearLayoutManager(context));
        recyclerViewOfficer.setAdapter(adapter);

    }


    private class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.ViewHolder>{
        private List<EmplyeeInfo> listdata = new ArrayList<>();

        // RecyclerView recyclerView;
        public MyListAdapter(List<EmplyeeInfo> listdata) {
            this.listdata = listdata;
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View listItem= layoutInflater.inflate(R.layout.item_office, parent, false);
            ViewHolder viewHolder = new ViewHolder(listItem);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final EmplyeeInfo myListData = listdata.get(position);

            holder.tvName.setText(myListData.getName());
            holder.tvEmail.setText(myListData.getEmail());
            holder.tvContact.setText(myListData.getContact());
            holder.tvPost.setText(myListData.getPost());
            holder.tvDepartment.setText(myListData.getDepartment());
            holder.tvTimePeriod.setText(myListData.getTime_period());


        }


        @Override
        public int getItemCount() {
            return listdata.size();
        }

        public  class ViewHolder extends RecyclerView.ViewHolder {
            public TextView tvName,tvEmail,tvContact,tvPost,tvDepartment,tvTimePeriod;
            // public LinearLayout linView;
            public ViewHolder(View itemView) {
                super(itemView);
                this.tvName = (TextView) itemView.findViewById(R.id.tvName);
                this.tvEmail = (TextView) itemView.findViewById(R.id.tvEmail);
                this.tvContact = (TextView) itemView.findViewById(R.id.tvContact);
                this.tvPost = (TextView) itemView.findViewById(R.id.tvPost);
                this.tvDepartment = (TextView) itemView.findViewById(R.id.tvDepartment);
                this.tvTimePeriod = (TextView) itemView.findViewById(R.id.tvTimePeriod);
               // tvSession.setMovementMethod(new ScrollingMovementMethod());
            }
        }
    }
}
