package com.eot_app.nav_menu.drag_and_drop_functinality;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.ClipData;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.eot_app.R;
import com.eot_app.databinding.ActivityDropItemOnMapBinding;
import com.eot_app.utility.AppUtility;
import com.eot_app.utility.CompressImageInBack;
import com.eot_app.utility.util_interfaces.OnImageCompressed;

import java.util.ArrayList;
import java.util.List;

public class Drop_Item_On_Map_Activity extends AppCompatActivity {
     ActivityDropItemOnMapBinding binding;
    CompressImageInBack compressImageInBack = null;
    Drop_Item_Adapter item_adapter;
    String bitmapString;
    boolean listshow =false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_drop_item_on_map);

        Uri uri = Uri.parse(getIntent().getStringExtra("uri"));
        imageEditing(uri);
        List <String> list=new ArrayList<>();
        list.add("equipment Icon 1");
        list.add("equipment Icon 2");
        list.add("equipment Icon 3");
        list.add("equipment Icon 4");
        list.add("equipment Icon 5");
         item_adapter = new Drop_Item_Adapter(this);
        item_adapter.setList(list);
         binding.rvItemList.setAdapter(item_adapter);
         binding.itemShowHide.setOnClickListener(v -> {
             if(!listshow){
                 listshow = true;
                 binding.rvItemList.setVisibility(View.VISIBLE);
             }else {
                 listshow = false;
             binding.rvItemList.setVisibility(View.GONE);}
         });


         binding.imageForDropItem.setOnDragListener(new View.OnDragListener() {
             @Override
             public boolean onDrag(View v, DragEvent event) {
                 int action = event.getAction();
                 switch (action) {
                     case DragEvent.ACTION_DROP:
                         ClipData.Item item = event.getClipData().getItemAt(0);
                         int position = Integer.parseInt(item.getText().toString());

                         if (position >= 0 && position < list.size()) {
                             list.remove(position);
                             item_adapter.notifyItemRemoved(position);

                             View draggedView = (View) event.getLocalState();
                             if (draggedView.getVisibility() != View.VISIBLE) {
                                 draggedView.setVisibility(View.VISIBLE);
                             }

                             // Calculate new position based on drop location
                             float newX = event.getX() - draggedView.getWidth() / 2;
                             float newY = event.getY() - draggedView.getHeight() / 2;

                             // Set new position for the dragged view
                             draggedView.setX(newX);
                             draggedView.setY(newY);

                            /* parent = (ViewGroup) draggedView.getParent();
                             if (parent != null) {
                                 parent.removeView(draggedView);
                             }

                             // Add the dragged view to the new parent (dropImage)
                             dropimagecontainer.addView(draggedView);

                             ImageView itemremove = draggedView.findViewById(R.id.itemremove);
                             itemremove.setVisibility(View.VISIBLE);
                             itemremove.setOnClickListener(view -> {
                                 dropimagecontainer.removeView(draggedView);
                                 TextView viewById = draggedView.findViewById(R.id.lvItem);
                                 draggedViewText = viewById.getText().toString();
                                 list.add(draggedViewText);
                                 dragDropAdapter.updateList(list);
                             });*/
                         }
                 }
                 return true;
             }
         });



    }
    private void imageEditing(Uri uri1) {

//        img_doc.setImageURI(uri);
        compressImageInBack = new CompressImageInBack(this, new OnImageCompressed() {
            @Override
            public void onImageCompressed(Bitmap bitmap) {
                String savedImagePath = compressImageInBack.getSavedImagePath();
                if (bitmap != null) {
                    bitmapString= AppUtility.BitMapToString(bitmap);
                    binding.imageForDropItem.setImageBitmap(bitmap);
                }
            }
        }, uri1);
        compressImageInBack.setSaveBitmap(true);
        //  compressImageInBack.execute(uri);
        compressImageInBack.compressImageInBckg();



    }
}