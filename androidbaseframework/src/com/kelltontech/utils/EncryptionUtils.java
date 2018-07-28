/*
 *
 *  Proprietary and confidential. Property of Kellton Tech Solutions Ltd. Do not disclose or distribute.
 *  You must have written permission from Kellton Tech Solutions Ltd. to use this code.
 *
 */

package com.kelltontech.utils;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * <h1><font color="orange">EncryptionUtils</font></h1>
 * This class hold encryption related methods
 *
 * @author Faisal Khan
 */
public class EncryptionUtils {

    /**
     * Method to encrypt string to base64
     * @param string string
     * @return base64 string
     */
    public static String encryptToBase64String(String string) {
        try {
            byte[] data = string.getBytes("UTF-8");
            return Base64.encodeToString(data, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
            return string;
        }
    }

    /**
     * Method to decrypt base64 to string
     * @param base64 base64 string
     * @return string
     */
    public static String decryptToBase64String(String base64) {
        try {
            byte[] data = Base64.decode(base64, Base64.DEFAULT);
            return new String(data, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return base64;
        }
    }
}
