package com.ceria.pkl.voteq;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by pandhu on 12/07/16.
 */
public class NetworkService {
    private final RequestQueue requestQueue;
    private final Context context;

    public NetworkService(Context context) {
        this.context = context;
        this.requestQueue = Volley. newRequestQueue(context);
    }

    public void login(final String email, final String password, final ClientCallback clientCallback){
        String url= context.getResources().getString(R.)
    }

}
