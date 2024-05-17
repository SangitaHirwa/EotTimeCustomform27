package com.eot_app.nav_menu.jobs.job_detail.documents;


import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.ext.SdkExtensions;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.eot_app.R;
import com.eot_app.nav_menu.jobs.job_detail.detail.DetailFragment;
import com.eot_app.nav_menu.jobs.job_detail.documents.doc_model.Attachments;
import com.eot_app.nav_menu.jobs.job_detail.documents.doc_model.CompressImg;
import com.eot_app.nav_menu.jobs.job_detail.documents.doc_model.NotifyForMultiDocAddForAttach;
import com.eot_app.nav_menu.jobs.job_detail.documents.fileattach_mvp.Doc_Attch_Pc;
import com.eot_app.nav_menu.jobs.job_detail.documents.fileattach_mvp.Doc_Attch_Pi;
import com.eot_app.nav_menu.jobs.job_detail.documents.fileattach_mvp.Doc_Attch_View;
import com.eot_app.nav_menu.jobs.job_detail.documents.work_manager.UploadMultiImgWorker;
import com.eot_app.nav_menu.jobs.job_detail.form_form.get_qus_list.qus_model.QuesRspncModel;
import com.eot_app.services.Service_apis;
import com.eot_app.utility.AppCenterLogs;
import com.eot_app.utility.AppConstant;
import com.eot_app.utility.AppUtility;
import com.eot_app.utility.App_preference;
import com.eot_app.utility.EotApp;
import com.eot_app.utility.db.AppDataBase;
import com.eot_app.utility.db.OfflineDataController;
import com.eot_app.utility.language_support.LanguageController;
import com.eot_app.utility.language_support.Language_Preference;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.hypertrack.hyperlog.HyperLog;

import org.checkerframework.checker.units.qual.A;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import ja.burhanrashid52.photoeditor.OnPhotoEditorListener;
import ja.burhanrashid52.photoeditor.ViewType;

public class DocumentsFragment extends Fragment implements Doc_Attch_View, DocumentListAdapter.FileDesc_Item_Selected, OnPhotoEditorListener, NotifyForMultiDocAddForAttach,OtherDocumentListAdapter.FileDesc_Item_Selected {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private final static int CAPTURE_IMAGE_GALLARY = 222;
    private static final int DOUCMENT_UPLOAD_CODE = 156;
    private final int CAMERA_CODE = 100;
    private final int ATTACHFILE_CODE = 101;
    RecyclerView fileupload_rc, rv_otherFileUpload;
    Doc_Attch_Pi doc_attch_pi;
    RecyclerView.LayoutManager layoutManager,layoutManager1;
    LinearLayout nodoc_linear;
    private String captureImagePath;
    TextView noDocList, txt_lblOther, txt_userNote;
    private DocumentListAdapter documentListAdapter;
    private OtherDocumentListAdapter otherDocumentListAdapter;
    // TODO: Rename and change types of parameters
    String mParam1;
    private String jobId;
    private EditText edtSearch;
    private ImageView imvCross;
    private String query = "";
    private ArrayList<Attachments> fileList_res = new ArrayList<>();
    private EditImageDialog currentDialog = null;
    private SwipeRefreshLayout swipeRefreshLayout;
    private WorkManager mWorkManager;
    String queId ="", tempId ="";
    ConstraintLayout cl_parentOthersAttach;
    String permissionMsg ="";

