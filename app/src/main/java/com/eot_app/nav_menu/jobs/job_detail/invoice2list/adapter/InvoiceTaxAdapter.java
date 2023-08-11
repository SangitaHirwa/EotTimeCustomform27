package com.eot_app.nav_menu.jobs.job_detail.invoice2list.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eot_app.R;
import com.eot_app.nav_menu.jobs.job_detail.invoice2list.itemlist_model.TaxData;
import com.eot_app.utility.AppUtility;

import java.util.List;

public class InvoiceTaxAdapter extends  RecyclerView.Adapter<InvoiceTaxAdapter.MyViewHolder>{

    Context context;
    List<TaxData> list;
    public InvoiceTaxAdapter(Context context, List<TaxData> list){

        this.context = context;
        this.list = list;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.rv_invoice_multiple_tax_item,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.txt_taxLabel.setText("Tax : "+list.get(position).getLabel()+" ("+list.get(position).getRate()+"%)");
        holder.txt_taxAmt.setText(AppUtility.getRoundoff_amount(""+list.get(position).getTaxAmount()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<TaxData>list){

        this.list = list;
        notifyDataSetChanged();
    }

    public class  MyViewHolder extends RecyclerView.ViewHolder{

        TextView txt_taxLabel, txt_taxAmt;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_taxLabel = itemView.findViewById(R.id.txt_lbl_tax);
            txt_taxAmt = itemView.findViewById(R.id.txt_tax);
        }
    }
}
