package com.eot_app.nav_menu.drag_and_drop_functinality;

import android.util.SparseArray;
import android.view.View;

public class ViewManager {
    private SparseArray<View> viewList;

    public ViewManager() {
        viewList = new SparseArray<>();
    }

    public void addView(int id, View view) {
        viewList.put(id, view);
    }

    public View getView(int id) {
        return viewList.get(id);
    }

}
