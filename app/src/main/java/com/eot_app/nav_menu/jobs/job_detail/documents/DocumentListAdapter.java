package com.eot_app.nav_menu.jobs.job_detail.documents;

import android.content.Context;
import android.graphics.Bitmap;
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
import com.eot_app.R;
import com.eot_app.nav_menu.jobs.job_complation.JobCompletionActivity;
import com.eot_app.nav_menu.jobs.job_detail.documents.doc_model.Attachments;
import com.eot_app.utility.AppConstant;
import com.eot_app.utility.AppUtility;
import com.eot_app.utility.App_preference;
import com.eot_app.utility.language_support.LanguageController;

import java.util.ArrayList;


/**
 * Created by ubuntu on 9/10/18.
 */

public class DocumentListAdapter extends RecyclerView.Adapter<DocumentListAdapter.MyView_Holder> implements Filterable {
    final ArrayList<Attachments> suggestions = new ArrayList<>();
    private final FileDesc_Item_Selected fileDesc_item_selected;
    private final String jobId;
    private ArrayList<Attachments> getFileList_res = new ArrayList<>();
    private ArrayList<Attachments> tempFileList;
    boolean isClickDisabled = false;
    String isEqu="0";

    Filter nameFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
            ArrayList<Attachments> FilteredArrList = new ArrayList<>();
            if (tempFileList == null) {
                tempFileList = new ArrayList<Attachments>(getFileList_res); // saves the original data in mOriginalValues
            }
            FilteredArrList.clear();
            if (constraint == null || constraint.length() == 0) {

                // set the Original result to return
                results.count = tempFileList.size();
                results.values = tempFileList;
            } else {
                constraint = constraint.toString().toLowerCase();
                for (Attachments fileList : getFileList_res) {
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
            getFileList_res = (ArrayList<Attachments>) results.values;
            notifyDataSetChanged();  // notifies the
        }
    };
    private Context context;
    private JobCompletionActivity jobCompletionActivity;
    private DocumentsFragment documentsFragment;


    public DocumentListAdapter(FileDesc_Item_Selected fileDesc_item_selected, ArrayList<Attachments> getFileList_res, String jobId) {
        this.getFileList_res = getFileList_res;
        this.fileDesc_item_selected = fileDesc_item_selected;
        tempFileList = new ArrayList<>();
        this.tempFileList = getFileList_res;
        this.jobId = jobId;
    }
    public DocumentListAdapter(FileDesc_Item_Selected fileDesc_item_selected, ArrayList<Attachments> getFileList_res, String jobId,DocumentsFragment documentsFragment) {
        this.getFileList_res = getFileList_res;
        this.fileDesc_item_selected = fileDesc_item_selected;
        tempFileList = new ArrayList<>();
        this.tempFileList = getFileList_res;
        this.jobId = jobId;
        this.documentsFragment = documentsFragment;
    }
    public DocumentListAdapter(FileDesc_Item_Selected fileDesc_item_selected, ArrayList<Attachments> getFileList_res, String jobId, String isEqu) {
        this.getFileList_res = getFileList_res;
        this.fileDesc_item_selected = fileDesc_item_selected;
        tempFileList = new ArrayList<>();
        this.tempFileList = getFileList_res;
        this.jobId = jobId;
        this.isEqu = isEqu;
    }
    public DocumentListAdapter(FileDesc_Item_Selected fileDesc_item_selected, ArrayList<Attachments> getFileList_res, String jobId, boolean isClickDisabled) {
        this.getFileList_res = getFileList_res;
        this.fileDesc_item_selected = fileDesc_item_selected;
        tempFileList = new ArrayList<>();
        this.tempFileList = getFileList_res;
        this.jobId = jobId;
        this.isClickDisabled = isClickDisabled;
    }

    public DocumentListAdapter(FileDesc_Item_Selected fileDesc_item_selected, ArrayList<Attachments> getFileList_res, JobCompletionActivity jobCompletionActivity, String jobId) {
        this.getFileList_res = getFileList_res;
        this.fileDesc_item_selected = fileDesc_item_selected;
        tempFileList = new ArrayList<>();
        this.tempFileList = getFileList_res;
        this.jobCompletionActivity = jobCompletionActivity;
        this.jobId = jobId;
    }

    public void updateFileList(ArrayList<Attachments> getFileListres, boolean firstCall) {
        if (this.getFileList_res != null) {
            if (firstCall) this.getFileList_res.clear();
            this.getFileList_res.addAll(getFileListres);
            notifyDataSetChanged();
        }
    }


