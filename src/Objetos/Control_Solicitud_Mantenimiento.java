/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objetos;

import static CapaInterfaces.FRM_ControlSolicitudesMantenimiento.btn_eliminar;
import static CapaInterfaces.FRM_ControlSolicitudesMantenimiento.btn_enviarMantenimiento;
import static CapaInterfaces.FRM_ControlSolicitudesMantenimiento.btn_modificarEncargadooMantenimiento;
import static CapaInterfaces.FRM_ControlSolicitudesMantenimiento.btn_modificarSolicitud;
import static CapaInterfaces.FRM_ControlSolicitudesMantenimiento.btn_registrarMantenimiento;
import static CapaInterfaces.FRM_ControlSolicitudesMantenimiento.combo_idResponsable_area;
import static CapaInterfaces.FRM_ControlSolicitudesMantenimiento.combo_tipo_area;
import static CapaInterfaces.FRM_ControlSolicitudesMantenimiento.date_fecha_docSolicitud;
import static CapaInterfaces.FRM_ControlSolicitudesMantenimiento.rbtn_condigo_institucional;
import static CapaInterfaces.FRM_ControlSolicitudesMantenimiento.rbtn_estado_docSolicitud;
import static CapaInterfaces.FRM_ControlSolicitudesMantenimiento.rbtn_fecha_docSolicitud;
import static CapaInterfaces.FRM_ControlSolicitudesMantenimiento.rbtn_solicitud_enProceso;
import static CapaInterfaces.FRM_ControlSolicitudesMantenimiento.rbtn_solicitud_pendiente;
import static CapaInterfaces.FRM_ControlSolicitudesMantenimiento.rbtn_solicitud_realizado;
import static CapaInterfaces.FRM_ControlSolicitudesMantenimiento.rbtn_tipo_area;
import static CapaInterfaces.FRM_ControlSolicitudesMantenimiento.tabla_solicitudes_mantenimiento;
import static CapaInterfaces.FRM_ControlSolicitudesMantenimiento.txt_codigoInternoInsticucional_activo;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import javax.swing.ButtonGroup;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Riden
 */


//Código de la interfaz Control Solicitud de Mantenimiento
public class Control_Solicitud_Mantenimiento {
    Capa_ConexionBD.Conexion conexion = new Capa_ConexionBD.Conexion();
    DefaultTableModel model;
    static Capa_Mensajes.Mensajes mensajes = new Capa_Mensajes.Mensajes();
    SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");    
    public int estado_ColSolMant;
    
