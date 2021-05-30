package sv.ues.fia.eisi.pdm115proyecto1;

public class EquipoInformatico {

    private int id_equipo;
    private String nombreEquipo;
    private String modeloEquipo;
    private String marcaEquipo;
    private String estado;
    private String categoriaEquipo;
    private String fechaEquipoAdquisicion;


    public EquipoInformatico() {
    }

    public EquipoInformatico(int id_equipo, String nombreEquipo, String modeloEquipo, String marcaEquipo, String estado, String categoriaEquipo, String fechaEquipoAdquisicion) {
        this.id_equipo = id_equipo;
        this.nombreEquipo = nombreEquipo;
        this.modeloEquipo = modeloEquipo;
        this.marcaEquipo = marcaEquipo;
        this.estado = estado;
        this.categoriaEquipo = categoriaEquipo;
        this.fechaEquipoAdquisicion = fechaEquipoAdquisicion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getId_equipo() {
        return id_equipo;
    }

    public void setId_equipo(int id_equipo) {
        this.id_equipo = id_equipo;
    }

    public String getNombreEquipo() {
        return nombreEquipo;
    }

    public void setNombreEquipo(String nombreEquipo) {
        this.nombreEquipo = nombreEquipo;
    }

    public String getModeloEquipo() {
        return modeloEquipo;
    }

    public void setModeloEquipo(String modeloEquipo) {
        this.modeloEquipo = modeloEquipo;
    }

    public String getMarcaEquipo() {
        return marcaEquipo;
    }

    public void setMarcaEquipo(String marcaEquipo) {
        this.marcaEquipo = marcaEquipo;
    }

    public String getCategoriaEquipo() {
        return categoriaEquipo;
    }

    public void setCategoriaEquipo(String categoriaEquipo) {
        this.categoriaEquipo = categoriaEquipo;
    }

    public String getFechaEquipoAdquisicion() {
        return fechaEquipoAdquisicion;
    }

    public void setFechaEquipoAdquisicion(String fechaEquipoAdquisicion) {
        this.fechaEquipoAdquisicion = fechaEquipoAdquisicion;
    }

    @Override
    public String toString(){
        return  "ID: " + id_equipo +
                " | Nombre:" +nombreEquipo + " | Estado: " + estado;
    }
}
