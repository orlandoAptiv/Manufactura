/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Reportes;

import Clases.PlataformaGenteCorte;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.table.DefaultTableModel;
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
public class ExcelRPR_gua {
    
      File ArchivoXLS;
    HSSFSheet hoja;
    HSSFWorkbook libro;
    int Renglon=0, c=4;
    Row RenglonGAP;
    int columna=0;
    CellStyle celdaStiloCabecera;
    CellStyle celdaDetalle;
    CellStyle celdaTotales;
    
     public ExcelRPR_gua(ArrayList<PlataformaGenteCorte> modelos,  String Nombre,DefaultTableModel modelo,ArrayList<PlataformaGenteCorte>modelosGAP,DefaultTableModel modeloTgap) throws IOException{
       FileOutputStream archivo = null;
        try {
            /*La ruta donde se creará el archivo*/
            String rutaArchivo = System.getProperty("user.home")+"/desktop/RPR "+Nombre+".xls";
            /*Se crea el objeto de tipo File con la ruta del archivo*/
            File archivoXLS = new File(rutaArchivo);
            libro = new HSSFWorkbook();
            archivo = new FileOutputStream(archivoXLS);
            hoja = libro.createSheet("RPR");
            RegresaTituloStyle();
            RegresaStyleDetalle();
            Row renglon=hoja.createRow(0);
         
            renglon=hoja.createRow(renglon.getRowNum());
                    hoja.addMergedRegion(new CellRangeAddress(0, 0, 1, 10) );
                    Cell celda=null;
                    
                        celda=renglon.createCell(1);
                        celda.setCellStyle(getTituloStyleTotales(HSSFColor.LIGHT_TURQUOISE.index));
                       // celda.setCellStyle(celdaStiloCabecera);
                     
                    
                     celda=renglon.getCell(1);
                    celda.setCellValue("REPORTE RPR GUAMUCHIL   "  +       "   FECHA:  "   +   GetFecha(new Date()).toString());
                    
//                   
                   
                  
           for(PlataformaGenteCorte g:modelos)
           {
            renglon=hoja.createRow(renglon.getRowNum()+1);
             
            if(g.modeloExportar.getRowCount()>0)
            {            
                 renglon=hoja.createRow(renglon.getRowNum()+1);
             
                for(int i=1; i<g.modeloExportar.getColumnCount(); i++)
                {
                    // acomodo columna gsc,msd,mfg,gap
                     
                    hoja.autoSizeColumn(i);
                    celda=renglon.createCell(i+1);
                    celda.setCellValue(g.modeloExportar.getColumnName(i).toString());
                    celda.setCellStyle(celdaStiloCabecera);
                   
                      celda.setCellStyle(getTituloStyleTotales(HSSFColor.LIGHT_CORNFLOWER_BLUE.index));  
                        
                }
            }
            
             celda =renglon.createCell(0);
            celda.setCellValue(g.NombrePlataforma);
            celda.setCellStyle(celdaStiloCabecera);
              celda.setCellStyle(getTituloStyleTotales(HSSFColor.LIGHT_CORNFLOWER_BLUE.index));
            
              
              
            ///datos de plataformas
            for(int x=0; x<g.modeloExportar.getRowCount(); x++)
            {
                //muestra registros del modelo
                renglon=hoja.createRow(renglon.getRowNum()+1);
               
               celda=null;
                   for(int i=0; i<g.modeloExportar.getColumnCount(); i++)
                    {
                        if(i!=g.modeloExportar.getColumnCount()-1)
                        {   
                        celda =renglon.createCell(i+1);
                        try{
                            celda.setCellValue(Double.parseDouble( g.modeloExportar.getValueAt(x, i).toString()));
                        }catch(Exception e)
                        {
                            celda.setCellValue( g.modeloExportar.getValueAt(x, i).toString());
                        }
                        }
                      
                   celda.setCellStyle(celdaDetalle);
                 // celda.setCellStyle(getTituloStyleTotales(HSSFColor.LIGHT_TURQUOISE.index));
                     
                    }
            }
           }
           
          ////panel totales 
            renglon=hoja.createRow(13);
                for(int i=1; i<modelo.getColumnCount(); i++)
                {
                 
                   hoja.autoSizeColumn(i);
                    celda=renglon.createCell(i+1);
                    celda.setCellValue(modelo.getColumnName(i).toString());
                    celda.setCellStyle(getTituloStyleTotales(HSSFColor.LIGHT_ORANGE.index));
                }
                 
               
            celda =renglon.createCell(0);
            celda.setCellValue("TOTAL");
            celda.setCellStyle(celdaStiloCabecera);
              celda.setCellStyle(getTituloStyleTotales(HSSFColor.LIGHT_CORNFLOWER_BLUE.index));
            
                for(int x=0; x<modelo.getRowCount(); x++)
            {
                     Row renglon2=hoja.createRow((x+14));

                     Cell celda2=null;
                   for(int i=2; i<modelo.getColumnCount()+1; i++)
                    {
                        celda =renglon2.createCell(i-1);
                        try{
                            celda2.setCellValue(Double.parseDouble( modelo.getValueAt(x, i).toString()));
                        }catch(Exception e)
                        {
                            celda.setCellValue( modelo.getValueAt(x, i-2).toString());
                        }

                   // celda.setCellStyle(getTituloStyleTotales(HSSFColor.LIGHT_TURQUOISE.index));

                       celda.setCellStyle(celdaDetalle);
                    }
            }
        
                //gap plataforma
           
            //renglon=hoja.createRow(renglon.getRowNum()+1);
             RenglonGAP=hoja.getRow(1);
            if(modelosGAP.get(0).modeloExportarGAP.getRowCount()>0)
            {            
                //renglon=hoja.getRow(3);
                RenglonGAP=hoja.getRow(RenglonGAP.getRowNum()+1);
                
                for(int i=0; i<modelosGAP.get(0).modeloExportarGAP.getColumnCount(); i++)
                {
                
                     
                    hoja.autoSizeColumn(i+3);
                    celda=RenglonGAP.createCell(i+7);
                    celda.setCellValue(modelosGAP.get(0).modeloExportarGAP.getColumnName(i).toString());
                    celda.setCellStyle(celdaStiloCabecera);
                      celda.setCellStyle(getTituloStyleTotales(HSSFColor.LIGHT_CORNFLOWER_BLUE.index));
                }
            }
          
           for(PlataformaGenteCorte g2:modelosGAP){
              
            for(int x=1; x<g2.modeloExportarGAP.getRowCount(); x++)
            {
                //muestra registros del modelo
               RenglonGAP=hoja.getRow(RenglonGAP.getRowNum()+1);
                 
                celda=null;
                   for(int i=0; i<g2.modeloExportarGAP.getColumnCount(); i++)
                    {
                        if(i!=g2.modeloExportarGAP.getColumnCount())
                        {   
                            // renglon=hoja.getRow(3);
                        celda =RenglonGAP.createCell(i+7);
                        try{
                            celda.setCellValue(Double.parseDouble( g2.modeloExportarGAP.getValueAt(x, i).toString()));
                                                           
                        }catch(Exception e)
                        {
                            celda.setCellValue( g2.modeloExportarGAP.getValueAt(x, i).toString());
                        }
                        }
                 //  celda.setCellStyle(getTituloStyleTotales(HSSFColor.LIGHT_TURQUOISE.index));

                   celda.setCellStyle(celdaStiloCabecera);
                    }
                   RenglonGAP=hoja.getRow(RenglonGAP.getRowNum()+modelos.get(0).modeloExportar.getRowCount()+1);
            }
     
           }
       
             ////panel total GAP
           
                     Row renglon2=hoja.getRow((13));
     
                     Cell celda2=null;
                   for(int i=2; i<modeloTgap.getColumnCount()+2; i++)
                    {
                        celda =renglon2.createCell(i+5);
                        try{
                            celda2.setCellValue(Double.parseDouble( modelo.getValueAt(0, i).toString()));
                        }catch(Exception e)
                        {
                            celda.setCellValue( modeloTgap.getValueAt(0, i-2).toString());
                        }
                                     //   celda.setCellStyle(getTituloStyleTotales(HSSFColor.GREY_25_PERCENT.index));

                       celda.setCellStyle(celdaStiloCabecera);
                    }
           
        
             
         
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
                System.out.println(ex.toString());
            }
        }
    }
     
     
