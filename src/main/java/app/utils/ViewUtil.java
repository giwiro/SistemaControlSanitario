package app.utils;

//import org.eclipse.jetty.http.*;
import app.Routes;
import spark.*;
import spark.template.velocity.VelocityTemplateEngine;

import java.util.Map;
import static app.utils.SessionUtil.*;

/**
 * Created by Vicky on 30/06/2016.
 */
public class ViewUtil {

    private static VelocityTemplateEngine engine;

    public static String render(Request request, Map<String, Object> model, String templatePath) {

        if (isAdmin(request)) {
            model.put("currentUser", getSessionCurrentAdmin(request));
            model.put("isAdmin", true);
        }else {
            model.put("currentUser", getSessionCurrentUser(request));
            model.put("isAdmin", false);
        }

        model.put("isLogged", isLogged(request));
        model.put("WebPath", Routes.Web.class); // Access application URLs from templates
        if (model.get("titulo") == null) {
            model.put("titulo", "Sistema de Control Sanitario");
        }
        return getRenderer().render(new ModelAndView(model, templatePath));
    }

    private static VelocityTemplateEngine getRenderer() {
        if (engine == null) {
            engine = new VelocityTemplateEngine();

        }

        return engine;
    }
}
