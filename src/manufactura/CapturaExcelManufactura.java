
package manufactura;

import Clases.DatosManufactura;
import Clases.RenderColorear;
import Reportes.CeldaCheck;
import Reportes.ExcelEficiencia;
import Reportes.Render_CheckBox;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;



public class CapturaExcelManufactura extends javax.swing.JFrame {
    /**
     * Creates new form CapturaExcelGSD
     */
    DefaultTableModel modelo;
    String Turno="";
    String Cadena="";
      
      public CapturaExcelManufactura(String Cadena, String Turno) {
            initComponents();
           // EnlazarCbxPlataforma();
          //  enlazarPorc();
            this.Turno=Turno;
            this.Cadena=Cadena;
            Image icon = new ImageIcon(getClass().getResource("/Images/competitors-icon.png")).getImage();
            setIconImage(icon);
            EnlazarDgv();
          
      }
     
      public Double Regresa2Decimales(Double Valor){
        try{
         int aux = (int)(Valor*100);//1243
            Valor = aux/100d;// 
        }catch(Exception e)
        {
            System.out.println(e.toString());
        }
        return Valor;
      }
      
                 public static DefaultTableModel concentrado_plataformasLM() throws SQLException{
         DefaultTableModel modelotemp=null;
         
          try{
              modelotemp=new DefaultTableModel();
     ResultSet rs=Principal.cn.GetConsulta("select DISTINCT manu.plataforma as PLATAFORMA, manu.turno AS TURNO, sum(manu.hc)as HC,ROUND(sum(IF(manu.LINEA='CORTE' AND manu.CADENA=6,cortehrsemb.hrsEMBC,manu.horasemb)),2)as HRS_EMBARCADAS, ROUND(sum(manu.hrsPagadas),2) as HRS_PAGADAS,ROUND(IF(manu.LINEA='CORTE' AND manu.CADENA=6,(cortehrsemb.hrsEMBC/manu.hrsPagadas)*100,(sum(manu.horasemb)/sum(manu.hrsPagadas))*100),2)as EFICIENCIA   from \n" +
"(select c.plataforma,c.arnes,c.LINEA, c.CADENA, c.turno, sum(p.hcdirecto) as hc,round( sum((m.PUNTOSPZAPOND*m.SALIDAENPIEZA/100)),2) as horasemb,  IF(c.TURNO='A', sum(((elinea+estaciones+ kits+HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*9)),\n" +
"sum(((elinea+estaciones+ kits+ HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*7.9))) as hrsPagadas, m.activo  from manufactura as m, personas  as p, codigos as c where m.idcodigo=c.IDCODIGO and  m.idcodigo=p.IDCODIGO and c.CADENA<>4  and c.CADENA<>6 GROUP BY c.idcodigo order by c.TURNO) as manu ,\n" +
"(SELECT SUM(TRUNCATE((((g.HCDIRCORTE*m.SALIDAENPIEZA)/100)),2)) as hrsEMBC FROM gsd as g, manufactura as m, codigos as c where c.IDCODIGO=m.IDCODIGO and c.IDCODIGO=g.IDCODIGO and m.activo=1  and c.TURNO='B' GROUP BY c.TURNO) as cortehrsemb\n" +
"where manu.CADENA<>4 and manu.activo<>0  group by manu.plataforma,manu.turno  \n" +
"UNION ALL\n" +
"SELECT DISTINCT hcpag.plataforma,hcpag.turno, hcpag.HCDIRLINEA as hc ,corte.hrsEMBC, hcpag.horaspagadas, ROUND(truncate((corte.hrsEMBC/hcpag.horaspagadas)*100,2),2) as eficiencia \n" +
"from   (select  c.LINEA, c.CODIGO, c.TURNO, m.SALIDAENPIEZA, g.HCDIRCORTE as cortee,  \n" +
"SUM(TRUNCATE((((g.HCDIRCORTE*m.SALIDAENPIEZA)/100)),2)) as hrsEMBC,   g.HCDIRLPS as 'POND.LPS', \n" +
"TRUNCATE( if(c.TURNO='B' or c.TURNO='C', ((g.HCDIRLPS*m.SALIDAENPIEZA)/7.9/100),((g.HCDIRLPS *m.SALIDAENPIEZA)/9/100)) ,2) as LPS, \n" +
"g.HCDIRENSFINAL as 'PUNTOS.EF', TRUNCATE( if(c.TURNO='B' or c.TURNO='C',((g.HCDIRENSFINAL*m.SALIDAENPIEZA)/7.9/100), \n" +
"((g.HCDIRENSFINAL *m.SALIDAENPIEZA)/9/100)),2) as ENSFINAL  FROM codigos as c, manufactura as m, gsd  as g\n" +
"where c.IDCODIGO=m.IDCODIGO and c.IDCODIGO=g.IDCODIGO and m.activo=1 AND C.CADENA<>6 GROUP BY c.TURNO) as corte,\n" +
"(select c.plataforma,c.cadena,c.linea,c.CODIGO, HCDIRLINEA, C.TURNO,c.activo, case c.TURNO when 'B' THEN SUM((HCDIRLINEA)*7.9) \n" +
"WHEN 'A' THEN sum((HCDIRLINEA)*9) \n" +
"WHEN 'C' THEN sum((m.HCDIRLINEA)*7.32)  END \n" +
"as horaspagadas from manufactura as m INNER JOIN codigos as c ON m.IDCODIGO=c.IDCODIGO where  c.CADENA=4  GROUP BY c.TURNO) as hcpag WHERE hcpag.TURNO=corte.TURNO union all            \n" +
"SELECT codigos.PLATAFORMA,codigos.TURNO,manufactura.HCDIRLINEA as HC, IF(CODIGOS.PLATAFORMA='SERVICIOS' AND codigos.CADENA=1 and codigos.TURNO='A', 15,0)as horasemb, IF(CODIGOS.PLATAFORMA='SERVICIOS' AND codigos.CADENA=1 and codigos.TURNO='A', manufactura.HCDIRLINEA*9,manufactura.HCDIRLINEA*7.9)as horapag ,ROUND(IF(CODIGOS.PLATAFORMA='SERVICIOS' AND codigos.CADENA=1 and codigos.TURNO='A', (15/(manufactura.HCDIRLINEA*9)),(0/(manufactura.HCDIRLINEA*7.9))),2)as efic FROM manufactura , codigos WHERE  manufactura.IDCODIGO = codigos.IDCODIGO and CODIGOS.PLATAFORMA='SERVICIOS' AND codigos.CADENA<>6  \n" +
"union all                                                   \n" +
"SELECT codigos.PLATAFORMA,codigos.turno, manufactura.HCDIRLINEA,0,IF(CODIGOS.PLATAFORMA='EXTRAS GERENCIA' AND codigos.CADENA=7 and codigos.TURNO='A', manufactura.HCDIRLINEA*9,manufactura.HCDIRLINEA*7.9)as horapag,  0  FROM manufactura , codigos WHERE manufactura.IDCODIGO = codigos.IDCODIGO and CODIGOS.linea='91' order by 2\n" +
"");
                        //                                     1      2        3         4                 5               6        7          8      9
             modelotemp.setColumnIdentifiers(new Object[]{"PLATAFORMA", "TURNO", "HC","HRS_ EMBARCADAS","HRS_PAGADAS","EFICIENCIA"});
             while(rs.next())
              {
                    modelotemp.addRow(new Object[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)});
              }
         }catch(Exception e)
         {
             System.out.println(e+"get modelo");
         }
          return modelotemp;
      } 
      
                 
           public static DefaultTableModel concentrado_plataformasGML() throws SQLException{
         DefaultTableModel modelotemp=null;
         
          try{
              modelotemp=new DefaultTableModel();
     ResultSet rs=Principal.cn.GetConsulta("select DISTINCT manu.plataforma, manu.turno, sum(manu.hc),ROUND(sum(IF(manu.LINEA='CORTE' AND manu.CADENA=6,cortehrsemb.hrsEMBC,manu.horasemb)),2)as horasemb, ROUND(sum(manu.hrsPagadas),2) as hrsPagadas,ROUND(IF(manu.LINEA='CORTE' AND manu.CADENA=6,(cortehrsemb.hrsEMBC/manu.hrsPagadas)*100,(sum(manu.horasemb)/sum(manu.hrsPagadas))*100),2)as efic   from \n" +
"(select c.plataforma,c.CODIGO,c.arnes,c.LINEA, c.CADENA, c.turno, sum(p.hcdirecto) as hc,round( sum((m.PUNTOSPZAPOND*m.SALIDAENPIEZA/100)),2) as horasemb,  IF(c.TURNO='A', sum(((elinea+estaciones+ kits+HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*9)),\n" +
"sum(((elinea+estaciones+ kits+ HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*7.9))) as hrsPagadas, m.activo  from manufactura as m, codigos as c,personas  as p where m.idcodigo=c.IDCODIGO and  m.idcodigo=p.IDCODIGO and c.CADENA<>4    GROUP BY c.idcodigo order by c.PLATAFORMA) as manu ,\n" +
"(SELECT SUM(TRUNCATE((((g.HCDIRCORTE*m.SALIDAENPIEZA)/100)),2)) as hrsEMBC FROM gsd as g, manufactura as m, codigos as c where c.IDCODIGO=m.IDCODIGO and c.IDCODIGO=g.IDCODIGO and m.activo=1 and c.TURNO='B' GROUP BY c.TURNO) as cortehrsemb\n" +
"where manu.CADENA<>4 and manu.activo<>0 and manu.plataforma<>'CORTE' group by manu.plataforma,manu.turno  \n" +
"UNION ALL\n" +
"select DISTINCT manu.PLATAFORMA, manu.turno, manu.hc,IF(manu.LINEA='CORTE' AND manu.CADENA=6,cortehrsemb.hrsEMBC,manu.horasemb)as horasemb, (manu.hrsPagadas) as hrsPagadas,ROUND(IF(manu.LINEA='CORTE' AND manu.CADENA=6,(cortehrsemb.hrsEMBC/manu.hrsPagadas)*100,(manu.horasemb/manu.hrsPagadas)*100),2)as efic   from \n" +
"(select c.plataforma,c.CODIGO,c.arnes,c.LINEA, c.CADENA, c.turno, sum(m.HCDIRLINEA) as hc,round( sum((m.PUNTOSPZAPOND*m.SALIDAENPIEZA/100)),2) as horasemb,  IF(c.TURNO='A', sum(((elinea+estaciones+ kits+HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*9)),\n" +
"sum(((elinea+estaciones+ kits+ HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*7.9))) as hrsPagadas, m.activo  from manufactura as m, codigos as c where m.idcodigo=c.IDCODIGO and c.CADENA<>4  and c.TURNO='A' and c.codigo='corte'  GROUP BY c.idcodigo order by c.PLATAFORMA) as manu ,\n" +
"(SELECT SUM(TRUNCATE((((g.HCDIRCORTE*m.SALIDAENPIEZA)/100)),2)) as hrsEMBC FROM gsd as g, manufactura as m, codigos as c where c.IDCODIGO=m.IDCODIGO and c.IDCODIGO=g.IDCODIGO and m.activo=1  and c.TURNO='A' GROUP BY c.TURNO) as cortehrsemb\n" +
"where manu.CADENA<>4 and manu.activo<>0  UNION ALL\n" +
"select DISTINCT manu.plataforma, manu.turno, manu.hc,IF(manu.LINEA='CORTE' AND manu.CADENA=6,cortehrsemb.hrsEMBC,manu.horasemb)as horasemb, (manu.hrsPagadas) as hrsPagadas,ROUND(IF(manu.LINEA='CORTE' AND manu.CADENA=6,(cortehrsemb.hrsEMBC/manu.hrsPagadas)*100,(manu.horasemb/manu.hrsPagadas)*100),2)as efic   from \n" +
"(select c.plataforma,c.CODIGO,c.arnes,c.LINEA, c.CADENA, c.turno, sum(m.HCDIRLINEA) as hc,round( sum((m.PUNTOSPZAPOND*m.SALIDAENPIEZA/100)),2) as horasemb,  IF(c.TURNO='A', sum(((elinea+estaciones+ kits+HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*9)),\n" +
"sum(((elinea+estaciones+ kits+ HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*7.9))) as hrsPagadas, m.activo  from manufactura as m, codigos as c where m.idcodigo=c.IDCODIGO and c.CADENA<>4 and c.TURNO='B' and c.codigo='corte'  GROUP BY c.idcodigo) as manu,\n" +
"(SELECT SUM(TRUNCATE((((g.HCDIRCORTE*m.SALIDAENPIEZA)/100)),2)) as hrsEMBC FROM gsd as g, manufactura as m, codigos as c where c.IDCODIGO=m.IDCODIGO and c.IDCODIGO=g.IDCODIGO and m.activo=1   and c.TURNO='B'  GROUP BY c.TURNO) as cortehrsemb\n" +
"where  manu.activo<>0 order by 2");
                        //                                     1      2        3         4                 5               6        7          8      9
             modelotemp.setColumnIdentifiers(new Object[]{"PLATAFORMA", "TURNO", "HC","HRS_ EMBARCADAS","HRS_PAGADAS","EFICIENCIA"});
             while(rs.next())
              {
                    modelotemp.addRow(new Object[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)});
              }
         }catch(Exception e)
         {
             System.out.println(e+"get modelo");
         }
          return modelotemp;
      }                
           
           
  public static DefaultTableModel concentrado_plataformasLMYGML() throws SQLException{
         DefaultTableModel modelotemp=null;
         
          try{
              modelotemp=new DefaultTableModel();
     ResultSet rs=Principal.cn.GetConsulta("select DISTINCT manu.plataforma as PLATAFORMA,'LM' as PLANTA, manu.turno AS TURNO, sum(manu.hc)as HC,sum(IF(manu.LINEA='CORTE' AND manu.CADENA=6,cortehrsemb.hrsEMBC,manu.horasemb))as HRS_EMBARCADAS, sum(manu.hrsPagadas) as HRS_PAGADAS,ROUND(IF(manu.LINEA='CORTE' AND manu.CADENA=6,(cortehrsemb.hrsEMBC/manu.hrsPagadas)*100,(sum(manu.horasemb)/sum(manu.hrsPagadas))*100),2)as EFICIENCIA   from \n" +
"(select c.plataforma,c.arnes,c.LINEA, c.CADENA, c.turno, sum(p.hcdirecto) as hc,round( sum((m.PUNTOSPZAPOND*m.SALIDAENPIEZA/100)),2) as horasemb,  IF(c.TURNO='A', sum(((elinea+estaciones+ kits+HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*9)),\n" +
"sum(((elinea+estaciones+ kits+ HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*7.9))) as hrsPagadas, m.activo  from manufactura as m, personas  as p, codigos as c where m.idcodigo=c.IDCODIGO and  m.idcodigo=p.IDCODIGO and c.CADENA<>4  and c.CADENA<>6 GROUP BY c.idcodigo order by c.TURNO) as manu ,\n" +
"(SELECT SUM(TRUNCATE((((g.HCDIRCORTE*m.SALIDAENPIEZA)/100)),2)) as hrsEMBC FROM gsd as g, manufactura as m, codigos as c where c.IDCODIGO=m.IDCODIGO and c.IDCODIGO=g.IDCODIGO and m.activo=1 and c.TURNO='B' GROUP BY c.TURNO) as cortehrsemb\n" +
"where manu.CADENA<>4 and manu.activo<>0  group by manu.plataforma,manu.turno  \n" +
"UNION ALL\n" +
"SELECT DISTINCT hcpag.plataforma,'LM' as PLANTA,hcpag.turno, hcpag.HCDIRLINEA as hc ,corte.hrsEMBC, hcpag.horaspagadas, truncate((corte.hrsEMBC/hcpag.horaspagadas)*100,2) as eficiencia\n" +
"from   (select  c.LINEA, c.CODIGO, c.TURNO, m.SALIDAENPIEZA, g.HCDIRCORTE as cortee,  \n" +
"SUM(TRUNCATE((((g.HCDIRCORTE*m.SALIDAENPIEZA)/100)),2)) as hrsEMBC,   g.HCDIRLPS as 'POND.LPS', \n" +
"TRUNCATE( if(c.TURNO='B' or c.TURNO='C', ((g.HCDIRLPS*m.SALIDAENPIEZA)/7.9/100),((g.HCDIRLPS *m.SALIDAENPIEZA)/9/100)) ,2) as LPS, \n" +
"g.HCDIRENSFINAL as 'PUNTOS.EF', TRUNCATE( if(c.TURNO='B' or c.TURNO='C',((g.HCDIRENSFINAL*m.SALIDAENPIEZA)/7.9/100), \n" +
"((g.HCDIRENSFINAL *m.SALIDAENPIEZA)/9/100)),2) as ENSFINAL  FROM codigos as c, manufactura as m, gsd  as g\n" +
"where c.IDCODIGO=m.IDCODIGO and c.IDCODIGO=g.IDCODIGO and m.activo=1 AND C.CADENA<>6 GROUP BY c.TURNO) as corte,\n" +
"(select c.plataforma,c.cadena,c.linea,c.CODIGO, HCDIRLINEA, C.TURNO,c.activo, case c.TURNO when 'B' THEN SUM((HCDIRLINEA)*7.9) \n" +
"WHEN 'A' THEN sum((HCDIRLINEA)*9) \n" +
"WHEN 'C' THEN sum((m.HCDIRLINEA)*7.32)  END \n" +
"as horaspagadas from manufactura as m INNER JOIN codigos as c ON m.IDCODIGO=c.IDCODIGO where  c.CADENA=4  GROUP BY c.TURNO) as hcpag WHERE hcpag.TURNO=corte.TURNO union all            \n" +
"SELECT codigos.PLATAFORMA,'LM' as PLANTA,codigos.TURNO,manufactura.HCDIRLINEA as HC, IF(CODIGOS.PLATAFORMA='SERVICIOS' AND codigos.CADENA=1 and codigos.TURNO='A', 15,0)as horasemb, IF(CODIGOS.PLATAFORMA='SERVICIOS' AND codigos.CADENA=1 and codigos.TURNO='A', manufactura.HCDIRLINEA*9,manufactura.HCDIRLINEA*7.9)as horapag ,IF(CODIGOS.PLATAFORMA='SERVICIOS' AND codigos.CADENA=1 and codigos.TURNO='A', (15/(manufactura.HCDIRLINEA*9)),(0/(manufactura.HCDIRLINEA*7.9)))as efic FROM manufactura , codigos WHERE  manufactura.IDCODIGO = codigos.IDCODIGO and CODIGOS.PLATAFORMA='SERVICIOS' AND codigos.CADENA<>6 \n" +
"union all                                                                                                                                                                                                                                                                                \n" +
"select DISTINCT manu.plataforma,'GML' as PLANTA, manu.turno, sum(manu.hc),sum(IF(manu.LINEA='CORTE' AND manu.CADENA=6,cortehrsemb.hrsEMBC,manu.horasemb))as horasemb, sum(manu.hrsPagadas) as hrsPagadas,ROUND(IF(manu.LINEA='CORTE' AND manu.CADENA=6,(cortehrsemb.hrsEMBC/manu.hrsPagadas)*100,(sum(manu.horasemb)/sum(manu.hrsPagadas))*100),2)as efic   from \n" +
"(select c.plataforma,c.CODIGO,c.arnes,c.LINEA, c.CADENA, c.turno, sum(p.hcdirecto) as hc,round( sum((m.PUNTOSPZAPOND*m.SALIDAENPIEZA/100)),2) as horasemb,  IF(c.TURNO='A', sum(((elinea+estaciones+ kits+HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*9)),\n" +
"sum(((elinea+estaciones+ kits+ HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*7.9))) as hrsPagadas, m.activo  from manufactura as m, codigos as c,personas  as p where m.idcodigo=c.IDCODIGO and  m.idcodigo=p.IDCODIGO and c.CADENA<>4     GROUP BY c.idcodigo order by c.PLATAFORMA) as manu ,\n" +
"(SELECT SUM(TRUNCATE((((g.HCDIRCORTE*m.SALIDAENPIEZA)/100)),2)) as hrsEMBC FROM gsd as g, manufactura as m, codigos as c where c.IDCODIGO=m.IDCODIGO and c.IDCODIGO=g.IDCODIGO and m.activo=1  and c.TURNO='B' GROUP BY c.TURNO) as cortehrsemb\n" +
"where manu.CADENA<>4 and manu.activo<>0 and manu.plataforma<>'CORTE' group by manu.plataforma,manu.turno  \n" +
"UNION ALL\n" +
"select DISTINCT manu.PLATAFORMA,'GML' as PLANTA, manu.turno, manu.hc,IF(manu.LINEA='CORTE' AND manu.CADENA=6,cortehrsemb.hrsEMBC,manu.horasemb)as horasemb, (manu.hrsPagadas) as hrsPagadas,ROUND(IF(manu.LINEA='CORTE' AND manu.CADENA=6,(cortehrsemb.hrsEMBC/manu.hrsPagadas)*100,(manu.horasemb/manu.hrsPagadas)*100),2)as efic   from \n" +
"(select c.plataforma,c.CODIGO,c.arnes,c.LINEA, c.CADENA, c.turno, sum(m.HCDIRLINEA) as hc,round( sum((m.PUNTOSPZAPOND*m.SALIDAENPIEZA/100)),2) as horasemb,  IF(c.TURNO='A', sum(((elinea+estaciones+ kits+HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*9)),\n" +
"sum(((elinea+estaciones+ kits+ HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*7.9))) as hrsPagadas, m.activo  from manufactura as m, codigos as c where m.idcodigo=c.IDCODIGO and c.CADENA<>4  and c.TURNO='A' and c.codigo='corte'  GROUP BY c.idcodigo order by c.PLATAFORMA) as manu ,\n" +
"(SELECT SUM(TRUNCATE((((g.HCDIRCORTE*m.SALIDAENPIEZA)/100)),2)) as hrsEMBC FROM gsd as g, manufactura as m, codigos as c where c.IDCODIGO=m.IDCODIGO and c.IDCODIGO=g.IDCODIGO and m.activo=1  and c.TURNO='A' GROUP BY c.TURNO) as cortehrsemb\n" +
"where manu.CADENA<>4 and manu.activo<>0  UNION ALL\n" +
"select DISTINCT manu.plataforma,'GML' as PLANTA, manu.turno, manu.hc,IF(manu.LINEA='CORTE' AND manu.CADENA=6,cortehrsemb.hrsEMBC,manu.horasemb)as horasemb, (manu.hrsPagadas) as hrsPagadas,ROUND(IF(manu.LINEA='CORTE' AND manu.CADENA=6,(cortehrsemb.hrsEMBC/manu.hrsPagadas)*100,(manu.horasemb/manu.hrsPagadas)*100),2)as efic   from \n" +
"(select c.plataforma,c.CODIGO,c.arnes,c.LINEA, c.CADENA, c.turno, sum(m.HCDIRLINEA) as hc,round( sum((m.PUNTOSPZAPOND*m.SALIDAENPIEZA/100)),2) as horasemb,  IF(c.TURNO='A', sum(((elinea+estaciones+ kits+HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*9)),\n" +
"sum(((elinea+estaciones+ kits+ HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*7.9))) as hrsPagadas, m.activo  from manufactura as m, codigos as c where m.idcodigo=c.IDCODIGO and c.CADENA<>4 and c.TURNO='B' and c.codigo='corte' GROUP BY c.idcodigo) as manu,\n" +
"(SELECT SUM(TRUNCATE((((g.HCDIRCORTE*m.SALIDAENPIEZA)/100)),2)) as hrsEMBC FROM gsd as g, manufactura as m, codigos as c where c.IDCODIGO=m.IDCODIGO and c.IDCODIGO=g.IDCODIGO and m.activo=1   and c.TURNO='B'  GROUP BY c.TURNO) as cortehrsemb\n" +
"where  manu.activo<>0                    \n" +
"union all \n" +
"SELECT codigos.PLATAFORMA,'LM' as planta, codigos.turno, manufactura.HCDIRLINEA,0,IF(CODIGOS.PLATAFORMA='EXTRAS GERENCIA' AND codigos.CADENA=7 and codigos.TURNO='A', manufactura.HCDIRLINEA*9,manufactura.HCDIRLINEA*7.9)as horapag,  0  FROM manufactura , codigos WHERE manufactura.IDCODIGO = codigos.IDCODIGO and CODIGOS.linea='91' \n" +
"order by 2 DESC");
                        //                                     1      2        3         4                 5               6        7          8      9
             modelotemp.setColumnIdentifiers(new Object[]{"PLATAFORMA", "PLANTA","TURNO", "HC","HRS_ EMBARCADAS","HRS_PAGADAS","EFICIENCIA"});
             while(rs.next())
              {
                    modelotemp.addRow(new Object[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)});
              }
         }catch(Exception e)
         {
             System.out.println(e+"get modelo");
         }
          return modelotemp;
      }                
                          
           
           
           
           
                 
                 
                 
      
      
                 public static DefaultTableModel getModeloplataformasLM() throws SQLException{
         DefaultTableModel modelotemp=null;
         
          try{
              modelotemp=new DefaultTableModel();
     ResultSet rs=Principal.cn.GetConsulta("select DISTINCT manu.plataforma, manu.turno, sum(manu.hc)as HC,sum(IF(manu.LINEA='CORTE' AND manu.CADENA=6,cortehrsemb.hrsEMBC,manu.horasemb))as horasemb, sum(manu.hrsPagadas) as hrsPagadas,ROUND(IF(manu.LINEA='CORTE' AND manu.CADENA=6,(cortehrsemb.hrsEMBC/manu.hrsPagadas)*100,(sum(manu.horasemb)/sum(manu.hrsPagadas))*100),2)as efic   from \n" +
"(select c.plataforma,c.arnes,c.LINEA, c.CADENA, c.turno, sum(p.hcdirecto) as hc,round( sum((m.PUNTOSPZAPOND*m.SALIDAENPIEZA/100)),2) as horasemb,  IF(c.TURNO='A', sum(((elinea+estaciones+ kits+HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*9)),\n" +
"sum(((elinea+estaciones+ kits+ HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*7.9))) as hrsPagadas, m.activo  from manufactura as m, personas  as p, codigos as c where m.idcodigo=c.IDCODIGO and  m.idcodigo=p.IDCODIGO and c.CADENA<>4  and c.CADENA<>6 GROUP BY c.idcodigo order by c.TURNO) as manu ,\n" +
"(SELECT SUM(TRUNCATE((((g.HCDIRCORTE*m.SALIDAENPIEZA)/100)),2)) as hrsEMBC FROM gsd as g, manufactura as m, codigos as c where c.IDCODIGO=m.IDCODIGO and c.IDCODIGO=g.IDCODIGO and m.activo=1 and c.TURNO='B' GROUP BY c.TURNO) as cortehrsemb\n" +
"where manu.CADENA<>4 and manu.activo<>0  group by manu.plataforma,manu.turno  \n" +
"UNION ALL\n" +
"SELECT DISTINCT hcpag.plataforma,hcpag.turno, hcpag.HCDIRLINEA as hc ,corte.hrsEMBC, hcpag.horaspagadas, truncate((corte.hrsEMBC/hcpag.horaspagadas)*100,2) as eficiencia \n" +
"from   (select  c.LINEA, c.CODIGO, c.TURNO, m.SALIDAENPIEZA, g.HCDIRCORTE as cortee,  \n" +
"SUM(TRUNCATE((((g.HCDIRCORTE*m.SALIDAENPIEZA)/100)),2)) as hrsEMBC,   g.HCDIRLPS as 'POND.LPS', \n" +
"TRUNCATE( if(c.TURNO='B' or c.TURNO='C', ((g.HCDIRLPS*m.SALIDAENPIEZA)/7.9/100),((g.HCDIRLPS *m.SALIDAENPIEZA)/9/100)) ,2) as LPS, \n" +
"g.HCDIRENSFINAL as 'PUNTOS.EF', TRUNCATE( if(c.TURNO='B' or c.TURNO='C',((g.HCDIRENSFINAL*m.SALIDAENPIEZA)/7.9/100), \n" +
"((g.HCDIRENSFINAL *m.SALIDAENPIEZA)/9/100)),2) as ENSFINAL  FROM codigos as c, manufactura as m, gsd  as g\n" +
"where c.IDCODIGO=m.IDCODIGO and c.IDCODIGO=g.IDCODIGO and m.activo=1 AND C.CADENA<>6 GROUP BY c.TURNO) as corte,\n" +
"(select c.plataforma,c.cadena,c.linea,c.CODIGO, HCDIRLINEA, C.TURNO,c.activo, case c.TURNO when 'B' THEN SUM((HCDIRLINEA)*7.9) \n" +
"WHEN 'A' THEN sum((HCDIRLINEA)*9) \n" +
"WHEN 'C' THEN sum((m.HCDIRLINEA)*7.32)  END \n" +
"as horaspagadas from manufactura as m INNER JOIN codigos as c ON m.IDCODIGO=c.IDCODIGO where  c.CADENA=4  GROUP BY c.TURNO) as hcpag WHERE hcpag.TURNO=corte.TURNO   order by 2                \n" +
"");
                        //                                     1      2        3         4                 5               6        7          8      9
             modelotemp.setColumnIdentifiers(new Object[]{"PLATAFORMA", "TURNO", "HC","HRS_ EMB","HRS_PAG","EFIC"});
             while(rs.next())
              {
                    modelotemp.addRow(new Object[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)});
              }
         }catch(Exception e)
         {
             System.out.println(e+"get modelo");
         }
          return modelotemp;
      } 
      
      
      
      
      
      
      public static DefaultTableModel getModeloMFG(String Cadena, String Turno) throws SQLException{
         DefaultTableModel modelotemp=null;
         
          try{
              modelotemp=new DefaultTableModel();
     ResultSet rs=Principal.cn.GetConsulta("select manu.PLATAFORMA, manu.codigo, manu.linea,   manu.turno, manu.SALIDAENPIEZA, manu.hc, (HCDIRLINEA+kits+estaciones+elinea) as 'CONV.POST.KITS', hcdirconte AS 'CONTENSION', HCDIRSOPORTE AS 'SOPORTE', HCDIRTABINSP AS 'TAB.INSP.', hcrutasint AS 'RUTAS INT.', hcdirlps AS 'LPS', HCDIRSOPLPS AS 'SOP. LPS', hcdirpilotos AS 'PILOTOS', hcdirftq AS 'FTQ', hcdirsistemas AS 'SISTEMAS',  manu.PUNTOSPZAPOND AS 'PTOS. PIEZA',IF(manu.codigo='CORTE' AND manu.CADENA=6,cortehrsemb.hrsEMBC, IF( manu.codigo='SERV' AND manu.CADENA=1 AND manu.turno='A',manu.horasemb+15,IF(manu.codigo='SERV' and manu.CADENA=1 and manu.turno= 'B',manu.horasemb+0,manu.horasemb))) as horasemb , manu.hrsPagadas as hrsPagadas, IF(manu.codigo='CORTE' AND manu.CADENA=6,cortehrsemb.hrsEMBC/manu.hrsPagadas,manu.horasemb/manu.hrsPagadas)as efic,manu.curva, manu.curva from \n" +
"(select  p.plataforma, p.LINEA, p.CADENA, p.codigo, p.turno,  m.SALIDAENPIEZA, m.PUNTOSPZAPOND, kits, estaciones, elinea,  HCDIRLINEA, hcdirconte, HCDIRSOPORTE, HCDIRTABINSP, hcrutasint, hcdirlps, HCDIRSOPLPS, hcdirpilotos, hcdirftq, hcdirsistemas, sum(p.hcdirecto) as hc,curva, round(sum((m.PUNTOSPZAPOND*m.SALIDAENPIEZA/100)),2) as horasemb,  round(IF(p.TURNO='A', sum(((p.hcdirecto)*9)),sum(((p.hcdirecto)*7.9))),2) as hrsPagadas, m.activo  from manufactura as m, personas  as p where m.idcodigo=p.IDCODIGO GROUP BY p.idcodigo) as manu,\n" +
"(SELECT  SUM(TRUNCATE((((g.HCDIRCORTE*m.SALIDAENPIEZA)/100)),2)) as hrsEMBC FROM gsd as g, manufactura as m, codigos as c where c.IDCODIGO=m.IDCODIGO and c.IDCODIGO=g.IDCODIGO and m.activo=1 and c.turno='"+Turno+"'  GROUP BY c.TURNO) as cortehrsemb\n" +
" where  manu.CADENA='"+Cadena+"' and manu.turno='"+Turno+"' and manu.activo<>0");
      
//     // query para separar servicios horas embarcadas  de la pestana de cadena1//
//     ResultSet rs=Principal.cn.GetConsulta("select manu.PLATAFORMA, manu.codigo, manu.linea,   manu.turno, manu.SALIDAENPIEZA, manu.hc, (HCDIRLINEA+kits+estaciones+elinea) as 'CONV.POST.KITS', hcdirconte AS 'CONTENSION', HCDIRSOPORTE AS 'SOPORTE', HCDIRTABINSP AS 'TAB.INSP.', hcrutasint AS 'RUTAS INT.', hcdirlps AS 'LPS', HCDIRSOPLPS AS 'SOP. LPS', hcdirpilotos AS 'PILOTOS', hcdirftq AS 'FTQ', hcdirsistemas AS 'SISTEMAS',  manu.PUNTOSPZAPOND AS 'PTOS. PIEZA',IF(manu.codigo='CORTE' AND manu.CADENA=6,cortehrsemb.hrsEMBC,manu.horasemb)as horasemb , manu.hrsPagadas as hrsPagadas, IF(manu.codigo='CORTE' AND manu.CADENA=6,cortehrsemb.hrsEMBC/manu.hrsPagadas,manu.horasemb/manu.hrsPagadas)as efic,manu.curva, manu.curva from \n" +
//"(select  p.plataforma, p.LINEA, p.CADENA, p.codigo, p.turno,  m.SALIDAENPIEZA, m.PUNTOSPZAPOND, kits, estaciones, elinea,  HCDIRLINEA, hcdirconte, HCDIRSOPORTE, HCDIRTABINSP, hcrutasint, hcdirlps, HCDIRSOPLPS, hcdirpilotos, hcdirftq, hcdirsistemas, sum(p.hcdirecto) as hc,curva, round(sum((m.PUNTOSPZAPOND*m.SALIDAENPIEZA/100)),2) as horasemb,  round(IF(p.TURNO='A', sum(((p.hcdirecto)*9)),sum(((p.hcdirecto)*7.9))),2) as hrsPagadas, m.activo  from manufactura as m, personas  as p where m.idcodigo=p.IDCODIGO GROUP BY p.idcodigo) as manu,\n" +
//"(SELECT  SUM(TRUNCATE((((g.HCDIRCORTE*m.SALIDAENPIEZA)/100)),2)) as hrsEMBC FROM gsd as g, manufactura as m, codigos as c where c.IDCODIGO=m.IDCODIGO and c.IDCODIGO=g.IDCODIGO and m.activo=1 AND C.CADENA=6 and c.turno='"+Turno+"'  GROUP BY c.TURNO) as cortehrsemb\n" +
//" where  manu.CADENA='"+Cadena+"' and manu.turno='"+Turno+"' and manu.activo<>0");
     
     /// query para separar serviicos de la pestana de cadena 1////
//      ResultSet rs=Principal.cn.GetConsulta("select manu.PLATAFORMA, manu.codigo, manu.linea,   manu.turno, manu.SALIDAENPIEZA, manu.hc, (HCDIRLINEA+kits+estaciones+elinea) as 'CONV.POST.KITS', hcdirconte AS 'CONTENSION', HCDIRSOPORTE AS 'SOPORTE', HCDIRTABINSP AS 'TAB.INSP.', hcrutasint AS 'RUTAS INT.', hcdirlps AS 'LPS', HCDIRSOPLPS AS 'SOP. LPS', hcdirpilotos AS 'PILOTOS', hcdirftq AS 'FTQ', hcdirsistemas AS 'SISTEMAS',  manu.PUNTOSPZAPOND AS 'PTOS. PIEZA',IF(manu.codigo='CORTE' AND manu.CADENA=6,cortehrsemb.hrsEMBC,manu.horasemb)as horasemb , manu.hrsPagadas as hrsPagadas, IF(manu.codigo='CORTE' AND manu.CADENA=6,cortehrsemb.hrsEMBC/manu.hrsPagadas,manu.horasemb/manu.hrsPagadas)as efic,manu.curva, manu.curva from \n" +
//"(select  p.plataforma, p.LINEA, p.CADENA, p.codigo, p.turno,  m.SALIDAENPIEZA, m.PUNTOSPZAPOND, kits, estaciones, elinea,  HCDIRLINEA, hcdirconte, HCDIRSOPORTE, HCDIRTABINSP, hcrutasint, hcdirlps, HCDIRSOPLPS, hcdirpilotos, hcdirftq, hcdirsistemas, sum(p.hcdirecto) as hc,curva, round(sum((m.PUNTOSPZAPOND*m.SALIDAENPIEZA/100)),2) as horasemb,  round(IF(p.TURNO='A', sum(((p.hcdirecto)*9)),sum(((p.hcdirecto)*7.9))),2) as hrsPagadas, m.activo  from manufactura as m, personas  as p where m.idcodigo=p.IDCODIGO GROUP BY p.idcodigo) as manu,\n" +
//"(SELECT  SUM(TRUNCATE((((g.HCDIRCORTE*m.SALIDAENPIEZA)/100)),2)) as hrsEMBC FROM gsd as g, manufactura as m, codigos as c where c.IDCODIGO=m.IDCODIGO and c.IDCODIGO=g.IDCODIGO and m.activo=1 AND C.CADENA=6 and c.turno='"+Turno+"'  GROUP BY c.TURNO) as cortehrsemb\n" +
//" where  manu.CADENA='"+Cadena+"' and manu.turno='"+Turno+"'  and manu.activo<>0 and manu.LINEA<>'29A'");
      
      
  //          ResultSet rs=Principal.cn.GetConsulta("select   manu.codigo, manu.linea,   manu.turno, manu.SALIDAENPIEZA, manu.hc, (HCDIRLINEA+kits+estaciones+elinea) as 'CONV.POST.KITS', hcdirconte AS 'CONTENSION', HCDIRSOPORTE AS 'SOPORTE', HCDIRTABINSP AS 'TAB.INSP.', hcrutasint AS 'RUTAS INT.', hcdirlps AS 'LPS', HCDIRSOPLPS AS 'SOP. LPS', hcdirpilotos AS 'PILOTOS', hcdirftq AS 'FTQ', hcdirsistemas AS 'SISTEMAS',  manu.PUNTOSPZAPOND AS 'PTOS. PIEZA',manu.horasemb as horasemb, manu.hrsPagadas as hrsPagadas,  ( manu.horasemb/manu.hrsPagadas) as efic from \n" +
//"                (select p.LINEA, p.CADENA, p.codigo, p.turno,  m.SALIDAENPIEZA, m.PUNTOSPZAPOND, kits, estaciones, elinea,  HCDIRLINEA, hcdirconte, HCDIRSOPORTE, HCDIRTABINSP, hcrutasint, hcdirlps, HCDIRSOPLPS, hcdirpilotos, hcdirftq, hcdirsistemas, sum(p.hcdirecto) as hc, round(sum((m.PUNTOSPZAPOND*m.SALIDAENPIEZA/100))) as horasemb,  round(IF(p.TURNO='A', sum(((p.hcdirecto)*9)),sum(((p.hcdirecto)*7.9))),2) as hrsPagadas, m.activo  from manufactura as m, personas  as p where m.idcodigo=p.IDCODIGO GROUP BY p.idcodigo) as manu\n" +
//"                where   manu.activo<>0 and manu.CADENA='"+Cadena+"' and manu.turno='"+Turno+"'");
                        //                                            1     2       3         4                 5               6              7             8       9             10           11     12         13         14      15          16            17         18              19          20         
             modelotemp.setColumnIdentifiers(new Object[]{"PLATAFORMA","CODIGO", "LINEA", "TURNO", "SALIDA EN PZA.", "HC.DIRECTO", "HC ING", "CONTESION", "SOPORTES", "TAB.INSP.", "RUTAS INT.", "AVANZADA", "SOP.LPS", "PILOTOS" , "FTQ", "SISTEMAS", "PTOS.PZA.","HRS.EMB.", "HRS.PAGADAS", "EFICIENCIA" });
             while(rs.next())
              {
                    modelotemp.addRow(new Object[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7),rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13), rs.getString(14), rs.getString(15), rs.getString(16), rs.getString(17), rs.getString(18), rs.getString(19), rs.getString(20)});
              }
         }catch(Exception e)
         {
             System.out.println(e+"get modelo");
         }
          return modelotemp;
      } 
      
      
            public static DefaultTableModel getModeloLPS(String Cadena, String Turno) throws SQLException{
         DefaultTableModel modelotemp=null;
         
          try{
              modelotemp=new DefaultTableModel();
     ResultSet rs=Principal.cn.GetConsulta("select manu.PLATAFORMA, manu.codigo, manu.linea,   manu.turno, manu.SALIDAENPIEZA, manu.hc, (HCDIRLINEA+kits+estaciones+elinea) as 'CONV.POST.KITS', hcdirconte AS 'CONTENSION', HCDIRSOPORTE AS 'SOPORTE', HCDIRTABINSP AS 'TAB.INSP.', hcrutasint AS 'RUTAS INT.', hcdirlps AS 'LPS', HCDIRSOPLPS AS 'SOP. LPS', hcdirpilotos AS 'PILOTOS', hcdirftq AS 'FTQ', hcdirsistemas AS 'SISTEMAS',  manu.PUNTOSPZAPOND AS 'PTOS. PIEZA',IF(manu.codigo='CORTE' AND manu.CADENA=6,cortehrsemb.hrsEMBC, IF( manu.codigo='SERV' AND manu.CADENA=1 AND manu.turno='A',manu.horasemb+15,IF(manu.codigo='SERV' and manu.CADENA=1 and manu.turno= 'B',manu.horasemb+0,manu.horasemb))) as horasemb , manu.hrsPagadas as hrsPagadas, IF(manu.codigo='CORTE' AND manu.CADENA=6,cortehrsemb.hrsEMBC/manu.hrsPagadas,manu.horasemb/manu.hrsPagadas)as efic,manu.curva, manu.curva from \n" +
"(select  p.plataforma, p.LINEA, p.CADENA, p.codigo, p.turno,  m.SALIDAENPIEZA, m.PUNTOSPZAPOND, kits, estaciones, elinea,  HCDIRLINEA, hcdirconte, HCDIRSOPORTE, HCDIRTABINSP, hcrutasint, hcdirlps, HCDIRSOPLPS, hcdirpilotos, hcdirftq, hcdirsistemas, sum(p.hcdirecto) as hc,curva, round(sum((m.PUNTOSPZAPOND*m.SALIDAENPIEZA/100)),2) as horasemb,  round(IF(p.TURNO='A', sum(((p.hcdirecto)*9)),sum(((p.hcdirecto)*7.9))),2) as hrsPagadas, m.activo  from manufactura as m, personas  as p where m.idcodigo=p.IDCODIGO GROUP BY p.idcodigo) as manu,\n" +
"(SELECT  SUM(TRUNCATE((((g.HCDIRCORTE*m.SALIDAENPIEZA)/100)),2)) as hrsEMBC FROM gsd as g, manufactura as m, codigos as c where c.IDCODIGO=m.IDCODIGO and c.IDCODIGO=g.IDCODIGO and m.activo=1  and c.turno='"+Turno+"'  GROUP BY c.TURNO) as cortehrsemb\n" +
" where  manu.CADENA='"+Cadena+"' and manu.turno='"+Turno+"' and manu.activo<>0");
      
//     // query para separar servicios horas embarcadas  de la pestana de cadena1//
//     ResultSet rs=Principal.cn.GetConsulta("select manu.PLATAFORMA, manu.codigo, manu.linea,   manu.turno, manu.SALIDAENPIEZA, manu.hc, (HCDIRLINEA+kits+estaciones+elinea) as 'CONV.POST.KITS', hcdirconte AS 'CONTENSION', HCDIRSOPORTE AS 'SOPORTE', HCDIRTABINSP AS 'TAB.INSP.', hcrutasint AS 'RUTAS INT.', hcdirlps AS 'LPS', HCDIRSOPLPS AS 'SOP. LPS', hcdirpilotos AS 'PILOTOS', hcdirftq AS 'FTQ', hcdirsistemas AS 'SISTEMAS',  manu.PUNTOSPZAPOND AS 'PTOS. PIEZA',IF(manu.codigo='CORTE' AND manu.CADENA=6,cortehrsemb.hrsEMBC,manu.horasemb)as horasemb , manu.hrsPagadas as hrsPagadas, IF(manu.codigo='CORTE' AND manu.CADENA=6,cortehrsemb.hrsEMBC/manu.hrsPagadas,manu.horasemb/manu.hrsPagadas)as efic,manu.curva, manu.curva from \n" +
//"(select  p.plataforma, p.LINEA, p.CADENA, p.codigo, p.turno,  m.SALIDAENPIEZA, m.PUNTOSPZAPOND, kits, estaciones, elinea,  HCDIRLINEA, hcdirconte, HCDIRSOPORTE, HCDIRTABINSP, hcrutasint, hcdirlps, HCDIRSOPLPS, hcdirpilotos, hcdirftq, hcdirsistemas, sum(p.hcdirecto) as hc,curva, round(sum((m.PUNTOSPZAPOND*m.SALIDAENPIEZA/100)),2) as horasemb,  round(IF(p.TURNO='A', sum(((p.hcdirecto)*9)),sum(((p.hcdirecto)*7.9))),2) as hrsPagadas, m.activo  from manufactura as m, personas  as p where m.idcodigo=p.IDCODIGO GROUP BY p.idcodigo) as manu,\n" +
//"(SELECT  SUM(TRUNCATE((((g.HCDIRCORTE*m.SALIDAENPIEZA)/100)),2)) as hrsEMBC FROM gsd as g, manufactura as m, codigos as c where c.IDCODIGO=m.IDCODIGO and c.IDCODIGO=g.IDCODIGO and m.activo=1 AND C.CADENA=6 and c.turno='"+Turno+"'  GROUP BY c.TURNO) as cortehrsemb\n" +
//" where  manu.CADENA='"+Cadena+"' and manu.turno='"+Turno+"' and manu.activo<>0");
     
     /// query para separar serviicos de la pestana de cadena 1////
//      ResultSet rs=Principal.cn.GetConsulta("select manu.PLATAFORMA, manu.codigo, manu.linea,   manu.turno, manu.SALIDAENPIEZA, manu.hc, (HCDIRLINEA+kits+estaciones+elinea) as 'CONV.POST.KITS', hcdirconte AS 'CONTENSION', HCDIRSOPORTE AS 'SOPORTE', HCDIRTABINSP AS 'TAB.INSP.', hcrutasint AS 'RUTAS INT.', hcdirlps AS 'LPS', HCDIRSOPLPS AS 'SOP. LPS', hcdirpilotos AS 'PILOTOS', hcdirftq AS 'FTQ', hcdirsistemas AS 'SISTEMAS',  manu.PUNTOSPZAPOND AS 'PTOS. PIEZA',IF(manu.codigo='CORTE' AND manu.CADENA=6,cortehrsemb.hrsEMBC,manu.horasemb)as horasemb , manu.hrsPagadas as hrsPagadas, IF(manu.codigo='CORTE' AND manu.CADENA=6,cortehrsemb.hrsEMBC/manu.hrsPagadas,manu.horasemb/manu.hrsPagadas)as efic,manu.curva, manu.curva from \n" +
//"(select  p.plataforma, p.LINEA, p.CADENA, p.codigo, p.turno,  m.SALIDAENPIEZA, m.PUNTOSPZAPOND, kits, estaciones, elinea,  HCDIRLINEA, hcdirconte, HCDIRSOPORTE, HCDIRTABINSP, hcrutasint, hcdirlps, HCDIRSOPLPS, hcdirpilotos, hcdirftq, hcdirsistemas, sum(p.hcdirecto) as hc,curva, round(sum((m.PUNTOSPZAPOND*m.SALIDAENPIEZA/100)),2) as horasemb,  round(IF(p.TURNO='A', sum(((p.hcdirecto)*9)),sum(((p.hcdirecto)*7.9))),2) as hrsPagadas, m.activo  from manufactura as m, personas  as p where m.idcodigo=p.IDCODIGO GROUP BY p.idcodigo) as manu,\n" +
//"(SELECT  SUM(TRUNCATE((((g.HCDIRCORTE*m.SALIDAENPIEZA)/100)),2)) as hrsEMBC FROM gsd as g, manufactura as m, codigos as c where c.IDCODIGO=m.IDCODIGO and c.IDCODIGO=g.IDCODIGO and m.activo=1 AND C.CADENA=6 and c.turno='"+Turno+"'  GROUP BY c.TURNO) as cortehrsemb\n" +
//" where  manu.CADENA='"+Cadena+"' and manu.turno='"+Turno+"'  and manu.activo<>0 and manu.LINEA<>'29A'");
      
      
  //          ResultSet rs=Principal.cn.GetConsulta("select   manu.codigo, manu.linea,   manu.turno, manu.SALIDAENPIEZA, manu.hc, (HCDIRLINEA+kits+estaciones+elinea) as 'CONV.POST.KITS', hcdirconte AS 'CONTENSION', HCDIRSOPORTE AS 'SOPORTE', HCDIRTABINSP AS 'TAB.INSP.', hcrutasint AS 'RUTAS INT.', hcdirlps AS 'LPS', HCDIRSOPLPS AS 'SOP. LPS', hcdirpilotos AS 'PILOTOS', hcdirftq AS 'FTQ', hcdirsistemas AS 'SISTEMAS',  manu.PUNTOSPZAPOND AS 'PTOS. PIEZA',manu.horasemb as horasemb, manu.hrsPagadas as hrsPagadas,  ( manu.horasemb/manu.hrsPagadas) as efic from \n" +
//"                (select p.LINEA, p.CADENA, p.codigo, p.turno,  m.SALIDAENPIEZA, m.PUNTOSPZAPOND, kits, estaciones, elinea,  HCDIRLINEA, hcdirconte, HCDIRSOPORTE, HCDIRTABINSP, hcrutasint, hcdirlps, HCDIRSOPLPS, hcdirpilotos, hcdirftq, hcdirsistemas, sum(p.hcdirecto) as hc, round(sum((m.PUNTOSPZAPOND*m.SALIDAENPIEZA/100))) as horasemb,  round(IF(p.TURNO='A', sum(((p.hcdirecto)*9)),sum(((p.hcdirecto)*7.9))),2) as hrsPagadas, m.activo  from manufactura as m, personas  as p where m.idcodigo=p.IDCODIGO GROUP BY p.idcodigo) as manu\n" +
//"                where   manu.activo<>0 and manu.CADENA='"+Cadena+"' and manu.turno='"+Turno+"'");
                        //                                            1     2       3         4                 5               6              7             8       9             10           11     12         13         14      15          16            17         18              19          20         
             modelotemp.setColumnIdentifiers(new Object[]{"PLATAFORMA","CODIGO", "LINEA", "TURNO", "SALIDA EN PZA.", "HC.DIRECTO", "HC ING", "CONTESION", "SOPORTES", "TAB.INSP.", "RUTAS INT.", "LPS", "SOP.LPS", "PILOTOS" , "FTQ", "SISTEMAS", "PTOS.PZA.","HRS.EMB.", "HRS.PAGADAS", "EFICIENCIA" });
             while(rs.next())
              {
                    modelotemp.addRow(new Object[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7),rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13), rs.getString(14), rs.getString(15), rs.getString(16), rs.getString(17), rs.getString(18), rs.getString(19), rs.getString(20)});
              }
         }catch(Exception e)
         {
             System.out.println(e+"get modelo");
         }
          return modelotemp;
      } 

                        public static DefaultTableModel getModeloplataformaMSD() throws SQLException{
         DefaultTableModel modelotemp=null;
         
          try{
              modelotemp=new DefaultTableModel();
              ResultSet rs=Principal.cn.GetConsulta("select DISTINCT manu.plataforma, manu.codigo,manu.CADENA,manu.LINEA,ROUND(manu.SALIDAENPIEZA,0), manu.turno, manu.hc,IF(manu.LINEA='CORTE' AND manu.CADENA=6,cortehrsemb.hrsEMBC,manu.horasemb)as horasemb, ROUND((manu.hrsPagadas),2) as hrsPagadas,ROUND(IF(manu.LINEA='CORTE' AND manu.CADENA=6,(cortehrsemb.hrsEMBC/manu.hrsPagadas)*100,(manu.horasemb/manu.hrsPagadas)*100),2)as efic,manu.HCDIRLINEA as HC_ING, ROUND((manu.HCDIRLINEA)*1.1,0) as HC_MSD, (manu.hc-ROUND((manu.HCDIRLINEA)*1.1,0))as HC_DESP,\n" +
" IF(manu.turno='A' ,ROUND((manu.HCDIRLINEA)*1.1,0)*9,ROUND((manu.HCDIRLINEA)*1.1,0)*7.9)as HP_MSD,IFNULL(ROUND((IF(manu.LINEA='CORTE' AND manu.CADENA=6,cortehrsemb.hrsEMBC,manu.horasemb)/IF(manu.turno='A' ,ROUND((manu.HCDIRLINEA)*1.1,0)*9,ROUND((manu.HCDIRLINEA)*1.1,0)*7.9))*100,2),0) as EFIC_MSD from \n" +
"(select c.plataforma,c.CODIGO,c.arnes,c.LINEA, c.CADENA, c.turno, sum(p.hcdirecto) as hc,round( sum((m.PUNTOSPZAPOND*m.SALIDAENPIEZA/100)),2) as horasemb,m.SALIDAENPIEZA,  IF(c.TURNO='A', sum(((elinea+estaciones+ kits+HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*9)),\n" +
"sum(((elinea+estaciones+ kits+ HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*7.9))) as hrsPagadas, m.activo,HCDIRLINEA  from manufactura as m, codigos as c , personas  as p where m.idcodigo=c.IDCODIGO  and  m.idcodigo=p.IDCODIGO and c.CADENA<>4 and c.CADENA<>6  and c.TURNO='A' and c.LINEA<>'29A' GROUP BY c.idcodigo order by c.PLATAFORMA) as manu ,\n" +
"(SELECT SUM(TRUNCATE((((g.HCDIRCORTE*m.SALIDAENPIEZA)/100)),2)) as hrsEMBC FROM gsd as g, manufactura as m, codigos as c where c.IDCODIGO=m.IDCODIGO and c.IDCODIGO=g.IDCODIGO and m.activo=1 AND C.CADENA<>6 and c.TURNO='A' GROUP BY c.TURNO) as cortehrsemb\n" +
"where manu.CADENA<>4 and manu.activo<>0 \n" +
"UNION \n" +
"select DISTINCT manu.plataforma,manu.codigo,manu.CADENA,manu.LINEA,manu.SALIDAENPIEZA, manu.turno, manu.hc,IF(manu.LINEA='CORTE' AND manu.CADENA=6,cortehrsemb.hrsEMBC,manu.horasemb)as horasemb,ROUND((manu.hrsPagadas),2) as hrsPagadas,ROUND(IF(manu.LINEA='CORTE' AND manu.CADENA=6,(cortehrsemb.hrsEMBC/manu.hrsPagadas)*100,(manu.horasemb/manu.hrsPagadas)*100),2)as efic,manu.HCDIRLINEA AS HC_ING,ROUND((manu.HCDIRLINEA)*1.1,0) as HC_MSD, (manu.hc-ROUND((manu.HCDIRLINEA)*1.1,0))as HC_DESP,  \n" +
"IF(manu.turno='A' ,ROUND((manu.HCDIRLINEA)*1.1,0)*9,ROUND((manu.HCDIRLINEA)*1.1,0)*7.9)as HP_MSD, IFNULL(ROUND((IF(manu.LINEA='CORTE' AND manu.CADENA=6,cortehrsemb.hrsEMBC,manu.horasemb)/IF(manu.turno='A' ,ROUND((manu.HCDIRLINEA)*1.1,0)*9,ROUND((manu.HCDIRLINEA)*1.1,0)*7.9))*100,2),0) as EFIC_MSD from \n" +
"(select c.plataforma,c.CODIGO,c.arnes,c.LINEA, c.CADENA, c.turno,sum(p.hcdirecto) as hc,round( sum((m.PUNTOSPZAPOND*m.SALIDAENPIEZA/100)),2) as horasemb, m.SALIDAENPIEZA, IF(c.TURNO='A', sum(((elinea+estaciones+ kits+HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*9)),\n" +
"sum(((elinea+estaciones+ kits+ HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*7.9))) as hrsPagadas, m.activo,HCDIRLINEA  from manufactura as m, codigos as c , personas  as p where m.idcodigo=c.IDCODIGO and  m.idcodigo=p.IDCODIGO and c.CADENA<>4 and c.TURNO='B' and c.LINEA<>'29A' GROUP BY c.idcodigo) as manu,\n" +
"(SELECT SUM(TRUNCATE((((g.HCDIRCORTE*m.SALIDAENPIEZA)/100)),2)) as hrsEMBC FROM gsd as g, manufactura as m, codigos as c where c.IDCODIGO=m.IDCODIGO and c.IDCODIGO=g.IDCODIGO and m.activo=1 AND C.CADENA<>6  and c.TURNO='B'  GROUP BY c.TURNO) as cortehrsemb\n" +
"where  manu.activo<>0 and manu.linea<>'29A' AND manu.CADENA<>6\n" +
"UNION\n" +
"SELECT DISTINCT hcpag.plataforma,hcpag.CODIGO,hcpag.cadena, hcpag.linea,corte.SALIDAENPIEZA,hcpag.turno, hcpag.HCDIRLINEA as hc ,ROUND((corte.hrsEMBC)/7,2) AS HRS_EMB, hcpag.horaspagadas, truncate(((corte.hrsEMBC/7)/hcpag.horaspagadas)*100,2) as eficiencia,(corte.operativoMaq)*1.25 as HC_ING,(corte.operativoMaq)*1.25 as HC_MSD, (hcpag.HCDIRLINEA-(corte.operativoMaq)*1.25)as HC_DESP,\n" +
"IF(hcpag.turno='A' ,((corte.operativoMaq)*1.25)*9,((corte.operativoMaq)*1.25)*7.9)as HP_MSD, ((corte.hrsEMBC/7)/IF(hcpag.turno='A' ,((corte.operativoMaq)*1.25)*9,((corte.operativoMaq)*1.25)*7.9))*100 AS EIC_MSD from   (select  c.LINEA, c.CODIGO, c.TURNO, m.SALIDAENPIEZA, g.HCDIRCORTE as cortee,t.operativoMaq,\n" +
"SUM(TRUNCATE((((g.HCDIRCORTE*m.SALIDAENPIEZA)/100)),2)) as hrsEMBC,   g.HCDIRLPS as 'POND.LPS', \n" +
"TRUNCATE( if(c.TURNO='B' or c.TURNO='C', ((g.HCDIRLPS*m.SALIDAENPIEZA)/7.9/100),((g.HCDIRLPS *m.SALIDAENPIEZA)/9/100)) ,2) as LPS, \n" +
"g.HCDIRENSFINAL as 'PUNTOS.EF', TRUNCATE( if(c.TURNO='B' or c.TURNO='C',((g.HCDIRENSFINAL*m.SALIDAENPIEZA)/7.9/100), \n" +
"((g.HCDIRENSFINAL *m.SALIDAENPIEZA)/9/100)),2) as ENSFINAL  FROM codigos as c, manufactura as m, gsd  as g,tablacorte as t\n" +
"where c.IDCODIGO=m.IDCODIGO and c.IDCODIGO=g.IDCODIGO  and m.activo=1 AND C.CADENA<>6 GROUP BY c.TURNO) as corte,\n" +
"(select c.plataforma,c.cadena,c.linea,c.CODIGO, HCDIRLINEA, C.TURNO,c.activo, case c.TURNO when 'B' THEN SUM((HCDIRLINEA)*7.9) \n" +
"WHEN 'A' THEN sum((HCDIRLINEA)*9) \n" +
"WHEN 'C' THEN sum((m.HCDIRLINEA)*7.32)  END \n" +
"as horaspagadas from manufactura as m INNER JOIN codigos as c ON m.IDCODIGO=c.IDCODIGO INNER JOIN tablacorte ON tablacorte.idcodigo=m.IDCODIGO where  c.CADENA=4 AND (m.IDCODIGO=81 or m.IDCODIGO=84) GROUP BY c.TURNO) as hcpag WHERE hcpag.TURNO=corte.TURNO \n" +
"UNION\n" +
"SELECT codigos.PLATAFORMA,codigos.CODIGO,codigos.CADENA,codigos.LINEA,manufactura.SALIDAENPIEZA,codigos.TURNO,manufactura.HCDIRLINEA, IF(CODIGOS.CODIGO='SERV' AND codigos.CADENA=1 and codigos.TURNO='A', 15,0)as horasemb, IF(CODIGOS.CODIGO='SERV' AND codigos.CADENA=1 and codigos.TURNO='A',manufactura.HCDIRLINEA*9,manufactura.HCDIRLINEA*7.9)as horapag ,IF(CODIGOS.CODIGO='SERV' AND codigos.CADENA=1 and codigos.TURNO='A', (15/(manufactura.HCDIRLINEA*9))*100,(0/(manufactura.HCDIRLINEA*7.9))*100)as efic,'0' AS HC_ING,'0' AS HC_MSD,manufactura.HCDIRLINEA AS HC_DESP,\n" +
"'0' as HP_MSD,'0' as EFIC_MSD FROM manufactura , codigos WHERE  manufactura.IDCODIGO = codigos.IDCODIGO and CODIGOS.CODIGO='SERV'\n" +
"union all\n" +
"SELECT codigos.PLATAFORMA,codigos.CODIGO,codigos.CADENA,codigos.LINEA,manufactura.SALIDAENPIEZA,codigos.turno, manufactura.HCDIRLINEA,0,IF(CODIGOS.PLATAFORMA='EXTRAS GERENCIA' AND codigos.CADENA=7 and codigos.TURNO='A', manufactura.HCDIRLINEA*9,manufactura.HCDIRLINEA*7.9)as horapag,  0,'0' AS HC_ING,'0' AS HC_MSD,manufactura.HCDIRLINEA AS HC_DEPS,\n" +
"'0' as HP_MSD,(IF(CODIGOS.CODIGO='SERV' AND codigos.CADENA=1 and codigos.TURNO='A', 15,0)/IF(codigos.TURNO='A' ,manufactura.HCDIRLINEA*9,manufactura.HCDIRLINEA*7.9)*100) FROM manufactura , codigos WHERE manufactura.IDCODIGO = codigos.IDCODIGO and CODIGOS.linea='91' \n" +
"");
              
//     ResultSet rs=Principal.cn.GetConsulta("select DISTINCT manu.plataforma, manu.codigo,manu.CADENA,manu.LINEA,manu.SALIDAENPIEZA, manu.turno, manu.hc,IF(manu.LINEA='CORTE' AND manu.CADENA=6,cortehrsemb.hrsEMBC,manu.horasemb)as horasemb, ROUND((manu.hrsPagadas),2) as hrsPagadas,ROUND(IF(manu.LINEA='CORTE' AND manu.CADENA=6,(cortehrsemb.hrsEMBC/manu.hrsPagadas)*100,(manu.horasemb/manu.hrsPagadas)*100),2)as efic,manu.HCDIRLINEA as HC_ING, ROUND((manu.HCDIRLINEA)*1.1,0) as HC_MSD, (manu.hc-ROUND((manu.HCDIRLINEA)*1.1,0))as HC_DESP,\n" +
//" IF(manu.turno='A' ,ROUND((manu.HCDIRLINEA)*1.1,0)*9,ROUND((manu.HCDIRLINEA)*1.1,0)*7.9)as HP_MSD,IFNULL(ROUND((IF(manu.LINEA='CORTE' AND manu.CADENA=6,cortehrsemb.hrsEMBC,manu.horasemb)/IF(manu.turno='A' ,ROUND((manu.HCDIRLINEA)*1.1,0)*9,ROUND((manu.HCDIRLINEA)*1.1,0)*7.9))*100,2),0) as EFIC_MSD from \n" +
//"(select c.plataforma,c.CODIGO,c.arnes,c.LINEA, c.CADENA, c.turno, sum(p.hcdirecto) as hc,round( sum((m.PUNTOSPZAPOND*m.SALIDAENPIEZA/100)),2) as horasemb,m.SALIDAENPIEZA,  IF(c.TURNO='A', sum(((elinea+estaciones+ kits+HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*9)),\n" +
//"sum(((elinea+estaciones+ kits+ HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*7.9))) as hrsPagadas, m.activo,HCDIRLINEA  from manufactura as m, codigos as c , personas  as p where m.idcodigo=c.IDCODIGO  and  m.idcodigo=p.IDCODIGO and c.CADENA<>4 and c.CADENA<>6  and c.TURNO='A' and c.LINEA<>'29A' GROUP BY c.idcodigo order by c.PLATAFORMA) as manu ,\n" +
//"(SELECT SUM(TRUNCATE((((g.HCDIRCORTE*m.SALIDAENPIEZA)/100)),2)) as hrsEMBC FROM gsd as g, manufactura as m, codigos as c where c.IDCODIGO=m.IDCODIGO and c.IDCODIGO=g.IDCODIGO and m.activo=1 AND C.CADENA<>6 and c.TURNO='A' GROUP BY c.TURNO) as cortehrsemb\n" +
//"where manu.CADENA<>4 and manu.activo<>0 \n" +
//"UNION \n" +
//"select DISTINCT manu.plataforma,manu.codigo,manu.CADENA,manu.LINEA,manu.SALIDAENPIEZA, manu.turno, manu.hc,IF(manu.LINEA='CORTE' AND manu.CADENA=6,cortehrsemb.hrsEMBC,manu.horasemb)as horasemb,ROUND((manu.hrsPagadas),2) as hrsPagadas,ROUND(IF(manu.LINEA='CORTE' AND manu.CADENA=6,(cortehrsemb.hrsEMBC/manu.hrsPagadas)*100,(manu.horasemb/manu.hrsPagadas)*100),2)as efic,manu.HCDIRLINEA AS HC_ING,ROUND((manu.HCDIRLINEA)*1.1,0) as HC_MSD, (manu.hc-ROUND((manu.HCDIRLINEA)*1.1,0))as HC_DESP,  \n" +
//"IF(manu.turno='A' ,ROUND((manu.HCDIRLINEA)*1.1,0)*9,ROUND((manu.HCDIRLINEA)*1.1,0)*7.9)as HP_MSD, IFNULL(ROUND((IF(manu.LINEA='CORTE' AND manu.CADENA=6,cortehrsemb.hrsEMBC,manu.horasemb)/IF(manu.turno='A' ,ROUND((manu.HCDIRLINEA)*1.1,0)*9,ROUND((manu.HCDIRLINEA)*1.1,0)*7.9))*100,2),0) as EFIC_MSD from \n" +
//"(select c.plataforma,c.CODIGO,c.arnes,c.LINEA, c.CADENA, c.turno,sum(p.hcdirecto) as hc,round( sum((m.PUNTOSPZAPOND*m.SALIDAENPIEZA/100)),2) as horasemb, m.SALIDAENPIEZA, IF(c.TURNO='A', sum(((elinea+estaciones+ kits+HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*9)),\n" +
//"sum(((elinea+estaciones+ kits+ HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*7.9))) as hrsPagadas, m.activo,HCDIRLINEA  from manufactura as m, codigos as c , personas  as p where m.idcodigo=c.IDCODIGO and  m.idcodigo=p.IDCODIGO and c.CADENA<>4 and c.TURNO='B' and c.LINEA<>'29A' GROUP BY c.idcodigo) as manu,\n" +
//"(SELECT SUM(TRUNCATE((((g.HCDIRCORTE*m.SALIDAENPIEZA)/100)),2)) as hrsEMBC FROM gsd as g, manufactura as m, codigos as c where c.IDCODIGO=m.IDCODIGO and c.IDCODIGO=g.IDCODIGO and m.activo=1 AND C.CADENA<>6  and c.TURNO='B'  GROUP BY c.TURNO) as cortehrsemb\n" +
//"where  manu.activo<>0 and manu.linea<>'29A' AND manu.CADENA<>6\n" +
//"UNION\n" +
//"SELECT DISTINCT hcpag.plataforma,hcpag.CODIGO,hcpag.cadena, hcpag.linea,corte.SALIDAENPIEZA,hcpag.turno, hcpag.HCDIRLINEA as hc ,corte.hrsEMBC, hcpag.horaspagadas, truncate((corte.hrsEMBC/hcpag.horaspagadas)*100,2) as eficiencia,(corte.operativoMaq)*1.25 as HC_ING,(corte.operativoMaq)*1.25 as HC_MSD, (hcpag.HCDIRLINEA-(corte.operativoMaq)*1.25)as HC_DESP,\n" +
//"IF(hcpag.turno='A' ,((corte.operativoMaq)*1.25)*9,((corte.operativoMaq)*1.25)*7.9)as HP_MSD, (corte.hrsEMBC/IF(hcpag.turno='A' ,((corte.operativoMaq)*1.25)*9,((corte.operativoMaq)*1.25)*7.9)) from   (select  c.LINEA, c.CODIGO, c.TURNO, m.SALIDAENPIEZA, g.HCDIRCORTE as cortee,t.operativoMaq,\n" +
//"SUM(TRUNCATE((((g.HCDIRCORTE*m.SALIDAENPIEZA)/100)),2)) as hrsEMBC,   g.HCDIRLPS as 'POND.LPS', \n" +
//"TRUNCATE( if(c.TURNO='B' or c.TURNO='C', ((g.HCDIRLPS*m.SALIDAENPIEZA)/7.9/100),((g.HCDIRLPS *m.SALIDAENPIEZA)/9/100)) ,2) as LPS, \n" +
//"g.HCDIRENSFINAL as 'PUNTOS.EF', TRUNCATE( if(c.TURNO='B' or c.TURNO='C',((g.HCDIRENSFINAL*m.SALIDAENPIEZA)/7.9/100), \n" +
//"((g.HCDIRENSFINAL *m.SALIDAENPIEZA)/9/100)),2) as ENSFINAL  FROM codigos as c, manufactura as m, gsd  as g,tablacorte as t\n" +
//"where c.IDCODIGO=m.IDCODIGO and c.IDCODIGO=g.IDCODIGO and m.activo=1 AND C.CADENA<>6 GROUP BY c.TURNO) as corte,\n" +
//"(select c.plataforma,c.cadena,c.linea,c.CODIGO, HCDIRLINEA, C.TURNO,c.activo, case c.TURNO when 'B' THEN SUM((HCDIRLINEA)*7.9) \n" +
//"WHEN 'A' THEN sum((HCDIRLINEA)*9) \n" +
//"WHEN 'C' THEN sum((m.HCDIRLINEA)*7.32)  END \n" +
//"as horaspagadas from manufactura as m INNER JOIN codigos as c ON m.IDCODIGO=c.IDCODIGO INNER JOIN tablacorte ON tablacorte.idcodigo=m.IDCODIGO where  c.CADENA=4 AND (m.IDCODIGO=81 or m.IDCODIGO=84) GROUP BY c.TURNO) as hcpag WHERE hcpag.TURNO=corte.TURNO \n" +
//"UNION\n" +
//"SELECT codigos.PLATAFORMA,codigos.CODIGO,codigos.CADENA,codigos.LINEA,manufactura.SALIDAENPIEZA,codigos.TURNO,manufactura.HCDIRLINEA, IF(CODIGOS.CODIGO='SERV' AND codigos.CADENA=1 and codigos.TURNO='A', 30,0)as horasemb, IF(CODIGOS.CODIGO='SERV' AND codigos.CADENA=1 and codigos.TURNO='A',manufactura.HCDIRLINEA*9,manufactura.HCDIRLINEA*7.9)as horapag ,IF(CODIGOS.CODIGO='SERV' AND codigos.CADENA=1 and codigos.TURNO='A', (30/(manufactura.HCDIRLINEA*9)),(0/(manufactura.HCDIRLINEA*7.9)))as efic,manufactura.HCDIRLINEA AS HC_ING,manufactura.HCDIRLINEA AS HC_MSD,'0' AS HC_DESP,\n" +
//"IF(codigos.TURNO='A' ,manufactura.HCDIRLINEA*9,manufactura.HCDIRLINEA*7.9) as HP_MSD,(IF(CODIGOS.CODIGO='SERV' AND codigos.CADENA=1 and codigos.TURNO='A', 30,0)/IF(codigos.TURNO='A' ,manufactura.HCDIRLINEA*9,manufactura.HCDIRLINEA*7.9)*100) as EFIC_MSD FROM manufactura , codigos WHERE  manufactura.IDCODIGO = codigos.IDCODIGO and CODIGOS.CODIGO='SERV'\n" +
//"union all\n" +
//"SELECT codigos.PLATAFORMA,codigos.CODIGO,codigos.CADENA,codigos.LINEA,manufactura.SALIDAENPIEZA,codigos.turno, manufactura.HCDIRLINEA,0,IF(CODIGOS.PLATAFORMA='EXTRAS GERENCIA' AND codigos.CADENA=7 and codigos.TURNO='A', manufactura.HCDIRLINEA*9,manufactura.HCDIRLINEA*7.9)as horapag,  0,manufactura.HCDIRLINEA AS HC_ING,manufactura.HCDIRLINEA AS HC_MSD,'0' AS HC_DEPS,\n" +
//"IF(codigos.TURNO='A' ,manufactura.HCDIRLINEA*9,manufactura.HCDIRLINEA*7.9) as HP_MSD,(IF(CODIGOS.CODIGO='SERV' AND codigos.CADENA=1 and codigos.TURNO='A', 30,0)/IF(codigos.TURNO='A' ,manufactura.HCDIRLINEA*9,manufactura.HCDIRLINEA*7.9)*100) FROM manufactura , codigos WHERE manufactura.IDCODIGO = codigos.IDCODIGO and CODIGOS.linea='91' \n" +
//"\n" +
//"");
     
                                                               // 1         2        3        4          5          6          7            8           9          10     11       12       13        14       15        16                                                           
             modelotemp.setColumnIdentifiers(new Object[]{"PLATAFORMA","CODIGO","CADENA", "LINEA", "SALIDA PZA","TURNO", "HC NEC MFG","HRS EMB","HRS PAG","EFIC","HC ING","HC MSD","HC DESP","HP MSD","EFIC MSD"});
             while(rs.next())
              {
                    modelotemp.addRow(new Object[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7),rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13), rs.getString(14), rs.getString(15)});
              }
         }catch(Exception e)
         {
             System.out.println(e+"get modelo");
         }
          return modelotemp;
      } 
      
      
      
      
      
            public static DefaultTableModel getModeloplataforma() throws SQLException{
         DefaultTableModel modelotemp=null;
         
          try{
              modelotemp=new DefaultTableModel();
     ResultSet rs=Principal.cn.GetConsulta("select DISTINCT manu.plataforma, manu.codigo,manu.CADENA,manu.LINEA, manu.turno, manu.hc,manu.SALIDAENPIEZA,IF(manu.LINEA='CORTE' AND manu.CADENA=6,cortehrsemb.hrsEMBC,manu.horasemb)as horasemb, (manu.hrsPagadas) as hrsPagadas,ROUND(IF(manu.LINEA='CORTE' AND manu.CADENA=6,(cortehrsemb.hrsEMBC/manu.hrsPagadas)*100,(manu.horasemb/manu.hrsPagadas)*100),2)as efic   from\n" +
"(select c.plataforma,c.CODIGO,c.arnes,c.LINEA, c.CADENA, c.turno, sum(p.hcdirecto) as hc,round( sum((m.PUNTOSPZAPOND*m.SALIDAENPIEZA/100)),2) as horasemb,m.SALIDAENPIEZA,  IF(c.TURNO='A', sum(((elinea+estaciones+ kits+HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*9)),\n" +
"sum(((elinea+estaciones+ kits+ HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*7.9))) as hrsPagadas, m.activo  from manufactura as m, codigos as c , personas  as p where m.idcodigo=c.IDCODIGO  and  m.idcodigo=p.IDCODIGO and c.CADENA<>4 and c.CADENA<>6  and c.TURNO='A' and c.LINEA<>'29A' GROUP BY c.idcodigo order by c.PLATAFORMA) as manu ,\n" +
"(SELECT SUM(TRUNCATE((((g.HCDIRCORTE*m.SALIDAENPIEZA)/100)),2)) as hrsEMBC FROM gsd as g, manufactura as m, codigos as c where c.IDCODIGO=m.IDCODIGO and c.IDCODIGO=g.IDCODIGO and m.activo=1 AND C.CADENA<>6 and c.TURNO='A' GROUP BY c.TURNO) as cortehrsemb\n" +
"where manu.CADENA<>4 and manu.activo<>0  UNION select DISTINCT manu.plataforma,manu.codigo,manu.CADENA,manu.LINEA, manu.turno, manu.hc,manu.SALIDAENPIEZA,IF(manu.LINEA='CORTE' AND manu.CADENA=6,cortehrsemb.hrsEMBC,manu.horasemb)as horasemb, (manu.hrsPagadas) as hrsPagadas,ROUND(IF(manu.LINEA='CORTE' AND manu.CADENA=6,(cortehrsemb.hrsEMBC/manu.hrsPagadas)*100,(manu.horasemb/manu.hrsPagadas)*100),2)as efic   from \n" +
"(select c.plataforma,c.CODIGO,c.arnes,c.LINEA, c.CADENA, c.turno,sum(p.hcdirecto) as hc,round( sum((m.PUNTOSPZAPOND*m.SALIDAENPIEZA/100)),2) as horasemb, m.SALIDAENPIEZA, IF(c.TURNO='A', sum(((elinea+estaciones+ kits+HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*9)),\n" +
"sum(((elinea+estaciones+ kits+ HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*7.9))) as hrsPagadas, m.activo  from manufactura as m, codigos as c , personas  as p where m.idcodigo=c.IDCODIGO and  m.idcodigo=p.IDCODIGO and c.CADENA<>4 and c.TURNO='B' and c.LINEA<>'29A' GROUP BY c.idcodigo) as manu,\n" +
"(SELECT SUM(TRUNCATE((((g.HCDIRCORTE*m.SALIDAENPIEZA)/100)),2)) as hrsEMBC FROM gsd as g, manufactura as m, codigos as c where c.IDCODIGO=m.IDCODIGO and c.IDCODIGO=g.IDCODIGO and m.activo=1 AND C.CADENA<>6  and c.TURNO='B'  GROUP BY c.TURNO) as cortehrsemb\n" +
"where  manu.activo<>0 and manu.linea<>'29A' AND manu.CADENA<>6\n" +
"union\n" +
"SELECT DISTINCT hcpag.plataforma,hcpag.CODIGO,hcpag.cadena, hcpag.linea,hcpag.turno, hcpag.HCDIRLINEA as hc ,corte.SALIDAENPIEZA,corte.hrsEMBC, hcpag.horaspagadas, truncate((corte.hrsEMBC/hcpag.horaspagadas)*100,2) as eficiencia \n" +
"from   (select  c.LINEA, c.CODIGO, c.TURNO, m.SALIDAENPIEZA, g.HCDIRCORTE as cortee, \n" +
"SUM(TRUNCATE((((g.HCDIRCORTE*m.SALIDAENPIEZA)/100)),2)) as hrsEMBC,   g.HCDIRLPS as 'POND.LPS', \n" +
"TRUNCATE( if(c.TURNO='B' or c.TURNO='C', ((g.HCDIRLPS*m.SALIDAENPIEZA)/7.9/100),((g.HCDIRLPS *m.SALIDAENPIEZA)/9/100)) ,2) as LPS, \n" +
"g.HCDIRENSFINAL as 'PUNTOS.EF', TRUNCATE( if(c.TURNO='B' or c.TURNO='C',((g.HCDIRENSFINAL*m.SALIDAENPIEZA)/7.9/100), \n" +
"((g.HCDIRENSFINAL *m.SALIDAENPIEZA)/9/100)),2) as ENSFINAL  FROM codigos as c, manufactura as m, gsd  as g\n" +
"where c.IDCODIGO=m.IDCODIGO and c.IDCODIGO=g.IDCODIGO and m.activo=1 AND C.CADENA<>6 GROUP BY c.TURNO) as corte,\n" +
"(select c.plataforma,c.cadena,c.linea,c.CODIGO, HCDIRLINEA, C.TURNO,c.activo, case c.TURNO when 'B' THEN SUM((HCDIRLINEA)*7.9) \n" +
"WHEN 'A' THEN sum((HCDIRLINEA)*9) \n" +
"WHEN 'C' THEN sum((m.HCDIRLINEA)*7.32)  END \n" +
"as horaspagadas from manufactura as m INNER JOIN codigos as c ON m.IDCODIGO=c.IDCODIGO where  c.CADENA=4  GROUP BY c.TURNO) as hcpag WHERE hcpag.TURNO=corte.TURNO \n" +
"union\n" +
"SELECT codigos.PLATAFORMA,codigos.CODIGO,codigos.CADENA,codigos.LINEA,codigos.TURNO,manufactura.HCDIRLINEA,manufactura.SALIDAENPIEZA, IF(CODIGOS.CODIGO='SERV' AND codigos.CADENA=1 and codigos.TURNO='A', 15,0)as horasemb, IF(CODIGOS.CODIGO='SERV' AND codigos.CADENA=1 and codigos.TURNO='A',manufactura.HCDIRLINEA*9,manufactura.HCDIRLINEA*7.9)as horapag ,IF(CODIGOS.CODIGO='SERV' AND codigos.CADENA=1 and codigos.TURNO='A', (15/(manufactura.HCDIRLINEA*9)),(0/(manufactura.HCDIRLINEA*7.9)))as efic FROM manufactura , codigos WHERE  manufactura.IDCODIGO = codigos.IDCODIGO and CODIGOS.CODIGO='SERV'\n" +
"union all\n" +
"SELECT codigos.PLATAFORMA,codigos.CODIGO,codigos.CADENA,codigos.LINEA,codigos.turno, manufactura.HCDIRLINEA,manufactura.SALIDAENPIEZA,0,IF(CODIGOS.PLATAFORMA='EXTRAS GERENCIA' AND codigos.CADENA=7 and codigos.TURNO='A', manufactura.HCDIRLINEA*9,manufactura.HCDIRLINEA*7.9)as horapag,  0  FROM manufactura , codigos WHERE manufactura.IDCODIGO = codigos.IDCODIGO and CODIGOS.linea='91' \n" +
"     \n" +
"\n" +
" ");
     
    // 1      2        3         4                 5               6        7          8      9
             modelotemp.setColumnIdentifiers(new Object[]{"PLATAFORMA","CODIGO","CADENA", "LINEA", "TURNO", "HC","SALIDAPZA","HRS_ EMB","HRS_PAG","EFIC"});
             while(rs.next())
              {
                    modelotemp.addRow(new Object[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7),rs.getString(8), rs.getString(9), rs.getString(10)});
              }
         }catch(Exception e)
         {
             System.out.println(e+"get modelo");
         }
          return modelotemp;
      } 
      
        
                       public static DefaultTableModel getModeloplataformatOTALES() throws SQLException{
         DefaultTableModel modelotemp=null;
         
          try{
              modelotemp=new DefaultTableModel();
     ResultSet rs=Principal.cn.GetConsulta("select DISTINCT manu.plataforma as PLATAFORMA,ROUND(sum(IF(manu.LINEA='CORTE' AND manu.CADENA=6,cortehrsemb.hrsEMBC,manu.horasemb)),2)as HRS_EMBARCADAS, ROUND(sum(manu.hrsPagadas),2) as HRS_PAGADAS, sum(manu.hc)   from \n" +
"(select c.plataforma,c.arnes,c.LINEA, c.CADENA, c.turno, sum(p.hcdirecto) as hc,round( sum((m.PUNTOSPZAPOND*m.SALIDAENPIEZA/100)),2) as horasemb,  IF(c.TURNO='A', sum(((elinea+estaciones+ kits+HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*9)),\n" +
"sum(((elinea+estaciones+ kits+ HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*7.9))) as hrsPagadas, m.activo  from manufactura as m, personas  as p, codigos as c where m.idcodigo=c.IDCODIGO and  m.idcodigo=p.IDCODIGO and c.CADENA<>4  and c.CADENA<>6 GROUP BY c.idcodigo order by c.TURNO) as manu ,\n" +
"(SELECT SUM(TRUNCATE((((g.HCDIRCORTE*m.SALIDAENPIEZA)/100)),2)) as hrsEMBC FROM gsd as g, manufactura as m, codigos as c where c.IDCODIGO=m.IDCODIGO and c.IDCODIGO=g.IDCODIGO and m.activo=1 and c.TURNO='B' GROUP BY c.TURNO) as cortehrsemb\n" +
"where manu.CADENA<>4 and manu.activo<>0 AND manu.LINEA<>'29A'  group by manu.plataforma  \n" +
"UNION ALL\n" +
"\n" +
"SELECT DISTINCT hcpag.plataforma ,(corte.hrsEMBC),(hcpag.horaspagadas), (hcpag.HCDIRLINEA)\n" +
"from   (select  c.LINEA, c.CODIGO, c.TURNO, m.SALIDAENPIEZA, g.HCDIRCORTE as cortee,  \n" +
"SUM(TRUNCATE((((g.HCDIRCORTE*m.SALIDAENPIEZA)/100)),2)) as hrsEMBC,   g.HCDIRLPS as 'POND.LPS', \n" +
"TRUNCATE( if(c.TURNO='B' or c.TURNO='C', ((g.HCDIRLPS*m.SALIDAENPIEZA)/7.9/100),((g.HCDIRLPS *m.SALIDAENPIEZA)/9/100)) ,2) as LPS, \n" +
"g.HCDIRENSFINAL as 'PUNTOS.EF', TRUNCATE( if(c.TURNO='B' or c.TURNO='C',((g.HCDIRENSFINAL*m.SALIDAENPIEZA)/7.9/100), \n" +
"((g.HCDIRENSFINAL *m.SALIDAENPIEZA)/9/100)),2) as ENSFINAL  FROM codigos as c, manufactura as m, gsd as g\n" +
"where c.IDCODIGO=m.IDCODIGO and c.IDCODIGO=g.IDCODIGO and m.activo=1 AND C.CADENA<>6) as corte,\n" +
"(select c.plataforma,c.cadena,c.linea,c.CODIGO, SUM(m.HCDIRLINEA)AS HCDIRLINEA, C.TURNO,c.activo,sum( case c.TURNO when 'B' THEN m.HCDIRLINEA*7.9 \n" +
"WHEN 'A' THEN HCDIRLINEA*9\n" +
"END)as horaspagadas from manufactura as m INNER JOIN codigos as c ON m.IDCODIGO=c.IDCODIGO where c.CADENA=4) as hcpag   \n" +
"\n" +
"union all    \n" +
"SELECT codigos.PLATAFORMA,sum(IF(CODIGOS.PLATAFORMA='SERVICIOS' AND codigos.CADENA=1 and codigos.TURNO='A', 15,0))as horasemb,sum(IF(CODIGOS.PLATAFORMA='SERVICIOS' AND codigos.CADENA=1 and codigos.TURNO='A', manufactura.HCDIRLINEA*9,manufactura.HCDIRLINEA*7.9))as horapag ,SUM(manufactura.HCDIRLINEA) FROM manufactura , codigos WHERE  manufactura.IDCODIGO = codigos.IDCODIGO and CODIGOS.PLATAFORMA='SERVICIOS' AND codigos.CADENA<>6\n" +
"union all                                           \n" +
"SELECT codigos.PLATAFORMA,0,sum(IF(CODIGOS.PLATAFORMA='EXTRAS GERENCIA' AND codigos.CADENA=7 and codigos.TURNO='A', manufactura.HCDIRLINEA*9,manufactura.HCDIRLINEA*7.9))as horapago,SUM(manufactura.HCDIRLINEA)  FROM manufactura , codigos WHERE manufactura.IDCODIGO = codigos.IDCODIGO and CODIGOS.linea='91' \n" +
"     ");
    // 1      2        3         4                 5               6        7          8      9
             modelotemp.setColumnIdentifiers(new Object[]{"PLATAFORMA","HC TOTAL","HE (EF+LPS)","HP (EF+LPS)","  %  ","HC T. PLAT","HE (EF+LPS+CUT+PMS)"," HP (EF+LPS+CUT+PMS+EXT)"," EFIC"});
             while(rs.next())
              {
                    modelotemp.addRow(new Object[]{rs.getString(1),rs.getString(4), rs.getString(2), rs.getString(3)});
              }
         }catch(Exception e)
         {
             System.out.println(e+"get modelo");
         }
          return modelotemp;
      }  
            
                              public static DefaultTableModel getModelotOTALESXcodigo() throws SQLException{
         DefaultTableModel modelotemp=null;
         
          try{
              modelotemp=new DefaultTableModel();
     ResultSet rs=Principal.cn.GetConsulta("select DISTINCT SUBSTR(manu.CODIGO,1,9)AS CODIGO,ROUND(sum(IF(manu.LINEA='CORTE',cortehrsemb.hrsEMBC,manu.horasemb)),2)as HRS_EMBARCADAS, ROUND(sum(manu.hrsPagadas),2) as HRS_PAGADAS   from \n" +
"(select c.plataforma,c.CODIGO,c.arnes,c.LINEA, c.CADENA, c.turno, sum(p.hcdirecto) as hc,round( sum((m.PUNTOSPZAPOND*m.SALIDAENPIEZA/100)),2) as horasemb,  IF(c.TURNO='A', sum(((elinea+estaciones+ kits+HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*9)),\n" +
"sum(((elinea+estaciones+ kits+ HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*7.9))) as hrsPagadas, m.activo  from manufactura as m, personas  as p, codigos as c where m.idcodigo=c.IDCODIGO and  m.idcodigo=p.IDCODIGO and c.CADENA<>4  and c.CADENA<>6 GROUP BY c.idcodigo order by c.TURNO) as manu ,\n" +
"(SELECT SUM(TRUNCATE((((g.HCDIRCORTE*m.SALIDAENPIEZA)/100)),2)) as hrsEMBC FROM gsd as g, manufactura as m, codigos as c where c.IDCODIGO=m.IDCODIGO and c.IDCODIGO=g.IDCODIGO and m.activo=1  and c.TURNO='B' GROUP BY c.TURNO) as cortehrsemb\n" +
"where manu.CADENA<>4 and manu.activo<>0 AND manu.LINEA<>'29A' GROUP BY SUBSTR(manu.CODIGO,1,5) \n" +
"UNION ALL\n" +
"SELECT DISTINCT hcpag.plataforma ,ROUND((corte.hrsEMBC),2),(hcpag.horaspagadas)\n" +
"from   (select  c.LINEA, c.CODIGO, c.TURNO, m.SALIDAENPIEZA, g.HCDIRCORTE as cortee,  \n" +
"SUM(TRUNCATE((((g.HCDIRCORTE*m.SALIDAENPIEZA)/100)),2)) as hrsEMBC,   g.HCDIRLPS as 'POND.LPS', \n" +
"TRUNCATE( if(c.TURNO='B' or c.TURNO='C', ((g.HCDIRLPS*m.SALIDAENPIEZA)/7.9/100),((g.HCDIRLPS *m.SALIDAENPIEZA)/9/100)) ,2) as LPS,\n" +
"g.HCDIRENSFINAL as 'PUNTOS.EF', TRUNCATE( if(c.TURNO='B' or c.TURNO='C',((g.HCDIRENSFINAL*m.SALIDAENPIEZA)/7.9/100), \n" +
"((g.HCDIRENSFINAL *m.SALIDAENPIEZA)/9/100)),2) as ENSFINAL  FROM codigos as c, manufactura as m, gsd  as g\n" +
"where c.IDCODIGO=m.IDCODIGO and c.IDCODIGO=g.IDCODIGO and m.activo=1 AND C.CADENA<>6) as corte,\n" +
"(select c.plataforma,c.cadena,c.linea,c.CODIGO, HCDIRLINEA, C.TURNO,c.activo,sum( case c.TURNO when 'B' THEN HCDIRLINEA*7.9 \n" +
"WHEN 'A' THEN HCDIRLINEA*9\n" +
" END)as horaspagadas from manufactura as m INNER JOIN codigos as c ON m.IDCODIGO=c.IDCODIGO where c.CADENA=4) as hcpag   \n" +
"union all \n" +
"SELECT codigos.PLATAFORMA,sum(IF(CODIGOS.PLATAFORMA='SERVICIOS' AND codigos.CADENA=1 and codigos.TURNO='A',15,0))as horasemb,sum(IF(CODIGOS.PLATAFORMA='SERVICIOS' AND codigos.CADENA=1 and codigos.TURNO='A', manufactura.HCDIRLINEA*9,manufactura.HCDIRLINEA*7.9))as horapag  FROM manufactura , codigos WHERE  manufactura.IDCODIGO = codigos.IDCODIGO and CODIGOS.PLATAFORMA='SERVICIOS' AND codigos.CADENA<>6\n" +
"union all                                          \n" +
"SELECT codigos.PLATAFORMA,0,sum(IF(CODIGOS.PLATAFORMA='EXTRAS GERENCIA' AND codigos.CADENA=7 and codigos.TURNO='A', manufactura.HCDIRLINEA*9,manufactura.HCDIRLINEA*7.9))as horapago  FROM manufactura , codigos WHERE manufactura.IDCODIGO = codigos.IDCODIGO and CODIGOS.linea='91' \n" +
"      ");
     
    // 1      2        3         4                 5               6        7          8      9
             modelotemp.setColumnIdentifiers(new Object[]{"CODIGO","HE (EF+LPS)","HP (EF+LPS)","  %  ","HE (EF+LPS+CUT+PMS)"," HP (EF+LPS+CUT+PMS+EXT)"," EFIC"});
             while(rs.next())
              {
                    modelotemp.addRow(new Object[]{rs.getString(1), rs.getString(2), rs.getString(3)});
              }
         }catch(Exception e)
         {
             System.out.println(e+"get modelo");
         }
          return modelotemp;
      }  
                       
                       
                       
                       
            
                      public static DefaultTableModel getModeloplataformaGMTLM() throws SQLException{
         DefaultTableModel modelotemp=null;
         
          try{
              modelotemp=new DefaultTableModel();
     ResultSet rs=Principal.cn.GetConsulta("select DISTINCT manu.plataforma, manu.codigo,manu.CADENA,manu.LINEA, manu.turno, manu.hc,IF(manu.LINEA='CORTE' AND manu.CADENA=6,cortehrsemb.hrsEMBC,manu.horasemb)as horasemb, (manu.hrsPagadas) as hrsPagadas,ROUND(IF(manu.LINEA='CORTE' AND manu.CADENA=6,(cortehrsemb.hrsEMBC/manu.hrsPagadas)*100,(manu.horasemb/manu.hrsPagadas)*100),2)as efic   from \n" +
"(select c.plataforma,c.CODIGO,c.arnes,c.LINEA, c.CADENA, c.turno, sum(p.hcdirecto) as hc,round( sum((m.PUNTOSPZAPOND*m.SALIDAENPIEZA/100)),2) as horasemb,  IF(c.TURNO='A', sum(((elinea+estaciones+ kits+HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*9)),\n" +
"sum(((elinea+estaciones+ kits+ HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*7.9))) as hrsPagadas, m.activo  from manufactura as m, personas  as p, codigos as c where m.idcodigo=c.IDCODIGO and  m.idcodigo=p.IDCODIGO and c.CADENA<>4  and c.CADENA<>6 and c.PLATAFORMA='GMT 610'  GROUP BY c.idcodigo order by c.PLATAFORMA) as manu ,\n" +
"(SELECT SUM(TRUNCATE((((g.HCDIRCORTE*m.SALIDAENPIEZA)/100)),2)) as hrsEMBC FROM gsd as g, manufactura as m, codigos as c where c.IDCODIGO=m.IDCODIGO and c.IDCODIGO=g.IDCODIGO and m.activo=1 AND C.CADENA=6 and c.TURNO='B' GROUP BY c.TURNO) as cortehrsemb\n" +
"where manu.CADENA<>4 and manu.activo<>0");
                        //                                     1      2        3         4                 5               6        7          8      9
             modelotemp.setColumnIdentifiers(new Object[]{"PLATAFORMA","CODIGO","CADENA", "LINEA", "TURNO", "HC","HRS_ EMB","HRS_PAG","EFIC"});
             while(rs.next())
              {
                    modelotemp.addRow(new Object[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7),rs.getString(8), rs.getString(9)});
              }
         }catch(Exception e)
         {
             System.out.println(e+"get modelo");
         }
          return modelotemp;
      }
                      
                      
                      
                      
                      
                      
                      
                      public static DefaultTableModel getModeloplataformaLMK2XX() throws SQLException{
         DefaultTableModel modelotemp=null;
         
          try{
              modelotemp=new DefaultTableModel();
     ResultSet rs=Principal.cn.GetConsulta("select DISTINCT manu.plataforma, manu.codigo,manu.CADENA,manu.LINEA, manu.turno, manu.hc,IF(manu.LINEA='CORTE' AND manu.CADENA=6,cortehrsemb.hrsEMBC,manu.horasemb)as horasemb, (manu.hrsPagadas) as hrsPagadas,ROUND(IF(manu.LINEA='CORTE' AND manu.CADENA=6,(cortehrsemb.hrsEMBC/manu.hrsPagadas)*100,(manu.horasemb/manu.hrsPagadas)*100),2)as efic   from \n" +
"(select c.plataforma,c.CODIGO,c.arnes,c.LINEA, c.CADENA, c.turno, sum(p.hcdirecto) as hc,round( sum((m.PUNTOSPZAPOND*m.SALIDAENPIEZA/100)),2) as horasemb,  IF(c.TURNO='A', sum(((elinea+estaciones+ kits+HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*9)),\n" +
"sum(((elinea+estaciones+ kits+ HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*7.9))) as hrsPagadas, m.activo  from manufactura as m, codigos as c , personas  as p where m.idcodigo=c.IDCODIGO and  m.idcodigo=p.IDCODIGO and c.CADENA<>4  and c.CADENA<>6 and c.PLATAFORMA='K2XX'  GROUP BY c.idcodigo order by c.PLATAFORMA) as manu ,\n" +
"(SELECT SUM(TRUNCATE((((g.HCDIRCORTE*m.SALIDAENPIEZA)/100)),2)) as hrsEMBC FROM gsd as g, manufactura as m, codigos as c where c.IDCODIGO=m.IDCODIGO and c.IDCODIGO=g.IDCODIGO and m.activo=1 AND C.CADENA=6 and c.TURNO='B' GROUP BY c.TURNO) as cortehrsemb\n" +
"where manu.CADENA<>4 and manu.activo<>0 ");
                        //                                     1      2        3         4                 5               6        7          8      9
             modelotemp.setColumnIdentifiers(new Object[]{"PLATAFORMA","CODIGO","CADENA", "LINEA", "TURNO", "HC","HRS_ EMB","HRS_PAG","EFIC"});
             while(rs.next())
              {
                    modelotemp.addRow(new Object[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7),rs.getString(8), rs.getString(9)});
              }
         }catch(Exception e)
         {
             System.out.println(e+"get modelo");
         }
          return modelotemp;
      }
            
                      
                       public static DefaultTableModel getModeloplataformaLMT1XX() throws SQLException{
         DefaultTableModel modelotemp=null;
         
          try{
              modelotemp=new DefaultTableModel();
     ResultSet rs=Principal.cn.GetConsulta("select DISTINCT manu.plataforma, manu.codigo,manu.CADENA,manu.LINEA, manu.turno, manu.hc,IF(manu.LINEA='CORTE' AND manu.CADENA=6,cortehrsemb.hrsEMBC,manu.horasemb)as horasemb, (manu.hrsPagadas) as hrsPagadas,ROUND(IF(manu.LINEA='CORTE' AND manu.CADENA=6,(cortehrsemb.hrsEMBC/manu.hrsPagadas)*100,(manu.horasemb/manu.hrsPagadas)*100),2)as efic   from \n" +
"(select c.plataforma,c.CODIGO,c.arnes,c.LINEA, c.CADENA, c.turno, sum(p.hcdirecto) as hc,round( sum((m.PUNTOSPZAPOND*m.SALIDAENPIEZA/100)),2) as horasemb,  IF(c.TURNO='A', sum(((elinea+estaciones+ kits+HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*9)),\n" +
"sum(((elinea+estaciones+ kits+ HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*7.9))) as hrsPagadas, m.activo  from manufactura as m, codigos as c, personas  as p where m.idcodigo=c.IDCODIGO and  m.idcodigo=p.IDCODIGO and c.CADENA<>4  and c.CADENA<>6 and c.PLATAFORMA='T1XX'  GROUP BY c.idcodigo order by c.PLATAFORMA) as manu ,\n" +
"(SELECT SUM(TRUNCATE((((g.HCDIRCORTE*m.SALIDAENPIEZA)/100)),2)) as hrsEMBC FROM gsd as g, manufactura as m, codigos as c where c.IDCODIGO=m.IDCODIGO and c.IDCODIGO=g.IDCODIGO and m.activo=1 AND C.CADENA=6 and c.TURNO='B' GROUP BY c.TURNO) as cortehrsemb\n" +
"where manu.CADENA<>4 and manu.activo<>0\n" +
"");
                        //                                     1      2        3         4                 5               6        7          8      9
             modelotemp.setColumnIdentifiers(new Object[]{"PLATAFORMA","CODIGO","CADENA", "LINEA", "TURNO", "HC","HRS_ EMB","HRS_PAG","EFIC"});
             while(rs.next())
              {
                    modelotemp.addRow(new Object[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7),rs.getString(8), rs.getString(9)});
              }
         }catch(Exception e)
         {
             System.out.println(e+"get modelo");
         }
          return modelotemp;
      }
                          
          public static DefaultTableModel getModeloplataformaLME2XX() throws SQLException{
         DefaultTableModel modelotemp=null;
         
          try{
              modelotemp=new DefaultTableModel();
     ResultSet rs=Principal.cn.GetConsulta("select DISTINCT manu.plataforma, manu.codigo,manu.CADENA,manu.LINEA, manu.turno, manu.hc,IF(manu.LINEA='CORTE' AND manu.CADENA=6,cortehrsemb.hrsEMBC,manu.horasemb)as horasemb, (manu.hrsPagadas) as hrsPagadas,ROUND(IF(manu.LINEA='CORTE' AND manu.CADENA=6,(cortehrsemb.hrsEMBC/manu.hrsPagadas)*100,(manu.horasemb/manu.hrsPagadas)*100),2)as efic   from \n" +
"(select c.plataforma,c.CODIGO,c.arnes,c.LINEA, c.CADENA, c.turno, sum(p.hcdirecto) as hc,round( sum((m.PUNTOSPZAPOND*m.SALIDAENPIEZA/100)),2) as horasemb,  IF(c.TURNO='A', sum(((elinea+estaciones+ kits+HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*9)),\n" +
"sum(((elinea+estaciones+ kits+ HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*7.9))) as hrsPagadas, m.activo  from manufactura as m, codigos as c , personas  as p where m.idcodigo=c.IDCODIGO and  m.idcodigo=p.IDCODIGO and c.CADENA<>4  and c.CADENA<>6 and c.PLATAFORMA='E2XX'  GROUP BY c.idcodigo order by c.PLATAFORMA) as manu ,\n" +
"(SELECT SUM(TRUNCATE((((g.HCDIRCORTE*m.SALIDAENPIEZA)/100)),2)) as hrsEMBC FROM gsd as g, manufactura as m, codigos as c where c.IDCODIGO=m.IDCODIGO and c.IDCODIGO=g.IDCODIGO and m.activo=1 AND C.CADENA=6 and c.TURNO='B' GROUP BY c.TURNO) as cortehrsemb\n" +
"where manu.CADENA<>4 and manu.activo<>0 ");
                        //                                     1      2        3         4                 5               6        7          8      9
             modelotemp.setColumnIdentifiers(new Object[]{"PLATAFORMA","CODIGO","CADENA", "LINEA", "TURNO", "HC","HRS_ EMB","HRS_PAG","EFIC"});
             while(rs.next())
              {
                    modelotemp.addRow(new Object[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7),rs.getString(8), rs.getString(9)});
              }
         }catch(Exception e)
         {
             System.out.println(e+"get modelo");
         }
          return modelotemp;
      }             
           
          
           public static DefaultTableModel getModeloplataformaLMISUZU() throws SQLException{
         DefaultTableModel modelotemp=null;
         
          try{
              modelotemp=new DefaultTableModel();
     ResultSet rs=Principal.cn.GetConsulta("select DISTINCT manu.plataforma, manu.codigo,manu.CADENA,manu.LINEA, manu.turno, manu.hc,IF(manu.LINEA='CORTE' AND manu.CADENA=6,cortehrsemb.hrsEMBC,manu.horasemb)as horasemb, (manu.hrsPagadas) as hrsPagadas,ROUND(IF(manu.LINEA='CORTE' AND manu.CADENA=6,(cortehrsemb.hrsEMBC/manu.hrsPagadas)*100,(manu.horasemb/manu.hrsPagadas)*100),2)as efic   from \n" +
"(select c.plataforma,c.CODIGO,c.arnes,c.LINEA, c.CADENA, c.turno, sum(p.hcdirecto) as hc,round( sum((m.PUNTOSPZAPOND*m.SALIDAENPIEZA/100)),2) as horasemb,  IF(c.TURNO='A', sum(((elinea+estaciones+ kits+HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*9)),\n" +
"sum(((elinea+estaciones+ kits+ HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*7.9))) as hrsPagadas, m.activo  from manufactura as m, codigos as c , personas  as p where m.idcodigo=c.IDCODIGO and  m.idcodigo=p.IDCODIGO  and c.CADENA<>4  and c.CADENA<>6 and c.PLATAFORMA='ISUZU'  GROUP BY c.idcodigo order by c.PLATAFORMA) as manu ,\n" +
"(SELECT SUM(TRUNCATE((((g.HCDIRCORTE*m.SALIDAENPIEZA)/100)),2)) as hrsEMBC FROM gsd as g, manufactura as m, codigos as c where c.IDCODIGO=m.IDCODIGO and c.IDCODIGO=g.IDCODIGO and m.activo=1 AND C.CADENA=6 and c.TURNO='B' GROUP BY c.TURNO) as cortehrsemb\n" +
"where manu.CADENA<>4 and manu.activo<>0 ");
                        //                                     1      2        3         4                 5               6        7          8      9
             modelotemp.setColumnIdentifiers(new Object[]{"PLATAFORMA","CODIGO","CADENA", "LINEA", "TURNO", "HC","HRS_ EMB","HRS_PAG","EFIC"});
             while(rs.next())
              {
                    modelotemp.addRow(new Object[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7),rs.getString(8), rs.getString(9)});
              }
         }catch(Exception e)
         {
             System.out.println(e+"get modelo");
         }
          return modelotemp;
      }             
               
            public static DefaultTableModel getModeloplataformaLMSERVICIOS() throws SQLException{
         DefaultTableModel modelotemp=null;
         
          try{
              modelotemp=new DefaultTableModel();
     ResultSet rs=Principal.cn.GetConsulta("SELECT codigos.PLATAFORMA,manufactura.IDCODIGO,codigos.CADENA,codigos.LINEA,codigos.TURNO,manufactura.HCDIRLINEA, IF(CODIGOS.PLATAFORMA='SERVICIOS' AND codigos.CADENA=1 and codigos.TURNO='A', 15,0)as horasemb, IF(CODIGOS.PLATAFORMA='SERVICIOS' AND codigos.CADENA=1 and codigos.TURNO='A', manufactura.HCDIRLINEA*9,manufactura.HCDIRLINEA*7.9)as horapag ,IF(CODIGOS.PLATAFORMA='SERVICIOS' AND codigos.CADENA=1 and codigos.TURNO='A', (15/(manufactura.HCDIRLINEA*9)),(0/(manufactura.HCDIRLINEA*7.9)))as efic FROM manufactura , codigos WHERE  manufactura.IDCODIGO = codigos.IDCODIGO and CODIGOS.PLATAFORMA='SERVICIOS'  ");
                        //                                     1      2        3         4                 5               6        7          8      9
             modelotemp.setColumnIdentifiers(new Object[]{"PLATAFORMA","CODIGO","CADENA", "LINEA", "TURNO", "HC","HRS_ EMB","HRS_PAG","EFIC"});
             while(rs.next())
              {
                  
                    modelotemp.addRow(new Object[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7),rs.getString(8), rs.getString(9)});
              }
         }catch(Exception e)
         {
             System.out.println(e+"get modelo");
         }
          return modelotemp;
      }    
            
                   public static DefaultTableModel getModeloplataformaLMCORTE() throws SQLException{
         DefaultTableModel modelotemp=null;
         
          try{
              modelotemp=new DefaultTableModel();
     ResultSet rs=Principal.cn.GetConsulta("SELECT DISTINCT hcpag.plataforma,hcpag.CODIGO,hcpag.cadena, hcpag.linea,hcpag.turno, hcpag.HCDIRLINEA as hc ,corte.hrsEMBC, hcpag.horaspagadas, truncate((corte.hrsEMBC/hcpag.horaspagadas)*100,2) as eficiencia \n" +
"from   (select  c.LINEA, c.CODIGO, c.TURNO, m.SALIDAENPIEZA, g.HCDIRCORTE as cortee,  \n" +
"SUM(TRUNCATE((((g.HCDIRCORTE*m.SALIDAENPIEZA)/100)),2)) as hrsEMBC,   g.HCDIRLPS as 'POND.LPS', \n" +
"TRUNCATE( if(c.TURNO='B' or c.TURNO='C', ((g.HCDIRLPS*m.SALIDAENPIEZA)/7.9/100),((g.HCDIRLPS *m.SALIDAENPIEZA)/9/100)) ,2) as LPS, \n" +
"g.HCDIRENSFINAL as 'PUNTOS.EF', TRUNCATE( if(c.TURNO='B' or c.TURNO='C',((g.HCDIRENSFINAL*m.SALIDAENPIEZA)/7.9/100), \n" +
"((g.HCDIRENSFINAL *m.SALIDAENPIEZA)/9/100)),2) as ENSFINAL  FROM codigos as c, manufactura as m, gsd  as g\n" +
"where c.IDCODIGO=m.IDCODIGO and c.IDCODIGO=g.IDCODIGO and m.activo=1 AND C.CADENA<>6 GROUP BY c.TURNO) as corte,\n" +
"(select c.plataforma,c.cadena,c.linea,c.CODIGO, HCDIRLINEA, C.TURNO,c.activo, case c.TURNO when 'B' THEN SUM((HCDIRLINEA)*7.9) \n" +
"WHEN 'A' THEN sum((HCDIRLINEA)*9) \n" +
"WHEN 'C' THEN sum((m.HCDIRLINEA)*7.32)  END \n" +
"as horaspagadas from manufactura as m INNER JOIN codigos as c ON m.IDCODIGO=c.IDCODIGO where  c.CADENA=4  GROUP BY c.TURNO) as hcpag WHERE hcpag.TURNO=corte.TURNO");
                        //                                     1      2        3         4                 5               6        7          8      9
             modelotemp.setColumnIdentifiers(new Object[]{"PLATAFORMA","CODIGO","CADENA", "LINEA", "TURNO", "HC","HRS_ EMB","HRS_PAG","EFIC"});
             while(rs.next())
              {
                    modelotemp.addRow(new Object[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7),rs.getString(8), rs.getString(9)});
              }
         }catch(Exception e)
         {
             System.out.println(e+"get modelo");
         }
          return modelotemp;
      } 
                      
                   
                   
                   
                      public static DefaultTableModel getModeloplataformaGMTGML() throws SQLException{
         DefaultTableModel modelotemp=null;
         
          try{
              modelotemp=new DefaultTableModel();
     ResultSet rs=Principal.cn.GetConsulta("select DISTINCT manu.plataforma, manu.codigo,manu.CADENA,manu.LINEA, manu.turno, manu.hc,IF(manu.LINEA='CORTE' AND manu.CADENA=6,cortehrsemb.hrsEMBC,manu.horasemb)as horasemb, (manu.hrsPagadas) as hrsPagadas,ROUND(IF(manu.LINEA='CORTE' AND manu.CADENA=6,(cortehrsemb.hrsEMBC/manu.hrsPagadas)*100,(manu.horasemb/manu.hrsPagadas)*100),2)as efic   from \n" +
"(select c.plataforma,c.CODIGO,c.arnes,c.LINEA, c.CADENA, c.turno, sum(p.hcdirecto) as hc,round( sum((m.PUNTOSPZAPOND*m.SALIDAENPIEZA/100)),2) as horasemb,  IF(c.TURNO='A', sum(((elinea+estaciones+ kits+HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*9)),\n" +
"sum(((elinea+estaciones+ kits+ HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*7.9))) as hrsPagadas, m.activo  from manufactura as m, codigos as c,personas  as p where m.idcodigo=c.IDCODIGO and  m.idcodigo=p.IDCODIGO and c.CADENA<>4  and c.CADENA=6 and c.PLATAFORMA='GMT 610'  GROUP BY c.idcodigo order by c.PLATAFORMA) as manu ,\n" +
"(SELECT SUM(TRUNCATE((((g.HCDIRCORTE*m.SALIDAENPIEZA)/100)),2)) as hrsEMBC FROM gsd as g, manufactura as m, codigos as c where c.IDCODIGO=m.IDCODIGO and c.IDCODIGO=g.IDCODIGO and m.activo=1 AND C.CADENA=6 and c.TURNO='B' GROUP BY c.TURNO) as cortehrsemb\n" +
"where manu.CADENA<>4 and manu.activo<>0 ");
                        //                                     1      2        3         4                 5               6        7          8      9
             modelotemp.setColumnIdentifiers(new Object[]{"PLATAFORMA","CODIGO","CADENA", "LINEA", "TURNO", "HC","HRS_ EMB","HRS_PAG","EFIC"});
             while(rs.next())
              {
                    modelotemp.addRow(new Object[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7),rs.getString(8), rs.getString(9)});
              }
         }catch(Exception e)
         {
             System.out.println(e+"get modelo");
         }
          return modelotemp;
      }
                   
            
                       public static DefaultTableModel getModeloplataformaGMLK2XX() throws SQLException{
         DefaultTableModel modelotemp=null;
         
          try{
              modelotemp=new DefaultTableModel();
     ResultSet rs=Principal.cn.GetConsulta("select DISTINCT manu.plataforma, manu.codigo,manu.CADENA,manu.LINEA, manu.turno, manu.hc,IF(manu.LINEA='CORTE' AND manu.CADENA=6,cortehrsemb.hrsEMBC,manu.horasemb)as horasemb, (manu.hrsPagadas) as hrsPagadas,ROUND(IF(manu.LINEA='CORTE' AND manu.CADENA=6,(cortehrsemb.hrsEMBC/manu.hrsPagadas)*100,(manu.horasemb/manu.hrsPagadas)*100),2)as efic   from \n" +
"(select c.plataforma,c.CODIGO,c.arnes,c.LINEA, c.CADENA, c.turno, sum(p.hcdirecto) as hc,round( sum((m.PUNTOSPZAPOND*m.SALIDAENPIEZA/100)),2) as horasemb,  IF(c.TURNO='A', sum(((elinea+estaciones+ kits+HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*9)),\n" +
"sum(((elinea+estaciones+ kits+ HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*7.9))) as hrsPagadas, m.activo  from manufactura as m, codigos as c, personas  as p where m.idcodigo=c.IDCODIGO  and  m.idcodigo=p.IDCODIGO and c.CADENA<>4  and c.CADENA=6 and c.PLATAFORMA='K2XX' and c.codigo<>'corte'  GROUP BY c.idcodigo order by c.PLATAFORMA) as manu ,\n" +
"(SELECT SUM(TRUNCATE((((g.HCDIRCORTE*m.SALIDAENPIEZA)/100)),2)) as hrsEMBC FROM gsd as g, manufactura as m, codigos as c where c.IDCODIGO=m.IDCODIGO and c.IDCODIGO=g.IDCODIGO and m.activo=1 AND C.CADENA=6 and c.TURNO='B' GROUP BY c.TURNO) as cortehrsemb\n" +
"where manu.CADENA<>4 and manu.activo<>0");
                        //                                     1      2        3         4                 5               6        7          8      9
             modelotemp.setColumnIdentifiers(new Object[]{"PLATAFORMA","CODIGO","CADENA", "LINEA", "TURNO", "HC","HRS_ EMB","HRS_PAG","EFIC"});
             while(rs.next())
              {
                    modelotemp.addRow(new Object[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7),rs.getString(8), rs.getString(9)});
              }
         }catch(Exception e)
         {
             System.out.println(e+"get modelo");
         }
          return modelotemp;
      }
                       
                       
        public static DefaultTableModel getModeloplataformaGMLT1XX() throws SQLException{
         DefaultTableModel modelotemp=null;
         
          try{
              modelotemp=new DefaultTableModel();
     ResultSet rs=Principal.cn.GetConsulta("select DISTINCT manu.plataforma, manu.codigo,manu.CADENA,manu.LINEA, manu.turno, manu.hc,IF(manu.LINEA='CORTE' AND manu.CADENA=6,cortehrsemb.hrsEMBC,manu.horasemb)as horasemb, (manu.hrsPagadas) as hrsPagadas,ROUND(IF(manu.LINEA='CORTE' AND manu.CADENA=6,(cortehrsemb.hrsEMBC/manu.hrsPagadas)*100,(manu.horasemb/manu.hrsPagadas)*100),2)as efic   from \n" +
"(select c.plataforma,c.CODIGO,c.arnes,c.LINEA, c.CADENA, c.turno, sum(p.hcdirecto) as hc,round( sum((m.PUNTOSPZAPOND*m.SALIDAENPIEZA/100)),2) as horasemb,  IF(c.TURNO='A', sum(((elinea+estaciones+ kits+HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*9)),\n" +
"sum(((elinea+estaciones+ kits+ HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*7.9))) as hrsPagadas, m.activo  from manufactura as m, codigos as c, personas  as p where m.idcodigo=c.IDCODIGO and  m.idcodigo=p.IDCODIGO and c.CADENA<>4  and c.CADENA=6 and c.PLATAFORMA='T1XX' GROUP BY c.idcodigo order by c.PLATAFORMA) as manu ,\n" +
"(SELECT SUM(TRUNCATE((((g.HCDIRCORTE*m.SALIDAENPIEZA)/100)),2)) as hrsEMBC FROM gsd as g, manufactura as m, codigos as c where c.IDCODIGO=m.IDCODIGO and c.IDCODIGO=g.IDCODIGO and m.activo=1 AND C.CADENA=6 and c.TURNO='B' GROUP BY c.TURNO) as cortehrsemb\n" +
"where manu.CADENA<>4 and manu.activo<>0");
                        //                                     1      2        3         4                 5               6        7          8      9
             modelotemp.setColumnIdentifiers(new Object[]{"PLATAFORMA","CODIGO","CADENA", "LINEA", "TURNO", "HC","HRS_ EMB","HRS_PAG","EFIC"});
             while(rs.next())
              {
                    modelotemp.addRow(new Object[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7),rs.getString(8), rs.getString(9)});
              }
         }catch(Exception e)
         {
             System.out.println(e+"get modelo");
         }
          return modelotemp;
      }          
        
        
        
         public static DefaultTableModel getModeloplataformaGMLcorte() throws SQLException{
         DefaultTableModel modelotemp=null;
         
          try{
              modelotemp=new DefaultTableModel();
     ResultSet rs=Principal.cn.GetConsulta("select DISTINCT manu.codigo, manu.codigo,manu.CADENA,manu.LINEA, manu.turno, manu.hc,IF(manu.LINEA='CORTE' AND manu.CADENA=6,cortehrsemb.hrsEMBC,manu.horasemb)as horasemb, (manu.hrsPagadas) as hrsPagadas,ROUND(IF(manu.LINEA='CORTE' AND manu.CADENA=6,(cortehrsemb.hrsEMBC/manu.hrsPagadas)*100,(manu.horasemb/manu.hrsPagadas)*100),2)as efic   from \n" +
"(select c.plataforma,c.CODIGO,c.arnes,c.LINEA, c.CADENA, c.turno, sum(m.HCDIRLINEA) as hc,round( sum((m.PUNTOSPZAPOND*m.SALIDAENPIEZA/100)),2) as horasemb,  IF(c.TURNO='A', sum(((elinea+estaciones+ kits+HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*9)),\n" +
"sum(((elinea+estaciones+ kits+ HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*7.9))) as hrsPagadas, m.activo  from manufactura as m, codigos as c where m.idcodigo=c.IDCODIGO and c.CADENA<>4  and c.TURNO='A' and c.codigo='corte' AND C.CADENA=6 GROUP BY c.idcodigo order by c.PLATAFORMA) as manu ,\n" +
"(SELECT SUM(TRUNCATE((((g.HCDIRCORTE*m.SALIDAENPIEZA)/100)),2)) as hrsEMBC FROM gsd as g, manufactura as m, codigos as c where c.IDCODIGO=m.IDCODIGO and c.IDCODIGO=g.IDCODIGO and m.activo=1 AND C.CADENA=6 and c.TURNO='A' GROUP BY c.TURNO) as cortehrsemb\n" +
"where manu.CADENA<>4 and manu.activo<>0  UNION select DISTINCT manu.plataforma,manu.codigo,manu.CADENA,manu.LINEA, manu.turno, manu.hc,IF(manu.LINEA='CORTE' AND manu.CADENA=6,cortehrsemb.hrsEMBC,manu.horasemb)as horasemb, (manu.hrsPagadas) as hrsPagadas,ROUND(IF(manu.LINEA='CORTE' AND manu.CADENA=6,(cortehrsemb.hrsEMBC/manu.hrsPagadas)*100,(manu.horasemb/manu.hrsPagadas)*100),2)as efic   from \n" +
"(select c.plataforma,c.CODIGO,c.arnes,c.LINEA, c.CADENA, c.turno, sum(m.HCDIRLINEA) as hc,round( sum((m.PUNTOSPZAPOND*m.SALIDAENPIEZA/100)),2) as horasemb,  IF(c.TURNO='A', sum(((elinea+estaciones+ kits+HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*9)),\n" +
"sum(((elinea+estaciones+ kits+ HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*7.9))) as hrsPagadas, m.activo  from manufactura as m, codigos as c where m.idcodigo=c.IDCODIGO and c.CADENA<>4 and c.TURNO='B' and c.codigo='corte' AND C.CADENA=6 GROUP BY c.idcodigo) as manu,\n" +
"(SELECT SUM(TRUNCATE((((g.HCDIRCORTE*m.SALIDAENPIEZA)/100)),2)) as hrsEMBC FROM gsd as g, manufactura as m, codigos as c where c.IDCODIGO=m.IDCODIGO and c.IDCODIGO=g.IDCODIGO and m.activo=1 AND C.CADENA=6  and c.TURNO='B'  GROUP BY c.TURNO) as cortehrsemb\n" +
"where  manu.activo<>0 and manu.linea<>'29A' ");
                        //                                     1      2        3         4                 5               6        7          8      9
             modelotemp.setColumnIdentifiers(new Object[]{"PLATAFORMA","CODIGO","CADENA", "LINEA", "TURNO", "HC","HRS_ EMB","HRS_PAG","EFIC"});
             while(rs.next())
              {
                    modelotemp.addRow(new Object[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7),rs.getString(8), rs.getString(9)});
              }
         }catch(Exception e)
         {
             System.out.println(e+"get modelo");
         }
          return modelotemp;
      }                  
          
            public static DefaultTableModel getModeloplataformaGMLservicios() throws SQLException{
         DefaultTableModel modelotemp=null;
         
          try{
              modelotemp=new DefaultTableModel();
     ResultSet rs=Principal.cn.GetConsulta(" select DISTINCT manu.plataforma,manu.codigo,manu.CADENA,manu.LINEA, manu.turno, manu.hc,IF(manu.LINEA='CORTE' AND manu.CADENA=6,cortehrsemb.hrsEMBC,manu.horasemb)as horasemb, (manu.hrsPagadas) as hrsPagadas,ROUND(IF(manu.LINEA='CORTE' AND manu.CADENA=6,(cortehrsemb.hrsEMBC/manu.hrsPagadas)*100,(manu.horasemb/manu.hrsPagadas)*100),2)as efic   from \n" +
"(select c.plataforma,c.CODIGO,c.arnes,c.LINEA, c.CADENA, c.turno, sum(m.HCDIRLINEA) as hc,round( sum((m.PUNTOSPZAPOND*m.SALIDAENPIEZA/100)),2) as horasemb,  IF(c.TURNO='A', sum(((elinea+estaciones+ kits+HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*9)),\n" +
"sum(((elinea+estaciones+ kits+ HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*7.9))) as hrsPagadas, m.activo  from manufactura as m, codigos as c where m.idcodigo=c.IDCODIGO and c.CADENA<>4  and c.CADENA=6 and c.PLATAFORMA='N/A' GROUP BY c.idcodigo) as manu,\n" +
"(SELECT SUM(TRUNCATE((((g.HCDIRCORTE*m.SALIDAENPIEZA)/100)),2)) as hrsEMBC FROM gsd as g, manufactura as m, codigos as c where c.IDCODIGO=m.IDCODIGO and c.IDCODIGO=g.IDCODIGO and m.activo=1 AND C.CADENA=6  and c.TURNO='B'  GROUP BY c.TURNO) as cortehrsemb\n" +
"where  manu.activo<>0 and manu.linea<>'29A' ");
                        //                                     1      2        3         4                 5               6        7          8      9
             modelotemp.setColumnIdentifiers(new Object[]{"PLATAFORMA","CODIGO","CADENA", "LINEA", "TURNO", "HC","HRS_ EMB","HRS_PAG","EFIC"});
             while(rs.next())
              {
                    modelotemp.addRow(new Object[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7),rs.getString(8), rs.getString(9)});
              }
         }catch(Exception e)
         {
             System.out.println(e+"get modelo");
         }
          return modelotemp;
      }          
            
            
            
                  public static DefaultTableModel tablacatia() throws SQLException{
         DefaultTableModel modelotemp=null;
         
          try{
              modelotemp=new DefaultTableModel();
     ResultSet rs=Principal.cn.GetConsulta("SELECT c.CADENA,c.CODIGO,c.LINEA,c.TURNO,sum(m.elinea+m.estaciones+m.kits+m.HCDIRLINEA+m.hcdirconte+m.HCDIRSOPORTE+m.HCDIRTABINSP+m.hcrutasint+m.hcdirlps+m.HCDIRSOPLPS+m.hcdirpilotos+m.hcdirftq+m.hcdirsistemas) AS HC, SUM(g.HCDIRCORTE+g.hcdirlps+g.hcdirensfinal) AS 'PP CORTE + EF + LPS'\n" +
"FROM manufactura as m, codigos as c, gsd as g where m.IDCODIGO=c.IDCODIGO and c.IDCODIGO=g.IDCODIGO and m.ACTIVO=1 AND c.CADENA<>6 AND (c.TURNO='A' OR c.TURNO='B') GROUP BY c.LINEA,c.TURNO order by c.TURNO,c.CADENA,c.LINEA");
                        //                                     1      2        3         4        5                  6       
             modelotemp.setColumnIdentifiers(new Object[]{"CADENA","CODIGO","LINEA", "TURNO", "HC EF + LPS", "PP CORTE + EF + LPS"});
             while(rs.next())
              {
                    modelotemp.addRow(new Object[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)});
              }
         }catch(Exception e)
         {
             System.out.println(e+"get modelo");
         }
          return modelotemp;
      }    
         
                     
                  public static DefaultTableModel pp_corte() throws SQLException{
         DefaultTableModel modelotemp=null;
         
          try{
              modelotemp=new DefaultTableModel();
     ResultSet rs=Principal.cn.GetConsulta("select  c.CADENA, c.CODIGO,c.LINEA, c.TURNO, m.SALIDAENPIEZA AS SALIDA_PZA,\"0\" AS HC_DIRECTO,\"0\" AS CONV_POTS,\"0\" AS CONTENSION ,\"0\" AS SOPORTES,\"0\" AS TAB_INSP,\"0\" AS RUTAS_INT,\"0\" AS LPS,\"0\" AS SOP_LPS,\"0\" AS PILOTOS,\"0\" AS FTQ,\"0\" AS SISTEMAS,g.HCDIRCORTE AS PTOS_PZA_CUT, \n" +
"TRUNCATE((((g.HCDIRCORTE*m.SALIDAENPIEZA)/100)),2) as HRS_EMB ,\"0\" AS HRS_PAG,\"0\" AS EFIC, if(c.TURNO='A',9,7.9)as HRS_TURNO,ROUND((TRUNCATE((((g.HCDIRCORTE*m.SALIDAENPIEZA)/100)),2)/if(c.TURNO='A',9,7.9)),2) AS GENTE_GCSD   FROM codigos as c, manufactura as m, gsd  as g\n" +
"where c.IDCODIGO=m.IDCODIGO and c.IDCODIGO=g.IDCODIGO and m.activo=1 AND C.CADENA<>6 AND C.CADENA<>4 AND C.CADENA<>3  \n" +
"union all                                         \n" +
"SELECT DISTINCT  corte.CADENA,corte.LINEA,corte.CODIGO, hcpag.TURNO,\"0\" AS SALIDA_PZA, hcpag.HCDIRLINEA AS HC_DIRECTO,\"0\" AS CONV_POTS,\"0\" AS CONTENSION ,\"0\" AS SOPORTES,\"0\" AS TAB_INSP,\"0\" AS RUTAS_INT,\"0\" AS LPS,\"0\" AS SOP_LPS,\"0\" AS PILOTOS,\"0\" AS FTQ,\"0\" AS SISTEMAS,\"0\" AS PTOS_PZA_CUT, corte.hrsEMBC, hcpag.horaspagadas, truncate((corte.hrsEMBC/hcpag.horaspagadas)*100,2) as eficiencia \n" +
",if(hcpag.TURNO='A',9,7.9)as HRS_TURNO, \"0\" from   (select c.CADENA,c.IDCODIGO, c.LINEA, c.CODIGO, c.TURNO, m.SALIDAENPIEZA, g.HCDIRCORTE as cortee,  \n" +
"SUM(TRUNCATE((((g.HCDIRCORTE*m.SALIDAENPIEZA)/100)),2)) as hrsEMBC,   g.HCDIRLPS as 'POND.LPS', \n" +
"TRUNCATE( if(c.TURNO='B' or c.TURNO='C', ((g.HCDIRLPS*m.SALIDAENPIEZA)/7.9/100),((g.HCDIRLPS *m.SALIDAENPIEZA)/9/100)) ,2) as LPS, \n" +
"g.HCDIRENSFINAL as 'PUNTOS.EF', TRUNCATE( if(c.TURNO='B' or c.TURNO='C',((g.HCDIRENSFINAL*m.SALIDAENPIEZA)/7.9/100), \n" +
"((g.HCDIRENSFINAL *m.SALIDAENPIEZA)/9/100)),2) as ENSFINAL  FROM codigos as c, manufactura as m, gsd  as g\n" +
"where c.IDCODIGO=m.IDCODIGO and c.IDCODIGO=g.IDCODIGO and m.activo=1 AND C.CADENA<>6 AND C.CADENA=4 GROUP BY c.TURNO) as corte,\n" +
"(select c.IDCODIGO, HCDIRLINEA, C.TURNO, case c.TURNO when 'B' THEN SUM((HCDIRLINEA)*7.9) \n" +
"WHEN 'A' THEN sum((HCDIRLINEA)*9) \n" +
"WHEN 'C' THEN sum((m.HCDIRLINEA)*7.32)  END \n" +
"as horaspagadas from manufactura as m INNER JOIN codigos as c ON m.IDCODIGO=c.IDCODIGO where c.CADENA=\"4\" and (c.turno='A' or c.turno='B') and (C.LINEA='CORTE' or C.LINEA='Cuarentena/Covid')  GROUP BY c.TURNO) as hcpag WHERE hcpag.TURNO=corte.TURNO");
                        //                                     1      2        3         4                 5               6        7          8      9
             modelotemp.setColumnIdentifiers(new Object[]{"CADENA","CODIGO","LINEA", "TURNO", "SALIDA_PZA", "HC_DIRECTO", "CONV_POST", "CONTENSIONES", "SOPORTES", "TAB_INSP", "RUTA_INT", "LPS", "SOP_LPS", "PILOTOS", "FTQ", "SISTEMAS", "PTOS_PZA_CUT", "HRS_EMB", "HRS_PAG", "EFIC", "HRS_TURNO", "GENTE_GCSD ","PROPORCION FINAL MSD"});
             while(rs.next())
              {
                    modelotemp.addRow(new Object[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13), rs.getString(14), rs.getString(15), rs.getString(16), rs.getString(17), rs.getString(18), rs.getString(19), rs.getString(20), rs.getString(21), rs.getString(22)});
              }
         }catch(Exception e)
         {
             System.out.println(e+"get modelo");
         }
          return modelotemp;
      }     
                  
         
 
      
      public static DefaultTableModel getModeloServiciosA(String Linea, String Turno) throws SQLException{
         DefaultTableModel modelotemp=null;
         
          try{
            
              modelotemp=new DefaultTableModel();
             ResultSet rs=Principal.cn.GetConsulta("select   manu.codigo, manu.linea,   manu.turno, manu.SALIDAENPIEZA, manu.hc, (HCDIRLINEA+kits+estaciones+elinea) as 'CONV.POST.KITS', hcdirconte AS 'CONTENSION', HCDIRSOPORTE AS 'SOPORTE', HCDIRTABINSP AS 'TAB.INSP.', hcrutasint AS 'RUTAS INT.', hcdirlps AS 'LPS', HCDIRSOPLPS AS 'SOP. LPS', hcdirpilotos AS 'PILOTOS', hcdirftq AS 'FTQ', hcdirsistemas AS 'SISTEMAS',  manu.PUNTOSPZAPOND AS 'PTOS. PIEZA',manu.horasemb as horasemb, manu.hrsPagadas as hrsPagadas,  ( manu.horasemb/manu.hrsPagadas) as efic, manu.horasemb from \n" +
"                (select p.LINEA, p.CADENA, p.codigo, p.turno,  m.SALIDAENPIEZA, m.PUNTOSPZAPOND, kits, estaciones, elinea,  HCDIRLINEA, hcdirconte, HCDIRSOPORTE, HCDIRTABINSP, hcrutasint, hcdirlps, HCDIRSOPLPS, hcdirpilotos, hcdirftq, hcdirsistemas, sum(p.hcdirecto) as hc, round(15) as horasemb,  round(IF(p.TURNO='A', sum(((p.hcdirecto)*9)),sum(((p.hcdirecto)*7.9))),2) as hrsPagadas, m.activo  from manufactura as m, personaservicios  as p where m.idcodigo=p.IDCODIGO GROUP BY p.idcodigo) as manu\n" +
"                where  manu.CADENA<>5  and manu.Linea='"+Linea+"' and manu.turno='"+Turno+"' and manu.cadena=1");
                        //                                        1     2       3         4                 5               6                  7             8       9             10            11    12         13         14     15          16            17         18              19          20             
             modelotemp.setColumnIdentifiers(new Object[]{"CODIGO", "LINEA", "TURNO", "SALIDA EN PZA.", "HC.DIRECTO", "CONV.POTS.", "CONTESION", "SOPORTES", "TAB.INSP.", "RUTAS INT.", "LPS", "SOP.LPS", "PILOTOS" , "FTQ", "SISTEMAS", "PTOS.PZA.","HRS.EMB.", "HRS.PAGADAS", "EFICIENCIA" });
             while(rs.next())
              {
                    modelotemp.addRow(new Object[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7),rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13), rs.getString(14), rs.getString(15), rs.getString(16), rs.getString(17), rs.getString(18), rs.getString(19)});
              }
         }catch(Exception e)
         {
             System.out.println(e+"get modelo");
         }
          return modelotemp;
      } 
      
      
        public static DefaultTableModel getModeloServiciosB(String Linea, String Turno) throws SQLException{
         DefaultTableModel modelotemp=null;
         
          try{
            
              modelotemp=new DefaultTableModel();
             ResultSet rs=Principal.cn.GetConsulta("select   manu.codigo, manu.linea,   manu.turno, manu.SALIDAENPIEZA, manu.hc, (HCDIRLINEA+kits+estaciones+elinea) as 'CONV.POST.KITS', hcdirconte AS 'CONTENSION', HCDIRSOPORTE AS 'SOPORTE', HCDIRTABINSP AS 'TAB.INSP.', hcrutasint AS 'RUTAS INT.', hcdirlps AS 'LPS', HCDIRSOPLPS AS 'SOP. LPS', hcdirpilotos AS 'PILOTOS', hcdirftq AS 'FTQ', hcdirsistemas AS 'SISTEMAS',  manu.PUNTOSPZAPOND AS 'PTOS. PIEZA',manu.horasemb as horasemb, manu.hrsPagadas as hrsPagadas,  ( manu.horasemb/manu.hrsPagadas) as efic, manu.horasemb from \n" +
"                (select p.LINEA, p.CADENA, p.codigo, p.turno,  m.SALIDAENPIEZA, m.PUNTOSPZAPOND, kits, estaciones, elinea,  HCDIRLINEA, hcdirconte, HCDIRSOPORTE, HCDIRTABINSP, hcrutasint, hcdirlps, HCDIRSOPLPS, hcdirpilotos, hcdirftq, hcdirsistemas, sum(p.hcdirecto) as hc, round(23) as horasemb,  round(IF(p.TURNO='A', sum(((p.hcdirecto)*9)),sum(((p.hcdirecto)*7.9))),2) as hrsPagadas, m.activo  from manufactura as m, personaservicios  as p where m.idcodigo=p.IDCODIGO GROUP BY p.idcodigo) as manu\n" +
"                where  manu.CADENA<>5  and manu.Linea='"+Linea+"' and manu.turno='"+Turno+"' and manu.cadena=1");
                        //                                        1     2       3         4                 5               6                  7             8       9             10            11    12         13         14     15          16            17         18              19          20             
             modelotemp.setColumnIdentifiers(new Object[]{"CODIGO", "LINEA", "TURNO", "SALIDA EN PZA.", "HC.DIRECTO", "CONV.POTS.", "CONTESION", "SOPORTES", "TAB.INSP.", "RUTAS INT.", "LPS", "SOP.LPS", "PILOTOS" , "FTQ", "SISTEMAS", "PTOS.PZA.","HRS.EMB.", "HRS.PAGADAS", "EFICIENCIA" });
             while(rs.next())
              {
                    modelotemp.addRow(new Object[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7),rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13), rs.getString(14), rs.getString(15), rs.getString(16), rs.getString(17), rs.getString(18), rs.getString(19)});
              }
         }catch(Exception e)
         {
             System.out.println(e+"get modelo");
         }
          return modelotemp;
      } 
   
      public static DefaultTableModel GetModeloCorte(String Cadena) throws SQLException{
         DefaultTableModel modelotemp=null;
         
          try{
            String HrsEmbA="", hrsEmbB="", hrsEmbC="", EfA="", EfB="", EfC="",HrsEmbAC="", hrsEmbBC="", EfAC="", EfBC="";
                     
              modelotemp=new DefaultTableModel();
              ResultSet rs=Principal.cn.GetConsulta("SELECT DISTINCT corte.idcodigo, hcpag.IDCODIGO, hcpag.HCDIRLINEA, corte.hrsEMBC, hcpag.horaspagadas, hcpag.TURNO, truncate((corte.hrsEMBC/hcpag.horaspagadas)*100,2) as eficiencia \n" +
                    "                from   (select c.IDCODIGO, c.LINEA, c.CODIGO, c.TURNO, m.SALIDAENPIEZA, g.HCDIRCORTE as cortee,  \n" +
                    "SUM(TRUNCATE((((g.HCDIRCORTE*m.SALIDAENPIEZA)/100)),2)) as hrsEMBC,   g.HCDIRLPS as 'POND.LPS', \n" +
                    "TRUNCATE( if(c.TURNO='B' or c.TURNO='C', ((g.HCDIRLPS*m.SALIDAENPIEZA)/7.9/100),((g.HCDIRLPS *m.SALIDAENPIEZA)/9/100)) ,2) as LPS, \n" +
                    "g.HCDIRENSFINAL as 'PUNTOS.EF', TRUNCATE( if(c.TURNO='B' or c.TURNO='C',((g.HCDIRENSFINAL*m.SALIDAENPIEZA)/7.9/100), \n" +
                    "((g.HCDIRENSFINAL *m.SALIDAENPIEZA)/9/100)),2) as ENSFINAL  FROM codigos as c, manufactura as m, gsd  as g\n" +
                    " where c.IDCODIGO=m.IDCODIGO and c.IDCODIGO=g.IDCODIGO and m.activo=1 AND C.CADENA<>6 GROUP BY c.TURNO) as corte,\n" +
                    "                (select c.IDCODIGO, HCDIRLINEA, C.TURNO, case c.TURNO when 'B' THEN SUM((HCDIRLINEA)*7.9) \n" +
                    "                     WHEN 'A' THEN sum((HCDIRLINEA)*9) \n" +
                    "                       WHEN 'C' THEN sum((m.HCDIRLINEA)*7.32)  END \n" +
                    "                     as horaspagadas from manufactura as m INNER JOIN codigos as c ON m.IDCODIGO=c.IDCODIGO where c.CADENA="+Cadena+" and (C.LINEA='CORTE' or C.LINEA='Cuarentena/Covid')  GROUP BY c.TURNO) as hcpag WHERE hcpag.TURNO=corte.TURNO");
//                 ResultSet  rs=Principal.cn.GetConsulta("SELECT DISTINCT\n" +
//"                corte.idcodigo,\n" +
//"                hcpag.IDCODIGO, hcpag.HCDIRLINEA, corte.hrsEMBC, hcpag.horaspagadas, hcpag.TURNO, truncate((corte.hrsEMBC/hcpag.horaspagadas)*100,2) as eficiencia \n" +
//"                from   (select c.IDCODIGO, c.LINEA, c.CODIGO, c.TURNO, m.SALIDAENPIEZA, g.HCDIRCORTE as cortee,  SUM(TRUNCATE((((g.HCDIRCORTE*m.SALIDAENPIEZA)/100)),2)) as hrsEMBC,   g.HCDIRLPS as 'POND.LPS', TRUNCATE( if(c.TURNO='B' or c.TURNO='C', ((g.HCDIRLPS*m.SALIDAENPIEZA)/7.9/100),((g.HCDIRLPS *m.SALIDAENPIEZA)/9/100)) ,2) as LPS, g.HCDIRENSFINAL as 'PUNTOS.EF', TRUNCATE( if(c.TURNO='B' or c.TURNO='C',((g.HCDIRENSFINAL*m.SALIDAENPIEZA)/7.9/100), ((g.HCDIRENSFINAL *m.SALIDAENPIEZA)/9/100)),2) as ENSFINAL  FROM codigos as c, manufactura as m, gsd  as g where c.IDCODIGO=m.IDCODIGO and c.IDCODIGO=g.IDCODIGO and m.activo=1   GROUP BY c.TURNO) as corte,\n" +
//"                (select c.IDCODIGO, HCDIRLINEA, C.TURNO, case c.TURNO when'B' THEN SUM((M.HCDIRLPS+M.HCDIRSOPLPS +M.HCDIRLINEA+ m.hcdirconte+m.HCDIRSOPORTE+m.HCDIRTABINSP+m.hcrutasint+m.hcdirpilotos+m.hcdirftq+ m.hcdirsistemas)*7.9) "
//                     + " WHEN 'A' THEN sum((M.HCDIRLPS+M.HCDIRSOPLPS +M.HCDIRLINEA+ m.hcdirconte+m.HCDIRSOPORTE+m.HCDIRTABINSP+m.hcrutasint+m.hcdirpilotos+m.hcdirftq+ m.hcdirsistemas)*9) "+
//                      " WHEN 'C' THEN sum((M.HCDIRLPS+M.HCDIRSOPLPS +M.HCDIRLINEA+ m.hcdirconte+m.HCDIRSOPORTE+m.HCDIRTABINSP+m.hcrutasint+m.hcdirpilotos+m.hcdirftq+ m.hcdirsistemas)*6.1)  END "+
//                     "as horaspagadas from manufactura as m, codigos as c where m.IDCODIGO=c.IDCODIGO and c.CADENA=4  GROUP BY c.TURNO) as hcpag ");
             int i=0; 
                while(rs.next())
                {
                 switch(i)
                 {
                     case 0:
                         HrsEmbA=rs.getString("hrsEMBC");
                         EfA=rs.getString("eficiencia");
                         break;
                          case 1:
                         hrsEmbB=rs.getString("hrsEMBC");
                         EfB=rs.getString("eficiencia");
                         break;
                         case 2:
                         hrsEmbC=rs.getString("hrsEMBC");
                         EfC=rs.getString("eficiencia");
                         break;
                             
                             case 3:
                         HrsEmbAC=rs.getString("hrsEMBC");
                         EfAC=rs.getString("eficiencia");
                         break;
                          case 4:
                         hrsEmbBC=rs.getString("hrsEMBC");
                         EfBC=rs.getString("eficiencia");
                         break;
                             
                 }
                 i++;
                }
                rs=Principal.cn.GetConsulta("SELECT codigos.CADENA, codigos.TURNO, codigos.LINEA, tablacorte.idcodigo, tablacorte.operativoMaq, tablacorte.lideManufactura, tablacorte.SoportManufactura,\n" +
                            "tablacorte.RecuperacionDonas, tablacorte.bto, tablacorte.Capturista, tablacorte.Retrabajo, Tablacorte.Contension, tablacorte.AudSistema, tablacorte.Servicios,\n" +
                            "tablacorte.Ataderos,tablacorte.OTROS,tablacorte.Covid,tablacorte.Ruteros,tablacorte.Recuperador, manufactura.HCDIRLINEA AS TOTAL FROM tablacorte , codigos, manufactura where tablacorte.idcodigo=codigos.IDCODIGO AND tablacorte.idcodigo=manufactura.IDCODIGO and codigos.cadena='"+Cadena+"'");
                                                    //                                        1     2       3         4                 5               6                  7             8       9             10            11    12         13         14     15          16            17         18              19          20             
             modelotemp.setColumnIdentifiers(new Object[]{"CADENA",  "TURNO", "LINEA",  "OP.MAQUINARIA", "LIDER.MANUF.", "SOPORTES", "PILOTOS C", "SISTEMAS", "CAPTURISTA", "RETRABAJO", "CONTENSION" , "ENTTO", "SERVICIOS", "ATADEROS","CAO","COVID"," RUTEROS","RECUPERADOR", "TOTAL", "HRS.EMB", "HRS.PAG", "EFIC","HC ING"});
             i=0;
             if(Cadena.equals("6"))
             {
               //HrsEmbA="0";
               //hrsEmbB="0";
               //EfA="0";
               //EfB="0";
             }
             while(rs.next())
              {
                  switch(i)
                  { case 0:
                       modelotemp.addRow(new Object[]{rs.getString("CADENA"), rs.getString("TURNO"), rs.getString("LINEA"), rs.getString("operativomaq"), rs.getString("lidemanufactura"), rs.getString("soportmanufactura"),rs.getString("recuperaciondonas"), rs.getString("bto"), rs.getString("capturista"), rs.getString("retrabajo"), rs.getString("contension"), rs.getString("audsistema"), rs.getString("servicios"), rs.getString("ataderos"),rs.getString("OTROS"),rs.getString("Covid"),rs.getString("Ruteros"),rs.getString("Recuperador"), rs.getString("TOTAL"), HrsEmbA,  (rs.getDouble("TOTAL")*9), EfA,(rs.getDouble("operativomaq")*1.5)});
                      break;
                 case 1:
                      modelotemp.addRow(new Object[]{rs.getString("CADENA"), rs.getString("TURNO"), rs.getString("LINEA"), rs.getString("operativomaq"), rs.getString("lidemanufactura"), rs.getString("soportmanufactura"),rs.getString("recuperaciondonas"), rs.getString("bto"), rs.getString("capturista"), rs.getString("retrabajo"), rs.getString("contension"), rs.getString("audsistema"), rs.getString("servicios"), rs.getString("ataderos"),rs.getString("OTROS"),rs.getString("Covid"),rs.getString("Ruteros"),rs.getString("Recuperador"), rs.getString("TOTAL"), hrsEmbB,  (rs.getDouble("TOTAL")*7.9), EfB,(rs.getDouble("operativomaq")*1.5)});
                      break;
                 case 2:
                     modelotemp.addRow(new Object[]{rs.getString("CADENA"), rs.getString("TURNO"), rs.getString("LINEA"), rs.getString("operativomaq"), rs.getString("lidemanufactura"), rs.getString("soportmanufactura"),rs.getString("recuperaciondonas"), rs.getString("bto"), rs.getString("capturista"), rs.getString("retrabajo"), rs.getString("contension"), rs.getString("audsistema"), rs.getString("servicios"), rs.getString("ataderos"),rs.getString("OTROS"),rs.getString("Covid"),rs.getString("Ruteros"),rs.getString("Recuperador"), rs.getString("TOTAL"), hrsEmbC,  (rs.getDouble("TOTAL")*7.32), EfC,(rs.getDouble("operativomaq")*1.5) });
                    break;                     
                 case 3:
                     modelotemp.addRow(new Object[]{rs.getString("CADENA"), rs.getString("TURNO"), rs.getString("LINEA"), rs.getString("operativomaq"), rs.getString("lidemanufactura"), rs.getString("soportmanufactura"),rs.getString("recuperaciondonas"), rs.getString("bto"), rs.getString("capturista"), rs.getString("retrabajo"), rs.getString("contension"), rs.getString("audsistema"), rs.getString("servicios"), rs.getString("ataderos"),rs.getString("OTROS"),rs.getString("Covid"),rs.getString("Ruteros"),rs.getString("Recuperador"), rs.getString("TOTAL"), hrsEmbC,  (rs.getDouble("TOTAL")*9), EfBC,(rs.getDouble("operativomaq")*1.5) });
                    break;                      
                 case 4:
                     modelotemp.addRow(new Object[]{rs.getString("CADENA"), rs.getString("TURNO"), rs.getString("LINEA"), rs.getString("operativomaq"), rs.getString("lidemanufactura"), rs.getString("soportmanufactura"),rs.getString("recuperaciondonas"), rs.getString("bto"), rs.getString("capturista"), rs.getString("retrabajo"), rs.getString("contension"), rs.getString("audsistema"), rs.getString("servicios"), rs.getString("ataderos"),rs.getString("OTROS"),rs.getString("Covid"),rs.getString("Ruteros"),rs.getString("Recuperador"), rs.getString("TOTAL"), hrsEmbC,  (rs.getDouble("TOTAL")*7.9), EfAC ,(rs.getDouble("operativomaq")*1.5)});
                    break;
                     
                  } 
             i++;
              }
             
         }catch(Exception e)
         {
             System.out.println(e+"getmodelocorte");
         }
          return modelotemp;
      }
      
      
        public static DefaultTableModel GetModeloCortegml(String Cadena) throws SQLException{
         DefaultTableModel modelotemp=null;
         
          try{
            String HrsEmbA="", hrsEmbB="", hrsEmbC="", EfA="", EfB="", EfC="";
                     
              modelotemp=new DefaultTableModel();
              ResultSet rs=Principal.cn.GetConsulta(" SELECT DISTINCT corte.idcodigo, hcpag.IDCODIGO, hcpag.HCDIRLINEA, corte.hrsEMBC, hcpag.horaspagadas, hcpag.TURNO, truncate((corte.hrsEMBC/hcpag.horaspagadas)*100,2) as eficiencia \n" +
"                                    from   (select c.IDCODIGO, c.LINEA, c.CODIGO, c.TURNO, m.SALIDAENPIEZA, g.HCDIRCORTE as cortee,  \n" +
"                    SUM(TRUNCATE((((g.HCDIRCORTE*m.SALIDAENPIEZA)/100)),2)) as hrsEMBC,   g.HCDIRLPS as 'POND.LPS', \n" +
"                    TRUNCATE( if(c.TURNO='B' or c.TURNO='C', ((g.HCDIRLPS*m.SALIDAENPIEZA)/7.9/100),((g.HCDIRLPS *m.SALIDAENPIEZA)/9/100)) ,2) as LPS, \n" +
"                    g.HCDIRENSFINAL as 'PUNTOS.EF', TRUNCATE( if(c.TURNO='B' or c.TURNO='C',((g.HCDIRENSFINAL*m.SALIDAENPIEZA)/7.9/100), \n" +
"                    ((g.HCDIRENSFINAL *m.SALIDAENPIEZA)/9/100)),2) as ENSFINAL  FROM codigos as c, manufactura as m, gsd  as g\n" +
"                    where c.IDCODIGO=m.IDCODIGO and c.IDCODIGO=g.IDCODIGO and m.activo=1 AND C.CADENA=6 GROUP BY c.TURNO) as corte,\n" +
"                                   (select c.IDCODIGO, HCDIRLINEA, C.TURNO, case c.TURNO when 'B' THEN SUM((HCDIRLINEA)*7.9) \n" +
"                                       WHEN 'A' THEN sum((HCDIRLINEA)*9) \n" +
"                                         WHEN 'C' THEN sum((m.HCDIRLINEA)*7.32)  END \n" +
"                                       as horaspagadas from manufactura as m INNER JOIN codigos as c ON m.IDCODIGO=c.IDCODIGO where c.CADENA=\"6\" and C.LINEA='CORTE'  GROUP BY c.TURNO) as hcpag WHERE hcpag.TURNO=corte.TURNO\n" +
"               ");
//                 ResultSet  rs=Principal.cn.GetConsulta("SELECT DISTINCT\n" +
//"                corte.idcodigo,\n" +
//"                hcpag.IDCODIGO, hcpag.HCDIRLINEA, corte.hrsEMBC, hcpag.horaspagadas, hcpag.TURNO, truncate((corte.hrsEMBC/hcpag.horaspagadas)*100,2) as eficiencia \n" +
//"                from   (select c.IDCODIGO, c.LINEA, c.CODIGO, c.TURNO, m.SALIDAENPIEZA, g.HCDIRCORTE as cortee,  SUM(TRUNCATE((((g.HCDIRCORTE*m.SALIDAENPIEZA)/100)),2)) as hrsEMBC,   g.HCDIRLPS as 'POND.LPS', TRUNCATE( if(c.TURNO='B' or c.TURNO='C', ((g.HCDIRLPS*m.SALIDAENPIEZA)/7.9/100),((g.HCDIRLPS *m.SALIDAENPIEZA)/9/100)) ,2) as LPS, g.HCDIRENSFINAL as 'PUNTOS.EF', TRUNCATE( if(c.TURNO='B' or c.TURNO='C',((g.HCDIRENSFINAL*m.SALIDAENPIEZA)/7.9/100), ((g.HCDIRENSFINAL *m.SALIDAENPIEZA)/9/100)),2) as ENSFINAL  FROM codigos as c, manufactura as m, gsd  as g where c.IDCODIGO=m.IDCODIGO and c.IDCODIGO=g.IDCODIGO and m.activo=1   GROUP BY c.TURNO) as corte,\n" +
//"                (select c.IDCODIGO, HCDIRLINEA, C.TURNO, case c.TURNO when'B' THEN SUM((M.HCDIRLPS+M.HCDIRSOPLPS +M.HCDIRLINEA+ m.hcdirconte+m.HCDIRSOPORTE+m.HCDIRTABINSP+m.hcrutasint+m.hcdirpilotos+m.hcdirftq+ m.hcdirsistemas)*7.9) "
//                     + " WHEN 'A' THEN sum((M.HCDIRLPS+M.HCDIRSOPLPS +M.HCDIRLINEA+ m.hcdirconte+m.HCDIRSOPORTE+m.HCDIRTABINSP+m.hcrutasint+m.hcdirpilotos+m.hcdirftq+ m.hcdirsistemas)*9) "+
//                      " WHEN 'C' THEN sum((M.HCDIRLPS+M.HCDIRSOPLPS +M.HCDIRLINEA+ m.hcdirconte+m.HCDIRSOPORTE+m.HCDIRTABINSP+m.hcrutasint+m.hcdirpilotos+m.hcdirftq+ m.hcdirsistemas)*6.1)  END "+
//                     "as horaspagadas from manufactura as m, codigos as c where m.IDCODIGO=c.IDCODIGO and c.CADENA=4  GROUP BY c.TURNO) as hcpag ");
             int i=0; 
                while(rs.next())
                {
                 switch(i)
                 {
                     case 0:
                         HrsEmbA=rs.getString("hrsEMBC");
                         EfA=rs.getString("eficiencia");
                         break;
                          case 1:
                         hrsEmbB=rs.getString("hrsEMBC");
                         EfB=rs.getString("eficiencia");
                         break;
                         case 2:
                         hrsEmbC=rs.getString("hrsEMBC");
                         EfC=rs.getString("eficiencia");
                         break;
                 }
                 i++;
                }
                rs=Principal.cn.GetConsulta("SELECT codigos.CADENA, codigos.TURNO, codigos.LINEA, tablacorte.idcodigo, tablacorte.operativoMaq, tablacorte.lideManufactura, tablacorte.SoportManufactura,\n" +
                            "tablacorte.RecuperacionDonas, tablacorte.bto, tablacorte.Capturista, tablacorte.Retrabajo, Tablacorte.Contension, tablacorte.AudSistema, tablacorte.Servicios,\n" +
                            "tablacorte.Ataderos,tablacorte.OTROS, manufactura.HCDIRLINEA AS TOTAL FROM tablacorte , codigos, manufactura where tablacorte.idcodigo=codigos.IDCODIGO AND tablacorte.idcodigo=manufactura.IDCODIGO and codigos.cadena='6'");
                                                    //                                        1     2       3         4                 5               6                  7             8       9             10            11    12         13         14     15          16            17         18              19          20             
             modelotemp.setColumnIdentifiers(new Object[]{"CADENA",  "TURNO", "LINEA",  "OP.MAQUINARIA", "LIDER.MANUF.", "SOPORTES", "REC.DONAS", "BTO", "CAPTURISTA", "RETRABAJO", "CONTENSION" , "AUDITORES", "SERVICIOS", "ATADEROS","OTROS", "TOTAL", "HRS.EMB", "HRS.PAG", "EFIC"});
             i=0;
             if(Cadena.equals("6"))
             {
               // HrsEmbA="0";
                //hrsEmbB="0";
                 //EfA="0";
                //EfB="0";
             }
             while(rs.next())
              {
                  switch(i)
                  { case 0:
                       modelotemp.addRow(new Object[]{rs.getString("CADENA"), rs.getString("TURNO"), rs.getString("LINEA"), rs.getString("operativomaq"), rs.getString("lidemanufactura"), rs.getString("soportmanufactura"),rs.getString("recuperaciondonas"), rs.getString("bto"), rs.getString("capturista"), rs.getString("retrabajo"), rs.getString("contension"), rs.getString("audsistema"), rs.getString("servicios"), rs.getString("ataderos"),rs.getString("OTROS"), rs.getString("TOTAL"), HrsEmbA,  (rs.getDouble("TOTAL")*9), EfA });
                      break;
                  case 1:
                      modelotemp.addRow(new Object[]{rs.getString("CADENA"), rs.getString("TURNO"), rs.getString("LINEA"), rs.getString("operativomaq"), rs.getString("lidemanufactura"), rs.getString("soportmanufactura"),rs.getString("recuperaciondonas"), rs.getString("bto"), rs.getString("capturista"), rs.getString("retrabajo"), rs.getString("contension"), rs.getString("audsistema"), rs.getString("servicios"), rs.getString("ataderos"),rs.getString("OTROS"), rs.getString("TOTAL"), hrsEmbB,  (rs.getDouble("TOTAL")*7.9), EfB });
                      break;
                  case 2:
                       modelotemp.addRow(new Object[]{rs.getString("CADENA"), rs.getString("TURNO"), rs.getString("LINEA"), rs.getString("operativomaq"), rs.getString("lidemanufactura"), rs.getString("soportmanufactura"),rs.getString("recuperaciondonas"), rs.getString("bto"), rs.getString("capturista"), rs.getString("retrabajo"), rs.getString("contension"), rs.getString("audsistema"), rs.getString("servicios"), rs.getString("ataderos"),rs.getString("OTROS"), rs.getString("TOTAL"), hrsEmbC,  (rs.getDouble("TOTAL")*7.32), EfC });
                      break;
                  } 
             i++;
              }
             
         }catch(Exception e)
         {
             System.out.println(e+"getmodelocorte");
         }
          return modelotemp;
      }
      
      
      
      
      
      public void EnlazarDgv(String Plataforma, String Arnes, String Cad1, String Cad2, String Cad3, String Cad4, String Cad5, String Cad6,  String Codigo, String Turno){
        try {
            modelo =new DefaultTableModel( ){ 
                @Override
                public boolean isCellEditable(int row, int column) {
                        boolean rsp=true;
                if(Principal.UsuarioLogeado.ReadOnly)
                {
                    return false;
                }  else
                {
                  if((column==16) || (column==20) || (column==21))
                    { 
                     rsp=false;
                    }
                 return rsp;  
                }
                }
            };
                                                            //1      2         3        4         5         6                      7       8            9           10         11        12        13         14        15      16                 17             18             19              20          21          22      23   
            modelo.setColumnIdentifiers(new Object[]{"IDCODIGO", "aPLAT.", "CODIGO", "LINEA", "TURNO", "CONV&POST", "CONTESION", "SOPORTE", "TAB.INSP", "RUTAS INT.", "LPS", "SOP. LPS",  "PILOTOS", "FTQ", "SISTEMAS", "RUTAS SURT.", "HCDIRECTO",  "SAL.EN.PZA", "PTOS.PIEZA", "C",  "HRS.EMB", "HRS.PAG","EFIC.", "ACTIVO","CURVA"});   
            String query="SELECT\n" +
                    "codigos.IDCODIGO,\n" +
                    "codigos.CADENA,\n" +
                    "PLATAFORMA, ARNES, CODIGO,  LINEA, TURNO, MANUFACTURA.activo, MANUFACTURA.CURVA, \n" +
                    "manufactura.HCDIRLINEA,\n" +
                    "manufactura.HCDIRLPS, \n" +
                    "manufactura.HCDIRSOPORTE, \n" +
                    "manufactura.HCDIRSOPLPS, \n" +
                    "manufactura.HCDIRTABINSP, \n" +
                    "manufactura.HCDIRCONTE,\n" +
                    "manufactura.HCDIRFTQ,\n" +
                    "manufactura.HCDIRPILOTOS,\n" +
                    "manufactura.HCDIRSISTEMAS,\n" +
                    "manufactura.HCINDRUTAS,\n" +
                    "manufactura.HCRUTASINT,\n" +
                    "manufactura.PUNTOSPZAPOND,"
                    + "(HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas) as hcdirecto,\n" +
                    "manufactura.CAP_UTIL_HTA, manufactura.salidaenpieza, \n" +
                    "ROUND((manufactura.SALIDAENPIEZA* manufactura.PUNTOSPZAPOND)/100,2) as HRSEMB, "+
                    "ROUND(if (codigos.turno='A', ((HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*9),"+
                    "(HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*7.9)) as HRSPAG "+
                    "FROM\n" +
                    "(select * from codigos where cadena<>7 and cadena<>4 and cadena='"+this.Cadena+"'";

            query+=") as codigos INNER JOIN\n" +
                    "manufactura\n" +
                    "ON\n" +
                    "codigos.IDCODIGO = manufactura.IDCODIGO ";
            if(!Plataforma.equals("TODOS"))
            query+="AND CODIGOS.PLATAFORMA='"+Plataforma+"'";
            if(!Arnes.equals("TODOS"))
            query+= "AND  CODIGOS.ARNES='"+Arnes+"'";
            if(!Codigo.equals("TODOS"))
            query+= "AND  CODIGOS.Codigo='"+Codigo+"'";
            if(!Turno.equals("TODOS"))
            query+= "AND  CODIGOS.Turno='"+this.Turno+"'";
            ResultSet rs=Principal.cn.GetConsulta(query);
            //modelo=new DefaultTableModel();
           
            while(rs.next())
            {
                 String efic="0 %";
            if(rs.getBoolean("ACTIVO"))
            {
             if(rs.getString("HRSPAG").equals("0")){
                  efic="0 %";
             }else{
               efic= Regresa2Decimales(rs.getDouble("HRSEMB")/ rs.getDouble("HRSPAG")*100)+" %";
             }
            }
               modelo.addRow(new Object[]{rs.getString("idcodigo"), rs.getString("PLATAFORMA"), rs.getString("CODIGO"), rs.getString("LINEA"), rs.getString("turno"), rs.getDouble("HCDIRLINEA"), rs.getDouble("HCDIRCONTE"), rs.getDouble("HCDIRSOPORTE"), rs.getDouble("HCDIRTABINSP"),  rs.getDouble("HCRUTASINT"), rs.getDouble("HCDIRLPS"), rs.getDouble("HCDIRSOPLPS"), rs.getDouble("HCDIRPILOTOS"), rs.getDouble("HCDIRFTQ"),  rs.getDouble("HCDIRSISTEMAS"), rs.getDouble("HCINDRUTAS"), rs.getDouble("HCDIRECTO"),  rs.getDouble("SALIDAENPIEZA"), rs.getDouble("PUNTOSPZAPOND"), rs.getDouble("CAP_UTIL_HTA"), rs.getString("HRSEMB"), rs.getString("HRSPAG"), efic, rs.getBoolean("ACTIVO"), rs.getBoolean("CURVA")});
            }
              tblCodigos.setModel(modelo);
              tblCodigos.getColumnModel().getColumn(0).setMaxWidth(35);
              tblCodigos.getColumnModel().getColumn(0).setMinWidth(35);
              tblCodigos.getColumnModel().getColumn(0).setPreferredWidth(35);
              tblCodigos.getColumnModel().getColumn(1).setMaxWidth(70);
              tblCodigos.getColumnModel().getColumn(1).setMinWidth(70);
              tblCodigos.getColumnModel().getColumn(1).setPreferredWidth(70);
              tblCodigos.getColumnModel().getColumn(2).setMaxWidth(80);
              tblCodigos.getColumnModel().getColumn(2).setMinWidth(80);
              tblCodigos.getColumnModel().getColumn(2).setPreferredWidth(80);
              tblCodigos.getColumnModel().getColumn(3).setMaxWidth(40);
              tblCodigos.getColumnModel().getColumn(3).setMinWidth(40);
              tblCodigos.getColumnModel().getColumn(3).setPreferredWidth(40);
              tblCodigos.getColumnModel().getColumn(4).setMaxWidth(30);
              tblCodigos.getColumnModel().getColumn(4).setMinWidth(30);
              tblCodigos.getColumnModel().getColumn(4).setPreferredWidth(30);
              tblCodigos.getColumnModel().getColumn(5).setMaxWidth(80);
              tblCodigos.getColumnModel().getColumn(5).setMinWidth(80);
              tblCodigos.getColumnModel().getColumn(5).setPreferredWidth(80);
              tblCodigos.getColumnModel().getColumn(10).setMaxWidth(40);
              tblCodigos.getColumnModel().getColumn(10).setMinWidth(40);
              tblCodigos.getColumnModel().getColumn(10).setPreferredWidth(40);
              tblCodigos.getColumnModel().getColumn(15).setMaxWidth(70);
              tblCodigos.getColumnModel().getColumn(15).setMinWidth(70);
              tblCodigos.getColumnModel().getColumn(15).setPreferredWidth(70); 
              tblCodigos.getColumnModel().getColumn(16).setMaxWidth(70);
              tblCodigos.getColumnModel().getColumn(16).setMinWidth(70);
              tblCodigos.getColumnModel().getColumn(16).setPreferredWidth(70); 
              tblCodigos.getColumnModel().getColumn(17).setPreferredWidth(70);               
              tblCodigos.getColumnModel().getColumn(18).setMaxWidth(70);
              tblCodigos.getColumnModel().getColumn(18).setMinWidth(70);
              tblCodigos.getColumnModel().getColumn(18).setPreferredWidth(70);
              Font font = tblCodigos.getFont();
              font.deriveFont(48);
              tblCodigos.setFont(font);
              tblCodigos.getColumnModel().getColumn(16).setCellRenderer(new RenderColorear(Color.LIGHT_GRAY));
              tblCodigos.getColumnModel().getColumn(22).setCellRenderer(new RenderColorear(Color.YELLOW));
              tblCodigos.getColumnModel().getColumn(23).setCellEditor(new CeldaCheck());
              tblCodigos.getColumnModel().getColumn(23).setCellRenderer(new Render_CheckBox());
              tblCodigos.getColumnModel().getColumn(23).setMaxWidth(30);
              
              tblCodigos.getColumnModel().getColumn(24).setCellEditor(new CeldaCheck());
              tblCodigos.getColumnModel().getColumn(24).setCellRenderer(new Render_CheckBox());
              tblCodigos.getColumnModel().getColumn(24).setMaxWidth(30);
              
              tblCodigos.getColumnModel().getColumn(13).setMinWidth(30);
              tblCodigos.getColumnModel().getColumn(13).setPreferredWidth(30);
             // tblCodigos.setRowHeight(500);
          
            
                    }catch(Exception e)
        {
            System.out.println(e);
        }
    }
    //esta es la funcion chila para modificar las tablas de las cadenas
      public void EnlazarDgv(){
        try {
            modelo =new DefaultTableModel( ){ 
                @Override
                public boolean isCellEditable(int row, int column) {
                        boolean rsp=true;
                if(Principal.UsuarioLogeado.ReadOnly)
                {
                    return false;
                }  else
                {
                  if((column==16) || (column==20) || (column==21))
                    { 
                     rsp=false;
                    }
                 return rsp;  
                }
                }
            };
                    //este query es el chilo para la tabla       //1      2         3        4         5         6                      7       8            9           10         11        12        13         14        15      16                 17             18             19              20          21          22      23   
            modelo.setColumnIdentifiers(new Object[]{"IDCODIGO", "aPLAT.", "CODIGO", "LINEA", "TURNO", "CONV&POST", "CONTESION", "SOPORTE", "TAB.INSP", "RUTAS INT.", "LPS", "SOP. LPS",  "PILOTOS", "FTQ", "SISTEMAS", "RUTAS SURT.", "HCDIRECTO",  "SAL.EN.PZA", "PTOS.PIEZA","C",  "HRS.EMB", "HRS.PAG","EFIC.", "ACTIVO","CURVA"});   
           String   query=" SELECT\n" +
"                    p.IDCODIGO,\n" +
"                    p.CADENA,\n" +
"                    PLATAFORMA, ARNES, CODIGO,  LINEA, TURNO, MANUFACTURA.activo,MANUFACTURA.CURVA,\n" +
"                    manufactura.HCDIRLINEA+kits+estaciones +manufactura.Elinea as HCDIRLINEA,\n" +
"                    manufactura.HCDIRLPS,\n" +
"                    manufactura.HCDIRSOPORTE,\n" +
"                    manufactura.HCDIRSOPLPS,\n" +
"                    manufactura.HCDIRTABINSP,\n" +
"                    manufactura.HCDIRCONTE,\n" +
"                    manufactura.HCDIRFTQ,\n" +
"                    manufactura.HCDIRPILOTOS,\n" +
"                    manufactura.HCDIRSISTEMAS,\n" +
"                    manufactura.HCINDRUTAS,\n" +
"                    manufactura.HCRUTASINT,\n" +
"                    manufactura.PUNTOSPZAPOND,(p.hcdirecto)\n" +
"                    as hcdirecto,\n" +
"                    manufactura.CAP_UTIL_HTA, manufactura.salidaenpieza,\n" +
"                    ROUND((manufactura.SALIDAENPIEZA* manufactura.PUNTOSPZAPOND)/100,2)  as HRSEMB,\n" +
"                    ROUND(if (p.turno='A', ((p.hcdirecto)*9),\n" +
"                    (p.hcdirecto)*7.9),2) as HRSPAG FROM\n" +
"                    (SELECT  SUM(TRUNCATE((((g.HCDIRCORTE*m.SALIDAENPIEZA)/100)),2)) as hrsEMBC FROM gsd as g, manufactura as m, codigos as c where c.IDCODIGO=m.IDCODIGO and c.IDCODIGO=g.IDCODIGO and m.activo=1 and c.turno='"+this.Turno+"'  GROUP BY c.TURNO) as cortehrsemb,\n" +
"                    (select * from personas  where cadena<>7  and cadena='"+this.Cadena+"') as p  INNER JOIN\n" +
"                    manufactura\n" +
"                    ON\n" +
"                    p.IDCODIGO = manufactura.IDCODIGO AND  p.Turno='"+this.Turno+"'";
               

            ResultSet rs=Principal.cn.GetConsulta(query);

            while(rs.next())
            {
                 String efic="0 %";
            if(rs.getBoolean("ACTIVO"))
            {
                if(rs.getString("HRSPAG").equals("0")){
                 efic="0 %";
         }else{
               efic= Regresa2Decimales(rs.getDouble("HRSEMB")/ rs.getDouble("HRSPAG")*100)+" %";
                }
            }
               modelo.addRow(new Object[]{rs.getString("idcodigo"), rs.getString("PLATAFORMA"), rs.getString("CODIGO"), rs.getString("LINEA"), rs.getString("turno"), rs.getDouble("HCDIRLINEA"), rs.getDouble("HCDIRCONTE"), rs.getDouble("HCDIRSOPORTE"), rs.getDouble("HCDIRTABINSP"),  rs.getDouble("HCRUTASINT"), rs.getDouble("HCDIRLPS"), rs.getDouble("HCDIRSOPLPS"), rs.getDouble("HCDIRPILOTOS"), rs.getDouble("HCDIRFTQ"),  rs.getDouble("HCDIRSISTEMAS"), rs.getDouble("HCINDRUTAS"), rs.getDouble("HCDIRECTO"),  rs.getDouble("SALIDAENPIEZA"), rs.getDouble("PUNTOSPZAPOND"), rs.getDouble("CAP_UTIL_HTA"), rs.getString("HRSEMB"), rs.getString("HRSPAG"), efic, rs.getBoolean("ACTIVO"), rs.getBoolean("CURVA")});
            }
              tblCodigos.setModel(modelo);
              tblCodigos.getColumnModel().getColumn(0).setMaxWidth(35);
              tblCodigos.getColumnModel().getColumn(0).setMinWidth(35);
              tblCodigos.getColumnModel().getColumn(0).setPreferredWidth(35);
              tblCodigos.getColumnModel().getColumn(1).setMaxWidth(75);
              tblCodigos.getColumnModel().getColumn(1).setMinWidth(75);
              tblCodigos.getColumnModel().getColumn(1).setPreferredWidth(75);
              tblCodigos.getColumnModel().getColumn(2).setMaxWidth(90);
              tblCodigos.getColumnModel().getColumn(2).setMinWidth(90);
              tblCodigos.getColumnModel().getColumn(2).setPreferredWidth(90);
              tblCodigos.getColumnModel().getColumn(3).setMaxWidth(70);
              tblCodigos.getColumnModel().getColumn(3).setMinWidth(70);
              tblCodigos.getColumnModel().getColumn(3).setPreferredWidth(70);
              tblCodigos.getColumnModel().getColumn(4).setMaxWidth(30);
              tblCodigos.getColumnModel().getColumn(4).setMinWidth(30);
              tblCodigos.getColumnModel().getColumn(4).setPreferredWidth(30);
              tblCodigos.getColumnModel().getColumn(5).setMaxWidth(80);
              tblCodigos.getColumnModel().getColumn(5).setMinWidth(80);
              tblCodigos.getColumnModel().getColumn(5).setPreferredWidth(80);
              
               tblCodigos.getColumnModel().getColumn(14).setMaxWidth(50);
              tblCodigos.getColumnModel().getColumn(14).setMinWidth(50);
              tblCodigos.getColumnModel().getColumn(14).setPreferredWidth(50);
                 tblCodigos.getColumnModel().getColumn(12).setMaxWidth(60);
              tblCodigos.getColumnModel().getColumn(12).setMinWidth(60);
              tblCodigos.getColumnModel().getColumn(12).setPreferredWidth(60);
              
              tblCodigos.getColumnModel().getColumn(15).setMaxWidth(70);
              tblCodigos.getColumnModel().getColumn(15).setMinWidth(70);
              tblCodigos.getColumnModel().getColumn(15).setPreferredWidth(70); 
              tblCodigos.getColumnModel().getColumn(16).setMaxWidth(70);
              tblCodigos.getColumnModel().getColumn(16).setMinWidth(70);
              tblCodigos.getColumnModel().getColumn(16).setPreferredWidth(70); 
              tblCodigos.getColumnModel().getColumn(17).setPreferredWidth(70);               
              tblCodigos.getColumnModel().getColumn(18).setMaxWidth(70);
              tblCodigos.getColumnModel().getColumn(18).setMinWidth(70);
              tblCodigos.getColumnModel().getColumn(18).setPreferredWidth(70);
               tblCodigos.getColumnModel().getColumn(19).setMaxWidth(1);
              tblCodigos.getColumnModel().getColumn(19).setMinWidth(1);
              tblCodigos.getColumnModel().getColumn(19).setPreferredWidth(1);
                tblCodigos.getColumnModel().getColumn(10).setMaxWidth(50);
              tblCodigos.getColumnModel().getColumn(10).setMinWidth(50);
              tblCodigos.getColumnModel().getColumn(10).setPreferredWidth(50);
              
                tblCodigos.getColumnModel().getColumn(22).setMaxWidth(60);
              tblCodigos.getColumnModel().getColumn(22).setMinWidth(60);
              tblCodigos.getColumnModel().getColumn(22).setPreferredWidth(60);
              Font font = tblCodigos.getFont();
              font.deriveFont(48);
              tblCodigos.setFont(font);
              tblCodigos.getColumnModel().getColumn(16).setCellRenderer(new RenderColorear(Color.LIGHT_GRAY));
              tblCodigos.getColumnModel().getColumn(22).setCellRenderer(new RenderColorear(Color.YELLOW));
              tblCodigos.getColumnModel().getColumn(23).setCellEditor(new CeldaCheck());
              tblCodigos.getColumnModel().getColumn(23).setCellRenderer(new Render_CheckBox());
              tblCodigos.getColumnModel().getColumn(23).setMaxWidth(30);
              
              tblCodigos.getColumnModel().getColumn(24).setCellEditor(new CeldaCheck());
              tblCodigos.getColumnModel().getColumn(24).setCellRenderer(new Render_CheckBox());
              tblCodigos.getColumnModel().getColumn(24).setMaxWidth(30);
              
              tblCodigos.getColumnModel().getColumn(13).setMinWidth(30);
              tblCodigos.getColumnModel().getColumn(13).setPreferredWidth(30);
             // tblCodigos.setRowHeight(500);
          
            
                    }catch(Exception e)
        {
            System.out.println(e);
        }
    }
      
