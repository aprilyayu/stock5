package com.ace.san.db;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.ace.san.R;

import java.util.List;

public class TransList extends ArrayAdapter<Trans> {
    private Activity context;
    List<Trans> trans;

    public TransList(Activity context, List<Trans> trans) {
        super(context, R.layout.activity_translist, trans);
        this.context = context;
        this.trans = trans;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewTrans = inflater.inflate(R.layout.activity_translist, null, true);

        TextView textViewName = (TextView) listViewTrans.findViewById(R.id.textViewName);
        TextView textViewFrom = (TextView) listViewTrans.findViewById(R.id.textViewFrom);

        Trans tran = trans.get(position);
        textViewName.setText(tran.getName());
        textViewFrom.setText(tran.getFrom());

        return listViewTrans;
    }

}
