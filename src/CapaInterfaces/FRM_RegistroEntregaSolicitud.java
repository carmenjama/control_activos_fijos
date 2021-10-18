/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaInterfaces;

import Capa_Mensajes.Mensajes;
import java.awt.Event;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

/**
 *
 * @author Karlita
 */
public class FRM_RegistroEntregaSolicitud extends javax.swing.JDialog {

    Mensajes mensajes = new Mensajes();
    /**
     * Creates new form RegistroActivos
     */
    public static String[] datos;
    public static int envia;
    public FRM_RegistroEntregaSolicitud(String[] datos) {
        this.datos = datos;
    }//Permite la coneccion entre los formilarios
    public FRM_RegistroEntregaSolicitud(int datos1){
        this.envia = datos1;
    }//Permite la coneccion entre los formilarios
    SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");//Da formato a la fecha para que se pueda guardar en la base de datos
    Capa_ConexionBD.Conexion conexion = new Capa_ConexionBD.Conexion();//Crea una conexion con la base de datos 
    Capa_Validaciones.Validaciones validar = new Capa_Validaciones.Validaciones();//Se utiliza para llamar las validaciones
    int id_rhh;
    //
    public FRM_RegistroEntregaSolicitud(java.awt.Dialog parent, boolean modal) {
        super(parent, modal);
        initComponents();
        recibe();
        setIconImage(new ImageIcon(getClass().getResource("/Icons/logo_2.png")).getImage());
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(0);
        date_fechaEntrega_docSolicitud.getDateEditor().setEnabled(false);
        validar.validaciondefecha(date_fechaEntrega_docSolicitud);
    }
    //Bloquea copia y pega
    public void bloquearTeclas(){
        InputMap map1 = txt_cedula_persona.getInputMap(txt_cedula_persona.WHEN_FOCUSED);
        map1.put(KeyStroke.getKeyStroke(KeyEvent.VK_V, Event.CTRL_MASK), "null");
    }
    
    //Limpia los campos de la interfaz de registro de entrega de activos
    public void btnNuevo() {
        txt_nombre_persona.setText("");
        txt_cedula_persona.setText("");
        date_fechaEntrega_docSolicitud.setDate(null);
    }

    
    //Llena los campos de acuerdo a los seleccionado de la interfaz Control de Entrega
    public void llenarcampos() {
        String datoTranf_fechaDocSolicitud = datos[1];
        Date fecha_docSolicitud = null;
        try {
            fecha_docSolicitud = formatoFecha.parse(datoTranf_fechaDocSolicitud);
        } catch (ParseException ex) {
            Logger.getLogger(FRM_RegistroMantenimiento.class.getName()).log(Level.SEVERE, null, ex);
        }
        date_fecha_docSolicitud.setDate(fecha_docSolicitud);
        txt_codigoOficio_docSolicitud.setText(datos[6]);

    }

    //Se llenan todos los campos de acuerdo a los seleccionado de la interfaz Control de Entrega
    public void DatosModificar() {
        String datoTranf_fechaDocSolicitud = datos[1], datoTranf_fechaIniActivo = datos[7];
        Date fecha_docSolicitud = null, fechaInicio_mantActivo = null;
        try {
            fecha_docSolicitud = formatoFecha.parse(datoTranf_fechaDocSolicitud);
            fechaInicio_mantActivo = formatoFecha.parse(datoTranf_fechaIniActivo);
        } catch (ParseException ex) {
            Logger.getLogger(FRM_RegistroMantenimiento.class.getName()).log(Level.SEVERE, null, ex);
        }
        conexion.crearConexion(); 
          String[] datosASerEnviados = new String[10];
         String sql =" select p.nombre_persona, p.apellido_persona, p.cedula_persona, rh.id_rrhh from tmaepercon as p\n" +
"			inner join tmovrehcon rh on rh.idpersona_rrhh=p.id_persona\n" +
"			inner join tmovdoscon ds on ds.identregante_docsolicitud=rh.id_rrhh\n" +
"			where codigooficio_docsolicitud='"+datos[6]+"'";
         ResultSet rs = conexion.ejecutarSQLSelect(sql);
        
        try {
            while (rs.next()){
                    datosASerEnviados[0] = rs.getString(1);
                    datosASerEnviados[1] = rs.getString(2);
                    datosASerEnviados[2] = rs.getString(3);
                    datosASerEnviados[3] = rs.getString(4);
                    txt_cedula_persona.setText(datosASerEnviados[2]);
                    txt_nombre_persona.setText(datosASerEnviados[0] + " " + datosASerEnviados[1]);       
            }
        } catch (SQLException ex) {
            Logger.getLogger(FRM_ControlEntrega.class.getName()).log(Level.SEVERE, null, ex);
        }
        date_fechaEntrega_docSolicitud.setDate(fechaInicio_mantActivo);
        date_fecha_docSolicitud.setDate(fecha_docSolicitud);
        txt_codigoOficio_docSolicitud.setText(datos[6]);

    }

    
    //Busca un número de cédula ya existente que pertenece a la tabla de recursos humanos de tipo mantenimiento
    public int busquedacedula(String cedulaingresada) {
        int devolucion = 0;
        conexion.crearConexion();
        String[] var1 = new String[10];
        String sql = "select p.cedula_persona, p.nombre_persona, p.apellido_persona, rh.idpersona_rrhh, rh.id_rrhh, p.id_persona from tmaepercon as p inner join tmovrehcon as rh on rh.idpersona_rrhh=p.id_persona\n" +
                     "where p.cedula_persona='"+cedulaingresada+"' and rh.tipo_rrhh = 'Mantenimiento';";
        ResultSet rs = conexion.ejecutarSQLSelect(sql);
        try {
            while (rs.next()) {
                devolucion = 1;
                var1[0] = rs.getString(1);
                var1[1] = rs.getString(2);
                var1[2] = rs.getString(3);
                var1[3] = rs.getString(4);
                var1[4] = rs.getString(5);
                var1[5] = rs.getString(6);
                txt_nombre_persona.setText(var1[1] + " " + var1[2]);
                id_rhh = rs.getInt(5);  
                //System.out.println(var1[4]);
            }
        } catch (Exception ex) {
            System.out.println("error");
            
        }
        return devolucion;
    }
  
