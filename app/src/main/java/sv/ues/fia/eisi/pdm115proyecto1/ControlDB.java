package sv.ues.fia.eisi.pdm115proyecto1;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.audiofx.DynamicsProcessing;
import android.provider.ContactsContract;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ControlDB {

    private static final String [] camposUsuario = new String[] {"nombre, contrasena, correo, rol"};
    private static final String [] camposAutor = new String[] {"id", "nombre"};
    private static final String [] camposAlumno = new String[] {"carnet", "nombre", "apellido"};
    private static final String [] camposEquipo = new String[] {"id", "nombre", "modelo", "marca", "estado", "categoria", "fecha"};
    private static final String [] camposTesis = new String[] {"id_tesis", "nombre_tesis", "fecha_publicacion", "idioma", "id_autor_tesis"};


    private final Context context;
    private SQLiteDatabase db;
    private DatabaseHelper DBHelper;

    public ControlDB(Context context) {
        this.context = context;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper{

        private static final String BASE_DATOS = "inve_5.s3db";
        private static final int version = 1;
        public DatabaseHelper (Context context){
            super(context, BASE_DATOS, null, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db){
            try{


                db.execSQL("CREATE TABLE autor(id INTEGER NOT NULL PRIMARY KEY, nombre VARCHAR (128));");
                db.execSQL("CREATE TABLE docente(id INTEGER NOT NULL PRIMARY KEY, nombre VARCHAR (128), apellido VARCHAR(128));");
                db.execSQL("CREATE TABLE alumno (carnet VARCHAR(128) NOT NULL PRIMARY KEY, nombre VARCHAR (128), apellido VARCHAR(128));");
                db.execSQL("CREATE TABLE usuario (id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, nombre VARCHAR (128), contrasena VARCAHR (128), correo VARCHAR(128), rol VARCHAR(128));");
                db.execSQL("CREATE TABLE rol (id INTEGER PRIMARY KEY, nombre VARCHAR (128))");
                db.execSQL("CREATE TABLE equipo (id INTEGER PRIMARY KEY, nombre VARCHAR (128), modelo VARCHAR (128), marca VARCHAR (128), estado VARCHAR (128), categoria VARCHAR (128), fecha VARCHAR (128))");
                db.execSQL("CREATE TABLE actividad (id INTEGER PRIMARY KEY, nombre VARCHAR (128), ubicacion VARCHAR (128));");
                db.execSQL("CREATE TABLE prestamo (id INTEGER PRIMARY KEY, fecha_prestamo VARCHAR (128), fecha_devolucion VARCHAR(128), actividad INTEGER, responsable VARCHAR (128), hora VARCHAR (128), equipo INTEGER);");

                //Damaris
                db.execSQL("CREATE TABLE razon (id INTEGER PRIMARY KEY AUTOINCREMENT,nombre_razon VARCHAR(128) , descripcion VARCHAR(128), equipo VARCHAR(10), fecha VARCHAR(128) , estado VARCHAR(30));");
                db.execSQL("CREATE TABLE libro (isbn INTEGER NOT NULL PRIMARY KEY,nombre_libro VARCHAR (256),autor INTEGER (13),ejemplar INTEGER,editorial VARCHAR(128), idioma VARCHAR(128));");
                db.execSQL("CREATE TABLE tesis (id_tesis INTEGER NOT NULL PRIMARY KEY,nombre_tesis VARCHAR (256), fecha_publicacion VARCHAR(128),idioma VARCHAR(128),id_autor_tesis VARCHAR);");

                //Francisco
                db.execSQL("CREATE TABLE categoria (id_categoria INTEGER PRIMARY KEY AUTOINCREMENT, nombre_categoria VARCHAR (256) NOT NULL)");


            }catch (SQLException e){
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

    public void abrir() throws SQLException{
        db = DBHelper.getWritableDatabase();
        return;
    }

    public void cerrar(){
        DBHelper.close();
    }

    public String insertar(Rol rol){

        ContentValues contentValues = new ContentValues();
        contentValues.put("id", rol.getIdRol());
        contentValues.put("nombre", rol.getNombreRol());
        db.insert("rol", null, contentValues);

        return "Rol agregado";
    }

    public String insertar(Tesis tesis){
        if(verificarIntegridad(tesis, 8)){
            return "Ya existe una tesis con este Titulo";
        }else{
            ContentValues contentValues = new ContentValues();
            contentValues.put("nombre_tesis", tesis.getTitulo_tesis());
            contentValues.put("fecha_publicacion", tesis.getFecha_publicacion());
            contentValues.put("idioma", tesis.getIdioma());
            contentValues.put("id_autor_tesis", tesis.getId_autor());

            db.insert("tesis", null, contentValues);

            return "Tesis guardada correctamente";
        }

    }

    public boolean eliminar(Tesis tesis){
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        String queryString = "DELETE FROM tesis WHERE id_tesis =" + tesis.getId_tesis();
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()){
            return true;
        }else {
            return false;
        }
    }

    public List<Tesis> getTesis(){

        List<Tesis> lista = new ArrayList<>();

        String queryString = "SELECT * FROM tesis";
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do{
                int id_tesis = cursor.getInt(0);
                String tituloTesis = cursor.getString(1);
                String fechapub = cursor.getString(2);
                String idioma = cursor.getString(3);
                String autor = cursor.getString(4);

                Tesis tesis = new Tesis(id_tesis, tituloTesis, fechapub, autor, idioma);
                lista.add(tesis);
            }while (cursor.moveToNext());
        }else {

        }
        cursor.close();
        db.close();
        return lista;
    }

    public List<Tesis> consultaTesis(String nombre_tesis) {
        List<Tesis> lista = new ArrayList<>();
        //String queryString = "SELECT * FROM tesis WHERE id_tesis = " + nombre_tesis;
        SQLiteDatabase db = DBHelper.getReadableDatabase();
        //Cursor cursor = db.rawQuery(queryString, null);

        String[] titulo_tesis = {nombre_tesis};

        Cursor cursor = db.query("tesis", camposTesis, "nombre_tesis = ?", titulo_tesis, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id_tesis2 = cursor.getInt(0);
                String tituloTesis = cursor.getString(1);
                String fechapub = cursor.getString(2);
                String autor = cursor.getString(3);
                String idioma = cursor.getString(4);
                Tesis tesis = new Tesis(id_tesis2, tituloTesis, fechapub, autor, idioma);
                lista.add(tesis);

            } while (cursor.moveToNext());
        } else {

        }
        cursor.close();
        db.close();
        return lista;
    }

    public String actualizar(Tesis tesis){

        if (verificarIntegridad(tesis, 9)){
            String[] id = {String.valueOf(tesis.getId_tesis())};
            ContentValues contentValues = new ContentValues();


            contentValues.put("nombre_tesis", tesis.getTitulo_tesis());
            contentValues.put("fecha_publicacion", tesis.getFecha_publicacion());
            contentValues.put("idioma", tesis.getIdioma());
            contentValues.put("id_autor_tesis", tesis.getId_autor());

            db.update("tesis", contentValues, "id_tesis = ?", id);

            return "Registro Actualizado";
        }
        else {
            return "Registro no existe";
        }

    }




    public String insertar(Libro libro){

        if(verificarIntegridad(libro, 5)){
            return "Ya existe un libro con este ISBN";
        }else{

            ContentValues contentValues = new ContentValues();
            contentValues.put("isbn", libro.getIsbn());
            contentValues.put("nombre_libro", libro.getNombreLibro());
            contentValues.put("autor", libro.getAutorId());
            contentValues.put("ejemplar", libro.getEjemplar());
            contentValues.put("editorial", libro.getEditorial());
            contentValues.put("idioma", libro.getIdioma());

            db.insert("libro", null, contentValues);

            return "Libro agregado";
        }


    }

    public String insertar(Actividad actividad){

        if(verificarIntegridad(actividad, 10)){
            return "Ya existe una actividad con este ID";
        }else{

            ContentValues contentValues = new ContentValues();
            contentValues.put("id", actividad.getIdActividad());
            contentValues.put("nombre", actividad.getNombreActividad());
            contentValues.put("ubicacion", actividad.getUbicacion());

            db.insert("actividad", null, contentValues);

            return "Actividad agregada";
        }

    }

    public String insertar(Prestamo prestamo){

        if(verificarIntegridad(prestamo, 11)){
            return "Ya existe una prestamo con este ID";
        }else{

            ContentValues contentValues = new ContentValues();
            contentValues.put("id", prestamo.getIdPrestamo());
            contentValues.put("fecha_prestamo", prestamo.getFechaPrestamo());
            contentValues.put("fecha_devolucion", prestamo.getFechaDevolucion());
            contentValues.put("actividad", prestamo.getActividad());
            contentValues.put("responsable", prestamo.getResponsable());
            contentValues.put("hora", prestamo.getHora());
            contentValues.put("equipo", prestamo.getEquipo());

            db.insert("prestamo", null, contentValues);

            return "Prestamo agregada";
        }

    }



    public String insertar(EquipoInformatico equipoInformatico){

        if(verificarIntegridad(equipoInformatico,7)){
            return "Ya existe un equipo con este ID";
        }else {

            ContentValues contentValues = new ContentValues();
            contentValues.put("id", equipoInformatico.getId_equipo());
            contentValues.put("nombre", equipoInformatico.getNombreEquipo());
            contentValues.put("modelo", equipoInformatico.getModeloEquipo());
            contentValues.put("marca", equipoInformatico.getMarcaEquipo());
            contentValues.put("estado", equipoInformatico.getEstado());
            contentValues.put("fecha", equipoInformatico.getFechaEquipoAdquisicion());
            contentValues.put("categoria", equipoInformatico.getCategoriaEquipo());

            db.insert("equipo", null, contentValues);

            return "Equipo agregado";
        }

    }


    public String insertar(Usuario usuario){

        if (verificarIntegridad(usuario, 6)){
            return "Ya existe un usuario con este correo";
        }else {

            ContentValues contentValues = new ContentValues();
            contentValues.put("nombre", usuario.getNombre());
            contentValues.put("correo", usuario.getCorreo());
            contentValues.put("contrasena", usuario.getContrasena());
            contentValues.put("rol", usuario.getRol());
            db.insert("usuario", null, contentValues);
            return "OK";
        }
    }

    public String insertar (Autor autor){

        if(verificarIntegridad(autor,1)){
            return "Ya existe un autor con este ID";
        }else {
            String regInsertado = "Registro Insertado Numero = ";

            long contador = 0;

            ContentValues values = new ContentValues();
            values.put("id", autor.getId());
            values.put("nombre", autor.getNombreAutor());
            contador = db.insert("autor", null, values);

            if (contador == -1 || contador == 0) {
                regInsertado = "ERROR";
            } else {
                regInsertado = regInsertado + contador;
            }

            return regInsertado;

        }

    }

    public String insertar(Docente docente){

        if(verificarIntegridad(docente, 2)){
            return "Ya existe un docente con este ID";
        }else{
            String regInsertado = "Registro Insertado Numero = ";
            long contador = 0;

            ContentValues contentValues = new ContentValues();
            contentValues.put("id", docente.getId());
            contentValues.put("nombre", docente.getNombre());
            contentValues.put("apellido", docente.getApellido());

            contador = db.insert("docente", null, contentValues);

            if (contador == -1 || contador == 0) {
                regInsertado = "ERROR";
            } else {
                regInsertado = regInsertado + contador;
            }

            return regInsertado;

        }

    }

    public String insertar(Alumno alumno){

        if (verificarIntegridad(alumno, 3)){
            return "Ya existe un alumno con este ID";
        }else{
            String regInsertado = "Registro Insertado Numero  = ";
            long contador = 0;

            ContentValues contentValues = new ContentValues();
            contentValues.put("carnet", alumno.getCarnet());
            contentValues.put("nombre", alumno.getNombre());
            contentValues.put("apellido", alumno.getApellidos());

            contador = db.insert("alumno", null, contentValues);

            if (contador == -1 || contador == 0) {
                regInsertado = "ERROR";
            } else {
                regInsertado = regInsertado + contador;
            }

            return regInsertado;


        }
    }

    public String insertar (Razon_Traslado razon_traslado){

        String regInsertado = "Registro Insertados Numero = ";
        long contador = 0;

        ContentValues values = new ContentValues();
        values.put("fecha", razon_traslado.getFecha_traslado());
        values.put("nombre_razon", razon_traslado.getNombre_razon_traslado());
        values.put("descripcion", razon_traslado.getDescripcion_razon_traslado());
        values.put("equipo", razon_traslado.getEquipo_informatico());
        values.put("estado", razon_traslado.getEstado());

        contador = db.insert("razon", null, values);

        if (contador==-1 || contador==0){
            regInsertado = "ERROR";
        }else {
            regInsertado = regInsertado+contador;
        }

        return regInsertado;
    }

    public String insertar(Categoria categoria){

        if(verificarIntegridad(categoria,4)){
            return "Ya existe una categoria con ese ID";
        }else{
            String regInsertados="Registro Insertado Nº= ";
            long contador=0;

            ContentValues cat = new ContentValues();
            //cat.put("id_categoria", categoria.getId_categoria());
            cat.put("nombre_categoria", categoria.getNombre_categoria());
            contador=db.insert("categoria",null, cat);

            if(contador==-1 || contador ==0){
                regInsertados = "Error al insertar el registro";
            }else{
                regInsertados=regInsertados+contador;
            }
            return regInsertados;
        }
    }

    public boolean eliminar(Categoria categoria){
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        String queryString = "DELETE FROM categoria WHERE id_categoria = " + categoria.getId_categoria();
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()){
            return true;
        }else {
            return false;
        }
    }

    public List<Categoria> consultaCategoria(String id_categoria){
        List<Categoria> lista = new ArrayList<>();

        // String queryString = "SELECT * FROM categoria WHERE id_categoria = " + id_categoria ;

        SQLiteDatabase db = DBHelper.getReadableDatabase();

        //Cursor cursor = db.rawQuery(queryString, null);

        String queryString = "SELECT * FROM categoria WHERE id_categoria = " + id_categoria;
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do {
                int id = cursor.getInt(0);
                String nombre = cursor.getString(1);

                Categoria cat =  new Categoria(id, nombre);
                lista.add(cat);

            }while (cursor.moveToNext());
        }else {

        }
        cursor.close();
        db.close();
        return lista;

    }

    public List<Categoria> getCategorias(){
        List<Categoria> lista = new ArrayList<>();
        String queryString = "SELECT * FROM categoria";
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            do{
                int id_categoria = cursor.getInt(0);
                String nombre_categoria = cursor.getString(1);
                Categoria categoria = new Categoria(id_categoria,nombre_categoria);
                lista.add(categoria);
            }while (cursor.moveToNext());
        }else{

        }
        cursor.close();
        db.close();
        return lista;
    }

    public List<String> getCatNombres(){
        List<String> lista = new ArrayList<>();
        String queryString = "SELECT * FROM categoria";
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            do{

                String nombre_categoria = cursor.getString(1);

                lista.add(nombre_categoria);
            }while (cursor.moveToNext());
        }else{

        }
        cursor.close();
        db.close();
        return lista;
    }







    public List<Razon_Traslado> getEveryoneRazon(){
        List<Razon_Traslado> lista = new ArrayList<>();

        String queryString = "SELECT * FROM razon";

        SQLiteDatabase db = DBHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do {
                int id = cursor.getInt(0);
                String nombre_razon = cursor.getString(1);
                String descripcion = cursor.getString(2);
                String equipo = cursor.getString(3);
                String fecha = cursor.getString(4);
                String estado = cursor.getString(5);

                Razon_Traslado razon =  new Razon_Traslado(id, nombre_razon, descripcion, equipo, fecha, estado);
                lista.add(razon);

            }while (cursor.moveToNext());
        }else {

        }

        cursor.close();
        db.close();
        return lista;
    }

    public List<Razon_Traslado> consultaRazon(int idd){
        List<Razon_Traslado> lista = new ArrayList<>();

        String queryString = "SELECT * FROM razon WHERE id = "  + idd;

        SQLiteDatabase db = DBHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do {
                int i   = cursor.getInt(0);
                String nombre_razon = cursor.getString(1);
                String descripcion = cursor.getString(2);
                String equipo = cursor.getString(3);
                String fecha = cursor.getString(4);
                String estado = cursor.getString(5);

                Razon_Traslado razon =  new Razon_Traslado(i, nombre_razon, descripcion, equipo, fecha, estado);
                lista.add(razon);

            }while (cursor.moveToNext());
        }else {

        }

        cursor.close();
        db.close();
        return lista;

    }

    public boolean eliminar(Razon_Traslado razon){

        SQLiteDatabase db = DBHelper.getWritableDatabase();
        String queryString = "DELETE FROM razon WHERE id = " + razon.getId_razon_traslado();
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()){
            return true;
        }else {
            return false;
        }

    }
    public String actualizar(Razon_Traslado razon) {


        String[] id = {String.valueOf(razon.getId_razon_traslado())};
        ContentValues contentValues = new ContentValues();
        contentValues.put("nombre_razon", razon.getNombre_razon_traslado());
        contentValues.put("descripcion", razon.getDescripcion_razon_traslado());
        contentValues.put("equipo", razon.getEquipo_informatico());
        contentValues.put("fecha", razon.getFecha_traslado());
        contentValues.put("estado", razon.getEstado());

        db.update("razon", contentValues, "id = ?", id);

        return "Actualizado";

    }

    public List<Actividad> getActividades(){

        List<Actividad> lista = new ArrayList<>();

        String queryString = "SELECT * FROM actividad";
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String nombre = cursor.getString(1);
                String ubicacion = cursor.getString(2);

                Actividad actividad = new Actividad(id,nombre,ubicacion);
                lista.add(actividad);
            }while (cursor.moveToNext());
        }else {

        }
        cursor.close();
        db.close();
        return lista;


    }

    public List<Prestamo> getPrestamo(){

        List<Prestamo> lista = new ArrayList<>();

        String queryString = "SELECT * FROM prestamo";
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do{

                int idPrestamo = cursor.getInt(0);
                String fechaPrestamo = cursor.getString(1);
                String fechaDevolucion = cursor.getString(2);
                int actividad = cursor.getInt(3);
                String responsable = cursor.getString(4);
                String hora = cursor.getString(5);
                int equipo = cursor.getInt(6);


                Prestamo prestamo = new Prestamo(idPrestamo,fechaPrestamo,fechaDevolucion, actividad, responsable, hora, equipo);
                lista.add(prestamo);
            }while (cursor.moveToNext());
        }else {

        }
        cursor.close();
        db.close();
        return lista;


    }



    public List<Libro> getLibros(){

        List<Libro> lista = new ArrayList<>();

        String queryString = "SELECT * FROM libro";
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do{
                int isbn = cursor.getInt(0);
                String nombreLibro = cursor.getString(1);
                int autor = cursor.getInt(2);
                int ejemplar = cursor.getInt(3);
                String editorial = cursor.getString(4);
                String idioma = cursor.getString(5);
                Libro libro = new Libro(isbn,nombreLibro,autor,ejemplar,editorial, idioma);
                lista.add(libro);
            }while (cursor.moveToNext());
        }else {

        }
        cursor.close();
        db.close();
        return lista;
    }




    public List<Docente> getDocentes(){
        List<Docente> lista = new ArrayList<>();
        String queryString = "SELECT * FROM docente";
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String nombre = cursor.getString(1);
                String apellido = cursor.getString(2);
                Docente docente = new Docente(id,nombre, apellido);
                lista.add(docente);
            }while (cursor.moveToNext());
        }else{

        }
        cursor.close();
        db.close();
        return lista;
    }

    public List<Alumno> getAlumnos(){
        List<Alumno> lista = new ArrayList<>();
        String queryString = "SELECT * FROM alumno";
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            do{
                String carnet = cursor.getString(0);
                String nombre = cursor.getString(1);
                String apellido = cursor.getString(2);
                Alumno alumno = new Alumno(carnet,nombre, apellido);
                lista.add(alumno);
            }while (cursor.moveToNext());
        }else{

        }
        cursor.close();
        db.close();
        return lista;
    }

    public List<Usuario> getUsuarios(){
        List<Usuario> lista = new ArrayList<>();
        String queryString = "SELECT * FROM usuario";
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            do{
                String nombre = cursor.getString(1);
                String contrasena = cursor.getString(2);
                String correo = cursor.getString(3);
                String rol = cursor.getString(4);

                Usuario usuario = new Usuario(nombre, contrasena, correo, rol);


                lista.add(usuario);
            }while (cursor.moveToNext());
        }else{

        }
        cursor.close();
        db.close();
        return lista;
    }

    public List<Autor> getEveryone(){
        List<Autor> lista = new ArrayList<>();

        String queryString = "SELECT * FROM autor";

        SQLiteDatabase db = DBHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do {
                int id = cursor.getInt(0);
                String nombre = cursor.getString(1);

                Autor autor =  new Autor(id, nombre);
                lista.add(autor);

            }while (cursor.moveToNext());
        }else {

        }

        cursor.close();
        db.close();
        return lista;
    }

    public List<Integer> getAutoresID(){
        List<Integer> lista = new ArrayList<>();

        String queryString = "SELECT * FROM autor";

        SQLiteDatabase db = DBHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do {
                int id = cursor.getInt(0);

                lista.add(id);

            }while (cursor.moveToNext());
        }else {

        }

        cursor.close();
        db.close();
        return lista;
    }

    public List<String> getDocentesNombre(){
        List<String> lista = new ArrayList<>();

        String queryString = "SELECT * FROM docente";

        SQLiteDatabase db = DBHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do {
                String id = cursor.getString(1);

                lista.add(id);

            }while (cursor.moveToNext());
        }else {

        }

        cursor.close();
        db.close();
        return lista;
    }

    public List<Integer> getActividadesID(){
        List<Integer> lista = new ArrayList<>();

        String queryString = "SELECT * FROM actividad";

        SQLiteDatabase db = DBHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do {
                int id = cursor.getInt(0);

                lista.add(id);

            }while (cursor.moveToNext());
        }else {

        }

        cursor.close();
        db.close();
        return lista;
    }

    public List<Integer> getEquipoID(){
        List<Integer> lista = new ArrayList<>();

        String queryString = "SELECT * FROM equipo";

        SQLiteDatabase db = DBHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do {
                int id = cursor.getInt(0);

                lista.add(id);

            }while (cursor.moveToNext());
        }else {

        }

        cursor.close();
        db.close();
        return lista;
    }


    public List<String> getRoles(){

        List<String> lista = new ArrayList<>();

        String queryString = "SELECT * FROM rol";

        SQLiteDatabase db = DBHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do {
                String rol = cursor.getString(1);

                lista.add(rol);

            }while (cursor.moveToNext());
        }else {

        }

        cursor.close();
        db.close();
        return lista;

    }

    public List<String> getAlumnosCarnet(){

        List<String> lista = new ArrayList<>();

        String queryString = "SELECT * FROM alumno";

        SQLiteDatabase db = DBHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do {
                String carnet = cursor.getString(0);

                lista.add(carnet);

            }while (cursor.moveToNext());
        }else {

        }

        cursor.close();
        db.close();
        return lista;

    }

    public List<EquipoInformatico> getEquipos(){
        List<EquipoInformatico> lista = new ArrayList<>();

        String queryString = "SELECT * FROM equipo";

        SQLiteDatabase db = DBHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do {
                int id_equipo = cursor.getInt(0);
                String nombreEquipo = cursor.getString(1);
                String modeloEquipo = cursor.getString(2);
                String marcaEquipo = cursor.getString(3);
                String estadoEquipo = cursor.getString(4);
                String categoriaEquipo = cursor.getString(5);
                String fechaEquipoAdquisicion = cursor.getString(6);

                EquipoInformatico equipoInformatico =  new EquipoInformatico(id_equipo, nombreEquipo, modeloEquipo, marcaEquipo, estadoEquipo, categoriaEquipo, fechaEquipoAdquisicion);
                lista.add(equipoInformatico);

            }while (cursor.moveToNext());
        }else {

        }

        cursor.close();
        db.close();
        return lista;
    }

    public List<Libro> consultaLibro(int isbn){
        List<Libro> lista = new ArrayList<>();
        String queryString = "SELECT * FROM libro WHERE isbn = " + isbn;
        SQLiteDatabase db = DBHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do{
                int isbn2 = cursor.getInt(0);
                String nombreLibro = cursor.getString(1);
                int autor = cursor.getInt(2);
                int ejemplar = cursor.getInt(3);
                String editorial = cursor.getString(4);
                String idioma = cursor.getString(5);
                Libro libro = new Libro(isbn2,nombreLibro,autor,ejemplar,editorial, idioma);
                lista.add(libro);
            }while (cursor.moveToNext());
        }else {

        }
        cursor.close();
        db.close();
        return lista;


    }

    public List<Actividad> consultaActividad(int id){

        List<Actividad> lista = new ArrayList<>();
        String queryString = "SELECT * FROM actividad WHERE id = " + id;
        SQLiteDatabase db = DBHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do{
                int id2 = cursor.getInt(0);
                String nombre = cursor.getString(1);
                String ubicacion = cursor.getString(2);

                Actividad actividad = new Actividad(id2,nombre,ubicacion);
                lista.add(actividad);
            }while (cursor.moveToNext());
        }else {

        }
        cursor.close();
        db.close();
        return lista;

    }

    public List<Prestamo> consultaPrestamos(int id){

        List<Prestamo> lista = new ArrayList<>();
        String queryString = "SELECT * FROM prestamo WHERE id = " + id;
        SQLiteDatabase db = DBHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do{
                int idPrestamo = cursor.getInt(0);
                String fechaPrestamo = cursor.getString(1);
                String fechaDevolucion = cursor.getString(2);
                int actividad = cursor.getInt(3);
                String responsable = cursor.getString(4);
                String hora = cursor.getString(5);
                int equipo = cursor.getInt(6);


                Prestamo prestamo = new Prestamo(idPrestamo,fechaPrestamo,fechaDevolucion, actividad, responsable, hora, equipo);
                lista.add(prestamo);
            }while (cursor.moveToNext());
        }else {

        }
        cursor.close();
        db.close();
        return lista;

    }

    public List<Autor> consulta(int idd){
        List<Autor> lista = new ArrayList<>();

        String queryString = "SELECT * FROM autor WHERE id = " + idd ;

        SQLiteDatabase db = DBHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do {
                int id = cursor.getInt(0);
                String nombre = cursor.getString(1);

                Autor autor =  new Autor(id, nombre);
                lista.add(autor);

            }while (cursor.moveToNext());
        }else {

        }

        cursor.close();
        db.close();
        return lista;

    }

    public List<Docente> consultaD(int idd){
        List<Docente> lista = new ArrayList<>();

        String queryString = "SELECT * FROM docente WHERE id = " + idd ;

        SQLiteDatabase db = DBHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do {
                int id = cursor.getInt(0);
                String nombre = cursor.getString(1);
                String apellido = cursor.getString(2);

                Docente docente =  new Docente(id, nombre, apellido);
                lista.add(docente);

            }while (cursor.moveToNext());
        }else {

        }

        cursor.close();
        db.close();
        return lista;

    }

    public List<Alumno> consultaA(String carnet){
        List<Alumno> lista = new ArrayList<>();

        // String queryString = "SELECT * FROM alumno WHERE carnet = " + carnet ;



        SQLiteDatabase db = DBHelper.getReadableDatabase();

        //Cursor cursor = db.rawQuery(queryString, null);

        String[] carnetd = {carnet};

        Cursor cursor = db.query("alumno", camposAlumno, "carnet = ?", carnetd, null,null,null);

        if(cursor.moveToFirst()){
            do {
                String carnet2 = cursor.getString(0);
                String nombre = cursor.getString(1);
                String apellido = cursor.getString(2);

                Alumno alumno =  new Alumno(carnet2, nombre, apellido);
                lista.add(alumno);

            }while (cursor.moveToNext());
        }else {

        }

        cursor.close();
        db.close();
        return lista;

    }

    public List<EquipoInformatico> consultaEquipo(String nombre){
        List<EquipoInformatico> lista = new ArrayList<>();

        SQLiteDatabase db = DBHelper.getReadableDatabase();

        String[] carnetd = {nombre};

        Cursor cursor = db.query("equipo", camposEquipo, "nombre = ?", carnetd, null,null,null);

        if(cursor.moveToFirst()){
            do {
                int id_equipo = cursor.getInt(0);
                String nombreEquipo = cursor.getString(1);
                String modeloEquipo = cursor.getString(2);
                String marcaEquipo = cursor.getString(3);
                String estadoEquipo = cursor.getString(4);
                String categoriaEquipo = cursor.getString(5);
                String fechaEquipoAdquisicion = cursor.getString(6);

                EquipoInformatico equipoInformatico =  new EquipoInformatico(id_equipo, nombreEquipo, modeloEquipo, marcaEquipo, estadoEquipo, categoriaEquipo, fechaEquipoAdquisicion);
                lista.add(equipoInformatico);

            }while (cursor.moveToNext());
        }else {

        }

        cursor.close();
        db.close();
        return lista;

    }

    public List<Usuario> consultaUsuario(String correo){
        List<Usuario> lista = new ArrayList<>();

        // String queryString = "SELECT * FROM alumno WHERE carnet = " + carnet ;



        SQLiteDatabase db = DBHelper.getReadableDatabase();

        //Cursor cursor = db.rawQuery(queryString, null);

        String[] carnetd = {correo};

        Cursor cursor = db.query("usuario", camposUsuario, "correo = ?", carnetd, null,null,null);

        if(cursor.moveToFirst()){
            do {

                String nombre = cursor.getString(0);
                String contrasena = cursor.getString(1);
                String correo2 = cursor.getString(2);
                String rol = cursor.getString(3);

                Usuario usuario = new Usuario(nombre, contrasena, correo2, rol);


                lista.add(usuario);

            }while (cursor.moveToNext());
        }else {

        }

        cursor.close();
        db.close();
        return lista;

    }


    public boolean eliminar(Libro libro){
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        String queryString = "DELETE FROM libro WHERE isbn =" + libro.getIsbn();
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()){
            return true;
        }else {
            return false;
        }
    }

    public boolean eliminar(Actividad actividad){
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        String queryString = "DELETE FROM actividad WHERE id =" + actividad.getIdActividad();
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()){
            return true;
        }else {
            return false;
        }
    }

    public boolean eliminar(Prestamo prestamo){
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        String queryString = "DELETE FROM prestamo WHERE id =" + prestamo.getIdPrestamo();
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()){
            return true;
        }else {
            return false;
        }
    }

    public boolean eliminar(EquipoInformatico equipoInformatico){
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        String queryString = "DELETE FROM equipo WHERE id =" + equipoInformatico.getId_equipo();
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()){
            return true;
        }else {
            return false;
        }
    }

    public boolean eliminar(Autor autor){

        SQLiteDatabase db = DBHelper.getWritableDatabase();
        String queryString = "DELETE FROM autor WHERE id = " + autor.getId();
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()){
            return true;
        }else {
            return false;
        }

    }

    public boolean eliminar(String alumno){

        String [] id = {alumno};
        SQLiteDatabase db = DBHelper.getWritableDatabase();


        db.delete("alumno", "carnet = ?",  id);

        return true;

    }

    public boolean eliminarU(String usuario){

        String [] id = {usuario};
        SQLiteDatabase db = DBHelper.getWritableDatabase();


        db.delete("usuario", "correo = ?",  id);

        return true;

    }


    public boolean eliminar(Docente docente){
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        String queryString = "DELETE FROM docente WHERE id = " + docente.getId();
        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()){
            return true;
        }else {
            return false;
        }
    }

    public String actualizar(Libro libro){

        if (verificarIntegridad(libro, 5)){
            String[] id = {String.valueOf(libro.getIsbn())};
            ContentValues contentValues = new ContentValues();


            contentValues.put("nombre_libro", libro.getNombreLibro());
            contentValues.put("autor", libro.getAutorId());
            contentValues.put("ejemplar", libro.getEjemplar());
            contentValues.put("editorial", libro.getEditorial());
            contentValues.put("idioma", libro.getIdioma());

            db.update("libro", contentValues, "isbn = ?", id);

            return "Actualizado";
        }
        else {
            return "Registro no existe";
        }

    }

    public String actualizar(Actividad actividad){

        if (verificarIntegridad(actividad, 10)){
            String[] id = {String.valueOf(actividad.getIdActividad())};
            ContentValues contentValues = new ContentValues();
            contentValues.put("nombre", actividad.getNombreActividad());
            contentValues.put("ubicacion", actividad.getUbicacion());

            db.update("actividad", contentValues, "id = ?", id);

            return "Actualizado";
        }
        else {
            return "Registro no existe";
        }

    }

    public String actualizar(Prestamo prestamo){

        if (verificarIntegridad(prestamo, 11)){

            String[] id = {String.valueOf(prestamo.getIdPrestamo())};
            ContentValues contentValues = new ContentValues();

            contentValues.put("fecha_prestamo", prestamo.getFechaPrestamo());
            contentValues.put("fecha_devolucion", prestamo.getFechaDevolucion());
            contentValues.put("actividad", prestamo.getActividad());
            contentValues.put("responsable", prestamo.getResponsable());
            contentValues.put("hora", prestamo.getHora());
            contentValues.put("equipo", prestamo.getEquipo());

            db.update("prestamo", contentValues, "id = ?", id);

            return "Actualizado";
        }
        else {
            return "Registro no existe";
        }

    }

    public String actualizar(Autor autor) {

        if (verificarIntegridad(autor, 1)){
            String[] id = {String.valueOf(autor.getId())};
            ContentValues contentValues = new ContentValues();
            contentValues.put("nombre", autor.getNombreAutor());

            db.update("autor", contentValues, "id = ?", id);

            return "Actualizado";
        }
        else {
            return "Registro no existe";
        }

    }

    public String actualizar(Categoria categoria) {

        if (verificarIntegridad(categoria, 4)){
            String[] id = {String.valueOf(categoria.getId_categoria())};
            ContentValues contentValues = new ContentValues();
            contentValues.put("nombre_categoria", categoria.getNombre_categoria());

            db.update("categoria", contentValues, "id_categoria = ?", id);

            return "Actualizado";
        }
        else {
            return "Registro no existe";
        }

    }

    public String actualizar(EquipoInformatico equipoInformatico) {

        if (verificarIntegridad(equipoInformatico, 7)){
            String[] id = {String.valueOf(equipoInformatico.getId_equipo())};
            ContentValues contentValues = new ContentValues();

            contentValues.put("nombre", equipoInformatico.getNombreEquipo());
            contentValues.put("modelo", equipoInformatico.getModeloEquipo());
            contentValues.put("marca", equipoInformatico.getMarcaEquipo());
            contentValues.put("estado", equipoInformatico.getEstado());
            contentValues.put("fecha", equipoInformatico.getFechaEquipoAdquisicion());
            contentValues.put("categoria", equipoInformatico.getCategoriaEquipo());

            db.update("equipo", contentValues, "id = ?", id);

            return "Actualizado";
        }
        else {
            return "Registro no existe";
        }

    }


    public String actualizar(Alumno alumno){
        if(verificarIntegridad(alumno,3)){
            String[] id = {alumno.getCarnet()};
            ContentValues contentValues = new ContentValues();
            contentValues.put("nombre", alumno.getNombre());
            contentValues.put("apellido", alumno.getApellidos());

            db.update("alumno", contentValues, "carnet = ?", id);
            return "Actualizado";
        }else {
            return "Registro no existe";
        }
    }

    public String actualizar(Usuario usuario){
        if(verificarIntegridad(usuario,6)){
            String[] id = {usuario.getCorreo()};
            ContentValues contentValues = new ContentValues();

            contentValues.put("nombre", usuario.getNombre());
            contentValues.put("contrasena", usuario.getContrasena());
            contentValues.put("rol", usuario.getRol());

            db.update("usuario", contentValues, "correo = ?", id);
            return "Actualizado";
        }else {
            return "Registro no existe";
        }
    }

    public String actualizar(Docente docente) {

        if (verificarIntegridad(docente, 2)){
            String[] id = {String.valueOf(docente.getId())};
            ContentValues contentValues = new ContentValues();
            contentValues.put("nombre", docente.getNombre());
            contentValues.put("apellido", docente.getApellido());

            db.update("docente", contentValues, "id = ?", id);

            return "Actualizado";
        }
        else {
            return "Registro no existe";
        }

    }




    public String inicio(String correo, String contrasena){

        String[] correo1 = {correo, contrasena};

        SQLiteDatabase db = DBHelper.getReadableDatabase();

        String adm = "Administrador";
        String sec = "Secreataria";

        Cursor c = db.rawQuery("select * from usuario where correo = ? AND contrasena = ?", correo1);

        if(c.moveToFirst()) {
            int id = c.getInt(0);
            String nombre = c.getString(1);
            String contras = c.getString(2);
            String email = c.getString(3);
            String rol = c.getString(4);

            if (email.equals(correo) && contras.equals(contrasena) && rol.equals(adm)) {
                return "admin";
            } else if (email.equals(correo) && contras.equals(contrasena) && rol.equals(sec)) {
                return "secre";
            } else {
                return "Usuario no coincide: " + email + contras + rol;
            }


        }else {
            return "Usuario no existe";
        }
    }


    public boolean verificarIntegridad(Object dato, int relacion)throws SQLException {

        switch (relacion) {
            case 1:
            {
                Autor autor = (Autor) dato;
                String [] id  = {String.valueOf(autor.getId())};
                abrir();
                Cursor cursor = db.query("autor", null, "id = ?", id, null, null, null);

                if (cursor.moveToFirst()){
                    return true;
                }else {
                    return false;
                }

            }

            case 2:
            {
                Docente docente = (Docente) dato;
                String [] id = {String.valueOf(docente.getId())};
                abrir();
                Cursor cursor = db.query("docente", null, "id = ?", id, null, null, null);

                if (cursor.moveToFirst()){
                    return true;

                }else {
                    return false;
                }

            }

            case 3:
            {
                Alumno alumno = (Alumno)dato;
                String [] id = {String.valueOf(alumno.getCarnet())};
                abrir();
                Cursor cursor = db.query("alumno", null, "carnet = ?", id, null, null, null);

                if(cursor.moveToFirst()){
                    return true;
                }else {
                    return false;
                }


            }

            case 4:{
                Categoria categoria = (Categoria) dato;
                String []id = {String.valueOf(categoria.getId_categoria())};
                abrir();
                Cursor cursor = db.query("categoria",null,"id_categoria =?",id,null,null,null);
                if (cursor.moveToFirst()){
                    return true;
                }else{
                    return false;
                }

            }

            case 5:
            {
                Libro libro = (Libro) dato;
                String [] id = {String.valueOf(libro.getIsbn())};
                abrir();
                Cursor cursor = db.query("libro", null, "isbn = ?", id, null, null, null);

                if(cursor.moveToFirst()){
                    return true;
                }else {
                    return false;
                }


            }

            case 6:
            {
                Usuario usuario = (Usuario) dato;
                String [] id = {String.valueOf(usuario.getCorreo())};
                abrir();
                Cursor cursor = db.query("usuario", null, "correo = ?", id, null, null, null);

                if(cursor.moveToFirst()){
                    return true;
                }else {
                    return false;
                }

            }

            case 7:
            {
                EquipoInformatico equipoInformatico = (EquipoInformatico) dato;
                String [] id = {String.valueOf(equipoInformatico.getId_equipo())};
                abrir();
                Cursor cursor = db.query("equipo", null, "id = ?", id, null, null, null);

                if(cursor.moveToFirst()){
                    return true;
                }else {
                    return false;
                }

            }

            case 8: {
                Tesis tesis = (Tesis) dato;
                String[] id = {String.valueOf(tesis.getTitulo_tesis())};
                abrir();
                Cursor cursor = db.query("tesis", null, "nombre_tesis = ?", id, null, null, null);

                if (cursor.moveToFirst()) {
                    return true;
                } else {
                    return false;
                }
            }

            case 9:{

                Tesis tesis = (Tesis) dato;
                String[] id = {String.valueOf(tesis.getId_tesis())};
                abrir();
                Cursor cursor = db.query("tesis", null, "id_tesis = ?", id, null, null, null);

                if (cursor.moveToFirst()) {
                    return true;
                } else {
                    return false;
                }

            }

            case 10:{

                Actividad actividad = (Actividad) dato;
                String[] id = {String.valueOf(actividad.getIdActividad())};
                abrir();
                Cursor cursor = db.query("actividad", null, "id = ?", id, null, null, null);

                if(cursor.moveToFirst()){
                    return true;
                }else {
                    return false;
                }

            }

            case 11:{

                Prestamo prestamo = (Prestamo) dato;
                String [] id =  {String.valueOf(prestamo.getIdPrestamo())};
                abrir();
                Cursor cursor = db.query("prestamo", null, "id = ?", id, null, null, null);

                if(cursor.moveToFirst()){
                    return true;
                }else {
                    return false;
                }


            }


            default:
                return false;
        }
    }


    public String llenarUsuario(){
        abrir();

        String queryString = "SELECT * FROM usuario";

        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){

        }else{

            Usuario usuario = new Usuario();
            usuario.setNombre("Administrador");
            usuario.setCorreo("admin@mail.com");
            usuario.setContrasena("123456");
            usuario.setRol("Administrador");
            insertar(usuario);

            Usuario usuario2 = new Usuario();
            usuario2.setNombre("Secretaria");
            usuario2.setCorreo("secre@mail.com");
            usuario2.setContrasena("123456");
            usuario2.setRol("Secretaria");
            insertar(usuario2);


        }


        db.execSQL("DELETE FROM rol");
        Rol rol = new Rol();
        rol.setIdRol(1111);
        rol.setNombreRol("Administrador");
        insertar(rol);

        Rol rol2= new Rol();
        rol2.setIdRol(2222);
        rol2.setNombreRol("Secretaria");
        insertar(rol2);

        cerrar();
        return "Usuarios de prueba creados";
    }



}
