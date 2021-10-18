/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CapaInterfaces;

import Capa_ConexionBD.Conexion;
import java.awt.GridBagLayout;
import Capa_Validaciones.Validaciones;
import Capa_Mensajes.Mensajes;
import java.awt.Event;
import java.sql.ResultSet;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import javax.swing.InputMap;
import javax.swing.KeyStroke;

/**
 *
 * @author Erick
 */
public class FRM_ControlRecursosHumanos extends javax.swing.JDialog {

    int idGlobal = 0;
    //Inicializa objetos a utilizar para conexion, validaciones y mensajes
    Conexion conexion = new Conexion();
    Validaciones validaciones = new Validaciones();
    Mensajes mensajes = new Mensajes();
    public static String tipo = "";
    //Variable utilizada para controlar si se guarda o actualiza un registro
    boolean save = false;

    //Variables a utilizar
    String tipo_usuario, cedula_persona, nombres_persona, apellidos_persona,
            correo_persona, nombre_usuario, clave_usuario, confirmarClave_usuario;

    /**
     * Creates new form prueba
     */
    public FRM_ControlRecursosHumanos(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(null);
        this.getContentPane().setLayout(new GridBagLayout());
        this.setDefaultCloseOperation(0);
        bloquearTeclas();
        //Inicializa botones o texto bloqueados por defecto
        conexion.crearConexion();
        cargarLista();
        txt_clave_usuario.setEnabled(false);
        txt_confirmeclave_usuario.setEnabled(false);
    }

    public void bloquearTeclas() {
        InputMap map1 = txt_cedula.getInputMap(txt_cedula.WHEN_FOCUSED);
        map1.put(KeyStroke.getKeyStroke(KeyEvent.VK_V, Event.CTRL_MASK), "null");
        InputMap map2 = txt_nombres.getInputMap(txt_nombres.WHEN_FOCUSED);
        map2.put(KeyStroke.getKeyStroke(KeyEvent.VK_V, Event.CTRL_MASK), "null");
        InputMap map3 = txt_apellidos.getInputMap(txt_apellidos.WHEN_FOCUSED);
        map3.put(KeyStroke.getKeyStroke(KeyEvent.VK_V, Event.CTRL_MASK), "null");
        InputMap map4 = txt_correo.getInputMap(txt_correo.WHEN_FOCUSED);
        map4.put(KeyStroke.getKeyStroke(KeyEvent.VK_V, Event.CTRL_MASK), "null");
        InputMap map5 = txt_clave_usuario.getInputMap(txt_clave_usuario.WHEN_FOCUSED);
        map5.put(KeyStroke.getKeyStroke(KeyEvent.VK_V, Event.CTRL_MASK), "null");
        InputMap map6 = txt_confirmeclave_usuario.getInputMap(txt_confirmeclave_usuario.WHEN_FOCUSED);
        map6.put(KeyStroke.getKeyStroke(KeyEvent.VK_V, Event.CTRL_MASK), "null");
    }

    //Cargar listas de acuerdo a si el usuario administrador está o ingresado en el sistema
    public void cargarLista() {
        btn_actualizar.setEnabled(false);
        txt_nombre_usuario.setEnabled(false);
        txt_clave_usuario.setEnabled(false);
        txt_confirmeclave_usuario.setEnabled(false);
        String[] lista1 = {"Docente", "Administrativo", "Mantenimiento", "admin_mantenimiento"};
        String[] lista2 = {"Docente", "Administrativo", "Mantenimiento"};
        try {
            String sql = "select count(*) from TMAEUSUCON where nombre_usuario = 'admin_mantenimiento'";
            ResultSet rs = conexion.ejecutarSQLSelect(sql);
            rs.next();
            int total = rs.getInt(1);
            if (total > 0) {
                //Se carga la lista para el caso de que el usuario administrador de mantenimiento no exista
                //y desbloquea los respectivos controles
                combo_tipo_usuario.setModel(new javax.swing.DefaultComboBoxModel(lista2));
                txt_nombre_usuario.setText("");
                txt_clave_usuario.setText("");
                txt_confirmeclave_usuario.setText("");
                txt_clave_usuario.setEnabled(false);
                txt_confirmeclave_usuario.setEnabled(false);
            } else {
                //Bloquea los controles necesarios
                combo_tipo_usuario.setModel(new javax.swing.DefaultComboBoxModel(lista1));
                txt_confirmeclave_usuario.setEnabled(true);
                txt_clave_usuario.setEnabled(true);
                txt_clave_usuario.setText("");
                txt_confirmeclave_usuario.setText("");
            }
            rs.close();
        } catch (Exception e) {
        }
    }

