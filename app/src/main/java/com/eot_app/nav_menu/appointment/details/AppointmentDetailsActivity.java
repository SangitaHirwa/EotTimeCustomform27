package com.eot_app.nav_menu.appointment.details;

import android.Manifest;
import android.animation.Animator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.eot_app.R;
import com.eot_app.UploadDocumentActivity;
import com.eot_app.databinding.ActivityAppointmentDetailsBinding;
import com.eot_app.nav_menu.appointment.Keepar;
import com.eot_app.nav_menu.appointment.addupdate.AddAppointmentActivity;
import com.eot_app.nav_menu.appointment.addupdate.model.AppointmentUpdateReq;
import com.eot_app.nav_menu.appointment.appointment_ItemData.AppointmentItemAdded_pi;
import com.eot_app.nav_menu.appointment.appointment_ItemData.AppointmentItemData_pc;
import com.eot_app.nav_menu.appointment.appointment_ItemData.AppointmentItemData_pi;
import com.eot_app.nav_menu.appointment.appointment_ItemData.UpdateItemDataList_pi;
import com.eot_app.nav_menu.appointment.appointment_model.AppintmentItemDataModel;
import com.eot_app.nav_menu.appointment.appointment_model.AppointmentItemDataInMap;
import com.eot_app.nav_menu.appointment.appointment_model.AppointmentStatusModel;
import com.eot_app.nav_menu.appointment.dbappointment.Appointment;
import com.eot_app.nav_menu.appointment.details.documents.ActivityDocumentUpload;
import com.eot_app.nav_menu.appointment.details.documents.DocumentExportReq;
import com.eot_app.nav_menu.appointment.details.documents.fileattach_mvp.Doc_Attch_Pc;
import com.eot_app.nav_menu.appointment.details.documents.fileattach_mvp.Doc_Attch_Pi;
import com.eot_app.nav_menu.appointment.details.documents.fileattach_mvp.Doc_Attch_View;
import com.eot_app.nav_menu.client.client_db.Client;
import com.eot_app.nav_menu.client.clientlist.client_detail.site.sitelist.editsite.editsitedb.SpinnerCountrySite;
import com.eot_app.nav_menu.jobs.add_job.Add_job_activity;
import com.eot_app.nav_menu.jobs.job_db.Job;
import com.eot_app.nav_menu.jobs.job_detail.JobDetailActivity;
import com.eot_app.nav_menu.jobs.job_detail.addinvoiveitem2pkg.AddEditInvoiceItemActivity2;
import com.eot_app.nav_menu.quote.add_quotes_pkg.AddQuotes_Activity;
import com.eot_app.nav_menu.quote.quote_invoice_pkg.Quote_Invoice_Details_Activity;
import com.eot_app.services.Service_apis;
import com.eot_app.utility.AppConstant;
import com.eot_app.utility.AppUtility;
import com.eot_app.utility.App_preference;
import com.eot_app.utility.EotApp;
import com.eot_app.utility.db.AppDataBase;
import com.eot_app.utility.db.OfflineDataController;
import com.eot_app.utility.language_support.LanguageController;
import com.eot_app.utility.util_interfaces.Callback_AlertDialog;
import com.eot_app.utility.util_interfaces.MyListItemSelected;
import com.eot_app.utility.util_interfaces.MySpinnerAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hypertrack.hyperlog.HyperLog;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static com.eot_app.nav_menu.jobs.job_detail.detail.DetailFragment.MY_PERMISSIONS_REQUEST_CALL_PHONE;

