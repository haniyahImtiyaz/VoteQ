package com.ceria.pkl.voteq.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ceria.pkl.voteq.R;
import com.ceria.pkl.voteq.adapter.VoteAdapter;
import com.ceria.pkl.voteq.itemAdapter.VoteItem;
import com.ceria.pkl.voteq.presenter.view.GetAllVoteView;
import com.ceria.pkl.voteq.presenter.viewinterface.GetAllVoteInterface;

import java.util.ArrayList;
import java.util.List;
import android.os.StrictMode;
import android.widget.Toast;

public class CardActivity extends Fragment implements GetAllVoteInterface {

    private RecyclerView recyclerView;
    private VoteAdapter adapter;
    private List<VoteItem> voteItemList = new ArrayList<>();
    ProgressDialog progressDialog;
    private SwipeRefreshLayout swipeRefreshLayout;
    private GetAllVoteView presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_card, container, false);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh);
        progressDialog = new ProgressDialog(getContext());

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        presenter = new GetAllVoteView(this);
        presenter.callGetAllVote();
       // prepareAlbums();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.callGetAllVote();
                voteItemList.clear();
            }
        });
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

      //  VoteItem a = new VoteItem("1", "username", "2016-12-12", "2016-12-31", "vote title", "category", "description........akdjakldjakdjkadjkasdjkasjdkasjdkasjdkasjdkasjiui jhdajh jasdhasjdhasjdh ajdh ajdh ", "ÖPEN", 30);
       // voteItemList.add(a);

        adapter.notifyDataSetChanged();

    }

    @Override
    public void showProgress() {
        progressDialog.setMessage("Please Wait ...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void setCredentialError() {
        Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNetworkFailure() {
        hideProgress();
        Toast.makeText(getContext(), "Network Failure Votelist", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSucceeded() {
        voteItemList = presenter.voteItemList;
        adapter = new VoteAdapter(getContext(), voteItemList, HomeActivity.token);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onSucceededDelete() {

    }
}
