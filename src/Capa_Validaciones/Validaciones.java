package Capa_Validaciones;

import CapaInterfaces.FRM_RegistroActivos;
import static CapaInterfaces.FRM_RegistroActivos.codigo_institucional;
import static CapaInterfaces.FRM_RegistroActivos.detalle;
import static CapaInterfaces.FRM_RegistroActivos.modelo_activo;
import static CapaInterfaces.FRM_RegistroActivos.serie_activo;
import static CapaInterfaces.FRM_RegistroActivos.txt_detalle_activo;
import static CapaInterfaces.FRM_RegistroActivos.txt_modelo_activo;
import static CapaInterfaces.FRM_RegistroActivos.txt_serie_activo;
import CapaInterfaces.FRM_RegistroSolicitudMantenimiento;
import Capa_ConexionBD.Conexion;
import java.awt.Component;
import static java.awt.image.ImageObserver.WIDTH;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JTextField;


import java.sql.ResultSet;
public class Validaciones {
    
    static ResultSet rs;
    static Capa_Mensajes.Mensajes msj = new Capa_Mensajes.Mensajes();
    static String mensaje;
    //Esta funcion se utiliza para validar tanto modelo como serie en activos
    public static boolean validarmayusculasynumeros(String texto){
       String validos = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ0123456789";

        int i, j, coinci = 0;
        String cadena = texto;
        for (j = 0; j < cadena.length(); j++) {
            for (i = 0; i < validos.length(); i++) {
                if (cadena.charAt(j) == validos.charAt(i)) {
                    coinci++;
                }

            }

        }
        if (cadena.length() != coinci) {
          return false;
        }
         return true;
    }
    //Esta funcion se utiliza para validar el codigo de oficio interno
    public static boolean validarmaynumguion(String texto){
        String validos = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-";
        int i, j, coinci = 0;
        String cadena = texto;
        for (j = 0; j < cadena.length(); j++) {
            for (i = 0; i < validos.length(); i++) {
                if (cadena.charAt(j) == validos.charAt(i)) {
                    coinci++;
                }

            }

        }
        if (cadena.length() != coinci) {
          return false;
        }
         return true;
    
    }
    //Esta funcion se utiliza para validar el codigo institucional
    public static boolean validarmayminnumguion(String texto){
        String validos = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-";
        int i, j, coinci = 0;
        String cadena = texto;
        for (j = 0; j < cadena.length(); j++) {
            for (i = 0; i < validos.length(); i++) {
                if (cadena.charAt(j) == validos.charAt(i)) {
                    coinci++;
                }

            }

        }
        if (cadena.length() != coinci) {
          return false;
        }
         return true;
    
    }
    
    //validar email
    public boolean validarEmail(String texto) {
//            String mydomain = "javahungry@blogspot.com";
        String emailregex = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        boolean respuesta=false;
        Boolean coincide = texto.matches(emailregex);
        String mensaje = "";
        if (coincide) {
            respuesta=true;
        }else{ 
            respuesta=false;
        }
        return respuesta;        
    }

    public static boolean validarfecha(Component Ventana, java.sql.Date fecha) {
        boolean resultado=false;
        try {
            java.sql.Date fecha_actual = new java.sql.Date(new java.util.Date().getTime());
            java.sql.Date fecha_compra;
            fecha_compra = fecha;
            if (fecha_compra.getTime() > fecha_actual.getTime()) {
                mensaje="La fecha de compra no puede ser mayor a la fecha actual.";
                msj.msg_advertencia(Ventana, mensaje);
                resultado=false;
            }else{
                resultado=true;
            }
        } catch (NullPointerException e) {
            resultado=false;
        }
        return resultado;
    }
    
    //valida codigo de oficio
    public static boolean validarcodigo_oficio(Component Ventana, String codigo){
        if(codigo.isEmpty()||validarmaynumguion(codigo)==false){
        mensaje="Debe ingresar el Codigo de Oficio.\nLos caracteres aceptados son (A-Z),(0-9) y (-).";
        msj.msg_advertencia(Ventana,mensaje);
        return false;
        }
        
        return true;
    }
    
