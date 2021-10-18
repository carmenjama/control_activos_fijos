
package CapaInterfaces;

import java.awt.GridBagLayout;
import Atxy2k.CustomTextField.RestrictedTextField;
import Capa_ConexionBD.Conexion;
import Capa_Validaciones.Validaciones;
import Objetos.Usuario;
import enviarmail.EnviarMail;
import java.awt.Event;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JFrame;
import javax.swing.KeyStroke;

/**
 *
 * @author Carmen
 */
public class FRM_Ingreso extends javax.swing.JFrame {
    
    //Inicia variables a utilizar
    String nombre_usuario, clave_usuario;
    //Variables a utilizar para controles de acceso
    int var_ingreso, tipo_usuario, numero_intentos=0;
    //Objetos para mensajes, usuario o conexion.
    static Capa_Mensajes.Mensajes msj = new Capa_Mensajes.Mensajes();
    Usuario usuario = new Usuario();
    Validaciones validar = new Validaciones();
    Conexion conexion = new Conexion();
    ResultSet rs;
    static EnviarMail EnviarMail = new EnviarMail();
    
    /**
     * Creates new form Inresar
     */
    
    public FRM_Ingreso() {
        initComponents();
        this.getContentPane().setLayout (new GridBagLayout());
        //Blquea Ctrl+C, Ctrl+V
        bloquearTeclas();
        //Limita los caracteres al escribir
        limitar_caracteres();
        //Icono de la aplicacion
        setIconImage(new ImageIcon(getClass().getResource("/Icons/logo_2.png")).getImage());
        //Metodo necesario para poder cambiar clave de usuario
        EnviarMail.Autentificacion();
        //Crea conexion con la BD
        conexion.crearConexion();
        cerrar();
    }
    
    //Bloquea acciones de copiar y pegar del teclado
    public void bloquearTeclas(){
        InputMap map1 = txt_nombre_usuario.getInputMap(txt_nombre_usuario.WHEN_FOCUSED);
        map1.put(KeyStroke.getKeyStroke(KeyEvent.VK_V, Event.CTRL_MASK), "null");
        InputMap map2 = txt_clave_usuario.getInputMap(txt_clave_usuario.WHEN_FOCUSED);
        map2.put(KeyStroke.getKeyStroke(KeyEvent.VK_V, Event.CTRL_MASK), "null");
    }
    
    //Limita numero de caracteres al ingresar
    public void limitar_caracteres(){
        RestrictedTextField limite_nombre = new RestrictedTextField(txt_nombre_usuario);
        limite_nombre.setLimit(20);
        RestrictedTextField limite_clave = new RestrictedTextField(txt_clave_usuario);
        limite_clave.setLimit(16);
    }
    
    //Valida que los campos esten correctos a ingresar
    public void validarCampos(){
        nombre_usuario=txt_nombre_usuario.getText().trim();
        clave_usuario=txt_clave_usuario.getText();
        if(nombre_usuario.equals("")){
            msj.msg_advertencia(this, "Los campos no pueden estar vacíos, por favor ingrese nombre de usuario.");
            txt_nombre_usuario.setText("");
            txt_nombre_usuario.requestFocus();
        }else{
            iniciar_sesion();
        }
    }
    
