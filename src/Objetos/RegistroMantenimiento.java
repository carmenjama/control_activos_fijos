/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objetos;


import static CapaInterfaces.FRM_ControlSolicitudesMantenimiento.tabla_solicitudes_mantenimiento;
import static CapaInterfaces.FRM_RegistroMantenimiento.btn_actualizar;
import static CapaInterfaces.FRM_RegistroMantenimiento.btn_guardar;
import static CapaInterfaces.FRM_RegistroMantenimiento.combo_discoduro_activo;
import static CapaInterfaces.FRM_RegistroMantenimiento.combo_memoria_activo;
import static CapaInterfaces.FRM_RegistroMantenimiento.combo_procesador_activo;
import static CapaInterfaces.FRM_RegistroMantenimiento.date_fechaFin_mantActivo;
import static CapaInterfaces.FRM_RegistroMantenimiento.date_fechaInicio_mantActivo;
import static CapaInterfaces.FRM_RegistroMantenimiento.date_fecha_docSolicitud;
import static CapaInterfaces.FRM_RegistroMantenimiento.datosContSolMant;
import static CapaInterfaces.FRM_RegistroMantenimiento.jTextArea1;
import static CapaInterfaces.FRM_RegistroMantenimiento.rbtn_mantenimiento_correctivo;
import static CapaInterfaces.FRM_RegistroMantenimiento.rbtn_mantenimiento_hardware;
import static CapaInterfaces.FRM_RegistroMantenimiento.rbtn_mantenimiento_preventivo;
import static CapaInterfaces.FRM_RegistroMantenimiento.rbtn_mantenimiento_software;
import static CapaInterfaces.FRM_RegistroMantenimiento.tabla_especificaciones_activos;
import static CapaInterfaces.FRM_RegistroMantenimiento.txt_actividades_mantActivo;
import static CapaInterfaces.FRM_RegistroMantenimiento.txt_codigoOficio;
import static CapaInterfaces.FRM_RegistroMantenimiento.txt_idSolicitante_docSolicitud;
import static CapaInterfaces.FRM_RegistroMantenimiento.txt_tipoGE_mantActivo;
import static CapaInterfaces.FRM_RegistroMantenimiento.txt_tipoMantPS_docSolicitud;
import static CapaInterfaces.FRM_RegistroMantenimiento.txt_nombre_encargado;
import java.awt.Component;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Riden
 */

