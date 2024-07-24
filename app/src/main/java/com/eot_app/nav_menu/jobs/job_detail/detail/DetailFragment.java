package com.eot_app.nav_menu.jobs.job_detail.detail;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.text.util.Linkify;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eot_app.BuildConfig;
import com.eot_app.R;
import com.eot_app.eoteditor.EotEditor;
import com.eot_app.eoteditor.PicassoImageGetter;
import com.eot_app.lat_lng_sync_pck.LatLngSycn_Controller;
import com.eot_app.locations.LocationTracker;
import com.eot_app.login_next.FooterMenu;
import com.eot_app.login_next.login_next_model.CompPermission;
import com.eot_app.nav_menu.client.clientlist.client_detail.site.sitelist.editsite.editsitedb.SpinnerCountrySite;
import com.eot_app.nav_menu.custom_fileds.CustomFiledListActivity;
import com.eot_app.nav_menu.custom_fileds.custom_model.CustOmFormQuestionsRes;
import com.eot_app.nav_menu.jobs.add_job.add_job_recr.RecurReqResModel;
import com.eot_app.nav_menu.jobs.job_complation.DocDeleteNotfy;
import com.eot_app.nav_menu.jobs.job_complation.JobCompletionActivity;
import com.eot_app.nav_menu.jobs.job_complation.JobCompletionAdpter;
import com.eot_app.nav_menu.jobs.job_complation.compla_model.NotifyForcompletionInDetail;
import com.eot_app.nav_menu.jobs.job_db.EquArrayModel;
import com.eot_app.nav_menu.jobs.job_db.IsMarkDoneWithJtid;
import com.eot_app.nav_menu.jobs.job_db.Job;
import com.eot_app.nav_menu.jobs.job_db.JtId;
import com.eot_app.nav_menu.jobs.job_db.OfflieCompleQueAns;
import com.eot_app.nav_menu.jobs.job_detail.JobDetailActivity;
import com.eot_app.nav_menu.jobs.job_detail.addinvoiveitem2pkg.model.InvoiceItemDataModel;
import com.eot_app.nav_menu.jobs.job_detail.chat.fire_Base_Model.Chat_Send_Msg_Model;
import com.eot_app.nav_menu.jobs.job_detail.customform.CustomFormCompletionCallBack;
import com.eot_app.nav_menu.jobs.job_detail.detail.filedworker_list.Filedworker_List_Adapter;
import com.eot_app.nav_menu.jobs.job_detail.detail.job_detail_presenter.JobDetail_pc;
import com.eot_app.nav_menu.jobs.job_detail.detail.job_detail_presenter.JobDetail_pi;
import com.eot_app.nav_menu.jobs.job_detail.detail.job_detail_view.JobDetail_view;
import com.eot_app.nav_menu.jobs.job_detail.detail.jobdetial_model.CompletionDetails;
import com.eot_app.nav_menu.jobs.job_detail.detail.jobdetial_model.JobStatusModelNew;
import com.eot_app.nav_menu.jobs.job_detail.detail.jobdetial_model.ServiceMarkDoneAdapter;
import com.eot_app.nav_menu.jobs.job_detail.documents.doc_model.Attachments;
import com.eot_app.nav_menu.jobs.job_detail.form_form.get_qus_list.qus_model.AnswerModel;
import com.eot_app.nav_menu.jobs.job_detail.form_form.get_qus_list.qus_model.QuesRspncModel;
import com.eot_app.nav_menu.jobs.job_detail.invoice2list.InvoiceItemList2Adpter;
import com.eot_app.nav_menu.jobs.job_detail.invoice2list.JoBInvoiceItemList2Activity;
import com.eot_app.nav_menu.jobs.job_detail.job_equipment.JobDetailEquipmentAdapter;
import com.eot_app.nav_menu.jobs.job_detail.job_equipment.JobEquipmentActivity;
import com.eot_app.nav_menu.jobs.job_detail.job_status_pkg.JobStatus_Controller;
import com.eot_app.nav_menu.jobs.job_detail.requested_item.AddUpdateRquestedItemActivity;
import com.eot_app.nav_menu.jobs.job_detail.requested_item.RequestedItemListAdapter;
import com.eot_app.nav_menu.jobs.job_detail.requested_item.requested_itemModel.AddUpdateRequestedModel;
import com.eot_app.nav_menu.jobs.job_detail.requested_item.requested_itemModel.RequestedItemModel;
import com.eot_app.nav_menu.jobs.job_detail.reschedule.RescheduleActivity;
import com.eot_app.nav_menu.jobs.job_detail.revisit.RevisitActivity;
import com.eot_app.nav_menu.jobs.job_list.JobList;
import com.eot_app.nav_menu.jobs.joboffline_db.JobOverViewNotify;
import com.eot_app.nav_menu.quote.quote_invoice_pkg.Quote_Invoice_Details_Activity;
import com.eot_app.services.Service_apis;
import com.eot_app.utility.AppConstant;
import com.eot_app.utility.AppUtility;
import com.eot_app.utility.App_preference;
import com.eot_app.utility.CustomLinearLayoutManager;
import com.eot_app.utility.EotApp;
import com.eot_app.utility.db.AppDataBase;
import com.eot_app.utility.language_support.LanguageController;
import com.eot_app.utility.settings.setting_db.FieldWorker;
import com.eot_app.utility.settings.setting_db.Offlinetable;
import com.eot_app.utility.settings.setting_db.TagData;
import com.eot_app.utility.util_interfaces.Callback_AlertDialog;
import com.eot_app.utility.util_interfaces.OnFragmentInteractionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.chip.ChipGroup;
import com.google.gson.Gson;
import com.hypertrack.hyperlog.HyperLog;
import com.squareup.picasso.Picasso;
import org.jetbrains.annotations.NotNull;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Callable;

public class DetailFragment extends Fragment
        implements Html.ImageGetter,
        View.OnClickListener,
        JobDetail_view,
        CustomFormCompletionCallBack,
        JobOverViewNotify, NotifyForItemCount,
//      ,NotifyForAddJob
        JobDetailEquipmentAdapter.OnEquipmentClicked,
        OnMapReadyCallback,
        JobCompletionAdpter.FileDesc_Item_Selected, EotApp.NotifyForEquipmentStatusList,
        NotifyForEquipmentCount
        , NotifyForAttchCount, DocDeleteNotfy, NotifyForcompletionInDetail,NotifyForRequestedItemList, RequestedItemListAdapter.DeleteItem,
        RequestedItemListAdapter.SelectedItemListener
{

    public static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1234;
    public static final int CUSTOMFILED = 222;
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int REQUEST_COMPLETION_NOTE = 111;
    private static final int REQUEST_RESCHEDULE = 454;
    private static final String TAG = DetailFragment.class.getSimpleName();
    private final LinkedHashMap<String, String> arraystatusvalue = new LinkedHashMap<>();
    private final LinkedHashMap<String, String> arraystatus = new LinkedHashMap<>();
    LocationTracker locationTracker;
    ArrayList<CustOmFormQuestionsRes> questionList = new ArrayList<>();
    long check = System.currentTimeMillis();
    MapView mMapView;
    String[] statusArray = new String[arraystatus.size()];
    String[] statusArrayForIds = new String[arraystatusvalue.size()];
    CustomLinearLayoutManager customLayoutManager;
    GridLayoutManager gridLayoutManager;
    FrameLayout frameView;
    TextView person_name, textViewJobCode, textViewTime, textViewPriority,
            textViewJobStatus, textViewAddress, textViewPONumber, textViewDescription,
            textViewContactperson, textViewTitle,
            textViewInstruction, txtViewHeader, textViewTags, txt_fw_nm_list,
            tv_description, tv_instruction, complation_txt, item_txt, eq_txt,
            complation_notes, tv_tag, fw_Nm_List, txt_serviceHeader, txt_notesHeader, requested_item_txt,txt_no_item_found;
    String mParam1;
    RelativeLayout map_layout;
    TextView custom_filed_txt, btnStopRecurView, btnComplationView,
             btn_add_item, btn_add_eq, recur_txt, txt_recur_msg, contact_name_lable, schdule_details_txt, job_status_lable, btn_add_requested_item;
    TextView customfiled_btn, signature_pad,customer_name, btn_add_signature, quotes_details_txt, quotes_details_number_txt, quotes_details_number;
    String strAddress = "";
    RecyclerView recyclerView, rv_mark_done;
    InvoiceItemList2Adpter invoice_list_adpter;
    RequestedItemListAdapter requestedItemListAdapter;
//    TextView text_misc;
    View ll_item, ll_equipment, ll_requested_item;
    RecyclerView recyclerView_job_item, recyclerView_requested_item;
    RecyclerView recyclerView_job_eq,rv_fw_list;
    TextView tv_label_ac_job_time, date_ac_start;
    TextView date_ac_end;
    TextView tv_label_job_travel_time, date_tr_start;
    TextView date_tr_end;
    String actualStart = "", actualFinish = "";
    String travelStart = "", travelFinish = "";
    TextView tvTravelJobTime, tvActualJobTime, btnActualEdit, btnTravelEdit;
    ImageView ivEditAc/*, show_requested_list,hide_requested_list*/;
    RelativeLayout ll_actual_date_time, ll_travel_date_time;
    LinearLayout ll_completion_detail, liner_layout_for_recurmsg, recurMsgShow, recurMsgHide;
    RelativeLayout rl_Collapse1, rl_Collapse2/*,requested_itemList_show_hide_rl*/;
    ConstraintLayout progressBar_timing;
    String firstTrvlBrkTime, lastTrvlBrkTime;
    String firstBrkTime, lastBrkTime;
    String lastStatus, lastStatusTime;
    //    check if travelling is enable or not
    //    for remove contact detail when permission not allowed from admin
    CardView contact_card, recur_parent_view, tagcardView,requested_item_cardView;//
    DialogActualTravelDateTime dialogActualTravelDateTime;
    CompletionDetails completionDetails;
    boolean isClickedActual = false;
    LinearLayout accept_reject_linear;
    private Button buttonAccept, buttonDecline, buttonView;
    private ImageView imageViewChat, imageViewCall, imageViewEmail;
    private View layout;
    private TextView image_txt;
    private Dialog enterFieldDialog;
    private JobStatusModelNew jobstatus;
    private JobDetail_pi jobDetail_pi;
    private Spinner new_status_spinner;
    private Job mParam2;
    private String param3;
    private View map_loc_txt;
    private OnFragmentInteractionListener mListener;
    private CardView customField_view, cardView_signature_pad, quotes_details_card;
    private LinearLayout ll_custom_views;
    private Boolean SAVEANS = false;
    private ImageView attachmemt_flag, item_flag, equi_flag, arrow_dp_icon, signature_img,requested_item_flag;
    RelativeLayout arraw_layout;
    private Job_Status_Adpter mySpinnerAdapter;
    // job actual and travel time
    private ChipGroup chipGroup;
    private LinearLayout ll_po_number;
    private CompletionAdpterJobDteails1 jobCompletionAdpter;
    private JobDetailEquipmentAdapter adapter;
    private TextView site_name;
    private String isMailSentToClt = "1";
    private String isKprChgStatusFalse = "0";
    private String isKprChgStatusTrue = "1";
    private String multipleKpr ="2";
    private String singelKpr = "";
    boolean accept=true,reject=true,travel=true,onhold=true,brack=true, isRefreshReqItem=false;
    String getDisCalculationType, getTaxCalculationType;
    GoogleMap mMap;
   ConstraintLayout progressBar_cyclic,progressBar_itemRequest;
    ArrayList<QuesRspncModel> complQuestionList;
    List<Attachments> attachmentsList;
    Set<IsMarkDoneWithJtid> isMarkDoneWithJtidsList = new HashSet<>();
    private  static DetailFragment instanse;
    ConstraintLayout cl_serviceMarkAsDone, cl_pbCompletion;
    ServiceMarkDoneAdapter serviceMarkDoneAdapter;
    boolean isAskForCompleteJob = false;
    List<RequestedItemModel> requestedItemList = new ArrayList<>();
    String brandName = "";
    RecurReqResModel recurData;
    boolean isClickedReqItem = false;

    public DetailFragment() {
        // Required empty public constructor
    }
    public static DetailFragment getInstance() {
        return instanse;
    }

    public static DetailFragment newInstance(String param1, String param3) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param3);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            param3 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public void OnItemClick_Document(Attachments getFileList_res) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(App_preference.getSharedprefInstance().getBaseURL() + "" + getFileList_res.getAttachFileName())));
    }

    @Override
    public void openAttachmentDialog() {

    }

    @Override
    public void updateCOuntAttchment() {
        Log.i("updateCOuntAttchment", "updateCOuntAttchment");
        if (jobDetail_pi != null) {
            jobDetail_pi.getAttachFileList(mParam2.getJobId(), App_preference.getSharedprefInstance().getLoginRes().getUsrId()
                    , "6");
            jobDetail_pi.loadFromServer(mParam2.getJobId());
        }
    }

    @Override
    public void notifyDocDelete() {

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        Log.e("GoogleMap", "1");
        mMap = googleMap;
        if (mParam2 == null) {
            HyperLog.i(TAG, "Job Not Found In DB");
        } else {
            try {
                Log.e("Job data", "" + new Gson().toJson(mParam2));
                if (mParam2.getLat() != null && mParam2.getLng() != null) {
                    if (mParam2.getLat().equals("") && mParam2.getLng().equals("")
                            || mParam2.getLat().equals("0") && mParam2.getLng().equals("0")) {
                        mParam2.setLng("0.0");
                        mParam2.setLat("0.0");
                    }
                    if (mParam2.getLat().equals("0.0") && mParam2.getLng().equals("0.0") && map_loc_txt != null) {
                        map_loc_txt.setVisibility(View.VISIBLE);
                        image_txt.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.location_not_found));
                    } else {
//                        Log.e("Api_Map_Key", BuildConfig.MAPS_API_KEY);
                        LatLng latLng = new LatLng(Double.parseDouble(mParam2.getLat()), Double.parseDouble(mParam2.getLng()));
                        mMap.addMarker(new MarkerOptions()
                                .position(latLng)
                        );
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,17.0f));
                        mMap.animateCamera(CameraUpdateFactory.zoomIn());
                        mMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f),2000,null);

                        if (map_loc_txt != null) {
                            map_loc_txt.setVisibility(View.GONE);
                        }
                    }
                } else {
                    mParam2.setLng("0.0");
                    mParam2.setLat("0.0");
                    map_loc_txt.setVisibility(View.VISIBLE);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                mParam2.setLng("0.0");
                mParam2.setLat("0.0");
                map_loc_txt.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void updateJobOverViewFlag() {
        Log.e("OnCreateDetail", "Overview Notify Called");
        try {
            EotApp.getAppinstance().notifyApiObserver(Service_apis.addAppointment);
            mParam2 = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().getJobsById(mParam2.getJobId());
            flagVisibility();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void notifyCustomberSign() {
        mParam2 = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().getJobsById(mParam2.getJobId());
        setUploadSignature();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        instanse = this;
        layout = inflater.inflate(R.layout.fragment_detail2, container, false);


        ll_completion_detail = layout.findViewById(R.id.ll_completion_detail);
        ll_item = layout.findViewById(R.id.ll_item);
        ll_equipment = layout.findViewById(R.id.ll_equipment);
        ll_requested_item = layout.findViewById(R.id.ll_requested_item);


        // permission checks for showing equipment and items
        for (FooterMenu serverList : App_preference.getSharedprefInstance().getLoginRes().getFooterMenu()) {
            if (serverList.isEnable.equals("1"))
                switch (serverList.getMenuField()) {
                    case "set_itemMenuOdrNo":
                        // hide the item and equipment on permission basis
                        if (App_preference.getSharedprefInstance().getLoginRes().getRights().get(0).getIsItemVisible() == 0) {
                            ll_item.setVisibility(View.VISIBLE);
                        } else {
                            ll_item.setVisibility(View.GONE);
                        }
                        break;
                    case "set_equipmentMenuOrdrNo":
                        // hide the item and equipment on permission basis
                        try {
                            if (App_preference.getSharedprefInstance().getLoginRes().getIsEquipmentEnable() != null) {
                                if (App_preference.getSharedprefInstance().getLoginRes().getIsEquipmentEnable().equals("1")) {
                                    ll_equipment.setVisibility(View.VISIBLE);
                                } else {
                                    ll_equipment.setVisibility(View.GONE);
                                }
                            } else {
                                ll_equipment.setVisibility(View.GONE);
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        break;
                }
        }


        mMapView = layout.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately
        Log.e("GoogleMap", "2");


        try {
            MapsInitializer.initialize(requireActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

//        getData from
        mParam2 = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().getJobsById(param3);
        try {
            if (mParam2 != null) {
                if (mParam2.getJobId() != null && !mParam2.getJobId().isEmpty()) {
                    /**After discussion with Rani change validation of canInvoiceCreated by isJobInvoiced 12/04/2024**/
                    if (mParam2.getIsJobInvoiced() != null && mParam2.getIsJobInvoiced().equals("1")) {
                        getDisCalculationType = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().disCalculationType(mParam2.getJobId());
                        getTaxCalculationType = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().taxCalculationType(mParam2.getJobId());
                    } else {
                        getDisCalculationType = App_preference.getSharedprefInstance().getLoginRes().getDisCalculationType();
                        getTaxCalculationType = App_preference.getSharedprefInstance().getLoginRes().getTaxCalculationType();
                    }
                } else {
                    getDisCalculationType = App_preference.getSharedprefInstance().getLoginRes().getDisCalculationType();
                    getTaxCalculationType = App_preference.getSharedprefInstance().getLoginRes().getTaxCalculationType();
                }
            } else {
                getDisCalculationType = App_preference.getSharedprefInstance().getLoginRes().getDisCalculationType();
                getTaxCalculationType = App_preference.getSharedprefInstance().getLoginRes().getTaxCalculationType();
            }
        }catch (Exception e){
            Log.e("Error", e.getMessage());
        }
        initializelables();
        mMapView.getMapAsync(this);

        // adapter for job status dropdown
        mySpinnerAdapter = new Job_Status_Adpter(getActivity(), statusArray,arraystatus, statusKey -> {

            Log.e("", "");
            if (statusKey.equalsIgnoreCase(AppConstant.Reschedule)) {
                if (mParam2.getJobId().equals(mParam2.getTempId())) {
                    showErrorDialog(LanguageController.getInstance().getMobileMsgByKey(AppConstant.job_not_sync));
                } else if (jobstatus != null && jobstatus.getStatus_no().equals(AppConstant.Completed) ||
                        Objects.requireNonNull(jobstatus).getStatus_no().equals(AppConstant.Closed)) {
                    showErrorDialog(LanguageController.getInstance().getMobileMsgByKey(AppConstant.close_completed_job_msg));
                } else {
                    Intent open_reschedule = new Intent(getActivity(), RescheduleActivity.class);
                    open_reschedule.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    String str = new Gson().toJson(mParam2);
                    startActivityForResult(open_reschedule.putExtra("job", str), REQUEST_RESCHEDULE);
                }
            } else if (statusKey.equals(AppConstant.Revisit)) {
                if (mParam2.getJobId().equals(mParam2.getTempId())) {
                    showErrorDialog(LanguageController.getInstance().getMobileMsgByKey(AppConstant.job_not_sync));
                } else if (mParam2.getParentId() != null && mParam2.getParentId().equals("0")) {
                    Intent open_revisit = new Intent(getActivity(), RevisitActivity.class);
                    open_revisit.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    String str = new Gson().toJson(mParam2);
                    startActivity(open_revisit.putExtra("job", str));
                } else
                    showErrorDialog(LanguageController.getInstance().getMobileMsgByKey(AppConstant.cannot_raise_revisit_request_for_job_which_is_already_marked_as_recurring));
            } else {
                //After discussion with Ayush sir, Add new condition for check signature of customer (27/Sep/2023)
                if(App_preference.getSharedprefInstance().getLoginRes().getIsJobCompCustSignEnable().equals("1")) {
                    if (statusKey.equalsIgnoreCase(AppConstant.Completed)) {
                        if (mParam2.getSignature() == null || mParam2.getSignature().equals("")) {
                            showErrorDialog(LanguageController.getInstance().getMobileMsgByKey(AppConstant.signature_alert));
                        }else {
                            checkForIsLeader(statusKey);
                        }
                    }else {
                        checkForIsLeader(statusKey);
                    }
                }
                else{
                    checkForIsLeader(statusKey);
                }
            }
        });

        new_status_spinner.setAdapter(mySpinnerAdapter);
        getViewIds();

        return layout;
    }
    private void checkForIsLeader(String statusId ){
        String[] kprArr = mParam2.getKpr().split(",");
        if (kprArr.length > 1) {
        if(statusId.equalsIgnoreCase(AppConstant.Completed)) {
            if (mParam2.getIsLeader().equals(App_preference.getSharedprefInstance().getLoginRes().getUsrId())) {
                    if(App_preference.getSharedprefInstance().getLoginRes().getCompPermission().get(0).getIsLeaderChgAllUsrStatusOnJb().equals("0")) {
                        AppUtility.alertDialog2(getActivity(), LanguageController.getInstance()
                                        .getMobileMsgByKey(AppConstant.status_dialog),
                                LanguageController.getInstance().getMobileMsgByKey(AppConstant.leader_change_completed_status_all_members),
                                LanguageController.getInstance().getMobileMsgByKey(AppConstant.yes),
                                LanguageController.getInstance().getMobileMsgByKey(AppConstant.no), new Callback_AlertDialog() {
                                    @Override
                                    public void onPossitiveCall() {
                                        changeStatus(statusId, isKprChgStatusTrue,multipleKpr);
                                    }

                                    @Override
                                    public void onNegativeCall() {
                                        changeStatus(statusId,isKprChgStatusFalse,multipleKpr);
                                    }
                                });
                    }else{
                        changeStatus(statusId, isKprChgStatusFalse,multipleKpr);
                    }
            } else {
                changeStatus(statusId, isKprChgStatusFalse,multipleKpr);
            }
        }else{
            changeStatus(statusId, isKprChgStatusFalse,multipleKpr);
        }
        }else {
            changeStatus(statusId, isKprChgStatusFalse,singelKpr);
        }

    }
    private void changeStatus(String statusId, String completeFor, String jobType){
        JobStatusModelNew statusModel = JobStatus_Controller.getInstance().getStatusObjectById(statusId);
        if (statusModel != null) {
            HyperLog.i(TAG, "Selected status:" + statusModel.getStatus_name());
            HyperLog.i(TAG, "onItemSelected:" + "Select status From DropDown");
            jobstatus.setStatus_name(statusModel.getStatus_name());
            jobstatus.setStatus_no(statusModel.getStatus_no());
        } else {
            return;
        }
        if (!jobDetail_pi.isOldStaus(jobstatus.getStatus_no(), mParam2.getJobId())) {
            //&& (i != -1){
            AppUtility.alertDialog2(getActivity(), LanguageController.getInstance()
                            .getMobileMsgByKey(AppConstant.status_dialog),
                    LanguageController.getInstance().getMobileMsgByKey(AppConstant.job_status_change),
                    LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok),
                    LanguageController.getInstance().getMobileMsgByKey(AppConstant.cancel), new Callback_AlertDialog() {
                        @Override
                        public void onPossitiveCall() {
                            HyperLog.i(TAG, "Request change status start");
                            ((JobDetailActivity) requireActivity()).openFormForEvent(jobstatus.getStatus_no(),completeFor,jobType);
                        }

                        @Override
                        public void onNegativeCall() {
                            HyperLog.i(TAG, "onNegativeCall::");
                            jobDetail_pi.setJobCurrentStatus(mParam2.getJobId());
                        }
                    });
        } else {
            jobDetail_pi.setJobCurrentStatus(mParam2.getJobId());
        }
    }
    @SuppressLint("SetTextI18n")
    private void initializelables() {


        tv_label_ac_job_time = layout.findViewById(R.id.tv_label_ac_job_time);
        tv_label_ac_job_time.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.actual_job_time));

        ll_actual_date_time = layout.findViewById(R.id.ll_actual_date_time);
        ll_travel_date_time = layout.findViewById(R.id.ll_travel_date_time);
        rl_Collapse1 = layout.findViewById(R.id.rl_Collapse1);

        rl_Collapse1.setOnClickListener(this);

        rl_Collapse2 = layout.findViewById(R.id.rl_Collapse2);

        rl_Collapse2.setOnClickListener(this);
        tvTravelJobTime = layout.findViewById(R.id.tvTravelJobTime);
        tvActualJobTime = layout.findViewById(R.id.tvActualJobTime);


        date_ac_start = layout.findViewById(R.id.date_ac_start);
        date_ac_start.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.actual_start_date_time));
//        date_ac_start.setOnClickListener(this);


        date_ac_end = layout.findViewById(R.id.date_ac_end);
        date_ac_end.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.actual_end_date_time));
//        date_ac_end.setOnClickListener(this);


        btnActualEdit = layout.findViewById(R.id.btnActualEdit);
        btnActualEdit.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.edit));
        btnActualEdit.setOnClickListener(this);
        ivEditAc = layout.findViewById(R.id.ivEditAc);
        ivEditAc.setOnClickListener(this);


        btnTravelEdit = layout.findViewById(R.id.btnTravelEdit);
        btnTravelEdit.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.edit));
        btnTravelEdit.setOnClickListener(this);

        tv_label_job_travel_time = layout.findViewById(R.id.tv_label_job_travel_time);
        tv_label_job_travel_time.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.travel_job_time));
        progressBar_timing = layout.findViewById(R.id.progressBar_timing);

        date_tr_start = layout.findViewById(R.id.date_tr_start);
        date_tr_start.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.travel_start_date_time));
