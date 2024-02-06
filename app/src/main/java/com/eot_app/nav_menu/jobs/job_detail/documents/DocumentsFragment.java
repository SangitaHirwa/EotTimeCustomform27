package com.eot_app.nav_menu.jobs.job_detail.documents;


import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.text.Editable;
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
import com.eot_app.home_screens.MainActivity;
import com.eot_app.nav_menu.jobs.job_detail.JobDetailActivity;
import com.eot_app.nav_menu.jobs.job_detail.documents.doc_model.Attachments;
import com.eot_app.nav_menu.jobs.job_detail.documents.doc_model.NotifyForMultiDocAdd;
import com.eot_app.nav_menu.jobs.job_detail.documents.doc_model.NotifyForMultiDocAddForAttach;
import com.eot_app.nav_menu.jobs.job_detail.documents.fileattach_mvp.Doc_Attch_Pc;
import com.eot_app.nav_menu.jobs.job_detail.documents.fileattach_mvp.Doc_Attch_Pi;
import com.eot_app.nav_menu.jobs.job_detail.documents.fileattach_mvp.Doc_Attch_View;
import com.eot_app.nav_menu.jobs.job_detail.documents.work_manager.UploadMultiImgWorker;
import com.eot_app.nav_menu.jobs.job_detail.form_form.get_qus_list.qus_model.QuesRspncModel;
import com.eot_app.services.Service_apis;
import com.eot_app.utility.AppConstant;
import com.eot_app.utility.AppUtility;
import com.eot_app.utility.App_preference;
import com.eot_app.utility.EotApp;
import com.eot_app.utility.db.AppDataBase;
import com.eot_app.utility.db.OfflineDataController;
import com.eot_app.utility.language_support.LanguageController;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.hypertrack.hyperlog.HyperLog;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ja.burhanrashid52.photoeditor.OnPhotoEditorListener;
import ja.burhanrashid52.photoeditor.ViewType;

