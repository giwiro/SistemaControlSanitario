package app.login;

import app.Routes;
import app.usuario.UsuarioDAO;
import app.usuario.UsuarioModel;
import app.utils.Result;
import app.utils.SessionUtil;
import app.utils.ViewUtil;
import spark.Filter;
import spark.Request;
import spark.Response;
import spark.Route;

import javax.swing.text.View;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vicky on 30/06/2016.
 */
public class LoginController {
    public static Route serverLoginPage = (request, response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("titulo", "Login");
        return ViewUtil.render(request, model, Routes.Template.LOGIN);
    };

    public static Filter middlewareLogin = (request, response) -> {
        if (SessionUtil.isLogged(request)) {
            response.redirect("/");
        }
    };

    public static Route handleLoginPost = (request, response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("titulo", "Login");
        //System.out.println("body: " + request.body());
        System.out.println("query: " + request.queryParams("usuario") + " " + request.queryParams("password"));

        Result<UsuarioModel> result
                = UsuarioDAO.validateUsuario(request.queryParams("usuario"),request.queryParams("password"));

        if (result.isSuccess()) {
            SessionUtil.initSession(request, result.getResult());
            response.redirect(Routes.Web.INDEX);
        }

        model.put("success", result.isSuccess());
        model.put("message", result.getMessage());

        System.out.println("Login: " + result.isSuccess());

        return ViewUtil.render(request, model, Routes.Template.LOGIN);
    };
}