    public DocumentsFragment() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DocumentsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DocumentsFragment newInstance(String param1, String param2) {
        DocumentsFragment fragment = new DocumentsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public void updateDocList() {
        if (doc_attch_pi != null && documentListAdapter != null) {
            doc_attch_pi.getMultiAttachFileList(jobId,  App_preference.getSharedprefInstance().getLoginRes().getUsrId(), "",true,-1,-1,"0","0",true,true);
//            doc_attch_pi.getAttachFileList(jobId, "", "",true);
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            jobId = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_documents, container, false);
        setHasOptionsMenu(true);
        doc_attch_pi = new Doc_Attch_Pc(this);
        initiliazeView(view);
        //  storageUserPermission();
        layoutManager = new GridLayoutManager(getActivity(), 2);
        layoutManager1 = new GridLayoutManager(getActivity(), 2);
        fileupload_rc.setLayoutManager(layoutManager);
        rv_otherFileUpload.setLayoutManager(layoutManager1);
        initializeAdapter();

//        doc_attch_pi.getAttachFileList(jobId, "", "",true);
//        if(mParam1.equalsIgnoreCase("yes")) {
//            doc_attch_pi.getMultiAttachFileList(jobId, App_preference.getSharedprefInstance().getLoginRes().getUsrId(), "", true, -1, -1, "0", "0", true, true);
//        }
        List<QuesRspncModel> queList = App_preference.getSharedprefInstance().getJobCompletionFormFields();
        if(queList!= null && queList.size()>0) {
            for (QuesRspncModel item : queList
            ) {
                if (item.getIsLinkWithService().equalsIgnoreCase("0") && item.getType().equals("13") && item.getJtId().isEmpty()) {
                    queId = item.getQueId();
                    break;
                }
            }
        }
//        setList((ArrayList<Attachments>) AppDataBase.getInMemoryDatabase(getActivity()).attachments_dao().getAllAttachmentsOfJob(jobId),"",true);
        return view;
    }

    private void initializeAdapter() {
        ArrayList<Attachments> getFileList_res = new ArrayList<>();
        documentListAdapter = new DocumentListAdapter(this, new ArrayList<Attachments>(), jobId,this);
        otherDocumentListAdapter = new OtherDocumentListAdapter(this, new ArrayList<Attachments>(), jobId,this);

        fileupload_rc.setAdapter(documentListAdapter);
        rv_otherFileUpload.setAdapter(otherDocumentListAdapter);
    }

    private void initiliazeView(View view) {
        fileupload_rc = view.findViewById(R.id.fileupload_rc);
        rv_otherFileUpload = view.findViewById(R.id.rv_otherFileUpload);
        cl_parentOthersAttach = view.findViewById(R.id.cl_parentOthersAttach);
        txt_userNote = view.findViewById(R.id.txt_userNote);
        txt_userNote.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.offline_attachment_notes));
        txt_lblOther = view.findViewById(R.id.txt_lblOther);
        txt_lblOther.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.others));
        nodoc_linear = view.findViewById(R.id.nodoc_linear);
        noDocList = view.findViewById(R.id.noDocList);
        swipeRefreshLayout = view.findViewById(R.id.swiperefresh);
        noDocList.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.documet_appear));

        FloatingActionButton fab = view.findViewById(R.id.doc_att);
        fab.setOnClickListener(view1 -> selectFiles());

        imvCross = view.findViewById(R.id.imvCross);
        edtSearch = view.findViewById(R.id.edtSearch);

        edtSearch.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.search));

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                query = edtSearch.getText().toString();
                if (query != null) {
                    if (query.length() >= 1) {
                        imvCross.setVisibility(View.VISIBLE);
                    } else {
                        imvCross.setVisibility(View.GONE);
                    }
                }

                query = edtSearch.getText().toString();
                DocumentsFragment.this.documentListAdapter.getFilter().filter(s.toString());
                DocumentsFragment.this.otherDocumentListAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        imvCross.setOnClickListener(v -> edtSearch.setText(""));

        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(true);
            doc_attch_pi.getMultiAttachFileList(jobId,  App_preference.getSharedprefInstance().getLoginRes().getUsrId(), "",true,-1,-1,"0","0",true,true);
