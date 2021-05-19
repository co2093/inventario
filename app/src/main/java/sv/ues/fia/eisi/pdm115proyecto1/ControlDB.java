package sv.ues.fia.eisi.pdm115proyecto1;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.concurrent.atomic.DoubleAccumulator;
import java.util.function.DoubleBinaryOperator;

public class ControlDB {

   // private static final String [] camposUsuario = new String[] {"id, nombre, correo, contrasena"};

    private final Context context;
    private SQLiteDatabase db;
    private DatabaseHelper DBHelper;

    public ControlDB(Context context) {
        this.context = context;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper{

        private static final String BASE_DATOS = "inv.s3db";
        private static final int version = 1;
        public DatabaseHelper (Context context){
            super(context, BASE_DATOS, null, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db){
            try{


                db.execSQL("CREATE TABLE autor(id INTEGER NOT NULL PRIMARY KEY, nombre VARCHAR (128));");
                db.execSQL("CREATE TABLE docente(id INTEGER NOT NULL PRIMARY KEY, nombre VARCHAR (128), apellido VARCHAR(128));");
                db.execSQL("CREATE TABLE alumno (id INTEGER NOT NULL PRIMARY KEY, nombre VARCHAR (128), apellido VARCHAR(128));");
                db.execSQL("CREATE TABLE usuario (id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, nombre VARCHAR (128), correo VARCAHR (128), contrasena VARCHAR(128));");


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

    public boolean inicio(String correo, String contrasena){

        String[] correo1 = {correo, contrasena};

        abrir();

        Cursor c = db.rawQuery("select * from usuario where correo = ? AND contrasena = ?", correo1);

        if(c.moveToFirst()) {

            return true;
        }

            return false;
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
