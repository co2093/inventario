package sv.ues.fia.eisi.pdm115proyecto1;

public class Usuario {

    private String nombre;
    private String contrasena;
    private String correo;
    private String rol;

    public Usuario() {
    }

    public Usuario(String nombre, String contrasena, String correo, String rol) {
        this.nombre = nombre;
        this.contrasena = contrasena;
        this.correo = correo;
        this.rol = rol;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    @Override
    public String toString(){



        return
                "Correo: " +correo +
                        " | Rol: " +rol;
    }
}
