/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Reportes;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
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
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;

public class ExcelImpactos {

    File ArchivoXLS;
    HSSFSheet hoja;
    HSSFWorkbook libro;
    int Renglon = 0;
    int columna = 0;
    CellStyle celdaStiloCabecera;
    CellStyle celdaDetalle;
    CellStyle celdaTotales;

    public ExcelImpactos() {

    }

    public void ExcelImpactos(String Ruta, String Semana, DefaultTableModel modeloDetalle) throws IOException {
        FileInputStream inp = null;
        try {
            inp = new FileInputStream(Ruta);
            HSSFWorkbook libros = new HSSFWorkbook(inp);
            HSSFSheet hoja = libros.getSheetAt(0);
//             for(Row r: hoja){
            //ASIGNA FECHA DE LA SEMANA
            Row r = hoja.getRow(8);
            //AGREGO SEMANA ACTUAL AL EXCLE                
            Cell c = r.getCell(2);
            // c.setCellType(Cell.CELL_TYPE_STRING);
            c.setCellValue((Semana));
            r = hoja.getRow(3);
//                //AGREGO LA CADENA 
            c = r.getCell(6);
            c.setCellValue(modeloDetalle.getValueAt(0, 1).toString());
//                //AGREGO EL TURNO
            c = r.getCell(11);
            c.setCellValue(modeloDetalle.getValueAt(0, 2).toString());
            int contSemana = 0;
            //AGREGAR OBJETIVO DE CADENA
            c = r.getCell(3);
            c.setCellValue(modeloDetalle.getValueAt(0, 3).toString());
            //AGREGO SEMANA, HORAS EMBARCADAS, HORAS PAGADAS
            for (int i = 0; i < modeloDetalle.getRowCount(); i++) {
                r = hoja.getRow(8);
                c = r.getCell(3 + contSemana);
                //c.setCellType(Cell.CELL_TYPE_NUMERIC);
                c.setCellValue((Date) modeloDetalle.getValueAt(i, 0));
                r = hoja.getRow(9);
                c = r.getCell(3 + contSemana);
                c.setCellType(Cell.CELL_TYPE_NUMERIC);
                c.setCellValue(Double.parseDouble(modeloDetalle.getValueAt(i, 4).toString()));
                r = hoja.getRow(10);
                c = r.getCell(3 + contSemana);
                c.setCellType(Cell.CELL_TYPE_NUMERIC);
                c.setCellValue(Double.parseDouble(modeloDetalle.getValueAt(i, 5).toString()));
                contSemana += 2;
            }

            //AGREGO COMENTARIOS Y FECHA DE CADA COMENTARIO
            for (int i = 0; i < modeloDetalle.getRowCount(); i++) {
                r = hoja.getRow(57 + i);
                c = r.getCell(2);
                c.setCellValue(modeloDetalle.getValueAt(i, 8).toString());
                c = r.getCell(1);
                c.setCellValue((Date) modeloDetalle.getValueAt(i, 0));
            }

            //SE UTLIZA PARA ACTUALIZAR LAS FORMULAS DEL ARCHIVO DE EXCEL
            FormulaEvaluator evaluator = libros.getCreationHelper().createFormulaEvaluator();
            for (Row row : hoja) {
                for (Cell cs : row) {
                    if (cs.getCellType() == Cell.CELL_TYPE_FORMULA) {
                        evaluator.evaluateFormulaCell(cs);
                    }
                }
            }

            FileOutputStream fileOut = new FileOutputStream(Ruta);
            libros.write(fileOut);
            fileOut.close();
            Desktop.getDesktop().open(new File(Ruta));
            fileOut.close();
            //}
            /*Y abrimos el archivo con la clase Desktop*/
            //Desktop.getDesktop().open(archivoXLS);
        } catch (Exception ex) {
            System.out.println(ex.toString() + "xxxx");
        }

    }
    //CONSTRUCTOR QUE EXPORTA A EXCEL TODAS LAS CADENA Y TURNO

