package utils;

import android.text.TextUtils;
import android.util.Patterns;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtils {

    public static boolean validateEmail(String emailStr) {
        return !TextUtils.isEmpty(emailStr) && Patterns.EMAIL_ADDRESS.matcher(emailStr).matches();
    }
    public static boolean isValidMobile(String phone) {
        String expression = "^([0-9\\+]|\\(\\d{1,9}\\))[0-9\\-\\. ]{9,11}$";
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }
}
