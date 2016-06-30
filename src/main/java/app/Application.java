package app;
import app.index.IndexController;

/**
 * Created by Vicky on 30/06/2016.
 */

import static spark.Spark.*;
import static spark.debug.DebugScreen.*;

public class Application {
    public static void main(String[] args) {

        port(8080);
        enableDebugScreen();

        get(Routes.Web.INDEX, IndexController.serveIndexPage);
        //get(Routes.Web.INDEX, (request, response) -> "Hello World");
    }
}
