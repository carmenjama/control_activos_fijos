package CapaInterfaces;

import static CapaInterfaces.FRM_RegistroDocumentoAsignacion.var_codigooficio;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;
import javax.swing.table.DefaultTableModel;
import Capa_ConexionBD.Conexion;
import Capa_Mensajes.Mensajes;
import java.awt.Event;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.print.PrinterException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.InputMap;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;

public class FRM_BusquedaActivoResponsable extends javax.swing.JDialog {
    Mensajes msj = new Mensajes();
    /**
     * Crea Nuevo Formulario BusquedaActivoResponsable
     */
    public FRM_BusquedaActivoResponsable(java.awt.Frame parent, boolean modal) {//Constructor
        super(parent, modal);//Se inician todos los componentes del form según se requiera
        initComponents();
        this.getContentPane().setLayout (new GridBagLayout());
        this.setLocationRelativeTo(null);
        tabla_activos_responsable.getTableHeader().setReorderingAllowed(false);  
        combo_oficio.removeAllItems();
        combo_oficio.setEnabled(false);
        check_oficio.setEnabled(false);
    }
    
    //Bloquea acciones de copiar y pegar del teclado
    public void bloquearTeclas(){
        InputMap map1 = txt_responsable.getInputMap(txt_responsable.WHEN_FOCUSED);
        map1.put(KeyStroke.getKeyStroke(KeyEvent.VK_V, Event.CTRL_MASK), "null");
    }
    
    Conexion conexion = new Conexion();//Nuevo objeto de conexión con la BD
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        txt_responsable = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabla_activos_responsable = new javax.swing.JTable();
        check_oficio = new javax.swing.JCheckBox();
        combo_oficio = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        btn_buscar_responsable = new javax.swing.JButton();
        jToolBar2 = new javax.swing.JToolBar();
        btn_nuevo = new javax.swing.JButton();
        btn_actualizar = new javax.swing.JButton();
        btn_imprimir = new javax.swing.JButton();
        btn_regresar = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        btn_eliminar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Búsqueda de Activo por Responsable");
        setResizable(false);

