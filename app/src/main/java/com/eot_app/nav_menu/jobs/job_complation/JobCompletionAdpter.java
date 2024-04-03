package com.eot_app.nav_menu.jobs.job_complation;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.eot_app.R;
import com.eot_app.nav_menu.jobs.job_detail.documents.DialogUpdateDocuments;
import com.eot_app.nav_menu.jobs.job_detail.documents.doc_model.Attachments;
import com.eot_app.utility.AppConstant;
import com.eot_app.utility.AppUtility;
import com.eot_app.utility.App_preference;
import com.eot_app.utility.language_support.LanguageController;
import java.util.ArrayList;

public class JobCompletionAdpter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {
    private final ArrayList<Attachments> suggestions = new ArrayList<>();
    private final FileDesc_Item_Selected fileDesc_item_selected;
    private final String jobId;
    private final JobCompletionActivity jobCompletionActivity;
    private final RemoveAttchment removeAttchment;
    private ArrayList<Attachments> attachments = new ArrayList<>();
    private ArrayList<Attachments> tempFileList;
    String  queId, jtId;
    Filter nameFilter = new Filter() {


        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
            ArrayList<Attachments> FilteredArrList = new ArrayList<>();


            if (tempFileList == null) {
                tempFileList = new ArrayList<Attachments>(attachments); // saves the original data in mOriginalValues
            }
            FilteredArrList.clear();
            if (constraint == null || constraint.length() == 0) {

                // set the Original result to return
                results.count = tempFileList.size();
                results.values = tempFileList;
            } else {
                constraint = constraint.toString().toLowerCase();

                for (Attachments fileList : attachments) {

                    if (fileList.getAttachFileActualName().toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                        FilteredArrList.add(fileList);
                    }
                }

                // set the Filtered result to return
                results.count = FilteredArrList.size();
                results.values = FilteredArrList;
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            attachments = (ArrayList<Attachments>) results.values;
            notifyDataSetChanged();  // notifies the
        }
    };
    private Context context;

    public JobCompletionAdpter(FileDesc_Item_Selected fileDesc_item_selected, ArrayList<Attachments> attachments,
                               JobCompletionActivity jobCompletionActivity, String jobId
            , RemoveAttchment removeAttchment) {
        this.attachments = attachments;
        this.fileDesc_item_selected = fileDesc_item_selected;
        tempFileList = new ArrayList<>();
        this.tempFileList = attachments;
        this.jobCompletionActivity = jobCompletionActivity;
        this.jobId = jobId;
        this.removeAttchment = removeAttchment;
    }

