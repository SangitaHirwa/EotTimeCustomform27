package com.eot_app.nav_menu.audit.nav_scan;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.eot_app.R;
import com.eot_app.nav_menu.client.clientlist.client_detail.site.sitelist.editsite.editsitedb.SpinnerCountrySite;
import com.eot_app.nav_menu.jobs.add_job.adapters.FilterCountry;
import com.eot_app.nav_menu.jobs.add_job.adapters.FilterStates;
import com.eot_app.utility.AppConstant;
import com.eot_app.utility.AppUtility;
import com.eot_app.utility.App_preference;
import com.eot_app.utility.Country;
import com.eot_app.utility.States;
import com.eot_app.utility.language_support.LanguageController;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class JobEquReallocateActivity extends AppCompatActivity implements TextWatcher, View.OnClickListener {
    EditText site_name,adr,city,zip;
    AutoCompleteTextView cntry, state;
    TextView old_location_detail, txt_old_location, txt_new_location;
    Button location_save_btn;
    TextInputLayout state_layout,country_layout,site_layout,adr_layout,city_layout,zip_layout;
    List<Country> countrylist= new ArrayList<>();
    List<States> stateslist= new ArrayList<>();
    String cntryId,stateId,location="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_equ_reallocate);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setTitle(LanguageController.getInstance().getMobileMsgByKey(AppConstant.reallocate));
       if(getIntent().hasExtra("old_location")){
           location = getIntent().getStringExtra("old_location");
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
        clientCountryList();
        setCompanySettingAdrs();
    }

    private void setData() {
        old_location_detail.setText(location);
    }

    private void initializelables() {
        old_location_detail = findViewById(R.id.old_location_detail);
        txt_old_location = findViewById(R.id.txt_old_location);
        txt_new_location = findViewById(R.id.txt_new_location);
        location_save_btn = findViewById(R.id.location_save_btn);
        site_layout = findViewById(R.id.site_layout);
        adr_layout = findViewById(R.id.adr_layout);
        city_layout = findViewById(R.id.city_layout);
        country_layout = findViewById(R.id.country_layout);
        state_layout = findViewById(R.id.state_layout);
        zip_layout = findViewById(R.id.zip_layout);
        site_name = findViewById(R.id.site_name);
        site_name.setHint((LanguageController.getInstance().getMobileMsgByKey(AppConstant.site_name)));

        adr = findViewById(R.id.adr);
        adr.setHint((LanguageController.getInstance().getMobileMsgByKey(AppConstant.address)));

        cntry = findViewById(R.id.cntry);
        cntry.setHint((LanguageController.getInstance().getMobileMsgByKey(AppConstant.country)) + " *");

        state = findViewById(R.id.state);
        state.setHint((LanguageController.getInstance().getMobileMsgByKey(AppConstant.state)) + " *");

        city = findViewById(R.id.city);
        city.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.city));

        zip = findViewById(R.id.zip);
        zip.setHint(LanguageController.getInstance().getMobileMsgByKey(AppConstant.zip));
        txt_new_location.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.new_location));
        txt_old_location.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.old_location));
        location_save_btn.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.save_btn));
        Objects.requireNonNull(adr_layout.getEditText()).addTextChangedListener(this);
        Objects.requireNonNull(city_layout.getEditText()).addTextChangedListener(this);
        Objects.requireNonNull(country_layout.getEditText()).addTextChangedListener(this);
        Objects.requireNonNull(state_layout.getEditText()).addTextChangedListener(this);
        Objects.requireNonNull(zip_layout.getEditText()).addTextChangedListener(this);
        Objects.requireNonNull(site_layout.getEditText()).addTextChangedListener(this);

        state.setOnClickListener(this);
        cntry.setOnClickListener(this);
        location_save_btn.setOnClickListener(this);
    }
    private void setCompanySettingAdrs() {
        cntry.setText(SpinnerCountrySite.getCountryNameById(App_preference.getSharedprefInstance().getCompanySettingsDetails().getCtry()));
        city_layout.setHintEnabled(true);
        state_layout.setHintEnabled(true);
        state.setText(SpinnerCountrySite.getStatenameById(App_preference.getSharedprefInstance().getCompanySettingsDetails().getCtry(), App_preference.getSharedprefInstance().getCompanySettingsDetails().getState()));
        city.setText(App_preference.getSharedprefInstance().getCompanySettingsDetails().getCity());
        state.setText(SpinnerCountrySite.getStatenameById(App_preference.getSharedprefInstance().getCompanySettingsDetails().getCtry(), App_preference.getSharedprefInstance().getCompanySettingsDetails().getState()));city.setText(App_preference.getSharedprefInstance().getCompanySettingsDetails().getCity());
        stateId = (App_preference.getSharedprefInstance().getCompanySettingsDetails().getState());
        cntryId = (App_preference.getSharedprefInstance().getCompanySettingsDetails().getCtry());

    }
    public void clientCountryList() {
        countrylist = SpinnerCountrySite.clientCountryList();
       setCountryList(countrylist);
    }
    public void clientStatesList(String cntryId) {
        stateslist = SpinnerCountrySite.clientStatesList(cntryId);
       setStateList(stateslist);
    }
    public void setCountryList(List<Country> countryList) {
        AppUtility.autocompletetextviewPopUpWindow(cntry);
        FilterCountry countryAdapter = new FilterCountry(this, R.layout.custom_adapter_item_layout, (ArrayList<Country>) countryList);
        cntry.setAdapter(countryAdapter);
        cntry.setThreshold(1);
        cntry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                cntryId = ((Country) adapterView.getItemAtPosition(i)).getId();
                clientStatesList(cntryId);
            }
        });
        cntry.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                state.setText("");
                stateId = "";
                if (charSequence.length() >= 1) {country_layout.setHintEnabled(true);
                } else if (charSequence.length() <= 0) {
                    country_layout.setHintEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void setStateList(List<States> stateList) {
        AppUtility.autocompletetextviewPopUpWindow(state);
        FilterStates stateAdapter = new FilterStates(this, R.layout.custom_adapter_item_layout, (ArrayList<States>) stateList);
        state.setAdapter(stateAdapter);

        state.setOnItemClickListener((adapterView, view, i, l) -> stateId = (((States) adapterView.getItemAtPosition(i)).getId()));
        state.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() >= 1) {
                    state_layout.setHintEnabled(true);
                } else if (charSequence.length() <= 0) {
                    state_layout.setHintEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    @Override
    public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
        if (charSequence.length() >= 1) {
            if (charSequence.hashCode() == adr.getText().hashCode())
                adr_layout.setHintEnabled(true);
            if (charSequence.hashCode() == city.getText().hashCode())
                city_layout.setHintEnabled(true);
            if (charSequence.hashCode() == zip.getText().hashCode())
                zip_layout.setHintEnabled(true);
            if (charSequence.hashCode() == site_name.getText().hashCode())
                site_layout.setHintEnabled(true);
        } else if (charSequence.length() <= 0) {
            if (charSequence.hashCode() == adr.getText().hashCode())
                adr_layout.setHintEnabled(false);
            if (charSequence.hashCode() == city.getText().hashCode())
                city_layout.setHintEnabled(false);
            if (charSequence.hashCode() == zip.getText().hashCode())
                zip_layout.setHintEnabled(false);
            if (charSequence.hashCode() == site_name.getText().hashCode())
                site_layout.setHintEnabled(false);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onClick(View v) {

    }
}