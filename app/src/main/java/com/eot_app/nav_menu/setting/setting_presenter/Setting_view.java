package com.eot_app.nav_menu.setting.setting_presenter;

public interface Setting_view {
    public void changePasswordSuccess(String msg);
    public void changePasswordFailure(String msg);
    void onSessionExpired(String msg);
}
