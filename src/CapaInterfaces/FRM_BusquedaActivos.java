
package CapaInterfaces;


import static CapaInterfaces.FRM_RegistroActivos.modeloCombo;
import static CapaInterfaces.FRM_RegistroActivos.txt_detalle_activo;
import Capa_ConexionBD.Conexion;
import static Capa_Validaciones.Validaciones.validarmayusculasynumeros;
import Objetos.Marcas;
import java.awt.Event;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.InputMap;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Alejandro
 */
public class FRM_BusquedaActivos extends javax.swing.JDialog {
    /**
     * Creates new form BusquedaActivo
     */
    ResultSet resultadodebusqueda;
    ResultSetMetaData resultadoMd;
    DefaultTableModel modelo = new DefaultTableModel(null, getcolumnas());
    ArrayList<Integer> id_activos = new ArrayList<Integer>();
    ArrayList<String> lista_tipo = new ArrayList<String>();
    String mensaje;
    static Capa_Mensajes.Mensajes msj = new Capa_Mensajes.Mensajes();

    public FRM_BusquedaActivos(java.awt.Dialog parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.getContentPane().setLayout(new GridBagLayout());
        this.setLocationRelativeTo(null);
        FRM_RegistroActivos formregistro = new FRM_RegistroActivos(null, modal);
        this.setDefaultCloseOperation(0);
        formregistro.dispose();
        bloquearTeclas();
        try {
            this.combo_tipo_activo.removeAll();
            lista_tipo = Marcas.LeerTipo();
            this.combo_tipo_activo.setModel(modeloCombo);
        } catch (SQLException ex) {
            Logger.getLogger(FRM_RegistroActivos.class.getName()).log(Level.SEVERE, null, ex);
        }

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
        desactivar_tipo();
        String tipo_activo = combo_tipo_activo.getSelectedItem().toString();

        String[] marcas = Marcas.LeerMarcas(tipo_activo);
        combo_marca_activo.removeAllItems();

        //Llena el combo de marcas segun el tipo de activo
        for (String marca_act : marcas) {
            combo_marca_activo.addItem(marca_act);

        }
        this.tabla_activo.setModel(modelo);
    }

    public void bloquearTeclas() {
        InputMap map1 = txt_codigoInternoInsticucional_activo.getInputMap(txt_codigoInternoInsticucional_activo.WHEN_FOCUSED);
        map1.put(KeyStroke.getKeyStroke(KeyEvent.VK_V, Event.CTRL_MASK), "null");
        InputMap map2 = txt_serie_activo.getInputMap(txt_serie_activo.WHEN_FOCUSED);
        map2.put(KeyStroke.getKeyStroke(KeyEvent.VK_V, Event.CTRL_MASK), "null");
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jTextField3 = new javax.swing.JTextField();
        grupo_tipo = new javax.swing.ButtonGroup();
        grupo_busqueda = new javax.swing.ButtonGroup();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        jToolBar1 = new javax.swing.JToolBar();
        btn_nuevo = new javax.swing.JButton();
        btn_imprimir = new javax.swing.JButton();
        btn_regresar = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txt_codigoInternoInsticucional_activo = new javax.swing.JTextField();
        rbtn_codigoinstitucional_activo = new javax.swing.JRadioButton();
        txt_serie_activo = new javax.swing.JTextField();
        rbtn_serie_activo = new javax.swing.JRadioButton();
        combo_estado_activo = new javax.swing.JComboBox();
        rbtn_estado_activo = new javax.swing.JRadioButton();
        combo_tipo_activo = new javax.swing.JComboBox();
        rbtn_tipo = new javax.swing.JRadioButton();
        jPanel3 = new javax.swing.JPanel();
        rbtn_marca_activo = new javax.swing.JRadioButton();
        rbtn_procesador_activo = new javax.swing.JRadioButton();
        rbtn_memoria_activo = new javax.swing.JRadioButton();
        rbtn_discoduro_activo = new javax.swing.JRadioButton();
        combo_marca_activo = new javax.swing.JComboBox();
        combo_procesador_activo = new javax.swing.JComboBox();
        combo_memoria_activo = new javax.swing.JComboBox();
        combo_discoduro_activo = new javax.swing.JComboBox();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabla_activo = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        btn_modificar = new javax.swing.JButton();
        btn_restaurar = new javax.swing.JButton();
        btn_darBaja = new javax.swing.JButton();
        btn_eliminar = new javax.swing.JButton();
        btn_buscar = new javax.swing.JButton();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jTextField3.setText("jTextField3");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Búsqueda de Activos ");
        setResizable(false);

        jDesktopPane1.setBackground(new java.awt.Color(232, 232, 232));

        jToolBar1.setBackground(new java.awt.Color(117, 214, 255));
        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);
        jToolBar1.setPreferredSize(new java.awt.Dimension(100, 80));

        btn_nuevo.setBackground(new java.awt.Color(117, 214, 255));
        btn_nuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/add-new-file (1).png"))); // NOI18N
        btn_nuevo.setText("   Nuevo   ");
        btn_nuevo.setContentAreaFilled(false);
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

