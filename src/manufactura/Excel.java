/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package manufactura;

import Clases.Cadena;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;

/**
 *
 * @author gzld6k
 */
public class Excel {
    
    File ArchivoXLS;
    HSSFSheet hoja;
    HSSFWorkbook libro;
    int Renglon=0;
    int columna=0;
    
    public Excel(ArrayList<Cadena> Cadena, Cadena Totales) throws IOException
    {
       FileOutputStream archivo = null;
        try {
            /*La ruta donde se creará el archivo*/
            String rutaArchivo = System.getProperty("user.home")+"/desktop/Eficiencia.xls";
            /*Se crea el objeto de tipo File con la ruta del archivo*/
            File archivoXLS = new File(rutaArchivo);
            libro = new HSSFWorkbook();
            archivo = new FileOutputStream(archivoXLS);
            hoja = libro.createSheet("Reporte"); 
            
            //RECORREMOS EL ARCHIVO EXCEL INSERTANDO RENGLONES EN BLANCO Y LAS CABECERAS
            
            //CREAMOS CABECERAS DEL REPORTE 
            CellStyle cs=libro.createCellStyle();
            cs.setAlignment((short)2);
            Font cellF=libro.createFont();
            cellF.setFontName(HSSFFont.FONT_ARIAL);
            cellF.setFontHeightInPoints((short)14);
            cellF.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            cs.setFont(cellF);
            CellStyle csEnc=libro.createCellStyle();
            csEnc.setAlignment((short)2);
            cellF=libro.createFont();
            cellF.setFontName(HSSFFont.FONT_ARIAL);
            cellF.setFontHeightInPoints((short)10);
            cellF.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            csEnc.setWrapText(true);
            csEnc.setFont(cellF);
       
            Row fila=hoja.createRow(0);
            Cell celda=fila.createCell(0);
            celda.setCellValue("REPORTE GENERAL DE EFICIENCIAS PLANTA");
            celda.setCellStyle(cs);
            hoja.addMergedRegion(new CellRangeAddress(0, 2, 0, 15));
            fila=hoja.createRow(3);
            Cell celda1=fila.createCell(0);
            celda1.setCellValue("Fecha Rev.:    "+ GetFecha(new Date()));
            celda1.setCellStyle(cs);
            hoja.addMergedRegion(new CellRangeAddress(3, 3, 0, 15));
             fila=hoja.createRow(5);
             celda1=fila.createCell(2);
             hoja.setColumnWidth(2, 4000);
            celda1.setCellValue("CADENA");
            celda1.setCellStyle(csEnc);
            hoja.addMergedRegion(new CellRangeAddress(5, 6, 2, 2));
            fila=hoja.createRow(7);
            celda1=fila.createCell(2);
            celda1.setCellValue("I");
            celda1.setCellStyle(csEnc);
            fila=hoja.createRow(8);
            celda1=fila.createCell(2);
            celda1.setCellValue("II");
            celda1.setCellStyle(csEnc);
            fila=hoja.createRow(9);
            celda1=fila.createCell(2);
            celda1.setCellValue("III");
            celda1.setCellStyle(csEnc);
            fila=hoja.createRow(10);
            celda1=fila.createCell(2);
            celda1.setCellValue("CORTE");
            celda1.setCellStyle(csEnc);
            fila=hoja.createRow(11);
            celda1=fila.createCell(2);
            celda1.setCellValue("SERVICIOS");
            celda1.setCellStyle(csEnc);
            fila=hoja.createRow(12);
            celda1=fila.createCell(2);
            celda1.setCellValue("TOTAL");
            celda1.setCellStyle(csEnc);
            fila=hoja.createRow(13);
            celda1=fila.createCell(2);
            celda1.setCellValue("TOTAL TA Y TB");
            celda1.setCellStyle(csEnc);
            // hoja.addMergedRegion(new CellRangeAddress(5, 6, 1, 2));
            hoja.setColumnWidth(1, 10000);
            //hoja.setc
            fila=hoja.getRow(5);
            celda1=fila.createCell(3);
            
            celda1.setCellValue("TOTAL GENTE");
            celda1.setCellStyle(csEnc);
            hoja.addMergedRegion(new CellRangeAddress(5, 5, 3, 4));
            fila=hoja.getRow(5);
            celda1=fila.createCell(5);
            celda1.setCellValue("EMB X DIA");
            celda1.setCellStyle(csEnc);
            hoja.addMergedRegion(new CellRangeAddress(5, 5, 5,6));
            fila=hoja.getRow(5);
            celda1=fila.createCell(7);
            celda1.setCellValue("HRS.PAG X DIA");
            celda1.setCellStyle(csEnc);
            hoja.addMergedRegion(new CellRangeAddress(5, 5, 7,8));
            fila=hoja.getRow(5);
            celda1=fila.createCell(9);
            celda1.setCellValue("EFIC.X TURNO");
            celda1.setCellStyle(csEnc);
            hoja.addMergedRegion(new CellRangeAddress(5, 5, 9,10));
            
             fila=hoja.getRow(5);
            celda1=fila.createCell(11);
            celda1.setCellValue("EFIC.X CADENA");
            celda1.setCellStyle(csEnc);
           
            hoja.addMergedRegion(new CellRangeAddress(5, 6, 11,11));
            fila=hoja.createRow(6);
            celda1=fila.createCell(3);
            hoja.setColumnWidth(3, 3000);
            celda1.setCellValue("TURNO A");
            celda1.setCellStyle(csEnc);
            fila=hoja.getRow(6);
            celda1=fila.createCell(4);
            hoja.setColumnWidth(4, 3000);
            celda1.setCellValue("TURNO B");
            celda1.setCellStyle(csEnc);
            fila=hoja.getRow(6);
            celda1=fila.createCell(5);
            hoja.setColumnWidth(5, 3000);
            celda1.setCellValue("TURNO A");
            celda1.setCellStyle(csEnc);
            fila=hoja.getRow(6);
            celda1=fila.createCell(6);
            hoja.setColumnWidth(6, 3000);
            celda1.setCellValue("TURNO B");
            celda1.setCellStyle(csEnc);
            fila=hoja.getRow(6);
            celda1=fila.createCell(7);
            hoja.setColumnWidth(7, 3000);
            celda1.setCellValue("TURNO A");
            celda1.setCellStyle(csEnc);
            fila=hoja.getRow(6);
            celda1=fila.createCell(8);
            hoja.setColumnWidth(8, 3000);
            celda1.setCellValue("TURNO B");
            celda1.setCellStyle(csEnc);
              fila=hoja.getRow(6);
            celda1=fila.createCell(9);
            hoja.setColumnWidth(9, 3000);
            celda1.setCellValue("TURNO A");
            celda1.setCellStyle(csEnc);
            fila=hoja.getRow(6);
            celda1=fila.createCell(10);
            hoja.setColumnWidth(10, 3000);
            celda1.setCellValue("TURNO B");
            celda1.setCellStyle(csEnc);
           
                for(int i =0; i<Cadena.size(); i++)
                {
                   fila=hoja.getRow(7+i);
                   celda1=fila.createCell(3);
                   celda1.setCellValue(Cadena.get(i).totalGenteA);
                   celda1=fila.createCell(4);
                   celda1.setCellValue(Cadena.get(i).totalGenteB);
                   celda1=fila.createCell(5);
                   celda1.setCellValue(Cadena.get(i).TotalHrsEmbA);
                   celda1=fila.createCell(6);
                   celda1.setCellValue(Cadena.get(i).TotalHrsEmbB);
                   celda1=fila.createCell(7);
                   celda1.setCellValue(Cadena.get(i).TotalHrsPagA);
                   celda1=fila.createCell(8);
                   celda1.setCellValue(Cadena.get(i).TotalHrsPagB);
                   celda1=fila.createCell(9);
                   celda1.setCellValue(Cadena.get(i).EficienciaA);
                   celda1=fila.createCell(10);
                   celda1.setCellValue(Cadena.get(i).EficienciaB);
                   celda1=fila.createCell(11);
                   celda1.setCellValue(Cadena.get(i).TotalCadena);
                }
             fila=hoja.getRow(13);
             celda1=fila.createCell(3);
             celda1.setCellValue(Totales.totalGenteA);
             celda1.setCellStyle(csEnc);
             hoja.addMergedRegion(new CellRangeAddress(13, 13, 3,4));
             
             celda1=fila.createCell(5);
             celda1.setCellValue(Totales.TotalHrsEmbA);
             celda1.setCellStyle(csEnc);
             hoja.addMergedRegion(new CellRangeAddress(13, 13, 5,6));
             
             celda1=fila.createCell(7);
             celda1.setCellValue(Totales.TotalHrsPagA);
             celda1.setCellStyle(csEnc);
             hoja.addMergedRegion(new CellRangeAddress(13, 13, 7,8));
             celda1=fila.createCell(11);
             celda1.setCellValue(Totales.EficienciaA);
             celda1.setCellStyle(csEnc);
             //hoja.addMergedRegion(new CellRangeAddress(13, 13, 7,8));
            HSSFCellStyle csCabeceraFecha=libro.createCellStyle();
            HSSFFont cellFCF=libro.createFont();
            cellFCF.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            cellFCF.setFontHeightInPoints((short)12);
            cellFCF.setFontName(HSSFFont.FONT_ARIAL);
            //csCabeceraFecha.setFillPattern(HSSFCellStyle.FINE_DOTS);
            csCabeceraFecha.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
            csCabeceraFecha.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
            csCabeceraFecha.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
            csCabeceraFecha.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            //csCabeceraFecha.setFillPattern(HSSFCellStyle.ALT_BARS);
            csCabeceraFecha.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
            csCabeceraFecha.setFillBackgroundColor(HSSFColor.YELLOW.index);
            csCabeceraFecha.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
            csCabeceraFecha.setAlignment((short)2);
           // csCabeceraFecha.setWrapText(true);
   
     
            CellStyle cstyleDetalle=libro.createCellStyle();
            cstyleDetalle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            cstyleDetalle.setBorderLeft((HSSFCellStyle.BORDER_THIN));
            cstyleDetalle.setBorderRight(HSSFCellStyle.BORDER_THIN);
            cstyleDetalle.setBorderTop(HSSFCellStyle.BORDER_THIN);
            cstyleDetalle.setAlignment((short)2);
            CellStyle CstyleEncontrado=libro.createCellStyle();
            CstyleEncontrado.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            CstyleEncontrado.setFillForegroundColor(HSSFColor.DARK_YELLOW.index);
            CstyleEncontrado.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            CstyleEncontrado.setBorderLeft((HSSFCellStyle.BORDER_THIN));
            CstyleEncontrado.setBorderRight(HSSFCellStyle.BORDER_THIN);
            CstyleEncontrado.setBorderTop(HSSFCellStyle.BORDER_THIN);
            CstyleEncontrado.setAlignment((short)2);

//            //BUSCAR EL VALOR EN LA LISTA DECOLUMNA
      
        libro.write(archivo);
        /*Cerramos el flujo de datos*/
        archivo.close();
        /*Y abrimos el archivo con la clase Desktop*/
        Desktop.getDesktop().open(archivoXLS);
        } catch (Exception ex) {
           System.out.println(ex.toString());
        } finally {
            try {
                archivo.close();
            } catch (IOException ex) {
                Logger.getLogger(Excel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    String GetFecha(java.util.Date fecha) {
     return new SimpleDateFormat("yyyy-MM-dd").format(fecha).toString();
    } 
    private String getColumnName(int columnNumber) {     
        String columnName = "";
        int dividend = columnNumber + 1;
        int modulus;
 
        while (dividend > 0){
            modulus = (dividend - 1) % 26;
            columnName = (char)(65 + modulus) + columnName;
            dividend = (int)((dividend - modulus) / 26);
        } 
 
        return columnName;
    }
    //CREAMOS CABECERAS DE LAS FECHAS
    public CellStyle RegresaTituloFechaStyle()
    {
            CellStyle csCabeceraFecha=libro.createCellStyle();
            Font cellFCF=libro.createFont();
            cellFCF.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            cellFCF.setFontHeightInPoints((short)12);
            cellFCF.setFontName(HSSFFont.FONT_ARIAL);
            csCabeceraFecha.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
            csCabeceraFecha.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
            csCabeceraFecha.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
            csCabeceraFecha.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
            csCabeceraFecha.setFillBackgroundColor(HSSFColor.GREY_50_PERCENT.index);
            return csCabeceraFecha;
    }
    
    //CREAMOS CELLSTYLE CON LA CONFIGURACION DELOS TITULOS
  
    
}
