package app.usuario;

import app.Application;
import app.local.LocalModel;
import app.utils.Result;
import org.sql2o.Connection;
import org.sql2o.Sql2oException;

import java.util.List;

/**
 * Created by Vicky on 02/07/2016.
 */
public class UsuarioDAO{

    private static final String getUsuarioSql
            = "SELECT * FROM Usuario" +
            "  WHERE Usuario = :usuario" +
            "  LIMIT 1;";

    private static final String getUsuariosSql
            = "SELECT * FROM Usuario;";

    private final static String insertUsuarioSql
            = "INSERT INTO Usuario (Nombre, Apellido, Usuario, Password)" +
            "  VALUES (:nombre, :apellido, :usuario, :password);";

    public static Result<UsuarioModel> insertUsuario(UsuarioModel usuario) {
        try(Connection con = Application.sql2o.open()) {
            int id = (int)con.createQuery(insertUsuarioSql, true)
                    .bind(usuario).executeUpdate().getKey();

            usuario.setId(id);

            return new Result<>(true, usuario, String.format("Inserted usuario #%d", id));

        }catch(Sql2oException e) {
            //e.printStackTrace();
            System.err.println(e.getMessage());
            return new Result<>(false, null, "Error del sistema");
        }
    }

    public static Result<List<UsuarioModel>> getAllUsuarios() {
        try(Connection con = Application.sql2o.open()) {
            List<UsuarioModel> usuarios
                    = con.createQuery(getUsuariosSql)
                    .executeAndFetch(UsuarioModel.class);

            if (usuarios.size() == 0) {
                return new Result<>(false, null, "No se han encontrado usuarios");
            }

            return new Result<>(true, usuarios, "Se han encontrado " + usuarios.size() + " usuarios");

        }catch(Sql2oException e) {
            //e.printStackTrace();
            System.err.println(e.getMessage());
            return new Result<>(false, null, "Error del sistema");
        }
    }

    public static Result<UsuarioModel> getUsuario(String usuario) {
        try(Connection con = Application.sql2o.open()) {
            List<UsuarioModel> usuariosTmp = con.createQuery(getUsuarioSql)
                    .addParameter("usuario", usuario)
                    .executeAndFetch(UsuarioModel.class);

            if (usuariosTmp.size() == 0) {
                return new Result<>(false, null, "No se ha encontrado al usuario");
            }

            return new Result<>(true, usuariosTmp.get(0), "Se ha obtenido el usuario");


        }catch(Sql2oException e) {
            //e.printStackTrace();
            System.err.println(e.getMessage());
            return new Result<>(false, null, "Error del sistema");
        }
    }

    public static Result<UsuarioModel> validateUsuario(String usuario, String password) {
        try(Connection con = Application.sql2o.open()) {
            List<UsuarioModel> usuariosTmp
                    = con.createQuery(getUsuarioSql)
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
