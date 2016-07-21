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
public class ListAdapterOption extends BaseAdapter {
    private List<OptionItem> optionItemList;
    private LayoutInflater inflater;

    public ListAdapterOption(List<OptionItem> optionItemList, Context context) {
        this.optionItemList = optionItemList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return optionItemList.size();
    }

    @Override
    public OptionItem getItem(int position) {
        return optionItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.option_item, parent, false);
            holder = new Holder();
            holder.textViewOption = (TextView) convertView.findViewById(R.id.text_option);
            holder.textViewValue = (TextView) convertView.findViewById(R.id.text_option_value);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        OptionItem optionItem = optionItemList.get(position);

        holder.textViewOption.setText(optionItem.getOption());
        holder.textViewValue.setText(optionItem.getOptionValue());

        return convertView;
    }

    static class Holder {
        TextView textViewOption;
        TextView textViewValue;
    }
}

