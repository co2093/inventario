package sv.ues.fia.eisi.pdm115proyecto1;

public class ControlFisico {

    private int idControl;
    private String categoriaControl;
    private int cantidadExistencia;
    private int cantidadPrestamo;

    public ControlFisico() {
    }

    public ControlFisico(int idControl, String categoria, int cantidadExistencia, int cantidadPrestamo) {
        this.idControl = idControl;
        this.categoriaControl = categoria;
        this.cantidadExistencia = cantidadExistencia;
        this.cantidadPrestamo = cantidadPrestamo;
    }

    public int getIdControl() {
        return idControl;
    }

    public void setIdControl(int idControl) {
        this.idControl = idControl;
    }

    public String getCategoriaControl() {
        return categoriaControl;
    }

    public void setCategoriaControl(String categoria) {
        this.categoriaControl = categoria;
    }

    public int getCantidadExistencia() {
        return cantidadExistencia;
    }

    public void setCantidadExistencia(int cantidadExistencia) {
        this.cantidadExistencia = cantidadExistencia;
    }

    public int getCantidadPrestamo() {
        return cantidadPrestamo;
    }

    public void setCantidadPrestamo(int cantidadPrestamo) {
        this.cantidadPrestamo = cantidadPrestamo;
    }

    @Override
    public String toString(){



        return
                "Categoria: " +categoriaControl +
                        " | Existencias: " +cantidadExistencia + " | Prestamos: " + cantidadPrestamo ;
    }
}
