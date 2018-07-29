package com.geekandroidframework.model;

import com.android.volley.VolleyError;

public class CmVolleyError extends VolleyError {
    public int mStatus;
    public int mResponseCode;
    public String mResponseMessage;
    public JsonObjectRequestEncryptedModel mJsonObjectRequestEncryptedModel;

    public CmVolleyError(String responseMessage,int status,int responseCode, JsonObjectRequestEncryptedModel jsonObjectRequestEncryptedModel) {
        super(responseMessage);
        mResponseMessage=responseMessage;
        mStatus=status;
        mResponseCode = responseCode;
        mJsonObjectRequestEncryptedModel = jsonObjectRequestEncryptedModel;
    }
}
