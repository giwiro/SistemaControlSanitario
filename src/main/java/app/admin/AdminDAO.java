package app.admin;

import app.Application;
import app.usuario.UsuarioModel;
import app.utils.Result;
import org.sql2o.Connection;
import org.sql2o.Sql2oException;

import java.util.List;

/**
 * Created by Vicky on 03/07/2016.
 */
public class AdminDAO {
    private static final String getAdminSql
            = "SELECT * FROM Admin" +
            "  WHERE Usuario = :usuario" +
            "  LIMIT 1;";

    private final static String insertAdminSql
            = "INSERT INTO Usuario (Nombre, Apellido, Usuario, Password)" +
            "  VALUES (:nombre, :apellido, :usuario, :password);";

    public static Result<AdminModel> insertAdmin(AdminModel admin) {
        try(Connection con = Application.sql2o.open()) {
            int id = (int)con.createQuery(insertAdminSql, true)
                    .bind(admin).executeUpdate().getKey();

            admin.setId(id);

            return new Result<>(true, admin, String.format("Inserted admin #%d", id));

        }catch(Sql2oException e) {
            //e.printStackTrace();
            System.err.println(e.getMessage());
            return new Result<>(false, null, "Error del sistema");
        }
    }

    public static Result<AdminModel> getAdmin(String usuario) {
        try(Connection con = Application.sql2o.open()) {
            List<AdminModel> adminTmp = con.createQuery(getAdminSql)
                    .addParameter("usuario", usuario)
                    .executeAndFetch(AdminModel.class);

            if (adminTmp.size() == 0) {
                return new Result<>(false, null, "No se ha encontrado al usuario");
            }

            return new Result<>(true, adminTmp.get(0), "Se ha obtenido el usuario");


        }catch(Sql2oException e) {
            //e.printStackTrace();
            System.err.println(e.getMessage());
            return new Result<>(false, null, "Error del sistema");
        }
    }

    public static Result<AdminModel> validateAdmin(String usuario, String password) {
        try(Connection con = Application.sql2o.open()) {
            List<AdminModel> adminTmp
                    = con.createQuery(getAdminSql)
                    .addParameter("usuario", usuario)
                    .executeAndFetch(AdminModel.class);
            AdminModel adminLog;
            if (adminTmp.size() == 0) {
                return new Result<>(false, null, "No se encontro el usuario");
            }else{
                adminLog = adminTmp.get(0);
            }

            if (comparePassword(adminLog.getPassword(), password)) {
                return new Result<>(true, adminLog, "Loggeado statisfactoriamente");
            }

            return new Result<>(false, null, "Los passwords no coinciden");


        }catch(Sql2oException e) {
            //e.printStackTrace();
            System.err.println(e.getMessage());
            return new Result<>(false, null, "Error del sistema");
        }
    }

    // Implementacion para comparar passwords, el hash
    // y el password hasheado deben matchear
    public static boolean comparePassword(String hashed, String password) {
        if (hashed.equals(password)) {
            return true;
        }else {
            return false;
        }
    }
}
