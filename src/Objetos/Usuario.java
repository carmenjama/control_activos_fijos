
package Objetos;

import Capa_ConexionBD.Conexion;
import Capa_Mensajes.Mensajes;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Usuario {
    Conexion conexion = new Conexion();
    ResultSet resultado;
    String sql;
    Mensajes mensajes = new Mensajes ();
    public String nombreUsuario = "";
    
    public boolean Conexion() {
        if(conexion.crearConexion()==true){
            return true;
        }else{
            return false;
        }
    }
    
    public int validar_ingreso(String nombre_usuario, String clave_usuario){
       int var_retorno=0;
       sql="select * from Login_TMAEUSUCON('"+nombre_usuario+"','"+clave_usuario+"');";
       resultado=conexion.ejecutarSQLSelect(sql);
       try {
           while(resultado.next()){
               var_retorno=resultado.getInt("login_tmaeusucon");
               nombreUsuario = nombre_usuario;
           }
       } catch (SQLException ex) {
           var_retorno=2;
       }
        return var_retorno;
    }
    
    public int tipo_usuario (String nombre_usuario, String clave_usuario){
        int var_retorno=0;
        sql="select * from tipoUsuario_TMAEUSUCON('"+nombre_usuario+"','"+clave_usuario+"');";
        resultado=conexion.ejecutarSQLSelect(sql);
        try {
            while(resultado.next()){
                var_retorno=resultado.getInt("tipousuario_tmaeusucon");
            }
        } catch (SQLException ex) {
            var_retorno=0;
        }
        return var_retorno;
    } 
}
