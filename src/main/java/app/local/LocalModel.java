package app.local;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Vicky on 02/07/2016.
 */
public class LocalModel {
    @Setter @Getter private int id;
    @Setter @Getter private String ruc;
    @Setter @Getter private String razon_social;
    @Setter @Getter private String distrito;
    @Setter @Getter private String provincia;
    @Setter @Getter private String departamento;
    @Setter @Getter private String administrador;
    @Setter @Getter private String dni_administrador;
    @Setter @Getter private String manipuladores;
    @Setter @Getter private String manipuladoresm;
    @Setter @Getter private String racionesdiarias;

    public LocalModel(String ruc, String razon_social, String distrito, String provincia, String departamento, String administrador, String dni_administrador, String manipuladores, String manipuladoresm, String racionesdiarias) {
        this.ruc = ruc;
        this.razon_social = razon_social;
        this.distrito = distrito;
        this.provincia = provincia;
        this.departamento = departamento;
        this.administrador = administrador;
        this.dni_administrador = dni_administrador;
        this.manipuladores = manipuladores;
        this.manipuladoresm = manipuladoresm;
        this.racionesdiarias = racionesdiarias;
    }
}
