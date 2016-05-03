package com.example.android.myapplocker;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AppAdapter extends ArrayAdapter<ApplicationInfo> {

    private List<ApplicationInfo> list;
    private PackageManager packageManager;
    private Context context;

    public AppAdapter(Context context, int textViewResourceId,
                      List<ApplicationInfo> list) {
        super(context, textViewResourceId, list);
        this.context = context;
        this.list = list;
        packageManager = context.getPackageManager();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public ApplicationInfo getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=layoutInflater.inflate(R.layout.list_row,null);
        }
        ApplicationInfo applicationInfo=list.get(position);
        if(applicationInfo != null){
            TextView appName = (TextView) convertView.findViewById(R.id.app_name);
            TextView packageName = (TextView) convertView.findViewById(R.id.app_paackage);
            ImageView iconview = (ImageView) convertView.findViewById(R.id.app_icon);

            appName.setText(applicationInfo.loadLabel(packageManager));
            packageName.setText(applicationInfo.packageName);
            iconview.setImageDrawable(applicationInfo.loadIcon(packageManager));

        }

        return convertView;
    }





}
