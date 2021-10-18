
package A_Ejecutar;

import CapaInterfaces.FRM_ConfiguracionInicial;
import CapaInterfaces.FRM_Ingreso;
import Capa_ConexionBD.Conexion;
import javax.swing.JOptionPane;

public class ControlActivosFijos {
    static Capa_Mensajes.Mensajes msj = new Capa_Mensajes.Mensajes();
    static Capa_ConexionBD.Conexion conexion = new Capa_ConexionBD.Conexion ();
    
    public static void main(String[] args) {
        Capa_Negocio.CorreoActivosMantenimientoProgramado.SendMail(); 
        if(conexion.verificarConfiguracion()==1){
            FRM_Ingreso ejecutar = new FRM_Ingreso();
            ejecutar.setVisible (true); 
        }else{
            FRM_ConfiguracionInicial ejecutar = new FRM_ConfiguracionInicial();
            ejecutar.setVisible (true); 
        }
    }
}