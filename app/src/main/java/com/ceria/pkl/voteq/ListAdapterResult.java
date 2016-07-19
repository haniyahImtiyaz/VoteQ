package com.ceria.pkl.voteq;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;

/**
 * Created by win 8 on 7/2/2016.
 */
public class ListAdapterResult extends BaseAdapter {
    private List<ResultItem> resultItemList;
    private LayoutInflater inflater;

    public ListAdapterResult(List<ResultItem> resultItemList, Context context) {
        this.resultItemList = resultItemList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return resultItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return resultItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.result_item,parent,false);
            holder = new Holder();
            holder.textViewTitle = (TextView) convertView.findViewById(R.id.textTitle);
            holder.textViewValue = (TextView) convertView.findViewById(R.id.textValue);
            holder.textViewPercent = (TextView) convertView.findViewById(R.id.textPercent);
            convertView.setTag(holder);
        }else {
            holder = (Holder)convertView.getTag();
        }

        ResultItem resultItem = resultItemList.get(position);

        holder.textViewTitle.setText(resultItem.getTextTitle());
        holder.textViewValue.setText(resultItem.getTextValue() + " Peoples Voted");
        holder.textViewPercent.setText(resultItem.getTextPercent() + " %");

        return convertView;
    }

    static class Holder{
        TextView textViewTitle;
        TextView textViewValue;
        TextView textViewPercent;
    }
}
