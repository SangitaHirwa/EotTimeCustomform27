package com.eot_app.nav_menu.drag_and_drop_functinality;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.TextView;

import com.eot_app.R;
import com.eot_app.databinding.ActivityTakePictureForDragDropBinding;
import com.eot_app.eoteditor.Utils;
import com.eot_app.nav_menu.jobs.job_card_view.JobCardViewActivity;
import com.eot_app.nav_menu.jobs.job_detail.documents.ActivityDocumentSaveUpload;
import com.eot_app.nav_menu.jobs.job_detail.documents.PathUtils;
import com.eot_app.nav_menu.audit.audit_list.documents.doc_model.GetFileList_Res;
import com.eot_app.nav_menu.jobs.job_detail.documents.doc_model.Attachments;
import com.eot_app.nav_menu.jobs.job_detail.documents.fileattach_mvp.Doc_Attch_View;
import com.eot_app.utility.AppConstant;
import com.eot_app.utility.AppUtility;
import com.eot_app.utility.CompressImageInBack;
import com.eot_app.utility.EotApp;
import com.eot_app.utility.language_support.LanguageController;
import com.eot_app.utility.util_interfaces.OnImageCompressed;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Callable;

public class Take_Picture_For_Drag_Drop extends AppCompatActivity implements View.OnClickListener, Doc_Attch_View {

    ActivityTakePictureForDragDropBinding binding;
    private final int ATTACHFILE_CODE = 101;
    private final int CAMERA_CODE = 100;
    private final static int CAPTURE_IMAGE_GALLARY = 222;

    private String captureImagePath;
    CompressImageInBack compressImageInBack = null;
    private boolean isFileImage= false;
    String bitmapString;
    Boolean isImage;
    Uri uriForMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

