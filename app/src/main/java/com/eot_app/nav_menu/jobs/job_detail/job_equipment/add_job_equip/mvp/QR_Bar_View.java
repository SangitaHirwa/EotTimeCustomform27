package com.eot_app.nav_menu.jobs.job_detail.job_equipment.add_job_equip.mvp;

public interface QR_Bar_View {
    void setBarCodeData(QRCOde_Barcode_Res_Model res_Model);
    void setQRCodeData(QRCOde_Barcode_Res_Model res_Model);
   void alertShow(String msg);
   void toastShow(String msg);

}
