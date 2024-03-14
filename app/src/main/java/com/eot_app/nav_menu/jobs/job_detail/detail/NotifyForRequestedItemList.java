package com.eot_app.nav_menu.jobs.job_detail.detail;

import com.eot_app.nav_menu.jobs.job_detail.requested_item.requested_itemModel.AddUpdateRequestedModel;
import com.eot_app.utility.settings.setting_db.Offlinetable;

public interface NotifyForRequestedItemList {
    void updateReqItemList(String api_name, String message, AddUpdateRequestedModel requestedModel);
}