//      public void enlazarPorc(){
//          try  {
//              Double HrsPagIA=0.0, HrsPagIIA=0.0, HrsPagIIIA=0.0, HrsPagIB=0.0, HrsPagIIB=0.0, HrsPagIIIB=0.0;
//              Double HrsEMBIA=0.0, HrsEMBIIA=0.0, HrsEMBIIIA=0.0, HrsEMBIB=0.0, HrsEMBIIB=0.0, HrsEMBIIIB=0.0;
//              Double EficIA=0.0, EficIIA=0.0, EficIIIA=0.0, EficIB=0.0, EficIIB=0.0, EficIIIB=0.0;
//              ResultSet rs= Principal.cn.GetConsulta("select  manu.CADENA, manu.turno, manu.hc, manu.activo,  (manu.horasemb) as horasemb, (manu.hrsPagadas) as hrsPagadas,  ( manu.horasemb/manu.hrsPagadas)*100 as efic from \n" +
//                "(select c.LINEA, c.CADENA, c.turno, sum(m.HCDIRLINEA) as hc, sum((m.PUNTOSPZAPOND*m.SALIDAENPIEZA/100)) as horasemb,  IF(c.TURNO='A', sum(((HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*9)),sum(((HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*7.9))) as hrsPagadas, m.activo  from manufactura as m, codigos as c where m.idcodigo=c.IDCODIGO GROUP BY c.idcodigo) as manu\n" +
//                "where manu.CADENA<>4 and manu.CADENA<>5  and manu.activo<>0");
//              while(rs.next())
//              {
//                  switch(rs.getString("CADENA"))
//                  {
//                      case "1":
//                          if(rs.getString("turno").equals("A"))
//                          {
//                          HrsPagIA+=rs.getDouble("hrsPagadas");
//                          HrsEMBIA+=rs.getDouble("horasemb");
//                          }else if(rs.getString("turno").equals("B"))
//                          {
//                          HrsPagIB+=rs.getDouble("hrsPagadas");
//                          HrsEMBIB+=rs.getDouble("horasemb");
//                          }
//                          break;
//                      case "2":
//                         if(rs.getString("turno").equals("A"))
//                          {
//                          HrsPagIIA+=rs.getDouble("hrsPagadas");
//                          HrsEMBIIA+=rs.getDouble("horasemb");
//                          }else if(rs.getString("turno").equals("B"))
//                          {
//                          HrsPagIIB+=rs.getDouble("hrsPagadas");
//                          HrsEMBIIB+=rs.getDouble("horasemb");
//                          }
//                          break;
//                      case "3":
//               if(rs.getString("turno").equals("A"))
//                          {
//                          HrsPagIIIA+=rs.getDouble("hrsPagadas");
//                          HrsEMBIIIA+=rs.getDouble("horasemb");
//                          }else if(rs.getString("turno").equals("B"))
//                          {
//                          HrsPagIIIB+=rs.getDouble("hrsPagadas");
//                          HrsEMBIIIB+=rs.getDouble("horasemb");
//                          }
//                          break;
//                  }
//              }
//              EficIA=( HrsEMBIA/HrsPagIA)*100;
//              EficIIA=(HrsEMBIIA/HrsPagIIA)*100;
//              EficIIIA=(HrsEMBIIIA/HrsPagIIIA)*100;
//              EficIB=( HrsEMBIB/HrsPagIB)*100;
//              EficIIB=(HrsEMBIIB/HrsPagIIB)*100;
//              EficIIIB=(HrsEMBIIIB/HrsPagIIIB)*100;
//              HrsPagIA=(double)Math.round(HrsPagIA);
//              HrsPagIIA=(double)Math.round(HrsPagIIA);
//              HrsPagIIIA=(double)Math.round(HrsPagIIIA);
//              HrsEMBIA=(double)Math.round(HrsEMBIA);
//              HrsEMBIIA=(double)Math.round(HrsEMBIIA);
//              HrsEMBIIIA=(double)Math.round(HrsEMBIIIA);
//              HrsPagIB=(double)Math.round(HrsPagIB);
//              HrsPagIIB=(double)Math.round(HrsPagIIB);
//              HrsPagIIIB=(double)Math.round(HrsPagIIIB);
//              HrsEMBIB=(double)Math.round(HrsEMBIB);
//              HrsEMBIIB=(double)Math.round(HrsEMBIIB);
//              HrsEMBIIIB=(double)Math.round(HrsEMBIIIB);
//              EficIA=Regresa2Decimales(EficIA);
//              EficIIA=Regresa2Decimales(EficIIA);
//              EficIIIA=Regresa2Decimales(EficIIIA);
//              EficIB=Regresa2Decimales(EficIB);
//              EficIIB=Regresa2Decimales(EficIIB);
//              EficIIIB=Regresa2Decimales(EficIIIB);
//              lblHorasPagIA.setText(HrsPagIA.toString() );
//              lblHorasPagIIA.setText(HrsPagIIA.toString() );
//              lblHorasPagIIIA.setText(HrsPagIIIA.toString() );
//              lblhorasEmb1A.setText(HrsEMBIA.toString());
//              lblhorasEmbIIA.setText(HrsEMBIIA.toString());
//              lblhorasEmbIIIA.setText(HrsEMBIIIA.toString());
//              lblEficManufIA.setText(EficIA.toString());
//              lblEficManufIIA.setText(EficIIA.toString());
//              lblEficManufIIIA.setText(EficIIIA.toString());
//              
//             
//             
//              lblHorasPagIB.setText(HrsPagIB.toString());
//              lblHorasPagIIB.setText(HrsPagIIB.toString());
//              lblHorasPagIIIB.setText(HrsPagIIIB.toString());
//              lblhorasEmbIB.setText(HrsEMBIB.toString());
//              lblhorasEmBIIB.setText(HrsEMBIIB.toString());
//              lblhorasEmbIIIB.setText(HrsEMBIIIB.toString());
//              lblEficManufIB.setText(EficIB.toString());
//              lblEficManufIIB.setText(EficIIB.toString());            
//              lblEficManufIIIB.setText(EficIIIB.toString());
//          }catch(Exception e )
//          {
//          System.out.println(e.toString());
//          }
//      }
     
