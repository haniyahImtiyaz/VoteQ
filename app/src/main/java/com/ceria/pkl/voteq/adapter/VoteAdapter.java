package com.ceria.pkl.voteq.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ceria.pkl.voteq.R;
import com.ceria.pkl.voteq.itemAdapter.VoteItem;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Haniyah on 12/17/2016.
 */

public class VoteAdapter extends RecyclerView.Adapter<VoteAdapter.MyViewHolder> {

    private Context mContext;
    private List<VoteItem> voteItemList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView username, started, title, category, description, status, responder;
        public ImageView voteImage;
        public CircleImageView userImage;


        public MyViewHolder(View view) {
            super(view);
            username = (TextView) view.findViewById(R.id.user);
            started = (TextView) view.findViewById(R.id.date);
            title = (TextView) view.findViewById(R.id.voteTitle);
            status = (TextView) view.findViewById(R.id.labelStatus);
            category = (TextView) view.findViewById(R.id.voteCategory);
            description = (TextView) view.findViewById(R.id.voteDescription);
            responder = (TextView) view.findViewById(R.id.voteResponder);
            voteImage = (ImageView) view.findViewById(R.id.voteImage);
            userImage = (CircleImageView) view.findViewById(R.id.image_circle);

        }
    }

    public VoteAdapter(Context mContext, List<VoteItem> voteItemList) {
        this.mContext = mContext;
        this.voteItemList = voteItemList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        VoteItem voteItem = voteItemList.get(position);
        holder.username.setText(voteItem.getUsername());
        holder.started.setText(voteItem.getStarted());
        holder.title.setText(voteItem.getTitle());
        holder.status.setText(voteItem.getStatus());
        holder.category.setText(voteItem.getCategory());
        holder.description.setText(voteItem.getDescription());
        holder.responder.setText(voteItem.getResponder() + " responder");
        holder.userImage.setImageResource(voteItem.getUserImage());
        holder.voteImage.setImageResource(voteItem.getVoteImage());

    }

    @Override
    public int getItemCount() {
        return voteItemList.size();
    }
}