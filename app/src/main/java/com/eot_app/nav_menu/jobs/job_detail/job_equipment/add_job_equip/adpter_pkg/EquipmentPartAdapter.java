package com.eot_app.nav_menu.jobs.job_detail.job_equipment.add_job_equip.adpter_pkg;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.eot_app.R;
import com.eot_app.nav_menu.audit.audit_list.equipment.equipment_room_db.entity.EquipmentStatus;
import com.eot_app.nav_menu.jobs.job_db.EquArrayModel;
import com.eot_app.nav_menu.jobs.job_detail.job_equipment.JobEquipmentAdapter;
import com.eot_app.nav_menu.jobs.job_detail.job_equipment.job_equ_remrk.JobEquRemarkRemarkActivity;
import com.eot_app.utility.AppConstant;
import com.eot_app.utility.AppUtility;
import com.eot_app.utility.App_preference;
import com.eot_app.utility.EotApp;
import com.eot_app.utility.db.AppDataBase;
import com.eot_app.utility.language_support.LanguageController;
import com.google.gson.Gson;


import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by Sonam-11 on 22/9/20.
 */
public class EquipmentPartAdapter extends RecyclerView.Adapter<EquipmentPartAdapter.MyViewHolder> {
    Context mContext;
    List<EquArrayModel> list;
    boolean isComeFromDetail=false;
    String mainEquipmentName="";
    JobEquipmentAdapter.OnEquipmentClicked onEquipmentClicked;
    JobEquipmentAdapter.OnEquipmentSelection onEquipmentSelection;
    JobEquipmentAdapter.OnEquipmentSelectionForDetails selectionForDetails;
    List<EquipmentStatus> equipmentCurrentStatus;

    List<EquipmentStatus> equipmentStatus;
    public EquipmentPartAdapter(Context mContext, List<EquArrayModel> list,
                                JobEquipmentAdapter.OnEquipmentSelectionForDetails selectionForDetails,
                                JobEquipmentAdapter.OnEquipmentSelection onEquipmentSelection,
                                boolean isComeFromDetail,
                                JobEquipmentAdapter.OnEquipmentClicked onEquipmentClicked) {
        this.mContext = mContext;
        this.list = list;
        this.selectionForDetails = selectionForDetails;
        this.onEquipmentClicked = onEquipmentClicked;
        this.onEquipmentSelection = onEquipmentSelection;
        this.isComeFromDetail = isComeFromDetail;
        equipmentStatus = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).equipmentStatusDao().getEquipmentCondition();

