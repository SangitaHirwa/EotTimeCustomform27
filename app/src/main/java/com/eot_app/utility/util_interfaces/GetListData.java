package com.eot_app.utility.util_interfaces;

import com.eot_app.nav_menu.jobs.job_detail.invoice2list.itemlist_model.TaxData;

import java.util.List;

public interface GetListData {
    public  void setCalculation (Double Subtotal, List<TaxData> listTax, boolean isShippingData,String singleTaxId);
}
