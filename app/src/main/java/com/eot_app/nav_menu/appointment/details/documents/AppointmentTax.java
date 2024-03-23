package com.eot_app.nav_menu.appointment.details.documents;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.TypeConverters;

import com.eot_app.nav_menu.appointment.appointment_model.AppointmentItemDataInMap;
import com.eot_app.nav_menu.jobs.job_detail.invoice.invoice_db.tax_dao.TaxConverter;
import com.eot_app.nav_menu.jobs.job_detail.invoice.invoice_detail_pkg.inv_detail_model.Tax;

import java.util.ArrayList;
import java.util.List;

public class AppointmentTax implements Parcelable   {

    private String rate;
    private String label;
    private int taxId;
    private int ilmmId;
    private int iltmmId;
    @TypeConverters(TaxConverter.class)
    private List<Tax> taxComponents;

    public AppointmentTax(int taxId,String rate,String label,List<Tax> taxComponents) {
        this.taxId=taxId;
        this.taxComponents = taxComponents;
        this.rate=rate;
        this.label=label;
    }

    protected AppointmentTax(Parcel in) {
        rate = in.readString();
        label = in.readString();
        taxId = in.readInt();
        ilmmId = in.readInt();
        iltmmId = in.readInt();
        taxComponents = new ArrayList<>();
        in.readList(taxComponents, Tax.class.getClassLoader());
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
        parcel.writeInt(taxId);
        parcel.writeInt(ilmmId);
        parcel.writeInt(iltmmId);
        parcel.writeList(taxComponents);
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

    public int getTaxId() {
        return taxId;
    }

    public void setTaxId(int taxId) {
        this.taxId = taxId;
    }

    public int getIlmmId() {
        return ilmmId;
    }

    public void setIlmmId(int ilmmId) {
        this.ilmmId = ilmmId;
    }

    public int getIltmmId() {
        return iltmmId;
    }

    public void setIltmmId(int iltmmId) {
        this.iltmmId = iltmmId;
    }

    public List<Tax> getTaxComponents() {
        return taxComponents;
    }

    public void setTaxComponents(List<Tax> taxComponents) {
        this.taxComponents = taxComponents;
    }
}
