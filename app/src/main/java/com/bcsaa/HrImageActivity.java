package com.bcsaa;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class HrImageActivity extends AppCompatActivity {

    Context context;
    private ImageView imgContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hr_image_activity);
        context =  this;

        initUi();
    }

    private void initUi() {
        imgContent = (ImageView)findViewById(R.id.imgContent);


    }
}