    //Valida los campos del registro de activos antes de guardar en la base de datos
    public static boolean validarcampos (Component Ventana){
        
        boolean regresar=false;
        serie_activo=txt_serie_activo.getText();
        modelo_activo=txt_modelo_activo.getText();
        codigo_institucional=FRM_RegistroActivos.txt_codigo_institucional.getText();
        detalle=txt_detalle_activo.getText();
        rs=Conexion.ejecutarSQLSelect("select * from TMOVACTCON where codigoInternoInstitucional_activo='"+FRM_RegistroActivos.txt_codigo_institucional.getText()+"'");
        FRM_RegistroActivos.procesador=FRM_RegistroActivos.combo_procesador_activo.getSelectedItem().toString();
        FRM_RegistroActivos.memoria=FRM_RegistroActivos.combo_memoria_activo.getSelectedItem().toString();
        FRM_RegistroActivos.discoduro=FRM_RegistroActivos.combo_discoduro_activo.getSelectedItem().toString();
        FRM_RegistroActivos.marca_activo=FRM_RegistroActivos.combo_marca_activo.getSelectedItem().toString();
        FRM_RegistroActivos.id_tipo_activo=FRM_RegistroActivos.combo_tipo_activo.getSelectedIndex();
        
        if(FRM_RegistroActivos.combo_tipo_activo.getSelectedItem().toString().equals("Seleccionar")){
            mensaje="Debe seleccionar un tipo de activo.";
            msj.msg_advertencia(Ventana, mensaje);
            FRM_RegistroActivos.combo_tipo_activo.requestFocus();
            return false;
        }
        
        if(FRM_RegistroActivos.combo_marca_activo.getSelectedItem().toString().equals("Seleccionar")){
            mensaje="Debe seleccionar una marca de activo.";
            msj.msg_advertencia(Ventana, mensaje);
            FRM_RegistroActivos.combo_marca_activo.requestFocus();
            return false;
        }
        
        if(FRM_RegistroActivos.combo_tipo_activo.getSelectedItem().toString().equals("Computadora de escritorio") || FRM_RegistroActivos.combo_tipo_activo.getSelectedItem().toString().equals("CPU") || FRM_RegistroActivos.combo_tipo_activo.getSelectedItem().toString().equals("Portátil")){
            if(FRM_RegistroActivos.combo_procesador_activo.getSelectedItem().toString().equals("Seleccionar")){
                mensaje="Debe seleccionar un tipo de procesador.";
                msj.msg_advertencia(Ventana, mensaje);
                FRM_RegistroActivos.combo_procesador_activo.requestFocus();
                return false;
            }
            if(FRM_RegistroActivos.combo_memoria_activo.getSelectedItem().toString().equals("Seleccionar")){
                mensaje="Debe seleccionar un tipo de memoria.";
                msj.msg_advertencia(Ventana, mensaje);
                FRM_RegistroActivos.combo_memoria_activo.requestFocus();
                return false;
            }
            if(FRM_RegistroActivos.combo_discoduro_activo.getSelectedItem().toString().equals("Seleccionar")){
                mensaje="Debe seleccionar un tipo de disco duro.";
                msj.msg_advertencia(Ventana, mensaje);
                FRM_RegistroActivos.combo_discoduro_activo.requestFocus();
                return false;
            }
        }
        
        if(modelo_activo.isEmpty()){
            mensaje="Debe ingresar el Modelo del activo.";
            msj.msg_advertencia(Ventana, mensaje);
            txt_modelo_activo.requestFocus();
            return false;
        }
        else if(serie_activo.isEmpty()){
            mensaje="Debe ingresar la Serie del activo.";
            msj.msg_advertencia(Ventana, mensaje);
            txt_serie_activo.requestFocus();
             return false;
        }
        
        if(codigo_institucional.isEmpty()){
             mensaje="Debe ingresar el Codigo Institucional.";
            msj.msg_advertencia(Ventana, mensaje);
           
            FRM_RegistroActivos.txt_codigo_institucional.requestFocus();
             return false;
        }
        else if(detalle.isEmpty()){
              mensaje="Debe ingresar el Detalle del activo.";
            msj.msg_advertencia(Ventana, mensaje);
           
            txt_detalle_activo.requestFocus();
             return false;
        }
        else{
           regresar=true;
        }
      return regresar;
    }
    