        notifyDataSetChanged();
    }

    public EquipmentPartAdapter(Context mContext, List<EquArrayModel> list) {
        this.mContext = mContext;
        this.list = list;
        equipmentStatus = AppDataBase.getInMemoryDatabase(EotApp.getAppinstance()).equipmentStatusDao().getEquipmentCondition();

        notifyDataSetChanged();
    }

    public List<EquArrayModel> getList() {
        return list;
    }

    @NonNull
    @Override
    public EquipmentPartAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_part, viewGroup, false);
        return new EquipmentPartAdapter.MyViewHolder(view);
    }
    @SuppressLint("NotifyDataSetChanged")
    public void setEquipmentCurrentStatus(List<EquipmentStatus> equipmentCurrentStatus) {
        this.equipmentCurrentStatus = equipmentCurrentStatus;
        notifyDataSetChanged();
    }

    private String getCurrentStatusNameById(String statusId) {
        String statusName = "";
        if (equipmentCurrentStatus != null) {
            for (EquipmentStatus equipmentStatus : equipmentCurrentStatus)
                if (equipmentStatus.getEsId().equals(statusId)) {
                    statusName = equipmentStatus.getStatusText();
                    break;
                }
        }
        return statusName;
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull EquipmentPartAdapter.MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        EquArrayModel equArrayModel = list.get(position);
        Log.e("List", "" + new Gson().toJson(list));

        if (!TextUtils.isEmpty(equArrayModel.getEquRemarkCondition()) || equArrayModel.getAttachments() != null && equArrayModel.getAttachments().size() > 0) {
            holder.img_show_remark.setVisibility(View.VISIBLE);
        }else {
            holder.img_show_remark.setVisibility(View.GONE);
        }
        if(isComeFromDetail)
        {
            holder.main_layout.setOnClickListener(v -> onEquipmentClicked.OnEquipmentClicked());
            holder.add_action.setOnClickListener(v -> onEquipmentClicked.OnEquipmentClicked());
            holder.view_details.setOnClickListener(v -> onEquipmentClicked.OnEquipmentClicked());

        }
        else {
            holder.view_details.setOnClickListener(v -> selectionForDetails.onEquipmentSelectedForDetails(list.get(position)));
            holder.add_action.setOnClickListener(v -> setRemarkActivity(holder.getBindingAdapterPosition(), true));
        }
        if(equArrayModel.getExpiryDate()!=null&&!equArrayModel.getExpiryDate().isEmpty()){
            try {
                String expiryDate = AppUtility.getDate(Long.parseLong(equArrayModel.getExpiryDate()),AppConstant.DATE_FORMAT);
                Log.e("Equipment",expiryDate);
                Log.e("Equipment",AppUtility.getCurrentDateByFormat(AppConstant.DATE_FORMAT));
                if(!AppUtility.compareTwoDatesWarranty(AppUtility.getCurrentDateByFormat(AppConstant.DATE_FORMAT),expiryDate,AppConstant.DATE_FORMAT)){
                    holder.tvWarranty.setTextColor(ContextCompat.getColor(mContext,R.color.red_color));
                    holder.ivAlert.setVisibility(View.VISIBLE);
                }
                else{
                    holder.tvWarranty.setTextColor(ContextCompat.getColor(mContext,R.color.txt_color));
                    holder.ivAlert.setVisibility(View.GONE);
                }
                holder.tvWarranty.setVisibility(View.VISIBLE);
                holder.tvWarranty.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.warranty_expiry_date) + ": "+expiryDate);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else {
            holder.tvWarranty.setVisibility(View.GONE);
        }



        holder.tv_item_name.setText(equArrayModel.getEqunm());


        if (!TextUtils.isEmpty(equArrayModel.getLocation())) {
            holder.tv_address.setText(equArrayModel.getLocation());
            holder.tv_address.setVisibility(View.GONE);
        } else holder.tv_address.setVisibility(View.GONE);


        holder.tv_model_label.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.equipment_model) + ": ");
        holder.tv_serial_label.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.equipment_serial) + ": ");

        holder.tv_model.setText("" + equArrayModel.getMno());
        holder.tv_serial.setText("" + equArrayModel.getSno());

        if(equArrayModel.getExpiryDate()!=null&&!equArrayModel.getExpiryDate().isEmpty()){
            try {
                String expiryDate = AppUtility.getDate(Long.parseLong(equArrayModel.getExpiryDate()),AppConstant.DATE_FORMAT);
                Log.e("Equipment",expiryDate);
                Log.e("Equipment",AppUtility.getCurrentDateByFormat(AppConstant.DATE_FORMAT));
                if(!AppUtility.compareTwoDatesWarranty(AppUtility.getCurrentDateByFormat(AppConstant.DATE_FORMAT),expiryDate,AppConstant.DATE_FORMAT)){
                    holder.tvWarranty.setTextColor(ContextCompat.getColor(mContext,R.color.red_color));
                    holder.ivAlert.setVisibility(View.VISIBLE);
                }
                else{
                    holder.tvWarranty.setTextColor(ContextCompat.getColor(mContext,R.color.txt_sub_color));
                    holder.ivAlert.setVisibility(View.GONE);
                }
                holder.tvWarranty.setVisibility(View.VISIBLE);
                holder.tvWarranty.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.warranty_expiry_date) + ": "+expiryDate);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        else {
            holder.tvWarranty.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(equArrayModel.getImage())) {
            holder.equ_img_view.setVisibility(View.GONE);
            holder.img_equipment.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(App_preference.getSharedprefInstance().getBaseURL() + equArrayModel.getImage())
                    .thumbnail(Glide.with(mContext).load(R.raw.loader_eot)).placeholder(R.drawable.app_logo2).into(holder.img_equipment);
        } else {
            holder.equ_img_view.setVisibility(View.VISIBLE);
            holder.equ_img_view.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.Equipment_img));
            holder.img_equipment.setVisibility(View.GONE);
        }

        if (!TextUtils.isEmpty(equArrayModel.getStatus()) || equArrayModel.getAttachments() != null && equArrayModel.getAttachments().size() > 0) {
            holder.add_action.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.action));
        } else {
            holder.add_action.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.action));
        }

        try {
            if (equArrayModel.getStatus() != null) {
                if (equArrayModel.getStatus().equals("")) {
                    holder.ll_status.setVisibility(View.GONE);
                } else if (equArrayModel.getStatus().equals("0")) {
                    holder.ll_status.setVisibility(View.GONE);
                } else {
                    holder.ll_status.setVisibility(View.VISIBLE);
                    holder.tv_status.setText(getCurrentStatusNameById(equArrayModel.getStatus()));

                    if(equArrayModel.getStatusUpdateDate() != null&&!equArrayModel.getStatusUpdateDate().isEmpty()){
                        holder.tv_date.setText(AppUtility.getDate(Long.parseLong(equArrayModel.getStatusUpdateDate()), AppConstant.DATE_FORMAT));
                        holder.tv_date.setVisibility(View.VISIBLE);
                    }
                    else
                        holder.tv_date.setVisibility(View.GONE);

                    if (getCurrentStatusNameById(equArrayModel.getStatus()).trim().equalsIgnoreCase("Discarded")) {
                        holder.ll_status.setBackgroundColor(ContextCompat.getColor(mContext, R.color.light_red));
                    } else {
                        holder.ll_status.setBackgroundColor(ContextCompat.getColor(mContext, R.color.dark_yellow));
                    }
                }
            } else {
                holder.ll_status.setVisibility(View.GONE);
            }
        } catch (Exception ex) {
            holder.ll_status.setVisibility(View.GONE);
        }
    }

    private String getStatusNameById(String statusId) {
        String statusName = "";
        if (equipmentStatus != null) {
            for (EquipmentStatus equipmentStatus : equipmentStatus)
                if (equipmentStatus.getEsId().equals(statusId)) {
                    statusName = equipmentStatus.getStatusText();
                    break;
                }
        }
        return statusName;
    }

    @Override
    public int getItemCount() {
        return this.list == null ? 0 : this.list.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        CardView client_item_constraint;
        TextView tv_item_name, tv_address;
        AppCompatImageView img_equipment;
        TextView equ_img_view;
        AppCompatTextView tv_model, tv_serial, tv_model_label, tv_serial_label,tv_status;//, tv_des;// tv_status, , tv_details ,, tv_remark
        TextView view_details, add_action,tv_date;
        LinearLayout main_layout;
        TextView tvWarranty;
        ImageView ivAlert, img_show_remark;
        LinearLayout ll_status;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            client_item_constraint = itemView.findViewById(R.id.client_item_constraint);
            main_layout = itemView.findViewById(R.id.main_layout);
            tv_item_name = itemView.findViewById(R.id.tv_item_name);
            tv_address = itemView.findViewById(R.id.tv_address);
            img_equipment = itemView.findViewById(R.id.img_equipment);
            tv_model = itemView.findViewById(R.id.tv_model);
            tv_serial = itemView.findViewById(R.id.tv_serial);
            tv_model_label = itemView.findViewById(R.id.tv_model_label);
            tv_serial_label = itemView.findViewById(R.id.tv_serial_label);
            view_details = itemView.findViewById(R.id.view_details);
            view_details.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.view_details));
            add_action = itemView.findViewById(R.id.add_action);
            equ_img_view = itemView.findViewById(R.id.equ_img_view);
            tv_status = itemView.findViewById(R.id.tv_status);
            ll_status = itemView.findViewById(R.id.ll_status);
            tv_date = itemView.findViewById(R.id.tv_date);
            tvWarranty = itemView.findViewById(R.id.tvWarranty);
            ivAlert = itemView.findViewById(R.id.ivAlert);
            img_show_remark = itemView.findViewById(R.id.img_show_remark);

        }


    }
    void setRemarkActivity(int position, boolean isAction) {
        if (onEquipmentSelection != null) {
            list.get(position).setIsPart("1");
            onEquipmentSelection.onEquipmentSelected(position, list.get(position),isAction);
        } else {
            String strEqu = new Gson().toJson(list.get(position));
            mContext.startActivity(new Intent(mContext, JobEquRemarkRemarkActivity.class).putExtra("equipment", strEqu));
        }
    }
}
