package com.geekandroidframework.volley.ext;

import java.io.UnsupportedEncodingException;

import org.json.JSONArray;
import org.json.JSONException;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.toolbox.HttpHeaderParser;

public abstract class JsonArrayRequest extends JsonRequest<JSONArray> {

    public JsonArrayRequest(String url, JSONArray jsonPayload, ErrorListener errorListener) {
        super(url, jsonPayload == null ? null : jsonPayload.toString(), errorListener);
    }

    @Override
    protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString =
                    new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(new JSONArray(jsonString),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException | JSONException e) {
            return Response.error(new ParseError(e));
        }
    }
}
