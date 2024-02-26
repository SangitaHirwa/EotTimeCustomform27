package com.eot_app.nav_menu.drag_and_drop_functinality;

import android.annotation.SuppressLint;
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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.eot_app.R;

import java.util.ArrayList;
import java.util.List;

public class Drop_Item_Adapter extends RecyclerView.Adapter<Drop_Item_ViewHolder> {
    Context context;
    List<MapItemModel> list1 = new ArrayList<>();
     RemoveItem removeItem;
    @SuppressLint("NotifyDataSetChanged")
    public Drop_Item_Adapter(Context context, List<MapItemModel> list, RemoveItem removeItem1) {
        this.context = context;
        this.list1 = list;
        removeItem =  removeItem1;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Drop_Item_ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.drop_item_adapter_view, viewGroup, false);
        return new Drop_Item_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Drop_Item_ViewHolder holder, int position) {
        MapItemModel itemModel = list1.get(position);
        holder.item.setText(itemModel.getName());
        holder.equipment.setText(itemModel.getName());

        holder.linearLayoutItemList.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                    removeItem.removeSelectedEqu(itemModel);
                    ClipData.Item item = new ClipData.Item(""+position);
                    ClipData dragData = new ClipData("dragData", new String[]{ClipDescription.MIMETYPE_TEXT_PLAIN}, item);
                    View.DragShadowBuilder myShadow = new View.DragShadowBuilder(v);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    v.startDragAndDrop(dragData, myShadow,  holder.cardView, 0);
                }
                return true;

            }

        });




    }


    @Override
    public int getItemCount() {
        return list1.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setList(List<MapItemModel> list){
        this.list1=list;
        notifyDataSetChanged();
    }
    public interface RemoveItem{
        void removeSelectedEqu(MapItemModel itemModel);
    }
}
class Drop_Item_ViewHolder extends RecyclerView.ViewHolder{
      LinearLayout linearLayoutItemList;
      RelativeLayout relativeLayout;
      TextView item,equipment;
      CardView cardView;
      ImageView cross,itemremove;
    public Drop_Item_ViewHolder(@NonNull View itemView) {
        super(itemView);
        linearLayoutItemList =itemView.findViewById(R.id.linear_layout_itemList);
        item = itemView.findViewById(R.id.tv_item);
        equipment = itemView.findViewById(R.id.tv_droped_item);
        cardView= itemView.findViewById(R.id.card_layout_dragdrop);
       itemremove = itemView.findViewById(R.id.equipment_close_icon);
    }
}
