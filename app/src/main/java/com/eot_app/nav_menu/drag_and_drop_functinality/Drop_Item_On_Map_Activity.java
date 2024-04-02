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
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.eot_app.R;
import com.eot_app.databinding.ActivityDropItemOnMapBinding;
import com.eot_app.nav_menu.drag_and_drop_functinality.drag_drop_pi_And_pc.DragDropMap_View;
import com.eot_app.nav_menu.drag_and_drop_functinality.drag_drop_pi_And_pc.MapView_pc;
import com.eot_app.nav_menu.drag_and_drop_functinality.drag_drop_pi_And_pc.MapView_pi;
import com.eot_app.nav_menu.drag_and_drop_functinality.model.AddUpdateMapReqModel;
import com.eot_app.nav_menu.drag_and_drop_functinality.model.DragAndDropMapModel;
import com.eot_app.nav_menu.drag_and_drop_functinality.model.MapItemModel;
import com.eot_app.nav_menu.jobs.add_job.add_job_recr.daily_recr_pkg.daily_recr_mvp.DailyRecr_View;
import com.eot_app.utility.AppUtility;
import com.eot_app.utility.CompressImageInBack;
import com.eot_app.utility.EotApp;
import com.eot_app.utility.util_interfaces.OnImageCompressed;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class Drop_Item_On_Map_Activity extends AppCompatActivity implements Drop_Item_Adapter.RemoveItem, View.OnClickListener, View.OnDragListener, DragDropMap_View {
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
    ViewGroup equCardViewParent;
    boolean isMovingView = false;
    MapItemModel droppedModel;
    MapItemModel itemModel1;
    ImageView itemremove;
    MapView_pi mapViewPi;
    DragAndDropMapModel dragAndDropMapModel;
    String savedImagePath;
    Uri uri;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_drop_item_on_map);
        mapViewPi = new MapView_pc(Drop_Item_On_Map_Activity.this);
        imageviewContainer = (ViewGroup) binding.imageForDropItem.getParent();
        uri  = Uri.parse(getIntent().getStringExtra("uri"));

        try {
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
            binding.tvBack.setOnClickListener(this);
            binding.imageForDropItem.setOnDragListener(this);
            binding.mapSaveBtn.setOnClickListener(this);
            mapViewPi.getDragAndDropMapDataApi();
            imageEditing(uri);
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }
    private void setEquipmentOnImage() {
        if(consumedItems != null && consumedItems.size() > 0){
            for(MapItemModel mapItemModel : consumedItems){
                RelativeLayout frameLayout = binding.framLayoutContainer1;
                View cv = LayoutInflater.from(this).inflate(R.layout.drop_item_adapter_view,frameLayout,false);
                equipmentDropedRl = cv.findViewById(R.id.relative_layout_equipment_item);
                linearItemListView = cv.findViewById(R.id.linear_layout_itemList);
                TextView tv_droped_item = cv.findViewById(R.id.tv_droped_item);
                tv_droped_item.setText(mapItemModel.getName());
                if (linearItemListView != null) linearItemListView.setVisibility(View.GONE);
                if (equipmentDropedRl != null) {
                    equipmentDropedRl.setVisibility(View.VISIBLE);
                    equCardViewParent = (ViewGroup) equipmentDropedRl.getParent();
                }
                if (equCardViewParent != null)
                    equCardViewParent.removeView(equipmentDropedRl);
                imageviewContainer.addView(equipmentDropedRl);
                equipmentDropedRl.setId(Integer.parseInt(mapItemModel.getItemId()));
                itemremove = equipmentDropedRl.findViewById(R.id.equipment_close_icon);
                equipmentDropedRl.setOnLongClickListener(onLongClickListener);
                itemremove.setOnClickListener(onClickListener);
                float newX = mapItemModel.getCordinetX()- (float) equipmentDropedRl.getWidth() / 2;
                float newY = mapItemModel.getCordinetY() - (float) equipmentDropedRl.getHeight() / 2;

                equipmentDropedRl.setX(newX);
                equipmentDropedRl.setY(newY);

                itemremove.setVisibility(View.VISIBLE);
            }
        }

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
            List<MapItemModel> availableremoveItem = new ArrayList<>();
            for(MapItemModel avaItem : availableItems){
                for (MapItemModel conItem:consumedItems){
                    if(avaItem.getItemId().equals(conItem.getItemId()) || avaItem.getItemId().equals(id+"")){
                        availableremoveItem.add(avaItem);
                        break;
                    }
                }
            }
                availableItems.removeAll(availableremoveItem);

                     for(MapItemModel itemModel: consumedItems){
                         if(itemModel.getItemId().equals(id+"")){
                             itemModel.setAvailability("true");
                             availableItems.add(itemModel);
                             item_adapter.setList(availableItems);
                             break;
                         }
                     }
        }
    };

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
                 savedImagePath = compressImageInBack.getSavedImagePath();
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

            case R.id.tv_back:
                onBackPressed();
                break;
            case R.id.map_save_btn:
                availableItems.removeAll(consumedItems);
                addUpdateMapData();
                break;
        }
    }

    private void addUpdateMapData() {
                List<MapItemModel> availableremoveItem = new ArrayList<>();
                for(MapItemModel avaItem : availableItems){
                    for (MapItemModel conItem:consumedItems){
                        if(avaItem.getItemId().equals(conItem.getItemId())){
                            availableremoveItem.add(avaItem);
                            break;
                        }
                    }
                }
                availableItems.removeAll(availableremoveItem);
                availableItems.addAll(consumedItems);
                dragAndDropMapModel.setMapItems(availableItems);

                AddUpdateMapReqModel addUpdateModel =  new AddUpdateMapReqModel(dragAndDropMapModel.getJobId(),
                        dragAndDropMapModel.getMapId(),String.valueOf(dragAndDropMapModel.getMapLength()),String.valueOf(dragAndDropMapModel.getMapWidth()),
                        dragAndDropMapModel.getTitle(),dragAndDropMapModel.getMapItems(),savedImagePath);
           /* List<AddUpdateMapReqModel> addUpdateList = new ArrayList<>();
                   addUpdateList.add(addUpdateModel);*/
        mapViewPi.addUpdateMapItemCordinat(addUpdateModel);

    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        listshow = false;
        binding.rvItemList.setVisibility(View.GONE);
        int action = event.getAction();
        switch (action) {
            case DragEvent.ACTION_DROP:
//                if (!isMovingView) {
                    ClipData.Item item = event.getClipData().getItemAt(0);
                    String itemModel = item.getText().toString();
                    Gson gson = new Gson();
                    itemModel1 = gson.fromJson(itemModel, MapItemModel.class);

//                }
                float dropX = event.getX();
                float dropY = event.getY();
                int[] location = new int[2];
                binding.imageForDropItem.getLocationOnScreen(location);
                int leftX = location[0];   // X coordinate of the left edge
                int topY = location[1];    // Y coordinate of the top edge
                int rightX = leftX + binding.imageForDropItem.getWidth();  // X coordinate of the right edge
                int bottomY = topY + binding.imageForDropItem.getHeight(); // Y coordinate of the bottom edge
                minX = leftX;
                minY = topY;
                maxX = rightX;
                maxY = bottomY;
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
                if(itemModel1 != null) {
                    itemModel1.setCordinetX((int) newX);
                    itemModel1.setCordinetY((int) newY);
                    itemremove.setVisibility(View.VISIBLE);
                    if (itemModel1.getAvailability().equals("true")) {
                        itemModel1.setAvailability("false");
                        for (MapItemModel model : availableItems) {
                            if (model.getItemId().equals(itemModel1.getItemId())) {
                                model.setCordinetX((int) newX);
                                model.setCordinetY((int) newY);
                                model.setAvailability("false");
                                consumedItems.add(model);
                                break;
                            }
                        }
                    } else {
                        for (MapItemModel model : consumedItems) {
                            if (model.getItemId().equals(itemModel1.getItemId())) {
                                model.setCordinetX((int) newX);
                                model.setCordinetY((int) newY);
                                model.setAvailability("false");
                                break;
                            }
                        }
                    }
                }
                break;
        }
        return true;
    }


    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void removeSelectedEqu(MapItemModel itemModel) {
        droppedModel = itemModel;
        consumedItems.add(itemModel);


        }

    @Override
    public void setDragDRopMapData(DragAndDropMapModel dragAndDropMapModel) {
        if (dragAndDropMapModel != null) {
            this.dragAndDropMapModel = dragAndDropMapModel;
            int layout_width, layout_height;
            layout_width = dragAndDropMapModel.getMapWidth();
           layout_height =dragAndDropMapModel.getMapLength();
           FrameLayout.LayoutParams params1 = new FrameLayout.LayoutParams(layout_width, layout_height);
           binding.framLayoutContainer1.setLayoutParams(params1);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(layout_width, layout_height);
            binding.imageForDropItem.setLayoutParams(params);
            List<MapItemModel> mapItems = dragAndDropMapModel.getMapItems();
            for (MapItemModel mapItemModel : mapItems) {
                if (mapItemModel.getAvailability().equals("false")) {
                    consumedItems.add(mapItemModel);
                }else {
                    availableItems.add(mapItemModel);
                }
            }
            item_adapter.setList(availableItems);
            setEquipmentOnImage();
        }
    }

    @Override
    public void showToastmsg(String msg) {
        EotApp.getAppinstance().showToastmsg(msg);
        this.finish();
    }

}