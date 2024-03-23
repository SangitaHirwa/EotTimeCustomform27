package com.eot_app.nav_menu.jobs.job_detail.requested_item;

import com.eot_app.nav_menu.jobs.job_detail.invoice.inventry_pkg.Inventry_ReS_Model;
import com.eot_app.nav_menu.jobs.job_detail.job_equipment.add_job_equip.model_pkg.BrandData;

import java.util.List;

public interface AddUpdateReqItem_View {
    void setItemdata(List<Inventry_ReS_Model> list);
    void setBrandList(List<BrandData> brandList);
    void showMessage(String msg);
    void showAlertDailog();
}