    //Guarda los datos modificados en la tabla de documento de solicitud
    public void Guardar_datos(String datos_recibidos) {
        String sql="";
        conexion.crearConexion();      
        try {
            sql="update tmovdoscon set fechaentrega_docsolicitud='"+datos_recibidos +"',estado_docsolicitud= 3, identregante_docsolicitud="+id_rhh+" where codigooficio_docsolicitud='"+datos[6]+"'";
            ResultSet rs = conexion.ejecutarSQLSelect(sql);
            sql="update TMOVACTCON set estado_activo=0 where (id_activo=(select idactivo_detallesolicitudmant from TMOVDTSCON where idDocSolicitud_detalleSolicitudMant=(select id_docsolicitud from TMOVDOSCON where codigooficio_docsolicitud='"+txt_codigoOficio_docSolicitud.getText()+"')));";
            ResultSet rs1 = conexion.ejecutarSQLSelect(sql);
            mensajes.msg_confirmacion(this, "Registro guardado con éxito");
        } catch (Exception x) {
        }
    }
    
    //Este método se encarga de recibir los datos de la interfaz de Control de entrega
    public void recibe() {

        if (envia == 0) {
            DatosModificar();
        } else {
            llenarcampos();
        }

    }

    /**
     * /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txt_codigoOficio_docSolicitud = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        date_fechaEntrega_docSolicitud = new com.toedter.calendar.JDateChooser();
        date_fecha_docSolicitud = new com.toedter.calendar.JDateChooser();
        jPanel2 = new javax.swing.JPanel();
        btn_buscar_persona = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txt_cedula_persona = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txt_nombre_persona = new javax.swing.JTextField();
        btn_buscar_responsabl1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Registro de Entregas");
        setPreferredSize(new java.awt.Dimension(806, 450));

        jPanel1.setPreferredSize(new java.awt.Dimension(783, 310));

        jToolBar1.setBackground(new java.awt.Color(117, 214, 255));
        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        jButton1.setBackground(new java.awt.Color(117, 214, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/add-new-file (1).png"))); // NOI18N
        jButton1.setText("   Nuevo   ");
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton1);

        jButton2.setBackground(new java.awt.Color(117, 214, 255));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/save (1).png"))); // NOI18N
        jButton2.setText("   Guardar   ");
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton2);

        jButton4.setBackground(new java.awt.Color(117, 214, 255));
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/logout (1).png"))); // NOI18N
        jButton4.setText("   Regresar");
        jButton4.setFocusable(false);
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton4);

        jPanel8.setBackground(new java.awt.Color(68, 69, 69));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 15, Short.MAX_VALUE)
        );

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 21)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Registro de Entregas");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel2.setText("Oficio de Solicitud");

        txt_codigoOficio_docSolicitud.setEnabled(false);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel3.setText("Fecha de solicitud");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel4.setText("Fecha de entrega");

        date_fecha_docSolicitud.setEnabled(false);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Detalle de persona que entrega el activo", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 17))); // NOI18N

        btn_buscar_persona.setBorder(null);
        btn_buscar_persona.setBorderPainted(false);
        btn_buscar_persona.setContentAreaFilled(false);
        btn_buscar_persona.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_buscar_personaActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel5.setText("Cédula");

        txt_cedula_persona.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_cedula_personaActionPerformed(evt);
            }
        });
        txt_cedula_persona.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_cedula_personaKeyTyped(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel6.setText("Nombres y Apellidos");

        txt_nombre_persona.setEnabled(false);
        txt_nombre_persona.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_nombre_personaActionPerformed(evt);
            }
        });

        btn_buscar_responsabl1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/magnifier-tool (1).png"))); // NOI18N
        btn_buscar_responsabl1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_buscar_responsabl1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(txt_cedula_persona, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btn_buscar_responsabl1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(418, 418, 418))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_nombre_persona, javax.swing.GroupLayout.DEFAULT_SIZE, 561, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_buscar_persona)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel5)
                                    .addComponent(txt_cedula_persona, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(btn_buscar_responsabl1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txt_nombre_persona, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btn_buscar_persona))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txt_codigoOficio_docSolicitud, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(date_fecha_docSolicitud, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(date_fechaEntrega_docSolicitud, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(10, 10, 10)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(date_fecha_docSolicitud, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(date_fechaEntrega_docSolicitud, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel4))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txt_codigoOficio_docSolicitud, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 15, Short.MAX_VALUE))
        );

        date_fecha_docSolicitud.getAccessibleContext().setAccessibleName("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 806, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 403, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_buscar_personaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_buscar_personaActionPerformed

    }//GEN-LAST:event_btn_buscar_personaActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       btnNuevo();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
       if(mensajes.msg_Pregunta(this, "¿Desea cerrar el registro de entrega de mantenimiento?")==0){
            dispose();
            FRM_ControlEntrega AbrirRegistro = new FRM_ControlEntrega(null, true);
            AbrirRegistro.setVisible(true);
       }  
    }//GEN-LAST:event_jButton4ActionPerformed

    private void btn_buscar_responsabl1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_buscar_responsabl1ActionPerformed
        //validar.validadorDeCedula(txt_cedula_persona.getText());   
        if (txt_cedula_persona.getText().trim().equals("")) {
                mensajes.msg_advertencia(this,"Ingrese un criterio de busqueda");
                txt_cedula_persona.setText("");
                txt_cedula_persona.requestFocus();
        } else {
            if (busquedacedula(txt_cedula_persona.getText()) == 0) {
                txt_nombre_persona.setText("");
                txt_cedula_persona.requestFocus();
            }
        }
    }//GEN-LAST:event_btn_buscar_responsabl1ActionPerformed

    private void txt_cedula_personaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_cedula_personaKeyTyped
        char x = evt.getKeyChar();
        if (x < '0' || x > '9' || txt_cedula_persona.getText().length()>9) {
            evt.consume();
        }
        //validar.validadorDeCedula(txt_cedula_persona.getText());
    }//GEN-LAST:event_txt_cedula_personaKeyTyped

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if (txt_cedula_persona.getText().trim().equals("")) {
            mensajes.msg_advertencia(this, "Ingrese cedula de entregante.");
            txt_cedula_persona.setText("");
            txt_cedula_persona.requestFocus();
        } else {
            if (date_fechaEntrega_docSolicitud.getDate() != null) {
                Guardar_datos(formatoFecha.format(date_fechaEntrega_docSolicitud.getDate()));
            } else {
                mensajes.msg_advertencia(this, "Ingrese fecha de entrega de soliciutud.");
                date_fechaEntrega_docSolicitud.requestFocus();
            }
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void txt_cedula_personaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_cedula_personaActionPerformed
        txt_cedula_persona.requestFocus();
    }//GEN-LAST:event_txt_cedula_personaActionPerformed

    private void txt_nombre_personaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_nombre_personaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_nombre_personaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FRM_RegistroEntregaSolicitud.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FRM_RegistroEntregaSolicitud.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FRM_RegistroEntregaSolicitud.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FRM_RegistroEntregaSolicitud.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FRM_RegistroEntregaSolicitud dialog = new FRM_RegistroEntregaSolicitud(new javax.swing.JDialog(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_buscar_persona;
    private javax.swing.JButton btn_buscar_responsabl1;
    public static com.toedter.calendar.JDateChooser date_fechaEntrega_docSolicitud;
    public static com.toedter.calendar.JDateChooser date_fecha_docSolicitud;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JToolBar jToolBar1;
    public static javax.swing.JTextField txt_cedula_persona;
    public static javax.swing.JTextField txt_codigoOficio_docSolicitud;
    public static javax.swing.JTextField txt_nombre_persona;
    // End of variables declaration//GEN-END:variables
}
