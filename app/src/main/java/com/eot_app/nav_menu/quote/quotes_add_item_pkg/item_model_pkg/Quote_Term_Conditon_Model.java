package com.eot_app.nav_menu.quote.quotes_add_item_pkg.item_model_pkg;

import com.google.gson.annotations.SerializedName;

public class Quote_Term_Conditon_Model {

    @SerializedName("tacId")
    
    private String tacId;
    @SerializedName("compId")
    
    private String compId;
    @SerializedName("tacName")
    
    private String tacName;
    @SerializedName("termAndCondition")
    
    private String termAndCondition;
    @SerializedName("isDefault")
    
    private String isDefault;

    public String getTacId() {
        return tacId;
    }

    public void setTacId(String tacId) {
        this.tacId = tacId;
    }

    public String getCompId() {
        return compId;
    }

    public void setCompId(String compId) {
        this.compId = compId;
    }

    public String getTacName() {
        return tacName;
    }

    public void setTacName(String tacName) {
        this.tacName = tacName;
    }

    public String getTermAndCondition() {
        return termAndCondition;
    }

    public void setTermAndCondition(String termAndCondition) {
        this.termAndCondition = termAndCondition;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }
}
