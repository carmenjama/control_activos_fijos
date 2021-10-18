/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enviarmail;


import Capa_Mensajes.Mensajes;
import java.awt.Component;
import java.net.Socket;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


/**
 *
 * @author Paul
 */
public class EnviarMail {
        public Session session;
        Mensajes mensaje = new Mensajes();
        String Username = "controlactivosfacci@gmail.com" ;
        String PassWord = "controlactivosfacci2016";
        
     
    /**
     * @param args the command line arguments
     */
    
 
    
    public static void main(String[] args) {
       
    }
    
    public  boolean comprobarConexion(Component ventana) {
        String dirWeb = "www.lineadecodigo.com";
        int puerto = 587;
        try {
            Socket s = new Socket(dirWeb, puerto);
            return s.isConnected();
        } catch (Exception e) {
            mensaje.msg_advertencia(ventana, "Necesita una conexion a internet para recuperar su clave");
            return false;
            
        }
    }

    


public void Autentificacion() {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
      
       

        session = Session.getInstance(props,
        new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(Username, PassWord);
            }
        });
}


 public boolean  SendMail(String To, String Mensaje) {
   
        String Subject = "Clave de acceso.";
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(Username));
            message.setRecipients(Message.RecipientType.TO,
            InternetAddress.parse(To));
            message.setSubject(Subject);
            message.setText(Mensaje);
            Transport.send(message);
            return true;        
        } catch (MessagingException e) {
            return false;
          
            
        }
        
    
     
    }
}
