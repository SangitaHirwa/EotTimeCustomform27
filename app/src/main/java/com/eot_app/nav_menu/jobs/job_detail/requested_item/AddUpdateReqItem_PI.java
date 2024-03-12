package com.eot_app.nav_menu.jobs.job_detail.requested_item;

import com.eot_app.nav_menu.jobs.job_detail.requested_item.requested_itemModel.AddUpdateRequestedModel;

public interface AddUpdateReqItem_PI {
    void getInventryItemList();
    void getDataFromServer(String search);
   void getBrandList();
    void addReqItemApi(AddUpdateRequestedModel addeRequestModel);
}
