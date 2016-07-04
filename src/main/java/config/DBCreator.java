package config;

import app.admin.AdminDAO;
import app.admin.AdminModel;
import app.local.LocalDAO;
import app.local.LocalModel;
import app.usuario.UsuarioDAO;
import app.usuario.UsuarioModel;
import app.utils.Result;
import org.sql2o.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gi Wah Davalos on 1/07/2016.
 */
public class DBCreator {
    private static final Sql2o sql2o = new Sql2o(DBInfo.getURL(), DBInfo.DB_USER, DBInfo.DB_PASSWORD);

    //final static String createSql = "CREATE TABLE IF NOT EXISTS testtable(id integer primary key, val varchar(50))";

    final static String createUsuarioSql
            = "CREATE TABLE IF NOT EXISTS Usuario" +
            "(" +
            "  ID serial NOT NULL PRIMARY KEY," +
            "  Nombre character varying(45)," +
            "  Apellido character varying," +
            "  Usuario character varying NOT NULL UNIQUE," +
            "  Password character varying" +
            /*"  CONSTRAINT PK_Usuario PRIMARY KEY (ID)" +*/
            ")";

    final static String createAdminSql
            = "CREATE TABLE IF NOT EXISTS Admin" +
            "(" +
            "  ID serial NOT NULL PRIMARY KEY," +
            "  Nombre character varying(45)," +
            "  Apellido character varying," +
            "  Usuario character varying NOT NULL UNIQUE," +
            "  Password character varying" +
            /*"  CONSTRAINT PK_Usuario PRIMARY KEY (ID)" +*/
            ")";

    final static String createLocalSql
            = "CREATE TABLE IF NOT EXISTS Local" +
            "(" +
            "  ID serial NOT NULL PRIMARY KEY," +
            "  RUC character varying(45) NOT NULL," +
            "  Razon_Social character varying(45) NOT NULL," +
            "  Distrito character varying(45) NOT NULL," +
            "  Provincia character varying(45) NOT NULL," +
            "  Departamento character varying(45) NOT NULL," +
            "  Administrador character varying(45)," +
            "  DNI_Administrador character varying(45) NOT NULL," +
            "  Manipuladores character varying(45) NOT NULL," +
            "  ManipuladoresM character varying(45) NOT NULL," +
            "  RacionesDiarias character varying(45) NOT NULL" +
            /*"  CONSTRAINT PK_Local PRIMARY KEY (ID)" +*/
            ")";

    final static String createProgramacionSql
            = "CREATE TABLE IF NOT EXISTS Programacion" +
            "(" +
            "  ID serial NOT NULL PRIMARY KEY," +
            "  fecha date," +
            "  id_usuario serial NOT NULL REFERENCES Usuario," +
            "  id_local serial NOT NULL REFERENCES Local" +
            ")";

    final static String createInspeccionSql
            = "CREATE TABLE IF NOT EXISTS Inspeccion" +
            "(" +
            "  ID serial NOT NULL PRIMARY KEY," +
            "  ubicacion character varying," +
            "  Almacen character varying," +
            "  Cocina character varying," +
            "  Comedor character varying(45)," +
            "  sshh_personal character varying(45)," +
            "  sshh_comensales character varying(45)," +
            "  agua character varying(45)," +
            "  desague character varying(45)," +
            "  residuos character varying(45)," +
            "  plagas character varying(45)," +
            "  equipos character varying(45)," +
            "  utensilios character varying(45)," +
            "  preparacion character varying(45)," +
            "  conservacion character varying(45)," +
            "  manipulador character varying(45)," +
            "  medidas_seguridad character varying(45)," +
            "  id_local serial NOT NULL REFERENCES Local" +
            ")";

    public static void main(String[] args) {

        try(Connection con = sql2o.beginTransaction()) {

            System.out.println("Creating Tables ...");
            // create table and insert something in a transaction
            con.createQuery(createAdminSql).executeUpdate();
            con.createQuery(createUsuarioSql).executeUpdate();
            con.createQuery(createLocalSql).executeUpdate();
            con.createQuery(createProgramacionSql).executeUpdate();
            con.createQuery(createInspeccionSql).executeUpdate();

            System.out.println("Inserting rows ...");
            insertUsuarios(con);
            insertAdmin(con);
            insertLocales(con);
            
            con.commit();
        }catch(Sql2oException e) {
            //e.printStackTrace();
            System.err.println(e.getMessage());
        }
        System.out.println();
    }

