
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package manufactura;

import Clases.Conection;
import Clases.DatosManufactura;

import Reportes.ExcelEficiencia;

import java.awt.Image;
import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author gzld6k
 */
public class MenuManufactura extends javax.swing.JFrame {

    /**
     * Creates new form MenuManufactura
     */
    public MenuManufactura() {
        try {
            initComponents();
            Principal.cn=new Conection();
            enlazarPorc();
            EFIC_ONLY_MOCHIS();
          // EFIC_ONLY_GUA();
            setLocationRelativeTo(null);
             Image icon = new ImageIcon(getClass().getResource("/Images/competitors-icon.png")).getImage();
            setIconImage(icon);
        } catch (Exception ex) {
            Logger.getLogger(MenuManufactura.class.getName()).log(Level.SEVERE, null, ex);
        } 
         Date now = new Date(System.currentTimeMillis());
SimpleDateFormat date = new SimpleDateFormat("yyyy");
SimpleDateFormat date2 = new SimpleDateFormat("yyyy-MM-dd");
SimpleDateFormat hour = new SimpleDateFormat("HH:mm:ss");

System.out.println(date.format(now));
System.out.println(hour.format(now));
//LBL_ANIO.setText(date.format(now));
txt_captura.setText(date2.format(now));
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
    
    public void enlazarPorc(){
          try  {
              Double HrsPagIA=0.0, HrsPagIIA=0.0, HrsPagIIIA=0.0, HrsPagIB=0.0, HrsPagIIB=0.0, HrsPagIIIB=0.0,HrsPagVIA=0.0,HrsPagVIB=0.0,HrsPagVIIIB=0.0,HrsPagVIIIA=0.0;
              Double HrsEMBIA=0.0, HrsEMBIIA=0.0, HrsEMBIIIA=0.0, HrsEMBIB=0.0, HrsEMBIIB=0.0, HrsEMBIIIB=0.0,HrsEMBVIA=0.0,HrsEMBVIB=0.0,HrsEMBVIIIB=0.0,HrsEMBVIIIA=0.0;
              Double EficIA=0.0, EficIIA=0.0, EficIIIA=0.0, EficIB=0.0, EficIIB=0.0, EficIIIB=0.0,EficVIA=0.0,EficVIB=0.0,EficVIIIB=0.0,EficVIIIA=0.0;
              Double HrsPagCorteA=0.0, HrsEmbCorteA=0.0, EficCorteA=0.0
                      , HrsPagCorteB=0.0, HrsEmbCorteB=0.0, EficCorteB=0.0,HrsPagCorteC=0.0, HrsEmbCorteC=0.0, EficCorteC=0.0;
              ResultSet rs= Principal.cn.GetConsulta("select DISTINCT manu.IDCODIGO,manu.CADENA,manu.LINEA, manu.turno, manu.hc, manu.activo,IF(manu.LINEA='CORTE' AND manu.CADENA=6,cortehrsemb.hrsEMBC,manu.horasemb)as horasemb, (manu.hrsPagadas) as hrsPagadas,ROUND(IF(manu.LINEA='CORTE' AND manu.CADENA=6,(cortehrsemb.hrsEMBC/manu.hrsPagadas)*100,(manu.horasemb/manu.hrsPagadas)*100),2)as efic   from \n" +
"              (select c.IDCODIGO,c.arnes,c.LINEA, c.CADENA, c.turno, sum(m.HCDIRLINEA) as hc,round( sum((m.PUNTOSPZAPOND*m.SALIDAENPIEZA/100)),2) as horasemb,  IF(c.TURNO='A', sum(((elinea+estaciones+ kits+HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*9)),\n" +
"               sum(((elinea+estaciones+ kits+ HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*7.9))) as hrsPagadas, m.activo  from manufactura as m, codigos as c where m.idcodigo=c.IDCODIGO and c.CADENA<>4  and c.TURNO='A' GROUP BY c.idcodigo) as manu,\n" +
"               (SELECT SUM(TRUNCATE((((g.HCDIRCORTE*m.SALIDAENPIEZA)/100)),2)) as hrsEMBC FROM gsd as g, manufactura as m, codigos as c where c.IDCODIGO=m.IDCODIGO and c.IDCODIGO=g.IDCODIGO and m.activo=1  and c.TURNO='A' GROUP BY c.TURNO) as cortehrsemb\n" +
"                where manu.CADENA<>4 and manu.activo<>0  UNION select DISTINCT manu.IDCODIGO,manu.CADENA,manu.LINEA, manu.turno, manu.hc, manu.activo,IF(manu.LINEA='CORTE',cortehrsemb.hrsEMBC,manu.horasemb)as horasemb, (manu.hrsPagadas) as hrsPagadas,ROUND(IF(manu.LINEA='CORTE' ,(cortehrsemb.hrsEMBC/manu.hrsPagadas)*100,(manu.horasemb/manu.hrsPagadas)*100),2)as efic   from \n" +
"              (select c.IDCODIGO,c.arnes,c.LINEA, c.CADENA, c.turno, sum(m.HCDIRLINEA) as hc,round( sum((m.PUNTOSPZAPOND*m.SALIDAENPIEZA/100)),2) as horasemb,  IF(c.TURNO='A', sum(((elinea+estaciones+ kits+HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*9)),\n" +
"               sum(((elinea+estaciones+ kits+ HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*7.9))) as hrsPagadas, m.activo  from manufactura as m, codigos as c where m.idcodigo=c.IDCODIGO and c.CADENA<>4 and c.TURNO='B' GROUP BY c.idcodigo) as manu,\n" +
"               (SELECT SUM(TRUNCATE((((g.HCDIRCORTE*m.SALIDAENPIEZA)/100)),2)) as hrsEMBC FROM gsd as g, manufactura as m, codigos as c where c.IDCODIGO=m.IDCODIGO and c.IDCODIGO=g.IDCODIGO and m.activo=1   and c.TURNO='B'  GROUP BY c.TURNO) as cortehrsemb\n" +
"                where manu.CADENA<>4 and manu.activo<>0 and manu.LINEA<>'29A'");
                     // + "select  manu.CADENA, manu.turno, manu.hc, manu.activo,ROUND( (manu.horasemb)*1,2) as horasemb, (manu.hrsPagadas) as hrsPagadas,  ROUND( ( manu.horasemb/manu.hrsPagadas)*100,2) as efic  from \n" +
              //  "(select c.LINEA, c.CADENA, c.turno, sum(m.HCDIRLINEA) as hc,round( sum((m.PUNTOSPZAPOND*m.SALIDAENPIEZA/100))) as horasemb,  IF(c.TURNO='A', sum(((elinea+estaciones+ kits+HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*9)),"+
               //       "sum(((elinea+estaciones+ kits+ HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*7.9))) as hrsPagadas, m.activo  from manufactura as m, codigos as c where m.idcodigo=c.IDCODIGO  GROUP BY c.idcodigo) as manu\n" +
              //  "where manu.CADENA<>4 and manu.activo<>0");
              while(rs.next())
              {
                  switch(rs.getString("CADENA"))
                  {
                      case "1":
                          if(rs.getString("turno").equals("A"))
                          {
                          HrsPagIA+=rs.getDouble("hrsPagadas");
                          HrsEMBIA+=rs.getDouble("horasemb");
                          }else if(rs.getString("turno").equals("B"))
                          {
                          HrsPagIB+=rs.getDouble("hrsPagadas");
                          HrsEMBIB+=rs.getDouble("horasemb");
                          }
                          break;
                      case "2":
                         if(rs.getString("turno").equals("A"))
                          {
                          HrsPagIIA+=rs.getDouble("hrsPagadas");
                          HrsEMBIIA+=rs.getDouble("horasemb");
                          }else if(rs.getString("turno").equals("B"))
                          {
                          HrsPagIIB+=rs.getDouble("hrsPagadas");
                          HrsEMBIIB+=rs.getDouble("horasemb");
                          }
                          break;
                      case "3":
               if(rs.getString("turno").equals("A"))
                          {
                          HrsPagIIIA+=rs.getDouble("hrsPagadas");
                          HrsEMBIIIA+=rs.getDouble("horasemb");
                          }else if(rs.getString("turno").equals("B"))
                          {
                          HrsPagIIIB+=rs.getDouble("hrsPagadas");
                          HrsEMBIIIB+=rs.getDouble("horasemb");
                          }
                          break;
                          case "6":
               if(rs.getString("turno").equals("A"))
                          {
                          HrsPagVIA+=rs.getDouble("hrsPagadas");
                          HrsEMBVIA+=rs.getDouble("horasemb");
                          }else if(rs.getString("turno").equals("B"))
                          {
                          HrsPagVIB+=rs.getDouble("hrsPagadas");
                          HrsEMBVIB+=rs.getDouble("horasemb");
                          }
                          break;
                              
                         
                              case "5":
               if(rs.getString("turno").equals("A"))
                          {
                          HrsPagVIIIA+=rs.getDouble("hrsPagadas");
                          HrsEMBVIIIB+=rs.getDouble("horasemb");
                          }else if(rs.getString("turno").equals("B"))
                          {
                          HrsPagVIIIB+=rs.getDouble("hrsPagadas");
                          HrsEMBVIIIB+=rs.getDouble("horasemb");
                          }
                          break;
                              
                  }
              }
//                rs=Principal.cn.GetConsulta("SELECT DISTINCT\n" +
//                "corte.idcodigo,\n" +
//                "hcpag.IDCODIGO, hcpag.HCDIRLINEA, corte.hrsEMBC, hcpag.horaspagadas, hcpag.TURNO, truncate((corte.hrsEMBC/hcpag.horaspagadas)*100,2) as eficiencia \n" +
//                "from \n" +
//                "(select c.IDCODIGO, c.LINEA, c.CODIGO, c.TURNO, m.SALIDAENPIEZA, g.HCDIRCORTE as cortee, round( SUM(TRUNCATE((((g.HCDIRCORTE*m.SALIDAENPIEZA)/100)),2))) as hrsEMBC,   g.HCDIRLPS as 'POND.LPS', TRUNCATE( if(c.TURNO='A', ((g.HCDIRLPS *m.SALIDAENPIEZA)/9/100), ((g.HCDIRLPS*m.SALIDAENPIEZA)/7.9/100)),2) as LPS, g.HCDIRENSFINAL as 'PUNTOS.EF', TRUNCATE( if(c.TURNO='A', ((g.HCDIRENSFINAL *m.SALIDAENPIEZA)/9/100),  ((g.HCDIRENSFINAL*m.SALIDAENPIEZA)/7.9/100)),2) as ENSFINAL  FROM codigos as c, manufactura as m, gsd  as g where c.IDCODIGO=m.IDCODIGO and c.IDCODIGO=g.IDCODIGO and m.activo=1   GROUP BY c.TURNO) as corte,\n" +
//                "(select c.IDCODIGO, HCDIRLINEA, C.TURNO, if(c.TURNO='A', sum((M.HCDIRLPS+M.HCDIRSOPLPS +M.HCDIRLINEA+ m.hcdirconte+m.HCDIRSOPORTE+m.HCDIRTABINSP+m.hcrutasint+m.hcdirpilotos+m.hcdirftq+ m.hcdirsistemas)*9), SUM((M.HCDIRLPS+M.HCDIRSOPLPS +M.HCDIRLINEA+ m.hcdirconte+m.HCDIRSOPORTE+m.HCDIRTABINSP+m.hcrutasint+m.hcdirpilotos+m.hcdirftq+ m.hcdirsistemas)*7.9)) as horaspagadas from manufactura as m, codigos as c where m.IDCODIGO=c.IDCODIGO and c.CADENA=4 GROUP BY c.TURNO) as hcpag");
               rs=Principal.cn.GetConsulta("SELECT DISTINCT\n" +
"                corte.idcodigo,\n" +
"                hcpag.IDCODIGO, hcpag.HCDIRLINEA, corte.hrsEMBC, hcpag.horaspagadas, hcpag.TURNO, truncate((corte.hrsEMBC/hcpag.horaspagadas)*100,2) as eficiencia \n" +
"                from \n" +
"                (select c.IDCODIGO, c.LINEA, c.CODIGO, c.TURNO, m.SALIDAENPIEZA, g.HCDIRCORTE as cortee,  SUM(TRUNCATE((((g.HCDIRCORTE*m.SALIDAENPIEZA)/100)),2)) as hrsEMBC,   g.HCDIRLPS as 'POND.LPS', TRUNCATE( if(c.TURNO='B' or c.TURNO='C', ((g.HCDIRLPS*m.SALIDAENPIEZA)/7.9/100),((g.HCDIRLPS *m.SALIDAENPIEZA)/9/100)) ,2) as LPS, g.HCDIRENSFINAL as 'PUNTOS.EF', TRUNCATE( if(c.TURNO='B' or c.TURNO='C',((g.HCDIRENSFINAL*m.SALIDAENPIEZA)/7.9/100), ((g.HCDIRENSFINAL *m.SALIDAENPIEZA)/9/100)),2) as ENSFINAL  FROM codigos as c, manufactura as m, gsd  as g where c.IDCODIGO=m.IDCODIGO and c.IDCODIGO=g.IDCODIGO and m.activo=1 and CADENA<>6  GROUP BY c.TURNO) as corte,\n" +
"                (select c.IDCODIGO, HCDIRLINEA, C.TURNO, "+
                     "case c.TURNO when'B' THEN SUM((M.elinea+M.estaciones+ M.kits+M.HCDIRLPS+M.HCDIRSOPLPS +M.HCDIRLINEA+ m.hcdirconte+m.HCDIRSOPORTE+m.HCDIRTABINSP+m.hcrutasint+m.hcdirpilotos+m.hcdirftq+ m.hcdirsistemas)*7.9) "
                     + " WHEN 'A' THEN sum((M.elinea+M.estaciones+ M.kits+M.HCDIRLPS+M.HCDIRSOPLPS +M.HCDIRLINEA+ m.hcdirconte+m.HCDIRSOPORTE+m.HCDIRTABINSP+m.hcrutasint+m.hcdirpilotos+m.hcdirftq+ m.hcdirsistemas)*9) "+
                      " WHEN 'C' THEN sum((M.elinea+M.estaciones+ M.kits+M.HCDIRLPS+M.HCDIRSOPLPS +M.HCDIRLINEA+ m.hcdirconte+m.HCDIRSOPORTE+m.HCDIRTABINSP+m.hcrutasint+m.hcdirpilotos+m.hcdirftq+ m.hcdirsistemas)*7.32)  END "+
                     "as horaspagadas from manufactura as m, codigos as c where m.IDCODIGO=c.IDCODIGO and c.CADENA=4   GROUP BY c.TURNO) as hcpag ");
          
              
              int cont=1;
                while(rs.next())
               {
                if(cont==1)
                { 
                    HrsEmbCorteA=Regresa2Decimales( rs.getDouble("hrsEMBC"));
                    HrsPagCorteA=Regresa2Decimales(rs.getDouble("horaspagadas"));
                    EficCorteA=Regresa2Decimales(rs.getDouble("eficiencia"));
                }
                if(cont==13)
                {
                    HrsEmbCorteB=Regresa2Decimales(rs.getDouble("hrsEMBC"));
                    HrsPagCorteB=Regresa2Decimales(rs.getDouble("horaspagadas"));
                    EficCorteB=Regresa2Decimales(rs.getDouble("eficiencia"));
                }
                //turno c
                if(cont==24)
                {
                    HrsEmbCorteC=Regresa2Decimales(rs.getDouble("hrsEMBC"));
                    HrsPagCorteC=Regresa2Decimales(rs.getDouble("horaspagadas"));
                    EficCorteC=Regresa2Decimales(rs.getDouble("eficiencia"));
                }
                cont++;
               }
                            rs=Principal.cn.GetConsulta("SELECT codigos.turno, manufactura.HCDIRLINEA\n" +
                    "FROM\n" +
                    "manufactura ,\n" +
                    "codigos \n" +
                    "WHERE\n" +
                    "manufactura.IDCODIGO = codigos.IDCODIGO and CODIGOS.linea='91'");
               Double kachirulesA=0.0, kachirulesB=0.0;
               while(rs.next())
               {
                   if(rs.getString("TURNO").equals("A"))
                   kachirulesA=rs.getDouble("HCDIRLINEA");
                   else
                   kachirulesB=rs.getDouble("HCDIRLINEA");
               }
               
               
              rs=Principal.cn.GetConsulta("SELECT manufactura.HCDIRLINEA\n" +
                    "FROM\n" +
                    "manufactura ,\n" +
                    "codigos \n" +
                    "WHERE\n" +
                    "manufactura.IDCODIGO = codigos.IDCODIGO and CODIGOS.PLATAFORMA='SERVICIOS' and codigos.TURNO='a'");
               Double ServiciosA=0.0;
               if(rs.next())
               {
                   ServiciosA=rs.getDouble("HCDIRLINEA");
               }
               
                rs=Principal.cn.GetConsulta("SELECT manufactura.HCDIRLINEA\n" +
                    "FROM\n" +
                    "manufactura ,\n" +
                    "codigos \n" +
                    "WHERE\n" +
                    "manufactura.IDCODIGO = codigos.IDCODIGO and CODIGOS.PLATAFORMA='SERVICIOS' and codigos.TURNO='b'");
               Double ServiciosB=0.0;
               if(rs.next())
               {
                   ServiciosB=rs.getDouble("HCDIRLINEA");
               }
               
              EficIA=( HrsEMBIA/HrsPagIA)*100;
              EficIIA=(HrsEMBIIA/HrsPagIIA)*100;
              EficIIIA=(HrsEMBIIIA/HrsPagIIIA)*100;
              EficVIA=(HrsEMBVIA/HrsPagVIA)*100;
              EficVIIIA=(HrsEMBVIIIA/HrsPagVIIIA)*100;
               
              EficIB=( HrsEMBIB/HrsPagIB)*100;
              EficIIB=(HrsEMBIIB/HrsPagIIB)*100;
              EficIIIB=(HrsEMBIIIB/HrsPagIIIB)*100;
              EficVIB=(HrsEMBVIB/HrsPagVIB)*100;
              EficVIIIB=(HrsEMBVIIIB/HrsPagVIIIB)*100;
   
              HrsPagIA=(double)Math.round(HrsPagIA);
              HrsPagIIA=(double)Math.round(HrsPagIIA);
              HrsPagIIIA=(double)Math.round(HrsPagIIIA);
              HrsPagVIIIA=(double)Math.round(HrsPagVIIIA);
              
              HrsEMBIA=(double)Math.round(HrsEMBIA);
              HrsEMBIIA=(double)Math.round(HrsEMBIIA);
              HrsEMBIIIA=(double)Math.round(HrsEMBIIIA);
              HrsEMBVIIIA=(double)Math.round(HrsEMBVIIIA);
              
              HrsPagIB=(double)Math.round(HrsPagIB);
              HrsPagIIB=(double)Math.round(HrsPagIIB);
              HrsPagIIIB=(double)Math.round(HrsPagIIIB);
              HrsPagVIIIB=(double)Math.round(HrsPagVIIIB);
            
              HrsEMBIB=(double)Math.round(HrsEMBIB);
              HrsEMBIIB=(double)Math.round(HrsEMBIIB);
              HrsEMBIIIB=(double)Math.round(HrsEMBIIIB);
              HrsEMBVIA=(double)Math.round(HrsEMBVIA);
              HrsEMBVIB=(double)Math.round(HrsEMBVIB);
              HrsEMBVIIIB=(double)Math.round(HrsEMBVIIIB);
              HrsEMBVIIIA=(double)Math.round(HrsEMBVIIIA);
              
              
              
              
              EficIA=Regresa2Decimales(EficIA);
              EficIIA=Regresa2Decimales(EficIIA);
              EficIIIA=Regresa2Decimales(EficIIIA);
              EficIB=Regresa2Decimales(EficIB);
              EficIIB=Regresa2Decimales(EficIIB);
              EficIIIB=Regresa2Decimales(EficIIIB);
              EficVIA=Regresa2Decimales(EficVIA);
              EficVIB=Regresa2Decimales(EficVIB);
              EficVIIIB=Regresa2Decimales(EficVIIIB);
              EficVIIIA=Regresa2Decimales(EficVIIIA);
              
             
              Double HrsEMBTotalA= Regresa2Decimales( HrsEMBIA+HrsEMBIIA+HrsEMBIIIA+HrsEMBVIA+HrsEmbCorteA+HrsEMBVIIIA);
              Double HrsEMBTotalB=Regresa2Decimales( HrsEMBIB+HrsEMBIIB+HrsEMBIIIB+HrsEMBVIB+HrsEmbCorteB+HrsEMBVIIIB);
              Double HrsPagTotalA=Regresa2Decimales( HrsPagIA+HrsPagIIA+HrsPagIIIA+HrsPagVIA+HrsPagCorteA+((kachirulesA+ServiciosA)*9)+HrsPagVIIIA);
              Double HrsPagTotalB=Regresa2Decimales( HrsPagIB+HrsPagIIB+HrsPagIIIB+HrsPagVIB+HrsPagCorteB+((kachirulesB+ServiciosB)*7.9)+HrsPagVIIIB);
              Double EficTotalA=Regresa2Decimales((HrsEMBTotalA/HrsPagTotalA)*100);
              Double EficTotalB=Regresa2Decimales( (HrsEMBTotalB/HrsPagTotalB)*100);
              Double HrsTotalEMB=Regresa2Decimales(HrsEMBTotalA+HrsEMBTotalB+HrsEmbCorteC)+15+0;
              Double HrsTotalPag=Regresa2Decimales(HrsPagTotalA+HrsPagTotalB+HrsPagCorteC);
              Double EficTotal=Regresa2Decimales((HrsTotalEMB/HrsTotalPag)*100);
              //Double HrsPagTotalB=
              lblHorasPagIA.setText(HrsPagIA.toString() );
              lblHorasPagIIA.setText(HrsPagIIA.toString() );
              lblHorasPagIIIA.setText(HrsPagIIIA.toString() );
             
               
              lblhorasEmbIA.setText(HrsEMBIA.toString());
              lblhorasEmbIIA.setText(HrsEMBIIA.toString());
              lblhorasEmbIIIA.setText(HrsEMBIIIA.toString());
              
              
              
              lblEficManufIA.setText(EficIA.toString()+" %");
              lblEficManufIIA.setText(EficIIA.toString()+" %");
              lblEficManufIIIA.setText(EficIIIA.toString()+" %");
              lblEficManufIVA.setText(EficCorteA.toString()+" %");
              lblEficManufIVB.setText(EficCorteB.toString()+" %");
              lblEficManufIVC.setText(EficCorteC.toString()+" %");
              
              lblhorasEmbIVA.setText(HrsEmbCorteA.toString());
              lblHorasPagIVA.setText(HrsPagCorteA.toString());
              lblhorasEmbIVB.setText(HrsEmbCorteB.toString());
              lblHorasPagIVB.setText(HrsPagCorteB.toString());
              lblHorasPagVIA.setText(HrsPagVIA.toString());
              lblHorasPagVIB.setText(HrsPagVIB.toString());
              lblHorasPagIVC.setText(HrsPagCorteC.toString());
              
             
          
              lblHorasPagIB.setText(HrsPagIB.toString());
              lblHorasPagIIB.setText(HrsPagIIB.toString());
              lblHorasPagIIIB.setText(HrsPagIIIB.toString());
              
              lblhorasEmbIB.setText(HrsEMBIB.toString());
              lblhorasEmbIIB.setText(HrsEMBIIB.toString());
              lblhorasEmbIIIB.setText(HrsEMBIIIB.toString());
              lblhorasEmbVIA.setText(HrsEMBVIA.toString());
              lblhorasEmbVIB.setText(HrsEMBVIB.toString());
              lblhorasEmbIVC.setText(HrsEmbCorteC.toString());
             
               
              lblEficManufIB.setText(EficIB.toString()+" %");
              lblEficManufIIB.setText(EficIIB.toString()+" %");            
              lblEficManufIIIB.setText(EficIIIB.toString()+" %");
              lblEficManufVIA.setText(EficVIA.toString()+" %");
              lblEficManufVIB.setText(EficVIB.toString()+" %");
             
             
              lblHorasPagPlantaA.setText(HrsPagTotalA.toString());
              lblHorasPagPlantaB.setText(HrsPagTotalB.toString());
              lblhorasEmbPlantaA.setText(HrsEMBTotalA.toString());
              lblhorasEmbPlantaB.setText(HrsEMBTotalB.toString());
              lblEficManufPlantaA.setText(EficTotalA.toString()+" %");
              lblEficManufPlantaB.setText(EficTotalB.toString()+" %");
              lblhorasEmbPlanta.setText(HrsTotalEMB.toString());
              lblHorasPagPlanta.setText(HrsTotalPag.toString());
              lblEficManufPlanta.setText(EficTotal.toString()+ " %");
              
              
          }catch(Exception e )
          {
          System.out.println(e.toString());
          }
      }
    
     public void EFIC_ONLY_MOCHIS(){
          try  {
              Double HrsPagIA1=0.0, HrsPagIIA1=0.0, HrsPagIIIA1=0.0, HrsPagIB1=0.0, HrsPagIIB1=0.0, HrsPagIIIB1=0.0,HrsPagVIIIB=0.0,HrsPagVIIIA=0.0;
              Double HrsEMBIA1=0.0, HrsEMBIIA1=0.0, HrsEMBIIIA1=0.0, HrsEMBIB1=0.0, HrsEMBIIB1=0.0, HrsEMBIIIB1=0.0,HrsEMBVIIIA=0.0,HrsEMBVIIIB=0.0;
              Double EficIA1=0.0, EficIIA1=0.0, EficIIIA1=0.0, EficIB1=0.0, EficIIB1=0.0, EficIIIB1=0.0, EficVIIIB=0.0, EficVIIIA=0.0;
              Double HrsPagCorteA1=0.0, HrsEmbCorteA1=0.0, EficCorteA1=0.0, HrsPagCorteB1=0.0, HrsEmbCorteB1=0.0, EficCorteB1=0.0,HrsPagCorteC1=0.0, HrsEmbCorteC1=0.0, EficCorteC1=0.0;; 
              ResultSet rs1= Principal.cn.GetConsulta("select  manu.CADENA, manu.turno, manu.hc, manu.activo,  (manu.horasemb) as horasemb, (manu.hrsPagadas) as hrsPagadas,  ( manu.horasemb/manu.hrsPagadas)*100 as efic from \n" +
                "(select c.LINEA, c.CADENA, c.turno, sum(m.HCDIRLINEA+elinea+ estaciones+kits) as hc,round( sum((m.PUNTOSPZAPOND*m.SALIDAENPIEZA/100)),2) as horasemb,  IF(c.TURNO='A', sum(((elinea+ estaciones+kits+HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*9)),sum(((+m.elinea+ m.estaciones+m.kits+HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*7.9))) as hrsPagadas, m.activo  from manufactura as m, codigos as c where m.idcodigo=c.IDCODIGO and m.activo=1  GROUP BY c.idcodigo) as manu\n" +
                "where manu.CADENA<>4 and manu.activo<>0 and manu.CADENA<>6 and manu.linea<>'29A' ");
              while(rs1.next())
              {
                  switch(rs1.getString("CADENA"))
                  {
                      case "1":
                          if(rs1.getString("turno").equals("A"))
                          {
                          HrsPagIA1+=rs1.getDouble("hrsPagadas");
                          HrsEMBIA1+=rs1.getDouble("horasemb");
                          }else if(rs1.getString("turno").equals("B"))
                          {
                          HrsPagIB1+=rs1.getDouble("hrsPagadas");
                          HrsEMBIB1+=rs1.getDouble("horasemb");
                          }
                          break;
                      case "2":
                         if(rs1.getString("turno").equals("A"))
                          {
                          HrsPagIIA1+=rs1.getDouble("hrsPagadas");
                          HrsEMBIIA1+=rs1.getDouble("horasemb");
                          }else if(rs1.getString("turno").equals("B"))
                          {
                          HrsPagIIB1+=rs1.getDouble("hrsPagadas");
                          HrsEMBIIB1+=rs1.getDouble("horasemb");
                          }
                          break;
                      case "3":
               if(rs1.getString("turno").equals("A"))
                          {
                          HrsPagIIIA1+=rs1.getDouble("hrsPagadas");
                          HrsEMBIIIA1+=rs1.getDouble("horasemb");
                          }else if(rs1.getString("turno").equals("B"))
                          {
                          HrsPagIIIB1+=rs1.getDouble("hrsPagadas");
                          HrsEMBIIIB1+=rs1.getDouble("horasemb");
                          }
                          break;
                          
                          case "5":
               if(rs1.getString("turno").equals("A"))
                          {
                          HrsPagVIIIA+=rs1.getDouble("hrsPagadas");
                          HrsEMBVIIIA+=rs1.getDouble("horasemb");
                          }else if(rs1.getString("turno").equals("B"))
                          {
                          HrsPagVIIIB+=rs1.getDouble("hrsPagadas");
                          HrsEMBVIIIB+=rs1.getDouble("horasemb");
                          }
                          break;
                     
                  }
              }
                  rs1=Principal.cn.GetConsulta("SELECT DISTINCT\n" +
"               corte.idcodigo,\n" +
"               hcpag.IDCODIGO, hcpag.HCDIRLINEA, corte.hrsEMBC, hcpag.horaspagadas, hcpag.TURNO, truncate((corte.hrsEMBC/hcpag.horaspagadas)*100,2) as eficiencia                 from \n" +
"               (select c.IDCODIGO, c.LINEA, c.CODIGO, c.TURNO, m.SALIDAENPIEZA, g.HCDIRCORTE as cortee,  SUM(TRUNCATE((((g.HCDIRCORTE*m.SALIDAENPIEZA)/100)),2)) as hrsEMBC,   g.HCDIRLPS as 'POND.LPS', TRUNCATE( if(c.TURNO='B' or c.TURNO='C', ((g.HCDIRLPS*m.SALIDAENPIEZA)/7.9/100),((g.HCDIRLPS *m.SALIDAENPIEZA)/9/100)) ,2) as LPS, g.HCDIRENSFINAL as 'PUNTOS.EF', TRUNCATE( if(c.TURNO='B' or c.TURNO='C',((g.HCDIRENSFINAL*m.SALIDAENPIEZA)/7.9/100), ((g.HCDIRENSFINAL *m.SALIDAENPIEZA)/9/100)),2) as ENSFINAL  FROM codigos as c, manufactura as m, gsd  as g where c.IDCODIGO=m.IDCODIGO and c.IDCODIGO=g.IDCODIGO and m.activo=1 and CADENA<>6  GROUP BY c.TURNO) as corte,\n" +
"                (select c.IDCODIGO, HCDIRLINEA, C.TURNO, \n" +
"                    case c.TURNO when'B' THEN SUM((M.elinea+M.estaciones+ M.kits+M.HCDIRLPS+M.HCDIRSOPLPS +M.HCDIRLINEA+ m.hcdirconte+m.HCDIRSOPORTE+m.HCDIRTABINSP+m.hcrutasint+m.hcdirpilotos+m.hcdirftq+ m.hcdirsistemas)*7.9) \n" +
"                      WHEN 'A' THEN sum((M.elinea+M.estaciones+ M.kits+M.HCDIRLPS+M.HCDIRSOPLPS +M.HCDIRLINEA+ m.hcdirconte+m.HCDIRSOPORTE+m.HCDIRTABINSP+m.hcrutasint+m.hcdirpilotos+m.hcdirftq+ m.hcdirsistemas)*9) \n" +
"                       WHEN 'C' THEN sum((M.elinea+M.estaciones+ M.kits+M.HCDIRLPS+M.HCDIRSOPLPS +M.HCDIRLINEA+ m.hcdirconte+m.HCDIRSOPORTE+m.HCDIRTABINSP+m.hcrutasint+m.hcdirpilotos+m.hcdirftq+ m.hcdirsistemas)*7.32)  END \n" +
"                     as horaspagadas from manufactura as m, codigos as c where m.IDCODIGO=c.IDCODIGO and c.CADENA=4   GROUP BY c.TURNO) as hcpag");
             int cont=1; 
                while(rs1.next())
               {
                if(cont==1)
                { 
                    HrsEmbCorteA1=Regresa2Decimales( rs1.getDouble("hrsEMBC"));
                    HrsPagCorteA1=Regresa2Decimales(rs1.getDouble("horaspagadas"));
                    EficCorteA1=Regresa2Decimales(rs1.getDouble("eficiencia"));
                }
                if(cont==13)
                {
                    HrsEmbCorteB1=Regresa2Decimales(rs1.getDouble("hrsEMBC"));
                    HrsPagCorteB1=Regresa2Decimales(rs1.getDouble("horaspagadas"));
                    EficCorteB1=Regresa2Decimales(rs1.getDouble("eficiencia"));
                }
                //turno c
                if(cont==9)
                {
                    HrsEmbCorteC1=Regresa2Decimales(rs1.getDouble("hrsEMBC"));
                    HrsPagCorteC1=Regresa2Decimales(rs1.getDouble("horaspagadas"));
                    EficCorteC1=Regresa2Decimales(rs1.getDouble("eficiencia"));
                }
                cont++;
               }
                            rs1=Principal.cn.GetConsulta("SELECT codigos.turno, manufactura.HCDIRLINEA\n" +
                    "FROM\n" +
                    "manufactura ,\n" +
                    "codigos \n" +
                    "WHERE\n" +
                    "manufactura.IDCODIGO = codigos.IDCODIGO and CODIGOS.linea='91'");
               Double kachirulesA=0.0, kachirulesB=0.0;
               while(rs1.next())
               {
                   if(rs1.getString("TURNO").equals("A"))
                   kachirulesA=rs1.getDouble("HCDIRLINEA");
                   else
                   kachirulesB=rs1.getDouble("HCDIRLINEA");
               }
               
              rs1=Principal.cn.GetConsulta("SELECT manufactura.HCDIRLINEA\n" +
                    "FROM\n" +
                    "manufactura ,\n" +
                    "codigos \n" +
                    "WHERE\n" +
                    "manufactura.IDCODIGO = codigos.IDCODIGO and CODIGOS.PLATAFORMA='SERVICIOS' and codigos.TURNO='a'");
               Double ServiciosA=0.0;
               if(rs1.next())
               {
                   ServiciosA=rs1.getDouble("HCDIRLINEA");
               }
               
                rs1=Principal.cn.GetConsulta("SELECT manufactura.HCDIRLINEA\n" +
                    "FROM\n" +
                    "manufactura ,\n" +
                    "codigos \n" +
                    "WHERE\n" +
                    "manufactura.IDCODIGO = codigos.IDCODIGO and CODIGOS.PLATAFORMA='SERVICIOS' and codigos.TURNO='b'");
               Double ServiciosB=0.0;
               if(rs1.next())
               {
                   ServiciosB=rs1.getDouble("HCDIRLINEA");
               }
               
              EficIA1=( HrsEMBIA1/HrsPagIA1)*100;
              EficIIA1=(HrsEMBIIA1/HrsPagIIA1)*100;
              EficIIIA1=(HrsEMBIIIA1/HrsPagIIIA1)*100;
              EficIB1=( HrsEMBIB1/HrsPagIB1)*100;
              EficIIB1=(HrsEMBIIB1/HrsPagIIB1)*100;
              EficIIIB1=(HrsEMBIIIB1/HrsPagIIIB1)*100;
             
              EficVIIIA=(HrsEMBVIIIA/HrsPagVIIIA)*100;
              EficVIIIB=(HrsEMBVIIIB/HrsPagVIIIB)*100;
             
              
              HrsPagIA1=(double)Math.round(HrsPagIA1);
              HrsPagIIA1=(double)Math.round(HrsPagIIA1);
              HrsPagIIIA1=(double)Math.round(HrsPagIIIA1);
              HrsEMBIA1=(double)Math.round(HrsEMBIA1);
              HrsEMBIIA1=(double)Math.round(HrsEMBIIA1);
              HrsEMBIIIA1=(double)Math.round(HrsEMBIIIA1);
              HrsPagIB1=(double)Math.round(HrsPagIB1);
              HrsPagIIB1=(double)Math.round(HrsPagIIB1);
              HrsPagIIIB1=(double)Math.round(HrsPagIIIB1);
              HrsEMBIB1=(double)Math.round(HrsEMBIB1);
              HrsEMBIIB1=(double)Math.round(HrsEMBIIB1);
              HrsEMBIIIB1=(double)Math.round(HrsEMBIIIB1);
              
               HrsEMBVIIIA=(double)Math.round(HrsEMBVIIIA);
               HrsEMBVIIIB=(double)Math.round(HrsEMBVIIIB);

              Double HrsEMBTotalA1= Regresa2Decimales( HrsEMBIA1+HrsEMBIIA1+HrsEMBIIIA1+HrsEmbCorteA1+HrsEMBVIIIA);
              Double HrsEMBTotalB1=Regresa2Decimales( HrsEMBIB1+HrsEMBIIB1+HrsEMBIIIB1+HrsEmbCorteB1+HrsEMBVIIIB);
              Double HrsPagTotalA1=Regresa2Decimales( HrsPagIA1+HrsPagIIA1+HrsPagIIIA1+HrsPagCorteA1+((kachirulesA+ServiciosA)*9)+HrsPagVIIIA);
              Double HrsPagTotalB1=Regresa2Decimales( HrsPagIB1+HrsPagIIB1+HrsPagIIIB1+HrsPagCorteB1+((kachirulesB+ServiciosB)*7.9)+HrsPagVIIIB);
              //Double EficTotalA=Regresa2Decimales((HrsEMBTotalA1/HrsPagTotalA1)*100);
             // Double EficTotalB=Regresa2Decimales( (HrsEMBTotalB1/HrsPagTotalB1)*100);
              Double HrsTotalEMB1=Regresa2Decimales(HrsEMBTotalA1+HrsEMBTotalB1+HrsEmbCorteC1+15+0);
              //Double HrsTotalEMB1=Regresa2Decimales(HrsEMBTotalA1+HrsEMBTotalB1+HrsEmbCorteC1);
              Double HrsTotalPag1=Regresa2Decimales(HrsPagTotalA1+HrsPagTotalB1+HrsPagCorteC1);
              Double EficTotal1=Regresa2Decimales((HrsTotalEMB1/HrsTotalPag1)*100);
           
 
              lblhorasEmbPlanta1.setText(HrsTotalEMB1.toString()+ " %");
              lblHorasPagPlanta1.setText(HrsTotalPag1.toString()+ " %");
              lblEficManufPlanta1.setText(EficTotal1.toString()+ " %");
          }catch(Exception e )
          {
          System.out.println(e.toString());
          }
      }
    
      public void EFIC_ONLY_GUA(){
          try  {
              Double HrsPagIA1=0.0, HrsPagIIA1=0.0, HrsPagIIIA1=0.0, HrsPagIB1=0.0, HrsPagIIB1=0.0, HrsPagIIIB1=0.0;
              Double HrsEMBIA1=0.0, HrsEMBIIA1=0.0, HrsEMBIIIA1=0.0, HrsEMBIB1=0.0, HrsEMBIIB1=0.0, HrsEMBIIIB1=0.0;
              Double EficIA1=0.0, EficIIA1=0.0, EficIIIA1=0.0, EficIB1=0.0, EficIIB1=0.0, EficIIIB1=0.0;
              Double HrsPagCorteA1=0.0, HrsEmbCorteA1=0.0, EficCorteA1=0.0, HrsPagCorteB1=0.0, HrsEmbCorteB1=0.0, EficCorteB1=0.0,HrsPagCorteC1=0.0, HrsEmbCorteC1=0.0, EficCorteC1=0.0;; 
          
                ResultSet rs1= Principal.cn.GetConsulta("select DISTINCT manu.IDCODIGO,manu.CADENA,manu.LINEA, manu.turno, manu.hc, manu.activo,IF(manu.LINEA='CORTE' AND manu.CADENA=6,cortehrsemb.hrsEMBC,manu.horasemb)as horasemb, (manu.hrsPagadas) as hrsPagadas,ROUND(IF(manu.LINEA='CORTE' AND manu.CADENA=6,(cortehrsemb.hrsEMBC/manu.hrsPagadas)*100,(manu.horasemb/manu.hrsPagadas)*100),2)as efic   from \n" +
"              (select c.IDCODIGO,c.arnes,c.LINEA, c.CADENA, c.turno, sum(m.HCDIRLINEA) as hc,round( sum((m.PUNTOSPZAPOND*m.SALIDAENPIEZA/100)),2) as horasemb,  IF(c.TURNO='A', sum(((elinea+estaciones+ kits+HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*9)),\n" +
"               sum(((elinea+estaciones+ kits+ HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*7.9))) as hrsPagadas, m.activo  from manufactura as m, codigos as c where m.idcodigo=c.IDCODIGO and c.CADENA<>4  and c.TURNO='A' GROUP BY c.idcodigo) as manu,\n" +
"               (SELECT SUM(TRUNCATE((((g.HCDIRCORTE*m.SALIDAENPIEZA)/100)),2)) as hrsEMBC FROM gsd as g, manufactura as m, codigos as c where c.IDCODIGO=m.IDCODIGO and c.IDCODIGO=g.IDCODIGO and m.activo=1 AND C.CADENA=6 and c.TURNO='A' GROUP BY c.TURNO) as cortehrsemb\n" +
"                where manu.CADENA<>4 and manu.activo<>0  UNION select DISTINCT manu.IDCODIGO,manu.CADENA,manu.LINEA, manu.turno, manu.hc, manu.activo,IF(manu.LINEA='CORTE' AND manu.CADENA=6,cortehrsemb.hrsEMBC,manu.horasemb)as horasemb, (manu.hrsPagadas) as hrsPagadas,ROUND(IF(manu.LINEA='CORTE' AND manu.CADENA=6,(cortehrsemb.hrsEMBC/manu.hrsPagadas)*100,(manu.horasemb/manu.hrsPagadas)*100),2)as efic   from \n" +
"              (select c.IDCODIGO,c.arnes,c.LINEA, c.CADENA, c.turno, sum(m.HCDIRLINEA) as hc,round( sum((m.PUNTOSPZAPOND*m.SALIDAENPIEZA/100)),2) as horasemb,  IF(c.TURNO='A', sum(((elinea+estaciones+ kits+HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*9)),\n" +
"               sum(((elinea+estaciones+ kits+ HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*7.9))) as hrsPagadas, m.activo  from manufactura as m, codigos as c where m.idcodigo=c.IDCODIGO and c.CADENA<>4 and c.TURNO='B' GROUP BY c.idcodigo) as manu,\n" +
"               (SELECT SUM(TRUNCATE((((g.HCDIRCORTE*m.SALIDAENPIEZA)/100)),2)) as hrsEMBC FROM gsd as g, manufactura as m, codigos as c where c.IDCODIGO=m.IDCODIGO and c.IDCODIGO=g.IDCODIGO and m.activo=1 AND C.CADENA=6  and c.TURNO='B'  GROUP BY c.TURNO) as cortehrsemb\n" +
"                where manu.CADENA<>4 and manu.activo<>0 ");
              
//              ResultSet rs1= Principal.cn.GetConsulta("select  manu.CADENA, manu.turno, manu.hc, manu.activo,ROUND( (manu.horasemb)*1,2) as horasemb, (manu.hrsPagadas) as hrsPagadas,  ROUND( ( manu.horasemb/manu.hrsPagadas)*100,2) as efic  from \n" +
//              "(select c.LINEA, c.CADENA, c.turno, sum(m.HCDIRLINEA) as hc, sum((m.PUNTOSPZAPOND*m.SALIDAENPIEZA/100)) as horasemb,  IF(c.TURNO='A', sum(((elinea+estaciones+ kits+HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*9)),sum(((elinea+estaciones+ kits+HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*7.9))) as hrsPagadas, m.activo  from manufactura as m, codigos as c where m.idcodigo=c.IDCODIGO   GROUP BY c.idcodigo) as manu\n" +
//              " where   manu.activo<>0 and manu.CADENA<>4 and manu.CADENA=6 ");
              while(rs1.next())
              {
                  switch(rs1.getString("CADENA"))
                  {
                      case "6":
                          if(rs1.getString("turno").equals("A"))
                          {
                          HrsPagIA1+=rs1.getDouble("hrsPagadas");
                          HrsEMBIA1+=rs1.getDouble("horasemb");
                          }else if(rs1.getString("turno").equals("B"))
                          {
                          HrsPagIB1+=rs1.getDouble("hrsPagadas");
                          HrsEMBIB1+=rs1.getDouble("horasemb");
                          }
                          break;
                  
                     
                  }
              }
          
             
              
              HrsPagIA1=(double)Math.round(HrsPagIA1);
              HrsPagIIA1=(double)Math.round(HrsPagIIA1);
              HrsPagIIIA1=(double)Math.round(HrsPagIIIA1);
              HrsEMBIA1=(double)Math.round(HrsEMBIA1);
              HrsEMBIIA1=(double)Math.round(HrsEMBIIA1);
              HrsEMBIIIA1=(double)Math.round(HrsEMBIIIA1);
              HrsPagIB1=(double)Math.round(HrsPagIB1);
              HrsPagIIB1=(double)Math.round(HrsPagIIB1);
              HrsPagIIIB1=(double)Math.round(HrsPagIIIB1);
              HrsEMBIB1=(double)Math.round(HrsEMBIB1);
              HrsEMBIIB1=(double)Math.round(HrsEMBIIB1);
              HrsEMBIIIB1=(double)Math.round(HrsEMBIIIB1);
             
              
//              EficIA1=Regresa2Decimales(EficIA1);
//              EficIIA1=Regresa2Decimales(EficIIA1);
//              EficIIIA1=Regresa2Decimales(EficIIIA1);
//              EficIB1=Regresa2Decimales(EficIB1);
//              EficIIB1=Regresa2Decimales(EficIIB1);
//              EficIIIB1=Regresa2Decimales(EficIIIB1);
             
              
              Double HrsEMBTotalA1= Regresa2Decimales( HrsEMBIA1);
              Double HrsEMBTotalB1=Regresa2Decimales( HrsEMBIB1);
              Double HrsPagTotalA1=Regresa2Decimales( HrsPagIA1);
              Double HrsPagTotalB1=Regresa2Decimales( HrsPagIB1);
              //Double EficTotalA=Regresa2Decimales((HrsEMBTotalA1/HrsPagTotalA1)*100);
             // Double EficTotalB=Regresa2Decimales( (HrsEMBTotalB1/HrsPagTotalB1)*100);
              Double HrsTotalEMB1=Regresa2Decimales(HrsEMBTotalA1+HrsEMBTotalB1);
              Double HrsTotalPag1=Regresa2Decimales(HrsPagTotalA1+HrsPagTotalB1);
              Double EficTotal1=Regresa2Decimales((HrsTotalEMB1/HrsTotalPag1)*100);
           
        
             
              lblhorasEmbPlanta2.setText(HrsTotalEMB1.toString()+ " %");
              lblHorasPagPlanta2.setText(HrsTotalPag1.toString()+ " %");
              lblEficManufPlanta2.setText(EficTotal1.toString()+ " %");
          }catch(Exception e )
          {
          System.out.println(e.toString());
          }
      }

   
    
  
      /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        panelCadIA4 = new javax.swing.JPanel();
        jLabel50 = new javax.swing.JLabel();
        lblEficManufIA = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        lblhorasEmbIA = new javax.swing.JLabel();
        lblHorasPagIA = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        panelCadIA6 = new javax.swing.JPanel();
        jLabel56 = new javax.swing.JLabel();
        lblEficManufIB = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        lblhorasEmbIB = new javax.swing.JLabel();
        lblHorasPagIB = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        panelCadIA5 = new javax.swing.JPanel();
        jLabel53 = new javax.swing.JLabel();
        lblEficManufIIA = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        lblhorasEmbIIA = new javax.swing.JLabel();
        lblHorasPagIIA = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        panelCadIA7 = new javax.swing.JPanel();
        jLabel59 = new javax.swing.JLabel();
        lblEficManufIIB = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        lblhorasEmbIIB = new javax.swing.JLabel();
        lblHorasPagIIB = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        panelCadIA15 = new javax.swing.JPanel();
        jLabel83 = new javax.swing.JLabel();
        lblEficManufIVB = new javax.swing.JLabel();
        jLabel84 = new javax.swing.JLabel();
        lblhorasEmbIVB = new javax.swing.JLabel();
        lblHorasPagIVB = new javax.swing.JLabel();
        jLabel85 = new javax.swing.JLabel();
        panelCadIA16 = new javax.swing.JPanel();
        jLabel86 = new javax.swing.JLabel();
        lblEficManufPlantaA = new javax.swing.JLabel();
        jLabel87 = new javax.swing.JLabel();
        lblhorasEmbPlantaA = new javax.swing.JLabel();
        lblHorasPagPlantaA = new javax.swing.JLabel();
        jLabel88 = new javax.swing.JLabel();
        lblhorasEmbPlantaB = new javax.swing.JLabel();
        lblHorasPagPlantaB = new javax.swing.JLabel();
        lblEficManufPlantaB = new javax.swing.JLabel();
        jLabel89 = new javax.swing.JLabel();
        jLabel90 = new javax.swing.JLabel();
        jLabel91 = new javax.swing.JLabel();
        lblhorasEmbPlanta = new javax.swing.JLabel();
        lblHorasPagPlanta = new javax.swing.JLabel();
        lblEficManufPlanta = new javax.swing.JLabel();
        jLabel92 = new javax.swing.JLabel();
        jLabel93 = new javax.swing.JLabel();
        jLabel94 = new javax.swing.JLabel();
        jLabel95 = new javax.swing.JLabel();
        jLabel96 = new javax.swing.JLabel();
        jLabel97 = new javax.swing.JLabel();
        panelCadIA18 = new javax.swing.JPanel();
        jLabel101 = new javax.swing.JLabel();
        lblEficManufVIB = new javax.swing.JLabel();
        jLabel102 = new javax.swing.JLabel();
        lblhorasEmbVIB = new javax.swing.JLabel();
        lblHorasPagVIB = new javax.swing.JLabel();
        jLabel103 = new javax.swing.JLabel();
        panelCadIA17 = new javax.swing.JPanel();
        jLabel98 = new javax.swing.JLabel();
        lblEficManufVIA = new javax.swing.JLabel();
        jLabel99 = new javax.swing.JLabel();
        lblhorasEmbVIA = new javax.swing.JLabel();
        lblHorasPagVIA = new javax.swing.JLabel();
        jLabel100 = new javax.swing.JLabel();
        panelCadIA19 = new javax.swing.JPanel();
        jLabel105 = new javax.swing.JLabel();
        lblhorasEmbPlanta1 = new javax.swing.JLabel();
        lblHorasPagPlanta1 = new javax.swing.JLabel();
        lblEficManufPlanta1 = new javax.swing.JLabel();
        jLabel110 = new javax.swing.JLabel();
        jLabel111 = new javax.swing.JLabel();
        jLabel112 = new javax.swing.JLabel();
        panelCadIA24 = new javax.swing.JPanel();
        jLabel123 = new javax.swing.JLabel();
        lblhorasEmbPlanta5 = new javax.swing.JLabel();
        lblHorasPagPlanta5 = new javax.swing.JLabel();
        lblEficManufPlanta5 = new javax.swing.JLabel();
        jLabel124 = new javax.swing.JLabel();
        jLabel125 = new javax.swing.JLabel();
        jLabel126 = new javax.swing.JLabel();
        panelCadIA20 = new javax.swing.JPanel();
        jLabel104 = new javax.swing.JLabel();
        lblEficManufIVC = new javax.swing.JLabel();
        jLabel106 = new javax.swing.JLabel();
        lblhorasEmbIVC = new javax.swing.JLabel();
        lblHorasPagIVC = new javax.swing.JLabel();
        jLabel107 = new javax.swing.JLabel();
        panelCadIA21 = new javax.swing.JPanel();
        jLabel108 = new javax.swing.JLabel();
        lblhorasEmbPlanta2 = new javax.swing.JLabel();
        lblHorasPagPlanta2 = new javax.swing.JLabel();
        lblEficManufPlanta2 = new javax.swing.JLabel();
        jLabel113 = new javax.swing.JLabel();
        jLabel114 = new javax.swing.JLabel();
        jLabel115 = new javax.swing.JLabel();
        panelCadIA25 = new javax.swing.JPanel();
        jLabel127 = new javax.swing.JLabel();
        lblhorasEmbPlanta6 = new javax.swing.JLabel();
        lblHorasPagPlanta6 = new javax.swing.JLabel();
        lblEficManufPlanta6 = new javax.swing.JLabel();
        jLabel128 = new javax.swing.JLabel();
        jLabel129 = new javax.swing.JLabel();
        jLabel130 = new javax.swing.JLabel();
        btnExportar = new javax.swing.JButton();
        btnExportar1 = new javax.swing.JButton();
        panelCadIA22 = new javax.swing.JPanel();
        jLabel109 = new javax.swing.JLabel();
        lblEficManufIIIB = new javax.swing.JLabel();
        jLabel116 = new javax.swing.JLabel();
        lblhorasEmbIIIB = new javax.swing.JLabel();
        lblHorasPagIIIB = new javax.swing.JLabel();
        jLabel117 = new javax.swing.JLabel();
        panelCadIA23 = new javax.swing.JPanel();
        jLabel118 = new javax.swing.JLabel();
        lblEficManufIIIA = new javax.swing.JLabel();
        jLabel119 = new javax.swing.JLabel();
        lblhorasEmbIIIA = new javax.swing.JLabel();
        lblHorasPagIIIA = new javax.swing.JLabel();
        jLabel120 = new javax.swing.JLabel();
        panelCadIA14 = new javax.swing.JPanel();
        jLabel80 = new javax.swing.JLabel();
        lblEficManufIVA = new javax.swing.JLabel();
        jLabel81 = new javax.swing.JLabel();
        lblhorasEmbIVA = new javax.swing.JLabel();
        lblHorasPagIVA = new javax.swing.JLabel();
        jLabel82 = new javax.swing.JLabel();
        txt_captura = new javax.swing.JTextField();
        panelCadIA26 = new javax.swing.JPanel();
        jLabel121 = new javax.swing.JLabel();
        lblEficManufVIB1 = new javax.swing.JLabel();
        jLabel122 = new javax.swing.JLabel();
        lblhorasEmbVIB1 = new javax.swing.JLabel();
        lblHorasPagVIB1 = new javax.swing.JLabel();
        jLabel131 = new javax.swing.JLabel();
        panelCadIA27 = new javax.swing.JPanel();
        jLabel132 = new javax.swing.JLabel();
        lblEficManufVIA1 = new javax.swing.JLabel();
        jLabel133 = new javax.swing.JLabel();
        lblhorasEmbVIA1 = new javax.swing.JLabel();
        lblHorasPagVIA1 = new javax.swing.JLabel();
        jLabel134 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 255, 153), 4, true));
        jPanel2.setForeground(new java.awt.Color(102, 255, 102));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelCadIA4.setBackground(new java.awt.Color(51, 153, 255));
        panelCadIA4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "CADENA IA", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Agency FB", 1, 18))); // NOI18N
        panelCadIA4.setPreferredSize(new java.awt.Dimension(298, 109));
        panelCadIA4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelCadIA4MouseClicked(evt);
            }
        });
        panelCadIA4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                panelCadIA4KeyTyped(evt);
            }
        });
        panelCadIA4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel50.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel50.setText("EFIC. MANUF.:");
        panelCadIA4.add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, 20));

        lblEficManufIA.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblEficManufIA.setText("0.0");
        panelCadIA4.add(lblEficManufIA, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 70, 80, 20));

        jLabel51.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel51.setText("HRS.EMB.:");
        panelCadIA4.add(jLabel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1, 20));

        lblhorasEmbIA.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblhorasEmbIA.setText("0.0");
        lblhorasEmbIA.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblhorasEmbIAMouseClicked(evt);
            }
        });
        panelCadIA4.add(lblhorasEmbIA, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 30, 80, 20));

        lblHorasPagIA.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblHorasPagIA.setText("0.0");
        panelCadIA4.add(lblHorasPagIA, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 50, 80, 20));

        jLabel52.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel52.setText("HRS.PAG.:");
        panelCadIA4.add(jLabel52, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, -1, 20));

        jPanel2.add(panelCadIA4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 220, 119));

        panelCadIA6.setBackground(new java.awt.Color(51, 153, 255));
        panelCadIA6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "CADENA IB", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Agency FB", 1, 18))); // NOI18N
        panelCadIA6.setMinimumSize(new java.awt.Dimension(186, 90));
        panelCadIA6.setPreferredSize(new java.awt.Dimension(298, 109));
        panelCadIA6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelCadIA6MouseClicked(evt);
            }
        });
        panelCadIA6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel56.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel56.setText("EFIC. MANUF.:");
        panelCadIA6.add(jLabel56, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, 20));

        lblEficManufIB.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblEficManufIB.setText("0.0");
        panelCadIA6.add(lblEficManufIB, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 70, 80, 20));

        jLabel57.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel57.setText("HRS.EMB.:");
        panelCadIA6.add(jLabel57, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1, 20));

        lblhorasEmbIB.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblhorasEmbIB.setText("0.0");
        panelCadIA6.add(lblhorasEmbIB, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 30, 80, 20));

        lblHorasPagIB.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblHorasPagIB.setText("0.0");
        panelCadIA6.add(lblHorasPagIB, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 50, 80, 20));

        jLabel58.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel58.setText("HRS.PAG.:");
        panelCadIA6.add(jLabel58, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, -1, 20));

        jPanel2.add(panelCadIA6, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 10, 221, 119));

        panelCadIA5.setBackground(new java.awt.Color(0, 113, 7));
        panelCadIA5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "CADENA IIA", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Agency FB", 1, 18), new java.awt.Color(255, 255, 255))); // NOI18N
        panelCadIA5.setPreferredSize(new java.awt.Dimension(298, 109));
        panelCadIA5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelCadIA5MouseClicked(evt);
            }
        });
        panelCadIA5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel53.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel53.setText("EFIC. MANUF.:");
        panelCadIA5.add(jLabel53, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, 20));

        lblEficManufIIA.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblEficManufIIA.setForeground(new java.awt.Color(255, 255, 255));
        lblEficManufIIA.setText("0.0");
        panelCadIA5.add(lblEficManufIIA, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 70, 80, 20));

        jLabel54.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel54.setText("HRS.EMB.:");
        panelCadIA5.add(jLabel54, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1, 20));

        lblhorasEmbIIA.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblhorasEmbIIA.setForeground(new java.awt.Color(255, 255, 255));
        lblhorasEmbIIA.setText("0.0");
        panelCadIA5.add(lblhorasEmbIIA, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 30, 70, 20));

        lblHorasPagIIA.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblHorasPagIIA.setForeground(new java.awt.Color(255, 255, 255));
        lblHorasPagIIA.setText("0.0");
        panelCadIA5.add(lblHorasPagIIA, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 50, 80, 20));

        jLabel55.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel55.setText("HRS.PAG.:");
        panelCadIA5.add(jLabel55, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, -1, 20));

        jPanel2.add(panelCadIA5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 219, 119));

        panelCadIA7.setBackground(new java.awt.Color(0, 113, 7));
        panelCadIA7.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "CADENA IIB", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Agency FB", 1, 18), java.awt.Color.white)); // NOI18N
        panelCadIA7.setPreferredSize(new java.awt.Dimension(298, 109));
        panelCadIA7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelCadIA7MouseClicked(evt);
            }
        });
        panelCadIA7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel59.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel59.setText("EFIC. MANUF.:");
        panelCadIA7.add(jLabel59, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, 20));

        lblEficManufIIB.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblEficManufIIB.setForeground(new java.awt.Color(255, 255, 255));
        lblEficManufIIB.setText("0.0");
        panelCadIA7.add(lblEficManufIIB, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 70, 80, 20));

        jLabel60.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel60.setText("HRS.EMB.:");
        panelCadIA7.add(jLabel60, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1, 20));

        lblhorasEmbIIB.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblhorasEmbIIB.setForeground(new java.awt.Color(255, 255, 255));
        lblhorasEmbIIB.setText("0.0");
        panelCadIA7.add(lblhorasEmbIIB, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 30, 80, 20));

        lblHorasPagIIB.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblHorasPagIIB.setForeground(new java.awt.Color(255, 255, 255));
        lblHorasPagIIB.setText("0.0");
        panelCadIA7.add(lblHorasPagIIB, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 50, 80, 20));

        jLabel61.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel61.setText("HRS.PAG.:");
        panelCadIA7.add(jLabel61, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, -1, 20));

        jPanel2.add(panelCadIA7, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 140, 220, 119));

        panelCadIA15.setBackground(new java.awt.Color(204, 204, 255));
        panelCadIA15.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "CADENA IVB", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Agency FB", 1, 18))); // NOI18N
        panelCadIA15.setPreferredSize(new java.awt.Dimension(298, 109));
        panelCadIA15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelCadIA15MouseClicked(evt);
            }
        });
        panelCadIA15.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel83.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel83.setText("EFIC. MANUF.:");
        panelCadIA15.add(jLabel83, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, 20));

        lblEficManufIVB.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblEficManufIVB.setForeground(new java.awt.Color(255, 51, 51));
        lblEficManufIVB.setText("0.0");
        panelCadIA15.add(lblEficManufIVB, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 70, 90, 20));

        jLabel84.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel84.setText("HRS.EMB.:");
        panelCadIA15.add(jLabel84, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1, 20));

        lblhorasEmbIVB.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblhorasEmbIVB.setForeground(new java.awt.Color(255, 51, 51));
        lblhorasEmbIVB.setText("0.0");
        panelCadIA15.add(lblhorasEmbIVB, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 30, 90, 20));

        lblHorasPagIVB.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblHorasPagIVB.setForeground(new java.awt.Color(255, 51, 51));
        lblHorasPagIVB.setText("0.0");
        panelCadIA15.add(lblHorasPagIVB, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 50, 80, 20));

        jLabel85.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel85.setText("HRS.PAG.:");
        panelCadIA15.add(jLabel85, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, -1, 20));

        jPanel2.add(panelCadIA15, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 10, 219, 119));

        panelCadIA16.setBackground(new java.awt.Color(255, 255, 102));
        panelCadIA16.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "EFICIENCIA PLANTA", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Agency FB", 1, 24))); // NOI18N
        panelCadIA16.setPreferredSize(new java.awt.Dimension(298, 109));
        panelCadIA16.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel86.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel86.setText("EFIC. MANUF.:");
        panelCadIA16.add(jLabel86, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, 20));

        lblEficManufPlantaA.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblEficManufPlantaA.setForeground(new java.awt.Color(255, 51, 51));
        lblEficManufPlantaA.setText("0.0");
        panelCadIA16.add(lblEficManufPlantaA, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 90, 80, 20));

        jLabel87.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel87.setText("TOTAL");
        panelCadIA16.add(jLabel87, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 10, -1, 50));

        lblhorasEmbPlantaA.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblhorasEmbPlantaA.setForeground(new java.awt.Color(255, 51, 51));
        lblhorasEmbPlantaA.setText("0.0");
        panelCadIA16.add(lblhorasEmbPlantaA, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 50, 80, 20));

        lblHorasPagPlantaA.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblHorasPagPlantaA.setForeground(new java.awt.Color(255, 51, 51));
        lblHorasPagPlantaA.setText("0.0");
        panelCadIA16.add(lblHorasPagPlantaA, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 70, 80, 20));

        jLabel88.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel88.setText("HRS.PAG.:");
        panelCadIA16.add(jLabel88, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, -1, 20));

        lblhorasEmbPlantaB.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblhorasEmbPlantaB.setForeground(new java.awt.Color(255, 51, 51));
        lblhorasEmbPlantaB.setText("0.0");
        panelCadIA16.add(lblhorasEmbPlantaB, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 40, 90, 20));

        lblHorasPagPlantaB.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblHorasPagPlantaB.setForeground(new java.awt.Color(255, 51, 51));
        lblHorasPagPlantaB.setText("0.0");
        panelCadIA16.add(lblHorasPagPlantaB, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 60, 90, 20));

        lblEficManufPlantaB.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblEficManufPlantaB.setForeground(new java.awt.Color(255, 51, 51));
        lblEficManufPlantaB.setText("0.0");
        panelCadIA16.add(lblEficManufPlantaB, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 80, 90, 20));

        jLabel89.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel89.setText("HRS.EMB.:");
        panelCadIA16.add(jLabel89, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 40, -1, 20));

        jLabel90.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel90.setText("HRS.PAG.:");
        panelCadIA16.add(jLabel90, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 60, -1, 20));

        jLabel91.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel91.setText("EFIC. MANUF.:");
        panelCadIA16.add(jLabel91, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 80, -1, 20));

        lblhorasEmbPlanta.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblhorasEmbPlanta.setForeground(new java.awt.Color(255, 51, 51));
        lblhorasEmbPlanta.setText("0.0");
        panelCadIA16.add(lblhorasEmbPlanta, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 40, 100, 20));

        lblHorasPagPlanta.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblHorasPagPlanta.setForeground(new java.awt.Color(255, 51, 51));
        lblHorasPagPlanta.setText("0.0");
        panelCadIA16.add(lblHorasPagPlanta, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 60, 100, 20));

        lblEficManufPlanta.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblEficManufPlanta.setForeground(new java.awt.Color(255, 51, 51));
        lblEficManufPlanta.setText("0.0");
        panelCadIA16.add(lblEficManufPlanta, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 80, 100, 20));

        jLabel92.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel92.setText("HRS.EMB.:");
        panelCadIA16.add(jLabel92, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 40, -1, 20));

        jLabel93.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel93.setText("HRS.PAG.:");
        panelCadIA16.add(jLabel93, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 60, -1, 20));

        jLabel94.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel94.setText("EFIC. MANUF.:");
        panelCadIA16.add(jLabel94, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 80, -1, 20));

        jLabel95.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel95.setText("HRS.EMB.:");
        panelCadIA16.add(jLabel95, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, -1, 20));

        jLabel96.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel96.setText("A");
        panelCadIA16.add(jLabel96, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 10, -1, 50));

        jLabel97.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel97.setText("B");
        panelCadIA16.add(jLabel97, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 10, -1, 50));

        jPanel2.add(panelCadIA16, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 140, 680, 120));
        panelCadIA16.getAccessibleContext().setAccessibleName("EFICIENCIA PLANTA ");

        panelCadIA18.setBackground(new java.awt.Color(255, 51, 255));
        panelCadIA18.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "CADENA VIB", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Agency FB", 1, 18))); // NOI18N
        panelCadIA18.setPreferredSize(new java.awt.Dimension(298, 109));
        panelCadIA18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelCadIA18MouseClicked(evt);
            }
        });
        panelCadIA18.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel101.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel101.setText("EFIC. MANUF.:");
        panelCadIA18.add(jLabel101, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, 20));

        lblEficManufVIB.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblEficManufVIB.setForeground(new java.awt.Color(255, 255, 255));
        lblEficManufVIB.setText("0.0");
        panelCadIA18.add(lblEficManufVIB, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 70, 90, 20));

        jLabel102.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel102.setText("HRS.EMB.:");
        panelCadIA18.add(jLabel102, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1, 20));

        lblhorasEmbVIB.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblhorasEmbVIB.setForeground(new java.awt.Color(255, 255, 255));
        lblhorasEmbVIB.setText("0.0");
        panelCadIA18.add(lblhorasEmbVIB, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 30, 90, 20));

        lblHorasPagVIB.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblHorasPagVIB.setForeground(new java.awt.Color(255, 255, 255));
        lblHorasPagVIB.setText("0.0");
        panelCadIA18.add(lblHorasPagVIB, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 50, 80, 20));

        jLabel103.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel103.setText("HRS.PAG.:");
        panelCadIA18.add(jLabel103, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, -1, 20));

        jPanel2.add(panelCadIA18, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 550, 220, 119));

        panelCadIA17.setBackground(new java.awt.Color(255, 51, 255));
        panelCadIA17.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "CADENA VIA", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Agency FB", 1, 18))); // NOI18N
        panelCadIA17.setPreferredSize(new java.awt.Dimension(298, 109));
        panelCadIA17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelCadIA17MouseClicked(evt);
            }
        });
        panelCadIA17.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel98.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel98.setText("EFIC. MANUF.:");
        panelCadIA17.add(jLabel98, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, 20));

        lblEficManufVIA.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblEficManufVIA.setForeground(new java.awt.Color(255, 255, 255));
        lblEficManufVIA.setText("0.0");
        panelCadIA17.add(lblEficManufVIA, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 70, 90, 20));

        jLabel99.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel99.setText("HRS.EMB.:");
        panelCadIA17.add(jLabel99, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1, 20));

        lblhorasEmbVIA.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblhorasEmbVIA.setForeground(new java.awt.Color(255, 255, 255));
        lblhorasEmbVIA.setText("0.0");
        panelCadIA17.add(lblhorasEmbVIA, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 30, 80, 20));

        lblHorasPagVIA.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblHorasPagVIA.setForeground(new java.awt.Color(255, 255, 255));
        lblHorasPagVIA.setText("0.0");
        panelCadIA17.add(lblHorasPagVIA, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 50, 80, 20));

        jLabel100.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel100.setText("HRS.PAG.:");
        panelCadIA17.add(jLabel100, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, -1, 20));

        jPanel2.add(panelCadIA17, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 550, 220, 119));

        panelCadIA19.setBackground(new java.awt.Color(255, 255, 102));
        panelCadIA19.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "EFICIENCIA PLANTA", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Agency FB", 1, 24))); // NOI18N
        panelCadIA19.setPreferredSize(new java.awt.Dimension(298, 109));
        panelCadIA19.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel105.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel105.setText("TOTAL MOCHIS");
        panelCadIA19.add(jLabel105, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, 50));

        lblhorasEmbPlanta1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblhorasEmbPlanta1.setForeground(new java.awt.Color(255, 51, 51));
        lblhorasEmbPlanta1.setText("0.0");
        panelCadIA19.add(lblhorasEmbPlanta1, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 20, 100, 20));

        lblHorasPagPlanta1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblHorasPagPlanta1.setForeground(new java.awt.Color(255, 51, 51));
        lblHorasPagPlanta1.setText("0.0");
        panelCadIA19.add(lblHorasPagPlanta1, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 40, 100, 20));

        lblEficManufPlanta1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblEficManufPlanta1.setForeground(new java.awt.Color(255, 51, 51));
        lblEficManufPlanta1.setText("0.0");
        panelCadIA19.add(lblEficManufPlanta1, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 60, 100, 20));

        jLabel110.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel110.setText("HRS.EMB.:");
        panelCadIA19.add(jLabel110, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 20, -1, 20));

        jLabel111.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel111.setText("HRS.PAG.:");
        panelCadIA19.add(jLabel111, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 40, -1, 20));

        jLabel112.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel112.setText("EFIC. MANUF.:");
        panelCadIA19.add(jLabel112, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 60, -1, 20));

        panelCadIA24.setBackground(new java.awt.Color(255, 255, 102));
        panelCadIA24.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "EFICIENCIA PLANTA", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Agency FB", 1, 24))); // NOI18N
        panelCadIA24.setPreferredSize(new java.awt.Dimension(298, 109));
        panelCadIA24.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel123.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel123.setText("TOTAL MOCHIS");
        panelCadIA24.add(jLabel123, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 20, -1, 50));

        lblhorasEmbPlanta5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblhorasEmbPlanta5.setForeground(new java.awt.Color(255, 51, 51));
        lblhorasEmbPlanta5.setText("0.0");
        panelCadIA24.add(lblhorasEmbPlanta5, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 80, 100, 20));

        lblHorasPagPlanta5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblHorasPagPlanta5.setForeground(new java.awt.Color(255, 51, 51));
        lblHorasPagPlanta5.setText("0.0");
        panelCadIA24.add(lblHorasPagPlanta5, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 100, 100, 20));

        lblEficManufPlanta5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblEficManufPlanta5.setForeground(new java.awt.Color(255, 51, 51));
        lblEficManufPlanta5.setText("0.0");
        panelCadIA24.add(lblEficManufPlanta5, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 120, 100, 20));

        jLabel124.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel124.setText("HRS.EMB.:");
        panelCadIA24.add(jLabel124, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 80, -1, 20));

        jLabel125.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel125.setText("HRS.PAG.:");
        panelCadIA24.add(jLabel125, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 100, -1, 20));

        jLabel126.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel126.setText("EFIC. MANUF.:");
        panelCadIA24.add(jLabel126, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 120, -1, 20));

        panelCadIA19.add(panelCadIA24, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 470, 810, 164));

        jPanel2.add(panelCadIA19, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 260, 680, 90));
        panelCadIA19.getAccessibleContext().setAccessibleName("EFICIENCIA  PLANTADFGDFHGFD");

        panelCadIA20.setBackground(new java.awt.Color(204, 204, 255));
        panelCadIA20.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "CADENA IVC", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Agency FB", 1, 18))); // NOI18N
        panelCadIA20.setPreferredSize(new java.awt.Dimension(298, 109));
        panelCadIA20.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelCadIA20MouseClicked(evt);
            }
        });
        panelCadIA20.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel104.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel104.setText("EFIC. MANUF.:");
        panelCadIA20.add(jLabel104, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, 20));

        lblEficManufIVC.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblEficManufIVC.setForeground(new java.awt.Color(255, 51, 51));
        lblEficManufIVC.setText("0.0");
        panelCadIA20.add(lblEficManufIVC, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 70, 90, 20));

        jLabel106.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel106.setText("HRS.EMB.:");
        panelCadIA20.add(jLabel106, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1, 20));

        lblhorasEmbIVC.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblhorasEmbIVC.setForeground(new java.awt.Color(255, 51, 51));
        lblhorasEmbIVC.setText("0.0");
        panelCadIA20.add(lblhorasEmbIVC, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 30, 90, 20));

        lblHorasPagIVC.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblHorasPagIVC.setForeground(new java.awt.Color(255, 51, 51));
        lblHorasPagIVC.setText("0.0");
        panelCadIA20.add(lblHorasPagIVC, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 50, 80, 20));

        jLabel107.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel107.setText("HRS.PAG.:");
        panelCadIA20.add(jLabel107, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, -1, 20));

        jPanel2.add(panelCadIA20, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 10, 219, 119));

        panelCadIA21.setBackground(new java.awt.Color(255, 255, 102));
        panelCadIA21.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "EFICIENCIA PLANTA", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Agency FB", 1, 24))); // NOI18N
        panelCadIA21.setPreferredSize(new java.awt.Dimension(298, 109));
        panelCadIA21.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel108.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel108.setText("TOTAL GUAMUCHIL");
        panelCadIA21.add(jLabel108, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 50));

        lblhorasEmbPlanta2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblhorasEmbPlanta2.setForeground(new java.awt.Color(255, 51, 51));
        lblhorasEmbPlanta2.setText("0.0");
        panelCadIA21.add(lblhorasEmbPlanta2, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 20, 100, 20));

        lblHorasPagPlanta2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblHorasPagPlanta2.setForeground(new java.awt.Color(255, 51, 51));
        lblHorasPagPlanta2.setText("0.0");
        panelCadIA21.add(lblHorasPagPlanta2, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 40, 100, 20));

        lblEficManufPlanta2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblEficManufPlanta2.setForeground(new java.awt.Color(255, 51, 51));
        lblEficManufPlanta2.setText("0.0");
        panelCadIA21.add(lblEficManufPlanta2, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 60, 100, 20));

        jLabel113.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel113.setText("HRS.EMB.:");
        panelCadIA21.add(jLabel113, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 20, -1, 20));

        jLabel114.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel114.setText("HRS.PAG.:");
        panelCadIA21.add(jLabel114, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 40, -1, 20));

        jLabel115.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel115.setText("EFIC. MANUF.:");
        panelCadIA21.add(jLabel115, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 60, -1, 20));

        panelCadIA25.setBackground(new java.awt.Color(255, 255, 102));
        panelCadIA25.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "EFICIENCIA PLANTA", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Agency FB", 1, 24))); // NOI18N
        panelCadIA25.setPreferredSize(new java.awt.Dimension(298, 109));
        panelCadIA25.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel127.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel127.setText("TOTAL MOCHIS");
        panelCadIA25.add(jLabel127, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 20, -1, 50));

        lblhorasEmbPlanta6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblhorasEmbPlanta6.setForeground(new java.awt.Color(255, 51, 51));
        lblhorasEmbPlanta6.setText("0.0");
        panelCadIA25.add(lblhorasEmbPlanta6, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 80, 100, 20));

        lblHorasPagPlanta6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblHorasPagPlanta6.setForeground(new java.awt.Color(255, 51, 51));
        lblHorasPagPlanta6.setText("0.0");
        panelCadIA25.add(lblHorasPagPlanta6, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 100, 100, 20));

        lblEficManufPlanta6.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblEficManufPlanta6.setForeground(new java.awt.Color(255, 51, 51));
        lblEficManufPlanta6.setText("0.0");
        panelCadIA25.add(lblEficManufPlanta6, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 120, 100, 20));

        jLabel128.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel128.setText("HRS.EMB.:");
        panelCadIA25.add(jLabel128, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 80, -1, 20));

        jLabel129.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel129.setText("HRS.PAG.:");
        panelCadIA25.add(jLabel129, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 100, -1, 20));

        jLabel130.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel130.setText("EFIC. MANUF.:");
        panelCadIA25.add(jLabel130, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 120, -1, 20));

        panelCadIA21.add(panelCadIA25, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 470, 810, 164));

        jPanel2.add(panelCadIA21, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 350, 680, 90));

        btnExportar.setBackground(new java.awt.Color(204, 204, 204));
        btnExportar.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnExportar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/tests48.png"))); // NOI18N
        btnExportar.setText("AGREGAR NOTA");
        btnExportar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnExportar.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnExportar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnExportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportarActionPerformed(evt);
            }
        });
        jPanel2.add(btnExportar, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 460, 140, 100));

        btnExportar1.setBackground(new java.awt.Color(204, 204, 204));
        btnExportar1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnExportar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/filetype-xls-icon (2).png"))); // NOI18N
        btnExportar1.setText("EXPORTAR");
        btnExportar1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnExportar1.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnExportar1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnExportar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportar1ActionPerformed(evt);
            }
        });
        jPanel2.add(btnExportar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 460, 120, 100));

        panelCadIA22.setBackground(new java.awt.Color(255, 153, 102));
        panelCadIA22.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "CADENA IIIB", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Agency FB", 1, 18))); // NOI18N
        panelCadIA22.setPreferredSize(new java.awt.Dimension(298, 109));
        panelCadIA22.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelCadIA22MouseClicked(evt);
            }
        });
        panelCadIA22.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel109.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel109.setText("EFIC. MANUF.:");
        panelCadIA22.add(jLabel109, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, 20));

        lblEficManufIIIB.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblEficManufIIIB.setForeground(new java.awt.Color(255, 255, 255));
        lblEficManufIIIB.setText("0.0");
        panelCadIA22.add(lblEficManufIIIB, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 70, 90, 20));

        jLabel116.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel116.setText("HRS.EMB.:");
        panelCadIA22.add(jLabel116, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1, 20));

        lblhorasEmbIIIB.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblhorasEmbIIIB.setForeground(new java.awt.Color(255, 255, 255));
        lblhorasEmbIIIB.setText("0.0");
        panelCadIA22.add(lblhorasEmbIIIB, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 30, 90, 20));

        lblHorasPagIIIB.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblHorasPagIIIB.setForeground(new java.awt.Color(255, 255, 255));
        lblHorasPagIIIB.setText("0.0");
        panelCadIA22.add(lblHorasPagIIIB, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 50, 80, 20));

        jLabel117.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel117.setText("HRS.PAG.:");
        panelCadIA22.add(jLabel117, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, -1, 20));

        jPanel2.add(panelCadIA22, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 270, 220, 119));

        panelCadIA23.setBackground(new java.awt.Color(255, 153, 102));
        panelCadIA23.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "CADENA IIIA", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Agency FB", 1, 18))); // NOI18N
        panelCadIA23.setPreferredSize(new java.awt.Dimension(298, 109));
        panelCadIA23.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelCadIA23MouseClicked(evt);
            }
        });
        panelCadIA23.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel118.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel118.setText("EFIC. MANUF.:");
        panelCadIA23.add(jLabel118, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, 20));

        lblEficManufIIIA.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblEficManufIIIA.setForeground(new java.awt.Color(255, 255, 255));
        lblEficManufIIIA.setText("0.0");
        panelCadIA23.add(lblEficManufIIIA, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 70, 90, 20));

        jLabel119.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel119.setText("HRS.EMB.:");
        panelCadIA23.add(jLabel119, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1, 20));

        lblhorasEmbIIIA.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblhorasEmbIIIA.setForeground(new java.awt.Color(255, 255, 255));
        lblhorasEmbIIIA.setText("0.0");
        panelCadIA23.add(lblhorasEmbIIIA, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 30, 80, 20));

        lblHorasPagIIIA.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblHorasPagIIIA.setForeground(new java.awt.Color(255, 255, 255));
        lblHorasPagIIIA.setText("0.0");
        panelCadIA23.add(lblHorasPagIIIA, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 50, 80, 20));

        jLabel120.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel120.setText("HRS.PAG.:");
        panelCadIA23.add(jLabel120, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, -1, 20));

        jPanel2.add(panelCadIA23, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, 220, 119));

        panelCadIA14.setBackground(new java.awt.Color(204, 204, 255));
        panelCadIA14.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "CADENA IVA", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Agency FB", 1, 18))); // NOI18N
        panelCadIA14.setPreferredSize(new java.awt.Dimension(298, 109));
        panelCadIA14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelCadIA14MouseClicked(evt);
            }
        });
        panelCadIA14.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel80.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel80.setText("EFIC. MANUF.:");
        panelCadIA14.add(jLabel80, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, 20));

        lblEficManufIVA.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblEficManufIVA.setForeground(new java.awt.Color(255, 51, 51));
        lblEficManufIVA.setText("0.0");
        panelCadIA14.add(lblEficManufIVA, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 70, 90, 20));

        jLabel81.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel81.setText("HRS.EMB.:");
        panelCadIA14.add(jLabel81, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1, 20));

        lblhorasEmbIVA.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblhorasEmbIVA.setForeground(new java.awt.Color(255, 51, 51));
        lblhorasEmbIVA.setText("0.0");
        panelCadIA14.add(lblhorasEmbIVA, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 30, 80, 20));

        lblHorasPagIVA.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblHorasPagIVA.setForeground(new java.awt.Color(255, 51, 51));
        lblHorasPagIVA.setText("0.0");
        panelCadIA14.add(lblHorasPagIVA, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 50, 80, 20));

        jLabel82.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel82.setText("HRS.PAG.:");
        panelCadIA14.add(jLabel82, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, -1, 20));

        jPanel2.add(panelCadIA14, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 10, 219, 119));

        txt_captura.setEditable(false);
        txt_captura.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txt_captura.setForeground(new java.awt.Color(51, 51, 255));
        jPanel2.add(txt_captura, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 700, 180, 30));

        panelCadIA26.setBackground(new java.awt.Color(0, 153, 153));
        panelCadIA26.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "CADENA VB", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Agency FB", 1, 18))); // NOI18N
        panelCadIA26.setPreferredSize(new java.awt.Dimension(298, 109));
        panelCadIA26.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelCadIA26MouseClicked(evt);
            }
        });
        panelCadIA26.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel121.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel121.setText("EFIC. MANUF.:");
        panelCadIA26.add(jLabel121, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, 20));

        lblEficManufVIB1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblEficManufVIB1.setForeground(new java.awt.Color(255, 255, 255));
        lblEficManufVIB1.setText("0.0");
        panelCadIA26.add(lblEficManufVIB1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 70, 90, 20));

        jLabel122.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel122.setText("HRS.EMB.:");
        panelCadIA26.add(jLabel122, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1, 20));

        lblhorasEmbVIB1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblhorasEmbVIB1.setForeground(new java.awt.Color(255, 255, 255));
        lblhorasEmbVIB1.setText("0.0");
        panelCadIA26.add(lblhorasEmbVIB1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 30, 90, 20));

        lblHorasPagVIB1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblHorasPagVIB1.setForeground(new java.awt.Color(255, 255, 255));
        lblHorasPagVIB1.setText("0.0");
        panelCadIA26.add(lblHorasPagVIB1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 50, 80, 20));

        jLabel131.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel131.setText("HRS.PAG.:");
        panelCadIA26.add(jLabel131, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, -1, 20));

        jPanel2.add(panelCadIA26, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 410, 220, 119));

        panelCadIA27.setBackground(new java.awt.Color(0, 153, 153));
        panelCadIA27.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "CADENA VA", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Agency FB", 1, 18))); // NOI18N
        panelCadIA27.setPreferredSize(new java.awt.Dimension(298, 109));
        panelCadIA27.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                panelCadIA27MouseClicked(evt);
            }
        });
        panelCadIA27.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel132.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel132.setText("EFIC. MANUF.:");
        panelCadIA27.add(jLabel132, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, 20));

        lblEficManufVIA1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblEficManufVIA1.setForeground(new java.awt.Color(255, 255, 255));
        lblEficManufVIA1.setText("0.0");
        panelCadIA27.add(lblEficManufVIA1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 70, 90, 20));

        jLabel133.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel133.setText("HRS.EMB.:");
        panelCadIA27.add(jLabel133, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1, 20));

        lblhorasEmbVIA1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblhorasEmbVIA1.setForeground(new java.awt.Color(255, 255, 255));
        lblhorasEmbVIA1.setText("0.0");
        panelCadIA27.add(lblhorasEmbVIA1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 30, 80, 20));

        lblHorasPagVIA1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblHorasPagVIA1.setForeground(new java.awt.Color(255, 255, 255));
        lblHorasPagVIA1.setText("0.0");
        panelCadIA27.add(lblHorasPagVIA1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 50, 80, 20));

        jLabel134.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel134.setText("HRS.PAG.:");
        panelCadIA27.add(jLabel134, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, -1, 20));

        jPanel2.add(panelCadIA27, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 410, 220, 119));

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Edit-Document-icon (1).png"))); // NOI18N
        jButton1.setText("CAPTURA GSD");
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 460, -1, 100));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 1228, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 798, Short.MAX_VALUE)
                .addGap(22, 22, 22))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblhorasEmbIAMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblhorasEmbIAMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_lblhorasEmbIAMouseClicked

    private void panelCadIA4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelCadIA4MouseClicked
        // TODO add your handling code here:
        this.hide();
        CapturaExcelManufactura manuf=new CapturaExcelManufactura("1", "A");
        manuf.setVisible(true);
        manuf.setLocationRelativeTo(null);
        
    }//GEN-LAST:event_panelCadIA4MouseClicked

    private void panelCadIA6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelCadIA6MouseClicked
        // TODO add your handling code here:
        this.hide();
        CapturaExcelManufactura manuf=new CapturaExcelManufactura("1", "B");
        manuf.setVisible(true);
        manuf.setLocationRelativeTo(null);
    }//GEN-LAST:event_panelCadIA6MouseClicked

    private void panelCadIA5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelCadIA5MouseClicked
        // TODO add your handling code here:     
        this.hide();
        CapturaExcelManufactura manuf=new CapturaExcelManufactura("2", "A");
        manuf.setVisible(true);
        manuf.setLocationRelativeTo(null);
        
    }//GEN-LAST:event_panelCadIA5MouseClicked

    private void panelCadIA7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelCadIA7MouseClicked
        // TODO add your handling code here:
        this.hide();
        CapturaExcelManufactura manuf=new CapturaExcelManufactura("2", "B");
        manuf.setVisible(true);
        manuf.setLocationRelativeTo(null);
    }//GEN-LAST:event_panelCadIA7MouseClicked

    private void btnExportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportarActionPerformed
        AgregarNota G=new  AgregarNota();
           G.setVisible(true);
           G.setLocationRelativeTo(null);
    }//GEN-LAST:event_btnExportarActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
        Principal   p=new Principal(Principal.UsuarioLogeado);
        p.setLocationRelativeTo(null);
        p.setVisible(true);
    }//GEN-LAST:event_formWindowClosed

    private void panelCadIA14MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelCadIA14MouseClicked
        // TODO add your handling code here:
        
         this.hide();
        CapturaExcelManufactura manuf=new CapturaExcelManufactura("4", "A");
        manuf.setVisible(true);
        manuf.setLocationRelativeTo(null);
