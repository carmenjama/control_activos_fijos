/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reportes;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfPageEventHelper;
import com.lowagie.text.pdf.PdfWriter;
import java.text.SimpleDateFormat;

/**
 *
 * @author Ana
 */
public class PiedePagina extends PdfPageEventHelper{
    SimpleDateFormat formateador = new SimpleDateFormat(" dd/MM/yyyy hh:mm:ss");
        
   java.util.Date date=new java.util.Date();
    public void onEndPage(PdfWriter writer, Document document) {
        Paragraph fecha=new Paragraph(String.valueOf(formateador.format(date))+"\n"+"\n");
       ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_LEFT, new Phrase("Fecha " + fecha),document.right() - 2 , 0, 0);
       ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_RIGHT, new Phrase("Pàgina " + document.getPageNumber()+"          "), document.left() - 2 , 0, 0);
    }
}
