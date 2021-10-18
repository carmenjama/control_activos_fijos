package CapaInterfaces;

import static CapaInterfaces.FRM_ControlSolicitudesMantenimiento.tabla_solicitudes_mantenimiento;
import static CapaInterfaces.FRM_RegistroActivos.sp_costo_activo;
import static CapaInterfaces.FRM_RegistroActivos.txt_codigo_institucional;
import static CapaInterfaces.FRM_RegistroActivos.txt_detalle_activo;
import static CapaInterfaces.FRM_RegistroActivos.txt_fecha_compra;
import static CapaInterfaces.FRM_RegistroActivos.txt_modelo_activo;
import static CapaInterfaces.FRM_RegistroActivos.txt_serie_activo;
import static CapaInterfaces.FRM_RegistroSolicitudMantenimiento.datos_ConSolMant;
import static CapaInterfaces.FRM_RegistroSolicitudMantenimiento.tipoTransaccion_ConSolMant;
import java.awt.Event;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.InputMap;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableModel;

public class FRM_RegistroSolicitudMantenimiento extends javax.swing.JDialog {
    Objetos.Control_Solicitud_Mantenimiento control_Solicitud_Mantenimiento = new Objetos.Control_Solicitud_Mantenimiento();
    Capa_Validaciones.Validaciones validaciones = new Capa_Validaciones.Validaciones();
    Capa_ConexionBD.Conexion conexion = new Capa_ConexionBD.Conexion();
    Capa_Mensajes.Mensajes msj = new Capa_Mensajes.Mensajes(); 
    DefaultTableModel model;
    SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");    
    public static String[][] datos_tabla;      
    public static int numero_filas;      
    public static int cod_hablitar;
    public static int estado_Solicitud;    
    public static String[][][] var1;  
    //Modificar registro de solicitudes        
    int id_solicitud;    
    public static boolean save=false;
    public static int id_solicitud_modificar;
    public static String datos_ConSolMant[];
    public static  int tipoTransaccion_ConSolMant;
    public static int [] id_activo;

//constructores globales para obtener datos de otros formularios
    public FRM_RegistroSolicitudMantenimiento(String [] datos_ConSolMant,int tipoTransaccion_ConSolMant,int id_solicitud_modificar) {
        this.datos_ConSolMant = datos_ConSolMant;
        this.cod_hablitar = tipoTransaccion_ConSolMant;
        this.id_solicitud_modificar = id_solicitud_modificar;
    }    
    
    public FRM_RegistroSolicitudMantenimiento(int [] id_activo,String[][]  datos_tabla,int numero_filas,int cod_hablitar) {
        this.datos_tabla = datos_tabla;
        this.numero_filas = numero_filas;
        this.cod_hablitar = cod_hablitar;
        this.id_activo = id_activo;
    }             
    
    public FRM_RegistroSolicitudMantenimiento(int estado_Solicitud) {
        this.estado_Solicitud = estado_Solicitud;
    } 
    
    public FRM_RegistroSolicitudMantenimiento(String[][][] var1) {        
        this.var1 = var1;
    }    
    
//inicio de los omponentes del formulario
    public FRM_RegistroSolicitudMantenimiento(java.awt.Dialog parent, boolean modal) {
        super(parent, modal);
        initComponents();
        date_fecha_docSolicitud.getDateEditor().setEnabled(false);
        validaciones.validaciondefecha(date_fecha_docSolicitud);
        this.setLocationRelativeTo(null);
        btn_eliminar.setEnabled(false);
        this.setDefaultCloseOperation(0);
        bloquearTeclas();
        //this.getContentPane().setLayout (new GridBagLayout());
        date_fecha_docSolicitud.setMaxSelectableDate(new java.util.Date());
        //date_fecha_docSolicitud.setMinSelectableDate(new Date(1980,01,01));
        tabla_solicitud_mantenimiento.getTableHeader().setReorderingAllowed(false);
        if(cod_hablitar == 1){
            btn_actualizar.setEnabled(true);
            btn_guardar.setEnabled(false);
            txt_idDocSolicitud_detalleSolicitudMant.setEnabled(false);
            llenarTabla_ControlSolMant();            
            cod_hablitar=0;
        }else{
            btn_guardar.setEnabled(true);
            btn_actualizar.setEnabled(false);
            llenar_tabla();
            llenar_campos();
        }       
        
    }
    
