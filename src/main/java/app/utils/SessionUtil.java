package app.utils;


import app.admin.AdminModel;
import app.usuario.UsuarioModel;
import spark.Request;

/**
 * Created by Vicky on 30/06/2016.
 */
public class SessionUtil {

    public static UsuarioModel getSessionCurrentUser(Request request) {
        return request.session().attribute("currentUser");
    }

    public static AdminModel getSessionCurrentAdmin(Request request) {
        return request.session().attribute("currentUser");
    }

    public static boolean isLogged(Request request) {
        boolean isLogged
                = request.session().attribute("isLogged") == null ? false : request.session().attribute("isLogged");
        return isLogged;
    }

    public static void initSession(Request request, UsuarioModel usuario) {
        request.session().attribute("currentUser", usuario);
        request.session().attribute("isLogged", true);
        request.session().attribute("userType", "regular");
    }

    public static void initSession(Request request, AdminModel usuario) {
        request.session().attribute("currentUser", usuario);
        request.session().attribute("isLogged", true);
        request.session().attribute("userType", "admin");
    }

    public static boolean isAdmin(Request request) {
        String type = request.session().attribute("userType");
        if (type != null && type.equals("admin")) {
            return true;
        }

        return false;
    }

    public static void removeSession(Request request) {
        request.session().removeAttribute("currentUser");
        request.session().attribute("isLogged", false);
        request.session().attribute("loggedOut", true);
        request.session().removeAttribute("userType");
    }
}
