/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaInterfaces;

import static CapaInterfaces.FRM_ConfiguracionInicial.msj;
import Capa_Mensajes.Mensajes;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;


/**
 *
 * @author Erick
 */
public class FRM_Principal extends javax.swing.JFrame {
    //Llama obbjeto para los mensajes
    Mensajes mensajes = new Mensajes();
    //Crea variables para controlar el tipo de usuario que ingresa al sistema
    private int tipo_usuario;
    private String nombre_usuario;
    /**
     * Creates new form Principal
     */
    
    //Siempre que se inicialice el formulario se recibira el tipo y nombre de usuario
    public FRM_Principal(int var_tipoUsuario, String var_nombreUsuario) {
        this.tipo_usuario=var_tipoUsuario;
        this.nombre_usuario=var_nombreUsuario;
        initComponents();
        setLocationRelativeTo(null);
        setIconImage(new ImageIcon(getClass().getResource("/Icons/logo_2.png")).getImage());
        cerrar();
    }
    
    //Valida boton de cerrar del formulario y Alt+F4
    public void cerrar() {
        try {
            this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    confirmarSalida();
                }
            });
            this.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // Muestra un mensaje de confirmacion de salida del formulario
    public void confirmarSalida() {
        if (msj.msg_Pregunta(this, "Al cerrar esta ventana, se cerrará sesión de su cuenta.\n ¿Está seguro que desea salir?")==0) {
            dispose();
        new FRM_Ingreso().setVisible(true);
        }
    }
    /**
     * En cada uno de los siguientes metodos se llama a las diferentes acciones
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuBar2 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        btn_configuracion = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        txt_actisoft = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        txt_nombre_institucion = new javax.swing.JLabel();
        txt_descripcion_institucion = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        btn_inventario = new javax.swing.JMenu();
        btn_registrar_activo = new javax.swing.JMenuItem();
        btn_asignar_activo = new javax.swing.JMenuItem();
        btn_activos_responsable = new javax.swing.JMenuItem();
        btn_gestion_areas = new javax.swing.JMenuItem();
        btn_recursos_humanos = new javax.swing.JMenu();
        btn_registrar_rrhh = new javax.swing.JMenuItem();
        btn_mantenimiento = new javax.swing.JMenu();
        btn_solicitar_mantenimiento = new javax.swing.JMenuItem();
        btn_actualizar_solicitudes = new javax.swing.JMenuItem();
        btn_control_entregas = new javax.swing.JMenuItem();
        btn_cuenta = new javax.swing.JMenu();
        btn_mi_cuenta = new javax.swing.JMenuItem();
        btn_cambiar_clave = new javax.swing.JMenuItem();
        btn_cerrar_sesion = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        btn_manual_usuario = new javax.swing.JMenuItem();
        btn_manual_tecnico = new javax.swing.JMenuItem();

        jMenuItem3.setText("jMenuItem3");

        jMenu3.setText("File");
        jMenuBar2.add(jMenu3);

        jMenu4.setText("Edit");
        jMenuBar2.add(jMenu4);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("ActiSoft");
        setExtendedState(6);
        addWindowStateListener(new java.awt.event.WindowStateListener() {
            public void windowStateChanged(java.awt.event.WindowEvent evt) {
                formWindowStateChanged(evt);
            }
        });

        btn_configuracion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/edite (1).png"))); // NOI18N
        btn_configuracion.setContentAreaFilled(false);
        btn_configuracion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_configuracionActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(68, 69, 69));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 13, Short.MAX_VALUE)
        );

        txt_actisoft.setFont(new java.awt.Font("Calibri Light", 0, 220)); // NOI18N
        txt_actisoft.setForeground(new java.awt.Color(61, 168, 255));
        txt_actisoft.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txt_actisoft.setText("actisoft");
        txt_actisoft.setNextFocusableComponent(txt_actisoft);

        txt_nombre_institucion.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        txt_nombre_institucion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txt_nombre_institucion.setText("Universidad Laica Eloy Alfaro de Manabí");

        txt_descripcion_institucion.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        txt_descripcion_institucion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txt_descripcion_institucion.setText("Facultad de Ciencias Informáticas");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txt_nombre_institucion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(txt_descripcion_institucion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txt_nombre_institucion, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_descripcion_institucion, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(73, Short.MAX_VALUE))
        );

        jMenuBar1.setBackground(new java.awt.Color(117, 214, 255));
        jMenuBar1.setPreferredSize(new java.awt.Dimension(536, 60));

        btn_inventario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/formulario (1).png"))); // NOI18N
        btn_inventario.setText("Inventario");

        btn_registrar_activo.setText("Registrar Activo");
        btn_registrar_activo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_registrar_activoActionPerformed(evt);
            }
        });
        btn_inventario.add(btn_registrar_activo);

        btn_asignar_activo.setText("Asignar Activos");
        btn_asignar_activo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_asignar_activoActionPerformed(evt);
            }
        });
        btn_inventario.add(btn_asignar_activo);

        btn_activos_responsable.setText("Activos por Responsable");
        btn_activos_responsable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_activos_responsableActionPerformed(evt);
            }
        });
        btn_inventario.add(btn_activos_responsable);

        btn_gestion_areas.setText("Control de Áreas");
        btn_gestion_areas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_gestion_areasActionPerformed(evt);
            }
        });
        btn_inventario.add(btn_gestion_areas);

        jMenuBar1.add(btn_inventario);

        btn_recursos_humanos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/grupo (1).png"))); // NOI18N
        btn_recursos_humanos.setText("Recursos Humanos");

        btn_registrar_rrhh.setText("Control Recursos Humanos");
        btn_registrar_rrhh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_registrar_rrhhActionPerformed(evt);
            }
        });
        btn_recursos_humanos.add(btn_registrar_rrhh);

        jMenuBar1.add(btn_recursos_humanos);

        btn_mantenimiento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/repairing-service (1).png"))); // NOI18N
        btn_mantenimiento.setText("Mantenimiento");

        btn_solicitar_mantenimiento.setText("Solicitar Mantenimiento");
        btn_solicitar_mantenimiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_solicitar_mantenimientoActionPerformed(evt);
            }
        });
        btn_mantenimiento.add(btn_solicitar_mantenimiento);

        btn_actualizar_solicitudes.setText("Actualizar Solicitudes");
        btn_actualizar_solicitudes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_actualizar_solicitudesActionPerformed(evt);
            }
        });
        btn_mantenimiento.add(btn_actualizar_solicitudes);

        btn_control_entregas.setText("Control de Entregas");
        btn_control_entregas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_control_entregasActionPerformed(evt);
            }
        });
        btn_mantenimiento.add(btn_control_entregas);

        jMenuBar1.add(btn_mantenimiento);

        btn_cuenta.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/usuario (1).png"))); // NOI18N
        btn_cuenta.setText("Mi Cuenta");

        btn_mi_cuenta.setText("Mi cuenta");
        btn_mi_cuenta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_mi_cuentaActionPerformed(evt);
            }
        });
        btn_cuenta.add(btn_mi_cuenta);

        btn_cambiar_clave.setText("Cambiar Clave");
        btn_cambiar_clave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cambiar_claveActionPerformed(evt);
            }
        });
        btn_cuenta.add(btn_cambiar_clave);

        btn_cerrar_sesion.setText("Cerrar Sesión");
        btn_cerrar_sesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cerrar_sesionActionPerformed(evt);
            }
        });
        btn_cuenta.add(btn_cerrar_sesion);

        jMenuBar1.add(btn_cuenta);

        jMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/signo-de-interrogacion (1).png"))); // NOI18N
        jMenu1.setText("Ayuda");

        btn_manual_usuario.setText("Manual Usuario");
        btn_manual_usuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_manual_usuarioActionPerformed(evt);
            }
        });
        jMenu1.add(btn_manual_usuario);

        btn_manual_tecnico.setText("Manual Tecnico");
        btn_manual_tecnico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_manual_tecnicoActionPerformed(evt);
            }
        });
        jMenu1.add(btn_manual_tecnico);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txt_actisoft, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 822, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btn_configuracion, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_configuracion)
                .addGap(219, 219, 219)
                .addComponent(txt_actisoft, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowStateChanged(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowStateChanged
         //TODO add your handling code here:
    }//GEN-LAST:event_formWindowStateChanged

    private void btn_registrar_activoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_registrar_activoActionPerformed
        new FRM_RegistroActivos(this, true).setVisible(true);
        FRM_RegistroActivos.id_activo   =0;
    }//GEN-LAST:event_btn_registrar_activoActionPerformed

    private void btn_control_entregasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_control_entregasActionPerformed
        new FRM_ControlEntrega(this, true).setVisible(true);
    }//GEN-LAST:event_btn_control_entregasActionPerformed

    private void btn_cerrar_sesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cerrar_sesionActionPerformed
        dispose();
        new FRM_Ingreso().setVisible(true);
    }//GEN-LAST:event_btn_cerrar_sesionActionPerformed

    private void btn_asignar_activoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_asignar_activoActionPerformed
        new FRM_AsignacionActivos( this, true).setVisible(true);
    }//GEN-LAST:event_btn_asignar_activoActionPerformed

    private void btn_activos_responsableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_activos_responsableActionPerformed
        new FRM_BusquedaActivoResponsable( this, true).setVisible(true);
    }//GEN-LAST:event_btn_activos_responsableActionPerformed

    private void btn_gestion_areasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_gestion_areasActionPerformed
        new FRM_ControlAreas( this, true).setVisible(true);
    }//GEN-LAST:event_btn_gestion_areasActionPerformed

    private void btn_registrar_rrhhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_registrar_rrhhActionPerformed
        new FRM_ControlRecursosHumanos(this, true).setVisible(true);
    }//GEN-LAST:event_btn_registrar_rrhhActionPerformed

    private void btn_solicitar_mantenimientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_solicitar_mantenimientoActionPerformed
        new FRM_SolicitarMantenimiento( this, true).setVisible(true);
    }//GEN-LAST:event_btn_solicitar_mantenimientoActionPerformed

    private void btn_actualizar_solicitudesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_actualizar_solicitudesActionPerformed
        new FRM_ControlSolicitudesMantenimiento( this, true).setVisible(true);
    }//GEN-LAST:event_btn_actualizar_solicitudesActionPerformed

    private void btn_mi_cuentaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_mi_cuentaActionPerformed
        new MiCuenta( this, true, nombre_usuario).setVisible(true);
    }//GEN-LAST:event_btn_mi_cuentaActionPerformed

    private void btn_cambiar_claveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cambiar_claveActionPerformed
        new CambiarClave( this, true, nombre_usuario).setVisible(true);
    }//GEN-LAST:event_btn_cambiar_claveActionPerformed

    private void btn_manual_usuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_manual_usuarioActionPerformed
        new JPDF(this, true, "src/Reportes/Manualusu.pdf").setVisible(true);
    }//GEN-LAST:event_btn_manual_usuarioActionPerformed

    private void btn_manual_tecnicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_manual_tecnicoActionPerformed
        new JPDF(this, true, "src/Reportes/Manual.pdf").setVisible(true);
    }//GEN-LAST:event_btn_manual_tecnicoActionPerformed

    private void btn_configuracionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_configuracionActionPerformed
        new Configuracion(this, true, nombre_usuario).setVisible(true);
    }//GEN-LAST:event_btn_configuracionActionPerformed
    
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
            java.util.logging.Logger.getLogger(FRM_Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FRM_Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FRM_Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FRM_Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FRM_Principal(0, "").setVisible(true);
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem btn_activos_responsable;
    private javax.swing.JMenuItem btn_actualizar_solicitudes;
    private javax.swing.JMenuItem btn_asignar_activo;
    private javax.swing.JMenuItem btn_cambiar_clave;
    private javax.swing.JMenuItem btn_cerrar_sesion;
    public static javax.swing.JButton btn_configuracion;
    private javax.swing.JMenuItem btn_control_entregas;
    public static javax.swing.JMenu btn_cuenta;
    private javax.swing.JMenuItem btn_gestion_areas;
    public static javax.swing.JMenu btn_inventario;
    public static javax.swing.JMenu btn_mantenimiento;
    private javax.swing.JMenuItem btn_manual_tecnico;
    private javax.swing.JMenuItem btn_manual_usuario;
    private javax.swing.JMenuItem btn_mi_cuenta;
    public static javax.swing.JMenu btn_recursos_humanos;
    private javax.swing.JMenuItem btn_registrar_activo;
    private javax.swing.JMenuItem btn_registrar_rrhh;
    private javax.swing.JMenuItem btn_solicitar_mantenimiento;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuBar jMenuBar2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel txt_actisoft;
    public static javax.swing.JLabel txt_descripcion_institucion;
    public static javax.swing.JLabel txt_nombre_institucion;
    // End of variables declaration//GEN-END:variables
}
