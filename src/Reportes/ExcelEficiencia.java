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
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.formula.FormulaParseException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;

public final class ExcelEficiencia {

    File ArchivoXLS;
    HSSFSheet hoja;
    HSSFWorkbook libro;
    int Renglon = 0;
    int columna = 0;
    CellStyle celdaStiloCabecera;
    CellStyle celdaDetalle;
    CellStyle celdaTotales;

    public ExcelEficiencia() {

    }

    public ExcelEficiencia(DefaultTableModel modelo, String Nombre, String FECHA) throws IOException {
        FileOutputStream archivo = null;
        try {
            /*La ruta donde se creará el archivo*/
            String rutaArchivo = "C:\\compartida\\produccion_corte\\templates\\ReporteHC" + FECHA + ".xls";
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
                celda.setCellValue(GetFecha(new Date()));

                renglon = hoja.createRow(2);
                for (int i = 2; i < modelo.getColumnCount() + 2; i++) {
                    hoja.setColumnWidth(i, modelo.getColumnName(i - 2).length() * 400);
                    // hoja.autoSizeColumn(i);
                    celda = renglon.createCell(i);
                    celda.setCellValue(modelo.getColumnName(i - 2));
                    celda.setCellStyle(getTituloStyleTotales(HSSFColor.YELLOW.index));
                }
            }
            for (int x = 0; x < modelo.getRowCount(); x++) {
                Row renglon = hoja.createRow((x + 3));

                Cell celda = null;
                for (int i = 2; i < modelo.getColumnCount() + 2; i++) {
                    celda = renglon.createCell(i);
                    try {
                        celda.setCellValue(Double.parseDouble(modelo.getValueAt(x, i - 2).toString()));
                    } catch (NumberFormatException e) {
                        celda.setCellValue(modelo.getValueAt(x, i - 2).toString());
                    }

                    celda.setCellStyle(celdaDetalle);
                }
            }

//                AGREGAR RENGLON EN LA LIBRETA
            Row renglon = hoja.createRow((modelo.getRowCount() + 3));
            Cell celda = null;
            celda = renglon.createCell(2);
            celda.setCellValue("TOTAL PLANTAaa");
            celda.setCellStyle(celdaDetalle);
            celda = renglon.createCell(3);
            celda.setCellStyle(celdaDetalle);
//                     celda=renglon.createCell(4);
//                     celda.setCellStyle(celdaDetalle);
//                     celda.setCellFormula("SUM(E4:E"+(modelo.getRowCount()+3)+")");
//                     celda=renglon.createCell(5);
//                     celda.setCellStyle(celdaDetalle);
//                     celda.setCellFormula("SUM(F4:F"+(modelo.getRowCount()+3)+")");

            for (int i = 0; i < modelo.getRowCount() + 1; i++) {
                renglon = hoja.getRow(i + 3);
                celda = renglon.createCell(10);
                celda.setCellStyle(getStyleDetallePorc());
                celda.setCellFormula("E" + (i + 4) + "/F" + (i + 4));
            }
// 
            //

            libro.write(archivo);
            /*Cerramos el flujo de datos*/
            archivo.close();
            /*Y abrimos el archivo con la clase Desktop*/
            Desktop.getDesktop().open(archivoXLS);
        } catch (FormulaParseException | IOException ex) {
            System.out.println(ex.toString() + "aqui fue el pedo 7");
        } finally {
            try {
                archivo.close();
            } catch (IOException ex) {
                System.out.println(ex.toString() + "aqui fue el pedo 8");
            }
        }
    }
    //CONSTRUCTOR QUE EXPORTA A EXCEL TODAS LAS CADENA Y TURNO EL BUENO MIRIAM

    public ExcelEficiencia(ArrayList<DefaultTableModel> modelo, DefaultTableModel modeloTotal, ArrayList<String> Nombre, DefaultTableModel modeloTotal2, DefaultTableModel ModelocorteM, DefaultTableModel plataformas, DefaultTableModel plataformasMSD, DefaultTableModel TCODIGOS, DefaultTableModel plataformasT, DefaultTableModel concentradoplataformaslm, DefaultTableModel concentradoplataformasgml, DefaultTableModel concentradoplataformasLMYGML, DefaultTableModel plataformasGMTLM, DefaultTableModel plataformaK2XXLM, DefaultTableModel plataformaT1XXLM, DefaultTableModel plataformaE2XXLM, DefaultTableModel plataformaISUZULM, DefaultTableModel plataformaSERVICIOSLM, DefaultTableModel plataformaCORTELM, DefaultTableModel plataformasGMTGML, DefaultTableModel plataformasK2XXGML, DefaultTableModel plataformasT1XXGML, DefaultTableModel plataformascorteGML, DefaultTableModel plataformasserviciosGML, DefaultTableModel TBLCATIA, DefaultTableModel PPCORTE, String Nota, String FECHA) throws IOException {
        FileOutputStream archivo = null;
        int ho = 0;
        try {
            /*La ruta donde se creará el archivo*/
            //String rutaArchivo = System.getProperty("user.home")+"/desktop/Reporte HC "+Nombre+".xls";
            String rutaArchivo = "C:\\REPORTES HC\\ReporteHC " + FECHA + ".xls";
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
                        celda.setCellValue(modelo.get(ho).getColumnName(i));
                        celda.setCellStyle(celdaStiloCabecera);

                    }
                }
                ///aqui es el pedo
                for (int x = 0; x < modelo.get(ho).getRowCount(); x++) {
                    Row renglon = hoja.createRow(x + 3);
                    for (int i = 0; i < modelo.get(ho).getColumnCount(); i++) {
                        Cell celda = renglon.createCell(i);
                        try {
                            // se forma plataforma,codigo,linea,turno
                            celda.setCellValue(modelo.get(ho).getValueAt(x, i).toString());
                            // SALIDA EN PIEZA A EFICIENCIA
                            celda.setCellValue(Double.parseDouble(modelo.get(ho).getValueAt(x, i).toString()));
                        } catch (Exception e) {
                            System.out.println(e.toString() + "ENTRO AL CATCH");
                        }
                        if (i != modelo.get(ho).getColumnCount() - 1) {
                            celda.setCellStyle(celdaDetalle);
                        } else {
                            celda.setCellStyle(getStyleDetallePorc());
                        }
                    }
                }
                for (int x = 0; x < modelo.get(ho).getRowCount(); x++) {
                    try {
                        Row renglon = hoja.getRow(x + 3);
                        Cell celda = renglon.createCell(modelo.get(ho).getColumnCount() - 1);
                        celda.setCellFormula("+" + getColumnName(modelo.get(ho).getColumnCount() - 3) + "" + (x + 4) + "/" + getColumnName(modelo.get(ho).getColumnCount() - 2) + "" + (x + 4));
                        celda.setCellStyle(getStyleDetallePorc());
                    } catch (Exception e) {
                        System.out.println(e.toString() + "aqui fue el pedo");
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
            ////METODO PARA CREAR HOJA DE CORTE TREES TURNOS 

            hoja = libro.createSheet("CORTE MOCHIS");
            Row r = hoja.createRow(3);
            for (int c = 0; c < ModelocorteM.getColumnCount() + 1; c++) {
                //hoja.setColumnWidth(i, Modelocorte.getColumnName(i).length()*450);
                hoja.setColumnWidth(c, ModelocorteM.getColumnName(c).length() * 350);
                Cell celda = r.createCell(c);
                celda.setCellStyle(celdaStiloCabecera);
                try {
                    celda.setCellValue(ModelocorteM.getColumnName(c));
                } catch (Exception e) {
//                            celda.setCellValue( modelo.get(ho).getValueAt(x, i).toString());
                }
            }
            for (int i = 0; i < ModelocorteM.getRowCount(); i++) {

                r = hoja.createRow(r.getRowNum() + 1);
                for (int x = 0; x < ModelocorteM.getColumnCount(); x++) {

                    Cell celda = r.createCell(x);
                    if (x > 2)
                                     try {
                        celda.setCellValue(Double.parseDouble(ModelocorteM.getValueAt(i, x).toString()));
                    } catch (NumberFormatException e) {

                    } else {
                        celda.setCellValue(ModelocorteM.getValueAt(i, x).toString());
                    }

                }

            }

            /// PARTE DONDE SE FORMA LA PESTANA DE PLATAFORMAS CONCENTRADO TOTAL (T.Plataforma)////////////
            hoja = libro.createSheet("T.Plataforma");

            // celdak.setCellStyle(getTituloStyleTotales(HSSFColor.GREEN.index));
            r = hoja.createRow(1);
            for (int c = 0; c < plataformasT.getColumnCount(); c++) {
                //hoja.setColumnWidth(i, Modelocorte.getColumnName(i).length()*450);
                hoja.setColumnWidth(c, plataformasT.getColumnName(c).length() * 550);
                Cell celda = r.createCell(c);
                celda.setCellStyle(celdaStiloCabecera);
                try {
                    celda.setCellValue(plataformasT.getColumnName(c));
                } catch (Exception e) {
                    //celda.setCellValue( modelo.get(ho).getValueAt(x, i).toString());
                }
            }

            for (int i = 0; i < plataformasT.getRowCount() - 3; i++) {

                r = hoja.createRow(r.getRowNum() + 1);
                for (int x = 0; x < plataformasT.getColumnCount(); x++) {
                    Cell celda = r.createCell(x);

                    if (x > 0)
                                     try {
                        celda.setCellValue(Double.parseDouble(plataformasT.getValueAt(i, x).toString()));
                    } catch (Exception e) {
                    } else {
                        celda.setCellValue(plataformasT.getValueAt(i, x).toString());
                    }
                }

            }
            /// tabla de corte srvicios y kachirules
            r = hoja.createRow(10);
            for (int i = 3; i < plataformasT.getRowCount(); i++) // aqui se le mueve cuando cambian las plataformas, -1 para abajo, +1 para arriba
            {

                r = hoja.createRow(r.getRowNum() + 1);
                for (int x = 0; x < plataformasT.getColumnCount(); x++) {
                    Cell celdak = r.createCell(x);

                    if (x > 0)
                                     try {
                        celdak.setCellValue(Double.parseDouble(plataformasT.getValueAt(i, x).toString()));
                    } catch (Exception e) {
                    } else {
                        celdak.setCellValue(plataformasT.getValueAt(i, x).toString());
                    }
                }

            }
            //total de corte serv +kachiorules/////
            Row renglonk = hoja.createRow((plataformasT.getRowCount() + 9)); // aqui tambien se le mueve cuando cambian las plataformas +1 para abajo, -1 para arriba
            Cell celdak = null;
            celdak = renglonk.createCell(0);
            celdak.setCellValue("TOTAL");
            celdak.setCellStyle(getTituloStyleTotales(HSSFColor.GREEN.index));

            celdak = renglonk.createCell(1);
            celdak.setCellStyle(celdaDetalle);
            celdak.setCellFormula("SUM(B12:B15)");

            celdak = renglonk.createCell(2);
            celdak.setCellStyle(celdaDetalle);
            celdak.setCellFormula("SUM(C12:C15)");

            celdak = renglonk.createCell(3);
            celdak.setCellStyle(celdaDetalle);
            celdak.setCellFormula("SUM(D12:D15)");

            Row renglonT = hoja.createRow((plataformasT.getRowCount()));
            Cell celdaT = null;
            celdaT = renglonT.createCell(0);
            celdaT.setCellValue("TOTAL");
            celdaT.setCellStyle(getTituloStyleTotales(HSSFColor.GREEN.index));
            celdaT = renglonT.createCell(1);
            celdaT.setCellStyle(celdaDetalle);
            celdaT.setCellFormula("SUM(B3:B6)");

            celdaT = renglonT.createCell(2);
            celdaT.setCellStyle(celdaDetalle);
            celdaT.setCellFormula("SUM(C3:C6)");

            celdaT = renglonT.createCell(3);
            celdaT.setCellStyle(celdaDetalle);
            celdaT.setCellFormula("SUM(D3:D6)");

            celdaT = renglonT.createCell(4);
            celdaT.setCellStyle(celdaDetalle);
            celdaT.setCellStyle(getStyleDetallePorc());
            celdaT.setCellFormula("SUM(E3:E6)");

            celdaT = renglonT.createCell(5);
            celdaT.setCellStyle(celdaDetalle);
            celdaT.setCellFormula("SUM(F3:F6)");

            celdaT = renglonT.createCell(6);
            celdaT.setCellStyle(celdaDetalle);
            celdaT.setCellFormula("SUM(G3:G6)");

            celdaT = renglonT.createCell(7);
            celdaT.setCellStyle(celdaDetalle);
            celdaT.setCellFormula("SUM(H3:H6)");

            celdaT = renglonT.createCell(8);
            celdaT.setCellStyle(celdaDetalle);
            celdaT.setCellStyle(getStyleDetallePorc());
            celdaT.setCellFormula("G7/H7");
//                            
//                 ///Porcentage horas embarcadas////
            for (int i = 0; i < plataformasT.getRowCount() - 3; i++) {
                Row renglonpor = hoja.getRow(i + 2);
                Cell celdapor = null;
                celdapor = renglonpor.createCell(4);
                celdapor.setCellStyle(celdaDetalle);
                celdapor.setCellStyle(getStyleDetallePorc());
                celdapor.setCellFormula("C" + (i + 3) + "/$C$7");
            }

            for (int i = 0; i < plataformasT.getRowCount() - 3; i++) {
                Row renglonpor = hoja.getRow(i + 2);
                Cell celdapor = null;
                celdapor = renglonpor.createCell(5);
                celdapor.setCellStyle(celdaDetalle);
                celdapor.setCellFormula("(E" + (i + 3) + "*$B$16)+B" + (i + 3) + "");
            }

            //Porcentage horas pagadas////
            for (int i = 0; i < plataformasT.getRowCount() - 3; i++) {
                Row renglonpor = hoja.getRow(i + 2);
                Cell celdapor = null;
                celdapor = renglonpor.createCell(6);
                celdapor.setCellStyle(celdaDetalle);
                //celdapor.setCellStyle(getStyleDetallePorc());
                celdapor.setCellFormula("(C16*E" + (i + 3) + ")+C" + (i + 3) + "");
            }

            // HRS EMB EF+CORTE////
            for (int i = 0; i < plataformasT.getRowCount() - 3; i++) {
                Row renglonpor = hoja.getRow(i + 2);
                Cell celdapor = null;
                celdapor = renglonpor.createCell(7);
                celdapor.setCellStyle(celdaDetalle);
                //  celdapor.setCellStyle(getStyleDetallePorc());
                //  celdapor.setCellFormula("C"+(i+3)+"/C9");
                celdapor.setCellFormula("(D16*E" + (i + 3) + ")+D" + (i + 3) + "");
            }

            ///EFIC////
            for (int i = 0; i < plataformasT.getRowCount() - 3; i++) {
                Row renglonpor = hoja.getRow(i + 2);
                Cell celdapor = null;
                celdapor = renglonpor.createCell(8);
                //  celdapor.setCellStyle(celdaDetalle);
                celdapor.setCellStyle(getStyleDetallePorc());
                celdapor.setCellFormula("G" + (i + 3) + "/H" + (i + 3) + "");
            }
            /////////////////// AQUI TERMINA PESTANA DE PLATAFORMAS TOTALES ///////////// 

            /// PARTE DONDE SE FORMA LA PESTANA DE CODIGOS CONCENTRADO TOTAL////////////
            hoja = libro.createSheet("T.CODIGOS");
            r = hoja.createRow(1);
            for (int c = 0; c < TCODIGOS.getColumnCount(); c++) {
                //hoja.setColumnWidth(i, Modelocorte.getColumnName(i).length()*450);
                hoja.setColumnWidth(c, TCODIGOS.getColumnName(c).length() * 550);
                Cell celda = r.createCell(c);
                celda.setCellStyle(celdaStiloCabecera);
                try {
                    celda.setCellValue(TCODIGOS.getColumnName(c));
                } catch (Exception e) {
                    //celda.setCellValue( modelo.get(ho).getValueAt(x, i).toString());
                }
            }

            for (int i = 0; i < TCODIGOS.getRowCount() - 3; i++) {

                r = hoja.createRow(r.getRowNum() + 1);
                for (int x = 0; x < TCODIGOS.getColumnCount(); x++) {
                    Cell celda = r.createCell(x);

                    if (x > 0)
                                     try {
                        celda.setCellValue(Double.parseDouble(TCODIGOS.getValueAt(i, x).toString()));
                    } catch (Exception e) {
                    } else {
                        celda.setCellValue(TCODIGOS.getValueAt(i, x).toString());
                    }
                }

            }
            /// tabla de corte srvicios y kachirules
            r = hoja.createRow(29);
            for (int i = 12; i < TCODIGOS.getRowCount(); i++) // aqui tambien se le mueve cuando cambian las plataformas CUANDO DESACTIVAS ES -1 AGREGAS ES MAS +1
            {

                r = hoja.createRow(r.getRowNum() + 1);
                for (int x = 0; x < TCODIGOS.getColumnCount(); x++) {
                    Cell celdatc = r.createCell(x);

                    if (x > 0)
                                     try {
                        celdatc.setCellValue(Double.parseDouble(TCODIGOS.getValueAt(i, x).toString()));
                    } catch (Exception e) {
                    } else {
                        celdatc.setCellValue(TCODIGOS.getValueAt(i, x).toString());
                    }
                }

            }
            //totales de corte serv +kachiorules/////
            Row renglontc = hoja.createRow((TCODIGOS.getRowCount() + 20));
            Cell celdatc = null;
            celdatc = renglontc.createCell(0);
            celdatc.setCellValue("TOTAL");
            celdatc.setCellStyle(celdaStiloCabecera);
            celdatc = renglontc.createCell(1);
            celdatc.setCellStyle(celdaDetalle);
            celdatc.setCellFormula("SUM(B31:B34)");

            celdatc = renglontc.createCell(2);
            celdatc.setCellStyle(celdaDetalle);
            celdatc.setCellFormula("SUM(C31:C34)");

            Row renglonTc = hoja.createRow((plataformasT.getRowCount() + 21)); // aqui tambien se le mueve cuando cambian las plataformas
            Cell celdaTc = null;
            celdaTc = renglonTc.createCell(0);
            celdaTc.setCellValue("TOTAL2");
            celdaTc.setCellStyle(celdaStiloCabecera);
            celdaTc = renglonTc.createCell(1);
            celdaTc.setCellStyle(celdaDetalle);
            celdaTc.setCellFormula("SUM(B3:B26)");

            celdaTc = renglonTc.createCell(2);
            celdaTc.setCellStyle(celdaDetalle);
            celdaTc.setCellFormula("SUM(C3:C26)");

            celdaTc = renglonTc.createCell(3);
            celdaTc.setCellStyle(celdaDetalle);
            celdaTc.setCellStyle(getStyleDetallePorc());
            celdaTc.setCellFormula("SUM(D3:D26)");

            celdaTc = renglonTc.createCell(4);
            celdaTc.setCellStyle(celdaDetalle);
            celdaTc.setCellFormula("SUM(E3:E26)");

            celdaTc = renglonTc.createCell(5);
            celdaTc.setCellStyle(celdaDetalle);
            celdaTc.setCellFormula("SUM(F3:F26)");

            celdaTc = renglonTc.createCell(6);
            celdaTc.setCellStyle(celdaDetalle);
            celdaTc.setCellStyle(getStyleDetallePorc());
            celdaTc.setCellFormula("E28/F28");
//                            
//                 ///Porcentage horas embarcadas////
            for (int i = 0; i < TCODIGOS.getRowCount() - 3; i++) {
                Row renglonpor = hoja.getRow(i + 2);
                Cell celdapor = null;
                celdapor = renglonpor.createCell(3);
                celdapor.setCellStyle(celdaDetalle);
                celdapor.setCellStyle(getStyleDetallePorc());
                celdapor.setCellFormula("B" + (i + 3) + "/$B$28");
            }

            //Porcentage horas pagadas////
            for (int i = 0; i < TCODIGOS.getRowCount() - 3; i++) {
                Row renglonpor = hoja.getRow(i + 2);
                Cell celdapor = null;
                celdapor = renglonpor.createCell(4);
                celdapor.setCellStyle(celdaDetalle);
                //celdapor.setCellStyle(getStyleDetallePorc());
                celdapor.setCellFormula("($B$37*D" + (i + 3) + ")+B" + (i + 3) + "");
            }

            // HRS EMB EF+CORTE////
            for (int i = 0; i < TCODIGOS.getRowCount() - 3; i++) {
                Row renglonpor = hoja.getRow(i + 2);
                Cell celdapor = null;
                celdapor = renglonpor.createCell(5);
                celdapor.setCellStyle(celdaDetalle);
                //  celdapor.setCellStyle(getStyleDetallePorc());
                //  celdapor.setCellFormula("C"+(i+3)+"/C9");
                celdapor.setCellFormula("($C$37*D" + (i + 3) + ")+C" + (i + 3) + "");
            }

            ///EFIC////
            for (int i = 0; i < TCODIGOS.getRowCount() - 3; i++) {
                Row renglonpor = hoja.getRow(i + 2);
                Cell celdapor;
                celdapor = null;
                celdapor = renglonpor.createCell(6);
                celdapor.setCellStyle(celdaDetalle);
                celdapor.setCellStyle(getStyleDetallePorc());
                celdapor.setCellFormula("E" + (i + 3) + "/F" + (i + 3) + "");
            }

            /////////////////// AQUI TERMINA PESTANA DE CODIGOS TOTALES ///////////// 
            ////// EMPIEZA PESTAÑA PLATAFORMAS/////////////
            hoja = libro.createSheet("PLATAFORMAS");
            r = hoja.createRow(1);
            for (int c = 0; c < plataformas.getColumnCount(); c++) {
                //hoja.setColumnWidth(i, Modelocorte.getColumnName(i).length()*450);
                hoja.setColumnWidth(c, plataformas.getColumnName(c).length() * 550);
                Cell celda = r.createCell(c);
                celda.setCellStyle(celdaStiloCabecera);
                try {
                    celda.setCellValue(plataformas.getColumnName(c));
                } catch (Exception e) {
//                            celda.setCellValue( modelo.get(ho).getValueAt(x, i).toString());
                }
            }
            for (int i = 0; i < plataformas.getRowCount(); i++) {

                r = hoja.createRow(r.getRowNum() + 1);
                for (int x = 0; x < plataformas.getColumnCount(); x++) {

                    Cell celda = r.createCell(x);

                    if (x > 4)
                                     try {
                        celda.setCellValue(Double.parseDouble(plataformas.getValueAt(i, x).toString()));
                    } catch (Exception e) {

                    } else {
                        celda.setCellValue(plataformas.getValueAt(i, x).toString());
                    }
                }

            }
            Row renglonN = hoja.createRow((plataformas.getRowCount() + 6));
            Cell celdaA = null;
            Cell celdaPOR = null;
            celdaA = renglonN.createCell(0);
            celdaA.setCellValue("TOTAL");
            celdaA.setCellStyle(celdaDetalle);
//                     celda=renglon.createCell(4);
//                     celda.setCellStyle(celdaDetalle);
//                     celda.setCellFormula("SUM(E4:E"+(modelo.getRowCount()+3)+")");
//                     celda=renglon.createCell(5);
//                     celda.setCellStyle(celdaDetalle);
//                     celda.setCellFormula("SUM(F4:F"+(modelo.getRowCount()+3)+")");
            celdaA = renglonN.createCell(5);
            celdaA.setCellStyle(celdaDetalle);
            celdaA.setCellFormula("SUBTOTAL(9,F3:F69)");
            celdaA.setCellStyle(getTituloStyleTotales(HSSFColor.GREEN.index));

            celdaA = renglonN.createCell(7);
            celdaA.setCellStyle(celdaDetalle);
            celdaA.setCellFormula("SUBTOTAL(9,H3:H69)");
            celdaA.setCellStyle(getTituloStyleTotales(HSSFColor.GREEN.index));

            celdaA = renglonN.createCell(8);
            celdaA.setCellStyle(celdaDetalle);
            celdaA.setCellFormula("SUBTOTAL(9,I3:I69)");
            celdaA.setCellStyle(getTituloStyleTotales(HSSFColor.GREEN.index));

            celdaPOR = renglonN.createCell(9);
            celdaPOR.setCellStyle(celdaDetalle);
            celdaPOR.setCellFormula("H73/I73");
            celdaPOR.setCellStyle(getStyleDetallePorc(HSSFColor.GREEN.index));

            /////////////////// AQUI TERMINA PESTANA DE PLATAFORMAS /////////////      
            ////// EMPIEZA PESTAÑA MSD/////////////
            hoja = libro.createSheet("MSD");

            r = hoja.createRow(1);
            for (int c = 0; c < plataformasMSD.getColumnCount(); c++) {
                //hoja.setColumnWidth(i, Modelocorte.getColumnName(i).length()*450);
                hoja.setColumnWidth(c, plataformasMSD.getColumnName(c).length() * 550);
                Cell celda = r.createCell(c);
                celda.setCellStyle(celdaStiloCabecera);
                try {
                    celda.setCellValue(plataformasMSD.getColumnName(c));
                } catch (Exception e) {
//                            celda.setCellValue( modelo.get(ho).getValueAt(x, i).toString());
                }
            }
            for (int i = 0; i < plataformasMSD.getRowCount(); i++) {
                r = hoja.createRow(r.getRowNum() + 1);
                for (int x = 0; x < plataformasMSD.getColumnCount(); x++) {
                    Cell celda = r.createCell(x);
                    if (x > 5)
                                     try {
                        celda.setCellValue(Double.parseDouble(plataformasMSD.getValueAt(i, x).toString()));
                        // celda.setCellValue(plataformasMSD.getValueAt(i,x).toString());
                    } catch (Exception e) {
                    } else {
                        celda.setCellValue(plataformasMSD.getValueAt(i, x).toString());
                    }
                }

            }
            Row renglonMSD = hoja.createRow((plataformasMSD.getRowCount() + 4));
            Cell celdab = null;
            Cell celdaPORc = null;
            celdab = renglonMSD.createCell(0);
            celdab.setCellValue("TOTAL");
            celdab.setCellStyle(celdaDetalle);
            celdab.setCellStyle(getTituloStyleTotales(HSSFColor.GREEN.index));

            celdab = renglonMSD.createCell(6);
            celdab.setCellStyle(celdaDetalle);
            celdab.setCellFormula("SUBTOTAL(9,G3:G73)");
            celdab.setCellStyle(getTituloStyleTotales(HSSFColor.GREEN.index));

            celdab = renglonMSD.createCell(7);
            celdab.setCellStyle(celdaDetalle);
            celdab.setCellFormula("SUBTOTAL(9,H3:H73)");
            celdab.setCellStyle(getTituloStyleTotales(HSSFColor.GREEN.index));

            celdab = renglonMSD.createCell(8);
            celdab.setCellStyle(celdaDetalle);
            celdab.setCellFormula("SUBTOTAL(9,I3:I73)");
            celdab.setCellStyle(getTituloStyleTotales(HSSFColor.GREEN.index));

            celdaPORc = renglonMSD.createCell(9);
            celdaPORc.setCellStyle(celdaDetalle);
            celdaPORc.setCellStyle(getStyleDetallePorc(HSSFColor.GREEN.index));
            celdaPORc.setCellFormula("H76/I76");

            celdab = renglonMSD.createCell(10);
            celdab.setCellStyle(celdaDetalle);
            celdab.setCellFormula("SUBTOTAL(9,K3:K73)");
            celdab.setCellStyle(getTituloStyleTotales(HSSFColor.GREEN.index));

            celdab = renglonMSD.createCell(11);
            celdab.setCellStyle(celdaDetalle);
            celdab.setCellFormula("SUBTOTAL(9,L3:L73)");
            celdab.setCellStyle(getTituloStyleTotales(HSSFColor.GREEN.index));

            celdab = renglonMSD.createCell(12);
            celdab.setCellStyle(celdaDetalle);
            celdab.setCellFormula("SUBTOTAL(9,M3:M73)");
            celdab.setCellStyle(getTituloStyleTotales(HSSFColor.GREEN.index));

            celdab = renglonMSD.createCell(13);
            celdab.setCellStyle(celdaDetalle);
            celdab.setCellFormula("SUBTOTAL(9,N3:N73)");
            celdab.setCellStyle(getTituloStyleTotales(HSSFColor.GREEN.index));

            celdaPORc = renglonMSD.createCell(14);
            celdaPORc.setCellStyle(celdaDetalle);
            celdaPORc.setCellStyle(getStyleDetallePorc(HSSFColor.GREEN.index));
            celdaPORc.setCellFormula("H76/N76");

            /////////////////// AQUI TERMINA PESTANA DE MSD /////////////                              
            ////// EMPIEZA PESTAÑA TABLA CATIA/////////////
            hoja = libro.createSheet("HC EF+LPS");
            r = hoja.createRow(1);
            for (int c = 0; c < TBLCATIA.getColumnCount(); c++) {
                //hoja.setColumnWidth(i, Modelocorte.getColumnName(i).length()*450);
                hoja.setColumnWidth(c, TBLCATIA.getColumnName(c).length() * 550);
                Cell celda = r.createCell(c);
                celda.setCellStyle(celdaStiloCabecera);
                try {
                    celda.setCellValue(TBLCATIA.getColumnName(c));
                } catch (Exception e) {
//                            celda.setCellValue( modelo.get(ho).getValueAt(x, i).toString());
                }
            }
            for (int i = 0; i < TBLCATIA.getRowCount(); i++) {
                r = hoja.createRow(r.getRowNum() + 1);
                for (int x = 0; x < TBLCATIA.getColumnCount(); x++) {
                    Cell celda = r.createCell(x);
                    if (x > 3)
                                     try {
                        celda.setCellValue(Double.parseDouble(TBLCATIA.getValueAt(i, x).toString()));
                        //celda.setCellValue(TBLCATIA.getValueAt(i,x).toString());
                    } catch (Exception e) {
                    } else {
                        celda.setCellValue(TBLCATIA.getValueAt(i, x).toString());
                    }
                }

            }

            Row renglonCa = hoja.createRow((TBLCATIA.getRowCount() + 4));
            Cell celdac = null;
            celdac = renglonCa.createCell(0);
            celdac.setCellValue("TOTAL");
            celdac.setCellStyle(celdaDetalle);
            celdac.setCellStyle(getTituloStyleTotales(HSSFColor.GREEN.index));

            celdac = renglonCa.createCell(4);
            celdac.setCellStyle(celdaDetalle);
            celdac.setCellFormula("SUBTOTAL(9,E3:E39)");
            celdac.setCellStyle(getTituloStyleTotales(HSSFColor.GREEN.index));

            /////////////////// AQUI TERMINA PESTANA TABLA CATIA /////////////                
            ////// EMPIEZA PESTAÑA TABLA PUNTOS PIEZA CORTE/////////////
            hoja = libro.createSheet("PP_CORTE");
            r = hoja.createRow(1);
            for (int c = 0; c < PPCORTE.getColumnCount(); c++) {
                //hoja.setColumnWidth(i, Modelocorte.getColumnName(i).length()*450);
                hoja.setColumnWidth(c, PPCORTE.getColumnName(c).length() * 405);
                hoja.setColumnWidth(1, 6000);
                Cell celda = r.createCell(c);
                celda.setCellStyle(celdaStiloCabecera);
                try {
                    celda.setCellValue(PPCORTE.getColumnName(c));
                } catch (Exception e) {
//                            celda.setCellValue( modelo.get(ho).getValueAt(x, i).toString());
                }
            }
            for (int i = 0; i < PPCORTE.getRowCount(); i++) {
                r = hoja.createRow(r.getRowNum() + 1);
                for (int x = 0; x < PPCORTE.getColumnCount(); x++) {
                    Cell celda = r.createCell(x);
                    if (x > 5)
                                     try {
                        celda.setCellValue(Double.parseDouble(PPCORTE.getValueAt(i, x).toString()));
                        //celda.setCellValue(TBLCATIA.getValueAt(i,x).toString());
                    } catch (Exception e) {
                    } else {
                        celda.setCellValue(PPCORTE.getValueAt(i, x).toString());
                    }
                }

            }

            Row renglonPP = hoja.createRow((PPCORTE.getRowCount() + 4));
            Cell celdaPP = null;
            Cell celdaPORcP = null;
            celdaPP = renglonPP.createCell(0);
            celdaPP.setCellValue("TOTAL");
            celdaPP.setCellStyle(getTituloStyleTotales(HSSFColor.GREEN.index));

            celdaPP = renglonPP.createCell(17);
            celdaPP.setCellStyle(celdaDetalle);
            celdaPP.setCellFormula("SUBTOTAL(9,R3:R37)");
            celdaPP.setCellStyle(getTituloStyleTotales(HSSFColor.GREEN.index));

            celdaPP = renglonPP.createCell(18);
            celdaPP.setCellStyle(celdaDetalle);
            celdaPP.setCellFormula("SUBTOTAL(9,S3:S37)");
            celdaPP.setCellStyle(getTituloStyleTotales(HSSFColor.GREEN.index));

            celdaPORcP = renglonPP.createCell(19);
            celdaPORcP.setCellStyle(celdaDetalle);
            //celdaPORcP.setCellStyle(getStyleDetallePorc());
            celdaPORcP.setCellStyle(getStyleDetallePorc(HSSFColor.GREEN.index));
            celdaPORcP.setCellFormula("R40/S40");

            celdaPP = renglonPP.createCell(21);
            celdaPP.setCellStyle(celdaDetalle);
            celdaPP.setCellFormula("SUBTOTAL(9,V3:V37)");
            celdaPP.setCellStyle(getTituloStyleTotales(HSSFColor.GREEN.index));

            celdaPP = renglonPP.createCell(22);
            celdaPP.setCellStyle(celdaDetalle);
            celdaPP.setCellFormula("SUBTOTAL(9,W3:W37)");
            celdaPP.setCellStyle(getTituloStyleTotales(HSSFColor.GREEN.index));

            ///Porcentage horas embarcadas////
            for (int i = 0; i < PPCORTE.getRowCount(); i++) {
                Row renglonpor = hoja.getRow(i + 2);
                Cell celdapor = null;
                celdapor = renglonpor.createCell(22);
                celdapor.setCellStyle(celdaDetalle);
                celdapor.setCellFormula("ROUND(V" + (i + 3) + "/$V$40*135,2)");
            }

            ///////////////////  EMPIEZA PESTAÑA TABLA PUNTOS PIEZA CORTE /////////////            
//                  hoja=libro.createSheet("PLAT.Concentrado");
//             r=hoja.createRow(1);
//             
//          
//               Row renglonC=hoja.createRow(0);
//               // hoja.addMergedRegion(new CellRangeAddress(1,1, 1,6));
//                 Cell celdaC=renglonC.createCell(0);
//                    celdaC.setCellValue("PLATAFORMAS MOCHIS");
//                    celdaC.setCellStyle(celdaStiloCabecera);
//                     
//             
//                    
//                    
//                    
//            for(int c=0; c<concentradoplataformaslm.getColumnCount(); c++)
//                    {
//                        //hoja.setColumnWidth(i, Modelocorte.getColumnName(i).length()*450);
//                        hoja.setColumnWidth(c, concentradoplataformaslm.getColumnName(c).length()*400);
//                         Cell celda=r.createCell(c);
//                         celda.setCellStyle(celdaStiloCabecera);
//                        try{
//                            celda.setCellValue(concentradoplataformaslm.getColumnName(c));
//                        }catch(Exception e)
//                        {
//                           // celda.setCellValue( modelo.get(ho).getValueAt(x, i).toString());
//                        }       
//                    }
//            for(int i=0; i<concentradoplataformaslm.getRowCount(); i++)
//            {
//
//                   r=hoja.createRow(r.getRowNum()+1);
//                    for(int x=0; x<concentradoplataformaslm.getColumnCount(); x++)
//                    {
//                      
//                                 Cell celda=r.createCell(x);
//                                  
//                                 if(x>1){
//                                     try{
//                                         
//                                     
//                                  celda.setCellValue(Double.parseDouble( concentradoplataformaslm.getValueAt(i,x).toString()));
//                                     } 
//                                     catch(Exception e ){
//                                     
//                                     }          
//                                }
//                                 else{
//                                     celda.setCellValue( concentradoplataformaslm.getValueAt(i,x).toString());
//                                     // celda.setCellStyle(getStyleDetallePorc());
//                                 }
//                    }
//  
//            }
//            
//           
//             Row rengloncONLM=hoja.createRow((concentradoplataformaslm.getRowCount()+3));
//                     Cell celdaCONLM=null;
//                     celdaCONLM=rengloncONLM.createCell(0);
//                     celdaCONLM.setCellValue("TOTAL LM");
//                     celdaCONLM.setCellStyle(celdaDetalle);
////                     celda=renglon.createCell(4);
////                     celda.setCellStyle(celdaDetalle);
////                     celda.setCellFormula("SUM(E4:E"+(modelo.getRowCount()+3)+")");
////                     celda=renglon.createCell(5);
////                     celda.setCellStyle(celdaDetalle);
////                     celda.setCellFormula("SUM(F4:F"+(modelo.getRowCount()+3)+")");
//                   celdaCONLM=rengloncONLM.createCell(2);
//                            celdaCONLM.setCellStyle(celdaDetalle);
//                            celdaCONLM.setCellFormula("SUM(C3:C21)");
//                            
//                             celdaCONLM=rengloncONLM.createCell(3);
//                            celdaCONLM.setCellStyle(celdaDetalle);
//                            celdaCONLM.setCellFormula("SUM(D3:D21)");
//                            
//                              celdaCONLM=rengloncONLM.createCell(4);
//                            celdaCONLM.setCellStyle(celdaDetalle);
//                            celdaCONLM.setCellFormula("SUM(E3:E21)");
//                       
//                               celdaCONLM=rengloncONLM.createCell(5);
//                            celdaCONLM.setCellStyle(celdaDetalle);
//                            celdaCONLM.setCellStyle(getStyleDetallePorc());
//                            celdaCONLM.setCellFormula("SUM(D3:D21)/SUM(E3:E21)");
//                
//                            
//                            
//                          //aqui empieza plataformas de gml  
//                            r=hoja.createRow(25);
//                            
//                            Row renglonCML=hoja.createRow(24);
//               // hoja.addMergedRegion(new CellRangeAddress(1,1, 1,6));
//                 Cell celdaCML=renglonCML.createCell(0);
//                    celdaCML.setCellValue("PLATAFORMAS GUAMUCHIL");
//                    celdaCML.setCellStyle(celdaStiloCabecera);
//                    
//                               for(int c=0; c<concentradoplataformasgml.getColumnCount(); c++)
//                    {
//                        //hoja.setColumnWidth(i, Modelocorte.getColumnName(i).length()*450);
//                        hoja.setColumnWidth(c, concentradoplataformasgml.getColumnName(c).length()*400);
//                         Cell celda=r.createCell(c);
//                         celda.setCellStyle(celdaStiloCabecera);
//                        try{
//                            celda.setCellValue(concentradoplataformasgml.getColumnName(c));
//                        }catch(Exception e)
//                        {
////                            celda.setCellValue( modelo.get(ho).getValueAt(x, i).toString());
//                        }       
//                    }
//            for(int i=0; i<concentradoplataformasgml.getRowCount(); i++)
//            {
//
//                   r=hoja.createRow(r.getRowNum()+1);
//                    for(int x=0; x<concentradoplataformasgml.getColumnCount(); x++)
//                    {
//                      
//                                 Cell celda=r.createCell(x);
//                                  
//                                 if(x>1){
//                                  celda.setCellValue(Double.parseDouble( concentradoplataformasgml.getValueAt(i,x).toString()));
//                                               
//                                }
//                                 else{
//                                     celda.setCellValue( concentradoplataformasgml.getValueAt(i,x).toString());
//                                     // celda.setCellStyle(getStyleDetallePorc());
//                                 }
//                    }
//  
//            }
//            
//            
//             Row rengloncONgml=hoja.createRow((concentradoplataformasgml.getRowCount()+27));
//                     Cell celdaCONgml=null;
//                     celdaCONgml=rengloncONgml.createCell(0);
//                     celdaCONgml.setCellValue("TOTAL GML");
//                     celdaCONgml.setCellStyle(celdaDetalle);
////                     celda=renglon.createCell(4);
////                     celda.setCellStyle(celdaDetalle);
////                     celda.setCellFormula("SUM(E4:E"+(modelo.getRowCount()+3)+")");
////                     celda=renglon.createCell(5);
////                     celda.setCellStyle(celdaDetalle);
////                     celda.setCellFormula("SUM(F4:F"+(modelo.getRowCount()+3)+")");
//                   celdaCONgml=rengloncONgml.createCell(2);
//                            celdaCONgml.setCellStyle(celdaDetalle);
//                            celdaCONgml.setCellFormula("SUM(C27:C34)");
//                            
//                             celdaCONgml=rengloncONgml.createCell(3);
//                            celdaCONgml.setCellStyle(celdaDetalle);
//                            celdaCONgml.setCellFormula("SUM(D27:D34)");
//                            
//                              celdaCONgml=rengloncONgml.createCell(4);
//                            celdaCONgml.setCellStyle(celdaDetalle);
//                            celdaCONgml.setCellFormula("SUM(E27:E34)");
//                       
//                               celdaCONgml=rengloncONgml.createCell(5);
//                            celdaCONgml.setCellStyle(celdaDetalle);
//                            celdaCONgml.setCellStyle(getStyleDetallePorc());
//                            celdaCONgml.setCellFormula("SUM(D27:D34)/SUM(E27:E34)");
//             
//                            
//                            
//                       //AQUI EMPIEZA SUMAS DE CONCENTRADOS DE PLATAFORMAS LM Y GML
//                            
//                               
//                            r=hoja.createRow(38);
//                               for(int c=0; c<concentradoplataformasLMYGML.getColumnCount(); c++)
//                    {
//                        //hoja.setColumnWidth(i, Modelocorte.getColumnName(i).length()*450);
//                        hoja.setColumnWidth(c, concentradoplataformasLMYGML.getColumnName(c).length()*400);
//                         Cell celda=r.createCell(c);
//                         celda.setCellStyle(celdaStiloCabecera);
//                        try{
//                            celda.setCellValue(concentradoplataformasLMYGML.getColumnName(c));
//                        }catch(Exception e)
//                        {
////                           celda.setCellValue( modelo.get(ho).getValueAt(x, i).toString());
//                        }       
//                    }
//            for(int i=0; i<concentradoplataformasLMYGML.getRowCount(); i++)
//            {
//
//                  r=hoja.createRow(r.getRowNum()+1);
//                   for(int x=0; x<concentradoplataformasLMYGML.getColumnCount(); x++)
//                    {
//                      try{
//                               Cell celda=r.createCell(x);
//                                 
//                                 if(x>2){
//                                   
//                                 celda.setCellValue(Double.parseDouble(concentradoplataformasLMYGML.getValueAt(i,x).toString()));
//                                 celda.setCellValue( concentradoplataformasLMYGML.getValueAt(i,x).toString());        
//                               }
//                                 else{
//                                     celda.setCellValue( concentradoplataformasLMYGML.getValueAt(i,x).toString());
//                                     // celda.setCellStyle(getStyleDetallePorc());
//                                 }
//                      }catch(NullPointerException e){
//                          
//                      }
//                             
//                    }
//  
//            }
//            
//             
//                            Row renglonTP=hoja.createRow(37);
//               // hoja.addMergedRegion(new CellRangeAddress(1,1, 1,6));
//                 Cell celdaTP=renglonTP.createCell(0);
//                    celdaTP.setCellValue("PLATAFORMAS TOTAL");
//                    celdaTP.setCellStyle(celdaStiloCabecera);
//             Row rengloncONLMYGML=hoja.createRow((concentradoplataformasLMYGML.getRowCount()+41));
//                     Cell celdaCONLMYGML=null;
//                     celdaCONLMYGML=rengloncONLMYGML.createCell(0);
//                     celdaCONLMYGML.setCellValue("TOTAL LM+GML");
//                     celdaCONLMYGML.setCellStyle(celdaDetalle);
////                     celda=renglon.createCell(4);
////                     celda.setCellStyle(celdaDetalle);
////                     celda.setCellFormula("SUM(E4:E"+(modelo.getRowCount()+3)+")");
////                     celda=renglon.createCell(5);
////                     celda.setCellStyle(celdaDetalle);
////                     celda.setCellFormula("SUM(F4:F"+(modelo.getRowCount()+3)+")");
//                   celdaCONLMYGML=rengloncONLMYGML.createCell(3);
//                            celdaCONLMYGML.setCellStyle(celdaDetalle);
//                            celdaCONLMYGML.setCellFormula("SUM(D40:D66)");
//                            
//                             celdaCONLMYGML=rengloncONLMYGML.createCell(4);
//                            celdaCONLMYGML.setCellStyle(celdaDetalle);
//                            celdaCONLMYGML.setCellFormula("SUM(E40:E66)");
//                            
//                              celdaCONLMYGML=rengloncONLMYGML.createCell(5);
//                            celdaCONLMYGML.setCellStyle(celdaDetalle);
//                            celdaCONLMYGML.setCellFormula("SUM(F40:F66)");
//                       
//                               celdaCONLMYGML=rengloncONLMYGML.createCell(6);
//                            celdaCONLMYGML.setCellStyle(celdaDetalle);
//                            celdaCONLMYGML.setCellStyle(getStyleDetallePorc());
//                           celdaCONLMYGML.setCellFormula("SUM(E40:E66)/SUM(F40:F66)");
//                            
//                            
//                            
//                            
//                            
//                            
//                            
//               hoja=libro.createSheet("PLATAFORMAS LM");
//             r=hoja.createRow(1);
//             
//             //GMT610
//            for(int c=0; c<plataformasGMTLM.getColumnCount(); c++)
//                    {
//                        //hoja.setColumnWidth(i, Modelocorte.getColumnName(i).length()*450);
//                        hoja.setColumnWidth(c, plataformasGMTLM.getColumnName(c).length()*350);
//                         Cell celda=r.createCell(c);
//                         celda.setCellStyle(celdaStiloCabecera);
//                        try{
//                            celda.setCellValue(plataformasGMTLM.getColumnName(c));
//                        }catch(Exception e)
//                        {
////                            celda.setCellValue( modelo.get(ho).getValueAt(x, i).toString());
//                        }       
//                    }
//            for(int i=0; i<plataformasGMTLM.getRowCount(); i++)
//            {
//
//                   r=hoja.createRow(r.getRowNum()+1);
//                    for(int x=0; x<plataformasGMTLM.getColumnCount(); x++)
//                    {
//                      
//                                 Cell celda=r.createCell(x);
//                                  
//                                 if(x>4)
//                                  celda.setCellValue(Double.parseDouble( plataformasGMTLM.getValueAt(i,x).toString()));
//                                 
//                                 else
//                                     celda.setCellValue( plataformasGMTLM.getValueAt(i,x).toString());
//                    }
//  
//            }
//             Row renglonGMT=hoja.createRow((plataformasGMTLM.getRowCount()+4));
//                     Cell celdaGMT=null;
//                     celdaGMT=renglonGMT.createCell(0);
//                     celdaGMT.setCellValue("TOTAL GMT 610");
//                     celdaGMT.setCellStyle(celdaDetalle);
//                     celdaGMT.setCellStyle(celdaStiloCabecera);
////                     celda=renglon.createCell(4);
////                     celda.setCellStyle(celdaDetalle);
////                     celda.setCellFormula("SUM(E4:E"+(modelo.getRowCount()+3)+")");
////                     celda=renglon.createCell(5);
////                     celda.setCellStyle(celdaDetalle);
////                     celda.setCellFormula("SUM(F4:F"+(modelo.getRowCount()+3)+")");
//                   celdaGMT=renglonGMT.createCell(6);
//                            celdaGMT.setCellStyle(celdaDetalle);
//                            celdaGMT.setCellFormula("SUM(G3:G27)");
//                            
//                             celdaGMT=renglonGMT.createCell(7);
//                            celdaGMT.setCellStyle(celdaDetalle);
//                            celdaGMT.setCellFormula("SUM(H3:H27)");
//            
//                               celdaGMT=renglonGMT.createCell(8);
//                            celdaGMT.setCellStyle(celdaDetalle);
//                            celdaGMT.setCellStyle(getStyleDetallePorc());
//                            celdaGMT.setCellFormula("G28/H28");              
//                            
//                             
//                            
//                            //K2XX
//                            r=hoja.createRow(30);
//                             for(int c=0; c<plataformaK2XXLM.getColumnCount(); c++)
//                    {
//                        //hoja.setColumnWidth(i, Modelocorte.getColumnName(i).length()*450);
//                        hoja.setColumnWidth(c, plataformaK2XXLM.getColumnName(c).length()*350);
//                         Cell celda=r.createCell(c);
//                         celda.setCellStyle(celdaStiloCabecera);
//                        try{
//                            celda.setCellValue(plataformaK2XXLM.getColumnName(c));
//                        }catch(Exception e)
//                        {
////                            celda.setCellValue( modelo.get(ho).getValueAt(x, i).toString());
//                        }       
//                    }
//            for(int i=0; i<plataformaK2XXLM.getRowCount(); i++)
//            {
//
//                   r=hoja.createRow(r.getRowNum()+1);
//                    for(int x=0; x<plataformaK2XXLM.getColumnCount(); x++)
//                    {
//                      
//                                 Cell celda=r.createCell(x);
//                                  
//                                 if(x>4)
//                                  celda.setCellValue(Double.parseDouble( plataformaK2XXLM.getValueAt(i,x).toString()));
//                                 
//                                 else
//                                     celda.setCellValue( plataformaK2XXLM.getValueAt(i,x).toString());
//                    }
//  
//            }
//             Row renglonK2XX=hoja.createRow((plataformaK2XXLM.getRowCount()+33));
//                     Cell celdaK2XX=null;
//                     celdaK2XX=renglonK2XX.createCell(0);
//                     celdaK2XX.setCellValue("TOTAL K2XX");
//                     celdaK2XX.setCellStyle(celdaDetalle);
//                     celdaK2XX.setCellStyle(celdaStiloCabecera);
////                     celda=renglon.createCell(4);
////                     celda.setCellStyle(celdaDetalle);
////                     celda.setCellFormula("SUM(E4:E"+(modelo.getRowCount()+3)+")");
////                     celda=renglon.createCell(5);
////                     celda.setCellStyle(celdaDetalle);
////                     celda.setCellFormula("SUM(F4:F"+(modelo.getRowCount()+3)+")");
//                   celdaK2XX=renglonK2XX.createCell(6);
//                            celdaK2XX.setCellStyle(celdaDetalle);
//                            celdaK2XX.setCellFormula("SUM(G32:G51)");
//                            
//                             celdaK2XX=renglonK2XX.createCell(7);
//                            celdaK2XX.setCellStyle(celdaDetalle);
//                            celdaK2XX.setCellFormula("SUM(H32:H51)");
//            
//                               celdaK2XX=renglonK2XX.createCell(8);
//                            celdaK2XX.setCellStyle(celdaDetalle);
//                            celdaK2XX.setCellStyle(getStyleDetallePorc());
//                            celdaK2XX.setCellFormula("G52/H52");  
//                            
//                            
//                            
//                              //T1XX
//                            r=hoja.createRow(54);
//                             for(int c=0; c<plataformaT1XXLM.getColumnCount(); c++)
//                    {
//                        //hoja.setColumnWidth(i, Modelocorte.getColumnName(i).length()*450);
//                        hoja.setColumnWidth(c, plataformaT1XXLM.getColumnName(c).length()*350);
//                         Cell celda=r.createCell(c);
//                         celda.setCellStyle(celdaStiloCabecera);
//                        try{
//                            celda.setCellValue(plataformaT1XXLM.getColumnName(c));
//                        }catch(Exception e)
//                        {
////                            celda.setCellValue( modelo.get(ho).getValueAt(x, i).toString());
//                        }       
//                    }
//            for(int i=0; i<plataformaT1XXLM.getRowCount(); i++)
//            {
//
//                   r=hoja.createRow(r.getRowNum()+1);
//                    for(int x=0; x<plataformaT1XXLM.getColumnCount(); x++)
//                    {
//                      
//                                 Cell celda=r.createCell(x);
//                                  
//                                 if(x>4)
//                                  celda.setCellValue(Double.parseDouble( plataformaT1XXLM.getValueAt(i,x).toString()));
//                                 
//                                 else
//                                     celda.setCellValue( plataformaT1XXLM.getValueAt(i,x).toString());
//                    }
//  
//            }
//             Row renglonT1XX=hoja.createRow((plataformaT1XXLM.getRowCount()+57));
//                     Cell celdaT1XX=null;
//                     celdaT1XX=renglonT1XX.createCell(0);
//                     celdaT1XX.setCellValue("TOTAL T1XX");
//                     celdaT1XX.setCellStyle(celdaDetalle);
//                     celdaT1XX.setCellStyle(celdaStiloCabecera);
////                     celda=renglon.createCell(4);
////                     celda.setCellStyle(celdaDetalle);
////                     celda.setCellFormula("SUM(E4:E"+(modelo.getRowCount()+3)+")");
////                     celda=renglon.createCell(5);
////                     celda.setCellStyle(celdaDetalle);
////                     celda.setCellFormula("SUM(F4:F"+(modelo.getRowCount()+3)+")");
//                   celdaT1XX=renglonT1XX.createCell(6);
//                            celdaT1XX.setCellStyle(celdaDetalle);
//                            celdaT1XX.setCellFormula("SUM(G56:G73)");
//                            
//                             celdaT1XX=renglonT1XX.createCell(7);
//                            celdaT1XX.setCellStyle(celdaDetalle);
//                            celdaT1XX.setCellFormula("SUM(H56:H73)");
//            
//                               celdaT1XX=renglonT1XX.createCell(8);
//                            celdaT1XX.setCellStyle(celdaDetalle);
//                            celdaT1XX.setCellStyle(getStyleDetallePorc());
//                            celdaT1XX.setCellFormula("G74/H74"); 
//                            
//                            
//                         //E2XX
//                            r=hoja.createRow(77);
//                             for(int c=0; c<plataformaE2XXLM.getColumnCount(); c++)
//                    {
//                        //hoja.setColumnWidth(i, Modelocorte.getColumnName(i).length()*450);
//                        hoja.setColumnWidth(c, plataformaE2XXLM.getColumnName(c).length()*350);
//                         Cell celda=r.createCell(c);
//                         celda.setCellStyle(celdaStiloCabecera);
//                        try{
//                            celda.setCellValue(plataformaE2XXLM.getColumnName(c));
//                        }catch(Exception e)
//                        {
////                            celda.setCellValue( modelo.get(ho).getValueAt(x, i).toString());
//                        }       
//                    }
//            for(int i=0; i<plataformaE2XXLM.getRowCount(); i++)
//            {
//
//                   r=hoja.createRow(r.getRowNum()+1);
//                    for(int x=0; x<plataformaE2XXLM.getColumnCount(); x++)
//                    {
//                      
//                                 Cell celda=r.createCell(x);
//                                  
//                                 if(x>4)
//                                  celda.setCellValue(Double.parseDouble( plataformaE2XXLM.getValueAt(i,x).toString()));
//                                 
//                                 else
//                                     celda.setCellValue( plataformaE2XXLM.getValueAt(i,x).toString());
//                    }
//  
//            }
//             Row renglonE2XX=hoja.createRow((plataformaE2XXLM.getRowCount()+80));
//                     Cell celdaE2XX=null;
//                     celdaE2XX=renglonE2XX.createCell(0);
//                     celdaE2XX.setCellValue("TOTAL E2XX");
//                     celdaE2XX.setCellStyle(celdaDetalle);
//                     celdaE2XX.setCellStyle(celdaStiloCabecera);
////                     celda=renglon.createCell(4);
////                     celda.setCellStyle(celdaDetalle);
////                     celda.setCellFormula("SUM(E4:E"+(modelo.getRowCount()+3)+")");
////                     celda=renglon.createCell(5);
////                     celda.setCellStyle(celdaDetalle);
////                     celda.setCellFormula("SUM(F4:F"+(modelo.getRowCount()+3)+")");
//                            celdaE2XX=renglonE2XX.createCell(6);
//                            celdaE2XX.setCellStyle(celdaDetalle);
//                            celdaE2XX.setCellFormula("SUM(G79:G82)");
//                            
//                            celdaE2XX=renglonE2XX.createCell(7);
//                            celdaE2XX.setCellStyle(celdaDetalle);
//                            celdaE2XX.setCellFormula("SUM(H79:H82)");
//            
//                            celdaE2XX=renglonE2XX.createCell(8);
//                            celdaE2XX.setCellStyle(celdaDetalle);
//                            celdaE2XX.setCellStyle(getStyleDetallePorc());
//                            celdaE2XX.setCellFormula("G83/H83");     
//                            
//                            
//                            
//                            //ISUZU
//                            r=hoja.createRow(85);
//                             for(int c=0; c<plataformaISUZULM.getColumnCount(); c++)
//                    {
//                        //hoja.setColumnWidth(i, Modelocorte.getColumnName(i).length()*450);
//                        hoja.setColumnWidth(c, plataformaISUZULM.getColumnName(c).length()*350);
//                         Cell celda=r.createCell(c);
//                         celda.setCellStyle(celdaStiloCabecera);
//                        try{
//                            celda.setCellValue(plataformaISUZULM.getColumnName(c));
//                        }catch(Exception e)
//                        {
////                            celda.setCellValue( modelo.get(ho).getValueAt(x, i).toString());
//                        }       
//                    }
//            for(int i=0; i<plataformaISUZULM.getRowCount(); i++)
//            {
//
//                   r=hoja.createRow(r.getRowNum()+1);
//                    for(int x=0; x<plataformaISUZULM.getColumnCount(); x++)
//                    {
//                      
//                                 Cell celda=r.createCell(x);
//                                  
//                                 if(x>4)
//                                  celda.setCellValue(Double.parseDouble( plataformaISUZULM.getValueAt(i,x).toString()));
//                                 
//                                 else
//                                     celda.setCellValue( plataformaISUZULM.getValueAt(i,x).toString());
//                    }
//  
//            }
//             Row renglonISUZU=hoja.createRow((plataformaISUZULM.getRowCount()+88));
//                     Cell celdaISUZU=null;
//                     celdaISUZU=renglonISUZU.createCell(0);
//                     celdaISUZU.setCellValue("TOTAL ISUZU");
//                     celdaISUZU.setCellStyle(celdaDetalle);
//                      celdaISUZU.setCellStyle(celdaStiloCabecera);
////                     celda=renglon.createCell(4);
////                     celda.setCellStyle(celdaDetalle);
////                     celda.setCellFormula("SUM(E4:E"+(modelo.getRowCount()+3)+")");
////                     celda=renglon.createCell(5);
////                     celda.setCellStyle(celdaDetalle);
////                     celda.setCellFormula("SUM(F4:F"+(modelo.getRowCount()+3)+")");
//                            celdaISUZU=renglonISUZU.createCell(6);
//                            celdaISUZU.setCellStyle(celdaDetalle);
//                            celdaISUZU.setCellFormula("SUM(G87:G90)");
//                            
//                            celdaISUZU=renglonISUZU.createCell(7);
//                            celdaISUZU.setCellStyle(celdaDetalle);
//                            celdaISUZU.setCellFormula("SUM(H87:H90)");
//            
//                            celdaISUZU=renglonISUZU.createCell(8);
//                            celdaISUZU.setCellStyle(celdaDetalle);
//                            celdaISUZU.setCellStyle(getStyleDetallePorc());
//                            celdaISUZU.setCellFormula("G91/H91");  
//                            
//                            
//                            
//                            
//                            //SERVICIOS
//                            r=hoja.createRow(93);
//                             for(int c=0; c<plataformaSERVICIOSLM.getColumnCount(); c++)
//                    {
//                        //hoja.setColumnWidth(i, Modelocorte.getColumnName(i).length()*450);
//                        hoja.setColumnWidth(c, plataformaSERVICIOSLM.getColumnName(c).length()*350);
//                         Cell celda=r.createCell(c);
//                         celda.setCellStyle(celdaStiloCabecera);
//                        try{
//                            celda.setCellValue(plataformaSERVICIOSLM.getColumnName(c));
//                        }catch(Exception e)
//                        {
////                            celda.setCellValue( modelo.get(ho).getValueAt(x, i).toString());
//                        }       
//                    }
//            for(int i=0; i<plataformaSERVICIOSLM.getRowCount(); i++)
//            {
//
//                   r=hoja.createRow(r.getRowNum()+1);
//                    for(int x=0; x<plataformaSERVICIOSLM.getColumnCount(); x++)
//                    {
//                      
//                                 Cell celda=r.createCell(x);
//                                  
//                                 if(x>4)
//                                  celda.setCellValue(Double.parseDouble( plataformaSERVICIOSLM.getValueAt(i,x).toString()));
//                                 
//                                 else
//                                     celda.setCellValue( plataformaSERVICIOSLM.getValueAt(i,x).toString());
//                    }
//  
//            }
//             Row renglonSERVICIOS=hoja.createRow((plataformaSERVICIOSLM.getRowCount()+96));
//                     Cell celdaSERVICIOS=null;
//                     celdaSERVICIOS=renglonSERVICIOS.createCell(0);
//                     celdaSERVICIOS.setCellValue("TOTAL SERVICIOS");
//                     celdaSERVICIOS.setCellStyle(celdaDetalle);
//                     celdaSERVICIOS.setCellStyle(celdaStiloCabecera);
////                     celda=renglon.createCell(4);
////                     celda.setCellStyle(celdaDetalle);
////                     celda.setCellFormula("SUM(E4:E"+(modelo.getRowCount()+3)+")");
////                     celda=renglon.createCell(5);
////                     celda.setCellStyle(celdaDetalle);
////                     celda.setCellFormula("SUM(F4:F"+(modelo.getRowCount()+3)+")");
//                            celdaSERVICIOS=renglonSERVICIOS.createCell(6);
//                            celdaSERVICIOS.setCellStyle(celdaDetalle);
//                            celdaSERVICIOS.setCellFormula("SUM(G95:G98)");
//                            
//                            celdaSERVICIOS=renglonSERVICIOS.createCell(7);
//                            celdaSERVICIOS.setCellStyle(celdaDetalle);
//                            celdaSERVICIOS.setCellFormula("SUM(H95:H98)");
//            
//                            celdaSERVICIOS=renglonSERVICIOS.createCell(8);
//                            celdaSERVICIOS.setCellStyle(celdaDetalle);
//                            celdaSERVICIOS.setCellStyle(getStyleDetallePorc());
//                            celdaSERVICIOS.setCellFormula("G99/H99"); 
//                            
//                            
//                            
//                            
//                              //CORTE LM
//                            r=hoja.createRow(101);
//                             for(int c=0; c<plataformaCORTELM.getColumnCount(); c++)
//                    {
//                        //hoja.setColumnWidth(i, Modelocorte.getColumnName(i).length()*450);
//                        hoja.setColumnWidth(c, plataformaCORTELM.getColumnName(c).length()*350);
//                         Cell celda=r.createCell(c);
//                         celda.setCellStyle(celdaStiloCabecera);
//                        try{
//                            celda.setCellValue(plataformaCORTELM.getColumnName(c));
//                        }catch(Exception e)
//                        {
////                            celda.setCellValue( modelo.get(ho).getValueAt(x, i).toString());
//                        }       
//                    }
//            for(int i=0; i<plataformaCORTELM.getRowCount(); i++)
//            {
//
//                   r=hoja.createRow(r.getRowNum()+1);
//                    for(int x=0; x<plataformaCORTELM.getColumnCount(); x++)
//                    {
//                      
//                                 Cell celda=r.createCell(x);
//                                  
//                                 if(x>4)
//                                  celda.setCellValue(Double.parseDouble( plataformaCORTELM.getValueAt(i,x).toString()));
//                                 
//                                 else
//                                     celda.setCellValue( plataformaCORTELM.getValueAt(i,x).toString());
//                    }
//  
//            }
//             Row renglonCORTE=hoja.createRow((plataformaCORTELM.getRowCount()+104));
//                     Cell celdaCORTE=null;
//                     celdaCORTE=renglonCORTE.createCell(0);
//                     celdaCORTE.setCellValue("TOTAL CORTE");
//                     celdaCORTE.setCellStyle(celdaDetalle);
//                     celdaCORTE.setCellStyle(celdaStiloCabecera);
////                     celda=renglon.createCell(4);
////                     celda.setCellStyle(celdaDetalle);
////                     celda.setCellFormula("SUM(E4:E"+(modelo.getRowCount()+3)+")");
////                     celda=renglon.createCell(5);
////                     celda.setCellStyle(celdaDetalle);
////                     celda.setCellFormula("SUM(F4:F"+(modelo.getRowCount()+3)+")");
//                            celdaCORTE=renglonCORTE.createCell(6);
//                            celdaCORTE.setCellStyle(celdaDetalle);
//                            celdaCORTE.setCellFormula("SUM(G103:G107)");
//                            
//                            celdaCORTE=renglonCORTE.createCell(7);
//                            celdaCORTE.setCellStyle(celdaDetalle);
//                            celdaCORTE.setCellFormula("SUM(H103:H107)");
//            
//                            celdaCORTE=renglonCORTE.createCell(8);
//                            celdaCORTE.setCellStyle(celdaDetalle);
//                            celdaCORTE.setCellStyle(getStyleDetallePorc());
//                            celdaCORTE.setCellFormula("G108/H108"); 
//                            
//                            
//             Row renglonTOTALLM=hoja.createRow((plataformaCORTELM.getRowCount()+107));
//                     Cell celdaTOTALLM=null;
//                     celdaTOTALLM=renglonTOTALLM.createCell(0);
//                     celdaTOTALLM.setCellValue("TOTAL PLANTA LM");
//                     celdaTOTALLM.setCellStyle(celdaDetalle);
//                     celdaTOTALLM.setCellStyle(celdaStiloCabecera);
////                     celda=renglon.createCell(4);
////                     celda.setCellStyle(celdaDetalle);
////                     celda.setCellFormula("SUM(E4:E"+(modelo.getRowCount()+3)+")");
////                     celda=renglon.createCell(5);
////                     celda.setCellStyle(celdaDetalle);
////                     celda.setCellFormula("SUM(F4:F"+(modelo.getRowCount()+3)+")");
//                            celdaTOTALLM=renglonTOTALLM.createCell(6);
//                            celdaTOTALLM.setCellStyle(celdaDetalle);
//                            celdaTOTALLM.setCellFormula("SUM(G3:G26,G32:G50,G56:G72,G79:G81,G87:G89,G95:G97,G103:G106)");
//                            
//                            celdaTOTALLM=renglonTOTALLM.createCell(7);
//                            celdaTOTALLM.setCellStyle(celdaDetalle);
//                            celdaTOTALLM.setCellFormula("SUM(H3:H26,H32:H50,H56:H72,H79:H81,H87:H89,H95:H97,H103:H106)");
//            
//                            celdaTOTALLM=renglonTOTALLM.createCell(8);
//                            celdaTOTALLM.setCellStyle(celdaDetalle);
//                            celdaTOTALLM.setCellStyle(getStyleDetallePorc());
//                            celdaTOTALLM.setCellFormula("G111/H111");                
//                            
            // PESTAñA PLATAFORMA GMT 610 GML
//         hoja=libro.createSheet("PLATAFORMAS GML");
//             r=hoja.createRow(1);
//             
//             //GMT610 GML
//            for(int c=0; c<plataformasGMTGML.getColumnCount(); c++)
//                    {
//                        //hoja.setColumnWidth(i, Modelocorte.getColumnName(i).length()*450);
//                        hoja.setColumnWidth(c, plataformasGMTGML.getColumnName(c).length()*350);
//                         Cell celda=r.createCell(c);
//                         celda.setCellStyle(celdaStiloCabecera);
//                        try{
//                            celda.setCellValue(plataformasGMTGML.getColumnName(c));
//                        }catch(Exception e)
//                        {
////                            celda.setCellValue( modelo.get(ho).getValueAt(x, i).toString());
//                        }       
//                    }
//            for(int i=0; i<plataformasGMTGML.getRowCount(); i++)
//            {
//
//                   r=hoja.createRow(r.getRowNum()+1);
//                    for(int x=0; x<plataformasGMTGML.getColumnCount(); x++)
//                    {
//                      
//                                 Cell celda=r.createCell(x);
//                                  
//                                 if(x>4)
//                                  celda.setCellValue(Double.parseDouble( plataformasGMTGML.getValueAt(i,x).toString()));
//                                 
//                                 else
//                                     celda.setCellValue( plataformasGMTGML.getValueAt(i,x).toString());
//                    }
//  
//            }
//             Row renglonGMTGML=hoja.createRow((plataformasGMTGML.getRowCount()+4));
//                     Cell celdaGMTGML=null;
//                     celdaGMTGML=renglonGMTGML.createCell(0);
//                     celdaGMTGML.setCellValue("TOTAL GMT 610");
//                     celdaGMTGML.setCellStyle(celdaDetalle);
//                     celdaGMTGML.setCellStyle(celdaStiloCabecera);
////                     celda=renglon.createCell(4);
////                     celda.setCellStyle(celdaDetalle);
////                     celda.setCellFormula("SUM(E4:E"+(modelo.getRowCount()+3)+")");
////                     celda=renglon.createCell(5);
////                     celda.setCellStyle(celdaDetalle);
////                     celda.setCellFormula("SUM(F4:F"+(modelo.getRowCount()+3)+")");
//                   celdaGMTGML=renglonGMTGML.createCell(6);
//                            celdaGMTGML.setCellStyle(celdaDetalle);
//                            celdaGMTGML.setCellFormula("SUM(G3:G17)");
//                            
//                             celdaGMTGML=renglonGMTGML.createCell(7);
//                            celdaGMTGML.setCellStyle(celdaDetalle);
//                            celdaGMTGML.setCellFormula("SUM(H3:H17)");
//            
//                               celdaGMTGML=renglonGMTGML.createCell(8);
//                            celdaGMTGML.setCellStyle(celdaDetalle);
//                            celdaGMTGML.setCellStyle(getStyleDetallePorc());
//                            celdaGMTGML.setCellFormula("G18/H18");              
//                            
//
//                              //K2XX GML
//                             r=hoja.createRow(20);
//            for(int c=0; c<plataformasK2XXGML.getColumnCount(); c++)
//                    {
//                        //hoja.setColumnWidth(i, Modelocorte.getColumnName(i).length()*450);
//                        hoja.setColumnWidth(c, plataformasK2XXGML.getColumnName(c).length()*350);
//                         Cell celda=r.createCell(c);
//                         celda.setCellStyle(celdaStiloCabecera);
//                        try{
//                            celda.setCellValue(plataformasK2XXGML.getColumnName(c));
//                        }catch(Exception e)
//                        {
////                            celda.setCellValue( modelo.get(ho).getValueAt(x, i).toString());
//                        }       
//                    }
//            for(int i=0; i<plataformasK2XXGML.getRowCount(); i++)
//            {
//
//                   r=hoja.createRow(r.getRowNum()+1);
//                    for(int x=0; x<plataformasK2XXGML.getColumnCount(); x++)
//                    {
//                      
//                                 Cell celda=r.createCell(x);
//                                  
//                                 if(x>4)
//                                  celda.setCellValue(Double.parseDouble( plataformasK2XXGML.getValueAt(i,x).toString()));
//                                 
//                                 else
//                                     celda.setCellValue( plataformasK2XXGML.getValueAt(i,x).toString());
//                    }
//  
//            }
//             Row renglonK2XXGML=hoja.createRow((plataformasK2XXGML.getRowCount()+23));
//                     Cell celdaK2XXGML=null;
//                     celdaK2XXGML=renglonK2XXGML.createCell(0);
//                     celdaK2XXGML.setCellValue("TOTAL K2XX");
//                     celdaK2XXGML.setCellStyle(celdaDetalle);
//                     celdaK2XXGML.setCellStyle(celdaStiloCabecera);
////                     celda=renglon.createCell(4);
////                     celda.setCellStyle(celdaDetalle);
////                     celda.setCellFormula("SUM(E4:E"+(modelo.getRowCount()+3)+")");
////                     celda=renglon.createCell(5);
////                     celda.setCellStyle(celdaDetalle);
////                     celda.setCellFormula("SUM(F4:F"+(modelo.getRowCount()+3)+")");
//                   celdaK2XXGML=renglonK2XXGML.createCell(6);
//                            celdaK2XXGML.setCellStyle(celdaDetalle);
//                            celdaK2XXGML.setCellFormula("SUM(G22:G32)");
//                            
//                             celdaK2XXGML=renglonK2XXGML.createCell(7);
//                            celdaK2XXGML.setCellStyle(celdaDetalle);
//                            celdaK2XXGML.setCellFormula("SUM(H22:H32)");
//            
//                               celdaK2XXGML=renglonK2XXGML.createCell(8);
//                            celdaK2XXGML.setCellStyle(celdaDetalle);
//                            celdaK2XXGML.setCellStyle(getStyleDetallePorc());
//                            celdaK2XXGML.setCellFormula("G34/H34"); 
//                            
//                            
//                            
//                     //T1XX GML
//                             r=hoja.createRow(37);
//            for(int c=0; c<plataformasT1XXGML.getColumnCount(); c++)
//                    {
//                        //hoja.setColumnWidth(i, Modelocorte.getColumnName(i).length()*450);
//                        hoja.setColumnWidth(c, plataformasT1XXGML.getColumnName(c).length()*350);
//                         Cell celda=r.createCell(c);
//                         celda.setCellStyle(celdaStiloCabecera);
//                        try{
//                            celda.setCellValue(plataformasT1XXGML.getColumnName(c));
//                        }catch(Exception e)
//                        {
////                            celda.setCellValue( modelo.get(ho).getValueAt(x, i).toString());
//                        }       
//                    }
//            for(int i=0; i<plataformasT1XXGML.getRowCount(); i++)
//            {
//
//                   r=hoja.createRow(r.getRowNum()+1);
//                    for(int x=0; x<plataformasT1XXGML.getColumnCount(); x++)
//                    {
//                      
//                                 Cell celda=r.createCell(x);
//                                  
//                                 if(x>4)
//                                  celda.setCellValue(Double.parseDouble( plataformasT1XXGML.getValueAt(i,x).toString()));
//                                 
//                                 else
//                                     celda.setCellValue( plataformasT1XXGML.getValueAt(i,x).toString());
//                    }
//  
//            }
//             Row renglonT1GML=hoja.createRow((plataformasT1XXGML.getRowCount()+40));
//                     Cell celdaT1GML=null;
//                     celdaT1GML=renglonT1GML.createCell(0);
//                     celdaT1GML.setCellValue("TOTAL T1XX");
//                     celdaT1GML.setCellStyle(celdaDetalle);
//                     celdaT1GML.setCellStyle(celdaStiloCabecera);
////                     celda=renglon.createCell(4);
////                     celda.setCellStyle(celdaDetalle);
////                     celda.setCellFormula("SUM(E4:E"+(modelo.getRowCount()+3)+")");
////                     celda=renglon.createCell(5);
////                     celda.setCellStyle(celdaDetalle);
////                     celda.setCellFormula("SUM(F4:F"+(modelo.getRowCount()+3)+")");
//                   celdaT1GML=renglonT1GML.createCell(6);
//                            celdaT1GML.setCellStyle(celdaDetalle);
//                            celdaT1GML.setCellFormula("SUM(G39:G56)");
//                            
//                             celdaT1GML=renglonT1GML.createCell(7);
//                            celdaT1GML.setCellStyle(celdaDetalle);
//                            celdaT1GML.setCellFormula("SUM(H39:H56)");
//            
//                               celdaT1GML=renglonT1GML.createCell(8);
//                            celdaT1GML.setCellStyle(celdaDetalle);
//                            celdaT1GML.setCellStyle(getStyleDetallePorc());
//                            celdaT1GML.setCellFormula("G58/H58");        
//                            
//                            
//                            //corte gml
//                        
//                            
//                                          r=hoja.createRow(62);
//            for(int c=0; c<plataformascorteGML.getColumnCount(); c++)
//                    {
//                        //hoja.setColumnWidth(i, Modelocorte.getColumnName(i).length()*450);
//                        hoja.setColumnWidth(c, plataformascorteGML.getColumnName(c).length()*350);
//                         Cell celda=r.createCell(c);
//                         celda.setCellStyle(celdaStiloCabecera);
//                        try{
//                            celda.setCellValue(plataformascorteGML.getColumnName(c));
//                        }catch(Exception e)
//                        {
////                            celda.setCellValue( modelo.get(ho).getValueAt(x, i).toString());
//                        }       
//                    }
//            for(int i=0; i<plataformascorteGML.getRowCount(); i++)
//            {
//
//                   r=hoja.createRow(r.getRowNum()+1);
//                    for(int x=0; x<plataformascorteGML.getColumnCount(); x++)
//                    {
//                      
//                                 Cell celda=r.createCell(x);
//                                  
//                                 if(x>4)
//                                  celda.setCellValue(Double.parseDouble( plataformascorteGML.getValueAt(i,x).toString()));
//                                 
//                                 else
//                                     celda.setCellValue( plataformascorteGML.getValueAt(i,x).toString());
//                    }
//  
//            }
//             Row rengloncorteGML=hoja.createRow((plataformascorteGML.getRowCount()+63));
//                     Cell celdacorteGML=null;
//                     celdacorteGML=rengloncorteGML.createCell(0);
//                     celdacorteGML.setCellValue("TOTAL CORTE");
//                     celdacorteGML.setCellStyle(celdaDetalle);
//                     celdacorteGML.setCellStyle(celdaStiloCabecera);
////                     celda=renglon.createCell(4);
////                     celda.setCellStyle(celdaDetalle);
////                     celda.setCellFormula("SUM(E4:E"+(modelo.getRowCount()+3)+")");
////                     celda=renglon.createCell(5);
////                     celda.setCellStyle(celdaDetalle);
////                     celda.setCellFormula("SUM(F4:F"+(modelo.getRowCount()+3)+")");
//                   celdacorteGML=rengloncorteGML.createCell(6);
//                            celdacorteGML.setCellStyle(celdaDetalle);
//                            celdacorteGML.setCellFormula("SUM(G64:G65)");
//                            
//                            celdacorteGML=rengloncorteGML.createCell(7);
//                            celdacorteGML.setCellStyle(celdaDetalle);
//                            celdacorteGML.setCellFormula("SUM(H64:H65)");
//            
//                             celdacorteGML=rengloncorteGML.createCell(8);
//                            celdacorteGML.setCellStyle(celdaDetalle);
//                            celdacorteGML.setCellStyle(getStyleDetallePorc());
//                            celdacorteGML.setCellFormula("G66/H66"); 
//                               
//                            
//                            
//                            
//                            //servicis gml
//                            
//                            
//                                 r=hoja.createRow(69);
//            for(int c=0; c<plataformasserviciosGML.getColumnCount(); c++)
//                    {
//                        //hoja.setColumnWidth(i, Modelocorte.getColumnName(i).length()*450);
//                        hoja.setColumnWidth(c, plataformasserviciosGML.getColumnName(c).length()*350);
//                         Cell celda=r.createCell(c);
//                         celda.setCellStyle(celdaStiloCabecera);
//                        try{
//                            celda.setCellValue(plataformasserviciosGML.getColumnName(c));
//                        }catch(Exception e)
//                        {
////                            celda.setCellValue( modelo.get(ho).getValueAt(x, i).toString());
//                        }       
//                    }
//            for(int i=0; i<plataformasserviciosGML.getRowCount(); i++)
//            {
//
//                   r=hoja.createRow(r.getRowNum()+1);
//                    for(int x=0; x<plataformasserviciosGML.getColumnCount(); x++)
//                    {
//                      
//                                 Cell celda=r.createCell(x);
//                                  
//                                 if(x>4)
//                                  celda.setCellValue(Double.parseDouble( plataformasserviciosGML.getValueAt(i,x).toString()));
//                                 
//                                 else
//                                     celda.setCellValue( plataformasserviciosGML.getValueAt(i,x).toString());
//                    }
//  
//            }
            /////suma total turno
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
                celda.setCellValue(GetFecha(new Date()));

                renglon = hoja.createRow(2);
                for (int i = 2; i < modeloTotal.getColumnCount() + 2; i++) {
                    hoja.setColumnWidth(i, modeloTotal.getColumnName(i - 2).length() * 500);
                    hoja.setColumnWidth(2, 8850);
                    // hoja.autoSizeColumn(i);
                    celda = renglon.createCell(i);
                    celda.setCellValue(modeloTotal.getColumnName(i - 2));
                    celda.setCellStyle(celdaStiloCabecera);

                    celda = renglon.createCell(modeloTotal.getColumnCount() + 2);
                    celda.setCellStyle(celdaStiloCabecera);
                    celda.setCellValue("HC TOTAL ");
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

            //AGREGAMOS AL FINAL DE LA LINEA UN SUM CON LOS TOTALES HRS EMB. HRS. PAGADAS,hc
            Row renglon = hoja.createRow((modeloTotal.getRowCount() + 9));
            Cell celda = null;

//                       celda=renglon.createCell(2);
//                       celda.setCellValue("TOTAL PLANTA");
//                       celda.setCellStyle(celdaDetalle);
//                       celda=renglon.createCell(3);
//                       celda.setCellStyle(celdaDetalle);
//
//                       for (int i = 2; i < modeloTotal.getColumnCount(); i++) {
//                            celda=renglon.createCell(i+2);
//                            celda.setCellStyle(celdaDetalle);
//                             // celda.setCellFormula("SUM(F27/G27)");
//                            celda.setCellFormula("sum("+getColumnName(i+2)+4+":"+getColumnName(i+2)+18+")");
//                            
//                                                    }
            //eficiencia columna H ///
            for (int i = 0; i < modeloTotal.getRowCount(); i++) {
                renglon = hoja.getRow(i + 3);
                celda = renglon.createCell(7);
                celda.setCellStyle(getStyleDetallePorc());
                celda.setCellFormula("sum(F" + (i + 4) + "/G" + (i + 4) + ")");

            }

            //HC TOTAL COLUMNA J
            for (int i = 0; i < modeloTotal.getRowCount(); i++) {
                renglon = hoja.getRow(i + 3);
                // celda=renglon.createCell(9);
                celda = renglon.createCell(modeloTotal.getColumnCount() + 2);
                celda.setCellStyle(celdaDetalle);
                celda.setCellFormula("sum(E" + (i + 4) + "+I" + (i + 4) + ")");

            }

            Row renglon3 = hoja.createRow((modeloTotal.getRowCount() + 5));
            Cell celda3 = null;
            celda3 = renglon3.createCell(2);
            celda3.setCellValue("TOTAL PLANTA MOCHIS");
            celda3.setCellStyle(celdaDetalle);
            celda3 = renglon.createCell(3);
            celda3.setCellStyle(celdaDetalle);

            //    for (int i = 2; i < modeloTotal.getColumnCount(); i++) {
            celda3 = renglon3.createCell(4);
            celda3.setCellStyle(celdaDetalle);

            celda3.setCellFormula("SUM(E14:E15)");

            celda3 = renglon3.createCell(5);
            celda3.setCellStyle(celdaDetalle);

            celda3.setCellFormula("SUM(F14:F15)");

            celda3 = renglon3.createCell(6);
            celda3.setCellStyle(celdaDetalle);

            celda3.setCellFormula("SUM(G14:G15)");

            celda3 = renglon3.createCell(8);
            celda3.setCellStyle(celdaDetalle);

            celda3.setCellFormula("SUM(I14:I15)");
            //  }

            celda3 = renglon3.createCell(7);
            celda3.setCellStyle(getStyleDetallePorc());
            celda3.setCellFormula("SUM(F16/G16)");

            celda3 = renglon3.createCell(9);
            celda3.setCellStyle(celdaDetalle);

            celda3.setCellFormula("SUM(E16+I16)");

            Row renglon5 = hoja.createRow((modeloTotal.getRowCount() + 3));
            Cell celda5 = null;
            celda5 = renglon5.createCell(2);
            celda5.setCellStyle(celdaDetalle);
            celda5.setCellValue("TOTAL PLANTA MOCHIS TURNO A");
            celda5 = renglon5.createCell(3);
            celda5.setCellStyle(celdaDetalle);

            //    for (int i = 2; i < modeloTotal.getColumnCount(); i++) {
            celda5 = renglon5.createCell(4);
            celda5.setCellStyle(celdaDetalle);

            celda5.setCellFormula("SUM(E4,E5,E6,E7,E12)");

            celda5 = renglon5.createCell(5);
            celda5.setCellStyle(celdaDetalle);

            celda5.setCellFormula("SUM(F4,F5,F6,F7,F12)");

            celda5 = renglon5.createCell(6);
            celda5.setCellStyle(celdaDetalle);

            celda5.setCellFormula("SUM(G4,G5,G6,G7,G12)");

            celda5 = renglon5.createCell(8);
            celda5.setCellStyle(celdaDetalle);

            celda5.setCellFormula("SUM(I4,I5,I6,I7,I12)");
            //  }

            celda5 = renglon5.createCell(7);
            celda5.setCellStyle(getStyleDetallePorc());
            celda5.setCellFormula("SUM(F14/G14)");
            //////
            celda5 = renglon5.createCell(9);
            celda5.setCellStyle(celdaDetalle);

            celda5.setCellFormula("SUM(E14+I14)");

            Row renglon6 = hoja.createRow((modeloTotal.getRowCount() + 4));
            Cell celda6 = null;
            celda6 = renglon6.createCell(2);
            celda6.setCellValue("TOTAL PLANTA MOCHIS TURNO B");
            celda6.setCellStyle(celdaDetalle);
            celda6 = renglon6.createCell(3);
            celda6.setCellStyle(celdaDetalle);

            //    for (int i = 2; i < modeloTotal.getColumnCount(); i++) {
            celda6 = renglon6.createCell(4);
            celda6.setCellStyle(celdaDetalle);

            celda6.setCellFormula("SUM(E8,E9,E10,E11,E13)");

            celda6 = renglon6.createCell(5);
            celda6.setCellStyle(celdaDetalle);

            celda6.setCellFormula("SUM(F8,F9,F10,F11,F13)");

            celda6 = renglon6.createCell(6);
            celda6.setCellStyle(celdaDetalle);

            celda6.setCellFormula("SUM(G8,G9,G10,G11,G13)");

            celda6 = renglon6.createCell(8);
            celda6.setCellStyle(celdaDetalle);

            celda6.setCellFormula("SUM(I8,I9,I10,I11,I13)");

            celda6 = renglon6.createCell(7);
            celda6.setCellStyle(getStyleDetallePorc());
            celda6.setCellFormula("SUM(F15/G15)");

            celda6 = renglon6.createCell(9);
            celda6.setCellStyle(celdaDetalle);
            celda6.setCellFormula("SUM(E15+I15)");

            //////
//                            Row renglon7=hoja.createRow((modeloTotal.getRowCount()+6));
//                      Cell celda7=null;
//                       celda7=renglon7.createCell(2);
//                       celda7.setCellValue("TOTAL PLANTA GUAMUCHIL TURNO A");
//                       celda7.setCellStyle(celdaDetalle);
//                       celda7=renglon7.createCell(3);
//                       celda7.setCellStyle(celdaDetalle);
//                       
//                   //    for (int i = 2; i < modeloTotal.getColumnCount(); i++) {
//                        celda7=renglon7.createCell(4);
//                            celda7.setCellStyle(celdaDetalle);
//                     
//                            celda7.setCellFormula("E8");
//                       
//                       celda7=renglon7.createCell(5);
//                            celda7.setCellStyle(celdaDetalle);
//                     
//                            celda7.setCellFormula("F8");
//                             
//                            
//                             celda7=renglon7.createCell(6);
//                            celda7.setCellStyle(celdaDetalle);
//                     
//                            celda7.setCellFormula("G8");
//                            
//                             celda7=renglon7.createCell(8);
//                            celda7.setCellStyle(celdaDetalle);
//                     
//                            celda7.setCellFormula("I8");
//                     //  }
//                       celda7=renglon7.createCell(9);
//                            celda7.setCellStyle(celdaDetalle);                    
//                            celda7.setCellFormula("SUM(E20+I20)");
//                            
//                            
//                             celda7=renglon7.createCell(7);
//                            celda7.setCellStyle(getStyleDetallePorc());
//                            celda7.setCellFormula("SUM(F20/G20)");
//                            
//                  
//                            //////
//                            Row renglon8=hoja.createRow((modeloTotal.getRowCount()+7));
//                      Cell celda8=null;
//                       celda8=renglon8.createCell(2);
//                       celda8.setCellValue("TOTAL PLANTA GUAMUCHIL TURNO B");
//                       celda8.setCellStyle(celdaDetalle);
//                       celda8=renglon8.createCell(3);
//                       celda8.setCellStyle(celdaDetalle);
//                       
//                   //    for (int i = 2; i < modeloTotal.getColumnCount(); i++) {
//                        celda8=renglon8.createCell(4);
//                            celda8.setCellStyle(celdaDetalle);
//                     
//                            celda8.setCellFormula("E13");
//                       
//                       celda8=renglon8.createCell(5);
//                            celda8.setCellStyle(celdaDetalle);
//                     
//                            celda8.setCellFormula("F13");
//                             
//                            
//                             celda8=renglon8.createCell(6);
//                            celda8.setCellStyle(celdaDetalle);
//                     
//                            celda8.setCellFormula("G13");
//                            
//                             celda8=renglon8.createCell(8);
//                            celda8.setCellStyle(celdaDetalle);
//                     
//                            celda8.setCellFormula("I13");
//                     //  }
//                      celda8=renglon8.createCell(9);
//                            celda8.setCellStyle(celdaDetalle);                    
//                            celda8.setCellFormula("SUM(E21+I21)");
//                            
//                             celda8=renglon8.createCell(7);
//                            celda8.setCellStyle(getStyleDetallePorc());
//                            celda8.setCellFormula("SUM(F21/G21)");
//                            
//                  
////                                
////                    for (int i = 14; i < modeloTotal.getRowCount()+1; i++) {
////                      renglon=hoja.getRow(i+4);
////                      celda=renglon.createCell(7);
////                      celda.setCellStyle(getStyleDetallePorc());
////                      celda.setCellFormula("+sum(F"+(i+16)+"/G"+(i+16)+")");
////                    }
//////                  
////                    
//                    
//                    
//                     Row renglon4=hoja.createRow((modeloTotal.getRowCount()+8));
//                      Cell celda4=null;
//                       celda4=renglon4.createCell(2);
//                       celda4.setCellValue("TOTAL PLANTA GUAMUCHIL");
//                       celda4.setCellStyle(celdaDetalle);
//                       celda4=renglon4.createCell(3);
//                       celda4.setCellStyle(celdaDetalle);
////                       
//                      // for (int i = 3; i < modeloTotal.getColumnCount(); i++) {
//                            celda4=renglon4.createCell(4);
//                            celda4.setCellStyle(celdaDetalle);
//                            celda4.setCellFormula("SUM(E20,E21)");
//                           
//                       
//                            celda4=renglon4.createCell(5);
//                            celda4.setCellStyle(celdaDetalle);                   
//                            celda4.setCellFormula("SUM(F20,F21)");
//                             
//                            
//                            celda4=renglon4.createCell(6);
//                            celda4.setCellStyle(celdaDetalle);
//                            celda4.setCellFormula("SUM(G20,G21)");
//                            
//                            celda4=renglon4.createCell(7);
//                            celda4.setCellStyle(getStyleDetallePorc());
//                            celda4.setCellFormula("SUM(F22/G22)");
//           
//                 
//                            celda4=renglon4.createCell(8);
//                            celda4.setCellStyle(celdaDetalle);
//                            celda4.setCellFormula("SUM(I20,I21)");
//                            
//                            celda4=renglon4.createCell(9);
//                            celda4.setCellStyle(celdaDetalle);
//                            celda4.setCellFormula("SUM(E22+I22)");
//                            
//                            
//                             Row renglonP=hoja.createRow((modeloTotal.getRowCount()+9));
//                      Cell celdaP=null;
//                       celdaP=renglonP.createCell(2);
//                       celdaP.setCellValue("TOTAL PLANTA");
//                       celdaP.setCellStyle(celdaDetalle);
//                       celdaP=renglonP.createCell(3);
//                       celdaP.setCellStyle(celdaDetalle);
////                       
//                      // for (int i = 3; i < modeloTotal.getColumnCount(); i++) {
//                            celdaP=renglonP.createCell(4);
//                            celdaP.setCellStyle(celdaDetalle);
//                            celdaP.setCellFormula("SUM(E19,E22)");
//                           
//                       
//                            celdaP=renglonP.createCell(5);
//                            celdaP.setCellStyle(celdaDetalle);                   
//                            celdaP.setCellFormula("SUM(F19,F22)");
//                             
//                            
//                            celdaP=renglonP.createCell(6);
//                            celdaP.setCellStyle(celdaDetalle);
//                            celdaP.setCellFormula("SUM(G19,G22)");
//                            
//                            celdaP=renglonP.createCell(7);
//                            celdaP.setCellStyle(getStyleDetallePorc());
//                            celdaP.setCellFormula("F23/G23");
//           
//                 
//                            celdaP=renglonP.createCell(8);
//                            celdaP.setCellStyle(celdaDetalle);
//                            celdaP.setCellFormula("SUM(I19,I22)");
//                            
//                             celdaP=renglonP.createCell(9);
//                            celdaP.setCellStyle(celdaDetalle);
//                            celdaP.setCellFormula("SUM(E23+I23)");
            //}
//                    hoja = libro.createSheet("CADENA_TURNO");
//                     if(modeloTotal2.getRowCount()>0)
//            {
//                Row renglon2=hoja.createRow(1);
//                hoja.addMergedRegion(new CellRangeAddress(1,1, 1,6));
//                 Cell celda2=renglon2.createCell(1);
//                    celda2.setCellValue("REPORTE TOTAL CADENA TURNO");
//                    celda2.setCellStyle(celdaStiloCabecera);
//                renglon2=hoja.createRow(0);
//                celda2=renglon2.createCell(modeloTotal2.getColumnCount());
//                celda2.setCellValue("Fecha: ");
//                celda2=renglon2.createCell(modeloTotal2.getColumnCount()+1);
//                celda2.setCellValue(GetFecha(new Date()));
//                                        
//                 renglon2=hoja.createRow(2);
//                for(int i=2; i<modeloTotal2.getColumnCount()+2; i++)
//                {
//                   hoja.setColumnWidth(i, modeloTotal2.getColumnName(i-2).length()*500);
//                   // hoja.autoSizeColumn(i);
//                    celda2=renglon2.createCell(i);
//                    celda2.setCellValue(modeloTotal2.getColumnName(i-2));
//                    celda2.setCellStyle(celdaStiloCabecera);
//                }
//            }
//            for(int x=0; x<modeloTotal2.getRowCount(); x++)
//            {
//                     Row renglon2=hoja.createRow((x+3));
//
//                     Cell celda2=null;
//                   for(int i=2; i<modeloTotal2.getColumnCount()+2; i++)
//                    {
//                        celda2 =renglon2.createCell(i);
//                        try{
//                            celda2.setCellValue(Double.parseDouble( modeloTotal2.getValueAt(x, i-2).toString()));
//                        }catch(Exception e)
//                        {
//                            celda2.setCellValue( modeloTotal2.getValueAt(x, i-2).toString());
//                        }
//
//                       celda2.setCellStyle(celdaDetalle);
//                    }
//            }
//            
//           
////                AGREGAR RENGLON EN LA LIBRETA
//                      Row renglon2=hoja.createRow((modeloTotal2.getRowCount()+3));
//                      Cell celda2=null;
//                       celda2=renglon2.createCell(2);
//                       celda2.setCellValue("TOTAL PLANTA");
//                       celda2.setCellStyle(celdaDetalle);
//                       celda2=renglon2.createCell(3);
//                       celda2.setCellStyle(celdaDetalle);
//
//                       for (int i = 3; i < modeloTotal2.getColumnCount(); i++) {
//                            celda2=renglon2.createCell(i);
//                            celda2.setCellStyle(celdaDetalle);
//                            celda2.setCellFormula("sum("+getColumnName(i)+4+":"+getColumnName(i)+9+")");
//                       }
//                      
//                    for (int i = 0; i < modeloTotal2.getRowCount()+1; i++) {
//                      renglon2=hoja.getRow(i+3);
//                      celda2=renglon2.createCell(12);
//                    celda2.setCellStyle(getStyleDetallePorc());
//                      celda2.setCellFormula("g"+(i+4)+"/j"+(i+4));
//                      
//                      celda2=renglon2.createCell(13);
//                      celda2.setCellStyle(getStyleDetallePorc());
//                      celda2.setCellFormula("h"+(i+4)+"/k"+(i+4));
//                      
//                         celda2=renglon2.createCell(14);
//                      celda2.setCellStyle(getStyleDetallePorc());
//                      celda2.setCellFormula("i"+(i+4)+"/l"+(i+4));
//                        
//                      celda2=renglon2.createCell(15);
//                      celda2.setCellStyle(getStyleDetallePorc());
//                      celda2.setCellFormula("sum(G"+(i+4)+":h"+(i+4)+":i"+(i+4)+")/sum(j"+(i+4)+":k"+(i+4)+":L"+(i+4)+")");
//                    }
//                     r=hoja.getRow(6);
//                    Cell c=r.getCell(15);
//                    c.setCellStyle(getStyleDetallePorc());
//                     r=hoja.getRow(7);
//                    c=r.getCell(15);
//                    c.setCellStyle(getStyleDetallePorc());
            libro.write(archivo);
            /*Cerramos el flujo de datos*/
            archivo.close();
            /*Y abrimos el archivo con la clase Desktop*/
            Desktop.getDesktop().open(archivoXLS);
            //aqui cahcar el mensaje que da el pedo este
        } catch (FormulaParseException | NumberFormatException | IOException ex) {
            JOptionPane.showMessageDialog(null, "YA ESTA ABIERTO EL REPORTE CON EL MISMO NOMBRE");
            JOptionPane.showMessageDialog(null, "CREA CARPETA C:\\REPORTES HC");
            System.out.println(ex.toString() + String.valueOf(ho) + "aqui fue el pedo2");

        } finally {
            try {
                archivo.close();
            } catch (IOException ex) {
                Logger.getLogger(ExcelEficiencia.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    //METODO QUE REGRESA LA FECHA EN FORMATO MYSQL 
//       public void GetExceltotalPlanta(DefaultTableModel modelo, String Nombre) throws IOException{
//       FileOutputStream archivo = null;
//        try {
//            /*La ruta donde se creará el archivo*/
//            String rutaArchivo = System.getProperty("user.home")+"/desktop/Reporte Eficiencia "+Nombre+".xls";
//            /*Se crea el objeto de tipo File con la ruta del archivo*/
//            File archivoXLS = new File(rutaArchivo);
//            libro = new HSSFWorkbook();
//            archivo = new FileOutputStream(archivoXLS);
//            hoja = libro.createSheet("Reporte");
//            getTituloStyle();
//            getStyleDetalle();
//            if(modelo.getRowCount()>0)
//            {
//                Row renglon=hoja.createRow(1);
//                hoja.addMergedRegion(new CellRangeAddress(1,1, 1,6));
//                 Cell celda=renglon.createCell(1);
//                    celda.setCellValue("REPORTE DE EFICIENCIAaaa");
//                    celda.setCellStyle(celdaStiloCabecera);
//                renglon=hoja.createRow(0);
//                celda=renglon.createCell(modelo.getColumnCount());
//                celda.setCellValue("Fecha: ");
//                celda=renglon.createCell(modelo.getColumnCount()+1);
//                celda.setCellValue(GetFecha(new Date()));
//                                        
//                 renglon=hoja.createRow(2);
//                for(int i=2; i<modelo.getColumnCount()+2; i++)
//                {
//                   hoja.setColumnWidth(i, modelo.getColumnName(i-2).length()*500);
//                   // hoja.autoSizeColumn(i);
//                    celda=renglon.createCell(i);
//                    celda.setCellValue(modelo.getColumnName(i-2));
//                    celda.setCellStyle(celdaStiloCabecera);
//                }
//            }
//            for(int x=0; x<modelo.getRowCount(); x++)
//            {
//                     Row renglon=hoja.createRow((x+3));
//
//                     Cell celda=null;
//                   for(int i=2; i<modelo.getColumnCount()+2; i++)
//                    {
//                        celda =renglon.createCell(i);
//                        try{
//                            celda.setCellValue(Double.parseDouble( modelo.getValueAt(x, i-2).toString()));
//                        }catch(Exception e)
//                        {
//                            celda.setCellValue( modelo.getValueAt(x, i-2).toString());
//                        }
//
//                       celda.setCellStyle(celdaDetalle);
//                    }
//            }
//            
//           
////                AGREGAR RENGLON EN LA LIBRETA
//                      Row renglon=hoja.createRow((modelo.getRowCount()+3));
//                      Cell celda=null;
//                       celda=renglon.createCell(2);
//                       celda.setCellValue("TOTAL PLANTAa");
//                       celda.setCellStyle(celdaDetalle);
//                       celda=renglon.createCell(3);
//                       celda.setCellStyle(celdaDetalle);
//
//                       for (int i = 3; i < modelo.getColumnCount(); i++) {
//                            celda=renglon.createCell(i);
//                            celda.setCellStyle(celdaDetalle);
//                     
//                            celda.setCellFormula("sum("+getColumnName(i)+4+":"+getColumnName(i)+8+")");
//                       }
//                      
//                    for (int i = 0; i < modelo.getRowCount()+1; i++) {
//                      renglon=hoja.getRow(i+3);
//                      celda=renglon.createCell(9);
//                      celda.setCellStyle(getStyleDetallePorc());
//                      celda.setCellFormula("F"+(i+4)+"/H"+(i+4));
//                      
//                      celda=renglon.createCell(10);
//                      celda.setCellStyle(getStyleDetallePorc());
//                      celda.setCellFormula("G"+(i+4)+"/I"+(i+4));
//                        
//                      celda=renglon.createCell(11);
//                      celda.setCellStyle(getStyleDetallePorc());
//                      celda.setCellFormula("sum(F"+(i+4)+":G"+(i+4)+")/sum(H"+(i+4)+":I"+(i+4)+")");
//                    }
//// 
//               //
//            
//        libro.write(archivo);
//        /*Cerramos el flujo de datos*/
//        archivo.close();
//        /*Y abrimos el archivo con la clase Desktop*/
//        Desktop.getDesktop().open(archivoXLS);
//        } catch (FormulaParseException | IOException ex) {
//           System.out.println(ex.toString()+"aqui fue el pedo 3");
//        } finally {
//            try {
//                archivo.close();
//            } catch (IOException ex) {
//                System.out.println(ex.toString()+"aqui fue el pedo 4");
//            }
//        }
//    }

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
                celda.setCellValue(GetFecha(new Date()));

                renglon = hoja.createRow(2);
                for (int i = 2; i < modelo.getColumnCount() + 2; i++) {
                    hoja.setColumnWidth(i, modelo.getColumnName(i - 2).length() * 500);
                    // hoja.autoSizeColumn(i);
                    celda = renglon.createCell(i);
                    celda.setCellValue(modelo.getColumnName(i - 2));
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
                    } catch (NumberFormatException e) {
                        celda.setCellValue(modelo.getValueAt(x, i - 2).toString());
                    }

                    celda.setCellStyle(celdaDetalle);
                }
            }

            //AGREGAMOS AL FINAL DE LA LINEA UN SUM CON LOS TOTALES HRS EMB. HRS. PAGADAS
            Row renglon = hoja.createRow((modelo.getRowCount() + 3));
            Cell celda = null;
            celda = renglon.createCell(2);
            celda.setCellValue("TOTAL PLANTAa");
            celda.setCellStyle(celdaDetalle);
            celda = renglon.createCell(3);
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
                celda.setCellFormula("sum(F" + (i + 4) + "/G" + (i + 4) + ")");
            }
// 
            //

            libro.write(archivo);
            /*Cerramos el flujo de datos*/
            archivo.close();
            /*Y abrimos el archivo con la clase Desktop*/
            Desktop.getDesktop().open(archivoXLS);
        } catch (FormulaParseException | IOException ex) {
            System.out.println(ex.toString() + "aqui fue el pedo 5");
        } finally {
            try {
                archivo.close();
            } catch (IOException ex) {
                System.out.println(ex.toString() + "aqui fue el pedo 6");
            }
        }
    }

    String GetFecha(java.util.Date fecha) {
        return new SimpleDateFormat("dd-MM-yyyy").format(fecha);
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
