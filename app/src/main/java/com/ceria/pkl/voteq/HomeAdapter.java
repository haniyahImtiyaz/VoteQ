package com.ceria.pkl.voteq;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by pandhu on 11/07/16.
 */
public class HomeAdapter extends BaseAdapter {
    private List<HomeItem> homeItems;
    private LayoutInflater inflater;


    public HomeAdapter(List<HomeItem> homeItems, Context context) {
        this.homeItems = homeItems;
        this.inflater = LayoutInflater.from(context);
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

        if(convertView == null){
            convertView= inflater.inflate(R.layout.home_item,parent,false);
            holder = new Holder();
            holder.textViewTitle = (TextView)convertView.findViewById(R.id.txt_vote_title);
            holder.textViewCount = (TextView)convertView.findViewById(R.id.txt_count_vote);
            holder.textViewLabel = (TextView)convertView.findViewById(R.id.label_status);
            holder.linearLayout = (LinearLayout)convertView.findViewById(R.id.layout_home_list);
            holder.circleImageView = (CircleImageView)convertView.findViewById(R.id.image_circle);
            convertView.setTag(holder);
        }else{
            holder = (Holder)convertView.getTag();
        }

        HomeItem homeItem = homeItems.get(position);

        holder.textViewTitle.setText(homeItem.getTextTitle());
        holder.textViewCount.setText(homeItem.getTextCount()+" Peoples Voted");
        holder.textViewLabel.setText(homeItem.getLabel());

        if(homeItem.getLabel().toString().equals("Closed")){
            holder.linearLayout.setBackgroundColor(Color.parseColor("#F44336"));
        }else{
            holder.linearLayout.setBackgroundColor(Color.parseColor("#4CAF50"));
        }

        holder.circleImageView.setImageResource(homeItem.getImage());

        return convertView;
    }

    static class Holder{
        TextView textViewTitle;
        TextView textViewCount;
        TextView textViewLabel;
        LinearLayout linearLayout;
        CircleImageView circleImageView;
    }
}