public class AppointmentDetailsActivity extends UploadDocumentActivity
        implements View.OnClickListener, AttachementAdapter.OnItemSelection , Doc_Attch_View, MyListItemSelected<AppintmentItemDataModel>, UpdateItemDataList_pi, RequirementGetheringListAdapter.DeleteItem {
    private static final int EDIT_APPOINTMENT_CODE = 10;
    private static final int UPLOADED_NEW_LIST = 148;
    private static final int UPDATE_ITEM = 398;
    private final int ADD_QUOTE_RESULT = 123;
    private String statusByValue=null;
    long check = System.currentTimeMillis();
    private final LinkedHashMap<String, String> arraystatus = new LinkedHashMap<>();
    private  String[] statusArray = new String[arraystatus.size()];
    List<AppointmentStatusModel> allAppointmentStatusList =new ArrayList<>();
    RequirementGetheringListAdapter reqGethListAdapter ;
    ArrayList<AppointmentAttachment> allAttachmentList=new ArrayList<>();
    List<AppintmentItemDataModel> itemList = new ArrayList<>();
    ActivityAppointmentDetailsBinding binding;
    AppointmentDetailsViewModel detailsViewModel;
    Appointment model = new Appointment();
    Doc_Attch_Pi doc_attch_pi;
    AppointmentItemData_pi appointmentItemData_pi;
    private final int GET_ITEM_LIST=5;
     AppintmentItemDataModel itemDataModel=new AppintmentItemDataModel();

    private final BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            if (model != null) {
                model = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).appointmentModel().getAppointmentById(model.getAppId());
                setJobDetails();
            }
            Log.d("appointment", "Appointment details receiver called");
        }
    };
    AttachementAdapter attachementAdapter;

    boolean isFBMenuOpened;
    //custom dialog for instruction and details
    private Dialog enterFieldDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailsViewModel = new ViewModelProvider(this).get(AppointmentDetailsViewModel.class);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_appointment_details);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(LanguageController.getInstance().getMobileMsgByKey(AppConstant.appointment_details));

        binding.tvLabelDes.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.description));
        binding.tvAddNew.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.appointment_add_new_attach));
        binding.tvLableScheduleDateTime.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.appointment_start_end));
        binding.tvViewOnMap.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.view_on_map));
        binding.tbLabelAttachment.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.document));
        //binding.tvFabAddQuote.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.create_quotation));
       // binding.tvFabJob.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.title_add_job));
        binding.checkboxSelectAll.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.select_all));
        binding.tvExportAll.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.export_document));
        binding.tvLabelQuotation.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.quotation_label));
        binding.tvRecentQuote.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.recent_quote));
       /* binding.btnAppointmentDone.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.mark_as_done));
        binding.btnAppointmentCompleted.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.completed));*/


        if (AppUtility.isInternetConnected()) {
            binding.progressBar.setVisibility(View.VISIBLE);
            binding.nolistTxt.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.appointment_attach_msg));
        } else {
            binding.progressBar.setVisibility(View.GONE);
            binding.nolistTxt.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.err_check_network));
            binding.nolistLinear.setVisibility(View.VISIBLE);
        }
        appointmentItemData_pi=new AppointmentItemData_pc(this) ;
        doc_attch_pi = new Doc_Attch_Pc(this);
        model = getIntent().getParcelableExtra("appointmentData");
        if (model != null) {
            setDataInUI(model);
        }
        for (AppointmentItemDataInMap itemData : model.getItemData()) {
            AppintmentItemDataModel itemData1 = itemData.getItemData();
             itemList.add(itemData1);
        }

        reqGethListAdapter=new RequirementGetheringListAdapter(itemList,this);;
