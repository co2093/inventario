package sv.ues.fia.eisi.pdm115proyecto1;

public class Docente {

    private int id;
    private String nombre;
    private String apellido;

    public Docente() {
    }

    public Docente(int id, String nombre, String apellido) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    @Override
    public String toString(){
        return
                "ID: " +id +
                        " | Nombre: " +nombre +
                        " | Apellidos: " + apellido
                ;
    }

}