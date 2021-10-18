/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objetos;

import Capa_ConexionBD.Conexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Ana
 */
public class Marcas {
    public static ArrayList<Integer> lista_idArea=new ArrayList<Integer>();
    public static String[] LeerMarcas(String Tipo){
        String[] seleccionar = {"Seleccionar"};
        String[] marcas = {"Otros"};
        String[] camaras={"Seleccionar", "Samsung","Sony","EverFocus", "Avtech", "Otros"};
        String[] cpu={"Seleccionar","ACER","DELL","HP", "TOSHIBA","Otros"};
        String[] escritorio={"Seleccionar","ACER","DELL","HP", "TOSHIBA","Otros"};
        String[] detectorhuellas={"Seleccionar","Secun Gen Hanster"};
        String[] impresora={"Seleccionar","Canon","Epson","HP","XEROX","Otros"};
        String[] mouse={"Seleccionar","Compac","Genius","HP","Microsoft","Otros"};
        String[] monitor={"Seleccionar","ACER","BENQ","HP","LG","LENOVO","Otros"};
        String[] portatil={"Seleccionar","ACER","DELL","HP", "TOSHIBA","Otros"};
        String[] proyector={"Seleccionar","EPSON","INFOCUS","PANASONIC","SONY","Otros"};
        String[] servidores={"Seleccionar","COMPAQ","DELL","IBM","TOSHIBA","Otros"};
        String[] teclado={"Seleccionar","DELL","DLINK","Genius","Microsoft","Otros"};
        try{
        switch(Tipo){
            case "Cámara de vigilancia":
                return camaras;
            case "CPU":
                return cpu;
            case "Computadora de escritorio":
                return escritorio;
            case "Detector de Huellas":
                return detectorhuellas;
            case "Impresora":
                return impresora;
            case "Mouse":
                return mouse;
            case "Pantalla o Monitor":
                return monitor;
            case "Portátil":
                return portatil;
            case "Proyector":
                return proyector;
            case "Servidores":
                return servidores;
            case "Teclado":
                return teclado;
            case "Otros":
                return marcas;
            case "Seleccionar":
                return seleccionar;
         }
        }catch(NullPointerException e){
            System.err.println(e);
        }
        return marcas;
    }
    
    public static String[] LeerMemorias(){
        String[] memorias = {"Seleccionar","8GB","4GB","2GB","1GB"};
        return memorias;
    }
     public static String[] LeerProcesadores(){
        String[] procesadores = {"Seleccionar","Core i7","Core i5","Core i3", "AMD", "Dual Core","Intel","Quad Core"};
        return procesadores;
    }
      public static String[] LeerDiscos(){
        String[] discosduro = {"Seleccionar","160GB","250GB","320GB","500GB","1TB","1.5TB","2TB","2.5TB","3TB"};
        return discosduro;
    }
      public static ArrayList LeerTipo() throws SQLException{
          ArrayList<String> lista_tipos=new ArrayList<String>();
          lista_tipos.add("Seleccionar");
          ResultSet resultadodetipo;
          String sql = "select * from tmaetaccon";
          try{
          resultadodetipo = Conexion.ejecutarSQLSelect(sql);
          while (resultadodetipo.next()) {
              lista_tipos.add(resultadodetipo.getString("nombre_tipoactivo"));
          
           }
          }catch(NullPointerException e){
              System.err.println(e);
      }
        return lista_tipos;
      }
    
      public static ArrayList LeerTipoAreas() throws SQLException {
          ArrayList<String> lista_tipoarea=new ArrayList<String>();
          ResultSet resultadodetipo;
          String sql = "select tipo_area from tmaearecon";
          try{
          resultadodetipo = Conexion.ejecutarSQLSelect(sql);
          while (resultadodetipo.next()) {
              lista_tipoarea.add(resultadodetipo.getString("tipo_area"));
          
           }
          }catch(NullPointerException e){
              System.err.println(e);
      }
        return lista_tipoarea;
      }
      public static ArrayList LeerDescripcionTipoAreas() throws SQLException {
           ArrayList<String> lista_descripcion_tipoarea=new ArrayList<String>();
          ResultSet resultadodetipo;
          String sql = "select descripcion_area from tmaearecon";
          try{
          resultadodetipo = Conexion.ejecutarSQLSelect(sql);
          while (resultadodetipo.next()) {
              lista_descripcion_tipoarea.add(resultadodetipo.getString("descripcion_area"));
          
           }
          }catch(NullPointerException e){
              System.err.println(e);
      }
        return lista_descripcion_tipoarea;
      }
      
        public static ArrayList LeerPersona(String var_tipoArea) throws SQLException {
          lista_idArea.clear();
          ArrayList<String> lista_persona=new ArrayList<String>();
          lista_persona.clear();
          ResultSet resultado;
          String sql = "select (p.nombre_persona || ' ' || p.apellido_persona) AS NOMBRE_COMPLETO,  a.id_area AS id_area FROM TMAEPERCON AS p \n" +
"							INNER JOIN TMOVREHCON rh ON p.id_persona=rh.idpersona_rrhh\n" +
"							INNER JOIN TMAEARECON a ON rh.id_rrhh=a.idresponsable_area WHERE a.tipo_area='"+var_tipoArea+"';";
          try{
          resultado = Conexion.ejecutarSQLSelect(sql);
          while (resultado.next()) {
              lista_persona.add(resultado.getString("nombre_completo"));
              lista_idArea.add(resultado.getInt("id_area"));
           }
          }catch(NullPointerException e){
              System.err.println(e);
        }
        
        return lista_persona;
      }
}
