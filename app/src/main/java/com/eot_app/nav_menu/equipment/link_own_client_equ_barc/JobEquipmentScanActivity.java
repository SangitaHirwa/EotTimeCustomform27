package com.eot_app.nav_menu.equipment.link_own_client_equ_barc;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.widget.ContentLoadingProgressBar;

/*import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;*/

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
import com.eot_app.utility.db.AppDataBase;
import com.eot_app.utility.language_support.LanguageController;
import com.eot_app.utility.settings.equipmentdb.Equipment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner;
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning;
//import com.google.zxing.BarcodeFormat;
import com.gun0912.tedpermission.PermissionListener;
//import com.gun0912.tedpermission.TedPermission;
import com.gun0912.tedpermission.rx3.TedPermission;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;


public class JobEquipmentScanActivity extends AppCompatActivity implements ScanEquView, LinkEquipmentView {
    //   public static boolean SUCCESS = false;
    List<EquArrayModel> myEquList = new ArrayList<>();
    String myEqu = "";
//    private CodeScanner mCodeScanner;
    private ScanEquPc scanBarcode_pc;
    AppCompatImageView img_search;
    private EditText edit_barcode;
    AppCompatTextView tv_scan_label;
    String jobId = "";
    String strstatus = "";
    String type = "", cltId = "", contrId = "";
    LinkEquipmentPI linkEquipmentPI;
    ContentLoadingProgressBar content_loading_progress;
//    CodeScannerView scannerView = null;
    GmsBarcodeScannerOptions options;
    GmsBarcodeScanner scanner;
    TextView txt_status;
    boolean isSearching = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_equipment_scan);
        setTitle(LanguageController.getInstance().getMobileMsgByKey(AppConstant.detail_scan_barcode));
        initializeViews();


        scanBarcode_pc = new ScanEquPc(this);
        linkEquipmentPI = new LinkEquipmentPC(this);
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
            options = new GmsBarcodeScannerOptions.Builder()
                    .setBarcodeFormats(
                            Barcode.FORMAT_ALL_FORMATS)
                    .enableAutoZoom()
                    .allowManualInput()
                    .build();
        }else {
            options = new GmsBarcodeScannerOptions.Builder()
                    .setBarcodeFormats(
                            Barcode.FORMAT_ALL_FORMATS)
                    .enableAutoZoom()
                    .build();
        }
        scanner = GmsBarcodeScanning.getClient(this, options);
        try {
            if (getIntent().hasExtra("JOBID")) {
                jobId = getIntent().getStringExtra("JOBID");
                type = getIntent().getStringExtra("type");
                cltId = getIntent().getStringExtra("cltId");
                contrId = getIntent().getStringExtra("contrId");
                /** We resolved crassh issue by commentd below code #Eye014366 18/8/2023*/
//                myEqu = getIntent().getStringExtra("myEquList");
//                Type listType = new TypeToken<List<EquArrayModel>>() {
//                }.getType();
//                myEquList = new Gson().fromJson(myEqu, listType);
                strstatus = getIntent().getStringExtra("strstatus");
                /**After discuss with Ayush sir and Jit sir stop api callin here*/
//                if (TextUtils.isEmpty(contrId))
//                    linkEquipmentPI.getEquipmentList(type, cltId, jobId);
//                else linkEquipmentPI.getContractList(new ContractEquipmentReq(type, jobId, contrId));
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }


    }
    private void askTedPermission(int type,String[] permissions) {
        String permissionMsg ="";
        if(type == 0){
            permissionMsg = "<b>Need Camera and Storage Permission</b><br><br>If you reject permission,you can not use this service<br><br>Please turn on permissions at [SettingActivity] > [Permission]";
        }
        TedPermission.create()
                .setPermissionListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
//                        if (type == 0)
//                            mCodeScanner.startPreview();
                    }

                    @Override
                    public void onPermissionDenied(List<String> deniedPermissions) {

                    }
                })
                .setDeniedMessage(Html.fromHtml(permissionMsg))
                .setPermissions(permissions);
       /* TedPermission.with(EotApp.getAppinstance())
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
                .setDeniedMessage(Html.fromHtml(permissionMsg))
                .setPermissions(permissions)
                .check();*/
    }
    public String getJobId() {
        return jobId;
    }

    @Override
    public void onResume() {
        super.onResume();
//        startScanner();

        if(isSearching){
            txt_status.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.searching)+"...");
        }else {
            txt_status.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.loading)+"...");
            startGoogleScan();
        }

    }

    @Override
    public void onPause() {
//        mCodeScanner.releaseResources();
        super.onPause();
    }

    private void initializeViews() {

        content_loading_progress = findViewById(R.id.content_loading_progress);
//        scannerView = findViewById(R.id.scanner_view);
//        txt_status = findViewById(R.id.txt_status);
//        mCodeScanner = new CodeScanner(this, scannerView);
//        List<BarcodeFormat> list = new ArrayList<>();
//        list.add(BarcodeFormat.CODE_128);
//        list.add(BarcodeFormat.UPC_A);
//        list.add(BarcodeFormat.UPC_E);
//        list.add(BarcodeFormat.EAN_13);
//        list.add(BarcodeFormat.CODE_39);
//        list.add(BarcodeFormat.CODABAR);
//        mCodeScanner.setFormats(list);
//        mCodeScanner.setAutoFocusEnabled(true);
        img_search = findViewById(R.id.img_search);
        edit_barcode = findViewById(R.id.edit_barcode);
        tv_scan_label = findViewById(R.id.tv_scan_label);

        tv_scan_label.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.scan_barcode_manually));

//        mCodeScanner.setDecodeCallback(result -> JobEquipmentScanActivity.this.runOnUiThread(() -> searchEquipment(result.getText())));
//        scannerView.setOnClickListener(view -> startScanner());


        img_search.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(edit_barcode.getText().toString())) {
                AppUtility.hideSoftKeyboard(JobEquipmentScanActivity.this);
                searchEquipment(edit_barcode.getText().toString());
            }

        });

//        scannerView.setVisibility(View.GONE);

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
//            mCodeScanner.startPreview();
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
        isSearching = false;
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
//            scannerView.setVisibility(View.VISIBLE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }

    @Override
    public void refreshEquipmentList(boolean isReturn , boolean eqiAdd) {

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

    public  void startGoogleScan()  {

        scanner
                .startScan()
                .addOnSuccessListener(
                        barcode -> {
                            Log.e("ScanResult", barcode.getRawValue());
//                                    Toast.makeText(BarcodeScanActivity.this, "Searching...", Toast.LENGTH_SHORT).show();
                            isSearching = true;
//                            searchEquipment(barcode.getRawValue());
                            if (barcode.getRawValue() != null) {
                                String str = barcode.getRawValue();
                                Intent intent = new Intent();
                                intent.putExtra("str", str);
                                setResult(RESULT_OK, intent);
                            }
                            finish();
                        })
                .addOnCanceledListener(
                        () -> {
                            Log.e("ScanResult", "Cancle");
                            finish();
                        })
                .addOnFailureListener(
                        e -> {
                            isSearching = true;
                            Log.e("ScanResult", "error = "+e.getMessage());
                            AppUtility.alertDialog(this, "", LanguageController.getInstance().getMobileMsgByKey(AppConstant.scanner_error), LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok), "", new Callable<Boolean>() {
                                @Override
                                public Boolean call() throws Exception {
                                    isSearching = false;
                                    finish();
                                    return null;
                                }
                            });
                        });
    }
}