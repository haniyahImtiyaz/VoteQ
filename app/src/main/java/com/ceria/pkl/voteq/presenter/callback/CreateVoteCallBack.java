package com.ceria.pkl.voteq.presenter.callback;

import java.util.List;

/**
 * Created by win 8 on 10/21/2016.
 */
public interface CreateVoteCallBack {
    void callCreateVote(String token, String title, String option, List<String> options, Boolean is_open);

    void onDestroy();
}
