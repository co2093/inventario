package sv.ues.fia.eisi.pdm115proyecto1;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Paint;
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
    private static final String [] camposControl = new String[] {"id", "categoria", "existencias", "prestamos"};


    private final Context context;
    private SQLiteDatabase db;
    private DatabaseHelper DBHelper;

    public ControlDB(Context context) {
        this.context = context;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper{

        private static final String BASE_DATOS = "proyecto1_vfinal43.s3db";
        private static final int version = 1;
        public DatabaseHelper (Context context){
            super(context, BASE_DATOS, null, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db){
            try{

                //Tablas
                db.execSQL("CREATE TABLE autor(id INTEGER NOT NULL PRIMARY KEY, nombre VARCHAR (128));");
                db.execSQL("CREATE TABLE docente(id INTEGER NOT NULL PRIMARY KEY, nombre VARCHAR (128), apellido VARCHAR(128));");
                db.execSQL("CREATE TABLE alumno (carnet VARCHAR(128) NOT NULL PRIMARY KEY, nombre VARCHAR (128), apellido VARCHAR(128));");
                db.execSQL("CREATE TABLE usuario (id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, nombre VARCHAR (128), contrasena VARCAHR (128), correo VARCHAR(128), rol VARCHAR(128));");
                db.execSQL("CREATE TABLE rol (id INTEGER PRIMARY KEY, nombre VARCHAR (128))");
                db.execSQL("CREATE TABLE equipo (id INTEGER PRIMARY KEY, nombre VARCHAR (128), modelo VARCHAR (128), marca VARCHAR (128), estado VARCHAR (128), categoria VARCHAR (128), fecha VARCHAR (128))");
                db.execSQL("CREATE TABLE actividad (id INTEGER PRIMARY KEY, nombre VARCHAR (128), ubicacion VARCHAR (128));");
                db.execSQL("CREATE TABLE prestamo (id INTEGER PRIMARY KEY, fecha_prestamo VARCHAR (128), fecha_devolucion VARCHAR(128), actividad INTEGER, responsable VARCHAR (128), categoria VARCHAR (128), equipo INTEGER);");
                db.execSQL("CREATE TABLE control_fisico (id INTEGER PRIMARY KEY AUTOINCREMENT, categoria VARCHAR (128), existencias INTEGER, prestamos INTEGER);");
                db.execSQL("CREATE TABLE idioma (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre VARCHAR (128));");
                db.execSQL("CREATE TABLE pais (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre VARCHAR (128));");
                db.execSQL("CREATE TABLE editorial (id INTEGER PRIMARY KEY,nombre VARCHAR (128), pais VARCHAR(128));");
                db.execSQL("CREATE TABLE razon (id INTEGER PRIMARY KEY AUTOINCREMENT,nombre_razon VARCHAR(128) , descripcion VARCHAR(128), equipo VARCHAR(10), fecha VARCHAR(128) , estado VARCHAR(30));");
                db.execSQL("CREATE TABLE libro (isbn INTEGER NOT NULL PRIMARY KEY,nombre_libro VARCHAR (256),autor INTEGER (13),ejemplar INTEGER,editorial VARCHAR(128), idioma VARCHAR(128));");
                db.execSQL("CREATE TABLE tesis (id_tesis INTEGER NOT NULL PRIMARY KEY,nombre_tesis VARCHAR (256), fecha_publicacion VARCHAR(128),idioma VARCHAR(128),id_autor_tesis VARCHAR);");
                db.execSQL("CREATE TABLE categoria (id_categoria INTEGER PRIMARY KEY AUTOINCREMENT, nombre_categoria VARCHAR (256) NOT NULL)");

                //Triggers
                db.execSQL("CREATE TRIGGER actualizar_estado_equipo\n" +
                        "AFTER INSERT\n" +
                        "ON prestamo \n" +
                        "FOR EACH ROW\n" +
                        "BEGIN \n" +
                        "        UPDATE equipo SET estado = \"Prestado\" WHERE equipo.id == new.equipo;\n" +
                        "END");

                db.execSQL("CREATE TRIGGER actualizar_estado_equipo_dos\n" +
                        "AFTER DELETE\n" +
                        "ON prestamo \n" +
                        "FOR EACH ROW\n" +
                        "BEGIN \n" +
                        "        UPDATE equipo SET estado = \"Disponible\" WHERE equipo.id == old.equipo;\n" +
                        "END");

                db.execSQL("CREATE TRIGGER control_fisico_categoria\n" +
                        "AFTER INSERT\n" +
                        "ON categoria \n" +
                        "FOR EACH ROW\n" +
                        "BEGIN\n" +
                        " \n" +
                        "        INSERT INTO control_fisico (categoria, existencias, prestamos) VALUES(new.nombre_categoria, 0,0);\n" +
                        "\n" +
                        "END");

                db.execSQL("CREATE TRIGGER actualizar_control_existencias\n" +
                        "AFTER INSERT\n" +
                        "ON equipo \n" +
                        "FOR EACH ROW\n" +
                        "BEGIN \n" +
                        "        UPDATE control_fisico SET existencias = existencias+1 WHERE new.categoria == control_fisico.categoria;\n" +
                        "END");

                db.execSQL("CREATE TRIGGER actualizar_control_existencias_dos\n" +
                        "AFTER DELETE\n" +
                        "ON equipo \n" +
                        "FOR EACH ROW\n" +
                        "BEGIN \n" +
                        "     UPDATE control_fisico SET existencias = existencias-1 WHERE old.categoria == control_fisico.categoria;\n" +
                        "END");

                db.execSQL("CREATE TRIGGER actualizar_control_prestamo\n" +
                        "AFTER INSERT\n" +
                        "ON prestamo \n" +
                        "FOR EACH ROW\n" +
                        "BEGIN \n" +
                        "        UPDATE control_fisico SET prestamos = prestamos+1 WHERE control_fisico.categoria == new.categoria;\n" +
                        "END");

                db.execSQL("CREATE TRIGGER actualizar_control_prestamo_dos\n" +
                        "AFTER INSERT\n" +
                        "ON prestamo \n" +
                        "FOR EACH ROW\n" +
                        "BEGIN \n" +
                        "        UPDATE control_fisico SET existencias = existencias -1 WHERE control_fisico.categoria == new.categoria;\n" +
                        "END");

                db.execSQL("CREATE TRIGGER actualizar_existencia_por_prestamo\n" +
                        "AFTER DELETE\n" +
                        "ON prestamo \n" +
                        "FOR EACH ROW\n" +
                        "BEGIN \n" +
                        "      UPDATE control_fisico SET existencias = existencias+1 WHERE control_fisico.categoria == old.categoria;\n" +
                        "END");

                db.execSQL("CREATE TRIGGER actualizar_prestamo_control\n" +
                        "AFTER DELETE\n" +
                        "ON prestamo \n" +
                        "FOR EACH ROW\n" +
                        "BEGIN \n" +
                        "      UPDATE control_fisico SET prestamos = prestamos-1 WHERE control_fisico.categoria == old.categoria;\n" +
                        "END");


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
            contentValues.put("categoria", prestamo.getCategoriaPrestamo());
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

    public String insertar(Editorial editorial){

        if (verificarIntegridad(editorial, 12)){
            return "Ya existe una editorial con este id";
        }else {

            ContentValues contentValues = new ContentValues();
            contentValues.put("id", editorial.getId());
            contentValues.put("nombre", editorial.getNombre());
            contentValues.put("pais", editorial.getPais());

            db.insert("editorial", null, contentValues);
            return "OK";
        }
    }

    public String insertar(Idioma idioma){


            ContentValues contentValues = new ContentValues();
            contentValues.put("nombre", idioma.getNombre());

            db.insert("idioma", null, contentValues);
            return "";
    }

    public String insertar(Pais pais){


        ContentValues contentValues = new ContentValues();
        contentValues.put("nombre", pais.getNombre());

        db.insert("pais", null, contentValues);
        return "";
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
            return "Ya existe una categoria con ese nombre";
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

        SQLiteDatabase db = DBHelper.getReadableDatabase();
        String carnet = categoria.getNombre_categoria();
        String[] carnetd = {carnet};

        Cursor cursor = db.query("equipo", camposEquipo, "categoria = ?", carnetd, null,null,null);

        if(cursor.moveToFirst()){
            return false;
        }else {
            SQLiteDatabase db2 = DBHelper.getWritableDatabase();
            String queryString2 = "DELETE FROM categoria WHERE id_categoria = " + categoria.getId_categoria();
            Cursor cursor2 = db2.rawQuery(queryString2, null);

            if (cursor2.moveToFirst()){
                return true;
            }else {
                return false;
            }
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

    public List<Editorial> getEditorial(){
        List<Editorial> lista = new ArrayList<>();
        String queryString = "SELECT * FROM editorial";
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String nombre = cursor.getString(1);
                String pais = cursor.getString(2);
                Editorial editorial = new Editorial(id, nombre, pais);
                lista.add(editorial);
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

    public String getCategoriaEquipo(int equipo){
        SQLiteDatabase db = DBHelper.getReadableDatabase();

        String nombre = "";

        String queryString = "SELECT * FROM equipo WHERE id = " + equipo;
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
                nombre = cursor.getString(5);

        }else {

        }
        cursor.close();
        db.close();
        return nombre;


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

    public List<ControlFisico> getControlFisico(){

        List<ControlFisico> lista = new ArrayList<>();

        String queryString = "SELECT * FROM control_fisico";
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String categoria = cursor.getString(1);
                int existencias = cursor.getInt(2);
                int prestamos = cursor.getInt(3);


                ControlFisico controlFisico = new ControlFisico(id,categoria,existencias, prestamos);
                lista.add(controlFisico);
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
                String categoria = cursor.getString(5);
                int equipo = cursor.getInt(6);


                Prestamo prestamo = new Prestamo(idPrestamo,fechaPrestamo,fechaDevolucion, actividad, responsable, categoria, equipo);
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

    public List<Integer> getRazonID(){
        List<Integer> lista = new ArrayList<>();

        String queryString = "SELECT * FROM razon";

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

    public List<String> getEditorialNombres(){
        List<String> lista = new ArrayList<>();

        String queryString = "SELECT * FROM editorial";

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

    public List<String> getIdiomas(){
        List<String> lista = new ArrayList<>();

        String queryString = "SELECT * FROM idioma";

        SQLiteDatabase db = DBHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do {
                String nombre = cursor.getString(1);

                lista.add(nombre);

            }while (cursor.moveToNext());
        }else {

        }

        cursor.close();
        db.close();
        return lista;
    }

    public List<String> getPaises(){
        List<String> lista = new ArrayList<>();

        String queryString = "SELECT * FROM pais";

        SQLiteDatabase db = DBHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do {
                String nombre = cursor.getString(1);

                lista.add(nombre);

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

        SQLiteDatabase db = DBHelper.getReadableDatabase();

        String es = "Disponible";

        String[] carnetd = {es};

        Cursor cursor = db.query("equipo", camposEquipo, "estado = ?", carnetd, null,null,null);

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

    public List<Editorial> consultaEditorial(int id){

        List<Editorial> lista = new ArrayList<>();
        String queryString = "SELECT * FROM editorial WHERE id = " + id;
        SQLiteDatabase db = DBHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            do{
                int id2 = cursor.getInt(0);
                String nombre = cursor.getString(1);
                String pais = cursor.getString(2);

                Editorial editorial = new Editorial(id2,nombre,pais);
                lista.add(editorial);
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
                String categoria = cursor.getString(5);
                int equipo = cursor.getInt(6);


                Prestamo prestamo = new Prestamo(idPrestamo,fechaPrestamo,fechaDevolucion, actividad, responsable, categoria, equipo);
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

    public List<ControlFisico> consultaControlFisico(String carnet){
        List<ControlFisico> lista = new ArrayList<>();

        // String queryString = "SELECT * FROM alumno WHERE carnet = " + carnet ;



        SQLiteDatabase db = DBHelper.getReadableDatabase();

        //Cursor cursor = db.rawQuery(queryString, null);

        String[] carnetd = {carnet};

        Cursor cursor = db.query("control_fisico", camposControl, "categoria = ?", carnetd, null,null,null);

        if(cursor.moveToFirst()){
            do {

                    int id = cursor.getInt(0);
                    String categoria = cursor.getString(1);
                    int existencias = cursor.getInt(2);
                    int prestamos = cursor.getInt(3);


                    ControlFisico controlFisico = new ControlFisico(id,categoria,existencias, prestamos);
                    lista.add(controlFisico);

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

    public boolean eliminar(Editorial editorial){
        SQLiteDatabase db = DBHelper.getWritableDatabase();
        String queryString = "DELETE FROM libro WHERE id =" + editorial.getId();
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

    public String actualizar(Editorial editorial){

        if (verificarIntegridad(editorial, 12)){
            String[] id = {String.valueOf(editorial.getId())};
            ContentValues contentValues = new ContentValues();
            contentValues.put("nombre", editorial.getNombre());
            contentValues.put("pais", editorial.getPais());

            db.update("editorial", contentValues, "id = ?", id);

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
            contentValues.put("categoria", prestamo.getCategoriaPrestamo());
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
        String sec = "Secretaria";

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
                String []id = {String.valueOf(categoria.getNombre_categoria())};
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

            case 12:{

                Editorial editorial = (Editorial) dato;
                String [] id =  {String.valueOf(editorial.getId())};
                abrir();
                Cursor cursor = db.query("editorial", null, "id = ?", id, null, null, null);

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

        db.execSQL("DELETE FROM idioma");
        Idioma idioma = new Idioma();
        idioma.setNombre("Inglés");
        insertar(idioma);

        Idioma idioma2 = new Idioma();
        idioma2.setNombre("Español");
        insertar(idioma2);

        Idioma idioma3 = new Idioma();
        idioma3.setNombre("Francés");
        insertar(idioma3);

        Idioma idioma4 = new Idioma();
        idioma4.setNombre("Alemán");
        insertar(idioma4);

        Idioma idioma5 = new Idioma();
        idioma5.setNombre("Portugués");
        insertar(idioma5);


        Idioma idioma7 = new Idioma();
        idioma7.setNombre("Otro");
        insertar(idioma7);

        db.execSQL("DELETE FROM pais");
        Pais pais = new Pais();
        pais.setNombre("El Salvador");
        insertar(pais);

        Pais pais2 = new Pais();
        pais2.setNombre("México");
        insertar(pais2);

        Pais pais3 = new Pais();
        pais3.setNombre("España");
        insertar(pais3);

        Pais pais4 = new Pais();
        pais4.setNombre("Costa Rica");
        insertar(pais4);

        Pais pais5 = new Pais();
        pais5.setNombre("Brasil");
        insertar(pais5);

        Pais pais6 = new Pais();
        pais6.setNombre("Francia");
        insertar(pais6);

        Pais pais7 = new Pais();
        pais7.setNombre("Estados Unidos");
        insertar(pais7);

        Pais pais8 = new Pais();
        pais8.setNombre("Canadá");
        insertar(pais8);

        Pais pais9 = new Pais();
        pais9.setNombre("Alemania");
        insertar(pais9);

        Pais pais0 = new Pais();
        pais0.setNombre("Otro");
        insertar(pais0);




        cerrar();
        return "Usuarios de prueba creados";
    }



}
