package app;
import app.index.IndexController;
import app.inspeccion.InspeccionController;
import app.login.LoginAdminController;
import app.login.LoginController;
import app.logout.LogoutController;
import config.DBInfo;
import org.sql2o.Sql2o;
import spark.Route;

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

        get(Routes.Web.INDEX,                   IndexController.serveIndexPage);

        // Login usuario regular
        before(Routes.Web.LOGIN,                LoginController.middlewareLogin);
        get(Routes.Web.LOGIN,                   LoginController.serverLoginPage);
        post(Routes.Web.LOGIN,                  LoginController.handleLoginPost);

        // Login admin
        before(Routes.Web.LOGIN_ADMIN,          LoginAdminController.middlewareLoginAdmin);
        get(Routes.Web.LOGIN_ADMIN,             LoginAdminController.serverLoginAdminPage);
        post(Routes.Web.LOGIN_ADMIN,            LoginAdminController.handleLoginAdminPost);

        // Crear Inspeccion
        before(Routes.Web.CREATE_INSPECCION,    InspeccionController.middlewareCrearInspeccion);
        get(Routes.Web.CREATE_INSPECCION,       InspeccionController.serverCrearInspeccionPage);
        post(Routes.Web.CREATE_INSPECCION,      InspeccionController.handleInsertInspeccionPost);

        // Logout
        get(Routes.Web.LOGOUT,          LogoutController.handleLogout);

        // Crear inspeccion

    }

    private static int getPort(int defaultPort) {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return defaultPort; //return default port if heroku-port isn't set (i.e. on localhost)
    }
}
