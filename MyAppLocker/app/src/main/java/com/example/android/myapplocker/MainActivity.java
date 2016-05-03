package com.example.android.myapplocker;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends Activity implements AdapterView.OnItemClickListener {
    ListView listView;
    LoadApps loadApps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadApps = new LoadApps(this);
        loadApps.execute();
        listView = (ListView) findViewById(R.id.list);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        List<ApplicationInfo> list =loadApps.getInfoList();
        Intent intent = new Intent(this, ClearCache.class);
        intent.putExtra("app_label",list.get(position).loadLabel(getPackageManager()));
        intent.putExtra("app_icon", String.valueOf(list.get(position).loadIcon(getPackageManager())));
        intent.putExtra("app_packName",list.get(position).packageName);
        startActivity(intent);
    }
}
