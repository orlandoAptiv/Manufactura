/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package effortGUA;

//import CapturasEffort.*;
import Clases.Depto;
import Clases.Modulos;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;

/**
 *
 * @author FelipeM
 */
public class ExcelEffort2 {
    File ArchivoXLS;
    HSSFSheet hoja;
    HSSFWorkbook libro;
    CellStyle celdaTotales;
    FileOutputStream archivo = null;
    String rutaArchivo = System.getProperty("user.home")+"/desktop/Reporte EFFORT_GUA.xls";
    File archivoXLS;
    public ExcelEffort2(DefaultTableModel modelo, ArrayList<Depto> Deptos  ) {         /*Se crea el objeto de tipo File con la ruta del archivo*/
   //FileOutputStream archivo = null;
        try {
            /*La ruta donde se creará el archivo*/
            //String rutaArchivo = System.getProperty("user.home")+"/desktop/Reporte EFFORT.xls";
            /*Se crea el objeto de tipo File con la ruta del archivo*/
             archivoXLS = new File(rutaArchivo);
            libro = new HSSFWorkbook();
            archivo = new FileOutputStream(archivoXLS);
            hoja = libro.createSheet("EFFORT GUAMUCHIL");
            hoja.setColumnWidth(1, 5000);
            hoja.setColumnWidth(2, 5000);
            hoja.setColumnWidth(3, 5000);
            hoja.setColumnWidth(4, 5000);
            Row r=hoja.createRow(1);
            Cell celda=r.createCell(1);
            celda.setCellValue("NO. DEPTO");
            celda.setCellStyle(getTituloStyleTotales((int)HSSFColor.GREY_50_PERCENT.index));
            celda=r.createCell(2);
            celda.setCellValue("DEPTO");
            celda.setCellStyle(getTituloStyleTotales((int)HSSFColor.GREY_50_PERCENT.index));
            celda=r.createCell(3);
            celda.setCellValue("PLATAFORMA");
            celda.setCellStyle(getTituloStyleTotales((int)HSSFColor.GREY_50_PERCENT.index));
            celda=r.createCell(4);
            celda.setCellValue("EFFORT");
            celda.setCellStyle(getTituloStyleTotales((int)HSSFColor.GREY_50_PERCENT.index));
            
             for(int i=0; i<Deptos.size(); i++)
             {
                 for(int c=1; c<modelo.getColumnCount(); c++)
                 {
                       r=hoja.createRow(r.getRowNum()+1);
                       celda=r.createCell(1);
                       celda.setCellValue(Deptos.get(i).iddepto);
                       celda=r.createCell(2);
                       celda.setCellValue(Deptos.get(i).Depto);
                       celda=r.createCell(3);
                       celda.setCellValue(modelo.getColumnName(c));
                       celda=r.createCell(4);
                       celda.setCellValue(Double.parseDouble( modelo.getValueAt(2, c).toString()));
                 }
                       r=hoja.createRow(r.getRowNum()+1);
                       celda=r.createCell(1);
                       celda.setCellValue(Deptos.get(i).iddepto);
                       celda.setCellStyle(getTituloStyleTotales((int)HSSFColor.YELLOW.index));
                       celda=r.createCell(2);
                       celda.setCellValue(Deptos.get(i).Depto);
                       celda.setCellStyle(getTituloStyleTotales((int)HSSFColor.YELLOW.index));
                       celda=r.createCell(3);
                       celda.setCellValue("TOTAL");
                       celda.setCellStyle(getTituloStyleTotales((int)HSSFColor.YELLOW.index));
                       celda=r.createCell(4);
                       celda.setCellValue("100");
                       celda.setCellStyle(getTituloStyleTotales((int)HSSFColor.YELLOW.index));
             }
             
             for(int re=0; re<modelo.getRowCount()-1; re++)
             {
                 int color=1;//(int)(Math.random()*40 + 1);
                 switch(re){
                     case 0:
                         color=(int)HSSFColor.AQUA.index;
                         break;
                     case 1:
                         color=(int)HSSFColor.LIGHT_BLUE.index;
                         break;
                         case 2:
                         color=(int)HSSFColor.LIGHT_ORANGE.index;
                         break;
                        case 4:
                         color=(int)HSSFColor.TURQUOISE.index;
                         break;
                 }
//                 while((color==(int)HSSFColor.YELLOW.index) || ((color==(int)HSSFColor.BLACK.index)) || (color==(int)HSSFColor.BROWN.index) || (color==(int)HSSFColor.DARK_BLUE.index) || (color==(int)HSSFColor.MAROON.index) || (color==(int)HSSFColor.LIME.index))
//                 {
//                 color=(int)(Math.random()*40 + 1);
//                 }
                 for (int x = 1; x < modelo.getColumnCount(); x++) {
                     r = hoja.createRow(r.getRowNum() + 1);
                     celda = r.createCell(1);
                     celda.setCellValue("05900");
                     celda.setCellStyle(getTituloStyleDetD(color));
                     celda = r.createCell(2);
                     celda.setCellValue(modelo.getValueAt(re, 0).toString());
                     celda.setCellStyle(getTituloStyleDetD(color));
                     celda = r.createCell(3);
                     celda.setCellValue(modelo.getColumnName(x));
                     celda.setCellStyle(getTituloStyleDetD(color));
                     celda = r.createCell(4);
                     celda.setCellValue(Double.parseDouble(modelo.getValueAt(re, x).toString()));
                     celda.setCellStyle(getTituloStyleDetD(color));
                 }
                r=hoja.createRow(r.getRowNum()+1);
                       celda=r.createCell(1);
                       celda.setCellValue("05900");
                       celda.setCellStyle(getTituloStyleTotales((int)HSSFColor.YELLOW.index));
                       celda=r.createCell(2);
                       celda.setCellValue(modelo.getValueAt(re, 0).toString());
                       celda.setCellStyle(getTituloStyleTotales((int)HSSFColor.YELLOW.index));
                       celda=r.createCell(3);
                       celda.setCellValue("TOTAL");
                       celda.setCellStyle(getTituloStyleTotales((int)HSSFColor.YELLOW.index));
                       celda=r.createCell(4);
                       celda.setCellValue("100");
                       celda.setCellStyle(getTituloStyleTotales((int)HSSFColor.YELLOW.index));
             }
             /*Cerramos el flujo de datos*/
//             libro.write(archivo);
//             archivo.close();
//            /*Y abrimos el archivo con la clase Desktop*/
//             Desktop.getDesktop().open(archivoXLS);
        }catch(Exception e)
        {
            System.out.println(e.toString());
        }
    }
    
