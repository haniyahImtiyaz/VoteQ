package com.ceria.pkl.voteq.presenter.interactor;

import java.util.List;

/**
 * Created by win 8 on 10/23/2016.
 */
public interface CreateVoteInteractor {

    interface OnCreateVoteFinishedListener {
        void onTitleError();

        void onOptionError();

        void onOptionsError();

        void onSuccess();

        void onError();

        void onFailure();
    }

    void createVote(String title, String option, List<String> options, Boolean is_open, OnCreateVoteFinishedListener listener);
}
