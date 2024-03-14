package com.eot_app.nav_menu.jobs.job_detail.documents.work_manager;

import android.content.ClipData;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.eot_app.nav_menu.jobs.job_complation.ImgPathWithTemp;
import com.eot_app.nav_menu.jobs.job_detail.documents.ActivityDocumentSaveUpload;
import com.eot_app.nav_menu.jobs.job_detail.documents.doc_model.CompressImg;
import com.eot_app.nav_menu.jobs.job_detail.documents.doc_model.MultiDocUpdateRequest;
import com.eot_app.services.Service_apis;
import com.eot_app.utility.AppConstant;
import com.eot_app.utility.AppUtility;
import com.eot_app.utility.App_preference;
import com.eot_app.utility.CompressImageInBack;
import com.eot_app.utility.EotApp;
import com.eot_app.utility.db.OfflineDataController;
import com.eot_app.utility.util_interfaces.OnImageCompressed;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.checkerframework.checker.units.qual.C;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UploadMultiImgWorker extends Worker {
    boolean lastUpload, isAttachmentSection;
    String dateTime ;
    int pos =0;
    String desc = "";
    String type = "";
    String isFromCompletion = "";
    CompressImageInBack compressImageInBack ;
    String jobId, queId, jtId, tempId ;
    int parentPosition = -1, position = -1;

Context context;
    public UploadMultiImgWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    @NonNull
    @Override
    public Result doWork() {

        dateTime= AppUtility.getDateByFormat(AppConstant.DATE_TIME_FORMAT);
        try {
            String imgPathList = getInputData().getString("imgPathArray");
            Gson gson = new Gson();
                    Type listType = new TypeToken<ArrayList<ImgPathWithTemp>>() {}.getType();
                    List<ImgPathWithTemp> list1 = gson.fromJson(imgPathList,listType);
            String [] resourceUri = getInputData().getStringArray("imgPathArray");
            boolean imgPath = getInputData().getBoolean("imgPath",false);
            boolean isCompNotes = getInputData().getBoolean("isCompNotes",false);
             jobId = getInputData().getString("jobId");
             queId = getInputData().getString("queId");
             jtId = getInputData().getString("jtId");
             parentPosition = getInputData().getInt("parentPosition",-1);
             position = getInputData().getInt("position",-1);
             tempId = getInputData().getString("tempId");

             isAttachmentSection = getInputData().getBoolean("isAttachmentSection",false);
            if(jobId == null ){
                jobId = "";
            }
            if(queId == null ){
                queId = "";
            }
            if(jtId == null ){
                jtId = "";
            }

            if(pos < list1.size()) {
                Log.e("Length", "of multi Images" + list1.size());
                lastUpload = false;
                if (pos == list1.size() - 1) {
                    lastUpload = true;
                }
                Uri uri = Uri.fromFile(new File(list1.get(pos).getName()));
                final String[] fileName = new String[1];
                if(isCompNotes){
                    type = "6";
                    isFromCompletion = "1";
                }else {
                    type = "2";
                    isFromCompletion = "0";
                }
                 desc = "";

                if (imgPath) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            fileName[0] = new ActivityDocumentSaveUpload().getFileName(list1.get(pos).getName());
                            Log.e("File name ", fileName[0]);
//                            CompressImg compressImg = new CompressImg(context);
//                            String savedImagePath = compressImg.compressImage(uri.toString());
//                            if (savedImagePath != null) {
                                Log.e("Loopin", "Run inner loop" + pos);
                                Log.e("File name ", "Call api ===" + fileName[0]);
                                OfflineDataController.getInstance().addInOfflineDB(Service_apis.upload_document, getParam(jobId, queId, jtId, list1.get(pos).getName(),
                                        fileName[0],
                                        desc,
                                        type,
                                        isFromCompletion, lastUpload,isAttachmentSection,parentPosition, position, list1.get(pos).getTempId()), dateTime);
                                pos++;
                                doWork();
//                            }

                        }
                    });

                } else {
                    OfflineDataController.getInstance().addInOfflineDB(Service_apis.upload_document, getParam(jobId, queId, jtId,list1.get(pos).getName(),
                            fileName[0],
                            desc,
                            type,
                            isFromCompletion, lastUpload,isAttachmentSection,parentPosition,position,tempId), dateTime);
                    pos++;
                    doWork();

                }
            }
            return Result.success();
        }catch (Exception e) {
            return Result.failure();
        }
    }
    public String getParam(String job_Id,String queId,String jtId, String file, String finalFname, String desc, String type, String isAddAttachAsCompletionNote,boolean lastCall, boolean isAttachmentSection, int parentPosition, int postion, String tempId){
        MultiDocUpdateRequest multi_DocUpdateRequest = new MultiDocUpdateRequest(job_Id, queId, jtId,file,finalFname,desc,type,isAddAttachAsCompletionNote,lastCall, isAttachmentSection, parentPosition, postion,tempId);
        Gson gson = new Gson();
        String multiDocUpdateRequest = gson.toJson(multi_DocUpdateRequest);

        return multiDocUpdateRequest;
    }


}

