
package CapaInterfaces;

import static CapaInterfaces.FRM_BusquedaActivos.getcolumnas;
import static CapaInterfaces.FRM_RegistroActivos.txt_serie_activo;
import Capa_ConexionBD.Conexion;
import Objetos.Marcas;
import java.awt.Event;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.SQLException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.InputMap;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Alejandro
 */
public class FRM_RegistroDocumentoAsignacion extends javax.swing.JDialog {

    /**
     * Creates new form RegistroDocumentoAsignacion
     */
    static DefaultComboBoxModel modeloCombo = new DefaultComboBoxModel();
    static DefaultComboBoxModel modeloCombopersona = new DefaultComboBoxModel();
    static DefaultComboBoxModel modeloCombodescripcion = new DefaultComboBoxModel();
    DefaultTableModel modelo = new DefaultTableModel(null, getcolumnas());
    public static int bandera=0;
    static Capa_Mensajes.Mensajes msj = new Capa_Mensajes.Mensajes();
    static String mensaje;
    String sql = null;
    ResultSet resultado;
    public static int id_codoficio;
    int id;
    boolean resultado_actualizaractivo = false;
    public static ArrayList<Integer> id_activoasignar = new ArrayList<Integer>();
    public static ArrayList<Integer> id_activoasignados = new ArrayList<Integer>();
    public static String var_codigooficio ="";
    public FRM_RegistroDocumentoAsignacion(java.awt.Dialog parent, boolean modal, String doc) {
        super(parent, modal);
        initComponents();
        this.getContentPane().setLayout (new GridBagLayout());
        this.setLocationRelativeTo(null);  
        this.setDefaultCloseOperation(0);
        btn_eliminar.setText("<html><p>Eliminar</p><p>de Lista</p></html>");
        combo_idResponsable_area.removeAllItems();
        limpiarTabla();
        if(bandera==1){
            limpiarTabla();
            llenartabla(id_activoasignar);
        }else if(bandera==2){
            try {
                txt_codigoOficio_asigActivo.setText(var_codigooficio);
                txt_codigoOficio_asigActivo.setEnabled(false);
                sql = "select a.id_area AS ID_AREA, a.tipo_area AS TIPO_AREA, (p.nombre_persona || ' ' || p.apellido_persona) AS NOMBRE_COMPLETO from TMAEARECON AS a \n"
                        + "	INNER JOIN TMOVDASCON AS ds ON ds.idarea_asigactivo=a.id_area \n"
                        + "	INNER JOIN TMOVREHCON AS rh ON rh.id_rrhh=a.idresponsable_area \n"
                        + "	INNER JOIN TMAEPERCON AS p ON p.id_persona=rh.idpersona_rrhh\n"
                        + "where codigooficio_asigactivo='" + txt_codigoOficio_asigActivo.getText() + "';";
                resultado = Conexion.ejecutarSQLSelect(sql);
                if(resultado.next()){
                    combo_tipo_area.setSelectedItem(resultado.getString("TIPO_AREA"));
                }
                llenartabla(id_codoficio);
                btn_guardar.setEnabled(false);
                btn_nuevo.setEnabled(false);
                btn_actualizar.setEnabled(true);
            } catch (SQLException ex) {
                Logger.getLogger(FRM_RegistroDocumentoAsignacion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        bloquearTeclas();
    }
    //Bloquea acciones de copiar y pegar del teclado
    public void bloquearTeclas(){
        InputMap map1 = txt_codigoOficio_asigActivo.getInputMap(txt_codigoOficio_asigActivo.WHEN_FOCUSED);
        map1.put(KeyStroke.getKeyStroke(KeyEvent.VK_V, Event.CTRL_MASK), "null");
    }
    
    public void actualizarResponsable(int oficio){
        sql="update TMOVDASCON set idArea_asigActivo="+Marcas.lista_idArea.get(combo_idResponsable_area.getSelectedIndex())+" where id_asigActivo="+oficio;
        if(Conexion.ejecutarSQL(sql)==true){
            msj.msg_confirmacion(this, "Registro actualizado con éxito.");
        }else{
            msj.msg_advertencia(this, "No se ha podido asignar nuevo responsable, inténte de nuevo.");
        }
        
    }
    public void busca_persona(){
        //llena persona
        combo_idResponsable_area.removeAllItems();
         try {
            ArrayList<String> lista_persona;
            lista_persona = Marcas.LeerPersona(combo_tipo_area.getSelectedItem().toString());
             for (int contador = 0; contador < lista_persona.size(); contador++) {
                modeloCombopersona.addElement(lista_persona.get(contador));
            }
             this.combo_idResponsable_area.setModel(modeloCombopersona);
        } catch (SQLException ex) {
            Logger.getLogger(FRM_RegistroDocumentoAsignacion.class.getName()).log(Level.SEVERE, null, ex);
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

        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        combo_tipo_area = new javax.swing.JComboBox();
        combo_idResponsable_area = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txt_codigoOficio_asigActivo = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabla_documentos_asignacion = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        btn_eliminar = new javax.swing.JButton();
        jToolBar1 = new javax.swing.JToolBar();
        btn_nuevo = new javax.swing.JButton();
        btn_guardar = new javax.swing.JButton();
        btn_actualizar = new javax.swing.JButton();
        btn_regresar = new javax.swing.JButton();
        jPanel9 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Registro Documento de Asignación");
        setResizable(false);

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        combo_tipo_area.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        combo_tipo_area.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccionar", "Administración", "Docente", "Laboratorio" }));
        combo_tipo_area.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_tipo_areaActionPerformed(evt);
            }
        });

        combo_idResponsable_area.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        combo_idResponsable_area.setNextFocusableComponent(btn_guardar);
        combo_idResponsable_area.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_idResponsable_areaActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel3.setText("Tipo Área");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel5.setText("Recurso Humano");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(combo_tipo_area, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(combo_idResponsable_area, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combo_tipo_area, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(combo_idResponsable_area, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel5))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        combo_tipo_area.getAccessibleContext().setAccessibleName("cb_Tipo_Area");
        combo_idResponsable_area.getAccessibleContext().setAccessibleName("cb_Recurso_Humano");

        txt_codigoOficio_asigActivo.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        txt_codigoOficio_asigActivo.setNextFocusableComponent(combo_tipo_area);
        txt_codigoOficio_asigActivo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_codigoOficio_asigActivoFocusLost(evt);
            }
        });
        txt_codigoOficio_asigActivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_codigoOficio_asigActivoActionPerformed(evt);
            }
        });
        txt_codigoOficio_asigActivo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_codigoOficio_asigActivoKeyTyped(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel2.setText("Codigo de Oficio");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 21)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Registro de Documentación de Asignación ");

        tabla_documentos_asignacion.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        tabla_documentos_asignacion.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Tipo ", "Marca", "Procesador", "Memoria ", "Disco Duro", "Modelo", "Serie", "Costo", "Fecha Compra", "Cod. Institucional"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabla_documentos_asignacion.removeColumn(tabla_documentos_asignacion.getColumnModel().getColumn(0));
        tabla_documentos_asignacion.setNextFocusableComponent(btn_eliminar);
        tabla_documentos_asignacion.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane3.setViewportView(tabla_documentos_asignacion);
        tabla_documentos_asignacion.getAccessibleContext().setAccessibleName("");

        jPanel8.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btn_eliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/1/rubbish-bin (1).png"))); // NOI18N
        btn_eliminar.setText("Eliminar de Lista");
        btn_eliminar.setContentAreaFilled(false);
        btn_eliminar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_eliminar.setNextFocusableComponent(tabla_documentos_asignacion);
        btn_eliminar.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btn_eliminar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_eliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_eliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_eliminar)
                .addContainerGap(130, Short.MAX_VALUE))
        );

        jToolBar1.setBackground(new java.awt.Color(117, 214, 255));
        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        btn_nuevo.setBackground(new java.awt.Color(117, 214, 255));
        btn_nuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/add-new-file (1).png"))); // NOI18N
        btn_nuevo.setText("   Nuevo   ");
        btn_nuevo.setFocusable(false);
        btn_nuevo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_nuevo.setNextFocusableComponent(txt_codigoOficio_asigActivo);
        btn_nuevo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_nuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nuevoActionPerformed(evt);
            }
        });
        jToolBar1.add(btn_nuevo);

        btn_guardar.setBackground(new java.awt.Color(117, 214, 255));
        btn_guardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/save (1).png"))); // NOI18N
        btn_guardar.setText("   Guardar   ");
        btn_guardar.setFocusable(false);
        btn_guardar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_guardar.setNextFocusableComponent(btn_regresar);
        btn_guardar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_guardarActionPerformed(evt);
            }
        });
        jToolBar1.add(btn_guardar);

        btn_actualizar.setBackground(new java.awt.Color(117, 214, 255));
        btn_actualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/refresh-page-arrow-button (1).png"))); // NOI18N
        btn_actualizar.setText("   Actualizar  ");
        btn_actualizar.setFocusable(false);
        btn_actualizar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_actualizar.setNextFocusableComponent(btn_regresar);
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
        btn_regresar.setNextFocusableComponent(txt_codigoOficio_asigActivo);
        btn_regresar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_regresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_regresarActionPerformed(evt);
            }
        });
        jToolBar1.add(btn_regresar);

        jPanel9.setBackground(new java.awt.Color(68, 69, 69));

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 15, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 1007, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 977, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                            .addGap(0, 0, Short.MAX_VALUE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 880, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(303, 303, 303))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(18, 18, 18)
                                        .addComponent(txt_codigoOficio_asigActivo, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(892, 892, 892)
                                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_codigoOficio_asigActivo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        txt_codigoOficio_asigActivo.getAccessibleContext().setAccessibleName("txt_Codigo_Oficio");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 1018, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_regresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_regresarActionPerformed
        if (bandera == 1) {
            if (msj.msg_Pregunta(this, "Esta seguro que desea cancelar registro de documento de asignacion de responsable?") == 0) {
                id_activoasignar=new ArrayList<Integer>();
                dispose();
                new CapaInterfaces.FRM_AsignacionActivos(null, rootPaneCheckingEnabled).setVisible(true);
            }
        } else if(bandera==2){
             if (msj.msg_Pregunta(this, "Esta seguro que desea cancelar registro de documento de asignacion de responsable?") == 0) {
                dispose();
                new CapaInterfaces.FRM_BusquedaActivoResponsable(null, rootPaneCheckingEnabled).setVisible(true);
            }
        }else{
            if (msj.msg_Pregunta(this, "Esta seguro que desea cancelar registro de documento de asignacion de responsable?") == 0) {
                id_activoasignar=new ArrayList<Integer>();
                dispose();
                new FRM_RegistroActivos(null, rootPaneCheckingEnabled).setVisible(true);
            }
        }
    }//GEN-LAST:event_btn_regresarActionPerformed

    private void txt_codigoOficio_asigActivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_codigoOficio_asigActivoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_codigoOficio_asigActivoActionPerformed

    private void txt_codigoOficio_asigActivoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_codigoOficio_asigActivoFocusLost
      
    }//GEN-LAST:event_txt_codigoOficio_asigActivoFocusLost

    private void btn_guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_guardarActionPerformed
        //Valida si no existe el codigo institucional
        if (Capa_Validaciones.Validaciones.validarcodigo_oficio(this,txt_codigoOficio_asigActivo.getText()) == true  && !combo_tipo_area.getSelectedItem().toString().equals("Seleccionar")) {
            metodo();
            btn_guardar.setEnabled(false);
        }else{
        msj.msg_advertencia(this,"Debe seleccionar un tipo de area");
        combo_tipo_area.requestFocus();
        }
    }//GEN-LAST:event_btn_guardarActionPerformed

    public void metodo(){
        try {
                sql = "select id_asigactivo from tmovdascon where codigooficio_asigactivo='" + txt_codigoOficio_asigActivo.getText()+"'";
                resultado = Capa_ConexionBD.Conexion.ejecutarSQLSelect(sql);
                if (resultado.next() == true) {
                    mensaje = "El oficio de asignacion ya existe.\n¿Desea agregar el activo a este oficio?";
                    id_codoficio=resultado.getInt(1);
                    
                    if (msj.msg_Pregunta(this,mensaje) == 0) {
                        this.btn_guardar.setEnabled(false);
                        this.btn_actualizar.setEnabled(false);
                        this.btn_nuevo.setEnabled(false);
                        sql = "select a.id_area AS ID_AREA, a.tipo_area AS TIPO_AREA, (p.nombre_persona || ' ' || p.apellido_persona) AS NOMBRE_COMPLETO from TMAEARECON AS a \n"
                                + "	INNER JOIN TMOVDASCON AS ds ON ds.idarea_asigactivo=a.id_area \n"
                                + "	INNER JOIN TMOVREHCON AS rh ON rh.id_rrhh=a.idresponsable_area \n"
                                + "	INNER JOIN TMAEPERCON AS p ON p.id_persona=rh.idpersona_rrhh\n"
                                + "where codigooficio_asigactivo='" + txt_codigoOficio_asigActivo.getText() + "';";
                        resultado=Conexion.ejecutarSQLSelect(sql);
                        resultado.next();
                        combo_tipo_area.setSelectedItem(resultado.getString("TIPO_AREA"));
                        combo_idResponsable_area.setSelectedItem(resultado.getString("NOMBRE_COMPLETO"));
                        combo_tipo_area.setEnabled(false);
                        combo_idResponsable_area.setEnabled(false);
                        actualizar_oficio(id_codoficio);
                    }
                } else {
                 crear_oficio();
                }

            } catch (SQLException ex) {
                Logger.getLogger(FRM_RegistroDocumentoAsignacion.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NullPointerException ex) {
                Logger.getLogger(FRM_RegistroDocumentoAsignacion.class.getName()).log(Level.SEVERE, null, ex);

            }
    }
    
    public void crear_oficio() {
        try {
            int id = 0;
            sql = "select * from registrar_tmovdascon('" + txt_codigoOficio_asigActivo.getText() + "'," + Marcas.lista_idArea.get(combo_idResponsable_area.getSelectedIndex()) + ");";
            resultado = Capa_ConexionBD.Conexion.ejecutarSQLSelect(sql);
            resultado.next();
            id_codoficio = resultado.getInt(1);
            if (id_codoficio > 0) {
              id_codoficio = resultado.getInt(1);
                actualizar_oficio(id_codoficio);
            } else {
                mensaje = "¡No se pudo generar el documento de asignacion!";
                msj.msg_advertencia(this,mensaje);
            }
        } catch (NullPointerException ex) {
            System.out.println(ex);
        } catch (SQLException ex) {
            Logger.getLogger(FRM_RegistroDocumentoAsignacion.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public void actualizar_oficio(int id_oficio){
          
                for (int c = 0; c < id_activoasignar.size(); c++) {
                    id = id_activoasignar.get(c);
                    //Actualiza el codigo de oficio del activo 
                    sql = "update TMOVACTCON  set iddocasignacion_activo=" + id_oficio + " where id_activo =" + id;
                    resultado_actualizaractivo = Capa_ConexionBD.Conexion.ejecutarSQL(sql);
                }
                if (resultado_actualizaractivo == true) {
                        mensaje = "¡Activo asignado con èxito!";
                        msj.msg_confirmacion(this,mensaje);
                        limpiarTabla();
                       //Muestra en la tabla todo los activos asignados a ese codigo de oficio
                        llenartabla(id_oficio);
                        btn_eliminar.setEnabled(true);
                        //btn_guardar.setEnabled(rootPaneCheckingEnabled);
                    
                } else {
                    mensaje = "¡No se pudo asignar el activo!";
                    msj.msg_advertencia(this,mensaje);
                }

    }
    //Este metodo limpia la tabla
public void llenartabla(int id_oficio){
    limpiarTabla();
   // id_activoasignados.clear();
    try {
            sql="  select * from buscar_tmovactcon_docasignaccion("+id_oficio +") "
                    + "AS (\"CODIGO ACTIVO\" integer, \"NOMBRE\" varchar, \"MARCA\" varchar, \"PROCESADOR\" varchar, \"MEMORIA\" varchar, \"DISCO DURO\" varchar, \"MODELO\" VARCHAR, \"SERIE\" VARCHAR, \"PRECIO\" DECIMAL, \"FECHA DE COMPRA\" DATE, \"CODIGO INSTITUCIONAL\" VARCHAR, \"DETALLE\" VARCHAR);";
            resultado=Capa_ConexionBD.Conexion.ejecutarSQLSelect(sql);
            int cantidadColumnas =resultado.getMetaData().getColumnCount();
            Object[] fila = new Object[cantidadColumnas];
            while(resultado.next()){
                fila[0] = resultado.getString(2);
                fila[1] = resultado.getString(3);
                fila[2] = resultado.getString(4);
                fila[3] = resultado.getString(5);
                fila[4] = resultado.getString(6);
                fila[5] = resultado.getString(7);
                fila[6] = resultado.getString(8);
                fila[7] = resultado.getDouble(9);
                fila[8] = resultado.getDate(10);
                fila[9] = resultado.getString(11);
                fila[10] = resultado.getString(12);
                id_activoasignados.add(resultado.getInt(1));
                modelo.addRow(fila);
            }
            this.tabla_documentos_asignacion.setModel(modelo);
            this.txt_codigoOficio_asigActivo.setEnabled(false);
        } catch (SQLException ex) {
            Logger.getLogger(FRM_RegistroDocumentoAsignacion.class.getName()).log(Level.SEVERE, null, ex);
        }
}

public void llenartabla(ArrayList idactivos){
    limpiarTabla();
    for(int contador=0;contador<idactivos.size();contador++){
   // id_activoasignados.clear();
    try {
            sql="  select * from buscar_tmovactcon_idactivo("+idactivos.get(contador) +")"
                    + "AS (\"CODIGO ACTIVO\" integer, \"NOMBRE\" varchar, \"MARCA\" varchar, \"PROCESADOR\" varchar, \"MEMORIA\" varchar, \"DISCO DURO\" varchar, \"MODELO\" VARCHAR, \"SERIE\" VARCHAR, \"PRECIO\" DECIMAL, \"FECHA DE COMPRA\" DATE, \"CODIGO INSTITUCIONAL\" VARCHAR, \"DETALLE\" VARCHAR);";
            resultado=Capa_ConexionBD.Conexion.ejecutarSQLSelect(sql);
            int cantidadColumnas =resultado.getMetaData().getColumnCount();
            Object[] fila = new Object[cantidadColumnas];
            while(resultado.next()){
                fila[0] = resultado.getString(2);
                fila[1] = resultado.getString(3);
                fila[2] = resultado.getString(4);
                fila[3] = resultado.getString(5);
                fila[4] = resultado.getString(6);
                fila[5] = resultado.getString(7);
                fila[6] = resultado.getString(8);
                fila[7] = resultado.getDouble(9);
                fila[8] = resultado.getDate(10);
                fila[9] = resultado.getString(11);
                fila[10] = resultado.getString(12);
                id_activoasignados.add(resultado.getInt(1));
                modelo.addRow(fila);
            }
           
        } catch (SQLException ex) {
            Logger.getLogger(FRM_RegistroDocumentoAsignacion.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch(NullPointerException ex){
            
        }
    }
     this.tabla_documentos_asignacion.setModel(modelo);
    
}

    void limpiarTabla() {
        DefaultTableModel temp;
        try {
            temp = (DefaultTableModel) this.tabla_documentos_asignacion.getModel();
            int cont = temp.getRowCount();
            for (int i = 0; i < cont; i++) {
                temp.removeRow(0);
            }
        } catch (Exception e) {

        }
    }
    private void combo_idResponsable_areaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_idResponsable_areaActionPerformed
        try{
         System.out.print(Marcas.lista_idArea.get(combo_idResponsable_area.getSelectedIndex()));
        }catch(Exception ex){
        
        }

    }//GEN-LAST:event_combo_idResponsable_areaActionPerformed

    private void combo_tipo_areaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_tipo_areaActionPerformed
        combo_idResponsable_area.removeAllItems();
        busca_persona();
    }//GEN-LAST:event_combo_tipo_areaActionPerformed

    private void btn_nuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nuevoActionPerformed
       txt_codigoOficio_asigActivo.setText("");
       
       dispose();
       new FRM_BusquedaActivoResponsable(null, rootPaneCheckingEnabled).setVisible(true);
       
    }//GEN-LAST:event_btn_nuevoActionPerformed

    private void btn_actualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_actualizarActionPerformed
        //actualizar_oficio(id_codoficio);
        //Este metodo solo se llama cuando se actualiza al responsable del area.
        if(bandera==2){
            actualizarResponsable(id_codoficio);
        }
    }//GEN-LAST:event_btn_actualizarActionPerformed

    private void txt_codigoOficio_asigActivoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_codigoOficio_asigActivoKeyTyped
        char c=evt.getKeyChar(); 
        char var = '-';
        char var_1 = 'Ñ';
        char var_2 = 'ñ';
        if (txt_codigoOficio_asigActivo.getText().length()>=200 && c!=var_1 && c!=var_2){//Si el dato ingresado pasa del limite no se registrara en el campo
            evt.consume();     
        }else{
            if(Character.isDigit(c) || Character.isLetter(c) || c==var){
               if(Character.isLetter(c)) {//si es una letra
                   evt.setKeyChar(Character.toUpperCase(c));//la registra pero como mayúscula
               }
            }else{
                evt.consume();
            }
       }
    }//GEN-LAST:event_txt_codigoOficio_asigActivoKeyTyped

    private void btn_eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eliminarActionPerformed
        String sql;
        int filaseleccionada = this.tabla_documentos_asignacion.getSelectedRow();
        //Compruba que por lo menos una fila se seleccione 
        if (filaseleccionada < 0) {
            mensaje = "Debe seleccionar una fila para poder editar el activo.\n Seleccione una fila e intente de nuevo.";
            msj.msg_advertencia(this, mensaje);
        } else {
            if (msj.msg_Pregunta(this, "¿Desea desvincular este acctivo de su responsable?") == 0) {
                //Obtiene el codigo del activo de la lista de codigos
                int codigo = id_activoasignados.get(filaseleccionada);
                System.out.println("Codigo a eliminar" + codigo);
                sql = "update tmovactcon set idDocAsignacion_activo=2 where id_activo=" + codigo;
                if (Conexion.ejecutarSQL(sql) == true) {
                    mensaje = "Activo desvicunlado de su responsable con éxito.";
                    msj.msg_confirmacion(this, mensaje);
                    llenartabla(id_codoficio);
                }
                ResultSet rs = null;
                sql = "select count(*) from TMOVACTCON where iddocasignacion_activo=(select id_asigactivo from TMOVDASCON where codigooficio_asigactivo='" + txt_codigoOficio_asigActivo.getText().trim() + "')";
                rs=Conexion.ejecutarSQLSelect(sql);
                try{
                    rs.next();
                    if(rs.getInt("count")==0){
                        sql="delete from TMOVDASCON where codigooficio_asigactivo='"+txt_codigoOficio_asigActivo.getText().trim()+"'";
                        Conexion.ejecutarSQL(sql);
                        msj.msg_confirmacion(this, "Se ha eliminado documento de asignación por no poseer activos asignados.");
                        btn_guardar.setEnabled(false);
                    }
                }catch (SQLException ex){
                
                }
            }
        }
    }//GEN-LAST:event_btn_eliminarActionPerformed

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
            java.util.logging.Logger.getLogger(FRM_RegistroDocumentoAsignacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FRM_RegistroDocumentoAsignacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FRM_RegistroDocumentoAsignacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FRM_RegistroDocumentoAsignacion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FRM_RegistroDocumentoAsignacion dialog = new FRM_RegistroDocumentoAsignacion(new javax.swing.JDialog(), true, "");
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
    public javax.swing.JButton btn_actualizar;
    public javax.swing.JButton btn_eliminar;
    private javax.swing.JButton btn_guardar;
    public static javax.swing.JButton btn_nuevo;
    private javax.swing.JButton btn_regresar;
    private javax.swing.JComboBox combo_idResponsable_area;
    private javax.swing.JComboBox combo_tipo_area;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JToolBar jToolBar1;
    public javax.swing.JTable tabla_documentos_asignacion;
    public static javax.swing.JTextField txt_codigoOficio_asigActivo;
    // End of variables declaration//GEN-END:variables
}
