package app.utils;

//import org.eclipse.jetty.http.*;
import app.Routes;
import spark.*;
import spark.template.handlebars.*;
import java.util.Map;
import static app.utils.SessionUtil.*;

/**
 * Created by Vicky on 30/06/2016.
 */
public class ViewUtil {

    private static HandlebarsTemplateEngine engine;

    public static String render(Request request, Map<String, Object> model, String templatePath) {
        model.put("currentUser", getSessionCurrentUser(request));
        model.put("WebPath", Routes.Web.class); // Access application URLs from templates
        if (model.get("titulo") == null) {
            model.put("titulo", "Sistema de Control Sanitario");
        }
        return getRenderer().render(new ModelAndView(model, templatePath));
    }

    private static HandlebarsTemplateEngine getRenderer() {
        if (engine == null) {
            engine = new HandlebarsTemplateEngine();
        }

        return engine;
    }
}
