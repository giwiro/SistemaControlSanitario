package app.inspeccion;

import app.Routes;
import app.utils.SessionUtil;
import app.utils.ViewUtil;
import spark.Filter;
import spark.Route;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vicky on 03/07/2016.
 */
public class InspeccionController {
    public static Route serverCrearInspeccionPage = (request, response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("titulo", "Crear Inspeccion");
        return ViewUtil.render(request, model, Routes.Template.CREATE_INSPECCION);
    };

    public static Filter middlewareCrearInspeccion = (request, response) -> {
        if (!SessionUtil.isLogged(request) || !SessionUtil.isAdmin(request)) {
            response.redirect("/");
        }
    };

    public static Route handleInsertInspeccionPost = (request, response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("titulo", "Crear Inspeccion");
        return null;
        /*Result<AdminModel> result
                = AdminDAO.validateAdmin(request.queryParams("usuario"),request.queryParams("password"));

        if (result.isSuccess()) {
            //Reconoce automaticamente si es Admin o Regular
            SessionUtil.initSession(request, result.getResult());
            response.redirect(Routes.Web.INDEX);
        }

        model.put("result", result);

        System.out.println("Login: " + result.isSuccess());

        return ViewUtil.render(request, model, Routes.Template.LOGIN);*/
    };
}