//Código de la interfaz registro de mantenimiento
public class RegistroMantenimiento {    
     Capa_ConexionBD.Conexion conexion = new Capa_ConexionBD.Conexion();
    DefaultTableModel model;
    public static int estado = 0;
    public static int estadoActivo;
    SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd"); 
    static Capa_Mensajes.Mensajes mensajes = new Capa_Mensajes.Mensajes();
    int contador_cambioActivo=0;
    String [][] activosCambiados = new String[2][30];        
    
    
   //Toma los  campos que le mando Control Solicitud de Mantenimiento
    public void llenarCampos(String [] datosContSolMant,int estado) {        
        if (estado != 0) {
            String datoTranf_fechaDocSolicitud = datosContSolMant[2], datoTranf_fechaIniActivo = datosContSolMant[5],fechaFin_unida;
            Date fecha_docSolicitud = null, fechaInicio_mantActivo = null,fechaFin_final=null,fechaFin = null;
            Date fechaFin_mantActivo = new Date();            
            try {
                fecha_docSolicitud = formatoFecha.parse(datoTranf_fechaDocSolicitud);
                fechaInicio_mantActivo = formatoFecha.parse(datoTranf_fechaIniActivo);
                fechaFin_final = formatoFecha.parse(formatoFecha.format(fechaFin_mantActivo));
                fechaFin = formatoFecha.parse(datosContSolMant[10]);
            } catch (ParseException ex) {
                System.out.println("llenar campos");
            }
            txt_codigoOficio.setText(datosContSolMant[1]);
            date_fecha_docSolicitud.setDate(fecha_docSolicitud);
            txt_idSolicitante_docSolicitud.setText(datosContSolMant[4]);
            date_fechaInicio_mantActivo.setDate(fechaInicio_mantActivo);
            if("0".equals(datosContSolMant[6])){
                txt_tipoMantPS_docSolicitud.setText("Programado");
                rbtn_mantenimiento_preventivo.setSelected(true);
            }else if("1".equals(datosContSolMant[6])){
                txt_tipoMantPS_docSolicitud.setText("Solicitud");
                rbtn_mantenimiento_correctivo.setSelected(true);
            }
            if("0".equals(datosContSolMant[7])){
                txt_tipoGE_mantActivo.setText("General");
                rbtn_mantenimiento_hardware.setSelected(true);
            }else if("1".equals(datosContSolMant[7])){
                txt_tipoGE_mantActivo.setText("Eq. Individual");
                rbtn_mantenimiento_software.setSelected(true);
            }            
            txt_nombre_encargado.setText(datosContSolMant[9]);                               
            if (estado == 1) {
                btn_actualizar.setEnabled(false);
                date_fechaFin_mantActivo.setDate(fechaFin_final);
            } else if (estado == 2) {
                btn_guardar.setEnabled(false);
                date_fechaFin_mantActivo.setDate(fechaFin);
                txt_actividades_mantActivo.setText(datosContSolMant[11]);                
                jTextArea1.setText(datosContSolMant[12]);
            }            
            llenarActivos_regMant(Integer.parseInt(datosContSolMant[0]));
        }
    }
    
    
    //Llena los Combos box con los datos de procesador, memoria y disco duro (solo para CPU, portatil y computadoras de escritorio)
    public void llenar_Combos() {
        String[] procesador = {"Core i7","Core i5","Core i3"},
                memoria = {"8GB","4GB","2GB","1GB"},
                discoDuro = {"3 TB","2.5 TB","2 TB","1.5 TB","1 TB","500 GB","320 GB","250 GB","160 GB"};        
        int contador=0;
        combo_procesador_activo.removeAllItems();
        while(contador!=procesador.length){
            combo_procesador_activo.addItem(procesador[contador]);
            contador++;
        }
        contador=0;
        combo_memoria_activo.removeAllItems();
        while(contador!=memoria.length){
            combo_memoria_activo.addItem(memoria[contador]);
            contador++;
        }
        contador=0;
        combo_discoduro_activo.removeAllItems();
        while(contador!=discoDuro.length){
            combo_discoduro_activo.addItem(discoDuro[contador]);
            contador++;
        }
        
    }       
    
