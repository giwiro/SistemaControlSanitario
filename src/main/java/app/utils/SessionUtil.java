package app.utils;


import app.usuario.UsuarioModel;
import com.sun.istack.internal.Nullable;
import spark.Request;

/**
 * Created by Vicky on 30/06/2016.
 */
public class SessionUtil {

    public static UsuarioModel getSessionCurrentUser(Request request) {
        return request.session().attribute("currentUser");
    }

    @Nullable
    public static boolean isLogged(Request request) {
        boolean isLogged
                = request.session().attribute("isLogged") == null ? false : request.session().attribute("isLogged");
        return isLogged;
    }

    public static void initSession(Request request, UsuarioModel usuario) {
        request.session().attribute("currentUser", usuario);
        request.session().attribute("isLogged", true);
    }

    public static void removeSession(Request request) {
        request.session().removeAttribute("currentUser");
        request.session().attribute("isLogged", false);
        request.session().attribute("loggedOut", true);
    }
}
