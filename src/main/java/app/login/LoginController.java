package app.login;

import app.Routes;
import app.utils.ViewUtil;
import spark.Request;
import spark.Response;
import spark.Route;

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

    public static Route handleLoginPost = (request, response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("titulo", "Login");
        return ViewUtil.render(request, model, Routes.Template.LOGIN);
    };
}
