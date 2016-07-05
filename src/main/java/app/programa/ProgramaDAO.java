package app.programa;

import app.Application;
import app.local.LocalModel;
import app.usuario.UsuarioModel;
import app.utils.Result;
import org.sql2o.Connection;
import org.sql2o.Sql2oException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Vicky on 04/07/2016.
 */
public class ProgramaDAO {

    final static String getAllProgramasSql
            = "SELECT * FROM Programacion";

    final static String getProgramaByLocalSql
            = "SELECT * FROM Programacion" +
            "  WHERE id_local = :id";

    final static String getAllProgramasJoinPerUserSql
            = "SELECT " +
            "  programacion.id," +
            "  programacion.fecha," +

            "  usuario.id AS usuario_id," +
            "  usuario.nombre AS usuario_nombre," +
            "  usuario.apellido AS usuario_apellido," +

            "  local.id AS local_id," +
            "  local.razon_social AS local_razon_social" +

            "  FROM Programacion" +
            "  INNER JOIN Usuario" +
            "    ON programacion.id_usuario = usuario.id" +
            "  INNER JOIN Local" +
            "    ON programacion.id_local = local.id" +
            "  WHERE programacion.id_usuario = :id";

    final static String getAllProgramasJoinSql
            = "SELECT " +
            "  programacion.id," +
            "  programacion.fecha," +

            "  usuario.id AS usuario_id," +
            "  usuario.nombre AS usuario_nombre," +
            "  usuario.apellido AS usuario_apellido," +

            "  local.id AS local_id," +
            "  local.razon_social AS local_razon_social" +

            "  FROM Programacion" +
            "  INNER JOIN Usuario" +
            "    ON programacion.id_usuario = usuario.id" +
            "  INNER JOIN Local" +
            "    ON programacion.id_local = local.id;";

    final static String insertProgramaSql
            = "INSERT INTO Programacion (Fecha, Id_usuario, Id_local)" +
            "  VALUES (:fecha, :id_usuario, :id_local)";

    public static Result<ProgramaModel> getProgramaByLocal(int id) {

        try(Connection con = Application.sql2o.open()) {

            List<ProgramaModel> programa
                    = con.createQuery(getProgramaByLocalSql)
                    .addParameter("id", id)
                    .executeAndFetch(ProgramaModel.class);

            if (programa.size() == 0) {
                return new Result<>(false, null, "No se han encontrado programas");
            }

            return new Result<>(true, programa.get(0), "Se han encontrado el programa");


        }catch(Sql2oException e) {
            //e.printStackTrace();
            System.err.println(e.getMessage());
            return new Result<>(false, null, "Error del sistema");
        }
    }

    public static Result<List<ProgramaRichModel>> getAllProgramasPerUser(int id) {
        try(Connection con = Application.sql2o.open()) {

            List<Map<String, Object>> programasRaw
                    = con.createQuery(getAllProgramasJoinPerUserSql)
                    .addParameter("id", id)
                    .executeAndFetchTable()
                    .asList();

            List<ProgramaRichModel> programasEnriched
                    = new ArrayList<>();

            for (Map<String, Object> field : programasRaw) {
                //System.out.println("------------------");
                ProgramaRichModel programaTmp = new ProgramaRichModel();
                programaTmp.setId((int)field.get("id"));
                programaTmp.setFecha((Date)field.get("fecha"));

                UsuarioModel usuarioTmp = new UsuarioModel();
                usuarioTmp.setId((int)field.get("usuario_id"));
                usuarioTmp.setNombre((String)field.get("usuario_nombre"));
                usuarioTmp.setApellido((String)field.get("usuario_apellido"));

                LocalModel localTmp = new LocalModel();
                localTmp.setId((int)field.get("local_id"));
                localTmp.setRazon_social((String)field.get("local_razon_social"));

                programaTmp.setUsuario(usuarioTmp);
                programaTmp.setLocal(localTmp);

                programasEnriched.add(programaTmp);
            }


            if (programasEnriched.size() == 0) {
                return new Result<>(false, null, "No se han encontrado programas");
            }

            return new Result<>(true, programasEnriched, "Se han encontrado " + programasEnriched.size() + " programas");


        }catch(Sql2oException e) {
            //e.printStackTrace();
            System.err.println(e.getMessage());
            return new Result<>(false, null, "Error del sistema");
        }
    }

    public static Result<List<ProgramaRichModel>> getAllProgramas() {
        try(Connection con = Application.sql2o.open()) {

            List<Map<String, Object>> programasRaw
                    = con.createQuery(getAllProgramasJoinSql)
                    .executeAndFetchTable()
                    .asList();

            List<ProgramaRichModel> programasEnriched
                    = new ArrayList<>();

            for (Map<String, Object> field : programasRaw) {
                //System.out.println("------------------");
                ProgramaRichModel programaTmp = new ProgramaRichModel();
                programaTmp.setId((int)field.get("id"));
                programaTmp.setFecha((Date)field.get("fecha"));

                UsuarioModel usuarioTmp = new UsuarioModel();
                usuarioTmp.setId((int)field.get("usuario_id"));
                usuarioTmp.setNombre((String)field.get("usuario_nombre"));
                usuarioTmp.setApellido((String)field.get("usuario_apellido"));

                LocalModel localTmp = new LocalModel();
                localTmp.setId((int)field.get("local_id"));
                localTmp.setRazon_social((String)field.get("local_razon_social"));

                programaTmp.setUsuario(usuarioTmp);
                programaTmp.setLocal(localTmp);

                programasEnriched.add(programaTmp);
            }


            if (programasEnriched.size() == 0) {
                return new Result<>(false, null, "No se han encontrado programas");
            }

            return new Result<>(true, programasEnriched, "Se han encontrado " + programasEnriched.size() + " programas");


        }catch(Sql2oException e) {
            //e.printStackTrace();
            System.err.println(e.getMessage());
            return new Result<>(false, null, "Error del sistema");
        }
    }



    public static Result<ProgramaModel> insertPrograma(ProgramaModel programa) {
        try(Connection con = Application.sql2o.open()) {
            int id = (int)con.createQuery(insertProgramaSql, true)
                    .bind(programa).executeUpdate().getKey();

            programa.setId(id);

            return new Result<>(true, programa, String.format("Inserted programa #%d", id));

        }catch(Sql2oException e) {
            //e.printStackTrace();
            System.err.println(e.getMessage());
            return new Result<>(false, null, "Error del sistema");
        }
    }
}
