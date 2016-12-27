package com.ceria.pkl.voteq.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.ceria.pkl.voteq.R;
import com.ceria.pkl.voteq.adapter.HomeAdapter;
import com.ceria.pkl.voteq.itemAdapter.HomeItem;
import com.ceria.pkl.voteq.presenter.view.DeleteVoteView;
import com.ceria.pkl.voteq.presenter.view.GetAllVoteView;
import com.ceria.pkl.voteq.presenter.viewinterface.GetAllVoteInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pandhu on 11/07/16.
 */
public class MyVoteList extends Fragment implements GetAllVoteInterface{
    private ListView listViewVote;
    static List<HomeItem> listItem = new ArrayList<>();
    ProgressDialog progressDialog;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int position_id;
    private GetAllVoteView presenter;
    private DeleteVoteView presenterDelete;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.page_my_vote_list, container, false);
        listViewVote = (ListView) rootView.findViewById(R.id.list_my_vote);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh);
        presenter = new GetAllVoteView(this);
        presenterDelete = new DeleteVoteView(this);
        progressDialog = new ProgressDialog(getContext());
//        if (listItem.isEmpty()) {
            visible();
//        }
        listViewVote.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (listItem.get(position).getLabel().equals("Open") || (listItem.get(position).getLabel().equals("Closed") && listItem.get(position).getIdCreator().equals(HomeActivity.token))) {
                    Intent intent = new Intent(getContext(), VoteActivity.class);
                    intent.putExtra("fragment", "myvoteList");
                    intent.putExtra("position", position);
                    intent.putExtra("id", listItem.get(position).getId());
                    intent.putExtra("title", listItem.get(position).getTextTitle());
                    intent.putExtra("count", listItem.get(position).getTextCount());
                    intent.putExtra("status", listItem.get(position).getLabel());
                    intent.putExtra("creator_id", listItem.get(position).getIdCreator());
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getContext(), VoteActivity.class);
                    intent.putExtra("id", listItem.get(position).getId());
                    intent.putExtra("title", listItem.get(position).getTextTitle());
                    intent.putExtra("count", listItem.get(position).getTextCount());
                    intent.putExtra("status", listItem.get(position).getLabel());
                    intent.putExtra("creator_id", listItem.get(position).getIdCreator());
                    startActivity(intent);
                }
            }
        });

        listViewVote.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                if (listItem.get(position).getLabel().equals("Closed")){
                    new AlertDialog.Builder(getContext()).setMessage("Are you sure you want to delete '" + listItem.get(position).getTextTitle() + "' ?")
                            .setTitle("Delete")
                            .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                     presenterDelete.callDeleteVote(HomeActivity.token, listItem.get(position).getId());
                                     position_id = position;
                                }
                            })
                            .setNegativeButton("no", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();
                }
                return true;
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.callGetAllVote();
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
        presenter.callGetAllVote();
    }

    @Override
    public void showProgress() {
        progressDialog.setMessage("Please Wait ...");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public void hideProgress() {
        progressDialog.hide();
    }

    @Override
    public void setCredentialError() {
        Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onNetworkFailure() {
        Toast.makeText(getContext(), "Network Failure", Toast.LENGTH_SHORT).show();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onSucceeded() {
       // listItem = presenter.homeItemList;
        HomeActivity.homeAdapter2 = new HomeAdapter(listItem, getContext());
        swipeRefreshLayout.setRefreshing(false);

        if (presenter.voteItemList.size() == 0) {
            getView().setBackgroundResource(R.drawable.background);
        } else {
            listViewVote.setAdapter(HomeActivity.homeAdapter2);
        }
    }

    @Override
    public void onSucceededDelete() {
        listItem.remove(position_id);
        HomeActivity.homeAdapter2.notifyDataSetChanged();
        Toast.makeText(getContext(), "Your vote has been deleted", Toast.LENGTH_SHORT).show();
    }
}
