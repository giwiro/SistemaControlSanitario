package config;

import org.sql2o.*;

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
            "  Apellido character varying" +
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
            "  Administrados character varying(45)," +
            "  DNI character varying(45) NOT NULL," +
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
        System.out.println("Creating Tables ...");
        try(Connection con = sql2o.beginTransaction()) {
            // create table and insert something in a transaction
            con.createQuery(createUsuarioSql).executeUpdate();
            con.createQuery(createLocalSql).executeUpdate();
            con.createQuery(createProgramacionSql).executeUpdate();
            con.createQuery(createInspeccionSql).executeUpdate();


            con.commit();
        }catch(Sql2oException e) {
            //e.printStackTrace();
            System.err.println(e.getMessage());
        }
        System.out.println();
    }
}
