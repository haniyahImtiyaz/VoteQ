package com.ceria.pkl.voteq;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pandhu on 12/07/16.
 */
public class NetworkService {
    private final RequestQueue requestQueue;
    private final Context context;
    private String auth_token;
    private final List<HomeItem> homeItemList = new ArrayList<HomeItem>();
    private final List<ResultItem> resultItemList = new ArrayList<ResultItem>();
    private String date;

    public NetworkService(Context context) {
        this.context = context;
        this.requestQueue = Volley.newRequestQueue(context);
    }

    public List<HomeItem> getHomeItemList() {
        return homeItemList;
    }

    public void setHomeItemList(String id, String title, String count, String label, int image) {
        homeItemList.add(get(id, title, count, label, image));
    }

    private HomeItem get(String id, String title, String count, String label, int image) {
        return new HomeItem(id, title, count, label, image);

    }

    public List<ResultItem> getResultItemList() {
        return resultItemList;
    }

    public void setResultItemList(String id, String title, String count, String percentage) {
        resultItemList.add(new ResultItem(id, title, count, percentage));
    }

    public void signUp(final String email, final String pwd, final String pwdConfirm, final ClientCallback clientCallback) {
        String url = context.getResources().getString(R.string.base_url) + context.getResources().getString(R.string.sign_up);
        StringRequest signUpRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject logResponse = new JSONObject(response);
                    Log.d("signUpPost", "response " + logResponse.toString(2));
                    String status = logResponse.getString("status");
                    if (status.equals("error")) {
                        JSONObject dataResponse = logResponse.getJSONObject("data");
                        JSONArray arrayResponseEmail = dataResponse.getJSONArray("email");
                        JSONArray arrayResponsePassword = dataResponse.getJSONArray("password");
                        JSONArray arrayResponsePasswordConfirm = dataResponse.getJSONArray("password_confirmation");
                        String responseEmail = arrayResponseEmail.getString(0);
                        String responsePwd = arrayResponseEmail.getString(0);
                        if (responseEmail.equals("has already been taken")) {
                            clientCallback.onEmailSame();
                        } else {
                            clientCallback.onFailed();
                        }
                    }
                } catch (JSONException e) {
                    Log.d("signUpPost", "response " + response);
                    e.printStackTrace();
                    clientCallback.onFailed();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("signUpPost", "error signUp " + error.toString());
                clientCallback.onFailed();

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Content-Type", "application/x-www-form-urlencoded");
                return header;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user[email]", email);
                params.put("user[password]", pwd);
                params.put("user[password_confirmation]", pwdConfirm);
                return params;
            }
        };
        requestQueue.add(signUpRequest);
    }

    public void login(final String email, final String password, final ClientCallbackSignIn clientCallback) {
        String url = context.getResources().getString(R.string.base_url) + context.getResources().getString(R.string.sign_in);
        StringRequest loginRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject logResponse = new JSONObject(response);
                    Log.d("response", logResponse.toString(2));
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    JSONObject dataResponse = logResponse.getJSONObject("data");
                    auth_token = dataResponse.getString("auth_token");

                    if (status.equals("success")) {
                        clientCallback.onSucceded();
                    } else {
                        clientCallback.onFailed();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.d("Response", response);
                    clientCallback.onFailed();
                }
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", error.toString());
                clientCallback.onFailed();
            }
        }) {
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Context-Type", "application/x-www-form-urlencoded");
                return header;
            }

            public Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("session[email]", email);
                params.put("session[password]", password);
                return params;
            }
        };
        requestQueue.add(loginRequest);
    }

    public String getAuth_token() {
        return auth_token;
    }

    public void createVote(final String token, final String title, final ArrayList<String> option, final boolean is_open, final ClientCallbackSignIn clientCallback) {
        String url = context.getResources().getString(R.string.base_url) + context.getResources().getString(R.string.create_vote);
        StringRequest createVoteRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject logResponse = new JSONObject(response);
                    Log.d("createVote", "response " + logResponse.toString(2));
                    String status = logResponse.getString("status");
                    if (status.equals("success")) {
                        clientCallback.onSucceded();
                    } else {
                        clientCallback.onFailed();
                    }
                } catch (JSONException e) {
                    Log.d("createVote", "response " + response);
                    e.printStackTrace();
                    clientCallback.onFailed();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("createVote", "error create vote " + error.toString());
                clientCallback.onFailed();

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", token);
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("title", title);
                for (int i = 0; i < option.size(); i++) {
                    params.put("options[]", option.get(i));
                }
                params.put("is_open", String.valueOf(is_open));
                return params;
            }
        };
        requestQueue.add(createVoteRequest);
    }

    public void getAllVote(final String token, final String current_user, final ClientCallbackSignIn clientCallback) {
        String url;
        if (current_user.equals("true")) {
            url = context.getResources().getString(R.string.base_url) + context.getResources().getString(R.string.get_myvote);
        } else {
            url = context.getResources().getString(R.string.base_url) + context.getResources().getString(R.string.create_vote);
        }
        StringRequest getAllVoteRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject logResponse = new JSONObject(response);
                    Log.d("getAllVote", "response " + logResponse.toString(2));
                    String status = logResponse.getString("status");
                    JSONObject data = logResponse.getJSONObject("data");
                    JSONArray vote = data.getJSONArray("vote");
                    for (int i = 0; i < vote.length(); i++) {
                        JSONObject dataVote = vote.getJSONObject(i);
                        String title = dataVote.getString("title");
                        String voter = dataVote.getString("voter_count");
                        String vote_id = dataVote.getString("id");
                        String label;
                        if (dataVote.getBoolean("status") == true) {
                            label = "Open";
                        } else {
                            label = "Closed";
                        }

                        setHomeItemList(vote_id, title, voter, label, R.mipmap.ic_launcher);

                    }
                    if (status.equals("OK")) {
                        clientCallback.onSucceded();
                    } else {
                        clientCallback.onFailed();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    clientCallback.onFailed();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("getAllVote", "error get all vote " + error.toString());
                clientCallback.onFailed();

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Authorization", token);
                return header;
            }

        };

        requestQueue.add(getAllVoteRequest);

    }

    public void specificVote(final String token, final String id, final ClientCallbackSignIn clientCallback) {
        String url = context.getResources().getString(R.string.base_url) + context.getResources().getString(R.string.create_vote) + "/" + id;
        StringRequest specificVote = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject logResponse = new JSONObject(response);
                    Log.d("specificVote", "response " + logResponse.toString(2));
                    String status = logResponse.getString("status");
                    JSONObject data = logResponse.getJSONObject("data");
                    JSONObject vote = data.getJSONObject("vote");
                    date = vote.getString("created_at");
                    Boolean label = vote.getBoolean("status");
                    JSONArray options = vote.getJSONArray("options");
                    for (int i = 0; i < options.length(); i++) {
                        JSONObject dataOptions = options.getJSONObject(i);
                        String id = dataOptions.getString("id");
                        String title = dataOptions.getString("title");
                        String count = dataOptions.getString("count");
                        String percentage = dataOptions.getString("percentage");
                        setResultItemList(id,title, count, percentage);
                    }

                    if (status.equals("OK")) {
                        clientCallback.onSucceded();
                    } else {
                        clientCallback.onFailed();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    clientCallback.onFailed();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("specificVote", "specific vote" + error.toString());
                clientCallback.onFailed();

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Authorization", token);
                return header;
            }
        };

        requestQueue.add(specificVote);
    }
    public Date getDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM,dd,yyyy");
        Date dateText = new Date();
        try {
            dateText = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Log.d("getDate", dateText.toString());
        return dateText;
    }
    public void givingVote(final String token, final int vote_id, final int option_id, final ClientCallbackSignIn clientCallback) {
        String url = context.getResources().getString(R.string.base_url) + context.getResources().getString(R.string.giving_vote);
        StringRequest givingVoteRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject logResponse = new JSONObject(response);
                    Log.d("givingVote", "response " + logResponse.toString(2));
                    String status = logResponse.getString("status");
                    if (status.equals("success")) {
                        clientCallback.onSucceded();
                    } else {
                        clientCallback.onFailed();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    clientCallback.onFailed();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("givingVote", "error giving vote " + error.toString());
                clientCallback.onFailed();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Authorization", token);
                return header;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("vote_id", String.valueOf(vote_id));
                params.put("vote_option_id", String.valueOf(option_id));
                return params;
            }
        };
        requestQueue.add(givingVoteRequest);
    }
}