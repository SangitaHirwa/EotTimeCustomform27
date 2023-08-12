package com.eot_app.utility.util_interfaces;

import android.content.Context;
import android.content.SharedPreferences;

import com.eot_app.utility.App_preference;
import com.eot_app.utility.EotApp;

public class FIrstSyncPreference implements Sync_S_Model{

    public static Sync_S_Model INSTANCE = new FIrstSyncPreference();
    private final SharedPreferences.Editor editor;
    private final SharedPreferences sp;
    final String PREF_NAME = "eot_pref_sync";
    private final String JobIndexValue = "JobIndexValue";
    private final String ClientIndexValue = "ClientIndexValue";
    private final String ContactIndexValue = "ContactIndexValue";
    private final String SiteIndexValue = "SiteIndexValue";
    private final String ChatGrtopIndexValue = "chatgroupIndexValue";
    private final String AppoinmentIndexValue = "appoinmentIndexValue";
    private final String InvoiceItemIndexValue = "invoiceItemIndexValue";
    private final String InvoiceTextIndexValue = "invoiceTextIndexValue";
    private final String AuditIndexValue = "auditIndexValue";
    private final String ContractIndexValue = "contractIndexValue";
    private final String EquipmentIndexValue = "equipmentIndexValue";
    private final String CustomFormIndexValue = "customFormIndexValue";

    public FIrstSyncPreference() {
        sp = EotApp.getAppinstance().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    public static Sync_S_Model getSharedprefInstance() {
        return INSTANCE;
    }

    @Override
    public void setJobIndexValue(int jobindex) {
        editor.putInt(JobIndexValue,jobindex);
        editor.commit();
    }

    @Override
    public int getJobIndexValue() {
        return sp.getInt(JobIndexValue,0);
    }

    @Override
    public void setclientIndexValue(int clientindex) {
        editor.putInt(ClientIndexValue,clientindex);
        editor.commit();
    }

    @Override
    public int getclilentIndexValue() {
        return sp.getInt(ClientIndexValue,0);
    }

    @Override
    public void setContactIndexValue(int contactindex) {
        editor.putInt(ContactIndexValue,contactindex);
        editor.commit();
    }

    @Override
    public int getContactIndexValue() {
        return sp.getInt(ContactIndexValue,0);
    }

    @Override
    public void setSiteIndexValue(int siteindex) {
        editor.putInt(SiteIndexValue,siteindex);
        editor.commit();
    }

    @Override
    public int getSiteIndexValue() {
        return sp.getInt(SiteIndexValue,0);
    }

    @Override
    public void setChatGroupIndexValue(int chatGroupindex) {
        editor.putInt(ChatGrtopIndexValue,chatGroupindex);
        editor.commit();
    }

    @Override
    public int getChatgroupIndexValue() {
        return sp.getInt(ChatGrtopIndexValue,0);
    }

    @Override
    public void setAppoinmentIndexValue(int appoinmentindex) {
        editor.putInt(AppoinmentIndexValue,appoinmentindex);
        editor.commit();
    }

    @Override
    public int getAppoinmentIndexValue() {
        return sp.getInt(AppoinmentIndexValue,0);
    }

    @Override
    public void setInvoiceItemIndexValue(int invoiceItemindex) {
        editor.putInt(InvoiceItemIndexValue,invoiceItemindex);
        editor.commit();
    }

    @Override
    public int getInvoiceItemIndexValue() {
        return sp.getInt(InvoiceItemIndexValue,0);
    }

    @Override
    public void setInvoiceTextIndexValue(int invoiceTextindex) {
        editor.putInt(InvoiceTextIndexValue,invoiceTextindex);
        editor.commit();
    }

    @Override
    public int getInvoiceTextIndexValue() {
        return sp.getInt(InvoiceTextIndexValue,0);
    }

    @Override
    public void setAuditIndexValue(int auditindex) {
        editor.putInt(AuditIndexValue,auditindex);
        editor.commit();
    }

    @Override
    public int getAuditIndexValue() {
        return sp.getInt(AuditIndexValue,0);
    }

    @Override
    public void setContractIndexValue(int contractindex) {
        editor.putInt(ContractIndexValue,contractindex);
        editor.commit();
    }

    @Override
    public int getContractIndexValue() {
        return sp.getInt(ContractIndexValue,0);
    }

    @Override
    public void setEquipmentIndexValue(int equipmentindex) {
        editor.putInt(EquipmentIndexValue,equipmentindex);
        editor.commit();
    }

    @Override
    public int getEquipmentIndexValue() {
        return sp.getInt(EquipmentIndexValue,0);
    }

    @Override
    public void setCustomIndexValue(int customformindex) {
        editor.putInt(CustomFormIndexValue,customformindex);
        editor.commit();
    }

    @Override
    public int getCustomIndexValue() {
        return sp.getInt(CustomFormIndexValue,0);
    }


}