          binding = DataBindingUtil.setContentView(this, R.layout.activity_take_picture_for_drag_drop);
          initiView();
    }

    private void initiView() {
        binding.tvBack.setOnClickListener(this);
        binding.tvTakePicture.setOnClickListener(this);
        binding.tvDropOnMap.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_back:
                onBackPressed();
                break;
            case R.id.tv_take_picture:
              selectFiles();
                break;
            case R.id.tv_drop_on_map:
                sendImageForMap();
                break;
        }
    }

    private void sendImageForMap() {
        if(uriForMap!=null) {
            Intent intent = new Intent(Take_Picture_For_Drag_Drop.this, Drop_Item_On_Map_Activity.class);
            intent.putExtra("uri", uriForMap.toString());
            startActivity(intent);
        }else {
            AppUtility.alertDialog(Take_Picture_For_Drag_Drop.this,
                    LanguageController.getInstance().getMobileMsgByKey("Take A image"),
                    LanguageController.getInstance().getMobileMsgByKey("do not switch other page without select a image, so please take a picture"),
                    LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok), "", new Callable<Boolean>() {
                @Override
                public Boolean call() throws Exception {
                    return null;
                }
            });
        }
    }


    private void takePictureFromCamera() {

        if (!AppUtility.askCameraTakePicture(Take_Picture_For_Drag_Drop.this)) {
            return;
        }
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File path = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Eot Directory");

        if (!path.exists()) {
            path.mkdir();
        }

        File imageFile = null;
        try {
            imageFile = createImageFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Uri uri = FileProvider.getUriForFile(Take_Picture_For_Drag_Drop.this, Take_Picture_For_Drag_Drop.this.getApplicationContext().getPackageName() + ".provider", imageFile);

        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        takePictureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);

        List<ResolveInfo> resInfoList = Take_Picture_For_Drag_Drop.this.getPackageManager().queryIntentActivities(takePictureIntent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo resolveInfo : resInfoList) {
            String packageName = resolveInfo.activityInfo.packageName;
            Take_Picture_For_Drag_Drop.this.grantUriPermission(packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        startActivityForResult(takePictureIntent, CAMERA_CODE); // IMAGE_CAPTURE = 0
    }
    private void takeimageFromGalary() {
        //allow upload file extension
        String[] mimeTypes = {"image/jpeg", "image/jpg", "image/png",
                "application/msword", "application/vnd.openxmlformats-officedocument.wordprocessingml.document",//.doc & .docx
                "application/vnd.ms-excel", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",//.xls & .xlsx
                "application/pdf",//pdf
                "text/csv", "text/plain"//csv
        };

        /* *only for document uploading */
        Intent documentIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        documentIntent.addCategory(Intent.CATEGORY_OPENABLE);
        documentIntent.setType("*/*");
        documentIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        startActivityForResult(documentIntent, ATTACHFILE_CODE);
    }
    private File createImageFile() throws IOException {


        Calendar calendar = Calendar.getInstance();
        long imageFileName = calendar.getTime().getTime();
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Eot Directory");

        File directoryPath = new File(storageDir.getPath());
        File image = File.createTempFile(
                String.valueOf(imageFileName),  /* prefix */
                ".jpg",         /* suffix */
                directoryPath   /* directory */
        );
        captureImagePath = image.getAbsolutePath();
        return new File(image.getPath());
    }
    private void getImageFromGallray() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, CAPTURE_IMAGE_GALLARY);
    }
    private void askTedPermission(int type,String[] permissions) {
        TedPermission.with(EotApp.getAppinstance())
                .setPermissionListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        if (type == 2)
                            takeimageFromGalary();
                    }

                    @Override
                    public void onPermissionDenied(ArrayList<String> deniedPermissions) {

                    }
                })
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [SettingActivity] > [Permission]")
                .setPermissions(permissions)
                .check();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            /*case DOUCMENT_UPLOAD_CODE:
                if (resultCode == RESULT_OK)
                    if (doc_attch_pi != null&&data!=null) {
                        String fileNameExt = AppUtility.getFileNameWithExtension(data.getStringExtra("imgPath"));
                        String bitmapString="";
                        if(data.getBooleanExtra("isFileImage",false)){
                            Bitmap bitmap = AppUtility.getBitmapFromPath(data.getStringExtra("imgPath"));
                            bitmapString = AppUtility.BitMapToString(bitmap);
                        }
                        GetFileList_Res obj=new GetFileList_Res("0",fileNameExt,fileNameExt,bitmapString);
                        ArrayList<GetFileList_Res> getFileList_res =new ArrayList<>();
                        if (fileList_res != null) {
                            getFileList_res.addAll(fileList_res);
                        }
                        getFileList_res.add(obj);

                        setList(getFileList_res, "",true);

                        if(data.getStringExtra("fileName")!=null){
                            try
                            {
                                doc_attch_pi.uploadDocuments(jobId, data.getStringExtra("imgPath"),
                                        data.getStringExtra("fileName"),
                                        data.getStringExtra("desc"),
                                        data.getStringExtra("type") ,
                                        data.getStringExtra("isFromCmpletion"));
                            }
                            catch (Exception e)
                            {
                                if (getFileList_res.size()==1) {
                                    fileList_res.remove(getFileList_res.get(0));
                                    setList(fileList_res, "",true);
                                }
                                e.printStackTrace();
                            }
                        }
                    }
                break;*/
            case CAMERA_CODE:
                if (resultCode == RESULT_OK) {
                    try {
                        //get uri from current created path
                        if(captureImagePath!=null) {
                            File file = AppUtility.scaleToActualAspectRatio(captureImagePath, 1024f, 1024f);
                            if (file != null)
                                imageEditing(Uri.fromFile(file), true);

                        }  isImage=true;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    return;
                }

                break;
            case CAPTURE_IMAGE_GALLARY:
                if (resultCode == RESULT_OK) {
                    Uri galreyImguriUri = data.getData();
                    //   String gallery_image_Path = com.eot_app.nav_menu.jobs.job_detail.documents.PathUtils.getPath(getActivity(), galreyImguriUri);
                    String gallery_image_Path = com.eot_app.nav_menu.jobs.job_detail.documents.PathUtils.getRealPath(Take_Picture_For_Drag_Drop.this, galreyImguriUri);
                    String img_extension = gallery_image_Path.substring(gallery_image_Path.lastIndexOf("."));
                    /******('jpg','png','jpeg','pdf','doc','docx','xlsx','csv','xls'); supporting extensions*/
                    if (img_extension.equals(".jpg") || img_extension.equals(".png") || img_extension.equals(".jpeg")) {
                        imageEditing(data.getData(), true);
                        isImage=true;
                    } else {
                        isImage=false;
                        uploadDocumentsJobCard(gallery_image_Path);
                    }
                } else {
                    return;
                }
                break;
            case ATTACHFILE_CODE:
                if (resultCode == RESULT_OK) {
                    try {
                        Uri galreyImguriUri = data.getData();
                        //  String gallery_image_Path = PathUtils.getPath(getActivity(), galreyImguriUri);
                        String gallery_image_Path = PathUtils.getRealPath(this, galreyImguriUri);
                        String img_extension = gallery_image_Path.substring(gallery_image_Path.lastIndexOf("."));
                        //('jpg','png','jpeg','pdf','doc','docx','xlsx','csv','xls'); supporting extensions
                        if (img_extension.equals(".jpg") || img_extension.equals(".png") || img_extension.equals(".jpeg")) {
                            imageEditing(data.getData(), true);
                            isImage=true;
                        } else {
                            isImage=false;
                            uploadDocumentsJobCard(gallery_image_Path);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    return;
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void uploadDocumentsJobCard(String galleryImagePath) {
        int fileIcons = getFileIcons(galleryImagePath);
        binding.selectdPicture.setImageResource(fileIcons);


    }
    private int getFileIcons(String serverFilePath) {
        int resId = 0;
        String ext = serverFilePath.substring((serverFilePath.lastIndexOf(".")) + 1);

        if (!ext.isEmpty()) {
            if (ext.equals("doc") || ext.equals("docx")) {
                resId = R.drawable.word;
            } else if (ext.equals("pdf")) {
                resId = R.drawable.pdf;
            } else if (ext.equals("xlsx") || ext.equals("xls")) {
                resId = R.drawable.excel;

            } else if (ext.equals("csv")) {
                resId = R.drawable.csv;
            } else {
                resId = R.drawable.doc;
            }
        }
        return resId;
    }


    private void imageEditing(Uri uri1, boolean isImage) {
        uriForMap = uri1;
//        img_doc.setImageURI(uri);
        compressImageInBack = new CompressImageInBack(this, new OnImageCompressed() {
            @Override
            public void onImageCompressed(Bitmap bitmap) {
                String savedImagePath = compressImageInBack.getSavedImagePath();
                if (bitmap != null) {
                    bitmapString=AppUtility.BitMapToString(bitmap);
                    binding.selectdPicture.setImageBitmap(bitmap);
                }
            }
        }, uri1);
        compressImageInBack.setSaveBitmap(true);
        //  compressImageInBack.execute(uri);
        compressImageInBack.compressImageInBckg();
        isFileImage = true;


    }


    @Override
    public void selectFiles()
        {
            if (!Utils.isOnline(Take_Picture_For_Drag_Drop.this)) {

                AppUtility.alertDialog(Take_Picture_For_Drag_Drop.this, LanguageController.getInstance().getMobileMsgByKey(AppConstant.dialog_error_title),  LanguageController.getInstance().getMobileMsgByKey(AppConstant.feature_not_available), LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok), "", new Callable<Boolean>() {
                    @Override
                    public Boolean call() throws Exception {
                        return null;
                    }
                });
            } else {
                final BottomSheetDialog dialog = new BottomSheetDialog(Take_Picture_For_Drag_Drop.this);
                dialog.setContentView(R.layout.bottom_image_chooser);
                TextView camera = dialog.findViewById(R.id.camera);
                camera.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.camera));
                TextView gallery = dialog.findViewById(R.id.gallery);
                gallery.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.gallery));
                TextView drive_document = dialog.findViewById(R.id.drive_document);
                drive_document.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.document));
                camera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (AppUtility.askCameraTakePicture(Take_Picture_For_Drag_Drop.this)) {
                            takePictureFromCamera();
                        }else {
                            // Sdk version 33
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU ) {
                                askTedPermission(0, AppConstant.cameraPermissions33);
                            }else {
                                askTedPermission(0, AppConstant.cameraPermissions);
                            }
                        }
                        dialog.dismiss();
                    }
                });

                gallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (AppUtility.askGalaryTakeImagePermiSsion(Take_Picture_For_Drag_Drop.this)) {
                            getImageFromGallray();
                        }else {
                            // Sdk version 33
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU ) {
                                askTedPermission(1, AppConstant.galleryPermissions33);
                            }else {
                                askTedPermission(1, AppConstant.galleryPermissions);
                            }
                        }
                        dialog.dismiss();
                    }
                });

                drive_document.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (AppUtility.askGalaryTakeImagePermiSsion(Take_Picture_For_Drag_Drop.this)) {
                            takeimageFromGalary();//only for drive documents
                        }else {
                            // Sdk version 33
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU ) {
                                askTedPermission(2, AppConstant.galleryPermissions33);
                            }else {
                                askTedPermission(2, AppConstant.galleryPermissions);
                            }
                        }
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }


    }

    @Override
    public void selectFilesForCompletion(boolean isCompletion) {

    }

    @Override
    public void setList(ArrayList<Attachments> getFileList_res, String isAttachCompletionNotes, boolean firstCall) {

    }

    @Override
    public void setMultiList(ArrayList<Attachments> getFileList_res, String isAttachCompletionNotes, boolean firstCall, int parentPosition, int position, String queId, String jtId) {

    }

    @Override
    public void addNewItemToAttachmentList(ArrayList<Attachments> getFileList_res, String isAttachCompletionNotes) {

    }

    @Override
    public void addView() {

    }

    @Override
    public void onSessionExpire(String msg) {

    }

    @Override
    public void fileExtensionNotSupport(String msg) {

    }

    @Override
    public void onDocumentUpdate(String msg, boolean isSuccess) {

    }

    @Override
    public void hideProgressBar() {

    }

    @Override
    public void showProgressBar() {

    }
}