//        date_tr_start.setOnClickListener(this);


        date_tr_end = layout.findViewById(R.id.date_tr_end);
        date_tr_end.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.travel_end_date_time));
//        date_tr_end.setOnClickListener(this);


        frameView = layout.findViewById(R.id.frameView);
        person_name = layout.findViewById(R.id.person_name);
        textViewJobCode = layout.findViewById(R.id.textViewJobCode);
        textViewTime = layout.findViewById(R.id.textViewTime);
        textViewPriority = layout.findViewById(R.id.textViewPriority);
        textViewJobStatus = layout.findViewById(R.id.textViewJobStatus);
        textViewAddress = layout.findViewById(R.id.textViewAddress);
        textViewPONumber = layout.findViewById(R.id.textViewPONumber);
        textViewAddress.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.no_location));

        textViewDescription = layout.findViewById(R.id.textViewDescription);
        textViewDescription.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.no_desc));

        textViewTags = layout.findViewById(R.id.textViewTags);
        textViewTags.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.no_tag));

        accept_reject_linear = layout.findViewById(R.id.accept_reject_linear);

        textViewContactperson = layout.findViewById(R.id.textViewContactperson);
        textViewContactperson.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.no_contact_available));
        textViewTitle = layout.findViewById(R.id.textViewTitle);

        textViewInstruction = layout.findViewById(R.id.textViewInstruction);
        textViewInstruction.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.no_instr));

        txtViewHeader = layout.findViewById(R.id.txtViewHeader);
        buttonAccept = layout.findViewById(R.id.buttonAccept);
        buttonDecline = layout.findViewById(R.id.buttonDecline);
        buttonView = layout.findViewById(R.id.buttonView);
        buttonView.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.view));


        imageViewChat = layout.findViewById(R.id.imageViewChat);
        imageViewCall = layout.findViewById(R.id.imageViewCall);
        imageViewEmail = layout.findViewById(R.id.imageViewEmail);

     /*   txt_fw_nm_list = layout.findViewById(R.id.txt_fw_nm_list);
        txt_fw_nm_list.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.no_fw_available));*/

        rv_fw_list  =layout.findViewById(R.id.rv_fw_list);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_fw_list.setLayoutManager(layoutManager);

        contact_card = layout.findViewById(R.id.contact_card);


        tv_description = layout.findViewById(R.id.textView8);
        tv_description.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.description));

        tv_instruction = layout.findViewById(R.id.textView9);
        tv_instruction.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.instr));


        fw_Nm_List = layout.findViewById(R.id.fw_Nm_List);
        fw_Nm_List.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.fieldworkers));

        tv_tag = layout.findViewById(R.id.textView11);
        tv_tag.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.job_tags));

        complation_txt = layout.findViewById(R.id.complation_txt);
        complation_notes = layout.findViewById(R.id.complation_notes);
        txt_notesHeader = layout.findViewById(R.id.txt_notesHeader);
        txt_notesHeader.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.notes));
        complation_txt.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.completion_details));
        btnComplationView = layout.findViewById(R.id.btnComplationView);
        cl_pbCompletion = layout.findViewById(R.id.cl_pbCompletion);
        rv_mark_done = layout.findViewById(R.id.rv_mark_done);
        txt_serviceHeader = layout.findViewById(R.id.txt_serviceHeader);
        cl_serviceMarkAsDone = layout.findViewById(R.id.cl_serviceMarkAsDone);


        item_txt = layout.findViewById(R.id.item_txt);
        item_txt.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.item));
        btn_add_item = layout.findViewById(R.id.btn_add_item);
        btn_add_item.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.add));

        requested_item_txt = layout.findViewById(R.id.requested_item_txt);
        requested_item_txt.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.item_requested));
        requested_item_txt.setOnClickListener(this);

        progressBar_itemRequest =layout.findViewById(R.id.progressBar_itemRequest);
        txt_no_item_found =layout.findViewById(R.id.txt_no_item_found);
        txt_no_item_found.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.no_item_requested_found));

        requested_item_cardView = layout.findViewById(R.id.requested_item_cardView);
       /* show_requested_list = layout.findViewById(R.id.show_requested_list);
        hide_requested_list = layout.findViewById(R.id.hide_requested_list);*/
        requested_item_flag = layout.findViewById(R.id.requested_item_flag);
      /*  requested_itemList_show_hide_rl = layout.findViewById(R.id.requested_itemList_show_hide_rl);
        show_requested_list.setOnClickListener(this);
        hide_requested_list.setOnClickListener(this);*/
        btn_add_requested_item = layout.findViewById(R.id.btn_add_requested_item);
        btn_add_requested_item.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.add));
        btn_add_requested_item.setOnClickListener(this);
        if (App_preference.getSharedprefInstance().getLoginRes().getCompPermission().get(0).getIsItemEnable().equals("1")) {
            btn_add_item.setVisibility(View.GONE);
        }

        eq_txt = layout.findViewById(R.id.eq_txt);
        eq_txt.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.equipment));
        btn_add_eq = layout.findViewById(R.id.btn_add_eq);
        btn_add_eq.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.add));

        customField_view = layout.findViewById(R.id.customField_view);
        ll_custom_views = layout.findViewById(R.id.ll_custom_views);
        ll_po_number = layout.findViewById(R.id.ll_po_number);

        custom_filed_txt = layout.findViewById(R.id.custom_filed_txt);
        custom_filed_txt.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.title_cutom_field));
        custom_filed_txt.setOnClickListener(this);


        customfiled_btn = layout.findViewById(R.id.customfiled_btn);

        attachmemt_flag = layout.findViewById(R.id.attachmemt_flag);
        item_flag = layout.findViewById(R.id.item_flag);
        equi_flag = layout.findViewById(R.id.equi_flag);

        recur_parent_view = layout.findViewById(R.id.recur_parent_view);
        recurMsgShow = layout.findViewById(R.id.liner_layout_for_recurmsg_show);
        recurMsgHide = layout.findViewById(R.id.liner_layout_for_recurmsg_hide);
        progressBar_cyclic = layout.findViewById(R.id.progressBar_cyclic);
        recur_txt = layout.findViewById(R.id.recur_txt);
        txt_recur_msg = layout.findViewById(R.id.txt_recur_msg);
        btnStopRecurView = layout.findViewById(R.id.btnStopRecurView);
        liner_layout_for_recurmsg = layout.findViewById(R.id.liner_layout_for_recurmsg);
        recurMsgShow.setOnClickListener(this);
        recurMsgHide.setOnClickListener(this);
        btn_add_signature = layout.findViewById(R.id.btn_add_signature);
        btnStopRecurView.setOnClickListener(this);
        btn_add_signature.setOnClickListener(this);
        btnStopRecurView.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.stop_recur));
        recur_txt.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.job_recur));
        btn_add_signature.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.menu_upload_sign));

        contact_name_lable = layout.findViewById(R.id.contact_name_lable);
        contact_name_lable.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.contact_details));
        schdule_details_txt = layout.findViewById(R.id.schdule_details_txt);
        schdule_details_txt.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.schedule_details));

        job_status_lable = layout.findViewById(R.id.job_status_lable);
        job_status_lable.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.status_detail) + " : ");

        arrow_dp_icon = layout.findViewById(R.id.arrow_dp_icon);
      //  arrow_dp_icon.setOnClickListener(this);

        arraw_layout=layout.findViewById(R.id.linear_arraw_layout);
        arraw_layout.setOnClickListener(this);

        new_status_spinner = layout.findViewById(R.id.new_status_spinner);

        cardView_signature_pad = layout.findViewById(R.id.cardView_signature_pad);
        signature_img = layout.findViewById(R.id.signature_img);
        customer_name = layout.findViewById(R.id.customer_name);
        chipGroup = layout.findViewById(R.id.chipGroup);

        recyclerView = layout.findViewById(R.id.recyclerView);
        recyclerView_job_item = layout.findViewById(R.id.recyclerView_job_item);
        recyclerView_requested_item = layout.findViewById(R.id.recyclerView_requested_item);
        recyclerView_job_eq = layout.findViewById(R.id.recyclerView_job_eq);
        signature_pad = layout.findViewById(R.id.signature_pad);
        signature_pad.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.customer_signature));


        map_layout = layout.findViewById(R.id.map_layout);
        map_loc_txt = layout.findViewById(R.id.map_loc_txt);

        try {
            image_txt = layout.findViewById(R.id.image_txt);
        } catch (Exception e) {
            e.printStackTrace();

        }

        site_name = layout.findViewById(R.id.site_name);
//        text_misc = layout.findViewById(R.id.text_misc);
        tagcardView = layout.findViewById(R.id.tagcardView);


        quotes_details_card = layout.findViewById(R.id.quotes_details_card);
        quotes_details_txt = layout.findViewById(R.id.quotes_details_txt);
        quotes_details_number_txt = layout.findViewById(R.id.quotes_details_number_txt);
        quotes_details_number = layout.findViewById(R.id.quotes_details_number);

        quotes_details_txt.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.quotes_details));
        quotes_details_number_txt.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.quotes_num));
//        text_misc.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.misc));

//        customLayoutManager = new CustomLinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL
//                , false);
        gridLayoutManager = new GridLayoutManager(getActivity(),3);
        recyclerView.setLayoutManager(gridLayoutManager);
        jobCompletionAdpter = new CompletionAdpterJobDteails1(new ArrayList<>()
                , () -> /*((JobDetailActivity) requireActivity()).getAttchmentFragment()*/showComplationViewDialog(true));
        recyclerView.setAdapter(jobCompletionAdpter);


        // set item data
        recyclerView_job_item.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL
                , false));
        String jobId = "";
        if(mParam2 != null && mParam2.getJobId() != null) {
            jobId = mParam2.getJobId();
        }
        invoice_list_adpter = new InvoiceItemList2Adpter(getActivity(), new ArrayList<>(), true, jobId, getDisCalculationType, getTaxCalculationType);//, this, this
        recyclerView_job_item.setAdapter(invoice_list_adpter);
        recyclerView_job_item.setNestedScrollingEnabled(false);
        //set service data
        rv_mark_done.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL
                , false));
        serviceMarkDoneAdapter = new ServiceMarkDoneAdapter(new ArrayList<>(),getContext());
        rv_mark_done.setAdapter(serviceMarkDoneAdapter);


        if (mParam2 != null && mParam2.getItemData() != null && invoice_list_adpter != null && !mParam2.getItemData().isEmpty()) {
            invoice_list_adpter.updateitemlist(mParam2.getItemData());
        }
        // set equipment data

        recyclerView_job_eq.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL
                , false));
        if (adapter == null) {
            adapter = new JobDetailEquipmentAdapter(getActivity(), this);
            adapter.setEquipmentCurrentStatus(App_preference.getSharedprefInstance().getEquipmentStatusList());
            recyclerView_job_eq.setAdapter(adapter);
            recyclerView_job_eq.setNestedScrollingEnabled(false);
        }

        EotApp.getAppinstance().setJobFlagObserver(this);
        EotApp.getAppinstance().setNotifyForAttchCount(this);