    //Recoge los valores de los campos
    public void recogerValores() {
        tipo_usuario = combo_tipo_usuario.getSelectedItem().toString().trim();
        cedula_persona = txt_cedula.getText().trim();
        nombres_persona = txt_nombres.getText().trim();
        apellidos_persona = txt_apellidos.getText().trim();
        correo_persona = txt_correo.getText().trim();
        nombre_usuario = txt_nombre_usuario.getText().trim();
        clave_usuario = txt_clave_usuario.getText().trim();
        confirmarClave_usuario = txt_confirmeclave_usuario.getText();
    }

    //Valida si hay campos vacios o no para enviar mensaje de comprobacion de salida de formulario
    public void nuevoBuscar() {
        recogerValores();
        if (tipo_usuario.equals("admin_mantenimiento")) {
            nombre_usuario = txt_nombre_usuario.getText().trim();
            clave_usuario = txt_clave_usuario.getText().trim();
            confirmarClave_usuario = txt_confirmeclave_usuario.getText().trim();
        }
        //Primro se valida por el caso de que sea un registro del tipo administradoe de mantenimiento
        if (cedula_persona.length() == 0 && nombres_persona.equals("") && apellidos_persona.equals("") && correo_persona.equals("") && tipo_usuario.equals("admin_mantenimiento") == true) {
            if (clave_usuario.equals("") && confirmarClave_usuario.equals("")) {
                //Preguntamos si se va crear un nuevo registro o buscar
                if (save == true) {
                    //Si se presiona nuevo limpia los campos y carga lista de tipo RRHH
                    borrarCampos();
                    cargarLista();
                } else {
                    //Si por el contrario se va a buscar, se cierra el formulario y se abre el de Buscar Recurso Humano
                    this.setVisible(false);
                    new FRM_BuscarRecursoHumano(this, true).setVisible(true);
                }
            } else {
                //Solo muestran los mensajes en caso de que haya ingresado algun valor en los campos
                if (save == true) {
                    //En caso de que se presione el boton de nuevo
                    if (mensajes.msg_Pregunta(this, "Los datos que no ha guardado se borraran.\n¿Esta seguro de crear un nuevo registro?") == 0) {
                        borrarCampos();
                        cargarLista();
                    }
                } else {
                    //Cuando se presiona buscar
                    if (mensajes.msg_Pregunta(this, "Los datos que no ha guardado se borraran.\n¿Esta seguro de que desea buscar un registro?") == 0) {
                        //Si se responde si, se cierra el formulario y se abre el de Buscar Recurso Humano
                        this.setVisible(false);
                        new FRM_BuscarRecursoHumano(this, true).setVisible(true);
                    }
                }
            }
        } else {
            //Si son tipos diferentes de administrador de mantenimiento
            if (cedula_persona.equals("") && nombres_persona.equals("") && apellidos_persona.equals("") && correo_persona.equals("")) {
                if (save == true) {
                    //Si se presiona nuevo limpia los campos y carga lista de tipo RRHH
                    borrarCampos();
                    cargarLista();
                } else {
                    //Si por el contrario se va a buscar, se cierra el formulario y se abre el de Buscar Recurso Humano
                    this.setVisible(false);
                    new FRM_BuscarRecursoHumano(this, true).setVisible(true);
                }
            } else {
                //Solo muesta este mensaje si el usuario ha ingresado algun valor
                if (save == true) {
                    //En caso de que se presione el boton de nuevo
                    if (mensajes.msg_Pregunta(this, "Los datos que no ha guardado se borraran.\n¿Esta seguro de crear un nuevo registro?") == 0) {
                        borrarCampos();
                        cargarLista();
                    }
                } else {
                    //Cuando se presiona buscar
                    if (mensajes.msg_Pregunta(this, "Los datos que no ha guardado se borraran.\n¿Esta seguro de que desea buscar un registro?") == 0) {
                        //Si se responde si, se cierra el formulario y se abre el de Buscar Recurso Humano
                        this.setVisible(false);
                        new FRM_BuscarRecursoHumano(this, true).setVisible(true);
                    }
                }
            }
        }
        combo_tipo_usuario.requestFocus();
    }

