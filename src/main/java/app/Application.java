package app;
import app.index.IndexController;
import app.inspeccion.InspeccionController;
import app.inspeccion.InspeccionModel;
import app.programa.ProgramaController;
import app.login.LoginAdminController;
import app.login.LoginController;
import app.logout.LogoutController;
import config.DBInfo;
import org.sql2o.Sql2o;
import spark.Route;

/**
 * Created by Vicky on 30/06/2016.
 */

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import static spark.Spark.*;
import static spark.debug.DebugScreen.*;

public class Application {

    // Declare dependencies
    public static Sql2o sql2o;
    public static DateFormat formatter;

    public static void main(String[] args) {

        sql2o = new Sql2o(DBInfo.getURL(), DBInfo.DB_USER, DBInfo.DB_PASSWORD);
        formatter = new SimpleDateFormat("yyyy-MM-dd");

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

        // Crear Programa
        before(Routes.Web.CREATE_PROGRAMA,      ProgramaController.middlewareCrearPrograma);
        get(Routes.Web.CREATE_PROGRAMA,         ProgramaController.serverCrearProgramaPage);
        post(Routes.Web.CREATE_PROGRAMA,        ProgramaController.handleInsertProgramaPost);

        // Ver Programa Admin
        before(Routes.Web.VER_PROGRAMAS_ADMIN,  ProgramaController.middlewareVerProgramaAdmin);
        get(Routes.Web.VER_PROGRAMAS_ADMIN,     ProgramaController.serveVerProgramasAdminPage);

        // Ver Programa Usuario regular
        before(Routes.Web.VER_PROGRAMAS,        ProgramaController.middlewareVerPrograma);
        get(Routes.Web.VER_PROGRAMAS,           ProgramaController.serveVerProgramasPage);

        // Crear Inspeccion Usuario regular
        before(Routes.Web.CREAR_INSPECCION,     InspeccionController.middlewareCrearInspeccion);
        get(Routes.Web.CREAR_INSPECCION,        InspeccionController.serveCrearInspeccionPage);
        post(Routes.Web.CREAR_INSPECCION,       InspeccionController.handleCrearInspeccion);

        // Logout
        get(Routes.Web.LOGOUT,                  LogoutController.handleLogout);

    }

    private static int getPort(int defaultPort) {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return defaultPort; //return default port if heroku-port isn't set (i.e. on localhost)
    }
}
