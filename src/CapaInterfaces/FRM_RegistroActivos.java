/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaInterfaces;

import Capa_ConexionBD.Conexion;
import Capa_Validaciones.Validaciones;
import Objetos.Marcas;
import com.toedter.calendar.JDateChooser;
import java.awt.Event;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.InputMap;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Carmen
 */
public class FRM_RegistroActivos extends javax.swing.JDialog  {

    /**
     * Creates new form RegistroActivos
     */
    public static JDateChooser fecha;
    public static String tipo_activo;
    public static String modelo_activo;
    public static String serie_activo;
    public static String codigo_institucional;
    public static String detalle;
    public static String marca_activo;
    public static String memoria = null;
    public static String discoduro = null;
    public static String procesador = null;
    public static java.sql.Date fecha_compra;
    public static double costo;
    public static int id_tipo_activo;
    public static int id_activo;
    String mensaje;
    public static int filadebusqueda;
    static DefaultComboBoxModel modeloCombo = new DefaultComboBoxModel();
    Validaciones validaciones = new Validaciones();

    static ResultSet rs = null;
    static Capa_Mensajes.Mensajes msj = new Capa_Mensajes.Mensajes();

    public FRM_RegistroActivos(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.getContentPane().setLayout(new GridBagLayout());
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(0);
        txt_fecha_compra.setMaxSelectableDate(new java.util.Date());
        txt_fecha_compra.getDateEditor().setEnabled(false);
        inicializar();
        bloquearTeclas();
        //System.out.println("Trae el activo:"+id_activo);
        //Si el id_activo es 0 se va a realizar un nuevo ingreso y se inicializa todo
        if (id_activo == 0) {
            inicializar();
        } //Si el id del activo no es 0 se va a actualizar un activo y trae todos los datos de la tabla de busqueda
        else {
            try {
                this.combo_tipo_activo.removeAllItems();
                ArrayList<String> lista_tipo = Marcas.LeerTipo();
                for (int contador = 0; contador < lista_tipo.size(); contador++) {
                    modeloCombo.addElement(lista_tipo.get(contador));
                }
                this.combo_tipo_activo.setModel(modeloCombo);

            } catch (SQLException ex) {
                Logger.getLogger(FRM_RegistroActivos.class.getName()).log(Level.SEVERE, null, ex);
            }

            //Trae las lista de las memorias, procesadors, discosduros y limpia los combos
            String[] memorias = Marcas.LeerMemorias();
            combo_memoria_activo.removeAllItems();

            String[] procesadores = Marcas.LeerProcesadores();
            combo_procesador_activo.removeAllItems();

            String[] discosduros = Marcas.LeerDiscos();
            combo_discoduro_activo.removeAllItems();

            //Llena el combo de memorias
            for (String memoria_activo : memorias) {
                combo_memoria_activo.addItem(memoria_activo);
            }

            //Llena el combo de procesador
            for (String procesador_act : procesadores) {
                combo_procesador_activo.addItem(procesador_act);
            }

            //Llena el combo de discos duro
            for (String discos_act : discosduros) {
                combo_discoduro_activo.addItem(discos_act);
            }
            //Trae la fecha de la tabla
            String fecha = FRM_BusquedaActivos.tabla_activo.getValueAt(filadebusqueda, 8).toString();

            //Habilita el boton actualizar y deshabilita los de nuevo y guardar.
            btn_actualizar.setEnabled(true);
            btn_nuevo.setEnabled(false);
            btn_guardar.setEnabled(false);
            btn_buscar.setEnabled(false);

            //Llena campos con los datos de la tabla
            java.sql.Date fecha_compra_editar = null;
            java.sql.Date fecha_c = fecha_compra_editar.valueOf(fecha);
            this.combo_tipo_activo.setSelectedItem(FRM_BusquedaActivos.tabla_activo.getValueAt(filadebusqueda, 0).toString());
            combo_marca_activo.setSelectedItem(FRM_BusquedaActivos.tabla_activo.getValueAt(filadebusqueda, 1).toString());
            combo_procesador_activo.setSelectedItem(FRM_BusquedaActivos.tabla_activo.getValueAt(filadebusqueda, 2).toString());
            combo_memoria_activo.setSelectedItem(FRM_BusquedaActivos.tabla_activo.getValueAt(filadebusqueda, 3).toString());
            combo_discoduro_activo.setSelectedItem(FRM_BusquedaActivos.tabla_activo.getValueAt(filadebusqueda, 4).toString());
            txt_modelo_activo.setText(FRM_BusquedaActivos.tabla_activo.getValueAt(filadebusqueda, 5).toString());
            txt_serie_activo.setText(FRM_BusquedaActivos.tabla_activo.getValueAt(filadebusqueda, 6).toString());
            sp_costo_activo.setValue(FRM_BusquedaActivos.tabla_activo.getValueAt(filadebusqueda, 7));
            txt_fecha_compra.setDate(fecha_c);
            txt_codigo_institucional.setText(FRM_BusquedaActivos.tabla_activo.getValueAt(filadebusqueda, 9).toString());
            txt_detalle_activo.setText(FRM_BusquedaActivos.tabla_activo.getValueAt(filadebusqueda, 10).toString());

        }

    }