//        reqGethListAdapter.setItemList(itemList);
        binding.recyclerOfReqGeth.setAdapter(reqGethListAdapter);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        binding.recyclerView.setNestedScrollingEnabled(false);

        attachementAdapter = new AttachementAdapter(this);
        attachementAdapter.setOnItemSelection(this);

        binding.recyclerView.setAdapter(attachementAdapter);


        detailsViewModel.fetchAppointmentDetails(model);


        detailsViewModel.getLiveAttachments().observe(this, appointmentAttachments -> {
            binding.progressBar.setVisibility(View.GONE);
            if (appointmentAttachments != null && appointmentAttachments.size() > 0) {
                binding.nolistLinear.setVisibility(View.GONE);
                allAttachmentList.addAll(appointmentAttachments);
                attachementAdapter.setList(appointmentAttachments);
            } else {
                binding.nolistLinear.setVisibility(View.VISIBLE);
            }
        });


        detailsViewModel.getIsUploading().observe(this, aBoolean -> {
            if (aBoolean)
                AppUtility.progressBarShow(AppointmentDetailsActivity.this);
            else AppUtility.progressBarDissMiss();
        });

        detailsViewModel.pdfPath.observe(this, s -> {
            if (s != null) {
                try {
                    Bundle bundle = new Bundle();
                    bundle.putString("appId", model.getAppId());
                    bundle.putString("pdfPath", s);
                    DialogEmailDocument emailDocument = new DialogEmailDocument();
                    emailDocument.setArguments(bundle);
                    emailDocument.show(getSupportFragmentManager(), "emaildialog");
                    //   startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(App_preference.getSharedprefInstance().getBaseURL() + "" + s)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        binding.checkboxSelectAll.setOnCheckedChangeListener(selectAllListener());
      //  binding.fab.setOnClickListener(this);
        binding.backgroundView.setOnClickListener(this);
        binding.linearFabQuote.setOnClickListener(this);
        binding.linearFabJob.setOnClickListener(this);
        binding.tvExportAll.setOnClickListener(this);
        binding.linearArrawLayout.setOnClickListener(this);
        binding.seemore.setOnClickListener(this);
        binding.seeless.setOnClickListener(this);
        binding.tvAddNewItem.setOnClickListener(this);


        binding.tvAddNewItem.setOnClickListener(view -> {
            Intent intent = new Intent(this, AddEditInvoiceItemActivity2.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            intent.putExtra("AddRequirmentGetheringItem",true);
            intent.putExtra("appId",model.getAppId());
            startActivityForResult(intent,GET_ITEM_LIST);

        });
        binding.swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                appointmentItemData_pi.getItemFromServer(model.getAppId(),reqGethListAdapter);
            }
        });

        /*binding.btnAppointmentDone.setOnClickListener(v -> {
            if (model != null && !model.getTempId().equals(model.getAppId())) {
                sendUpdateRequest();
                TransitionManager.beginDelayedTransition(binding.parentLayout);
                binding.btnAppointmentDone.setVisibility(View.GONE);
                binding.btnAppointmentCompleted.setVisibility(View.VISIBLE);
            } else
                showDialogs(LanguageController.getInstance().getMobileMsgByKey(AppConstant.appointment_not_sync));

        });*/

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("appointment_details_refresh"));
    }

    private CompoundButton.OnCheckedChangeListener selectAllListener() {
        return (buttonView, isChecked) -> {
            if (attachementAdapter != null)
                attachementAdapter.selectAllFiles(isChecked);
        };
    }

    private void setDataInUI(final Appointment model) {
        HyperLog.i("", "setDataInUI(M) start");
        if (model != null) {


            setClientName(model);
            setAppointmentStatusList();
            setQuotationDetails();
            setJobDetails();
            if(!TextUtils.isEmpty(model.getStatus()))
                binding.statusLabel.setText(arraystatus.get(model.getStatus()));
            if (!TextUtils.isEmpty(model.getNm()))
                binding.tvClientName.setText(model.getNm());
            setcompleteAddress(model);


            setScheduleDates(model);

            appoinmentAttchment(model);

            AppUtility.spinnerPopUpWindow(binding.statusSpinner);
            binding.statusSpinner.setSelected(false);
            binding.statusSpinner.setAdapter(new MySpinnerAdapter(AppointmentDetailsActivity.this, statusArray));
            binding.statusSpinner.post(() -> binding.statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                    if (System.currentTimeMillis() - check < 1000) {
                        return;
                    }
                    AppUtility.alertDialog2(AppointmentDetailsActivity.this, LanguageController.getInstance().getMobileMsgByKey(AppConstant.status_dialog), LanguageController.getInstance().getMobileMsgByKey(AppConstant.audit_status_change), LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok), LanguageController.getInstance().getMobileMsgByKey(AppConstant.cancel), new Callback_AlertDialog() {
                        @Override
                        public void onPossitiveCall() {
                            binding.statusLabel.setText(statusArray[position]);
                            for (Map.Entry mapElement : arraystatus.entrySet()) {
                                if (mapElement.getValue().equals(binding.statusLabel.getText().toString())) {
                                    statusByValue=String.valueOf(mapElement.getKey());
                                    break;
                                }
                            }
                            model.setStatus(statusByValue);
                            sendUpdateRequest();
                        }

                        @Override
                        public void onNegativeCall() {

                        }
                    });
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            }));


            binding.imgCall.setOnClickListener(this);
            binding.imgEmail.setOnClickListener(this);
            binding.tvViewOnMap.setOnClickListener(this);
            binding.tvAddNew.setOnClickListener(this);
            binding.editAppointment.setOnClickListener(this);

        }


        HyperLog.i("", "setDataInUI(M) completed");

    }

    private void appoinmentAttchment(Appointment model) {
        try {
            binding.editor.setPlaceholder(LanguageController.getInstance().getMobileMsgByKey(AppConstant.description));
            binding.editor.setTextColor(Color.parseColor("#8C9293"));

            binding.editor.setBackgroundColor(Color.TRANSPARENT);
            binding.editor.focusEditor();
            binding.editor.setInputEnabled(false);
            binding.editor.getSettings().setAllowFileAccess(true);

            if (!TextUtils.isEmpty(model.getDes())) {
                // binding.tvDes.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.description));
                binding.editor.setHtml(model.getDes());
                //   binding.tvDes.setVisibility(View.VISIBLE);
            } else {
                //    binding.tvDes.setVisibility(View.GONE);
            }



        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @SuppressLint("SetTextI18n")
    private void setQuotationDetails() {
        if (!TextUtils.isEmpty(model.getQuotId()) &&
                !TextUtils.isEmpty(model.getQuotLabel()) &&
                App_preference.getSharedprefInstance().getLoginRes().getCompPermission().get(0).getIsQuoteNoShowOnAppointment() != null
                && App_preference.getSharedprefInstance().getLoginRes().getCompPermission().get(0).getIsQuoteNoShowOnAppointment().equals("0")) {
            binding.quoteViews.setVisibility(View.VISIBLE);
            binding.tvRecentQuote.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.recent_quote) + " : ");
            binding.tvLabelQuotationId.setText(model.getQuotLabel());
            binding.llQuote.setVisibility(View.VISIBLE);
            binding.llQuoteDetails.setOnClickListener(view -> {
                Intent quotesinvoiceIntent = new Intent(AppointmentDetailsActivity.this, Quote_Invoice_Details_Activity.class);
                quotesinvoiceIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                quotesinvoiceIntent.putExtra("quotId", model.getQuotId());
                startActivity(quotesinvoiceIntent);
            });
        } else {
            binding.llQuote.setVisibility(View.GONE);
            binding.quoteViews.setVisibility(View.GONE);
        }


    }

    @SuppressLint("SetTextI18n")
    private void setJobDetails() {
        if (model != null)
            if (!TextUtils.isEmpty(model.getJobId()) &&
                    !TextUtils.isEmpty(model.getJobLabel())) {
                binding.tvRecentJob.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.recent_job) + " : ");
                binding.tvLabelJobId.setText(model.getJobLabel());
                binding.llJob.setVisibility(View.VISIBLE);
                binding.llJobDetails.setOnClickListener(view -> {
                    Intent jobDetails = new Intent(AppointmentDetailsActivity.this, JobDetailActivity.class);
                    Job jobsById = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).jobModel().getJobsById(model.getJobId());
                    if (jobsById != null) {
                        //    jobDetails.putExtra("JOBS", jobsById);
                        String strjob = new Gson().toJson(jobsById);
                        jobDetails.putExtra("JOBS", strjob);
                        jobDetails.putExtra("appId", model.getAppId());
                        jobDetails.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(jobDetails);
                    } else {
                        AppUtility.alertDialog(AppointmentDetailsActivity.this,
                                LanguageController.getInstance().getMobileMsgByKey(AppConstant.job),
                                LanguageController.getInstance().getMobileMsgByKey(AppConstant.job_not_fount), "",
                                LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok), null);
                    }
                });
            } else binding.llJob.setVisibility(View.GONE);

    }


    private void setClientName(Appointment model) {
        if (!TextUtils.isEmpty(model.getCltId())) {
            try {
                int i = Integer.parseInt(model.getCltId());
                Client clientFromId = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance())
                        .clientModel().getClientFromId(i);
                if (clientFromId != null)
                    binding.tvClientName.setText(clientFromId.getNm());
            } catch (NumberFormatException e) {
                e.printStackTrace();
                HyperLog.i("", "setClientName(M) exception:" + e.toString());

            }
        }
    }

    private void setScheduleDates(Appointment model) {
        if (model != null && !TextUtils.isEmpty(model.getSchdlStart())) {
            try {
                long longStartTime = Long.parseLong(model.getSchdlStart());
                String timeFormat = AppUtility.getDateWithFormate(longStartTime,
                        //"hh:mm a"
                        AppUtility.dateTimeByAmPmFormate("hh:mm a", "HH:mm")
                );
                binding.tvStartTime.setText(timeFormat);


                String dateFormat = AppUtility.getDateWithFormate(longStartTime, "dd-MMM-yyyy");
                binding.tvStartDate.setText(dateFormat);
                binding.tvDateTime.setText(timeFormat+" "+dateFormat);

                long endTime = Long.parseLong(model.getSchdlFinish());
                timeFormat = AppUtility.getDateWithFormate(endTime,
                        //"hh:mm a"
                        AppUtility.dateTimeByAmPmFormate("hh:mm a", "HH:mm"));
                binding.tvEndTime.setText(timeFormat);

                dateFormat = AppUtility.getDateWithFormate(endTime, "dd-MMM-yyyy");
                binding.tvEndDate.setText(dateFormat);

            } catch (Exception ex) {
                HyperLog.i("", "setScheduleDates(M) exception:" + ex.toString());

            }
        }
    }

    private void setcompleteAddress(Appointment model) {
        binding.tvCompleteAddress.setText("");
        if (!TextUtils.isEmpty(model.getAdr()))
            binding.tvCompleteAddress.setText(model.getAdr());
        if (!TextUtils.isEmpty(model.getCity()))
            binding.tvCompleteAddress.append(" " + model.getCity() + ", ");
        if (!TextUtils.isEmpty(model.getState())
                && !TextUtils.isEmpty(model.getCtry())) {
            try {
                String stateName = SpinnerCountrySite.getStatenameById(model.getCtry(), model.getState());
                if (!TextUtils.isEmpty(stateName))
                    binding.tvCompleteAddress.append(stateName);
                String countryId = SpinnerCountrySite.getCountryNameById(model.getCtry());
                if (!TextUtils.isEmpty(countryId))
                    binding.tvCompleteAddress.append(", " + countryId);
            } catch (Exception e) {
                e.printStackTrace();
                HyperLog.i("", "setcompleteAddress(M) exception:" + e.toString());

            }
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
            enterFieldDialog = new Dialog(AppointmentDetailsActivity.this);
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
            enterFieldDialog = new Dialog(AppointmentDetailsActivity.this);
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

    private void raiseCall() {
        if (this.model != null && this.model.getMob1() != null) {
            if (!this.model.getMob1().equals("") || this.model.getMob2() != null && !this.model.getMob2().equals("")) {
                //frameView.setAlpha(0.3F);
                getDialogCall();
                try {
                    final TextView txtViewSkypeCon1 = enterFieldDialog.findViewById(R.id.txtViewSkypeCon1);
                    txtViewSkypeCon1.setVisibility(View.VISIBLE);
                    final SpannableString s1 = new SpannableString(this.model.getMob1().trim());
                    Linkify.addLinks(txtViewSkypeCon1, Linkify.ALL);
                    if (TextUtils.isEmpty(s1))
                        txtViewSkypeCon1.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.no_contact_available));
                    else
                        txtViewSkypeCon1.setText(s1);

                    txtViewSkypeCon1.setOnClickListener(v -> {
                        if (ContextCompat.checkSelfPermission(AppointmentDetailsActivity.this, Manifest.permission.CALL_PHONE)
                                != PackageManager.PERMISSION_GRANTED) {

                            ActivityCompat.requestPermissions(AppointmentDetailsActivity.this, new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_CALL_PHONE);
                        } else if (AppUtility.isvalidPhoneNo(txtViewSkypeCon1.getText().toString())) {
                            AppUtility.getCallOnNumber(AppointmentDetailsActivity.this, txtViewSkypeCon1.getText().toString());

                        }
                    });

                    final TextView txtViewSkypeCon2 = enterFieldDialog.findViewById(R.id.txtViewSkypeCon2);
                    txtViewSkypeCon2.setVisibility(View.VISIBLE);
                    Linkify.addLinks(txtViewSkypeCon2, Linkify.ALL);
                    final SpannableString s = new SpannableString(this.model.getMob2().trim());
                    if (TextUtils.isEmpty(s))
                        txtViewSkypeCon2.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.no_contact_available));
                    else
                        txtViewSkypeCon2.setText(s);
                    txtViewSkypeCon2.setOnClickListener(v -> {
                        if (ContextCompat.checkSelfPermission(AppointmentDetailsActivity.this, Manifest.permission.CALL_PHONE)
                                != PackageManager.PERMISSION_GRANTED) {

                            ActivityCompat.requestPermissions(AppointmentDetailsActivity.this, new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_CALL_PHONE);
                        } else if (AppUtility.isvalidPhoneNo(txtViewSkypeCon2.getText().toString())) {
                            AppUtility.getCallOnNumber(AppointmentDetailsActivity.this, txtViewSkypeCon2.getText().toString());

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
                AppUtility.alertDialog(AppointmentDetailsActivity.this, LanguageController.getInstance().getMobileMsgByKey(AppConstant.dialog_alert), LanguageController.getInstance().getMobileMsgByKey(AppConstant.no_contact_available), LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok), "", () -> true);
            }
        }
    }

    private void showEmailDialog() {
        if (model != null) {
            if (model.getEmail() != null && !model.getEmail().equals("")) {
                // frameView.setAlpha(0.3F);
                getDialogEmail();
                TextView txtViewSkypeCon1 = enterFieldDialog.findViewById(R.id.txt_email_popup);
                final SpannableString s1 = new SpannableString(model.getEmail().trim());
                Linkify.addLinks(s1, Linkify.WEB_URLS | Linkify.EMAIL_ADDRESSES);
                txtViewSkypeCon1.setTextIsSelectable(true);
                txtViewSkypeCon1.setMovementMethod(LinkMovementMethod.getInstance());
                if (TextUtils.isEmpty(s1))
                    txtViewSkypeCon1.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.appoint_email_not_available));
                else
                    txtViewSkypeCon1.setText(s1);
                enterFieldDialog.show();

                TextView okBtn = enterFieldDialog.findViewById(R.id.btnClose);
                okBtn.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok));

                okBtn.setOnClickListener(v -> enterFieldDialog.dismiss());

            } else {
                AppUtility.alertDialog(this, LanguageController.getInstance().getMobileMsgByKey(AppConstant.dialog_alert), LanguageController.getInstance().getMobileMsgByKey(AppConstant.appoint_email_not_available), LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok), "", () -> true);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.quotes_menu, menu);
        menu.getItem(0).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
       /* else if (item.getItemId() == R.id.quotes_edit) {
            Intent intent = new Intent(this, AddAppointmentActivity.class);
            intent.putExtra(AddAppointmentActivity.ISINEDITMODE, true);
            intent.putExtra("appointment", model);
            intent.putExtra("editView",true);
            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivityForResult(intent, EDIT_APPOINTMENT_CODE);
        }*/
        return true;
    }

    @Override
    public void onBackPressed() {
        //   EotApp.getAppinstance().notifyApiObserver(Service_apis.addAppointment);
        super.onBackPressed();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.backgroundView:
                closeFABMenu();
                break;
            case R.id.fab:
                if (isFBMenuOpened)
                    closeFABMenu();
                else
                    showFBButtons();
                break;
            case R.id.linearFabQuote:
                if (model != null) {
                    if (model.getTempId().equals(model.getAppId()))
                        showDialogs(LanguageController.getInstance().getMobileMsgByKey(AppConstant.appointment_not_sync));
                    else {
                        closeFABMenu();
                        Intent intent = new Intent(this, AddQuotes_Activity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivityForResult(intent.putExtra("appointmentId", model.getAppId()), ADD_QUOTE_RESULT);
                    }
                }
                break;

            case R.id.linearFabJob:
                if (model != null) {
                    if (model.getTempId().equals(model.getAppId()))
                        showDialogs(LanguageController.getInstance().getMobileMsgByKey(AppConstant.appointment_not_sync));
                    else {
                        closeFABMenu();
                        Intent open_add_job = new Intent(AppointmentDetailsActivity.this, Add_job_activity.class);
                        open_add_job.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(open_add_job.putExtra("appId", model.getAppId()));
                    }
                }

                break;
            case R.id.img_call:
                raiseCall();
                break;
            case R.id.img_email:
                showEmailDialog();
                break;

            case R.id.tv_view_on_map:
                showLocation();
                break;

            case R.id.tv_add_new:
                if (model != null && model.getTempId().equals(model.getAppId())) {
                    showDialogs(LanguageController.getInstance().getMobileMsgByKey(AppConstant.appointment_not_sync));
                } else if (!AppUtility.isInternetConnected())
                    showDialogs(LanguageController.getInstance().getMobileMsgByKey(AppConstant.network_error));
                else {
                    selectFile(false);
                    //selectAttachment();
                }
                break;
            case R.id.tv_export_all:
                exportDocument();
                break;

            case R.id.linear_arraw_layout:
                binding.statusSpinner.performClick();
                break;

            case R.id.seemore:
                binding.seemore.setVisibility(View.GONE);
                binding.seeless.setVisibility(View.VISIBLE);
                binding.LayoutForSeeMoreLess.setVisibility(View.VISIBLE);
                break;
            case R.id.seeless:
                binding.seemore.setVisibility(View.VISIBLE);
                binding.seeless.setVisibility(View.GONE);
                binding.LayoutForSeeMoreLess.setVisibility(View.GONE);
                break;
            case R.id.edit_appointment:
                Intent intent = new Intent(this, AddAppointmentActivity.class);
                intent.putExtra(AddAppointmentActivity.ISINEDITMODE, true);
                intent.putExtra("appointment", model);
                intent.putExtra("editView",true);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivityForResult(intent, EDIT_APPOINTMENT_CODE);
                break;
           /* case R.id.tv_add_new_item:
                Intent requirmentGethering = new Intent(this, AddEditInvoiceItemActivity2.class);
                requirmentGethering.putExtra("req", "yes");
                startActivityForResult(requirmentGethering,GET_ITEM_LIST);
                break;*/
        }

    }

    private void exportDocument() {

        if (attachementAdapter != null) {
            HyperLog.i("", "exportDocument(M) start");
            List<AppointmentAttachment> list = attachementAdapter.getList();
            if (list != null) {
                List<String> selectedFiles = new ArrayList<>();
                for (AppointmentAttachment attachment : list) {
                    if (attachment.isSelected())
                        selectedFiles.add(attachment.getAttachmentId());
                }
                DocumentExportReq req = new DocumentExportReq();
                req.setJobId(model.getAppId());
                req.setDocumentId(selectedFiles);
                detailsViewModel.exportDocumentToPDF(req);

                HyperLog.i("", "exportDocument(M) completed");

            }
        }
    }

    private void showLocation() {
        if (model != null) {

            String locationdata = "";
            if (!TextUtils.isEmpty(model.getLat()) && !model.getLat().equals("0") && !model.getLng().equals("0")) {
                locationdata = "http://maps.google.com/maps?daddr=" + model.getLat() + "," + model.getLng();
            } else {
                String completeAddress = model.getAdr() + " " + model.getCity() + " " + SpinnerCountrySite.getCountryNameById(model.getCtry());
                //    String searchableAddress = completeAddress.replace(" ", "+");
                locationdata = "http://maps.google.com/maps?daddr=" + completeAddress;
            }
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(locationdata));
                //   intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } else {
            AppUtility.alertDialog(AppointmentDetailsActivity.this, LanguageController.getInstance().getMobileMsgByKey(AppConstant.dialog_alert), LanguageController.getInstance().getMobileMsgByKey(AppConstant.no_location), LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok), "", () -> true);
        }
    }


    @Override
    public void onDocumentSelected(String path,String name, boolean isImage) {
        super.onDocumentSelected(path,name, isImage);
        if (path != null) {
            try {
                if (isImage) {
                    File file = new File(path);
                    if (file != null && file.exists()) {
                        Uri parse = Uri.fromFile(file);
                        openUploadDocument(parse, isImage);
                    }
                } else {
                    openUploadDocument(path, isImage);
                }
            } catch (Exception e) {
                HyperLog.d("", "Exception in appointment Details: " + e.toString());
                e.printStackTrace();
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case ADD_QUOTE_RESULT:
                if (data != null && data.hasExtra("quotId")) {
                    if (model != null && !TextUtils.isEmpty(model.getAppId())) {
                        model.setQuotId(data.getStringExtra("quotId"));
                        model.setQuotLabel(data.getStringExtra("label"));
                        AppDataBase.getInMemoryDatabase(EotApp.getAppinstance())
                                .appointmentModel().updateAppointment(model.getAppId(), model.getQuotId(), model.getQuotLabel());
                        setQuotationDetails();
                    }
                }
                break;


            case EDIT_APPOINTMENT_CODE:
                if (data != null) {
                    model = (Appointment) data.getSerializableExtra("appointment");
                    if (model != null)
                        setDataInUI(model);
                }

                break;

            case UPLOADED_NEW_LIST:
                if (data != null && data.hasExtra("imgPath")) {
                    String fileNameExt = AppUtility.getFileNameWithExtension(data.getStringExtra("imgPath"));
                    String bitmapString="";
                    if(data.getBooleanExtra("isFileImage",false)){
                        Bitmap bitmap = AppUtility.getBitmapFromPath(data.getStringExtra("imgPath"));
                        bitmapString = AppUtility.BitMapToString(bitmap);
                    }
                    AppointmentAttachment obj=new AppointmentAttachment("0",fileNameExt,fileNameExt,bitmapString);
                    ArrayList<AppointmentAttachment> getFileList_res =new ArrayList<>();
                    getFileList_res.add(obj);

                    if (allAttachmentList != null) {
                        getFileList_res.addAll(allAttachmentList);
                    }
                    if (attachementAdapter != null) {
                        allAttachmentList=getFileList_res;
                        attachementAdapter.setList(getFileList_res);
                        binding.nolistLinear.setVisibility(View.GONE);
                    }
                    if (doc_attch_pi != null) {
                        if(data.getStringExtra("fileName")!=null){
                            try
                            {
                                doc_attch_pi.uploadDocuments(data.getStringExtra("appId"),
                                        data.getStringExtra("imgPath"),
                                        data.getStringExtra("fileName"),
                                        data.getStringExtra("desc"));
                            }
                            catch (Exception e)
                            {
                                if (getFileList_res.size()==1) {
                                    allAttachmentList.remove(getFileList_res.get(0));
                                    attachementAdapter.setList(getFileList_res);
                                }
                                e.printStackTrace();
                            }
                        }
                    }
                }
                break;

            case GET_ITEM_LIST:
                if(data!=null) {
                    //itemDataModel = data.getParcelableExtra("AddItemData");
                    appointmentItemData_pi.getItemFromServer(model.getAppId(),reqGethListAdapter);
                   // itemList.add(itemDataModel);

                }
                break;

            case UPDATE_ITEM:
                if(data!=null){
                       String appId= data.getStringExtra("appId");

                    String updatedItemData = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).appointmentModel().getUpdatedItemData(appId);
                    Type listType = new TypeToken<List<AppointmentItemDataInMap>>() {
                    }.getType();
                    List<AppointmentItemDataInMap> updatedItemList = new Gson().fromJson(updatedItemData, listType);
                    List<AppintmentItemDataModel> tempItemList = new ArrayList<>();
                    for (AppointmentItemDataInMap itemData : updatedItemList) {
                        AppintmentItemDataModel itemData1 = itemData.getItemData();
                       tempItemList.add(itemData1);
                    }
                    reqGethListAdapter.setItemList(tempItemList);
                    reqGethListAdapter.notifyDataSetChanged();

                }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void openUploadDocument(Uri uri, boolean isImage) {
        Intent intent = new Intent(this, ActivityDocumentUpload.class);
        intent.putExtra("uri", uri);
        intent.putExtra("isImage", isImage);
        intent.putExtra("appId", model.getAppId());
        startActivityForResult(intent, UPLOADED_NEW_LIST);
    }

    private void openUploadDocument(String uri, boolean isImage) {
        Intent intent = new Intent(this, ActivityDocumentUpload.class);
        intent.putExtra("uri", uri);
        intent.putExtra("isImage", isImage);
        intent.putExtra("appId", model.getAppId());
        startActivityForResult(intent, UPLOADED_NEW_LIST);
    }

    private void showDialogs(String msg) {
        AppUtility.error_Alert_Dialog(this, msg, LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok)
                , () -> null);
    }


    private void showFBButtons() {
        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.bg_color)));
        getSupportActionBar().setStackedBackgroundDrawable(new ColorDrawable(Color.parseColor("#90ffffff")));
        binding.backgroundView.setVisibility(View.VISIBLE);
        if (App_preference.getSharedprefInstance().getLoginRes().getRights().get(0).getIsQuoteVisible() == 0)
            binding.linearFabQuote.setVisibility(View.VISIBLE);
        binding.linearFabJob.setVisibility(View.VISIBLE);
        binding.linearFabJob.animate().translationY(getResources().getDimension(R.dimen.standard_55));
        binding.linearFabQuote.animate().translationY(getResources().getDimension(R.dimen.standard_100));
        isFBMenuOpened = true;
    }

    private void closeFABMenu() {
        isFBMenuOpened = false;
        binding.linearFabJob.animate().translationY(0);
        binding.linearFabQuote.animate().translationY(0);

        binding.linearFabQuote.animate().translationY(0).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (!isFBMenuOpened) {
                    binding.backgroundView.setVisibility(View.GONE);
                    binding.linearFabJob.setVisibility(View.GONE);
                    binding.linearFabQuote.setVisibility(View.GONE);
                    Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));

                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

    }

    @Override
    public void showExportOption(boolean b) {

        if (b)
            binding.rlExportDoc.setVisibility(View.VISIBLE);
        else
            binding.rlExportDoc.setVisibility(View.GONE);

    }

    @Override
    public void selectAllOption(boolean b) {
        binding.checkboxSelectAll.setOnCheckedChangeListener(null);
        binding.checkboxSelectAll.setChecked(b);
        binding.checkboxSelectAll.setOnCheckedChangeListener(selectAllListener());
    }

    @Override
    public void showDocFormatMSG() {
        AppUtility.alertDialog(this, "", LanguageController.getInstance().getMobileMsgByKey(AppConstant.select_doc_validation), LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok), "", () -> null);
    }

    private void sendUpdateRequest() {
        HyperLog.i("", "sendUpdateRequest(M) start");

        HyperLog.i("", "sendUpdateRequest(M) on synced appointment");
        AppointmentUpdateReq updateReq = new AppointmentUpdateReq();
        updateReq.setAppId(model.getAppId());
        updateReq.setCltId(model.getCltId());
        updateReq.setSiteId(model.getSiteId());
        updateReq.setConId(model.getConId());
        updateReq.setStatus(model.getStatus());
        updateReq.setDes(model.getDes());

        try {
            updateReq.setSchdlStart(AppUtility.getDateWithFormate(Long.parseLong(model.getSchdlStart()), "yyyy-MM-dd HH:mm:ss"));
            updateReq.setSchdlFinish(AppUtility.getDateWithFormate(Long.parseLong(model.getSchdlFinish()), "yyyy-MM-dd HH:mm:ss"));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            updateReq.setSchdlStart("");
            updateReq.setSchdlFinish("");
        }
        Set<String> kprs = new HashSet<>();
        if (model != null && model.getKpr() != null)
            for (Keepar keepar : model.getKpr()) {
                kprs.add(keepar.getUsrId());
                updateReq.setMemIds(kprs);
            }
        assert model != null;
        updateReq.setCtry(model.getCtry());
        updateReq.setState(model.getState());
        updateReq.setCity(model.getCity());
        updateReq.setAdr(model.getAdr());
        updateReq.setZip(model.getZip());
        updateReq.setMob1(model.getMob1());
        updateReq.setNm(model.getNm());
        updateReq.setEmail(model.getEmail());
        updateReq.setAttachCount(model.getAttachCount());

        List<String> files = new ArrayList<>();
        updateReq.setAppDoc(files);
        String s = new Gson().toJson(updateReq);
        String dateTime = AppUtility.getDateByFormat(AppConstant.DATE_TIME_FORMAT);

        //update in local DB
        model.setStatus(statusByValue);
        AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).appointmentModel().insertSingleAppointment(model);
        OfflineDataController.getInstance().addInOfflineDB(Service_apis.updateAppointment, s, dateTime);
        Intent intent = new Intent();
        intent.putExtra("isUpdate", true);
        setResult(RESULT_OK, intent);
        //refresh and mark appointment as completed on list
        LocalBroadcastManager.getInstance(this).sendBroadcast(new Intent("appointment_refresh"));
        HyperLog.i("", "sendUpdateRequest(M) Completed");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (mMessageReceiver != null)
                LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void selectFile() {

    }

    @Override
    public void setList(ArrayList<AppointmentAttachment> getFileList_res) {
        // remove the temporary added item for showing loader
        if (allAttachmentList != null&&!allAttachmentList.isEmpty()) {
            int position = -1;
            for (int i = 0; i < allAttachmentList.size(); i++) {
                if(allAttachmentList.get(i).getAttachmentId().equalsIgnoreCase("0")){
                    position=i;
                    break;
                }
            }
            if(position!=-1)
                allAttachmentList.remove(allAttachmentList.get(position));
        }
        // to add the new entry into existing list
        if (getFileList_res != null&& attachementAdapter != null) {
            assert allAttachmentList != null;
            getFileList_res.addAll(allAttachmentList);
            allAttachmentList=getFileList_res;
            attachementAdapter.setList(getFileList_res);
            binding.nolistLinear.setVisibility(View.GONE);
        }
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
    public void setAppointmentStatusList(){

        allAppointmentStatusList = AppDataBase.getInMemoryDatabase(this).appointmentStatusDao().getAllAppointmentStatusList();
        for (AppointmentStatusModel statusModel:allAppointmentStatusList)
        {
            if(statusModel.getIsStatusShow().equals("1")) {
                arraystatus.put(statusModel.getKey(), statusModel.getName());
            }
        }
        int i=0;
        statusArray = new String[arraystatus.size()];
        for (Map.Entry mapElement : arraystatus.entrySet()) {
            statusArray[i] = ((String) mapElement.getValue());
            i++;
        }

    }

    @Override
    public void onMyListitemSeleted(AppintmentItemDataModel appintmentItemDataModel) {
        Intent intent = new Intent(this, AddEditInvoiceItemActivity2.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.putExtra("UpdateItemRequirmentGethering",true);
        intent.putExtra("appId",model.getAppId());
        intent.putExtra("appItemModelForUpdate",appintmentItemDataModel);

        startActivityForResult(intent,UPDATE_ITEM);
    }

    @Override
    public void updateItemDataList(List<AppintmentItemDataModel> dataModelList, RequirementGetheringListAdapter requirementGetheringListAdapter) {
        itemList.clear();
        itemList.addAll(dataModelList);
//        setData(itemList);
//      reqGethListAdapter=new RequirementGetheringListAdapter(this);
        requirementGetheringListAdapter.updateItemList(itemList);
        requirementGetheringListAdapter.notifyDataSetChanged();
    }
    public void setData(List<AppintmentItemDataModel> dataModelList){

        reqGethListAdapter.updateItemList(itemList);
        reqGethListAdapter.notifyDataSetChanged();
    }



    @Override
    public void itemDelete(String ilmmId) {

    }
}
