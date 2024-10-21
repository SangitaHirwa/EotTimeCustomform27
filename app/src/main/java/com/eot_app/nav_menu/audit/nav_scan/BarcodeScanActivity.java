package com.eot_app.nav_menu.audit.nav_scan;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

//import com.budiyev.android.codescanner.CodeScanner;
//import com.budiyev.android.codescanner.CodeScannerView;
import com.eot_app.R;
import com.eot_app.home_screens.MainActivity;
import com.eot_app.nav_menu.audit.audit_list.audit_mvp.model.AuditList_Res;
import com.eot_app.nav_menu.audit.audit_list.equipment.model.Equipment_Res;
import com.eot_app.nav_menu.audit.audit_list.equipment.remark.RemarkActivity;
import com.eot_app.nav_menu.audit.audit_list.scanbarcode.model.ScanBarcodeRequest;
import com.eot_app.nav_menu.audit.audit_list.scanbarcode.scan_mvp.ScanBarcode_PC;
import com.eot_app.nav_menu.audit.audit_list.scanbarcode.scan_mvp.ScanBarcode_View;
import com.eot_app.nav_menu.jobs.job_db.EquArrayModel;
import com.eot_app.nav_menu.jobs.job_db.Job;
import com.eot_app.nav_menu.jobs.job_detail.job_equipment.JobEquipmentActivity;
import com.eot_app.nav_menu.jobs.job_detail.job_equipment.job_equ_remrk.JobEquRemarkRemarkActivity;
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
import com.gun0912.tedpermission.rx3.TedPermission;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by Mahendra Dabi on 8/11/19.
 */
public class BarcodeScanActivity extends AppCompatActivity implements ScanBarcode_View {

//    private CodeScanner mCodeScanner;
    private ScanBarcode_PC scanBarcode_pc;
    AppCompatImageView img_search;
    private EditText edit_barcode;
    AppCompatTextView tv_scan_label;
    private String codeText;
    private String comeFrom;
    LinearLayout layout_search;
    List<EquArrayModel> jobList;
    boolean isScannerValue;
    boolean isSearching = false;
    boolean isFailuer = false;
    GmsBarcodeScannerOptions options;
    GmsBarcodeScanner scanner;
    TextView txt_status;
    boolean isJobEquList = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_scanner);
        layout_search = findViewById(R.id.layout_search);
        txt_status = findViewById(R.id.txt_status);
        if (getIntent() != null) {
            comeFrom = getIntent().getStringExtra("comeFrom");

            if (comeFrom != null && comeFrom.equalsIgnoreCase("AddEquipment") || comeFrom != null && comeFrom.equalsIgnoreCase("jobEquList")) {
                layout_search.setVisibility(View.GONE);
                options = new GmsBarcodeScannerOptions.Builder().setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS).enableAutoZoom().build();
                if(comeFrom.equalsIgnoreCase("jobEquList")){
                    isJobEquList = true;
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                        options = new GmsBarcodeScannerOptions.Builder().setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS).enableAutoZoom().allowManualInput().build();
                    } else {
                        options = new GmsBarcodeScannerOptions.Builder().setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS).enableAutoZoom().build();
                    }
                }
            } else {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                    options = new GmsBarcodeScannerOptions.Builder().setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS).enableAutoZoom().allowManualInput().build();
                } else {
                    options = new GmsBarcodeScannerOptions.Builder().setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS).enableAutoZoom().build();
                }
            }
        } else {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                options = new GmsBarcodeScannerOptions.Builder().setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS).enableAutoZoom().allowManualInput().build();
            } else {
                options = new GmsBarcodeScannerOptions.Builder().setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS).enableAutoZoom().build();
            }
        }
        scanner = GmsBarcodeScanning.getClient(this, options);
        scanBarcode_pc = new ScanBarcode_PC(this, true);

        hideActionBar(true);

//        CodeScannerView scannerView = findViewById(R.id.scanner_view);

        img_search = findViewById(R.id.img_search);
        edit_barcode = findViewById(R.id.edit_barcode);
        tv_scan_label = findViewById(R.id.tv_scan_label);
        jobList = new ArrayList<>();
        tv_scan_label.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.scan_barcode_manually));

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
//        mCodeScanner.setDecodeCallback(result -> runOnUiThread(() -> {
//            codeText = result.getText();
//            Log.d("codeee", codeText);
//            if (comeFrom != null && comeFrom.equalsIgnoreCase("AddEquipment")) {
//                Intent intent = new Intent();
//                intent.putExtra("code", codeText);
//                setResult(RESULT_OK, intent);
//                finish();
//            } else {
//                isScannerValue = true;
//                searchEquipment(result.getText(), isScannerValue);
//            }
//        }));

