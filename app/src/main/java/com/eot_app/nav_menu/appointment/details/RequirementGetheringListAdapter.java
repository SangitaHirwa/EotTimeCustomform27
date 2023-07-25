package com.eot_app.nav_menu.appointment.details;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.eot_app.R;
import com.eot_app.nav_menu.appointment.appointment_model.AppintmentItemDataModel;
import com.eot_app.nav_menu.jobs.job_detail.addinvoiveitem2pkg.model.InvoiceItemDataModel;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.zip.Inflater;

public class RequirementGetheringListAdapter extends RecyclerView.Adapter<RequirementGetheringViewHolder> {
    Context context;
    List<AppintmentItemDataModel> itemDataModels;
    AppintmentItemDataModel ItemDataModel;


    RequirementGetheringListAdapter ( List<AppintmentItemDataModel> itemDataModels)
    {
        this.itemDataModels=itemDataModels;
    }

    @NonNull
    @NotNull
    @Override
    public RequirementGetheringViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.requirment_gethering_list_view, parent, false);
        return new RequirementGetheringViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RequirementGetheringViewHolder holder, int position) {
        ItemDataModel = itemDataModels.get(position);
        holder.service.setText(ItemDataModel.getInm());
        holder.desOfRequirment.setText(ItemDataModel.getDes());
        holder.count.setText("X"+ItemDataModel.getQty());
    }

    @Override
    public int getItemCount() {
            if(itemDataModels.size()==0)
                return 0;
            else return itemDataModels.size();
    }
}
class RequirementGetheringViewHolder extends RecyclerView.ViewHolder{
    ImageView deleteItem;
    TextView work,service,desOfRequirment,count;
    ConstraintLayout item_req_geth;
    public RequirementGetheringViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);
        deleteItem=itemView.findViewById(R.id.delete_item_icon);
        work=itemView.findViewById(R.id.work_tv);
        service=itemView.findViewById(R.id.service_tv);
        desOfRequirment=itemView.findViewById(R.id.desofRequirment);
        count=itemView.findViewById(R.id.count);
        item_req_geth = itemView.findViewById(R.id.item_req_geth);


    }
}