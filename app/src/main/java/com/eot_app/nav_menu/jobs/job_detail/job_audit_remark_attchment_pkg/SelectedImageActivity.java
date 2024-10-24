package com.eot_app.nav_menu.jobs.job_detail.job_audit_remark_attchment_pkg;

import static com.eot_app.nav_menu.jobs.job_detail.job_equipment.job_equ_remrk.JobEquRemarkRemarkActivity.SINGLEATTCHMENT;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eot_app.R;
import com.eot_app.UploadDocumentActivity;
import com.eot_app.nav_menu.jobs.job_detail.documents.doc_model.GetFileList_Res;
import com.eot_app.nav_menu.jobs.job_detail.job_audit_remark_attchment_pkg.model_pkg.JobAuditSingleAttchReqModel;
import com.eot_app.nav_menu.jobs.job_detail.job_audit_remark_attchment_pkg.mvp.JobAudit_PI;
import com.eot_app.nav_menu.jobs.job_detail.job_audit_remark_attchment_pkg.mvp.JobAudit_Pc;
import com.eot_app.nav_menu.jobs.job_detail.job_audit_remark_attchment_pkg.mvp.JobAudit_View;
import com.eot_app.utility.AppConstant;
import com.eot_app.utility.AppUtility;
import com.eot_app.utility.language_support.LanguageController;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class SelectedImageActivity extends UploadDocumentActivity implements
        JobAudit_View, View.OnClickListener, RadioGroup.OnCheckedChangeListener {


    JobAuditSingleAttchReqModel model;
    JobAudit_PI jobAuditPi;
    private boolean isTagSet = false;
    private RadioGroup rediogrp;
    private RadioButton radio_before, radio_after;
    private RelativeLayout image_with_tag;
    private TextView image_txt, chip_txt;//, remove_txt;
    ImageView deleteChip;
    private View chip_layout;
    private EditText ed_doc_title;
    private ImageView img_attachment,et_doc_title_cancel;
    private Button button_submit;
    boolean isImage=false;
    private String  image_name="";
    private String nameExt="";
    private String nameWithoutExt="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_image);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        initializeViews();
        Intent intent = getIntent();
        if (intent.hasExtra("attchment")) {
            String str = intent.getExtras().getString("attchment");
            image_name = intent.getExtras().getString("image_name");
            String[] split = image_name.split("\\.");
            nameWithoutExt=split[0];
            nameExt=split[1];
            model = new Gson().fromJson(str, JobAuditSingleAttchReqModel.class);

            setImageView();
        }
    }

    private void initializeViews() {
        button_submit = findViewById(R.id.button_submit);
        img_attachment = findViewById(R.id.img_attachment);
        image_with_tag = findViewById(R.id.image_with_tag);
        image_txt = findViewById(R.id.image_txt);
        ed_doc_title=findViewById(R.id.et_doc_title);
        et_doc_title_cancel=findViewById(R.id.et_doc_title_cancel);
        et_doc_title_cancel.setOnClickListener(this);

        radio_before = findViewById(R.id.radio_before);
        radio_before.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.before));
        radio_before.setChecked(false);

        radio_after = findViewById(R.id.radio_after);
        radio_after.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.after));
        radio_after.setChecked(false);


        chip_txt = findViewById(R.id.chip_txt);
        deleteChip = findViewById(R.id.deleteChip);
        chip_layout = findViewById(R.id.chip_layout);
        deleteChip.setOnClickListener(this);
        button_submit.setOnClickListener(this);
        rediogrp = findViewById(R.id.rediogrp);
        rediogrp.setOnCheckedChangeListener(this);
        jobAuditPi = new JobAudit_Pc(this);

    }

    @Override
    public void onDocumentSelected(String path, String name, boolean isImage) {

    }

    private void setImageView() {
        button_submit.setText(model.getSetButtontxt());
        setTitle(model.getSetTile());
        img_attachment.setVisibility(View.VISIBLE);
//        img_attachment.setImageURI(Uri.fromFile(new File(model.getPath())));
        ed_doc_title.setText(nameWithoutExt);
        ed_doc_title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                et_doc_title_cancel.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        imageShowInView();

        removeTagData();
    }

    private void imageShowInView() {
        try {
            rediogrp.setVisibility(View.GONE);
            String[] separated = model.getPath().split("\\.");
            if (separated[separated.length - 1] != null) {
                String ext = separated[separated.length - 1].toLowerCase();
                if (!ext.isEmpty()) {
                    if (ext.equals("jpg") || ext.equals("jpeg") || ext.equals("png")) {
                        isImage=true;
                        rediogrp.setVisibility(View.VISIBLE);
                        img_attachment.setImageURI(Uri.fromFile(new File(model.getPath())));
                    } else if (ext.equals("doc") || ext.equals("docx")) {
                        img_attachment.setImageResource(R.drawable.word);
                        img_attachment.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

                    } else if (ext.equals("pdf")) {
                        img_attachment.setImageResource(R.drawable.pdf);
                        img_attachment.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

                    } else if (ext.equals("xlsx") || ext.equals("xls")) {
                        img_attachment.setImageResource(R.drawable.excel);
                        img_attachment.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

                    } else if (ext.equals("csv")) {
                        img_attachment.setImageResource(R.drawable.csv);
                        img_attachment.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                    } else {
                        img_attachment.setImageResource(R.drawable.doc);
                        img_attachment.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                    }
                }
            } else {
                img_attachment.setImageURI(Uri.fromFile(new File(model.getPath())));
            }
        } catch (Exception exception) {
            img_attachment.setImageURI(Uri.fromFile(new File(model.getPath())));
            exception.printStackTrace();
        }

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radio_before:
                isTagSet = true;
                image_txt.setVisibility(View.VISIBLE);
                image_txt.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.before));
                rediogrp.setVisibility(View.GONE);
                chip_layout.setVisibility(View.VISIBLE);
                chip_txt.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.before));

                break;
            case R.id.radio_after:
                isTagSet = true;
                image_txt.setVisibility(View.VISIBLE);
                image_txt.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.after));
                rediogrp.setVisibility(View.GONE);
                chip_layout.setVisibility(View.VISIBLE);
                chip_txt.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.after));
                break;
        }

    }

    private void removeTagData() {
        radio_after.setChecked(false);
        radio_before.setChecked(false);
        isTagSet = false;
        image_txt.setVisibility(View.GONE);
        image_txt.setText("");
        //  rediogrp.setVisibility(View.VISIBLE);
        chip_layout.setVisibility(View.GONE);
        chip_txt.setText("");

    }

    @Override
    public void onClickContinuarEvent(Uri uri) {

    }

    private String saveBitMap(View drawView) {

        File pictureFileDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        String filename = pictureFileDir.getPath() + File.separator + System.currentTimeMillis() + ".jpg";
        File pictureFile = new File(filename);
        Bitmap bitmap = getBitmapFromView(drawView);
        try {
            pictureFile.createNewFile();
            FileOutputStream oStream = new FileOutputStream(pictureFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, oStream);
            oStream.flush();
            oStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.i("TAG", "There was an issue saving the image.");
        }

        return filename;
    }

    private Bitmap getBitmapFromView(View view) {

        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null) {
            bgDrawable.draw(canvas);
        } else {
            canvas.drawColor(Color.WHITE);
        }

        view.draw(canvas);
        return returnedBitmap;
    }

    private void processImageAndUpload() {
        model.setPath(saveBitMap(image_with_tag));
        try {

            Intent intent = new Intent();
            intent.putExtra("code", model);
            intent.putExtra("isImage", isImage);
            if(!ed_doc_title.getText().toString().equals(""))
            intent.putExtra("image_name",ed_doc_title.getText().toString()+"."+nameExt);
            else intent.putExtra("image_name",image_name);
            setResult(SINGLEATTCHMENT, intent);
            this.finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        jobAuditPi.uploadAttchmentOnserverForJobAudit(model);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_submit:
                if(!ed_doc_title.getText().toString().equals("")) {
                    if (isTagSet) {
                        processImageAndUpload();
                    } else  {
                        Intent intent = new Intent();
                        intent.putExtra("code", model);
                        intent.putExtra("isImage", isImage);
                        intent.putExtra("image_name",ed_doc_title.getText().toString()+"."+nameExt);
                        setResult(SINGLEATTCHMENT, intent);
                        this.finish();
                    }

                }else{
                    if (isTagSet) {
                        processImageAndUpload();
                    } else  {
                        Intent intent = new Intent();
                        intent.putExtra("code", model);
                        intent.putExtra("isImage", isImage);
                        intent.putExtra("image_name",image_name);
                        setResult(SINGLEATTCHMENT, intent);
                        this.finish();
                    }
                }

                break;
            case R.id.deleteChip:
                isTagSet = false;
                image_txt.setText("");
                image_txt.setVisibility(View.GONE);
                chip_layout.setVisibility(View.GONE);
                rediogrp.setVisibility(View.VISIBLE);
                radio_after.setChecked(false);
                radio_before.setChecked(false);
                break;
            case R.id.et_doc_title_cancel:
                ed_doc_title.setText("");
                et_doc_title_cancel.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onErrorMsg(String s) {

    }

    @Override
    public void onSessionExpire(String message) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();

        return true;
    }

    @Override
    public void attchmentUpload(String convert) {
        Intent intent = new Intent();
        intent.putExtra("code", convert);
        setResult(SINGLEATTCHMENT, intent);
        this.finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(777, intent);
        this.finish();
    }
}