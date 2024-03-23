package com.eot_app.nav_menu.jobs.job_detail.detail;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.eot_app.R;
import com.eot_app.nav_menu.jobs.job_complation.JobCompletionActivity;
import com.eot_app.nav_menu.jobs.job_detail.documents.doc_model.Attachments;
import com.eot_app.nav_menu.jobs.job_detail.job_equipment.JobEquipmentAdapter;
import com.eot_app.utility.AppUtility;
import com.eot_app.utility.App_preference;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class CompletionAdpterJobDteails1 extends RecyclerView.Adapter<CompletionAdpterJobDteails1.ViewHolder> {

    ArrayList<Attachments> getFileListres = new ArrayList<>();
    boolean EQUIPMENTATTCHMENT = false;
    JobEquipmentAdapter.SelectedImageView selectedImageView;
    private CallBAckForAttchemnt callBAckForAttchemnt;
    private CallBAckFormAttchemntToCompl callBAckFormAttchemntToCompl;
    private Context context;
    private int counter = 0;
    int position , parentPosition;

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param getFileListres String[] containing the data to populate views to be used
     *                       by RecyclerView.
     */
    public CompletionAdpterJobDteails1(ArrayList<Attachments> attachmentsArrayList, CallBAckForAttchemnt callBAckForAttchemnt) {
        this.getFileListres = attachmentsArrayList;
        this.callBAckForAttchemnt = callBAckForAttchemnt;
        this.EQUIPMENTATTCHMENT = false;
    }


    public CompletionAdpterJobDteails1(ArrayList<Attachments> attachmentsArrayList, JobEquipmentAdapter.SelectedImageView selectedImageView) {
        this.getFileListres = attachmentsArrayList;
        this.EQUIPMENTATTCHMENT = true;
        this.selectedImageView = selectedImageView;
    }
 public CompletionAdpterJobDteails1(ArrayList<Attachments> attachmentsArrayList, CallBAckFormAttchemntToCompl callBAckFormAttchemntToCompl, Context context,int position, int parentPosition) {
        this.getFileListres = attachmentsArrayList;
        this.callBAckFormAttchemntToCompl = callBAckFormAttchemntToCompl;
        this.context = context;
        this.position = position;
        this.parentPosition = parentPosition;
    }

    public void updateFileList(ArrayList<Attachments> getFileListres) {
        if (this.getFileListres != null) this.getFileListres.clear();
        this.getFileListres.addAll(getFileListres);
        notifyDataSetChanged();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        context = viewGroup.getContext();
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.text_row_item, viewGroup, false);

        return new ViewHolder(view);
    }

    public int getCounter() {
        return counter;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element

        Attachments fileList = getFileListres.get(position);
        counter = +1;
        final String ext = fileList.getImage_name().substring((fileList.getImage_name().lastIndexOf(".")) + 1).toLowerCase();
        if (!ext.isEmpty()) {
            if (ext.equals("jpg") || ext.equals("jpeg") || ext.equals("png")) {
//                if(fileList.getAttachmentId().contains("Attachment-") && fileList.getAttachThumnailFileName() != null){
//                    Glide.with(context).load(fileList.getAttachThumnailFileName())
//                            .format(DecodeFormat.PREFER_ARGB_8888)
//                            .thumbnail(Glide.with(context).load(R.raw.loader_eot)).placeholder(R.drawable.picture).into(viewHolder.image_thumb_nail);
//                }else
                if(fileList.getBitmap()!= null && !fileList.getBitmap().isEmpty() && new File(fileList.getBitmap()).exists()){
                    Bitmap bitmap1= AppUtility.getBitmapFromPath(fileList.getBitmap());
                    viewHolder.image_thumb_nail.setImageBitmap(bitmap1);
                }else
                {
                    Glide.with(context).load(App_preference.getSharedprefInstance().getBaseURL() + fileList.getAttachThumnailFileName())
                            .format(DecodeFormat.PREFER_ARGB_8888)
                            .thumbnail(Glide.with(context).load(R.raw.loader_eot)).placeholder(R.drawable.picture).into(viewHolder.image_thumb_nail);
                }
                viewHolder.image_thumb_nail.setScaleType(ImageView.ScaleType.CENTER_CROP);
            } else if (ext.equals("doc") || ext.equals("docx")) {
                viewHolder.image_thumb_nail.setImageResource(R.drawable.word);
                viewHolder.image_thumb_nail.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            } else if (ext.equals("pdf")) {
                viewHolder.image_thumb_nail.setImageResource(R.drawable.pdf);
                viewHolder.image_thumb_nail.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

            } else if (ext.equals("xlsx") || ext.equals("xls")) {
                viewHolder.image_thumb_nail.setImageResource(R.drawable.excel);
                viewHolder.image_thumb_nail.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

            } else if (ext.equals("csv")) {
                viewHolder.image_thumb_nail.setImageResource(R.drawable.csv);
                viewHolder.image_thumb_nail.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            } else {
                viewHolder.image_thumb_nail.setImageResource(R.drawable.doc);
                viewHolder.image_thumb_nail.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            }
            if (position != 0 && position != 1 && getFileListres.size() > 3) {
                viewHolder.myImageViewText.setVisibility(View.VISIBLE);
                try {
                    int pos = (getFileListres.size() - (position + 1));
                    DecimalFormat formatter = new DecimalFormat("00");
                    String s = (formatter.format(Integer.parseInt(pos + "")));
                    viewHolder.myImageViewText.setText(s + "+");
                } catch (Exception ex) {
                    viewHolder.myImageViewText.setText((getFileListres.size() - (position + 1)) + "+");
                }
            } else {
                viewHolder.myImageViewText.setVisibility(View.GONE);
            }

            viewHolder.layout_doc.setOnClickListener(v -> {
                Log.e("", "");
                try {
                    if (!EQUIPMENTATTCHMENT && callBAckForAttchemnt != null)
                        callBAckForAttchemnt.getAttchment();
                    else if (EQUIPMENTATTCHMENT && selectedImageView != null) {
                        selectedImageView.sleecteAttch(position);
                    }else  if(context instanceof JobCompletionActivity){
                        callBAckFormAttchemntToCompl.hideView(true, fileList.getJobId(), fileList.getQueId(), fileList.getJtId(),this.position, parentPosition);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });
            if(AppUtility.isInternetConnected()){
                if(fileList.getBitmap()!= null && !fileList.getBitmap().isEmpty() && fileList.getAttachmentId().contains("Attachment-"))
                {
                    viewHolder.image_loader.setVisibility(View.VISIBLE);
                    Glide.with(context).load("").centerCrop()
                            .thumbnail(Glide.with(context).load(R.raw.loader_eot2)).into(viewHolder.image_loader);
                }
                else {
                    viewHolder.image_loader.setVisibility(View.GONE);
                }
            }else {
                if(fileList.getBitmap()== null && fileList.getBitmap().isEmpty() || fileList.getBitmap().isEmpty()){
                    Glide.with(context).load(R.mipmap.no_internet_placeholder).into(viewHolder.image_thumb_nail);
                }
            }


        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        if(getFileListres.size()>3){
            return 3;
        }else {
            return getFileListres.size();
        }
    }

    public interface CallBAckForAttchemnt {
        void getAttchment();
    }
    public interface CallBAckFormAttchemntToCompl {
        void hideView(boolean hide,String jobId, String queId, String jtId, int position, int parentPosition);
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView image_thumb_nail, image_loader;
        private final TextView myImageViewText;
        private final RelativeLayout layout_doc;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            image_thumb_nail = view.findViewById(R.id.image_thumb_nail);
            myImageViewText = view.findViewById(R.id.myImageViewText);
            layout_doc = view.findViewById(R.id.layout_doc);
            image_loader = view.findViewById(R.id.image_loader);
        }

    }

}