//        CapturaGenteCorte CapturaGente=new CapturaGenteCorte("A");
//        CapturaGente.setVisible(true);
//        CapturaGente.setLocationRelativeTo(null);
                
    }//GEN-LAST:event_panelCadIA14MouseClicked

    private void panelCadIA17MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelCadIA17MouseClicked
        // TODO add your handling code here:
//        
//        this.hide();
//        CapturaExcelManufactura manuf=new CapturaExcelManufactura("6", "A");
//        manuf.setVisible(true);
//        manuf.setLocationRelativeTo(null);
    }//GEN-LAST:event_panelCadIA17MouseClicked

    private void panelCadIA18MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelCadIA18MouseClicked
        // TODO add your handling code here:
//        this.hide();
//        CapturaExcelManufactura manuf=new CapturaExcelManufactura("6", "B");
//        manuf.setVisible(true);
//        manuf.setLocationRelativeTo(null);
    }//GEN-LAST:event_panelCadIA18MouseClicked

    private void panelCadIA15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelCadIA15MouseClicked
        // TODO add your handling code here:
         this.hide();
        CapturaExcelManufactura manuf=new CapturaExcelManufactura("4", "B");
        manuf.setVisible(true);
        manuf.setLocationRelativeTo(null);
    }//GEN-LAST:event_panelCadIA15MouseClicked

    private void panelCadIA20MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelCadIA20MouseClicked
        this.hide();
        CapturaExcelManufactura manuf=new CapturaExcelManufactura("4", "C");
        manuf.setVisible(true);
        manuf.setLocationRelativeTo(null);        // TODO add your handling code here:
    }//GEN-LAST:event_panelCadIA20MouseClicked

    private void btnExportar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportar1ActionPerformed
  try {
            // TODO add your handling code here:
            ArrayList<DefaultTableModel> modelos=new ArrayList<DefaultTableModel>();
            ArrayList<String> modelosS=new ArrayList<String>();
            modelosS.add("1A");
            modelosS.add("1B");
          //modelosS.add("SERV-A");
          //modelosS.add("SERV-B");
            modelosS.add("2A");
            modelosS.add("2B");
            modelosS.add("LPS-A");
            modelosS.add("LPS-B");
           // modelosS.add("5A");
            //modelosS.add("5B");
            //modelosS.add("6A");
           // modelosS.add("6B"); 
           
           /// modelosS.add("PLATAFORMAS"); 
            
            //modelosS.add("5A");
            //modelosS.add("5B");
            
            modelos.add(CapturaExcelManufactura.getModeloMFG("1", "A"));
            modelos.add(CapturaExcelManufactura.getModeloMFG("1", "B"));
          //  modelos.add(CapturaExcelManufactura.getModeloServiciosA("29A", "A"));
            //modelos.add(CapturaExcelManufactura.getModeloServiciosB("29A", "B"));
            modelos.add(CapturaExcelManufactura.getModeloMFG("2", "A"));
            modelos.add(CapturaExcelManufactura.getModeloMFG("2", "B"));
            modelos.add(CapturaExcelManufactura.getModeloLPS("3", "A"));
            modelos.add(CapturaExcelManufactura.getModeloLPS("3", "B"));
          //modelos.add(CapturaExcelManufactura.getModelo("4", "A"));
          //modelos.add(CapturaExcelManufactura.getModelo("4", "B"));
           //  modelos.add(CapturaExcelManufactura.getModelo("5", "A"));
          // modelos.add(CapturaExcelManufactura.getModelo("5", "B"));
            //modelos.add(CapturaExcelManufactura.getModeloMFG("6", "A"));
            //modelos.add(CapturaExcelManufactura.getModeloMFG("6", "B"));
         
           // modelos.add(CapturaExcelManufactura.getModeloplataforma());

          // modelos.add(CapturaExcelManufactura.getModelo("5", "A"));
           //modelos.add(CapturaExcelManufactura.getModelo("5", "B"));
            DefaultTableModel corteMochis=CapturaExcelManufactura.GetModeloCorte("4");
           // DefaultTableModel corteGML=CapturaExcelManufactura.GetModeloCortegml("6");
            DefaultTableModel plataformas=CapturaExcelManufactura.getModeloplataforma();
            DefaultTableModel plataformasMSD=CapturaExcelManufactura.getModeloplataformaMSD();
            DefaultTableModel TCODIGOS=CapturaExcelManufactura.getModelotOTALESXcodigo();
            DefaultTableModel plataformastot=CapturaExcelManufactura.getModeloplataformatOTALES();
            DefaultTableModel plataformasConcentradoLM=CapturaExcelManufactura.concentrado_plataformasLM();
            DefaultTableModel plataformasConcentradoGML=CapturaExcelManufactura.concentrado_plataformasGML();
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
            //modelos.add(CapturaExcelManufactura.GetModeloCorte("4", "A"));
//modelos.add(CapturaExcelManufactura.GetModeloCorte("4", "B"));
//modelos.add(CapturaExcelManufactura.GetModeloCorte("4", "C"));
          ResultSet rs= Principal.cn.GetConsulta("select * from notas");
          String nota="";
          if(rs.next())
              nota=rs.getString(1);
            DatosManufactura dt=new DatosManufactura(); 
            System.out.println("entro a todo");
            ExcelEficiencia excel=new ExcelEficiencia(modelos, dt.GetReporteHCJorge(), modelosS, dt.GetReporte(), corteMochis,plataformas,plataformasMSD,TCODIGOS,plataformastot,plataformasConcentradoLM,plataformasConcentradoGML,plataformasConcentradoLMYGML,plataformasGMTLM,plataformasK2XXLM,plataformasT1XXLM,plataformasE2XXLM,plataformasISUZULM,plataformasSERVICIOSLM,plataformasCORTELM,plataformasGMTGML,plataformasK2XXGML,plataformasT1XXGML,plataformascorteGML,plataformasserviciosGML,CATIA,PPCORTE ,nota,txt_captura.getText());

        } catch (SQLException | IOException ex) {
            System.out.println(ex+"2333");
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_btnExportar1ActionPerformed

    private void panelCadIA22MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelCadIA22MouseClicked
        // TODO add your handling code here:
        this.hide();
        CapturaExcelManufactura manuf=new CapturaExcelManufactura("3", "B");
        manuf.setVisible(true);
        manuf.setLocationRelativeTo(null);
    }//GEN-LAST:event_panelCadIA22MouseClicked

    private void panelCadIA23MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelCadIA23MouseClicked
        // TODO add your handling code here:
         this.hide();
        CapturaExcelManufactura manuf=new CapturaExcelManufactura("3", "A");
        manuf.setVisible(true);
        manuf.setLocationRelativeTo(null);
    }//GEN-LAST:event_panelCadIA23MouseClicked

    private void panelCadIA26MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelCadIA26MouseClicked
 this.hide();
        CapturaExcelManufactura manuf=new CapturaExcelManufactura("5", "B");
        manuf.setVisible(true);
        manuf.setLocationRelativeTo(null);  
        // TODO add your handling code here:
    }//GEN-LAST:event_panelCadIA26MouseClicked

    private void panelCadIA27MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelCadIA27MouseClicked
