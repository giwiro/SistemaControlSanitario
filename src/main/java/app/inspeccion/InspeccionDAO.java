package app.inspeccion;

import app.Application;
import app.utils.Result;
import org.sql2o.Connection;
import org.sql2o.Sql2oException;

/**
 * Created by Vicky on 04/07/2016.
 */
public class InspeccionDAO {

    final static String insertInspeccionSql
            = "INSERT INTO inspeccion (Ubicacion, Almacen, Cocina, id_local)" +
            "  VALUES (:ubicacion, :almacen, :cocina, :id_local)";

    public static Result<InspeccionModel> insertInspeccion(InspeccionModel inspeccion) {
        try(Connection con = Application.sql2o.open()){
            int id = (int)con.createQuery(insertInspeccionSql, true)
                    .bind(inspeccion).executeUpdate().getKey();

            inspeccion.setId(id);
            return new Result<>(true, inspeccion, String.format("Inserted inspeccion #%d", id));

        }catch(Sql2oException e) {
            e.printStackTrace();
            //System.err.println(e.getMessage());
            return new Result<>(false, null, "Error del sistema");
        }
    }
}