    //Metodo que valida si el usuario y la clave de acceso son correctos
    public void iniciar_sesion(){
        if(usuario.Conexion()==true){
            var_ingreso=usuario.validar_ingreso(nombre_usuario, clave_usuario);
            if(var_ingreso==0){
                msj.msg_advertencia(this, "Usuario o clave incorrectas, intente de nuevo.");
                txt_nombre_usuario.setText("");
                txt_clave_usuario.setText("");
                contar_intentos(); 
                txt_nombre_usuario.requestFocus();
            }else{
                if(var_ingreso==1){
                    try {
                        //Llama a metodo para saber el tipo de usuario que ingresa al sistema, necesario para bloquear 
                        //o desbloquear botones
                        tipo_usuario=usuario.tipo_usuario(txt_nombre_usuario.getText(), txt_clave_usuario.getText());
                        //Cierra la centana de inicio de sesion y abre el formulario principal del sistema
                        dispose();
                        FRM_Principal ejecutar = new FRM_Principal(tipo_usuario, nombre_usuario);
                        //Se raliza una consulta para que el formulario principal inice con los datos de la institucion
                        String sql="select nombre_institucion, direccion_institucion from tmaeinscon where id_institucion=1";
                        rs=Capa_ConexionBD.Conexion.ejecutarSQLSelect(sql);
                        rs.next();
                        ejecutar.txt_nombre_institucion.setText(rs.getString("nombre_institucion"));
                        ejecutar.txt_descripcion_institucion.setText(rs.getString("direccion_institucion"));
                        //Desbloquea controles para el usuario de administrador global
                        if (tipo_usuario==1){
                            ejecutar.btn_configuracion.setEnabled(true);
                            ejecutar.btn_cuenta.setEnabled(true);
                            ejecutar.btn_inventario.setEnabled(true);
                            ejecutar.btn_mantenimiento.setEnabled(true);
                            ejecutar.btn_recursos_humanos.setEnabled(true);
                            ejecutar.setVisible (true);
                        }
                        //Bloquea o desbloquea acciones para el usuario de administrador
                        else if(tipo_usuario==2){
                            ejecutar.btn_configuracion.setEnabled(true);
                            ejecutar.btn_cuenta.setEnabled(true);
                            ejecutar.btn_inventario.setEnabled(true);
                            ejecutar.btn_recursos_humanos.setEnabled(true);
                            ejecutar.btn_mantenimiento.setEnabled(false);
                            ejecutar.setVisible (true);
                        }else if(tipo_usuario==3){
                            //Bloquea o desbloquea controles para el usuario administrador de mantenimiento
                            ejecutar.btn_inventario.setEnabled(false);
                            ejecutar.btn_cuenta.setEnabled(true);
                            ejecutar.btn_mantenimiento.setEnabled(true);
                            ejecutar.btn_recursos_humanos.setEnabled(false);
                            ejecutar.btn_configuracion.setEnabled(false);
                            ejecutar.setVisible(true);
                        }
                        rs.close();
                    } catch (SQLException ex) {
                        Logger.getLogger(FRM_Ingreso.class.getName()).log(Level.SEVERE, null, ex);
                    }   
                }
            }
        }else{
            msj.msg_advertencia(this, "Ha ocurrido un error, inténtelo de nuevo.");
        }
    }
    
    //Metodo para limitar el numero de intentos de acceso al sistema
    public void contar_intentos(){
        numero_intentos=numero_intentos+1;
        //Si es mayor a 4 entonces bloquea boton de acceso
        if(numero_intentos>3){
            msj.msg_advertencia(this, "Exedió su número de intentos, vuelva más tarde.\n Si ha olvidado su clave, intente recuperarla.");
            btn_ingresar.setEnabled(false);
            final Runnable tarea = new Runnable() {
            public void run() {
                btn_ingresar.setEnabled(true);
            }
            };
            //Bloquea controles por un total de 3 minutos
            ScheduledExecutorService timer = Executors.newSingleThreadScheduledExecutor();
            timer.scheduleAtFixedRate(tarea, 3, 3, TimeUnit.MINUTES);
        }
    }
    
