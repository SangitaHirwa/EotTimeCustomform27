package com.eot_app.nav_menu.jobs.job_detail.job_equipment.add_job_equip;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.eot_app.R;
import com.eot_app.utility.AppConstant;
import com.eot_app.utility.language_support.LanguageController;

public class AddEquipNavigationDialog extends DialogFragment implements View.OnClickListener {

    TextView txt_title;
    Button btn_action,btn_list;

    NavigateToPage navigateToPage;
    public AddEquipNavigationDialog(NavigateToPage navigateToPage){
        this.navigateToPage = navigateToPage;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.CustomDialog);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_add_equip_navigate, container, false);
        initViews(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setCancelable(false);
    }

    private void initViews(View view) {

        txt_title = view.findViewById(R.id.txt_title);
        txt_title.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.navigate_to));
        btn_action = view.findViewById(R.id.btn_action);
        btn_action.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.equipment_action_page));
        btn_action.setOnClickListener(this);
        btn_list = view.findViewById(R.id.btn_list);
        btn_list.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.equipment_list_page));
        btn_list.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_action:
                navigateToPage.setNavigation(true);
                    dismiss();
                break;
            case R.id.btn_list:
                navigateToPage.setNavigation(false);
                dismiss();
                break;
        }
    }

    interface NavigateToPage{
        public void setNavigation(boolean isActionPage);
    }

    }


