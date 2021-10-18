package CapaInterfaces;

import Capa_Mensajes.Mensajes;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.sql.ResultSet;
import java.util.Arrays;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 *
 * @author Grupo 3 Alejandro
 */

public class FRM_SolicitarMantenimiento extends javax.swing.JDialog {

    public static int cod_hablitar = 0;
    DefaultTableModel model;
    Capa_ConexionBD.Conexion conexion = new Capa_ConexionBD.Conexion();
    public static int [] id_activo = new int[20];
    Mensajes mensajes = new Mensajes();
    int bandera=0;
    public FRM_SolicitarMantenimiento(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        this.getContentPane().setLayout(new GridBagLayout());
        combo_Tipo_Area();
        this.setDefaultCloseOperation(0);
        btn_asignar.setEnabled(false);
    }

    public void combo_Tipo_Area() {
//        combo_tipo_area.removeAllItems();
        combo_tipo_area.addItem("Laboratorio");
        combo_tipo_area.addItem("Administración");
        combo_tipo_area.addItem("Docente");
    }

    public void combo_RecursoHumano() {
        combo_tipo_rrhh.removeAllItems();
        conexion.crearConexion();
        String sql = "select p.nombre_persona \n"
                + "from tmaepercon p, tmovrehcon rh, tmaearecon a\n"
                + "where p.id_persona = rh.idpersona_rrhh and "
                + "rh.id_rrhh = a.idresponsable_area and a.tipo_area ="
                + " '" + combo_tipo_area.getSelectedItem().toString() + "'";
        ResultSet rs = conexion.ejecutarSQLSelect(sql);
        try {
            while (rs.next()) {
                combo_tipo_rrhh.addItem(rs.getString(1));
            }
        } catch (Exception ex) {
            System.out.println("error");
        }
    }

    public void limpiarTabla(JTable tabla) {
        try {
            DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
            int filas = tabla.getRowCount();  //Obtener el numero total de columnas del modelo de la tabla
            for (int i = 0; filas > i; i++) {  //recorrer una a una de las filas ingresadas dentro de la tabala hasta que no queden datos
                modelo.removeRow(0);        //eliminar la fila seleccionada o en la cual esta la posición
            }
        } catch (Exception e) {
            System.out.println("Error al limpiar la tabla.");
        }
    }

    public void Detalles_Busqueda() {
        conexion.crearConexion();
        String[] var1 = new String[11];
        model = (DefaultTableModel) tabla_lista_activos.getModel();
        String sql = "select idTipo_activo,id_activo,marca_activo,precesador_acrtivo,memoria_activo,"
                + "discoduro_activo,modelo_activo,serie_activo,\n"
                + "costo_activo,fechacompra_activo,codigointernoinstitucional_activo \n"
                + "from tmaepercon p,tmovrehcon rh,TMAEARECON ar,TMOVDASCON da,TMOVACTCON a\n"
                + "where p.id_persona = rh.idpersona_rrhh and rh.id_rrhh = ar.idresponsable_area and ar.id_area = da.idarea_asigactivo \n"
                + "and da.id_asigactivo = a.iddocasignacion_activo and p.nombre_persona= '"+ combo_tipo_rrhh.getSelectedItem().toString() + "' "
                + "and ar.tipo_area= '"+ combo_tipo_area.getSelectedItem().toString() + "' and a.estado_activo=0";
        ResultSet rs = conexion.ejecutarSQLSelect(sql);
        try {
            limpiarTabla(tabla_lista_activos);
            int i = 0;
            while (rs.next()) {              
                var1[0] = rs.getString(1);                
                var1[1] = rs.getString(2);
                var1[2] = rs.getString(3);
                var1[3] = rs.getString(4);
                var1[4] = rs.getString(5);
                var1[5] = rs.getString(6);
                var1[6] = rs.getString(7);
                var1[7] = rs.getString(8);
                var1[8] = rs.getString(9);
                var1[9] = rs.getString(10);
                var1[10] = rs.getString(11);
                model.addRow(var1);
                tabla_lista_activos.setValueAt(false, i, 10);
                i = i + 1;
            }
        } catch (Exception ex) {
            System.out.println("error");
        }
    }

    public void obtener_datos_filtrados() {
        conexion.crearConexion();
        int a = tabla_lista_activos.getRowCount();
        int filas = 0, arrayF = 0,numero_registros=0;
        String[][] datos_tabla = new String[a][10];
        while (filas != a) {
            String valor = tabla_lista_activos.getValueAt(filas, 10).toString();
            int columnas = 0;
            if (valor.equals("true")) {
                while (columnas != 10) {                         
                    datos_tabla[arrayF][columnas] = tabla_lista_activos.getValueAt(filas, columnas).toString();
                    id_activo [arrayF]= Integer.parseInt(tabla_lista_activos.getValueAt(filas, 0).toString());
                    columnas++;
                }
                numero_registros++;
                arrayF++;
            }
            filas++;
        }
        bandera=numero_registros;
        Definir_tipo_mantenimiento();        
        FRM_RegistroSolicitudMantenimiento rsm = new FRM_RegistroSolicitudMantenimiento(id_activo,datos_tabla, numero_registros, cod_hablitar = 0);
    }

