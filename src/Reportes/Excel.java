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

public class Excel {

    File ArchivoXLS;
    HSSFSheet hoja;
    HSSFWorkbook libro;
    int Renglon = 0;
    int columna = 0;
    CellStyle celdaStiloCabecera;
    CellStyle celdaDetalle;
    CellStyle celdaTotales;

    public Excel(DefaultTableModel modelo, String Nombre) throws IOException {
        FileOutputStream archivo = null;
        try {
            /*La ruta donde se creará el archivo*/
            String rutaArchivo = System.getProperty("user.home") + "/desktop/Reporte HC " + Nombre + ".xls";
            /*Se crea el objeto de tipo File con la ruta del archivo*/
            File archivoXLS = new File(rutaArchivo);
            libro = new HSSFWorkbook();
            archivo = new FileOutputStream(archivoXLS);
            hoja = libro.createSheet("Reporte");
            RegresaTituloStyle();
            RegresaStyleDetalle();
            if (modelo.getRowCount() > 0) {
                Row renglonEncabezado = hoja.createRow(1);
                if (!Nombre.equals("CODIGO")) {
                    Cell c = renglonEncabezado.createCell(1);
                    hoja.addMergedRegion(new CellRangeAddress(1, 1, 1, 4));
                    c.setCellValue("HC. DIRECTA");
                    c.setCellStyle(celdaStiloCabecera);

                    hoja.addMergedRegion(new CellRangeAddress(1, 1, 5, 6));
                    c = renglonEncabezado.createCell(5);
                    c.setCellValue("HC. LPS");
                    c.setCellStyle(celdaStiloCabecera);
                    hoja.addMergedRegion(new CellRangeAddress(1, 1, 7, 10));
                    c = renglonEncabezado.createCell(7);
                    c.setCellValue("HC. OTROS");
                    c.setCellStyle(celdaStiloCabecera);

                    c = renglonEncabezado.createCell(13);
                    c.setCellValue("HC. IND.");
                    c.setCellStyle(celdaStiloCabecera);
                } else {
//                     Cell c= renglonEncabezado.createCell(3);
//                     hoja.addMergedRegion(new CellRangeAddress(1, 1, 3, 12));
//                     c.setCellValue("HC. DIRECTA");
//                     c.setCellStyle(celdaStiloCabecera);
//                     
//                     c.setCellStyle(celdaStiloCabecera);
//                      c=renglonEncabezado.createCell(15);
//                     c.setCellValue("HC. IND.");
//                     c.setCellStyle(celdaStiloCabecera);
                    Cell c = renglonEncabezado.createCell(7);
                    hoja.addMergedRegion(new CellRangeAddress(1, 1, 7, 11));
                    c.setCellValue("HC. DIRECTA");
                    c.setCellStyle(celdaStiloCabecera);

                    hoja.addMergedRegion(new CellRangeAddress(1, 1, 12, 13));
                    c = renglonEncabezado.createCell(12);
                    c.setCellValue("HC. LPS");
                    c.setCellStyle(celdaStiloCabecera);
                    hoja.addMergedRegion(new CellRangeAddress(1, 1, 14, 16));
                    c = renglonEncabezado.createCell(14);
                    c.setCellValue("HC. OTROS");
                    c.setCellStyle(celdaStiloCabecera);

                    c = renglonEncabezado.createCell(19);
                    c.setCellValue("HC. IND.");
                    c.setCellStyle(celdaStiloCabecera);
                }

                Row renglon = hoja.createRow(2);
                for (int i = 0; i < modelo.getColumnCount() - 1; i++) {
                    hoja.setColumnWidth(i, modelo.getColumnName(i).length() * 400);
                    Cell celda = renglon.createCell(i);
                    celda.setCellValue(modelo.getColumnName(i).toString());
                    celda.setCellStyle(celdaStiloCabecera);
                }
                hoja.setColumnWidth(modelo.getColumnCount() + 1, modelo.getColumnName(modelo.getColumnCount() - 1).length() * 500);
                Cell celda = renglon.createCell(modelo.getColumnCount() + 1);
                celda.setCellValue(modelo.getColumnName(modelo.getColumnCount() - 1).toString());
                celda.setCellStyle(celdaStiloCabecera);
            }
            for (int x = 0; x < modelo.getRowCount(); x++) {
                Row renglon = hoja.createRow(x + 3);
                Cell celda = null;
                for (int i = 0; i < modelo.getColumnCount(); i++) {
                    if (i != modelo.getColumnCount() - 1) {
                        celda = renglon.createCell(i);
                        try {
                            celda.setCellValue(Double.parseDouble(modelo.getValueAt(x, i).toString()));
                        } catch (Exception e) {
                            celda.setCellValue(modelo.getValueAt(x, i).toString());
                        }
                    } else {
                        celda = renglon.createCell(i + 2);
                        celda.setCellValue(Double.parseDouble(modelo.getValueAt(x, i).toString()));
                    }
                    celda.setCellStyle(celdaDetalle);
                }
            }
            Row renglon = hoja.createRow(modelo.getRowCount() + 3);
            Cell celda = renglon.createCell(0);
            celda.setCellValue("TOTAL");
            celda.setCellStyle(celdaDetalle);
            for (int i = 1; i < modelo.getColumnCount() - 1; i++) {
                celda = renglon.createCell(i);
                String formula = "SUM(" + getColumnName(i) + "4:" + getColumnName(i) + (modelo.getRowCount() + 3) + ")";
                celda.setCellFormula(formula);
                celda.setCellStyle(celdaDetalle);
            }
            renglon = hoja.getRow(2);
            celda = renglon.createCell(modelo.getColumnCount() - 1);
            celda.setCellValue("TOTAL HC");
            celda.setCellStyle(celdaStiloCabecera);
            for (int i = 1; i < modelo.getRowCount() + 1; i++) {
                renglon = hoja.getRow(i + 2);
                celda = renglon.createCell(modelo.getColumnCount() - 1);
                String formula = "SUM(" + getColumnName(7) + (i + 3) + ":" + getColumnName(modelo.getColumnCount() - 2) + (i + 3) + ")";
                celda.setCellFormula(formula);
                celda.setCellStyle(celdaDetalle);
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

    public Excel(ArrayList<DefaultTableModel> modelo, ArrayList<String> Nombre) throws IOException {
        FileOutputStream archivo = null;
        try {
            /*La ruta donde se creará el archivo*/
            String rutaArchivo = System.getProperty("user.home") + "/desktop/Reporte HC " + Nombre + ".xls";
            /*Se crea el objeto de tipo File con la ruta del archivo*/
            File archivoXLS = new File(rutaArchivo);
            libro = new HSSFWorkbook();
            archivo = new FileOutputStream(archivoXLS);
            for (int ho = 0; ho < modelo.size(); ho++) {
                hoja = libro.createSheet("Reporte" + ho);
                RegresaTituloStyle();
                RegresaStyleDetalle();
                if (modelo.get(ho).getRowCount() > 0) {
                    Row renglonEncabezado = hoja.createRow(1);
                    if (!Nombre.get(ho).equals("CODIGO")) {
                        Cell c = renglonEncabezado.createCell(1);
                        c.setCellValue("HC. DIRECTA");
                        c.setCellStyle(celdaStiloCabecera);
                        hoja.addMergedRegion(new CellRangeAddress(1, 1, 1, 10));
                        c = renglonEncabezado.createCell(11);
                        c.setCellValue("HC. IND.");
                        c.setCellStyle(celdaStiloCabecera);
                    } else {
                        Cell c = renglonEncabezado.createCell(3);
                        c.setCellValue("HC. DIRECTA");
                        c.setCellStyle(celdaStiloCabecera);
                        hoja.addMergedRegion(new CellRangeAddress(1, 1, 3, 12));
                        c = renglonEncabezado.createCell(13);
                        c.setCellValue("HC. IND.");
                        c.setCellStyle(celdaStiloCabecera);
                    }

                    Row renglon = hoja.createRow(2);
                    for (int i = 0; i < modelo.get(ho).getColumnCount(); i++) {
                        hoja.setColumnWidth(i, modelo.get(ho).getColumnName(i).length() * 500);
                        Cell celda = renglon.createCell(i);
                        celda.setCellValue(modelo.get(ho).getColumnName(i).toString());
                        celda.setCellStyle(celdaStiloCabecera);
                    }
                }
                for (int x = 0; x < modelo.get(ho).getRowCount(); x++) {
                    Row renglon = hoja.createRow(x + 3);
                    for (int i = 0; i < modelo.get(ho).getColumnCount(); i++) {
                        Cell celda = renglon.createCell(i);
                        try {
                            celda.setCellValue(Double.parseDouble(modelo.get(ho).getValueAt(x, i).toString()));
                        } catch (Exception e) {
                            celda.setCellValue(modelo.get(ho).getValueAt(x, i).toString());
                        }
                        celda.setCellStyle(celdaDetalle);
                    }
                }
                Row renglon = hoja.createRow(modelo.get(ho).getRowCount() + 3);
                Cell celda = renglon.createCell(0);
                celda.setCellValue("TOTAL");
                celda.setCellStyle(celdaDetalle);
                for (int i = 1; i < modelo.get(ho).getColumnCount(); i++) {
                    celda = renglon.createCell(i);
                    String formula = "SUM(" + getColumnName(i) + "4:" + getColumnName(i) + (modelo.get(ho).getRowCount() + 3) + ")";
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
                Logger.getLogger(Excel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    //METODO QUE REGRESA LA FECHA EN FORMATO MYSQL 

    String GetFecha(java.util.Date fecha) {
        return new SimpleDateFormat("yyyy-MM-dd").format(fecha).toString();
    }

    //METODO QUE REGRESA LA LETRA DE LA COLUMNA
    private String getColumnName(int columnNumber) {
        String columnName = "";
        int dividend = columnNumber + 1;
        int modulus;

        while (dividend > 0) {
            modulus = (dividend - 1) % 26;
            columnName = (char) (65 + modulus) + columnName;
            dividend = (int) ((dividend - modulus) / 26);
        }

        return columnName;
    }

    //CREAMOS CABECERAS DE LAS FECHAS
    public void RegresaTituloStyle() {
        //CREAMOS CABECERAS DEL REPORTE 
        celdaStiloCabecera = libro.createCellStyle();
        celdaStiloCabecera.setAlignment((short) 2);
        Font cellF = libro.createFont();
        cellF.setFontName(HSSFFont.FONT_ARIAL);
        cellF.setFontHeightInPoints((short) 10);
        cellF.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        celdaStiloCabecera.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
        celdaStiloCabecera.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
        celdaStiloCabecera.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
        celdaStiloCabecera.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
        celdaStiloCabecera.setFont(cellF);
    }

    public void RegresaStyleDetalle() {
        celdaDetalle = libro.createCellStyle();
        Font cellFCF = libro.createFont();
        cellFCF.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        cellFCF.setFontHeightInPoints((short) 8);
        cellFCF.setFontName(HSSFFont.FONT_ARIAL);
        celdaDetalle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        celdaDetalle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        celdaDetalle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        celdaDetalle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        celdaDetalle.setFillBackgroundColor(HSSFColor.GREY_50_PERCENT.index);
    }

    //CREAMOS CELLSTYLE CON LA CONFIGURACION DELOS TITULOS
}
