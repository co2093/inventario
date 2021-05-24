package sv.ues.fia.eisi.pdm115proyecto1;

public class Libro {

    private int isbn;
    private String nombreLibro;
    private int autorId;
    private int ejemplar;
    private String editorial;

    public Libro() {
    }

    public Libro(int isbn, String nombreLibro, int autorId, int ejemplar, String editorial) {
        this.isbn = isbn;
        this.nombreLibro = nombreLibro;
        this.autorId = autorId;
        this.ejemplar = ejemplar;
        this.editorial = editorial;
    }

    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    public String getNombreLibro() {
        return nombreLibro;
    }

    public void setNombreLibro(String nombreLibro) {
        this.nombreLibro = nombreLibro;
    }

    public int getAutorId() {
        return autorId;
    }

    public void setAutorId(int autorId) {
        this.autorId = autorId;
    }

    public int getEjemplar() {
        return ejemplar;
    }

    public void setEjemplar(int ejemplar) {
        this.ejemplar = ejemplar;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    @Override
    public String toString(){
        return  "ISBN: " + isbn +
                " | Nombre:" +nombreLibro;
    }
}
