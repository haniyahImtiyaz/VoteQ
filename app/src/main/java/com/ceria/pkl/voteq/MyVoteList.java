package com.ceria.pkl.voteq;

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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pandhu on 11/07/16.
 */
public class MyVoteList extends Fragment implements ClientCallbackSignIn {
    private ListView listViewVote;
    private  HomeAdapter homeAdapter;
    static List<HomeItem> listItem = new ArrayList<HomeItem>();
    ProgressDialog progressDialog;
    NetworkService networkService;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.page_my_vote_list, container, false);
        listViewVote = (ListView)rootView.findViewById(R.id.list_my_vote);
        swipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.swipe_refresh);
        if (listItem.isEmpty()){
            visible();
        }
        listViewVote.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(listItem.get(position).getLabel().equals("Open") || (listItem.get(position).getLabel().equals("Close") && listItem.get(position).getIdCreator().equals(HomeActivity.token))){
                    Intent intent = new Intent(getContext(), VoteActivity.class);
                    intent.putExtra("id", listItem.get(position).getId());
                    intent.putExtra("title", listItem.get(position).getTextTitle());
                    intent.putExtra("count",listItem.get(position).getTextCount() );
                    intent.putExtra("status", listItem.get(position).getLabel());
                    intent.putExtra("creator_id", listItem.get(position).getIdCreator());
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(getContext(), VoteActivity.class);
                    intent.putExtra("id", listItem.get(position).getId());
                    intent.putExtra("title", listItem.get(position).getTextTitle());
                    intent.putExtra("count",listItem.get(position).getTextCount() );
                    intent.putExtra("status", listItem.get(position).getLabel());
                    intent.putExtra("creator_id", listItem.get(position).getIdCreator());
                    startActivity(intent);
                }
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                networkService = new NetworkService(getContext());
                networkService.getAllVote(HomeActivity.token, "true", MyVoteList.this);
            }
        });

        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        return rootView;
    }

    @Override
    public void onSucceded() {
        listItem = networkService.getHomeItemList();
        HomeActivity.homeAdapter2 = new HomeAdapter(listItem,getContext());

        progressDialog.dismiss();
        swipeRefreshLayout.setRefreshing(false);

        if (networkService.getHomeItemList().size() == 0){
            getView().setBackgroundResource(R.drawable.background);
        }else{
            listViewVote.setAdapter(HomeActivity.homeAdapter2);
        }
    }

    @Override
    public void onFailed() {
        progressDialog.dismiss();
        Toast.makeText(getContext(), "Network Failure in MyVote List", Toast.LENGTH_SHORT).show();
        swipeRefreshLayout.setRefreshing(false);
    }

    public void visible() {
        networkService = new NetworkService(getContext());
        networkService.getAllVote(HomeActivity.token, "true", MyVoteList.this);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();
    }

}