    public  ExcelEffort2(){
    }
    
    public void  gethojaScrapSq(DefaultTableModel modelo, ArrayList<Modulos> modulos, DefaultTableModel modeloPorc ) {         /*Se crea el objeto de tipo File con la ruta del archivo*/
    
        try {

            hoja = libro.createSheet("EFFORT SCRAP-TLO-SQFT");
            hoja.setColumnWidth(1, 5000);
            hoja.setColumnWidth(2, 5000);
            hoja.setColumnWidth(3, 5000);
            hoja.setColumnWidth(4, 5000);
            Row r=hoja.createRow(0);
            for(Modulos m:modulos)
            {
                int numeroRValor=r.getRowNum()+1;
                r=hoja.createRow(r.getRowNum()+1);
                Cell celda=r.createCell(1);
                celda.setCellValue(m.Nombre);
                celda.setCellStyle(getTituloStyleTotales((int)HSSFColor.GREY_50_PERCENT.index));
                celda=r.createCell(2);
                celda.setCellValue("NUMPLANTA");
                celda.setCellStyle(getTituloStyleTotales((int)HSSFColor.GREY_50_PERCENT.index));
                celda=r.createCell(3);
                celda.setCellValue("LINEA");
                celda.setCellStyle(getTituloStyleTotales((int)HSSFColor.GREY_50_PERCENT.index));
                celda=r.createCell(4);
                celda.setCellValue("EFFORT");
                celda.setCellStyle(getTituloStyleTotales((int)HSSFColor.GREY_50_PERCENT.index));
                celda=r.createCell(5);
                celda.setCellValue(m.Valor);
                celda.setCellStyle(getTituloStyleTotales((int)HSSFColor.GREY_50_PERCENT.index));
                for(int i=1; i<modelo.getColumnCount(); i++)
                {
                    r=hoja.createRow(r.getRowNum()+1);
                    Double Porcentaje=Double.parseDouble( modelo.getValueAt(2, i).toString());
                    Double Valor=m.Valor*Porcentaje/100;
                     celda=r.createCell(2);
                     celda.setCellValue("MOCHIS");
                     celda=r.createCell(3);
                     celda.setCellValue(modelo.getColumnName(i));
                     celda=r.createCell(4);
                     celda.setCellValue(Porcentaje);
                     celda=r.createCell(5);
                     celda.setCellFormula("+"+getColumnName(4)+(r.getRowNum()+1)+"*+"+getColumnName(5)+(numeroRValor+1)+"/100");
                }
                r=hoja.createRow(r.getRowNum()+4);
            }
            
            
                for(int i=0; i<modeloPorc.getRowCount(); i++)
                {
                    int numeroRValor=r.getRowNum()+1;
                r=hoja.createRow(r.getRowNum()+1);
                Cell celda=r.createCell(1);
                celda.setCellValue(modeloPorc.getValueAt(i, 0).toString());
                celda.setCellStyle(getTituloStyleTotales((int)HSSFColor.GREY_50_PERCENT.index));
                celda=r.createCell(2);
                celda.setCellValue("NUMPLANTA");
                celda.setCellStyle(getTituloStyleTotales((int)HSSFColor.GREY_50_PERCENT.index));
                celda=r.createCell(3);
                celda.setCellValue("LINEA");
                celda.setCellStyle(getTituloStyleTotales((int)HSSFColor.GREY_50_PERCENT.index));
                celda=r.createCell(4);
                celda.setCellValue("EFFORT");
                celda.setCellStyle(getTituloStyleTotales((int)HSSFColor.GREY_50_PERCENT.index));
                celda=r.createCell(5);
                celda.setCellValue(modeloPorc.getValueAt(i, 7).toString());
                celda.setCellStyle(getTituloStyleTotales((int)HSSFColor.GREY_50_PERCENT.index));
                for(int c=1; c<modeloPorc.getColumnCount()-1; c++)
                {
                    r=hoja.createRow(r.getRowNum()+1);
                    celda=r.createCell(2);
                    celda.setCellValue("MOCHIS");
                    celda=r.createCell(3);
                    celda.setCellValue(modeloPorc.getColumnName(c));
                    celda=r.createCell(4);
                    celda.setCellValue(modeloPorc.getValueAt(i, c).toString());
                    celda=r.createCell(5);
                     celda.setCellFormula("+"+getColumnName(4)+(r.getRowNum()+1)+"*+"+getColumnName(5)+(numeroRValor+1)+"/100");
                    //celda.setCellValue(modeloPorc.getValueAt(i, c).toString());
                }
                r=hoja.createRow(r.getRowNum()+2);
                }
             /*Cerramos el flujo de datos*/
//             libro.write(archivo);
//             archivo.close();
            /*Y abrimos el archivo con la clase Desktop*/
             //Desktop.getDesktop().open(archivoXLS);
        }catch(Exception e)
        {
            System.out.println(e.toString());
        }
    }

