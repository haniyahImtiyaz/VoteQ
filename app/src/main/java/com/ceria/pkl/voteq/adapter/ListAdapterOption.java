package com.ceria.pkl.voteq.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ceria.pkl.voteq.itemAdapter.OptionItem;
import com.ceria.pkl.voteq.R;

import java.util.List;

/**
 * Created by win 8 on 7/2/2016.
 */
public class ListAdapterOption extends BaseAdapter {
    private List<OptionItem> optionItemList;
    private LayoutInflater inflater;
    int selectedIndex = -1;

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
            holder.title = (TextView) convertView.findViewById(R.id.titleOption);
            holder.image = (ImageView) convertView.findViewById(R.id.imageOption);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        OptionItem optionItem = optionItemList.get(position);

        holder.title.setText(optionItem.getTitle());
        if(optionItem.getImage() == ""){
            holder.image.setImageResource(R.mipmap.ic_launcher);
        }else {
            Glide.with(convertView.getContext())
                    .load(optionItem.getImage())
                    .into(holder.image);
        }

        RadioButton rbSelect = (RadioButton) convertView.findViewById(R.id.radioButton);
        if(selectedIndex == position){  // check the position to update correct radio button.
            rbSelect.setChecked(true);
        }
        else{
            rbSelect.setChecked(false);
        }

        return convertView;
    }

    static class Holder {
        TextView title;
        ImageView image;
    }

    public void setSelectedIndex(int index) {
        selectedIndex = index;
    }
}