//        EotApp.getAppinstance().setAddJobObserver(this);
        EotApp.getAppinstance().setNotifyForItemCount(this);
        EotApp.getAppinstance().setNotifyForEquipmentCount(this);
        EotApp.getAppinstance().setNotifyForEquipmentStatusList(this);
        EotApp.getAppinstance().setNotifyForcompletionInDetail(this);
        EotApp.getAppinstance().setNotifyForRequestedItemList(this);

        if (mParam2!= null && mParam2.getRecurType() != null && App_preference.getSharedprefInstance().getLoginRes().getRights().get(0).getIsRecur().equals("0") && !mParam2.getRecurType().equalsIgnoreCase("0")
        ||mParam2.getParentRecurType() != null && App_preference.getSharedprefInstance().getLoginRes().getRights().get(0).getIsRecur().equals("0") && !mParam2.getParentRecurType().equalsIgnoreCase("0")){
            if(!mParam2.getParentId().equalsIgnoreCase("0") && mParam2.getIsSubjob().equals("0") || mParam2.getParentId().equalsIgnoreCase("0") && mParam2.getIsRecur().equalsIgnoreCase("1")) {
                recur_parent_view.setVisibility(View.VISIBLE);
            }else {
                recur_parent_view.setVisibility(View.GONE);
                    }
        }else{
            recur_parent_view.setVisibility(View.GONE);
        }
        //set Requested item recyclerview in adapter
        recyclerView_requested_item.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL
                , false));
        requestedItemListAdapter = new RequestedItemListAdapter(requestedItemList,getContext(),this,this);//, this, this
        recyclerView_requested_item.setAdapter(requestedItemListAdapter);
        recyclerView_requested_item.setNestedScrollingEnabled(false);

        if(App_preference.getSharedprefInstance().getLoginRes().getCompPermission().get(0).getIsItemRequested() == 0){
            ll_requested_item.setVisibility(View.VISIBLE);
            if(mParam2 != null && mParam2.getItemRequested() != null && mParam2.getItemRequested().equals("1")){
                requested_item_flag.setVisibility(View.VISIBLE);
//                requested_itemList_show_hide_rl.setVisibility(View.VISIBLE);
                requested_item_txt.setClickable(true);
                requested_item_txt.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.down), null, null, null);

            }else {
                requested_item_txt.setClickable(false);
                requested_item_flag.setVisibility(View.GONE);
//                requested_itemList_show_hide_rl.setVisibility(View.GONE);
                requested_item_txt.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);

            }
        }else{
            ll_requested_item.setVisibility(View.GONE);
        }
    }

    private void addJobServicesInChips(JtId jtildModel) {
        LayoutInflater vi = (LayoutInflater) requireActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = vi.inflate(R.layout.job_lable_dynamic_layout, null);
        TextView textView = v.findViewById(R.id.job_lables);
        textView.setText(jtildModel.getTitle());
        chipGroup.setChipSpacing(10);
        chipGroup.addView(v, 0, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    @Override
    public void StopRecurPatternHide() {
        recurMsgShow.performClick();
//        recur_parent_view.setVisibility(View.GONE);
    }

    @Override
    public void setItemListByJob(List<InvoiceItemDataModel> itemList) {
        /* *** *Sort Item By name***/
        try {
            Collections.sort(itemList, (o1, o2) -> o1.getInm().compareTo(o2.getInm()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (itemList != null && invoice_list_adpter != null && itemList.size() > 0) {
            invoice_list_adpter.updateitemlist(itemList);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void setCompletionDetails(List<CompletionDetails> completionDetailsList) {


        date_ac_start.setText("");
        date_ac_end.setText("");
        date_tr_start.setText("");
        date_tr_end.setText("");
        tvActualJobTime.setText("00:00 Hours");
        tvTravelJobTime.setText("00:00 Hours");
        // for dismissing the dialog on update
        if (dialogActualTravelDateTime != null)
            dialogActualTravelDateTime.dismiss();


        completionDetails = completionDetailsList.get(0);
        String timeFormate = AppUtility.dateTimeByAmPmFormate(AppConstant.DATE_FORMAT+", hh:mm a", AppConstant.DATE_FORMAT+", HH:mm");
        String timeFormate1 = AppUtility.dateTimeByAmPmFormate(AppConstant.DATE_FORMAT+" hh:mm a", AppConstant.DATE_FORMAT+" HH:mm");

        if (completionDetailsList.get(0).getLoginTime() != null && !completionDetailsList.get(0).getLoginTime().isEmpty()&& !completionDetailsList.get(0).getLoginTime().equals("0")) {
            actualStart = AppUtility.getDate(Long.parseLong(completionDetailsList.get(0).getLoginTime()), timeFormate1);
            date_ac_start.setText("Start : " + AppUtility.getDateByLang(actualStart,true));
        }
        if (completionDetailsList.get(0).getLogoutTime() != null && !completionDetailsList.get(0).getLogoutTime().isEmpty()&& !completionDetailsList.get(0).getLogoutTime().equals("0")) {
            actualFinish = AppUtility.getDate(Long.parseLong(completionDetailsList.get(0).getLogoutTime()), timeFormate1);
            date_ac_end.setText("End : " + AppUtility.getDateByLang(actualFinish,true));

        }
        if (completionDetailsList.get(0).getTravel_loginTime() != null && !completionDetailsList.get(0).getTravel_loginTime().isEmpty()) {
            travelStart = AppUtility.getDate(Long.parseLong(completionDetailsList.get(0).getTravel_loginTime()), timeFormate1);
            date_tr_start.setText("Start : " + AppUtility.getDateByLang(travelStart,true));
        }
        if (completionDetailsList.get(0).getTarvel_logoutTime() != null && !completionDetailsList.get(0).getTarvel_logoutTime().isEmpty()) {
            travelFinish = AppUtility.getDate(Long.parseLong(completionDetailsList.get(0).getTarvel_logoutTime()), timeFormate1);
            date_tr_end.setText("End : " + AppUtility.getDateByLang(travelFinish,true));
        }
        if (completionDetailsList.get(0).getFirstTrvlBrkTime() != null && !completionDetailsList.get(0).getFirstTrvlBrkTime().isEmpty()) {
            firstTrvlBrkTime = completionDetailsList.get(0).getFirstTrvlBrkTime();
        }
        if (completionDetailsList.get(0).getLastTrvlBrkTime() != null && !completionDetailsList.get(0).getLastTrvlBrkTime().isEmpty()) {
            lastTrvlBrkTime = completionDetailsList.get(0).getLastBrkTime();
        }
        if (completionDetailsList.get(0).getFirstBrkTime() != null && !completionDetailsList.get(0).getFirstBrkTime().isEmpty()) {
            firstBrkTime = completionDetailsList.get(0).getFirstBrkTime();
        }
        if (completionDetailsList.get(0).getLastBrkTime() != null && !completionDetailsList.get(0).getLastBrkTime().isEmpty()) {
            lastBrkTime = completionDetailsList.get(0).getLastBrkTime();
        }
        if (completionDetailsList.get(0).getActualTime() != 0) {
            tvActualJobTime.setText(AppUtility.getRealTime(completionDetailsList.get(0).getActualTime(), completionDetailsList.get(0).getPauseTime()) + " Hours");
        }
        if (completionDetailsList.get(0).getTravelTime() != 0) {
            tvTravelJobTime.setText(AppUtility.getRealTime(completionDetailsList.get(0).getTravelTime(), completionDetailsList.get(0).getTravel_pauseTime()) + " Hours");
        }
        if (completionDetailsList.get(0).getLastStatus() != null && !completionDetailsList.get(0).getLastStatus().isEmpty()) {
            lastStatus = completionDetailsList.get(0).getLastStatus();
        }
        if (completionDetailsList.get(0).getLastStatusTime() != null && !completionDetailsList.get(0).getLastStatusTime().isEmpty()) {
            lastStatusTime = AppUtility.getDate(Long.parseLong(completionDetailsList.get(0).getLastStatusTime()), timeFormate1);
        }
        progressBar_timing.setVisibility(View.GONE);
    }


    @SuppressLint("SetTextI18n")
    private void showRecurmsg() {
        /*try {
            if (App_preference.getSharedprefInstance().getLoginRes().getRights().get(0).getIsRecur().equals("0")
                    && mParam2.getIsRecur() != null && mParam2.getIsRecur().equals("1") && mParam2.getRecurData().size() > 0) {
                recur_parent_view.setVisibility(View.VISIBLE);
                if (mParam2.getRecurType() != null && mParam2.getRecurType().equals("1") && mParam2.getRecurData() != null) {
                    if (mParam2.getRecurData().get(0).getJobRecurModel().getMode() != null
                            && mParam2.getRecurData().get(0).getJobRecurModel().getMode().equals("1")
                            && mParam2.getRecurData().get(0).getJobRecurModel().getEndRecurMode() != null
                            && mParam2.getRecurData().get(0).getJobRecurModel().getEndRecurMode().equals("0")) {
                        txt_recur_msg.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.custom_recur_msg1) + " " +
                                mParam2.getRecurData().get(0).getJobRecurModel().getInterval() + " " + LanguageController.getInstance().getMobileMsgByKey(AppConstant.starting_on) +
                                " " + mParam2.getRecurData().get(0).getJobRecurModel().getStartDate() + " " +
                                LanguageController.getInstance().getMobileMsgByKey(AppConstant.custom_recur_msg2) + " " +
                                LanguageController.getInstance().getMobileMsgByKey(AppConstant.infinity));
                    } else {
                        txt_recur_msg.setText
                                (LanguageController.getInstance().getMobileMsgByKey(AppConstant.custom_recur_msg1) + " " +
                                        " " + mParam2.getRecurData().get(0).getJobRecurModel().getInterval() +
                                        " " + LanguageController.getInstance().getMobileMsgByKey(AppConstant.starting_on)
                                        + " " + mParam2.getRecurData().get(0).getJobRecurModel().getStartDate() + " " +
                                        LanguageController.getInstance().getMobileMsgByKey(AppConstant.custom_recur_msg2)
                                        + " " + mParam2.getRecurData().get(0).getJobRecurModel().getOccurences() +
                                        " " + LanguageController.getInstance().getMobileMsgByKey(AppConstant.custom_recur_msg3)
                                        + " " + mParam2.getRecurData().get(0).getJobRecurModel().getEndDate());
                    }
                } else if (mParam2.getRecurType() != null && mParam2.getRecurType().equals("2")
                        && mParam2.getRecurData() != null
                        && mParam2.getRecurData().get(0).getJobRecurModel().getOccur_days() != null
                        && mParam2.getRecurData().get(0).getJobRecurModel().getInterval() != null) {
                    if (mParam2.getRecurData().get(0).getJobRecurModel().getEndRecurMode() != null
                            && mParam2.getRecurData().get(0).getJobRecurModel().getEndRecurMode().equals("0")) {
                        txt_recur_msg.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.weekly_msg1) + " " +
                                mParam2.getRecurData().get(0).getJobRecurModel().getOccur_days() + " " +
                                LanguageController.getInstance().getMobileMsgByKey(AppConstant.every) + " " +
                                mParam2.getRecurData().get(0).getJobRecurModel().getInterval() + " " +
                                LanguageController.getInstance().getMobileMsgByKey(AppConstant.weeks) + " " +
                                mParam2.getRecurData().get(0).getJobRecurModel().getStartDate() + " " +
                                LanguageController.getInstance().getMobileMsgByKey(AppConstant.custom_recur_msg2)
                                + " " + LanguageController.getInstance().getMobileMsgByKey(AppConstant.infinity));
                    } else {
                        txt_recur_msg.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.weekly_msg1) + " " +
                                mParam2.getRecurData().get(0).getJobRecurModel().getOccur_days() + " " +
                                LanguageController.getInstance().getMobileMsgByKey(AppConstant.every) + " " +
                                mParam2.getRecurData().get(0).getJobRecurModel().getInterval() + " " +
                                LanguageController.getInstance().getMobileMsgByKey(AppConstant.weeks) + " " +
                                mParam2.getRecurData().get(0).getJobRecurModel().getStartDate() + " " +
                                LanguageController.getInstance().getMobileMsgByKey(AppConstant.custom_recur_msg2)
                                + " " + mParam2.getRecurData().get(0).getJobRecurModel().getOccurences() +
                                " " + LanguageController.getInstance().getMobileMsgByKey(AppConstant.custom_recur_msg3) +
                                " " + mParam2.getRecurData().get(0).getJobRecurModel().getEndDate());
                    }

                } else if (mParam2.getRecurType() != null && mParam2.getRecurType().equals("3")
                        && mParam2.getRecurData() != null
                        && mParam2.getRecurData().get(0).getJobRecurModel().getEndRecurMode()
                        != null && mParam2.getRecurData().get(0).getJobRecurModel().getWeek_num() != null
                        && mParam2.getRecurData().get(0).getJobRecurModel().getInterval() != null) {
                    if (mParam2.getRecurData().get(0).getJobRecurModel().getEndRecurMode() != null
                            && mParam2.getRecurData().get(0).getJobRecurModel().getEndRecurMode().equals("0")) {
                        txt_recur_msg.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.custom_recur_msg1) + " " +
                                mParam2.getRecurData().get(0).getJobRecurModel().getInterval() + " " + LanguageController.getInstance().getMobileMsgByKey(AppConstant.starting_on) +
                                " " + mParam2.getRecurData().get(0).getJobRecurModel().getStartDate() + " " +
                                LanguageController.getInstance().getMobileMsgByKey(AppConstant.custom_recur_msg2) + " " +
                                LanguageController.getInstance().getMobileMsgByKey(AppConstant.infinity));
                    } else
                        txt_recur_msg.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.weekly_msg1) + " " +
                                mParam2.getRecurData().get(0).getJobRecurModel().getWeek_num() + " "
                                + LanguageController.getInstance().getMobileMsgByKey(AppConstant.every) + " " +

                                mParam2.getRecurData().get(0).getJobRecurModel().getInterval() + " " +
                                LanguageController.getInstance().getMobileMsgByKey(AppConstant.months_starting_on) + " "
                                + mParam2.getRecurData().get(0).getJobRecurModel().getStartDate() + " " +

                                LanguageController.getInstance().getMobileMsgByKey(AppConstant.custom_recur_msg2) + " "
                                + mParam2.getRecurData().get(0).getJobRecurModel().getOccurences() +
                                " " + LanguageController.getInstance().getMobileMsgByKey(AppConstant.custom_recur_msg3) + " " +
                                mParam2.getRecurData().get(0).getJobRecurModel().getEndDate());
                }
            } else {
                recur_parent_view.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    private void flagVisibility() {
        if (mParam2 != null) {
            if (mParam2.getAttachCount() != null && !mParam2.getAttachCount().isEmpty() && Integer.parseInt(mParam2.getAttachCount()) > 0) {
                attachmemt_flag.setVisibility(View.VISIBLE);
            } else {
                attachmemt_flag.setVisibility(View.GONE);
            }
            if (mParam2.getItemData() != null && mParam2.getItemData().size() > 0) {
                item_flag.setVisibility(View.VISIBLE);
                // for updating data
                invoice_list_adpter.updateitemlist(mParam2.getItemData());
            } else {
                item_flag.setVisibility(View.GONE);
            }
            if (mParam2.getEquArray() != null && mParam2.getEquArray().size() > 0) {
                equi_flag.setVisibility(View.VISIBLE);
            } else {
                equi_flag.setVisibility(View.GONE);
            }
        }
    }


    public void getViewIds() {
        buttonAccept.setOnClickListener(this);
        buttonDecline.setOnClickListener(this);
        textViewAddress.setOnClickListener(this);
        imageViewEmail.setOnClickListener(this);
        buttonView.setOnClickListener(this);
        imageViewChat.setOnClickListener(this);
        imageViewCall.setOnClickListener(this);
        btnComplationView.setOnClickListener(this);
        btn_add_eq.setOnClickListener(this);
        btn_add_item.setOnClickListener(this);
        customfiled_btn.setOnClickListener(this);
        quotes_details_card.setOnClickListener(this);

        jobDetail_pi = new JobDetail_pc(this);

        try {
            if (mParam2 == null) {
                HyperLog.i(TAG, "Job Not Found In DB");
                return;
            }
            if (mParam2.getStatus() != null && !AppUtility.getJobStatusList().contains(mParam2.getStatus()) && mParam2.getJobId() != null) {
                AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().deleteJobById(mParam2.getJobId());
                HyperLog.i(TAG, "Job Status Not Found In DB");
                return;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            HyperLog.i(TAG, "Job Not Found In DB");
            return;
        }

        /* ***custom fields question list** */
        if(App_preference.getSharedprefInstance().getLoginRes().getRights().get(0).getIsCustomField()==0) {
            if (App_preference.getSharedprefInstance().getLoginRes().getIsCustomFieldEnable().equals("1")) {
                jobDetail_pi.getCustomFieldQues(mParam2.getJobId());
            }
        }else {
            customField_view.setVisibility(View.GONE);
        }

        addComplationButtonTxt();
        setDataToView();


        //permission for showing completion details edit actual and travel time
        try {
            if (App_preference.getSharedprefInstance().getLoginRes().getCompPermission().get(0).getIsEditJobAndTravelTime().equalsIgnoreCase("0")) {
                btnActualEdit.setVisibility(View.VISIBLE);
                btnTravelEdit.setVisibility(View.VISIBLE);
                date_ac_start.setEnabled(true);
                date_ac_end.setEnabled(true);
                date_tr_end.setEnabled(true);
                date_tr_start.setEnabled(true);
            } else {
                btnActualEdit.setVisibility(View.GONE);
                btnTravelEdit.setVisibility(View.GONE);
                date_ac_start.setEnabled(false);
                date_ac_end.setEnabled(false);
                date_tr_end.setEnabled(false);
                date_tr_start.setEnabled(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

       /* if (mParam2.getEquArray() != null && !mParam2.getEquArray().isEmpty()) {
            //For fetching local list for the first time
            setEuqipmentList(mParam2.getEquArray());
        }*/
        AppUtility.spinnerPopUpWindow(new_status_spinner);
        //get data from other db's
        if (mParam2 != null) {
            setJobDetail();

            jobstatus = new JobStatusModelNew(mParam2.getStatus(), jobDetail_pi.getStatusName(mParam2.getStatus()), jobDetail_pi.getImg());
            setButtonsUI(jobstatus); //changes
//          this check is use for show/hide contact detail.
            if (jobDetail_pi.checkContactHideOrNot()) {
                contact_card.setVisibility(View.GONE);
            }else {
                // this check is use for show/hide contatct detail on dispatch status IsHideContactOnDispatch == 0- enable/show , 1 -disable /hide
                if(App_preference.getSharedprefInstance().getLoginRes().getIsHideContactOnDispatch().equals("1")
                        && mParam2.getStatus().equals(AppConstant.Not_Started)){
                    contact_card.setVisibility(View.GONE);
                }
            }
        }

        new_status_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        /* **** Equipment Item & attachment visibility***/
        flagVisibility();
/*
        if (!mParam2.getJobId().equals(mParam2.getTempId()))
            showRecurmsg();
        else recur_parent_view.setVisibility(View.GONE);*/
        // for equipment
        if (jobDetail_pi != null)
            jobDetail_pi.getEquipmentStatus();
        setEuqipmentList(mParam2.getEquArray());
        if (jobDetail_pi != null)
            jobDetail_pi.loadFromServer(mParam2.getJobId());
        // for completion details
        if (jobDetail_pi != null)
            jobDetail_pi.getJobCompletionDetails(mParam2.getJobId());
        // for completion details
        // for get attachment
        if (jobDetail_pi != null) {
            jobDetail_pi.getAttachFileList(mParam2.getJobId(), App_preference.getSharedprefInstance().getLoginRes().getUsrId()
                    , "");
        }
    }


    @Override
    public void setList(ArrayList<Attachments> getFileList_res, String isAttachCommpletionNotes) {
        if(isAttachCommpletionNotes.equals("6")){
            if(jobDetail_pi != null) {
                jobDetail_pi.loadFromServer(mParam2.getJobId());
            }
        }
        if(jobCompletionAdpter != null) {

            if(getFileList_res.size() == 0){
                recyclerView.setVisibility(View.GONE);
            }else {
                recyclerView.setVisibility(View.VISIBLE);
                (jobCompletionAdpter).updateFileList(getFileList_res);
            }
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void setRecurData(RecurReqResModel recurData) {
        this.recurData = recurData;
        progressBar_cyclic.setVisibility(View.GONE);
        recurMsgHide.setVisibility(View.VISIBLE);
        recurMsgShow.setVisibility(View.GONE);
        liner_layout_for_recurmsg.setVisibility(View.VISIBLE);
        try {
            if ( recurData != null) {
                if(recurData.getJobRecurModel().getRecurStatus().equalsIgnoreCase("0") || recurData.getJobRecurModel().getRecurStatus().equalsIgnoreCase("3")) {
                    btnStopRecurView.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.pause));
                    btnStopRecurView.setTextColor(this.getResources().getColor(R.color.dark_yellow));
                    btnStopRecurView.setClickable(true);
                }else if(recurData.getJobRecurModel().getRecurStatus().equalsIgnoreCase("2")){
                    btnStopRecurView.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.resume));
                    btnStopRecurView.setTextColor(this.getResources().getColor(R.color.green1));
                    btnStopRecurView.setClickable(true);
                } else if (recurData.getJobRecurModel().getRecurStatus().equalsIgnoreCase("1")) {
                    btnStopRecurView.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.expired));
                    btnStopRecurView.setTextColor(this.getResources().getColor(R.color.red_color));
                    btnStopRecurView.setClickable(false);
                }
                if (mParam2.getRecurType() != null && mParam2.getRecurType().equals("1") || mParam2.getParentRecurType() != null && mParam2.getParentRecurType().equals("1")) {
                    if (recurData.getJobRecurModel().getMode() != null &&recurData.getJobRecurModel().getMode().equals("1") && recurData.getJobRecurModel().getEndRecurMode() != null
                            && recurData.getJobRecurModel().getEndRecurMode().equals("0")) {
                        txt_recur_msg.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.custom_recur_msg1) + " " +
                                recurData.getJobRecurModel().getInterval() + " " + LanguageController.getInstance().getMobileMsgByKey(AppConstant.starting_on) +
                                " " + recurData.getJobRecurModel().getStartDate() + " " +
                                LanguageController.getInstance().getMobileMsgByKey(AppConstant.custom_recur_msg2) + " " +
                                LanguageController.getInstance().getMobileMsgByKey(AppConstant.infinity));
                    } else {
                        txt_recur_msg.setText
                                (LanguageController.getInstance().getMobileMsgByKey(AppConstant.custom_recur_msg1) + " " +
                                        " " + recurData.getJobRecurModel().getInterval() +
                                        " " + LanguageController.getInstance().getMobileMsgByKey(AppConstant.starting_on)
                                        + " " + recurData.getJobRecurModel().getStartDate() + " " +
                                        LanguageController.getInstance().getMobileMsgByKey(AppConstant.custom_recur_msg2)
                                        + " " + recurData.getJobRecurModel().getOccurences() +
                                        " " + LanguageController.getInstance().getMobileMsgByKey(AppConstant.custom_recur_msg3)
                                        + " " + recurData.getJobRecurModel().getEndDate());
                    }
                } else if (mParam2.getRecurType() != null && mParam2.getRecurType().equals("2") && recurData.getJobRecurModel().getOccur_days() != null
                        && recurData.getJobRecurModel().getInterval() != null || mParam2.getParentRecurType() != null && mParam2.getParentRecurType().equals("2") && recurData.getJobRecurModel().getOccur_days() != null
                        && recurData.getJobRecurModel().getInterval() != null) {
                    if (recurData.getJobRecurModel().getEndRecurMode() != null && recurData.getJobRecurModel().getEndRecurMode().equals("0")) {
                        txt_recur_msg.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.weekly_msg1) + " " +
                                recurData.getJobRecurModel().getOccur_days() + " " +
                                LanguageController.getInstance().getMobileMsgByKey(AppConstant.every) + " " +
                                recurData.getJobRecurModel().getInterval() + " " +
                                LanguageController.getInstance().getMobileMsgByKey(AppConstant.weeks) + " " +
                                recurData.getJobRecurModel().getStartDate() + " " +
                                LanguageController.getInstance().getMobileMsgByKey(AppConstant.custom_recur_msg2)
                                + " " + LanguageController.getInstance().getMobileMsgByKey(AppConstant.infinity));
                    } else {
                        txt_recur_msg.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.weekly_msg1) + " " +
                                recurData.getJobRecurModel().getOccur_days() + " " +
                                LanguageController.getInstance().getMobileMsgByKey(AppConstant.every) + " " +
                                recurData.getJobRecurModel().getInterval() + " " +
                                LanguageController.getInstance().getMobileMsgByKey(AppConstant.weeks) + " " +
                                recurData.getJobRecurModel().getStartDate() + " " +
                                LanguageController.getInstance().getMobileMsgByKey(AppConstant.custom_recur_msg2)
                                + " " + recurData.getJobRecurModel().getOccurences() +
                                " " + LanguageController.getInstance().getMobileMsgByKey(AppConstant.custom_recur_msg3) +
                                " " + recurData.getJobRecurModel().getEndDate());
                    }

                } else if (mParam2.getRecurType() != null && mParam2.getRecurType().equals("3")
                        && recurData.getJobRecurModel().getEndRecurMode() != null && recurData.getJobRecurModel().getWeek_num() != null && recurData.getJobRecurModel().getInterval() != null
                || mParam2.getParentRecurType() != null && mParam2.getParentRecurType().equals("3")
                        && recurData.getJobRecurModel().getEndRecurMode() != null && recurData.getJobRecurModel().getWeek_num() != null && recurData.getJobRecurModel().getInterval() != null) {
                    if (recurData.getJobRecurModel().getEndRecurMode() != null && recurData.getJobRecurModel().getEndRecurMode().equals("0")) {
                        txt_recur_msg.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.custom_recur_msg1) + " " +
                                recurData.getJobRecurModel().getInterval() + " " + LanguageController.getInstance().getMobileMsgByKey(AppConstant.starting_on) +
                                " " + recurData.getJobRecurModel().getStartDate() + " " +
                                LanguageController.getInstance().getMobileMsgByKey(AppConstant.custom_recur_msg2) + " " +
                                LanguageController.getInstance().getMobileMsgByKey(AppConstant.infinity));
                    } else
                        txt_recur_msg.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.weekly_msg1) + " " +
                                recurData.getJobRecurModel().getWeek_num() + " " + LanguageController.getInstance().getMobileMsgByKey(AppConstant.every) + " " +
                                recurData.getJobRecurModel().getInterval() + " " + LanguageController.getInstance().getMobileMsgByKey(AppConstant.months_starting_on) + " "
                                + recurData.getJobRecurModel().getStartDate() + " " + LanguageController.getInstance().getMobileMsgByKey(AppConstant.custom_recur_msg2) + " "
                                + recurData.getJobRecurModel().getOccurences() +
                                " " + LanguageController.getInstance().getMobileMsgByKey(AppConstant.custom_recur_msg3) + " " +
                                recurData.getJobRecurModel().getEndDate());
                }
            } else {
                recurMsgHide.setVisibility(View.GONE);
                recurMsgShow.setVisibility(View.VISIBLE);
                liner_layout_for_recurmsg.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setRequestItemData(List<RequestedItemModel> requestItemData) {
        progressBar_itemRequest.setVisibility(View.GONE);
            if(requestItemData != null && requestItemData.size() > 0){
                requested_item_txt.setClickable(true);
                if(isRefreshReqItem){
                    isRefreshReqItem = false;
                    requested_item_flag.setVisibility(View.VISIBLE);
//                    requested_itemList_show_hide_rl.setVisibility(View.VISIBLE);
                    requested_item_txt.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.down), null, null, null);
                }else {
                    requested_item_flag.setVisibility(View.VISIBLE);
//                    requested_itemList_show_hide_rl.setVisibility(View.VISIBLE);
                    requested_item_txt.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.up), null, null, null);
                    recyclerView_requested_item.setVisibility(View.VISIBLE);
                    txt_no_item_found.setVisibility(View.GONE);
                    requestedItemListAdapter.setReqItemList(requestItemData);
                }
            }else {
                requested_item_txt.setClickable(false);
//                requested_itemList_show_hide_rl.setVisibility(View.GONE);
                requested_item_txt.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                requested_item_flag.setVisibility(View.GONE);
                requestedItemListAdapter.setReqItemList(new ArrayList<>());
                recyclerView_requested_item.setVisibility(View.GONE);
            }
    }

    @Override
    public void notDataFoundInRecureData(String msg) {
        progressBar_cyclic.setVisibility(View.GONE);
        AppUtility.alertDialog(getActivity(), LanguageController.getInstance().getMobileMsgByKey(AppConstant.dialog_error_title),
                msg, LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok), "", () -> {
            return null;

        });
    }

    @Override
    public void notDtateFoundInRequestedItemList(String msg) {
        EotApp.getAppinstance().showToastmsg(msg);
        progressBar_itemRequest.setVisibility(View.GONE);
        requested_item_txt.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.down), null, null, null);
