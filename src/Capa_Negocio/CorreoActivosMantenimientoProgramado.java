package Capa_Negocio;

/**
 *
 * @author Kevin Muñiz
 */

import java.net.Socket; // libreria para activar los puertos de conexion
import java.util.Properties;
import javax.mail.Message; //  COntenido del mensaje 
import javax.mail.MessagingException;// exepciones que puede contener el envio del correo
import javax.mail.PasswordAuthentication; // Autenticacion del usuario por medio del correo y password
import javax.mail.Session;// Crear una secion del correo establecido
import javax.mail.Transport;// Clases de transporte del  correo y envio del mismo por los sercidores de correo
import javax.mail.internet.InternetAddress;//Coneccion por medio de las direcciones y puertos del servidor de correo escogido
import javax.mail.internet.MimeMessage;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CorreoActivosMantenimientoProgramado {
    
    private  static final String cadena = "jdbc:postgresql://localhost:5432/ActiSoft";
    private  static final String usuario = "postgres";
    private  static final String contrasenia = "12345";        
    
    public static boolean comprobarConexion(){
        // direccion web para la comprobacion de coneccion a internet
        String dirWeb = "www.lineadecodigo.com"; 
        // es el puerto por el cual se hara la comprobacion de salida a internet
        int puerto = 587;
        try{
            // asignamos la salida de coneccion a una direccion establecida 
            Socket s = new Socket(dirWeb, puerto);    
            // validamos si existe una coneccion saliente a internet
            if(s.isConnected()){ 
            return true;
            }
        }catch(Exception e){            
            // en el caso que falle la coneccion se muestra este mensaje
            
            return false;
        }
        return false;
    };

    public static String tomarActivosPasados(){
        String Mensaje=""; 
        int id=0;        
        try{
                Class.forName("org.postgresql.Driver");
                Connection conex = DriverManager.getConnection(cadena,usuario,contrasenia);
                java.sql.Statement st = conex.createStatement();                
                // se llama a la vista llamada activos- esta contiene a los activo que tienen 90 dias 
                //(3 meses) desde que no se les da un mantenimiento, tambien a los que tienen el mismo tiempo de adquiridos 
                //por la facultad y no tienen nigun mantenimiento registrado 
                String sql = "select * from activos;";
                ResultSet resultSet = st.executeQuery(sql);                
                // parte del cuerpo del correo 
                Mensaje = "Buen día Sr/a. Administrador de Mantenimiento.\n\n\nA continuación le mostraremos la lista de los Activos "
                        + "que necesitan un proceso de Mantenimiento Preventivo: \n*Especificando el Area donde se encuentra el activo  -Tipo de Activo "
                        + " -Marca de Activo  -Modelo del Activo* \n \n"; 
                // se comienza a obtener el detalle de cada uno de los activos que estam dentro de esta lista
                while(resultSet.next()){
                    String descripcion= resultSet.getString("descripcion");
                    String nombre= resultSet.getString("nombre_tipoactivo");
                    String marca= resultSet.getString("marca_activo");
                    String modelo= resultSet.getString("modelo_activo");                
                    // se elabora esta linea por cada activo que esta dentro de esta lista
                    Mensaje+="  - Area de "+descripcion+". Activo: "+nombre+"  Marca: "+marca+"  Modelo: "+modelo+" \n";
                }                                   
                resultSet.close();                
                st.close();
                conex.close();                
            }catch(Exception exc){                
            }
        //JOptionPane.showMessageDialog(null, Mensaje);
        if(Mensaje.length() <= 269)
        {
            Mensaje = "No"; //en el casi de que no se encuentre ningun activo a falta de mantenimiento
        }
        return(Mensaje);
    }
    
    public static String obtenercorreoadmin()
    {
        String mensaje="";
        try{
                Class.forName("org.postgresql.Driver");
                Connection conex = DriverManager.getConnection(cadena,usuario,contrasenia);
                java.sql.Statement st = conex.createStatement();                
                String sql = "select per.correo_persona from tmaepercon per inner join tmaeusucon usu on usu.id_persona = per.id_persona where usu.nombre_usuario = 'admin_mantenimiento'"; // se obtiene el correo del administrador de mantenimiento
                ResultSet resultSet = st.executeQuery(sql); 
                try {
                if (resultSet.next() == true) {
                    mensaje = resultSet.getString("correo_persona");
                    resultSet.close();                
                    st.close();
                    conex.close();                
                    
                }
            } catch (SQLException ex) {
                
            }               
                return (mensaje);
            }catch(Exception exc){                
                return(exc.getMessage());
            }                     
    }
    
     public static void SendMail() {
        if ((comprobarConexion())&&(!tomarActivosPasados().equals("No"))){ // se valida que exista coneccion a internet por el puerto establecido y si existen activos que necesiten mantenimiento
           Properties props = new Properties(); // se crean las propiedad para el envio del correo
           props.put("mail.smtp.auth", "true");// configuraciones para coneccion a los servidores para envio de correo
           props.put("mail.smtp.starttls.enable", "true");
           props.put("mail.smtp.host", "smtp.gmail.com");// se establece que el correo sera enviado desde un servidor de correo que es de gmail
           props.put("mail.smtp.port", "587");
           final String Username = "controlactivosfacci@gmail.com" ; // el correro del remitente
           final String PassWord = "controlactivosfacci2016";// contraseña del remitente
           String To =obtenercorreoadmin();// obtenemos el correo del usuario admin- mantenimiento y a quien sera enviado el correo 
           String Subject = "Mantenimiento";// sera el asunto del correo
           String Mensage = tomarActivosPasados(); // obtenemos el contenido del mensaje que este mismo poseera la lista de los activos que necesitan el mantenimiento

           Session session = Session.getInstance(props, 
                   new javax.mail.Authenticator() {
                       @Override
                       protected PasswordAuthentication getPasswordAuthentication() {
                           return new PasswordAuthentication(Username, PassWord);
                       }
                   }); // se crea la secion del usuario que enviara el correo

           try {
                Message message = new MimeMessage(session); // creamos el cuerpo del nuevo correo
                message.setFrom(new InternetAddress(Username));// se le asigna quien es el remitente del mismo
                message.setRecipients(Message.RecipientType.TO,// configuracion del receptor
                InternetAddress.parse(To));
                message.setSubject(Subject);// asusnto del correo
                message.setText(Mensage);// el contenido del correo
                Transport.send(message);// envio del correo
           } catch (MessagingException e) {
               //throw new RuntimeException(e);
           }
       }
    }  
}