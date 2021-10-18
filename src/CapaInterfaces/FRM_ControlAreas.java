/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package CapaInterfaces;

import Capa_ConexionBD.Conexion;
import Capa_Mensajes.Mensajes;
import Capa_Validaciones.Validaciones;
import java.awt.Event;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.InputMap;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Carmen
 */
public class FRM_ControlAreas extends javax.swing.JDialog {
    
    Conexion conexion = new Conexion();
    String tipo_area, descripcion_area, cedula_persona;
    int idresponsable_area;
    boolean save=false;
    int bandera=0;
    Object[] rowData=null;
    String areaActual;
    String idArea;
    String bander_area;
    
    Mensajes mensajes = new Mensajes ();
    ButtonGroup group = new ButtonGroup();
    //Buscar_Recurso_Humano buscarRRHH = new Buscar_Recurso_Humano(this, rootPaneCheckingEnabled);
    //String TipoAreaSeleccionada ="Laboratorio";
    //Validaciones validaciones = new Validaciones();
    //
    //
    //int bandera =0;

    public FRM_ControlAreas(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        this.getContentPane().setLayout (new GridBagLayout());
        this.setDefaultCloseOperation(0);
        table_area.getTableHeader().setReorderingAllowed(false);
        limpiarCampos();
        bloquearTeclas();
    }
    
    public void bloquearTeclas() {
        InputMap map1 = txt_descripcion_area.getInputMap(txt_descripcion_area.WHEN_FOCUSED);
        map1.put(KeyStroke.getKeyStroke(KeyEvent.VK_V, Event.CTRL_MASK), "null");
        InputMap map2 = txt_cedula_persona.getInputMap(txt_cedula_persona.WHEN_FOCUSED);
        map2.put(KeyStroke.getKeyStroke(KeyEvent.VK_V, Event.CTRL_MASK), "null");
        InputMap map3 = txt_busqueda_nombre_persona.getInputMap(txt_busqueda_nombre_persona.WHEN_FOCUSED);
        map3.put(KeyStroke.getKeyStroke(KeyEvent.VK_V, Event.CTRL_MASK), "null");
    }
    
    public void recogerValores(){
        tipo_area=combo_tipo_area.getSelectedItem().toString().trim();
        descripcion_area=txt_descripcion_area.getText().trim();
        cedula_persona=txt_cedula_persona.getText().toString().trim();
    }
    
    public void nuevoBuscar(){
        recogerValores();
        if(descripcion_area.equals("") && cedula_persona.equals("")){
            limpiarCampos();
        }else{
            if(mensajes.msg_Pregunta(this, "Los datos que no ha guardado se borraran.\n¿Esta seguro de crear un nuevo registro?")==0){
                limpiarCampos();
            }
        }
    }
    public void limpiarCampos(){
        combo_tipo_area.setSelectedIndex(0);
        txt_descripcion_area.setText("");
        txt_cedula_persona.setText("");
        txt_nombre_persona.setText("");
        btn_modificar.setEnabled(false);
        btn_actualizar.setEnabled(false);
        btn_eliminar.setEnabled(false);
        jRadioButton1.setSelected(true);
        txt_busqueda_nombre_persona.setEnabled(false);
        group.add(jRadioButton1); group.add(jRadioButton2);
        table_area.setModel(new DefaultTableModel());
        txt_busqueda_nombre_persona.setText("");
        btn_guardar.setEnabled(true);
    }
    