    //Devuelve todos los valore en blanco
    public void borrarCampos() {
        txt_cedula.setText("");
        txt_nombres.setText("");
        txt_apellidos.setText("");
        txt_correo.setText("");
        txt_nombre_usuario.setText("");
        txt_clave_usuario.setText("");
        txt_confirmeclave_usuario.setText("");
        txt_cedula.setEnabled(true);
        btn_actualizar.setEnabled(true);
    }

    //Habilita campos de acuerdo a lo que se ha seleccionado en el tipo de RRHH
    public void habilitarCampos() {
        if (combo_tipo_usuario.getSelectedItem().equals("admin_mantenimiento")) {
            txt_nombre_usuario.setText("admin_mantenimiento");
            txt_clave_usuario.setEnabled(true);
            txt_confirmeclave_usuario.setEnabled(true);
        } else {
            txt_nombre_usuario.setText("");
            txt_confirmeclave_usuario.setEnabled(false);
            txt_clave_usuario.setEnabled(false);
        }
        txt_clave_usuario.setText("");
        txt_confirmeclave_usuario.setText("");
    }

    //Controla la existencia de una cedula en la BD
    public boolean validdarExistenciaCedula() {
        boolean cedula_existe = false;
        try {
            //SQL que realiza la consulta
            String sql = "select count(*) from TMAEPERCON where cedula_persona = '" + cedula_persona.toString() + "'";
            ResultSet rs = conexion.ejecutarSQLSelect(sql);
            rs.next();
            int total = rs.getInt(1);
            if (total > 0) {
                //Si la cedula existe se envia el foto al text respectivo
                mensajes.msg_advertencia(this, "Ya existe un registro con este número de cédula.\nIngese nuevo dato o modifique el existente.");
                txt_cedula.requestFocus();
                cedula_existe = true;
            }
            rs.close();
        } catch (Exception e) {
        }
        return cedula_existe;
    }

    //Valida que los campos sean correctos para ingresar o actualizar
    public void validarCampos() {
        recogerValores();
        if (cedula_persona.length() == 0) {
            mensajes.msg_advertencia(this, "Ingrese cédula de persona.");
            txt_cedula.requestFocus();
        } else {
            //Llama a metodo para comprobar que la cedula sea correcta
            /*if (validaciones.validadorDeCedula1(this, txt_cedula.getText()) == false) {//Cambiar a true
                txt_cedula.requestFocus();
            } else {*/
                if (save == false) {
                    validarCamposDos();
                } else {
                    if (validdarExistenciaCedula() == false) {
                        if (nombres_persona.equals("")) {
                            mensajes.msg_advertencia(this, "Ingrese nombre de recurso humano.");
                            txt_nombres.setText("");
                            txt_nombres.requestFocus();
                            validarCamposDos();
                        } else {
                            validarCamposDos();
                        }
                    }
                }
            //}
        }
    }

