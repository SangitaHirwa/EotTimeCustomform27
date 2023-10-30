package com.eot_app.nav_menu.jobs.job_detail.detail.filedworker_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.eot_app.R;
import com.eot_app.nav_menu.jobs.job_db.Job;
import com.eot_app.utility.db.AppDataBase;
import com.eot_app.utility.settings.setting_db.FieldWorker;

import java.util.List;

public class Filedworker_List_Adapter extends RecyclerView.Adapter<FiledworkerViewHolder> {
      List<String> list;
      Job job;
      Context context;
    public Filedworker_List_Adapter(Job mParam2, List<String> list, Context context) {
        this.job=mParam2;
        this.list=list;
        this.context=context;
    }

    @NonNull
    @Override
    public FiledworkerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.filedworker_list_adapter, parent, false);
        return new FiledworkerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FiledworkerViewHolder holder, int position) {
        String fw_id = list.get(position);
        if(!fw_id.equals("")){
            FieldWorker fieldWorker = AppDataBase.getInMemoryDatabase(context).fieldWorkerModel().getFieldWorkerByID(fw_id);

            if (fieldWorker!=null){
                if(fieldWorker.getUsrId().equals(job.getIsLeader())){
                    holder.icon_for_leader.setVisibility(View.VISIBLE);
                    holder.text_for_fw.setText(fieldWorker.getName());
                }
                else {
                    holder.icon_for_leader.setVisibility(View.GONE);
                    holder.text_for_fw.setText(fieldWorker.getName());
                }
            }
        }

    }

    @Override
    public int getItemCount() {

        return list.size();

    }
}
class FiledworkerViewHolder extends RecyclerView.ViewHolder {
    TextView text_for_fw;
    ImageView icon_for_leader;
    public FiledworkerViewHolder(@NonNull View itemView) {
        super(itemView);
        text_for_fw = itemView.findViewById(R.id.text_for_fw);
        icon_for_leader = itemView.findViewById(R.id.icone_for_leader);
    }
}