    public void busquedaCedula() {
        String cedulaPersona = txt_cedula_persona.getText().toString();//guardamos la cedula en una variable
        try {
            if (conexion.crearConexion()) {  //si se puede hacer la conexión se ejecuta
                String sql= "select rrhh.id_rrhh, p.cedula_persona, p.nombre_persona, p.apellido_persona from TMOVREHCON as rrhh inner join TMAEPERCON as p on rrhh.idpersona_rrhh=p.id_persona where rrhh.tipo_rrhh!='Mantenimiento' and idpersona_rrhh=(select id_persona from TMAEPERCON where cedula_persona='"+txt_cedula_persona.getText().toString()+"')";//seleccionamos al responsable por la cedula
                ResultSet rs = conexion.ejecutarSQLSelect(sql);//mandamos a ejecutar el sql
                if(rs.next()){
                    idresponsable_area=rs.getInt("id_rrhh");
                    txt_nombre_persona.setText(rs.getString("nombre_persona")+" "+rs.getString("apellido_persona"));
                }
            }
        } catch (Exception e) {
            
        }
    }
    
    public boolean existenciaArea(){
        boolean existe=false;
        String descripcionArea = txt_descripcion_area.getText().toString();//guardamos la cedula en una variable
        try {
            if (conexion.crearConexion()) {  //si se puede hacer la conexión se ejecuta
                String sql= "select count(*) from TMAEARECON where descripcion_area='"+txt_descripcion_area.getText().toString()+"';";
                ResultSet rs = conexion.ejecutarSQLSelect(sql);//mandamos a ejecutar el sql
                if(rs.next()){
                    if(bandera==0){
                        if(rs.getInt("count")>0){
                            mensajes.msg_advertencia(this, "Area ya éxiste, por favor ingrese una nueva descripción o modifique la existente.");
                            txt_descripcion_area.setText("");
                            txt_descripcion_area.requestFocus();
                            existe=true;
                        }
                    }
                }
            }
        } catch (Exception e) {
            
        }
        return existe;
    }
    
    public void validarCampos(){
        recogerValores();
        if(descripcion_area.equals("")){
            mensajes.msg_advertencia(this, "Ingrese descripción de área.");
            txt_descripcion_area.setText("");
            txt_descripcion_area.requestFocus();
        }else{
            if(cedula_persona.equals("")){
                mensajes.msg_advertencia(this, "Ingrese cédula de responsable.");
                txt_cedula_persona.setText("");
                txt_cedula_persona.requestFocus();
            }else{
                if(txt_nombre_persona.getText().trim().equals("")){
                    mensajes.msg_advertencia(this, "El responsable de área especificado no existe. \n Por favor realice una nueva búsqueda o \nregistrelo en la opción de recursos humanos.");
                    txt_cedula_persona.setText("");
                    txt_cedula_persona.requestFocus();
                } else {
                    if (save == true) {
                        if (existenciaArea() == false) {
                            guardar();
                        }
                    }else{
                        if(bander_area.equals(descripcion_area)){
                            actualizar();
                        }else{
                            if (existenciaArea() == false) {
                                actualizar();
                            }
                        }
                    }
                }
            }
        }
    }
    
    public void guardar(){
        String sql= "insert into TMAEARECON (tipo_area, descripcion_area, idresponsable_area) values('"+tipo_area+"', '"+descripcion_area+"', "+idresponsable_area+")"; //hacermos el sql de inster con los datos necesarios
        if(conexion.ejecutarSQL(sql)){ //el result set nos dará el mensaje desde le procedimiento de la base de datos
            mensajes.msg_confirmacion(this, "Registro guardado con éxito.");
            limpiarCampos();
        }
    }
    
    public void actualizar(){
        String sql= "update TMAEARECON set tipo_area='"+tipo_area+"', descripcion_area='"+descripcion_area+"', idresponsable_area="+idresponsable_area+" where id_area="+idArea;
        if(conexion.ejecutarSQL(sql)){ //el result set nos dará el mensaje desde le procedimiento de la base de datos
            mensajes.msg_confirmacion(this, "Registro modificado con éxito.");
            limpiarCampos();
        }
    }
    