//      public void EnlazarCbxPlataforma(){
//      cbxPlataforma.removeAllItems();
//      cbxPlataforma.addItem("TODOS");
//        try {
//            ResultSet rs= Principal.cn.GetConsulta("select DISTINCT(codigos.PLATAFORMA) as plataforma from codigos WHERE CODIGOS.CADENA<>7");
//            while(rs.next())
//            {
//                cbxPlataforma.addItem(rs.getString("plataforma"));
//                //modelo.addListDataListener(rs);
//            }
//        } catch (SQLException ex) {
//            System.out.println(ex.toString());
//        }
//    }  
     
//      public void EnlazarCbxArnes(String Plataforma){
//      cbxArnes.removeAllItems();
//      cbxArnes.addItem("TODOS");
//        try {
//            ResultSet rs= Principal.cn.GetConsulta("select DISTINCT(codigos.arnes) as ARNES from codigos where codigos.PLATAFORMA='"+Plataforma+"'");
//            while(rs.next())
//            {
//                cbxArnes.addItem(rs.getString("ARNES"));
//                //modelo.addListDataListener(rs);
//            }
//        } catch (SQLException ex) {
//            System.out.println(ex.toString());
//        }
//    }
      
//      public void DefinirParametros(){
//          try
//          {
//          if((cbxCadena.getSelectedItem()!=null) && (cbxTurno.getSelectedItem()!=null))
//          {
//              switch (cbxCadena.getSelectedItem().toString() )
//            {
//                case "1":
//                    EnlazarDgv(cbxPlataforma.getSelectedItem().toString(), cbxArnes.getSelectedItem().toString(), "1", "0", "0", "0", "0", "TODOS", cbxTurno.getSelectedItem().toString());
//                    break;
//                case "2":
//                    EnlazarDgv(cbxPlataforma.getSelectedItem().toString(), cbxArnes.getSelectedItem().toString(), "0", "1", "0", "0", "0",  "TODOS", cbxTurno.getSelectedItem().toString());
//                break;
//                case "3":
//                     EnlazarDgv(cbxPlataforma.getSelectedItem().toString(), cbxArnes.getSelectedItem().toString(), "0", "0", "1", "0", "0",  "TODOS", cbxTurno.getSelectedItem().toString());
//                break;
//                case "4":
//                    EnlazarDgv(cbxPlataforma.getSelectedItem().toString(), cbxArnes.getSelectedItem().toString(), "0", "0", "0", "1","0",  "TODOS", cbxTurno.getSelectedItem().toString());
//                break;
//                case "5":
//                    EnlazarDgv(cbxPlataforma.getSelectedItem().toString(), cbxArnes.getSelectedItem().toString(), "0", "0", "0", "0", "1", "TODOS", cbxTurno.getSelectedItem().toString());
//                break;
//                case "TODOS":
//                    EnlazarDgv(cbxPlataforma.getSelectedItem().toString(), cbxArnes.getSelectedItem().toString(), Principal.UsuarioLogeado.Cadena1, Principal.UsuarioLogeado.Cadena2, Principal.UsuarioLogeado.Cadena3, Principal.UsuarioLogeado.Cadena4, Principal.UsuarioLogeado.Cadena5, "TODOS", cbxTurno.getSelectedItem().toString());
//                break;
//              }
//          }
//          }catch(Exception e)
//          {
//              System.out.println(e.toString());
//          }
//        
//      }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnSalir = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCodigos = new Compille.RXTable();
        lblCadena = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        btnExportar = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        btnSalir1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("CAPTURA DE MANUF. TODOS");
        setFocusTraversalPolicyProvider(true);
        setPreferredSize(new java.awt.Dimension(1750, 900));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/navigate-left48.png"))); // NOI18N
        btnSalir.setText("Regresar");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        getContentPane().add(btnSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(13, 438, -1, 60));

        tblCodigos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblCodigos.setSelectAllForEdit(true);
        tblCodigos.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tblCodigosPropertyChange(evt);
            }
        });
        jScrollPane1.setViewportView(tblCodigos);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 1600, 370));

        lblCadena.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        lblCadena.setForeground(new java.awt.Color(51, 0, 255));
        getContentPane().add(lblCadena, new org.netbeans.lib.awtextra.AbsoluteConstraints(414, 11, 598, 27));

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel9.setText("CADENA:");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(336, 21, -1, -1));

        btnExportar.setBackground(new java.awt.Color(204, 204, 204));
        btnExportar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/filetype-xls-icon (2).png"))); // NOI18N
        btnExportar.setText("EXPORTAR");
        btnExportar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnExportar.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnExportar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnExportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportarActionPerformed(evt);
            }
        });
        getContentPane().add(btnExportar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1225, 438, 90, 80));

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(483, 438, 384, -1));

        btnSalir1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/navigate-left48.png"))); // NOI18N
        btnSalir1.setText("Regresar");
        btnSalir1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalir1ActionPerformed(evt);
            }
        });
        getContentPane().add(btnSalir1, new org.netbeans.lib.awtextra.AbsoluteConstraints(877, 458, -1, 60));

        jLabel1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel1.setText("CODIGO:");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 440, 188, 59));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblCodigosPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tblCodigosPropertyChange
        // TODO add your handling code here:
        try{
        if( (tblCodigos.getSelectedRow()>-1))
        {
            
           
            ArrayList<Object> lista=new ArrayList<Object>();
            lista.add(tblCodigos.getValueAt(tblCodigos.getSelectedRow(), 5).toString());
            lista.add(tblCodigos.getValueAt(tblCodigos.getSelectedRow(), 6).toString());
            lista.add(tblCodigos.getValueAt(tblCodigos.getSelectedRow(), 7).toString());
            lista.add(tblCodigos.getValueAt(tblCodigos.getSelectedRow(), 8).toString());
            lista.add(tblCodigos.getValueAt(tblCodigos.getSelectedRow(), 9).toString());
            lista.add(tblCodigos.getValueAt(tblCodigos.getSelectedRow(), 10).toString());
            lista.add(tblCodigos.getValueAt(tblCodigos.getSelectedRow(), 11).toString());
            lista.add(tblCodigos.getValueAt(tblCodigos.getSelectedRow(), 12).toString());
            lista.add(tblCodigos.getValueAt(tblCodigos.getSelectedRow(), 13).toString());
            lista.add(tblCodigos.getValueAt(tblCodigos.getSelectedRow(), 14).toString());
            lista.add(tblCodigos.getValueAt(tblCodigos.getSelectedRow(), 15).toString());
            lista.add(tblCodigos.getValueAt(tblCodigos.getSelectedRow(), 17).toString());
            lista.add(tblCodigos.getValueAt(tblCodigos.getSelectedRow(), 18).toString());
            lista.add(tblCodigos.getValueAt(tblCodigos.getSelectedRow(), 19).toString());
            lista.add(Principal.UsuarioLogeado.codigo);
            if(tblCodigos.getValueAt(tblCodigos.getSelectedRow(), 23).toString().equals("true"))
             lista.add(1);
            else
                lista.add(0);
            lista.add(tblCodigos.getValueAt(tblCodigos.getSelectedRow(), 0).toString());        
            Principal.cn.EjecutarInsertOb("update MANUFACTURA set  HCDIRLINEA=?, hcdirconte=?, hcdirsoporte=?,  hcdirtabinsp=?, hcrutasint=?, hcdirlps=?,  hcdirsoplps=?,  hcdirpilotos=?,   hcdirftq=?,  hcdirsistemas=?, hcindrutas=?, salidaenpieza=?,  puntospzapond=?,   cap_util_hta=?,   usuariomodif=?, activo=?  where manufactura.idcodigo=?", lista);
            //DefinirParametros();
            EnlazarDgv();
            //enlazarPorc();
           
       }
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
    }//GEN-LAST:event_tblCodigosPropertyChange

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
        MenuManufactura p=new MenuManufactura();
        p.setLocationRelativeTo(null);
        p.setVisible(true);
    }//GEN-LAST:event_formWindowClosed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnExportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportarActionPerformed
        try {
            // TODO add your handling code here:
            ArrayList<DefaultTableModel> modelos=new ArrayList<DefaultTableModel>();
            ArrayList<String> modelosS=new ArrayList<String>();
            
            modelosS.add("1A");
            modelosS.add("1B");
            modelosS.add("2A");
            modelosS.add("2B");
            modelosS.add("3A");
            modelosS.add("3B");
            
            modelosS.add("6A");
            modelosS.add("6B");
            modelosS.add("5A");
            modelosS.add("5B");

            modelos.add(getModeloMFG("1", "A"));
            modelos.add(getModeloMFG("1", "B"));
            modelos.add(getModeloMFG("2", "A"));
            modelos.add(getModeloMFG("2", "B"));
            modelos.add(getModeloLPS("3", "A"));
            modelos.add(getModeloLPS("3", "B"));
            modelos.add(getModeloMFG("4", "A"));
            modelos.add(getModeloMFG("4", "B"));
           // modelos.add(getModeloMFG("6", "A"));
           // modelos.add(getModeloMFG("6", "B"));
            modelos.add(getModeloMFG("5", "A"));
            modelos.add(getModeloMFG("5", "B"));
            DefaultTableModel corteMochis=CapturaExcelManufactura.GetModeloCorte("4");
           // DefaultTableModel corteGML=CapturaExcelManufactura.GetModeloCorte("6");
            DefaultTableModel plataformas=CapturaExcelManufactura.getModeloplataforma();
            DefaultTableModel plataformasMSD=CapturaExcelManufactura.getModeloplataformaMSD();
            DefaultTableModel tCODIGO=CapturaExcelManufactura.getModelotOTALESXcodigo();
            DefaultTableModel plataformastot=CapturaExcelManufactura.getModeloplataformatOTALES();
            DefaultTableModel plataformasConcentradoLM=CapturaExcelManufactura.concentrado_plataformasLM();
            DefaultTableModel plataformasConcentradogml=CapturaExcelManufactura.concentrado_plataformasGML();
            DefaultTableModel plataformasConcentradoLMYGML=CapturaExcelManufactura.concentrado_plataformasLMYGML();
            DefaultTableModel plataformasGMTLM=CapturaExcelManufactura.getModeloplataformaGMTLM();
            DefaultTableModel plataformasK2XXLM=CapturaExcelManufactura.getModeloplataformaLMK2XX();
            DefaultTableModel plataformasT1XXLM=CapturaExcelManufactura.getModeloplataformaLMT1XX();
            DefaultTableModel plataformasE2XXLM=CapturaExcelManufactura.getModeloplataformaLME2XX();
            DefaultTableModel plataformasISUZULM=CapturaExcelManufactura.getModeloplataformaLMISUZU();
            DefaultTableModel plataformasSERVICIOSLM=CapturaExcelManufactura.getModeloplataformaLMSERVICIOS();
            DefaultTableModel plataformasCORTELM=CapturaExcelManufactura.getModeloplataformaLMCORTE();
             
            DefaultTableModel plataformasGMTGML=CapturaExcelManufactura.getModeloplataformaGMTGML();
            DefaultTableModel plataformasK2XXGML=CapturaExcelManufactura.getModeloplataformaGMLK2XX();
            DefaultTableModel plataformasT1XXGML=CapturaExcelManufactura.getModeloplataformaGMLT1XX();
            DefaultTableModel plataformascorteGML=CapturaExcelManufactura.getModeloplataformaGMLcorte();
            DefaultTableModel plataformasserviciosGML=CapturaExcelManufactura.getModeloplataformaGMLservicios();
            DefaultTableModel CATIA=CapturaExcelManufactura.tablacatia();
            DefaultTableModel PPCORTE=CapturaExcelManufactura.pp_corte();
            DatosManufactura dt=new DatosManufactura();
            
            ExcelEficiencia excel=new ExcelEficiencia(modelos, dt.GetReporteHCJorge(), modelosS,dt.GetReporte(),corteMochis,plataformas,plataformasMSD,tCODIGO,plataformastot,plataformasConcentradoLM,plataformasConcentradogml,plataformasConcentradoLMYGML,plataformasGMTLM,plataformasK2XXLM,plataformasT1XXLM, plataformasE2XXLM,plataformasISUZULM,plataformasSERVICIOSLM,plataformasCORTELM,plataformasGMTGML,plataformasK2XXGML,plataformasT1XXGML,plataformascorteGML,plataformasserviciosGML,CATIA,PPCORTE,"","");
        } catch (Exception ex) {
            System.out.println(ex+"2333");
        } 
        
            
    }//GEN-LAST:event_btnExportarActionPerformed

    private void btnSalir1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalir1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSalir1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CapturaExcelManufactura.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CapturaExcelManufactura.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CapturaExcelManufactura.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CapturaExcelManufactura.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CapturaExcelManufactura(null, null).setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExportar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton btnSalir1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel lblCadena;
    private Compille.RXTable tblCodigos;
    // End of variables declaration//GEN-END:variables
}
