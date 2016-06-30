package app;

import lombok.Getter;

/**
 * Created by Vicky on 30/06/2016.
 */
public class Routes {

    public static class Web {
        @Getter public static final String INDEX = "/";
    }

    public static class Template {
        public final static String INDEX = "/index/index.hbs";
    }
}
