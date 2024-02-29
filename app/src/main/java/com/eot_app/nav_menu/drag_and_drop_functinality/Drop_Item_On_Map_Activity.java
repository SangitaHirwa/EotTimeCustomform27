package com.eot_app.nav_menu.drag_and_drop_functinality;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
    float minX = 0;
    float minY = 0;
    float maxX = 0;
    float maxY = 0;
    View cardViewParentView,equipmentDropedRl,linearItemListView;
    ViewGroup imageviewContainer;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_drop_item_on_map);
       imageviewContainer = (ViewGroup) binding.imageForDropItem.getParent();
        Uri uri = Uri.parse(getIntent().getStringExtra("uri"));
        imageEditing(uri);
        List<String> list = new ArrayList<>();
        list.add("equipment Icon 1");
        list.add("equipment Icon 2");
        list.add("equipment Icon 3");
        list.add("equipment Icon 4");
        list.add("equipment Icon 5");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.rvItemList.setLayoutManager(linearLayoutManager);
        item_adapter = new Drop_Item_Adapter(this, list);
        binding.rvItemList.setAdapter(item_adapter);
        item_adapter.setList(list);
        binding.itemShowHide.setOnClickListener(v -> {
            if (!listshow) {
                listshow = true;
                binding.rvItemList.setVisibility(View.VISIBLE);
            } else {
                listshow = false;
                binding.rvItemList.setVisibility(View.GONE);
            }
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
                            float dropX = event.getX();
                            float dropY = event.getY();

                            int[] location = new int[2];
                            binding.imageForDropItem.getLocationOnScreen(location);
                            int leftX = location[0];   // X coordinate of the left edge
                            int topY = location[1];    // Y coordinate of the top edge
                            int rightX = leftX + binding.imageForDropItem.getWidth();  // X coordinate of the right edge
                            int bottomY = topY + binding.imageForDropItem.getHeight(); // Y coordinate of the bottom edge

                                   // Calculate minX, minY, maxX, maxY
                            minX = leftX;
                             minY = topY;
                             maxX = rightX;
                            maxY = bottomY;

                            // Validate the drop area (customize this based on your requirements)
                            if (isValidDropArea(dropX, dropY)) {
                                list.remove(position);
                                item_adapter.notifyItemRemoved(position);

                                // ... (rest of your code)
                            } else {
                                // Handle invalid drop area (e.g., show a message)
                                Toast.makeText(getApplicationContext(), "Invalid Drop Area", Toast.LENGTH_SHORT).show();
                            }


                             cardViewParentView = (View) event.getLocalState();
                             equipmentDropedRl = cardViewParentView.findViewById(R.id.relative_layout_equipment_item);
                             linearItemListView = cardViewParentView.findViewById(R.id.linear_layout_itemList);
                             linearItemListView.setVisibility(View.GONE);
                            equipmentDropedRl.setVisibility(View.VISIBLE);

                            ViewGroup parent = (ViewGroup) equipmentDropedRl.getParent();
                            if (parent != null) parent.removeView(equipmentDropedRl);
                            // Add the dragged view to the new parent (dropImage)
                            imageviewContainer.addView(equipmentDropedRl);
                            // Calculate new position based on drop location
                            float newX = event.getX() - equipmentDropedRl.getWidth() / 2;
                            float newY = event.getY() - equipmentDropedRl.getHeight() / 2;

                            // Set new position for the dragged view
                            equipmentDropedRl.setX(newX);
                            equipmentDropedRl.setY(newY);

                             ImageView itemremove = equipmentDropedRl.findViewById(R.id.equipment_close_icon);
                              TextView equipmentText = equipmentDropedRl.findViewById(R.id.tv_droped_item);
                             itemremove.setVisibility(View.VISIBLE);
                             itemremove.setOnClickListener(view -> {
                                 imageviewContainer.removeView(equipmentDropedRl);
                                String  removeDropedText = equipmentText.getText().toString();
                                 list.add(removeDropedText);
                                 item_adapter.setList(list);
                             });
                            equipmentDropedRl.setOnTouchListener(new View.OnTouchListener() {
                                private float lastX, lastY;
                                private int parentWidth, parentHeight;

                                @Override
                                public boolean onTouch(View v, MotionEvent event) {
                                    switch (event.getAction()) {
                                        case MotionEvent.ACTION_DOWN:
                                            // Save the initial touch coordinates
                                            lastX = event.getRawX();
                                            lastY = event.getRawY();

                                            // Get the dimensions of the parent view
                                            ViewParent parent = v.getParent();
                                            if (parent instanceof View) {
                                                View parentView = (View) parent;
                                                parentWidth = parentView.getWidth();
                                                parentHeight = parentView.getHeight();
                                            }
                                            break;

                                        case MotionEvent.ACTION_MOVE:
                                            // Calculate the distance moved
                                            float deltaX = event.getRawX() - lastX;
                                            float deltaY = event.getRawY() - lastY;

                                            // Update the position of the dragged view within the bounds
                                            float newX = v.getX() + deltaX;
                                            float newY = v.getY() + deltaY;

                                            // Ensure the view stays within the bounds of the parent
                                            newX = Math.max(0, Math.min(newX, parentWidth - v.getWidth()));
                                            newY = Math.max(0, Math.min(newY, parentHeight - v.getHeight()));

                                            v.setX(newX);
                                            v.setY(newY);

                                            // Save the current touch coordinates for the next move
                                            lastX = event.getRawX();
                                            lastY = event.getRawY();
                                            break;

                                        default:
                                            return false;
                                    }
                                    return true;
                                }
                            });


                        }
                }
                return true;
            }
        });
    }
    private boolean isValidDropArea(float dropX, float dropY) {
        // Customize this method based on your drop area validation logic
        // Example: Allow drop only in a specific rectangular area
        return dropX >= minX && dropX <= maxX && dropY >= minY && dropY <= maxY;
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