    public void updateFileList(ArrayList<Attachments> getFileListres, boolean firstCall, String queId, String jtId) {
        this.queId = queId;
        this.jtId = jtId;
        if(firstCall) this.attachments.clear();
        this.attachments.addAll(getFileListres);
        notifyDataSetChanged();

    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0)
            return 1;
        else
            return 0;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = null;
        if (viewType == 1) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_attachemnt_view, parent, false);
            return new JobCompletionAdpter.UploadViewHolder(view);
        } else
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.jobcompletion_adpter, parent, false);
        return new MyView_Holder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, final int pos) {
        if (viewHolder instanceof MyView_Holder) {
            final int position = pos - 1;
            final MyView_Holder holder = (MyView_Holder) viewHolder;
            Attachments fileList = attachments.get(position);

            final String ext = fileList.getImage_name().substring((fileList.getImage_name()                       .lastIndexOf(".")) + 1).toLowerCase();
            if (!ext.isEmpty()) {

                if (ext.equals("jpg") || ext.equals("jpeg") || ext.equals("png")) {
//                    if(fileList.getAttachmentId().contains("Attachment-") && fileList.getAttachThumnailFileName() != null){
//                        Glide.with(context).load(fileList.getAttachThumnailFileName())
//                                .format(DecodeFormat.PREFER_ARGB_8888)
//                                .thumbnail(Glide.with(context).load(R.raw.loader_eot)).placeholder(R.drawable.picture).into(holder.image_thumb_nail);
//                    }else
//                    if(fileList.getAttachmentId().equalsIgnoreCase("0")&&fileList.getBitmap1()!=null)
//                    {
//                        holder.image_thumb_nail.setImageBitmap(fileList.getBitmap1());
//                    }else
                        if(fileList.getBitmap()!=null && !fileList.getBitmap().isEmpty())
                    {
                        Bitmap bitmap1= AppUtility.getBitmapFromPath(fileList.getBitmap());
                        holder.image_thumb_nail.setImageBitmap(bitmap1);
                    }
                    else {
                        Glide.with(context).load(App_preference.getSharedprefInstance().getBaseURL() + fileList.getAttachThumnailFileName())
                                .format(DecodeFormat.PREFER_ARGB_8888)
                                .thumbnail(Glide.with(context).load(R.raw.loader_eot)).placeholder(R.drawable.picture).into(holder.image_thumb_nail);
                    }
                    holder.image_thumb_nail.setScaleType(ImageView.ScaleType.CENTER_CROP);
                } else if (ext.equals("doc") || ext.equals("docx")) {
                    holder.image_thumb_nail.setImageResource(R.drawable.word);
                    holder.image_thumb_nail.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

                } else if (ext.equals("pdf")) {
                    holder.image_thumb_nail.setImageResource(R.drawable.pdf);
                    holder.image_thumb_nail.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

                } else if (ext.equals("xlsx") || ext.equals("xls")) {
                    holder.image_thumb_nail.setImageResource(R.drawable.excel);
                    holder.image_thumb_nail.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

                } else if (ext.equals("csv")) {
                    holder.image_thumb_nail.setImageResource(R.drawable.csv);
                    holder.image_thumb_nail.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

                } else {
                    holder.image_thumb_nail.setImageResource(R.drawable.doc);
                    holder.image_thumb_nail.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                }

                if(AppUtility.isInternetConnected()){
                    if(fileList.getBitmap()!= null && !fileList.getBitmap().isEmpty() && fileList.getAttachmentId().contains("Attachment-"))
                    {
                        holder.image_loader.setVisibility(View.VISIBLE);
                        Glide.with(context).load("").centerCrop()
                                .thumbnail(Glide.with(context).load(R.raw.loader_eot2)).into(holder.image_loader);
                        holder.image_txt.setVisibility(View.GONE);
                    }
                    else {
                        holder.image_loader.setVisibility(View.GONE);
                        holder.image_txt.setVisibility(View.VISIBLE);
                    }
                }else {
                    if(fileList.getBitmap()== null && fileList.getBitmap().isEmpty() || fileList.getBitmap().isEmpty()){
                        Glide.with(context).load(R.mipmap.no_internet_placeholder).into(holder.image_thumb_nail);
                    }
                }
            }
            holder.image_thumb_nail.setOnClickListener(view -> {
                if(AppUtility.isInternetConnected()){
                    if (attachments.get(position).getType() != null && attachments.get(position).getType().equals("2") || attachments.get(position).getType().equals("6")) {
                        DialogUpdateDocuments
                                dialogUpdateDocuments = new DialogUpdateDocuments();

                        if (!TextUtils.isEmpty(ext) && ext.equals("jpg") || ext.equals("jpeg") || ext.equals("png"))
                            dialogUpdateDocuments.setIsFileImage(true);
                        String img_name = "";
                        if (attachments.get(position).getAtt_docName() == null) {
                            img_name = attachments.get(position).getAttachFileActualName();
                        } else {
                            img_name = attachments.get(position).getAtt_docName() + "." + ext;
                        }

                        dialogUpdateDocuments.setImgPath(
                                attachments.get(position).getAttachmentId(),
                                attachments.get(position).getAttachFileName(),
                                img_name,
                                attachments.get(position).getDes(),
                                attachments.get(position).getType(),
                                jobId,queId,jtId);

                        dialogUpdateDocuments.setOnDocumentUpdate((desc, name, queId, jtId) -> {
                            if (desc != null)
                                attachments.get(position).setDes(desc);
                            if (name != null)
                                attachments.get(position).setAtt_docName(name);

                            if (jobCompletionActivity != null)
                                jobCompletionActivity.setUpdatedDesc(desc,queId,jtId);

                        });
                        dialogUpdateDocuments.show(((AppCompatActivity) context).getSupportFragmentManager(), "dialog");
                    } else
                        fileDesc_item_selected.OnItemClick_Document(attachments.get(holder.getBindingAdapterPosition()));
                }else {
                    jobCompletionActivity.showErrorDialog(LanguageController.getInstance().getMobileMsgByKey(AppConstant.offline_feature_alert));
                }
            });

            holder.image_txt.setOnClickListener(v -> removeAttchment.removeAttchment(attachments.get(position).getAttachmentId(),queId, jtId));

        } else if (viewHolder instanceof UploadViewHolder) {
            UploadViewHolder holder = (UploadViewHolder) viewHolder;
            holder.add_attach_btn.setOnClickListener(v -> {
                if (fileDesc_item_selected != null)
                    fileDesc_item_selected.openAttachmentDialog();
            });
        }
    }

    @Override
    public int getItemCount() {
        if (attachments == null)
            return 1;
        else
            return attachments.size() + 1;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    public interface FileDesc_Item_Selected {
        void OnItemClick_Document(Attachments attachments);

        void openAttachmentDialog();
    }

    public interface RemoveAttchment {
        void removeAttchment(String id, String queId, String jtId);
    }

    static class MyView_Holder extends RecyclerView.ViewHolder {
        LinearLayout layout_doc;
        ImageView image_thumb_nail;
        //        ImageView viewOne, viewTwo;
        ImageView image_txt;
        //  ImageView add_attach_btn;
        ImageView image_loader;
        public MyView_Holder(View itemView) {
            super(itemView);
            layout_doc = itemView.findViewById(R.id.layout_doc);
            image_thumb_nail = itemView.findViewById(R.id.image_thumb_nail);
            image_loader = itemView.findViewById(R.id.image_loader);
            image_txt = itemView.findViewById(R.id.image_txt);
            //   add_attach_btn = itemView.findViewById(R.id.add_attach_btn);
        }
    }

    class UploadViewHolder extends RecyclerView.ViewHolder {
        LinearLayout add_attach_btn;
        TextView upload_file;


        public UploadViewHolder(View itemView) {
            super(itemView);
            add_attach_btn = itemView.findViewById(R.id.add_attach_btn);
            upload_file = itemView.findViewById(R.id.upload_file);
            upload_file.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.upld_fil));

        }
    }
}