    public void bloquearTeclas() {
        InputMap map1 = txt_modelo_activo.getInputMap(txt_modelo_activo.WHEN_FOCUSED);
        map1.put(KeyStroke.getKeyStroke(KeyEvent.VK_V, Event.CTRL_MASK), "null");
        InputMap map2 = txt_serie_activo.getInputMap(txt_serie_activo.WHEN_FOCUSED);
        map2.put(KeyStroke.getKeyStroke(KeyEvent.VK_V, Event.CTRL_MASK), "null");
        InputMap map3 = txt_fecha_compra.getInputMap(txt_fecha_compra.WHEN_FOCUSED);
        map3.put(KeyStroke.getKeyStroke(KeyEvent.VK_V, Event.CTRL_MASK), "null");
        InputMap map4 = sp_costo_activo.getInputMap(sp_costo_activo.WHEN_FOCUSED);
        map4.put(KeyStroke.getKeyStroke(KeyEvent.VK_V, Event.CTRL_MASK), "null");
        InputMap map5 = txt_codigo_institucional.getInputMap(txt_codigo_institucional.WHEN_FOCUSED);
        map5.put(KeyStroke.getKeyStroke(KeyEvent.VK_V, Event.CTRL_MASK), "null");
        InputMap map6 = txt_detalle_activo.getInputMap(txt_detalle_activo.WHEN_FOCUSED);
        map6.put(KeyStroke.getKeyStroke(KeyEvent.VK_V, Event.CTRL_MASK), "null");
    }
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDesktopPane1 = new javax.swing.JDesktopPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        combo_tipo_activo = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        combo_marca_activo = new javax.swing.JComboBox();
        jPanel3 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        combo_procesador_activo = new javax.swing.JComboBox();
        combo_memoria_activo = new javax.swing.JComboBox();
        combo_discoduro_activo = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        txt_modelo_activo = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        sp_costo_activo = new javax.swing.JSpinner();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txt_detalle_activo = new javax.swing.JTextArea();
        txt_codigo_institucional = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txt_serie_activo = new javax.swing.JTextField();
        txt_fecha_compra = new com.toedter.calendar.JDateChooser();
        jToolBar1 = new javax.swing.JToolBar();
        btn_nuevo = new javax.swing.JButton();
        btn_guardar = new javax.swing.JButton();
        btn_buscar = new javax.swing.JButton();
        btn_actualizar = new javax.swing.JButton();
        btn_regresar = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Registro de Activos");
        setResizable(false);