    public void  getPorcentajesHC(DefaultTableModel modeloPorcentajes, DefaultTableModel modeloHC) {         /*Se crea el objeto de tipo File con la ruta del archivo*/
    
        try {
            hoja = libro.createSheet("% POR PLATAFORMA");
            hoja.setColumnWidth(1, 5000);
            //hoja.setColumnWidth(2, 5000);
            hoja.setColumnWidth(3, 5000);
            hoja.setColumnWidth(4, 5000);
            Row r=hoja.createRow(2);
            Cell celda=null;
              for(int c=0; c<modeloPorcentajes.getColumnCount();c++)
                {
                    celda=r.createCell(c+1);
                    celda.setCellStyle(getTituloStyleTotales(HSSFColor.ORANGE.index));
                    celda.setCellValue(modeloPorcentajes.getColumnName(c));
                }
                    celda=r.createCell(modeloPorcentajes.getColumnCount()+1);
                    celda.setCellStyle(getTituloStyleTotales(HSSFColor.ORANGE.index));
                    celda.setCellValue("TOTAL");
            for(int i=0; i<modeloPorcentajes.getRowCount(); i++)
            {
                r=hoja.createRow(r.getRowNum()+1);
                for(int c=0; c<modeloPorcentajes.getColumnCount();c++)
                {
                     celda=r.createCell(c+1);
                    celda.setCellStyle(getTituloStyleDetLine(HSSFColor.WHITE.index));
                    celda.setCellValue(Double.parseDouble( modeloPorcentajes.getValueAt(i, c).toString()));
                    //celda.setCellFormula("ROUND("+getColumnName(c+1)+(r.getRowNum()+1)+",1)");
                }
               //celda.setCellStyle(getTituloStyleDet(HSSFColor.WHITE.index));
                celda=r.createCell(modeloPorcentajes.getColumnCount()+1);
                celda.setCellFormula("sum("+getColumnName(3)+(r.getRowNum()+1)+":"+getColumnName(modeloPorcentajes.getColumnCount())+(r.getRowNum()+1)+")");
                celda.setCellStyle(getTituloStyleDetLine(HSSFColor.WHITE.index));
            }
            //titulos de tabla porcentajes
            r=hoja.createRow(r.getRowNum()+5);
              for(int c=0; c<modeloHC.getColumnCount();c++)
                {
                    celda=r.createCell(c+2);
                    celda.setCellStyle(getTituloStyleTotales(HSSFColor.ORANGE.index));
                    celda.setCellValue(modeloHC.getColumnName(c));
                }
                    celda=r.createCell(modeloHC.getColumnCount()+2);
                    celda.setCellStyle(getTituloStyleTotales(HSSFColor.ORANGE.index));
                    celda.setCellValue("TOTAL");
              //DETALLE DE TABLA PORCENTAJES
              for(int i=0; i<modeloHC.getRowCount(); i++)
            {
                r=hoja.createRow(r.getRowNum()+1);
                for(int c=0; c<modeloHC.getColumnCount();c++)
                {
                    
                    celda=r.createCell(c+2);
                    celda.setCellStyle(getTituloStyleDet2Decimales(HSSFColor.WHITE.index));
                    if(c==0)
                        celda.setCellValue(modeloHC.getValueAt(i, c).toString());
                    else
                       celda.setCellValue(Double.parseDouble( modeloHC.getValueAt(i, c).toString()));  
                   
                }
                celda=r.createCell(modeloPorcentajes.getColumnCount()+1);
                celda.setCellFormula("sum("+getColumnName(3)+(r.getRowNum()+1)+":"+getColumnName(modeloHC.getColumnCount()+1)+(r.getRowNum()+1)+")");
                celda.setCellStyle(getTituloStyleDetLine(HSSFColor.WHITE.index));
               //celda.setCellStyle(getTituloStyleDet(HSSFColor.WHITE.index));
//                celda=r.createCell(modeloHC.getColumnCount()+1);
//                celda.setCellFormula("sum("+getColumnName(2)+(r.getRowNum()+3)+":"+getColumnName(modeloHC.getColumnCount())+(r.getRowNum()+1)+")");
//                celda.setCellStyle(getTituloStyleDet2Decimales(HSSFColor.WHITE.index));
            }
//                int numeroRValor=r.getRowNum()+1;
//                r=hoja.createRow(r.getRowNum()+1);
//                Cell celda=r.createCell(1);
//                celda.setCellValue(m.Nombre);
//                celda.setCellStyle(getTituloStyleTotales((int)HSSFColor.GREY_50_PERCENT.index));
//                celda=r.createCell(2);
//                celda.setCellValue("NUMPLANTA");
//                celda.setCellStyle(getTituloStyleTotales((int)HSSFColor.GREY_50_PERCENT.index));
//                celda=r.createCell(3);
//                celda.setCellValue("LINEA");
//                celda.setCellStyle(getTituloStyleTotales((int)HSSFColor.GREY_50_PERCENT.index));
//                celda=r.createCell(4);
//                celda.setCellValue("EFFORT");
//                celda.setCellStyle(getTituloStyleTotales((int)HSSFColor.GREY_50_PERCENT.index));
//                celda=r.createCell(5);
//                celda.setCellValue(m.Valor);
//                celda.setCellStyle(getTituloStyleTotales((int)HSSFColor.GREY_50_PERCENT.index));
//                for(int i=1; i<modelo.getColumnCount(); i++)
//                {
//                    r=hoja.createRow(r.getRowNum()+1);
//                    Double Porcentaje=Double.parseDouble( modelo.getValueAt(3, i).toString());
//                    Double Valor=m.Valor*Porcentaje/100;
//                     celda=r.createCell(2);
//                     celda.setCellValue("MOCHIS");
//                     celda=r.createCell(3);
//                     celda.setCellValue(modelo.getColumnName(i));
//                     celda=r.createCell(4);
//                     celda.setCellValue(Porcentaje);
//                     celda=r.createCell(5);
//                     celda.setCellFormula("+"+getColumnName(4)+(r.getRowNum()+1)+"*+"+getColumnName(5)+(numeroRValor+1)+"/100");
//                }
//                r=hoja.createRow(r.getRowNum()+2);
//            

             /*Cerramos el flujo de datos*/
             libro.write(archivo);
             archivo.close();
            /*Y abrimos el archivo con la clase Desktop*/
             Desktop.getDesktop().open(archivoXLS);
        }catch(Exception e)
        {
            System.out.println(e.toString());
        }
    }
   
