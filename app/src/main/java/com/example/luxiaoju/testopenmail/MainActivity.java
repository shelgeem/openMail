package com.example.luxiaoju.testopenmail;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button btn_callMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_callMail = findViewById(R.id.btn_call_mail);
        btn_callMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openListMail();
            }
        });
    }

    public void openListMail() {
        Uri uri = Uri.parse("mailto:"+"测试打开邮箱......");
        List<ResolveInfo> packageInfos = getPackageManager().queryIntentActivities(new Intent(Intent.ACTION_SENDTO, uri),0);
        List<String> packageList =new ArrayList<>();
        List<Intent> emailIntents =new ArrayList<>();
        for(ResolveInfo resolveInfo : packageInfos) {
            String packageName = resolveInfo.activityInfo.packageName;
            if(packageList.contains(packageName)) {
                packageList.remove(packageName);
            }
            packageList.add(packageName);
        }
        for(String packageName : packageList) {
            Intent intent = getPackageManager().getLaunchIntentForPackage(packageName);
            emailIntents.add(intent);
        }
        if (emailIntents == null || emailIntents.size() <1) {
            Toast.makeText(MainActivity.this,"没有可用的邮箱",Toast.LENGTH_SHORT).show();
        } else {
            Intent chooserIntent = Intent.createChooser(emailIntents.remove(0), "Select app!");
            if (chooserIntent != null) {
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, emailIntents.toArray(new Parcelable[]{}));
                startActivity(chooserIntent);
            }
        }

    }
}