    @NonNull
    @Override
    public MyView_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view;
        if(isEqu.equalsIgnoreCase("1"))
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.equipment_attchment_item, parent, false);
        }
        else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.documentlist_adapter, parent, false);
        }
        return new MyView_Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyView_Holder holder, final int pos) {
        Attachments fileList = getFileList_res.get(pos);


        final String ext = fileList.getImage_name().substring((fileList.getImage_name().lastIndexOf(".")) + 1).toLowerCase();

        if(fileList.getAtt_docName()==null){
            holder.file_name.setText(fileList.getAttachFileActualName());
        }else {
            holder.file_name.setText(fileList.getAtt_docName()+"."+ext);
        }


        if (!ext.isEmpty()) {
            if (ext.equals("jpg") || ext.equals("jpeg") || ext.equals("png")) {
                if(fileList.getAttachmentId().equalsIgnoreCase("0")&&fileList.getBitmap1()!=null)
                {
                    holder.image_thumb_nail.setImageBitmap(fileList.getBitmap1());
                }else if(fileList.getAttachmentId().equalsIgnoreCase("0")&&fileList.getBitmap()!=null|| fileList.getBitmap()!=null && !fileList.getBitmap().isEmpty())
                {
                    Bitmap bitmap1= AppUtility.StringToBitMap(fileList.getBitmap());
                    holder.image_thumb_nail.setImageBitmap(bitmap1);
                }
                else {
                    Glide
                            .with(context)
                            .load(App_preference.getSharedprefInstance().getBaseURL() + fileList.getAttachThumnailFileName())
                            .centerCrop()
                            .thumbnail(Glide.with(context).load(R.raw.loader_eot))
                            .placeholder(R.drawable.picture)
                            .into(holder.image_thumb_nail);
                }
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

            if(fileList.getAttachmentId().equalsIgnoreCase("0") || !fileList.getBitmap().isEmpty())
            {
                holder.image_loader.setVisibility(View.VISIBLE);
                Glide.with(context).load("")
                        .thumbnail(Glide.with(context).load(R.raw.loader_eot)).into(holder.image_loader);
            }
            else {
                holder.image_loader.setVisibility(View.GONE);
            }
        }
        holder.layout_doc.setOnClickListener(view -> {
            if(AppUtility.isInternetConnected()) {
                if (!TextUtils.isEmpty(getFileList_res.get(pos).getType())) {
                    if (getFileList_res.get(pos).getType() != null && getFileList_res.get(pos).getType().equals("2") || getFileList_res.get(pos).getType().equals("6")) {
                        DialogUpdateDocuments
                                dialogUpdateDocuments = new DialogUpdateDocuments();

                        if (!TextUtils.isEmpty(ext) && ext.equals("jpg") || ext.equals("jpeg") || ext.equals("png")) {
                            dialogUpdateDocuments.setIsFileImage(true);
                        }

                        dialogUpdateDocuments.setImgPath(
                                getFileList_res.get(pos).getAttachmentId(),
                                getFileList_res.get(pos).getAttachFileName(),
                                holder.file_name.getText().toString().trim(),
                                getFileList_res.get(pos).getDes(),
                                getFileList_res.get(pos).getType(),
                                jobId, isClickDisabled);


                        dialogUpdateDocuments.setOnDocumentUpdate((desc, name) -> {
                            if (desc != null) {
                                getFileList_res.get(pos).setDes(desc);
                            }
                            if (name != null) {
                                getFileList_res.get(pos).setAtt_docName(name);
                                holder.file_name.setText(fileList.getAtt_docName() + "." + ext);
                            }
                            if (jobCompletionActivity != null)
                                jobCompletionActivity.setUpdatedDesc(desc);

                        });
                        dialogUpdateDocuments.show(((AppCompatActivity) context).getSupportFragmentManager(), "dialog");
                    } else if (getFileList_res.get(pos).getType() != null && getFileList_res.get(pos).getType().equals("5"))
                        fileDesc_item_selected.OnItemClick_Document(getFileList_res.get(holder.getBindingAdapterPosition()));
                } else
                    fileDesc_item_selected.OnItemClick_Document(getFileList_res.get(holder.getBindingAdapterPosition()));
            }else {
                if(documentsFragment != null) {
                    documentsFragment.showErrorDialog(LanguageController.getInstance().getMobileMsgByKey(AppConstant.offline_feature_alert));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return getFileList_res.size();
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    public interface FileDesc_Item_Selected {
        void OnItemClick_Document(Attachments attachments);
    }

    class MyView_Holder extends RecyclerView.ViewHolder {
        TextView file_name;
        LinearLayout layout_doc;
        ImageView image_thumb_nail;
        ImageView image_loader;

        public MyView_Holder(View itemView) {
            super(itemView);
            file_name = itemView.findViewById(R.id.tv_file_name);
            layout_doc = itemView.findViewById(R.id.layout_doc);
            image_thumb_nail = itemView.findViewById(R.id.image_thumb_nail);
            image_loader = itemView.findViewById(R.id.image_loader);
        }
    }

}
