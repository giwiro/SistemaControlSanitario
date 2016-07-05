package app.login;

import app.Routes;
import app.admin.AdminDAO;
import app.admin.AdminModel;
import app.utils.Result;
import app.utils.SessionUtil;
import app.utils.ViewUtil;
import spark.Filter;
import spark.Route;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vicky on 03/07/2016.
 */
public class LoginAdminController {
    public static Route serverLoginAdminPage = (request, response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("titulo", "Login Admin");
        return ViewUtil.render(request, model, Routes.Template.LOGIN_ADMIN);
    };

    public static Filter middlewareLoginAdmin = (request, response) -> {
        if (SessionUtil.isLogged(request)) {
            response.redirect("/");
        }
    };

    public static Route handleLoginAdminPost = (request, response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("titulo", "Login");
        //System.out.println("body: " + request.body());
        //System.out.println("query: " + request.queryParams("usuario") + " " + request.queryParams("password"));

        Result<AdminModel> result
                = AdminDAO.validateAdmin(request.queryParams("usuario"),request.queryParams("password"));

        if (result.isSuccess()) {
            //Reconoce automaticamente si es Admin o Regular
            SessionUtil.initSession(request, result.getResult());
            response.redirect(Routes.Web.INDEX);
        }

        model.put("result", result);

        //System.out.println("Login: " + result.isSuccess());

        return ViewUtil.render(request, model, Routes.Template.LOGIN_ADMIN);
    };
}
