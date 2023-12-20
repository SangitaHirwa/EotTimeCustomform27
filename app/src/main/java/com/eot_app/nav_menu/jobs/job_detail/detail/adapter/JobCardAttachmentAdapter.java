package com.eot_app.nav_menu.jobs.job_detail.detail.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eot_app.R;
import com.eot_app.nav_menu.jobs.job_detail.detail.DialogJobCardDocuments;
import com.eot_app.nav_menu.jobs.job_detail.detail.jobdetial_model.JobCardAttachmentModel;

import java.util.ArrayList;
import java.util.List;

public class JobCardAttachmentAdapter extends RecyclerView.Adapter<JobCardAttachmentAdapter.MyViewHolder>{

    List<JobCardAttachmentModel> list = new ArrayList<>();
    Listener listener;

    public JobCardAttachmentAdapter(Listener listener) {
        this.listener = listener;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rv_job_cart_attachment_item,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        JobCardAttachmentModel item = list.get(position);
        holder.checkBox.setText(item.getName());
        holder.checkBox.setChecked(item.getChecked());

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                item.setChecked(isChecked);
                listener.cbClickListener(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void updateList (List<JobCardAttachmentModel> list){
        this.list = list;
        notifyDataSetChanged();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
CheckBox checkBox;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.cb_attachment);
        }
    }

    public interface Listener {
        public void  cbClickListener(JobCardAttachmentModel jobCardAttachmentModel);
    }
}
