/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Reportes;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class ExcelDistriHCPorCodigo {
    
    File ArchivoXLS;
    HSSFSheet hoja;
    HSSFWorkbook libro;
    int Renglon=0;
    int columna=0;
    int RenglonActivo=0;
    CellStyle celdaStiloCabecera;
    CellStyle celdaDetalle;
    CellStyle celdaTotales;
     public ExcelDistriHCPorCodigo()
     {
         
     }
     public void  GetDistriHCPorCodigo(DefaultTableModel modelodetalleA, DefaultTableModel modelodetalleB, DefaultTableModel modeloTotales, String Nombre) throws IOException{
       FileOutputStream archivo = null;
        try {
            /*La ruta donde se creará el archivo*/
            String rutaArchivo = System.getProperty("user.home")+"/desktop/Reporte Eficiencia "+Nombre+".xls";
            /*Se crea el objeto de tipo File con la ruta del archivo*/
            File archivoXLS = new File(rutaArchivo);
            libro = new HSSFWorkbook();
            archivo = new FileOutputStream(archivoXLS);
            hoja = libro.createSheet("Reporte");
            Row renglon=null;
            getTituloStyle();
            getStyleDetalle();
           
            if(modelodetalleA.getRowCount()>0)
            {
                renglon =hoja.createRow(RenglonActivo);
                 Cell celda=renglon.createCell(modelodetalleA.getColumnCount());
                 celda.setCellValue("Fecha: ");
                 celda=renglon.createCell(modelodetalleA.getColumnCount()+1);
                 celda.setCellValue(GetFecha(new Date()).toString());
                 RenglonActivo++;
                 renglon=hoja.createRow(RenglonActivo);
                 hoja.addMergedRegion(new CellRangeAddress(1,1, 1,6));
                 celda=renglon.createCell(1);
                 celda.setCellValue("REPORTE DE EFICIENCIA POR PLATAFORMA");
                 celda.setCellStyle(getTituloStylePrincipal());
                 RenglonActivo++;
                                        
                 renglon=hoja.createRow(RenglonActivo);
                 //FOR QUE RECORRE ENCABEZADO
                for(int i=2; i<modelodetalleA.getColumnCount()+2; i++)
                {
                   hoja.setColumnWidth(i, modelodetalleA.getColumnName(i-2).length()*450);
                    celda=renglon.createCell(i);
                    celda.setCellValue(modelodetalleA.getColumnName(i-2).toString());
                    celda.setCellStyle(celdaStiloCabecera);
                }
            }
            RenglonActivo++;
            //METODO QUE RECORRE EL TURNO A Y LO AGREGA AL EXCEL
            for(int x=0; x<modelodetalleA.getRowCount(); x++)
            {
                      renglon=hoja.createRow((x+RenglonActivo));
                     Cell celda=null;
                   for(int i=2; i<modelodetalleA.getColumnCount()+2; i++)
                    {
                        celda =renglon.createCell(i);
                        try{
                            celda.setCellValue(Double.parseDouble( modelodetalleA.getValueAt(x, i-2).toString()));
                        }catch(Exception e)
                        {
                            celda.setCellValue( modelodetalleA.getValueAt(x, i-2).toString());
                        }

                       celda.setCellStyle(celdaDetalle);
                    }
            }
            //FOR QUE RECORRE TOTAL DE TURNO A Y LO AGREGA AL FINAL DEL DETALLE A
            renglon=hoja.createRow(RenglonActivo+modelodetalleA.getRowCount());
            RenglonActivo+=2+modelodetalleA.getRowCount();
             for(int c=2; c<modeloTotales.getColumnCount()+2;c++)
                {
                    Cell celda=null;
                    celda =renglon.createCell(c);
                    try
                    {
                        celda.setCellValue(Double.parseDouble(modeloTotales.getValueAt(0, c-2).toString()));
                    }catch(Exception e)
                    {
                        celda.setCellValue(modeloTotales.getValueAt(0, c-2).toString());
                    }
                    celda.setCellStyle(getTituloStyleTotales((int)HSSFColor.GREEN.index));
                }
            
                 //FOR QUE RECORRE ENCABEZADO
             renglon=hoja.createRow(RenglonActivo);
           for(int i=2; i<modelodetalleA.getColumnCount()+2; i++)
           {
               Cell celda=null;
               celda=renglon.createCell(i);
               celda.setCellValue(modelodetalleA.getColumnName(i-2).toString());
               celda.setCellStyle(celdaStiloCabecera);
           }
           //FOR QUE RECORRE TURNO B Y LO AGREGA 
           for(int x=RenglonActivo; x<modelodetalleB.getRowCount()+RenglonActivo; x++)
            {
                      renglon=hoja.createRow((x+1));

                     Cell celda=null;
                   for(int i=2; i<modelodetalleB.getColumnCount()+2; i++)
                    {
                        celda =renglon.createCell(i);
                        try{
                            celda.setCellValue(Double.parseDouble( modelodetalleB.getValueAt(x-RenglonActivo, i-2).toString()));
                        }catch(Exception e)
                        {
                            celda.setCellValue( modelodetalleB.getValueAt(x-RenglonActivo, i-2).toString());
                        }

                       celda.setCellStyle(celdaDetalle);
                    }
            }
            renglon=hoja.createRow(RenglonActivo+modelodetalleB.getRowCount());
            RenglonActivo+=2+modelodetalleB.getRowCount();
             for(int c=2; c<modeloTotales.getColumnCount()+2;c++)
                {
                    Cell celda=null;
                    celda =renglon.createCell(c);
                    try
                    {
                        celda.setCellValue(Double.parseDouble(modeloTotales.getValueAt(1, c-2).toString()));
                    }catch(Exception e)
                    {
                        celda.setCellValue(modeloTotales.getValueAt(1, c-2).toString());
                    }
                    celda.setCellStyle(getTituloStyleTotales((int)HSSFColor.ORANGE.index));
                }
            renglon=hoja.createRow(RenglonActivo+4);
           // RenglonActivo+=2+modelodetalleB.getRowCount();
            
            //recorre
            for(int c=2; c<modeloTotales.getColumnCount()+2;c++)
                {
                    Cell celda=null;
                    celda =renglon.createCell(c);
                    try
                    {
                        celda.setCellValue(Double.parseDouble(modeloTotales.getValueAt(2, c-2).toString()));
                    }catch(Exception e)
                    {
                        celda.setCellValue(modeloTotales.getValueAt(2, c-2).toString());
                    }
                    celda.setCellStyle(getTituloStyleTotales((int)HSSFColor.YELLOW.index));
                }
            
            //FOR PARA PONER LOS TOTALES DE TURNO A Y B PLANTA
//            for(int r=modelodetalleA.getRowCount()+5; r<modelodetalleA.getRowCount()+modeloTotales.getRowCount()+5; r++)
//            {
//                 renglon=hoja.createRow(r);
//                Cell celda=null;
//                //if(!modeloTotales.getValueAt(r, 2).equals("A"))
//                for(int c=2; c<modeloTotales.getColumnCount()+2;c++)
//                {
//                    celda =renglon.createCell(c);
//                   
//                    try
//                    {
//                        celda.setCellValue(Double.parseDouble(modeloTotales.getValueAt(r-modelodetalleA.getRowCount()-5, c-2).toString()));
//                    }catch(Exception e)
//                    {
//                        celda.setCellValue(modeloTotales.getValueAt(r-modelodetalleA.getRowCount()-5, c-2).toString());
//                    }
//                    if(modeloTotales.getValueAt(r-modelodetalleA.getRowCount()-5, 2).toString().equals("A"))
//                        celda.setCellStyle(getTituloStyleTotales((int)HSSFColor.GREEN.index));
//                    else if(modeloTotales.getValueAt(r-modelodetalleA.getRowCount()-5, 2).toString().equals("B"))
//                        celda.setCellStyle(getTituloStyleTotales((int)HSSFColor.ORANGE.index));
//                    else
//                        celda.setCellStyle(getTituloStyleTotales((int)HSSFColor.YELLOW.index));
//                    
//                }
//                
//            }
//            

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
    
     public ExcelDistriHCPorCodigo(ArrayList<DefaultTableModel> modelo, ArrayList<String> Nombre) throws IOException{
       FileOutputStream archivo = null;
        try {
            /*La ruta donde se creará el archivo*/
            String rutaArchivo = System.getProperty("user.home")+"/desktop/Reporte HC "+Nombre+".xls";
            /*Se crea el objeto de tipo File con la ruta del archivo*/
            File archivoXLS = new File(rutaArchivo);
            libro = new HSSFWorkbook();
            archivo = new FileOutputStream(archivoXLS);
            for(int ho=0; ho<modelo.size(); ho++){
            hoja = libro.createSheet("Reporte"+ho);
            getTituloStyle();
            getStyleDetalle();
            if(modelo.get(ho).getRowCount()>0)
            {
                 Row renglonEncabezado=hoja.createRow(1);
                if(!Nombre.get(ho).equals("CODIGO"))
                {
                    Cell c= renglonEncabezado.createCell(1);
                    c.setCellValue("HC. DIRECTA");
                    c.setCellStyle(celdaStiloCabecera);
                    hoja.addMergedRegion(new CellRangeAddress(1, 1, 1, 10));
                     c=renglonEncabezado.createCell(11);
                     c.setCellValue("HC. IND.");
                    c.setCellStyle(celdaStiloCabecera);
                }
                else
                {
                     Cell c= renglonEncabezado.createCell(3);
                     c.setCellValue("HC. DIRECTA");
                     c.setCellStyle(celdaStiloCabecera);
                     hoja.addMergedRegion(new CellRangeAddress(1, 1, 3, 12));
                      c=renglonEncabezado.createCell(13);
                     c.setCellValue("HC. IND.");
                     c.setCellStyle(celdaStiloCabecera);
                }
                
                Row renglon=hoja.createRow(2);
                for(int i=0; i<modelo.get(ho).getColumnCount(); i++)
                {
                    hoja.setColumnWidth(i, modelo.get(ho).getColumnName(i).length()*500);
                    Cell celda=renglon.createCell(i);
                    celda.setCellValue(modelo.get(ho).getColumnName(i).toString());
                    celda.setCellStyle(celdaStiloCabecera);
                }
            }
            for(int x=0; x<modelo.get(ho).getRowCount(); x++)
            {
                Row renglon=hoja.createRow(x+3);
                   for(int i=0; i<modelo.get(ho).getColumnCount(); i++)
                    {
                         Cell celda=renglon.createCell(i);
                        try{
                            celda.setCellValue(Double.parseDouble( modelo.get(ho).getValueAt(x, i).toString()));
                        }catch(Exception e)
                        {
                            celda.setCellValue( modelo.get(ho).getValueAt(x, i).toString());
                        }       
                       celda.setCellStyle(celdaDetalle);
                    }
            }
            Row renglon=hoja.createRow(modelo.get(ho).getRowCount()+3);
            Cell celda=renglon.createCell(0);
            celda.setCellValue("TOTAL");
            celda.setCellStyle(celdaDetalle);
            for(int i =1; i<modelo.get(ho).getColumnCount(); i++)
            {
                 celda=renglon.createCell(i);
                String formula="SUM("+getColumnName(i)+"4:"+getColumnName(i)+(modelo.get(ho).getRowCount()+3)+")";
                celda.setCellFormula(formula);
                celda.setCellStyle(celdaDetalle);
            }
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
                Logger.getLogger(ExcelDistriHCPorCodigo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    //METODO QUE REGRESA LA FECHA EN FORMATO MYSQL 
    
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
    //CREAMOS CABECERAS DE LAS FECHAS
     public void getTituloStyle(){
                 //CREAMOS CABECERAS DEL REPORTE 
            celdaStiloCabecera=libro.createCellStyle();
            celdaStiloCabecera.setAlignment((short)2);
            Font cellF=libro.createFont();
            cellF.setFontName(HSSFFont.FONT_ARIAL);
            cellF.setFontHeightInPoints((short)12);
            cellF.setColor(HSSFColor.WHITE.index);
            cellF.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
          //  celdaStiloCabecera.setFillBackgroundColor(HSSFColor.ORANGE.index);
            celdaStiloCabecera.setFillPattern(CellStyle.SOLID_FOREGROUND);
            celdaStiloCabecera.setBorderBottom(HSSFCellStyle.BORDER_DOUBLE);
            celdaStiloCabecera.setBorderLeft(HSSFCellStyle.BORDER_DOUBLE);
            celdaStiloCabecera.setBorderRight(HSSFCellStyle.BORDER_DOUBLE);
            celdaStiloCabecera.setBorderTop(HSSFCellStyle.BORDER_DOUBLE);
            celdaStiloCabecera.setFont(cellF);
      }
     
         //CREAMOS CABECERAS DE LAS FECHAS
     public CellStyle getTituloStylePrincipal(){
                 //CREAMOS CABECERAS DEL REPORTE 
           CellStyle celdaStiloCabeceraS=libro.createCellStyle();
            celdaStiloCabeceraS.setAlignment((short)2);
            Font cellF=libro.createFont();
            cellF.setFontName(HSSFFont.FONT_ARIAL);
            cellF.setFontHeightInPoints((short)16);
            cellF.setColor(HSSFColor.BLACK.index);
            cellF.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
          //  celdaStiloCabecera.setFillBackgroundColor(HSSFColor.ORANGE.index);
          //  celdaStiloCabeceraS.setFillPattern(CellStyle.SOLID_FOREGROUND);
            celdaStiloCabeceraS.setBorderBottom(HSSFCellStyle.BORDER_DOUBLE);
            celdaStiloCabeceraS.setBorderLeft(HSSFCellStyle.BORDER_DOUBLE);
            celdaStiloCabeceraS.setBorderRight(HSSFCellStyle.BORDER_DOUBLE);
            celdaStiloCabeceraS.setBorderTop(HSSFCellStyle.BORDER_DOUBLE);
            celdaStiloCabeceraS.setFont(cellF);
            return celdaStiloCabeceraS;
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
    
     public CellStyle getTituloStyleTotales(int color){
            //CREAMOS CABECERAS DEL REPORTE 
            celdaTotales=libro.createCellStyle();
            celdaTotales.setAlignment((short)2);
            Font cellSF=libro.createFont();
            cellSF.setFontName(HSSFFont.FONT_ARIAL);
            cellSF.setFontHeightInPoints((short)12);
            cellSF.setColor(HSSFColor.BLACK.index);
            cellSF.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
           celdaTotales.setFillForegroundColor((short)color);
            celdaTotales.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

            celdaTotales.setBorderBottom(HSSFCellStyle.BORDER_DOUBLE);
            celdaTotales.setBorderLeft(HSSFCellStyle.BORDER_DOUBLE);
            celdaTotales.setBorderRight(HSSFCellStyle.BORDER_DOUBLE);
            celdaTotales.setBorderTop(HSSFCellStyle.BORDER_DOUBLE);
            celdaTotales.setFont(cellSF);
            return celdaTotales;
      } 
    //CREAMOS CELLSTYLE CON LA CONFIGURACION DELOS TITULOS
  
    
}
