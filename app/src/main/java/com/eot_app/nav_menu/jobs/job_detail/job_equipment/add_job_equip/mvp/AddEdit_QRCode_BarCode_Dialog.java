package com.eot_app.nav_menu.jobs.job_detail.job_equipment.add_job_equip.mvp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.eot_app.R;
import com.eot_app.nav_menu.audit.nav_scan.BarcodeScanActivity;
import com.eot_app.nav_menu.jobs.job_detail.job_equipment.add_job_equip.AddJobEquipMentActivity;
import com.eot_app.utility.AppConstant;
import com.eot_app.utility.language_support.LanguageController;

public class AddEdit_QRCode_BarCode_Dialog  extends DialogFragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    TextView txt_add_edit_barcode_qr,txt_or;
    Button scan_button,btn_save,btn_cancel;
    EditText edt_barcode_qr;
    RadioButton radio_barcode_qr_insert,radio_barcode_qr_generate;
    ImageView auto_gen_barcode_qr_image;
    private static final int BAR_CODE_REQUEST = 122;
    String tag = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, androidx.appcompat.R.style.Theme_AppCompat_Light_Dialog_Alert);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_edit_qrcode_barcode_dialog, container, false);
        initViews(view);
         tag = getTag();
        return view;
    }

    private void initViews(View view) {

        txt_add_edit_barcode_qr = view.findViewById(R.id.txt_add_edit_barcode_qr);
        txt_or =view.findViewById(R.id.txt_or);
        txt_or.setText(LanguageController.getInstance().getMobileMsgByKey("OR"));
        scan_button = view.findViewById(R.id.scan_button);
        scan_button.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.scan));
        btn_save = view.findViewById(R.id.btn_save);
        if(tag.equals("1")||tag.equals("3")) {
            btn_save.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.save_btn));
        }else {
            btn_save.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.update));
        }
        btn_cancel = view.findViewById(R.id.btn_cancel);
        btn_cancel.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.cancel));
        edt_barcode_qr = view.findViewById(R.id.edt_barcode_qr);

        radio_barcode_qr_insert = view.findViewById(R.id.radio_barcode_qr_insert);
        if(tag.equals("1") || tag.equals("2")) {
            radio_barcode_qr_insert.setText(LanguageController.getInstance().getMobileMsgByKey("Do you have a Barcode, Please insert it here OR Scan the Barcode"));
        }else{
            radio_barcode_qr_insert.setText(LanguageController.getInstance().getMobileMsgByKey("Do you have a QR Code, Please insert it here OR Scan the QR Code"));
        }

        radio_barcode_qr_generate = view.findViewById(R.id.radio_barcode_qr_generate);
        if(tag.equals("1") || tag.equals("2")) {
            radio_barcode_qr_generate.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.auto_gen_bar_code));
        }else{
            radio_barcode_qr_generate.setText(LanguageController.getInstance().getMobileMsgByKey("Auto generate QR COde"));
        }
        auto_gen_barcode_qr_image = view.findViewById(R.id.auto_gen_barcode_qr_image);
        if(tag.equals("1")){
            txt_add_edit_barcode_qr.setText(LanguageController.getInstance().getMobileMsgByKey("Add Barcode"));
            edt_barcode_qr.setText(LanguageController.getInstance().getMobileMsgByKey("Barcode"));
        }if(tag.equals("2")){
            txt_add_edit_barcode_qr.setText(LanguageController.getInstance().getMobileMsgByKey("Update Barcode"));
        }if(tag.equals("3")){
            edt_barcode_qr.setText(LanguageController.getInstance().getMobileMsgByKey("QR Code"));
            txt_add_edit_barcode_qr.setText(LanguageController.getInstance().getMobileMsgByKey("Add QR Code"));
        }if(tag.equals("4")){
            txt_add_edit_barcode_qr.setText(LanguageController.getInstance().getMobileMsgByKey("Update QR Code"));
        }
        scan_button.setOnClickListener(this);
        btn_save.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        radio_barcode_qr_generate.setOnCheckedChangeListener(this);
        radio_barcode_qr_insert.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_cancel:
                 dismiss();
                break;
            case R.id.btn_save:

                break;
            case R.id.scan_button:
                Intent intent = new Intent(getActivity(), BarcodeScanActivity.class);
                intent.putExtra("comeFrom", "AddEquipment");
                startActivityForResult(intent, BAR_CODE_REQUEST);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == BAR_CODE_REQUEST) {
                if (data.getStringExtra("code") != null) {
                    String barcode = data.getStringExtra("code");
                    edt_barcode_qr.setText(barcode);
                }
            }
        }


    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
           switch (buttonView.getId()){
               case R.id.radio_barcode_qr_insert:
                   if(isChecked) {
                       txt_or.setVisibility(View.VISIBLE);
                       edt_barcode_qr.setVisibility(View.VISIBLE);
                       scan_button.setVisibility(View.VISIBLE);
                       radio_barcode_qr_generate.setChecked(false);
                       radio_barcode_qr_insert.setChecked(true);
                   }else {
                       radio_barcode_qr_insert.setChecked(false);
                   }
                   break;
               case R.id.radio_barcode_qr_generate:
                   if(isChecked) {
                       txt_or.setVisibility(View.GONE);
                       edt_barcode_qr.setVisibility(View.GONE);
                       scan_button.setVisibility(View.GONE);
                       radio_barcode_qr_generate.setChecked(true);
                       radio_barcode_qr_insert.setChecked(false);
                   }else {
                       radio_barcode_qr_generate.setChecked(false);
                   }
                    break;
           }
    }

    public interface QR_Bar_DataPass {
        void onDataPass(String data);
    }
}