    public void buscar() {
        String tStringTipo = combo_busqueda_tipo_area.getSelectedItem().toString();  //guardamos lo que contienen los campos llenados
        Integer tipoNum = combo_busqueda_tipo_area.getSelectedIndex() + 1;
        String nombrePersona = txt_busqueda_nombre_persona.getText();
        String cedulaPersona = txt_cedula_persona.getText();                         //
        boolean isSelected = jRadioButton1.isSelected();       //revisamos que criterio de busqueda está seleccionado
        boolean isSelected2 = jRadioButton2.isSelected();      //

        if (isSelected && conexion.crearConexion()) {  //si tipo está seleccionado y se puedo crear la conexión se ejecuta la consulta de todas las áreas según el criterio seleccionado
            jRadioButton1.setSelected(true);
            String sql="SELECT * FROM BUSCAR_TMAEARECON_TIPO ('"+tStringTipo+"') AS (\"ID_AREA\" varchar, \"DESCRIPCION_AREA\" varchar, \"CEDULA_RESPONSABLE\" varchar, \"NOMBRES_RESPONSABLE\" varchar, \"APELLIDOS_RESPONSABLE\" varchar)";
            ResultSet rs = conexion.ejecutarSQLSelect(sql);
            ActualizarTabla(table_area, rs);  //el rs contiene los datos de la consulta, solo mandamos a actualizar la tabla con el método actualizar tabla
        }
        else{ if (isSelected2 && conexion.crearConexion()) {//si representante está escogido y se pudo crear la conexión se ejecuta la consulta de todas las áreas según el criterio seleccionado
            jRadioButton2.setSelected(true);
            String sql="SELECT * FROM BUSCAR_TMAEARECON_RESPONSABLE ('"+nombrePersona+"') AS (\"ID_AREA\" varchar, \"DESCRIPCION_AREA\" varchar, \"CEDULA_RESPONSABLE\" varchar, \"NOMBRES_RESPONSABLE\" varchar, \"APELLIDOS_RESPONSABLE\" varchar)";
            ResultSet rs = conexion.ejecutarSQLSelect(sql);
            ActualizarTabla(table_area, rs);//el rs contiene los datos de la consulta, solo mandamos a actualizar la tabla con el método actualizar tabla
        }
        }
    }
    