    public void bloquearTeclas() {
        InputMap map1 = txt_idDocSolicitud_detalleSolicitudMant.getInputMap(txt_idDocSolicitud_detalleSolicitudMant.WHEN_FOCUSED);
        map1.put(KeyStroke.getKeyStroke(KeyEvent.VK_V, Event.CTRL_MASK), "null");
        InputMap map2 = txt_motivo_docSolicitud.getInputMap(txt_motivo_docSolicitud.WHEN_FOCUSED);
        map2.put(KeyStroke.getKeyStroke(KeyEvent.VK_V, Event.CTRL_MASK), "null");
    }
    
    //Riden
    /*Llenar validaciones en comun con la creacion de solicitud de mantenimiento*/
    public void llenarTabla_ControlSolMant(){
        if (tipoTransaccion_ConSolMant == 0) {
            Date fecha_docSolicitud = null;            
            try {
                fecha_docSolicitud = formatoFecha.parse(datos_ConSolMant[3]);
            } catch (ParseException ex) {
                Logger.getLogger(FRM_RegistroSolicitudMantenimiento.class.getName()).log(Level.SEVERE, null, ex);
            }
            txt_cedula_persona.setText(datos_ConSolMant[0]);
            txt_nombre_persona.setText(datos_ConSolMant[1]);
            txt_idDocSolicitud_detalleSolicitudMant.setText(datos_ConSolMant[2]);
            date_fecha_docSolicitud.setDate(fecha_docSolicitud);
            txt_motivo_docSolicitud.setText(datos_ConSolMant[4]);   
            llenarActivos_RegSolMant(Integer.parseInt(datos_ConSolMant[5]));
            
        }
    }
    public void llenarActivos_RegSolMant(int idDoc_SolMant){
        String [] datos_tablaActivos = new String [9];
        String sql = "select act.id_activo,tact.nombre_tipoactivo,act.marca_activo,act.precesador_acrtivo, \n"
                + "act.memoria_activo,act.discoduro_activo,act.modelo_activo,act.serie_activo, d.codigooficio_docsolicitud \n"
                + "from tmaepercon p, tmovrehcon rh,tmovdoscon d,TMOVDTSCON dt,TMOVACTCON act,TMAETACCON tact \n"
                + "where p.id_persona = rh.idpersona_rrhh and d.idsolicitante_docsolicitud = rh.id_rrhh \n"
                + "and d.id_docsolicitud=dt.iddocsolicitud_detallesolicitudmant and act.id_activo = dt.idactivo_detallesolicitudmant \n"
                + "and tact.id_tipoactivo = act.idtipo_activo and d.id_docsolicitud="+idDoc_SolMant+"";        
        conexion.crearConexion();
        ResultSet rs = conexion.ejecutarSQLSelect(sql);
        String[] nombresTabla = {"Id", "Tipo", "Marca", "Procesador", "Memoria", "Disco Duro","Modelo","Serie","Cod. Institucion"};
        model = new DefaultTableModel(null, nombresTabla);        
        try {
            while (rs.next()) {                 
                datos_tablaActivos[0] = rs.getString(1);
                datos_tablaActivos[1] = rs.getString(2);
                datos_tablaActivos[2] = rs.getString(3);
                datos_tablaActivos[3] = rs.getString(4);
                datos_tablaActivos[4] = rs.getString(5);                
                datos_tablaActivos[5] = rs.getString(6);
                datos_tablaActivos[6] = rs.getString(7);
                datos_tablaActivos[7] = rs.getString(8);
                datos_tablaActivos[8] = rs.getString(9);
                model.addRow(datos_tablaActivos);
                 tabla_solicitud_mantenimiento.setModel(model);
            }
        } catch (Exception ex) {
            System.out.println("llenar activos");
        }      
    }
    
    /*Llenar validaciones en comun con la creacion de solicitud de mantenimiento*/
    //Riden
    
    
//Métodos aplicados para el formulario Registro_Solicitud_Mantenimiento
    
    //Se obtienen los datos de el formulario Solicitar_Mantenimiento
    public void llenar_campos(){
        txt_cedula_persona.setText(var1[0][0][1]);
        txt_nombre_persona.setText(var1[0][0][2]);
    }
    
