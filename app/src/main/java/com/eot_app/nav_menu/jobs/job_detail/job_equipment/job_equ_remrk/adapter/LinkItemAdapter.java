package com.eot_app.nav_menu.jobs.job_detail.job_equipment.job_equ_remrk.adapter;

import android.content.Context;
import android.renderscript.Float3;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eot_app.R;
import com.eot_app.nav_menu.jobs.job_detail.addinvoiveitem2pkg.model.InvoiceItemDataModel;
import com.eot_app.nav_menu.jobs.job_detail.form_form.get_qus_list.QuestionListAdapter;
import com.eot_app.utility.AppConstant;
import com.eot_app.utility.language_support.LanguageController;

import org.checkerframework.checker.units.qual.A;

import java.util.List;

public class LinkItemAdapter extends RecyclerView.Adapter<LinkItemAdapter.MyViewHolder> {
    Context context;
    List<InvoiceItemDataModel> list ;
    ClickListiner clickListiner;
    public LinkItemAdapter(List<InvoiceItemDataModel> list ,Context context, ClickListiner clickListiner) {
        this.context = context;
        this.list =list;
        this.clickListiner = clickListiner;
    }

    public void updateList(List<InvoiceItemDataModel> list){
        this.list =list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_link_item,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txt_item_name.setText(list.get(position).getInm());
        holder.txt_item_quantity.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.qty)+" : "+list.get(position).getQty());
        holder.txt_item_model_no.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.equipment_model)+" : "+list.get(position).getPno());
        holder.txt_item_brand_nm.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.brand)+" : "+list.get(position).getBrandNm());

        holder.txt_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListiner.onClick(list.get(position).getIjmmId(),list.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class  MyViewHolder extends RecyclerView.ViewHolder {
        TextView txt_item_name, txt_item_quantity, txt_item_model_no, txt_item_brand_nm, txt_link;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_item_name = itemView.findViewById(R.id.txt_item_name);
            txt_item_quantity = itemView.findViewById(R.id.txt_item_quantity);
            txt_item_model_no = itemView.findViewById(R.id.txt_item_model_no);
            txt_item_brand_nm = itemView.findViewById(R.id.txt_item_brand_nm);
            txt_link = itemView.findViewById(R.id.txt_link);
        }
    }
    public interface ClickListiner{
        public void onClick(String ijmmId, InvoiceItemDataModel invoiceItemDataModel);
    }
}
