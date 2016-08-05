package com.ceria.pkl.voteq;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by pandhu on 11/07/16.
 */
public class HomeAdapter extends BaseAdapter {
    private List<HomeItem> homeItems;
    private List<HomeItem> homeItemsArray;
    private LayoutInflater inflater;


    public HomeAdapter(List<HomeItem> homeItems, Context context) {
        this.homeItems = homeItems;
        this.inflater = LayoutInflater.from(context);
        this.homeItemsArray = new ArrayList<>();
        homeItemsArray.addAll(homeItems);
    }


    @Override
    public int getCount() {
        return homeItems.size();
    }

    @Override
    public Object getItem(int position) {
        return homeItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.home_item, parent, false);
            holder = new Holder();
            holder.textViewTitle = (TextView) convertView.findViewById(R.id.txt_vote_title);
            holder.textViewCount = (TextView) convertView.findViewById(R.id.txt_count_vote);
            holder.textViewLabel = (TextView) convertView.findViewById(R.id.label_status);
            holder.linearLayout = (LinearLayout) convertView.findViewById(R.id.layout_home_list);
            holder.circleImageView = (CircleImageView) convertView.findViewById(R.id.image_circle);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        HomeItem homeItem = homeItems.get(position);


        holder.textViewTitle.setText(homeItem.getTextTitle());
        if (homeItem.getTextCount().equals("1") || homeItem.getTextCount().equals("0")){
            holder.textViewCount.setText(homeItem.getTextCount() + " People Voted");
        }else{
            holder.textViewCount.setText(homeItem.getTextCount() + " Peoples Voted");
        }
        holder.textViewLabel.setText(homeItem.getLabel());

        int image;

        if (homeItem.getLabel().equals("Closed")) {
            holder.linearLayout.setBackgroundColor(Color.parseColor("#F44336"));
        } else {
            holder.linearLayout.setBackgroundColor(Color.parseColor("#4CAF50"));
        }

        String lowerTitle = homeItem.getTextTitle().toLowerCase();
        char lowerTitleFirst = lowerTitle.charAt(0);
        if(Character.isDigit(lowerTitleFirst)){
            String imageHold = "a"+String.valueOf(lowerTitleFirst);
            image = convertView.getResources().getIdentifier(imageHold, "drawable", inflater.getContext().getPackageName());
        }else{
           image = convertView.getResources().getIdentifier(String.valueOf(lowerTitleFirst), "drawable", inflater.getContext().getPackageName());
        }
        holder.circleImageView.setImageResource(image);

        return convertView;
    }

    static class Holder {
        TextView textViewTitle;
        TextView textViewCount;
        TextView textViewLabel;
        LinearLayout linearLayout;
        CircleImageView circleImageView;
    }

    public void filter(String textSearch) {
        textSearch = textSearch.toLowerCase(Locale.getDefault());
        homeItems.clear();
        if (textSearch.length() == 0) {
            homeItems.addAll(homeItemsArray);
        } else {
            for (HomeItem key : homeItemsArray) {
                if (key.getTextTitle().toLowerCase(Locale.getDefault())
                        .contains(textSearch)) {
                    homeItems.add(key);
                }
            }
        }
        notifyDataSetChanged();
    }
}
