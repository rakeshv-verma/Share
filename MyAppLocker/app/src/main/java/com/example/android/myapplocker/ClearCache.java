package com.example.android.myapplocker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class ClearCache extends Activity {
    ImageView appIcon;
    TextView appLabel;
    Button clearBtn;
    String appName,drawable,packageName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clear_cache);

        Intent intent = getIntent();
        appName= intent.getStringExtra("app_label");
        drawable = intent.getStringExtra("app_icon");
        packageName=intent.getStringExtra("app_packName");

        Log.e("icon ", drawable);

        appIcon = (ImageView) findViewById(R.id.appIcon);
        appLabel = (TextView) findViewById(R.id.appName);
        clearBtn = (Button) findViewById(R.id.clearBtn);

        appIcon.setImageDrawable(Drawable.createFromPath(drawable));
        appLabel.setText(appName);

        clearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    deleteCache(createPackageContext(packageName,CONTEXT_IGNORE_SECURITY));
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void deleteCache(Context context) {
        File cache = getCacheDir();
        File appDir = new File(cache.getParent());
        if (appDir.exists()) {
            String[] children = appDir.list();
            for (String s : children) {
                if (!s.equals("lib")) {
                    deleteDir(new File(appDir, s));
                    Log.e("TAG", "**************** File "+packageName+" " + s + " DELETED *******************");
                }
            }
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return dir.delete();
    }

    @Override
    public Context createPackageContext(String packageName, int flags) throws PackageManager.NameNotFoundException {
        return super.createPackageContext(packageName, flags);
    }

}