    public void ActualizarTabla(JTable jtabla, ResultSet rs) {
        DefaultTableModel modelo = new DefaultTableModel(); //Creo un modelo de datos para un jtable
        jtabla.setModel(modelo); //le asigno a la tabla el modelo de             //datos
        try {
            //creo las columnas con sus etiquetas
            //estas son las columnas del JTable
            modelo.addColumn("Tipo");
            modelo.addColumn("Descripcion");
            modelo.addColumn("Cedula Responsable");
            modelo.addColumn("Nombres Responsable");
            modelo.addColumn("Apellidos Responsable");


            //Recorro el ResultSet que contiene los resultados.
            while (rs.next()) {
                Object[] ob = new Object[5]; //Crea un vector
                //para almacenar los valores del ResultSet
                ob[0] = (rs.getString(1));
                ob[1] = (rs.getString(2));
                ob[2] = (rs.getString(3));
                ob[3] = (rs.getString(4));
                ob[4] = (rs.getString(5));

                // agrego cuantas filas tenga el resulset
                modelo.addRow(ob);
                ob = null; //limpia los datos de el vector de la memoria
            }
            rs.close(); //Cierra el ResultSet
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
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

        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        combo_tipo_area = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        txt_descripcion_area = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        btn_buscar_responsable = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txt_cedula_persona = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txt_nombre_persona = new javax.swing.JTextField();
        btn_buscar_responsabl = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jRadioButton1 = new javax.swing.JRadioButton();
        combo_busqueda_tipo_area = new javax.swing.JComboBox();
        jRadioButton2 = new javax.swing.JRadioButton();
        btn_busqueda_area = new javax.swing.JButton();
        txt_busqueda_nombre_persona = new javax.swing.JTextField();
        btn_buscar_area = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        table_area = new javax.swing.JTable();
        jPanel10 = new javax.swing.JPanel();
        btn_modificar = new javax.swing.JButton();
        btn_eliminar = new javax.swing.JButton();
        jToolBar1 = new javax.swing.JToolBar();
        btn_nuevo = new javax.swing.JButton();
        btn_guardar = new javax.swing.JButton();
        btn_actualizar = new javax.swing.JButton();
        btn_regresar = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Control de Áreas");
        setResizable(false);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 21)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Control de Áreas");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel2.setText("Tipo");

        combo_tipo_area.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        combo_tipo_area.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Laboratorio", "Administración", "Docente" }));
        combo_tipo_area.setToolTipText("");
        combo_tipo_area.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        combo_tipo_area.setNextFocusableComponent(txt_descripcion_area);
        combo_tipo_area.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_tipo_areaActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel3.setText("Descripción");

        txt_descripcion_area.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        txt_descripcion_area.setNextFocusableComponent(txt_cedula_persona);
        txt_descripcion_area.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_descripcion_areaKeyTyped(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Detalles del Responsable", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 17))); // NOI18N

        btn_buscar_responsable.setBorderPainted(false);
        btn_buscar_responsable.setContentAreaFilled(false);

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel4.setText("Cédula");

        txt_cedula_persona.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        txt_cedula_persona.setNextFocusableComponent(btn_guardar);
        txt_cedula_persona.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_cedula_personaFocusLost(evt);
            }
        });
        txt_cedula_persona.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_cedula_personaKeyTyped(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel5.setText("Nombres y Apellidos");

        txt_nombre_persona.setEnabled(false);
        txt_nombre_persona.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_nombre_personaMouseClicked(evt);
            }
        });
        txt_nombre_persona.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_nombre_personaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_nombre_personaKeyTyped(evt);
            }
        });

        btn_buscar_responsabl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/magnifier-tool (1).png"))); // NOI18N
        btn_buscar_responsabl.setContentAreaFilled(false);
        btn_buscar_responsabl.setNextFocusableComponent(btn_guardar);
        btn_buscar_responsabl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_buscar_responsablMouseClicked(evt);
            }
        });
        btn_buscar_responsabl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_buscar_responsablActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_cedula_persona, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_nombre_persona, javax.swing.GroupLayout.PREFERRED_SIZE, 342, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_buscar_responsabl, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_buscar_responsable)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel4)
                                .addComponent(txt_cedula_persona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel5)
                                .addComponent(txt_nombre_persona, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btn_buscar_responsable)))
                    .addComponent(btn_buscar_responsabl, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 21)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Búsqueda de Áreas");

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Detalles Búsqueda", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 17))); // NOI18N

        jRadioButton1.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jRadioButton1.setText("Tipo");
        jRadioButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jRadioButton1MouseClicked(evt);
            }
        });
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        combo_busqueda_tipo_area.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        combo_busqueda_tipo_area.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Laboratorio", "Administración", "Docente" }));
        combo_busqueda_tipo_area.setNextFocusableComponent(btn_buscar_area);
        combo_busqueda_tipo_area.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_busqueda_tipo_areaActionPerformed(evt);
            }
        });

        jRadioButton2.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jRadioButton2.setText("Responsable");
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });

        btn_busqueda_area.setBorderPainted(false);
        btn_busqueda_area.setContentAreaFilled(false);

        txt_busqueda_nombre_persona.setNextFocusableComponent(btn_buscar_area);
        txt_busqueda_nombre_persona.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_busqueda_nombre_personaKeyTyped(evt);
            }
        });

        btn_buscar_area.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/magnifier-tool (1).png"))); // NOI18N
        btn_buscar_area.setContentAreaFilled(false);
        btn_buscar_area.setName(""); // NOI18N
        btn_buscar_area.setNextFocusableComponent(table_area);
        btn_buscar_area.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_buscar_areaMouseClicked(evt);
            }
        });
        btn_buscar_area.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_buscar_areaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jRadioButton1)
                .addGap(18, 18, 18)
                .addComponent(combo_busqueda_tipo_area, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btn_busqueda_area))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jRadioButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_busqueda_nombre_persona)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_buscar_area, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addComponent(btn_busqueda_area))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btn_buscar_area, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jRadioButton1)
                                .addComponent(combo_busqueda_tipo_area, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jRadioButton2)
                                .addComponent(txt_busqueda_nombre_persona, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(19, 19, 19))
        );

        table_area = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false;
            }
        };
        table_area.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Tipo", "Descripción", "Cédula Responsable", "Nombres Responsable", "Apellidos Responsable"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table_area.setNextFocusableComponent(btn_modificar);
        table_area.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        table_area.removeColumn(table_area.getColumnModel().getColumn(0));
        table_area.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table_areaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table_area);

        jPanel10.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btn_modificar.setBackground(new java.awt.Color(255, 255, 255));
        btn_modificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/1/pencil.png"))); // NOI18N
        btn_modificar.setText("Editar");
        btn_modificar.setContentAreaFilled(false);
        btn_modificar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btn_modificar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_modificar.setNextFocusableComponent(btn_eliminar);
        btn_modificar.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btn_modificar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_modificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_modificarActionPerformed(evt);
            }
        });

        btn_eliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/1/rubbish-bin (1).png"))); // NOI18N
        btn_eliminar.setText("Eliminar");
        btn_eliminar.setContentAreaFilled(false);
        btn_eliminar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_eliminar.setNextFocusableComponent(table_area);
        btn_eliminar.setOpaque(true);
        btn_eliminar.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btn_eliminar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_eliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_eliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_modificar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_modificar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_eliminar, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
                .addContainerGap())
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
        btn_nuevo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_nuevoMouseClicked(evt);
            }
        });
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

        btn_actualizar.setBackground(new java.awt.Color(117, 214, 255));
        btn_actualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/refresh-page-arrow-button (1).png"))); // NOI18N
        btn_actualizar.setText("   Actualizar   ");
        btn_actualizar.setFocusable(false);
        btn_actualizar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_actualizar.setNextFocusableComponent(btn_nuevo);
        btn_actualizar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_actualizar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_actualizarMouseClicked(evt);
            }
        });
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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(combo_tipo_area, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(106, 106, 106)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_descripcion_area, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jLabel6)
                .addGap(44, 44, 44)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(combo_tipo_area, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(txt_descripcion_area, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_nuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nuevoActionPerformed
        save=false;
        nuevoBuscar();
    }//GEN-LAST:event_btn_nuevoActionPerformed

    private void btn_regresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_regresarActionPerformed
        if (mensajes.msg_Pregunta(this, "¿Esta seguro que desea salir del registro de areas?") == 0) {
            dispose();
        }
    }//GEN-LAST:event_btn_regresarActionPerformed

    private void txt_nombre_personaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nombre_personaKeyPressed
        // TODO add your handling code here:
        // TODO add your handling code here:

    }//GEN-LAST:event_txt_nombre_personaKeyPressed

    private void jRadioButton1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jRadioButton1MouseClicked
        
    }//GEN-LAST:event_jRadioButton1MouseClicked

    private void btn_buscar_areaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_buscar_areaMouseClicked
        // TODO add your handling code here:
        //buscar(); //mandamos a ejecutar el método de busqueda
    }//GEN-LAST:event_btn_buscar_areaMouseClicked

    private void txt_cedula_personaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_cedula_personaKeyTyped
        Character c = evt.getKeyChar();  //el caracter tipeado se guarda
        if(Character.isDigit(c)&&txt_cedula_persona.getText().length()<10) { //se evalua que sea digito y que la longitud de el campo no sea = o mayor que 10
            evt.setKeyChar(c); //el caracter tipeado se ingresa a la caja de texto            
        }else{
            evt.consume();//se consume el caracter tipeado
        }
    }//GEN-LAST:event_txt_cedula_personaKeyTyped

    private void txt_nombre_personaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nombre_personaKeyTyped
        // TODO add your handling code here:
        /*Character c = evt.getKeyChar();
        if(Character.isLetter(c) || c == KeyEvent.VK_SPACE ) { //validamos que solo sea letras o espacio
            evt.setKeyChar(c); //la dejamos ingresar a la caja de texto
            
        }else if ( evt.getKeyChar() == KeyEvent.VK_ENTER) { //si se da enter
            buscarRRHH.buscar();//se manda a ejecutar el método buscar de la clase buscar recurso humano
        }
        else{
            evt.consume();//se consume el caracter tipeado
        }*/
    }//GEN-LAST:event_txt_nombre_personaKeyTyped

    private void txt_nombre_personaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_nombre_personaMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_nombre_personaMouseClicked

    private void combo_busqueda_tipo_areaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_busqueda_tipo_areaActionPerformed
        jRadioButton2.setSelected(false);
        jRadioButton1.setEnabled(true);
        txt_busqueda_nombre_persona.setEnabled(false);
        txt_busqueda_nombre_persona.setText("");
    }//GEN-LAST:event_combo_busqueda_tipo_areaActionPerformed

    private void table_areaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table_areaMouseClicked
        // seleccionar item de la tabla
        rowData = new Object[table_area.getColumnCount()]; //iniciamos el array de objetos 
        JTable table = (JTable) evt.getSource(); //cogemos la fuente del clic
        Point point = evt.getPoint();// el punto
        int row = table.rowAtPoint(point);// guardamos el numero la fila seleccionada 
        if (evt.getClickCount() == 1) { //hacemos un count de clics
            for (int i = 0; i < table.getColumnCount(); i++) {// recorremos las columnas del registro
                rowData[i] = table.getValueAt(row, i);//y las guardamos en el array de objetos
            }
        } 
        btn_modificar.setEnabled(true); //desbloqueamos el boton modificar
        btn_eliminar.setEnabled(true);//desbloqueamos el boton eliminar
    }//GEN-LAST:event_table_areaMouseClicked

    private void btn_actualizarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_actualizarMouseClicked
        // actualizar datos del area
        /*if (conexion.crearConexion()&&validaciones.validadorDeCedula(txt_cedula_persona.getText())) { //si hay conexión y la cedula es correcta

            String sql= "select * from modificar_TMAEARECON('"+areaActual+"','"+combo_tipo_area.getSelectedItem().toString()+"','"+txt_descripcion_area.getText().trim()+"',"+txt_cedula_persona.getText()+","+idArea+");";//formamos el sql que realizará la modificación del registro
            ResultSet rs = conexion.ejecutarSQLSelect(sql);//guardamos lo que nos devuelve el procedimiento almacenado 
                try{
                    while  (rs.next()){ //mientras exista un dato
                        String mensaje=rs.getString(1); //guardamos el mensaje del procedimiento almacenado
                        mensajes.msg_confirmacion(this, mensaje);//mosntramos el mensaje
                    }
                }catch(Exception e){
                    System.out.println(""+e);
                }
     
        }*/
    }//GEN-LAST:event_btn_actualizarMouseClicked

    private void btn_nuevoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_nuevoMouseClicked
        // Vaciar los campos, desactivar actualizar, activar guardar, vaciar tabla y limpiar el array de objetos
        /*btn_modificar.setEnabled(false);
        btn_actualizar.setEnabled(false);
        btn_eliminar.setEnabled(false);
        btn_guardar.setEnabled(true);
        txt_cedula_persona.setText("");
        txt_nombre_persona.setText("");
        txt_descripcion_area.setText("");
        table_area.setModel(new DefaultTableModel());
        rowData=null;*/
    }//GEN-LAST:event_btn_nuevoMouseClicked

    private void txt_busqueda_nombre_personaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_busqueda_nombre_personaKeyTyped
        char c=evt.getKeyChar(); 
        String var = "'";
        if(txt_busqueda_nombre_persona.getText().length()>500){
            evt.consume();
        }else{
            if (Character.toString(c).equals(var)){
                evt.consume();
            }
        }
    }//GEN-LAST:event_txt_busqueda_nombre_personaKeyTyped

    private void txt_descripcion_areaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_descripcion_areaKeyTyped
        char c=evt.getKeyChar(); 
        char var = '-';
        char var2 = ' ';
        if(txt_descripcion_area.getText().length()>200){
            evt.consume();
        }else{
            if (Character.isLetter(c)==false && Character.isDigit(c)==false && c!=var && c!=var2){
                evt.consume();
            }
        }
    }//GEN-LAST:event_txt_descripcion_areaKeyTyped

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        combo_busqueda_tipo_area.setEnabled(true);
        txt_busqueda_nombre_persona.setEnabled(false);
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
        txt_busqueda_nombre_persona.setEnabled(true);
        combo_busqueda_tipo_area.setEnabled(false);
    }//GEN-LAST:event_jRadioButton2ActionPerformed

    private void combo_tipo_areaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_tipo_areaActionPerformed
        if(combo_tipo_area.getSelectedItem().toString().equals("Docente")){
            txt_descripcion_area.setText("DOC-"+txt_cedula_persona.getText());
            txt_descripcion_area.setEnabled(false);
        }else{
            txt_descripcion_area.setText("");
            txt_descripcion_area.setEnabled(true);
        }
    }//GEN-LAST:event_combo_tipo_areaActionPerformed

    private void txt_cedula_personaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_cedula_personaFocusLost
        busquedaCedula();
        if(combo_tipo_area.getSelectedItem().toString().equals("Docente")){
            txt_descripcion_area.setText("DOC-"+txt_cedula_persona.getText());
            txt_descripcion_area.setEnabled(false);
        }else{
            txt_descripcion_area.setEnabled(true);
        }
    }//GEN-LAST:event_txt_cedula_personaFocusLost

    private void btn_buscar_responsablActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_buscar_responsablActionPerformed
        busquedaCedula();
        if(combo_tipo_area.getSelectedItem().toString().equals("Docente")){
            txt_descripcion_area.setText("DOC-"+txt_cedula_persona.getText());
            txt_descripcion_area.setEnabled(false);
        }else{
            txt_descripcion_area.setEnabled(true);
        }
    }//GEN-LAST:event_btn_buscar_responsablActionPerformed

    private void btn_buscar_responsablMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_buscar_responsablMouseClicked
        // TODO add your handling code here:
        /**new Buscar_Recurso_Humano(this, true).setVisible(true);
        buscarRRHH.buscar(); //se llama al formulario de busqueda de recurso humano*/
    }//GEN-LAST:event_btn_buscar_responsablMouseClicked

    private void btn_guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_guardarActionPerformed
        save=true;
        validarCampos();
    }//GEN-LAST:event_btn_guardarActionPerformed

    private void btn_buscar_areaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_buscar_areaActionPerformed
        buscar();
    }//GEN-LAST:event_btn_buscar_areaActionPerformed

    private void btn_modificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_modificarActionPerformed
        //activa boton actualizar
        // botón editar, carga los datos de la tabla en los jtext desactiva el boton de guardar
        if (rowData != null) { //se ejecutará solo si rowData contiene datos del registro a modifica
            txt_cedula_persona.setText(rowData[2].toString());
            txt_nombre_persona.setText(rowData[3].toString()+" "+rowData[4].toString());
            txt_descripcion_area.setText(rowData[1].toString());
            bander_area=rowData[1].toString();
            btn_actualizar.setEnabled(true);//habilitamos el boton de actualizar
            btn_guardar.setEnabled(false);// inabilitamos el boton de guardar
            btn_modificar.setEnabled(false);// "" el boton de modificar
            areaActual =rowData[0].toString();
            
            if (conexion.crearConexion()) { //si hay conexion se ejecuta
                String sql ="select id_area from tmaearecon where descripcion_area='"+txt_descripcion_area.getText()+"' and tipo_area ='"+areaActual+"' ;"; //se crea el sql para los datos modificacion
                ResultSet rs = conexion.ejecutarSQLSelect(sql);//ejecutamos el sql
                try{
                    while (rs.next()){//mientras el result set tenga datos
                    idArea = rs.getString(1); //guardamos el id del área en la variable global, para tener access luego
                    }
                }catch(Exception e){
                    
                }
            }
                    
            rowData = null; //limpiamos los datos del array de objetos
        }
    }//GEN-LAST:event_btn_modificarActionPerformed

    private void btn_eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eliminarActionPerformed
        // eliminar objeto seleccionado
            int respuesta = mensajes.msg_Pregunta(this, "Está seguro que desea eliminar el área seleccionada"); // se pide confirmación de la acción 
            if (respuesta == 0) { // si la respuesta es si

                if (conexion.crearConexion()) { //comprobamos conexion
                    String sql = "select id_area from tmaearecon where descripcion_area='" + rowData[1].toString() + "' and tipo_area ='" + rowData[0].toString() + "' ;"; //formamos el sql para obtener el id del área que queremos eliminar

                    ResultSet rs = conexion.ejecutarSQLSelect(sql); //lo mandamos a ejecutar, guardando el mensaje del procedimiento
                    try {
                        while (rs.next()) {//mientras exista un dato
                            idArea = rs.getString(1); //guardamos el id del area antes
                            String sqlBorrar = "select * from eliminar_TMAEARECON (" + idArea + ");";//con este id realizamos la eliminación con el sql
                            ResultSet resultado = conexion.ejecutarSQLSelect(sqlBorrar);//mandamos a ejecutar el sql

                            while (resultado.next()) {//mientras exista un datos
                                if (resultado.getInt(1) == 1) {//si nos devolvió 1 se pudo eliminar
                                    mensajes.msg_confirmacion(this, "Área eliminada con éxito");//si muestra el mensaje
                                    txt_cedula_persona.setText("");
                                    txt_nombre_persona.setText("");
                                    txt_descripcion_area.setText("");
                                    table_area.setModel(new DefaultTableModel());
                                } else {
                                    mensajes.msg_advertencia(this, "El área no se pudo eliminar, compruebe que no está ligada a un documento de asignación");//se muestra el mensaje
                                }
                            }

                        }
                    } catch (Exception e) {

                    }
                }
                btn_eliminar.setEnabled(false);//se desabilida el boton eliminar
            }
    }//GEN-LAST:event_btn_eliminarActionPerformed

    private void btn_actualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_actualizarActionPerformed
        save=false;
        validarCampos();
    }//GEN-LAST:event_btn_actualizarActionPerformed

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
            java.util.logging.Logger.getLogger(FRM_ControlAreas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FRM_ControlAreas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FRM_ControlAreas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FRM_ControlAreas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FRM_ControlAreas dialog = new FRM_ControlAreas(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btn_buscar_area;
    private javax.swing.JButton btn_buscar_responsabl;
    private javax.swing.JButton btn_buscar_responsable;
    private javax.swing.JButton btn_busqueda_area;
    private javax.swing.JButton btn_eliminar;
    private javax.swing.JButton btn_guardar;
    private javax.swing.JButton btn_modificar;
    private javax.swing.JButton btn_nuevo;
    private javax.swing.JButton btn_regresar;
    private javax.swing.JComboBox combo_busqueda_tipo_area;
    private javax.swing.JComboBox combo_tipo_area;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTable table_area;
    private javax.swing.JTextField txt_busqueda_nombre_persona;
    private javax.swing.JTextField txt_cedula_persona;
    private javax.swing.JTextField txt_descripcion_area;
    private javax.swing.JTextField txt_nombre_persona;
    // End of variables declaration//GEN-END:variables
}
