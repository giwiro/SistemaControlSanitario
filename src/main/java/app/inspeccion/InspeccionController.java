package app.inspeccion;

import app.Application;
import app.Routes;
import app.programa.ProgramaDAO;
import app.programa.ProgramaModel;
import app.utils.RequestUtil;
import app.utils.Result;
import app.utils.SessionUtil;
import app.utils.ViewUtil;
import spark.Filter;
import spark.Request;
import spark.Route;
import spark.Session;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vicky on 04/07/2016.
 */
public class InspeccionController {

    public static Route serveCrearInspeccionPage = (request, response) -> {
        //System.out.println("serveCreatInspeccionPage");
        Map<String, Object> model = new HashMap<>();
        model.put("titulo", "Crear Inspeccion");
        int local_id;

        try {
            local_id = Integer.parseInt(request.params("local_id"));
            model.put("local_id", local_id);
        }catch(NumberFormatException e) {
            System.err.println(e.getMessage());
            response.redirect(Routes.Web.getVER_PROGRAMAS());
            return null;
        }

        Result<ProgramaModel> resultPrograma = ProgramaDAO.getProgramaByLocal(local_id);

        if (!resultPrograma.isSuccess()) {
            System.out.println(resultPrograma.getMessage());
            response.redirect(Routes.Web.VER_PROGRAMAS);
        }

        if (!Application.formatter.format(new Date()).equals(resultPrograma.getResult().getFecha().toString())) {
            response.redirect(Routes.Web.VER_PROGRAMAS);
        }

        if (resultPrograma.getResult().getId_usuario() != SessionUtil.getSessionCurrentUser(request).getId()) {
            response.redirect(Routes.Web.VER_PROGRAMAS);
        }

        return ViewUtil.render(request, model, Routes.Template.CREAR_INSPECCION);
        //return "<h1>h</h1>";

    };

    public static Filter middlewareCrearInspeccion = (request, response) -> {
        if (!SessionUtil.isLogged(request) || SessionUtil.isAdmin(request)) {
            response.redirect(Routes.Web.LOGIN);
        }
    };

    public static Route handleCrearInspeccion = (request, response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("titulo", "Crear Inspeccion");
        System.out.println("POST crear inspeccion");
        int local_id;

        try {
            local_id = Integer.parseInt(request.queryParams("local_id"));
        }catch(NumberFormatException e) {
            System.err.println(e.getMessage());
            response.redirect(Routes.Web.getVER_PROGRAMAS());
            return null;
        }


        Map<String, String> fields = new HashMap<>();
        fields.put("id_local", local_id + "");
        fields.put("ubicacion", request.queryParams("ubicacion"));
        fields.put("almacen", request.queryParams("almacen"));
        fields.put("cocina", request.queryParams("cocina"));


        Result resultValidate = RequestUtil.validateFields(fields);
        if (!resultValidate.isSuccess()) {
            model.put("resultValidate", resultValidate);
            return ViewUtil.render(request, model, Routes.Template.CREAR_INSPECCION);
        }

        Result<ProgramaModel> resultPrograma = ProgramaDAO.getProgramaByLocal(local_id);

        if (!resultPrograma.isSuccess()) {
            System.out.println(resultPrograma.getMessage());
            response.redirect(Routes.Web.getVER_PROGRAMAS());
        }

        if (!Application.formatter.format(new Date()).equals(resultPrograma.getResult().getFecha().toString())) {
            System.out.println("El programa no es para hoy");
            response.redirect(Routes.Web.getVER_PROGRAMAS());
        }

        if (resultPrograma.getResult().getId_usuario() != SessionUtil.getSessionCurrentUser(request).getId()) {
            System.out.println("El programa no esta asignado a este usuario");
            response.redirect(Routes.Web.getVER_PROGRAMAS());
        }

        System.out.println("creando el objeto inspeccion");

        InspeccionModel inspeccion = new InspeccionModel();
        inspeccion.setId_local(local_id);
        inspeccion.setUbicacion(fields.get("ubicacion"));
        inspeccion.setAlmacen(fields.get("almacen"));
        inspeccion.setCocina(fields.get("cocina"));

        Result<InspeccionModel> resultInsert
                = InspeccionDAO.insertInspeccion(inspeccion);

        if (!resultInsert.isSuccess()) {
            model.put("resultInsert", resultInsert);
            System.out.println(resultInsert.getMessage());
            return ViewUtil.render(request, model, Routes.Template.CREAR_INSPECCION);
        }else {
            System.out.println(resultInsert.getMessage());
            response.redirect(Routes.Web.getVER_PROGRAMAS());
        }

        return null;
    };
}