        txt_responsable.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        txt_responsable.setNextFocusableComponent(combo_oficio);
        txt_responsable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_responsableActionPerformed(evt);
            }
        });
        txt_responsable.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_responsableKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_responsableKeyTyped(evt);
            }
        });

        tabla_activos_responsable.setModel(new javax.swing.table.DefaultTableModel(
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
        tabla_activos_responsable.removeColumn(tabla_activos_responsable.getColumnModel().getColumn(0));
        tabla_activos_responsable.setNextFocusableComponent(btn_eliminar);
        tabla_activos_responsable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tabla_activos_responsable.getTableHeader().setReorderingAllowed(false);
        tabla_activos_responsable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabla_activos_responsableMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tabla_activos_responsable);

        check_oficio.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        check_oficio.setText("Oficio");
        check_oficio.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                check_oficioStateChanged(evt);
            }
        });
        check_oficio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                check_oficioMouseClicked(evt);
            }
        });
        check_oficio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                check_oficioActionPerformed(evt);
            }
        });

        combo_oficio.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        combo_oficio.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccionar...", "Item 1", "Item 2", "Item 3", "Item 4" }));
        combo_oficio.setNextFocusableComponent(btn_actualizar);
        combo_oficio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_oficioActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel2.setText("Responsable ");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 21)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Búsqueda Activos por Responsable");

        btn_buscar_responsable.setBorder(null);
        btn_buscar_responsable.setBorderPainted(false);
        btn_buscar_responsable.setContentAreaFilled(false);

        jToolBar2.setBackground(new java.awt.Color(117, 214, 255));
        jToolBar2.setFloatable(false);
        jToolBar2.setRollover(true);

        btn_nuevo.setBackground(new java.awt.Color(117, 214, 255));
        btn_nuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/add-new-file (1).png"))); // NOI18N
        btn_nuevo.setText("   Nuevo   ");
        btn_nuevo.setFocusable(false);
        btn_nuevo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_nuevo.setNextFocusableComponent(txt_responsable);
        btn_nuevo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_nuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nuevoActionPerformed(evt);
            }
        });
        jToolBar2.add(btn_nuevo);

        btn_actualizar.setBackground(new java.awt.Color(117, 214, 255));
        btn_actualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/refresh-page-arrow-button (1).png"))); // NOI18N
        btn_actualizar.setText("  Actualizar Asignación   ");
        btn_actualizar.setFocusable(false);
        btn_actualizar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_actualizar.setNextFocusableComponent(btn_nuevo);
        btn_actualizar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_actualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_actualizarActionPerformed(evt);
            }
        });
        jToolBar2.add(btn_actualizar);

        btn_imprimir.setBackground(new java.awt.Color(117, 214, 255));
        btn_imprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/printer- (1).png"))); // NOI18N
        btn_imprimir.setText("   Imprimir   ");
        btn_imprimir.setFocusable(false);
        btn_imprimir.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_imprimir.setNextFocusableComponent(txt_responsable);
        btn_imprimir.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_imprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_imprimirActionPerformed(evt);
            }
        });
        jToolBar2.add(btn_imprimir);

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
        jToolBar2.add(btn_regresar);

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

        jPanel8.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btn_eliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/1/rubbish-bin (1).png"))); // NOI18N
        btn_eliminar.setText("Desvincular");
        btn_eliminar.setContentAreaFilled(false);
        btn_eliminar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_eliminar.setNextFocusableComponent(tabla_activos_responsable);
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
            .addComponent(btn_eliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_eliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(122, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(643, 643, 643)
                                .addComponent(btn_buscar_responsable))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txt_responsable, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(check_oficio)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(combo_oficio, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 694, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jToolBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_buscar_responsable)
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txt_responsable, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(check_oficio)
                    .addComponent(combo_oficio, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        txt_responsable.getAccessibleContext().setAccessibleName("txt_Responsable");
        jScrollPane3.getAccessibleContext().setAccessibleName("Tabla_Activo_Responsable");
        check_oficio.getAccessibleContext().setAccessibleName("checkb_Oficio");
        combo_oficio.getAccessibleContext().setAccessibleName("cb_Oficio");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(1, 1, 1))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    Integer numeroColumnas = 11;
    
    public void cargarComboOficio(){//Método para cargar el comboBox de Oficio
        combo_oficio.removeAllItems();//Primero se vacía el combo box
        ArrayList<String> resultat;//Creamos las listas de array que necesitaremos
        ArrayList<String> ls = new ArrayList<String>();
        String valResponsable = txt_responsable.getText().toString();//Se obtiene el valor del JTextField de Responsable
        String sql = "select distinct codigooficio_asigactivo from tmovdascon as a inner join tmaearecon as b on (a.idarea_asigactivo = b.id_area)\n" +
                     "inner join tmovrehcon as c on (b.idresponsable_area = c.id_rrhh) inner join tmaepercon as d on (c.idpersona_rrhh = d.id_persona) "
                     + "where (d.nombre_persona like '"+valResponsable+"%' and a.id_asigactivo > 2) or (d.nombre_persona like '"+valResponsable+"%' and a.id_asigactivo > 2);";
        //Se crea el query necesario para obtener los resultados a mostrar en el comboBox
         if (!valResponsable.equals("")) {
           ResultSet rs = conexion.ejecutarSQLSelect(sql);//Ejecutamos el query y lo guardamos en un ResultSet
                try {
                     while(rs.next()){//Mientras haya registros
                         
                         ls.add(rs.getString("codigooficio_asigactivo"));//Se obtendrá el siguiente registro del campo mencionado
                     }               //y se lo agrega a la lista de array -> ls
                 } catch (SQLException ex) {//Si no se puede con el try obtener resultados
                     
                 }
                 resultat = ls;//La consulta tiene que retornar un ArrayList
                 
                 for(int i=0; i<resultat.size();i++){// Se recorre la lista mientras las iteraciones sean menores a su tamaño
                     combo_oficio.addItem(resultat.get(i));//Se obtendrá el objeto en la lista según su posición y será agregado al comboBox
                 } 
        }
    }
    
    public void busquedaResponsable(){  //Método para buscar activos por responsables
        String valResponsable = txt_responsable.getText().toString(); //Se obtiene el valor del JTextField de Responsable     
        if (conexion.crearConexion() && !valResponsable.equals("")) { //Si se puede hacer la conexión y el texto de Responsable no está vacío           
            String[] titulos = {"Id","Tipo","Marca", "Procesador", "Memoria", "Disco Duro", "Modelo", "Serie", "Costo", "Fecha Compra", "Código Interno"};
            DefaultTableModel modelo = new DefaultTableModel (null, titulos); //Se crea un vector String para titulos de table y con ellos un nuevo modelo de tabla
            Object[] fila = new Object[11];//Se crea un vector de tipo Objeto
            String sql = "select  id_activo, idtipo_activo, marca_activo, precesador_acrtivo, memoria_activo, discoduro_activo, modelo_activo, \n" +
                         "serie_activo, costo_activo, fechacompra_activo, codigointernoinstitucional_activo from tmovactcon as a \n" +
                         "inner join tmovdascon as b on (a.iddocasignacion_activo = b.id_asigactivo)\n" +
                         "inner join tmaearecon as c on (b.idarea_asigactivo = c.id_area)\n" +
                         "inner join tmovrehcon as d on (c.idresponsable_area = d.id_rrhh)\n" +
                         "inner join tmaepercon as e on (d.idpersona_rrhh = e.id_persona) where (e.apellido_persona like '"+valResponsable+"%' and b.id_asigactivo > 2) or (e.nombre_persona like '"+valResponsable+"%' and b.id_asigactivo > 2) order by a.id_activo asc;";
                        try{//Se crea el query para hacer la búsqueda de activos
                            ResultSet rs = conexion.ejecutarSQLSelect(sql);//Se ejecuta el query
                            while(rs.next()){//Mientras exista registros
                                  for (int i = 1; i <= numeroColumnas; i++) {    //Se seguirá recorriendo mientras el número de i sea menor a las columnas que queremos                                  
                                    fila[i - 1] = rs.getObject(i) ;   //Se obtiene el objeto n´mero i de rs y se lo guarda en el vector fila en la posición i-1                                   
                                } 
                            modelo.addRow(fila);//Se agrega la fila al modelo de la tabla
                            }
                            tabla_activos_responsable.setModel(modelo);  //Se coloca el modelo de la tabla en la tabla                          
                        }catch(Exception ex){//Si no se obtiene resultados en el try

                            
                        }
        }   
    }
    
    public void eliminarAsignación(){//Método para quitar la asignación de un activo a un responsable
        if (!idActivo.equals("")) {//Si el objeto idActivo no está vacío
            if (JOptionPane.showConfirmDialog(rootPane, "¿Desea realmente desasignar el activo de su responsable?",//Se muestra un cuadro de dialogo
                "confirmar acción", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {//Pidiendo confirmación, si es afirmativo
                if (conexion.crearConexion()) {//Se verifica la conexión
                    String sql2 = "select distinct b.codigooficio_asigactivo from tmovactcon as a inner join tmovdascon as b on (a.iddocasignacion_activo = b.id_asigactivo) where id_asigactivo = (select iddocasignacion_activo from tmovactcon where id_activo = "+Integer.parseInt(idActivo)+") and id_asigactivo > 2;";
                    ResultSet rs2 = conexion.ejecutarSQLSelect(sql2); //Se ejecuta el query    
                    String codActivo = "";//Se inicializa la variable codActivo
                    try {                           
                           if (rs2.next() == true) {//Si hay al menos un registro
                               codActivo = rs2.getString("codigooficio_asigactivo");  //Se guarda la consulta en un string                             
                            } 
                        } catch (SQLException ex) {
                            Logger.getLogger(FRM_RegistroDocumentoAsignacion.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    //Se crea el query para actualizar la asignación del activo
                    String sql = "update tmovactcon set iddocasignacion_activo = 2 where id_activo = "+idActivo+";";//Se crea el query para la consulta
                    conexion.ejecutarSQL(sql);//Se realiza el update
                    msj.msg_confirmacion(this,"El activo ya no tiene responsable asignado.");//Se confirma que el activo ya no está asignado                                       
                    ResultSet rs3 = conexion.ejecutarSQLSelect(sql2); //se ejecuta el query de consulta sql2                         
                    try {                           
                           if (rs3.next() == false) {//Si no hay al menos un registro
                               //Se crea la sentencia para eliminar el codigo de oficio de asignación 
                                   String sql1 = "delete from tmovdascon where codigooficio_asigactivo = '"+codActivo+"';";
                                   conexion.ejecutarSQL(sql1);//Se ejecuta el query
                            } 
                        } catch (SQLException ex) {
                            Logger.getLogger(FRM_RegistroDocumentoAsignacion.class.getName()).log(Level.SEVERE, null, ex);
                        }
                }
                idActivo = "";//Se coloca vacía la variable idActivo
                codDocAsigActivo = "";//Se vacía la variable
                DefaultTableModel tablaResponsable = (DefaultTableModel) tabla_activos_responsable.getModel();//Se obtiene el modelo de la tabla 
                tablaResponsable.removeRow(tabla_activos_responsable.getSelectedRow()); //Se remueve la fila que se desasignó
                cargarComboOficio();            //Se vuelve a cargar el combo de oficio    
                    combo_oficio.setEnabled(false);//se coloca disable el combo oficio
                    check_oficio.setEnabled(false);//se coloca disable el check oficio
                    check_oficio.setSelected(false);//Se desmarca el check oficio
                
            }                       
        } else{//Si idActivo está vacío
            JOptionPane.showMessageDialog(rootPane,"No ha seleccionado ningún activo");//Se muestra el mensaje que no se ha seleccionado activos
        }
    }
    
    private void txt_responsableKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_responsableKeyTyped
        //Validación del campo de Responsable
        Character c = evt.getKeyChar();//Se captura la tecla presionada
                if(Character.isLetter(c) || c == KeyEvent.VK_SPACE || c == KeyEvent.VK_BACK_SPACE) {//Si el caracter es una letra, espacio o la tecla de borrar
                    evt.setKeyChar(c);  //Se coloca el caracter
                }else{
                        evt.consume(); //Cualquier otro caracter no será ingresado al campo
                }    
    }//GEN-LAST:event_txt_responsableKeyTyped

    private void btn_actualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_actualizarActionPerformed
        if (!check_oficio.isSelected() || combo_oficio.getItemCount() <= 0) {//Se verifica que la seleccion sea valida
            msj.msg_advertencia(this, "No ha seleccionado ningún oficio.");//En caso de no serlo se mostrará el mensaje
        } else {//Si la opción es válida
            String sql2 = "select id_asigactivo from TMOVDASCON where codigooficio_asigactivo='" + combo_oficio.getSelectedItem().toString()+"'";
            ResultSet rs2 = conexion.ejecutarSQLSelect(sql2); //Se ejecuta el query
            try {
                rs2.next();
                FRM_RegistroDocumentoAsignacion.id_codoficio = rs2.getInt("id_asigactivo");
            } catch (SQLException ex) {
                Logger.getLogger(FRM_RegistroDocumentoAsignacion.class.getName()).log(Level.SEVERE, null, ex);
            }
            String oficio = combo_oficio.getSelectedItem().toString();  //Se obtiene el texto del Item seleccionado y se guarda              
            FRM_RegistroDocumentoAsignacion.bandera = 2;
            FRM_RegistroDocumentoAsignacion.var_codigooficio = oficio;
            dispose();
            new FRM_RegistroDocumentoAsignacion(this, true, oficio).setVisible(true);  //Se abre el nuevo formulario y se envía el dato requerido              */
        }
    }//GEN-LAST:event_btn_actualizarActionPerformed

    private void btn_regresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_regresarActionPerformed
        if(msj.msg_Pregunta(this, "¿Desea salir de la búsqueda de activos por responsable?")==0){
            dispose();//Se cierra el formulario
        }
    }//GEN-LAST:event_btn_regresarActionPerformed

    private void btn_nuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nuevoActionPerformed
        String[] titulos ={"Id","Tipo","Marca", "Procesador", "Memoria", "Disco Duro", "Modelo", "Serie", "Costo", "Fecha Compra", "Código Interno"};
        DefaultTableModel modelo = new DefaultTableModel (null, titulos); 
        tabla_activos_responsable.setModel(modelo);//Se limpia el Jtable 
        idActivo = ""; //Se vacían las variable
        codDocAsigActivo = "";
    }//GEN-LAST:event_btn_nuevoActionPerformed

    private void combo_oficioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_oficioActionPerformed
        
    }//GEN-LAST:event_combo_oficioActionPerformed

    private void btn_eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eliminarActionPerformed
        eliminarAsignación();//Se llama al método para eliminar una asignación
    }//GEN-LAST:event_btn_eliminarActionPerformed

    private void check_oficioStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_check_oficioStateChanged
        if (check_oficio.isSelected()) {//Si el check_oficio está seleccionado
            combo_oficio.setEnabled(true);//El combo_oficio saldrá activado
            cargarComboOficio();//Se cargarán los datos al combo box
        }
        else{//Caso contrario
            combo_oficio.setEnabled(false);//El combo_ofico se pondrá desactivado
            combo_oficio.removeAllItems();//Se removerán todos los datos del combo
        }
    }//GEN-LAST:event_check_oficioStateChanged

    private void check_oficioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_check_oficioActionPerformed

    }//GEN-LAST:event_check_oficioActionPerformed

    private void check_oficioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_check_oficioMouseClicked

    }//GEN-LAST:event_check_oficioMouseClicked

    String idActivo = "";//Se crea el objeto idActivo
    String codDocAsigActivo = "";

    private void tabla_activos_responsableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabla_activos_responsableMouseClicked
            JTable table =(JTable) evt.getSource();//Crea el evento para la tabla
            idActivo = "";
            codDocAsigActivo = "";
            Point point = evt.getPoint();//Para obtener posición del mouse
            int row = table.rowAtPoint(point);//Para saber en que fila se hizo click            
            if (evt.getClickCount() == 1) {//Si se ha dado un click
                idActivo = tabla_activos_responsable.getValueAt(tabla_activos_responsable.getSelectedRow(), 0).toString();                
            } //Se obtiene el valor de la columna 0 de la fila seleccionada y se guarda en idActivo              
            
    }//GEN-LAST:event_tabla_activos_responsableMouseClicked

    private void btn_imprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_imprimirActionPerformed
        if (tabla_activos_responsable.getRowCount() > 0) {//Si hay datos en la tabla
            try {
                tabla_activos_responsable.print();//envia los datos de la tabla a la impresora
            } catch (PrinterException ex) { //Si no se cumple nada en el try mostrará el error
                Logger.getLogger(FRM_BusquedaActivoResponsable.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
             msj.msg_advertencia(this,"No hay datos para imprimir.");//Si no hay datos se moestrará el mensaje
        }
    }//GEN-LAST:event_btn_imprimirActionPerformed

    private void txt_responsableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_responsableActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_responsableActionPerformed

    private void txt_responsableKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_responsableKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (txt_responsable.getText().equals("")) {//Se verifica si el textField de Responsable está vacío
                String[] titulos = {"Id", "Tipo", "Marca", "Procesador", "Memoria", "Disco Duro", "Modelo", "Serie", "Costo", "Fecha Compra", "Código Interno"};
                DefaultTableModel modelo = new DefaultTableModel(null, titulos); //Se crea un vector String para titulos de table y con ellos un nuevo modelo de tabla
                tabla_activos_responsable.setModel(modelo);//Se coloca el modelo en la tabla
            }
            busquedaResponsable(); //Se ejecuta el método para buscar responsable
            cargarComboOficio();//Se carga el combo oficio
            check_oficio.setEnabled(true);
        }
    }//GEN-LAST:event_txt_responsableKeyPressed

   
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
            java.util.logging.Logger.getLogger(FRM_BusquedaActivoResponsable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FRM_BusquedaActivoResponsable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FRM_BusquedaActivoResponsable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FRM_BusquedaActivoResponsable.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FRM_BusquedaActivoResponsable dialog = new FRM_BusquedaActivoResponsable(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btn_buscar_responsable;
    private javax.swing.JButton btn_eliminar;
    private javax.swing.JButton btn_imprimir;
    private javax.swing.JButton btn_nuevo;
    private javax.swing.JButton btn_regresar;
    private javax.swing.JCheckBox check_oficio;
    private javax.swing.JComboBox combo_oficio;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JTable tabla_activos_responsable;
    private javax.swing.JTextField txt_responsable;
    // End of variables declaration//GEN-END:variables
}
