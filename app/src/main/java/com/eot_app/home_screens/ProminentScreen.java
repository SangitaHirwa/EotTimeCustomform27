package com.eot_app.home_screens;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.eot_app.R;
import com.eot_app.login_next.Login2Activity;
import com.eot_app.services.GetKillEvent_ToDestryNotication;
import com.eot_app.utility.AppUtility;
import com.eot_app.utility.App_preference;
import com.eot_app.utility.settings.firstSync.FirstSyncActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public class ProminentScreen extends AppCompatActivity {

    TextView txtLable, btnAllow, btnDeny;
    int UPLOAD_FILE = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prominent);

        btnDeny = findViewById(R.id.btn_deny);
        btnAllow = findViewById(R.id.btn_allow);

        btnDeny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                App_preference.getSharedprefInstance().setLaunchFirst();
                Intent intent = new Intent(ProminentScreen.this, Login2Activity.class);
                startActivity(intent);
                finish();
            }
        });
        btnAllow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP && Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                    if (ContextCompat.checkSelfPermission(ProminentScreen.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    ) {
                        ActivityCompat.requestPermissions((Activity) ProminentScreen.this,
                                new String[]{
                                        Manifest.permission.ACCESS_FINE_LOCATION
                                }, UPLOAD_FILE);
                    } else {
                        ActivityCompat.requestPermissions((Activity) ProminentScreen.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

                    }
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    if (ContextCompat.checkSelfPermission(ProminentScreen.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    ) {
                        ActivityCompat.requestPermissions((Activity) ProminentScreen.this,
                                new String[]{
                                        Manifest.permission.ACCESS_FINE_LOCATION,
                                }, UPLOAD_FILE);
                    } else {
                        ActivityCompat.requestPermissions((Activity) ProminentScreen.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

                    }
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == UPLOAD_FILE) {
            App_preference.getSharedprefInstance().setLaunchFirst();
            Intent intent = new Intent(ProminentScreen.this, Login2Activity.class);
                startActivity(intent);
                finish();
        }
    }
}