    public void validarCamposDos() {
        if (apellidos_persona.equals("")) {
            mensajes.msg_advertencia(this, "Ingrese apellidos de recurso humano.");
            txt_apellidos.setText("");
            txt_apellidos.requestFocus();
        } else {
            if (correo_persona.equals("")) {
                mensajes.msg_advertencia(this, "Ingrese correo de persona.");
                txt_correo.setText("");
                txt_correo.requestFocus();
            } else {
                //Llama a metodo para comprobar que el email es correcto
                if (validaciones.validarEmail(correo_persona) == false) {
                    mensajes.msg_advertencia(this, "Ingrese correo válido.");
                    txt_correo.requestFocus();
                } else {
                    if (tipo_usuario.equals("admin_mantenimiento")) {
                        //Si el tipo de RRRHH comprueba que estos campos esten ingresados
                        if (clave_usuario.equals("") || confirmarClave_usuario.equals("")) {
                            mensajes.msg_advertencia(this, "Claves de acceso no pueden estar vacias, ingrese datos.");
                            txt_clave_usuario.setText("");
                            txt_confirmeclave_usuario.setText("");
                            txt_clave_usuario.requestFocus();
                        } else {
                            if (clave_usuario.equals(confirmarClave_usuario) == false) {
                                mensajes.msg_advertencia(this, "Claves de acceso no coinciden, por favor inténte de nuevo.");
                                txt_clave_usuario.setText("");
                                txt_confirmeclave_usuario.setText("");
                                txt_clave_usuario.requestFocus();
                            } else {
                                if (validaciones.ComplejidadClave(this, clave_usuario) == false) {
                                    txt_clave_usuario.setText("");
                                    txt_confirmeclave_usuario.setText("");
                                    txt_clave_usuario.requestFocus();
                                } else {
                                    //Si los campos pasan todas las validaciones se procecede a guardar o eliminar segun sea el caso
                                    if (save == true) {
                                        insertarRRHH();
                                        txt_clave_usuario.setEnabled(false);
                                        txt_confirmeclave_usuario.setEnabled(false);
                                    } else {
                                        actualizarRRHH();
                                        txt_clave_usuario.setEnabled(false);
                                        txt_confirmeclave_usuario.setEnabled(false);
                                    }
                                }
                            }
                        }
                    } else {
                        //Si los campos pasan todas las validaciones se procecede a guardar o eliminr segun sea el caso
                        if (save == true) {
                            insertarRRHH();
                            txt_clave_usuario.setEnabled(false);
                            txt_confirmeclave_usuario.setEnabled(false);
                        } else {
                            actualizarRRHH();
                            txt_clave_usuario.setEnabled(false);
                            txt_confirmeclave_usuario.setEnabled(false);
                        }
                    }
                }
            }
        }
    }

    //Ejecuta funcion de postgrest para guardar el recurso humano y el usuario de sea ese el caso
    protected void insertarRRHH() {
        try {
            String sql2 = "select * from INSERTAR_TMOVREHCON2('" + tipo_usuario + "', '" + cedula_persona.toString() + "', '" + nombres_persona + "', '" + apellidos_persona + "', '" + correo_persona + "', '" + nombre_usuario + "', '" + clave_usuario + "', null);";
            conexion.ejecutarSQLSelect(sql2);
            mensajes.msg_confirmacion(this, "Registro guardado con éxito.");
            borrarCampos();
            cargarLista();
        } catch (Exception e) {

        }
        combo_tipo_usuario.requestFocus();
    }

    public void actualizarRRHH() {
        try {
            String actualizarPer = "UPDATE TMAEPERCON SET nombre_persona = '" + nombres_persona + "', apellido_persona = '" + apellidos_persona + "', correo_persona = '" + correo_persona + "' WHERE id_persona=" + idGlobal + ";";
            String actReh = "UPDATE TMOVREHCON set tipo_rrhh = '" + tipo_usuario + "' where idpersona_rrhh = " + idGlobal + ";";
            conexion.ejecutarSQL(actualizarPer);
            conexion.ejecutarSQL(actReh);
            mensajes.msg_confirmacion(this, "Registro actualizado con éxito.");
        } catch (Exception e) {

        }
        combo_tipo_usuario.requestFocus();
    }

