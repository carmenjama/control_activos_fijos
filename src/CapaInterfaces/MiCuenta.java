/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaInterfaces;

import static CapaInterfaces.FRM_RegistroActivos.msj;
import Capa_ConexionBD.Conexion;
import Capa_Mensajes.Mensajes;
import Capa_Validaciones.Validaciones;
import Objetos.Usuario;
import java.awt.Event;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.InputMap;
import javax.swing.KeyStroke;

/**
 *
 * @author Karlita
 */
public class MiCuenta extends javax.swing.JDialog {

    /**
     * Creates new form MiCuenta
     */
    //
    Validaciones validar = new Validaciones();
    Mensajes mensaje = new Mensajes();
    Conexion conexionBD = new Conexion();
    ResultSet resultado;
    int conEspacioNombre = 0;
    int conEspacioApellido = 0;

    String nombre;    
    String apellido;
    String correo;
    String nombreUsuarioLogueado;
    Mensajes mensajes = new Mensajes();
    public MiCuenta(java.awt.Frame parent, boolean modal, String usuarioLogueado) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(null);
        this.setDefaultCloseOperation(0);
        bloquearTeclas();
        this.nombreUsuarioLogueado=usuarioLogueado;
         conexionBD.crearConexion();
        
        try {
            resultado = conexionBD.ejecutarSQLSelect("select nombre_persona, apellido_persona, correo_persona from tmaepercon as p Inner join tmaeusucon as u ON p.id_persona = u.id_persona where id_usuario = (select id_usuario from tmaeusucon where nombre_usuario = '"+nombreUsuarioLogueado+"' )");            
            while(resultado.next()){
            txt_nombre_persona.setText(resultado.getString("nombre_persona"));
            txt_apellidos_persona.setText(resultado.getString("apellido_persona"));
            txt_correo_persona.setText(resultado.getString("correo_persona"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(MiCuenta.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    //Bloquea acciones de copiar y pegar del teclado
    public void bloquearTeclas(){
        InputMap map1 = txt_nombre_persona.getInputMap(txt_nombre_persona.WHEN_FOCUSED);
        map1.put(KeyStroke.getKeyStroke(KeyEvent.VK_V, Event.CTRL_MASK), "null");
        InputMap map2 = txt_apellidos_persona.getInputMap(txt_apellidos_persona.WHEN_FOCUSED);
        map2.put(KeyStroke.getKeyStroke(KeyEvent.VK_V, Event.CTRL_MASK), "null");
        InputMap map3 = txt_correo_persona.getInputMap(txt_correo_persona.WHEN_FOCUSED);
        map3.put(KeyStroke.getKeyStroke(KeyEvent.VK_V, Event.CTRL_MASK), "null");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txt_nombre_persona = new javax.swing.JTextField();
        txt_apellidos_persona = new javax.swing.JTextField();
        txt_correo_persona = new javax.swing.JTextField();
        jToolBar1 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Mi cuenta");

        jPanel1.setPreferredSize(new java.awt.Dimension(4, 56));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 21)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Configuración de Cuenta");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel2.setText("Nombres");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel3.setText("Apellidos");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel4.setText("Corrreo");

        txt_nombre_persona.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_nombre_personaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_nombre_personaKeyTyped(evt);
            }
        });

        txt_apellidos_persona.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_apellidos_personaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_apellidos_personaKeyTyped(evt);
            }
        });

        jToolBar1.setBackground(new java.awt.Color(117, 214, 255));
        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        jButton1.setBackground(new java.awt.Color(117, 214, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/refresh-page-arrow-button (1).png"))); // NOI18N
        jButton1.setText("   Actualizar   ");
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
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/logout (1).png"))); // NOI18N
        jButton2.setText("   Regresar");
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton2);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 76, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txt_apellidos_persona)
                                    .addComponent(txt_correo_persona, javax.swing.GroupLayout.DEFAULT_SIZE, 356, Short.MAX_VALUE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(txt_nombre_persona, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(48, 48, 48))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(jLabel1)
                .addGap(48, 48, 48)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txt_nombre_persona, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_apellidos_persona, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_correo_persona, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 577, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 313, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if(msj.msg_Pregunta(this, "Los datos que no ha guardado se borraran.\n¿Esta seguro que desea regresar?")==0){
            dispose();
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        nombre = txt_nombre_persona.getText().trim();
        apellido = txt_apellidos_persona.getText().trim();
        correo = txt_correo_persona.getText().trim();

        if (!"".equals(nombre) && !"".equals(apellido) && !"".equals(correo)) {
            //Valida que el formato del correo este correcto al presionar Actualizar
            
            if (validar.validarEmail(correo)) {
                String Contraseña = mensaje.inputContraseña(this, "Para poder actualizar sus datos debe de ingresar su contraseña");
                 if (Contraseña != null) {
                     Contraseña = Contraseña.trim();
                if (!"".equals(Contraseña)) {
                             System.out.println(nombreUsuarioLogueado);
                        if (conexionBD.ejecutarSQLVerificarClave(nombreUsuarioLogueado, Contraseña)==true) {
                            if (conexionBD.ejecutarSQL("select actualizacion_TMAEUSUCON('"+nombreUsuarioLogueado+"' , '"+nombre+"' , '"+apellido+"' , '"+correo+"')")) {
                                mensaje.msg_confirmacion(this, "Datos actualizados con éxito");
                            } else {
                                mensaje.msg_advertencia(this, "Error al actualizar datos");
                            }
                        } else {
                            mensaje.msg_advertencia(this, "Clave Incorrecta");
                        }
                    }else{mensaje.msg_advertencia(this, "El campo no puede estar vacio");}
                }else{}
            } else {
                mensaje.msg_advertencia(this, "Correo no valido");
            }
        } else {
            mensaje.msg_advertencia(this, "Ningun campo puede estar vacio");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txt_nombre_personaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nombre_personaKeyPressed
           //verifica si el unico espacio permitido en el TextField Apellidos se ha borrado
        if (evt.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            String comparar = txt_nombre_persona.getText();
            if (comparar.indexOf(' ') != -1) {
                conEspacioNombre = 0;
            }
        }
    }//GEN-LAST:event_txt_nombre_personaKeyPressed

    private void txt_nombre_personaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nombre_personaKeyTyped
        char c = evt.getKeyChar();
        if (Character.isUpperCase(c) || Character.isLowerCase(c)) {
        } else if (Character.isSpaceChar(c)) {
            if (conEspacioNombre == 0) {
                conEspacioNombre = 1;
            } else {
                evt.consume();
            }
        } else if (evt.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
        } else {
            evt.consume();
        }
    }//GEN-LAST:event_txt_nombre_personaKeyTyped

    private void txt_apellidos_personaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_apellidos_personaKeyTyped
         char c = evt.getKeyChar();
        if (Character.isUpperCase(c) || Character.isLowerCase(c)) {
        } else if (Character.isSpaceChar(c)) {
            if (conEspacioApellido == 0) {
                conEspacioApellido = 1;
            } else {
                evt.consume();
            }
        } else if (evt.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
        } else {
            evt.consume();
        }
    }//GEN-LAST:event_txt_apellidos_personaKeyTyped

    private void txt_apellidos_personaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_apellidos_personaKeyPressed
         //verifica si el unico espacio permitido en el TextField apellidos se ha borrado 
        if (evt.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
            String comparar = txt_apellidos_persona.getText();
            if (comparar.indexOf(' ') != -1) {
                conEspacioApellido = 0;
            }
        }
    }//GEN-LAST:event_txt_apellidos_personaKeyPressed

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
            java.util.logging.Logger.getLogger(MiCuenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MiCuenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MiCuenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MiCuenta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                MiCuenta dialog = new MiCuenta(new javax.swing.JFrame(), true, "");
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
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTextField txt_apellidos_persona;
    private javax.swing.JTextField txt_correo_persona;
    private javax.swing.JTextField txt_nombre_persona;
    // End of variables declaration//GEN-END:variables
}
