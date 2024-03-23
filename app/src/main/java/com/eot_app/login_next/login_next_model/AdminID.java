package com.eot_app.login_next.login_next_model;

import com.google.gson.annotations.SerializedName;

public class AdminID {
    @SerializedName("usrId")
    private Integer usrId;
    @SerializedName("firebaseNotifpermi")
    private FirebaseNotifpermi firebaseNotifpermi;

    public Integer getUsrId() {
        return usrId;
    }

    public void setUsrId(Integer usrId) {
        this.usrId = usrId;
    }

    public FirebaseNotifpermi getFirebaseNotifpermi() {
        return firebaseNotifpermi;
    }

    public void setFirebaseNotifpermi(FirebaseNotifpermi firebaseNotifpermi) {
        this.firebaseNotifpermi = firebaseNotifpermi;
    }
}