    //Se ingresan los datos que se filtraron de la tabla del formulario anterior
    public void llenar_tabla() {
        String[] var1 = new String[11];
        model = (DefaultTableModel) tabla_solicitud_mantenimiento.getModel();        
        try {
            int filas = 0;
            while (filas != numero_filas) {                            
                var1[1] = datos_tabla[filas][0];
                var1[2] = datos_tabla[filas][1];
                var1[3] = datos_tabla[filas][2];
                var1[4] = datos_tabla[filas][3];
                var1[5] = datos_tabla[filas][4];
                var1[6] = datos_tabla[filas][5];
                var1[7] = datos_tabla[filas][6];
                var1[8] = datos_tabla[filas][7];
                var1[9] = datos_tabla[filas][8];
                var1[10] = datos_tabla[filas][9];
                model.addRow(var1);
                filas++;
            }
        } catch (Exception ex) {
            System.out.println("error");
        }

    }     
    
    //Se realiza un insert a la tabla de documento solicitud con los parametros del formulario
    public void actualizar_solicitud() {
        conexion.crearConexion();
        int filas = 0;       
        String sql = "update tmovdoscon set fecha_docsolicitud = '"+date_fecha_docSolicitud.getDate()+"'"
                   + ",fechaentrega_docsolicitud='0001-01-01',identregante_docsolicitud = 1,tipomantps_docsolicitud = 1,"
                   + "motivo_docsolicitud = '"+txt_motivo_docSolicitud.getText().toString()+"',estado_docsolicitud = 0 "
                   + "where id_docsolicitud ="+id_solicitud_modificar;
        while (filas != numero_filas) {
            String sql1 = "update tmovactcon set estado_activo = 1 where id_activo = " + datos_tabla[filas][0] + "";
            conexion.ejecutarSQLSelect(sql1);            
            filas++;
        }      
        msj.msg_confirmacion(rootPane,"Solicitud Modificada con éxito");
        try {
            ResultSet result = conexion.ejecutarSQLSelect(sql);
            if (result.next()) {
                id_solicitud = result.getInt(1);
            }
        } catch (Exception ex) {
            System.out.println("error");
        }
    }
    
    public void Registrar_solicitud(){
        conexion.crearConexion();                
        int filas =0;
        String sql = "insert into tmovdoscon "
                   + "(codigooficio_docsolicitud,fecha_docsolicitud,fechaentrega_docsolicitud,"
                   + "identregante_docsolicitud,idsolicitante_docsolicitud,tipomantps_docsolicitud,"
                   + "tipoge_mantactivo,motivo_docsolicitud,estado_docsolicitud) "
                   + "values ('"+txt_idDocSolicitud_detalleSolicitudMant.getText().toString()+"'"
                   + ",'"+date_fecha_docSolicitud.getDate()+"',"
                   + "'0001-01-01','1','"+var1[0][0][0]+"',1,'"+estado_Solicitud+"','"+txt_motivo_docSolicitud.getText().toString()+"',0)"
                   + "RETURNING id_docsolicitud";        
        while (filas != numero_filas){
        String sql1 = "update tmovactcon set estado_activo = 1 where id_activo = "+datos_tabla[filas][0]+"";
        conexion.ejecutarSQLSelect(sql1);
        filas++;
        }
            try {                       
        ResultSet result = conexion.ejecutarSQLSelect(sql);        
        if(result.next()){
            id_solicitud = result.getInt(1);            
        }
        } catch (Exception ex) {
            System.out.println("error");
        }                       
        Registrar_detalle_por_activo();                 
    }
    
    public void Registrar_detalle_por_activo() {
        conexion.crearConexion();
        int filas = 0; 
        try {
            while (filas != numero_filas) {
                String sql = "insert into tmovdtscon \n"
                        + "(iddocsolicitud_detallesolicitudmant,idactivo_detallesolicitudmant)\n"
                        + "values\n"
                        + "("+id_solicitud+","+id_activo[filas]+") ";
                ResultSet result = conexion.ejecutarSQLSelect(sql);
               filas++; 
            }
        } catch (Exception ex) {
            System.out.println("error");
        }
         FRM_RegistroEncargadoMantenimiento rdem = new FRM_RegistroEncargadoMantenimiento(id_solicitud);  
    }
    
