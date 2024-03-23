package com.eot_app.nav_menu.appointment.appointment_model;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.room.TypeConverters;

import com.eot_app.nav_menu.appointment.details.documents.AppointmentTaxConverter;
import com.eot_app.nav_menu.appointment.list.AppointmentItemDataConverter;


public class AppointmentItemDataInMap implements Parcelable {

    private String ilmmId;
    @TypeConverters(AppointmentItemDataConverter.class)
    private AppintmentItemDataModel itemDetails;

    public AppointmentItemDataInMap(String ilmmId, AppintmentItemDataModel itemDetails) {
        this.ilmmId = ilmmId;
        this.itemDetails = itemDetails;
    }

    protected AppointmentItemDataInMap(Parcel in) {
     ilmmId = in.readString();
        itemDetails = in.readParcelable(AppintmentItemDataModel.class.getClassLoader());
    }

    public static final Creator<AppointmentItemDataInMap> CREATOR = new Creator<AppointmentItemDataInMap>() {
        @Override
        public AppointmentItemDataInMap createFromParcel(Parcel in) {
            return new AppointmentItemDataInMap(in);
        }

        @Override
        public AppointmentItemDataInMap[] newArray(int size) {
            return new AppointmentItemDataInMap[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(ilmmId);
        parcel.writeParcelable(itemDetails,i);
    }

    public String getIlmmId() {
        return ilmmId;
    }

    public void setIlmmId(String ilmmId) {
        this.ilmmId = ilmmId;
    }

    public AppintmentItemDataModel getItemData() {
        return itemDetails;
    }

    public void setItemData(AppintmentItemDataModel itemData) {
        this.itemDetails = itemData;
    }
}