    public CellStyle getTituloStyleTotales(int color){
            //CREAMOS CABECERAS DEL REPORTE 
            celdaTotales=libro.createCellStyle();
            celdaTotales.setAlignment((short)2);
            Font cellSF=libro.createFont();
            cellSF.setFontName(HSSFFont.FONT_ARIAL);
            cellSF.setFontHeightInPoints((short)10);
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
   
    public CellStyle getTituloStyleDet(int color){
            //CREAMOS CABECERAS DEL REPORTE 
           CellStyle celda=libro.createCellStyle();
            //celda.setAlignment((short)2);
            Font cellSF=libro.createFont();
            cellSF.setFontName(HSSFFont.FONT_ARIAL);
            cellSF.setFontHeightInPoints((short)10);
            cellSF.setColor(HSSFColor.BLACK.index);
            //cellSF.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            celda.setFillForegroundColor((short)color);
            celda.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            celda.setDataFormat(libro.createDataFormat().getFormat("0"));
//            celda.setBorderBottom(HSSFCellStyle.BORDER_DOUBLE);
//            celda.setBorderLeft(HSSFCellStyle.BORDER_DOUBLE);
//            celda.setBorderRight(HSSFCellStyle.BORDER_DOUBLE);
//            celda.setBorderTop(HSSFCellStyle.BORDER_DOUBLE);
            celda.setFont(cellSF);
            return celda;
      } 
    
     public CellStyle getTituloStyleDetD(int color){
            //CREAMOS CABECERAS DEL REPORTE 
           CellStyle celda=libro.createCellStyle();
            //celda.setAlignment((short)2);
            Font cellSF=libro.createFont();
            cellSF.setFontName(HSSFFont.FONT_ARIAL);
            cellSF.setFontHeightInPoints((short)10);
            cellSF.setColor(HSSFColor.BLACK.index);
            //cellSF.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            celda.setFillForegroundColor((short)color);
            celda.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
          // celda.setDataFormat(libro.createDataFormat().getFormat("0"));
//            celda.setBorderBottom(HSSFCellStyle.BORDER_DOUBLE);
//            celda.setBorderLeft(HSSFCellStyle.BORDER_DOUBLE);
//            celda.setBorderRight(HSSFCellStyle.BORDER_DOUBLE);
//            celda.setBorderTop(HSSFCellStyle.BORDER_DOUBLE);
            celda.setFont(cellSF);
            return celda;
      } 
    
    public CellStyle getTituloStyleDet2Decimales(int color){
            //CREAMOS CABECERAS DEL REPORTE 
           CellStyle celda=libro.createCellStyle();
            //celda.setAlignment((short)2);
            Font cellSF=libro.createFont();
            cellSF.setFontName(HSSFFont.FONT_ARIAL);
            cellSF.setFontHeightInPoints((short)10);
            cellSF.setColor(HSSFColor.BLACK.index);
            //cellSF.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            celda.setFillForegroundColor((short)color);
            celda.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            celda.setDataFormat(libro.createDataFormat().getFormat("0.000"));
//            celda.setBorderBottom(HSSFCellStyle.BORDER_DOUBLE);
//            celda.setBorderLeft(HSSFCellStyle.BORDER_DOUBLE);
//            celda.setBorderRight(HSSFCellStyle.BORDER_DOUBLE);
//            celda.setBorderTop(HSSFCellStyle.BORDER_DOUBLE);
            celda.setFont(cellSF);
            return celda;
      } 
    
    public CellStyle getTituloStyleDetLine(int color){
            //CREAMOS CABECERAS DEL REPORTE 
           CellStyle celda=libro.createCellStyle();
            //celda.setAlignment((short)2);
            Font cellSF=libro.createFont();
            cellSF.setFontName(HSSFFont.FONT_ARIAL);
            cellSF.setFontHeightInPoints((short)10);
            cellSF.setColor(HSSFColor.BLACK.index);
            //cellSF.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            celda.setFillForegroundColor((short)color);
            celda.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            celda.setDataFormat(libro.createDataFormat().getFormat("0"));
            celda.setBorderBottom(HSSFCellStyle.BORDER_THIN);
            celda.setBorderLeft(HSSFCellStyle.BORDER_THIN);
            celda.setBorderRight(HSSFCellStyle.BORDER_THIN);
            celda.setBorderTop(HSSFCellStyle.BORDER_THIN);
            celda.setFont(cellSF);
            return celda;
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
}