  //Permite realizar la consulta según el campo seleccionado
    public void activarCampos(){        
        txt_codigoInternoInsticucional_activo.setEnabled(false);
        date_fecha_docSolicitud.setEnabled(false);
        combo_tipo_area.setEnabled(false);
        combo_idResponsable_area.setEnabled(false);        
        rbtn_solicitud_enProceso.setEnabled(false);
        rbtn_solicitud_pendiente.setEnabled(false); 
        rbtn_solicitud_realizado.setEnabled(false);
        if(rbtn_condigo_institucional.isSelected()){
            txt_codigoInternoInsticucional_activo.setEnabled(true);            
        }
        if(rbtn_fecha_docSolicitud.isSelected()){
            date_fecha_docSolicitud.setEnabled(true);                 
        }
        if(rbtn_tipo_area.isSelected()){            
            combo_tipo_area.setEnabled(true);
            combo_idResponsable_area.setEnabled(true);            
        }
        if(rbtn_estado_docSolicitud.isSelected()){
            rbtn_solicitud_realizado.setEnabled(true);
            rbtn_solicitud_enProceso.setEnabled(true);
            rbtn_solicitud_pendiente.setEnabled(true);            
        }
    }
    
    
    //
    public void combo_Descripcion(){
        combo_idResponsable_area.removeAll();
        conexion.crearConexion();        
        String sql = "select descripcion_area  from tmaearecon where tipo_area='"+combo_tipo_area.getSelectedItem().toString()+"'";
        ResultSet rs = conexion.ejecutarSQLSelect(sql);
        try {
            while (rs.next()) {
                combo_idResponsable_area.addItem(rs.getString(1));               
            }
        } catch (Exception ex) {
            System.out.println("error");
        }
    }
    
    
    //Al realizar otra búsqueda se eliminan los campos buscados anteriormente
    public void borrar_camposBusqueda(){
        txt_codigoInternoInsticucional_activo.setText("");
        date_fecha_docSolicitud.setDate(null);
        rbtn_solicitud_pendiente.setSelected(true);        
        tabla_solicitudes_mantenimiento.removeAll();
    }
    
    
    //Consulta la solicitud de mantenimiento según el criterio de búsqueda
    public void buscarControlSolicitudMantenimiento(String valorConsultar,int numeroComprar){
        String sql = null;          
        if(numeroComprar == 1){
            sql = "select  s.id_docsolicitud,s.codigooficio_docsolicitud,s.fecha_docsolicitud \n"
                    + ",p.cedula_persona,p.nombre_persona,p.apellido_persona,s.motivo_docsolicitud \n"
                    + ",s.estado_docsolicitud from tmovdoscon s,tmovrehcon rh,tmaepercon p \n"
                    + "where  rh.idpersona_rrhh = p.id_persona and s.idsolicitante_docsolicitud=rh.id_rrhh \n"                    
                    + "and s.codigooficio_docsolicitud = '"+valorConsultar+"' ";                    
        }else if(numeroComprar == 2){
            sql = "select  s.id_docsolicitud,s.codigooficio_docsolicitud,s.fecha_docsolicitud \n"
                    + ",p.cedula_persona,p.nombre_persona,p.apellido_persona,s.motivo_docsolicitud \n"
                    + ",s.estado_docsolicitud from tmovdoscon s,tmovrehcon rh,tmaepercon p \n"
                    + "where  rh.idpersona_rrhh = p.id_persona and s.idsolicitante_docsolicitud=rh.id_rrhh\n"                    
                    + "and s.fecha_docsolicitud = '"+valorConsultar+"' ";                                    
        }else if(numeroComprar == 3){
            int valorConsultarInt = Integer.valueOf(valorConsultar);            
            sql = "select  s.id_docsolicitud,s.codigooficio_docsolicitud,s.fecha_docsolicitud \n"
                    + ",p.cedula_persona,p.nombre_persona,p.apellido_persona,s.motivo_docsolicitud \n"
                    + ",s.estado_docsolicitud from tmovdoscon s,tmovrehcon rh,tmaepercon p\n"
                    + "where  rh.idpersona_rrhh = p.id_persona and s.idsolicitante_docsolicitud=rh.id_rrhh \n"
                    + "and s.estado_docsolicitud = "+valorConsultarInt+" ";
        }else if(numeroComprar == 4){
            sql = "select  s.id_docsolicitud,s.codigooficio_docsolicitud,s.fecha_docsolicitud \n"
                    + ",p.cedula_persona,p.nombre_persona,p.apellido_persona,s.motivo_docsolicitud \n"
                    + ",s.estado_docsolicitud from tmovdoscon s,tmovrehcon rh,tmaepercon p \n"
                    + "where  rh.idpersona_rrhh = p.id_persona and s.idsolicitante_docsolicitud=rh.id_rrhh \n"
                    + "and p.nombre_persona = '"+valorConsultar+"'";
        }        
        conexion.crearConexion();        
        String estado;
        String[] datosTabla = new String[7];
        String[] nombresTabla = {"Id", "Código Interno", "Fecha Solicitud", "Cédula Solicitante", "Solicitante", "Motivos","Estado"};
        model = new DefaultTableModel(null, nombresTabla);        
        ResultSet rs = conexion.ejecutarSQLSelect(sql);
        ResultSet rs1 = conexion.ejecutarSQLSelect(sql);
        try {
            if (!rs1.next()) {
                mensajes.msg_advertencia(null, "No existen datos de solicitud de mantenimiento");
                
                
            }
            while (rs.next()) {
                datosTabla[0] = rs.getString(1);
                datosTabla[1] = rs.getString(2);
                datosTabla[2] = rs.getString(3);
                datosTabla[3] = rs.getString(4);
                datosTabla[4] = rs.getString(5) + " " + rs.getString(6);
                datosTabla[5] = rs.getString(7);
                if("0".equals(rs.getString(8))){
                    estado="Pendiente";
                }else if("1".equals(rs.getString(8))){
                    estado="En proceso";
                }else {
                    estado="Realizado";
                }
                datosTabla[6] = estado;
                model.addRow(datosTabla);
                tabla_solicitudes_mantenimiento.setModel(model);
            }
        } catch (Exception ex) {
            mensajes.msg_advertencia(null, "La conexión no fue exitosa");
        }        
    }
    
