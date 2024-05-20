package com.eot_app.nav_menu.jobs.job_detail.job_equipment.add_job_equip.mvp;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.eot_app.R;
import com.eot_app.eoteditor.Utils;
import com.eot_app.nav_menu.audit.nav_scan.BarcodeScanActivity;
import com.eot_app.nav_menu.audit.nav_scan.UploadBarcodeActivity;
import com.eot_app.nav_menu.audit.nav_scan.UploadBarcodeViewModel;
import com.eot_app.nav_menu.jobs.job_detail.job_equipment.add_job_equip.AddJobEquipMentActivity;
import com.eot_app.utility.AppConstant;
import com.eot_app.utility.AppUtility;
import com.eot_app.utility.App_preference;
import com.eot_app.utility.CompressImageInBack;
import com.eot_app.utility.EotApp;
import com.eot_app.utility.language_support.LanguageController;
import com.eot_app.utility.util_interfaces.Callback_AlertDialog;
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
    private static final int QR_CODE_REQUEST = 123;
    String barCode , qrcode ;
    QR_Bar_Pi qrBarPi;
    QRCOde_Barcode_Res_Model res_Model;
    QR_Bar_DataPass qrBarDataPass;
    boolean isComeFromDetail = false;
    private UploadBarcodeViewModel uploadBarcodeViewModel;
    String equipmentId;

    public AddEdit_QRCode_BarCode_Dialog(QR_Bar_DataPass qrBarDataPass) {
        this.qrBarDataPass = qrBarDataPass;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, androidx.appcompat.R.style.Theme_AppCompat_Light_Dialog_Alert);
        uploadBarcodeViewModel = new ViewModelProvider(this).get(UploadBarcodeViewModel.class);
        uploadBarcodeViewModel.getUploadMessage().observe(this, s -> {
            if (!TextUtils.isEmpty(s)) {
                Toast.makeText(getContext(), LanguageController.getInstance().getServerMsgByKey(s), Toast.LENGTH_SHORT).show();
                qrBarDataPass.onDataPass(res_Model);
                dismiss();
            }
        });
        Bundle bundle = getArguments();
        if(bundle != null){
            if(bundle.get("barCode") != null){
                barCode = bundle.getString("barCode");
                equipmentId = bundle.getString("equipmentId");
                isComeFromDetail = bundle.getBoolean("isComeFromDetail", false);
            }else if(bundle.get("qrcode") != null){
                qrcode = bundle.getString("qrcode");
                equipmentId = bundle.getString("equipmentId");
                isComeFromDetail = bundle.getBoolean("isComeFromDetail", false);
            }
        }
        qrBarPi = new QR_Bar_Pc(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_edit_qrcode_barcode_dialog, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {
        progressBar_auto_gen_bar_qr = view.findViewById(R.id.progressBar_auto_gen_bar_qr);
        txt_add_edit_barcode_qr = view.findViewById(R.id.txt_add_edit_barcode_qr);
        txt_or =view.findViewById(R.id.txt_or);
        txt_or.setText(LanguageController.getInstance().getMobileMsgByKey("OR"));
        scan_button = view.findViewById(R.id.scan_button);
        scan_button.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.scan));
        btn_save = view.findViewById(R.id.btn_save);
        btn_cancel = view.findViewById(R.id.btn_cancel);
        edt_barcode_qr = view.findViewById(R.id.edt_barcode_qr);
        radio_barcode_qr_insert = view.findViewById(R.id.radio_barcode_qr_insert);
        radio_barcode_qr_generate = view.findViewById(R.id.radio_barcode_qr_generate);
        auto_gen_barcode_qr_image = view.findViewById(R.id.auto_gen_barcode_qr_image);

        scan_button.setOnClickListener(this);
        btn_save.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        radio_barcode_qr_generate.setOnCheckedChangeListener(this);
        radio_barcode_qr_insert.setOnCheckedChangeListener(this);

        btn_cancel.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.cancel));
        if(barCode != null){
           setBarcodeUI();
        }else if (qrcode != null){
            setQrcodeUI();
        }else {
            dismiss();
        }
    }
    public void setBarcodeUI(){
        radio_barcode_qr_insert.setText(LanguageController.getInstance().getMobileMsgByKey("Do you have a Barcode, Please insert it here OR Scan the Barcode"));
        radio_barcode_qr_generate.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.auto_gen_bar_code));
        if (barCode.isEmpty()){
            Log.e("barcode", "Add");
            btn_save.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.save_btn));
            txt_add_edit_barcode_qr.setText(LanguageController.getInstance().getMobileMsgByKey("Add Barcode"));
            edt_barcode_qr.setText(LanguageController.getInstance().getMobileMsgByKey("Barcode"));
        }else {
            Log.e("barcode", "Edit");
            btn_save.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.update));
            txt_add_edit_barcode_qr.setText(LanguageController.getInstance().getMobileMsgByKey("Update Barcode"));
            edt_barcode_qr.setText(barCode);
        }
    }
    public void setQrcodeUI(){
        radio_barcode_qr_insert.setText(LanguageController.getInstance().getMobileMsgByKey("Do you have a QR Code, Please insert it here OR Scan the QR Code"));
        radio_barcode_qr_generate.setText(LanguageController.getInstance().getMobileMsgByKey("Auto generate QR COde"));
        if (qrcode.isEmpty()){
            Log.e("qrcode", "Add");
            edt_barcode_qr.setText(LanguageController.getInstance().getMobileMsgByKey("QR Code"));
            txt_add_edit_barcode_qr.setText(LanguageController.getInstance().getMobileMsgByKey("Add QR Code"));
        }else {
            Log.e("qrcode", "Edit");
            btn_save.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.update));
            txt_add_edit_barcode_qr.setText(LanguageController.getInstance().getMobileMsgByKey("Update QR Code"));
            edt_barcode_qr.setText(qrcode);
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_cancel:
                 dismiss();
                break;
            case R.id.btn_save:
                    if(res_Model != null ){
                        if(isComeFromDetail){
                            AppUtility.alertDialog2(getContext(), LanguageController.getInstance().getMobileMsgByKey(AppConstant.equipment_btn), "If you upload it, Your old data will be deleted.", LanguageController.getInstance().getMobileMsgByKey(AppConstant.expense_upload), AppConstant.cancel, new Callback_AlertDialog() {
                                @Override
                                public void onPossitiveCall() {
                                    if (uploadBarcodeViewModel != null)
                                        if (!TextUtils.isEmpty(equipmentId)) {
                                            if (AppUtility.isInternetConnected()) {
                                                if (barCode != null && !barCode.isEmpty()) {
                                                    uploadBarcodeViewModel.uploadBarcode(equipmentId, barCode);
                                                } else if (qrcode != null && !qrcode.isEmpty()) {
                                                    uploadBarcodeViewModel.uploadQrcode(equipmentId, qrcode);
                                                }
                                            }
                                        }
                                }

                                @Override
                                public void onNegativeCall() {
                                }
                            });

//                            uploadBarcodeViewModel.uploadBarcode(equipmentId,barCode);
                        }else {
                            qrBarDataPass.onDataPass(res_Model);
                            dismiss();
                        }
                    }
                break;
            case R.id.scan_button:

                Intent intent = new Intent(getActivity(), BarcodeScanActivity.class);
                intent.putExtra("comeFrom", "AddEquipment");
                if(barCode != null) {
                    startActivityForResult(intent, BAR_CODE_REQUEST);
                }else if(qrcode != null) {
                    startActivityForResult(intent, QR_CODE_REQUEST);
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == BAR_CODE_REQUEST) {
                if (data.getStringExtra("code") != null) {
                    barCode = data.getStringExtra("code");
                    edt_barcode_qr.setText(barCode);
                        if(barCode!= null && !barCode.isEmpty()) {
                            qrBarPi.getBarCode(barCode);
                    }
                }
            }
            else if (requestCode == QR_CODE_REQUEST) {
                if (data.getStringExtra("code") != null) {
                    qrcode = data.getStringExtra("code");
                    edt_barcode_qr.setText(qrcode);
                        if(qrcode!= null && !qrcode.isEmpty()) {
                            qrBarPi.getQRCode(qrcode);
                    }
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
                       auto_gen_barcode_qr_image.setVisibility(View.GONE);
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
                       if(barCode != null ){
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
            barCode = bar_res_Model.getBarCode();
            if (bar_res_Model.getBarcodeImg() != null && !bar_res_Model.getBarcodeImg().isEmpty() && radio_barcode_qr_generate.isChecked() ) {
                auto_gen_barcode_qr_image.setVisibility(View.VISIBLE);
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
            qrcode = qr_res_Model.getQrcode();
                    if (qr_res_Model.getQrcodeImg() != null && !qr_res_Model.getQrcodeImg().isEmpty() && radio_barcode_qr_generate.isChecked()) {
                        auto_gen_barcode_qr_image.setVisibility(View.VISIBLE);
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

    public void showAlert(String msg){
        AppUtility.alertDialog(getContext(), "", msg, LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok), LanguageController.getInstance().getMobileMsgByKey(AppConstant.cancel), new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                qrBarPi.getBarCode(barCode);
                return null;
            }
        });
    }

    public interface QR_Bar_DataPass {
        void onDataPass(QRCOde_Barcode_Res_Model data);
    }
}
