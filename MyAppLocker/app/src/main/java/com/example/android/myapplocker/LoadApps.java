package com.example.android.myapplocker;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class LoadApps extends AsyncTask<Void,Void,Void>{
    Context context;
    Activity activity;
    PackageManager packageManager;

    public List<ApplicationInfo> infoList=null;
    private ProgressDialog progressDialog=null;

    public LoadApps(Context context){
        activity=(Activity)context;
        this.context=context;
        packageManager=context.getPackageManager();
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog=new ProgressDialog(context);
        progressDialog.setMessage("Loading Apps List");
        progressDialog.show();
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected Void doInBackground(Void... params) {
        infoList=checkForLaunchIntent(packageManager.getInstalledApplications(PackageManager.GET_META_DATA));
        return null;
    }



    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        progressDialog.dismiss();
        ListView listView= (ListView) activity.findViewById(R.id.list);
        AppAdapter appAdapter=new AppAdapter(context,R.layout.list_row,infoList);
        listView.setAdapter(appAdapter);

    }

    private List<ApplicationInfo> checkForLaunchIntent(List<ApplicationInfo> list) {
        ArrayList<ApplicationInfo> applist = new ArrayList<ApplicationInfo>();
        for (ApplicationInfo info : list) {
            try {
                if (null != packageManager.getLaunchIntentForPackage(info.packageName)) {
                    applist.add(info);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return applist;
    }

    public List<ApplicationInfo> getInfoList() {
        return infoList;
    }

    public void setInfoList(List<ApplicationInfo> infoList) {
        this.infoList = infoList;
    }

}
