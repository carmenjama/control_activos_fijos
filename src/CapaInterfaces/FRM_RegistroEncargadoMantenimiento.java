/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaInterfaces;

import java.awt.GridBagLayout;
import java.sql.ResultSet;
import java.util.Date;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Dayanna Luna
 */
public class FRM_RegistroEncargadoMantenimiento extends javax.swing.JDialog {

    Capa_ConexionBD.Conexion conexion = new Capa_ConexionBD.Conexion();
    Capa_Mensajes.Mensajes msj = new Capa_Mensajes.Mensajes();
    DefaultTableModel model;
    public static int id_solicitud;
    int id_Encargado_mantenimiento=0;
    String[] var1 = new String[5];
    int id_recurso_Humano = 0, iddocsolicitudmantenimiento;
    public static boolean save;

    public FRM_RegistroEncargadoMantenimiento(int id_solicitud) {
     this.id_solicitud = id_solicitud;
    }
            
        
    public FRM_RegistroEncargadoMantenimiento(java.awt.Dialog parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.getContentPane().setLayout (new GridBagLayout());
        this.setLocationRelativeTo(null);
        table_nombres_mantenimiento.getTableHeader().setReorderingAllowed(false);
        this.setDefaultCloseOperation(0);
        llenar_tabla();  
        btn_guardar.setEnabled(false);
        btn_actualizar.setEnabled(false);
        Date fecha = new java.util.Date();
        date_fechaInicio_mantActivo.setDate(fecha);
    }

    public void llenar_tabla(){
        conexion.crearConexion();
        String[] var1 = new String[5];
        model = (DefaultTableModel) table_nombres_mantenimiento.getModel();
        String sql = "select p.id_persona,p.cedula_persona,p.nombre_persona,"
                   + "p.apellido_persona from tmovrehcon rh ,tmaepercon p "
                   + "where p.id_persona = idpersona_rrhh and rh.tipo_rrhh = 'Mantenimiento'";
        ResultSet rs = conexion.ejecutarSQLSelect(sql);
        try {
            while (rs.next()) {
                var1[0] = rs.getString(1);
                var1[1] = rs.getString(2);
                var1[2] = rs.getString(3);
                var1[3] = rs.getString(4);
                model.addRow(var1);
            }
        } catch (Exception ex) {
            System.out.println("error");
        }
    }
    
    public void actualizar_estado_solicitud(){
        conexion.crearConexion();
        String sql = "update tmovdoscon set estado_docsolicitud = 1 where id_docsolicitud = '"+id_solicitud+"'";
        conexion.ejecutarSQLSelect(sql);        
    }

    public void registrar_encargado_solicitud(){
        conexion.crearConexion();        
        model = (DefaultTableModel) table_nombres_mantenimiento.getModel();
        String sql = "insert into TMOVMANCON (fechainicio_mantactivo,fechafin_mantactivo"
                + ",iddocsolicitud_mantactivo,tipopc_mantactivo,tipohs_mantactivo,actividades_mantactivo"
                + ",observaciones_mantactivo,idpersonarealiza_mantactivo)"
                + "values ('"+date_fechaInicio_mantActivo.getDate()+"','0001-01-01',"+ id_solicitud +",0,0,'','',"+id_recurso_Humano+")";          
        try {                       
        conexion.ejecutarSQLSelect(sql);        
        } catch (Exception ex) {
            System.out.println("error");
        }    
        msj.msg_confirmacion(rootPane, "Registro de mantenimiento guardado con ??xito.");
        actualizar_estado_solicitud();
        btn_guardar.setEnabled(false);
    }
    
    public void actualizar_encargado_solicitud(){
        conexion.crearConexion();        
        model = (DefaultTableModel) table_nombres_mantenimiento.getModel();
        String sql = "update TMOVMANCON set idpersonarealiza_mantactivo="+id_recurso_Humano+" where iddocsolicitud_mantactivo="+id_solicitud;          
        try {                       
        conexion.ejecutarSQLSelect(sql);        
        } catch (Exception ex) {
            System.out.println("error");
        }    
        msj.msg_confirmacion(rootPane, "Registro actualizado con ??xito.");
        actualizar_estado_solicitud();
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_nombres_mantenimiento = new javax.swing.JTable();
        label3 = new java.awt.Label();
        label2 = new java.awt.Label();
        date_fechaInicio_mantActivo = new com.toedter.calendar.JDateChooser();
        jToolBar1 = new javax.swing.JToolBar();
        btn_guardar = new javax.swing.JButton();
        btn_actualizar = new javax.swing.JButton();
        btn_regresar = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Registro Datos Encargado de Mantenimiento");
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jButton1.setBorder(null);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jButton5.setBorder(null);
        jButton5.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        table_nombres_mantenimiento.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "C??dula", "Nombres", "Apellidos"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table_nombres_mantenimiento.setNextFocusableComponent(btn_guardar);
        table_nombres_mantenimiento.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        table_nombres_mantenimiento.removeColumn(table_nombres_mantenimiento.getColumnModel().getColumn(0));
        table_nombres_mantenimiento.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_nombres_mantenimientoMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table_nombres_mantenimiento);

        label3.setAlignment(java.awt.Label.CENTER);
        label3.setFont(new java.awt.Font("Tahoma", 1, 21)); // NOI18N
        label3.setText("Registro Encargado de Mantenimiento");

