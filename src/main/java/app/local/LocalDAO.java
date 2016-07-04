package app.local;

import app.Application;
import app.utils.Result;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;

/**
 * Created by Vicky on 03/07/2016.
 */
public class LocalDAO {

    public static Sql2o sql2o;

    private static final String getLocalesSql
            = "SELECT * FROM Local;";

    private static final String getLocaByRucSql
            = "SELECT * FROM Local" +
            "  WHERE ruc = :ruc" +
            "  LIMIT 1;";

    private final static String insertLocalSql
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

    public static Result<LocalModel> insertLocal(LocalModel local) {
        try(Connection con = Application.sql2o.open()){
            int id = (int)con.createQuery(insertLocalSql, true)
                    .bind(local).executeUpdate().getKey();

            local.setId(id);
            return new Result<>(true, local, String.format("Inserted local #%d", id));

        }catch(Sql2oException e) {
            e.printStackTrace();
            //System.err.println(e.getMessage());
            return new Result<>(false, null, "Error del sistema");
        }
    }

    public static Result<List<LocalModel>> getLocales() {
        try(Connection con = Application.sql2o.open()){
            List<LocalModel> locales = con.createQuery(getLocalesSql)
                    .executeAndFetch(LocalModel.class);

            if (locales.size() == 0) {
                return new Result<>(false, null, "No se han encontrado locales");
            }

            return new Result<>(true, locales, "Se han encontrado " + locales.size() + " locales");

        }catch(Sql2oException e) {
            //e.printStackTrace();
            System.err.println(e.getMessage());
            return new Result<>(false, null, "Error del sistema");
        }
    }

    public static Result<LocalModel> getLocalByRuc(String ruc) {
        try(Connection con = Application.sql2o.open()){
            List<LocalModel> localTmp = con.createQuery(getLocaByRucSql)
                    .addParameter("ruc", ruc)
                    .executeAndFetch(LocalModel.class);

            if (localTmp.size() == 0) {
                return new Result<>(false, null, "No se ha encontrado el local");
            }

            return new Result<>(true, localTmp.get(0), "Se ha encontrado el local");

        }catch(Sql2oException e) {
            //e.printStackTrace();
            System.err.println(e.getMessage());
            return new Result<>(false, null, "Error del sistema");
        }
    }

}