//            doc_attch_pi.getAttachFileList(jobId, "", "",true);
        });


        EotApp.getAppinstance().setNotifyForMultiDocAddForAttach(this);
    }


    void filter(String text) {
        ArrayList<Attachments> temp = new ArrayList();
        for (Attachments d : fileList_res) {
            if (d.getAttachFileActualName().toLowerCase().startsWith(text)) {
                temp.add(d);
            }
        }
        documentListAdapter.updateFileList(temp,true);
    }


    @Override
    public void onPause() {
        edtSearch.setText("");
        super.onPause();
    }


    @Override
    public void setList(ArrayList<Attachments> getFileList_res, String isAttachCompletionNotes, boolean firstCall, boolean isOnline) {
//        AppUtility.hideSoftKeyboard(getActivity());
        if (currentDialog != null) {
            currentDialog.dismiss();
            currentDialog = null;
        }
        if(isOnline) {
            this.fileList_res = getFileList_res;
            rv_otherFileUpload.setVisibility(View.VISIBLE);
            txt_userNote.setVisibility(View.GONE);
            Log.e("List", "Other List" + getFileList_res.size());
            if (getFileList_res.size() > 0) {
                nodoc_linear.setVisibility(View.GONE);
                cl_parentOthersAttach.setVisibility(View.VISIBLE);
                otherDocumentListAdapter.updateFileList(getFileList_res, firstCall);
            } else if (rv_otherFileUpload.getAdapter() != null && fileupload_rc.getAdapter() != null && fileupload_rc.getAdapter().getItemCount() == 0 && rv_otherFileUpload.getAdapter().getItemCount() == 0){
                cl_parentOthersAttach.setVisibility(View.GONE);
                nodoc_linear.setVisibility(View.VISIBLE);

            }else  {
                cl_parentOthersAttach.setVisibility(View.GONE);
                rv_otherFileUpload.setVisibility(View.GONE);
            }
        }else {
            if (rv_otherFileUpload.getAdapter() != null && fileupload_rc.getAdapter() != null && fileupload_rc.getAdapter().getItemCount() == 0 && rv_otherFileUpload.getAdapter().getItemCount() == 0){
                cl_parentOthersAttach.setVisibility(View.GONE);
                nodoc_linear.setVisibility(View.VISIBLE);

            }else {
                cl_parentOthersAttach.setVisibility(View.VISIBLE);
                rv_otherFileUpload.setVisibility(View.GONE);
                txt_userNote.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void setMultiList(ArrayList<Attachments> getFileList_res, String isAttachCompletionNotes, boolean firstCall, int parentPositon, int position, String queId, String jtId) {
        Log.e("List", "Multi List"+ getFileList_res.size());
        if (getFileList_res.size() > 0) {
            nodoc_linear.setVisibility(View.GONE);
            fileupload_rc.setVisibility(View.VISIBLE);
            documentListAdapter.updateFileList(getFileList_res,firstCall);
            DetailFragment.getInstance().setList((ArrayList<Attachments>) AppDataBase.getInMemoryDatabase(getActivity()).attachments_dao().getAttachmentsByJobId(jobId),isAttachCompletionNotes);
        } else if (fileupload_rc.getAdapter() != null && rv_otherFileUpload.getAdapter() != null && fileupload_rc.getAdapter().getItemCount() == 0 && rv_otherFileUpload.getAdapter().getItemCount() == 0) {
            nodoc_linear.setVisibility(View.VISIBLE);
            fileupload_rc.setVisibility(View.GONE);
            cl_parentOthersAttach.setVisibility(View.GONE);
        }else {
            nodoc_linear.setVisibility(View.GONE);
            fileupload_rc.setVisibility(View.GONE);
            cl_parentOthersAttach.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void addNewItemToAttachmentList(ArrayList<Attachments> getFileList_res, String isAttachCompletionNotes) {
       // remove the temporary added item for showing loader
        if (fileList_res != null&&!fileList_res.isEmpty()) {
            int position = -1;
            for (int i = 0; i < fileList_res.size(); i++) {
                if(fileList_res.get(i).getAttachmentId().equalsIgnoreCase("0")){
                    position=i;
                    break;
                }
            }
            if(position!=-1) {
                fileList_res.remove(fileList_res.get(position));
            }
        }
        // to add the new entry into existing list
        if (getFileList_res != null&&fileList_res!=null) {
            fileList_res.addAll(getFileList_res);
            setList(fileList_res, "",true,true);
        }
    }


    @Override
    public void addView() {
        nodoc_linear.setVisibility(View.VISIBLE);
        fileupload_rc.setVisibility(View.GONE);
    }



    @Override
    public void selectFiles() {
//        if (!Utils.isOnline(getActivity())) {
//
//            AppUtility.alertDialog(getActivity(), LanguageController.getInstance().getMobileMsgByKey(AppConstant.dialog_error_title),  LanguageController.getInstance().getMobileMsgByKey(AppConstant.feature_not_available), LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok), "", new Callable<Boolean>() {
//                @Override
//                public Boolean call() {
//                    return null;
//                }
//            });
//        } else {
            final BottomSheetDialog dialog = new BottomSheetDialog(getActivity());
            dialog.setContentView(R.layout.bottom_image_chooser);
            TextView camera = dialog.findViewById(R.id.camera);
            camera.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.camera));
            TextView gallery = dialog.findViewById(R.id.gallery);
            gallery.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.gallery));
            TextView drive_document = dialog.findViewById(R.id.drive_document);
            drive_document.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.document));
            camera.setOnClickListener(view -> {
                if (AppUtility.askCameraTakePicture(getActivity())) {
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
            });

            gallery.setOnClickListener(view -> {
                if (AppUtility.askGalaryTakeImagePermiSsion(getActivity())) {
                    getImageFromGallray();
                } else {
                    // Sdk version 33
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU ) {
                        askTedPermission(1, AppConstant.galleryPermissions33);
                    }else {
                        askTedPermission(1, AppConstant.galleryPermissions);
                    }
                }
                dialog.dismiss();
            });

            drive_document.setOnClickListener(view -> {
                if (AppUtility.askGalaryTakeImagePermiSsion(getActivity())) {
                    takeimageFromGalary();//only for drive documents
                }
                else {
                    // Sdk version 33
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU ) {
                        askTedPermission(2, AppConstant.galleryPermissions33);
                    }else {
                        askTedPermission(2, AppConstant.galleryPermissions);
                    }
                }
                dialog.dismiss();
            });
            dialog.show();
