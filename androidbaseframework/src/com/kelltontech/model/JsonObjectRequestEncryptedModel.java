package com.kelltontech.model;

import com.android.volley.Response;
import com.kelltontech.volley.ext.IEncryption;

import java.util.Map;

/**
 * Model clas to keep request model on which volley working
 * @param <T> request class type
 *
 * @author Faisal khan
 */

public class JsonObjectRequestEncryptedModel<T>  {
    public String url;
    public Map<String, String> mRequestHeaders;
    public String jsonPayload;
    public Class<T> clazz;
    public Response.ErrorListener errorListener;
    public IEncryption iJsonObjectRequestCustomCallBack;

    public JsonObjectRequestEncryptedModel(String url, Map<String, String> mRequestHeaders, String jsonPayload, Class<T> clazz, Response.ErrorListener errorListener, IEncryption iJsonObjectRequestCustomCallBack) {
        this.url = url;
        this.mRequestHeaders = mRequestHeaders;
        this.jsonPayload = jsonPayload;
        this.clazz = clazz;
        this.errorListener = errorListener;
        this.iJsonObjectRequestCustomCallBack = iJsonObjectRequestCustomCallBack;
    }
}
