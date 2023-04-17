/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Reportes;

import Clases.Cadena;
import Clases.DatosGSD;
import Clases.PlatCodLinea;
import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import manufactura.Principal;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;

public class ExcelImportGSD {

    File ArchivoXLS;
    public String Cadena;
    HSSFSheet hoja;
    HSSFWorkbook libro;
    int Renglon = 0;
    int columna = 0;
    public static ArrayList<DatosGSD> listadatosGSD;

    public ExcelImportGSD(final String Cadena) {
        this.Cadena = Cadena;
        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
                ObtenerListaGSD(Cadena);
            }
        });
        t.start();

    }

    public ExcelImportGSD(ArrayList<Cadena> Cadena, Cadena Totales) throws IOException {
        FileOutputStream archivo = null;
        try {
            /*La ruta donde se creará el archivo*/
            String rutaArchivo = System.getProperty("user.home") + "/desktop/Eficiencia.xls";
            /*Se crea el objeto de tipo File con la ruta del archivo*/
            File archivoXLS = new File(rutaArchivo);
            libro = new HSSFWorkbook();
            archivo = new FileOutputStream(archivoXLS);
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

    public void ObtenerListaGSD(String Cadena) {
        listadatosGSD = new ArrayList<DatosGSD>();
        try {
            ResultSet rs = Principal.cn.GetConsulta("SELECT codigos.IDCODIGO,\n"
                    + "codigos.CADENA,\n"
                    + "codigos.PLATAFORMA,\n"
                    + "codigos.CODIGO,\n"
                    + "codigos.LINEA,\n"
                    + "codigos.TURNO,\n"
                    + "gsd.HCDIRLPS,\n"
                    + "gsd.HCDIRCORTE,\n"
                    + "gsd.HCDIRENSFINAL,\n"
                    + "gsd.HCINDRUTAS,\n"
                    + "gsd.PUNTOSPZAPOND,\n"
                    + "gsd.CAP_UTIL_HTA\n"
                    + "FROM\n"
                    + "gsd ,\n"
                    + "codigos WHERE codigos.IDCODIGO=gsd.IDCODIGO and codigos.linea<>'91' and cadena<>'4' and PLATAFORMA<>'SERVICIOS' AND CADENA='" + Cadena + "'");
            while (rs.next()) {
                DatosGSD datosGSD = new DatosGSD(new PlatCodLinea(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), ""), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12));
                listadatosGSD.add(datosGSD);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

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
    public String getLibroCorrecto(String Ruta) {
        String rsp = "";
        HSSFWorkbook libros;
        try {
            FileInputStream archivo = new FileInputStream(Ruta);
            if (Ruta.contains(".xls")) {
                libros = new HSSFWorkbook(archivo);
                // XSSFSheet hoja=libros.getSheet("CADENA 1");
                if (libros.getSheet("CADENA 1") != null) {
                    rsp = "CADENA 1";
                }
                if (libros.getSheet("CADENA 2") != null) {
                    rsp = "CADENA 2";
                }
                if (libros.getSheet("CADENA 3") != null) {
                    rsp = "CADENA 3";
                }
                if (libros.getSheet("CADENA 6") != null) {
                    rsp = "CADENA 6";
                }
            } else {
                libro = new HSSFWorkbook(archivo);
                //   HSSFSheet hoja=libro.getSheet("CADENA 1");
                if (libro.getSheet("CADENA 1") != null) {
                    rsp = "CADENA 1";
                }
                if (libro.getSheet("CADENA 2") != null) {
                    rsp = "CADENA 2";
                }
                if (libro.getSheet("CADENA 3") != null) {
                    rsp = "CADENA 3";
                }
                if (libro.getSheet("CADENA 6") != null) {
                    rsp = "CADENA 6";
                }
            }
            archivo.close();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return rsp;
    }

    public boolean lecturaArchivoXLS(String Ruta, String Hoja) {
        boolean rsp = false;
        HSSFWorkbook libros;
        try {
            //  File archivoXLS = new File(Ruta);
            FileInputStream archivo = new FileInputStream(Ruta);

            if (Ruta.contains(".xls")) {
                libros = new HSSFWorkbook(archivo);
                HSSFSheet hoja = libros.getSheet(Hoja);
                for (Row r : hoja) {

                    String Codigo = r.getCell(3).toString();
                    String Linea = r.getCell(4).toString();
                    if (Linea.contains(".")) {
                        switch (Cadena) {
                            case "1":
                                Linea = Linea.substring(0, 1);
                                break;
                            case "2":
                                Linea = Linea.substring(0, 2);
                                break;
                            case "3":
                                Linea = Linea.substring(0, 2);
                                break;
                            case "6":

                                Linea = Linea.substring(0, 2);
                                break;
                        }
                    }
                    String Turno = r.getCell(5).toString();
                    for (DatosGSD d : listadatosGSD) {
                        if ((!Codigo.isEmpty()) && (!Linea.isEmpty()) && (!Turno.isEmpty())) {
                            //hace comparacion tomando datos del ecxel guamuchil tomaba la linea con un punto, se hiso al revez la comparacion 
                            if (Codigo.trim().contains(d.Codigo.Codigo) && (Linea.trim().contains(d.Codigo.Linea)) && (Turno.trim().contains(d.Codigo.Turno))) {
                                // String variable=String.valueOf( r.getCell(6).getCellType());
                                switch (r.getCell(6).getCellType()) {
                                    case 1:
                                        d.HCDIRCORTE = String.valueOf(r.getCell(6).getStringCellValue());
                                        break;
                                    case 0:
                                        d.HCDIRCORTE = String.valueOf(r.getCell(6).getNumericCellValue());
                                        break;
                                    case 2:
                                        d.HCDIRCORTE = String.valueOf(r.getCell(6).getNumericCellValue());
                                        break;

                                }
                                switch (r.getCell(7).getCellType()) {
                                    case 1:
                                        d.HCDIRLPS = String.valueOf(r.getCell(7).getStringCellValue());
                                        break;
                                    case 0:
                                        d.HCDIRLPS = String.valueOf(r.getCell(7).getNumericCellValue());
                                        break;
                                    case 2:
                                        d.HCDIRLPS = String.valueOf(r.getCell(7).getNumericCellValue());
                                        break;
                                }
                                switch (r.getCell(8).getCellType()) {
                                    case 1:
                                        d.HCDIRENSFINAL = String.valueOf(r.getCell(8).getStringCellValue());
                                        break;
                                    case 0:
                                        d.HCDIRENSFINAL = String.valueOf(r.getCell(8).getNumericCellValue());
                                        break;
                                    case 2:
                                        d.HCDIRENSFINAL = String.valueOf(r.getCell(8).getNumericCellValue());
                                        break;
                                }
                                break;
                            }
                        }
                    }
                }
                //hoja.getr   
            } else {
                libro = new HSSFWorkbook(archivo);
                HSSFSheet hoja = libro.getSheet(Hoja);
            }
            archivo.close();
        } catch (Exception ex) {
            System.out.println(ex.toString() + "+++++");
        }
        return rsp;
    }

    public CellStyle RegresaTituloFechaStyle() {
        CellStyle csCabeceraFecha = libro.createCellStyle();
        Font cellFCF = libro.createFont();
        cellFCF.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        cellFCF.setFontHeightInPoints((short) 12);
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
