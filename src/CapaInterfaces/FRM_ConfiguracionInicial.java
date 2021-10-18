/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaInterfaces;

import Atxy2k.CustomTextField.RestrictedTextField;
import java.awt.Event;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JFrame;
import javax.swing.KeyStroke;

/**
 *
 * @author Erick
 */

public class FRM_ConfiguracionInicial extends javax.swing.JFrame {
    //Inicializa variables a utilizar
    String nombre_institucion, direccion_institucion, correo_institucion, 
            correo_persona, clave_usuario, claveConfirmar_usuario;
    
    //Inicializa objeto para mensajes de advertencia, confirmacion u pregunta
    static Capa_Mensajes.Mensajes msj = new Capa_Mensajes.Mensajes();
    //Variable para resultado de ejecucion de SQL
    ResultSet rs;
    //Inicializa objeto para realizar las validaciones
    static Capa_Validaciones.Validaciones validar= new Capa_Validaciones.Validaciones();
    
    public FRM_ConfiguracionInicial() {
        initComponents();
        //Llama al metodo que limita el numero de caracteres ingresados por teclado
        limitar_caracteres();
        //Inicia el formulario centrado en pantalla
        setLocationRelativeTo(null);
        //Asigna un icono por defecto para la aplicacion
        setIconImage(new ImageIcon(getClass().getResource("/Icons/logo_2.png")).getImage());
        bloquearTeclas();
        cerrar();
    }
    
    //Bloquea acciones de copiar y pegar del tecladdo
    public void bloquearTeclas(){
        InputMap map1 = txt_nombre_institucion.getInputMap(txt_nombre_institucion.WHEN_FOCUSED);
        map1.put(KeyStroke.getKeyStroke(KeyEvent.VK_V, Event.CTRL_MASK), "null");
        InputMap map2 = txt_correo_institucion.getInputMap(txt_correo_institucion.WHEN_FOCUSED);
        map2.put(KeyStroke.getKeyStroke(KeyEvent.VK_V, Event.CTRL_MASK), "null");
        InputMap map3 = txt_direccion_institucion.getInputMap(txt_direccion_institucion.WHEN_FOCUSED);
        map3.put(KeyStroke.getKeyStroke(KeyEvent.VK_V, Event.CTRL_MASK), "null");
        InputMap map4 = txt_correo_persona.getInputMap(txt_correo_persona.WHEN_FOCUSED);
        map4.put(KeyStroke.getKeyStroke(KeyEvent.VK_V, Event.CTRL_MASK), "null");
        InputMap map5 = txt_clave_usuario.getInputMap(txt_clave_usuario.WHEN_FOCUSED);
        map5.put(KeyStroke.getKeyStroke(KeyEvent.VK_V, Event.CTRL_MASK), "null");
        InputMap map6 = txt_confirmarclave_usuario.getInputMap(txt_confirmarclave_usuario.WHEN_FOCUSED);
        map6.put(KeyStroke.getKeyStroke(KeyEvent.VK_V, Event.CTRL_MASK), "null");
    }
    
    // Limita el tamaño de los campos al escribir
     public void limitar_caracteres(){
        RestrictedTextField limite_nombre = new RestrictedTextField(txt_nombre_institucion);
        limite_nombre.setLimit(150);
        RestrictedTextField limite_correo_institucion = new RestrictedTextField(txt_correo_institucion);
        limite_correo_institucion.setLimit(150);
        RestrictedTextField limite_correo_persona = new RestrictedTextField(txt_correo_persona);
        limite_correo_persona.setLimit(150);
        RestrictedTextField limite_clave = new RestrictedTextField(txt_clave_usuario);
        limite_clave.setLimit(16);
        RestrictedTextField limite_confirme_clave = new RestrictedTextField(txt_confirmarclave_usuario);
        limite_confirme_clave.setLimit(16);
    }
    