    //Llena los acctivos que van a estar en la tabla registro de mantenimiento
    public void llenarActivos_regMant(int idDoc_SolMant){
        String [] datos_tablaActivos = new String [9];
        String sql = "select act.id_activo,tact.nombre_tipoactivo,act.marca_activo,act.precesador_acrtivo, \n"
                + "act.memoria_activo,act.discoduro_activo,act.modelo_activo,act.serie_activo, d.codigooficio_docsolicitud \n"
                + "from tmaepercon p, tmovrehcon rh,tmovdoscon d,TMOVDTSCON dt,TMOVACTCON act,TMAETACCON tact \n"
                + "where p.id_persona = rh.idpersona_rrhh and d.idsolicitante_docsolicitud = rh.id_rrhh \n"
                + "and d.id_docsolicitud=dt.iddocsolicitud_detallesolicitudmant and act.id_activo = dt.idactivo_detallesolicitudmant \n"
                + "and tact.id_tipoactivo = act.idtipo_activo and d.id_docsolicitud="+idDoc_SolMant+"";        
        conexion.crearConexion();
        ResultSet rs = conexion.ejecutarSQLSelect(sql);
        String[] nombresTabla = {"Id", "Tipo", "Marca", "Procesador", "Memoria", "Disco Duro","Modelo","Serie","Cod. Institucion"};
        model = new DefaultTableModel(null, nombresTabla);        
        try {
            while (rs.next()) {                 
                datos_tablaActivos[0] = rs.getString(1);
                datos_tablaActivos[1] = rs.getString(2);
                datos_tablaActivos[2] = rs.getString(3);
                datos_tablaActivos[3] = rs.getString(4);
                datos_tablaActivos[4] = rs.getString(5);                
                datos_tablaActivos[5] = rs.getString(6);
                datos_tablaActivos[6] = rs.getString(7);
                datos_tablaActivos[7] = rs.getString(8);
                datos_tablaActivos[8] = rs.getString(9);
                model.addRow(datos_tablaActivos);
                tabla_especificaciones_activos.setModel(model);
            }
        } catch (Exception ex) {
            System.out.println("llenar activos");
        }      
    }
    
    
    //Agrupa los radio button
    public void radio(){
        ButtonGroup btng1_especificaciones = new ButtonGroup();        
        ButtonGroup btng2_especificaciones = new ButtonGroup();        
        btng1_especificaciones.add(rbtn_mantenimiento_correctivo);
        btng2_especificaciones.add(rbtn_mantenimiento_hardware);
        rbtn_mantenimiento_correctivo.setEnabled(true);
        btng1_especificaciones.add(rbtn_mantenimiento_preventivo);
        btng2_especificaciones.add(rbtn_mantenimiento_software);
        rbtn_mantenimiento_hardware.setEnabled(true);
    }
    
    
    // Desactiva los campos de disco duro, memoria y procesador cuando no son CPU, Computadoras de escritorio y portátiles
    public void desactivar_campos(){
        combo_discoduro_activo.setEnabled(false);
        combo_memoria_activo.setEnabled(false);
        combo_procesador_activo.setEnabled(false);
    }
    
    
    // Finaliza la solicitud que esté en proceso
    public void guardarMantenimiento_RegMant(int idDocSolicitud_regMant){
        conexion.crearConexion();        
        int tipopc,tipohs; 
        if(rbtn_mantenimiento_preventivo.isSelected()){
            tipopc=0;
        }else{
            tipopc=1;
        }
        if(rbtn_mantenimiento_hardware.isSelected()){
            tipohs=0;
        }else{
            tipohs=1;
        }
         try{ 
             String sql = "update TMOVMANCON set fechafin_mantactivo = '"+date_fechaFin_mantActivo.getDate()+"'\n"
                     + ",observaciones_mantactivo = '"+txt_actividades_mantActivo.getText()+"' \n"
                     + ",actividades_mantactivo = '"+jTextArea1.getText() +"' \n"
                     + ",tipopc_mantactivo="+tipopc+",tipohs_mantactivo="+tipohs+"\n"
                     + "where id_mantactivo = (\n"
                     + "select ma.id_mantactivo\n"
                     + "from tmovdoscon d,TMOVMANCON ma\n"
                     + "where ma.iddocsolicitud_mantactivo = d.id_docsolicitud and d.id_docsolicitud="+idDocSolicitud_regMant+")",
                     sql1 =sql1 ="update tmovdoscon set estado_docsolicitud = 2\n"
                     + "where id_docsolicitud = "+idDocSolicitud_regMant+"";
             ResultSet rs = conexion.ejecutarSQLSelect(sql),rs1 = conexion.ejecutarSQLSelect(sql1);
         }catch(Exception x){
             System.out.println("eliminar no existe");
         }
    }
    
    
    //Actualiza una solicitud ya realizada
    public void actualizar_regMant(int idDocSolicitud_regMant){
        conexion.crearConexion();        
        int tipopc,tipohs;        
        if(rbtn_mantenimiento_preventivo.isSelected()){
            tipopc=0;            
        }else{
            tipopc=1;
        }        
        if(rbtn_mantenimiento_hardware.isSelected()){
            tipohs=0;
        }else{
            tipohs=1;
        }        
         try{
             
             String sql = "update TMOVMANCON set \n"
                     + "observaciones_mantactivo = '"+txt_actividades_mantActivo.getText()+"' \n"
                     + ",actividades_mantactivo = '"+jTextArea1.getText() +"' \n"
                     + ",tipopc_mantactivo="+tipopc+",tipohs_mantactivo="+tipohs+"\n"
                     + "where id_mantactivo = (\n"
                     + "select ma.id_mantactivo\n"
                     + "from tmovdoscon d,TMOVMANCON ma\n"
                     + "where ma.iddocsolicitud_mantactivo = d.id_docsolicitud and d.id_docsolicitud="+idDocSolicitud_regMant+")";
             ResultSet rs = conexion.ejecutarSQLSelect(sql);
         }catch(Exception x){
             System.out.println("eliminar no existe");
         }
    }
    
