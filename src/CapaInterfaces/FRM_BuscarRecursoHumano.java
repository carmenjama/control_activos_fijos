/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package CapaInterfaces;
import java.awt.GridBagLayout;
import Capa_ConexionBD.Conexion;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import CapaInterfaces.FRM_ControlRecursosHumanos;
import Capa_Mensajes.Mensajes;
import java.awt.Event;
import javax.swing.InputMap;
import javax.swing.KeyStroke;

/**
 *
 * @author Dayanna Luna
 */
public class FRM_BuscarRecursoHumano extends javax.swing.JDialog {
    Object[] rowData;
    
    Conexion conexion = new Conexion();
    ButtonGroup group = new ButtonGroup();
    FRM_ControlRecursosHumanos controlrrhh = new FRM_ControlRecursosHumanos(null, true);
    Mensajes mensajes = new Mensajes();
    int id_persona=0;
    
    
    /**
     * Creates new form Buscar_Recurso_Humano
     */
    public FRM_BuscarRecursoHumano(java.awt.Dialog parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.getContentPane().setLayout(new GridBagLayout());
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(0);
        tabla_persona.getTableHeader().setReorderingAllowed(false);
        
        btn_modificar2.setEnabled(false);
        btn_eliminar.setEnabled(false);
        txt_cedula_persona.setEnabled(false);
        txt_nombre_persona.setEnabled(false);
        tabla_persona.getTableHeader().setReorderingAllowed(false);  
        conexion.crearConexion();
        bloquearTeclas();
    }
    
    //Bloquea acciones de copiar y pegar del teclado
    public void bloquearTeclas(){
        InputMap map1 = txt_cedula_persona.getInputMap(txt_cedula_persona.WHEN_FOCUSED);
        map1.put(KeyStroke.getKeyStroke(KeyEvent.VK_V, Event.CTRL_MASK), "null");
        InputMap map2 = txt_nombre_persona.getInputMap(txt_nombre_persona.WHEN_FOCUSED);
        map2.put(KeyStroke.getKeyStroke(KeyEvent.VK_V, Event.CTRL_MASK), "null");
    }
    
    public void buscar() {
        
        String nombrePersona = txt_nombre_persona.getText();
        String cedulaPersona = txt_cedula_persona.getText();
        
        boolean isSelected = jRadioButton1.isSelected();
        boolean isSelected2 = jRadioButton2.isSelected();
        
        if (isSelected) {
            jRadioButton1.setSelected(true);
            String sql="select * from TMAEPERCON where cedula_persona like '%"+cedulaPersona+"%' and id_persona>5 and ((select count (*) from tmaeusucon where id_persona=(select id_persona from tmaepercon where cedula_persona='"+cedulaPersona+"'))=0);";
            ResultSet rs = conexion.ejecutarSQLSelect(sql);
            ActualizarTabla(tabla_persona, rs);
        }
        else{ if (isSelected2) {
            jRadioButton2.setSelected(true);
            String sql="select * from TMAEPERCON where id_persona>5 and (nombre_persona like '%"+nombrePersona+"%' or apellido_persona like '%"+nombrePersona+"%') and ((select count (*) from tmaeusucon where id_persona=(select id_persona from tmaepercon where cedula_persona='"+cedulaPersona+"'))=0);";
            ResultSet rs = conexion.ejecutarSQLSelect(sql);
            ActualizarTabla(tabla_persona, rs);
        }
        }
    };
    
