package com.eot_app.nav_menu.drag_and_drop_functinality;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.eot_app.R;

public class Take_Site_Picture extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_site_picture);
        Button take_side = findViewById(R.id.button2);
        take_side.setOnClickListener(v -> {
            Intent intent=new Intent(this,Take_Picture_For_Drag_Drop.class);
            startActivity(intent);
        });

    }
}