    /*Metodo para eliminar un RRHH, se llama desde el formulario de busqueda, en este caso se valida si no esta siento
    siendo utilizado por su tabla con relacion directa ASIGNACION DE ACTIVO */
    public void elmininarRrhh() {
        boolean podemos_eliminar=false;
        try {
            String sql = "select count(*) from TMOVDASCON where idarea_asigactivo=(select id_area from TMAEARECON where idresponsable_area=(select id_rrhh from TMOVREHCON where idpersona_rrhh="+idGlobal+"))";
            ResultSet rs = conexion.ejecutarSQLSelect(sql);
            if(rs.next()){
                if(rs.getInt("count")>0){
                    podemos_eliminar=false;
                }else{
                    podemos_eliminar=true;
                }
            }
        } catch (Exception e) {
            System.out.println("");
        }
        if(podemos_eliminar==true){
            procesoEliminar();
        }else{
            if(mensajes.msg_Pregunta(this, "Este recurso humano está ligado a un documento de asignación, \n primero elimine asignación de activos correspondientes y proceda a su eliminación.\n"
                    + "¿Desea eliminar documento ligado a este responsable?")==0){
                procesoEliminar();
            }
        }
    }
    
    public void procesoEliminar (){
        try {
            String sql = "select * from ELIMINAR_RRHH("+idGlobal+");";
            ResultSet rs = conexion.ejecutarSQLSelect(sql);
            if(rs.next()){
                mensajes.msg_confirmacion(this, "Registro eliminado con exito.");
            }
        } catch (Exception e) {
            System.out.println("");
        }
    }

