package sv.ues.fia.eisi.pdm115proyecto1;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;


public class Controlador {

    public static String obtenerRespuestaPeticion(String url, Context ctx) {

        String respuesta = " ";

        // Estableciendo tiempo de espera del servicio
        HttpParams parametros = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(parametros, 3000);
        HttpConnectionParams.setSoTimeout(parametros, 5000);

        // Creando objetos de conexion
        HttpClient cliente = new DefaultHttpClient(parametros);
        HttpGet httpGet = new HttpGet(url);
        try {
            HttpResponse httpRespuesta = cliente.execute(httpGet);
            StatusLine estado = httpRespuesta.getStatusLine();
            int codigoEstado = estado.getStatusCode();
            if (codigoEstado == 200) {
                HttpEntity entidad = httpRespuesta.getEntity();
                respuesta = EntityUtils.toString(entidad);
            }
        } catch (Exception e) {
            Toast.makeText(ctx, "Error en la conexion", Toast.LENGTH_LONG)
                    .show();
            // Desplegando el error en el LogCat
            Log.v("Error de Conexion", e.toString());
        }
        return respuesta;
    }

    public static String obtenerRespuestaPost(String url, JSONObject obj, Context ctx){

        String respuesta = " ";

        try{
            HttpParams parametros = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(parametros, 3000);
            HttpConnectionParams.setSoTimeout(parametros, 5000);
            HttpClient cliente = new DefaultHttpClient(parametros);
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("content-type", "application/json");
            StringEntity nuevaEntidad = new StringEntity(obj.toString());
            httpPost.setEntity(nuevaEntidad);
            Log.v("Peticion",url);
            Log.v("POST", httpPost.toString());
            HttpResponse httpRespuesta = cliente.execute(httpPost);
            StatusLine estado = httpRespuesta.getStatusLine();

            int codigoEstado = estado.getStatusCode();

            if (codigoEstado == 200) {
                respuesta = Integer.toString(codigoEstado);
                Log.v("respuesta",respuesta);
            }else {
                Log.v("respuesta",Integer.toString(codigoEstado));
            }

        }catch (Exception e){
            Toast.makeText(ctx, "Error en la conexion", Toast.LENGTH_LONG).show();
            Log.v("Error de Conexion", e.toString());
        }
        return respuesta;
    }

    public static void insertarDocenteExterno(String peticion, Context ctx) {

        String json = obtenerRespuestaPeticion(peticion, ctx);
        try {

            JSONObject resultado = new JSONObject(json);

            Toast.makeText(ctx, "Registro ingresado"+ resultado.getJSONArray("resultado").toString(), Toast.LENGTH_LONG).show();
            int respuesta = resultado.getInt("resultado");
            if (respuesta == 1)
                Toast.makeText(ctx, "Registro ingresado", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(ctx, "Error registro duplicado", Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void insertarAlumnoExternoDos(String peticion, Context ctx) {

        String json = obtenerRespuestaPeticion(peticion, ctx);
        try {

            JSONObject resultado = new JSONObject(json);

            Toast.makeText(ctx, "Registro ingresado"+ resultado.getJSONArray("resultado").toString(), Toast.LENGTH_LONG).show();
            int respuesta = resultado.getInt("resultado");
            if (respuesta == 1)
                Toast.makeText(ctx, "Registro ingresado", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(ctx, "Error registro duplicado", Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void insertarAutorExterno(String peticion, Context ctx) {

        String json = obtenerRespuestaPeticion(peticion, ctx);
        try {

            JSONObject resultado = new JSONObject(json);

            Toast.makeText(ctx, "Registro ingresado"+ resultado.getJSONArray("resultado").toString(), Toast.LENGTH_LONG).show();
            int respuesta = resultado.getInt("resultado");
            if (respuesta == 1)
                Toast.makeText(ctx, "Registro ingresado", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(ctx, "Error registro duplicado", Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void insertarLibroExterno(String peticion, Context ctx) {

        String json = obtenerRespuestaPeticion(peticion, ctx);
        try {

            JSONObject resultado = new JSONObject(json);

            Toast.makeText(ctx, "Registro ingresado"+ resultado.getJSONArray("resultado").toString(), Toast.LENGTH_LONG).show();
            int respuesta = resultado.getInt("resultado");
            if (respuesta == 1)
                Toast.makeText(ctx, "Registro ingresado", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(ctx, "Error registro duplicado", Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static void insertarEquipoExternoSW(String peticion, Context ctx) {

        String json = obtenerRespuestaPeticion(peticion, ctx);
        try {

            JSONObject resultado = new JSONObject(json);

            Toast.makeText(ctx, "Registro ingresado"+ resultado.getJSONArray("resultado").toString(), Toast.LENGTH_LONG).show();
            int respuesta = resultado.getInt("resultado");
            if (respuesta == 1)
                Toast.makeText(ctx, "Registro ingresado", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(ctx, "Error registro duplicado", Toast.LENGTH_LONG).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public static List<Autor> obtenerAutorExterno(String json, Context ctx) {

        List<Autor> listAutor = new ArrayList<Autor>();

        try {
            JSONArray materiasJSON = new JSONArray(json);
            for (int i = 0; i < materiasJSON.length(); i++) {
                JSONObject obj = materiasJSON.getJSONObject(i);
                Autor autor = new Autor();
                autor.setId(obj.getInt("id"));
                autor.setNombreAutor(obj.getString("nombre"));
                listAutor.add(autor);
            }
            return listAutor;
        } catch (Exception e) {
            Toast.makeText(ctx, "Error en parseOO de JSON", Toast.LENGTH_LONG)
                    .show();
            return null;
        }
    }

    public static List<Docente> obtenerDocenteExterno(String json, Context ctx) {

        List<Docente> listDocente = new ArrayList<Docente>();

        try {
            JSONArray materiasJSON = new JSONArray(json);
            for (int i = 0; i < materiasJSON.length(); i++) {
                JSONObject obj = materiasJSON.getJSONObject(i);
                Docente docente = new Docente();
                docente.setId(obj.getInt("id"));
                docente.setNombre(obj.getString("nombre"));
                docente.setApellido(obj.getString("apellido"));
                listDocente.add(docente);
            }
            return listDocente;
        } catch (Exception e) {
            Toast.makeText(ctx, "Error en parseOO de JSON", Toast.LENGTH_LONG)
                    .show();
            return null;
        }
    }

    public static List<Libro> obtenerLibroExterno(String json, Context ctx) {

        List<Libro> listaLibro = new ArrayList<Libro>();

        try {
            JSONArray materiasJSON = new JSONArray(json);
            for (int i = 0; i < materiasJSON.length(); i++) {
                JSONObject obj = materiasJSON.getJSONObject(i);
                Libro libro = new Libro();
                libro.setIsbn(obj.getInt("isbn"));
                libro.setNombreLibro(obj.getString("nombre_libro"));
                libro.setAutorId(obj.getInt("autor"));
                libro.setEjemplar(obj.getInt("ejemplar"));
                libro.setEditorial(obj.getString("editorial"));
                libro.setIdioma(obj.getString("idioma"));
                listaLibro.add(libro);
            }
            return listaLibro;
        } catch (Exception e) {
            Toast.makeText(ctx, "Error en parseOO de JSON", Toast.LENGTH_LONG)
                    .show();
            return null;
        }
    }





}
