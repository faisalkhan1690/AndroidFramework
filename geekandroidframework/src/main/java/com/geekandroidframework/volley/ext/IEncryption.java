package com.geekandroidframework.volley.ext;

/**
 * <h1><font color="orange">interface</font></h1>
 * Callback class for  getting call from JsonObjectRequestEncrypted
 *
 * @author Faisal Khan
 */
public interface IEncryption<T> {

    /**
     * Method that will work at the place of deliverResponse.
     * When you are using JsonObjectRequestEncrypted at the place of JsonObjectRequest
     * @param response Generic type response
     */
     void deliverResponseDecrypted(T response);
}
