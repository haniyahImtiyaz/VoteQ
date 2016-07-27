package com.ceria.pkl.voteq;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by win 8 on 7/27/2016.
 */
public class JsonObjectRequestWithNull extends JsonRequest<JSONObject> {

    public JsonObjectRequestWithNull(int method, String url, JSONObject jsonRequest,
                                     Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(method, url, (jsonRequest == null) ? null : jsonRequest.toString(), listener,
                errorListener);
    }

    public JsonObjectRequestWithNull(String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener,
                                     Response.ErrorListener errorListener) {
        this(jsonRequest == null ? Request.Method.GET : Request.Method.POST, url, jsonRequest,
                listener, errorListener);
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
            //Allow null
            if (jsonString == null || jsonString.length() == 0) {
                return Response.success(null, HttpHeaderParser.parseCacheHeaders(response));
            }
            return Response.success(new JSONObject(jsonString),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }
}
