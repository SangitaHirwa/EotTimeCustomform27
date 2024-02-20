package com.eot_app.nav_menu.quote.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.eot_app.R;
import com.eot_app.nav_menu.quote.quotes_add_item_pkg.item_model_pkg.Quote_Term_Conditon_Model;

import java.util.List;

/**
 * Created by ubuntu on 8/8/18.
 */

public class Term_Condtion_Adapter extends BaseAdapter {

    private final LayoutInflater inflator;
    private List<Quote_Term_Conditon_Model> mCounting;


    public Term_Condtion_Adapter(Context context, List<Quote_Term_Conditon_Model> counting) {
        inflator = LayoutInflater.from(context);
        mCounting = counting;
    }



    public void updtaeList(List<Quote_Term_Conditon_Model> counting) {
        this.mCounting = counting;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mCounting.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = inflator.inflate(R.layout.spinner_item_layout, null);
        TextView tv = convertView.findViewById(R.id.spinner_textview);
        tv.setText(mCounting.get(position).getTacName());


        return convertView;
    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        convertView = super.getDropDownView(position, convertView,
                parent);

        convertView.setVisibility(View.VISIBLE);
        ViewGroup.LayoutParams p = convertView.getLayoutParams();
        if (p != null) {
            p.width = ViewGroup.LayoutParams.MATCH_PARENT;
            p.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        } else
            p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        convertView.setLayoutParams(p);

        return convertView;

    }
}