//        }
    }

    @Override
    public void selectFilesForCompletion(boolean isCompletion) {

    }

    private void askTedPermission(int type,String[] permissions) {
        if(type == 0){
            permissionMsg = "<b>Need Camera and Storage Permission</b><br><br>If you reject permission,you can not use this service<br><br>Please turn on permissions at [SettingActivity] > [Permission]";
        }else {
            permissionMsg = "<b>Need Storage Permission</b><br><br>If you reject permission,you can not use this service<br><br>Please turn on permissions at [SettingActivity] > [Permission]";

        }
        TedPermission.with(EotApp.getAppinstance())
                .setPermissionListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        if (type == 0)
                            takePictureFromCamera();
                        else if (type == 1)
                            getImageFromGallray();
                        else if (type == 2)
                            takeimageFromGalary();
                    }

                    @Override
                    public void onPermissionDenied(ArrayList<String> deniedPermissions) {

                    }
                })
                .setDeniedMessage(Html.fromHtml(permissionMsg))
                .setPermissions(permissions)
                .check();
    }
    private void getImageFromGallray() {
//        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
//        startActivityForResult(galleryIntent, CAPTURE_IMAGE_GALLARY);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R && SdkExtensions.getExtensionVersion(Build.VERSION_CODES.R) >= 2){

            Intent galleryIntent = new Intent(MediaStore.ACTION_PICK_IMAGES);
            galleryIntent.setType("image/*");
            galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            galleryIntent.putExtra(MediaStore.EXTRA_PICK_IMAGES_MAX,MediaStore.getPickImagesMaxLimit());
            startActivityForResult(Intent.createChooser(galleryIntent,"Select Picture"), CAPTURE_IMAGE_GALLARY);
        }else {
            Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            galleryIntent.setType("image/*");
            galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            this.startActivityForResult(galleryIntent, CAPTURE_IMAGE_GALLARY);
        }
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
//        documentIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(documentIntent, ATTACHFILE_CODE);
    }

    /**
     * get image from camera & edit & croping functinallity
     */
    private void takePictureFromCamera() {

        if (!AppUtility.askCameraTakePicture(getActivity())) {
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

        Uri uri = FileProvider.getUriForFile(getActivity(), getActivity().getApplicationContext().getPackageName() + ".provider", imageFile);

        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        takePictureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);

        List<ResolveInfo> resInfoList = getActivity().getPackageManager().queryIntentActivities(takePictureIntent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo resolveInfo : resInfoList) {
            String packageName = resolveInfo.activityInfo.packageName;
            getActivity().grantUriPermission(packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        startActivityForResult(takePictureIntent, CAMERA_CODE); // IMAGE_CAPTURE = 0
    }


    private File createImageFile() throws IOException {

        Calendar calendar = Calendar.getInstance();
        long imageFileName = calendar.getTime().getTime();
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Eot Directory");

        File directoryPath = new File(storageDir.getPath());
        //        File image = File.createTempFile(
//                String.valueOf(imageFileName),  /* prefix */
//                ".jpg",         /* suffix */
//                directoryPath   /* directory */
//        );
        File image = new File(directoryPath+"/Eot_"+imageFileName+".jpg");
        captureImagePath = image.getAbsolutePath();
        App_preference.getSharedprefInstance().setCapturePath(captureImagePath);
        HyperLog.i("UploadDocumentActivity", "captureImagePath " + captureImagePath);

        HyperLog.i("UploadDocumentActivity", "createImageFile Stop(M)");
        return new File(image.getPath());
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case DOUCMENT_UPLOAD_CODE:
                if (resultCode == RESULT_OK)
                    if (doc_attch_pi != null&&data!=null) {
                        tempId = "Attachment-"+App_preference.getSharedprefInstance().getLoginRes().getUsrId()+"-"+jobId+"-0-"+AppUtility.getCurrentMiliTiem();
                        String fileNameExt = AppUtility.getFileNameWithExtension(data.getStringExtra("imgPath"));
                        String img_extension = data.getStringExtra("imgPath").substring(data.getStringExtra("imgPath").lastIndexOf("."));
                        //('jpg','png','jpeg','pdf','doc','docx','xlsx','csv','xls'); supporting extensions
                        if (img_extension.equals(".jpg") || img_extension.equals(".png") || img_extension.equals(".jpeg")
                        ||img_extension.equals(".pdf") || img_extension.equals(".doc") || img_extension.equals(".docx")
                    ||img_extension.equals(".xlsx") || img_extension.equals(".csv") || img_extension.equals(".xls")) {
                            Attachments attachments = new Attachments(tempId, fileNameExt, fileNameExt, data.getStringExtra("imgPath"), "", "", data.getStringExtra("desc"), jobId, data.getStringExtra("type"), data.getStringExtra("imgPath"), tempId);
                            AppDataBase.getInMemoryDatabase(getActivity()).attachments_dao().insertSingleAttachments(attachments);
                            String type = "2";
                            if (data.getStringExtra("type") != null && !data.getStringExtra("type").isEmpty()) {
                                type = data.getStringExtra("type");
                            }
                            setMultiList((ArrayList<Attachments>) AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).attachments_dao().getAllAttachmentsOfJob(jobId), type, true, -1, -1, queId, jobId);

                            if (data.getStringExtra("fileName") != null) {
                                try {
                                    String _queId = "";
                                    if (data.getStringExtra("type").equals("6")) {
                                        _queId = queId;
                                    }
                                    OfflineDataController.getInstance().addInOfflineDB(Service_apis.upload_document, AppUtility.getParam(jobId, _queId, "",
                                            data.getStringExtra("imgPath"),
                                            data.getStringExtra("fileName"),
                                            data.getStringExtra("desc"),
                                            data.getStringExtra("type"),
                                            data.getStringExtra("isAttach"), true, true, -1, -1, tempId), AppUtility.getDateByFormat(AppConstant.DATE_TIME_FORMAT));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }else {
                            showImageErrorDialog(LanguageController.getInstance().getServerMsgByKey(AppConstant.invalid_extension));
                        }
                    }
                break;
            case CAMERA_CODE:

                if (resultCode == RESULT_OK) {
                    try {
                        File file = AppUtility.scaleToActualAspectRatio(App_preference.getSharedprefInstance().getCapturePath(), 1024f, 1024f);
                        if (file != null) {
                            imageEditing(Uri.fromFile(file), true);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    return;
                }

                break;

            case CAPTURE_IMAGE_GALLARY:
                if (resultCode == RESULT_OK) {
                    try {
                        if(data.getClipData() != null) {
                            boolean isMultipleImages = false;

                            Uri galreyImguriUri = data.getClipData().getItemAt(0).getUri();
                            //  String gallery_image_Path = PathUtils.getPath(getActivity(), galreyImguriUri);
                            String gallery_image_Path = PathUtils.getRealPath(getActivity().getApplicationContext(), galreyImguriUri);
                            String img_extension = gallery_image_Path.substring(gallery_image_Path.lastIndexOf("."));
                            if (data.getClipData().getItemCount() > 1) {
                                isMultipleImages = true;
                            } else {
                                isMultipleImages = false;
                            }
                            //('jpg','png','jpeg','pdf','doc','docx','xlsx','csv','xls'); supporting extensions
                            if (img_extension.equals(".jpg") || img_extension.equals(".png") || img_extension.equals(".jpeg")) {
                                if (!isMultipleImages) {
                                    imageEditing(data.getClipData().getItemAt(0).getUri(), true);
                                } else {
                                    uploadMultipleImges(data, true);
                                }
                            } else {
                                if (!isMultipleImages) {
                                    uploadFileDialog(gallery_image_Path);
                                } else {
                                    uploadMultipleImges(data, false);
                                }
                            }
                        }else {
                            try {
                                Uri galreyImguriUri = data.getData();
                                String gallery_image_Path = PathUtils.getRealPath(requireActivity(), galreyImguriUri);
                                String img_extension = gallery_image_Path.substring(gallery_image_Path.lastIndexOf("."));
                                //('jpg','png','jpeg','pdf','doc','docx','xlsx','csv','xls'); supporting extensions
                                if (img_extension.equals(".jpg") || img_extension.equals(".png") || img_extension.equals(".jpeg")) {
                                    imageEditing(data.getData(), true);
                                }else {
                                    showImageErrorDialog(LanguageController.getInstance().getServerMsgByKey(AppConstant.invalid_extension));
                                }

                            } catch (Exception e) {
                                AppCenterLogs.addLogToAppCenterOnAPIFail("JobCompletion","","onActivityResult()-->ATTACHFILE_CODE"+e.getMessage(),"JobCompletionActivity","");
                                e.printStackTrace();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
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
                        String gallery_image_Path = PathUtils.getRealPath(getActivity().getApplicationContext(), galreyImguriUri);
                        String img_extension = gallery_image_Path.substring(gallery_image_Path.lastIndexOf("."));
                        //('jpg','png','jpeg','pdf','doc','docx','xlsx','csv','xls'); supporting extensions
                        if (img_extension.equals(".jpg") || img_extension.equals(".png") || img_extension.equals(".jpeg")) {
                            imageEditing(data.getData(), true);
                        } else {
                            uploadFileDialog(gallery_image_Path);
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


    //upload image edit highlighting feature for image
    private void imageEditing(Uri uri, boolean isImage) {
        try {
            Intent intent = new Intent(getActivity(), ActivityDocumentSaveUpload.class);
            intent.putExtra("uri", uri);
            intent.putExtra("isImage", true);
            intent.putExtra("jobid", jobId);
            intent.putExtra("SAVEASCOMPLETION", false);
            startActivityForResult(intent, DOUCMENT_UPLOAD_CODE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //upload file dialog
    private void uploadFileDialog(final String selectedFilePath) {
        Intent intent = new Intent(getActivity(), ActivityDocumentSaveUpload.class);
        intent.putExtra("uri", selectedFilePath);
        intent.putExtra("isImage", false);
        intent.putExtra("jobid", jobId);
        intent.putExtra("SAVEASCOMPLETION", false);
        startActivityForResult(intent, DOUCMENT_UPLOAD_CODE);
    }

    @Override
    public void OnItemClick_Document(Attachments attachments) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(App_preference.getSharedprefInstance().getBaseURL() + "" + attachments.getAttachFileName())));
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        Log.e("", "");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.e("", "");
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSessionExpire(String msg) {
        AppUtility.alertDialog(getActivity(), LanguageController.getInstance().getMobileMsgByKey(AppConstant.dialog_error_title), msg, LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok), "",
                () -> {
                    EotApp.getAppinstance().sessionExpired();
                    return null;
                });
    }

   /* public void uploadFile(String imagePath, String imageName, String desc) {
        doc_attch_pi.uploadDocuments(jobId, imagePath, imageName, desc);
    }*/

    @Override
    public void fileExtensionNotSupport(String msg) {
        AppUtility.alertDialog(getActivity(), "", msg, LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok), "", new Callable<Boolean>() {
            @Override
            public Boolean call() {
                return null;
            }
        });
    }

    @Override
    public void onDocumentUpdate(String msg, boolean isSuccess) {

    }

    @Override
    public void hideProgressBar() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showProgressBar() {
        swipeRefreshLayout.setRefreshing(true);
    }


    @Override
    public void onEditTextChangeListener(View rootView, String text, int colorCode) {

    }

    @Override
    public void onAddViewListener(ViewType viewType, int numberOfAddedViews) {

    }

    @Override
    public void onRemoveViewListener(int numberOfAddedViews) {

    }

    @Override
    public void onRemoveViewListener(ViewType viewType, int numberOfAddedViews) {

    }

    @Override
    public void onStartViewChangeListener(ViewType viewType) {

    }

    @Override
    public void onStopViewChangeListener(ViewType viewType) {

    }

    int notSupportImgCount;
    private void uploadMultipleImges(Intent data, boolean imgPath){

        notSupportImgCount = 0;
        String [] imgPathArray = new String[data.getClipData().getItemCount()];
        JsonArray jsonArray = new JsonArray();
        ExecutorService service = Executors.newSingleThreadExecutor();

        service.execute(()->{
            for(int i =0; i<data.getClipData().getItemCount();i++) {
                Uri uri = data.getClipData().getItemAt(i).getUri();
                CompressImg compressImg = new CompressImg(requireActivity());
                String savedImagePath = compressImg.compressImage(uri.toString());
                String img_extension = savedImagePath.substring(savedImagePath.lastIndexOf("."));
                String fileNameExt = AppUtility.getFileNameWithExtension(PathUtils.getRealPath(getActivity().getApplicationContext(), uri));
                String[] fileName = fileNameExt.split("\\.");
                if(img_extension.equals(".jpg") || img_extension.equals(".png") || img_extension.equals(".jpeg")) {
                    imgPathArray[i] = PathUtils.getRealPath(getActivity(), uri);
                    tempId = "Attachment-" + App_preference.getSharedprefInstance().getLoginRes().getUsrId() + "-" + jobId + "-" + i + "-" + AppUtility.getCurrentMiliTiem();
                    Attachments attachments = new Attachments(tempId, fileNameExt, fileNameExt, imgPathArray[i], "", "", "", jobId, "2", savedImagePath, tempId);
                    AppDataBase.getInMemoryDatabase(getActivity()).attachments_dao().insertSingleAttachments(attachments);
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("name", imgPathArray[i]);
                    jsonObject.addProperty("tempId", tempId);
                    jsonArray.add(jsonObject);
                    requireActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setMultiList((ArrayList<Attachments>) AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).attachments_dao().getAllAttachmentsOfJob(jobId), "2", true, -1, -1, queId, jobId);
                        }
                    });
                }else {
                    notSupportImgCount++;
                }
            }
            requireActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(notSupportImgCount > 0){
                        String msg = LanguageController.getInstance().getServerMsgByKey(AppConstant.invalid_extension);
                        if(notSupportImgCount > 1){
                            msg = notSupportImgCount+" "+LanguageController.getInstance().getServerMsgByKey(AppConstant.invalid_extension);
                        }
                       showImageErrorDialog(msg);
                    }
                    uploadOffline(jsonArray.toString(),true,false,jobId);
                }
            });

        });



    }

    public void uploadOffline(String imgPathArray,boolean imgPath, boolean isCompNotes, String jobId){
        Data.Builder builder = new Data.Builder();

        builder.putString("imgPathArray",imgPathArray);
        builder.putBoolean("imgPath",true);
        builder.putBoolean("isCompNotes",false);
        builder.putString("jobId",jobId);
        builder.putBoolean("isAttachmentSection",true);
        builder.putString("tempId",tempId);

        mWorkManager = WorkManager.getInstance(getActivity().getApplicationContext());

        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(UploadMultiImgWorker.class)
                .setInputData(builder.build())
                .build();
        mWorkManager.enqueue(request);

        mWorkManager.getWorkInfoByIdLiveData(request.getId()).observe(this, new Observer<WorkInfo>() {
            @Override
            public void onChanged(WorkInfo workInfo) {
                if(workInfo.equals(WorkInfo.State.SUCCEEDED)){
                    Log.d("Worker", "==================== success");
                }
            }
        });
    }

    @Override
    public void updateMultiDoc(String apiName, String jobId, String type, boolean isRefreshFromApi) {
        switch (apiName) {
            case Service_apis.upload_document:
                         if(doc_attch_pi != null) {
                             doc_attch_pi.getMultiAttachFileList(jobId,  App_preference.getSharedprefInstance().getLoginRes().getUsrId(), type,true,-1,-1,"0","0",isRefreshFromApi,true);
                         }
                            break;
                         }
    }
    public void showErrorDialog(String msg) {
        AppUtility.error_Alert_Dialog(getActivity(), msg, LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok)
                , () -> null);
    }
    public void showImageErrorDialog(String msg) {
        AppUtility.alertDialog(getContext(), LanguageController.getInstance().getMobileMsgByKey(AppConstant.dialog_alert), msg,
                "",LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok), null);
    }

}
