package com.ceria.pkl.voteq.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.ceria.pkl.voteq.R;
import com.ceria.pkl.voteq.adapter.HomeAdapter;
import com.ceria.pkl.voteq.itemAdapter.HomeItem;
import com.ceria.pkl.voteq.presenter.view.GetAllVoteView;
import com.ceria.pkl.voteq.presenter.viewinterface.GetAllVoteInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pandhu on 11/07/16.
 */
public class VoteList extends Fragment implements GetAllVoteInterface {

    static List<HomeItem> listItem = new ArrayList<>();
    ProgressDialog progressDialog;
    private ListView listViewVote;
    private SwipeRefreshLayout swipeRefreshLayout;
    private GetAllVoteView presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.page_vote_list, container, false);
        listViewVote = (ListView) rootView.findViewById(R.id.list_vote);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh);
        progressDialog = new ProgressDialog(getContext());

        presenter = new GetAllVoteView(this);

        //   if (listItem.isEmpty()) {
        visible();
        //  }

        listViewVote.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listItem.get(position).getLabel().equals("Open") || (listItem.get(position).getLabel().equals("Closed") && listItem.get(position).getIdCreator().equals(HomeActivity.token))) {
                    Intent intent = new Intent(getContext(), VoteActivity.class);
                    intent.putExtra("fragment", "voteList");
                    intent.putExtra("position", position);
                    intent.putExtra("id", listItem.get(position).getId());
                    intent.putExtra("title", listItem.get(position).getTextTitle());
                    intent.putExtra("count", listItem.get(position).getTextCount());
                    intent.putExtra("status", listItem.get(position).getLabel());
                    intent.putExtra("creator_id", listItem.get(position).getIdCreator());
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getContext(), ResultActivity.class);
                    intent.putExtra("id", listItem.get(position).getId());
                    intent.putExtra("title", listItem.get(position).getTextTitle());
                    intent.putExtra("count", listItem.get(position).getTextCount());
                    intent.putExtra("status", listItem.get(position).getLabel());
                    intent.putExtra("creator_id", listItem.get(position).getIdCreator());
                    startActivity(intent);
                }

            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.callGetAllVote(HomeActivity.token, false);
                listItem.clear();
            }
        });

        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        return rootView;
    }

    public void visible() {
        presenter.callGetAllVote(HomeActivity.token, false);
        showProgress();
    }

    @Override
    public void onResume() {
        super.onResume();

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
        listItem = presenter.homeItemList;
        HomeActivity.homeAdapter = new HomeAdapter(listItem, getContext());
        listViewVote.setAdapter(HomeActivity.homeAdapter);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onSucceededDelete() {

    }
}
