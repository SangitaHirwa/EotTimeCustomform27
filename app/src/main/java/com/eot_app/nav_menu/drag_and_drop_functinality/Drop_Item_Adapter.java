package com.eot_app.nav_menu.drag_and_drop_functinality;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.os.Build;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eot_app.R;

import java.util.ArrayList;
import java.util.List;

public class Drop_Item_Adapter extends RecyclerView.Adapter<Drop_Item_ViewHolder> {
    Context context;
    List<String> list1=new ArrayList<>();

    public Drop_Item_Adapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public Drop_Item_ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.drop_item_adapter_view, viewGroup, false);
        return new Drop_Item_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Drop_Item_ViewHolder holder, int position) {
        String itemString = list1.get(position);
        holder.item.setText(itemString);
        holder.equipment.setText(itemString);
        holder.linearLayoutItemList.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                    ClipData.Item item = new ClipData.Item("image1");
                    ClipData dragData = new ClipData("dragData", new String[]{ClipDescription.MIMETYPE_TEXT_PLAIN}, item);
                    View.DragShadowBuilder myShadow = new View.DragShadowBuilder(v);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    v.startDragAndDrop(dragData, myShadow, null, 0);
                }
                return true;

            }
        });
    }

    @Override
    public int getItemCount() {
        return list1.size();
    }

    public void setList(List<String> list){
        this.list1=list;
    }
}
class Drop_Item_ViewHolder extends RecyclerView.ViewHolder{
      LinearLayout linearLayoutItemList;
      RelativeLayout relativeLayout;
      TextView item,equipment;
      ImageView cross;
    public Drop_Item_ViewHolder(@NonNull View itemView) {
        super(itemView);
        linearLayoutItemList =itemView.findViewById(R.id.linear_layout_itemList);
        item = itemView.findViewById(R.id.tv_item);
        equipment = itemView.findViewById(R.id.tv_droped_item);
    }
}
