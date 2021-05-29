package sv.ues.fia.eisi.pdm115proyecto1;

public class Tesis {
    private int id_tesis;
    private String titulo_tesis;
    private String fecha_publicacion;
    private int id_autor;
    private String idioma;

    public Tesis() { }

    public Tesis(int id_tesis, String titulo_tesis, String fecha_publicacion, int id_autor, String idioma) {
        this.id_tesis = id_tesis;
        this.titulo_tesis = titulo_tesis;
        this.fecha_publicacion = fecha_publicacion;
        this.id_autor = id_autor;
        this.idioma = idioma;
    }

    public int getId_tesis() {
        return id_tesis;
    }

    public void setId_tesis(int id_tesis) {
        this.id_tesis = id_tesis;
    }

    public String getTitulo_tesis() {
        return titulo_tesis;
    }

    public void setTitulo_tesis(String titulo_tesis) {
        this.titulo_tesis = titulo_tesis;
    }

    public String getFecha_publicacion() {
        return fecha_publicacion;
    }

    public void setFecha_publicacion(String fecha_publicacion) {
        this.fecha_publicacion = fecha_publicacion;
    }

    public int getId_autor() {
        return id_autor;
    }

    public void setId_autor(int id_autor) {
        this.id_autor = id_autor;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }


    @Override
    public String toString(){
        return  "N°: " + id_tesis +
                " | Titulo :" + titulo_tesis +
                " | Fecha Publicación : " + fecha_publicacion +
                " | autor/es: " + id_autor +
                " | idioma: " + idioma;
    }
}
