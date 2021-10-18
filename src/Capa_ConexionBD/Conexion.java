/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//en package le ponen el del proyecto que este lo creé en otro proyecto solo para hacer esta clase conexion
package Capa_ConexionBD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Usuario
 */
public class Conexion {

    private static Connection conexion = null;
    public int idUsuarioCorreo = 0;
    public String nombreUsuario = "";
    
    /**
     * Método utilizado para recuperar el valor del atributo conexion
     *
     * @return conexion contiene el estado de la conexión
     *
     */
    public Connection getConexion() {
        return conexion;
    }

    /**
     * Método utilizado para establecer la conexión con la base de datos
     *
     * @return estado regresa el estado de la conexión, true si se estableció la
     * conexión, falso en caso contrario
     */
    static String mensaje;
    static Capa_Mensajes.Mensajes msj = new Capa_Mensajes.Mensajes();

    public static boolean crearConexion() {
        try {
            Class.forName("org.postgresql.Driver");
            conexion = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ActiSoft", "postgres", "12345");
            if (conexion != null) {
                return true;
            }
            return true;
        } catch (SQLException ex) {
            mensaje = "No se pudo establecer conexiòn con la base de datos.";
            msj.msg_advertencia(null, mensaje);
            return false;
        } catch (ClassNotFoundException ex) {
            mensaje = "No se pudo establecer conexiòn con la base de datos.";
            msj.msg_advertencia(null, mensaje);
        }
        return true;
    }

    /**
    *
    *Método utilizado para realizar las instrucciones: INSERT, DELETE y UPDATE
    *@param sql Cadena que contiene la instrucción SQL a ejecutar
    *@return estado regresa el estado de la ejecución, true(éxito) o false(error)
    *
    */
    public  static boolean ejecutarSQL(String sql)
    {
     if (crearConexion()==true){
       try {
          PreparedStatement sentencia = conexion.prepareStatement(sql);
          sentencia.executeQuery();
          return true;
       } catch (SQLException ex) {
           
           //System.out.println("Algo sucede no se ejecuto:"+ex);
            return true;
       }
       catch(NullPointerException n){
           return false;
       }
     }
     else{return true;}
    }

    /**
    *
    *Método utilizado para realizar la instrucción SELECT
    *@param sql Cadena que contiene la instrucción SQL a ejecutar
    *@return resultado regresa los registros generados por la consulta
    *
    */
    public static ResultSet ejecutarSQLSelect(String sql)
    {
       ResultSet resultado;
      if (crearConexion()==true){
       try {
          PreparedStatement sentencia = conexion.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
          resultado = sentencia.executeQuery();
          return resultado;
       } catch (SQLException ex ) {
          System.err.println("Error: "+ex);
          return null;
       }catch(NullPointerException e){
           System.err.println("Error: "+e);
          return null;
       }
          
      }
      System.out.println("No hubo conexion");
          return null;
    }
    
     /**
    *
    *Método utilizado para verificar si esta correcta la clave
    *@param int id del usuario logueado
    *@param String clave ingresada por el usuario
    *@return boolean,True si esta correcto el usuario y la contrasena, false si esta incorrecto
    *
    */
       public boolean ejecutarSQLVerificarClave(String UsuarioName, String clave) {
        try {
            PreparedStatement sentencia = conexion.prepareStatement("select * from tmaeusucon where nombre_usuario= '"+UsuarioName+"' and clave_usuario= '"+clave+"'");
            ResultSet resultado = sentencia.executeQuery();
            if (resultado.next()) {
                return (true);
            }
            return (false);

        } catch (SQLException ex) {
            return false;
        }
    }
    
       public boolean ejecutarSQLVerificarCorreo(String correo) {
        try {
            // PreparedStatement sentencia = conexion.prepareStatement("select id_persona from tmaepercon where correo_persona = '"+correo+"'");
            PreparedStatement sentencia = conexion.prepareStatement("select p.id_persona, u.nombre_usuario from tmaepercon as p INNER JOIN tmaeusucon as u ON p.id_persona = u.id_persona where correo_persona = '"+correo+"'");
            ResultSet resultado = sentencia.executeQuery();
            if (resultado.next()) {
                idUsuarioCorreo = resultado.getInt("id_persona");
                nombreUsuario = resultado.getString("nombre_usuario");
                return (true);
            }
            return (false);
        } catch (SQLException ex) {
            return false;
        }
    }
       
       public String generarCorreoActualizar() {
        String clave = null;
        this.crearConexion();
        ResultSet resultado = this.ejecutarSQLSelect("select generadorclaves_tmaeusucon()");
        
        try {
            while (resultado.next()) {
                 clave = resultado.getString("generadorclaves_tmaeusucon");
                 this.ejecutarSQL("Update tmaeusucon SET clave_usuario = '"+clave+"' where id_persona = "+idUsuarioCorreo);
            }
        } catch (SQLException ex) {
            return clave;
        }

        return clave;
    }
       
       public static  int verificarConfiguracion() {
        int configurar =0;
        crearConexion();
        ResultSet resultado = ejecutarSQLSelect("select cod_instalacion from TMAEINSCON where id_institucion=1");
        
        try {
            while (resultado.next()) {
                 configurar = resultado.getInt("cod_instalacion");
            }
        } catch (SQLException ex) {
            return configurar;
        }

        return configurar;
    }
       
       
}
