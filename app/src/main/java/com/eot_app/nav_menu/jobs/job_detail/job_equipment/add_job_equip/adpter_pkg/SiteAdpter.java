package com.eot_app.nav_menu.jobs.job_detail.job_equipment.add_job_equip.adpter_pkg;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.eot_app.R;
import com.eot_app.nav_menu.client.clientlist.client_detail.site.sitelist.editsite.editsitedb.SpinnerCountrySite;
import com.eot_app.nav_menu.jobs.job_detail.job_equipment.add_job_equip.clientEqu.ClientEquRes;
import com.eot_app.nav_menu.jobs.job_detail.job_equipment.add_job_equip.model_pkg.BrandData;

import java.util.ArrayList;

public class SiteAdpter extends ArrayAdapter<ClientEquRes> {

    int layoutResourceId;
    Context contextMylocationAdapter;
    ArrayList<ClientEquRes> arrayOfLocationPickUpDelivery;
    ArrayList<ClientEquRes> itemsAll;
    ArrayList<ClientEquRes> suggestions;
    boolean isShowAdr = false;
    String adr,city,state,cntry,zip;
    String location = "";
    Filter nameFilter = new Filter() {
        @Override
        public String convertResultToString(Object resultValue) {
            return ((ClientEquRes) (resultValue)).getSnm();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (!TextUtils.isEmpty(constraint)) {
                suggestions.clear();
                for (ClientEquRes customer : itemsAll) {
                    if (customer.getSnm().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        suggestions.add(customer);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                FilterResults filterResults = new FilterResults();
                filterResults.values = itemsAll;
                filterResults.count =itemsAll.size();
                return filterResults;
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            @SuppressWarnings("unchecked")
            ArrayList<ClientEquRes> filteredList = (ArrayList<ClientEquRes>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (ClientEquRes c : filteredList) {
                    add(c);
                }
                notifyDataSetChanged();
            }
        }
    };

    @SuppressWarnings("unchecked")
    public SiteAdpter(Context contextMylocationAdapter, int layoutResourceId, ArrayList<ClientEquRes> arrayOfLocationPickUpDelivery) {
        super(contextMylocationAdapter, layoutResourceId, arrayOfLocationPickUpDelivery);
        this.layoutResourceId = layoutResourceId;
        this.contextMylocationAdapter = contextMylocationAdapter;
        this.arrayOfLocationPickUpDelivery = arrayOfLocationPickUpDelivery;
        this.itemsAll = (ArrayList<ClientEquRes>) arrayOfLocationPickUpDelivery.clone();
        this.suggestions = new ArrayList<ClientEquRes>();
    }
    public SiteAdpter(Context contextMylocationAdapter, int layoutResourceId, ArrayList<ClientEquRes> arrayOfLocationPickUpDelivery,boolean isShowAdr) {
        super(contextMylocationAdapter, layoutResourceId, arrayOfLocationPickUpDelivery);
        this.layoutResourceId = layoutResourceId;
        this.contextMylocationAdapter = contextMylocationAdapter;
        this.arrayOfLocationPickUpDelivery = arrayOfLocationPickUpDelivery;
        this.itemsAll = (ArrayList<ClientEquRes>) arrayOfLocationPickUpDelivery.clone();
        this.suggestions = new ArrayList<ClientEquRes>();
        this.isShowAdr = isShowAdr;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(layoutResourceId, null);
        }

        ClientEquRes pickOrDropModel = arrayOfLocationPickUpDelivery.get(position);
        if (pickOrDropModel != null) {
            TextView customerNameLabel = v.findViewById(R.id.item_title_name);
            TextView address = v.findViewById(R.id.item_subTitle_name);
            if (customerNameLabel != null) {
                Log.i("", "getView Customer Name:" + pickOrDropModel.getSnm());
                customerNameLabel.setText(pickOrDropModel.getSnm());
            }
            if(isShowAdr && address != null){
                location = "";
                address.setVisibility(View.VISIBLE);
                assert customerNameLabel != null;
                customerNameLabel.setTextColor(contextMylocationAdapter.getResources().getColor(R.color.black));
                if(pickOrDropModel.getAdr() != null && !pickOrDropModel.getAdr().isEmpty()){
                    adr = pickOrDropModel.getAdr();
                    location = location+""+adr;
                }if(pickOrDropModel.getCity() != null && !pickOrDropModel.getCity().isEmpty()){
                    city = pickOrDropModel.getCity();
                    location = location+", "+city;
                }if(pickOrDropModel.getState() != null && !pickOrDropModel.getState().isEmpty()&& !pickOrDropModel.getCtry().isEmpty() && !pickOrDropModel.getState().equals("0")){
                    state = pickOrDropModel.getState();
                    location = location+", "+state;
                }if(pickOrDropModel.getCtry() != null && !pickOrDropModel.getCtry().isEmpty() && !pickOrDropModel.getCtry().equals("0")){
                    cntry = SpinnerCountrySite.getCountryNameById(pickOrDropModel.getCtry());
                    location = location+", "+cntry;
                }if(pickOrDropModel.getZip() != null && !pickOrDropModel.getZip().isEmpty()){
                    zip = pickOrDropModel.getZip();
                    location = location+", "+zip;
                }
                address.setText(location);
            }
        }
        return v;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

}

