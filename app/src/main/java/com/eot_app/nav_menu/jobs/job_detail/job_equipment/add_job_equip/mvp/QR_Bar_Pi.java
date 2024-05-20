package com.eot_app.nav_menu.jobs.job_detail.job_equipment.add_job_equip.mvp;

public interface QR_Bar_Pi {
    void getBarCode(String barcode);
    void getQRCode(String qrCode);
    void updateQRCode(String equId,String qrCode);
    void updateBarcode(String equId,String qrCode);

}