    public static void insertAdmin(Connection con) {

        final String getAdminSql
                = "SELECT * FROM Admin" +
                "  WHERE Usuario = :usuario" +
                "  LIMIT 1";

        final String insertAdminSql
                = "INSERT INTO Admin (Nombre, Apellido, Usuario, Password)" +
                "  VALUES (:nombre, :apellido, :usuario, :password)";

        AdminModel admin = new AdminModel("Jorge", "Irey", "jirey", "abc");

        List<AdminModel> adminTmp = con.createQuery(getAdminSql)
                .addParameter("usuario", admin.getUsuario())
                .executeAndFetch(AdminModel.class);

        if (adminTmp.size() == 0) {
            con.createQuery(insertAdminSql)
                    .bind(admin)
                    .executeUpdate();
        }
    }

    public static void insertLocales(Connection con) {

        final String getLocalSql
                = "SELECT * FROM Local" +
                "  WHERE ruc = :ruc" +
                "  LIMIT 1";

        final String insertLocalSql
                = "INSERT INTO Local (" +
                "  ruc," +
                "  razon_social," +
                "  distrito," +
                "  provincia," +
                "  departamento," +
                "  administrador," +
                "  dni_administrador," +
                "  manipuladores," +
                "  manipuladoresm," +
                "  racionesdiarias)" +
                "  VALUES (" +
                "  :ruc," +
                "  :razon_social," +
                "  :distrito," +
                "  :provincia," +
                "  :departamento," +
                "  :administrador," +
                "  :dni_administrador, " +
                "  :manipuladores," +
                "  :manipuladoresm," +
                "  :racionesdiarias)";

        List<LocalModel> locales = new ArrayList<>();

        locales.add(
                new LocalModel(
                        "9712376160",
                        "El Sanguchito",
                        "San Miguel",
                        "Lima",
                        "Lima",
                        "Oscar Sizak",
                        "4589126",
                        "manipuladores",
                        "manipuladoresm",
                        "raciones diarias"));

        locales.add(
                new LocalModel(
                        "7854126951",
                        "Restaurante Puerto Chico",
                        "Callao",
                        "Lima",
                        "Lima",
                        "Liana Ramirez",
                        "5587163",
                        "manipuladores",
                        "manipuladoresm",
                        "raciones diarias"));

        locales.add(
                new LocalModel(
                        "123456789",
                        "Panaderia Tito",
                        "Pueblo Libre",
                        "Lima",
                        "Lima",
                        "Don Tito",
                        "57841269",
                        "manipuladores",
                        "manipuladoresm",
                        "raciones diarias"));

        locales.forEach( localModel -> {
            List<LocalModel> localesTmp = con.createQuery(getLocalSql)
                    .addParameter("ruc", localModel.getRuc())
                    .executeAndFetch(LocalModel.class);


            if (localesTmp.size() == 0) {
                con.createQuery(insertLocalSql)
                        .addParameter("ruc", localModel.getRuc())
                        .addParameter("razon_social", localModel.getRazon_social())
                        .addParameter("distrito", localModel.getDistrito())
                        .addParameter("provincia", localModel.getProvincia())
                        .addParameter("departamento", localModel.getDepartamento())
                        .addParameter("administrador", localModel.getAdministrador())
                        .addParameter("dni_administrador", localModel.getDni_administrador())
                        .addParameter("manipuladores", localModel.getManipuladores())
                        .addParameter("manipuladoresm", localModel.getManipuladoresm())
                        .addParameter("racionesdiarias", localModel.getRacionesdiarias())

                        .executeUpdate();
            }

        });
    }


    public static void insertUsuarios(Connection con) {

        final String getUsuarioSql
                = "SELECT * FROM Usuario" +
                "  WHERE Usuario = :usuario" +
                "  LIMIT 1";

        final String insertUsuarioSql
                = "INSERT INTO Usuario (Nombre, Apellido, Usuario, Password)" +
                "  VALUES (:nombre, :apellido, :usuario, :password)";

        List<UsuarioModel> usuarios = new ArrayList<>();

        usuarios.add(new UsuarioModel("Juan", "Perez", "juanp", "123"));
        usuarios.add(new UsuarioModel("Pepe", "Luna", "pepel", "123"));
        usuarios.add(new UsuarioModel("Gabriel", "Palacios", "gabrielp", "123"));

        usuarios.forEach( usuarioModel -> {
            List<UsuarioModel> usuariosTmp = con.createQuery(getUsuarioSql)
                    .addParameter("usuario", usuarioModel.getUsuario())
                    .executeAndFetch(UsuarioModel.class);

            if (usuariosTmp.size() == 0) {
                con.createQuery(insertUsuarioSql)
                        .bind(usuarioModel)
                        .executeUpdate();
            }

        });
    }
}