    public void Validar_Registro_Unico() {
        conexion.crearConexion();
        int filas = 0,valor = 0;        
            String sql = "select  count(codigooficio_docsolicitud) from tmovdoscon "
                       + "where codigooficio_docsolicitud='"+
                       txt_idDocSolicitud_detalleSolicitudMant.getText().toString()+"'";
            try {               
            ResultSet result = conexion.ejecutarSQLSelect(sql);
            if(result.next()){
                  valor = result.getInt(1);                 
            }            
            if(valor == 1){
                msj.msg_advertencia(this, "Ya éxiste un registro con este código, ingrese nuevo dato u modifique el existente.");
                txt_idDocSolicitud_detalleSolicitudMant.setText("");
                txt_idDocSolicitud_detalleSolicitudMant.setFocusable(true);
            }
        } catch (Exception ex) {
            System.out.println("error");
        }
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        label1 = new java.awt.Label();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla_solicitud_mantenimiento = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txt_cedula_persona = new javax.swing.JTextField();
        txt_nombre_persona = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        btn_editar_activosMantenimiento = new javax.swing.JButton();
        btn_eliminar_activosMantenimiento = new javax.swing.JButton();
        btn_seleccionar_todo = new javax.swing.JButton();
        jToolBar1 = new javax.swing.JToolBar();
        btn_nuevo = new javax.swing.JButton();
        btn_guardar = new javax.swing.JButton();
        btn_actualizar = new javax.swing.JButton();
        btn_regresar = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        txt_idDocSolicitud_detalleSolicitudMant = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        date_fecha_docSolicitud = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        btn_eliminar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        txt_motivo_docSolicitud = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Registro de Solicitud de Mantenimiento");
        setResizable(false);

        jButton1.setBorder(null);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jButton2.setBorder(null);
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jButton4.setBorder(null);
        jButton4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jButton5.setBorder(null);
        jButton5.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        label1.setAlignment(java.awt.Label.CENTER);
        label1.setFont(new java.awt.Font("Tahoma", 1, 21)); // NOI18N
        label1.setText("Solicitud de Mantenimiento");

        tabla_solicitud_mantenimiento.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        tabla_solicitud_mantenimiento.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Tipo", "Marca", "Procesador", "Memoria", "Disco Duro", "Modelo", "Serie", "Costo", "Fecha Compra", "Cod. Institucional"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabla_solicitud_mantenimiento.setNextFocusableComponent(btn_eliminar);
        tabla_solicitud_mantenimiento.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tabla_solicitud_mantenimiento.removeColumn(tabla_solicitud_mantenimiento.getColumnModel().getColumn(0));
        tabla_solicitud_mantenimiento.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabla_solicitud_mantenimientoMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabla_solicitud_mantenimiento);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Detalles Búsqueda", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 17))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel1.setText("Cédula");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel2.setText("Nombres y Apellidos");

        txt_cedula_persona.setEnabled(false);

