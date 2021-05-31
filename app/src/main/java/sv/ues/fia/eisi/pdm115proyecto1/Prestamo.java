package sv.ues.fia.eisi.pdm115proyecto1;

public class Prestamo {

    private int idPrestamo;
    private String fechaPrestamo;
    private String fechaDevolucion;
    private int actividad;
    private String responsable;
    private String categoriaPrestamo;
    private int equipo;

    public Prestamo() {
    }

    public Prestamo(int idPrestamo, String fechaPrestamo, String fechaDevolucion, int actividad, String responsable, String categoriaPrestamo, int equipo) {
        this.idPrestamo = idPrestamo;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
        this.actividad = actividad;
        this.responsable = responsable;
        this.categoriaPrestamo = categoriaPrestamo;
        this.equipo = equipo;
    }

    public Prestamo(int idPrestamo, String fechaPrestamo, String fechaDevolucion, int actividad, String responsable, int equipo) {
        this.idPrestamo = idPrestamo;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
        this.actividad = actividad;
        this.responsable = responsable;
        this.equipo = equipo;
    }

    public int getEquipo() {
        return equipo;
    }

    public void setEquipo(int equipo) {
        this.equipo = equipo;
    }

    public int getIdPrestamo() {
        return idPrestamo;
    }

    public void setIdPrestamo(int idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    public String getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(String fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public String getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(String fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public int getActividad() {
        return actividad;
    }

    public void setActividad(int actividad) {
        this.actividad = actividad;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getCategoriaPrestamo() {
        return categoriaPrestamo;
    }

    public void setCategoriaPrestamo(String categoriaPrestamo) {
        this.categoriaPrestamo = categoriaPrestamo;
    }

    @Override
    public String toString(){



        return
                "ID: " +idPrestamo +
                        " | Equipo: " + equipo + " | Cat: " + categoriaPrestamo;
    }
}
