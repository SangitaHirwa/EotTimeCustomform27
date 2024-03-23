package com.eot_app.nav_menu.jobs.job_detail.requested_item;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eot_app.R;
import com.eot_app.nav_menu.jobs.job_detail.detail.DetailFragment;
import com.eot_app.nav_menu.jobs.job_detail.requested_item.requested_itemModel.AddUpdateRequestedModel;
import com.eot_app.nav_menu.jobs.job_detail.requested_item.requested_itemModel.RequestedItemModel;
import com.eot_app.utility.AppConstant;
import com.eot_app.utility.AppUtility;
import com.eot_app.utility.db.AppDataBase;
import com.eot_app.utility.language_support.LanguageController;
import com.eot_app.utility.util_interfaces.Callback_AlertDialog;

import java.util.List;

public class RequestedItemListAdapter extends RecyclerView.Adapter<RequestedItemListAdapter.MyViewHolder> {
    List<RequestedItemModel> requestedItemList;
    RequestedItemListAdapter.DeleteItem deleteItem;
    RequestedItemListAdapter.SelectedItemListener selectedItemListener;
    Context context;
    public RequestedItemListAdapter(List<RequestedItemModel> requestedItemList, Context context1,DeleteItem deleteItem,SelectedItemListener selectedItemListener) {
        this.requestedItemList = requestedItemList;
        this.context = context1;
        this.deleteItem = deleteItem;
        this.selectedItemListener = selectedItemListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.requested_item_list_adapter_view,parent,false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        RequestedItemModel requestedItemModel = requestedItemList.get(position);
        holder.item_nm.setText(requestedItemModel.getInm());
        if(requestedItemModel.getQty().equals("")){
            holder.qty_item.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.qty)+":- "+"0");
        }
        holder.qty_item.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.qty)+":- "+requestedItemModel.getQty());
        if(requestedItemModel.getModelNo().equals("")){
            holder.model_no_text.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.part_no)+" ");
        }else {
            holder.model_no_text.setVisibility(View.VISIBLE);
            holder.model_no_text.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.part_no) + ":- " + requestedItemModel.getModelNo());
        }
        if(requestedItemModel.getEbId() != null && !requestedItemModel.getEbId().equals("")) {
            String brandName = AppDataBase.getInMemoryDatabase(context).brandDao().getBrandNameById(requestedItemModel.getEbId());
            holder.item_brand.setVisibility(View.VISIBLE);
            if(brandName != null && !brandName.isEmpty()) {
                holder.item_brand.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.brand) + ":- " + brandName);
            }else {
                holder.item_brand.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.brand)+" ");
            }
        }
        else {
            holder.item_brand.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.brand)+" ");
        }

        holder.item_view.setOnClickListener(v -> {
         selectedItemListener.itemSelected(requestedItemModel);
        });

        holder.remove_item.setOnClickListener(view -> {
            AppUtility.alertDialog2(context, "", LanguageController.getInstance().getMobileMsgByKey(AppConstant.remove_requested_item), LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok), LanguageController.getInstance().getMobileMsgByKey(AppConstant.cancel), new Callback_AlertDialog() {
                @Override
                public void onPossitiveCall() {
                    try {

                        if (!requestedItemModel.getId().equals("")) {
                            deleteItem.itemDelete(requestedItemModel.getId(),requestedItemModel);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }

                }

                @Override
                public void onNegativeCall() {

                }
            });
        });
    }

    @Override
    public int getItemCount() {
        return requestedItemList.size();
    }

    public void setReqItemList(List<RequestedItemModel> reqItemList){
        requestedItemList.clear();
        this.requestedItemList = reqItemList;
        notifyDataSetChanged();
    }
    public interface DeleteItem {
        void itemDelete(String irId, RequestedItemModel requestedModel);
    }
    public interface SelectedItemListener {
        void itemSelected(RequestedItemModel requestedItemModel);
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
       TextView item_nm,qty_item,model_no_text,item_brand;
       ImageView remove_item;
       RelativeLayout item_view;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        item_nm = itemView.findViewById(R.id.item_nm);
        qty_item = itemView.findViewById(R.id.qty_item);
        model_no_text = itemView.findViewById(R.id.model_no_text);
        item_brand = itemView.findViewById(R.id.item_brand);
        remove_item = itemView.findViewById(R.id.remove_item);
        item_view = itemView.findViewById(R.id.relativeLayout);
    }
}
}
