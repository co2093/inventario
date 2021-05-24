package sv.ues.fia.eisi.pdm115proyecto1;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ControlDB {

   // private static final String [] camposUsuario = new String[] {"id, nombre, correo, contrasena"};
   private static final String [] camposAutor = new String[] {"id", "nombre"};
   private static final String [] camposAlumno = new String[] {"carnet", "nombre", "apellido"};


    private final Context context;
    private SQLiteDatabase db;
    private DatabaseHelper DBHelper;

    public ControlDB(Context context) {
        this.context = context;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper{

        private static final String BASE_DATOS = "inve123.s3db";
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
                db.execSQL("CREATE TABLE usuario (id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, nombre VARCHAR (128), correo VARCAHR (128), contrasena VARCHAR(128));");

                //Damaris
                db.execSQL("CREATE TABLE razon (id INTEGER PRIMARY KEY AUTOINCREMENT,nombre_razon VARCHAR(128) , descripcion VARCHAR(128), equipo VARCHAR(10), fecha VARCHAR(128) , estado VARCHAR(30));");

                db.execSQL("CREATE TABLE tesis (id_tesis INTEGER NOT NULL PRIMARY KEY,nombre_tesis VARCHAR (256),titulo_tesis VARCHAR (256),fecha_publicacion VARCHAR(128),id_idioma INTEGER,id_autor_tesis INTEGER);");
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

    public String insertar(Usuario usuario){
        ContentValues contentValues = new ContentValues();
        contentValues.put("nombre", usuario.getNombre());
        contentValues.put("correo", usuario.getCorreo());
        contentValues.put("contrasena", usuario.getContrasena());
        db.insert("usuario", null, contentValues);
        return "OK";
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




    public boolean inicio(String correo, String contrasena){

        String[] correo1 = {correo, contrasena};

        abrir();

        Cursor c = db.rawQuery("select * from usuario where correo = ? AND contrasena = ?", correo1);

        if(c.moveToFirst()) {

            return true;
        }

            return false;
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

            default:
                return false;
        }
    }


    public String llenarUsuario(){
        abrir();
        db.execSQL("DELETE FROM usuario");
            Usuario usuario = new Usuario();
            usuario.setNombre("Administrador");
            usuario.setCorreo("admin@mail.com");
            usuario.setContrasena("123456");
            insertar(usuario);
        cerrar();
        return "Usuarios de prueba creados";
    }



}
