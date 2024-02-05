package com.eot_app.nav_menu.equipment.link_own_client_equ_barc;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.widget.ContentLoadingProgressBar;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;

import com.eot_app.R;
import com.eot_app.home_screens.MainActivity;
import com.eot_app.nav_menu.audit.audit_list.equipment.model.EquipmentStatus;
import com.eot_app.nav_menu.audit.audit_list.scanbarcode.model.ScanBarcodeRequest;
import com.eot_app.nav_menu.audit.nav_scan.BarcodeScanActivity;
import com.eot_app.nav_menu.equipment.link_own_client_equ_barc.mvp_scanbar.ScanEquPc;
import com.eot_app.nav_menu.equipment.link_own_client_equ_barc.mvp_scanbar.ScanEquView;
import com.eot_app.nav_menu.equipment.linkequip.ActivityLinkEquipment;
import com.eot_app.nav_menu.equipment.linkequip.linkMVP.LinkEquipmentPC;
import com.eot_app.nav_menu.equipment.linkequip.linkMVP.LinkEquipmentPI;
import com.eot_app.nav_menu.equipment.linkequip.linkMVP.LinkEquipmentView;
import com.eot_app.nav_menu.equipment.linkequip.linkMVP.model.ContractEquipmentReq;
import com.eot_app.nav_menu.jobs.job_db.EquArrayModel;
import com.eot_app.nav_menu.jobs.job_detail.JobDetailActivity;
import com.eot_app.nav_menu.jobs.job_detail.job_equipment.JobEquipmentActivity;
import com.eot_app.utility.AppConstant;
import com.eot_app.utility.AppUtility;
import com.eot_app.utility.EotApp;
import com.eot_app.utility.language_support.LanguageController;
import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;


import java.util.ArrayList;
import java.util.List;


public class JobEquipmentScanActivity extends AppCompatActivity implements ScanEquView, LinkEquipmentView {
    //   public static boolean SUCCESS = false;
    List<EquArrayModel> myEquList = new ArrayList<>();
    String myEqu = "";
    private CodeScanner mCodeScanner;
    private ScanEquPc scanBarcode_pc;
    AppCompatImageView img_search;
    private EditText edit_barcode;
    AppCompatTextView tv_scan_label;
    String jobId = "";
    String strstatus = "";
    String type = "", cltId = "", contrId = "";
    LinkEquipmentPI linkEquipmentPI;
    ContentLoadingProgressBar content_loading_progress;
    CodeScannerView scannerView = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_equipment_scan);
        setTitle(LanguageController.getInstance().getMobileMsgByKey(AppConstant.detail_scan_barcode));
        initializeViews();


        scanBarcode_pc = new ScanEquPc(this);
        linkEquipmentPI = new LinkEquipmentPC(this);

        try {
            if (getIntent().hasExtra("JOBID")) {
                jobId = getIntent().getStringExtra("JOBID");
                type = getIntent().getStringExtra("type");
                cltId = getIntent().getStringExtra("cltId");
                contrId = getIntent().getStringExtra("contrId");
//                myEqu = getIntent().getStringExtra("myEquList");
//                Type listType = new TypeToken<List<EquArrayModel>>() {
//                }.getType();
//                myEquList = new Gson().fromJson(myEqu, listType);
                strstatus = getIntent().getStringExtra("strstatus");

                if (TextUtils.isEmpty(contrId))
                    linkEquipmentPI.getEquipmentList(type, cltId, jobId);
                else linkEquipmentPI.getContractList(new ContractEquipmentReq(type, jobId, contrId));
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }


    }
    private void askTedPermission(int type,String[] permissions) {
        TedPermission.with(EotApp.getAppinstance())
                .setPermissionListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        if (type == 0)
                            mCodeScanner.startPreview();
                    }

                    @Override
                    public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                        finish();
                    }
                })
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [SettingActivity] > [Permission]")
                .setPermissions(permissions)
                .check();
    }
    public String getJobId() {
        return jobId;
    }

    @Override
    public void onResume() {
        super.onResume();
        startScanner();
    }

    @Override
    public void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }

    private void initializeViews() {

        content_loading_progress = findViewById(R.id.content_loading_progress);
        scannerView = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(this, scannerView);
        List<BarcodeFormat> list = new ArrayList<>();
        list.add(BarcodeFormat.CODE_128);
        list.add(BarcodeFormat.UPC_A);
        list.add(BarcodeFormat.UPC_E);
        list.add(BarcodeFormat.EAN_13);
        list.add(BarcodeFormat.CODE_39);
        list.add(BarcodeFormat.CODABAR);
        mCodeScanner.setFormats(list);
        mCodeScanner.setAutoFocusEnabled(true);
        img_search = findViewById(R.id.img_search);
        edit_barcode = findViewById(R.id.edit_barcode);
        tv_scan_label = findViewById(R.id.tv_scan_label);

        tv_scan_label.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.scan_barcode_manually));

        mCodeScanner.setDecodeCallback(result -> JobEquipmentScanActivity.this.runOnUiThread(() -> searchEquipment(result.getText())));
        scannerView.setOnClickListener(view -> startScanner());


        img_search.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(edit_barcode.getText().toString())) {
                AppUtility.hideSoftKeyboard(JobEquipmentScanActivity.this);
                searchEquipment(edit_barcode.getText().toString());
            }

        });

        scannerView.setVisibility(View.GONE);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }

    private void startScanner() {
//        if (AppUtility.askCameraTakePicture(this)) {
//            mCodeScanner.startPreview();
//        }

        if (AppUtility.askCameraTakePicture(this)) {
            mCodeScanner.startPreview();
        }
        else {
            // Sdk version 33
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU ) {
                askTedPermission(0, AppConstant.cameraPermissions33);
            }else {
                askTedPermission(0, AppConstant.cameraPermissions);
            }
        }
    }

    @Override
    public void onJobEquipmentFound(EquArrayModel equipmentRes) {
        if (equipmentRes != null) {
            String str = new Gson().toJson(equipmentRes);
            Intent intent = new Intent();
            intent.putExtra("str", str);
            setResult(RESULT_OK, intent);
        }
        finish();
    }

    /*create search request*/
    private void searchEquipment(String barcode) {
        ScanBarcodeRequest request = new ScanBarcodeRequest();
        /*barcode search Param*/
        request.setAudId(jobId);
        request.setBarCode(barcode);
        scanBarcode_pc.searchJobWithBarcode(request, myEquList);
    }

    @Override
    public void setEquipmentList(List<EquArrayModel> list) {
        if (list != null) {
            myEquList= list;
        }
    }

    @Override
    public void showHideProgressBar(boolean isShowProgress) {
        if (isShowProgress) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            content_loading_progress.setVisibility(View.VISIBLE);
        } else {
            content_loading_progress.setVisibility(View.GONE);
            scannerView.setVisibility(View.VISIBLE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }

    @Override
    public void refreshEquipmentList(boolean isReturn) {

    }

    @Override
    public void onSessionExpired(String msg) {
        showDialog(msg);
    }

    @Override
    public void setEquStatusList(List<EquipmentStatus> list) {

    }

    @Override
    public void updateLinkUnlinkEqu() {

    }

    @Override
    public void refreshEquList(boolean isReturn) {

    }

    private void showDialog(String msg) {
        AppUtility.alertDialog(this, LanguageController.getInstance().getMobileMsgByKey(AppConstant.dialog_error_title), msg, LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok), "", () -> {
            EotApp.getAppinstance().sessionExpired();
            return null;
        });
    }
}