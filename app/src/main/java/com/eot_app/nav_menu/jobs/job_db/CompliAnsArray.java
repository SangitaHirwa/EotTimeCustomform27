package com.eot_app.nav_menu.jobs.job_db;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.util.Objects;

public class CompliAnsArray implements Parcelable  {

    private String ansId;

    private String queId;

    private String type;

    private String value;

    private String usrId;

    private String jtId;

    private String key;

    private String valueThumb;

    public String getAnsId() {
        return ansId;
    }

    public void setAnsId(String ansId) {
        this.ansId = ansId;
    }

    public String getQueId() {
        return queId;
    }

    public void setQueId(String queId) {
        this.queId = queId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUsrId() {
        return usrId;
    }

    public void setUsrId(String usrId) {
        this.usrId = usrId;
    }

    public String getJtId() {
        return jtId;
    }

    public void setJtId(String jtId) {
        this.jtId = jtId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValueThumb() {
        return valueThumb;
    }

    public void setValueThumb(String valueThumb) {
        this.valueThumb = valueThumb;
    }

    public static final Parcelable.Creator<CompliAnsArray> CREATOR = new Parcelable.Creator<CompliAnsArray>() {
        @Override
        public CompliAnsArray createFromParcel(Parcel in) {
            return new CompliAnsArray(in);
        }

        @Override
        public CompliAnsArray[] newArray(int size) {
            return new CompliAnsArray[size];
        }
    };

    protected  CompliAnsArray(Parcel in) {
        ansId = in.readString();
        queId = in.readString();
        type = in.readString();
        value = in.readString();
        usrId = in.readString();
        jtId = in.readString();
        key = in.readString();
        valueThumb = in.readString();
    }
    public static Creator<CompliAnsArray> getCREATOR() {
        return CREATOR;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(ansId);
        dest.writeString(queId);
        dest.writeString(type);
        dest.writeString(value);
        dest.writeString(usrId);
        dest.writeString(jtId);
        dest.writeString(key);
        dest.writeString(valueThumb);
    }

    @Override
    public String toString() {
        return "CompliAnsArray{" +
                "ansId='" + ansId + '\'' +
                ", queId='" + queId + '\'' +
                ", type='" + type + '\'' +
                ", value='" + value + '\'' +
                ", usrId='" + usrId + '\'' +
                ", jtId='" + jtId + '\'' +
                ", key='" + key + '\'' +
                ", valueThumb='" + valueThumb + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CompliAnsArray that)) return false;
        return Objects.equals(getAnsId(), that.getAnsId()) && Objects.equals(getQueId(), that.getQueId()) && Objects.equals(getType(), that.getType()) && Objects.equals(getValue(), that.getValue()) && Objects.equals(getUsrId(), that.getUsrId()) && Objects.equals(getJtId(), that.getJtId()) && Objects.equals(getKey(), that.getKey()) && Objects.equals(getValueThumb(), that.getValueThumb());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAnsId(), getQueId(), getType(), getValue(), getUsrId(), getJtId(), getKey(), getValueThumb());
    }
}