    //Guarda datos que se utilizaran en registro de mantenimiento 
    public String[] consulta_gereral(int idDocSolicitud){        
        String [] datosASerEnviados = new String [14];
        String sql = "select s.id_docsolicitud,s.codigooficio_docsolicitud,s.fecha_docsolicitud \n"
                + ",p.cedula_persona,p.nombre_persona,p.apellido_persona,ma.fechainicio_mantactivo,\n"
                + "s.tipomantps_docsolicitud,s.tipoge_mantactivo,s.estado_docsolicitud,\n"
                + "(select (p.nombre_persona || ' ' || p.apellido_persona) as nombre from tmaepercon as p inner join tmovrehcon as rh on rh.idpersona_rrhh=p.id_persona inner join tmovdoscon as s on s.idsolicitante_docsolicitud=rh.id_rrhh\n"
                + "        where s.id_docsolicitud=" + idDocSolicitud +")"
                + ", ma.fechafin_mantactivo ,actividades_mantactivo,observaciones_mantactivo\n"
                + "from tmovdoscon s,tmovrehcon rh,tmaepercon p ,TMOVMANCON ma\n"
                + "where rh.idpersona_rrhh = p.id_persona and s.idsolicitante_docsolicitud=rh.id_rrhh \n"
                + "and ma.iddocsolicitud_mantactivo = s.id_docsolicitud\n"
                + "and s.id_docsolicitud = " + idDocSolicitud + "";   
        ResultSet rs = conexion.ejecutarSQLSelect(sql);               
        try {
            while (rs.next()) {                                
                datosASerEnviados[0] = rs.getString(1);
                datosASerEnviados[1] = rs.getString(2);
                datosASerEnviados[2] = rs.getString(3);
                datosASerEnviados[3] = rs.getString(4);
                datosASerEnviados[4] = rs.getString(5) + " " + rs.getString(6);                                               
                System.out.print(rs.getString(5) + " " + rs.getString(6));
                datosASerEnviados[5] = rs.getString(7);
                datosASerEnviados[6] = rs.getString(8);
                datosASerEnviados[7] = rs.getString(9);
                datosASerEnviados[8] = rs.getString(10);
                if("1".equals(rs.getString(10))){
                    estado_ColSolMant=1;
                }else if("2".equals(rs.getString(10))){
                    estado_ColSolMant=2;
                }                
                datosASerEnviados[9] = rs.getString(11);
                datosASerEnviados[10] = rs.getString(12);
                datosASerEnviados[11] = rs.getString(13);
                datosASerEnviados[12] = rs.getString(14);
            }
        } catch (Exception ex) {
            mensajes.msg_advertencia(null, "La conexión no fue exitosa");
        }  
        return datosASerEnviados;
    }
    
    //Se crean los combo box para Laboratorio, Administrativo y Docentes
    public void combo_Tipo_Area() {
        combo_tipo_area.removeAllItems();
        combo_tipo_area.addItem("Laboratorio");
        combo_tipo_area.addItem("Administrativo");
        combo_tipo_area.addItem("Docente");
    }
    
    //Nuevo
    public void nuevo(){
        txt_codigoInternoInsticucional_activo.setText("");
        limpiarTabla(tabla_solicitudes_mantenimiento);
        date_fecha_docSolicitud.setDate(null);
        combo_idResponsable_area.setSelectedItem("Seleccionar");
    }
    
    //elimina las solicitudes echas y ademas cambia el estado del activo para saber que esta utilizable
    public void eliminarSolicitud_ContSolMant(int row_tablaConSolMant){        
         conexion.crearConexion();
         int idDocSolicitud_ContSolMant= Integer.parseInt(tabla_solicitudes_mantenimiento.getValueAt(row_tablaConSolMant, 0).toString());         
         try{
             String sql0 = "update TMOVACTCON set estado_activo=0 \n"
                     + "where id_activo=(select a.id_activo \n"
                     + "from tmovdoscon d,TMOVDTSCON dt, TMOVACTCON a \n"
                     + "where d.id_docsolicitud = dt.iddocsolicitud_detallesolicitudmant and \n"
                     + "a.id_activo = dt.idactivo_detallesolicitudmant and d.id_docsolicitud="+idDocSolicitud_ContSolMant+")",
                     sql1 = "delete from TMOVDTSCON where iddocsolicitud_detallesolicitudmant="+idDocSolicitud_ContSolMant+"",
                     sql2 = "delete from tmovdoscon where id_docsolicitud="+idDocSolicitud_ContSolMant+"";
             ResultSet rs = conexion.ejecutarSQLSelect(sql0), rs1 = conexion.ejecutarSQLSelect(sql1), rs2 = conexion.ejecutarSQLSelect(sql2);
         }catch(Exception x){
             System.out.println("eliminar no existe");
         }
         
    }
    
    
   //Limpia la tabla de la búsqueda de Control de Solicitud de Mantenimiento
    public void limpiarTabla(JTable tabla){        
        try {
            DefaultTableModel modelo=(DefaultTableModel) tabla.getModel();
            int filas=tabla.getRowCount();  //Obtener el numero total de columnas del modelo de la tabla
            for (int i = 0;filas>i; i++) {  //recorrer una a una de las filas ingresadas dentro de la tabla hasta que no queden datos
                modelo.removeRow(0);        //eliminar la fila seleccionada o en la cual esta la posición
            }
        } catch (Exception e) {
            System.out.println("Error al limpiar la tabla.");
        }
    }
    