//        show_requested_list.setVisibility(View.VISIBLE);
        recyclerView_requested_item.setVisibility(View.GONE);
//        hide_requested_list.setVisibility(View.GONE);
    }

    @Override
    public void deletedRequestData(String message,AddUpdateRequestedModel requestedModel) {
        EotApp.getAppinstance().showToastmsg(LanguageController.getInstance().getServerMsgByKey(message.trim()));
        jobDetail_pi.getRequestedItemDataList(mParam2.getJobId());
        if(requestedModel != null) {
            if(requestedModel.getEbId() != null && !requestedModel.getEbId().equals("0") && !requestedModel.getEbId().equals("")) {
                brandName = AppDataBase.getInMemoryDatabase(getContext()).brandDao().getBrandNameById(requestedModel.getEbId());
            }
            String msg =
                    LanguageController.getInstance().getMobileMsgByKey(AppConstant.field_user_made_some_changes_on_the_requested_item)+"\n"+LanguageController.getInstance().getMobileMsgByKey(AppConstant.item_name)+": "+requestedModel.getItemName()+"\n"+
                            LanguageController.getInstance().getMobileMsgByKey(AppConstant.qty)+": "+requestedModel.getQty()+"\n"+
                            LanguageController.getInstance().getMobileMsgByKey(AppConstant.part_no)+": "+requestedModel.getModelNo()+"\n"+
                            LanguageController.getInstance().getMobileMsgByKey(AppConstant.brand)+": "+brandName;
            Chat_Send_Msg_Model chat_send_Msg_model = new Chat_Send_Msg_Model(
                    msg, "", AppUtility.getDateByMiliseconds(),
                    mParam2.getLabel(),
                    requestedModel.getJobId(), "1");
            if (jobDetail_pi != null) {
                jobDetail_pi.sendMsg(chat_send_Msg_model);
            }
        }
    }

    @Override
    public void setOfflineData() {
        isMarkDoneWithJtidsList.clear();
        if(AppUtility.isInternetConnected()) {
            mParam2 = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().getJobsById(mParam2.getJobId());
            if(mParam2 != null && mParam2.getComplNote() != null && mParam2.getComplNote().isEmpty() && mParam2.getCompliAnsArray() != null && mParam2.getCompliAnsArray().isEmpty()){
                txt_notesHeader.setVisibility(View.GONE);
                complation_notes.setVisibility(View.GONE);
                btnComplationView.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.add));
            }else if(mParam2 != null && mParam2.getComplNote() != null && mParam2.getComplNote().isEmpty() &&  mParam2.getCompliAnsArray() != null && !mParam2.getCompliAnsArray().isEmpty()){
                txt_notesHeader.setVisibility(View.GONE);
                complation_notes.setVisibility(View.GONE);
                btnComplationView.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.edit));
            }else if(mParam2 != null && mParam2.getComplNote() != null && mParam2.getComplNote().isEmpty()){
                txt_notesHeader.setVisibility(View.GONE);
                complation_notes.setVisibility(View.GONE);
                btnComplationView.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.add));
            }else {
                String tempstring=mParam2.getComplNote().replace("null", "");
                tempstring.replace("<br>","");
                complation_notes.setText(tempstring);
                btnComplationView.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.edit));
                txt_notesHeader.setVisibility(View.VISIBLE);
                complation_notes.setVisibility(View.VISIBLE);
            }
            if(mParam2 != null && mParam2.getIsMarkDoneWithJtId() !=null && mParam2.getIsMarkDoneWithJtId().size()>0) {
                cl_serviceMarkAsDone.setVisibility(View.VISIBLE);
                isMarkDoneWithJtidsList.addAll(mParam2.getIsMarkDoneWithJtId());
                serviceMarkDoneAdapter.updatList(mParam2.getIsMarkDoneWithJtId());
            }else {
                cl_serviceMarkAsDone.setVisibility(View.GONE);
            }
        }else if(mParam2 != null && AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).offline_completion_ans_dao().getComplQueAnsById(mParam2.getJobId()) != null){
            OfflieCompleQueAns offlieCompleQueAns = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).offline_completion_ans_dao().getComplQueAnsById(mParam2.getJobId());
            mParam2 = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().getJobsById(mParam2.getJobId());
            if(mParam2.getComplNote() != null && mParam2.getComplNote().isEmpty() && offlieCompleQueAns.getAllQuestionAnswer().isEmpty()){
                txt_notesHeader.setVisibility(View.GONE);
                complation_notes.setVisibility(View.GONE);
                btnComplationView.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.add));
            }else if(mParam2 != null && mParam2.getComplNote() != null && mParam2.getComplNote().isEmpty() && mParam2.getCompliAnsArray() != null &&  !mParam2.getCompliAnsArray().isEmpty()){
                txt_notesHeader.setVisibility(View.GONE);
                complation_notes.setVisibility(View.GONE);
                btnComplationView.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.edit));
            }else if(mParam2 != null && mParam2.getComplNote() != null && mParam2.getComplNote().isEmpty()){
                txt_notesHeader.setVisibility(View.GONE);
                complation_notes.setVisibility(View.GONE);
                btnComplationView.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.add));
            }else {
                String tempstring = "";
                if(mParam2 != null) {
                     tempstring = mParam2.getComplNote().replace("null", "");
                }
                tempstring.replace("<br>","");
                complation_notes.setText(tempstring);
                btnComplationView.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.edit));
                txt_notesHeader.setVisibility(View.VISIBLE);
                complation_notes.setVisibility(View.VISIBLE);
            }

            if(offlieCompleQueAns.getIsMarkDoneWithJtId().size()>0) {
                cl_serviceMarkAsDone.setVisibility(View.VISIBLE);
                isMarkDoneWithJtidsList.addAll(offlieCompleQueAns.getIsMarkDoneWithJtId());
                serviceMarkDoneAdapter.updatList(offlieCompleQueAns.getIsMarkDoneWithJtId());
            }else {
                cl_serviceMarkAsDone.setVisibility(View.GONE);
            }

        }else {
            if(mParam2 != null) {
                mParam2 = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().getJobsById(mParam2.getJobId());
                if (mParam2 != null && mParam2.getComplNote() != null && mParam2.getComplNote().isEmpty() && mParam2.getCompliAnsArray() != null && mParam2.getCompliAnsArray().isEmpty()) {
                    txt_notesHeader.setVisibility(View.GONE);
                    complation_notes.setVisibility(View.GONE);
                    btnComplationView.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.add));
                } else if (mParam2 != null && mParam2.getComplNote() != null && mParam2.getComplNote().isEmpty() && mParam2.getCompliAnsArray() != null && !mParam2.getCompliAnsArray().isEmpty()) {
                    txt_notesHeader.setVisibility(View.GONE);
                    complation_notes.setVisibility(View.GONE);
                    btnComplationView.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.edit));
                } else if (mParam2 != null && mParam2.getComplNote() != null && mParam2.getComplNote().isEmpty()) {
                    txt_notesHeader.setVisibility(View.GONE);
                    complation_notes.setVisibility(View.GONE);
                    btnComplationView.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.add));
                } else {
                    String tempstring = "";
                    if (mParam2 != null) {
                        tempstring = mParam2.getComplNote().replace("null", "");
                    }
                    tempstring.replace("<br>", "");
                    complation_notes.setText(tempstring);
                    btnComplationView.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.edit));
                    txt_notesHeader.setVisibility(View.VISIBLE);
                    complation_notes.setVisibility(View.VISIBLE);
                }
                if (mParam2 != null && mParam2.getIsMarkDoneWithJtId() != null && mParam2.getIsMarkDoneWithJtId().size() > 0) {
                    cl_serviceMarkAsDone.setVisibility(View.VISIBLE);
                    isMarkDoneWithJtidsList.addAll(mParam2.getIsMarkDoneWithJtId());
                    serviceMarkDoneAdapter.updatList(mParam2.getIsMarkDoneWithJtId());
                } else {
                    cl_serviceMarkAsDone.setVisibility(View.GONE);
                }
            }
        }
        cl_pbCompletion.setVisibility(View.GONE);
        setCompletionDetail();
//        (jobCompletionAdpter).updateFileList((ArrayList<Attachments>) AppDataBase.getInMemoryDatabase(requireActivity()).attachments_dao().getAttachmentsByJobId(mParam2.getJobId()));
//        addComplationButtonTxt();
        if(App_preference.getSharedprefInstance().getLoginRes().getIsCompleShowMarkDone().equals("1")) {
            if (App_preference.getSharedprefInstance().getLoginRes().getIsJobCompCustSignEnable().equals("0")) {
                if(mParam2 != null && mParam2.getStatus() != null && !mParam2.getStatus().equalsIgnoreCase("9")) {
                    if(isAskForCompleteJob) {
                        checkMarkServices();
                        isAskForCompleteJob = false;
                    }
                }
            }
        }

    }

    /*** add completion button view ****/
    private void addComplationButtonTxt() {
        String tempstring ="";
        HyperLog.i(TAG, "addComplationButtonTxt(M) start");
        HyperLog.i(TAG, mParam2.getComplNote());
        if (mParam2.getComplNote() !=null && TextUtils.isEmpty(mParam2.getComplNote()) && mParam2.getCompliAnsArray() != null &&  mParam2.getCompliAnsArray().isEmpty()) {
            txt_notesHeader.setVisibility(View.GONE);
            complation_notes.setVisibility(View.GONE);
            btnComplationView.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.add));
        }else if(mParam2.getComplNote() !=null && mParam2.getComplNote().isEmpty() && mParam2.getCompliAnsArray() != null && !mParam2.getCompliAnsArray().isEmpty()){
            txt_notesHeader.setVisibility(View.GONE);
            complation_notes.setVisibility(View.GONE);
            btnComplationView.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.edit));
        }else if(mParam2.getComplNote() != null && mParam2.getComplNote().isEmpty()){
            txt_notesHeader.setVisibility(View.GONE);
            complation_notes.setVisibility(View.GONE);
            btnComplationView.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.add));
        } else {
            txt_notesHeader.setVisibility(View.VISIBLE);
            complation_notes.setVisibility(View.VISIBLE);
            btnComplationView.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.edit));
            if(mParam2.getComplNote() != null) {
                tempstring = mParam2.getComplNote().replace("null", "");
                tempstring.replace("<br>","");
            }
            complation_notes.setText(tempstring);
        }
       setCompletionDetail();
        HyperLog.i(TAG, "addComplationButtonTxt(M) Stop");
    }
    int doneMark =0;
public void setCompletionDetail(){
    isMarkDoneWithJtidsList.clear();
    if(mParam2.getIsMarkDoneWithJtId() != null && mParam2.getIsMarkDoneWithJtId().size()>0){
        isMarkDoneWithJtidsList.addAll(mParam2.getIsMarkDoneWithJtId());
        if(mParam2.getJtId() != null) {
            for (JtId jtId : mParam2.getJtId()) {
                if (!checkInList(jtId.getJtId())) {
                    isMarkDoneWithJtidsList.add(new IsMarkDoneWithJtid("0", jtId.getJtId(), jtId.getTitle()));
                }
            }
        }
        setServiceMarkDoneList(isMarkDoneWithJtidsList);
    }else {
        if(mParam2.getJtId() != null)
        for (JtId jtid: mParam2.getJtId()
        ) {
            IsMarkDoneWithJtid isMarkDoneWithJtid = new IsMarkDoneWithJtid("0",jtid.getJtId(),jtid.getTitle());
            isMarkDoneWithJtidsList.add(isMarkDoneWithJtid);
        }
        setServiceMarkDoneList(isMarkDoneWithJtidsList);
    }

    List<IsMarkDoneWithJtid> list = new ArrayList<>();
    list.addAll(isMarkDoneWithJtidsList);

    //show done service
    if(App_preference.getSharedprefInstance().getLoginRes().getIsCompleShowMarkDone().equals("0")){
        cl_serviceMarkAsDone.setVisibility(View.GONE);
    }else {
        if(isAllServicesDone()){
            txt_serviceHeader.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.all)+" "+LanguageController.getInstance().getMobileMsgByKey(AppConstant.services_done));
            cl_serviceMarkAsDone.setVisibility(View.VISIBLE);
            serviceMarkDoneAdapter.updatList(list);
        }else {
            doneMark = 0;
            for (IsMarkDoneWithJtid item: isMarkDoneWithJtidsList
            ) {
                if(item.getStatus().equals("1")){
                    doneMark++;
                }
            }
            if(doneMark == 1) {
                txt_serviceHeader.setText("" + doneMark + " "+LanguageController.getInstance().getMobileMsgByKey(AppConstant._services_done));
            }else {
                txt_serviceHeader.setText("" + doneMark + " "+LanguageController.getInstance().getMobileMsgByKey(AppConstant.services_done));
            }
            if(doneMark==0){
                cl_serviceMarkAsDone.setVisibility(View.GONE);
            }else {
                cl_serviceMarkAsDone.setVisibility(View.VISIBLE);
                serviceMarkDoneAdapter.updatList(list);
            }
        }

    }
    //show attachment of completion of job
    ArrayList <Attachments> attachmentsArrayList = new ArrayList<>((ArrayList<Attachments>) AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).attachments_dao().getAttachmentsByJobId(mParam2.getJobId()));
    if(attachmentsArrayList.size() == 0){
        recyclerView.setVisibility(View.GONE);
    }else {
        recyclerView.setVisibility(View.VISIBLE);
        (jobCompletionAdpter).updateFileList(attachmentsArrayList);
    }
