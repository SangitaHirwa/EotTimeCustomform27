package com.eot_app.nav_menu.jobs.job_detail.invoice.invoice_detail_pkg.inv_detail_model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.eot_app.nav_menu.jobs.job_detail.invoice.invoice_db.tax_dao.TaxConverter;

import java.util.List;
import java.util.Objects;


public class TaxComponents implements Parcelable {

    public static final Creator<TaxComponents> CREATOR = new Creator<TaxComponents>() {
        @Override
        public TaxComponents createFromParcel(Parcel in) {
            return new TaxComponents(in);
        }

        @Override
        public TaxComponents[] newArray(int size) {
            return new TaxComponents[size];
        }
    };
    private String taxId;
    private String label;
//    private String isactive;
//    private String show_Invoice;
//    private String rate = "0";
    private String percentage = "0";




    protected TaxComponents(Parcel in) {
        taxId = in.readString();
        label = in.readString();
//        isactive = in.readString();
//        show_Invoice = in.readString();
//        rate = in.readString();
        percentage = in.readString();
    }

    public static Creator<TaxComponents> getCREATOR() {
        return CREATOR;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(taxId);
        dest.writeString(label);
//        dest.writeString(isactive);
//        dest.writeString(show_Invoice);
//        dest.writeString(rate);
        dest.writeString(percentage);

    }


    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    @Override
    public int describeContents() {
        return 0;
    }


    @NonNull
    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(@NonNull String taxId) {
        this.taxId = taxId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

//    public String getIsactive() {
//        return isactive;
//    }
//
//    public void setIsactive(String isactive) {
//        this.isactive = isactive;
//    }
//
//    public String getShow_Invoice() {
//        return show_Invoice;
//    }
//
//    public void setShow_Invoice(String show_Invoice) {
//        this.show_Invoice = show_Invoice;
//    }
//
//    public String getRate() {
//        return rate;
//    }
//
//    public void setRate(String rate) {
//        this.rate = rate;
//    }



    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaxComponents that = (TaxComponents) o;
        return Objects.equals(taxId, that.taxId) &&
                Objects.equals(label, that.label)
//                && Objects.equals(isactive, that.isactive)
//                && Objects.equals(show_Invoice, that.show_Invoice)
//                && Objects.equals(rate, that.rate)
                && Objects.equals(percentage, that.percentage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taxId, label, /*isactive, show_Invoice, rate,*/ percentage);
    }
}
