package app;
import app.index.IndexController;
import app.login.LoginController;
import app.logout.LogoutController;
import config.DBInfo;
import org.sql2o.Sql2o;

/**
 * Created by Vicky on 30/06/2016.
 */

import static spark.Spark.*;
import static spark.debug.DebugScreen.*;

public class Application {

    // Declare dependencies
    public static Sql2o sql2o;

    public static void main(String[] args) {

        sql2o = new Sql2o(DBInfo.getURL(), DBInfo.DB_USER, DBInfo.DB_PASSWORD);

        port(getPort(8080));
        enableDebugScreen();

        get(Routes.Web.INDEX,           IndexController.serveIndexPage);

        // Login
        before(Routes.Web.LOGIN,        LoginController.middlewareLogin);
        get(Routes.Web.LOGIN,           LoginController.serverLoginPage);
        post(Routes.Web.LOGIN,          LoginController.handleLoginPost);

        //Logout
        get(Routes.Web.LOGOUT,          LogoutController.handleLogout);
    }

    private static int getPort(int defaultPort) {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return defaultPort; //return default port if heroku-port isn't set (i.e. on localhost)
    }
}
