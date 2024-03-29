package com.eot_app.nav_menu.drag_and_drop_functinality;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.eot_app.R;
import com.eot_app.databinding.ActivityDropItemOnMapBinding;
import com.eot_app.nav_menu.jobs.job_detail.invoice.invoice_detail_pkg.inv_detail_model.Inv_Res_Model;
import com.eot_app.utility.AppUtility;
import com.eot_app.utility.CompressImageInBack;
import com.eot_app.utility.util_interfaces.OnImageCompressed;
import com.github.chrisbanes.photoview.OnScaleChangedListener;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;

public class Drop_Item_On_Map_Activity extends AppCompatActivity implements Drop_Item_Adapter.RemoveItem, View.OnClickListener, View.OnDragListener {
    ActivityDropItemOnMapBinding binding;
    CompressImageInBack compressImageInBack = null;
    Drop_Item_Adapter item_adapter;
    String bitmapString;
    boolean listshow = false;
    float minX = 0;
    float minY = 0;
    float maxX = 0;
    float maxY = 0;
    List<MapItemModel> availableItems = new ArrayList<>();
    List<MapItemModel> consumedItems = new ArrayList<>();
    private static View cardViewParentView;
    View equipmentDropedRl, linearItemListView;
    ViewGroup imageviewContainer;
    int position;
    private float xCoOrdinate, yCoOrdinate;
    ViewGroup equCardViewParent;
    SparseArray<View> dropItemList = new SparseArray<>();
    ViewManager viewManager = new ViewManager();
    int viewId = 1;
    boolean isMovingView = false;
    private float lastX, lastY;
    private int parentWidth, parentHeight;
    MapItemModel droppedModel;
    MapItemModel itemModel1;
    private float dX, dY;
    ImageView itemremove;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_drop_item_on_map);
        imageviewContainer = (ViewGroup) binding.imageForDropItem.getParent();
        Uri uri = Uri.parse(getIntent().getStringExtra("uri"));
        imageEditing(uri);
        MapItemModel itemModel = new MapItemModel(300, 300, "AC", "widsd", "", "1", true);
        MapItemModel itemModel1 = new MapItemModel(0, 0, "AC1", "widsd", "", "2", true);
        MapItemModel itemModel2 = new MapItemModel(0, 0, "AC2", "widsd", "", "3", true);
        MapItemModel itemModel3 = new MapItemModel(0, 0, "AC3", "widsd", "", "4", true);
        availableItems.add(itemModel);
        availableItems.add(itemModel1);
        availableItems.add(itemModel2);
        availableItems.add(itemModel3);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        binding.rvItemList.setLayoutManager(linearLayoutManager);
        item_adapter = new Drop_Item_Adapter(this, availableItems, this);
        binding.rvItemList.setAdapter(item_adapter);
        binding.itemShowHide.setOnClickListener(v -> {
            if (!listshow) {
                listshow = true;
                binding.rvItemList.setVisibility(View.VISIBLE);
            } else {
                listshow = false;
                binding.rvItemList.setVisibility(View.GONE);
            }
        });
        setEquipmentOnImage();
        binding.tvBack.setOnClickListener(this);
        binding.imageForDropItem.setOnDragListener(this);
