package manufactura;

import Clases.DatosGSD;
import Clases.PlatCodLinea;
import static Reportes.ExcelImportGSD.listadatosGSD;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class excel_layoff {

    CellStyle celdaStiloCabecera;
    CellStyle celdaDetalle;
    CellStyle celdaTotales;
    File ArchivoXLS;
    HSSFSheet hoja;
    HSSFWorkbook libro;
    int Renglon = 0;
    int columna = 0;

    public excel_layoff(DefaultTableModel modelo, String Nombre) throws IOException {

        /*La ruta donde se creará el archivo*/
        String rutaArchivo = System.getProperty("user.home") + "/desktop/CALCULO DE LAYOFF2.xls";
        /*Se crea el objeto de tipo File con la ruta del archivo*/
        File archivoXLS = new File(rutaArchivo);
        /*Si el archivo existe se elimina*/
        if (archivoXLS.exists()) {
            archivoXLS.delete();
        }
        /*Se crea el archivo*/
        archivoXLS.createNewFile();

        /*Se crea el libro de excel usando el objeto de tipo Workbook*/
        Workbook libro = new HSSFWorkbook();
        /*Se inicializa el flujo de datos con el archivo xls*/
        FileOutputStream archivo = new FileOutputStream(archivoXLS);

        /*Utilizamos la clase Sheet para crear una nueva hoja de trabajo dentro del libro que creamos anteriormente*/
        Sheet hoja = libro.createSheet("lay off A");

        /*Hacemos un ciclo para inicializar los valores de 10 filas de celdas*/
        //for(int f=0;f<10;f++){
        /*La clase Row nos permitirá crear las filas*/
//          
        Row fila1 = hoja.createRow(3);
////                   
////           // celda.setCellStyle(getTituloStyleTotales(HSSFColor.GREEN.index));
////            /*Cada fila tendrá 5 celdas de datos*/
////            //for(int c=0;c<5;c++){
////                /*Creamos la celda a partir de la fila actual*/
        Cell celda = fila1.createCell(1);
        Cell celda1 = fila1.createCell(2);
        Cell celda2 = fila1.createCell(3);
        Cell celda3 = fila1.createCell(4);
        Cell celda4 = fila1.createCell(5);
        Cell celda5 = fila1.createCell(6);
        Cell celda6 = fila1.createCell(7);
        Cell celda7 = fila1.createCell(8);
        Cell celda8 = fila1.createCell(9);
        Cell celda9 = fila1.createCell(10);
        Cell celda10 = fila1.createCell(11);
        Cell celda11 = fila1.createCell(12);
        Cell celda12 = fila1.createCell(13);
        Cell celda13 = fila1.createCell(14);

        celda.setCellValue("CODIGO");
        celda1.setCellValue("LINEA");
        celda2.setCellValue("T_A_MOD");
        celda3.setCellValue("LUNES");
        celda4.setCellValue("MARTES");
        celda5.setCellValue("MIERCOLES");
        celda6.setCellValue("JUEVES");
        celda7.setCellValue("VIERNES");
        celda8.setCellValue("  ");
        celda9.setCellValue("LUNES");
        celda10.setCellValue("MARTES");
        celda11.setCellValue("MIERCOLES");
        celda12.setCellValue("JUEVES");
        celda13.setCellValue("VIERNES");

//           
        /*Escribimos en el libro*/
        libro.write(archivo);
        /*Cerramos el flujo de datos*/
        archivo.close();
        /*Y abrimos el archivo con la clase Desktop*/
        Desktop.getDesktop().open(archivoXLS);
    }

    public void ObtenerListaGSD(String Cadena) {
        listadatosGSD = new ArrayList<DatosGSD>();
        try {
            ResultSet rs = Principal.cn.GetConsulta("SELECT lay_off_a.CODIGO,lay_off_a.LINEA,lay_off_a.T_A_MOD,lay_off_a.LUNES,lay_off_a.MARTES,lay_off_a.MIERCOLES,lay_off_a.JUEVES,lay_off_a.VIERNES,lay_off_a.LUNES2,lay_off_a.MARTES2,lay_off_a.MIERCOLES2,lay_off_a.JUEVES2,lay_off_a.VIERNES2 FROM lay_off_a");
            while (rs.next()) {
                DatosGSD datosGSD = new DatosGSD(new PlatCodLinea(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), ""), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12));
                listadatosGSD.add(datosGSD);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

}