public class DocumentsFragment extends Fragment implements Doc_Attch_View, DocumentListAdapter.FileDesc_Item_Selected, OnPhotoEditorListener, NotifyForMultiDocAddForAttach {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private final static int CAPTURE_IMAGE_GALLARY = 222;
    private static final int DOUCMENT_UPLOAD_CODE = 156;
    private final int CAMERA_CODE = 100;
    private final int ATTACHFILE_CODE = 101;
    RecyclerView fileupload_rc;
    Doc_Attch_Pi doc_attch_pi;
    RecyclerView.LayoutManager layoutManager;
    LinearLayout nodoc_linear;
    private String captureImagePath;
    TextView noDocList;
    private DocumentListAdapter documentListAdapter;
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
    String queId ="";

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
            doc_attch_pi.getAttachFileList(jobId, "", "",true);
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
        fileupload_rc.setLayoutManager(layoutManager);
        initializeAdapter();

//        doc_attch_pi.getAttachFileList(jobId, "", "",true);
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
        setList((ArrayList<Attachments>) AppDataBase.getInMemoryDatabase(getActivity()).attachments_dao().getAllAttachmentsOfJob(jobId),"",true);
        return view;
    }

    private void initializeAdapter() {
        ArrayList<Attachments> getFileList_res = new ArrayList<>();
        documentListAdapter = new DocumentListAdapter(this, getFileList_res, jobId,this);
        fileupload_rc.setAdapter(documentListAdapter);
    }

    private void initiliazeView(View view) {
        fileupload_rc = view.findViewById(R.id.fileupload_rc);
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
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        imvCross.setOnClickListener(v -> edtSearch.setText(""));

        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(true);
            doc_attch_pi.getAttachFileList(jobId, "", "",true);
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
    public void setList(ArrayList<Attachments> getFileList_res, String isAttachCompletionNotes, boolean firstCall) {
//        AppUtility.hideSoftKeyboard(getActivity());
        if (currentDialog != null) {
            currentDialog.dismiss();
            currentDialog = null;
        }
        this.fileList_res = getFileList_res;
        if (getFileList_res.size() > 0) {
            nodoc_linear.setVisibility(View.GONE);
            fileupload_rc.setVisibility(View.VISIBLE);
            (documentListAdapter).updateFileList(getFileList_res,firstCall);
        } else if (fileupload_rc.getAdapter() != null && fileupload_rc.getAdapter().getItemCount() == 0) {
            nodoc_linear.setVisibility(View.VISIBLE);
            fileupload_rc.setVisibility(View.GONE);
        }
    }

    @Override
    public void setMultiList(ArrayList<Attachments> getFileList_res, String isAttachCompletionNotes, boolean firstCall, int parentPositon, int position, String queId, String jtId) {
        AppDataBase.getInMemoryDatabase(getActivity()).attachments_dao().insertAttachments(getFileList_res);
        AppDataBase.getInMemoryDatabase(getActivity()).attachments_dao().deleteAttachments();
        documentListAdapter.updateFileList((ArrayList<Attachments>) AppDataBase.getInMemoryDatabase(getActivity()).attachments_dao().getAllAttachmentsOfJob(jobId),true);
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
            setList(fileList_res, "",true);
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
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [SettingActivity] > [Permission]")
                .setPermissions(permissions)
                .check();
    }
    private void getImageFromGallray() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(galleryIntent, CAPTURE_IMAGE_GALLARY);
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
        File image = File.createTempFile(
                String.valueOf(imageFileName),  /* prefix */
                ".jpg",         /* suffix */
                directoryPath   /* directory */
        );
        captureImagePath = image.getAbsolutePath();
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
                        String fileNameExt = AppUtility.getFileNameWithExtension(data.getStringExtra("imgPath"));
                        String bitmapString="";
                        if(data.getBooleanExtra("isFileImage",false)){
                            Bitmap bitmap = AppUtility.getBitmapFromPath(data.getStringExtra("imgPath"));
                            bitmapString = AppUtility.BitMapToString(bitmap);
                        }
//                        Attachments obj=new Attachments("0",fileNameExt,fileNameExt,bitmapString);
//                        ArrayList<Attachments> getFileList_res =new ArrayList<>();
//                        if (fileList_res != null) {
//                            getFileList_res.addAll(fileList_res);
//                        }
//                        getFileList_res.add(obj);
                        Attachments attachments = new Attachments("TempAttach-"+data.getStringExtra("fileName"),fileNameExt,fileNameExt,data.getStringExtra("imgPath"),"", "",data.getStringExtra("desc"),jobId,data.getStringExtra("type"),bitmapString);
                        AppDataBase.getInMemoryDatabase(getActivity()).attachments_dao().insertSingleAttachments(attachments);

                        setList((ArrayList<Attachments>) AppDataBase.getInMemoryDatabase(getActivity()).attachments_dao().getAllAttachmentsOfJob(jobId), "",true);

                        if(data.getStringExtra("fileName")!=null){
                            try
                            {
//                                doc_attch_pi.uploadDocuments(jobId, data.getStringExtra("imgPath"),
//                                        data.getStringExtra("fileName"),
//                                        data.getStringExtra("desc"),
//                                        data.getStringExtra("type") ,
//                                        data.getStringExtra("isFromCmpletion"));
                                OfflineDataController.getInstance().addInOfflineDB(Service_apis.upload_document, AppUtility.getParam(jobId, queId,"",
                                        data.getStringExtra("imgPath"),
                                        data.getStringExtra("fileName"),
                                        data.getStringExtra("desc"),
                                        data.getStringExtra("type"),
                                        data.getStringExtra("isFromCmpletion"), true,true,-1,-1), AppUtility.getDateByFormat(AppConstant.DATE_TIME_FORMAT));
                            }
                            catch (Exception e)
                            {
//                                if (getFileList_res.size()==1) {
//                                    fileList_res.remove(getFileList_res.get(0));
//                                    setList(fileList_res, "",true);
//                                }
                                e.printStackTrace();
                            }
                        }
                    }
                break;
            case CAMERA_CODE:

                if (resultCode == RESULT_OK) {
                    try {
                        File file = AppUtility.scaleToActualAspectRatio(captureImagePath, 1024f, 1024f);
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
                        boolean isMultipleImages = false;

                        Uri galreyImguriUri = data.getClipData().getItemAt(0).getUri();
                        //  String gallery_image_Path = PathUtils.getPath(getActivity(), galreyImguriUri);
                        String gallery_image_Path = PathUtils.getRealPath(getActivity(), galreyImguriUri);
                        String img_extension = gallery_image_Path.substring(gallery_image_Path.lastIndexOf("."));
                        if(data.getClipData().getItemCount()>1){
                            isMultipleImages = true;
                        }else {
                            isMultipleImages = false;
                        }
                        //('jpg','png','jpeg','pdf','doc','docx','xlsx','csv','xls'); supporting extensions
                        if (img_extension.equals(".jpg") || img_extension.equals(".png") || img_extension.equals(".jpeg")) {
                            if(!isMultipleImages) {
                                imageEditing(data.getClipData().getItemAt(0).getUri(), true);
                            }
                            else {
                                uploadMultipleImges(data,true);
                            }
                        } else {
                            if(!isMultipleImages) {
                                uploadFileDialog(gallery_image_Path);
                            }
                            else {
                                uploadMultipleImges(data,false);
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
                        String gallery_image_Path = PathUtils.getRealPath(getActivity(), galreyImguriUri);
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


    private void uploadMultipleImges(Intent data, boolean imgPath){


        String [] imgPathArray = new String[data.getClipData().getItemCount()];
        ExecutorService service = Executors.newSingleThreadExecutor();
        service.execute(()->{
            for(int i =0; i<data.getClipData().getItemCount();i++) {
                Uri uri = data.getClipData().getItemAt(i).getUri();
                String fileNameExt = AppUtility.getFileNameWithExtension(PathUtils.getRealPath(getActivity(), uri));
                String[] fileName = fileNameExt.split("\\.");
                imgPathArray[i] = PathUtils.getRealPath(getActivity(), uri);
                Bitmap bitmap = AppUtility.getBitmapFromPath(PathUtils.getRealPath(getActivity(), uri));
                String bitmapString = AppUtility.BitMapToString(bitmap);
                Attachments attachments = new Attachments("TempAttach-"+fileName[0],fileNameExt,fileNameExt,imgPathArray[i],"", "","",jobId,"2",bitmapString);
//                Attachments obj=new Attachments("0",fileNameExt,fileNameExt,bitmap);
//                ArrayList<Attachments> getFileList_res =new ArrayList<>();
//                if (fileList_res != null) {
//                    getFileList_res.clear();
//                    getFileList_res.addAll(fileList_res);
//                }
//                getFileList_res.add(obj);
                AppDataBase.getInMemoryDatabase(getActivity()).attachments_dao().insertSingleAttachments(attachments);
                new Handler(Looper.getMainLooper()).post(()->{
                    setList((ArrayList<Attachments>) AppDataBase.getInMemoryDatabase(getActivity()).attachments_dao().getAllAttachmentsOfJob(jobId), "",true);
                });
            }
            new Handler(Looper.getMainLooper()).post(()->{
                uploadOffline(imgPathArray,true,false,jobId);
            });

        });



    }

    public void uploadOffline(String[] imgPathArray,boolean imgPath, boolean isCompNotes, String jobId){
        Data.Builder builder = new Data.Builder();

        builder.putStringArray("imgPathArray",imgPathArray);
        builder.putBoolean("imgPath",true);
        builder.putBoolean("isCompNotes",false);
        builder.putString("jobId",jobId);
        builder.putBoolean("isAttachmentSection",true);

        mWorkManager = WorkManager.getInstance(getActivity().getApplication());

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
    public void updateMultiDoc(String apiName, String jobId) {
        switch (apiName) {
            case Service_apis.upload_document:
                         if(doc_attch_pi != null) {
                             doc_attch_pi.getMultiAttachFileList(jobId,  App_preference.getSharedprefInstance().getLoginRes().getUsrId(), "",true,-1,-1,"0","0");
                         }
                            break;
                         }
    }
    public void showErrorDialog(String msg) {
        AppUtility.error_Alert_Dialog(getActivity(), msg, LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok)
                , () -> null);
    }

}
