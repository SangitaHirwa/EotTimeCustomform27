package com.eot_app.nav_menu.appointment.details;
import android.content.Context;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.eot_app.R;
import com.eot_app.nav_menu.appointment.appointment_model.AppintmentItemDataModel;
import com.eot_app.nav_menu.jobs.job_detail.addinvoiveitem2pkg.model.InvoiceItemDataModel;
import com.eot_app.nav_menu.quote.quotes_list_pkg.QuoteList_Adpter;
import com.eot_app.nav_menu.quote.quotes_list_pkg.qoute_model_pkg.Quote_ReS;
import com.eot_app.utility.AppConstant;
import com.eot_app.utility.AppUtility;
import com.eot_app.utility.App_preference;
import com.eot_app.utility.EotApp;
import com.eot_app.utility.language_support.LanguageController;
import com.eot_app.utility.util_interfaces.Callback_AlertDialog;
import com.eot_app.utility.util_interfaces.MyListItemSelected;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class RequirementGetheringListAdapter extends RecyclerView.Adapter<RequirementGetheringViewHolder> {
    Context context;
    List<AppintmentItemDataModel> itemDataModels;
    AppintmentItemDataModel ItemDataModel;
    private final MyListItemSelected<AppintmentItemDataModel> mListner;
    DeleteItem deleteItem;


    RequirementGetheringListAdapter( List<AppintmentItemDataModel> itemList, MyListItemSelected<AppintmentItemDataModel> mListner,DeleteItem deleteItem,
                                     Context context) {
        this.itemDataModels=itemList;
        this.mListner = mListner;
        this.deleteItem=deleteItem;
        this.context=context;
    }


    @NonNull
    @NotNull
    @Override
    public RequirementGetheringViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.requirment_gethering_list_view, parent, false);
        return new RequirementGetheringViewHolder(view, mListner);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RequirementGetheringViewHolder holder, int position) {
        ItemDataModel = itemDataModels.get(position);
        if(!ItemDataModel.getInm().equals("")) {
            holder.service.setText(ItemDataModel.getInm());
            holder.service.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        }
        holder.desOfRequirment.setText(ItemDataModel.getDes());
        holder.count.setText("X" + ItemDataModel.getQty());
        if (ItemDataModel.getDataType() == 1) {
            String item="Item";
            holder.work.setText(item);
        } else if (ItemDataModel.getDataType() == 3) {
            String service="Service";
            holder.work.setText(service);
        }
        if (itemDataModels.get(position).getIlmmId() == 0) {
            holder.desOfRequirment.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.item_not_sync));
            holder.desOfRequirment.setTextColor(EotApp.getAppinstance().getResources().getColor(android.R.color.holo_red_light));
        }else{
            holder.desOfRequirment.setTextColor(EotApp.getAppinstance().getResources().getColor(R.color.txt_color));
        }

        holder.deleteItem.setOnClickListener(view -> {
            AppUtility.alertDialog2(context, "", LanguageController.getInstance().getMobileMsgByKey(AppConstant.invoice_remove), LanguageController.getInstance().getMobileMsgByKey(AppConstant.ok), LanguageController.getInstance().getMobileMsgByKey(AppConstant.cancel), new Callback_AlertDialog() {
                @Override
                public void onPossitiveCall() {
            try {

                if (itemDataModels.get(position).getIlmmId() != 0) {
                    deleteItem.itemDelete(String.valueOf(itemDataModels.get(position).getIlmmId())
                            ,itemDataModels.get(position).getDataType(),itemDataModels.get(position).getLeadId());
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
        holder.item_req_geth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (itemDataModels.get(position).getIlmmId() != 0) {
                        mListner.onMyListitemSeleted(itemDataModels.get(position));
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

    }

    public void setItemList(List<AppintmentItemDataModel> itemList) {
        this.itemDataModels = itemList;
        notifyDataSetChanged();
    }

    public void updateItemList(List<AppintmentItemDataModel> itemList) {
        itemDataModels.clear();
        itemDataModels.addAll(itemList);
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (itemDataModels.size() == 0)
            return 0;
        else return itemDataModels.size();
    }
    public interface DeleteItem {
        void itemDelete(String ilmmId,int isItemOrTitle, int leadId);
    }
}


    class RequirementGetheringViewHolder extends RecyclerView.ViewHolder {
        ImageView deleteItem;
        MyListItemSelected<AppintmentItemDataModel> mListner;
        TextView work,service,desOfRequirment,count;
        ConstraintLayout item_req_geth;
        public RequirementGetheringViewHolder(@NonNull @NotNull View itemView,MyListItemSelected<AppintmentItemDataModel> mListner) {
            super(itemView);
            this.mListner=mListner;
            deleteItem=itemView.findViewById(R.id.delete_item_icon);
            work=itemView.findViewById(R.id.work_tv);
            service=itemView.findViewById(R.id.service_tv);
            desOfRequirment=itemView.findViewById(R.id.desofRequirment);
            count=itemView.findViewById(R.id.count);
            item_req_geth = itemView.findViewById(R.id.item_req_geth);
            if (App_preference.getSharedprefInstance().getLoginRes().getIsItemDeleteEnable().equals("0")) {
                deleteItem.setVisibility(View.GONE);
                ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) desOfRequirment.getLayoutParams();
                layoutParams.setMarginStart(8);
                desOfRequirment.setLayoutParams(layoutParams);
            }else {
                deleteItem.setVisibility(View.VISIBLE);
            }


        }
    }



