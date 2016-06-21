package com.cloudcommerce.app.utils;

/**
 * Created by developer on 20/06/16.
 */
public class Utils {
    public static boolean isEmailValid(String emailId){
        return  android.util.Patterns.EMAIL_ADDRESS.matcher(emailId).matches();
    }
}
