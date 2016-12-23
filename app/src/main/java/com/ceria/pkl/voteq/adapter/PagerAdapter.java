package com.ceria.pkl.voteq.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ceria.pkl.voteq.activity.CardActivity;
import com.ceria.pkl.voteq.activity.MyVoteList;
import com.ceria.pkl.voteq.activity.VoteList;

/**
 * Created by pandhu on 11/07/16.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {
    //integer to count number of tabs
    int tabCount;

    //Constructor to the class
    public PagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        //Initializing tab count
        this.tabCount = tabCount;
    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                CardActivity cardActivity = new CardActivity();
                return cardActivity;
            case 2:
                MyVoteList myVoteList = new MyVoteList();
                return myVoteList;
            case 1:
                VoteList voteList = new VoteList();
                return voteList;
            default:
                return null;
        }
    }

    //Overriden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return tabCount;
    }
}