    //Se valida que check box está activado para saber en que parámetro se debe consultar
    public void buscarDatos_ContSolMant(){
        int valorTipoConsulta=0;          
        if(rbtn_condigo_institucional.isSelected()){
            valorTipoConsulta=1;
            buscarControlSolicitudMantenimiento(txt_codigoInternoInsticucional_activo.getText(),valorTipoConsulta);            
        }else if(rbtn_fecha_docSolicitud.isSelected()){            
            valorTipoConsulta=2;
            buscarControlSolicitudMantenimiento(formatoFecha.format(date_fecha_docSolicitud.getDate()),valorTipoConsulta);            
        }else if(rbtn_estado_docSolicitud.isSelected()){            
            valorTipoConsulta=3;
            String estadoConsulta="0";
            if(rbtn_solicitud_pendiente.isSelected()){
                estadoConsulta="0";
            }else if(rbtn_solicitud_enProceso.isSelected()){
                estadoConsulta="1";
            }else if(rbtn_solicitud_realizado.isSelected()){
                estadoConsulta="2";
            }            
            buscarControlSolicitudMantenimiento(estadoConsulta,valorTipoConsulta);
        }else if(rbtn_tipo_area.isSelected()){
            valorTipoConsulta=4;
            buscarControlSolicitudMantenimiento(combo_idResponsable_area.getSelectedItem().toString(),valorTipoConsulta);            
        }else{
            mensajes.msg_advertencia(null, "No existen datos");
        }
    }
    
    //Recibe y envia datos de consulta general al registro de mantenimiento
    public void mandarDatos_RegMant() {
      int RowDatosControlSolicitudMantenimiento = tabla_solicitudes_mantenimiento.getSelectedRow(),idDocSolicitud;        
      idDocSolicitud = Integer.parseInt(tabla_solicitudes_mantenimiento.getValueAt(RowDatosControlSolicitudMantenimiento, 0).toString());        
      CapaInterfaces.FRM_RegistroMantenimiento rm = new CapaInterfaces.FRM_RegistroMantenimiento(consulta_gereral(idDocSolicitud),estado_ColSolMant);        
    }
    
    
    //Desactiva los botones de icrud cuando hacemos una consulta, solo se activan cuando damos doble click
    public void objetos_desactivados(){
        btn_modificarSolicitud.setEnabled(false);
        btn_enviarMantenimiento.setEnabled(false);
        btn_eliminar.setEnabled(false);
        btn_modificarEncargadooMantenimiento.setEnabled(false);
        btn_registrarMantenimiento.setEnabled(false);
    }
    
    //Agrupa los radio button
    public void radio(){
        ButtonGroup btngGrupoConsulta = new ButtonGroup();
        ButtonGroup btngGrupoEstado = new ButtonGroup();
        btngGrupoConsulta.add(rbtn_condigo_institucional);
        btngGrupoConsulta.add(rbtn_fecha_docSolicitud);
        btngGrupoConsulta.add(rbtn_estado_docSolicitud);
        btngGrupoConsulta.add(rbtn_tipo_area);        
        btngGrupoEstado.add(rbtn_solicitud_enProceso);
        btngGrupoEstado.add(rbtn_solicitud_pendiente);
        btngGrupoEstado.add(rbtn_solicitud_realizado);        
    }
    
    public void combo_RecursoHumano() {
        combo_idResponsable_area.removeAllItems();
        conexion.crearConexion();
        String sql = "select p.nombre_persona \n"
                + "from tmaepercon p, tmovrehcon rh, tmaearecon a\n"
                + "where p.id_persona = rh.idpersona_rrhh and "
                + "rh.id_rrhh = a.idresponsable_area and a.tipo_area ="
                + " '" + combo_tipo_area.getSelectedItem().toString() + "'";
        ResultSet rs = conexion.ejecutarSQLSelect(sql);
        try {
            while (rs.next()) {
                combo_idResponsable_area.addItem(rs.getString(1));
            }
        } catch (Exception ex) {
            mensajes.msg_advertencia(null, "No existen datos de ese tipo de area");
        }
    }
   