//
//
//       /* binding.imageForDropItem.setOnDragListener(new View.OnDragListener() {
//            @SuppressLint("NotifyDataSetChanged")
//            @Override
//            public boolean onDrag(View v, DragEvent event) {
//                int action = event.getAction();
//                switch (action) {
//                    case DragEvent.ACTION_DROP:
//                        ClipData.Item item = event.getClipData().getItemAt(0);
//                        position = Integer.parseInt(item.getText().toString());
//
////                        if (position >= 0 && position < list.size()) {
//
//                            float dropX = event.getX();
//                            float dropY = event.getY();
//
//                            int[] location = new int[2];
//                            binding.imageForDropItem.getLocationOnScreen(location);
//                            int leftX = location[0];   // X coordinate of the left edge
//                            int topY = location[1];    // Y coordinate of the top edge
//                            int rightX = leftX + binding.imageForDropItem.getWidth();  // X coordinate of the right edge
//                            int bottomY = topY + binding.imageForDropItem.getHeight(); // Y coordinate of the bottom edge
//
//                            // Calculate minX, minY, maxX, maxY
//                            minX = leftX;
//                            minY = topY;
//                            maxX = rightX;
//                            maxY = bottomY;
//                      */
//        /*  list.remove(position);
//                        item_adapter.notifyItemRemoved(position);*//*
//                            // Validate the drop area (customize this based on your requirements)
//                           *//* if (isValidDropArea(dropX, dropY)) {
//
//
//                                // ... (rest of your code)
//                            } else {
//                                // Handle invalid drop area (e.g., show a message)
//                                Toast.makeText(getApplicationContext(), "Invalid Drop Area", Toast.LENGTH_SHORT).show();
//                            }*/
//        /*
//                            cardViewParentView = (View) event.getLocalState();
//                            equipmentDropedRl = cardViewParentView.findViewById(R.id.relative_layout_equipment_item);
//                            linearItemListView = cardViewParentView.findViewById(R.id.linear_layout_itemList);
//
//                            linearItemListView.setVisibility(View.GONE);
//                            if (equipmentDropedRl != null) {
//                                equipmentDropedRl.setVisibility(View.VISIBLE);
//                                equCardViewParent = (ViewGroup) equipmentDropedRl.getParent();
//                            }
//                            if (equCardViewParent != null)
//                                equCardViewParent.removeView(equipmentDropedRl);
//                            // Add the dragged view to the new parent (dropImage)
//                            imageviewContainer.addView(equipmentDropedRl);
//                            viewManager.addView(viewId, equipmentDropedRl);
//                            // Calculate new position based on drop location
//                            float newX = event.getX() - equipmentDropedRl.getWidth() / 2;
//                            float newY = event.getY() - equipmentDropedRl.getHeight() / 2;
//
//                            // Set new position for the dragged view
//                            equipmentDropedRl.setX(newX);
//                            equipmentDropedRl.setY(newY);
//                            viewId++;
//                            ImageView itemremove = equipmentDropedRl.findViewById(R.id.equipment_close_icon);
//                            itemremove.setVisibility(View.VISIBLE);
//                            itemremove.setOnClickListener(Drop_Item_On_Map_Activity.this);
//
//                      *//*  equipmentDropedRl.setOnTouchListener(new View.OnTouchListener() {
//                            float dX, dY;
//
//                            @Override
//                            public boolean onTouch(View v, MotionEvent event) {
//
//                                switch (event.getAction()) {
//                                   *//**/
//        /* case MotionEvent.ACTION_DOWN:
//                                        dX = v.getX() - event.getRawX();
//                                        dY = v.getY() - event.getRawY();
//                                        break;
//                                    case MotionEvent.ACTION_MOVE:
//                                        float newX = event.getRawX() + dX;
//                                        float newY = event.getRawY() + dY;
//
//                                        // Calculate boundaries
//                                        float maxX = imageviewContainer.getWidth() - v.getWidth();
//                                        float maxY = imageviewContainer.getHeight() - v.getHeight();
//                                        newX = Math.max(0, Math.min(newX, maxX));
//                                        newY = Math.max(0, Math.min(newY, maxY));
//
//                                        v.setX(newX);
//                                        v.setY(newY);
//                                        break;*//**/
//        /*
//                                    case MotionEvent.ACTION_DOWN:
//                                        // Save the initial touch coordinates
//                                        lastX = event.getRawX();
//                                        lastY = event.getRawY();
//
//                                        // Get the dimensions of the parent view
//                                        ViewParent parent = v.getParent();
//                                        if (parent instanceof View) {
//                                            View parentView = (View) parent;
//                                            parentWidth = parentView.getWidth();
//                                            parentHeight = parentView.getHeight();
//                                        }
//                                        break;
//
//                                    case MotionEvent.ACTION_MOVE:
//                                        // Calculate the distance moved
//                                        float deltaX = event.getRawX() - lastX;
//                                        float deltaY = event.getRawY() - lastY;
//
//                                        // Update the position of the dragged view within the bounds
//                                        float newX = v.getX() + deltaX;
//                                        float newY = v.getY() + deltaY;
//
//                                        // Ensure the view stays within the bounds of the parent
//                                        newX = Math.max(0, Math.min(newX, parentWidth - v.getWidth()));
//                                        newY = Math.max(0, Math.min(newY, parentHeight - v.getHeight()));
//
//                                        v.setX(newX);
//                                        v.setY(newY);
//
//                                        // Save the current touch coordinates for the next move
//                                        lastX = event.getRawX();
//                                        lastY = event.getRawY();
//                                        break;
//                                    default:
//                                        return false;
//                                }
//                                return true;
//                            }
//                        });*/
//
//
//
//                           /* equipmentDropedRl.setOnLongClickListener(new View.OnLongClickListener() {
//                                @Override
//                                public boolean onLongClick(View v) {
//                                    return false;
//                                }
//                            });*/
//                   /*     equipmentDropedRl.setOnTouchListener(new View.OnTouchListener() {
//                            private int _xDelta;
//                            private int _yDelta;
//                            private int _xLimit;
//                            private int _yLimit;
//
//                            @Override
//                            public boolean onTouch(View view, MotionEvent event) {
//
//                                final int X = (int) event.getRawX();
//                                final int Y = (int) event.getRawY();
//                                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
//
//                                switch (event.getAction()) {
//                                    case MotionEvent.ACTION_DOWN:
//                                        _xDelta = X - layoutParams.leftMargin;
//                                        _yDelta = Y - layoutParams.topMargin;
//                                        _xLimit = imageviewContainer.getWidth();
//                                        _yLimit = imageviewContainer.getHeight();
//                                        break;
//                                    case MotionEvent.ACTION_MOVE:
//                                        int newX = X - _xDelta;
//                                        int newY = Y - _yDelta;
//
//                                        // Limit the new position to stay within bounds
//                                        newX = Math.max(0, Math.min(300, _xLimit));
//                                        newY = Math.max(0, Math.min(300, _yLimit));
//
//                                        layoutParams.leftMargin = newX;
//                                        layoutParams.topMargin = newY;
//                                        view.setLayoutParams(layoutParams);
//                                        break;
//                                }
//
//                                return true;
//                            }
//                        });*/
//
//                            /*equipmentDropedRl.setOnLongClickListener(new View.OnTouchListener() {
//                                private float lastX, lastY;
//                                private int parentWidth, parentHeight;
//
//                                @Override
//                                public boolean onTouch(View v, MotionEvent event) {
//                                    switch (event.getAction()) {
//                                        case MotionEvent.ACTION_DOWN:
//                                            // Save the initial touch coordinates
//                                            lastX = event.getRawX();
//                                            lastY = event.getRawY();
//
//                                            // Get the dimensions of the parent view
//                                            ViewParent parent = v.getParent();
//                                            if (parent instanceof View) {
//                                                View parentView = (View) parent;
//                                                parentWidth = parentView.getWidth();
//                                                parentHeight = parentView.getHeight();
//                                            }
//                                            break;
//
//                                        case MotionEvent.ACTION_MOVE:
//                                            // Calculate the distance moved
//                                            float deltaX = event.getRawX() - lastX;
//                                            float deltaY = event.getRawY() - lastY;
//
//                                            // Update the position of the dragged view within the bounds
//                                            float newX = v.getX() + deltaX;
//                                            float newY = v.getY() + deltaY;
//
//                                            // Ensure the view stays within the bounds of the parent
//                                            newX = Math.max(0, Math.min(newX, parentWidth - v.getWidth()));
//                                            newY = Math.max(0, Math.min(newY, parentHeight - v.getHeight()));
//
//                                            v.setX(newX);
//                                            v.setY(newY);
//
//                                            // Save the current touch coordinates for the next move
//                                            lastX = event.getRawX();
//                                            lastY = event.getRawY();
//                                            break;
//
//                                        default:
//                                            return false;
//                                    }
//                                    return true;
//                                }
//                            });*/
//
////                            return true;
////                        }/
////                        break;
//               /* }
//                        return true;
//
//            }
//        });
//*/
    }

    private void setEquipmentOnImage() {

    }

    View.OnLongClickListener onLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            isMovingView = true;
            String vId="";
            String json = "";
            int id = v.getId();
            for(MapItemModel itemModel : consumedItems){
                if(itemModel.getItemId().equals(id+"")){
                    vId = itemModel.getItemId();
                    Gson gson=new Gson();
                     json = gson.toJson(itemModel);
                    break;
                }
            }
            ClipData.Item item = new ClipData.Item(json);
            ClipData dragData = new ClipData("dragData", new String[]{ClipDescription.MIMETYPE_TEXT_PLAIN}, item);
            View.DragShadowBuilder myShadow = new View.DragShadowBuilder(v);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                v.startDragAndDrop(dragData, myShadow, v, 0);
            }
