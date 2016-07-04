package app.usuario;

import app.Application;
import app.utils.Result;
import org.sql2o.Connection;
import org.sql2o.Sql2oException;

import java.util.List;

/**
 * Created by Vicky on 02/07/2016.
 */
public class UsuarioDAO{

    private static final String getUsuario
            = "SELECT * FROM Usuario" +
            "  WHERE Usuario = :usuario" +
            "  LIMIT 1;";

    public static Result<UsuarioModel> validateUsuario(String usuario, String password) {
        try(Connection con = Application.sql2o.open()) {
            List<UsuarioModel> usuariosTmp
                    = con.createQuery(getUsuario)
                    .addParameter("usuario", usuario)
                    .executeAndFetch(UsuarioModel.class);
            UsuarioModel usuarioLog;
            if (usuariosTmp.size() == 0) {
                return new Result<>(false, null, "No se encontro el usuario");
            }else{
                usuarioLog = usuariosTmp.get(0);
            }

            if (comparePassword(usuarioLog.getPassword(), password)) {
                return new Result<>(true, usuarioLog, "Loggeado statisfactoriamente");
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
