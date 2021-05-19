package sv.ues.fia.eisi.pdm115proyecto1;

import java.util.Date;

public class Razon_Traslado {
    private int id_razon_traslado;
    private String nombre_razon_traslado;
    private String descripcion_razon_traslado;
    private String equipo_informatico;
    private String fecha_traslado;
    private String estado;

    public Razon_Traslado() { }

    public Razon_Traslado(int id_razon_traslado, String nombre_razon_traslado, String descripcion_razon_traslado, String equipo_informatico, String fecha_traslado, String estado) {
        this.id_razon_traslado = id_razon_traslado;
        this.nombre_razon_traslado = nombre_razon_traslado;
        this.descripcion_razon_traslado = descripcion_razon_traslado;
        this.equipo_informatico = equipo_informatico;
        this.fecha_traslado = fecha_traslado;
        this.estado = estado;
    }

    public int getId_razon_traslado() {
        return id_razon_traslado;
    }

    public void setId_razon_traslado(int id_razon_traslado) {
        this.id_razon_traslado = id_razon_traslado;
    }

    public String getNombre_razon_traslado() {
        return nombre_razon_traslado;
    }

    public void setNombre_razon_traslado(String nombre_razon_traslado) {
        this.nombre_razon_traslado = nombre_razon_traslado;
    }

    public String getDescripcion_razon_traslado() {
        return descripcion_razon_traslado;
    }

    public void setDescripcion_razon_traslado(String descripcion_razon_traslado) {
        this.descripcion_razon_traslado = descripcion_razon_traslado;
    }

    public String getEquipo_informatico() {
        return equipo_informatico;
    }

    public void setEquipo_informatico(String equipo_informatico) {
        this.equipo_informatico = equipo_informatico;
    }

    public String getFecha_traslado() {
        return fecha_traslado;
    }

    public void setFecha_traslado(String fecha_traslado) {
        this.fecha_traslado = fecha_traslado;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString(){
        return
                "ID: " + id_razon_traslado +
                        " | Nombre:" + nombre_razon_traslado +
                            " | Descripción:" + descripcion_razon_traslado +
                                    " | Equipo:" + equipo_informatico +
                                         " | Fecha:" + fecha_traslado +
                                             " | Estado:" + estado;
    }
}
