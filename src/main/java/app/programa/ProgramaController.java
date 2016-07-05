package app.programa;

import app.Application;
import app.Routes;
import app.local.LocalDAO;
import app.local.LocalModel;
import app.usuario.UsuarioDAO;
import app.usuario.UsuarioModel;
import app.utils.RequestUtil;
import app.utils.Result;
import app.utils.SessionUtil;
import app.utils.ViewUtil;
import freemarker.template.SimpleDate;
import spark.Filter;
import spark.Route;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Vicky on 03/07/2016.
 */
public class ProgramaController {
    public static Route serverCrearProgramaPage = (request, response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("titulo", "Crear Programacion");

        Result<List<UsuarioModel>> resultUsuarios
                = UsuarioDAO.getAllUsuarios();

        Result<List<LocalModel>> resultLocales
                = LocalDAO.getAllLocales();

        model.put("usuarios", resultUsuarios.getResult());
        model.put("locales", resultLocales.getResult());

        return ViewUtil.render(request, model, Routes.Template.CREATE_PROGRAMA);
    };

    public static Route serveVerProgramasPage = (request, response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("titulo", "Ver Programaciones");

        Result<List<ProgramaRichModel>> resultProgramas
                = ProgramaDAO.getAllProgramasPerUser(SessionUtil.getSessionCurrentUser(request).getId());

        List<ProgramaRichModel> todayProgramas = new ArrayList<>();

        model.put("resultProgramas", resultProgramas);

        int cont = 0;

        //f = formatter.parse(fields.get("fecha"));
        for (ProgramaRichModel programa : resultProgramas.getResult()) {
            /*System.out.println("compating dates " + cont++);
            System.out.println(programa.getFecha());
            System.out.println(formatter.format(new Date()));
            System.out.println(formatter.format(new Date()).equals(programa.getFecha().toString()));*/
            if (Application.formatter.format(new Date()).equals(programa.getFecha().toString())) {
                todayProgramas.add(programa);
            }
        }

        model.put("todayProgramas", todayProgramas);

        return ViewUtil.render(request, model, Routes.Template.VER_PROGRAMAS);
    };

    public static Route serveVerProgramasAdminPage = (request, response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("titulo", "Ver Programaciones");

        Result<List<ProgramaRichModel>> resultProgramas
                = ProgramaDAO.getAllProgramas();

        model.put("resultProgramas", resultProgramas);

        return ViewUtil.render(request, model, Routes.Template.VER_PROGRAMAS_ADMIN);
    };

    public static Filter middlewareCrearPrograma = (request, response) -> {
        if (!SessionUtil.isLogged(request) || !SessionUtil.isAdmin(request)) {
            response.redirect(Routes.Web.LOGIN_ADMIN);
        }
    };

    public static Filter middlewareVerPrograma = (request, response) -> {
        if (!SessionUtil.isLogged(request) || SessionUtil.isAdmin(request)) {
            response.redirect(Routes.Web.LOGIN);
        }
    };

    public static Filter middlewareVerProgramaAdmin = (request, response) -> {
        if (!SessionUtil.isLogged(request) || !SessionUtil.isAdmin(request)) {
            response.redirect(Routes.Web.LOGIN_ADMIN);
        }
    };



    public static Route handleInsertProgramaPost = (request, response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("titulo", "Crear Programa");

        Map<String, String> fields = new HashMap<>();
        fields.put("fecha", request.queryParams("fecha"));
        fields.put("usuario", request.queryParams("usuario"));
        fields.put("local", request.queryParams("local"));

        Result<List<UsuarioModel>> resultUsuarios
                = UsuarioDAO.getAllUsuarios();

        Result<List<LocalModel>> resultLocales
                = LocalDAO.getAllLocales();

        model.put("usuarios", resultUsuarios.getResult());
        model.put("locales", resultLocales.getResult());

        Result resultValidate
                = RequestUtil.validateFields(fields);

        if (!resultValidate.isSuccess()) {
            model.put("resultValidate", resultValidate);
            return ViewUtil.render(request, model, Routes.Template.CREATE_PROGRAMA);
        }


        Date f = null;

        try{
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            f = formatter.parse(fields.get("fecha"));
            //System.out.format("formatted date %s", f.toString());
        }catch(ParseException e) {
            System.err.println(e.getMessage());
        }

        ProgramaModel programa
                = new ProgramaModel(
                f,
                Integer.parseInt(fields.get("usuario")),
                Integer.parseInt(fields.get("local")));

        Result resultInsert
                = ProgramaDAO.insertPrograma(programa);

        model.put("resultInsert", resultInsert);

        return ViewUtil.render(request, model, Routes.Template.CREATE_PROGRAMA);
    };
}