    //Recoge los valores de cada campo, eliminando espacios al inicio o final de cada campo
    public void recogerValores() {
        nombre_institucion = txt_nombre_institucion.getText().trim();
        correo_institucion = txt_correo_institucion.getText().trim();
        direccion_institucion = txt_correo_institucion.getText().trim();
        correo_persona = txt_correo_persona.getText().trim();
        clave_usuario = txt_clave_usuario.getText().trim();
        claveConfirmar_usuario = txt_confirmarclave_usuario.getText().trim();
    }
     
    //Metodo que valida que todos los campos ingresados sean correctos
    public void validarCampos(){
        recogerValores();
        if(nombre_institucion.equals("")){
            msj.msg_advertencia(this, "Ingrese el nombre de la institución.");
            txt_nombre_institucion.setText("");
            txt_nombre_institucion.requestFocus();
        }else{
            if(correo_institucion.equals("")){
                msj.msg_advertencia(this, "Ingrese correo de la institución.");
                txt_correo_institucion.setText("");
                txt_correo_institucion.requestFocus();
            }else{
                if(validar.validarEmail(correo_institucion)==false){
                    msj.msg_advertencia(this, "Ingrese correo de institución válido.");
                    txt_correo_institucion.setText("");
                    txt_correo_institucion.requestFocus();
                }else{
                    if(direccion_institucion.equals("")){
                        msj.msg_advertencia(this, "Ingrese dirección de la institución.");
                        txt_direccion_institucion.setText("");
                        txt_direccion_institucion.requestFocus();
                    }else{
                        if(correo_persona.equals("")){
                            msj.msg_advertencia(this, "Ingrese correo de administrador.");
                            txt_correo_persona.setText("");
                            txt_correo_persona.requestFocus();
                        }else{
                            if(validar.validarEmail(correo_persona)==false){
                                msj.msg_advertencia(this, "Ingrese correo de administrador válido.");
                                txt_correo_persona.setText("");
                                txt_correo_persona.requestFocus();
                            }else{
                                if(clave_usuario.equals("") || claveConfirmar_usuario.equals("")){
                                    msj.msg_advertencia(this, "Claves de acceso no pueden estar vacías.");
                                    txt_clave_usuario.setText("");
                                    txt_confirmarclave_usuario.setText("");
                                    txt_clave_usuario.requestFocus();
                                }else{
                                    if(clave_usuario.equals(claveConfirmar_usuario)==false){
                                        msj.msg_advertencia(this, "Claves de acceso no coinciden.");
                                        txt_clave_usuario.setText("");
                                        txt_confirmarclave_usuario.setText("");
                                        txt_clave_usuario.requestFocus();
                                    }else{
                                        if(validar.ComplejidadClave(this, clave_usuario)==false){
                                            txt_clave_usuario.setText("");
                                            txt_confirmarclave_usuario.setText("");
                                            txt_clave_usuario.requestFocus();
                                        }else{
                                            //Si se pasan todas las validaciones, entonces se procede a guardar valores
                                            String sql="update tmaeinscon set nombre_institucion='"+txt_nombre_institucion.getText().trim()+"', direccion_institucion='"+txt_direccion_institucion.getText()+"', correo_institucion='"+txt_correo_institucion.getText().trim()+"', cod_instalacion=1 where id_institucion=1";
                                            Capa_ConexionBD.Conexion.ejecutarSQL(sql);
                                            sql="update tmaepercon set correo_persona='"+txt_correo_persona.getText().trim()+"' where id_persona=2";
                                            Capa_ConexionBD.Conexion.ejecutarSQL(sql);
                                            sql="update tmaeusucon set clave_usuario='"+txt_clave_usuario.getText()+"' where nombre_usuario='admin'";
                                            Capa_ConexionBD.Conexion.ejecutarSQL(sql);
                                            msj.msg_confirmacion(this, "Instalación finalizada con éxito");
                                            //Se cierra formulario y se abre pantalla de ingreso
                                            dispose();
                                            FRM_Ingreso ejecutar = new FRM_Ingreso(); 
                                            ejecutar.setVisible (true);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
        
    //Metodo utilizado para validar el boton cerra y el Alt+F4
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
        if (msj.msg_Pregunta(this, "¿Esta seguro cancelar la configuración?")==0) {
            System.exit(0);
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

        jLabel8 = new javax.swing.JLabel();
        btn_finalizar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txt_nombre_institucion = new javax.swing.JTextField();
        txt_correo_institucion = new javax.swing.JTextField();
        txt_direccion_institucion = new javax.swing.JTextField();
        btn_cancelar = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txt_correo_persona = new javax.swing.JTextField();
        txt_clave_usuario = new javax.swing.JPasswordField();
        txt_confirmarclave_usuario = new javax.swing.JPasswordField();
        jLabel7 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Configuración Inicial");
        setName("configuracion_inicial"); // NOI18N
        setResizable(false);

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel8.setText("Ingrese los datos del administrador");

        btn_finalizar.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        btn_finalizar.setText("Finalizar");
        btn_finalizar.setNextFocusableComponent(btn_cancelar);
        btn_finalizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_finalizarActionPerformed(evt);
            }
        });
        btn_finalizar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btn_finalizarKeyPressed(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(228, 228, 228));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel1.setText("Nombre");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel5.setText("Correo");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel6.setText("Direccion");

        txt_nombre_institucion.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        txt_nombre_institucion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_nombre_institucionKeyTyped(evt);
            }
        });

        txt_correo_institucion.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        txt_correo_institucion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_correo_institucionKeyTyped(evt);
            }
        });

        txt_direccion_institucion.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        txt_direccion_institucion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_direccion_institucionKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(txt_direccion_institucion))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel1))
                        .addGap(29, 29, 29)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_correo_institucion, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_nombre_institucion, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(12, 12, 12))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txt_nombre_institucion, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txt_correo_institucion, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txt_direccion_institucion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btn_cancelar.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        btn_cancelar.setText("Cancelar");
        btn_cancelar.setName(""); // NOI18N
        btn_cancelar.setNextFocusableComponent(txt_nombre_institucion);
        btn_cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cancelarActionPerformed(evt);
            }
        });
        btn_cancelar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                btn_cancelarKeyPressed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel2.setText("Correo:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel3.setText("Clave de acceso:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel4.setText("Confirmar clave:");

        txt_correo_persona.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        txt_correo_persona.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_correo_personaKeyTyped(evt);
            }
        });

        txt_clave_usuario.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        txt_clave_usuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_clave_usuarioKeyTyped(evt);
            }
        });

        txt_confirmarclave_usuario.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        txt_confirmarclave_usuario.setToolTipText("");
        txt_confirmarclave_usuario.setNextFocusableComponent(btn_finalizar);
        txt_confirmarclave_usuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_confirmarclave_usuarioKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_correo_persona, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                    .addComponent(txt_clave_usuario)
                    .addComponent(txt_confirmarclave_usuario))
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txt_correo_persona, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txt_clave_usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txt_confirmarclave_usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel7.setText("Hola, para comenzar ingrese los datos de la institución");

        jPanel1.setBackground(new java.awt.Color(117, 214, 255));

        jLabel9.setFont(new java.awt.Font("Calibri Light", 1, 60)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("actisoft");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel9)
                .addContainerGap(34, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addGap(218, 218, 218))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_cancelar)
                        .addGap(18, 18, 18)
                        .addComponent(btn_finalizar)
                        .addGap(46, 46, 46))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(68, 68, 68)
                                .addComponent(jLabel7))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(127, 127, 127)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(138, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(69, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(jLabel8)
                .addGap(16, 16, 16)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_finalizar)
                    .addComponent(btn_cancelar))
                .addGap(34, 34, 34))
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_finalizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_finalizarActionPerformed
        validarCampos();
    }//GEN-LAST:event_btn_finalizarActionPerformed
            
    private void btn_cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelarActionPerformed
        if(msj.msg_Pregunta(this, "¿Esta seguro que desea cancelar la configuración?")==0){
            System.exit(0);
        }
    }//GEN-LAST:event_btn_cancelarActionPerformed

    private void txt_nombre_institucionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nombre_institucionKeyTyped
        char c=evt.getKeyChar(); 
        String var = "'";
        if(txt_nombre_institucion.getText().length()>150){
            evt.consume();
        }else{
            if (Character.toString(c).equals(var)){
                evt.consume();
            }
        }
    }//GEN-LAST:event_txt_nombre_institucionKeyTyped

    private void txt_correo_institucionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_correo_institucionKeyTyped
        char c=evt.getKeyChar();
        String var = "'";
        char var_1= 'ñ';
        char var_2= 'Ñ';
        if(txt_correo_institucion.getText().length()>150){
            if (Character.toString(c).equals(var) || var_1==c || var_2==c){
                evt.consume();     
            }
        }
    }//GEN-LAST:event_txt_correo_institucionKeyTyped

    private void txt_correo_personaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_correo_personaKeyTyped
        char c=evt.getKeyChar(); 
        String var = "'";
        if(txt_correo_persona.getText().length()>150){
            evt.consume();
        }else{
            if (Character.toString(c).equals(var)){
                evt.consume();     
            }
        }
    }//GEN-LAST:event_txt_correo_personaKeyTyped

    private void txt_clave_usuarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_clave_usuarioKeyTyped
        char c=evt.getKeyChar(); 
        String var = "'";
        if(txt_clave_usuario.getText().length()>16){
            evt.consume();
        }else{
            if (Character.toString(c).equals(var)){
                evt.consume();     
            }
        }
    }//GEN-LAST:event_txt_clave_usuarioKeyTyped

    private void txt_confirmarclave_usuarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_confirmarclave_usuarioKeyTyped
        char c=evt.getKeyChar(); 
        String var = "'";
        char var_1= 'ñ';
        char var_2= 'Ñ';
        if(txt_clave_usuario.getText().length()>16){
            evt.consume();
        }else{
            if (Character.toString(c).equals(var) || var_1==c || var_2==c){
                evt.consume();     
            }
        }
    }//GEN-LAST:event_txt_confirmarclave_usuarioKeyTyped

    private void txt_direccion_institucionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_direccion_institucionKeyTyped
        char c=evt.getKeyChar(); 
        String var = "'";
        if(txt_direccion_institucion.getText().length()>200){
            evt.consume();
        }else{
            if (Character.toString(c).equals(var)){
                evt.consume();
            }
        }
    }//GEN-LAST:event_txt_direccion_institucionKeyTyped

    private void btn_finalizarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btn_finalizarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            validarCampos();
        }
    }//GEN-LAST:event_btn_finalizarKeyPressed

    private void btn_cancelarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btn_cancelarKeyPressed
        if(msj.msg_Pregunta(this, "¿Esta seguro que desea cancelar la configuración?")==0){
            System.exit(0);
        }
    }//GEN-LAST:event_btn_cancelarKeyPressed

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
            java.util.logging.Logger.getLogger(FRM_ConfiguracionInicial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FRM_ConfiguracionInicial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FRM_ConfiguracionInicial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FRM_ConfiguracionInicial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FRM_ConfiguracionInicial().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_cancelar;
    private javax.swing.JButton btn_finalizar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPasswordField txt_clave_usuario;
    private javax.swing.JPasswordField txt_confirmarclave_usuario;
    private javax.swing.JTextField txt_correo_institucion;
    private javax.swing.JTextField txt_correo_persona;
    private javax.swing.JTextField txt_direccion_institucion;
    private javax.swing.JTextField txt_nombre_institucion;
    // End of variables declaration//GEN-END:variables
}
