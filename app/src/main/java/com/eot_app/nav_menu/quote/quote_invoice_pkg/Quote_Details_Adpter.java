package com.eot_app.nav_menu.quote.quote_invoice_pkg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.eot_app.R;
import com.eot_app.login_next.login_next_model.CompPermission;
import com.eot_app.nav_menu.jobs.job_detail.invoice.invoice_detail_pkg.inv_detail_model.Tax;
import com.eot_app.nav_menu.jobs.job_detail.invoice.invoice_detail_pkg.inv_detail_model.TaxComponents;
import com.eot_app.nav_menu.jobs.job_detail.invoice2list.GenerateInvoiceItemAdpter;
import com.eot_app.nav_menu.jobs.job_detail.invoice2list.itemlist_model.TaxData;
import com.eot_app.nav_menu.quote.quote_invoice_pkg.quote_model_pkg.Quote_ItemData;
import com.eot_app.utility.AppConstant;
import com.eot_app.utility.AppUtility;
import com.eot_app.utility.App_preference;
import com.eot_app.utility.language_support.LanguageController;
import com.eot_app.utility.util_interfaces.GetListData;
import com.eot_app.utility.util_interfaces.MyListItemSelected;
import com.eot_app.utility.util_interfaces.MyListItemSelectedLisT;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Quote_Details_Adpter extends RecyclerView.Adapter<Quote_Details_Adpter.MyViewHolder> {

    final Context context;
    final Set<String> nm_list;
    final Set<String> ids_list;
    private final MyListItemSelectedLisT<String> invoce_rm_item;
    private final ArrayList<String> removeItem = new ArrayList<>();
    private final String taxCalculationType;
    private final String disCalculationType;
    Set<Boolean> chk_pos;
    MyListItemSelected<Quote_ItemData> myListItemSelected;
    private List<Quote_ItemData> invoiceItemList;
    private boolean[] is_pos_checked;
    private List<TaxData> showTaxList = new ArrayList<>();
    private Double subTotal = 0.0;
    private GetListData getListData;
    private boolean getDataOfTaxComponent = true;
    private String getIsAddisDiscBefore = "0";
    String singleTaxId ="0";
    String singleTaxRate ="0";
    boolean isTaxIdSame =false;
    public Quote_Details_Adpter(Context context, List<Quote_ItemData> invoiceItemList, MyListItemSelected<Quote_ItemData> myListItemSelected, MyListItemSelectedLisT<String> invoce_rm_item, String taxCalculationType, String disCalculationType,String getIsAddisDiscBefore) {
        this.invoiceItemList = invoiceItemList;
        this.context = context;
        this.myListItemSelected = myListItemSelected;
        this.invoce_rm_item = invoce_rm_item;
        this.taxCalculationType = taxCalculationType;
        this.disCalculationType = disCalculationType;
        this.is_pos_checked = new boolean[invoiceItemList.size()];
        nm_list = new HashSet<>();
        ids_list = new HashSet<>();
        chk_pos = new HashSet<>();
        this.getListData = (GetListData) context;
        this.getIsAddisDiscBefore = getIsAddisDiscBefore;

    }


    void updateitemlist(List<Quote_ItemData> invoiceItemList) {
        this.is_pos_checked = new boolean[invoiceItemList.size()];
        this.invoiceItemList = invoiceItemList;
        //itemDataArrayList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Quote_Details_Adpter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.invoice_list_item_adpter, viewGroup, false);
        return new Quote_Details_Adpter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final Quote_Details_Adpter.MyViewHolder myViewHolder, final int position) {
        myViewHolder.item_nm_invoice.setText(invoiceItemList.get(position).getInm());
        //  String qty = AppUtility.getRoundoff_amount(invoiceItemList.get(position).getQty());
        //   myViewHolder.qty_invoice.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.qty) + ": " + invoiceItemList.get(position).getQty());
//        myViewHolder.item_price_invoice.setText(AppUtility.getRoundoff_amount(AppUtility.getCalculatedAmountForQuotes(
//                invoiceItemList.get(position).getQty(),
//                invoiceItemList.get(position).getRate(),
//                invoiceItemList.get(position).getDiscount()
//                , invoiceItemList.get(position).getTax(), taxCalculationType)));
        myViewHolder.item_price_invoice.setText(AppUtility.getRoundoff_amount
                (AppUtility.getCalculatedAmountForDiscount(invoiceItemList.get(position).getQty(),
                        invoiceItemList.get(position).getRate(),
                        invoiceItemList.get(position).getDiscount(),
                        invoiceItemList.get(position).getTax(),
                        taxCalculationType,disCalculationType,false).get("Amount")));

        String taxAmount = AppUtility.getRoundoff_amount
                (AppUtility.getCalculatedAmountForDiscount(invoiceItemList.get(position).getQty(),
                        invoiceItemList.get(position).getRate(),
                        invoiceItemList.get(position).getDiscount(),
                        invoiceItemList.get(position).getTax(),
                        taxCalculationType,disCalculationType,false).get("Tax"));
        String _subtotal = AppUtility.getRoundoff_amount
                (AppUtility.getCalculatedAmountForDiscount(invoiceItemList.get(position).getQty(),
                        invoiceItemList.get(position).getRate(),
                        invoiceItemList.get(position).getDiscount(),
                        invoiceItemList.get(position).getTax(),
                        taxCalculationType,disCalculationType,false).get("Subtotal"));

        myViewHolder.item_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myListItemSelected.onMyListitemSeleted(invoiceItemList.get(position));
            }
        });

        /***ShoW unit when Unit permission allow by Administrator ***/
        if (!App_preference.getSharedprefInstance().getLoginRes().getCompPermission().get(0).getUnit().equals("1") && invoiceItemList.get(position).getUnit() != null && !invoiceItemList.get(position).getUnit().equals("")) {
            myViewHolder.qty_invoice.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.unit) + ": " + invoiceItemList.get(position).getUnit());
        } else {
            myViewHolder.qty_invoice.setText(LanguageController.getInstance().getMobileMsgByKey(AppConstant.qty) + ": " + invoiceItemList.get(position).getQty());
        }


        /***remove this Jit Sir on 23 oct 2020**/
        if (App_preference.getSharedprefInstance().getLoginRes().getIsItemDeleteEnable().equals("0")) {
            myViewHolder.checkbox_invoice.setVisibility(View.GONE);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(-10, 0, 0, 0);
            myViewHolder.relativeLayout.setLayoutParams(params);
        } else {
            myViewHolder.checkbox_invoice.setVisibility(View.VISIBLE);
        }

        myViewHolder.checkbox_invoice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                is_pos_checked[position] = b;
                if (b) {
                    ids_list.add(invoiceItemList.get(myViewHolder.getBindingAdapterPosition()).getItemId());
                    //itemDataArrayList.add(invoiceItemList.get(myViewHolder.getBindingAdapterPosition()));
                    removeItem.add(invoiceItemList.get(myViewHolder.getBindingAdapterPosition()).getIqmmId());
                } else {
                    ids_list.remove(invoiceItemList.get(myViewHolder.getBindingAdapterPosition()).getItemId());
                    //itemDataArrayList.remove(invoiceItemList.get(myViewHolder.getBindingAdapterPosition()));
                    removeItem.remove(invoiceItemList.get(myViewHolder.getBindingAdapterPosition()).getIqmmId());
                }
                //invoce_rm_item.onMyListitem_Item_Seleted(itemDataArrayList);
                invoce_rm_item.onMyListitem_Item_Seleted(removeItem);
            }
        });


        myViewHolder.non_billable.setVisibility(View.GONE);


        myViewHolder.checkbox_invoice.setChecked(is_pos_checked[position]);

              /*Jit, 17:43 19-10-21
        Hi @all,
        items me  desc show karwana hai*/
        try {
            if (invoiceItemList.get(position).getDes() != null && !invoiceItemList.get(position).getDes().equals("")) {
                myViewHolder.description.setVisibility(View.VISIBLE);
                myViewHolder.description.setText(invoiceItemList.get(position).getDes());
            } else {
                myViewHolder.description.setVisibility(View.GONE);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        subTotal += Double.parseDouble(_subtotal);
        int taxItemPos=0;
        if(invoiceItemList.get(position).getTax().size()>0) {
//            if(getDataOfTaxComponent) {
                singleTaxId = invoiceItemList.get(position).getTax().get(0).getTaxId();
            if(invoiceItemList.get(position).getTax().get(0).getRate() != null && !invoiceItemList.get(position).getTax().get(0).getRate().isBlank() && invoiceItemList.get(position).getTax().get(0).getRate() != "0" ) {
                singleTaxRate = invoiceItemList.get(position).getTax().get(0).getRate();
            }else {
                singleTaxRate = invoiceItemList.get(position).getTax().get(0).getPercentage();
            }
                for (TaxData tax: showTaxList
                     ) {
                    if(singleTaxId.equals(tax.getTaxId())){
                        isTaxIdSame= true;
                        break;
                    }else {
                        isTaxIdSame= false;
                    }
                    taxItemPos++;
                }

                if (App_preference.getSharedprefInstance().getLoginRes().getTaxShowType().equals("2")) {
                    if (invoiceItemList.get(position).getTax().get(0).getTaxComponents() != null && invoiceItemList.get(position).getTax().get(0).getTaxComponents().size() > 0) {
                        for (TaxComponents tax2 : invoiceItemList.get(position).getTax().get(0).getTaxComponents()
                        ) {
                            List<Tax> tempList = new ArrayList<>();
                            Tax tax = new Tax();
                            tax.setLabel(tax2.getLabel());
                            tax.setTaxId(tax2.getTaxId());
                            tax.setPercentage(tax2.getPercentage());
                            tempList.add(tax);
                            String __taxAmt = AppUtility.getRoundoff_amount
                                    (AppUtility.getCalculatedAmountForDiscount(invoiceItemList.get(position).getQty(),
                                            invoiceItemList.get(position).getRate(),
                                            invoiceItemList.get(position).getDiscount(),
                                            tempList,
                                            taxCalculationType, disCalculationType, false).get("Tax"));
                            for (TaxData taxdata: showTaxList
                            ) {
                                if(taxdata.getTaxId().equals(tax2.getTaxId())){
                                    isTaxIdSame = true;
                                    break;
                                }
                            }
                            if(isTaxIdSame){
                                    sameTax(tax2.getTaxId(),tax2.getLabel(),__taxAmt);
                            }else
                            {
                                    notSameTax(tax2.getPercentage(), tax2.getLabel(), tax2.getTaxId(), __taxAmt);
                            }
                        }
                    }else {
                        if(isTaxIdSame){
                                sameTax(singleTaxId,invoiceItemList.get(position).getTax().get(0).getLabel(),taxAmount);
                        }
                        else{
                                notSameTax(invoiceItemList.get(position).getTax().get(0).getRate(),invoiceItemList.get(position).getTax().get(0).getLabel(),invoiceItemList.get(position).getTax().get(0).getTaxId(),taxAmount);
                        }
                    }
                } else {
                    if(isTaxIdSame){
                            sameTax(singleTaxId,invoiceItemList.get(position).getTax().get(0).getLabel(),taxAmount);
                    }
                    else{
                            notSameTax(invoiceItemList.get(position).getTax().get(0).getRate(),invoiceItemList.get(position).getTax().get(0).getLabel(),invoiceItemList.get(position).getTax().get(0).getTaxId(),taxAmount);
                    }

                }
                if(getIsAddisDiscBefore.equals("1")){
                    getDataOfTaxComponent = false;
                }
            }
//        }

        if(position == invoiceItemList.size()-1){
            List<TaxData> tempList = new ArrayList<>();
            tempList.addAll(showTaxList);
            getListData.setCalculation(subTotal,tempList,false,singleTaxId,"0");
            subTotal =0.0;
            isTaxIdSame = false;
            singleTaxId = "0";
            singleTaxRate = "0";
            showTaxList.clear();
            getDataOfTaxComponent = true;
        }
    }
    public void sameTax(String taxId,String label, String __taxAmt){
        for (TaxData taxData : showTaxList
        ) {
            if (taxData.getTaxId().equals(taxId) && taxData.getLabel().equals(label)) {
                Double subTotalTax = taxData.getTaxAmount() + Double.parseDouble(__taxAmt);
                taxData.setTaxAmount(subTotalTax);
                singleTaxId = taxData.getTaxId();
                singleTaxRate = ""+taxData.getRate();
            }
        }
    }
    public void notSameTax(String rate, String label, String taxId, String __taxAmt){
        TaxData taxData = new TaxData();
        taxData.setRate(Double.parseDouble(rate));
        taxData.setTaxAmount(Double.parseDouble(__taxAmt));
        taxData.setLabel(label);
        taxData.setTaxId(taxId);
        showTaxList.add(taxData);
        singleTaxId = taxData.getTaxId();
        singleTaxRate = ""+taxData.getRate();
    }
    @Override
    public int getItemCount() {
        return invoiceItemList.size();
    }
//    public interface GetListData {
//        public  void setCalculation (Double Subtotal, List<TaxData> listTax);
//    }
    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView item_nm_invoice;
        private final TextView qty_invoice;
        private final TextView item_price_invoice, description;
        final CardView card_invoice_item;
        private final CheckBox checkbox_invoice;
        private final RelativeLayout item_layout;
        private final RelativeLayout relativeLayout;
        private final TextView non_billable;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_nm_invoice = itemView.findViewById(R.id.item_nm_invoice);
            qty_invoice = itemView.findViewById(R.id.qty_invoice);
            item_price_invoice = itemView.findViewById(R.id.item_price_invoice);
            checkbox_invoice = itemView.findViewById(R.id.checkbox_invoice);

            card_invoice_item = itemView.findViewById(R.id.card_invoice_item);
            item_layout = itemView.findViewById(R.id.item_layout);
            relativeLayout = itemView.findViewById(R.id.relativeLayout);

            non_billable = itemView.findViewById(R.id.non_billable);
            description = itemView.findViewById(R.id.description);
            //TODO permission of amount
            CompPermission compPermission = App_preference.getSharedprefInstance().getLoginRes().getCompPermission().get(0);
            if (compPermission.getAmount().equals("1")) {
                item_price_invoice.setVisibility(View.GONE);
            }
            else {
                item_price_invoice.setVisibility(View.VISIBLE);
            }

        }
    }
}
