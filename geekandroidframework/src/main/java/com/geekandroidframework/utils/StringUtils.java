package com.geekandroidframework.utils;

import android.util.Log;

import java.util.Collection;
import java.util.regex.Pattern;


public abstract class StringUtils {

    private static final String LOG_TAG = "StringUtils";

    public static final String CHARSET_UTF_8 = "UTF-8";
    public static final String MIME_TEXT_HTML = "text/html";

    public static final String EMAIL_REGEX = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
   //public static final String EMAIL_REGEX = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\\\.[A-Za-z]{2,4}";


    public static boolean isNullOrEmpty(final String pStr) {
        return pStr == null || pStr.trim().length() == 0 || pStr.trim().equalsIgnoreCase("null");
    }

    public static boolean isValidEmail(String pEmail, boolean pAllowBlank) {
        if (pAllowBlank && isNullOrEmpty(pEmail)) {
            return true;
        }
        Pattern validRegexPattern = Pattern.compile(EMAIL_REGEX);
        boolean localEmailVaild =  validRegexPattern.matcher(pEmail).matches();
        return checkMailFor2Char(localEmailVaild, pEmail);
    }

    private static boolean checkMailFor2Char(boolean localEmailVaild, String email){
        if(localEmailVaild){
            String[] arr = email.split("@");
            String local1 = arr[1];
            String[] local = local1.split("\\.");
            String s = local[1];
            if(s.length() > 1)
                return true;
            else
                return false;

        }else {
            return localEmailVaild;
        }
    }

    public static int parseInt(String pStr, int pStartIndex, int pEndIndex) {
        if (pStr == null) {
            return 0;
        }
        try {
            if (pStartIndex == -1) {
                return Integer.parseInt(pStr);
            } else {
                return Integer.parseInt(pStr.substring(pStartIndex, pEndIndex));
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "parseInt() pStr: " + pStr + ", Start: " + pStartIndex + ", End: " + pEndIndex);
            return 0;
        }
    }


    public static String getFormattedURL(String url) {
        if (url.indexOf("http://") == 0 || url.indexOf("https://") == 0) {
            return url;
        } else if (url.indexOf("://") == 0) {
            return "http" + url;
        } else if (url.indexOf("//") == 0) {
            return "http:" + url;
        } else {
            return "http://" + url;
        }
    }

    /**
     * @returns
     */
    public static String firstLetterToUpperCase(String pWord) {
        pWord = pWord == null ? "" : pWord;
//        String output = "";
        StringBuilder output=new StringBuilder();
        for (int i = 0; i < pWord.length(); i++) {
            if (i == 0) {
                output.append(Character.toUpperCase(pWord.charAt(i)));
            } else {
                output.append(Character.toLowerCase(pWord.charAt(i)));
            }
        }
        return output.toString();
    }

    /**
     * @return
     * @note maxDigit validation can be implemented by XML
     */
    public static boolean isValidMobileNumber(String pMobileNumber, boolean pPlusSignNeeded, int pMinLength) {
        if (StringUtils.isNullOrEmpty(pMobileNumber)) {
            return false;
        }
        pMobileNumber = pMobileNumber.trim();
        if (pPlusSignNeeded && !pMobileNumber.startsWith("+")) {
            return false;
        }
        if (pMobileNumber.length() < pMinLength) {
            return false;
        }
        return true;
    }

    /**
     * check whether permission exits in active session or not.
     *
     * @param subset
     * @param superset
     * @return
     */
    public static boolean isSubsetOf(Collection<String> subset, Collection<String> superset) {
        for (String string : subset) {
            if (!superset.contains(string)) {
                return false;
            }
        }
        return true;
    }

    public static String getFormatDecimalAmount(float pInputFloat) {
        return getFormatDecimalAmount(pInputFloat, 2);
    }

    public static String getFormatDecimalAmount(float pInputFloat, int pNeededDigitsAfterDecimal) {
        if (pInputFloat == (int) pInputFloat || pNeededDigitsAfterDecimal <= 0) {
            return String.format("%d", (int) pInputFloat);
        } else {
            return String.format("%1." + pNeededDigitsAfterDecimal + "f", pInputFloat);
        }
    }

}
