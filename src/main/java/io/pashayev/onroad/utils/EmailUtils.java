package io.pashayev.onroad.utils;

public class EmailUtils {

    public static String getEmailMessage(String name, String host, String token) {
        return "Hello " + name + "\n\nYour new account has been created. Please click the link below to verify your account. \n\n" + getVerificationUrl(host, token);
    }

    private static String getVerificationUrl(String host, String token) {
        return host + "/user/verify?token=" + token;
    }
}
