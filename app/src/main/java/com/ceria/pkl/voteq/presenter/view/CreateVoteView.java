package com.ceria.pkl.voteq.presenter.view;

import com.ceria.pkl.voteq.presenter.callback.CreateVoteCallBack;
import com.ceria.pkl.voteq.presenter.interactor.CreateVoteInteractor;
import com.ceria.pkl.voteq.presenter.interactorImpl.CreateVoteInteractorImpl;
import com.ceria.pkl.voteq.presenter.viewinterface.CreateVoteInterface;

import java.util.List;

/**
 * Created by win 8 on 10/21/2016.
 */
public class CreateVoteView implements CreateVoteCallBack, CreateVoteInteractor.OnCreateVoteFinishedListener {
    private CreateVoteInterface createVoteInterface;
    private CreateVoteInteractor createVoteInteractor;

    public CreateVoteView(CreateVoteInterface createVoteInterface, String token) {
        this.createVoteInterface = createVoteInterface;
        this.createVoteInteractor = new CreateVoteInteractorImpl(token);
    }

    @Override
    public void callCreateVote(String token, String title, String option, List<String> options, Boolean is_open) {
        if (createVoteInterface != null) {
            createVoteInterface.showProgress();
        }
        createVoteInteractor.createVote(title, option, options, is_open, this);
    }

    @Override
    public void onDestroy() {
        createVoteInterface = null;
    }

    @Override
    public void onTitleError() {
        createVoteInterface.hideProgress();
        createVoteInterface.setTitleEmpty();
    }

    @Override
    public void onOptionError() {
        createVoteInterface.hideProgress();
        createVoteInterface.setOptionNotEmpty();
    }

    @Override
    public void onOptionsError() {
        createVoteInterface.hideProgress();
        createVoteInterface.setOptionsEmpty();
    }

    @Override
    public void onSuccess() {
        createVoteInterface.hideProgress();
        createVoteInterface.onSuccedeed();
        createVoteInterface.navigateToHome();
    }

    @Override
    public void onError() {
        createVoteInterface.hideProgress();
        createVoteInterface.setCredentialError();
    }

    @Override
    public void onFailure() {
        createVoteInterface.hideProgress();
        createVoteInterface.onNetworkFailure();
    }
}