        label2.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        label2.setText("Fecha de envio a Mantenimiento");

        date_fechaInicio_mantActivo.setDoubleBuffered(false);
        date_fechaInicio_mantActivo.setEnabled(false);

        jToolBar1.setBackground(new java.awt.Color(117, 214, 255));
        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        btn_guardar.setBackground(new java.awt.Color(117, 214, 255));
        btn_guardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/save (1).png"))); // NOI18N
        btn_guardar.setText("   Guardar   ");
        btn_guardar.setFocusable(false);
        btn_guardar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_guardar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_guardarActionPerformed(evt);
            }
        });
        jToolBar1.add(btn_guardar);

        btn_actualizar.setBackground(new java.awt.Color(117, 214, 255));
        btn_actualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/refresh-page-arrow-button (1).png"))); // NOI18N
        btn_actualizar.setText("   Actualizar   ");
        btn_actualizar.setFocusable(false);
        btn_actualizar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_actualizar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_actualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_actualizarActionPerformed(evt);
            }
        });
        jToolBar1.add(btn_actualizar);

        btn_regresar.setBackground(new java.awt.Color(117, 214, 255));
        btn_regresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/logout (1).png"))); // NOI18N
        btn_regresar.setText("   Regresar");
        btn_regresar.setFocusable(false);
        btn_regresar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_regresar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_regresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_regresarActionPerformed(evt);
            }
        });
        jToolBar1.add(btn_regresar);

        jPanel7.setBackground(new java.awt.Color(68, 69, 69));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 15, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(label3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 697, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1)
                        .addGap(40, 40, 40)
                        .addComponent(jButton5)
                        .addGap(43, 43, 43))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(label2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(date_fechaInicio_mantActivo, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(280, 280, 280))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addComponent(label3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(68, 68, 68)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(date_fechaInicio_mantActivo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(label2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton5)
                    .addComponent(jButton1))
                .addGap(35, 35, 35))
        );

        jButton1.getAccessibleContext().setAccessibleName("btn_guardar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void btn_guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_guardarActionPerformed
        registrar_encargado_solicitud();     
    }//GEN-LAST:event_btn_guardarActionPerformed

    private void table_nombres_mantenimientoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_nombres_mantenimientoMouseClicked
        if(save==true){
            btn_guardar.setEnabled(true);
            btn_actualizar.setEnabled(false);
        }else{
            btn_guardar.setEnabled(false);
            btn_actualizar.setEnabled(true);
        }
        int inicio = 0;
        String[] Encargado_Mantenimiento = new String[3];
        while (inicio != 3) {
            Encargado_Mantenimiento[inicio] = table_nombres_mantenimiento.getValueAt(table_nombres_mantenimiento.getSelectedRow(), inicio).toString();
            inicio++;
        }
        conexion.crearConexion();
        model = (DefaultTableModel) table_nombres_mantenimiento.getModel();
        String valor = (String)table_nombres_mantenimiento.getValueAt(table_nombres_mantenimiento.getSelectedRow(), 0);
        String sql = "select id_rrhh from TMOVREHCON where idpersona_rrhh=(select id_persona from TMAEPERCON where cedula_persona='"+valor+"')";
        try {
            ResultSet rs = conexion.ejecutarSQLSelect(sql);
            if (rs.next()) {
                System.out.print("ENCARGADO "+rs.getInt(1));
                id_recurso_Humano = rs.getInt(1);
            }
        } catch (Exception ex) {
            System.out.println("error");
        }
        
        if (table_nombres_mantenimiento.getSelectedRows().length > 0) {
            btn_guardar.setEnabled(true);
            //btn_actualizar.setEnabled(true);
            if (save == true) {
                btn_guardar.setEnabled(true);
                btn_actualizar.setEnabled(false);
            } else {
                btn_guardar.setEnabled(false);
                btn_actualizar.setEnabled(true);
            }
        }
    }//GEN-LAST:event_table_nombres_mantenimientoMouseClicked

    private void btn_actualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_actualizarActionPerformed
        actualizar_encargado_solicitud();
    }//GEN-LAST:event_btn_actualizarActionPerformed

    private void btn_regresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_regresarActionPerformed
        if(msj.msg_Pregunta(this, "??Est?? seguro que desea cancelar el env??o de sus activos a mantenimiento?")==0){
            dispose();
        }
    }//GEN-LAST:event_btn_regresarActionPerformed

    
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
            java.util.logging.Logger.getLogger(FRM_RegistroEncargadoMantenimiento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FRM_RegistroEncargadoMantenimiento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FRM_RegistroEncargadoMantenimiento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FRM_RegistroEncargadoMantenimiento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FRM_RegistroEncargadoMantenimiento dialog = new FRM_RegistroEncargadoMantenimiento(new javax.swing.JDialog(), true);
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
    private javax.swing.JButton btn_actualizar;
    private javax.swing.JButton btn_guardar;
    private javax.swing.JButton btn_regresar;
    private com.toedter.calendar.JDateChooser date_fechaInicio_mantActivo;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private java.awt.Label label2;
    private java.awt.Label label3;
    private javax.swing.JTable table_nombres_mantenimiento;
    // End of variables declaration//GEN-END:variables
}
