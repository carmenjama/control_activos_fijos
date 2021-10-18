/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reportes;

import CapaInterfaces.FRM_BusquedaActivos;
import Capa_Mensajes.Mensajes;
import com.lowagie.text.Chunk;
import java.io.FileOutputStream;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Header;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfTable;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Color;
import java.awt.Component;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.AttributeSet;


/**
 *
 * @author Ana
 */
public class Reportes {    
    
    public static  void PDF (Component Ventana, Object parametro_busqueda){
        try{
            
            Document documento = new Document(PageSize.A4.rotate(), 35, 30, 50, 50);
          
            //Document documento= new Document(PageSize.A4.rotate(),0,0,0,0);
            String nombre_reporte = "Reporte Activos "+parametro_busqueda;
            JFileChooser direccion=new JFileChooser();
            direccion.setSelectedFile(new File(nombre_reporte));
            direccion.addChoosableFileFilter(new FileNameExtensionFilter("todos los archivos*.PDF","pdf","PDF"));
            int ruta_seleccionada=direccion.showSaveDialog(null);
            
            //Si acepta guardar el documento se empezara a crear
            if(ruta_seleccionada==JFileChooser.APPROVE_OPTION){
           // direccion.setSelectedFile(new File(nombre_reporte+".pdf"));
            File ruta=direccion.getSelectedFile();
            Font fuente=new Font();
            Font fuente_columnas=new Font();
            fuente_columnas.setSize(11);
            fuente_columnas.setColor(Color.BLACK);
            fuente_columnas.setStyle(Font.BOLD);
            fuente.setSize(16);
            fuente.setColor(Color.BLUE);
            String ruta_guardada=ruta.getAbsolutePath();
            System.out.println("Ruta:"+ruta_guardada);
             //Crea el documento
            OutputStream file=new FileOutputStream(new File(ruta_guardada+".pdf"));
            PdfWriter separa=PdfWriter.getInstance(documento, file);

            documento.open();
            
            //Encabezado
           // Image imagen = Image.getInstance("C://Blog/Itext/images/00.jpg");//Agregamos la imagen al documento.                                
            Paragraph encabezado=new Paragraph(nombre_reporte,fuente);
            encabezado.setAlignment(Element.ALIGN_CENTER);
            documento.add(encabezado);
            documento.add(new Paragraph(" "));
           // documento.add(imagen);
            documento.add(new Paragraph(" "));           
            //Crea la tabla en el pdf
            PdfPTable tabla= new PdfPTable(FRM_BusquedaActivos.tabla_activo.getColumnCount());
            PdfPCell cell;
            
            for (int i = 0; i < FRM_BusquedaActivos.tabla_activo.getColumnCount(); i++) {
                cell = new PdfPCell(new Paragraph(FRM_BusquedaActivos.tabla_activo.getColumnName(i),fuente_columnas)); 
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                tabla.addCell(cell);
                
                
            }
            //Extrae los datos de la tabla y los guarda en la tabla del pdf
            for (int rows = 0; rows < FRM_BusquedaActivos.tabla_activo.getRowCount() ; rows++) { //borre -1
                for (int cols = 0; cols < FRM_BusquedaActivos.tabla_activo.getColumnCount(); cols++) {
                    tabla.addCell(FRM_BusquedaActivos.tabla_activo.getModel().getValueAt(rows, cols).toString());

                }
            }
            
                tabla.setWidthPercentage(90);
                tabla.setHorizontalAlignment(Element.ALIGN_CENTER);
                documento.add(tabla);
           
                //Numero de pagina
            int numeroPaginas = separa.getCurrentPageNumber();
            PiedePagina pie_de_pagina =new PiedePagina();

            if (numeroPaginas > numeroPaginas - 1) {
                separa.setPageEvent(pie_de_pagina);
            }
            documento.close();
            file.close();
            Mensajes mensajes = new Mensajes();
            mensajes.msg_confirmacion(null, "Reporte creado con exito.");
            }
        }catch(DocumentException e){
            JOptionPane.showMessageDialog(null, "Error inesperado, no se pudo crear el reporte.");
            Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, e);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void PDf(String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
        
    
   


    
}