//     
     
         
     public void RegresaTituloStyle()
    {
                 //CREAMOS CABECERAS DEL REPORTE 
            celdaStiloCabecera=libro.createCellStyle();
            celdaStiloCabecera.setAlignment((short)2);
            Font cellF=libro.createFont();
            cellF.setFontName(HSSFFont.FONT_ARIAL);
            cellF.setFontHeightInPoints((short)10);
            cellF.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            celdaStiloCabecera.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
            celdaStiloCabecera.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
            celdaStiloCabecera.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
            celdaStiloCabecera.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
            celdaStiloCabecera.setFont(cellF);
      }
     
      public void RegresaStyleDetalle()
    {
            celdaDetalle=libro.createCellStyle();
            Font cellFCF=libro.createFont();
            cellFCF.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
            cellFCF.setFontHeightInPoints((short)8);
            cellFCF.setFontName(HSSFFont.FONT_ARIAL);
            celdaDetalle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            celdaDetalle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            celdaDetalle.setBorderRight(HSSFCellStyle.BORDER_THIN);
            celdaDetalle.setBorderTop(HSSFCellStyle.BORDER_THIN);
            celdaDetalle.setFillBackgroundColor(HSSFColor.GREY_50_PERCENT.index);
    }
       public CellStyle getTituloStyleTotales(short color){
            //CREAMOS CABECERAS DEL REPORTE 
            celdaTotales=libro.createCellStyle();
            celdaTotales.setAlignment((short)2);
            Font cellSF=libro.createFont();
            cellSF.setFontName(HSSFFont.FONT_ARIAL);
            cellSF.setFontHeightInPoints((short)12);
            cellSF.setColor(HSSFColor.BLACK.index);
            cellSF.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            celdaTotales.setFillForegroundColor(color);
            celdaTotales.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

            celdaTotales.setBorderBottom(HSSFCellStyle.BORDER_DOUBLE);
            celdaTotales.setBorderLeft(HSSFCellStyle.BORDER_DOUBLE);
            celdaTotales.setBorderRight(HSSFCellStyle.BORDER_DOUBLE);
            celdaTotales.setBorderTop(HSSFCellStyle.BORDER_DOUBLE);
            celdaTotales.setFont(cellSF);
            return celdaTotales;
      } 
        String GetFecha(java.util.Date fecha) {
     return new SimpleDateFormat("dd-MM-yyyy").format(fecha).toString();
    }
       //METODO QUE REGRESA LA LETRA DE LA COLUMNA
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
       
       
       public void getStyleDetalle(){
            celdaDetalle=libro.createCellStyle();
            Font cellFCF=libro.createFont();
            cellFCF.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
                cellFCF.setFontHeightInPoints((short)8);
            cellFCF.setFontName(HSSFFont.FONT_ARIAL);
            celdaDetalle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            celdaDetalle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            celdaDetalle.setBorderRight(HSSFCellStyle.BORDER_THIN);
            celdaDetalle.setBorderTop(HSSFCellStyle.BORDER_THIN);
            celdaDetalle.setFillBackgroundColor(HSSFColor.GREY_50_PERCENT.index);
    }
        
       public CellStyle getStyleDetallePorc() {
            CellStyle celdaDetalle=libro.createCellStyle();
            Font cellFCF=libro.createFont();
            cellFCF.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
            cellFCF.setFontHeightInPoints((short)8);
            cellFCF.setFontName(HSSFFont.FONT_ARIAL);
            celdaDetalle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            celdaDetalle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            celdaDetalle.setBorderRight(HSSFCellStyle.BORDER_THIN);
            celdaDetalle.setBorderTop(HSSFCellStyle.BORDER_THIN);
            celdaDetalle.setDataFormat(libro.createDataFormat().getFormat("0.00%"));
            celdaDetalle.setFillBackgroundColor(HSSFColor.GREY_50_PERCENT.index);
            return celdaDetalle;
    } 
        
       public CellStyle getStyleDetallePorc(short  color) {
            CellStyle celdaDetalleS=libro.createCellStyle();
             Font cellSF=libro.createFont();
            cellSF.setFontName(HSSFFont.FONT_ARIAL);
            cellSF.setFontHeightInPoints((short)12);
            cellSF.setColor(HSSFColor.BLACK.index);
            cellSF.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            celdaDetalleS.setFont(cellSF);
            celdaDetalleS.setBorderBottom(HSSFCellStyle.BORDER_DOUBLE);
            celdaDetalleS.setBorderLeft(HSSFCellStyle.BORDER_DOUBLE);
            celdaDetalleS.setBorderRight(HSSFCellStyle.BORDER_DOUBLE);
            celdaDetalleS.setBorderTop(HSSFCellStyle.BORDER_DOUBLE);
            celdaDetalleS.setFillForegroundColor(color);
            celdaDetalleS.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            celdaDetalleS.setDataFormat(libro.createDataFormat().getFormat("0.00%"));
            celdaDetalleS.setFillBackgroundColor(HSSFColor.GREY_50_PERCENT.index);
            return celdaDetalleS;
    }
       
        public void getTituloStyle(){
                 //CREAMOS CABECERAS DEL REPORTE 
            celdaStiloCabecera=libro.createCellStyle();
            celdaStiloCabecera.setAlignment((short)2);
            Font cellF=libro.createFont();
            cellF.setFontName(HSSFFont.FONT_ARIAL);
            cellF.setFontHeightInPoints((short)9);
            cellF.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            celdaStiloCabecera.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
            celdaStiloCabecera.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
            celdaStiloCabecera.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
            celdaStiloCabecera.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
            celdaStiloCabecera.setFont(cellF);
      }
}
