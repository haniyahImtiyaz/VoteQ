package com.ceria.pkl.voteq;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
    private HomeAdapter homeAdapter;
    List<HomeItem> listItem;
    ProgressDialog progressDialog;
    NetworkService networkService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.page_my_vote_list, container, false);
        listViewVote = (ListView)rootView.findViewById(R.id.list_my_vote);
   //     homeAdapter = new HomeAdapter(getItem(), getContext());
   //     listViewVote.setAdapter(homeAdapter);

        networkService = new NetworkService(getContext());
        networkService.getAllVote(HomeActivity.token, "true", MyVoteList.this);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        listViewVote.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(getItem().get(position).getLabel().equals("Open")){
                    Intent intent = new Intent(getContext(), VoteActivity.class);
                    intent.putExtra("title", getItem().get(position).getTextTitle());
                    intent.putExtra("count", getItem().get(position).getTextCount());
                    intent.putExtra("status", getItem().get(position).getLabel());
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(getContext(), ResultActivity.class);
                    intent.putExtra("title", getItem().get(position).getTextTitle());
                    intent.putExtra("count", getItem().get(position).getTextCount());
                    intent.putExtra("status", getItem().get(position).getLabel());
                    startActivity(intent);
                }

            }
        });


        return rootView;
    }
    private HomeItem get(String title, String count,String label, int image){
        return new HomeItem(title,count,label,image);
    }
    private List<HomeItem> getItem(){
        List<HomeItem> list = new ArrayList<HomeItem>();
        list.add(get("Judul 1","1200","Open",R.mipmap.ic_launcher));
        list.add(get("Judul 2","1200","Open",R.mipmap.ic_edit_pencil));
        list.add(get("Judul 3","1200","Closed",R.mipmap.ic_launcher));
        list.add(get("Judul 4","1200","Open",R.mipmap.ic_edit_pencil));
        list.add(get("Judul 5","1200","Closed",R.mipmap.ic_launcher));
        return list;
    }

    @Override
    public void onSucceded() {
        listItem = networkService.getMyHomeItemList();
        homeAdapter = new HomeAdapter(listItem,getContext());
        listViewVote.setAdapter(homeAdapter);
        progressDialog.dismiss();
        Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailed() {
        progressDialog.dismiss();
        Toast.makeText(getContext(), "failure", Toast.LENGTH_SHORT).show();
    }
}
