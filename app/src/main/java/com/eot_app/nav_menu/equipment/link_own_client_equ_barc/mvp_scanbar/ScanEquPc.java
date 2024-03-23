package com.eot_app.nav_menu.equipment.link_own_client_equ_barc.mvp_scanbar;

import android.util.Log;

import com.eot_app.nav_menu.audit.audit_list.scanbarcode.model.ScanBarcodeRequest;
import com.eot_app.nav_menu.equipment.linkequip.linkMVP.model.ContractEquipmentReq;
import com.eot_app.nav_menu.equipment.linkequip.linkMVP.model.EquipmentListReq;
import com.eot_app.nav_menu.jobs.job_db.EquArrayModel;
import com.eot_app.services.ApiClient;
import com.eot_app.services.Service_apis;
import com.eot_app.utility.AppConstant;
import com.eot_app.utility.AppUtility;
import com.eot_app.utility.language_support.LanguageController;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Sona-11 on 28/8/21.
 */
public class ScanEquPc implements ScanEquPi {
    private final ScanEquView scanEquView;

    public ScanEquPc(ScanEquView scanEquView) {
        this.scanEquView = scanEquView;
    }

    @Override
    public void searchJobWithBarcode(ScanBarcodeRequest request, List<EquArrayModel> myEquList) {
        if (myEquList != null) {
            boolean isEquipmentFound = false;
            List<EquArrayModel> equArray = new ArrayList<>();
            equArray = myEquList;
            if (equArray != null) {
                for (EquArrayModel equipment : equArray) {
                    if (equipment.getSno() != null && equipment.getSno().equals(request.getBarCode()) ||
                            equipment.getBarcode() != null && equipment.getBarcode().equals(request.getBarCode())) {
                        isEquipmentFound = true;
                        scanEquView.onJobEquipmentFound(equipment);
                        break;
                    }
                }
            }
            if (!isEquipmentFound) scanEquView.onJobEquipmentFound(null);
        } else {
            scanEquView.onJobEquipmentFound(null);
        }
    }
}