//        jobDetail_pi.getAttachFileList(mParam2.getJobId(), App_preference.getSharedprefInstance().getLoginRes().getUsrId()
//                , "6");
}
    /***update form list after Ans Submit***/
    public void getUpdateForm() {
        if (jobDetail_pi != null) {
            if (App_preference.getSharedprefInstance().getLoginRes().getIsCustomFieldEnable().equals("1")) {
                jobDetail_pi.getCustomFieldQues(mParam2.getJobId());
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private void setJobDetail() {
        try {
            if (mParam2 != null) {

                if (App_preference.getSharedprefInstance().getLoginRes().getCompPermission().get(0).getIsCompNmShowMobile() != null &&
                        App_preference.getSharedprefInstance().getLoginRes().getCompPermission().get(0).getIsCompNmShowMobile().equals("0")) {
                    person_name.setVisibility(View.GONE);
                } else {
                    person_name.setVisibility(View.VISIBLE);
                }
                try {
                    if (mParam2.getNm() != null && !mParam2.getNm().equals("") && mParam2.getNm().length() > 1)
                        person_name.setText(mParam2.getNm().substring(0, 1).toUpperCase() + mParam2.getNm().substring(1).toLowerCase());
                    else person_name.setText(mParam2.getNm());
                } catch (Exception exception) {
                    person_name.setText(mParam2.getNm());
                    exception.printStackTrace();
                }

                try {
                    if (App_preference.getSharedprefInstance().getSiteNameShowInSetting()) {
                        site_name.setVisibility(View.VISIBLE);
                        if (!TextUtils.isEmpty(mParam2.getSnm())) {
                            site_name.setText(mParam2.getSnm());
                            ll_po_number.setVisibility(View.VISIBLE);
                        } else {
                            site_name.setText("");
                            site_name.setVisibility(View.INVISIBLE);
                        }
                    } else {
                        site_name.setVisibility(View.INVISIBLE);
                        if(textViewPONumber.getVisibility() == View.GONE){
                            ll_po_number.setVisibility(View.GONE);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                StringBuffer sb_adr = new StringBuffer();
                if (mParam2.getAdr() != null && !mParam2.getAdr().equals("")) {
                    sb_adr.append(mParam2.getAdr());
                }
                if (!TextUtils.isEmpty(mParam2.getLandmark())) {
                    sb_adr.append(", " + mParam2.getLandmark());
                }
                if (mParam2.getCity() != null && !mParam2.getCity().equals("")) {
                    sb_adr.append(", " + mParam2.getCity());
                }
                if (mParam2.getState() != null && !mParam2.getState().equals("")) {
                    sb_adr.append(", " + SpinnerCountrySite.getStatenameById(mParam2.getCtry(), mParam2.getState()));
                }
                if (mParam2.getCtry() != null && !mParam2.getCtry().equals("")) {
                    sb_adr.append(", " + SpinnerCountrySite.getCountryNameById(mParam2.getCtry()));
                }
                if (!TextUtils.isEmpty(mParam2.getZip())) {
                    sb_adr.append(", " + mParam2.getZip());
                }

                String upperStringAdrs = "";
                try {
                    upperStringAdrs = sb_adr.substring(0, 1).toUpperCase() + sb_adr.substring(1).toLowerCase();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }

                SpannableStringBuilder builder = new SpannableStringBuilder();
                SpannableString str1 = new SpannableString(upperStringAdrs);
                builder.append(str1);
                SpannableString str2 = new SpannableString("  " + LanguageController.getInstance().getMobileMsgByKey(AppConstant.get_direction));

                str2.setSpan(new ForegroundColorSpan(requireActivity().getResources().getColor(R.color.colorPrimary)),
                        0, str2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                builder.append(str2);

                textViewAddress.setText(builder, TextView.BufferType.SPANNABLE);
                strAddress = sb_adr + "";

                try {
                    if (mParam2.getCnm() != null && !mParam2.getCnm().equals("") && mParam2.getCnm().length() > 1) {
                        String upperString = mParam2.getCnm().substring(0, 1).toUpperCase() + mParam2.getCnm().substring(1).toLowerCase();
                        textViewContactperson.setText(upperString);
                    } else {
                        textViewContactperson.setText(mParam2.getCnm());
                    }
                } catch (Exception exception) {
                    textViewContactperson.setText(mParam2.getCnm());
                    exception.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Reschedule and Revisit works on permission basis from admin
     **/
    private void showHideRescheduleRevisit(String status) {
        //    List<JobStatusModelNew> jobStatusList = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobStatusModelNew().getFwStatusList();

        // Important
        // setting 0 for reschedule as the status are starting from 1
        // setting 1 in key for revisit as we are not including not started into the dropdown list so we can use this id for revisit
        if (status != null) {
           /* if (status.equals(AppConstant.Not_Started)) {
                if (App_preference.getSharedprefInstance().getLoginRes().getRights().get(0).getIsJobRescheduleOrNot() != 1) {
                    if (!arraystatus.containsKey("0")) {
                        arraystatusvalue.put("0", LanguageController.getInstance().getMobileMsgByKey(AppConstant.reschedule));
                        arraystatus.put("0", LanguageController.getInstance().getMobileMsgByKey(AppConstant.reschedule));
                    }
                } else {
                    if (arraystatus.containsKey("0")) {
                        arraystatusvalue.remove(0);
                        arraystatus.remove(0);
                    }
                }
                if (arraystatus.containsKey("1")) {
                    arraystatus.remove(1);
                    arraystatusvalue.remove(1);
                }

                // for adding custom status in drop down list
                for (JobStatusModelNew jobStatus : jobStatusList) {
                    if (jobStatus.getIsDefault().equalsIgnoreCase("0")) {
                        arraystatusvalue.put(jobStatus.getKey(), jobStatus.getKey());
                        arraystatus.put(jobStatus.getKey(), jobStatus.getStatus_name());
                    }
                }
                notifiMyAdpterForStatusDp();
            } else {*/
            if (App_preference.getSharedprefInstance().getLoginRes().getRights().get(0).getIsJobRescheduleOrNot() != 1) {
                if (!arraystatus.containsKey("0")) {
                    arraystatus.put("0", LanguageController.getInstance().getMobileMsgByKey(AppConstant.reschedule));
                    arraystatusvalue.put("0", LanguageController.getInstance().getMobileMsgByKey(AppConstant.reschedule));
                }
            } else {
                if (arraystatus.containsKey("0")) {
                    arraystatusvalue.remove(0);
                    arraystatus.remove(0);
                }
            }
            // added 19 for revisit instead of 1 because we are managing custom status
            if (App_preference.getSharedprefInstance().getLoginRes().getRights().get(0).getIsJobRevisitOrNot() != 1) {
                if (!arraystatus.containsKey("revisite")) {
                    arraystatus.put("revisite", LanguageController.getInstance().getMobileMsgByKey(AppConstant.require_revisit));
                    arraystatusvalue.put("revisite", LanguageController.getInstance().getMobileMsgByKey(AppConstant.require_revisit));
                }
            } else {
                if (arraystatus.containsKey("revisite")) {
                    arraystatus.remove("revisite");
                    arraystatusvalue.remove("revisite");
                }
            }
            addJobStatusInList(status);
        }
    }


    //set job status name on button
    @Override
    synchronized public void setButtonsUI(JobStatusModelNew model) {
        List<JobStatusModelNew> jobStatusList = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobStatusModelNew().getAllStatusList();
        for (JobStatusModelNew jobStatusModelNew:jobStatusList){
            switch (jobStatusModelNew.getStatus_no())
            {
                case "2":
                    if (jobStatusModelNew.getIsStatusShow().equals("0")||jobStatusModelNew.getIsFwSelect().equals("0")) {
                        accept = false;
                    }
                    break;
                case "3":
                    if (jobStatusModelNew.getIsStatusShow().equals("0")||jobStatusModelNew.getIsFwSelect().equals("0")) {
                        reject=false;
                    }
                    break;
                case "5":
                    if (jobStatusModelNew.getIsStatusShow().equals("0")||jobStatusModelNew.getIsFwSelect().equals("0")) {
                        travel = false;
                    }
                    break;
                case "6":
                    if (jobStatusModelNew.getIsStatusShow().equals("0")||jobStatusModelNew.getIsFwSelect().equals("0")) {
                        brack = false;
                    }
                    break;
                case "12":
                    if (jobStatusModelNew.getIsStatusShow().equals("0")||jobStatusModelNew.getIsFwSelect().equals("0")) {
                        onhold = false;
                    }
                    break;
            }
        }

        HyperLog.i(TAG, "setButtonsUI(M) Update UI started");
        HyperLog.i(TAG, "setButtonsUI(M) 12" + new Gson().toJson(model));
        try {
            if (mParam2 != null)
                HyperLog.i(TAG, "setButtonsUI(M)  13" + new Gson().toJson(mParam2));
        } catch (Exception exception) {
            HyperLog.i(TAG, "setButtonsUI(M)" + exception.getMessage());
        }

        String status = model.getStatus_no();
        // for setting the selected status name on view
        textViewJobStatus.setText(model.getStatus_name());

        HyperLog.i(TAG, "setButtonsUI(M) 11 " + model.getStatus_name());
        // by default visibility should be gone
        buttonAccept.setVisibility(View.VISIBLE);
        buttonDecline.setVisibility(View.VISIBLE);
        if (TextUtils.isEmpty(status))
            HyperLog.i(TAG, "status is null");

        // for setting UI for all the custom status
        // The buttons will get hide and the drop down list of spinner will contain all the status available
        if (Integer.parseInt(model.getStatus_no()) > 19) {
            buttonAccept.setVisibility(View.GONE);
            buttonDecline.setVisibility(View.GONE);
            showHideRescheduleRevisit(AppConstant.CUSTOM);
        } else {
            //set clickable
            new_status_spinner.setEnabled(true);
        }
        switch (status) {
            case AppConstant.New_On_Hold:
                buttonAccept.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.job_resume));
                buttonDecline.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.job_finish));
                showHideRescheduleRevisit(AppConstant.New_On_Hold);
                break;
            case AppConstant.Not_Started:
                buttonAccept.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.accept));
                buttonDecline.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.reject));
               /* if (App_preference.getSharedprefInstance().getLoginRes().getIsShowRejectStatus() != null
                        && App_preference.getSharedprefInstance().getLoginRes().getIsShowRejectStatus().equals("0")) {
                    buttonDecline.setVisibility(View.VISIBLE);
                } else {
                    buttonDecline.setVisibility(View.GONE);
                }*/
                if (!reject)
                {
                    buttonDecline.setVisibility(View.GONE);
                }
                if (!accept)
                {
                    if (!travel){
                        buttonDecline.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.job_start));
                        buttonDecline.setVisibility(View.VISIBLE);
                        buttonAccept.setVisibility(View.GONE);
                        buttonDecline.setBackgroundColor(Color.parseColor("#00848d"));
                        jobstatus = new JobStatusModelNew(AppConstant.Break, jobDetail_pi.getStatusName(AppConstant.Break), jobDetail_pi.getImg());
                        showHideRescheduleRevisit(AppConstant.Not_Started);
                    }else {
                        buttonAccept.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.travel_start));
                        buttonAccept.setVisibility(View.VISIBLE);
                        buttonDecline.setVisibility(View.GONE);
                        jobstatus = new JobStatusModelNew(AppConstant.Accepted, jobDetail_pi.getStatusName(AppConstant.Accepted), jobDetail_pi.getImg());
                        showHideRescheduleRevisit(AppConstant.Not_Started);
                    }
                }
                if (!accept&&!reject){
                    if (!travel){
                        buttonDecline.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.job_start));
                        buttonDecline.setVisibility(View.VISIBLE);
                        buttonDecline.setBackgroundColor(Color.parseColor("#00848d"));
                        jobstatus = new JobStatusModelNew(AppConstant.Break, jobDetail_pi.getStatusName(AppConstant.Break), jobDetail_pi.getImg());
                        showHideRescheduleRevisit(AppConstant.Not_Started);
                    }else {
                        buttonAccept.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.travel_start));
                        buttonAccept.setVisibility(View.VISIBLE);
                        jobstatus = new JobStatusModelNew(AppConstant.Accepted, jobDetail_pi.getStatusName(AppConstant.Accepted), jobDetail_pi.getImg());
                        showHideRescheduleRevisit(AppConstant.Not_Started);
                    }
                }else
                    showHideRescheduleRevisit(AppConstant.Not_Started);
                break;
            case AppConstant.Accepted:
                //hide travel start button when admin permission deny
                if (App_preference.getSharedprefInstance().getLoginRes() != null &&
                        App_preference.getSharedprefInstance().getLoginRes().getIsHideTravelBtn().equals("1")) {
                    buttonAccept.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.job_start));
                } else {
                    if (!travel){
                        buttonAccept.setVisibility(View.VISIBLE);
                        buttonAccept.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.job_start));
                        jobstatus = new JobStatusModelNew(AppConstant.Break, jobDetail_pi.getStatusName(AppConstant.Break), jobDetail_pi.getImg());
                    }else {
                        buttonAccept.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.travel_start));
                    }
                }
                buttonDecline.setVisibility(View.GONE);
                showHideRescheduleRevisit(AppConstant.Accepted);
                break;
            case AppConstant.Reject:
                showHideRescheduleRevisit(AppConstant.Reject);
                break;
            case AppConstant.Travelling:
                buttonAccept.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.break_key));
                buttonDecline.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.status_tr_fin_st));
                if (!brack)
                {
                    buttonAccept.setVisibility(View.GONE);
                }
                showHideRescheduleRevisit(AppConstant.Travelling);
                break;
            case AppConstant.Break:
                buttonAccept.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.resume));
                buttonDecline.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.status_tr_fin_st));
                showHideRescheduleRevisit(AppConstant.Break);
                break;
            case AppConstant.In_Progress:
                buttonAccept.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.new_on_hold));
                buttonDecline.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.job_finish));
                buttonDecline.setBackgroundColor(Color.parseColor("#e6c352"));
                if (!onhold)
                {
                    buttonAccept.setVisibility(View.GONE);
                }
                showHideRescheduleRevisit(AppConstant.In_Progress);
                break;
            case AppConstant.Pending:
                buttonAccept.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.job_resume));
                buttonDecline.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.job_finish));
                showHideRescheduleRevisit(AppConstant.Pending);
                break;
            case AppConstant.Completed:
                //TODO For checking the travelling time
              /*  jobstatus.setStatus_no(AppConstant.Accepted);
                if (App_preference.getSharedprefInstance().getLoginRes() != null &&
                        App_preference.getSharedprefInstance().getLoginRes().getIsHideTravelBtn().equals("1")) {
                    buttonAccept.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.job_start));
                } else {
                    buttonAccept.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.travel_start));
                }
                buttonDecline.setVisibility(View.GONE);*/

                // TODO changed from hiding the full view to buttons
                buttonAccept.setVisibility(View.GONE);
                buttonDecline.setVisibility(View.GONE);
                arraystatus.clear();
                arraystatusvalue.clear();
                showHideRescheduleRevisit(AppConstant.Completed);
                break;
            case AppConstant.Cancel:
                buttonAccept.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.cancel));
                buttonDecline.setVisibility(View.GONE);
                showHideRescheduleRevisit(AppConstant.Cancel);
                break;
        }
        HyperLog.i(TAG, "setButtonsUI(M) completed.");
    }

    /***** Add the status saved in local from api call of getJobStatusList ******/

    private void addJobStatusInList(String status) {
        List<JobStatusModelNew> jobStatusList = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobStatusModelNew().getAllStatusList();
/*
        arraystatusvalue is for saving the status number in key value pair for managing the linking between status number and status value
        arraystatus is for saving the status number and status name in key value pair
*/
        /* ****Remove Rejected status when permission not allow for rejected status******/
        /*if (!status.equals(AppConstant.Completed)) {
            for (JobStatusModelNew jobStatus : jobStatusList) {
                switch (jobStatus.getStatus_no()) {
                    case "3":
                        if (App_preference.getSharedprefInstance().getLoginRes().getIsShowRejectStatus() != null
                                && App_preference.getSharedprefInstance().getLoginRes().getIsShowRejectStatus().equals("0")) {
                            arraystatusvalue.put(jobStatus.getKey(), jobStatus.getKey());
                            arraystatus.put(jobStatus.getKey(), jobStatus.getStatus_name());
                        }
                        break;
                    case "4":
                    case "7":
                    case "8":
                    case "9":
                    case "12":
                        arraystatusvalue.put(jobStatus.getKey(), jobStatus.getKey());
                        arraystatus.put(jobStatus.getKey(), jobStatus.getStatus_name());
                        break;
                }
                // when user selected custom status
                if (status.equalsIgnoreCase(AppConstant.CUSTOM)
                ) {
                    if ("2".equals(jobStatus.getStatus_no())) {
                        arraystatusvalue.put(jobStatus.getKey(), jobStatus.getKey());
                        arraystatus.put(jobStatus.getKey(), jobStatus.getStatus_name());
                    }
                }

                if (jobStatus.getIsDefault().equalsIgnoreCase("0")) {
                    arraystatusvalue.put(jobStatus.getKey(), jobStatus.getKey());
                    arraystatus.put(jobStatus.getKey(), jobStatus.getStatus_name());
                }
            }
        } else {*/
        for (JobStatusModelNew jobStatus : jobStatusList) {
            switch (jobStatus.getStatus_no())
            {
                case "1":
                    if (jobStatus.getIsStatusShow().equals("1") && jobStatus.getIsFwSelect().equals("1")) {
                        arraystatusvalue.put(jobStatus.getKey(), jobStatus.getKey());
                        arraystatus.put(jobStatus.getKey(), jobStatus.getStatus_name());
                    }
                    break;
                case "2":
                    if (jobStatus.getIsStatusShow().equals("1") && jobStatus.getIsFwSelect().equals("1")) {
                        arraystatusvalue.put(jobStatus.getKey(), jobStatus.getKey());
                        arraystatus.put(jobStatus.getKey(), jobStatus.getStatus_name());
                    }
                    break;
                case "3":
                    if (jobStatus.getIsStatusShow().equals("1") && jobStatus.getIsFwSelect().equals("1")) {
                        arraystatusvalue.put(jobStatus.getKey(), jobStatus.getKey());
                        arraystatus.put(jobStatus.getKey(), jobStatus.getStatus_name());
                    }
                    break;
                case "4":
                    if (jobStatus.getIsStatusShow().equals("1") && jobStatus.getIsFwSelect().equals("1")) {
                        arraystatusvalue.put(jobStatus.getKey(), jobStatus.getKey());
                        arraystatus.put(jobStatus.getKey(), jobStatus.getStatus_name());
                    }
                    break;
                case "5":
                    if (jobStatus.getIsStatusShow().equals("1") && jobStatus.getIsFwSelect().equals("1")) {
                        arraystatusvalue.put(jobStatus.getKey(), jobStatus.getKey());
                        arraystatus.put(jobStatus.getKey(), jobStatus.getStatus_name());
                    }
                    break;
                case "6":
                    if (jobStatus.getIsStatusShow().equals("1") && jobStatus.getIsFwSelect().equals("1")) {
                        arraystatusvalue.put(jobStatus.getKey(), jobStatus.getKey());
                        arraystatus.put(jobStatus.getKey(), jobStatus.getStatus_name());
                    }
                    break;
                case "7":
                    if (jobStatus.getIsStatusShow().equals("1") && jobStatus.getIsFwSelect().equals("1")) {
                        arraystatusvalue.put(jobStatus.getKey(), jobStatus.getKey());
                        arraystatus.put(jobStatus.getKey(), jobStatus.getStatus_name());
                    }
                    break;
                case "8":
                    if (jobStatus.getIsStatusShow().equals("1") && jobStatus.getIsFwSelect().equals("1")) {
                        arraystatusvalue.put(jobStatus.getKey(), jobStatus.getKey());
                        arraystatus.put(jobStatus.getKey(), jobStatus.getStatus_name());
                    }
                    break;
                case "9":
                    if (jobStatus.getIsStatusShow().equals("1") && jobStatus.getIsFwSelect().equals("1")) {
                        arraystatusvalue.put(jobStatus.getKey(), jobStatus.getKey());
                        arraystatus.put(jobStatus.getKey(), jobStatus.getStatus_name());
                    }
                    break;
                case "10":
                    if (jobStatus.getIsStatusShow().equals("1") && jobStatus.getIsFwSelect().equals("1")) {
                        arraystatusvalue.put(jobStatus.getKey(), jobStatus.getKey());
                        arraystatus.put(jobStatus.getKey(), jobStatus.getStatus_name());
                    }
                    break;
                case "12":
                    if (jobStatus.getIsStatusShow().equals("1") && jobStatus.getIsFwSelect().equals("1")) {
                        arraystatusvalue.put(jobStatus.getKey(), jobStatus.getKey());
                        arraystatus.put(jobStatus.getKey(), jobStatus.getStatus_name());
                    }
                    break;
            }

            if (status.equalsIgnoreCase(AppConstant.CUSTOM)) {
                if ("2".equals(jobStatus.getStatus_no())) {
                    if (jobStatus.getIsStatusShow().equals("1") && jobStatus.getIsFwSelect().equals("1")) {
                        arraystatusvalue.put(jobStatus.getKey(), jobStatus.getKey());
                        arraystatus.put(jobStatus.getKey(), jobStatus.getStatus_name());
                    }
                }
            }
            if (jobStatus.getIsDefault().equalsIgnoreCase("0")) {
                if (jobStatus.getIsStatusShow().equals("1") && jobStatus.getIsFwSelect().equals("1")) {
                    arraystatusvalue.put(jobStatus.getKey(), jobStatus.getKey());
                    arraystatus.put(jobStatus.getKey(), jobStatus.getStatus_name());
                }
            }
        }
        notifiMyAdpterForStatusDp();
    }

    /***** method to notify the spinner adapter of status ******/
    private void notifiMyAdpterForStatusDp() {
       // arrow_dp_icon.setVisibility(arraystatus.size() > 0 ? View.VISIBLE : View.GONE);
        int i = 0;
        statusArray = new String[arraystatus.size()];
        for (Map.Entry mapElement : arraystatus.entrySet()) {
            statusArray[i] = ((String) mapElement.getValue());
            i++;
        }
        int j = 0;
        statusArrayForIds = new String[arraystatusvalue.size()];
        for (Map.Entry mapElement : arraystatusvalue.entrySet()) {
            statusArrayForIds[j] = ((String) mapElement.getValue());
            j++;
        }
        if (mySpinnerAdapter != null) {
            mySpinnerAdapter.updtaeList(statusArray,arraystatus);
        }
    }



    @Override
    public void setResultForChangeInJob(String update, String jobid) {
        Intent intent = new Intent();
        intent.putExtra("JobID", jobid);
        getActivity().setResult(JobList.UPDATE, intent);
    }

    //Reset current job status
    @Override
    public void resetstatus(String status_no) {
        jobstatus.setStatus_name("");
        jobstatus.setStatus_no(status_no);
    }

    @SuppressLint("SetTextI18n")
    public void setDataToView() {

        EotEditor mEditor = layout.findViewById(R.id.editor);
        mEditor.setPlaceholder("Job Description");
        mEditor.setEditorFontSize(13);
        mEditor.setEditorFontColor(Color.GRAY);
        mEditor.setBackgroundColor(Color.TRANSPARENT);
        mEditor.setInputEnabled(false);
        if (!TextUtils.isEmpty(mParam2.getDes()))
            mEditor.setHtml(mParam2.getDes());

        if (TextUtils.isEmpty(mParam2.getInst()))
            layout.findViewById(R.id.cv_instruction).setVisibility(View.GONE);
        else
            layout.findViewById(R.id.cv_instruction).setVisibility(View.VISIBLE);


        if (TextUtils.isEmpty(mParam2.getDes()))
            layout.findViewById(R.id.cv_des).setVisibility(View.GONE);
        else
            layout.findViewById(R.id.cv_des).setVisibility(View.VISIBLE);


        getUpdatedLocation();
        setFwList();
        try {
            StringBuffer StrJt = new StringBuffer();
            List<JtId> jtaray = mParam2.getJtId();
            Iterator<JtId> it = jtaray.iterator();
            if (it.hasNext()) {
                StrJt.append(it.next().getTitle());
            }
            while (it.hasNext()) {
                StrJt.append(", ").append(it.next().getTitle());
            }
            StringBuilder sb = new StringBuilder();
            for (TagData item : mParam2.getTagData()) {
                if (sb.length() > 0) {
                    sb.append(", ");
                }
                sb.append("#" + item.getTnm());
            }
            textViewTags.setText(sb.toString());
            /* ***removed by shivani as per discussion it should be visible****/

            /*try {
                if (textViewTags != null && textViewTags.getText().toString().equals(""))
                    tagcardView.setVisibility(View.GONE);
                else tagcardView.setVisibility(View.VISIBLE);
            } catch (Exception e) {
                e.printStackTrace();
            }
*/
            SpannableStringBuilder builder = new SpannableStringBuilder();
            SpannableString str1 = new SpannableString(
                    LanguageController.getInstance().getMobileMsgByKey(AppConstant.job_code) + " : ");
            builder.append(str1);

            if (mParam2.getLabel() != null) {
                SpannableString str2 = new SpannableString(mParam2.getLabel());
                str2.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD),
                        0, str2.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                builder.append(str2);
            }

            textViewJobCode.setText(builder, TextView.BufferType.SPANNABLE);

            SpannableStringBuilder builder1 = new SpannableStringBuilder();
            SpannableString str2 = new SpannableString(
                    LanguageController.getInstance().getMobileMsgByKey(AppConstant.order_ref_no) + " : ");
            builder1.append(str2);

            if (mParam2.getPono() != null && !mParam2.getPono().isEmpty()) {
                textViewPONumber.setVisibility(View.VISIBLE);
                ll_po_number.setVisibility(View.VISIBLE);
                SpannableString str3 = new SpannableString(mParam2.getPono());
                str3.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD),
                        0, str3.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                builder1.append(str3);
                textViewPONumber.setText(builder1, TextView.BufferType.SPANNABLE);
            } else {
                textViewPONumber.setVisibility(View.GONE);

            }

            String endtime = "", startDatTime = "";
            if (mParam2.getSchdlStart() != null && !mParam2.getSchdlStart().equals("")) {
                try {
                    String timeFormate = AppUtility.dateTimeByAmPmFormate(AppConstant.DATE_FORMAT+" hh:mm a", AppConstant.DATE_FORMAT+" HH:mm");
                    startDatTime = AppUtility.getDateWithFormate2((Long.parseLong(mParam2.
                                    getSchdlStart()) * 1000),
                            timeFormate);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (mParam2.getSchdlFinish() != null && !mParam2.getSchdlFinish().equals("")) {
                try {
                    String timeFormate = AppUtility.dateTimeByAmPmFormate(AppConstant.DATE_FORMAT+" hh:mm a", AppConstant.DATE_FORMAT+" HH:mm");
                    endtime = /*"\n" + "End  : " + (*/AppUtility.getDateWithFormate2((Long.parseLong(mParam2.
                                    getSchdlFinish()) * 1000),
                            timeFormate);//+" - "
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

                String splitStartDate [] = AppUtility.getDateByLang(startDatTime,true).split(" ");
                String splitEndtDate [] = AppUtility.getDateByLang(endtime,true).split(" ");
                String setStartDate= "",setEndDate = "";
                if(splitStartDate.length == 3){
                    setStartDate = splitStartDate[0]+", "+splitStartDate[1]+" "+splitStartDate[2];
                    setEndDate = splitEndtDate[0]+", "+splitEndtDate[1]+" "+splitEndtDate[2];
                }else{
                    setStartDate = splitStartDate[0]+", "+splitStartDate[1];
                    setEndDate = splitEndtDate[0]+", "+splitEndtDate[1];
                }

            textViewTime.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.start)+": " +setStartDate +"\n" +
                    LanguageController.getInstance().getMobileMsgByKey(AppConstant.end)+": " + setEndDate);


            if (mParam2.getPrty().equals("1"))
                textViewPriority.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.Low));
            else if (mParam2.getPrty().equals("2")) {
                textViewPriority.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.medium));
            } else
                textViewPriority.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.High));

            if (mParam2.getInst() != null)
                textViewInstruction.setText(mParam2.getInst());


            if (mParam2.getDes() != null) {
                Spannable spannableHtmlWithImageGetter = PicassoImageGetter.getSpannableHtmlWithImageGetter(textViewDescription,
                        mParam2.getDes());

                PicassoImageGetter.setClickListenerOnHtmlImageGetter(spannableHtmlWithImageGetter, imageUrl -> {
                    if (!TextUtils.isEmpty(imageUrl)) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(imageUrl)));
                    }
                });
                textViewDescription.setText(spannableHtmlWithImageGetter);
                textViewDescription.setMovementMethod(LinkMovementMethod.getInstance());
            }


            ArrayList<String> titleList = new ArrayList<>();
            if (mParam2.getJtId() != null) {
                for (JtId tempModel : mParam2.getJtId()) {
                    titleList.add(tempModel.getTitle());
                }
            }

            setUploadSignature();

            if (mParam2.getJtId() != null) {
                for (JtId jtildModel : mParam2.getJtId())
                    addJobServicesInChips(jtildModel);
            }
            notifiMyAdpterForStatusDp();
            showQuotesData();

        } catch (Exception e) {
            HyperLog.i("", "setDataToView(M) End  " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void showQuotesData() {
        // quotes_details_card
        try {
            if (App_preference.getSharedprefInstance().getLoginRes().getCompPermission().get(0).getIsQuoteNoShowOnJob() != null
                    && App_preference.getSharedprefInstance().getLoginRes().getCompPermission().get(0).getIsQuoteNoShowOnJob().equals("0")
                    && mParam2 != null && mParam2.getQuotLabel() != null && !mParam2.getQuotLabel().isEmpty()) {
                quotes_details_card.setVisibility(View.VISIBLE);
                quotes_details_number.setText(mParam2.getQuotLabel());
            } else {
                quotes_details_card.setVisibility(View.GONE);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private void setUploadSignature() {
        try {
            if (mParam2 != null && mParam2.getJobId() != null && mParam2.getTempId() != null && mParam2.getJobId().equals(mParam2.getTempId())) {
                cardView_signature_pad.setVisibility(View.GONE);

            } else {
                CompPermission compPermission = App_preference.getSharedprefInstance().getLoginRes().getCompPermission().get(0);
                if (compPermission != null && !compPermission.getIsJcSignEnable().equals("1")) {
                    cardView_signature_pad.setVisibility(View.VISIBLE);
                    if (mParam2.getSignature() != null && !mParam2.getSignature().equals("")) {
                        btn_add_signature.setVisibility(View.GONE);
                        signature_img.setVisibility(View.VISIBLE);
                        customer_name.setVisibility(View.VISIBLE);
                        customer_name.setText(mParam2.getCustomerName());
                        Picasso.get().load(App_preference.getSharedprefInstance().getBaseURL() +
                                mParam2.getSignature()).placeholder(R.drawable.ic_profile).error(R.drawable.ic_profile)
                                .into(signature_img);
                    } else {
                        btn_add_signature.setVisibility(View.VISIBLE);
                        signature_img.setVisibility(View.GONE);
                        customer_name.setVisibility(View.GONE);
                    }
                } else {
                    cardView_signature_pad.setVisibility(View.GONE);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private void setFwList() {
        try {
            List<FieldWorker> list =  new ArrayList<>();
          //  StringBuffer stringBuffer = new StringBuffer();
            String kpr = mParam2.getKpr();
            String[] kprList = kpr.split(",");
            /*list.addAll(Arrays.asList(kprList));
            Filedworker_List_Adapter filedworkerListAdapter = new Filedworker_List_Adapter(mParam2,list,getActivity().getApplicationContext());
            rv_fw_list.setAdapter(filedworkerListAdapter);*/

            for (String id : kprList) {
                FieldWorker fieldWorker = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).fieldWorkerModel().getFieldWorkerByID(id);
                if (fieldWorker != null) {
                    list.add(fieldWorker);

                   /* stringBuffer.append(fieldWorker.getName())
                            .append(fieldWorker.getLnm())
                            .append(", ");*/
                }
            }
            Filedworker_List_Adapter filedworkerListAdapter = new Filedworker_List_Adapter(mParam2,list,getActivity().getApplicationContext());
            rv_fw_list.setAdapter(filedworkerListAdapter);
           /* stringBuffer.deleteCharAt(stringBuffer.length() - 2);
            String paramIds = stringBuffer.toString();
            txt_fw_nm_list.setText(paramIds);*/
        } catch (Exception e) {
            e.printStackTrace();
          /*  txt_fw_nm_list.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.not_available));*/
        }
    }

    private void getDialog() {
        try {
            if (enterFieldDialog != null)
                if (enterFieldDialog.isShowing())
                    enterFieldDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (enterFieldDialog != null)
                enterFieldDialog = null;
            enterFieldDialog = new Dialog(getActivity());
            enterFieldDialog.setCancelable(false);
            enterFieldDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            enterFieldDialog.setContentView(R.layout.popup_details);

            Window window = enterFieldDialog.getWindow();
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            WindowManager.LayoutParams wlp = window.getAttributes();

            wlp.gravity = Gravity.CENTER;
            wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            window.setAttributes(wlp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getDialogCall() {
        try {
            if (enterFieldDialog != null)
                if (enterFieldDialog.isShowing())
                    enterFieldDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (enterFieldDialog != null)
                enterFieldDialog = null;
            enterFieldDialog = new Dialog(getActivity());
            enterFieldDialog.setCancelable(false);
            enterFieldDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            enterFieldDialog.setContentView(R.layout.popup_call);

            Window window = enterFieldDialog.getWindow();
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            WindowManager.LayoutParams wlp = window.getAttributes();

            wlp.gravity = Gravity.CENTER;
            wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            window.setAttributes(wlp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getDialogEmail() {
        try {
            if (enterFieldDialog != null)
                if (enterFieldDialog.isShowing())
                    enterFieldDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (enterFieldDialog != null)
                enterFieldDialog = null;
            enterFieldDialog = new Dialog(getActivity());
            enterFieldDialog.setCancelable(false);
            enterFieldDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            enterFieldDialog.setContentView(R.layout.popup_emai_layout);

            Window window = enterFieldDialog.getWindow();
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            WindowManager.LayoutParams wlp = window.getAttributes();

            wlp.gravity = Gravity.CENTER;
            wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            window.setAttributes(wlp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getDialogDes() {
        try {
            if (enterFieldDialog != null)
                if (enterFieldDialog.isShowing())
                    enterFieldDialog.dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (enterFieldDialog != null)
                enterFieldDialog = null;
            enterFieldDialog = new Dialog(getActivity());
            enterFieldDialog.setCancelable(false);
            enterFieldDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            enterFieldDialog.setContentView(R.layout.popup_descriprion);

            TextView txtViewHeader = enterFieldDialog.findViewById(R.id.txtViewHeader);
            txtViewHeader.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.description));

            Window window = enterFieldDialog.getWindow();
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            WindowManager.LayoutParams wlp = window.getAttributes();

            wlp.gravity = Gravity.CENTER;
            wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            window.setAttributes(wlp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("", "");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("", "");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("", "");
        mMapView.onPause();
        recyclerView_requested_item.setVisibility(View.GONE);
        AppUtility.progressBarDissMiss();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("OnResume", "onResumeCalled");
        check = System.currentTimeMillis();
        mMapView.onResume();
        // for updating item count
        updateCountItem();
        if(param3 != null && !param3.isEmpty()) {
            String itemRequested = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().getItemRequested(param3);
            if (itemRequested != null && itemRequested.equals("1")) {
                requested_item_flag.setVisibility(View.VISIBLE);
                requested_item_txt.setClickable(true);
//                requested_itemList_show_hide_rl.setVisibility(View.VISIBLE);
                requested_item_txt.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.down), null, null, null);
            }else {
                requested_item_txt.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
                requested_item_flag.setVisibility(View.GONE);
                requested_item_txt.setClickable(false);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.e("", "");
    }


    public void showErrorDialog(String msg) {
        AppUtility.error_Alert_Dialog(getActivity(), msg, LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok)
                , () -> null);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.quotes_details_card:
                reDirectOnQuotesDetails();
                break;
            case R.id.linear_arraw_layout:
                new_status_spinner.performClick();
                break;
            case R.id.btnStopRecurView:
                stopRecurpattern(recurData);
                break;
            case R.id.btn_add_signature:
                ((JobDetailActivity) requireActivity()).openCustomSignatureDialog(jobstatus.getStatus_no());
                break;
            case R.id.customfiled_btn:
                Intent intent1 = new Intent(getActivity(), CustomFiledListActivity.class);
                intent1.putExtra("jobId", mParam2.getJobId());
                intent1.putExtra("ansedit", SAVEANS);
                intent1.putExtra("AUDITCUSTOMFIELD", false);
                intent1.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                intent1.putParcelableArrayListExtra("list", questionList);
                startActivityForResult(intent1, CUSTOMFILED);
                break;
            case R.id.btnComplationView:
                showComplationViewDialog(false);
                break;

            case R.id.btn_add_eq:
                addEquipmentClicked();
                break;
            case R.id.btn_add_item:
                Intent intent = new Intent(getActivity(), JoBInvoiceItemList2Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.putExtra("JobId", mParam2.getJobId());
                startActivity(intent);
                break;
            case R.id.buttonAccept:
                setButtonsAction(jobstatus.getStatus_no(), 1);
                break;
            case R.id.buttonDecline:
                // trigger confirmation popup in case of can cel or rejection of job
                if (jobstatus == null || jobstatus.getStatus_no() == null) {
                    mParam2 = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().getJobsById(param3);
                    jobstatus = new JobStatusModelNew(mParam2.getStatus(), jobDetail_pi.getStatusName(mParam2.getStatus()));
                }
                //After discussion with Ayush sir, Add new condition for check signature of customer (27/Sep/2023)

                if(App_preference.getSharedprefInstance().getLoginRes().getIsJobCompCustSignEnable().equals("1")) {
                    if(buttonDecline.getText().equals(LanguageController.getInstance().getMobileMsgByKey(AppConstant.job_finish))) {
                        if (mParam2.getSignature() == null || mParam2.getSignature().equals("")) {
                            showErrorDialog(LanguageController.getInstance().getMobileMsgByKey(AppConstant.signature_alert));
                        } else {
                            setButtonsAction(jobstatus.getStatus_no(), 2);
                        }
                    }else {
                        setButtonsAction(jobstatus.getStatus_no(), 2);
                    }
                }else {
                    setButtonsAction(jobstatus.getStatus_no(), 2);
                }
                break;
            case R.id.textViewAddress:
                if (mParam2 != null) {
                    //sample url for source to destination redirection: http://maps.google.com/maps?saddr=20.344,34.34&daddr=20.5666,45.345"
                    String locationdata = "";
                    if (!TextUtils.isEmpty(mParam2.getLat()) && !mParam2.getLat().equals("0.0") && !mParam2.getLng().equals("0.0")) {
                        locationdata = "http://maps.google.com/maps?daddr=" + mParam2.getLat() + "," + mParam2.getLng();
                    } else {
                        String completeAddress = mParam2.getAdr() + " " + mParam2.getCity()
                                + " " + SpinnerCountrySite.getStatenameById(mParam2.getCtry(), mParam2.getState()
                                + " " + SpinnerCountrySite.getCountryNameById(mParam2.getCtry())
                        );
                        locationdata = "http://maps.google.com/maps?daddr=" + completeAddress;
                    }
                    try {
                        Intent intent3 = new Intent(Intent.ACTION_VIEW, Uri.parse(locationdata));
                        startActivity(intent3);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                } else {
                    AppUtility.alertDialog(getActivity(), LanguageController.getInstance().getMobileMsgByKey(AppConstant.dialog_alert), LanguageController.getInstance().getMobileMsgByKey(AppConstant.no_location), LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok), "", () -> true);
                }
                break;
            case R.id.buttonMapView:
                if (mParam2 != null) {

                    //sample url for source to destination redirection: http://maps.google.com/maps?saddr=20.344,34.34&daddr=20.5666,45.345"
                    String locationdata = "";
                    if (!TextUtils.isEmpty(mParam2.getLat()) && !mParam2.getLat().equals("0") && !mParam2.getLng().equals("0")) {
                        locationdata = "http://maps.google.com/maps?daddr=" + mParam2.getLat() + "," + mParam2.getLng();
                    } else {
                        String completeAddress = mParam2.getAdr() + " " + mParam2.getCity() + " " + SpinnerCountrySite.getCountryNameById(mParam2.getCtry());
                        //    String searchableAddress = completeAddress.replace(" ", "+");
                        locationdata = "http://maps.google.com/maps?daddr=" + completeAddress;
                    }
                    try {
                        Intent intent2 = new Intent(Intent.ACTION_VIEW,
                                Uri.parse(locationdata));
                        //   intent.setPackage("com.google.android.apps.maps");
                        startActivity(intent2);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                } else {
                    AppUtility.alertDialog(getActivity(), LanguageController.getInstance().getMobileMsgByKey(AppConstant.dialog_alert), LanguageController.getInstance().getMobileMsgByKey(AppConstant.no_location), LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok), "", () -> true);
                }
                break;
            case R.id.imageViewEmail:
                if (mParam2 != null) {
                    if (!mParam2.getEmail().equals("")) {
                        // frameView.setAlpha(0.3F);
                        getDialogEmail();
                        TextView txtViewSkypeCon1 = enterFieldDialog.findViewById(R.id.txt_email_popup);
                        final SpannableString s1 = new SpannableString(mParam2.getEmail().trim());
                        Linkify.addLinks(s1, Linkify.WEB_URLS | Linkify.EMAIL_ADDRESSES);
                        txtViewSkypeCon1.setTextIsSelectable(true);
                        txtViewSkypeCon1.setMovementMethod(LinkMovementMethod.getInstance());
                        if (TextUtils.isEmpty(s1))
                            txtViewSkypeCon1.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.job_det_email));
                        else
                            txtViewSkypeCon1.setText(s1);
                        enterFieldDialog.show();

                        TextView okBtn = enterFieldDialog.findViewById(R.id.btnClose);
                        okBtn.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok));

                        okBtn.setOnClickListener(v -> enterFieldDialog.dismiss());

                    } else {
                        AppUtility.alertDialog(getActivity(), LanguageController.getInstance().getMobileMsgByKey(AppConstant.dialog_alert), LanguageController.getInstance().getMobileMsgByKey(AppConstant.job_det_email), LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok), "", new Callable<Boolean>() {
                            @Override
                            public Boolean call() {
                                return true;
                            }
                        });
                    }
                }
                break;
            case R.id.buttonView:
                if (!mParam2.getDes().equals("")) {
                    //frameView.setAlpha(0.3F);
                    getDialogDes();
                    try {
                        TextView txtViewDeails = enterFieldDialog.findViewById(R.id.txtViewDeails);
                        txtViewDeails.setVisibility(View.VISIBLE);
                        txtViewDeails.setText(textViewDescription.getText());

                        TextView txtViewHeader = enterFieldDialog.findViewById(R.id.txtViewHeader);
                        txtViewHeader.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.description));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    TextView okBtn = enterFieldDialog.findViewById(R.id.btnClose);
                    okBtn.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok));
                    okBtn.setOnClickListener(v -> {
                        //frameView.setAlpha(1.0F);
                        enterFieldDialog.dismiss();
                    });
                    enterFieldDialog.show();
                } else {
                    AppUtility.alertDialog(getActivity(), LanguageController.getInstance().getMobileMsgByKey(AppConstant.dialog_alert), LanguageController.getInstance().getMobileMsgByKey(AppConstant.desc_no), LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok), "", new Callable<Boolean>() {
                        @Override
                        public Boolean call() {
                            return true;
                        }
                    });
                }
                break;
            case R.id.buttonView1:
                if (!mParam2.getInst().equals("")) {
                    getDialogDes();
                    try {
                        TextView txtViewDeails = enterFieldDialog.findViewById(R.id.txtViewDeails);
                        txtViewDeails.setVisibility(View.VISIBLE);
                        txtViewDeails.setText(mParam2.getInst());

                        TextView txtViewHeader = enterFieldDialog.findViewById(R.id.txtViewHeader);
                        txtViewHeader.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.instr));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    TextView okBtn = enterFieldDialog.findViewById(R.id.btnClose);
                    okBtn.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok));
                    okBtn.setOnClickListener(v -> {
                        //frameView.setAlpha(1.0F);
                        enterFieldDialog.dismiss();
                    });
                    enterFieldDialog.show();
                } else {
                    AppUtility.alertDialog(getActivity(), LanguageController.getInstance().getMobileMsgByKey(AppConstant.dialog_alert), LanguageController.getInstance().getMobileMsgByKey(AppConstant.no_inst), LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok), "", () -> true);
                }
                break;
            case R.id.imageViewChat:
                if (mParam2 != null) {
                    if (mParam2.getSkype() != null || mParam2.getTwitter() != null) {
                        if (!mParam2.getSkype().equals("") || !mParam2.getTwitter().equals("")) {
                            // frameView.setAlpha(0.3F);
                            getDialog();
                            TextView txtViewSkypeCon1 = enterFieldDialog.findViewById(R.id.txtViewSkypeCon1);
                            txtViewSkypeCon1.setVisibility(View.VISIBLE);
                            txtViewSkypeCon1.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.no_chat));
                            if (mParam2.getSkype() != null) {
                                txtViewSkypeCon1.setText(mParam2.getSkype());
                            }
                            txtViewSkypeCon1.setOnClickListener(view12 -> openApp(requireActivity(), "com.skype.raider", LanguageController.getInstance().getMobileMsgByKey(AppConstant.install_the_skype_app)));

                            TextView txtViewSkypeCon2 = enterFieldDialog.findViewById(R.id.txtViewSkypeCon2);
                            txtViewSkypeCon2.setVisibility(View.VISIBLE);

                            if (mParam2.getTwitter() != null) {
                                if (TextUtils.isEmpty(mParam2.getTwitter())) {
                                    txtViewSkypeCon2.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.no_chat));
                                } else {
                                    txtViewSkypeCon2.setText(mParam2.getTwitter());
                                }

                                txtViewSkypeCon2.setOnClickListener(view1 -> openApp(requireActivity(), "com.twitter.android", LanguageController.getInstance().getMobileMsgByKey(AppConstant.install_the_twitter_app)));
                            }

                            TextView okBtn = enterFieldDialog.findViewById(R.id.btnClose);
                            okBtn.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok));
                            okBtn.setOnClickListener(v -> {
                                //frameView.setAlpha(1.0F);
                                enterFieldDialog.dismiss();
                            });
                            enterFieldDialog.show();
                        } else {
                            AppUtility.alertDialog(getActivity(), LanguageController.getInstance().getMobileMsgByKey(AppConstant.dialog_alert), LanguageController.getInstance().getMobileMsgByKey(AppConstant.no_avail), LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok), "", () -> true);
                        }
                    } else {
                        AppUtility.alertDialog(getActivity(), LanguageController.getInstance().getMobileMsgByKey(AppConstant.dialog_alert), LanguageController.getInstance().getMobileMsgByKey(AppConstant.no_avail), LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok), "", () -> true);
                    }
                }
                break;
            case R.id.imageViewCall:
                if (mParam2 != null) {
                    if (mParam2.getMob1() != null && !mParam2.getMob1().equals("") ||
                            mParam2.getMob2() != null && !mParam2.getMob2().equals("")) {
                        //frameView.setAlpha(0.3F);
                        getDialogCall();
                        try {
                            final TextView txtViewSkypeCon1 = enterFieldDialog.findViewById(R.id.txtViewSkypeCon1);
                            txtViewSkypeCon1.setVisibility(View.VISIBLE);
                            final SpannableString s1 = new SpannableString(mParam2.getMob1().trim());
                            Linkify.addLinks(txtViewSkypeCon1, Linkify.ALL);
                            if (TextUtils.isEmpty(s1))
                                txtViewSkypeCon1.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.no_contact_available));
                            else
                                txtViewSkypeCon1.setText(s1);

                            txtViewSkypeCon1.setOnClickListener(v -> {
                                if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.CALL_PHONE)
                                        != PackageManager.PERMISSION_GRANTED) {

                                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_CALL_PHONE);
                                } else if (AppUtility.isvalidPhoneNo(txtViewSkypeCon1.getText().toString())) {
                                    AppUtility.getCallOnNumber(getActivity(), txtViewSkypeCon1.getText().toString());

                                }
                            });

                            final TextView txtViewSkypeCon2 = enterFieldDialog.findViewById(R.id.txtViewSkypeCon2);
                            txtViewSkypeCon2.setVisibility(View.VISIBLE);
                            Linkify.addLinks(txtViewSkypeCon2, Linkify.ALL);
                            final SpannableString s = new SpannableString(mParam2.getMob2().trim());
                            if (TextUtils.isEmpty(s))
                                txtViewSkypeCon2.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.no_contact_available));
                            else
                                txtViewSkypeCon2.setText(s);
                            txtViewSkypeCon2.setOnClickListener(v -> {
                                if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.CALL_PHONE)
                                        != PackageManager.PERMISSION_GRANTED) {

                                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_CALL_PHONE);
                                } else if (AppUtility.isvalidPhoneNo(txtViewSkypeCon2.getText().toString())) {
                                    AppUtility.getCallOnNumber(getActivity(), txtViewSkypeCon2.getText().toString());

                                }
                            });

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        TextView okBtn = enterFieldDialog.findViewById(R.id.btnClose);
                        okBtn.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok));
                        okBtn.setOnClickListener(v -> {
                            // frameView.setAlpha(1.0F);
                            enterFieldDialog.dismiss();
                        });
                        enterFieldDialog.show();
                    } else {
                        AppUtility.alertDialog(getActivity(), LanguageController.getInstance().getMobileMsgByKey(AppConstant.dialog_alert), LanguageController.getInstance().getMobileMsgByKey(AppConstant.no_contact_available), LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok), "", () -> true);
                    }
                }
                break;

            case R.id.btnActualEdit:

                dialogActualTravelDateTime = new DialogActualTravelDateTime();

                dialogActualTravelDateTime.setDataRequired(jobDetail_pi, 1, mParam2.getJobId(),
                        lastStatus, lastStatusTime, completionDetails);
                dialogActualTravelDateTime.show(getParentFragmentManager(), "dialog");
                break;
            case R.id.btnTravelEdit:
                dialogActualTravelDateTime = new DialogActualTravelDateTime();
                dialogActualTravelDateTime.setDataRequired(jobDetail_pi, 2, mParam2.getJobId(),
                        lastStatus, lastStatusTime, completionDetails);
                dialogActualTravelDateTime.show(getParentFragmentManager(), "dialog");
                break;
            case R.id.rl_Collapse1:
            case R.id.ivEditAc:
                if (isClickedActual) {
                    ivEditAc.setImageDrawable(ContextCompat.getDrawable(requireActivity(), R.drawable.ic_arrow_drop_down_black_24dp));
                    isClickedActual = false;
                    ll_actual_date_time.setVisibility(View.GONE);
                    ll_travel_date_time.setVisibility(View.GONE);
                    rl_Collapse2.setVisibility(View.GONE);
                } else {
                    ivEditAc.setImageDrawable(ContextCompat.getDrawable(requireActivity(), R.drawable.ic_baseline_arrow_drop_up_24));
                    ll_actual_date_time.setVisibility(View.VISIBLE);
                    ll_travel_date_time.setVisibility(View.VISIBLE);
                    rl_Collapse2.setVisibility(View.VISIBLE);
                    isClickedActual = true;
                    if (jobDetail_pi != null) {
                        progressBar_timing.setVisibility(View.VISIBLE);
                        jobDetail_pi.getJobCompletionDetails(mParam2.getJobId());
                    }
                }
                break;
            case R.id.liner_layout_for_recurmsg_show:
                if(AppUtility.isInternetConnected()){
                    if (jobDetail_pi != null) {
                        progressBar_cyclic.setVisibility(View.VISIBLE);
                        /*AppUtility.progressBarShow(getActivity());*/
                        if(mParam2.getParentId().equalsIgnoreCase("0")) {
                            jobDetail_pi.getRecureDataList(mParam2.getJobId(), mParam2.getRecurType());
                        }else {
                            jobDetail_pi.getRecureDataList(mParam2.getParentId(), mParam2.getParentRecurType());
                        }
                    }
                }else {
                    showErrorDialog(LanguageController.getInstance().getMobileMsgByKey(AppConstant.offline_feature_alert));
                }
                 break;
            case R.id.liner_layout_for_recurmsg_hide:
                recurMsgHide.setVisibility(View.GONE);
                recurMsgShow.setVisibility(View.VISIBLE);
                liner_layout_for_recurmsg.setVisibility(View.GONE);
                break;

            case R.id.btn_add_requested_item:
