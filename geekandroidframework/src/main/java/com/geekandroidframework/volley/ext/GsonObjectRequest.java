package com.geekandroidframework.volley.ext;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.geekandroidframework.application.BaseApplication;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.zip.GZIPInputStream;

public abstract class GsonObjectRequest<T> extends JsonRequest<T> {
    private final Gson mGson;
    private final Class<T> mClazz;
    private boolean headerEncoding;
    private static final String TAG = "GsonObjectRequest";

    public GsonObjectRequest(String url, String jsonPayload, Class<T> clazz, ErrorListener errorListener) {
        this(url, null, jsonPayload, clazz, errorListener);
    }


    public GsonObjectRequest(String url, Map<String, String> mRequestHeaders, String jsonPayload, Class<T> clazz, ErrorListener errorListener) {
        this(url, mRequestHeaders, jsonPayload, clazz, errorListener, new Gson());
    }


    public GsonObjectRequest(String url, String jsonPayload, Class<T> clazz, ErrorListener errorListener, Gson gson) {
        this(url, null, jsonPayload, clazz, errorListener, gson);
    }


    public GsonObjectRequest(String url, Map<String, String> mRequestHeaders, String jsonPayload, Class<T> clazz, ErrorListener errorListener, Gson gson) {
        super(url, mRequestHeaders, jsonPayload, errorListener);
        this.mClazz = clazz;
        headerEncoding = false;
        mGson = gson;
    }

    public GsonObjectRequest(int method, String url, Map<String, String> mRequestHeaders, String jsonPayload, Class<T> clazz, ErrorListener errorListener, Gson gson) {
        super(method, url, mRequestHeaders, jsonPayload, errorListener);
        this.mClazz = clazz;
        headerEncoding = false;
        mGson = gson;
    }


    public GsonObjectRequest(String url, Map<String, String> mRequestHeaders, String jsonPayload, Class<T> clazz, ErrorListener errorListener,boolean headerEncoding) {
        super(url, mRequestHeaders, jsonPayload, errorListener);
        this.mClazz = clazz;
        mGson = new Gson();
        this.headerEncoding = headerEncoding;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json;
                if(!headerEncoding) {
                    json= new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                    if(BaseApplication.IS_LOG_ENABLE) {
                        Log.e("JsonResponse", json);
                    }
                }
                else {
                    json = handleGzipResponse(response);
                }

            return Response.success(mGson.fromJson(json, mClazz), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }
    private String handleGzipResponse(NetworkResponse response) {
        StringBuilder output=new StringBuilder();
        try {
            GZIPInputStream gStream = new GZIPInputStream(new ByteArrayInputStream(response.data));
            InputStreamReader reader = new InputStreamReader(gStream);
            BufferedReader in = new BufferedReader(reader);
            String read;
            while ((read = in.readLine()) != null) {
                output.append(read);
            }
            reader.close();
            in.close();
            gStream.close();
        } catch (IOException e) {

        }
        return output.toString();
    }

}

