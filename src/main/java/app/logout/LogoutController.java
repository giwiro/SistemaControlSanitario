package app.logout;

import app.utils.SessionUtil;
import spark.Request;
import spark.Response;
import spark.Route;

/**
 * Created by Vicky on 03/07/2016.
 */
public class LogoutController {

    public static Route handleLogout = (request, response) -> {
        SessionUtil.removeSession(request);
        response.redirect("/");
        return null;
    };
}
