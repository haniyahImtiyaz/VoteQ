package com.ceria.pkl.voteq.presenter.callback;

/**
 * Created by Haniyah on 12/21/2016.
 */

public interface GetImageCallBack {
    void getImage(String token, String url);

    void onDestroy();
}
