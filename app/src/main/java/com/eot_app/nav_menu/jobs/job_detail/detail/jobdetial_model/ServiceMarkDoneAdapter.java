package com.eot_app.nav_menu.jobs.job_detail.detail.jobdetial_model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eot_app.R;
import com.eot_app.nav_menu.jobs.job_db.IsMarkDoneWithJtid;

import java.util.List;

public class ServiceMarkDoneAdapter extends RecyclerView.Adapter<ServiceMarkDoneAdapter.MyViewHolder> {

    List<IsMarkDoneWithJtid> list;
    Context context;

    public ServiceMarkDoneAdapter(List<IsMarkDoneWithJtid> list, Context context) {
        this.list = list;
        this.context = context;
    }


    public void updatList(List<IsMarkDoneWithJtid> list){
        this.list = list;
        notifyDataSetChanged();
    }
    @Override
    public MyViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_item_service_mark_done,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder( MyViewHolder holder, int position) {
        if(list.get(position).getStatus().equals("0")){
            holder.img_markDone.setVisibility(View.INVISIBLE);
        }else {
            holder.img_markDone.setVisibility(View.VISIBLE);
        }
        holder.txt_title_service.setText(list.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView txt_title_service;
        ImageView img_markDone;
        public MyViewHolder( View itemView) {
            super(itemView);
            txt_title_service = itemView.findViewById(R.id.txt_title_service);
            img_markDone = itemView.findViewById(R.id.img_markDone);
        }
    }
}
