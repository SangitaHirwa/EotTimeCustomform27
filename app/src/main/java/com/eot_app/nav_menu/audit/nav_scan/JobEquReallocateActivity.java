package com.eot_app.nav_menu.audit.nav_scan;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.eot_app.R;
import com.eot_app.nav_menu.client.clientlist.client_detail.site.sitelist.Site_model;
import com.eot_app.nav_menu.client.clientlist.client_detail.site.sitelist.editsite.editsitedb.SpinnerCountrySite;
import com.eot_app.nav_menu.jobs.add_job.adapters.FilterAdapterSites;
import com.eot_app.nav_menu.jobs.add_job.adapters.FilterCountry;
import com.eot_app.nav_menu.jobs.add_job.adapters.FilterStates;
import com.eot_app.nav_menu.jobs.job_detail.job_equipment.job_equ_remrk.job_equ_mvp.JobEquReallocate_Pc;
import com.eot_app.nav_menu.jobs.job_detail.job_equipment.job_equ_remrk.job_equ_mvp.JobEquReallocate_Pi;
import com.eot_app.nav_menu.jobs.job_detail.job_equipment.job_equ_remrk.job_equ_mvp.JobEquReallocate_View;
import com.eot_app.nav_menu.jobs.job_detail.job_equipment.model.UpdateSiteLocationReqModel;
import com.eot_app.utility.AppConstant;
import com.eot_app.utility.AppUtility;
import com.eot_app.utility.App_preference;
import com.eot_app.utility.Country;
import com.eot_app.utility.EotApp;
import com.eot_app.utility.States;
import com.eot_app.utility.db.AppDataBase;
import com.eot_app.utility.language_support.LanguageController;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class JobEquReallocateActivity extends AppCompatActivity implements View.OnClickListener, JobEquReallocate_View {
    TextView old_location_detail, txt_old_location, txt_new_location, no_site;
    Button location_save_btn;
    TextInputLayout adr_layout, input_layout_site;
    List<Country> countrylist = new ArrayList<>();
    List<States> stateslist = new ArrayList<>();
    LinearLayout site_dp_layout;
    String oldLocation = "";
    String newLocation = "";
    String clientId = "";
    String equId = "";
    AutoCompleteTextView auto_sites, adr;
    ImageView site_dp_img;
    List<Site_model> site_data;
    private String siteId = "";
    private Site_model selectedSiteData;
    FilterAdapterSites filterSites;
    View view_adr;
    ConstraintLayout new_location_Detail_l;
    JobEquReallocate_Pi reallocate_pi;
    UpdateSiteLocationReqModel reqModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_equ_reallocate);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setTitle(LanguageController.getInstance().getMobileMsgByKey(AppConstant.reallocate));
        if (getIntent().hasExtra("old_location")) {
            oldLocation = getIntent().getStringExtra("old_location");
            clientId = getIntent().getStringExtra("clientId");
            equId = getIntent().getStringExtra("equId");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        initializelables();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setData();
    }

    private void setData() {
        old_location_detail.setText(oldLocation);
        if (clientId != null && !clientId.isEmpty()) {
            getSiteList(clientId);
        }
    }

    private void initializelables() {
        old_location_detail = findViewById(R.id.old_location_detail);
        txt_old_location = findViewById(R.id.txt_old_location);
        txt_new_location = findViewById(R.id.txt_new_location);
        location_save_btn = findViewById(R.id.location_save_btn);
        new_location_Detail_l = findViewById(R.id.new_location_Detail_l);
        no_site = findViewById(R.id.no_site);
        adr = findViewById(R.id.adr);
        adr_layout = findViewById(R.id.adr_layout);
        site_dp_layout = findViewById(R.id.site_dp_layout);
        input_layout_site = findViewById(R.id.input_layout_site);
        auto_sites = findViewById(R.id.auto_sites);
        site_dp_img = findViewById(R.id.site_dp_img);
        view_adr = findViewById(R.id.view_adr);

        adr.setHint((LanguageController.getInstance().getMobileMsgByKey(AppConstant.address)));
        auto_sites.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.project_site_name));
        txt_new_location.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.new_location));
        txt_old_location.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.old_location));
        location_save_btn.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.save_btn));

        location_save_btn.setOnClickListener(this);
        auto_sites.setOnClickListener(this);
        site_dp_img.setOnClickListener(this);
        auto_sites.setFocusable(false);
        adr.setFocusable(false);
        adr_layout.setClickable(false);
        adr.setClickable(false);

        reallocate_pi = new JobEquReallocate_Pc(this);

        auto_sites.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                String site_txt = auto_sites.getText().toString().trim();
                if (site_txt.length() > 0) {
                    if (site_data != null) {
                        for (Site_model siteData : site_data) {
                            if (site_txt.equals(siteData.getSnm())) {
                                siteId = siteData.getSiteId();
                            } else {
                                auto_sites.setText(AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).sitemodel().getSiteFromSiteId(siteId).getSnm());
                            }
                        }
                    }
                }
            }
        });

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.site_dp_img:
                auto_sites.showDropDown();
                break;
            case R.id.location_save_btn:
                updateLocation();
                break;
        }
    }

    private void updateLocation() {
        if(selectedSiteData != null){
            String[] equIdArrr = new String[1];
            equIdArrr[0] = equId;
             reqModel =  new UpdateSiteLocationReqModel(equIdArrr,"1",clientId,"",
                    selectedSiteData.getAdr(),selectedSiteData.getCtry(),selectedSiteData.getState(),
                    selectedSiteData.getCity(),selectedSiteData.getZip(),"",selectedSiteData.getSiteId());
              reallocate_pi.updateLocation(reqModel);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }

    private void setSitetDefaultData(Site_model sitetData) {
        if (sitetData != null) {
            String countryNameById = "";
            String statenameById = "";
            siteId = sitetData.getSiteId();
            auto_sites.setFocusableInTouchMode(false);
            auto_sites.setFocusable(false);
            site_dp_img.setClickable(true);
            if (sitetData.getCtry() != null) {
                countryNameById = SpinnerCountrySite.getCountryNameById(sitetData.getCtry());
            }
            if (sitetData.getCtry() != null && sitetData.getState() != null) {
                statenameById = SpinnerCountrySite.getStatenameById((sitetData.getCtry()), sitetData.getState());
            }
            newLocation = sitetData.getAdr() + ", " + sitetData.getCity() + " " + countryNameById + " " + statenameById + " " + sitetData.getZip();
            adr.setText(newLocation);
            adr_layout.setHintEnabled(true);
        }
    }

    public void getSiteList(String cltId) {
        if (Integer.parseInt(cltId) > 0) {
            setSiteList(AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).sitemodel().getActiveSitesFromCltId(Integer.parseInt(cltId)));
        }
    }

    public void setSiteList(final List<Site_model> data) {
        if (data.size() > 1) {
            this.site_data = data;
            AppUtility.autocompletetextviewPopUpWindow(auto_sites);
                /*if(isFirst) {
                    isFirst = false;
                    for (Site_model siteData : data) {
                        if (siteData.getDef().equals("1")) {
                            auto_sites.setText(siteData.getSnm());
                            setSitetDefaultData(siteData);
                            input_layout_site.setHintEnabled(true);
                            break;
                        }
                    }
                }*/
            filterSites = new FilterAdapterSites(this, R.layout.custom_adapter_item_layout, (ArrayList<Site_model>) data);
            auto_sites.setAdapter(filterSites);
            auto_sites.setThreshold(0);
            auto_sites.setOnItemClickListener((adapterView, view, i, l) -> {
                Site_model site_model = (Site_model) (adapterView.getAdapter().getItem(i));
                selectedSiteData = site_model;
                setSitetDefaultData(site_model);
            });
            auto_sites.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    //new_site_nm = charSequence.toString();
                    if (charSequence.length() >= 1) {
                        input_layout_site.setHintEnabled(true);
                        adr_layout.setHintEnabled(true);
                    } else if (charSequence.length() <= 0) {
                        input_layout_site.setHintEnabled(false);
                        adr_layout.setHintEnabled(false);
                    }


                }

                @Override
                public void afterTextChanged(Editable editable) {
                }
            });
        } else {
            new_location_Detail_l.setVisibility(View.GONE);
            no_site.setVisibility(View.VISIBLE);
            view_adr.setVisibility(View.GONE);
            location_save_btn.setClickable(false);
            location_save_btn.setBackgroundResource(R.drawable.disable_submit_btn);
        }
    }

    @Override
    public void setNewLocation() {
        Intent intent = new Intent();
        String json = new Gson().toJson(reqModel);
        intent.putExtra("adr_model",json);
        intent.putExtra("loc",newLocation);
        setResult(RESULT_OK,intent);
        finish();
    }
}