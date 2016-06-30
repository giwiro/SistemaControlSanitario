package app.index;

import app.Routes;
import app.utils.ViewUtil;
import spark.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Vicky on 30/06/2016.
 */
public class IndexController {
    public static Route serveIndexPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        return ViewUtil.render(request, model, Routes.Template.INDEX);
    };
}
