package com.geekandroidframework.volley.ext;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyLog;
import com.geekandroidframework.application.BaseApplication;

import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Map;

public abstract class JsonRequest<T> extends Request<T> {

    private static final String PROTOCOL_CHARSET = "utf-8";
    private static final String PROTOCOL_CONTENT_TYPE =
            String.format("application/json; charset=%s", PROTOCOL_CHARSET);

    private final String mRequestBody;

    protected Map<String, String> mRequestHeaders;

    private Priority mPriority;

    public JsonRequest(String url, String jsonPayload, ErrorListener errorListener) {
        this(url, Collections.<String, String>emptyMap(), jsonPayload, errorListener);
    }

    public JsonRequest(String url, Map<String, String> mRequestHeaders, String jsonPayload, ErrorListener errorListener) {
        this(jsonPayload == null ? Method.GET : Method.POST, url, mRequestHeaders, jsonPayload, errorListener);

    }

    public JsonRequest(int method, String url, Map<String, String> mRequestHeaders, String jsonPayload, ErrorListener errorListener) {
        super(method, url, errorListener);
        this.mRequestBody = jsonPayload;
        this.mRequestHeaders = mRequestHeaders;

        if(BaseApplication.IS_LOG_ENABLE){
            if(mRequestBody!=null){
                Log.e("RequestBody",mRequestBody);
            }else
                Log.e("RequestBody"," mRequestBody is NULL" );
        }

    }


    public void setPriority(Priority mPriority) {
        this.mPriority = mPriority;
    }

    @Override
    public Priority getPriority() {
        if(mPriority==null){
            mPriority=Priority.IMMEDIATE;
        }
        Log.d("mPriority",mPriority+"");
        return this.mPriority;
    }


    @Override
    public String getBodyContentType() {
        return PROTOCOL_CONTENT_TYPE;
    }

    @Override
    public byte[] getBody() {
        try {

            return mRequestBody == null ? null : mRequestBody.getBytes(PROTOCOL_CHARSET);
        } catch (UnsupportedEncodingException uee) {
            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                    mRequestBody, PROTOCOL_CHARSET);
            return null;
        }
    }

    public Map<String, String> getHeaders() throws AuthFailureError {
        return mRequestHeaders;
    }


}
