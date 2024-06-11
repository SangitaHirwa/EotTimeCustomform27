package com.eot_app.nav_menu.jobs.job_detail.job_equipment;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.eot_app.R;
import com.eot_app.nav_menu.audit.nav_scan.BarcodeScanActivity;
import com.eot_app.nav_menu.audit.nav_scan.UploadBarcodeViewModel;
import com.eot_app.nav_menu.jobs.job_detail.job_equipment.add_job_equip.mvp.QRCOde_Barcode_Res_Model;
import com.eot_app.nav_menu.jobs.job_detail.job_equipment.add_job_equip.mvp.QR_Bar_Pc;
import com.eot_app.nav_menu.jobs.job_detail.job_equipment.add_job_equip.mvp.QR_Bar_Pi;
import com.eot_app.nav_menu.jobs.job_detail.job_equipment.add_job_equip.mvp.QR_Bar_View;
import com.eot_app.utility.AppConstant;
import com.eot_app.utility.AppUtility;
import com.eot_app.utility.App_preference;
import com.eot_app.utility.EotApp;
import com.eot_app.utility.language_support.LanguageController;
import com.eot_app.utility.util_interfaces.Callback_AlertDialog;
import com.squareup.picasso.Picasso;

import java.util.concurrent.Callable;

public class GenerateCodeDialog extends DialogFragment implements View.OnClickListener {

    TextView txt_title;
    Button btn_next;
    RadioButton rd_btn_barcode,rd_btn_qrcode;
    String generateCode = "";
    SelectOption selectOption;

public  GenerateCodeDialog (SelectOption selectOption){
    this.selectOption =selectOption;
}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.CustomDialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_generate_code, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {

        txt_title = view.findViewById(R.id.txt_title);
        txt_title.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.generate_code_msg));
        btn_next = view.findViewById(R.id.btn_next);
        btn_next.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.next_btn));
        rd_btn_barcode = view.findViewById(R.id.rd_btn_barcode);
        rd_btn_barcode.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.barcode));
        rd_btn_barcode.setChecked(true);
        generateCode = rd_btn_barcode.getText().toString();
        rd_btn_qrcode = view.findViewById(R.id.rd_btn_qrcode);
        rd_btn_qrcode.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.qrcode));
        rd_btn_qrcode.setOnClickListener(this);
        rd_btn_barcode.setOnClickListener(this);
        btn_next.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_next:
                   selectOption.generateOption(generateCode);
                    dismiss();
                break;
            case R.id.rd_btn_barcode:
                generateCode = rd_btn_barcode.getText().toString();
                break;
            case R.id.rd_btn_qrcode:
                generateCode = rd_btn_qrcode.getText().toString();
                break;
        }
    }
    public interface SelectOption {
        void generateOption(String data);

    }

    }


