package com.eot_app.nav_menu.appointment.details.documents;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.TypeConverters;

import com.eot_app.nav_menu.jobs.job_detail.invoice.invoice_db.tax_dao.TaxConverter;
import com.eot_app.nav_menu.jobs.job_detail.invoice.invoice_detail_pkg.inv_detail_model.Tax;

import java.util.List;

public class AppointmentTax implements Parcelable   {

    private String rate;
    private String label;
    private String taxId;
    private String ilmmId;
    private String iltmmId;
    @TypeConverters(TaxConverter.class)
    private List<Tax> taxComponents;


    protected AppointmentTax(Parcel in) {
        rate = in.readString();
        label = in.readString();
        taxId = in.readString();
        ilmmId = in.readString();
        iltmmId = in.readString();
        taxComponents = in.createTypedArrayList(Tax.CREATOR);
    }

    public static final Creator<AppointmentTax> CREATOR = new Creator<AppointmentTax>() {
        @Override
        public AppointmentTax createFromParcel(Parcel in) {
            return new AppointmentTax(in);
        }

        @Override
        public AppointmentTax[] newArray(int size) {
            return new AppointmentTax[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(rate);
        parcel.writeString(label);
        parcel.writeString(taxId);
        parcel.writeString(ilmmId);
        parcel.writeString(iltmmId);
        parcel.writeTypedList(taxComponents);
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public String getIlmmId() {
        return ilmmId;
    }

    public void setIlmmId(String ilmmId) {
        this.ilmmId = ilmmId;
    }

    public String getIltmmId() {
        return iltmmId;
    }

    public void setIltmmId(String iltmmId) {
        this.iltmmId = iltmmId;
    }

    public List<Tax> getTaxComponents() {
        return taxComponents;
    }

    public void setTaxComponents(List<Tax> taxComponents) {
        this.taxComponents = taxComponents;
    }
}
