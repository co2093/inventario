package sv.ues.fia.eisi.pdm115proyecto1;

public class Editorial {
    private int id;
    private String nombre;
    private String pais;

    public Editorial() {
    }

    public Editorial(int id, String nombre, String pais) {
        this.id = id;
        this.nombre = nombre;
        this.pais = pais;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    @Override
    public String toString() {
        return "ID: " + id +
                " || Nombre: " + nombre +
                " || Pais: " + pais;
    }
}