    public void Definir_tipo_mantenimiento() {
        int a = tabla_lista_activos.getRowCount();
        int filas = 0, asignar_estado = 0, estado = 1;
        while (filas != a) {
            String valor = tabla_lista_activos.getValueAt(filas, 10).toString();
            if (valor.equals("true")) {
                asignar_estado++;
            }
            filas++;
        }
        String comprobacion = combo_tipo_area.getSelectedItem().toString();
        if (comprobacion.equals("Docente")){
            estado = 1;
        }else{        
        if (asignar_estado == a) {
            estado = 0;
        } else {
            estado = 1;
        }
        }       
        FRM_RegistroSolicitudMantenimiento rsm = new FRM_RegistroSolicitudMantenimiento(estado);
    }

    public void seleccionar_todos_Tchaeck() {
        int a = tabla_lista_activos.getRowCount();
        int filas = 0, asignar_estado = 0;
        String estado;
        while (filas != a) {
            tabla_lista_activos.setValueAt(true, filas, 10);
            filas++;
        }
    }

    public void datos_solicitante() {
        conexion.crearConexion();
        String[][][] var1 = new String[3][3][3];
        String sql = "select rh.id_rrhh,p.cedula_persona,p.nombre_persona\n"
                + "from tmaepercon p, tmovrehcon rh, tmaearecon a\n"
                + "where p.id_persona = rh.idpersona_rrhh and rh.id_rrhh = a.idresponsable_area "
                + "and a.tipo_area = '" + combo_tipo_area.getSelectedItem().toString() +"'"
                + "and p.nombre_persona = '"+ combo_tipo_rrhh.getSelectedItem().toString() +"'";
        ResultSet rs = conexion.ejecutarSQLSelect(sql);
        try {                                   
            while (rs.next()) {
                var1[0][0][0] = rs.getString(1);
                var1[0][0][1] = rs.getString(2);
                var1[0][0][2] = rs.getString(3);
            }
        } catch (Exception ex) {
            System.out.println("error");
        }                               
        FRM_RegistroSolicitudMantenimiento rsm = new FRM_RegistroSolicitudMantenimiento(var1);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        label1 = new java.awt.Label();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla_lista_activos = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        combo_tipo_area = new javax.swing.JComboBox();
        combo_tipo_rrhh = new javax.swing.JComboBox();
        jButton3 = new javax.swing.JButton();
        btn_buscar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jToolBar1 = new javax.swing.JToolBar();
        btn_nuevo = new javax.swing.JButton();
        btn_asignar = new javax.swing.JButton();
        btn_regresar = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        btn_seleccionar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Solicitar Mantenimiento");
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jButton1.setBorder(null);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jButton5.setBorder(null);
        jButton5.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        label1.setAlignment(java.awt.Label.CENTER);
        label1.setFont(new java.awt.Font("Tahoma", 1, 21)); // NOI18N
        label1.setText("Listado de Activos");

        tabla_lista_activos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Tipo", "Marca", "Procesador", "Memoria", "Disco Duro", "Modelo", "Serie", "Costo", "Fecha Compra", "Cod. Institucional", ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabla_lista_activos.setColumnSelectionAllowed(true);
        tabla_lista_activos.setNextFocusableComponent(btn_asignar);
        tabla_lista_activos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tabla_lista_activos.removeColumn(tabla_lista_activos.getColumnModel().getColumn(0));
        tabla_lista_activos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabla_lista_activosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabla_lista_activos);
        tabla_lista_activos.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Detalles Búsqueda", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 17))); // NOI18N