        btn_imprimir.setBackground(new java.awt.Color(117, 214, 255));
        btn_imprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/printer- (1).png"))); // NOI18N
        btn_imprimir.setText("   Imprimir   ");
        btn_imprimir.setContentAreaFilled(false);
        btn_imprimir.setFocusable(false);
        btn_imprimir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_imprimir.setNextFocusableComponent(btn_nuevo);
        btn_imprimir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_imprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_imprimirActionPerformed(evt);
            }
        });
        jToolBar1.add(btn_imprimir);

        btn_regresar.setBackground(new java.awt.Color(117, 214, 255));
        btn_regresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/logout (1).png"))); // NOI18N
        btn_regresar.setText("   Regresar");
        btn_regresar.setContentAreaFilled(false);
        btn_regresar.setFocusable(false);
        btn_regresar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_regresar.setNextFocusableComponent(btn_nuevo);
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

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 21)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Búsqueda de Activos ");

        txt_codigoInternoInsticucional_activo.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        txt_codigoInternoInsticucional_activo.setNextFocusableComponent(btn_buscar);
        txt_codigoInternoInsticucional_activo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_codigoInternoInsticucional_activoKeyTyped(evt);
            }
        });

        rbtn_codigoinstitucional_activo.setBackground(new java.awt.Color(232, 232, 232));
        grupo_busqueda.add(rbtn_codigoinstitucional_activo);
        rbtn_codigoinstitucional_activo.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        rbtn_codigoinstitucional_activo.setText("Código Institucional");
        rbtn_codigoinstitucional_activo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtn_codigoinstitucional_activoActionPerformed(evt);
            }
        });

        txt_serie_activo.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        txt_serie_activo.setNextFocusableComponent(btn_buscar);
        txt_serie_activo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_serie_activoKeyTyped(evt);
            }
        });

        rbtn_serie_activo.setBackground(new java.awt.Color(232, 232, 232));
        grupo_busqueda.add(rbtn_serie_activo);
        rbtn_serie_activo.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        rbtn_serie_activo.setText("Serie");
        rbtn_serie_activo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtn_serie_activoActionPerformed(evt);
            }
        });

        combo_estado_activo.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        combo_estado_activo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Activo", "Inactivo", "Dado de baja" }));
        combo_estado_activo.setNextFocusableComponent(btn_buscar);
        combo_estado_activo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_estado_activoActionPerformed(evt);
            }
        });

        rbtn_estado_activo.setBackground(new java.awt.Color(232, 232, 232));
        grupo_busqueda.add(rbtn_estado_activo);
        rbtn_estado_activo.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        rbtn_estado_activo.setText("Estado");
        rbtn_estado_activo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtn_estado_activoActionPerformed(evt);
            }
        });

        combo_tipo_activo.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        combo_tipo_activo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccionar...", "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_tipo_activo.setNextFocusableComponent(combo_marca_activo);
        combo_tipo_activo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_tipo_activoActionPerformed(evt);
            }
        });

        rbtn_tipo.setBackground(new java.awt.Color(232, 232, 232));
        grupo_busqueda.add(rbtn_tipo);
        rbtn_tipo.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        rbtn_tipo.setText("Tipo");
        rbtn_tipo.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                rbtn_tipoStateChanged(evt);
            }
        });
        rbtn_tipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtn_tipoActionPerformed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        grupo_tipo.add(rbtn_marca_activo);
        rbtn_marca_activo.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        rbtn_marca_activo.setText("Marca");
        rbtn_marca_activo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtn_marca_activoActionPerformed(evt);
            }
        });

        grupo_tipo.add(rbtn_procesador_activo);
        rbtn_procesador_activo.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        rbtn_procesador_activo.setText("Procesador");
        rbtn_procesador_activo.setName(""); // NOI18N
        rbtn_procesador_activo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtn_procesador_activoActionPerformed(evt);
            }
        });

        grupo_tipo.add(rbtn_memoria_activo);
        rbtn_memoria_activo.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        rbtn_memoria_activo.setText("Memoria");
        rbtn_memoria_activo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtn_memoria_activoActionPerformed(evt);
            }
        });

        grupo_tipo.add(rbtn_discoduro_activo);
        rbtn_discoduro_activo.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        rbtn_discoduro_activo.setText("Disco Duro");
        rbtn_discoduro_activo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtn_discoduro_activoActionPerformed(evt);
            }
        });

        combo_marca_activo.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        combo_marca_activo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccionar...", "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_marca_activo.setNextFocusableComponent(btn_buscar);

        combo_procesador_activo.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        combo_procesador_activo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccionar...", "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_procesador_activo.setNextFocusableComponent(btn_buscar);

        combo_memoria_activo.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        combo_memoria_activo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccionar...", "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_memoria_activo.setNextFocusableComponent(btn_buscar);

        combo_discoduro_activo.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        combo_discoduro_activo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccionar...", "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_discoduro_activo.setNextFocusableComponent(btn_buscar);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(rbtn_marca_activo)
                        .addGap(18, 18, 18)
                        .addComponent(combo_marca_activo, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(rbtn_memoria_activo)
                            .addGap(36, 36, 36)
                            .addComponent(combo_memoria_activo, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(rbtn_procesador_activo)
                            .addGap(18, 18, 18)
                            .addComponent(combo_procesador_activo, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(rbtn_discoduro_activo)
                            .addGap(18, 18, 18)
                            .addComponent(combo_discoduro_activo, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(43, 43, 43))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbtn_marca_activo)
                    .addComponent(combo_marca_activo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbtn_procesador_activo)
                    .addComponent(combo_procesador_activo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbtn_memoria_activo)
                    .addComponent(combo_memoria_activo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbtn_discoduro_activo)
                    .addComponent(combo_discoduro_activo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabla_activo.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        tabla_activo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Tipo ", "Marca", "Procesador", "Memoria ", "Disco Duro", "Modelo", "Serie", "Costo", "Fecha Compra", "Cod. Institucional"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Double.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabla_activo.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tabla_activo.setNextFocusableComponent(btn_modificar);
        tabla_activo.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane3.setViewportView(tabla_activo);

        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btn_modificar.setBackground(new java.awt.Color(255, 255, 255));
        btn_modificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/1/pencil.png"))); // NOI18N
        btn_modificar.setText("Editar");
        btn_modificar.setContentAreaFilled(false);
        btn_modificar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btn_modificar.setNextFocusableComponent(btn_restaurar);
        btn_modificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_modificarActionPerformed(evt);
            }
        });

        btn_restaurar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/1/undo_file.png"))); // NOI18N
        btn_restaurar.setText("Restaurar");
        btn_restaurar.setToolTipText("");
        btn_restaurar.setContentAreaFilled(false);
        btn_restaurar.setNextFocusableComponent(btn_darBaja);
        btn_restaurar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_restaurarActionPerformed(evt);
            }
        });

        btn_darBaja.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/1/file.png"))); // NOI18N
        btn_darBaja.setText("Dar Baja");
        btn_darBaja.setToolTipText("");
        btn_darBaja.setContentAreaFilled(false);
        btn_darBaja.setNextFocusableComponent(btn_eliminar);
        btn_darBaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_darBajaActionPerformed(evt);
            }
        });

        btn_eliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/1/rubbish-bin (1).png"))); // NOI18N
        btn_eliminar.setText("Eliminar");
        btn_eliminar.setContentAreaFilled(false);
        btn_eliminar.setNextFocusableComponent(tabla_activo);
        btn_eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_eliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_modificar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_restaurar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_eliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_darBaja, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_modificar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_restaurar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btn_darBaja, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_eliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btn_buscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icon_buscar(1).png"))); // NOI18N
        btn_buscar.setBorder(null);
        btn_buscar.setContentAreaFilled(false);
        btn_buscar.setNextFocusableComponent(btn_nuevo);
        btn_buscar.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icon_buscar(1).png"))); // NOI18N
        btn_buscar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icon_buscar.png"))); // NOI18N
        btn_buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_buscarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jDesktopPane1Layout.createSequentialGroup()
                        .addComponent(jScrollPane3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jDesktopPane1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 974, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jDesktopPane1Layout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                                .addComponent(rbtn_tipo)
                                .addGap(18, 18, 18)
                                .addComponent(combo_tipo_activo, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                                .addComponent(rbtn_estado_activo)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(combo_estado_activo, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                                .addComponent(rbtn_serie_activo)
                                .addGap(18, 18, 18)
                                .addComponent(txt_serie_activo, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                                .addComponent(rbtn_codigoinstitucional_activo)
                                .addGap(18, 18, 18)
                                .addComponent(txt_codigoInternoInsticucional_activo, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                                .addGap(77, 77, 77)
                                .addComponent(btn_buscar)))
                        .addGap(43, 43, 43)))
                .addContainerGap())
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDesktopPane1Layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jDesktopPane1Layout.createSequentialGroup()
                        .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rbtn_tipo)
                            .addComponent(combo_tipo_activo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jDesktopPane1Layout.createSequentialGroup()
                        .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rbtn_estado_activo)
                            .addComponent(combo_estado_activo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rbtn_serie_activo)
                            .addComponent(txt_serie_activo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rbtn_codigoinstitucional_activo)
                            .addComponent(txt_codigoInternoInsticucional_activo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28)
                        .addComponent(btn_buscar)))
                .addGap(18, 18, 18)
                .addGroup(jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jDesktopPane1.setLayer(jToolBar1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jPanel6, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jLabel1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(txt_codigoInternoInsticucional_activo, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(rbtn_codigoinstitucional_activo, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(txt_serie_activo, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(rbtn_serie_activo, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(combo_estado_activo, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(rbtn_estado_activo, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(combo_tipo_activo, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(rbtn_tipo, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jPanel3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jScrollPane3, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(jPanel5, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jDesktopPane1.setLayer(btn_buscar, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jDesktopPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_nuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nuevoActionPerformed
        // Limpiar todo
        grupo_busqueda.clearSelection();
        grupo_tipo.clearSelection();
        combo_tipo_activo.setSelectedIndex(0);
        limpiarTabla();
    }//GEN-LAST:event_btn_nuevoActionPerformed

    private void btn_regresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_regresarActionPerformed
        dispose();
        FRM_RegistroActivos form = new FRM_RegistroActivos(null, rootPaneCheckingEnabled);
        form.setVisible(true);
    }//GEN-LAST:event_btn_regresarActionPerformed

    private void rbtn_codigoinstitucional_activoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtn_codigoinstitucional_activoActionPerformed
        //Desactiva todos los radiobutton que usa tipo
        if (rbtn_codigoinstitucional_activo.isSelected() == true) {
            desactivar_tipo();

        }
    }//GEN-LAST:event_rbtn_codigoinstitucional_activoActionPerformed

    private void rbtn_tipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtn_tipoActionPerformed
        //Activatodos los radiobutton que usa tipo
        String tipo_activo = combo_tipo_activo.getSelectedItem().toString();
        if (rbtn_tipo.isSelected() == true) {
            rbtn_marca_activo.setEnabled(true);
            combo_marca_activo.setEnabled(false);
            combo_tipo_activo.setEnabled(true);
        }
        //Activa los combo y radiobutton de memoria, discoduro y procesador solo si el tipo los requiere     
        if ((tipo_activo.equals("Computadora de escritorio") || tipo_activo.equals("CPU") || tipo_activo.equals("Portátil"))) {
            combo_discoduro_activo.setEnabled(false);
            combo_memoria_activo.setEnabled(false);
            combo_procesador_activo.setEnabled(false);
            rbtn_discoduro_activo.setEnabled(true);
            rbtn_memoria_activo.setEnabled(true);
            rbtn_procesador_activo.setEnabled(true);
        } else {
            combo_discoduro_activo.setEnabled(false);
            combo_memoria_activo.setEnabled(false);
            combo_procesador_activo.setEnabled(false);
            rbtn_discoduro_activo.setEnabled(false);
            rbtn_memoria_activo.setEnabled(false);
            rbtn_procesador_activo.setEnabled(false);
        }

    }//GEN-LAST:event_rbtn_tipoActionPerformed

    private void rbtn_marca_activoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtn_marca_activoActionPerformed
        if (rbtn_marca_activo.isSelected() == true) {
            combo_marca_activo.setEnabled(true);
            combo_discoduro_activo.setEnabled(false);
            combo_memoria_activo.setEnabled(false);
            combo_procesador_activo.setEnabled(false);
        } else {
            combo_marca_activo.setEnabled(false);
        }
    }//GEN-LAST:event_rbtn_marca_activoActionPerformed

    private void btn_darBajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_darBajaActionPerformed
        if (msj.msg_Pregunta(this, "¿Está seguro que desea dar de baja al activo?, \n Esto significaría no poder asignarle un responsable u enviarlos a mantenimiento.") == 0) {
            // Trae el numero de la fila seleccionada de la tabla
            int filaseleccionada = this.tabla_activo.getSelectedRow();
            //Compruba que por lo menos una fila se seleccione 
            if (filaseleccionada < 0) {
                mensaje = "Debe seleccionar una fila para poder editar el activo.\n Seleccione una fila e intente de nuevo.";
                msj.msg_advertencia(this, mensaje);
            } else {

                //Obtiene el codigo del activo de la lista de codigos
                int codigo = id_activos.get(filaseleccionada);
                System.out.println("Slecciona la fila:" + filaseleccionada);
                System.out.println("Va a dar de baja el activo:" + codigo);
                String sql = "select * from dardebaja_tmovactcon(" + codigo + ")";
                id_activos.remove(filaseleccionada);
                ResultSet rs = Conexion.ejecutarSQLSelect(sql);
                try {
                while (rs.next()) {                 
                    if(rs.getString("dardebaja_tmovactcon").equals("true")){
                        mensaje = "El activo fue dado de baja con èxito.!";
                        msj.msg_confirmacion(this, mensaje);
                        actualizartabla();
                    }else{
                        msj.msg_advertencia(this, "Este activo está asignado a: " + rs.getString("dardebaja_tmovactcon")+"\nPara eliminar u dar de baja, debe de desvincularlo de su responsable.");
                        actualizartabla();
                    }
                }
                } catch (Exception ex) {
                    System.out.println("llenar activos");
                }
            }
        }
    }//GEN-LAST:event_btn_darBajaActionPerformed

    private void btn_modificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_modificarActionPerformed

        // Trae el numero de la fila seleccionada de la tabla
        int filaseleccionada = this.tabla_activo.getSelectedRow();
        //Compruba que por lo menos una fila se seleccione 
        if (filaseleccionada < 0) {
            mensaje = "Debe seleccionar una fila para poder editar el activo.\n Seleccione una fila e intente de nuevo.";
            msj.msg_advertencia(this, mensaje);
        } else {
            //Cierra este formulario
            setVisible(false);
            //Obtiene el codigo del activo de la lista de codigos
            int codigo = id_activos.get(filaseleccionada);
            //Asigna el codigo a la variable id_activo del formulario RegistrActivos para poder actualizarlo
            FRM_RegistroActivos.id_activo = codigo;
            //Envia la fila que se selecciono para que el formulario de registro capture todos los datos
            FRM_RegistroActivos.filadebusqueda = filaseleccionada;
            //Llama al formulario RegistroActivos
            FRM_RegistroActivos actualizarregistro = new FRM_RegistroActivos(null, rootPaneCheckingEnabled);
            actualizarregistro.setVisible(true);

        }
    }//GEN-LAST:event_btn_modificarActionPerformed

    private void combo_tipo_activoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_tipo_activoActionPerformed

        try {
            String tipo_activo = combo_tipo_activo.getSelectedItem().toString();
            String[] marca_activos = Marcas.LeerMarcas(tipo_activo);
            combo_marca_activo.removeAllItems();

            //Llena el combo de marcas segun el tipo de activo
            for (String marca_activ : marca_activos) {
                combo_marca_activo.addItem(marca_activ);
            }

            //Habilita los combo de procesador, disco duro y memoria solo si se selecciona estos items
            if ((tipo_activo.equals("Computadora de escritorio") || tipo_activo.equals("CPU") || tipo_activo.equals("Portátil"))) {
                combo_discoduro_activo.setEnabled(false);
                combo_memoria_activo.setEnabled(false);
                combo_procesador_activo.setEnabled(false);
                rbtn_discoduro_activo.setEnabled(true);
                rbtn_memoria_activo.setEnabled(true);
                rbtn_procesador_activo.setEnabled(true);
            } else {
                combo_discoduro_activo.setEnabled(false);
                combo_memoria_activo.setEnabled(false);
                combo_procesador_activo.setEnabled(false);
                rbtn_discoduro_activo.setEnabled(false);
                rbtn_memoria_activo.setEnabled(false);
                rbtn_procesador_activo.setEnabled(false);
            }
        } catch (NullPointerException e) {
            System.err.println(e);
        }
    }//GEN-LAST:event_combo_tipo_activoActionPerformed

    private void rbtn_estado_activoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtn_estado_activoActionPerformed
        //Desactiva todos los radiobutton que usa tipo
        if (rbtn_estado_activo.isSelected() == true) {
            desactivar_tipo();

        }
    }//GEN-LAST:event_rbtn_estado_activoActionPerformed

    private void rbtn_serie_activoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtn_serie_activoActionPerformed
        //Desactiva todos los radiobutton que usa tipo
        if (rbtn_serie_activo.isSelected() == true) {
            desactivar_tipo();

        }
    }//GEN-LAST:event_rbtn_serie_activoActionPerformed

    private void btn_buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_buscarActionPerformed
     actualizartabla();
    }//GEN-LAST:event_btn_buscarActionPerformed

    public void actualizartabla(){
           btn_restaurar.setEnabled(false);
        id_activos.clear();
        String sql = null;
        limpiarTabla();
        try {
            //Verifica si la opcion de busqueda es por tipo
            if (rbtn_tipo.isSelected()) {
                //verifica si es una busqueda por multiples parametros 
                if (rbtn_marca_activo.isSelected()) {
                    sql = "SELECT * FROM BUSCAR_TMOVACTCON_TIPOACTIVO_Y_MARCA ('" + combo_tipo_activo.getSelectedItem().toString() + "','" + combo_marca_activo.getSelectedItem().toString() + "') AS (\"CODIGO ACTIVO\" integer, \"NOMBRE\" varchar, \"MARCA\" varchar, \"PROCESADOR\" varchar, \"MEMORIA\" varchar, \"DISCO DURO\" varchar, \"MODELO\" VARCHAR, \"SERIE\" VARCHAR, \"PRECIO\" DECIMAL, \"FECHA DE COMPRA\" DATE, \"CODIGO INSTITUCIONAL\" VARCHAR, \"DETALLE\" VARCHAR);";
                } else if (rbtn_memoria_activo.isSelected()) {
                    sql = "SELECT * FROM BUSCAR_TMOVACTCON_TIPOACTIVO_Y_MEMORIA('" + combo_tipo_activo.getSelectedItem().toString() + "','" + combo_memoria_activo.getSelectedItem().toString() + "') AS (\"CODIGO ACTIVO\" integer, \"NOMBRE\" varchar, \"MARCA\" varchar, \"PROCESADOR\" varchar, \"MEMORIA\" varchar, \"DISCO DURO\" varchar, \"MODELO\" VARCHAR, \"SERIE\" VARCHAR, \"PRECIO\" DECIMAL, \"FECHA DE COMPRA\" DATE, \"CODIGO INSTITUCIONAL\" VARCHAR, \"DETALLE\" VARCHAR);";
                } else if (rbtn_procesador_activo.isSelected()) {
                    sql = "SELECT * FROM BUSCAR_TMOVACTCON_TIPOACTIVO_Y_PROCESADOR('" + combo_tipo_activo.getSelectedItem().toString() + "','" + combo_procesador_activo.getSelectedItem().toString() + "') AS (\"CODIGO ACTIVO\" integer, \"NOMBRE\" varchar, \"MARCA\" varchar, \"PROCESADOR\" varchar, \"MEMORIA\" varchar, \"DISCO DURO\" varchar, \"MODELO\" VARCHAR, \"SERIE\" VARCHAR, \"PRECIO\" DECIMAL, \"FECHA DE COMPRA\" DATE, \"CODIGO INSTITUCIONAL\" VARCHAR, \"DETALLE\" VARCHAR);";
                } else if (rbtn_discoduro_activo.isSelected()) {
                    sql = "SELECT * FROM BUSCAR_TMOVACTCON_TIPOACTIVO_Y_DISCODURO ('" + combo_tipo_activo.getSelectedItem().toString() + "','" + combo_discoduro_activo.getSelectedItem().toString() + "') AS (\"CODIGO ACTIVO\" integer, \"NOMBRE\" varchar, \"MARCA\" varchar, \"PROCESADOR\" varchar, \"MEMORIA\" varchar, \"DISCO DURO\" varchar, \"MODELO\" VARCHAR, \"SERIE\" VARCHAR, \"PRECIO\" DECIMAL, \"FECHA DE COMPRA\" DATE, \"CODIGO INSTITUCIONAL\" VARCHAR, \"DETALLE\" VARCHAR);";
                } else {
                    sql = "SELECT * FROM BUSCAR_TMOVACTCON_TIPOACTIVO ('" + combo_tipo_activo.getSelectedItem().toString() + "') AS (\"CODIGO ACTIVO\" integer, \"NOMBRE\" varchar, \"MARCA\" varchar, \"PROCESADOR\" varchar, \"MEMORIA\" varchar, \"DISCO DURO\" varchar, \"MODELO\" VARCHAR, \"SERIE\" VARCHAR, \"PRECIO\" DECIMAL, \"FECHA DE COMPRA\" DATE, \"CODIGO INSTITUCIONAL\" VARCHAR, \"DETALLE\" VARCHAR);";
                }
            } //Verifica si la opcion de busqueda es por estado
            else if (rbtn_estado_activo.isSelected()) {
                sql = "SELECT * FROM buscar_tmovactcon_estadactivo(" + combo_estado_activo.getSelectedIndex() + ")AS (\"CODIGO ACTIVO\" integer, \"NOMBRE\" varchar, \"MARCA\" varchar, \"PROCESADOR\" varchar, \"MEMORIA\" varchar, \"DISCO DURO\" varchar, \"MODELO\" VARCHAR, \"SERIE\" VARCHAR, \"PRECIO\" DECIMAL, \"FECHA DE COMPRA\" DATE, \"CODIGO INSTITUCIONAL\" VARCHAR, \"DETALLE\" VARCHAR);";
                    if(combo_estado_activo.getSelectedItem().equals("Activo")){
                        btn_restaurar.setEnabled(false);
                        btn_darBaja.setEnabled(true);
                        btn_eliminar.setEnabled(true);
                    }else if(combo_estado_activo.getSelectedItem().equals("Dado de baja")){
                        btn_restaurar.setEnabled(true);
                        btn_darBaja.setEnabled(false);
                        btn_eliminar.setEnabled(true);
                    }else{
                        btn_restaurar.setEnabled(false);
                        btn_darBaja.setEnabled(false);
                        btn_eliminar.setEnabled(false);
                    }
            } //Verifica si la opcion de busqueda es por serie
            else if (rbtn_serie_activo.isSelected()) {
                //Llama al metodo para validar la serie y devuelve el sql para ejecutar el query
                sql = validarserie();
            } //Verifica si la opcion de busqueda es por codigo institucional
            else if (rbtn_codigoinstitucional_activo.isSelected()) {
                //Verifica que el txt_codigoInternoInstitucional no este vacio
                if (txt_codigoInternoInsticucional_activo.getText().isEmpty()) {
                    mensaje = "Debe ingresar el codigo institucional a buscar.\n Ingrese un dato e intente su busqueda de nuevo.";
                    msj.msg_advertencia(this, mensaje);
                    txt_codigoInternoInsticucional_activo.requestFocus();
                }
                sql = "SELECT * FROM buscar_tmovactcon_institucionalactivo('" + txt_codigoInternoInsticucional_activo.getText() + "')AS (\"CODIGO ACTIVO\" integer, \"NOMBRE\" varchar, \"MARCA\" varchar, \"PROCESADOR\" varchar, \"MEMORIA\" varchar, \"DISCO DURO\" varchar, \"MODELO\" VARCHAR, \"SERIE\" VARCHAR, \"PRECIO\" DECIMAL, \"FECHA DE COMPRA\" DATE, \"CODIGO INSTITUCIONAL\" VARCHAR, \"DETALLE\" VARCHAR);";
            } //Si no se selecciona criterio de busqueda muetrs todos los activos
            else {
                mensaje = "No ha seleccionado criterio de busqueda.\n Se mostraran todos los activos esto puede tradar unos segundos .\n¿Desea continuar?";
                int resp = msj.msg_Pregunta(this, mensaje);
                if (resp == 0) {
                    sql = "select * from buscar_tmovactcon_todos()AS (\"CODIGO ACTIVO\" integer, \"NOMBRE\" varchar, \"MARCA\" varchar, \"PROCESADOR\" varchar, \"MEMORIA\" varchar, \"DISCO DURO\" varchar, \"MODELO\" VARCHAR, \"SERIE\" VARCHAR, \"PRECIO\" DECIMAL, \"FECHA DE COMPRA\" DATE, \"CODIGO INSTITUCIONAL\" VARCHAR, \"DETALLE\" VARCHAR);";
                }
            }
            //Ejecuta el query guarda en el resulset resultadodebusqueda
            resultadodebusqueda = Conexion.ejecutarSQLSelect(sql);
            //Obtienela metadata del resulset
            resultadoMd = resultadodebusqueda.getMetaData();
            //Cuenta las columnas de la consulta
            int cantidadColumnas = resultadoMd.getColumnCount();
            //Verifica si la busqueda tiene algun valor
            if (!resultadodebusqueda.next()) {
                mensaje = "No existen datos que coincidan con este criterio de busqueda.\n Intente su busqueda con otro criterio.";
                msj.msg_advertencia(this, mensaje);
            } else {
                //Vuelve el resultset a su estado original ya que para verificar si la busqueda tiene algun valor se salta los registros
                resultadodebusqueda.beforeFirst();
                //Llena la tabla con todos los valores
                while (resultadodebusqueda.next()) {
                    Object[] fila = new Object[cantidadColumnas];
                    fila[0] = resultadodebusqueda.getString(2);
                    fila[1] = resultadodebusqueda.getString(3);
                    fila[2] = resultadodebusqueda.getString(4);
                    fila[3] = resultadodebusqueda.getString(5);
                    fila[4] = resultadodebusqueda.getString(6);
                    fila[5] = resultadodebusqueda.getString(7);
                    fila[6] = resultadodebusqueda.getString(8);
                    fila[7] = resultadodebusqueda.getDouble(9);
                    fila[8] = resultadodebusqueda.getDate(10);
                    fila[9] = resultadodebusqueda.getString(11);
                    fila[10] = resultadodebusqueda.getString(12);
                    //Guarda los codigos de los activos en una lista
                    id_activos.add(resultadodebusqueda.getInt(1));
                    //Añade la fila a la tabla
                    modelo.addRow(fila);

                }
            }
            //Añade todo el modelo a la tabla
            this.tabla_activo.setModel(modelo);
        } catch (SQLException ex) {
            Logger.getLogger(FRM_BusquedaActivos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullPointerException e) {
            System.out.println("Error: " + e);
        }


    }
    
    
    private void rbtn_tipoStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_rbtn_tipoStateChanged
        // Al seleccionar tipo deselecciona los radiobutton de abajo
        grupo_tipo.clearSelection();
    }//GEN-LAST:event_rbtn_tipoStateChanged

    private void btn_imprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_imprimirActionPerformed
        //Valida si hay datos que exportar
        if (this.tabla_activo.getRowCount() == 0) {
            mensaje = "No hay datos para generar el reporte.\n Intente con otra busqueda e intente de nuevo.";
            msj.msg_advertencia(this, mensaje);
        } else {
            verificar_tipo_busqueda();          
        }
    }//GEN-LAST:event_btn_imprimirActionPerformed
