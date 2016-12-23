package com.ceria.pkl.voteq.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.ceria.pkl.voteq.R;
import com.ceria.pkl.voteq.activity.VoteActivity;
import com.ceria.pkl.voteq.itemAdapter.VoteItem;

import java.io.File;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Haniyah on 12/17/2016.
 */

public class VoteAdapter extends RecyclerView.Adapter<VoteAdapter.MyViewHolder> {

    private Context mContext;
    private List<VoteItem> voteItemList;
    private String token;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView username, started, title, category, description, status, responder;
        public ImageView voteImage;
        public CircleImageView userImage;
        public Button btn;


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
            btn = (Button) view.findViewById(R.id.btnVote);

        }
    }

    public VoteAdapter(Context mContext, List<VoteItem> voteItemList, String token) {
        this.mContext = mContext;
        this.voteItemList = voteItemList;
        this.token = token;
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
        holder.started.setText(voteItem.getStarted() + " - " + voteItem.getEnded());
        holder.title.setText(voteItem.getTitle());
        holder.status.setText(voteItem.getStatus());
        holder.category.setText(voteItem.getCategory());
        holder.description.setText(voteItem.getDescription());
        holder.responder.setText(voteItem.getResponder() + " responder");

        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, VoteActivity.class);
                i.putExtra("id", voteItem.getId());
                mContext.startActivity(i);
            }
        });

        File f = new File(mContext.getExternalFilesDir(null) + File.separator + "avatar " + voteItem.getUsername() + ".jpg");
        Bitmap b = BitmapFactory.decodeFile(f.getAbsolutePath());

        LazyHeaders.Builder builder = new LazyHeaders.Builder()
                .addHeader("Authorization", "Token token=" + token);
        GlideUrl glideUrl = new GlideUrl("https://electa-engine.herokuapp.com" + voteItem.getUserImage(), builder.build());

        Glide.with(mContext)
                .load(glideUrl)
                .into(holder.userImage);

        if (voteItem.getVoteImage() == "") {
            String lowerTitle = voteItem.getTitle().toLowerCase();
            char lowerTitleFirst = lowerTitle.charAt(0);
            if (Character.isDigit(lowerTitleFirst)) {
                String imageHold = "activity_card" + String.valueOf(lowerTitleFirst);
                holder.voteImage.setImageResource(mContext.getResources().getIdentifier(imageHold, "drawable", mContext.getPackageName()));
            } else {
                holder.voteImage.setImageResource(mContext.getResources().getIdentifier(String.valueOf(lowerTitleFirst), "drawable", mContext.getPackageName()));
            }
        } else {
            Glide.with(mContext)
                    .load(voteItem.getVoteImage())
                    .into(holder.voteImage);
        }
    }

    @Override
    public int getItemCount() {
        return voteItemList.size();
    }

}