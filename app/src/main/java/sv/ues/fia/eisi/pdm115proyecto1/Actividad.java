package sv.ues.fia.eisi.pdm115proyecto1;

public class Actividad {

    private int idActividad;
    private String nombreActividad;
    private String ubicacion;

    public Actividad() {
    }

    public Actividad(int idActividad, String nombreActividad, String ubicacion) {
        this.idActividad = idActividad;
        this.nombreActividad = nombreActividad;
        this.ubicacion = ubicacion;
    }

    public int getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(int idActividad) {
        this.idActividad = idActividad;
    }

    public String getNombreActividad() {
        return nombreActividad;
    }

    public void setNombreActividad(String nombreActividad) {
        this.nombreActividad = nombreActividad;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    @Override
    public String toString(){
        return
                "ID: " +idActividad +
                        " | Nombre: " +nombreActividad +
                        " | Ubicacion: " + ubicacion
                ;
    }
}