public void verificar_tipo_busqueda(){
        //Ésta clase permite validar los campos escogidos en la interfaz
        //para poder agregar los datos al documento pdf(Encabezado y nombre delpdf)
        String tipo_activo, marca_activo, memoria_activo, procesador_activo,discoduro_activo, estado_activo, codigo_institucional, serie_activo;
    if(rbtn_estado_activo.isSelected()){
        estado_activo=combo_estado_activo.getSelectedItem().toString();
        Reportes.Reportes.PDF(this, " Por Estado del Activo - "+estado_activo);
    } else{
        if(rbtn_codigoinstitucional_activo.isSelected()){
            codigo_institucional=txt_codigoInternoInsticucional_activo.getText();
            Reportes.Reportes.PDF(this, "Por Código Institucional "+ codigo_institucional );
        }else{
            if(rbtn_serie_activo.isSelected()){
                serie_activo=txt_serie_activo.getText() ;
            Reportes.Reportes.PDF(this, "Por Serie del Activo "+ serie_activo);
        }else{
             if(rbtn_tipo.isSelected() && rbtn_marca_activo.isSelected()){
                 tipo_activo=combo_tipo_activo.getSelectedItem().toString();
                 marca_activo=combo_marca_activo.getSelectedItem().toString();               
                 Reportes.Reportes.PDF(this, "Por Tipo de Activo "+tipo_activo+" y Marca de Activo " +marca_activo);
        }else{
            if(rbtn_tipo.isSelected() && rbtn_procesador_activo.isSelected()){
                 tipo_activo=combo_tipo_activo.getSelectedItem().toString();
                 procesador_activo=combo_procesador_activo.getSelectedItem().toString();               
                 Reportes.Reportes.PDF(this, "Por Tipo de Activo "+tipo_activo+" y Procesador de Activo " +procesador_activo);
        }else{
            if(rbtn_tipo.isSelected() && rbtn_discoduro_activo.isSelected()){
                 tipo_activo=combo_tipo_activo.getSelectedItem().toString();
                 discoduro_activo=combo_discoduro_activo.getSelectedItem().toString();               
                 Reportes.Reportes.PDF(this, "Por Tipo de Activo "+tipo_activo+" y Dsico Duro de Activo " +discoduro_activo);
        }else{
            if(rbtn_tipo.isSelected() && rbtn_memoria_activo.isSelected()){
                 tipo_activo=combo_tipo_activo.getSelectedItem().toString();
                 memoria_activo=combo_memoria_activo.getSelectedItem().toString();               
                 Reportes.Reportes.PDF(this, "Por Tipo de Activo "+tipo_activo+" y Memoria de Activo " +memoria_activo);
        }else{
                Reportes.Reportes.PDF(this, "Listado todos mis activos");
                }
              }
            }
          }
        }
      }
    }
    
   }
    private void btn_eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eliminarActionPerformed
        if (msj.msg_Pregunta(this, "¿Está seguro que desea eliminar activo?, \n Esto significaría perder todos los datos relacionados al mismo.") == 0) {
            String sql;
            int filaseleccionada = this.tabla_activo.getSelectedRow();
            //Compruba que por lo menos una fila se seleccione 
            if (filaseleccionada < 0) {
                mensaje = "Debe seleccionar una fila para poder editar el activo.\n Seleccione una fila e intente de nuevo.";
                msj.msg_advertencia(this, mensaje);

            } else {

                //Obtiene el codigo del activo de la lista de codigos
                int codigo = id_activos.get(filaseleccionada);
                System.out.println("Codigo a eliminar" + codigo);
                //Asigna el codigo a la variable id_activo del formulario RegistrActivos para poder actualizarlo
                FRM_RegistroActivos.id_activo = codigo;
                String sql2 = "select * from buscar_documento_asignacion_poractivo(" + codigo + ") as (\"Doc Asignacion\" integer)";
                resultadodebusqueda = Conexion.ejecutarSQLSelect(sql2);
                try {
                    int codigo_documento_asignacion = 1;
                    while (resultadodebusqueda.next()) {
                        codigo_documento_asignacion = resultadodebusqueda.getInt("Doc Asignacion");
                    }
                    sql = "select * from eliminarporid_tmovactcon(" + codigo + "," + codigo_documento_asignacion + ")";

                    ResultSet rs = Conexion.ejecutarSQLSelect(sql);
                    try {
                        while (rs.next()) {
                            if (rs.getString("dardebaja_tmovactcon").equals("true")) {
                                mensaje = "El activo fue eliminado con èxito.!";
                                msj.msg_confirmacion(this, mensaje);
                                actualizartabla();
                            } else {
                                msj.msg_advertencia(this, "Este activo está asignado a: " + rs.getString("dardebaja_tmovactcon") + "\nPara eliminar u dar de baja, debe de desvincularlo de su responsable.");
                                actualizartabla();
                            }
                        }
                    } catch (Exception ex) {
                        System.out.println("llenar activos");
                    }

                    /*if (Conexion.ejecutarSQL(sql) == true) {
                        mensaje = "Se elimino el registro con exito";
                        msj.msg_confirmacion(this, mensaje);
                        actualizartabla();
                    }*/

                } catch (SQLException ex) {
                    Logger.getLogger(FRM_BusquedaActivos.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
        

    }//GEN-LAST:event_btn_eliminarActionPerformed

    private void combo_estado_activoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_estado_activoActionPerformed
            rbtn_estado_activo.setSelected(true);
    }//GEN-LAST:event_combo_estado_activoActionPerformed

    private void rbtn_procesador_activoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtn_procesador_activoActionPerformed
        if (rbtn_procesador_activo.isSelected() == true) {
            combo_procesador_activo.setEnabled(true);
        } else {
            combo_procesador_activo.setEnabled(false);
        }
    }//GEN-LAST:event_rbtn_procesador_activoActionPerformed

    private void rbtn_memoria_activoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtn_memoria_activoActionPerformed
        if (rbtn_memoria_activo.isSelected() == true) {
            combo_memoria_activo.setEnabled(true);
        } else {
            combo_memoria_activo.setEnabled(false);
        }
    }//GEN-LAST:event_rbtn_memoria_activoActionPerformed

    private void rbtn_discoduro_activoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtn_discoduro_activoActionPerformed
        if (rbtn_discoduro_activo.isSelected() == true) {
            combo_discoduro_activo.setEnabled(true);
        } else {
            combo_discoduro_activo.setEnabled(false);
        }
    }//GEN-LAST:event_rbtn_discoduro_activoActionPerformed

    private void btn_restaurarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_restaurarActionPerformed