    //Valida si ya existe el activo con ese codigo institucional
    public static boolean validarcodigo_institucional_existente(Component Ventana, String tipo_sentencia) throws SQLException{
        if(tipo_sentencia.equals("insertar") && rs.next()== true){
            mensaje="Codigo institucional ya existe, ingrese nuevamente o actualice el existente. ";
            msj.msg_advertencia(Ventana, mensaje);
            FRM_RegistroActivos.txt_codigo_institucional.setText("");
            FRM_RegistroActivos.txt_codigo_institucional.requestFocus();
            return false;
       }
        
        return true;
    }

    
    public boolean validadorDeCedula(String cedula) {
        boolean cedulaCorrecta = false;

        try {

        if (cedula.length() == 10) // ConstantesApp.LongitudCedula
        {
        int tercerDigito = Integer.parseInt(cedula.substring(2, 3));
        if (tercerDigito < 6) {
            // Coeficientes de validación cédula
            // El decimo digito se lo considera dígito verificador
             int[] coefValCedula = { 2, 1, 2, 1, 2, 1, 2, 1, 2 };
             int verificador = Integer.parseInt(cedula.substring(9,10));
             int suma = 0;
             int digito = 0;
                for (int i = 0; i < (cedula.length() - 1); i++) {
                 digito = Integer.parseInt(cedula.substring(i, i + 1))* coefValCedula[i];
                 suma += ((digito % 10) + (digito / 10));
                }

            if ((suma % 10 == 0) && (suma % 10 == verificador)) {
             cedulaCorrecta = true;
            }
            else if ((10 - (suma % 10)) == verificador) {
             cedulaCorrecta = true;
            } else {
             cedulaCorrecta = false;
            }
        } else {
        cedulaCorrecta = false;
        }
        } else {
        cedulaCorrecta = false;
        }
        } catch (NumberFormatException nfe) {
        cedulaCorrecta = false;
        } catch (Exception err) {
        //System.out.println("Una excepcion ocurrio en el proceso de validadcion");
        cedulaCorrecta = false;
        }
 
    if (!cedulaCorrecta) {
    //System.out.println("La Cédula ingresada es Incorrecta");
    }
    return cedulaCorrecta;
    }

    
    
    
  //PaulDelgado
  //Metodo que valida la complejidad de un campo contraseña
     public boolean ComplejidadClave(Component Ventana, String contraseña) {                        
                contraseña = contraseña.trim();
                if(contraseña.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,16}$")){ //Utiliza una expresion regular para validar la comlejidad . 
                                                                                    //(?=.*\d)verifica la existencia de un caracter numerico, 
                                                                                    //(?=.*[a-z]) la de una letra minuscula
                                                                                    //(?=.*[A-Z]) la de una letra en mayusculas. Verifica la longitud{6,15}.
                    return true;                                                   
                }else{
                    mensaje="Su clave no cuenta con la seguridad recomendada. \nDebe de tener entre 6-16 caracteres, \n"
                            + "incluir al menos una letra mayuscula y un número";
                    msj.msg_advertencia(Ventana, mensaje);
                }
                return false;
     }
    //PaulDelgado
    //Metodo que verifica la igualdad de dos campos 
    public boolean IgualdadCampos(Component Ventana, String campo1, String campo2){
      if (campo1.equals(campo2)){return true;}
        mensaje="Claves de acceso no coinciden, intente de nuevo.";
                    msj.msg_advertencia(Ventana, mensaje);
                    return false ;  
      
    }
    
     // Con este metodo validamos la fecha minima y la fecha maxima que se puede ingresar
    //OJO esto se llama despues del initComponents()
    public void validaciondefecha(com.toedter.calendar.JDateChooser fecha_docSolicitud ){
        ((JTextField) fecha_docSolicitud.getDateEditor()).setEditable(false);  
        SimpleDateFormat simpleDateFormat_formatoFecha = new SimpleDateFormat("d/MM/yyyy");                 
        Date date_fechaMinima=null;
        Date date_fechaMaxima = new Date();        
        try {            
            date_fechaMinima= simpleDateFormat_formatoFecha.parse("01/01/2015");
        } catch (ParseException ex) {
            Logger.getLogger(FRM_RegistroSolicitudMantenimiento.class.getName()).log(Level.SEVERE, null, ex);
        }   
        fecha_docSolicitud.setSelectableDateRange(date_fechaMinima, date_fechaMaxima);
    }
    
    public boolean validadorDeCedula1(Component Ventana, String cedula) {
        boolean cedulaCorrecta = false;
        try {
            if (cedula.length() == 10) {
                int PrimerDigito = Integer.parseInt(cedula.substring(0, 1)),
                        SegundoDigito = Integer.parseInt(cedula.substring(1, 2));
                if (PrimerDigito == 2 && SegundoDigito >= 5) {
                    mensaje="La Cédula ingresada es incorrecta.";
                    msj.msg_advertencia(Ventana, mensaje);
                    return cedulaCorrecta;
                } else if (PrimerDigito > 3) {
                    mensaje="La Cédula ingresada es incorrecta.";
                    msj.msg_advertencia(Ventana, mensaje);
                    return cedulaCorrecta;
                } else if (PrimerDigito < 3) {
                    int[] coefValCedula = {2, 1, 2, 1, 2, 1, 2, 1, 2};
                    int verificador = Integer.parseInt(cedula.substring(9, 10));
                    int suma = 0;
                    int digito = 0;
                    for (int i = 0; i < (cedula.length() - 1); i++) {
                        digito = Integer.parseInt(cedula.substring(i, i + 1)) * coefValCedula[i];
                        suma += ((digito % 10) + (digito / 10));
                    }
                    if ((suma % 10 == 0) && (suma % 10 == verificador)) {
                        cedulaCorrecta = true;
                    } else if ((10 - (suma % 10)) == verificador) {
                        cedulaCorrecta = true;

                    } else {
                        cedulaCorrecta = false;
                    }
                } else {
                    cedulaCorrecta = false;
                }
            } else {
                cedulaCorrecta = false;
            }

        } catch (NumberFormatException e) {
            cedulaCorrecta = false;
        } catch (Exception e) {
            mensaje="Una excepcion ocurrio en el proceso de validadcion";
            msj.msg_advertencia(Ventana, mensaje);
            cedulaCorrecta = false;
        }
        if (!cedulaCorrecta) {
            
            mensaje="La Cédula ingresada es Incorrecta ";
            msj.msg_advertencia(Ventana, mensaje);

        }

        return cedulaCorrecta;
    }
    public static boolean camposVacios(final String s) {
        // Null-safe, short-circuit evaluation.
        return s == null || s.trim().isEmpty();
    };
    
    
        /*public boolean ValidarEmail(String texto) {
//            String mydomain = "javahungry@blogspot.com";
         String emailregex = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
            return(texto.matches(emailregex));         
        }*/
}