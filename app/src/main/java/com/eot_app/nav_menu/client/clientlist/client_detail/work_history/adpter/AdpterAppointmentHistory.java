package com.eot_app.nav_menu.client.clientlist.client_detail.work_history.adpter;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.eot_app.R;
import com.eot_app.nav_menu.appointment.dbappointment.Appointment;
import com.eot_app.nav_menu.client.clientlist.client_detail.work_history.ClientWorkHistoryList;
import com.eot_app.nav_menu.client.clientlist.client_detail.work_history.LoadMoreItem;
import com.eot_app.utility.AppConstant;
import com.eot_app.utility.AppUtility;
import com.eot_app.utility.App_preference;
import com.eot_app.utility.EotApp;
import com.eot_app.utility.language_support.LanguageController;

import java.util.ArrayList;
import java.util.List;

public class AdpterAppointmentHistory extends RecyclerView.Adapter<AdpterAppointmentHistory.MyViewHolder> {
    final Context mContext;
    LoadMoreItem loadMoreItem;
    private OnAppointmentSelection onAppointmentSelection;
    private List<Appointment> list;
    boolean isShowSiteName = false;

    public AdpterAppointmentHistory(Context mContext) {
        this.mContext = mContext;
        this.list = new ArrayList<>();
    }

    public void setOnAuditSelection(OnAppointmentSelection onAppointmentSelection) {
        this.onAppointmentSelection = onAppointmentSelection;
    }

    public void setLoadMoreItem(LoadMoreItem loadMoreItem) {
        this.loadMoreItem = loadMoreItem;
    }

    public void setFromShowSiteName(boolean isShowSiteName) {
        this.isShowSiteName = isShowSiteName;
    }

    public void setList(List<Appointment> list) {
        if (this.list == null)
            this.list = new ArrayList<>();
        this.list.addAll(list);
        notifyDataSetChanged();

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int type) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.work_history_aduit_list, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (position == getItemCount() - 1) holder.blank_view.setVisibility(View.VISIBLE);
        else holder.blank_view.setVisibility(View.GONE);
        String formate = AppUtility.dateTimeByAmPmFormate("EEE, dd MMM yyyy ,hh:mm ,a", "EEE, dd MMM yyyy ,HH:mm");
        String today_date = AppUtility.getDateByFormat(formate);
        String[] today_dt = today_date.split(",");
        String to_day = today_dt[1];


        String temp_date = "", cr_date;

        switchDefaultColor(holder, EotApp.getAppinstance().getResources().getColor(R.color.body_font_color));
        if (list.get(position).getSchdlStart() != null && !list.get(position).getSchdlStart().equals("")) {
            String[] date = AppUtility.getFormatedTime(list.get(position).getSchdlStart());
            cr_date = date[0];
            String[] date_separated = cr_date.split(",");
            Spannable txtFrst;

            if (to_day.contains(date_separated[1])) {
                txtFrst = new SpannableString("Today" + ",");
            } else {
                txtFrst = new SpannableString(date_separated[0] + ",");
            }

            txtFrst.setSpan(new ForegroundColorSpan(Color.parseColor("#00848d")), 0, txtFrst.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.job_time.setText(date[1]);
            try {
                if (App_preference.getSharedprefInstance().getLoginRes().getIs24hrFormatEnable() != null
                        && App_preference.getSharedprefInstance().getLoginRes().getIs24hrFormatEnable().equals("0"))
                    holder.job_am_pm.setText(date[2]);
            } catch (Exception e) {
                e.getMessage();
            }

            if (position < list.size())
                if (position == 0 || (list.get(position - 1).getSchdlStart() != null && list.get(position - 1).getSchdlStart().equals(""))) {
                    holder.txt_date.setText(txtFrst);
                    holder.txt_date.append(date_separated[1]);
                    holder.date_layout.setVisibility(View.VISIBLE);

                } else if (list.get(position - 1).getSchdlStart() != null && !list.get(position - 1).getSchdlStart().equals("")) {
                    String[] date2 = AppUtility.getFormatedTime(list.get(position - 1).getSchdlStart());
                    temp_date = date2[0];
                    if (cr_date.equals(temp_date)) {
                        holder.date_layout.setVisibility(View.GONE);
                    } else {
                        holder.txt_date.setText(txtFrst);
                        holder.txt_date.append(date_separated[1]);
                        holder.date_layout.setVisibility(View.VISIBLE);
                    }
                }
        } else {
            holder.date_layout.setVisibility(View.GONE);
            holder.txt_date.setText("");
            holder.job_time.setText("");
            holder.job_am_pm.setText("");
        }
        String label = list.get(position).getLabel();
        String Nm = list.get(position).getNm();

        if (label != null && Nm != null) {
            if (label != null && Nm != null) {
                holder.label.setText(label + " - " + Nm);
            }
        }


        /** show site name from setting */
        if (App_preference.getSharedprefInstance().getSiteNameShowInSetting()) {
            holder.tv_site_name.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(list.get(position).getSnm()))
                holder.tv_site_name.setText(list.get(position).getSnm());
            else holder.tv_site_name.setText("");
        } else holder.tv_site_name.setVisibility(View.GONE);


        String full_address = list.get(position).getAdr() + ", " + list.get(position).getCity();
        holder.address.setText(full_address);


        if (list.get(position).getStatus() != null)
            try {
                setViewByAppointmentStatus(holder, Integer.parseInt(list.get(position).getStatus()));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

        if (position == list.size() - 1) {
            if (loadMoreItem != null)
                loadMoreItem.onLoadMoreItem(list.size(), ClientWorkHistoryList.FragmentTypes.APPOINTMENT);
        }

    }

    private void setViewByAppointmentStatus(MyViewHolder holder, int status) {
        if (status == 9) {
            holder.status.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.done));
            holder.status_constraints.setBackgroundResource(R.color.white);
            holder.status_img.setImageResource(R.drawable.ic_complete_task);
        } else if (status == 12) {
            holder.status.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.status_new));
            holder.status_constraints.setBackgroundResource(R.color.white);
            holder.status_img.setImageResource(R.drawable.ic_new_task);
        }


    }

    private void switchDefaultColor(MyViewHolder holder, int color) {
        holder.status.setTextColor(color);
        holder.job_time.setTextColor(color);
        holder.job_am_pm.setTextColor(color);
        holder.status.setTextColor(color);
    }


    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public interface OnAppointmentSelection {
        void onAppointmentSelected(Appointment appointment);

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_date, job_time, job_am_pm, label, address, status, tv_site_name;
        ImageView status_img;
        LinearLayout date_layout;
        ConstraintLayout status_constraints;
        View blank_view;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            blank_view = itemView.findViewById(R.id.blank_view);
            txt_date = itemView.findViewById(R.id.txt_date);
            job_time = itemView.findViewById(R.id.job_time);
            job_am_pm = itemView.findViewById(R.id.job_am_pm);
            label = itemView.findViewById(R.id.label);
            address = itemView.findViewById(R.id.address);
            status_img = itemView.findViewById(R.id.status_img);
            status = itemView.findViewById(R.id.status);
            status_constraints = itemView.findViewById(R.id.status_constraints);
            date_layout = itemView.findViewById(R.id.date_layout);
            tv_site_name = itemView.findViewById(R.id.tv_site_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onAppointmentSelection != null)
                        try {
                            onAppointmentSelection.onAppointmentSelected(list.get(getBindingAdapterPosition()));
                        } catch (ArrayIndexOutOfBoundsException e) {
                            e.printStackTrace();
                        }
                }
            });
        }
    }
}
