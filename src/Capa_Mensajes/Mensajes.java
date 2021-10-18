package Capa_Mensajes;

import java.awt.Component;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

/**
 *
 * @author Alejandro
 */
public class Mensajes {
    
  public void msg_confirmacion(Component Ventana, String msg_confirmacion){
    //System.out.println(getClass().getResource("/Icons_msg/checked (1).png"));
      Icon img = new ImageIcon(getClass().getResource("/Icons_msg/checked (1).png"));
      JOptionPane.showMessageDialog(Ventana,msg_confirmacion,"Titulo", JOptionPane.DEFAULT_OPTION,img);
  }
   
  public void msg_advertencia(Component Ventana, String msg_advertencia){
      Icon img = new ImageIcon(getClass().getResource("/Icons_msg/warning (1).png"));
      /*Icon img = new ImageIcon(getClass().getResource("Icons_msg/warning (1).png"));*/
      JOptionPane.showMessageDialog(Ventana,msg_advertencia,"ADVERTENCIA", JOptionPane.DEFAULT_OPTION,img);
  }
  
  public int msg_Pregunta(Component Ventana, String msg_pregunta){
      Icon img = new ImageIcon(getClass().getResource("/Icons_msg/question (1).png"));
      int resp = JOptionPane.showConfirmDialog(Ventana,msg_pregunta, "Alerta!", JOptionPane.YES_NO_OPTION,JOptionPane.DEFAULT_OPTION,img); 
      return(resp);
  }
  
   public String msg_ConCuadroDeTexto(Component Ventana,String msg_pregunta){
      String respuesta = JOptionPane.showInputDialog(Ventana, msg_pregunta);
      return respuesta;
   }//joptionpane con passwordFIeld
     public String inputContraseña(Component th,
            String mensaje) {
        String titulo= "Seguridad";
        String password = null;
        JPasswordField passwordField = new JPasswordField();
        Object[] obj = {mensaje + ":\n\n", passwordField};
        Object stringArray[] = {"OK", "Cancel"};
        if (JOptionPane.showOptionDialog(th, obj, titulo,
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, stringArray, obj) == JOptionPane.YES_OPTION) {
            password = password = new String(passwordField.getPassword());
        }
        return password;
    }
}
