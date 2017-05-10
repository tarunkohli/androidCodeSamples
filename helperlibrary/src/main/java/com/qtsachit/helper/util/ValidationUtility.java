package com.qtsachit.helper.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The class is Created by Sachit on 03/February/2017
 * <p>
 * Description- This class will
 * <p>
 * Additional notes-
 */
public class ValidationUtility {

    /**
     * Don't let anyone instantiate this class.
     */
    private ValidationUtility() {
        throw new Error("Do not need instantiate!");
    }

    /**
     * This method will check that email is valid or not
     *
     * @param email email address
     * @return true/false
     */
    public static boolean isEmail(String email) {
        Pattern pattern = Pattern
                .compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    /**
     * This method will check that mobile number is valid or not
     *
     * @param data phone number
     * @return true/false
     */
    public static boolean isMobileNumber(String data) {
        String expr = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
        return data.matches(expr);
    }

    /**
     * This method will check that data contains only number and letters
     *
     * @param data
     * @return
     */
    public static boolean isNumberLetter(String data) {
        String expr = "^[A-Za-z0-9]+$";
        return data.matches(expr);
    }

    /**
     * This method will check that data contains only number
     *
     * @param data
     * @return
     */
    public static boolean isNumber(String data) {
        String expr = "^[0-9]+$";
        return data.matches(expr);
    }

    /**
     * This method will check that data contains only letters
     *
     * @param data
     * @return
     */
    public static boolean isLetter(String data) {
        String expr = "^[A-Za-z]+$";
        return data.matches(expr);
    }


    /**
     * This method will check that data is valid postal code or not
     *
     * @param data
     * @return
     */
    public static boolean isPostalCode(String data) {
        String expr = "^[0-9]{6,10}";
        return data.matches(expr);
    }

    /**
     * This method will do length validation
     *
     * @param data
     * @param length
     * @return
     */
    public static boolean isLength(String data, int length) {

        return data != null && data.length() == length;
    }
}