//        // TODO add your handling code here:
         this.hide();
        CapturaExcelManufactura manuf=new CapturaExcelManufactura("5", "A");
        manuf.setVisible(true);
        manuf.setLocationRelativeTo(null);
    }//GEN-LAST:event_panelCadIA27MouseClicked

    private void panelCadIA4KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_panelCadIA4KeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_panelCadIA4KeyTyped

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
          if(Principal.UsuarioLogeado.turno.equals("TODOS"))
        {
        CapturaExcelGSD cEG=new CapturaExcelGSD();
        cEG.setLocationRelativeTo(null);
        cEG.setVisible(true);
        //this.setVisible(false);
        }
         else
             JOptionPane.showMessageDialog(this, "No tiene permisos para accesar a este modulo...", "Error", JOptionPane.WARNING_MESSAGE);
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(MenuManufactura.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MenuManufactura.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MenuManufactura.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MenuManufactura.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MenuManufactura().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExportar;
    private javax.swing.JButton btnExportar1;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel100;
    private javax.swing.JLabel jLabel101;
    private javax.swing.JLabel jLabel102;
    private javax.swing.JLabel jLabel103;
    private javax.swing.JLabel jLabel104;
    private javax.swing.JLabel jLabel105;
    private javax.swing.JLabel jLabel106;
    private javax.swing.JLabel jLabel107;
    private javax.swing.JLabel jLabel108;
    private javax.swing.JLabel jLabel109;
    private javax.swing.JLabel jLabel110;
    private javax.swing.JLabel jLabel111;
    private javax.swing.JLabel jLabel112;
    private javax.swing.JLabel jLabel113;
    private javax.swing.JLabel jLabel114;
    private javax.swing.JLabel jLabel115;
    private javax.swing.JLabel jLabel116;
    private javax.swing.JLabel jLabel117;
    private javax.swing.JLabel jLabel118;
    private javax.swing.JLabel jLabel119;
    private javax.swing.JLabel jLabel120;
    private javax.swing.JLabel jLabel121;
    private javax.swing.JLabel jLabel122;
    private javax.swing.JLabel jLabel123;
    private javax.swing.JLabel jLabel124;
    private javax.swing.JLabel jLabel125;
    private javax.swing.JLabel jLabel126;
    private javax.swing.JLabel jLabel127;
    private javax.swing.JLabel jLabel128;
    private javax.swing.JLabel jLabel129;
    private javax.swing.JLabel jLabel130;
    private javax.swing.JLabel jLabel131;
    private javax.swing.JLabel jLabel132;
    private javax.swing.JLabel jLabel133;
    private javax.swing.JLabel jLabel134;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JLabel jLabel98;
    private javax.swing.JLabel jLabel99;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblEficManufIA;
    private javax.swing.JLabel lblEficManufIB;
    private javax.swing.JLabel lblEficManufIIA;
    private javax.swing.JLabel lblEficManufIIB;
    private javax.swing.JLabel lblEficManufIIIA;
    private javax.swing.JLabel lblEficManufIIIB;
    private javax.swing.JLabel lblEficManufIVA;
    private javax.swing.JLabel lblEficManufIVB;
    private javax.swing.JLabel lblEficManufIVC;
    private javax.swing.JLabel lblEficManufPlanta;
    private javax.swing.JLabel lblEficManufPlanta1;
    private javax.swing.JLabel lblEficManufPlanta2;
    private javax.swing.JLabel lblEficManufPlanta5;
    private javax.swing.JLabel lblEficManufPlanta6;
    private javax.swing.JLabel lblEficManufPlantaA;
    private javax.swing.JLabel lblEficManufPlantaB;
    private javax.swing.JLabel lblEficManufVIA;
    private javax.swing.JLabel lblEficManufVIA1;
    private javax.swing.JLabel lblEficManufVIB;
    private javax.swing.JLabel lblEficManufVIB1;
    private javax.swing.JLabel lblHorasPagIA;
    private javax.swing.JLabel lblHorasPagIB;
    private javax.swing.JLabel lblHorasPagIIA;
    private javax.swing.JLabel lblHorasPagIIB;
    private javax.swing.JLabel lblHorasPagIIIA;
    private javax.swing.JLabel lblHorasPagIIIB;
    private javax.swing.JLabel lblHorasPagIVA;
    private javax.swing.JLabel lblHorasPagIVB;
    private javax.swing.JLabel lblHorasPagIVC;
    private javax.swing.JLabel lblHorasPagPlanta;
    private javax.swing.JLabel lblHorasPagPlanta1;
    private javax.swing.JLabel lblHorasPagPlanta2;
    private javax.swing.JLabel lblHorasPagPlanta5;
    private javax.swing.JLabel lblHorasPagPlanta6;
    private javax.swing.JLabel lblHorasPagPlantaA;
    private javax.swing.JLabel lblHorasPagPlantaB;
    private javax.swing.JLabel lblHorasPagVIA;
    private javax.swing.JLabel lblHorasPagVIA1;
    private javax.swing.JLabel lblHorasPagVIB;
    private javax.swing.JLabel lblHorasPagVIB1;
    private javax.swing.JLabel lblhorasEmbIA;
    private javax.swing.JLabel lblhorasEmbIB;
    private javax.swing.JLabel lblhorasEmbIIA;
    private javax.swing.JLabel lblhorasEmbIIB;
    private javax.swing.JLabel lblhorasEmbIIIA;
    private javax.swing.JLabel lblhorasEmbIIIB;
    private javax.swing.JLabel lblhorasEmbIVA;
    private javax.swing.JLabel lblhorasEmbIVB;
    private javax.swing.JLabel lblhorasEmbIVC;
    private javax.swing.JLabel lblhorasEmbPlanta;
    private javax.swing.JLabel lblhorasEmbPlanta1;
    private javax.swing.JLabel lblhorasEmbPlanta2;
    private javax.swing.JLabel lblhorasEmbPlanta5;
    private javax.swing.JLabel lblhorasEmbPlanta6;
    private javax.swing.JLabel lblhorasEmbPlantaA;
    private javax.swing.JLabel lblhorasEmbPlantaB;
    private javax.swing.JLabel lblhorasEmbVIA;
    private javax.swing.JLabel lblhorasEmbVIA1;
    private javax.swing.JLabel lblhorasEmbVIB;
    private javax.swing.JLabel lblhorasEmbVIB1;
    private javax.swing.JPanel panelCadIA14;
    private javax.swing.JPanel panelCadIA15;
    private javax.swing.JPanel panelCadIA16;
    private javax.swing.JPanel panelCadIA17;
    private javax.swing.JPanel panelCadIA18;
    private javax.swing.JPanel panelCadIA19;
    private javax.swing.JPanel panelCadIA20;
    private javax.swing.JPanel panelCadIA21;
    private javax.swing.JPanel panelCadIA22;
    private javax.swing.JPanel panelCadIA23;
    private javax.swing.JPanel panelCadIA24;
    private javax.swing.JPanel panelCadIA25;
    private javax.swing.JPanel panelCadIA26;
    private javax.swing.JPanel panelCadIA27;
    private javax.swing.JPanel panelCadIA4;
    private javax.swing.JPanel panelCadIA5;
    private javax.swing.JPanel panelCadIA6;
    private javax.swing.JPanel panelCadIA7;
    private javax.swing.JTextField txt_captura;
    // End of variables declaration//GEN-END:variables
}