//                show_requested_list.setVisibility(View.VISIBLE);
                recyclerView_requested_item.setVisibility(View.GONE);
//                hide_requested_list.setVisibility(View.GONE);
                txt_no_item_found.setVisibility(View.GONE);
                Intent intent2 = new Intent(getActivity(), AddUpdateRquestedItemActivity.class);
                intent2.putExtra("addReqItem",true);
                intent2.putExtra("jobId",mParam2.getJobId());
                intent2.putExtra("jobLabel",mParam2.getLabel());
                startActivity(intent2);
                break;

            case R.id.requested_item_txt:
                if(!isClickedReqItem) {
                    if(AppUtility.isInternetConnected()){
                        isClickedReqItem = true;
                        progressBar_itemRequest.setVisibility(View.VISIBLE);
                        if (jobDetail_pi != null) {
                            jobDetail_pi.getRequestedItemDataList(mParam2.getJobId());
                        }
                    }else {
                        showErrorDialog(LanguageController.getInstance().getMobileMsgByKey(AppConstant.offline_feature_alert));
                    }
                }else {
                    isClickedReqItem = false;
                    recyclerView_requested_item.setVisibility(View.GONE);
                    txt_no_item_found.setVisibility(View.GONE);
                    requested_item_txt.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.down), null, null, null);
                }
               /* if(AppUtility.isInternetConnected()){
                    progressBar_itemRequest.setVisibility(View.VISIBLE);
//                    show_requested_list.setVisibility(View.GONE);
//                    hide_requested_list.setVisibility(View.VISIBLE);
                    if (jobDetail_pi != null) {
                        jobDetail_pi.getRequestedItemDataList(mParam2.getJobId());
                        recyclerView_requested_item.setVisibility(View.VISIBLE);
                    }
                }else {
                    showErrorDialog(LanguageController.getInstance().getMobileMsgByKey(AppConstant.offline_feature_alert));
                }*/


                break;
          /*  case R.id.hide_requested_list:
//                show_requested_list.setVisibility(View.VISIBLE);
//                recyclerView_requested_item.setVisibility(View.GONE);
//                hide_requested_list.setVisibility(View.GONE);
                txt_no_item_found.setVisibility(View.GONE);
                break;*/
        }
    }


    private void addEquipmentClicked() {
        Intent intenteq = new Intent(getActivity(), JobEquipmentActivity.class);
        intenteq.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intenteq.putExtra("JobId", mParam2.getJobId());
        intenteq.putExtra("cltId", mParam2.getCltId());
        intenteq.putExtra("contrId", mParam2.getContrId());
        intenteq.putExtra("appId", "");
        intenteq.putExtra("siteid",mParam2.getSiteId());
        if (mParam2.getEquArray() != null && mParam2.getEquArray().size() > 0)
            intenteq.putExtra("auditid", mParam2.getEquArray().get(0).getAudId());
        else intenteq.putExtra("auditid", "0");
        startActivity(intenteq);
    }

    private void reDirectOnQuotesDetails() {
        if (mParam2 != null && mParam2.getQuotId() != null) {
            Intent quotesinvoiceIntent = new Intent(getActivity(), Quote_Invoice_Details_Activity.class);
            quotesinvoiceIntent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            quotesinvoiceIntent.putExtra("quotId", mParam2.getQuotId());
            startActivity(quotesinvoiceIntent);
        }
    }


    private void stopRecurpattern(RecurReqResModel recurData) {
        if (AppUtility.isInternetConnected()) {
            String msg = "";
            if(recurData != null) {
                if (recurData.getJobRecurModel().getRecurStatus().equalsIgnoreCase("0") || recurData.getJobRecurModel().getRecurStatus().equalsIgnoreCase("3")) {
                    msg = LanguageController.getInstance().getMobileMsgByKey(AppConstant.pause_recur_msg);
                } else if (recurData.getJobRecurModel().getRecurStatus().equalsIgnoreCase("2")) {
                    msg = LanguageController.getInstance().getMobileMsgByKey(AppConstant.resume_recur_msg);
                }
            }
            AppUtility.alertDialog2(getActivity(),
                    "",
                    msg,
                    LanguageController.getInstance().getMobileMsgByKey(AppConstant.yes),
                    LanguageController.getInstance().getMobileMsgByKey(AppConstant.no), new Callback_AlertDialog() {
                        @Override
                        public void onPossitiveCall() {
                            jobDetail_pi.pauseResumeRecurr(mParam2, recurData.getJobRecurModel().getRecurStatus());
                        }

                        @Override
                        public void onNegativeCall() {

                        }
                    });
        } else {
            showErrorDialog(LanguageController.getInstance().getMobileMsgByKey(AppConstant.offline_feature_alert));
        }
    }


    private void showComplationViewDialog(boolean showAttachment) {
        String jobdata = new Gson().toJson(mParam2);
        Intent intent = new Intent(getActivity(), JobCompletionActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.putExtra("Complation", jobdata);
        intent.putExtra("showAttachment",showAttachment);
        startActivityForResult(intent, REQUEST_COMPLETION_NOTE);
    }


    private boolean openApp(Context context, String packageName, String msg) {
        PackageManager manager = context.getPackageManager();
        try {
            Intent i = manager.getLaunchIntentForPackage(packageName);
            if (i == null) {
                showAppInstallDialog(msg);
                return false;
            }
            i.addCategory(Intent.CATEGORY_LAUNCHER);
            context.startActivity(i);
            return true;
        } catch (ActivityNotFoundException e) {
            return false;
        }
    }

    void showAppInstallDialog(String msg) {
        EotApp.getAppinstance().showToastmsg(msg);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 145) {
            if (grantResults != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (locationTracker != null) {
                    locationTracker.getCurrentLocation();
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    private void getUpdatedLocation() {
        locationTracker = new LocationTracker(getActivity(), (isLocationUpdated, isPermissionAllowed) -> {
            if (isPermissionAllowed) {
                locationTracker.getCurrentLocation();
            } else {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION},
                        145);
            }
        });
    }

    /***** method to check the current status and set the next action to perform ******/

    private void setButtonsAction(String status, int btn_id) {
        try {
            HyperLog.i("", "setButtonsAction(M) Start");
            if (App_preference.getSharedprefInstance().getLoginRes() != null &&
                    App_preference.getSharedprefInstance().getLoginRes().getIsHideTravelBtn().equals("1") && status.equals(AppConstant.Accepted)) {
                //When Permission denny for fw travling start not show travel start status name on button
                //   jobstatus = JobStatus_Controller.getInstance().getStatusByButtonAction(AppConstant.Pending, 1);
                jobstatus = JobStatus_Controller.getInstance().getStatusByButtonAction(AppConstant.New_On_Hold, 1);
            } else {
                jobstatus = JobStatus_Controller.getInstance().getStatusByButtonAction(status, btn_id);
            }

            if (jobstatus.getStatus_no().equals(AppConstant.Cancel) ||
                    jobstatus.getStatus_no().equals(AppConstant.Reject)) {
                showDialogForRejectionConfirmation();
            } else {
                showTriggerForStatusChange();
            }

            HyperLog.i("", "setButtonsAction(M) Stop");
        }catch (Exception e){
            Log.e("Error Detail Fragment", "setButtonAction"+e.getMessage());
        }
    }

    /***** check the current status and update the next status accordingly ******/

    private void showTriggerForStatusChange() {
        if (!buttonAccept.getText().toString().toLowerCase().contains("resume")) {
            HyperLog.i("", "Resume states case");
            //  ((JobDetailActivity) getActivity()).openFormForEvent(jobstatus.getStatus_no());
            try {
                HyperLog.i("", "Resume states found");
                if(jobstatus.getKey().equals(AppConstant.Completed)) {
                        for (Map.Entry mapElement : arraystatus.entrySet()) {
                            if (mapElement.getKey().equals(AppConstant.Completed)) {
                                    checkForIsLeader(mapElement.getKey().toString());
                                  break;
                            }
                    }
                }else {
                    ((JobDetailActivity) requireActivity()).openFormForEvent(jobstatus.getStatus_no(),"0","");
                }
            } catch (Exception e) {
                e.printStackTrace();
                HyperLog.i("", "Resume states Exception handle" + e.getMessage());
                try {
                    if (jobstatus == null) {
                        HyperLog.i("", "Resume Job states not found in json " + e.getMessage());
                        if (mParam2 == null && param3 != null && !param3.equals("")) {
                            HyperLog.i("", "Job Data Not found");
                            HyperLog.i("", "Job Id" + param3);
                            mParam2 = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().getJobsById(param3);
                            jobstatus = new JobStatusModelNew(mParam2.getStatus(), jobDetail_pi.getStatusName(mParam2.getStatus()));
                        } else if (mParam2.getStatus() != null && !mParam2.getStatus().equals("")) {
                            HyperLog.i("", "Find Job status from Json file in Job status resume condition..");
                            jobstatus = new JobStatusModelNew(mParam2.getStatus(), jobDetail_pi.getStatusName(mParam2.getStatus()));
                        } else {
                            mParam2 = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().getJobsById(param3);
                            jobstatus = new JobStatusModelNew(mParam2.getStatus(), jobDetail_pi.getStatusName(mParam2.getStatus()));
                        }
                    } else {
                        HyperLog.i("", "Job states not null");
                        mParam2 = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().getJobsById(param3);
                        jobstatus = new JobStatusModelNew(mParam2.getStatus(), jobDetail_pi.getStatusName(mParam2.getStatus()));
                    }
                    ((JobDetailActivity) requireActivity()).openFormForEvent(jobstatus.getStatus_no(),"0","");
                } catch (Exception exception) {
                    exception.printStackTrace();
                    HyperLog.i("", "Exception" + exception.getMessage());
                    mParam2 = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().getJobsById(param3);
                    jobstatus = new JobStatusModelNew(mParam2.getStatus(), jobDetail_pi.getStatusName(mParam2.getStatus()));
                    ((JobDetailActivity) requireActivity()).openFormForEvent(jobstatus.getStatus_no(),"0","");
                }
            }
        } else {
            onFormSuccess("","");
        }
    }

    //start date,time must be grater than to end date time
    private boolean conditionCheck(String schdlStart, String schdlFinish) {
        Locale.getDefault().getDisplayLanguage();
        try {
            SimpleDateFormat gettingfmt = new SimpleDateFormat(
                    //"dd-MM-yyyy hh:mm a"
                    AppUtility.dateTimeByAmPmFormate(AppConstant.DATE_FORMAT+" hh:mm a", AppConstant.DATE_FORMAT+" HH:mm")
                    , Locale.US);
            Date date = gettingfmt.parse(schdlStart);
            date.getTime();

            Date date1 = gettingfmt.parse(schdlFinish);
            date1.getTime();
            if (date1.getTime() > date.getTime()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /***** Method call for custom form success ******/
    @Override
    public void onFormSuccess(String statusCompleteFor, String jobType) {
        String dateTime = AppUtility.getDateByFormat(AppConstant.DATE_FORMAT+" hh:mm a");
        HyperLog.i(TAG, "onFormSuccess(M) Start");
         if (jobstatus != null) {
             if (jobDetail_pi.checkContactHideOrNot()) {
                 contact_card.setVisibility(View.GONE);
             }else {
                 if (!jobstatus.getStatus_no().equals(AppConstant.Not_Started)) {
                     contact_card.setVisibility(View.VISIBLE);
                 } else {
                     if (App_preference.getSharedprefInstance().getLoginRes().getIsHideContactOnDispatch().equals("1")
                             && jobstatus.getStatus_no().equals(AppConstant.Not_Started)) {
                         contact_card.setVisibility(View.GONE);
                     } else {
                         contact_card.setVisibility(View.VISIBLE);
                     }
                 }
             }
            /* **** check permission for  custom form ******/

            if (App_preference.getSharedprefInstance().getLoginRes().getConfirmationTrigger() != null) {
                if (jobstatus != null && jobstatus.getStatus_no() != null && App_preference.getSharedprefInstance().getLoginRes().getConfirmationTrigger().contains(jobstatus.getStatus_no())) {
                    showDialogForSendMailToClt(statusCompleteFor,jobType);
                } else {
                    isMailSentToClt = "1";
                    updateStatusApiCall(statusCompleteFor,jobType);
                }
            } else {
                isMailSentToClt = "1";
                updateStatusApiCall(statusCompleteFor,jobType);
            }
            /*** Set Actual time and Travel time*/
            if(jobstatus.getId().equals("5")){
                date_tr_start.setText("Start : " + AppUtility.getDateByLang(dateTime,true));
                date_tr_start.setEnabled(true);
            }else if (jobstatus.getId().equals("7")){
                date_ac_start.setText("Start : " +  AppUtility.getDateByLang(dateTime,true));
                date_ac_start.setEnabled(true);
                date_tr_end.setText("End : " +  AppUtility.getDateByLang(dateTime,true));
                date_tr_end.setEnabled(true);
            }else if (jobstatus.getId().equals("9")){
                date_ac_end.setText("End : " +  AppUtility.getDateByLang(dateTime,true));
                date_ac_end.setEnabled(true);
            }
        }
         /**drop up actual and travel time***/
        ivEditAc.setImageDrawable(ContextCompat.getDrawable(requireActivity(), R.drawable.ic_arrow_drop_down_black_24dp));
        isClickedActual = false;
        ll_actual_date_time.setVisibility(View.GONE);
        ll_travel_date_time.setVisibility(View.GONE);
        rl_Collapse2.setVisibility(View.GONE);
        HyperLog.i(TAG, "onFormSuccess(M) Stop");
    }

    /***** This is synchronized because the status should get updated one by one* *****/
    synchronized private void updateStatusApiCall(String statusCompleteFor,String jobType) {
        if (LatLngSycn_Controller.getInstance().getLat() != null
                && !LatLngSycn_Controller.getInstance().getLat().isEmpty()
                && LatLngSycn_Controller.getInstance().getLng() != null
                && !LatLngSycn_Controller.getInstance().getLng().isEmpty()
        ) {
            HyperLog.e("Location  enable", "Location providing data success");
            jobDetail_pi.changeJobStatusAlertInvisible(mParam2.getJobId(), mParam2.getType(), jobstatus, LatLngSycn_Controller.getInstance().getLat(),
                    LatLngSycn_Controller.getInstance().getLng(), isMailSentToClt,statusCompleteFor,mParam2.getLabel(),jobType);
        } else {
            HyperLog.e("Location not enable", "Location not providing data");
            jobDetail_pi.changeJobStatusAlertInvisible(mParam2.getJobId(), mParam2.getType(), jobstatus, "0.0", "0.0", isMailSentToClt,
                    statusCompleteFor,mParam2.getLabel(),jobType);
        }

        new Handler().postDelayed(() -> {
            //#Eye09693 - {Android} Status entry issue from mobile side
        }, 100);
    }

    /***** popup for asking the user for send mail confirmation
     * This works on permission basis from admin
     * *****/
    private void showDialogForSendMailToClt(String statusCompleteFor, String jobType) {
        // added by shivani for resolving the double tap issue
        final Dialog dialog = new Dialog(getActivity());
        TextView dialog_msg, dialog_yes, dialog_no;
        dialog.setContentView(R.layout.send_email_dialog);
        dialog_msg = dialog.findViewById(R.id.dia_msg);
        dialog_yes = dialog.findViewById(R.id.dia_yes);
        dialog_no = dialog.findViewById(R.id.dia_no);

        dialog_msg.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.send_client_mail),
                TextView.BufferType.SPANNABLE);
        dialog_yes.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.yes));
        dialog_no.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.no));
        dialog.setCancelable(false);
        dialog_yes.setOnClickListener(view -> {
            dialog_yes.setEnabled(false);
            dialog_yes.setClickable(false);
            dialog_no.setEnabled(false);
            dialog_no.setClickable(false);
            dialog.dismiss();
            isMailSentToClt = "1";
            updateStatusApiCall(statusCompleteFor,jobType);
        });
        dialog_no.setOnClickListener(view -> {
            dialog_yes.setEnabled(false);
            dialog_yes.setClickable(false);
            dialog_no.setEnabled(false);
            dialog_no.setClickable(false);
            dialog.dismiss();
            isMailSentToClt = "0";
            updateStatusApiCall(statusCompleteFor,jobType);
        });
        dialog.show();
    }

    /***** popup for asking the user for rejection confirmation *****/

    private void showDialogForRejectionConfirmation() {
        AppUtility.alertDialog2(getActivity(),
                "",
                LanguageController.getInstance().getMobileMsgByKey(AppConstant.are_you_sure_reject),
                LanguageController.getInstance().getMobileMsgByKey(AppConstant.yes),
                LanguageController.getInstance().getMobileMsgByKey(AppConstant.no), new Callback_AlertDialog() {
                    @Override
                    public void onPossitiveCall() {
                        showTriggerForStatusChange();
                    }

                    @Override
                    public void onNegativeCall() {
                        // set job status to null to reassign it the older value
                        mParam2 = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().getJobsById(param3);
                        jobstatus = new JobStatusModelNew(mParam2.getStatus(), jobDetail_pi.getStatusName(mParam2.getStatus()));
                    }
                });
    }


    /*****method call when the custom form goes on error *****/

    @Override
    public void onFormError() {
        jobDetail_pi.setJobCurrentStatus(mParam2.getJobId());
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_COMPLETION_NOTE) {
            try {
                cl_pbCompletion.setVisibility(View.GONE);
                isAskForCompleteJob = true;
                if (data != null && data.hasExtra("note")) {
                    if (mParam2 != null)
                        mParam2.setComplNote(data.getStringExtra("note"));
                    String tempstring=data.getStringExtra("note").replace("null", "");
                    tempstring.replace("<br>","");
                    complation_notes.setText(tempstring);
//                    btnComplationView.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.edit));
                }
//                if(!mParam2.getComplNote().isEmpty()){
//                    btnComplationView.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.edit));
//                }else {
//                    btnComplationView.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.add));
//                }
//                if(AppUtility.isInternetConnected()) {
//                    jobDetail_pi.loadFromServer();
//                }else {
//                    setOfflineData();
//                }
                addComplationButtonTxt();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == REQUEST_RESCHEDULE) {
            try {
                if (data != null && data.hasExtra("stime")) {
                    if (mParam2 != null) {
                        mParam2.setSchdlStart(data.getStringExtra("stime"));
                        mParam2.setSchdlFinish(data.getStringExtra("etime"));
                        /*String[] formated_date = AppUtility.getFormatedTime(mParam2.getSchdlStart());
                        textViewTime.setText(formated_date[1] + formated_date[2]);*/

                        // change for after reschedule job time and date show proper
                        String timeFormate = AppUtility.dateTimeByAmPmFormate(AppConstant.DATE_FORMAT+" hh:mm a", AppConstant.DATE_FORMAT+" HH:mm");
                        String startDatTime = AppUtility.getDateWithFormate2((Long.parseLong(mParam2.
                                        getSchdlStart()) * 1000),
                                timeFormate);
                        String endtime =AppUtility.getDateWithFormate2((Long.parseLong(mParam2.
                                        getSchdlFinish()) * 1000),
                                timeFormate);//+" - "
                        textViewTime.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.start)+": " +AppUtility.getDateByLang(startDatTime,true) +"\n" +
                                LanguageController.getInstance().getMobileMsgByKey(AppConstant.end)+": " + AppUtility.getDateByLang( endtime,true));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == CUSTOMFILED) {
            getUpdateForm();
        }
    }

    public void setCompletionNotes(String data) {
        if (data != null) {
            if (mParam2 != null)
                mParam2.setComplNote(data);
            if (complation_notes != null && btnComplationView != null) {
                String tempstring=data.replace("null","");
                tempstring.replace("<br>","");
                complation_notes.setText(tempstring);
                btnComplationView.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.edit));
            }
        }
    }

    /*
     * inflate custom view accrording question type
     */

    /*****Custom field's Data List*****/
    @Override
    public void setCustomFiledList(ArrayList<CustOmFormQuestionsRes> questionList) {

        try {
            if (questionList.size() > 0) {
                this.questionList = questionList;
                customField_view.setVisibility(View.VISIBLE);
                ll_custom_views.removeAllViews();
                for (CustOmFormQuestionsRes res : questionList) {
                    View customView = LayoutInflater.from(getActivity()).inflate(R.layout.item_custom_field_job_overview, null, false);
                    TextView textView = customView.findViewById(R.id.custom_filed_form);
                    AppCompatCheckBox checkBox = customView.findViewById(R.id.checkbox);
                    textView.setText(res.getDes() + " : ");

                    List<AnswerModel> ans = res.getAns();
                    if (ans != null && ans.size() > 0) {
                        for (AnswerModel model : ans) {
                            if (!TextUtils.isEmpty(model.getValue())) {
                                switch (res.getType()) {
                                    case "5"://date type
                                        try {
                                            if (!(model.getValue()).equals("")) {
                                                String[] dateConvert = AppUtility.getFormatedTime(model.getValue());
                                                String s = dateConvert[0];
                                                String[] date = s.split(",");
                                                textView.append(AppUtility.getDateByLang(date[1].trim().replace(" ", "-"),false));
                                                //  holder.tvDate.setText(date[1].trim().replace(" ", "-"));
                                            }
                                        } catch (Exception ex) {
                                            ex.printStackTrace();
                                        }
                                        break;
                                    case "6"://Time type
                                        try {
                                            if (!(model.equals(""))) {
                                                String time = AppUtility.getDateWithFormate2((Long.parseLong(model.
                                                                getValue()) * 1000),
                                                        AppUtility.dateTimeByAmPmFormate("hh:mm a", "HH:mm"));
                                                textView.append(time);
                                            }
                                        } catch (Exception ex) {
                                            ex.printStackTrace();
                                        }
                                        break;
                                    case "7": //Date Time Type
                                        try {
                                            if (!model.getValue().equals("")) {
                                                Long dateLong = Long.parseLong(model.getValue());
                                                String dateConvert = AppUtility.getDate(dateLong, AppUtility.dateTimeByAmPmFormate(AppConstant.DATE_FORMAT+" hh:mm a", AppConstant.DATE_FORMAT+" HH:mm"));
                                                textView.append(AppUtility.getDateByLang(dateConvert,true));
                                            }
                                        } catch (NumberFormatException e) {
                                            e.printStackTrace();
                                        }
                                        break;

                                    //single checkbox
                                    case "8":
                                        checkBox.setVisibility(View.VISIBLE);
                                        if (!TextUtils.isEmpty(model.getValue())) {
                                            checkBox.setChecked(model.getValue().equals("1"));
                                        } else checkBox.setChecked(false);
                                        textView.setText(res.getDes());
                                        break;

                                    default:
                                        textView.append(model.getValue() + " ");
                                }
                            }
                        }
                    } else if (res.getType().equals("8")) {
                        textView.setText(res.getDes());
                        checkBox.setVisibility(View.VISIBLE);
                    }
                    ll_custom_views.addView(customView);
                }
                addCustomFieldText();
            } else {
                customField_view.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void addCustomFieldText() {
        boolean btnText = false;
        try {
            for (CustOmFormQuestionsRes model : questionList) {
                if (model.getAns().size() > 0 && !model.getAns().get(0).getValue().equals("")) {
                    btnText = true;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if(App_preference.getSharedprefInstance().getLoginRes().getRights().get(0).getIsEditCustomFormVisible()==0) {

            if (!btnText) {
                customfiled_btn.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.add));
                SAVEANS = false;
            } else {
                customfiled_btn.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.edit));
                SAVEANS = true;
            }
        }
        else {
            customfiled_btn.setVisibility(View.GONE);
        }
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void sessionExpire(String msg) {
        AppUtility.alertDialog(getActivity(), LanguageController.getInstance().getMobileMsgByKey(AppConstant.dialog_error_title), msg, LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok), "", () -> {
            EotApp.getAppinstance().sessionExpired();
            return null;
        });
    }

    @Override
    public void setEuqipmentList(List<EquArrayModel> equArray) {
        Collections.sort(equArray, (o1, o2) -> o1.getEqunm().compareTo(o2.getEqunm()));
        if (equArray != null) {
            adapter.setList(equArray);
            adapter.setEquipmentCurrentStatus(App_preference.getSharedprefInstance().getEquipmentStatusList());
        }
    }

    @Override
    public Drawable getDrawable(String source) {
        LevelListDrawable d = new LevelListDrawable();
        Drawable empty = getResources().getDrawable(R.drawable.ic_launcher);
        d.addLevel(0, 0, empty);
        d.setBounds(0, 0, empty.getIntrinsicWidth(), empty.getIntrinsicHeight());
        new LoadImage().execute(source, d);
        return d;
    }

    @Override
    public void OnEquipmentClicked() {
        addEquipmentClicked();
    }

    @Override
    public void updateCountEquipment() {
        Log.e("OnCreateDetail", "Equipment Notify Called");
        mParam2 = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().getJobsById(mParam2.getJobId());
        if (mParam2.getEquArray() != null && mParam2.getEquArray().size() > 0) {
            // for updating data
            setEuqipmentList(mParam2.getEquArray());
        }
        // for updating items added from equipment parts
        if (mParam2.getItemData() != null && mParam2.getItemData().size() > 0) {
            // for updating data
            invoice_list_adpter.updateitemlist(mParam2.getItemData());
        }
    }

    @Override
    public void updateCountItem() {
        try {
            Log.e("OnCreateDetail", "Item Notify Called");
            if(mParam2 != null && mParam2.getJobId() != null) {
                mParam2 = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().getJobsById(mParam2.getJobId());
                // for updating items added from equipment parts
                Log.e("OnCreateDetail", "Item Notify Called" + new Gson().toJson(mParam2.getItemData()));
                if(mParam2 != null) {
                    if (mParam2.getItemData() != null && mParam2.getItemData().size() > 0) {
                        // for updating data
                        invoice_list_adpter.updateitemlist(mParam2.getItemData());
                    }
                    if (mParam2.getEquArray() != null && mParam2.getEquArray().size() > 0) {
                        // for updating data
                        setEuqipmentList(mParam2.getEquArray());
                        Log.e("mParam2::", new Gson().toJson(mParam2.getEquArray()));
                    }
                }
            }
        }catch (Exception e){
            Log.e("Detail Fragment Error","After on Resume "+ e.getMessage());
        }
    }

    @Override
    public void updateStatusEquipment() {
        if (adapter == null) {
            adapter.setEquipmentCurrentStatus(App_preference.getSharedprefInstance().getEquipmentStatusList());
        }
    }

    @Override
    public void upateForCompletion(String apiName, String jobId) {
        this.jobDetail_pi = new JobDetail_pc(this);

//            jobDetail_pi.getAttachFileList(jobId, App_preference.getSharedprefInstance().getLoginRes().getUsrId()
//                    , "6");
            jobDetail_pi.loadFromServer(jobId);
    }

    @Override
    public void updateReqItemList(String api_name, String message, AddUpdateRequestedModel requestedModel) {
        switch (api_name){
            case Service_apis.addItemRequest:
                showAppInstallDialog(LanguageController.getInstance().getServerMsgByKey(message.trim()));
                requested_item_flag.setVisibility(View.VISIBLE);
                requested_item_txt.setClickable(true);
//                requested_itemList_show_hide_rl.setVisibility(View.VISIBLE);
                requested_item_txt.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.down), null, null, null);
                if(requestedModel != null) {
                    String msg =
                            LanguageController.getInstance().getMobileMsgByKey(AppConstant.item_requested_by_the_field_user)+"\n"+LanguageController.getInstance().getMobileMsgByKey(AppConstant.item_name)+": "+requestedModel.getItemName()+"\n"+
                            LanguageController.getInstance().getMobileMsgByKey(AppConstant.qty)+": "+requestedModel.getQty()+"\n"+
                            LanguageController.getInstance().getMobileMsgByKey(AppConstant.part_no)+": "+requestedModel.getModelNo()+"\n"+
                            LanguageController.getInstance().getMobileMsgByKey(AppConstant.brand)+": "+requestedModel.getBrandName();
                    Chat_Send_Msg_Model chat_send_Msg_model = new Chat_Send_Msg_Model(
                            msg, "", AppUtility.getDateByMiliseconds(),
                            requestedModel.getJobLabel(),
                            requestedModel.getJobId(), "1");
                    AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().updateRequestedItem("1",requestedModel.getJobId());
                    if (jobDetail_pi != null) {
                        jobDetail_pi.sendMsg(chat_send_Msg_model);
                    }
                }
                break;
            case Service_apis.updateItemRequest:
                showAppInstallDialog(LanguageController.getInstance().getServerMsgByKey(message.trim()));
                if(requestedModel != null) {
                    String msg =
                            LanguageController.getInstance().getMobileMsgByKey(AppConstant.field_user_made_some_changes_on_the_requested_item)+"\n"+LanguageController.getInstance().getMobileMsgByKey(AppConstant.item_name)+": "+requestedModel.getItemName()+"\n"+
                                    LanguageController.getInstance().getMobileMsgByKey(AppConstant.qty)+": "+requestedModel.getQty()+"\n"+
                                    LanguageController.getInstance().getMobileMsgByKey(AppConstant.part_no)+": "+requestedModel.getModelNo()+"\n"+
                                    LanguageController.getInstance().getMobileMsgByKey(AppConstant.brand)+": "+requestedModel.getBrandName();
                    Chat_Send_Msg_Model chat_send_Msg_model = new Chat_Send_Msg_Model(
                            msg, "", AppUtility.getDateByMiliseconds(),
                            requestedModel.getJobLabel(),
                            requestedModel.getJobId(), "1");
                    if (jobDetail_pi != null) {
                        jobDetail_pi.sendMsg(chat_send_Msg_model);
                    }
                }
                break;
        }
    }

    @Override
    public void itemDelete(String irId,RequestedItemModel requestedModel) {
        if(AppUtility.isInternetConnected()){
            progressBar_itemRequest.setVisibility(View.VISIBLE);
            AddUpdateRequestedModel requestedModel1 = new AddUpdateRequestedModel(requestedModel.getInm(),requestedModel.getEbId(),requestedModel.getQty(),
                    requestedModel.getModelNo(),"",requestedModel.getItemId(),mParam2.getJobId());
            jobDetail_pi.deleteRequestedItem(irId,mParam2.getJobId(),requestedModel1);
        }
    }

    @Override
    public void itemSelected(RequestedItemModel updateRequestedItemModel) {
//        show_requested_list.setVisibility(View.VISIBLE);
        requested_item_txt.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.down), null, null, null);
        recyclerView_requested_item.setVisibility(View.GONE);
