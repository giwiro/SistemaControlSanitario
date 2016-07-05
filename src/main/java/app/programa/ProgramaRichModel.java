package app.programa;

import app.local.LocalModel;
import app.usuario.UsuarioModel;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by Vicky on 04/07/2016.
 */
public class ProgramaRichModel {
    @Setter @Getter private int id;
    @Setter @Getter private Date fecha;
    @Setter @Getter private UsuarioModel usuario;
    @Setter @Getter private LocalModel local;
}
