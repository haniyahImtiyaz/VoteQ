package com.ceria.pkl.voteq.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ceria.pkl.voteq.R;
import com.ceria.pkl.voteq.adapter.VoteAdapter;
import com.ceria.pkl.voteq.itemAdapter.VoteItem;

import java.util.ArrayList;
import java.util.List;

public class CardActivity extends Fragment {

    private RecyclerView recyclerView;
    private VoteAdapter adapter;
    private List<VoteItem> voteItemList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_card, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        voteItemList = new ArrayList<>();
        adapter = new VoteAdapter(getContext(), voteItemList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        prepareAlbums();
        return rootView;
    }


    private void prepareAlbums() {
        int[] userImage = new int[]{
                R.drawable.background,
                R.drawable.background,
                R.drawable.background,
                R.drawable.background,
                R.drawable.background};

        int[] voteImage = new int[]{
                R.mipmap.ic_launcher,
                R.mipmap.ic_launcher,
                R.mipmap.ic_launcher,
                R.mipmap.ic_launcher,
                R.mipmap.ic_launcher};

        VoteItem a = new VoteItem("1", "username", "2016-12-12", "2016-12-31", "vote title", "category", "description........akdjakldjakdjkadjkasdjkasjdkasjdkasjdkasjdkasjiui jhdajh jasdhasjdhasjdh ajdh ajdh ", "ÖPEN", "30", userImage[0], voteImage[0]);
        voteItemList.add(a);
        voteItemList.add(a);
        voteItemList.add(a);
        voteItemList.add(a);
        voteItemList.add(a);


        adapter.notifyDataSetChanged();
    }


}
