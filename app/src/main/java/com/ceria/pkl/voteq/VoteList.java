package com.ceria.pkl.voteq;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pandhu on 11/07/16.
 */
public class VoteList extends Fragment implements ClientCallbackSignIn {

    private ListView listViewVote;
    private HomeAdapter homeAdapter;
    static List<HomeItem> listItem = new ArrayList<HomeItem>();
    ProgressDialog progressDialog;
    NetworkService networkService;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        Log.d("voteList", "voteListCreate");
        View rootView = inflater.inflate(R.layout.page_vote_list, container, false);
        listViewVote = (ListView)rootView.findViewById(R.id.list_vote);
        if (listItem.size() == 0) {
            visible();
        }
        listViewVote.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                if(listItem.get(position).getLabel().equals("Open")){
                    Intent intent = new Intent(getContext(), VoteActivity.class);
                    intent.putExtra("id", listItem.get(position).getId());
                    intent.putExtra("title", listItem.get(position).getTextTitle());
                    intent.putExtra("count",listItem.get(position).getTextCount() );
                    intent.putExtra("status", listItem.get(position).getLabel());
                    intent.putExtra("creator_id", listItem.get(position).getIdCreator());
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(getContext(), ResultActivity.class);
                    intent.putExtra("id", listItem.get(position).getId());
                    intent.putExtra("title", listItem.get(position).getTextTitle());
                    intent.putExtra("count",listItem.get(position).getTextCount() );
                    intent.putExtra("status", listItem.get(position).getLabel());
                    startActivity(intent);
                }

            }
        });

        return rootView;
    }
    @Override
    public void onSucceded() {
        listItem = networkService.getHomeItemList();
        homeAdapter = new HomeAdapter(listItem,getContext());
        listViewVote.setAdapter(homeAdapter);
        progressDialog.dismiss();
    }

    @Override
    public void onFailed() {
        progressDialog.dismiss();
        Toast.makeText(getContext(), "failure", Toast.LENGTH_SHORT).show();
    }

    public void visible() {
        networkService = new NetworkService(getContext());
        networkService.getAllVote(HomeActivity.token, "false", VoteList.this);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();
    }

}