        combo_tipo_area.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        combo_tipo_area.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                combo_tipo_areaItemStateChanged(evt);
            }
        });
        combo_tipo_area.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                combo_tipo_areaFocusGained(evt);
            }
        });

        combo_tipo_rrhh.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        combo_tipo_rrhh.setEnabled(false);
        combo_tipo_rrhh.setNextFocusableComponent(btn_buscar);
        combo_tipo_rrhh.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                combo_tipo_rrhhItemStateChanged(evt);
            }
        });
        combo_tipo_rrhh.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                combo_tipo_rrhhFocusGained(evt);
            }
        });

        jButton3.setBorder(null);
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        btn_buscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icon_buscar(1).png"))); // NOI18N
        btn_buscar.setBorder(null);
        btn_buscar.setContentAreaFilled(false);
        btn_buscar.setNextFocusableComponent(tabla_lista_activos);
        btn_buscar.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icon_buscar(1).png"))); // NOI18N
        btn_buscar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/icon_buscar.png"))); // NOI18N
        btn_buscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_buscarMouseClicked(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel1.setText("Tipo Área");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel2.setText("Recurso Humano");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(104, 724, Short.MAX_VALUE)
                .addComponent(btn_buscar)
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(combo_tipo_area, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(566, 566, 566)
                        .addComponent(jButton3))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(105, 105, 105)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(combo_tipo_rrhh, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combo_tipo_area, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(combo_tipo_rrhh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(0, 0, 0)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_buscar))
        );

        jToolBar1.setBackground(new java.awt.Color(117, 214, 255));
        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        btn_nuevo.setBackground(new java.awt.Color(117, 214, 255));
        btn_nuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/add-new-file (1).png"))); // NOI18N
        btn_nuevo.setText("   Nuevo   ");
        btn_nuevo.setFocusable(false);
        btn_nuevo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_nuevo.setNextFocusableComponent(combo_tipo_area);
        btn_nuevo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_nuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nuevoActionPerformed(evt);
            }
        });
        jToolBar1.add(btn_nuevo);

        btn_asignar.setBackground(new java.awt.Color(117, 214, 255));
        btn_asignar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/servicio.png"))); // NOI18N
        btn_asignar.setText("   Solicitar Mantenimiento");
        btn_asignar.setFocusable(false);
        btn_asignar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_asignar.setNextFocusableComponent(btn_nuevo);
        btn_asignar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_asignar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_asignarActionPerformed(evt);
            }
        });
        jToolBar1.add(btn_asignar);

        btn_regresar.setBackground(new java.awt.Color(117, 214, 255));
        btn_regresar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/logout (1).png"))); // NOI18N
        btn_regresar.setText("   Regresar");
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

        jPanel9.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btn_seleccionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/1/seleccionar.png"))); // NOI18N
        btn_seleccionar.setText("Seleccionar");
        btn_seleccionar.setContentAreaFilled(false);
        btn_seleccionar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_seleccionar.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btn_seleccionar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_seleccionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_seleccionarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_seleccionar)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_seleccionar)
                .addContainerGap(141, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(label1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(712, 712, 712)
                        .addComponent(jButton5)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton5)
                    .addComponent(jButton1))
                .addContainerGap())
        );

        jButton1.getAccessibleContext().setAccessibleName("btn_Solicitar");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void btn_regresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_regresarActionPerformed
        if(mensajes.msg_Pregunta(this, "¿Esta seguro que desea cancelar solicitud de mantenimiento?")==0){
            dispose();
        }
    }//GEN-LAST:event_btn_regresarActionPerformed
    private void btn_asignarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_asignarActionPerformed
        obtener_datos_filtrados();
        datos_solicitante();
        if(bandera>0){
            dispose();
            FRM_RegistroSolicitudMantenimiento.save=true;
            new FRM_RegistroSolicitudMantenimiento(this, true).setVisible(true);
        }else{
            mensajes.msg_advertencia(this, "Para solicitar mantenimiento debe de seleccionar al menos un actiivo.");
        }
    }//GEN-LAST:event_btn_asignarActionPerformed
    private void combo_tipo_areaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_combo_tipo_areaFocusGained
        
    }//GEN-LAST:event_combo_tipo_areaFocusGained
    private void btn_buscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_buscarMouseClicked
        Detalles_Busqueda();
        btn_asignar.setEnabled(true);
    }//GEN-LAST:event_btn_buscarMouseClicked
    private void combo_tipo_rrhhFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_combo_tipo_rrhhFocusGained
        combo_RecursoHumano();
    }//GEN-LAST:event_combo_tipo_rrhhFocusGained
    private void btn_seleccionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_seleccionarActionPerformed
        seleccionar_todos_Tchaeck();
    }//GEN-LAST:event_btn_seleccionarActionPerformed
    private void combo_tipo_areaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_combo_tipo_areaItemStateChanged
        combo_tipo_rrhh.setEnabled(true);
        combo_tipo_rrhh.removeAllItems();
    }//GEN-LAST:event_combo_tipo_areaItemStateChanged
    private void combo_tipo_rrhhItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_combo_tipo_rrhhItemStateChanged
        limpiarTabla(tabla_lista_activos);
    }//GEN-LAST:event_combo_tipo_rrhhItemStateChanged

    private void tabla_lista_activosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabla_lista_activosMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tabla_lista_activosMouseClicked

    private void btn_nuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nuevoActionPerformed
        btn_asignar.setEnabled(false);
        DefaultTableModel temp= (DefaultTableModel) tabla_lista_activos.getModel();
        try{
            int a =temp.getRowCount()-1;
            for(int i=0; i<=a; i++){
                temp.removeRow(0);
            }
        }catch(Exception e){
            
        }
    }//GEN-LAST:event_btn_nuevoActionPerformed

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
            java.util.logging.Logger.getLogger(FRM_SolicitarMantenimiento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FRM_SolicitarMantenimiento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FRM_SolicitarMantenimiento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FRM_SolicitarMantenimiento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>=
        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FRM_SolicitarMantenimiento dialog = new FRM_SolicitarMantenimiento(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btn_asignar;
    private javax.swing.JButton btn_buscar;
    private javax.swing.JButton btn_nuevo;
    private javax.swing.JButton btn_regresar;
    private javax.swing.JButton btn_seleccionar;
    private javax.swing.JComboBox combo_tipo_area;
    private javax.swing.JComboBox combo_tipo_rrhh;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private java.awt.Label label1;
    private javax.swing.JTable tabla_lista_activos;
    // End of variables declaration//GEN-END:variables
}