        txt_nombre_persona.setEnabled(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_cedula_persona, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_nombre_persona)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(txt_cedula_persona, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2))
                    .addComponent(txt_nombre_persona, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        btn_editar_activosMantenimiento.setToolTipText("");
        btn_editar_activosMantenimiento.setBorder(null);
        btn_editar_activosMantenimiento.setBorderPainted(false);
        btn_editar_activosMantenimiento.setContentAreaFilled(false);

        btn_eliminar_activosMantenimiento.setBorder(null);
        btn_eliminar_activosMantenimiento.setBorderPainted(false);
        btn_eliminar_activosMantenimiento.setContentAreaFilled(false);

        btn_seleccionar_todo.setBorder(null);
        btn_seleccionar_todo.setBorderPainted(false);
        btn_seleccionar_todo.setContentAreaFilled(false);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_editar_activosMantenimiento)
                    .addComponent(btn_eliminar_activosMantenimiento)
                    .addComponent(btn_seleccionar_todo))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_editar_activosMantenimiento)
                .addGap(18, 18, 18)
                .addComponent(btn_eliminar_activosMantenimiento)
                .addGap(18, 18, 18)
                .addComponent(btn_seleccionar_todo)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jToolBar1.setBackground(new java.awt.Color(117, 214, 255));
        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        btn_nuevo.setBackground(new java.awt.Color(117, 214, 255));
        btn_nuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/add-new-file (1).png"))); // NOI18N
        btn_nuevo.setText("   Nuevo   ");
        btn_nuevo.setFocusable(false);
        btn_nuevo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_nuevo.setNextFocusableComponent(txt_idDocSolicitud_detalleSolicitudMant);
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

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel3.setText("Código de oficio");

        txt_idDocSolicitud_detalleSolicitudMant.setNextFocusableComponent(date_fecha_docSolicitud);
        txt_idDocSolicitud_detalleSolicitudMant.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_idDocSolicitud_detalleSolicitudMantFocusLost(evt);
            }
        });
        txt_idDocSolicitud_detalleSolicitudMant.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_idDocSolicitud_detalleSolicitudMantKeyTyped(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel4.setText("Fecha de Solicitud");

        date_fecha_docSolicitud.setMinSelectableDate(new java.util.Date(315554497000L));
        date_fecha_docSolicitud.setNextFocusableComponent(txt_motivo_docSolicitud);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel5.setText("Detalle");

        jPanel8.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btn_eliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/1/rubbish-bin (1).png"))); // NOI18N
        btn_eliminar.setText("Eliminar");
        btn_eliminar.setContentAreaFilled(false);
        btn_eliminar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_eliminar.setNextFocusableComponent(tabla_solicitud_mantenimiento);
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
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_eliminar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_eliminar)
                .addContainerGap(102, Short.MAX_VALUE))
        );

        txt_motivo_docSolicitud.setColumns(20);
        txt_motivo_docSolicitud.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        txt_motivo_docSolicitud.setRows(5);
        txt_motivo_docSolicitud.setNextFocusableComponent(btn_guardar);
        txt_motivo_docSolicitud.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_motivo_docSolicitudKeyTyped(evt);
            }
        });
        jScrollPane2.setViewportView(txt_motivo_docSolicitud);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_idDocSolicitud_detalleSolicitudMant, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(date_fecha_docSolicitud, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(5, 5, 5))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane2))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jButton1)
                                .addGap(772, 772, 772)
                                .addComponent(jButton2)
                                .addGap(18, 18, 18)
                                .addComponent(jButton4)
                                .addGap(18, 18, 18)
                                .addComponent(jButton5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(txt_idDocSolicitud_detalleSolicitudMant, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4))
                    .addComponent(date_fecha_docSolicitud, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(340, 340, 340)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton5)
                    .addComponent(jButton4)
                    .addComponent(jButton2)
                    .addComponent(jButton1))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 643, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_regresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_regresarActionPerformed
        if(msj.msg_Pregunta(this, "Desea cerrar registro de solicitud de mantenimiento?")==0){
            if(save==true){
                dispose();
                new FRM_SolicitarMantenimiento(null, true).setVisible(true);
            }else{
                dispose();
                new FRM_ControlSolicitudesMantenimiento(null, true).setVisible(true);
            }
            
        }
    }//GEN-LAST:event_btn_regresarActionPerformed

    private void btn_guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_guardarActionPerformed
        if(txt_idDocSolicitud_detalleSolicitudMant.getText().trim().equals("")){
            msj.msg_advertencia(this, "Ingrese código institucional.");
            txt_idDocSolicitud_detalleSolicitudMant.setText("");
            txt_idDocSolicitud_detalleSolicitudMant.requestFocus();
        }else{
            if(date_fecha_docSolicitud.getCalendar()==null){
                msj.msg_advertencia(this, "Ingrese fecha de solicitud.");
                date_fecha_docSolicitud.requestFocus();
            }else{
                if(txt_motivo_docSolicitud.getText().trim().equals("")){
                    msj.msg_advertencia(this, "Ingrese detalle de solicitud de mantenimiento.");
                    txt_motivo_docSolicitud.requestFocus();
                }else{
                    if(tabla_solicitud_mantenimiento.getRowCount()>0){
                        Registrar_solicitud();        
                        String a = "          !Solicitud guardada con éxito¡\n"
                                 + "¿Dese Envir a mantenimiento los activos?";
                        int resp = msj.msg_Pregunta(this, a);
                        if (resp == 0){
                            FRM_RegistroEncargadoMantenimiento.save=true;
                            new FRM_RegistroEncargadoMantenimiento(this, true).setVisible(true);
                        }else{
                            msj.msg_confirmacion(this, "Su solicitud esta pendiente.");
                        }
                        btn_guardar.setEnabled(false);
                        btn_nuevo.setEnabled(false);
                    }else{
                        msj.msg_advertencia(this, "La tabla de activos debe de poseer al menos \nun registro para poder realizar una solicitud.");
                    }
                }
            }
        }
    }//GEN-LAST:event_btn_guardarActionPerformed
   
    private void btn_eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eliminarActionPerformed
      DefaultTableModel dtm = (DefaultTableModel) tabla_solicitud_mantenimiento.getModel();
      dtm.removeRow(tabla_solicitud_mantenimiento.getSelectedRow()); 
    }//GEN-LAST:event_btn_eliminarActionPerformed

    private void txt_idDocSolicitud_detalleSolicitudMantFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_idDocSolicitud_detalleSolicitudMantFocusLost
        Validar_Registro_Unico();
    }//GEN-LAST:event_txt_idDocSolicitud_detalleSolicitudMantFocusLost

    private void btn_actualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_actualizarActionPerformed
        if(tabla_solicitud_mantenimiento.getRowCount()>0){
            actualizar_solicitud();
        }else{
            msj.msg_advertencia(this, "La tabla de activos debe de poseer al menos \nun registro para poder realizar una solicitud.");
        }
    }//GEN-LAST:event_btn_actualizarActionPerformed

    private void txt_motivo_docSolicitudKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_motivo_docSolicitudKeyTyped
      char c=evt.getKeyChar(); 
        String var = "'";
        if(txt_motivo_docSolicitud.getText().length()>500){
            evt.consume();
        }else{
            if (Character.toString(c).equals(var)){
                evt.consume();
            }
        }
    }//GEN-LAST:event_txt_motivo_docSolicitudKeyTyped

    private void txt_idDocSolicitud_detalleSolicitudMantKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_idDocSolicitud_detalleSolicitudMantKeyTyped
         char c=evt.getKeyChar(); 
        String var0 = "'";
        char var1 = '-';
        char var2 = 'ñ';
        char var3 = 'Ñ';
        if(txt_idDocSolicitud_detalleSolicitudMant.getText().length()>200){
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
    }//GEN-LAST:event_txt_idDocSolicitud_detalleSolicitudMantKeyTyped

    private void btn_nuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nuevoActionPerformed
        if(txt_idDocSolicitud_detalleSolicitudMant.getText().equals("")==true && date_fecha_docSolicitud.getDate()==null && txt_motivo_docSolicitud.getText().equals("")==true){
            
        }else{
            if(msj.msg_Pregunta(this, "Los datos que no ha guardado se borraran.\n¿Esta seguro de crear un nuevo registro?")==0){
                txt_idDocSolicitud_detalleSolicitudMant.setText("");
                date_fecha_docSolicitud.setDate(null);
                txt_motivo_docSolicitud.setText("");
            }
        }
    }//GEN-LAST:event_btn_nuevoActionPerformed

    private void tabla_solicitud_mantenimientoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabla_solicitud_mantenimientoMouseClicked
        if(save==true){
            btn_eliminar.setEnabled(true);
        }else{
            btn_eliminar.setEnabled(false);
        }
    }//GEN-LAST:event_tabla_solicitud_mantenimientoMouseClicked

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
            java.util.logging.Logger.getLogger(FRM_RegistroSolicitudMantenimiento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FRM_RegistroSolicitudMantenimiento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FRM_RegistroSolicitudMantenimiento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FRM_RegistroSolicitudMantenimiento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FRM_RegistroSolicitudMantenimiento dialog = new FRM_RegistroSolicitudMantenimiento(new javax.swing.JDialog(), true);
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
    private javax.swing.JButton btn_editar_activosMantenimiento;
    private javax.swing.JButton btn_eliminar;
    private javax.swing.JButton btn_eliminar_activosMantenimiento;
    private javax.swing.JButton btn_guardar;
    private javax.swing.JButton btn_nuevo;
    private javax.swing.JButton btn_regresar;
    private javax.swing.JButton btn_seleccionar_todo;
    private com.toedter.calendar.JDateChooser date_fecha_docSolicitud;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JToolBar jToolBar1;
    private java.awt.Label label1;
    private javax.swing.JTable tabla_solicitud_mantenimiento;
    private javax.swing.JTextField txt_cedula_persona;
    private javax.swing.JTextField txt_idDocSolicitud_detalleSolicitudMant;
    private javax.swing.JTextArea txt_motivo_docSolicitud;
    private javax.swing.JTextField txt_nombre_persona;
    // End of variables declaration//GEN-END:variables
}