    public ExcelImpactos(ArrayList<DefaultTableModel> modelo, DefaultTableModel modeloTotal, ArrayList<String> Nombre) throws IOException {
        FileOutputStream archivo = null;
        int ho = 0;
        try {
            /*La ruta donde se creará el archivo*/
            String rutaArchivo = System.getProperty("user.home") + "/desktop/Reporte HC " + Nombre + ".xls";
            /*Se crea el objeto de tipo File con la ruta del archivo*/
            File archivoXLS = new File(rutaArchivo);
            libro = new HSSFWorkbook();
            archivo = new FileOutputStream(archivoXLS);
            for (ho = 0; ho < modelo.size(); ho++) {
                hoja = libro.createSheet(Nombre.get(ho));
                getTituloStyle();
                getStyleDetalle();
                if (modelo.get(ho).getRowCount() > 0) {
                    Row renglon = hoja.createRow(2);
                    for (int i = 0; i < modelo.get(ho).getColumnCount(); i++) {
                        hoja.setColumnWidth(i, modelo.get(ho).getColumnName(i).length() * 350);
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
                        if (i != modelo.get(ho).getColumnCount() - 1) {
                            celda.setCellStyle(celdaDetalle);
                        } else {
                            celda.setCellStyle(getStyleDetallePorc());
                        }
                    }
                }
                Row renglon = hoja.createRow(modelo.get(ho).getRowCount() + 3);
                Cell celda = renglon.createCell(0);
                celda.setCellValue("TOTAL");
                celda.setCellStyle(getTituloStyleTotales(HSSFColor.GREEN.index));
                for (int i = 3; i < modelo.get(ho).getColumnCount() - 1; i++) {
                    // if(modelo.get(ho).getColumnName(i).toString().equals("HC")
                    celda = renglon.createCell(i);
                    String formula = "SUM(" + getColumnName(i) + "4:" + getColumnName(i) + (modelo.get(ho).getRowCount() + 3) + ")";
                    celda.setCellFormula(formula);
                    celda.setCellStyle(getTituloStyleTotales(HSSFColor.GREEN.index));
                    //celda.setCellStyle(celdaDetalle);
                }
                celda = renglon.createCell(modelo.get(ho).getColumnCount() - 1);
                String formula = "+" + getColumnName(modelo.get(ho).getColumnCount() - 3) + String.valueOf(modelo.get(ho).getRowCount() + 4) + "/+" + getColumnName(modelo.get(ho).getColumnCount() - 2) + String.valueOf(modelo.get(ho).getRowCount() + 4);
                celda.setCellFormula(formula);
                celda.setCellStyle(getStyleDetallePorc(HSSFColor.GREEN.index));
            }

            //HOJA DE TOTALES AL FINAL
            hoja = libro.createSheet("Eficiencia Total");

            if (modeloTotal.getRowCount() > 0) {
                Row renglon = hoja.createRow(1);
                hoja.addMergedRegion(new CellRangeAddress(1, 1, 1, 6));
                Cell celda = renglon.createCell(1);
                celda.setCellValue("REPORTE DE EFICIENCIA");
                celda.setCellStyle(celdaStiloCabecera);
                renglon = hoja.createRow(0);
                celda = renglon.createCell(modeloTotal.getColumnCount());
                celda.setCellValue("Fecha: ");
                celda = renglon.createCell(modeloTotal.getColumnCount() + 1);
                celda.setCellValue(GetFecha(new Date()).toString());

                renglon = hoja.createRow(2);
                for (int i = 2; i < modeloTotal.getColumnCount() + 2; i++) {
                    hoja.setColumnWidth(i, modeloTotal.getColumnName(i - 2).length() * 500);
                    // hoja.autoSizeColumn(i);
                    celda = renglon.createCell(i);
                    celda.setCellValue(modeloTotal.getColumnName(i - 2).toString());
                    celda.setCellStyle(celdaStiloCabecera);
                }
            }
            for (int x = 0; x < modeloTotal.getRowCount(); x++) {
                Row renglon = hoja.createRow((x + 3));
                Cell celda = null;
                for (int i = 2; i < modeloTotal.getColumnCount() + 2; i++) {
                    celda = renglon.createCell(i);
                    try {
                        celda.setCellValue(Double.parseDouble(modeloTotal.getValueAt(x, i - 2).toString()));
                    } catch (Exception e) {
                        celda.setCellValue(modeloTotal.getValueAt(x, i - 2).toString());
                    }

                    celda.setCellStyle(celdaDetalle);
                }
            }

            //AGREGAMOS AL FINAL DE LA LINEA UN SUM CON LOS TOTALES HRS EMB. HRS. PAGADAS
            Row renglon = hoja.createRow((modeloTotal.getRowCount() + 3));
            Cell celda = null;
            celda = renglon.createCell(2);
            celda.setCellValue("TOTAL PLANTA");
            celda.setCellStyle(celdaDetalle);

            for (int i = 3; i < modeloTotal.getColumnCount(); i++) {
                celda = renglon.createCell(i + 2);
                celda.setCellStyle(celdaDetalle);

                celda.setCellFormula("sum(" + getColumnName(i + 2) + 4 + ":" + getColumnName(i + 2) + 14 + ")");
            }
//                      
            for (int i = 0; i < modeloTotal.getRowCount() + 1; i++) {
                renglon = hoja.getRow(i + 3);
                celda = renglon.createCell(7);
                celda.setCellStyle(getStyleDetallePorc());
                celda.setCellFormula("+sum(F" + (i + 4) + "/G" + (i + 4) + ")");
            }
// 
            //

            libro.write(archivo);
            /*Cerramos el flujo de datos*/
            archivo.close();
            /*Y abrimos el archivo con la clase Desktop*/
            Desktop.getDesktop().open(archivoXLS);
        } catch (Exception ex) {
            System.out.println(ex.toString() + String.valueOf(ho));
        } finally {
            try {
                archivo.close();
            } catch (IOException ex) {
                Logger.getLogger(ExcelImpactos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    //METODO QUE REGRESA LA FECHA EN FORMATO MYSQL 

    public void GetExceltotalPlanta(DefaultTableModel modelo, String Nombre) throws IOException {
        FileOutputStream archivo = null;
        try {
            /*La ruta donde se creará el archivo*/
            String rutaArchivo = System.getProperty("user.home") + "/desktop/Reporte Eficiencia " + Nombre + ".xls";
            /*Se crea el objeto de tipo File con la ruta del archivo*/
            File archivoXLS = new File(rutaArchivo);
            libro = new HSSFWorkbook();
            archivo = new FileOutputStream(archivoXLS);
            hoja = libro.createSheet("Reporte");
            getTituloStyle();
            getStyleDetalle();
            if (modelo.getRowCount() > 0) {
                Row renglon = hoja.createRow(1);
                hoja.addMergedRegion(new CellRangeAddress(1, 1, 1, 6));
                Cell celda = renglon.createCell(1);
                celda.setCellValue("REPORTE DE EFICIENCIA");
                celda.setCellStyle(celdaStiloCabecera);
                renglon = hoja.createRow(0);
                celda = renglon.createCell(modelo.getColumnCount());
                celda.setCellValue("Fecha: ");
                celda = renglon.createCell(modelo.getColumnCount() + 1);
                celda.setCellValue(GetFecha(new Date()).toString());

                renglon = hoja.createRow(2);
                for (int i = 2; i < modelo.getColumnCount() + 2; i++) {
                    hoja.setColumnWidth(i, modelo.getColumnName(i - 2).length() * 500);
                    // hoja.autoSizeColumn(i);
                    celda = renglon.createCell(i);
                    celda.setCellValue(modelo.getColumnName(i - 2).toString());
                    celda.setCellStyle(celdaStiloCabecera);
                }
            }
            for (int x = 0; x < modelo.getRowCount(); x++) {
                Row renglon = hoja.createRow((x + 3));

                Cell celda = null;
                for (int i = 2; i < modelo.getColumnCount() + 2; i++) {
                    celda = renglon.createCell(i);
                    try {
                        celda.setCellValue(Double.parseDouble(modelo.getValueAt(x, i - 2).toString()));
                    } catch (Exception e) {
                        celda.setCellValue(modelo.getValueAt(x, i - 2).toString());
                    }

                    celda.setCellStyle(celdaDetalle);
                }
            }

//                AGREGAR RENGLON EN LA LIBRETA
            Row renglon = hoja.createRow((modelo.getRowCount() + 3));
            Cell celda = null;
            celda = renglon.createCell(2);
            celda.setCellValue("TOTAL PLANTA");
            celda.setCellStyle(celdaDetalle);

            for (int i = 3; i < modelo.getColumnCount(); i++) {
                celda = renglon.createCell(i);
                celda.setCellStyle(celdaDetalle);

                celda.setCellFormula("sum(" + getColumnName(i) + 4 + ":" + getColumnName(i) + 8 + ")");
            }

            for (int i = 0; i < modelo.getRowCount() + 1; i++) {
                renglon = hoja.getRow(i + 3);
                celda = renglon.createCell(9);
                celda.setCellStyle(getStyleDetallePorc());
                celda.setCellFormula("+F" + (i + 4) + "/H" + (i + 4));

                celda = renglon.createCell(10);
                celda.setCellStyle(getStyleDetallePorc());
                celda.setCellFormula("+G" + (i + 4) + "/I" + (i + 4));

                celda = renglon.createCell(11);
                celda.setCellStyle(getStyleDetallePorc());
                celda.setCellFormula("+sum(F" + (i + 4) + ":G" + (i + 4) + ")/sum(H" + (i + 4) + ":I" + (i + 4) + ")");
            }
// 
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

    public void GetExceltotalPlantaJorge(DefaultTableModel modelo, String Nombre) throws IOException {
        FileOutputStream archivo = null;
        try {
            /*La ruta donde se creará el archivo*/
            String rutaArchivo = System.getProperty("user.home") + "/desktop/Reporte Eficiencia " + Nombre + ".xls";
            /*Se crea el objeto de tipo File con la ruta del archivo*/
            File archivoXLS = new File(rutaArchivo);
            libro = new HSSFWorkbook();
            archivo = new FileOutputStream(archivoXLS);
            hoja = libro.createSheet("Reporte");
            getTituloStyle();
            getStyleDetalle();
            if (modelo.getRowCount() > 0) {
                Row renglon = hoja.createRow(1);
                hoja.addMergedRegion(new CellRangeAddress(1, 1, 1, 6));
                Cell celda = renglon.createCell(1);
                celda.setCellValue("REPORTE DE EFICIENCIA");
                celda.setCellStyle(celdaStiloCabecera);
                renglon = hoja.createRow(0);
                celda = renglon.createCell(modelo.getColumnCount());
                celda.setCellValue("Fecha: ");
                celda = renglon.createCell(modelo.getColumnCount() + 1);
                celda.setCellValue(GetFecha(new Date()).toString());

                renglon = hoja.createRow(2);
                for (int i = 2; i < modelo.getColumnCount() + 2; i++) {
                    hoja.setColumnWidth(i, modelo.getColumnName(i - 2).length() * 500);
                    // hoja.autoSizeColumn(i);
                    celda = renglon.createCell(i);
                    celda.setCellValue(modelo.getColumnName(i - 2).toString());
                    celda.setCellStyle(celdaStiloCabecera);
                }
            }
            for (int x = 0; x < modelo.getRowCount(); x++) {
                Row renglon = hoja.createRow((x + 3));
                Cell celda = null;
                for (int i = 2; i < modelo.getColumnCount() + 2; i++) {
                    celda = renglon.createCell(i);
                    try {
                        celda.setCellValue(Double.parseDouble(modelo.getValueAt(x, i - 2).toString()));
                    } catch (Exception e) {
                        celda.setCellValue(modelo.getValueAt(x, i - 2).toString());
                    }

                    celda.setCellStyle(celdaDetalle);
                }
            }

            //AGREGAMOS AL FINAL DE LA LINEA UN SUM CON LOS TOTALES HRS EMB. HRS. PAGADAS
            Row renglon = hoja.createRow((modelo.getRowCount() + 3));
            Cell celda = null;
            celda = renglon.createCell(2);
            celda.setCellValue("TOTAL PLANTA");
            celda.setCellStyle(celdaDetalle);

            for (int i = 3; i < modelo.getColumnCount(); i++) {
                celda = renglon.createCell(i + 2);
                celda.setCellStyle(celdaDetalle);

                celda.setCellFormula("sum(" + getColumnName(i + 2) + 4 + ":" + getColumnName(i + 2) + 14 + ")");
            }
//                      
            for (int i = 0; i < modelo.getRowCount() + 1; i++) {
                renglon = hoja.getRow(i + 3);
                // celda=renglon.createCell(9);
//                      celda.setCellStyle(getStyleDetallePorc());
//                      celda.setCellFormula("+F"+(i+4)+"/H"+(i+4));
//                      
//                      celda=renglon.createCell(10);
//                      celda.setCellStyle(getStyleDetallePorc());
//                      celda.setCellFormula("+G"+(i+4)+"/I"+(i+4));

                celda = renglon.createCell(7);
                celda.setCellStyle(getStyleDetallePorc());
                celda.setCellFormula("+sum(F" + (i + 4) + "/G" + (i + 4) + ")");
            }
// 
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

    String GetFecha(java.util.Date fecha) {
        return new SimpleDateFormat("dd-MM-yyyy").format(fecha).toString();
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

    public void getTituloStyle() {
        //CREAMOS CABECERAS DEL REPORTE 
        celdaStiloCabecera = libro.createCellStyle();
        celdaStiloCabecera.setAlignment((short) 2);
        Font cellF = libro.createFont();
        cellF.setFontName(HSSFFont.FONT_ARIAL);
        cellF.setFontHeightInPoints((short) 9);
        cellF.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        celdaStiloCabecera.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
        celdaStiloCabecera.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
        celdaStiloCabecera.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
        celdaStiloCabecera.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
        celdaStiloCabecera.setFont(cellF);
    }

    public void getStyleDetalle() {
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

    public CellStyle getStyleDetallePorc() {
        CellStyle celdaDetalle = libro.createCellStyle();
        Font cellFCF = libro.createFont();
        cellFCF.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        cellFCF.setFontHeightInPoints((short) 8);
        cellFCF.setFontName(HSSFFont.FONT_ARIAL);
        celdaDetalle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        celdaDetalle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        celdaDetalle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        celdaDetalle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        celdaDetalle.setDataFormat(libro.createDataFormat().getFormat("0.00%"));
        celdaDetalle.setFillBackgroundColor(HSSFColor.GREY_50_PERCENT.index);
        return celdaDetalle;
    }

    public CellStyle getStyleDetallePorc(short color) {
        CellStyle celdaDetalleS = libro.createCellStyle();
        Font cellSF = libro.createFont();
        cellSF.setFontName(HSSFFont.FONT_ARIAL);
        cellSF.setFontHeightInPoints((short) 12);
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

    public CellStyle getTituloStyleTotales(short color) {
        //CREAMOS CABECERAS DEL REPORTE 
        celdaTotales = libro.createCellStyle();
        celdaTotales.setAlignment((short) 2);
        Font cellSF = libro.createFont();
        cellSF.setFontName(HSSFFont.FONT_ARIAL);
        cellSF.setFontHeightInPoints((short) 12);
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
    //CREAMOS CELLSTYLE CON LA CONFIGURACION DELOS TITULOS

}
