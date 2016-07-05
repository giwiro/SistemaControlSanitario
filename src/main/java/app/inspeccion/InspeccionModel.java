package app.inspeccion;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by Vicky on 04/07/2016.
 */
public class InspeccionModel {
    @Setter @Getter private int id;
    @Setter @Getter private String ubicacion;
    @Setter @Getter private String almacen;
    @Setter @Getter private String cocina;
    @Setter @Getter private int id_local;
}