    //Metodo utilizado cambiar clave de acceso
    public void cambiarClave() {
        if (EnviarMail.comprobarConexion(this)) {
            String email = msj.msg_ConCuadroDeTexto(this, "Ingrese su correo por favor.");
            if (email != null) {
                email= email.trim();
                if (!"".equals(email)) {
                    if (validar.validarEmail(email)) {
                        //Se valida que el correo ingresado pertenesca a un usuario
                        if (conexion.ejecutarSQLVerificarCorreo(email)) {
                            if (EnviarMail.comprobarConexion(this)) {
                                //Se arma el mensaje con el nombre de usuario y la clave de accesso nueva que genera la BD
                                String ContenidoMail = " Nombre Usuario: " + conexion.nombreUsuario + "\n Nueva Clave:    " + conexion.generarCorreoActualizar();
                                msj.msg_confirmacion(this, "Hemos enviado una clave temporal a su correo, por favor revise su bandeja de entrada.");
                                EnviarMail.SendMail(email, ContenidoMail);
                            }
                        } else {
                            //Si el correo no pertenece a ningun usuario se envia menssaje
                            msj.msg_advertencia(this, "Este correo no esta vinculado a un usuario del sistema.");
                        }
                    } else {
                        msj.msg_advertencia(this, "Correo inválido, ingrese dato nuevamente.");
                        cambiarClave();
                    }
                } else {
                    msj.msg_advertencia(this, "Coreo no puede estarr vacío, por favor ingrese valor.");
                }
            }
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txt_nombre_usuario = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        btn_ingresar = new javax.swing.JButton();
        txt_clave_usuario = new javax.swing.JPasswordField();
        btn_cambiar_clave = new javax.swing.JButton();
        btn_salir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Inicio Sesión");
        setBackground(new java.awt.Color(255, 255, 255));
        setExtendedState(6);

        jPanel.setBackground(new java.awt.Color(243, 243, 243));
        jPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 21)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(61, 168, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Ingreso al Sistema");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel3.setText("Usuario");

        txt_nombre_usuario.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        txt_nombre_usuario.setNextFocusableComponent(txt_clave_usuario);
        txt_nombre_usuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_nombre_usuarioKeyTyped(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel4.setText("Clave");

        btn_ingresar.setBackground(new java.awt.Color(237, 237, 237));
        btn_ingresar.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        btn_ingresar.setText("Ingresar");
        btn_ingresar.setNextFocusableComponent(btn_salir);
        btn_ingresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ingresarActionPerformed(evt);
            }
        });
        btn_ingresar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btn_ingresarKeyPressed(evt);
            }
        });

        txt_clave_usuario.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        txt_clave_usuario.setNextFocusableComponent(btn_ingresar);
        txt_clave_usuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_clave_usuarioKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_clave_usuarioKeyTyped(evt);
            }
        });

        btn_cambiar_clave.setBackground(new java.awt.Color(237, 237, 237));
        btn_cambiar_clave.setText("¿Olvido su clave?");
        btn_cambiar_clave.setBorder(null);
        btn_cambiar_clave.setContentAreaFilled(false);
        btn_cambiar_clave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cambiar_claveActionPerformed(evt);
            }
        });

        btn_salir.setBackground(new java.awt.Color(230, 230, 230));
        btn_salir.setFont(new java.awt.Font("Tahoma", 1, 15)); // NOI18N
        btn_salir.setText("Salir");
        btn_salir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_salir.setNextFocusableComponent(txt_nombre_usuario);
        btn_salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_salirActionPerformed(evt);
            }
        });
        btn_salir.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btn_salirKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanelLayout = new javax.swing.GroupLayout(jPanel);
        jPanel.setLayout(jPanelLayout);
        jPanelLayout.setHorizontalGroup(
            jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_cambiar_clave, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(93, 93, 93))
            .addGroup(jPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanelLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_nombre_usuario, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                            .addComponent(txt_clave_usuario))
                        .addGap(15, 15, 15))
                    .addGroup(jPanelLayout.createSequentialGroup()
                        .addComponent(btn_ingresar, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_salir, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanelLayout.setVerticalGroup(
            jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_nombre_usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_clave_usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addComponent(btn_cambiar_clave, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_ingresar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_salir, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_ingresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ingresarActionPerformed
        validarCampos();
    }//GEN-LAST:event_btn_ingresarActionPerformed
    
    private void btn_cambiar_claveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cambiar_claveActionPerformed
        cambiarClave();
    }//GEN-LAST:event_btn_cambiar_claveActionPerformed
    
    private void btn_salirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_salirActionPerformed
       if(msj.msg_Pregunta(this, "¿Esta seguro de salir de la aplicaciòn?")==0){
           System.exit(0); 
        }
    }//GEN-LAST:event_btn_salirActionPerformed

    private void txt_nombre_usuarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nombre_usuarioKeyTyped
        char c=evt.getKeyChar(); 
        String var = "'";
        if(txt_nombre_usuario.getText().length()>20){
            evt.consume();
        }else{
            if (Character.toString(c).equals(var)){
                evt.consume();
            }
        }
    }//GEN-LAST:event_txt_nombre_usuarioKeyTyped

    private void txt_clave_usuarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_clave_usuarioKeyTyped
        char c=evt.getKeyChar(); 
        String var = "'";
        if (Character.toString(c).equals(var)){
            evt.consume();
        }
    }//GEN-LAST:event_txt_clave_usuarioKeyTyped

    private void txt_clave_usuarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_clave_usuarioKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            validarCampos();
        }
    }//GEN-LAST:event_txt_clave_usuarioKeyPressed

    private void btn_ingresarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btn_ingresarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            validarCampos();
        }
    }//GEN-LAST:event_btn_ingresarKeyPressed

    private void btn_salirKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btn_salirKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if(msj.msg_Pregunta(this, "¿Desea salir de la aplicación?")==0){
                System.exit(0);
            }
        }
    }//GEN-LAST:event_btn_salirKeyPressed
    
    //Valida boton de cerrar de formulario y Alt+F4
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
        if (msj.msg_Pregunta(this, "¿Está seguro que desea cerrar la aplicación?")==0) {
            System.exit(0);
        }
    }
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
            java.util.logging.Logger.getLogger(FRM_Ingreso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FRM_Ingreso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FRM_Ingreso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FRM_Ingreso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FRM_Ingreso().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_cambiar_clave;
    private javax.swing.JButton btn_ingresar;
    private javax.swing.JButton btn_salir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel;
    private javax.swing.JPasswordField txt_clave_usuario;
    private javax.swing.JTextField txt_nombre_usuario;
    // End of variables declaration//GEN-END:variables
}
