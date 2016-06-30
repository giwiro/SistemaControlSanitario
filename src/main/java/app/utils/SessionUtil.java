package app.utils;


import spark.Request;

/**
 * Created by Vicky on 30/06/2016.
 */
public class SessionUtil {

    public static String getSessionCurrentUser(Request request) {
        return request.session().attribute("currentUser");
    }
}