//                            v.setVisibility(View.INVISIBLE);
            return true;

        }
    };
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

                    ViewGroup parent1 = (ViewGroup) v.getParent();
                    ViewGroup parent2 = (ViewGroup) parent1.getParent();
                              parent2.removeView(parent1);
                     int id = parent1.getId();
                     for(MapItemModel itemModel: consumedItems){
                         if(itemModel.getItemId().equals(id+"")){
                             availableItems.add(itemModel);
                             item_adapter.setList(availableItems);
                         }
                     }
         /*   TextView equipmentText = equipmentDropedRl.findViewById(R.id.tv_droped_item);
                    linearItemListView = cardViewParentView.findViewById(R.id.linear_layout_itemList);
                    if (linearItemListView != null) linearItemListView.setVisibility(View.VISIBLE);
                    ViewGroup parent = (ViewGroup) equipmentDropedRl.getParent();
                    parent.removeView(equipmentDropedRl);
                    equCardViewParent.addView(equipmentDropedRl);
                    equipmentDropedRl.setVisibility(View.GONE);*/
//               list.add(position,equipmentText.getText().toString());
                    /* item_adapter.setList(list);*//**/

        }
    };

    private boolean isValidDropArea(float dropX, float dropY) {
        // Customize this method based on your drop area validation logic
        // Example: Allow drop only in a specific rectangular area
        return dropX >= minX && dropX <= maxX && dropY >= minY && dropY <= maxY;
    }
    /*    binding.imageForDropItem.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int action = event.getAction();
                switch (action) {
                    case DragEvent.ACTION_DROP:
                        ClipData.Item item = event.getClipData().getItemAt(0);
                        int position = Integer.parseInt(item.getText().toString());
                            float dropX = event.getX();
                            float dropY = event.getY();
                            int rightX = binding.imageForDropItem.getWidth();
                            int bottomY =  binding.imageForDropItem.getHeight();
                             maxX = rightX;
                            maxY = bottomY;
                            if (dropX < rightX && dropY < bottomY ) {
                                list.remove(position);
                                item_adapter.notifyItemRemoved(position);
                             cardViewParentView = (View) event.getLocalState();
                             equipmentDropedRl = cardViewParentView.findViewById(R.id.relative_layout_equipment_item);
                             linearItemListView = cardViewParentView.findViewById(R.id.linear_layout_itemList);
                             linearItemListView.setVisibility(View.GONE);
                            equipmentDropedRl.setVisibility(View.VISIBLE);
                                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                                float newX = event.getX() - (float) equipmentDropedRl.getWidth() / 2;
                                float newY = event.getY() - (float) equipmentDropedRl.getHeight() / 2;

                                equipmentDropedRl.setX(newX);
                                equipmentDropedRl.setY(newY);
                        layoutParams.leftMargin = (int) newX;
                        layoutParams.topMargin = (int) newY;
                        equipmentDropedRl.setLayoutParams(layoutParams);
                            ViewGroup parent = (ViewGroup) equipmentDropedRl.getParent();
                            if (parent != null) parent.removeView(equipmentDropedRl);
                            // Add the dragged view to the new parent (dropImage)
                            imageviewContainer.addView(equipmentDropedRl);
                                equipmentDropedRl.bringToFront();
                            // Calculate new position based on drop location

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
                                    @Override
                                    public boolean onTouch(View view, MotionEvent event) {
                                        switch (event.getActionMasked()) {
                                            case MotionEvent.ACTION_DOWN:
                                                ClipData data = ClipData.newPlainText("", "0");
                                                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(equipmentDropedRl);

                                                equipmentDropedRl.startDrag(data, shadowBuilder, equipmentDropedRl, 0);
                                                equipmentDropedRl.setVisibility(View.INVISIBLE);
                                                */
    /*xCoOrdinate = imageviewContainer.getX() - event.getRawX();
                                                yCoOrdinate = imageviewContainer.getY() - event.getRawY();*/
    /*
                                                break;
                                            case MotionEvent.ACTION_MOVE:
                                                imageviewContainer.animate().x(event.getRawX() + xCoOrdinate).y(event.getRawY() + yCoOrdinate).setDuration(0).start();
                                                break;
                                            default:
                                                return false;
                                        }
                                        return true;
                                    }
                                });

                } else {
                                // Handle invalid drop area (e.g., show a message)
                                Toast.makeText(getApplicationContext(), "Invalid Drop Area", Toast.LENGTH_SHORT).show();
                            }
            }
                return true;
        }
    });*/


    private void imageEditing(Uri uri1) {

//        img_doc.setImageURI(uri);
        compressImageInBack = new CompressImageInBack(this, new OnImageCompressed() {
            @Override
            public void onImageCompressed(Bitmap bitmap) {
                String savedImagePath = compressImageInBack.getSavedImagePath();
                if (bitmap != null) {
                    bitmapString = AppUtility.BitMapToString(bitmap);
                    binding.imageForDropItem.setImageBitmap(bitmap);
                }
            }
        }, uri1);
        compressImageInBack.setSaveBitmap(true);
        //  compressImageInBack.execute(uri);
        compressImageInBack.compressImageInBckg();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
           /* case R.id.equipment_close_icon:
                TextView equipmentText = equipmentDropedRl.findViewById(R.id.tv_droped_item);
                linearItemListView = cardViewParentView.findViewById(R.id.linear_layout_itemList);
                if (linearItemListView != null) linearItemListView.setVisibility(View.VISIBLE);
                ViewGroup parent = (ViewGroup) equipmentDropedRl.getParent();
                parent.removeView(equipmentDropedRl);
                equCardViewParent.addView(equipmentDropedRl);
                equipmentDropedRl.setVisibility(View.GONE);
//               list.add(position,equipmentText.getText().toString());
                 item_adapter.setList(list);
                break;*/
            case R.id.tv_back:
                onBackPressed();
                break;
        }
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {

        int action = event.getAction();
        switch (action) {
            case DragEvent.ACTION_DROP:
                listshow = false;
                binding.rvItemList.setVisibility(View.GONE);
                if (!isMovingView) {
                    ClipData.Item item = event.getClipData().getItemAt(0);
                    String itemModel = item.getText().toString();
//                    item_adapter.setList(availableItems);
//                        if (position >= 0 && position < list.size()) {
                    Gson gson = new Gson();
                    itemModel1 = gson.fromJson(itemModel, MapItemModel.class);
                    if (!consumedItems.contains(itemModel1)) {
                        consumedItems.add(itemModel1);

                    }
                }
                float dropX = event.getX();
                float dropY = event.getY();
                if (droppedModel != null) {
                    for (MapItemModel model : availableItems) {
                        if (model.getItemId().equals(droppedModel.getItemId())) {
                            model.setAvailability(droppedModel.getAvailability());
                            break;
                        }
                    }
                }
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
                      /*  list.remove(position);
                        item_adapter.notifyItemRemoved(position);*/
                // Validate the drop area (customize this based on your requirements)
                           /* if (isValidDropArea(dropX, dropY)) {


                                // ... (rest of your code)
                            } else {
                                // Handle invalid drop area (e.g., show a message)
                                Toast.makeText(getApplicationContext(), "Invalid Drop Area", Toast.LENGTH_SHORT).show();
                            }*/

                if (!isMovingView) {
                    cardViewParentView = (View) event.getLocalState();
                    equipmentDropedRl = cardViewParentView.findViewById(R.id.relative_layout_equipment_item);
                    linearItemListView = cardViewParentView.findViewById(R.id.linear_layout_itemList);

                    if (linearItemListView != null) linearItemListView.setVisibility(View.GONE);
                    if (equipmentDropedRl != null) {
                        equipmentDropedRl.setVisibility(View.VISIBLE);
                        equCardViewParent = (ViewGroup) equipmentDropedRl.getParent();
                    }
                    if (equCardViewParent != null)
                        equCardViewParent.removeView(equipmentDropedRl);
                    // Add the dragged view to the new parent (dropImage)
                    imageviewContainer.addView(equipmentDropedRl);

                    equipmentDropedRl.setId(Integer.parseInt(itemModel1.getItemId()));
                    itemremove = equipmentDropedRl.findViewById(R.id.equipment_close_icon);
                    equipmentDropedRl.setOnLongClickListener(onLongClickListener);
                    itemremove.setOnClickListener(onClickListener);
                } else {
                    equipmentDropedRl = (View) event.getLocalState();
                    imageviewContainer = (ViewGroup) equipmentDropedRl.getParent();
                    imageviewContainer.removeView(equipmentDropedRl);
                    imageviewContainer.addView(equipmentDropedRl);
                    equipmentDropedRl.setVisibility(View.VISIBLE);
                    isMovingView = false;
                }
                // Calculate new position based on drop location
                float newX = dropX - (float) equipmentDropedRl.getWidth() / 2;
                float newY = dropY - (float) equipmentDropedRl.getHeight() / 2;

                // Set new position for the dragged view
                equipmentDropedRl.setX(newX);
                equipmentDropedRl.setY(newY);
                itemremove.setVisibility(View.VISIBLE);


               /* equipmentDropedRl.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {

                        isMovingView = true;
                        ClipData.Item item = new ClipData.Item("" + position);
                        ClipData dragData = new ClipData("dragData", new String[]{ClipDescription.MIMETYPE_TEXT_PLAIN}, item);
                        View.DragShadowBuilder myShadow = new View.DragShadowBuilder(v);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            v.startDragAndDrop(dragData, myShadow, equipmentDropedRl, 0);
                        }
//                            v.setVisibility(View.INVISIBLE);
                        return true;
                    }
                });*/
                break;
        }
        return true;

//        }else{
//            switch (event.getAction()) {
//                case DragEvent.ACTION_DROP:
//                    isMovingView = false;
//                    float x = event.getX() - equipmentDropedRl.getWidth() / 2;
//                    float y = event.getY() - equipmentDropedRl.getHeight() / 2;
//                    x = Math.max(0, Math.min(x, imageviewContainer.getWidth() - equipmentDropedRl.getWidth()));
//                    y = Math.max(0, Math.min(y, imageviewContainer.getHeight() - equipmentDropedRl.getHeight()));
//                    equipmentDropedRl.setX(x);
//                    equipmentDropedRl.setY(y);
//                    equipmentDropedRl.setVisibility(View.VISIBLE);
//                    return true;
//                default:
//                    return false;
//            }
//        }
    }


    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void removeSelectedEqu(MapItemModel itemModel) {
        droppedModel = itemModel;
        consumedItems.add(itemModel);


        }
//        availableItems.remove(itemModel);

}