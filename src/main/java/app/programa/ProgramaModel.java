package app.programa;

/**
 * Created by Vicky on 03/07/2016.
 */

import lombok.Getter;
import lombok.Setter;

import java.util.Date;


public class ProgramaModel {
    @Setter @Getter private int id;
    @Setter @Getter private Date fecha;
    @Setter @Getter private int id_usuario;
    @Setter @Getter private int id_local;

    public ProgramaModel(Date fecha, int id_usuario, int id_local) {
        this.fecha = fecha;
        this.id_usuario = id_usuario;
        this.id_local = id_local;
    }
}
