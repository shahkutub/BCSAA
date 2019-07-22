package com.bcsaa.utils;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bcsaa.R;
import com.bcsaa.model.SpinnerModel;

import java.util.ArrayList;
import java.util.List;

public class CustomSpinnerAdapter extends ArrayAdapter<SpinnerModel> {

    LayoutInflater flater;
    List<SpinnerModel> list = new ArrayList<>();

    public CustomSpinnerAdapter(Activity context, int resouceId, int textviewId, List<SpinnerModel> list){

        super(context,resouceId,textviewId, list);
        flater = context.getLayoutInflater();
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        SpinnerModel rowItem = getItem(position);

        View rowview = flater.inflate(R.layout.spinner_item,null,true);

        TextView txtTitle = (TextView) rowview.findViewById(R.id.title);

        if(rowItem.getName()!=null){
            txtTitle.setText(rowItem.getName().toString());
        }


        return rowview;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = flater.inflate(R.layout.spinner_item,parent, false);
        }
        SpinnerModel rowItem = getItem(position);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.title);
        if(rowItem.getName()!=null){
            txtTitle.setText(rowItem.getName().toString());
        }

        return convertView;
    }
}