    //Toma los datos de los combo box y actualiza el activo seleccionado
    public void cambiarActivos_regMant(){
        conexion.crearConexion();
        contador_cambioActivo = contador_cambioActivo -1;
        System.out.println("Cuenta regresiva " + contador_cambioActivo);
        String tipoActivo_regMant = activosCambiados[0][contador_cambioActivo];        
        if ("CPU".equals(tipoActivo_regMant) || "Computadora de escritorio".equals(tipoActivo_regMant) || "Portátil".equals(tipoActivo_regMant) || "Servidores".equals(tipoActivo_regMant)) {
            int idActivo_regMant = Integer.parseInt(activosCambiados[1][contador_cambioActivo]);
            
            try {
                String sql = "update TMOVACTCON set precesador_acrtivo='" + combo_procesador_activo.getSelectedItem().toString() + "', memoria_activo = '" + combo_memoria_activo.getSelectedItem().toString() + "', \n"
                        + "discoduro_activo = '" + combo_discoduro_activo.getSelectedItem().toString() + "'\n"
                        + "where id_activo = " + idActivo_regMant + "";
                ResultSet rs = conexion.ejecutarSQLSelect(sql);
            } catch (Exception x) {
                System.out.println("eliminar no existe");
            }
        }else{
            mensajes.msg_advertencia(null, "Este activo no cuenta con esos componentes");
        }
    }
        
    //Verifica que campos "actividades" y "observaciones" no estén vacíos 
    public boolean camposVacios_regMant(){
        if("".equals(txt_actividades_mantActivo.getText())){
            mensajes.msg_advertencia(null, "Llene el campo de las 'actividades realizadas'");
            return false;
        }else{
            return true;
        }
            
    }
    public void cambiarEsteAct(){
        estadoActivo =1;
    }
    
    public void btnguardar_regMant(Component Ventana) {
        
            if (camposVacios_regMant() == true) {
                guardarMantenimiento_RegMant(Integer.parseInt(datosContSolMant[0]));
                if (estadoActivo == 1) {
                    int contador=0;
                    int contador2 = contador_cambioActivo;
                    while(contador != contador2){
                        System.out.println("aki contador " + contador + " aki contador2 " + contador2 );
                        cambiarActivos_regMant();
                        
                        contador++;
                    }                    
                    estadoActivo = 0;
                }
                llenarActivos_regMant(Integer.parseInt(datosContSolMant[0]));
            }
            mensajes.msg_confirmacion(Ventana, "Registro guardado con exito.");
    }
    public void btnactualizar_regMant(Component Ventana){
            if (camposVacios_regMant() == true) {
               actualizar_regMant(Integer.parseInt(datosContSolMant[0]));
                if (estadoActivo == 1) {
                    int contador=0;
                    int contador2 = contador_cambioActivo;
                    while(contador != contador2){
                        System.out.println("Aki contador " + contador + " Aki contador 2 " + contador2);
                        cambiarActivos_regMant();
                        contador++;
                    }
                    estadoActivo = 0;
                }
                llenarActivos_regMant(Integer.parseInt(datosContSolMant[0]));
            }
            mensajes.msg_confirmacion(Ventana, "Reistro modificado con exito.");
    }            
    
    public void nuevo(){
        txt_actividades_mantActivo.setText("");
        jTextArea1.setText("");
    }    
    public void activosCambiar(){        
        activosCambiados[0][contador_cambioActivo]= tabla_especificaciones_activos.getValueAt(tabla_especificaciones_activos.getSelectedRow(), 1).toString();
        activosCambiados[1][contador_cambioActivo]= tabla_especificaciones_activos.getValueAt(tabla_especificaciones_activos.getSelectedRow(), 0).toString();
        contador_cambioActivo++;        
    }
}
