package sv.ues.fia.eisi.pdm115proyecto1;

public class Categoria {
    private int id_categoria;
    private String nombre_categoria;

    public Categoria(){
    }

    public Categoria(int id, String nombre){
        this.id_categoria = id;
        this.nombre_categoria = nombre;
    }

    public int getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }

    public String getNombre_categoria() {
        return nombre_categoria;
    }

    public void setNombre_categoria(String nombre_categoria) {
        this.nombre_categoria = nombre_categoria;
    }

    @Override
    public String toString(){
        return
                "ID: " + id_categoria +
                " | Categoria:" +nombre_categoria;
    }

}
