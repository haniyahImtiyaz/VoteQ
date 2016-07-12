package com.ceria.pkl.voteq;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

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
        this.tabCount= tabCount;
    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                VoteList voteList = new VoteList();
                return voteList;
            case 1:
                MyVoteList myVoteList = new MyVoteList();
                return  myVoteList;
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
