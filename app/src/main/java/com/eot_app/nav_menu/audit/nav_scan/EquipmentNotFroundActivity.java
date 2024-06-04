package com.eot_app.nav_menu.audit.nav_scan;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.eot_app.R;
import com.eot_app.utility.AppConstant;
import com.eot_app.utility.language_support.LanguageController;

public class EquipmentNotFroundActivity extends AppCompatActivity implements View.OnClickListener {
TextView txt_equ_not_found,txt_for_link_or_not;
Button button_no,button_yes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_equipment_not_fround);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initializelables();
    }

    private void initializelables() {
        txt_equ_not_found = findViewById(R.id.txt_equ_not_found);
        txt_for_link_or_not = findViewById(R.id.txt_for_link_or_not);
        button_yes = findViewById(R.id.button_yes);
        button_no = findViewById(R.id.button_no);
        txt_equ_not_found.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.system_can_not_find_item_you_scanned));
        txt_for_link_or_not.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.item_not_currently_linked_with_job_want_to_link));
        button_yes.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.yes));
        button_no.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.no));
        button_yes.setOnClickListener(this);
        button_no.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_yes:
                break;
            case R.id.button_no:
                break;
        }
    }

}