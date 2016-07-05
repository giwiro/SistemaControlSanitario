package app;

import lombok.Getter;

/**
 * Created by Vicky on 30/06/2016.
 */
public class Routes {

    public static class Web {
        @Getter public static final String INDEX = "/";
        @Getter public static final String LOGIN = "/login";
        @Getter public static final String LOGOUT = "/logout";
        @Getter public static final String LOGIN_ADMIN = "/admin/login";
        @Getter public static final String CREATE_PROGRAMA = "/admin/crear/programa";
        @Getter public static final String VER_PROGRAMAS_ADMIN = "/admin/ver/programas";
        @Getter public static final String VER_PROGRAMAS = "/ver/programacion";
        @Getter public static final String VER_INSPECCIONES = "/ver/inspecciones";
        @Getter public static final String CREAR_INSPECCION = "/crear/inspeccion/:local_id";
    }

    public static class Template {
        public final static String INDEX = "/templates/index/index.vm";
        public final static String LOGIN = "/templates/login/login.vm";
        public final static String LOGIN_ADMIN = "/templates/login/login_admin.vm";
        public final static String CREATE_PROGRAMA = "/templates/admin/crear_programa.vm";
        public final static String VER_PROGRAMAS_ADMIN = "/templates/admin/ver_programas.vm";
        public final static String VER_PROGRAMAS = "/templates/programas/ver_programas.vm";
        public final static String CREAR_INSPECCION = "/templates/inspeccion/crear_inspeccion.vm";
    }
}