    //Al dar click en una radio button de estado de mantenimiento se realizara la consulta que se requiere
    public void verificar_estadoMantenimiento(){
        String [] datos_enviados;
        int row_estadoDocSol = tabla_solicitudes_mantenimiento.getSelectedRow(), estado_docSolicitud=4,idDocSol_regMant;        
        idDocSol_regMant = Integer.parseInt(tabla_solicitudes_mantenimiento.getValueAt(row_estadoDocSol, 0).toString());
        if(null != tabla_solicitudes_mantenimiento.getValueAt(row_estadoDocSol, 6).toString()){
            switch(tabla_solicitudes_mantenimiento.getValueAt(row_estadoDocSol, 6).toString()) {
            case "Pendiente":
                estado_docSolicitud=0;
                break;
            case "En proceso":
                estado_docSolicitud=1;
                break;                
            default:
                estado_docSolicitud=2;
                break;
        }
        }
        datos_enviados = consulta_gereral(idDocSol_regMant);          
        switch (estado_docSolicitud) {
            case 0:
                objetos_desactivados();
                btn_modificarSolicitud.setEnabled(true);
                btn_enviarMantenimiento.setEnabled(true);
                btn_eliminar.setEnabled(true);
                estado_ColSolMant=0;                
                break;
            case 1:
                objetos_desactivados();
                btn_modificarEncargadooMantenimiento.setEnabled(true);
                btn_registrarMantenimiento.setEnabled(true);                
                estado_ColSolMant=1;
                break;
            case 2:
                objetos_desactivados();
                btn_registrarMantenimiento.setEnabled(true);
                estado_ColSolMant=2;
                break;
            default:
                break;
        }                
    }
    
    //Desactiva botones que realizan las acciones de eliminar, enviar a mantenimiento, modificar y registrar
    public void desactivarBtn_contSolMant(){
        btn_eliminar.setEnabled(false);
        btn_enviarMantenimiento.setEnabled(false);
        btn_modificarEncargadooMantenimiento.setEnabled(false);
        btn_modificarSolicitud.setEnabled(false);
        btn_registrarMantenimiento.setEnabled(false);        
    }
    
    
    //Envia datos al registro Solicitud de Mantenimiento
    public void mandarDatos_RegistroSolicitudMantenimiento(int fila_oficio){
        int idCodOficio_ConSolMant = Integer.parseInt(tabla_solicitudes_mantenimiento.getValueAt(fila_oficio, 0).toString()),tipoTran_conSolMant=1;
        String [] datosEnviar_regSolMant = new String [7];
        int id_solicitud_modificar=0;
        String sql = "select p.cedula_persona,p.nombre_persona,p.apellido_persona,d.codigooficio_docsolicitud,d.fecha_docsolicitud,\n"
                + "d.motivo_docsolicitud,d.id_docsolicitud\n"
                + "from tmaepercon p, tmovrehcon rh,tmovdoscon d\n"
                + "where p.id_persona = rh.idpersona_rrhh and d.idsolicitante_docsolicitud = rh.id_rrhh and d.id_docsolicitud="+idCodOficio_ConSolMant+"";
        ResultSet rs = conexion.ejecutarSQLSelect(sql);               
        try {
            while (rs.next()) {                                
                datosEnviar_regSolMant[0] = rs.getString(1);
                datosEnviar_regSolMant[1] = rs.getString(2)+ " " +rs.getString(3);
                datosEnviar_regSolMant[2] = rs.getString(4);
                datosEnviar_regSolMant[3] = rs.getString(5);
                datosEnviar_regSolMant[4] = rs.getString(6);
                datosEnviar_regSolMant[5] = rs.getString(7); 
                id_solicitud_modificar=Integer.parseInt(datosEnviar_regSolMant[5]);
            }
        } catch (Exception ex) {
            System.out.println("mandarDatos R_S_M");
        }                
        CapaInterfaces.FRM_RegistroSolicitudMantenimiento rsm = new CapaInterfaces.FRM_RegistroSolicitudMantenimiento(datosEnviar_regSolMant,tipoTran_conSolMant, id_solicitud_modificar);
    }
}