// Trae el numero de la fila seleccionada de la tabla
        int filaseleccionada = this.tabla_activo.getSelectedRow();
        //Compruba que por lo menos una fila se seleccione 
        if (filaseleccionada < 0) {
            mensaje = "Debe seleccionar una fila para poder editar el activo.\n Seleccione una fila e intente de nuevo.";
            msj.msg_advertencia(this, mensaje);
        } else {

            //Obtiene el codigo del activo de la lista de codigos
            int codigo = id_activos.get(filaseleccionada);
            String sql = "select * from dardealta_tmovactcon(" + codigo + ")";
            id_activos.remove(filaseleccionada);
            if (Conexion.ejecutarSQL(sql) == true) {
                mensaje = "El activo fue dado de alta con èxito.!";
                msj.msg_confirmacion(this, mensaje);
                actualizartabla();
            } else {
                mensaje = "No se puede dar de alta el activo.!";
                msj.msg_advertencia(this, mensaje);
            }

        }        
        
    }//GEN-LAST:event_btn_restaurarActionPerformed

    private void txt_serie_activoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_serie_activoKeyTyped
        char c=evt.getKeyChar(); 
        String var0 = "'";
        char var2 = 'ñ';
        char var3 = 'Ñ';
        if(txt_serie_activo.getText().length()>200){
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

    private void txt_codigoInternoInsticucional_activoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_codigoInternoInsticucional_activoKeyTyped
        char c=evt.getKeyChar(); 
        String var0 = "'";
        char var1 = '-';
        char var2 = 'ñ';
        char var3 = 'Ñ';
        if(txt_codigoInternoInsticucional_activo.getText().length()>200){
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
    }//GEN-LAST:event_txt_codigoInternoInsticucional_activoKeyTyped

//Deshabilita todos los radiobutton y combos que se utiliza en conjunto con tipo
    public void desactivar_tipo() {
        rbtn_marca_activo.setEnabled(false);
        combo_marca_activo.setEnabled(false);
        combo_discoduro_activo.setEnabled(false);
        combo_memoria_activo.setEnabled(false);
        combo_procesador_activo.setEnabled(false);
        rbtn_discoduro_activo.setEnabled(false);
        rbtn_memoria_activo.setEnabled(false);
        rbtn_procesador_activo.setEnabled(false);
        combo_tipo_activo.setEnabled(false);
    }

    //Obtiene las columnas de la tabla

    public static String[] getcolumnas() {
        String columnas[] = new String[]{"NOMBRE", "MARCA", "PROCESADOR", "MEMORIA", "DISCO DURO", "MODELO", "SERIE", "PRECIO", "FECHA DE COMPRA", "CODIGO INSTITUCIONAL", "DETALLE"};
        return columnas;
    }

    //Valida que ingrese la serie

    private String validarserie() {
        String sql;
        if (txt_serie_activo.getText().isEmpty() || validarmayusculasynumeros(txt_serie_activo.getText()) == false) {
            mensaje = "Debe ingresar la serie a buscar.\n Ingrese un dato e intente su busqueda de nuevo.";
            msj.msg_advertencia(this, mensaje);
            txt_serie_activo.requestFocus();
            sql = null;
        } else {
            sql = "SELECT * FROM buscar_tmovactcon_serieactivo('" + txt_serie_activo.getText() + "')AS (\"CODIGO ACTIVO\" integer, \"NOMBRE\" varchar, \"MARCA\" varchar, \"PROCESADOR\" varchar, \"MEMORIA\" varchar, \"DISCO DURO\" varchar, \"MODELO\" VARCHAR, \"SERIE\" VARCHAR, \"PRECIO\" DECIMAL, \"FECHA DE COMPRA\" DATE, \"CODIGO INSTITUCIONAL\" VARCHAR, \"DETALLE\" VARCHAR);";
        }
        return sql;
    }

    //Este metodo limpia la tabla

    void limpiarTabla() {
        DefaultTableModel temp;
        try {
            temp = (DefaultTableModel) this.tabla_activo.getModel();
            int cont = temp.getRowCount();
            for (int i = 0; i < cont; i++) {
                temp.removeRow(0);
            }
        } catch (Exception e) {

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
            java.util.logging.Logger.getLogger(FRM_BusquedaActivos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FRM_BusquedaActivos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FRM_BusquedaActivos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FRM_BusquedaActivos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FRM_BusquedaActivos dialog = new FRM_BusquedaActivos(new javax.swing.JDialog(), true);
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
    private javax.swing.JButton btn_buscar;
    private javax.swing.JButton btn_darBaja;
    private javax.swing.JButton btn_eliminar;
    private javax.swing.JButton btn_imprimir;
    private javax.swing.JButton btn_modificar;
    private javax.swing.JButton btn_nuevo;
    private javax.swing.JButton btn_regresar;
    private javax.swing.JButton btn_restaurar;
    private javax.swing.JComboBox combo_discoduro_activo;
    private javax.swing.JComboBox combo_estado_activo;
    private javax.swing.JComboBox combo_marca_activo;
    private javax.swing.JComboBox combo_memoria_activo;
    private javax.swing.JComboBox combo_procesador_activo;
    private javax.swing.JComboBox combo_tipo_activo;
    private javax.swing.ButtonGroup grupo_busqueda;
    private javax.swing.ButtonGroup grupo_tipo;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JRadioButton rbtn_codigoinstitucional_activo;
    private javax.swing.JRadioButton rbtn_discoduro_activo;
    private javax.swing.JRadioButton rbtn_estado_activo;
    private javax.swing.JRadioButton rbtn_marca_activo;
    private javax.swing.JRadioButton rbtn_memoria_activo;
    private javax.swing.JRadioButton rbtn_procesador_activo;
    private javax.swing.JRadioButton rbtn_serie_activo;
    private javax.swing.JRadioButton rbtn_tipo;
    public static javax.swing.JTable tabla_activo;
    private javax.swing.JTextField txt_codigoInternoInsticucional_activo;
    private javax.swing.JTextField txt_serie_activo;
    // End of variables declaration//GEN-END:variables
}
