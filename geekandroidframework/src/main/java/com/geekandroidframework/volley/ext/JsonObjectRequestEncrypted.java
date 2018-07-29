package com.geekandroidframework.volley.ext;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.geekandroidframework.application.BaseApplication;
import com.geekandroidframework.model.BaseModelCM;
import com.geekandroidframework.model.CmVolleyError;
import com.geekandroidframework.model.EncryptionModelRequest;
import com.geekandroidframework.model.EncryptionModelResponse;
import com.geekandroidframework.model.JsonObjectRequestEncryptedModel;
import com.geekandroidframework.utils.EncryptionUtils;
import com.geekandroidframework.utils.StringUtils;
import com.google.gson.Gson;

import java.util.Map;


public class JsonObjectRequestEncrypted<T> extends GsonObjectRequest<T> {

    private IEncryption iJsonObjectRequestCustomCallBack;
    private Class<T> mClass;
    private Response.ErrorListener mErrorListener;
    public static boolean isEncryptionRequired = true; // set true if Encryption required
    public JsonObjectRequestEncryptedModel mJsonObjectRequestEncryptedModel;

    public JsonObjectRequestEncrypted(String url, Map<String, String> mRequestHeaders, String jsonPayload, Class<T> clazz,
                                      Response.ErrorListener errorListener, IEncryption iJsonObjectRequestCustomCallBack) {
        super(url, mRequestHeaders, getEncryptedModel(jsonPayload), encryptionRequired(clazz), errorListener);
        this.mJsonObjectRequestEncryptedModel=new JsonObjectRequestEncryptedModel<>(url,mRequestHeaders,jsonPayload,clazz,errorListener,iJsonObjectRequestCustomCallBack);
        this.iJsonObjectRequestCustomCallBack = iJsonObjectRequestCustomCallBack;
        this.mClass = clazz;
        this.mErrorListener = errorListener;
    }

    private static <T> Class<T> encryptionRequired(Class<T> classz) {
        if (isEncryptionRequired) {
            return (Class<T>) EncryptionModelResponse.class;
        } else {
            return classz;
        }
    }

    public JsonObjectRequestEncrypted(int method, String url, Map<String, String> mRequestHeaders, String jsonPayload, Class<T> clazz, Response.ErrorListener errorListener, IEncryption iJsonObjectRequestCustomCallBack, Gson gson) {
        super(method, url, mRequestHeaders, getEncryptedModel(jsonPayload), encryptionRequired(clazz), errorListener, gson);
        this.mJsonObjectRequestEncryptedModel=new JsonObjectRequestEncryptedModel<>(url,mRequestHeaders,jsonPayload,clazz,errorListener,iJsonObjectRequestCustomCallBack);
        this.iJsonObjectRequestCustomCallBack = iJsonObjectRequestCustomCallBack;
        this.mClass = clazz;
        this.mErrorListener = errorListener;
    }

    private static String getEncryptedModel(String jsonPayload) {
        if (isEncryptionRequired) {
            if (BaseApplication.IS_LOG_ENABLE) {
                if (jsonPayload != null) {
                    Log.e("RequestBodyEncrypted", jsonPayload);
                } else
                    Log.e("RequestBodyEncrypted", " mRequestBody is NULL");
            }
            if(jsonPayload==null){
                return null;
            }else{
                return new Gson().toJson(new EncryptionModelRequest(EncryptionUtils.encryptToBase64String(jsonPayload)));
            }

        } else {
            return jsonPayload;
        }

    }

    @Override
    protected void deliverResponse(T response) {
        if (isEncryptionRequired) {
            String data;
            BaseModelCM baseModelCM;
            EncryptionModelResponse encryptionModel;
            try{
                encryptionModel=(EncryptionModelResponse) response;
                data = EncryptionUtils.decryptToBase64String((encryptionModel).str);
                baseModelCM=new Gson().fromJson(data, BaseModelCM.class);
            }catch (NullPointerException | ClassCastException e){
                e.printStackTrace();
                if (mErrorListener != null)
                    mErrorListener.onErrorResponse(new VolleyError("Data is null after decryption"));
                return;
            }

            if (BaseApplication.IS_LOG_ENABLE) {
                if (data != null) {
                    Log.e("JsonResponseDecrypted", data);
                } else
                    Log.e("JsonResponseDecrypted", " mRequestBody is NULL");
            }
            if (StringUtils.isNullOrEmpty(data)) {
                if (mErrorListener != null)
                    mErrorListener.onErrorResponse(new CmVolleyError(encryptionModel.responseMessage,encryptionModel.status,encryptionModel.responseCode,mJsonObjectRequestEncryptedModel));
            } else {
                try {
                    if(baseModelCM!=null && baseModelCM.status==0 && baseModelCM.responseCode==9){
                        mErrorListener.onErrorResponse(new CmVolleyError(baseModelCM.responseMessage,baseModelCM.status,baseModelCM.responseCode,mJsonObjectRequestEncryptedModel));
                    }else if (iJsonObjectRequestCustomCallBack != null)
                        iJsonObjectRequestCustomCallBack.deliverResponseDecrypted(new Gson().fromJson(data, mClass));
                } catch (Exception e) {
                    if (mErrorListener != null)
                        mErrorListener.onErrorResponse(new CmVolleyError(encryptionModel.responseMessage,encryptionModel.status,encryptionModel.responseCode,mJsonObjectRequestEncryptedModel));
                }

            }
        } else {
            try {
                if (iJsonObjectRequestCustomCallBack != null)
                    iJsonObjectRequestCustomCallBack.deliverResponseDecrypted(response);
                else if (mErrorListener != null)
                    mErrorListener.onErrorResponse(new VolleyError("Data is null after decryption"));
            } catch (Exception e) {
                if (mErrorListener != null)
                    mErrorListener.onErrorResponse(new VolleyError("Data is null after decryption"));
            }

        }

    }
}