//        hide_requested_list.setVisibility(View.GONE);
        Intent intent = new Intent(getActivity(),AddUpdateRquestedItemActivity.class);
        intent.putExtra("updateSelectedReqItem",updateRequestedItemModel);
        intent.putExtra("UpdateReqItem",true);
        intent.putExtra("jobId",mParam2.getJobId());
        intent.putExtra("jobLabel",mParam2.getLabel());
        startActivity(intent);
    }


    /***  method for loading image set into description editor ***/
    @SuppressLint("StaticFieldLeak")
    class LoadImage extends AsyncTask<Object, Void, Bitmap> {
        private LevelListDrawable mDrawable;

        @Override
        protected Bitmap doInBackground(Object... params) {
            String source = (String) params[0];
            mDrawable = (LevelListDrawable) params[1];
            Log.d(TAG, "doInBackground " + source);
            try {
                InputStream is = new URL(source).openStream();
                return BitmapFactory.decodeStream(is);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            Log.d(TAG, "onPostExecute drawable " + mDrawable);
            Log.d(TAG, "onPostExecute bitmap " + bitmap);
            if (bitmap != null) {
                BitmapDrawable d = new BitmapDrawable(getResources(), bitmap);
                mDrawable.addLevel(1, 1, d);
                mDrawable.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
                mDrawable.setLevel(1);
                CharSequence t = textViewDescription.getText();
                textViewDescription.setText(t);
            }
        }
    }

//    public void setCompAttachmentList(List<Attachments> list){
//        attachmentsList =list;
//    }
//    public List<Attachments> getCompAttachmentList(){
//        return attachmentsList;
//    }
    public void setServiceMarkDoneList(Set<IsMarkDoneWithJtid> list){
        mParam2.setIsMarkDoneWithJtId(new ArrayList<>(list));
        ArrayList<IsMarkDoneWithJtid> convertList = new ArrayList<>();
        convertList.addAll(list);

        AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().updateServiceMarkDoneList(mParam2.getIsMarkDoneWithJtId(),mParam2.getJobId());
    }
//    public Set<IsMarkDoneWithJtid> getServicMarkDoneList(){
//        return isMarkDoneWithJtidsList;
//    }
    public boolean checkInList (String jtid){
        boolean isIn = false;
        for (IsMarkDoneWithJtid item: isMarkDoneWithJtidsList
             ) {
            if(item.getJtId().equals(jtid)){
                isIn = true;
                break;
            }
        }
        return isIn;
    }
    public boolean isAllServicesDone(){
        boolean isIt = true;
        for (IsMarkDoneWithJtid item: isMarkDoneWithJtidsList
        ) {
            if(item.getStatus().equals("0")){
                isIt = false;
                break;
            }
        }
        return isIt;
    }

    public void checkMarkServices(){
        if(isAllServicesDone()){
            int i =0;
//            for (String s: statusArray
//                 ) {
//                if(s.equalsIgnoreCase(arraystatus.get("9"))){
////                if(s.equalsIgnoreCase(LanguageController.getInstance().getMobileMsgByKey(AppConstant.completed))){
//                    break;
//                }
//                i++;
//            }
//
            for(Map.Entry item:arraystatus.entrySet()){
                if(item.getKey().equals(AppConstant.Completed)){
                    checkForIsLeader(item.getKey().toString());
                    break;
                }
            }

        }
    }
}