    public void loadData(Object check[]) {
        cedula_persona = check[0].toString();
        nombres_persona = check[1].toString();
        apellidos_persona = check[2].toString();
        correo_persona = check[3].toString();
        int id = Integer.parseInt(check[4].toString());
        tipo_usuario = combo_tipo_usuario.getSelectedItem().toString();
        System.out.print(id);
        Integer tipoNum = combo_tipo_usuario.getSelectedIndex() + 1;
        idGlobal = id;
        txt_cedula.setText(cedula_persona);
        txt_nombres.setText(nombres_persona);
        txt_apellidos.setText(apellidos_persona);
        txt_correo.setText(correo_persona);
        try {
            String sql = "select tipo_rrhh from TMAEPERCON as p inner join TMOVREHCON as rh on rh.idpersona_rrhh=p.id_persona where p.cedula_persona='"+txt_cedula.getText()+"'";
            ResultSet rs = conexion.ejecutarSQLSelect(sql);
            if(rs.next()){
                tipo_usuario=rs.getString("tipo_rrhh");
            }
        } catch (SQLException ex) {

        }
        
        if(tipo_usuario.equals("Docente")){
            combo_tipo_usuario.setSelectedIndex(0);
        }else{
            if(tipo_usuario.equals("Administrativo")){
                combo_tipo_usuario.setSelectedIndex(1);
            }else{
                if(tipo_usuario.equals("Mantenimiento")){
                    combo_tipo_usuario.setSelectedIndex(2);
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

        jPanel3 = new javax.swing.JPanel();
        combo_tipo_usuario = new javax.swing.JComboBox();
        panel_detalles_usuario = new javax.swing.JPanel();
        txt_clave_usuario = new javax.swing.JPasswordField();
        txt_confirmeclave_usuario = new javax.swing.JPasswordField();
        jLabel8 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txt_nombre_usuario = new javax.swing.JTextField();
        jPanel7 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        btn_nuevo = new javax.swing.JButton();
        btn_guardar = new javax.swing.JButton();
        btn_buscar = new javax.swing.JButton();
        btn_actualizar = new javax.swing.JButton();
        btn_regresar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txt_cedula = new javax.swing.JTextField();
        txt_nombres = new javax.swing.JTextField();
        txt_apellidos = new javax.swing.JTextField();
        txt_correo = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Control Recursos Humanos");
        setResizable(false);

        combo_tipo_usuario.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        combo_tipo_usuario.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Docente", "Administrativo", "Mantenimiento", "admin_mantenimiento" }));
        combo_tipo_usuario.setNextFocusableComponent(txt_cedula);
        combo_tipo_usuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_tipo_usuarioActionPerformed(evt);
            }
        });

        panel_detalles_usuario.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Detalles de Usuario", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 17))); // NOI18N

        txt_clave_usuario.setNextFocusableComponent(txt_confirmeclave_usuario);
        txt_clave_usuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_clave_usuarioKeyTyped(evt);
            }
        });

        txt_confirmeclave_usuario.setNextFocusableComponent(btn_guardar);
        txt_confirmeclave_usuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_confirmeclave_usuarioKeyTyped(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel8.setText("Clave");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel7.setText("Usuario");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel9.setText("Confirmar Clave");

        txt_nombre_usuario.setNextFocusableComponent(txt_clave_usuario);

        javax.swing.GroupLayout panel_detalles_usuarioLayout = new javax.swing.GroupLayout(panel_detalles_usuario);
        panel_detalles_usuario.setLayout(panel_detalles_usuarioLayout);
        panel_detalles_usuarioLayout.setHorizontalGroup(
            panel_detalles_usuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_detalles_usuarioLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(panel_detalles_usuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(jLabel7))
                .addGap(18, 18, 18)
                .addGroup(panel_detalles_usuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_clave_usuario)
                    .addComponent(txt_confirmeclave_usuario)
                    .addComponent(txt_nombre_usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        panel_detalles_usuarioLayout.setVerticalGroup(
            panel_detalles_usuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_detalles_usuarioLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(panel_detalles_usuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panel_detalles_usuarioLayout.createSequentialGroup()
                        .addGroup(panel_detalles_usuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_nombre_usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addGap(18, 18, 18)
                        .addGroup(panel_detalles_usuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_clave_usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addGap(18, 18, 18)
                        .addComponent(txt_confirmeclave_usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(41, Short.MAX_VALUE))
        );

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

        jToolBar1.setBackground(new java.awt.Color(117, 214, 255));
        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        btn_nuevo.setBackground(new java.awt.Color(117, 214, 255));
        btn_nuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icons/add-new-file (1).png"))); // NOI18N
        btn_nuevo.setText("   Nuevo   ");
        btn_nuevo.setFocusable(false);
        btn_nuevo.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_nuevo.setNextFocusableComponent(combo_tipo_usuario);
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
        btn_guardar.setNextFocusableComponent(combo_tipo_usuario);
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
        btn_buscar.setNextFocusableComponent(combo_tipo_usuario);
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
        btn_regresar.setNextFocusableComponent(btn_nuevo);
        btn_regresar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btn_regresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_regresarActionPerformed(evt);
            }
        });
        jToolBar1.add(btn_regresar);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 21)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Control Recursos Humanos");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel2.setText("Tipo");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel3.setText("Cédula");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel4.setText("Nombres");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel5.setText("Apellidos");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        jLabel6.setText("Correo");

        txt_cedula.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        txt_cedula.setNextFocusableComponent(txt_nombres);
        txt_cedula.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_cedulaKeyTyped(evt);
            }
        });

        txt_nombres.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        txt_nombres.setNextFocusableComponent(txt_apellidos);
        txt_nombres.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_nombresKeyTyped(evt);
            }
        });

        txt_apellidos.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        txt_apellidos.setNextFocusableComponent(txt_correo);
        txt_apellidos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_apellidosKeyTyped(evt);
            }
        });

        txt_correo.setFont(new java.awt.Font("Tahoma", 0, 17)); // NOI18N
        txt_correo.setNextFocusableComponent(btn_guardar);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(115, 115, 115)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(combo_tipo_usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_cedula, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_nombres, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(panel_detalles_usuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel6)
                                .addComponent(jLabel5))
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel3)
                                .addComponent(jLabel4)
                                .addComponent(jLabel2)))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_apellidos, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txt_correo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(446, 446, 446))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(combo_tipo_usuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txt_cedula, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txt_nombres, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txt_apellidos, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txt_correo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(panel_detalles_usuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void combo_tipo_usuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_tipo_usuarioActionPerformed
        habilitarCampos();
        }//GEN-LAST:event_combo_tipo_usuarioActionPerformed

    private void btn_regresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_regresarActionPerformed
        if (mensajes.msg_Pregunta(this, "¿Esta seguro que desea salir del registro de recursos humanos?") == 0) {
            dispose();
            
        }
        }//GEN-LAST:event_btn_regresarActionPerformed

    private void btn_buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_buscarActionPerformed
        save = false;
        nuevoBuscar();
        }//GEN-LAST:event_btn_buscarActionPerformed

    private void txt_nombresKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nombresKeyTyped
        Character c = evt.getKeyChar();

        if (txt_nombre_usuario.getText().trim().length() > 200) {
            evt.consume();
        } else {
            if (Character.isLetter(c) || c == KeyEvent.VK_SPACE) {
                evt.setKeyChar(c);
            } else {
                evt.consume();
            }
        }
    }//GEN-LAST:event_txt_nombresKeyTyped

    private void txt_apellidosKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_apellidosKeyTyped
        Character c = evt.getKeyChar();

        if (txt_apellidos.getText().trim().length() > 200) {
            evt.consume();
        } else {
            if (Character.isLetter(c) || c == KeyEvent.VK_SPACE) {
                evt.setKeyChar(c);
            } else {
                evt.consume();
            }
        }
    }//GEN-LAST:event_txt_apellidosKeyTyped

    private void txt_cedulaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_cedulaKeyTyped
        Character c = evt.getKeyChar();
        if (txt_cedula.getText().trim().length() > 9) {
            evt.consume();
        } else {
            if (Character.isDigit(c)) {
                evt.setKeyChar(c);
            } else {
                evt.consume();
            }
        }
    }//GEN-LAST:event_txt_cedulaKeyTyped

    private void btn_guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_guardarActionPerformed
        save = true;
        validarCampos();
    }//GEN-LAST:event_btn_guardarActionPerformed

    private void btn_nuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_nuevoActionPerformed
        save = true;
        nuevoBuscar();
    }//GEN-LAST:event_btn_nuevoActionPerformed

    private void txt_clave_usuarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_clave_usuarioKeyTyped
        Character c = evt.getKeyChar();
        if (Character.toString(c).equals("'")) {
            evt.consume();
        }
    }//GEN-LAST:event_txt_clave_usuarioKeyTyped

    private void txt_confirmeclave_usuarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_confirmeclave_usuarioKeyTyped
        Character c = evt.getKeyChar();
        if (Character.toString(c).equals("'")) {
            evt.consume();
        }
    }//GEN-LAST:event_txt_confirmeclave_usuarioKeyTyped

    private void btn_actualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_actualizarActionPerformed
        save = false;
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
            java.util.logging.Logger.getLogger(FRM_ControlRecursosHumanos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FRM_ControlRecursosHumanos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FRM_ControlRecursosHumanos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FRM_ControlRecursosHumanos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
//</editor-fold>
//</editor-fold>
//</editor-fold>
//</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FRM_ControlRecursosHumanos dialog = new FRM_ControlRecursosHumanos(new javax.swing.JFrame(), true);
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
    public javax.swing.JButton btn_buscar;
    public javax.swing.JButton btn_guardar;
    public javax.swing.JButton btn_nuevo;
    public javax.swing.JButton btn_regresar;
    public javax.swing.JComboBox combo_tipo_usuario;
    public javax.swing.JLabel jLabel1;
    public javax.swing.JLabel jLabel2;
    public javax.swing.JLabel jLabel3;
    public javax.swing.JLabel jLabel4;
    public javax.swing.JLabel jLabel5;
    public javax.swing.JLabel jLabel6;
    public javax.swing.JLabel jLabel7;
    public javax.swing.JLabel jLabel8;
    public javax.swing.JLabel jLabel9;
    public javax.swing.JPanel jPanel3;
    public javax.swing.JPanel jPanel7;
    public javax.swing.JToolBar jToolBar1;
    public javax.swing.JPanel panel_detalles_usuario;
    public javax.swing.JTextField txt_apellidos;
    public javax.swing.JTextField txt_cedula;
    public javax.swing.JPasswordField txt_clave_usuario;
    public javax.swing.JPasswordField txt_confirmeclave_usuario;
    public javax.swing.JTextField txt_correo;
    public javax.swing.JTextField txt_nombre_usuario;
    public javax.swing.JTextField txt_nombres;
    // End of variables declaration//GEN-END:variables
}
