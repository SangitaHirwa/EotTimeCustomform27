package com.eot_app.nav_menu.jobs.job_db;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class IsMarkDoneWithJtid implements Parcelable {

       private String status;
        private String jtId;
        private String title;

    public IsMarkDoneWithJtid(String status, String jtId, String title) {
        this.status = status;
        this.jtId = jtId;
        this.title = title;
    }

    protected IsMarkDoneWithJtid(Parcel in) {
        status = in.readString();
        jtId = in.readString();
        title = in.readString();
    }

    public static final Creator<IsMarkDoneWithJtid> CREATOR = new Creator<IsMarkDoneWithJtid>() {
        @Override
        public IsMarkDoneWithJtid createFromParcel(Parcel in) {
            return new IsMarkDoneWithJtid(in);
        }

        @Override
        public IsMarkDoneWithJtid[] newArray(int size) {
            return new IsMarkDoneWithJtid[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getJtId() {
        return jtId;
    }

    public void setJtId(String jtId) {
        this.jtId = jtId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(status);
        dest.writeString(jtId);
        dest.writeString(title);
    }
}
