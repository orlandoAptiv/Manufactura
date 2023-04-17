/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package manufactura;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 *
 * @author Felipe
 */
public class ReporteGSDVSActual {
   File ArchivoXLS;
    HSSFSheet hoja;
    HSSFWorkbook libro;
    int Renglon=0;
    int columna=0;
    public ReporteGSDVSActual() {
        
        FileOutputStream archivo = null;
        try {
            /*La ruta donde se creará el archivo*/
            String rutaArchivo = System.getProperty("user.home")+"/desktop/GCSD VS Actual Report.xls";
            /*Se crea el objeto de tipo File con la ruta del archivo*/
            File archivoXLS = new File(rutaArchivo);
            libro = new HSSFWorkbook();
            archivo = new FileOutputStream(archivoXLS);
            hoja = libro.createSheet("Reporte"); 
            Principal.cn.GetConsulta("SELECT c.IDCODIGO, c.cadena, c.PLATAFORMA,  c.codigo, c.ARNES, c.TURNO, d.corte, HCDIRLPS AS 'SLP',  HCDIRLINEA as 'POST-OPERA', kits as KITS, Elinea AS HCLINEa, estaciones AS ESTACION, m.HCDIRSOPORTE as 'SOPORTE',  m.HCDIRFTQ as 'FTQ', m.HCDIRTABINSP as 'INSPECCIONES', m.HCRUTASINT,  m.activo FROM manufactura AS m, codigos as c, desglosecorte as d where m.IDCODIGO=c.IDCODIGO and c.IDCODIGO=d.idcodigo AND M.ACTIVO=1 order by c.CADENA, IDCODIGO");
        libro.write(archivo);
        /*Cerramos el flujo de datos*/
        archivo.close();
        /*Y abrimos el archivo con la clase Desktop*/
        Desktop.getDesktop().open(archivoXLS);
            
        }catch(Exception e )
        {
            
        }
    }
    
    
            
    
}
