package com.ceria.pkl.voteq;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pandhu on 11/07/16.
 */
public class MyVoteList extends Fragment {
    private ListView listViewVote;
    private HomeAdapter homeAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.page_my_vote_list, container, false);
        listViewVote = (ListView)rootView.findViewById(R.id.list_my_vote);
        homeAdapter = new HomeAdapter(getItem(), getContext());
        listViewVote.setAdapter(homeAdapter);

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
}