//        scannerView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startScanner();
//            }
//        });

//        img_search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!TextUtils.isEmpty(edit_barcode.getText().toString())) {
//                    AppUtility.hideSoftKeyboard(BarcodeScanActivity.this);
//                    codeText = edit_barcode.getText().toString();
//                    isScannerValue = false;
//                    searchEquipment(edit_barcode.getText().toString(), isScannerValue);
//                    edit_barcode.getText().clear();
//                }
//            }
//        });
    }

    @Override
    public void onResume() {
        super.onResume();
//        startScanner();
        if (isSearching) {
            txt_status.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.searching) + "...");
        } else {
            txt_status.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.loading) + "...");
            startGoogleScan();
        }

    }


    @Override
    public void onSessionExpired(String msg) {
        showDialog(msg);
    }

    @Override
    public void onAudiListCall(String barcode) {
        AppUtility.progressBarDissMiss();
        ScanBarcodeRequest request = new ScanBarcodeRequest();
        /*barcode search Param*/
        request.setAudId("");
        request.setBarCode(barcode);
        scanBarcode_pc.searchEquipmentinAudit(request);

    }

    private void showDialog(String msg) {
        AppUtility.alertDialog(this, LanguageController.getInstance().getMobileMsgByKey(AppConstant.dialog_error_title), msg, LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok), "", new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                EotApp.getAppinstance().sessionExpired();
                return null;
            }
        });
    }

    @Override
    public void onPause() {
//        mCodeScanner.releaseResources();
        super.onPause();
    }

    private void startScanner() {
        if (AppUtility.askCameraTakePicture(this)) {
//            mCodeScanner.startPreview();
        } else {
            // Sdk version 33
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                askTedPermission(0, AppConstant.cameraPermissions33);
            } else {
                askTedPermission(0, AppConstant.cameraPermissions);
            }
        }
    }

    private void askTedPermission(int type, String[] permissions) {
        String permissionMsg = "";
        if (type == 0) {
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
       /* TedPermission.with(EotApp.getAppinstance()).setPermissionListener(new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                if (type == 0) mCodeScanner.startPreview();
            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                startActivity(new Intent(BarcodeScanActivity.this, MainActivity.class));
            }
        }).setDeniedMessage(Html.fromHtml(permissionMsg)).setPermissions(permissions).check();*/
    }

    /*hide show actionbar*/
    private void hideActionBar(boolean hide) {
        Window window = getWindow();
        if (hide) {
//            getSupportActionBar().hide();
            window.setStatusBarColor(Color.BLACK);
        } else {
//            getSupportActionBar().show();
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
    }

    @Override
    public void onEquipmentFound(Equipment_Res equipmentRes) {
        if (equipmentRes != null) {
            String strEqu = new Gson().toJson(equipmentRes);
            startActivity(new Intent(this, RemarkActivity.class).putExtra("equipment", strEqu));
        }
    }

    @Override
    public void onEquipmentFoundButNotLinked(Equipment equipment) {
        AppUtility.progressBarDissMiss();
        if (equipment == null) {
            try {
                TextView dailog_title, dialog_msg;
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

                LayoutInflater inflater = getLayoutInflater();// (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View customLayout = inflater.inflate(R.layout.dialog_layout, null);
                alertDialog.setView(customLayout);
                alertDialog.setCancelable(false);

                dailog_title = customLayout.findViewById(R.id.dai_title);
                dialog_msg = customLayout.findViewById(R.id.dia_msg);
                dailog_title.setVisibility(View.VISIBLE);
                dailog_title.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.equipment));
                dialog_msg.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.equ_not_found));

                alertDialog.setPositiveButton(LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                alertDialog.setNegativeButton(LanguageController.getInstance().getMobileMsgByKey(AppConstant.equipment_sync_button_text), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // dialog.dismiss();
                        scanBarcode_pc.syncEquipments();
                    }
                });
                AlertDialog alertDialog1 = alertDialog.create();
                alertDialog1.show();
                alertDialog1.getButton(AlertDialog.BUTTON_NEGATIVE).setAllCaps(false);

            } catch (Exception e) {
                e.printStackTrace();
                EotApp.getAppinstance().showToastmsg("Something went wrong here.");
            }

        } else {
            Intent intent = new Intent(this, EquipmentDetailsActivity.class);
            intent.putExtra("equipment", true);
            intent.putExtra("equipment_id", equipment.getEquId());
            startActivity(intent);
        }
    }

    @Override
    public void onRecordFound(List<AuditList_Res> list) {
        isSearching = false;
        if (jobList != null && !jobList.isEmpty()) {
            List<String> jobEquipmentByEquipmentId = null;
            if (!jobList.get(0).getBarcode().equals("")) {
                jobEquipmentByEquipmentId = AppDataBase.getInMemoryDatabase(this).jobModel().getjobidsbyequid("%" + jobList.get(0).getBarcode() + "%");
            } else if (!jobList.get(0).getQrcode().equals("")) {
                jobEquipmentByEquipmentId = AppDataBase.getInMemoryDatabase(this).jobModel().getjobidsbyequid("%" + jobList.get(0).getQrcode() + "%");
            } else {
                jobEquipmentByEquipmentId = AppDataBase.getInMemoryDatabase(this).jobModel().getjobidsbyequid("%" + jobList.get(0).getSno() + "%");
            }
            String s = new Gson().toJson(jobEquipmentByEquipmentId);
            if (jobEquipmentByEquipmentId.size() == 0 && list.size() == 0) {
                Equipment equipment = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).equipmentDao().getEquipmentById(jobList.get(0).getEquId());
                if (equipment != null) {
                    if(!isJobEquList) {
                        Intent intent = new Intent(this, EquipmentDetailsActivity.class);
                        intent.putExtra("equipment", true);
                        intent.putExtra("equipment_id", jobList.get(0).getEquId());
                        if (comeFrom != null && comeFrom.equalsIgnoreCase("linkFromEquList")) {
                            intent.putExtra("found_or_not", "equipmentFound");
                        }
                        startActivity(intent);
                    }else {
                        String equip = new Gson().toJson(equipment);
                        findEquipment(equip,codeText);
                    }
                } else {
                    if(!isJobEquList) {
                        Toast.makeText(this, LanguageController.getInstance().getMobileMsgByKey(AppConstant.eqi_not_foun_txt), Toast.LENGTH_SHORT).show();
                        onResume();
                    }else {
                        String equip = new Gson().toJson(jobList.get(0));
                        findEquipment(equip,codeText);
                    }
                }
            } else {
                if(!isJobEquList) {
                    /** If Equipment link with Job */
                    Intent intent = new Intent(this, EquipmentDetailsActivity.class);
                    String jobData = new Gson().toJson(jobList);
                    String auditData = new Gson().toJson(list);
                    intent.putExtra("jobids", s);
                    intent.putExtra("AUDITDATA", auditData);
                    intent.putExtra("codetext", codeText);
                    if (comeFrom != null && comeFrom.equalsIgnoreCase("linkFromEquList")) {
                        intent.putExtra("found_or_not", "equipmentFound");
                    }
                    startActivityForResult(intent, 100);
                }else {
                    String equip = new Gson().toJson(jobList.get(0));
                    findEquipment(equip,codeText);
                }
            }
        } else if (isJobEquList) {
            String equip = "";
            findEquipment(equip,codeText);
        } else {
            Toast.makeText(this, LanguageController.getInstance().getMobileMsgByKey(AppConstant.eqi_not_foun_txt), Toast.LENGTH_SHORT).show();
            onResume();
        }
    }


    @Override
    public void Setjobequipment(List<EquArrayModel> equilist) {
        this.jobList = equilist;
    }

    /*create search request*/
    private void searchEquipment(String barcode, boolean isScannerValue) {

        if (AppUtility.isInternetConnected()) {
            scanBarcode_pc.equipmentbarcode(barcode, isScannerValue);
        } else {
            jobList.clear();
            List<Job> jobList1 = AppDataBase.getInMemoryDatabase(this).jobModel().getEqupByBarcode("%" + barcode + "%");
            if (jobList1.size() > 0) {
                outerloop:
                for (Job item : jobList1) {
                    for (EquArrayModel item1 : item.getEquArray()) {
                        if (item1.getBarcode().equals(barcode) || item1.getSno().equals(barcode) || item1.getQrcode().equals(barcode)) {
                            jobList.add(item1);
                            break outerloop;
                        }
                    }
                }
                Log.e("List Size", "" + jobList.size());
                onAudiListCall(barcode);
            } else {
                onAudiListCall(barcode);
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            if (data != null)
                //  if (data.hasExtra("data"))
                if (data.hasExtra("AUDITDATA")) handleAuditIntent(data);
                    //   else if (data.hasExtra("jobdata"))
                else if (data.hasExtra("JOBDATA")) handleJobIntent(data);
        }
    }

    private void handleJobIntent(Intent data) {
        if (data.hasExtra("JOBDATA")) {
            // List<Job> list = (List<Job>) data.getExtras().get("jobdata");
            List<String> list = new ArrayList<>();
            String str = "";
            try {
                Type listType = new TypeToken<List<String>>() {
                }.getType();
                list = new Gson().fromJson(data.getExtras().getString("JOBDATA"), listType);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
            if (list != null && list.size() == 1) {
                Job jobsById = AppDataBase.getInMemoryDatabase(this).jobModel().getJobsById(list.get(0));
                str = new Gson().toJson(jobsById);
                List<EquArrayModel> equArray = jobsById.getEquArray();
                if (equArray != null) {
                    for (EquArrayModel equipment : equArray) {
                        if (equipment.getSno() != null && equipment.getSno().equals(codeText) || equipment.getBarcode() != null && equipment.getBarcode().equals(codeText) || equipment.getQrcode() != null && equipment.getQrcode().equals(codeText)) {

                            Intent intent = new Intent(this, JobEquRemarkRemarkActivity.class);
                            String strEqu = new Gson().toJson(equipment);
                            intent.putExtra("equipment", strEqu);
                            intent.putExtra("jobId", jobsById.getJobId());
                            intent.putExtra("JOBDATA", str);
                            startActivity(intent);
                            finish();
                        }
                    }
                }
            } else {
                Intent intent = new Intent();
                intent.putExtra("code", codeText);
                intent.putExtra("JOBDATA", data.getExtras().getString("JOBDATA"));
                setResult(RESULT_OK, intent);
                finish();
            }
        }
    }

    private void handleAuditIntent(Intent data) {
        if (data.hasExtra("AUDITDATA")) {
            // List<AuditList_Res> list = (List<AuditList_Res>) data.getExtras().get("data");
            List<AuditList_Res> list = new ArrayList<>();
            String str = "";
            try {
                str = data.getExtras().getString("AUDITDATA");
                Type listType = new TypeToken<List<AuditList_Res>>() {
                }.getType();
                list = new Gson().fromJson(str, listType);
            } catch (Exception exception) {
                exception.printStackTrace();
            }

            if (list != null && list.size() == 1) {
                List<Equipment_Res> equArray = list.get(0).getEquArray();
                if (equArray != null) {
                    for (Equipment_Res equipment : equArray) {
                        if (equipment.getSno() != null && equipment.getSno().equals(codeText) || equipment.getBarcode() != null && equipment.getBarcode().equals(codeText) || equipment.getQrcode() != null && equipment.getQrcode().equals(codeText)) {
                            String strEqu = new Gson().toJson(equipment);
                            startActivity(new Intent(this, RemarkActivity.class).putExtra("equipment", strEqu).putExtra("AUDITDATA", str));
                            finish();
                        }
                    }

                }
            } else {
                Intent intent = new Intent();
                intent.putExtra("code", codeText);
                intent.putExtra("AUDITDATA", str);
                setResult(RESULT_OK, intent);
                finish();
            }


        }
    }

    public void startGoogleScan() {

        scanner.startScan().addOnSuccessListener(barcode -> {
            Log.e("ScanResult", barcode.getRawValue());
//                                    Toast.makeText(BarcodeScanActivity.this, "Searching...", Toast.LENGTH_SHORT).show();
            if (comeFrom != null && comeFrom.equalsIgnoreCase("AddEquipment")) {
                Intent intent = new Intent();
                intent.putExtra("code", barcode.getRawValue());
                setResult(RESULT_OK, intent);
                finish();
            } else {
                codeText = barcode.getRawValue();
                isSearching = true;
                isScannerValue = true;
                searchEquipment(barcode.getRawValue(), isScannerValue);
            }
        }).addOnCanceledListener(() -> {
            Log.e("ScanResult", "Cancle");
            finish();
        }).addOnFailureListener(e -> {
            isSearching = true;
            Log.e("ScanResult", "error = " + e.getMessage());
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
/**
 * Scan code from job equipment list and move back to equipment list screen   */
    public void findEquipment(String Equipment, String scanCode){

        Intent intent = new Intent();
        intent.putExtra("equipment", Equipment);
        intent.putExtra("codetext", scanCode);
        setResult(JobEquipmentActivity.SCANNER_DETAIL_CODE,intent);
        finish();
    }

}
