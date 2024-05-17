package com.eot_app.nav_menu.jobs.job_detail.job_equipment.add_job_equip.mvp;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;

import com.eot_app.R;
import com.eot_app.eoteditor.Utils;
import com.eot_app.nav_menu.audit.nav_scan.BarcodeScanActivity;
import com.eot_app.nav_menu.jobs.job_detail.job_equipment.add_job_equip.AddJobEquipMentActivity;
import com.eot_app.utility.AppConstant;
import com.eot_app.utility.AppUtility;
import com.eot_app.utility.App_preference;
import com.eot_app.utility.CompressImageInBack;
import com.eot_app.utility.EotApp;
import com.eot_app.utility.language_support.LanguageController;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.concurrent.Callable;

public class AddEdit_QRCode_BarCode_Dialog  extends DialogFragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener,QR_Bar_View {

    TextView txt_add_edit_barcode_qr,txt_or;
    Button scan_button,btn_save,btn_cancel;
    EditText edt_barcode_qr;
    RadioButton radio_barcode_qr_insert,radio_barcode_qr_generate;
    ImageView auto_gen_barcode_qr_image;
    ConstraintLayout progressBar_auto_gen_bar_qr;
    private static final int BAR_CODE_REQUEST = 122;
    String tag = "";
    QR_Bar_Pi qrBarPi;
    QRCOde_Barcode_Res_Model res_Model;
    QR_Bar_DataPass qrBarDataPass;

    public AddEdit_QRCode_BarCode_Dialog(AddJobEquipMentActivity addJobEquipMentActivity) {
        this.qrBarDataPass = addJobEquipMentActivity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, androidx.appcompat.R.style.Theme_AppCompat_Light_Dialog_Alert);
        Bundle arguments = getArguments();
        if (arguments != null) {
            tag = arguments.getString("barcod_qrcode_dialog");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_edit_qrcode_barcode_dialog, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        qrBarPi = new QR_Bar_Pc(this);
        progressBar_auto_gen_bar_qr = view.findViewById(R.id.progressBar_auto_gen_bar_qr);
        txt_add_edit_barcode_qr = view.findViewById(R.id.txt_add_edit_barcode_qr);
        txt_or =view.findViewById(R.id.txt_or);
        txt_or.setText(LanguageController.getInstance().getMobileMsgByKey("OR"));
        scan_button = view.findViewById(R.id.scan_button);
        scan_button.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.scan));
        btn_save = view.findViewById(R.id.btn_save);
        if(tag.equals("addBarcode")||tag.equals("addQRcode")) {
            btn_save.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.save_btn));
        }else {
            btn_save.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.update));
        }
        btn_cancel = view.findViewById(R.id.btn_cancel);
        btn_cancel.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.cancel));
        edt_barcode_qr = view.findViewById(R.id.edt_barcode_qr);

        radio_barcode_qr_insert = view.findViewById(R.id.radio_barcode_qr_insert);
        if(tag.equals("addBarcode") || tag.equals("editBarcode")) {
            radio_barcode_qr_insert.setText(LanguageController.getInstance().getMobileMsgByKey("Do you have a Barcode, Please insert it here OR Scan the Barcode"));
        }else{
            radio_barcode_qr_insert.setText(LanguageController.getInstance().getMobileMsgByKey("Do you have a QR Code, Please insert it here OR Scan the QR Code"));
        }

        radio_barcode_qr_generate = view.findViewById(R.id.radio_barcode_qr_generate);
        if(tag.equals("addBarcode") || tag.equals("editBarcode")) {
            radio_barcode_qr_generate.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.auto_gen_bar_code));
        }else{
            radio_barcode_qr_generate.setText(LanguageController.getInstance().getMobileMsgByKey("Auto generate QR COde"));
        }
        auto_gen_barcode_qr_image = view.findViewById(R.id.auto_gen_barcode_qr_image);
        if(tag.equals("addBarcode")){
            txt_add_edit_barcode_qr.setText(LanguageController.getInstance().getMobileMsgByKey("Add Barcode"));
            edt_barcode_qr.setText(LanguageController.getInstance().getMobileMsgByKey("Barcode"));
        }if(tag.equals("editBarcode")){
            txt_add_edit_barcode_qr.setText(LanguageController.getInstance().getMobileMsgByKey("Update Barcode"));
        }if(tag.equals("addQRcode")){
            edt_barcode_qr.setText(LanguageController.getInstance().getMobileMsgByKey("QR Code"));
            txt_add_edit_barcode_qr.setText(LanguageController.getInstance().getMobileMsgByKey("Add QR Code"));
        }if(tag.equals("editQRcode")){
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
                if(tag.equals("addBarcode") || tag.equals("editBarcode")){
                    if(res_Model != null ){
                        qrBarDataPass.onDataPass(res_Model);
                    }

                }else{

                }
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
                       progressBar_auto_gen_bar_qr.setVisibility(View.VISIBLE);
                       radio_barcode_qr_generate.setChecked(true);
                       radio_barcode_qr_insert.setChecked(false);
                       if(tag.equals("addBarcode") || tag.equals("editBarcode")){
                                  qrBarPi.getBarCode("");
                       }else {
                               qrBarPi.getQRCode("");
                       }
                   }else {
                       progressBar_auto_gen_bar_qr.setVisibility(View.GONE);
                       radio_barcode_qr_generate.setChecked(false);
                   }
                    break;
           }
    }

    @Override
    public void setBarCodeData(QRCOde_Barcode_Res_Model bar_res_Model) {
        if(bar_res_Model != null) {
            this.res_Model = bar_res_Model;
            auto_gen_barcode_qr_image.setVisibility(View.VISIBLE);
            if (bar_res_Model.getBarcodeImg() != null && !bar_res_Model.getBarcodeImg().isEmpty()) {
                Picasso.get().load(App_preference.getSharedprefInstance().getBaseURL() + bar_res_Model.getBarcodeImg()).placeholder(R.drawable.ic_profile).error(R.drawable.ic_profile).into(auto_gen_barcode_qr_image);
                progressBar_auto_gen_bar_qr.setVisibility(View.GONE);
            }
            progressBar_auto_gen_bar_qr.setVisibility(View.GONE);

        }
    }

    @Override
    public void setQRCodeData(QRCOde_Barcode_Res_Model qr_res_Model) {
        if(qr_res_Model != null) {
            this.res_Model = qr_res_Model;
            auto_gen_barcode_qr_image.setVisibility(View.VISIBLE);
                    if (qr_res_Model.getQrcodeImg() != null && !qr_res_Model.getQrcodeImg().isEmpty()) {
                        Picasso.get().load(App_preference.getSharedprefInstance().getBaseURL() + qr_res_Model.getQrcodeImg()).placeholder(R.drawable.ic_profile).error(R.drawable.ic_profile).into(auto_gen_barcode_qr_image);
                        progressBar_auto_gen_bar_qr.setVisibility(View.GONE);
                    }
                    progressBar_auto_gen_bar_qr.setVisibility(View.GONE);
        }
    }

    @Override
    public void alertShow(String msg) {
        AppUtility.alertDialog(getActivity(),
                "", msg, LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok), "", new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        return null;
                    }
                });
    }

    @Override
    public void toastShow(String msg) {
        EotApp.getAppinstance().showToastmsg(msg);
    }


    public interface QR_Bar_DataPass {
        void onDataPass(QRCOde_Barcode_Res_Model data);
    }
}