        jDesktopPane1.setBackground(new java.awt.Color(240, 240, 240));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 21)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Registro de Activos ");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel2.setText("Tipo");

        combo_tipo_activo.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        combo_tipo_activo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccionar...", "tem 1", "Item 2", "Item 3", "Item 4" }));
        combo_tipo_activo.setNextFocusableComponent(combo_marca_activo);
        combo_tipo_activo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_tipo_activoActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel3.setText("Marca");

        combo_marca_activo.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        combo_marca_activo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccionar...", "Item 2", "Item 3", "Item 4" }));
        combo_marca_activo.setNextFocusableComponent(txt_modelo_activo);

        jPanel3.setBackground(new java.awt.Color(247, 247, 247));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(51, 51, 51));
        jLabel10.setText("Procesador");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(51, 51, 51));
        jLabel11.setText("Memoria ");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(51, 51, 51));
        jLabel12.setText("Disco Duro");

        combo_procesador_activo.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        combo_procesador_activo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccionar...", "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_procesador_activo.setNextFocusableComponent(combo_memoria_activo);

        combo_memoria_activo.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        combo_memoria_activo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccionar...", "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_memoria_activo.setNextFocusableComponent(combo_discoduro_activo);

        combo_discoduro_activo.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        combo_discoduro_activo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccionar...", "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_discoduro_activo.setNextFocusableComponent(txt_modelo_activo);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addComponent(jLabel11)
                    .addComponent(jLabel10))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(combo_procesador_activo, javax.swing.GroupLayout.Alignment.LEADING, 0, 219, Short.MAX_VALUE)
                    .addComponent(combo_memoria_activo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(combo_discoduro_activo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(26, 26, 26))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(combo_procesador_activo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(combo_memoria_activo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(combo_discoduro_activo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addGap(35, 35, 35))
        );

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel4.setText("Modelo");

        txt_modelo_activo.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        txt_modelo_activo.setNextFocusableComponent(txt_serie_activo);
        txt_modelo_activo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_modelo_activoKeyTyped(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel5.setText("Costo");

        sp_costo_activo.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        sp_costo_activo.setModel(new javax.swing.SpinnerNumberModel(0.5d, 0.5d, 9999999.99d, 1.0d));
        sp_costo_activo.setToolTipText("");
        sp_costo_activo.setNextFocusableComponent(txt_fecha_compra);

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel6.setText("Código Institucional");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel7.setText("Detalle");

        txt_detalle_activo.setColumns(20);
        txt_detalle_activo.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        txt_detalle_activo.setRows(5);
        txt_detalle_activo.setNextFocusableComponent(btn_guardar);
        txt_detalle_activo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_detalle_activoKeyTyped(evt);
            }
        });
        jScrollPane2.setViewportView(txt_detalle_activo);

        txt_codigo_institucional.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        txt_codigo_institucional.setNextFocusableComponent(txt_detalle_activo);
        txt_codigo_institucional.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_codigo_institucionalFocusLost(evt);
            }
        });
        txt_codigo_institucional.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_codigo_institucionalActionPerformed(evt);
            }
        });
        txt_codigo_institucional.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_codigo_institucionalKeyTyped(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel9.setText("Fecha de Compra");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel8.setText("Serie");

        txt_serie_activo.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        txt_serie_activo.setNextFocusableComponent(sp_costo_activo);
        txt_serie_activo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_serie_activoKeyTyped(evt);
            }
        });

        txt_fecha_compra.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        txt_fecha_compra.setMaxSelectableDate(new java.util.Date(253370786519000L));
        txt_fecha_compra.setMinSelectableDate(new java.util.Date(315554519000L));
        txt_fecha_compra.setNextFocusableComponent(txt_codigo_institucional);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(combo_marca_activo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(31, 31, 31)
                        .addComponent(combo_tipo_activo, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 117, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txt_codigo_institucional, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(31, 31, 31)
                        .addComponent(sp_costo_activo, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_fecha_compra, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel8))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_serie_activo)
                            .addComponent(txt_modelo_activo, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(combo_tipo_activo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(combo_marca_activo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_modelo_activo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_serie_activo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel9)
                                    .addComponent(sp_costo_activo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addComponent(jLabel5))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_fecha_compra, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(23, 23, 23)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_codigo_institucional, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        jToolBar1.setBackground(new java.awt.Color(117, 214, 255));
        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        btn_nuevo.setBackground(new java.awt.Color(117, 214, 255));
        btn_nuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/add-new-file (1).png"))); // NOI18N
        btn_nuevo.setText("   Nuevo    ");
        btn_nuevo.setFocusable(false);
        btn_nuevo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_nuevo.setNextFocusableComponent(combo_tipo_activo);
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
        btn_guardar.setNextFocusableComponent(btn_nuevo);
        btn_guardar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_guardarActionPerformed(evt);
            }
        });
        jToolBar1.add(btn_guardar);

        btn_buscar.setBackground(new java.awt.Color(117, 214, 255));
        btn_buscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/magnifier-tool (1).png"))); // NOI18N
        btn_buscar.setText("   Buscar   ");
        btn_buscar.setFocusable(false);
        btn_buscar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_buscar.setNextFocusableComponent(btn_nuevo);
        btn_buscar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_buscarActionPerformed(evt);
            }
        });
        jToolBar1.add(btn_buscar);

        btn_actualizar.setBackground(new java.awt.Color(117, 214, 255));
        btn_actualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/refresh-page-arrow-button (1).png"))); // NOI18N
        btn_actualizar.setText("   Actualizar   ");
        btn_actualizar.setFocusable(false);
        btn_actualizar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_actualizar.setNextFocusableComponent(btn_nuevo);
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
        btn_regresar.setNextFocusableComponent(combo_tipo_activo);
        btn_regresar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_regresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_regresarActionPerformed(evt);
            }
        });
        jToolBar1.add(btn_regresar);

        jPanel6.setBackground(new java.awt.Color(68, 69, 69));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 15, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jDesktopPane1.setLayer(jPanel2, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jToolBar1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jPanel6, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jDesktopPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void combo_tipo_activoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_tipo_activoActionPerformed

        try {
            tipo_activo = combo_tipo_activo.getSelectedItem().toString();
        } catch (NullPointerException e) {
            System.out.println(e);
        }

        String[] marca_activos = Marcas.LeerMarcas(tipo_activo);
        combo_marca_activo.removeAllItems();

        //Llena el combo de marcas segun el tipo de activo
        for (String marca_activ : marca_activos) {
            combo_marca_activo.addItem(marca_activ);
        }

        //Habilita los combo de procesador, disco duro y memoria solo si se selecciona estos items
        try {
            if (tipo_activo.equals("Computadora de escritorio") || tipo_activo.equals("CPU") || tipo_activo.equals("Portátil")) {
                combo_discoduro_activo.enable();
                combo_memoria_activo.enable();
                combo_procesador_activo.enable();
            } else {
                combo_discoduro_activo.disable();
                combo_memoria_activo.disable();
                combo_procesador_activo.disable();
            }
        } catch (NullPointerException e) {
            System.err.println(e);
        }

    }//GEN-LAST:event_combo_tipo_activoActionPerformed

    private void btn_buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_buscarActionPerformed
        //Llama al formulario de busqueda
        if (combo_tipo_activo.getSelectedItem().toString().equals("Seleccionar") == true && combo_marca_activo.getSelectedItem().toString().equals("Seleccionar") == true && combo_procesador_activo.getSelectedItem().toString().equals("Seleccionar") == true
                && combo_memoria_activo.getSelectedItem().toString().equals("Seleccionar") == true && combo_discoduro_activo.getSelectedItem().toString().equals("Seleccionar") == true && txt_modelo_activo.getText().equals("") == true
                && txt_serie_activo.getText().toString().equals("") == true && txt_fecha_compra.getCalendar() == null && txt_codigo_institucional.getText().equals("") == true && txt_detalle_activo.getText().equals("") == true) {
            dispose();
            new FRM_BusquedaActivos(this, true).setVisible(true);
        } else {
            mensaje = "Los datos que no ha guardado se borraran.\n¿Esta seguro de buscar un registro?";
            if (msj.msg_Pregunta(this, mensaje) == 0) {
                dispose();
                new FRM_BusquedaActivos(this, true).setVisible(true);
            }
        }
    }//GEN-LAST:event_btn_buscarActionPerformed

    private void btn_regresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_regresarActionPerformed
        if(msj.msg_Pregunta(this, "¿Esta seguro que desea cancelar el registro de activos?")==0){
            dispose();
        }
    }//GEN-LAST:event_btn_regresarActionPerformed

    private void btn_nuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nuevoActionPerformed
        if (combo_tipo_activo.getSelectedItem().toString().equals("Seleccionar") == true && combo_marca_activo.getSelectedItem().toString().equals("Seleccionar") == true && combo_procesador_activo.getSelectedItem().toString().equals("Seleccionar") == true
                && combo_memoria_activo.getSelectedItem().toString().equals("Seleccionar") == true && combo_discoduro_activo.getSelectedItem().toString().equals("Seleccionar") == true && txt_modelo_activo.getText().equals("") == true
                && txt_serie_activo.getText().toString().equals("") == true && txt_fecha_compra.getCalendar() == null && txt_codigo_institucional.getText().equals("") == true && txt_detalle_activo.getText().equals("") == true) {
            id_activo = 0;
            txt_codigo_institucional.setText("");
            txt_detalle_activo.setText("");
            txt_modelo_activo.setText("");
            txt_serie_activo.setText("");
            sp_costo_activo.setValue(0.50);
            txt_fecha_compra.setCalendar(null);
            combo_memoria_activo.removeAllItems();
            combo_procesador_activo.removeAllItems();
            combo_discoduro_activo.removeAllItems();
            inicializar();
        } else {
            mensaje = "Los datos que no ha guardado se borraran.\n¿Esta seguro de crear un nuevo registro?";

            if (msj.msg_Pregunta(this, mensaje) == 0) {
                id_activo = 0;
                txt_codigo_institucional.setText("");
                txt_detalle_activo.setText("");
                txt_modelo_activo.setText("");
                txt_serie_activo.setText("");
                sp_costo_activo.setValue(0.50);
                txt_fecha_compra.setCalendar(null);
                combo_memoria_activo.removeAllItems();
                combo_procesador_activo.removeAllItems();
                combo_discoduro_activo.removeAllItems();
                inicializar();
            }
        }
    }//GEN-LAST:event_btn_nuevoActionPerformed

    private void btn_actualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_actualizarActionPerformed
        //Llama la funcion de guardar valores indicando que se va a actualizar un activo
        if(validaciones.validarcampos(this)==true){
            guardarvalores("actualizar");
        }
    }//GEN-LAST:event_btn_actualizarActionPerformed

    private void btn_guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_guardarActionPerformed
        fecha=txt_fecha_compra;
        if(validaciones.validarcampos(this)==true){
            guardarvalores("insertar");
        }
    }//GEN-LAST:event_btn_guardarActionPerformed

    private void txt_codigo_institucionalFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_codigo_institucionalFocusLost
        // Validar si existe el codigo institucional
        rs = Conexion.ejecutarSQLSelect("select * from TMOVACTCON where codigoInternoInstitucional_activo='" + FRM_RegistroActivos.txt_codigo_institucional.getText() + "'");
        try {
            if (rs.next() == true) {
                mensaje = "Codigo institucional ya existe, ingrese nuevamente o actualice el existente.";
                msj.msg_advertencia(this, mensaje);
                txt_codigo_institucional.setText("");
                txt_codigo_institucional.requestFocus();
            }
        } catch (SQLException ex) {
            Logger.getLogger(FRM_RegistroActivos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_txt_codigo_institucionalFocusLost

    private void txt_modelo_activoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_modelo_activoKeyTyped
        char c=evt.getKeyChar(); 
        String var0 = "'";
        char var2 = 'ñ';
        char var3 = 'Ñ';
        if(txt_detalle_activo.getText().length()>200){
            evt.consume();
        }else{
            if (Character.isLetter(c) || Character.isDigit(c) && Character.toString(c).equals(var0)==false){
                if(Character.isLetter(c)){
                    evt.setKeyChar(Character.toUpperCase(c));
                }
            }else{
                evt.consume();
            }
        }
    }//GEN-LAST:event_txt_modelo_activoKeyTyped

    private void txt_detalle_activoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_detalle_activoKeyTyped
        char c=evt.getKeyChar(); 
        String var = "'";
        if(txt_detalle_activo.getText().length()>500){
            evt.consume();
        }else{
            if (Character.toString(c).equals(var)){
                evt.consume();
            }
        }
    }//GEN-LAST:event_txt_detalle_activoKeyTyped

    private void txt_codigo_institucionalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_codigo_institucionalKeyTyped
        char c=evt.getKeyChar(); 
        String var0 = "'";
        char var1 = '-';
        char var2 = 'ñ';
        char var3 = 'Ñ';
        if(txt_detalle_activo.getText().length()>200){
            evt.consume();
        }else{
            if (Character.isLetter(c) || Character.isDigit(c) || c==var1 && Character.toString(c).equals(var0)==false){
                if(Character.isLetter(c)){
                    evt.setKeyChar(Character.toUpperCase(c));
                }
            }else{
                evt.consume();
            }
        }
    }//GEN-LAST:event_txt_codigo_institucionalKeyTyped

    private void txt_serie_activoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_serie_activoKeyTyped
        char c=evt.getKeyChar(); 
        String var0 = "'";
        char var2 = 'ñ';
        char var3 = 'Ñ';
        if(txt_detalle_activo.getText().length()>200){
            evt.consume();
        }else{
            if (Character.isLetter(c) || Character.isDigit(c) && Character.toString(c).equals(var0)==false){
                if(Character.isLetter(c)){
                    evt.setKeyChar(Character.toUpperCase(c));
                }
            }else{
                evt.consume();
            }
        }
    }//GEN-LAST:event_txt_serie_activoKeyTyped

    private void txt_codigo_institucionalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_codigo_institucionalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_codigo_institucionalActionPerformed

    public void guardarvalores(String tipo_sentencia) {
        serie_activo = txt_serie_activo.getText();
        modelo_activo = txt_modelo_activo.getText();
        codigo_institucional = txt_codigo_institucional.getText();
        detalle = txt_detalle_activo.getText();
        marca_activo = combo_marca_activo.getSelectedItem().toString();
        int tipo = combo_tipo_activo.getSelectedIndex();
        id_tipo_activo = tipo + 2;
        java.sql.Date fecha_actual = new java.sql.Date(new java.util.Date().getTime());

        //Valida todo y ejecuta los query
        //Valida que se seleccione fecha
        try {
            fecha_compra = new java.sql.Date(txt_fecha_compra.getDate().getTime());
            String tipo_act = combo_tipo_activo.getSelectedItem().toString();
            costo = Double.parseDouble(sp_costo_activo.getValue().toString());
            boolean resultado = false;
            ResultSet resultado_ingreso = null;
            int id_activo_nuevo = 0;
            try {
                String sql;
                //Comprobacion para enviar o no los datos de memoria, procesador y disco duro
                if ((tipo_act.equals("Computadora de escritorio") || tipo_act.equals("CPU") || tipo_act.equals("Portátil"))) {
                    memoria = combo_memoria_activo.getSelectedItem().toString();
                    procesador = combo_procesador_activo.getSelectedItem().toString();
                    discoduro = combo_discoduro_activo.getSelectedItem().toString();
                } else {
                    memoria = "";
                    procesador = "";
                    discoduro = "";
                }
                //Comprueba si se va a guardar un nuevo registro o si se va a actualizar el registro existente
                if (tipo_sentencia.equals("insertar")) {
                    sql = "select * from registro_TMOVACTCON('" + tipo_act + "','" + marca_activo + "','" + procesador + "','" + memoria + "','" + discoduro + "','" + modelo_activo + "','" + serie_activo + "'," + costo + ",'" + fecha_compra + "','" + codigo_institucional + "','" + detalle + "') ";
                    resultado_ingreso = Capa_ConexionBD.Conexion.ejecutarSQLSelect(sql);
                    resultado_ingreso.next();
                    id_activo_nuevo = resultado_ingreso.getInt(1);
                    //Comprueba si se ingreso el registro y pregunta si desea asignarle el rrhh
                    if (id_activo_nuevo > 0) {
                        mensaje = "¡DATOS GUARDADOS CON EXITO!.\n¿Desea asignar un responsable a este activo?";
                        if (msj.msg_Pregunta(this, mensaje) == 0) {
                            Object[][] data = {{tipo_act, marca_activo, procesador, memoria, discoduro, modelo_activo, serie_activo, costo, fecha_compra, codigo_institucional, detalle}};
                            FRM_RegistroDocumentoAsignacion form_asignacion = new FRM_RegistroDocumentoAsignacion(this, true, "");
                            DefaultTableModel modelotabla = new DefaultTableModel(data, FRM_BusquedaActivos.getcolumnas());
                            form_asignacion.id_activoasignar.add(id_activo_nuevo);
                            form_asignacion.tabla_documentos_asignacion.setModel(modelotabla);
                            form_asignacion.btn_actualizar.setEnabled(false);
                            form_asignacion.btn_eliminar.setEnabled(false);
                            form_asignacion.btn_nuevo.setEnabled(false);
                            FRM_RegistroDocumentoAsignacion.bandera=3;
                            dispose();
                            form_asignacion.setVisible(true);
                        }
                        id_activo = 0;
                        txt_codigo_institucional.setText("");
                        txt_detalle_activo.setText("");
                        txt_modelo_activo.setText("");
                        txt_serie_activo.setText("");
                        sp_costo_activo.setValue(0.50);
                        txt_fecha_compra.setCalendar(null);
                        combo_memoria_activo.removeAllItems();
                        combo_procesador_activo.removeAllItems();
                        combo_discoduro_activo.removeAllItems();
                        inicializar();
                    } else {
                        mensaje = "¡NO SE PUDO REGISTRAR EL ACTIVO!";
                        msj.msg_confirmacion(this, mensaje);
                    }

                } else if (tipo_sentencia.equals("actualizar")) {
                    sql = "select * from actualizacion_tmovactcon(" + id_activo + ",'" + tipo_act + "','" + marca_activo + "','" + procesador + "','" + memoria + "','" + discoduro + "','" + modelo_activo + "','" + serie_activo + "'," + costo + ",'" + fecha_compra + "','" + codigo_institucional + "','" + detalle + "') ";
                    resultado = Conexion.ejecutarSQL(sql);

                    //Comprueba si se actualizo el registro
                    if (resultado == true) {
                        sql = "select iddocasignacion_activo from tmovactcon where id_activo =" + id_activo;
                        resultado_ingreso = Conexion.ejecutarSQLSelect(sql);
                        resultado_ingreso.next();

                        //Comprueba si el activo actualizado tiene o no asignado un rrhh y si no tiene pregunta para asignarlo
                        if (resultado_ingreso.getInt(1) == 1) {
                            mensaje = "¡DATOS ACTUALIZADOS CON EXITO!.\n¿Desea asignar un responsable a este activo?";
                            if (msj.msg_Pregunta(this, mensaje) == 0) {
                                Object[][] data = {{tipo_act, marca_activo, procesador, memoria, discoduro, modelo_activo, serie_activo, costo, fecha_compra, codigo_institucional, detalle}};
                                FRM_RegistroDocumentoAsignacion form_asignacion = new FRM_RegistroDocumentoAsignacion(this, true, "");
                                DefaultTableModel modelotabla = new DefaultTableModel(data, FRM_BusquedaActivos.getcolumnas());
                                form_asignacion.id_activoasignar.add(id_activo_nuevo);
                                form_asignacion.tabla_documentos_asignacion.setModel(modelotabla);
                                form_asignacion.btn_actualizar.setEnabled(false);
                                form_asignacion.btn_eliminar.setEnabled(false);
                                dispose();
                                form_asignacion.setVisible(true);
                            }

                        } //Caso contrario solo lo actualiza
                        else {
                            mensaje = "¡DATOS ACTUALIZADO CON EXITO!";
                            msj.msg_confirmacion(this, mensaje);
                            btn_nuevo.setEnabled(false);
                        }
                    }
                }

            } catch (SQLException ex) {
                System.out.println(ex);
            } catch (NullPointerException ex) {
                System.out.println(ex);
            }
        } catch (NullPointerException e) {
            mensaje = "Debe seleccionar una fecha.";
            msj.msg_advertencia(this, mensaje);
        }
    }

    //Este metodo inicializa todos los valores al momento de crear un nuevo registro
    public static void inicializar() {
        btn_actualizar.setEnabled(false);
        try {
            combo_tipo_activo.removeAllItems();
            ArrayList<String> lista_tipo = Marcas.LeerTipo();
            for (int contador = 0; contador < lista_tipo.size(); contador++) {
                modeloCombo.addElement(lista_tipo.get(contador));
            }
            combo_tipo_activo.setModel(modeloCombo);

        } catch (SQLException ex) {
            Logger.getLogger(FRM_RegistroActivos.class.getName()).log(Level.SEVERE, null, ex);
        }

        //combo_tipo_activo.setSelectedIndex(0);
        String[] memorias = Marcas.LeerMemorias();
        combo_memoria_activo.removeAllItems();

        String[] procesadores = Marcas.LeerProcesadores();
        combo_procesador_activo.removeAllItems();

        String[] discosduros = Marcas.LeerDiscos();
        combo_discoduro_activo.removeAllItems();

        //Llena el combo de memorias
        for (String memoria_activo : memorias) {
            combo_memoria_activo.addItem(memoria_activo);
        }

        //Llena el combo de procesador
        for (String procesador_act : procesadores) {
            combo_procesador_activo.addItem(procesador_act);
        }

        //Llena el combo de discos duro
        for (String discos_act : discosduros) {
            combo_discoduro_activo.addItem(discos_act);
        }
        combo_procesador_activo.disable();
        combo_memoria_activo.disable();
        combo_discoduro_activo.disable();
        try {
            tipo_activo = combo_tipo_activo.getSelectedItem().toString();
        } catch (NullPointerException e) {
            System.err.println(e);
        }
        String[] discosduro_act = Marcas.LeerMarcas(tipo_activo);
        combo_marca_activo.removeAllItems();

        //Llena el combo de marcas segun el tipo de activo
        for (String marca_act : discosduro_act) {
            combo_marca_activo.addItem(marca_act);

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
            java.util.logging.Logger.getLogger(FRM_RegistroActivos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FRM_RegistroActivos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FRM_RegistroActivos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FRM_RegistroActivos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FRM_RegistroActivos dialog = new FRM_RegistroActivos(new javax.swing.JFrame(), true);
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
    public static javax.swing.JButton btn_actualizar;
    private javax.swing.JButton btn_buscar;
    private javax.swing.JButton btn_guardar;
    private javax.swing.JButton btn_nuevo;
    private javax.swing.JButton btn_regresar;
    public static javax.swing.JComboBox combo_discoduro_activo;
    public static javax.swing.JComboBox combo_marca_activo;
    public static javax.swing.JComboBox combo_memoria_activo;
    public static javax.swing.JComboBox combo_procesador_activo;
    public static javax.swing.JComboBox combo_tipo_activo;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JToolBar jToolBar1;
    public static javax.swing.JSpinner sp_costo_activo;
    public static javax.swing.JTextField txt_codigo_institucional;
    public static javax.swing.JTextArea txt_detalle_activo;
    public static com.toedter.calendar.JDateChooser txt_fecha_compra;
    public static javax.swing.JTextField txt_modelo_activo;
    public static javax.swing.JTextField txt_serie_activo;
    // End of variables declaration//GEN-END:variables
}