    public void ActualizarTabla(JTable jtabla, ResultSet rs) {
        DefaultTableModel modelo = new DefaultTableModel(); //Creo un modelo de datos para un jtable
        jtabla.setModel(modelo); //le asigno a la tabla el modelo de             //datos
        try {
            //creo 3 columnas con sus etiquetas
            //estas son las columnas del JTable
            modelo.addColumn("Cédula");
            modelo.addColumn("Nombres");
            modelo.addColumn("Apellidos");
            modelo.addColumn("Correo");
            modelo.addColumn("id");
            tabla_persona.getColumnModel().getColumn(4).setMinWidth(0);
            tabla_persona.getColumnModel().getColumn(4).setMaxWidth(0);
            tabla_persona.getColumnModel().getColumn(4).setWidth(0);
            
            //Recorro el ResultSet que contiene los resultados.
            while (rs.next()) {
                Object[] ob = new Object[5]; //Crea un vector
                
                ob[0] = (rs.getString(2));
                ob[1] = (rs.getString(3));
                ob[2] = (rs.getString(4));
                ob[3] = (rs.getString(5));
                ob[4] = (rs.getInt(1));
                
                modelo.addRow(ob); ob = null; //limpia los datos de el vector de la memoria
            }
            rs.close(); //Cierra el ResultSet
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    };
    
    public void borrarCampos() {
        txt_cedula_persona.setText("");
        txt_nombre_persona.setText("");
        DefaultTableModel temp = (DefaultTableModel) tabla_persona.getModel();
        int a =temp.getRowCount()-1;
        for(int i=0; i<=a; i++)
            temp.removeRow(0);
    };
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabla_persona = new javax.swing.JTable();
        txt_cedula_persona = new javax.swing.JTextField();
        txt_nombre_persona = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        btn_modificar2 = new javax.swing.JButton();
        btn_eliminar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jToolBar1 = new javax.swing.JToolBar();
        btn_nuevo = new javax.swing.JButton();
        btn_regresar = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Buscar Recurso Humano");
        setResizable(false);

        jPanel2.setBackground(new java.awt.Color(232, 232, 232));
        jPanel2.setToolTipText("Buscar Recurso Humano");
        jPanel2.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jPanel2MouseMoved(evt);
            }
        });

        jRadioButton1.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jRadioButton1.setLabel("Cédula");
        jRadioButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton1ActionPerformed(evt);
            }
        });

        jRadioButton2.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jRadioButton2.setText("Nombres y Apellidos");
        jRadioButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton2ActionPerformed(evt);
            }
        });

        tabla_persona.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Cédula", "Nombres", "Apellidos", "Correo"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabla_persona.setNextFocusableComponent(btn_modificar2);
        tabla_persona.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tabla_persona.removeColumn(tabla_persona.getColumnModel().getColumn(0));
        tabla_persona.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabla_personaMouseClicked(evt);
            }
        });
        tabla_persona.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tabla_personaKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tabla_persona);

        txt_cedula_persona.setNextFocusableComponent(txt_nombre_persona);
        txt_cedula_persona.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_cedula_personaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_cedula_personaKeyTyped(evt);
            }
        });

        txt_nombre_persona.setNextFocusableComponent(txt_cedula_persona);
        txt_nombre_persona.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_nombre_personaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_nombre_personaKeyTyped(evt);
            }
        });

        jPanel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btn_modificar2.setBackground(new java.awt.Color(255, 255, 255));
        btn_modificar2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/1/pencil.png"))); // NOI18N
        btn_modificar2.setText("Editar");
        btn_modificar2.setToolTipText("");
        btn_modificar2.setContentAreaFilled(false);
        btn_modificar2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btn_modificar2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_modificar2.setNextFocusableComponent(btn_eliminar);
        btn_modificar2.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btn_modificar2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_modificar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_modificar2ActionPerformed(evt);
            }
        });

        btn_eliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/1/rubbish-bin (1).png"))); // NOI18N
        btn_eliminar.setText("Eliminar");
        btn_eliminar.setContentAreaFilled(false);
        btn_eliminar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_eliminar.setNextFocusableComponent(tabla_persona);
        btn_eliminar.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btn_eliminar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_eliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_eliminar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_modificar2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_modificar2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_eliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 21)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Búsqueda de Recurso Humano");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jRadioButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_cedula_persona, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jRadioButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_nombre_persona, javax.swing.GroupLayout.DEFAULT_SIZE, 293, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel1)
                .addGap(30, 30, 30)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButton1)
                    .addComponent(txt_cedula_persona, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jRadioButton2)
                    .addComponent(txt_nombre_persona, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jRadioButton1.getAccessibleContext().setAccessibleName("rb_cedula");

        jToolBar1.setBackground(new java.awt.Color(117, 214, 255));
        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        btn_nuevo.setBackground(new java.awt.Color(117, 214, 255));
        btn_nuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/add-new-file (1).png"))); // NOI18N
        btn_nuevo.setText("   Nuevo   ");
        btn_nuevo.setFocusable(false);
        btn_nuevo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_nuevo.setNextFocusableComponent(txt_cedula_persona);
        btn_nuevo.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_nuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_nuevoActionPerformed(evt);
            }
        });
        jToolBar1.add(btn_nuevo);

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

        jPanel8.setBackground(new java.awt.Color(68, 69, 69));

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 15, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_regresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_regresarActionPerformed
        dispose();
        controlrrhh.save=true;
        controlrrhh.borrarCampos();
        controlrrhh.cargarLista();
        controlrrhh.setVisible(true);
        }//GEN-LAST:event_btn_regresarActionPerformed

    private void txt_nombre_personaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nombre_personaKeyTyped
        // TODO add your handling code here:
        Character c = evt.getKeyChar();
        if (txt_nombre_persona.getText().trim().length() > 200) {
            evt.consume();
        } else {
            if (Character.isLetter(c) || c == KeyEvent.VK_SPACE) {
                evt.setKeyChar(c);
            } else if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
                buscar();
            } else {
                evt.consume();
            }
        }
        
    }//GEN-LAST:event_txt_nombre_personaKeyTyped

    private void txt_cedula_personaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_cedula_personaKeyTyped
        // TODO add your handling code here:
        Character c = evt.getKeyChar();
        if (txt_cedula_persona.getText().trim().length() > 9) {
            evt.consume();
        } else {
            if (Character.isDigit(c)) {
                evt.setKeyChar(c);
            } else if (evt.getKeyChar() == KeyEvent.VK_ENTER) {
                buscar();
            } else {
                evt.consume();
            }
        }
    }//GEN-LAST:event_txt_cedula_personaKeyTyped

    private void jPanel2MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseMoved

    }//GEN-LAST:event_jPanel2MouseMoved

    private void jRadioButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton1ActionPerformed
        group.add(jRadioButton1);group.add(jRadioButton2);
        txt_nombre_persona.setEnabled(false);
        txt_cedula_persona.setEnabled(true);
    }//GEN-LAST:event_jRadioButton1ActionPerformed

    private void tabla_personaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabla_personaMouseClicked
        btn_eliminar.setEnabled(true);
        btn_modificar2.setEnabled(true);
        rowData = new Object[tabla_persona.getColumnCount()];
        int mostrar = 0;
        JTable table = (JTable) evt.getSource();
        Point point = evt.getPoint();
        int row = table.rowAtPoint(point);
        
        if (evt.getClickCount() == 1) {
            for (int i = 0; i < table.getColumnCount(); i++) {
                rowData[i] = table.getValueAt(row, i);
            }
        }
    }//GEN-LAST:event_tabla_personaMouseClicked

    private void btn_nuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nuevoActionPerformed
        borrarCampos();
    }//GEN-LAST:event_btn_nuevoActionPerformed

    private void btn_eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eliminarActionPerformed
        if(tabla_persona.getSelectedRow()<0){
            mensajes.msg_advertencia(this, "Debe seleccionar de seleccionar un registro.");
        }else{
            if(mensajes.msg_Pregunta(this, "¿Está seguro que desea eliminar recurso humano?")==0){
                controlrrhh.loadData(rowData);
                controlrrhh.elmininarRrhh();
                controlrrhh.borrarCampos();
                controlrrhh.cargarLista();
                borrarCampos();
            }
        }
    }//GEN-LAST:event_btn_eliminarActionPerformed

    private void txt_nombre_personaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nombre_personaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            buscar();
        }
    }//GEN-LAST:event_txt_nombre_personaKeyPressed

    private void txt_cedula_personaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_cedula_personaKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            buscar();
        }
    }//GEN-LAST:event_txt_cedula_personaKeyPressed

    private void btn_modificar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_modificar2ActionPerformed
        if(tabla_persona.getSelectedRow()<0){
            mensajes.msg_advertencia(this, "Debe seleccionar de seleccionar un registro.");
        }else{
            controlrrhh.btn_guardar.setEnabled(false);// inabilitamos el boton de guardar
            controlrrhh.btn_actualizar.setEnabled(true);
            controlrrhh.loadData(rowData);
            dispose();
            controlrrhh.setVisible(true);
        }
    }//GEN-LAST:event_btn_modificar2ActionPerformed

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton2ActionPerformed
        group.add(jRadioButton1);group.add(jRadioButton2);
        txt_nombre_persona.setEnabled(true);
        txt_cedula_persona.setEnabled(false);
    }//GEN-LAST:event_jRadioButton2ActionPerformed

    private void tabla_personaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tabla_personaKeyReleased
        try {
            if (evt.getKeyCode() == 38 || evt.getKeyCode() == 40) {
                btn_eliminar.setEnabled(true);
                btn_modificar2.setEnabled(true);
                rowData = new Object[tabla_persona.getColumnCount()];
                int mostrar = 0;
                JTable table = (JTable) evt.getSource();
                int fila = tabla_persona.getRowCount();
                for (int i = 0; i < fila; i++) {
                    rowData[0] = table.getValueAt(i,0);
                    rowData[1] = table.getValueAt(i,1);
                    rowData[2] = table.getValueAt(i,2);
                    rowData[3] = table.getValueAt(i,3);
                    rowData[4] = table.getValueAt(i,4);
                }
            }
        } catch (Exception ex) {

        }
    }//GEN-LAST:event_tabla_personaKeyReleased
    
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
            for (javax.swing.UIManager.LookAndFeelInfo info: javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FRM_BuscarRecursoHumano.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FRM_BuscarRecursoHumano.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FRM_BuscarRecursoHumano.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FRM_BuscarRecursoHumano.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FRM_BuscarRecursoHumano dialog = new FRM_BuscarRecursoHumano(new javax.swing.JDialog(), true);
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
    private javax.swing.JButton btn_eliminar;
    public static javax.swing.JButton btn_modificar2;
    private javax.swing.JButton btn_nuevo;
    private javax.swing.JButton btn_regresar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTable tabla_persona;
    private javax.swing.JTextField txt_cedula_persona;
    private javax.swing.JTextField txt_nombre_persona;
    // End of variables declaration//GEN-END:variables
}