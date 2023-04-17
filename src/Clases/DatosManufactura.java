/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import Reportes.ExcelDistriHCPorCodigo;
import Reportes.ExcelEficiencia;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;
import manufactura.Principal;

/**
 *
 * @author gzld6k
 */
public class DatosManufactura {
    public PlatCodLinea Codigo;
  //public String idcodigo="";
    public String HCDIRLINEA="";
    public String HCDIRLPS ="";
    public String HCDIRSOPORTE="";
    public String HCDIRTABINSP="";
    public String HCDIRCONTE="";
    public String HCDIRFTQ="";
    public String HCDIRPILOTOS="";
    public String HCDIRSISTEMAS="";
    public String HCINDRUTAS="";
    public String HCRUTASINT="";
    public String PUNTOSPZAPOND="";
    public String CAP_UTIL_HTA="";
    public String SalidaEnPieza="";
    public String HCDIRSOPLPS="";   
    public DatosManufactura(){ 
    }
    
     public DatosManufactura(PlatCodLinea  codigo,  String hcdirlinea, String hcdirlps, String hcdirsoporte, String hcdirtabinsp, String hcdirconte, String hcdirftq, String hcdirpilotos, String hcdirsistemas, String hcindRutas, String hcRutasInt, String ptosPzaPond, String CapUtilHta, String PiezasSalida, String HcdirSopLPS){
        Codigo=codigo;
        HCDIRLINEA=hcdirlinea;
        HCDIRLPS=hcdirlps;
        HCDIRSOPORTE=hcdirsoporte;
        HCDIRTABINSP=hcdirtabinsp;
        HCDIRCONTE=hcdirconte;
        HCDIRFTQ=hcdirftq;
        HCDIRPILOTOS=hcdirpilotos;
        HCDIRSISTEMAS=hcdirsistemas;
        HCINDRUTAS=hcindRutas;
        PUNTOSPZAPOND=ptosPzaPond;
        CAP_UTIL_HTA=CapUtilHta;
        SalidaEnPieza=PiezasSalida;
        HCDIRSOPLPS=HcdirSopLPS;
        HCRUTASINT=hcRutasInt;
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
    
     public DefaultTableModel  GetReporte(){
         DefaultTableModel modelo=new DefaultTableModel();
         try  {
              Double HrsPagIA=0.0, HrsPagIIA=0.0, HrsPagIIIA=0.0, HrsPagIB=0.0, HrsPagIIB=0.0, HrsPagIIIB=0.0,HrsPagVIA=0.0,HrsPagVIB=0.0,HrsPagVIIIB=0.0,HrsPagVIIIA=0.0;
              Double HrsEMBIA=0.0, HrsEMBIIA=0.0, HrsEMBIIIA=0.0, HrsEMBIB=0.0, HrsEMBIIB=0.0, HrsEMBIIIB=0.0,HrsEMBVIA=0.0,HrsEMBVIB=0.0,HrsEMBVIIIB=0.0,HrsEMBVIIIA=0.0;
              Double EficIA=0.0, EficIIA=0.0, EficIIIA=0.0, EficIB=0.0, EficIIB=0.0, EficIIIB=0.0,EficVIA=0.0,EficVIB=0.0,EficVIIIB=0.0,EficVIIIA=0.0;
              Double HrsPagCorteA=0.0, HrsEmbCorteA=0.0, EficCorteA=0.0, HrsPagCorteB=0.0, HrsEmbCorteB=0.0, EficCorteB=0.0,HorasEmbServicios=15.0,HrsPagCorteC=0.0, HrsEmbCorteC=0.0, EficCorteC=0.0,HorasEmbServiciosB=0.0; 
              ResultSet rs= Principal.cn.GetConsulta("select DISTINCT manu.IDCODIGO,manu.CADENA,manu.LINEA, manu.turno, manu.hc, manu.activo,IF(manu.LINEA='CORTE' AND manu.CADENA=6,cortehrsemb.hrsEMBC,manu.horasemb)as horasemb, (manu.hrsPagadas) as hrsPagadas,ROUND(IF(manu.LINEA='CORTE' AND manu.CADENA=6,(cortehrsemb.hrsEMBC/manu.hrsPagadas)*100,(manu.horasemb/manu.hrsPagadas)*100),2)as efic   from \n" +
"              (select c.IDCODIGO,c.arnes,c.LINEA, c.CADENA, c.turno, sum(m.HCDIRLINEA) as hc,round( sum((m.PUNTOSPZAPOND*m.SALIDAENPIEZA/100))) as horasemb,  IF(c.TURNO='A', sum(((elinea+estaciones+ kits+HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*9)),\n" +
"               sum(((elinea+estaciones+ kits+ HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*7.9))) as hrsPagadas, m.activo  from manufactura as m, codigos as c where m.idcodigo=c.IDCODIGO and c.CADENA<>4  and c.TURNO='A' GROUP BY c.idcodigo) as manu,\n" +
"               (SELECT SUM(TRUNCATE((((g.HCDIRCORTE*m.SALIDAENPIEZA)/100)),2)) as hrsEMBC FROM gsd as g, manufactura as m, codigos as c where c.IDCODIGO=m.IDCODIGO and c.IDCODIGO=g.IDCODIGO and m.activo=1 AND C.CADENA=6 and c.TURNO='A' GROUP BY c.TURNO) as cortehrsemb\n" +
"                where manu.CADENA<>4 and manu.activo<>0  UNION select DISTINCT manu.IDCODIGO,manu.CADENA,manu.LINEA, manu.turno, manu.hc, manu.activo,IF(manu.LINEA='CORTE' AND manu.CADENA=6,cortehrsemb.hrsEMBC,manu.horasemb)as horasemb, (manu.hrsPagadas) as hrsPagadas,ROUND(IF(manu.LINEA='CORTE' AND manu.CADENA=6,(cortehrsemb.hrsEMBC/manu.hrsPagadas)*100,(manu.horasemb/manu.hrsPagadas)*100),2)as efic   from \n" +
"              (select c.IDCODIGO,c.arnes,c.LINEA, c.CADENA, c.turno, sum(m.HCDIRLINEA) as hc,round( sum((m.PUNTOSPZAPOND*m.SALIDAENPIEZA/100))) as horasemb,  IF(c.TURNO='A', sum(((elinea+estaciones+ kits+HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*9)),\n" +
"               sum(((elinea+estaciones+ kits+ HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*7.9))) as hrsPagadas, m.activo  from manufactura as m, codigos as c where m.idcodigo=c.IDCODIGO and c.CADENA<>4 and c.TURNO='B' GROUP BY c.idcodigo) as manu,\n" +
"               (SELECT SUM(TRUNCATE((((g.HCDIRCORTE*m.SALIDAENPIEZA)/100)),2)) as hrsEMBC FROM gsd as g, manufactura as m, codigos as c where c.IDCODIGO=m.IDCODIGO and c.IDCODIGO=g.IDCODIGO and m.activo=1  and c.TURNO='B'  GROUP BY c.TURNO) as cortehrsemb\n" +
"                where manu.CADENA<>4 and manu.activo<>0 ");
                   //   + "select  manu.CADENA, manu.turno, manu.hc, manu.activo,  (manu.horasemb) as horasemb, (manu.hrsPagadas) as hrsPagadas,  ( manu.horasemb/manu.hrsPagadas)*100 as efic from \n" +
              //  "(select c.LINEA, c.CADENA, c.turno, sum(m.HCDIRLINEA) as hc, round (sum((m.PUNTOSPZAPOND*m.SALIDAENPIEZA/100))) as horasemb,  IF(c.TURNO='A', sum(((c.hcdirecto)*9)),sum(((c.hcdirecto)*7.9))) as hrsPagadas, m.activo  from manufactura as m, personas as c where m.idcodigo=c.IDCODIGO   GROUP BY c.idcodigo) as manu\n" +
              //  "where manu.CADENA<>4   and manu.activo<>0");
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
                          HrsEMBVIIIA+=rs.getDouble("horasemb");
                          }else if(rs.getString("turno").equals("B"))
                          {
                          HrsPagVIIIB+=rs.getDouble("hrsPagadas");
                          HrsEMBVIIIB+=rs.getDouble("horasemb");
                          }
                          break;
                  }
              }
               rs=Principal.cn.GetConsulta("SELECT manufactura.HCDIRLINEA\n" +
                    "FROM\n" +
                    "manufactura ,\n" +
                    "codigos \n" +
                    "WHERE\n" +
                    "manufactura.IDCODIGO = codigos.IDCODIGO and CODIGOS.PLATAFORMA='SERVICIOS' and codigos.turno='a'");
              Double kachirulesAA=0.0;
               if(rs.next())
               {
                   kachirulesAA=rs.getDouble("HCDIRLINEA");
               }
               
                rs=Principal.cn.GetConsulta("SELECT manufactura.HCDIRLINEA\n" +
                    "FROM\n" +
                    "manufactura ,\n" +
                    "codigos \n" +
                    "WHERE\n" +
                    "manufactura.IDCODIGO = codigos.IDCODIGO and CODIGOS.PLATAFORMA='SERVICIOS' and codigos.turno='b'");
              Double kachirulesBB=0.0;
               if(rs.next())
               {
                   kachirulesBB=rs.getDouble("HCDIRLINEA");
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
               
               Double Servicios=0.0;
               if(rs.next())
               {
                   Servicios=rs.getDouble("HCDIRLINEA");
               }
               
               
                   rs=Principal.cn.GetConsulta("SELECT DISTINCT corte.idcodigo, hcpag.IDCODIGO, hcpag.HCDIRLINEA, corte.hrsEMBC, hcpag.horaspagadas, hcpag.TURNO, truncate((corte.hrsEMBC/hcpag.horaspagadas)*100,2) as eficiencia \n" +
"                                    from   (select c.IDCODIGO, c.LINEA, c.CODIGO, c.TURNO, m.SALIDAENPIEZA, g.HCDIRCORTE as cortee,  \n" +
"                    SUM(TRUNCATE((((g.HCDIRCORTE*m.SALIDAENPIEZA)/100)),2)) as hrsEMBC,   g.HCDIRLPS as 'POND.LPS', \n" +
"                    TRUNCATE( if(c.TURNO='B' or c.TURNO='C', ((g.HCDIRLPS*m.SALIDAENPIEZA)/7.9/100),((g.HCDIRLPS *m.SALIDAENPIEZA)/9/100)) ,2) as LPS, \n" +
"                    g.HCDIRENSFINAL as 'PUNTOS.EF', TRUNCATE( if(c.TURNO='B' or c.TURNO='C',((g.HCDIRENSFINAL*m.SALIDAENPIEZA)/7.9/100), \n" +
"                    ((g.HCDIRENSFINAL *m.SALIDAENPIEZA)/9/100)),2) as ENSFINAL  FROM codigos as c, manufactura as m, gsd  as g\n" +
"                     where c.IDCODIGO=m.IDCODIGO and c.IDCODIGO=g.IDCODIGO and m.activo=1 AND C.CADENA<>6 GROUP BY c.TURNO) as corte,\n" +
"                                   (select c.IDCODIGO, HCDIRLINEA, C.TURNO, case c.TURNO when 'B' THEN SUM((HCDIRLINEA)*7.9) \n" +
"                                         WHEN 'A' THEN sum((HCDIRLINEA)*9) \n" +
"                                           WHEN 'C' THEN sum((m.HCDIRLINEA)*7.32)  END \n" +
"                                         as horaspagadas from manufactura as m INNER JOIN codigos as c ON m.IDCODIGO=c.IDCODIGO where  C.CADENA='4'  GROUP BY c.TURNO) as hcpag WHERE hcpag.TURNO=corte.TURNO");
//"                                       as horaspagadas from manufactura as m INNER JOIN codigos as c ON m.IDCODIGO=c.IDCODIGO where c.CADENA=\"+Cadena+\" and C.LINEA='CORTE'  GROUP BY c.TURNO) as hcpag WHERE hcpag.TURNO=corte.TURNO ");
//                           + "            SELECT DISTINCT                corte.idcodigo, hcpag.IDCODIGO, hcpag.HCDIRLINEA, corte.hrsEMBC, hcpag.horaspagadas, hcpag.TURNO, \n" +
//"truncate((corte.hrsEMBC/hcpag.horaspagadas)*100,2) as eficiencia from  (select c.IDCODIGO, c.LINEA, c.CODIGO, c.TURNO, m.SALIDAENPIEZA, g.HCDIRCORTE as cortee, \n" +
//" SUM(TRUNCATE((((g.HCDIRCORTE*m.SALIDAENPIEZA)/100)),2)) as hrsEMBC,   g.HCDIRLPS as 'POND.LPS', \n" +
//"TRUNCATE( if(c.TURNO='B' or c.TURNO='C', ((g.HCDIRLPS*m.SALIDAENPIEZA)/7.9/100),((g.HCDIRLPS *m.SALIDAENPIEZA)/9/100)) ,2) as LPS, g.HCDIRENSFINAL as \n" +
//"'PUNTOS.EF', TRUNCATE( if(c.TURNO='B' or c.TURNO='C',((g.HCDIRENSFINAL*m.SALIDAENPIEZA)/7.9/100), ((g.HCDIRENSFINAL *m.SALIDAENPIEZA)/9/100)),2) as ENSFINAL  \n" +
//"FROM codigos as c, manufactura as m, gsd  as g where c.IDCODIGO=m.IDCODIGO and c.IDCODIGO=g.IDCODIGO and m.activo=1   GROUP BY c.TURNO) as corte,\n" +
//"                (select c.IDCODIGO, HCDIRLINEA, C.TURNO, \n" +
//"                     case c.TURNO when'B' THEN SUM((elinea+ estaciones+kits+HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*7.9)\n" +
//"                     WHEN 'A' THEN sum((elinea+ estaciones+kits+HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*9) \n" +
//"                      WHEN 'C' THEN sum((elinea+ estaciones+kits+HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*6.1)  END \n" +
//"                     as horaspagadas from manufactura as m, codigos  as c where m.IDCODIGO=c.IDCODIGO and c.CADENA=4   GROUP BY c.TURNO) as hcpag ");
             int cont=1; 
                while(rs.next())
               {
                if(cont==0)
                { 
                    HrsEmbCorteA=Regresa2Decimales( rs.getDouble("hrsEMBC"));
                    HrsPagCorteA=Regresa2Decimales(rs.getDouble("horaspagadas"));
                    EficCorteA=Regresa2Decimales(rs.getDouble("eficiencia"));
                }
                if(cont==2)
                {
                    HrsEmbCorteB=Regresa2Decimales(rs.getDouble("hrsEMBC"));
                    HrsPagCorteB=Regresa2Decimales(rs.getDouble("horaspagadas"));
                    EficCorteB=Regresa2Decimales(rs.getDouble("eficiencia"));
                }
                //turno c
                if(cont==4)
                {
                    HrsEmbCorteC=Regresa2Decimales(rs.getDouble("hrsEMBC"));
                    HrsPagCorteC=Regresa2Decimales(rs.getDouble("horaspagadas"));
                    EficCorteC=Regresa2Decimales(rs.getDouble("eficiencia"));
                }
                cont++;
               }

              EficIA=( HrsEMBIA/HrsPagIA);
              EficIIA=(HrsEMBIIA/HrsPagIIA);
              EficIIIA=(HrsEMBIIIA/HrsPagIIIA);
              EficIB=( HrsEMBIB/HrsPagIB);
              EficIIB=(HrsEMBIIB/HrsPagIIB);
              EficIIIB=(HrsEMBIIIB/HrsPagIIIB);
              EficVIA=(HrsEMBVIA/HrsPagVIA);
              EficVIB=(HrsEMBVIB/HrsPagVIB);
              EficVIIIA=(HrsEMBVIIIA/HrsPagVIIIA);
              EficVIIIB=(HrsEMBVIIIB/HrsPagVIIIB);
             
             HrsPagIA=Regresa2Decimales(HrsPagIA);
             HrsPagIIA=Regresa2Decimales(HrsPagIIA);
             HrsPagIIIA=Regresa2Decimales(HrsPagIIIA);
             HrsPagVIA=Regresa2Decimales(HrsPagVIA);
             HrsPagVIIIA=Regresa2Decimales(HrsPagVIIIA);
             
             
             HrsEMBIA=Regresa2Decimales(HrsEMBIA);
             HrsEMBIIA=Regresa2Decimales(HrsEMBIIA);
             HrsEMBIIIA=Regresa2Decimales(HrsEMBIIIA);
             HrsEMBVIA=Regresa2Decimales(HrsEMBVIA);
             HrsEMBVIIIA=Regresa2Decimales(HrsEMBVIIIA);
          

             HrsEMBIB=Regresa2Decimales(HrsEMBIB);
             HrsEMBIIB=Regresa2Decimales(HrsEMBIIB);
             HrsEMBIIIB=Regresa2Decimales(HrsEMBIIIB);
             HrsEMBVIB=Regresa2Decimales(HrsEMBVIB);
             HrsEMBVIIIB=Regresa2Decimales(HrsEMBVIIIB);
         
             
             HrsPagIB=Regresa2Decimales(HrsPagIB);
             HrsPagIIB=Regresa2Decimales(HrsPagIIB);
             HrsPagIIIB=Regresa2Decimales(HrsPagIIIB);
             HrsPagVIB=Regresa2Decimales(HrsPagVIB);
             HrsPagVIIIB=Regresa2Decimales(HrsPagVIIIB);
           
              EficIA=Regresa2Decimales(EficIA);
              EficIIA=Regresa2Decimales(EficIIA);
              EficIIIA=Regresa2Decimales(EficIIIA);
              EficIB=Regresa2Decimales(EficIB);
              EficIIB=Regresa2Decimales(EficIIB);
              EficIIIB=Regresa2Decimales(EficIIIB);
              EficVIA=Regresa2Decimales(EficVIA);
              EficVIB=Regresa2Decimales(EficVIB);
              EficVIIIA=Regresa2Decimales(EficVIIIA);
              EficVIIIB=Regresa2Decimales(EficVIIIB);
              
             // EficServicios=Regresa2Decimales(HorasEmbServicios/(Servicios*9));
              
              
              //Double hrsEmbTotal=HrsEMBIA+HrsEMBIB+HrsEMBIIA+HrsEMBIIB+HrsEMBIIIA+HrsEMBIIIB+HrsEmbCorteA+HrsEmbCorteB;
              //Double hrsPagTotal=HrsPagIA+HrsPagIB+HrsPagIIA+HrsPagIIB+HrsPagIIIA+HrsPagIIIB+HrsPagCorteA+hrs
              modelo.setColumnIdentifiers(new Object[]{"CADENA", "HC A", "HC B","HC C", "HRS EMB A",  "HRS EMB B","HRS EMB C", "HRS.PAG. A", "HRS.PAG. B","HRS.PAG. C",  "% EFIC A", "% EFIC B","% EFIC C", "% EFIC CADENA"});
              modelo.addRow(new Object[]{"1", (HrsPagIA/9), (double)Math.round(HrsPagIB/7.9),0, HrsEMBIA, HrsEMBIB,0, HrsPagIA, HrsPagIB,0, EficIA, EficIB,0, 0});
              modelo.addRow(new Object[]{"2", (HrsPagIIA/9), (double)Math.round(HrsPagIIB/7.9),0, HrsEMBIIA, HrsEMBIIB,0, HrsPagIIA, HrsPagIIB,0, EficIIA, EficIIB,0, 0 });
              modelo.addRow(new Object[]{"3", (HrsPagIIIA/9), (double)Math.round(HrsPagIIIB/7.9),0, HrsEMBIIIA, HrsEMBIIIB,0, HrsPagIIIA, HrsPagIIIB,0, EficIIIA, EficIIIB,0, 0 });
              modelo.addRow(new Object[]{"4", (HrsPagCorteA/9),(double)Math.round(HrsPagCorteB/7.9),(double)Math.round(HrsPagCorteC/7.32), HrsEmbCorteA, HrsEmbCorteB,HrsEmbCorteC, HrsPagCorteA, HrsPagCorteB,HrsPagCorteC, EficCorteA, EficCorteB,EficCorteC, 0});
              modelo.addRow(new Object[]{"6", (HrsPagVIA/9), (double)Math.round(HrsPagVIB/7.9),0, HrsEMBVIA, HrsEMBVIB,0, HrsPagVIA, HrsPagVIB,0, EficVIA, EficVIB,0, 0 });
            modelo.addRow(new Object[]{"5", (HrsPagVIIIA/9), (double)Math.round(HrsPagVIIIB/7.9),0, HrsEMBVIIIA, HrsEMBVIIIB,0, HrsPagVIIIA, HrsPagVIIIB,0, EficVIIIA, EficVIIIB,0, 0 });

              double eficienciaServicios= kachirulesAA/(kachirulesAA*9);
              double eficienciaServiciosB= kachirulesBB/(kachirulesBB*7.9);
              double ExtrasGA= kachirulesA/(kachirulesA*9);
              double ExtrasGB= kachirulesB/(kachirulesB*7.9);
            
             modelo.addRow(new Object[]{"EXTRAS GERENCIA", kachirulesA, kachirulesB,0,  0, 0,0,   kachirulesA*9, kachirulesB*7.9,0, 0,0 ,0,0});
              modelo.addRow(new Object[]{"SERVICIOS", kachirulesAA, kachirulesBB,0, HorasEmbServicios, HorasEmbServiciosB,0, kachirulesAA*9, kachirulesBB*7.9,0, eficienciaServicios ,  0, 0 ,   0});
             
           //  modelo.addRow(new Object[]{"EXTRAS GERENCIA", kachirulesA, kachirulesB,  0, 0,   0, 0, 0,0 ,0});
           //  modelo.addRow(new Object[]{"SERVICIOS",  "A", Servicios, HorasEmbServicios,  Servicios*9,  EficServicios, 0});
              
              ExcelEficiencia excel=new ExcelEficiencia();
             // excel.GetExceltotalPlanta(modelo, "EFICIENCIA PLANTA");
          }catch(Exception e )
          {
            System.out.println(e.toString()+"getreporte normal");
          }
         return modelo;
      }
     
     public DefaultTableModel  GetReporteHCJorge(){
         DefaultTableModel modelo=new DefaultTableModel();
         try  {
              Double HrsPagIA=0.0, HrsPagIIA=0.0, HrsPagIIIA=0.0, HrsPagIB=0.0, HrsPagIIB=0.0, HrsPagIIIB=0.0, HrsPagVIA=0.0,HrsPagVIIIB=0.0,HrsPagVIIIA=0.0,HrsPagVIB=0.0;
              Double HrsEMBIA=0.0, HrsEMBIIA=0.0, HrsEMBIIIA=0.0, HrsEMBIB=0.0, HrsEMBIIB=0.0, HrsEMBIIIB=0.0, HrsEMBVIA=0.0,HrsEMBVIB=0.0,HrsEMBVIIIB=0.0,HrsEMBVIIIA=0.0;
              Double EficIA=0.0, EficIIA=0.0, EficIIIA=0.0, EficIB=0.0, EficIIB=0.0, EficIIIB=0.0,EficVIA=0.0,EficVIB=0.0,EficVIIIB=0.0,EficVIIIA=0.0;
              Double EficServiciosA=0.0, HorasEmbServicios=15.0,EficServiciosB=.0,HorasEmbServiciosB=0.0;
              Double ContIA=0.0, ContIB=0.0, ContIIA=0.0, ContIIB=0.0, ContIIIB=0.0, ContIIIA=0.0, ContIVA=0.0, ContIVB=0.0, ContIVC=0.0, ContVIIIA=0.0, ContVIIIB=0.0, ContVIA=0.0, ContVIB=0.0;
              Double HrsPagCorteA=0.0, HrsEmbCorteA=0.0, EficCorteA=0.0, HrsPagCorteB=0.0, HrsEmbCorteB=0.0, EficCorteB=0.0,HrsPagCorteC=0.0, HrsEmbCorteC=0.0, EficCorteC=0.0; 
              ResultSet rs= Principal.cn.GetConsulta("select DISTINCT manu.IDCODIGO,manu.CADENA,manu.LINEA, manu.turno, manu.hc, manu.activo,IF(manu.LINEA='CORTE' AND manu.CADENA=6,cortehrsemb.hrsEMBC,manu.horasemb)as horasemb, (manu.hrsPagadas) as hrsPagadas,ROUND(IF(manu.LINEA='CORTE' AND manu.CADENA=6,(cortehrsemb.hrsEMBC/manu.hrsPagadas)*100,(manu.horasemb/manu.hrsPagadas)*100),2)as efic   from \n" +
"              (select c.IDCODIGO,c.arnes,c.LINEA, c.CADENA, c.turno, sum(m.HCDIRLINEA) as hc,round( sum((m.PUNTOSPZAPOND*m.SALIDAENPIEZA/100)),2) as horasemb,  IF(c.TURNO='A', sum(((elinea+estaciones+ kits+HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*9)),\n" +
"              sum(((elinea+estaciones+ kits+ HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*7.9))) as hrsPagadas, m.activo  from manufactura as m, codigos as c where m.idcodigo=c.IDCODIGO and c.CADENA<>4  and c.TURNO='A' GROUP BY c.idcodigo) as manu,\n" +
"              (SELECT SUM(TRUNCATE((((g.HCDIRCORTE*m.SALIDAENPIEZA)/100)),2)) as hrsEMBC FROM gsd as g, manufactura as m, codigos as c where c.IDCODIGO=m.IDCODIGO and c.IDCODIGO=g.IDCODIGO and m.activo=1 AND C.CADENA=6 and c.TURNO='A' GROUP BY c.TURNO) as cortehrsemb\n" +
"              where manu.CADENA<>4 and manu.activo<>0  UNION select DISTINCT manu.IDCODIGO,manu.CADENA,manu.LINEA, manu.turno, manu.hc, manu.activo,IF(manu.LINEA='CORTE' AND manu.CADENA=6,cortehrsemb.hrsEMBC,manu.horasemb)as horasemb, (manu.hrsPagadas) as hrsPagadas,ROUND(IF(manu.LINEA='CORTE' AND manu.CADENA=6,(cortehrsemb.hrsEMBC/manu.hrsPagadas)*100,(manu.horasemb/manu.hrsPagadas)*100),2)as efic   from \n" +
"              (select c.IDCODIGO,c.arnes,c.LINEA, c.CADENA, c.turno, sum(m.HCDIRLINEA) as hc,round( sum((m.PUNTOSPZAPOND*m.SALIDAENPIEZA/100)),2) as horasemb,  IF(c.TURNO='A', sum(((elinea+estaciones+ kits+HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*9)),\n" +
"              sum(((elinea+estaciones+ kits+ HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*7.9))) as hrsPagadas, m.activo  from manufactura as m, codigos as c where m.idcodigo=c.IDCODIGO and c.CADENA<>4 GROUP BY c.idcodigo) as manu,\n" +
"              (SELECT SUM(TRUNCATE((((g.HCDIRCORTE*m.SALIDAENPIEZA)/100)),2)) as hrsEMBC FROM gsd as g, manufactura as m, codigos as c where c.IDCODIGO=m.IDCODIGO and c.IDCODIGO=g.IDCODIGO and m.activo=1 and c.TURNO='B'  GROUP BY c.TURNO) as cortehrsemb where manu.CADENA<>4 and manu.activo<>0");
                     // + "select  manu.CADENA, manu.turno, manu.hc, manu.activo,  (manu.horasemb) as horasemb, (manu.hrsPagadas) as hrsPagadas,  ( manu.horasemb/manu.hrsPagadas)*100 as efic from \n" +
               // "(select c.LINEA, c.CADENA, c.turno, sum(m.HCDIRLINEA+elinea+ estaciones+kits) as hc,round( sum((m.PUNTOSPZAPOND*m.SALIDAENPIEZA/100))) as horasemb,  IF(c.TURNO='A', sum(((elinea+ estaciones+kits+HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*9)),sum(((+m.elinea+ m.estaciones+m.kits+HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*7.9))) as hrsPagadas, m.activo  from manufactura as m, codigos as c where m.idcodigo=c.IDCODIGO and m.activo=1 GROUP BY c.idcodigo) as manu\n" +
              //  "where manu.CADENA<>4   and manu.activo<>0");
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
                          HrsEMBVIIIA+=rs.getDouble("horasemb");
                          }else if(rs.getString("turno").equals("B"))
                          {
                          HrsPagVIIIB+=rs.getDouble("hrsPagadas");
                          HrsEMBVIIIB+=rs.getDouble("horasemb");
                          }
                          break;
                  }
                  
                  
                  
                  
              }
               rs=Principal.cn.GetConsulta("SELECT codigos.turno, manufactura.HCDIRLINEA\n" +
                    "FROM\n" +
                    "manufactura ,\n" +
                    "codigos \n" +
                    "WHERE\n" +
                    "manufactura.IDCODIGO = codigos.IDCODIGO and CODIGOS.linea='91' AND CADENA='7' AND TURNO<>'C'");
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
               
                  rs=Principal.cn.GetConsulta("SELECT DISTINCT corte.idcodigo, hcpag.IDCODIGO, hcpag.HCDIRLINEA, corte.hrsEMBC, hcpag.horaspagadas, hcpag.TURNO, truncate((corte.hrsEMBC/hcpag.horaspagadas)*100,2) as eficiencia \n" +
"                                    from   (select c.IDCODIGO, c.LINEA, c.CODIGO, c.TURNO, m.SALIDAENPIEZA, g.HCDIRCORTE as cortee,  \n" +
"                    SUM(TRUNCATE((((g.HCDIRCORTE*m.SALIDAENPIEZA)/100)),2)) as hrsEMBC,   g.HCDIRLPS as 'POND.LPS', \n" +
"                    TRUNCATE( if(c.TURNO='B' or c.TURNO='C', ((g.HCDIRLPS*m.SALIDAENPIEZA)/7.9/100),((g.HCDIRLPS *m.SALIDAENPIEZA)/9/100)) ,2) as LPS, \n" +
"                    g.HCDIRENSFINAL as 'PUNTOS.EF', TRUNCATE( if(c.TURNO='B' or c.TURNO='C',((g.HCDIRENSFINAL*m.SALIDAENPIEZA)/7.9/100), \n" +
"                    ((g.HCDIRENSFINAL *m.SALIDAENPIEZA)/9/100)),2) as ENSFINAL  FROM codigos as c, manufactura as m, gsd  as g\n" +
"                     where c.IDCODIGO=m.IDCODIGO and c.IDCODIGO=g.IDCODIGO and m.activo=1 AND C.CADENA<>6 GROUP BY c.TURNO) as corte,\n" +
"                                   (select c.IDCODIGO, HCDIRLINEA, C.TURNO, case c.TURNO when 'B' THEN SUM((HCDIRLINEA)*7.9) \n" +
"                                         WHEN 'A' THEN sum((HCDIRLINEA)*9) \n" +
"                                           WHEN 'C' THEN sum((m.HCDIRLINEA)*7.32)  END \n" +
"                                         as horaspagadas from manufactura as m INNER JOIN codigos as c ON m.IDCODIGO=c.IDCODIGO where  c.CADENA=4  GROUP BY c.TURNO) as hcpag WHERE hcpag.TURNO=corte.TURNO");
                        //  + "SELECT DISTINCT\n" +
//"                corte.idcodigo,\n" +
//"                hcpag.IDCODIGO, hcpag.HCDIRLINEA, corte.hrsEMBC, hcpag.horaspagadas, hcpag.TURNO, truncate((corte.hrsEMBC/hcpag.horaspagadas)*100,2) as eficiencia \n" +
//"                from \n" +
//"                (select c.IDCODIGO, c.LINEA, c.CODIGO, c.TURNO, m.SALIDAENPIEZA, g.HCDIRCORTE as cortee,  SUM(TRUNCATE((((g.HCDIRCORTE*m.SALIDAENPIEZA)/100)),2)) as hrsEMBC,   g.HCDIRLPS as 'POND.LPS', TRUNCATE( if(c.TURNO='B' or c.TURNO='C', ((g.HCDIRLPS*m.SALIDAENPIEZA)/7.9/100),((g.HCDIRLPS *m.SALIDAENPIEZA)/9/100)) ,2) as LPS, g.HCDIRENSFINAL as 'PUNTOS.EF', TRUNCATE( if(c.TURNO='B' or c.TURNO='C',((g.HCDIRENSFINAL*m.SALIDAENPIEZA)/7.9/100), ((g.HCDIRENSFINAL *m.SALIDAENPIEZA)/9/100)),2) as ENSFINAL  FROM codigos as c, manufactura as m, gsd  as g where c.IDCODIGO=m.IDCODIGO and c.IDCODIGO=g.IDCODIGO and m.activo=1   GROUP BY c.TURNO) as corte,\n" +
//"                (select c.IDCODIGO, HCDIRLINEA, C.TURNO, "+
//                     "case c.TURNO when'B' THEN SUM((M.HCDIRLPS+M.HCDIRSOPLPS +M.HCDIRLINEA+ m.hcdirconte+m.HCDIRSOPORTE+m.HCDIRTABINSP+m.hcrutasint+m.hcdirpilotos+m.hcdirftq+ m.hcdirsistemas +m.elinea+ m.estaciones+m.kits)*7.9) "
//                     + " WHEN 'A' THEN sum((M.HCDIRLPS+M.HCDIRSOPLPS +M.HCDIRLINEA+ m.hcdirconte+m.HCDIRSOPORTE+m.HCDIRTABINSP+m.hcrutasint+m.hcdirpilotos+m.hcdirftq+ m.hcdirsistemas+m.elinea+ m.estaciones+m.kits)*9) "+
//                      " WHEN 'C' THEN sum((M.HCDIRLPS+M.HCDIRSOPLPS +M.HCDIRLINEA+ m.hcdirconte+m.HCDIRSOPORTE+m.HCDIRTABINSP+m.hcrutasint+m.hcdirpilotos+m.hcdirftq+ m.hcdirsistemas+m.elinea+ m.estaciones+m.kits)*6.1)  END "+
//                     "as horaspagadas from manufactura as m, codigos as c where m.IDCODIGO=c.IDCODIGO and c.CADENA=4  GROUP BY c.TURNO) as hcpag ");
             int cont=1; 
                while(rs.next())
               {
                if(cont==1)
                { 
                    HrsEmbCorteA=Regresa2Decimales( rs.getDouble("hrsEMBC"));
                    HrsPagCorteA=Regresa2Decimales(rs.getDouble("horaspagadas"));
                    EficCorteA=Regresa2Decimales(rs.getDouble("eficiencia"));
                }
                if(cont==2)
                {
                    HrsEmbCorteB=Regresa2Decimales(rs.getDouble("hrsEMBC"));
                    HrsPagCorteB=Regresa2Decimales(rs.getDouble("horaspagadas"));
                    EficCorteB=Regresa2Decimales(rs.getDouble("eficiencia"));
                }
                //turno c
//                if(cont==5)
//                {
//                    HrsEmbCorteC=Regresa2Decimales(rs.getDouble("hrsEMBC"));
//                    HrsPagCorteC=Regresa2Decimales(rs.getDouble("horaspagadas"));
//                    EficCorteC=Regresa2Decimales(rs.getDouble("eficiencia"));
//                }
                cont++;
               }
                ///REPORTE EFICIENCIA TOTAL EXCEL
              EficIA=( HrsEMBIA/HrsPagIA);
              EficIIA=(HrsEMBIIA/HrsPagIIA);
              EficIIIA=(HrsEMBIIIA/HrsPagIIIA);
              EficVIA=(HrsEMBVIA/HrsPagVIA);
              EficVIIIA=(HrsEMBVIIIA/HrsPagVIIIA);
              
              
              EficIB=( HrsEMBIB/HrsPagIB);
              EficIIB=(HrsEMBIIB/HrsPagIIB);
              EficIIIB=(HrsEMBIIIB/HrsPagIIIB);
              EficVIB=(HrsEMBVIB/HrsPagVIB);
                EficVIIIB=(HrsEMBVIIIB/HrsPagVIIIB);
              
             
              HrsPagIA=Regresa2Decimales(HrsPagIA);
              HrsPagIIA=Regresa2Decimales(HrsPagIIA);
              HrsPagIIIA=Regresa2Decimales(HrsPagIIIA);
              HrsPagVIA=Regresa2Decimales(HrsPagVIA);
              HrsPagVIIIB=Regresa2Decimales(HrsPagVIIIB);
              
              HrsEMBIA=Regresa2Decimales(HrsEMBIA);
              HrsEMBIIA=Regresa2Decimales(HrsEMBIIA);
              HrsEMBIIIA=Regresa2Decimales(HrsEMBIIIA);
              HrsEMBVIA=Regresa2Decimales(HrsEMBVIA);
              HrsEMBVIIIA=Regresa2Decimales(HrsEMBVIIIA);

              HrsEMBIB=Regresa2Decimales(HrsEMBIB);
              HrsEMBIIB=Regresa2Decimales(HrsEMBIIB);
              HrsEMBIIIB=Regresa2Decimales(HrsEMBIIIB);
              HrsEMBVIB=Regresa2Decimales(HrsEMBVIB);
              HrsEMBVIIIB=Regresa2Decimales(HrsEMBVIIIB);
           
             
              HrsPagIB=Regresa2Decimales(HrsPagIB);
              HrsPagIIB=Regresa2Decimales(HrsPagIIB);
              HrsPagIIIB=Regresa2Decimales(HrsPagIIIB);
              HrsPagVIB=Regresa2Decimales(HrsPagVIB);
              HrsPagVIIIB=Regresa2Decimales(HrsPagVIIIB);
           
              EficIA=Regresa2Decimales(EficIA);
              EficIIA=Regresa2Decimales(EficIIA);
              EficIIIA=Regresa2Decimales(EficIIIA);
              EficVIA=Regresa2Decimales(EficVIA);
              EficVIIIA=Regresa2Decimales(EficVIIIA);
              
              EficIB=Regresa2Decimales(EficIB);
              EficIIB=Regresa2Decimales(EficIIB);
              EficIIIB=Regresa2Decimales(EficIIIB);
              EficVIB=Regresa2Decimales(EficVIB);
              EficVIIIB=Regresa2Decimales(EficVIIIB);
              
              EficServiciosA=Regresa2Decimales(HorasEmbServicios/(ServiciosA*9));
               EficServiciosB=Regresa2Decimales(HorasEmbServiciosB/(ServiciosB*7.9));
              rs=Principal.cn.GetConsulta("select sum(hcagregada) as pza, cadena, turno  from contigencias as cont inner join codigos as cod on cont.idcodigo=cod.idcodigo group by cadena, turno ");
              while(rs.next())
              {
                  if(rs.getString("cadena").equals("1") && (rs.getString("turno").equals("A")))
                      ContIA=rs.getDouble("pza");
                  if(rs.getString("cadena").equals("1") && (rs.getString("turno").equals("B")))
                      ContIB=rs.getDouble("pza");
                  if(rs.getString("cadena").equals("2") && (rs.getString("turno").equals("A")))
                      ContIIA=rs.getDouble("pza");
                  if(rs.getString("cadena").equals("2") && (rs.getString("turno").equals("B")))
                      ContIIB=rs.getDouble("pza");
                  if(rs.getString("cadena").equals("3") && (rs.getString("turno").equals("A")))
                      ContIIIA=rs.getDouble("pza");
                  if(rs.getString("cadena").equals("3") && (rs.getString("turno").equals("B")))
                      ContIIIB=rs.getDouble("pza");
                  if(rs.getString("cadena").equals("4") && (rs.getString("turno").equals("A")))
                      ContIVA=rs.getDouble("pza");
                  if(rs.getString("cadena").equals("4") && (rs.getString("turno").equals("B")))
                      ContIVB=rs.getDouble("pza");
                  if(rs.getString("cadena").equals("6") && (rs.getString("turno").equals("A")))
                      ContVIA=rs.getDouble("pza");
                  if(rs.getString("cadena").equals("6") && (rs.getString("turno").equals("B")))
                      ContVIB=rs.getDouble("pza");
                  if(rs.getString("cadena").equals("5") && (rs.getString("turno").equals("A")))
                      ContVIIIA=rs.getDouble("pza");
                  if(rs.getString("cadena").equals("5") && (rs.getString("turno").equals("B")))
                      ContVIIIB=rs.getDouble("pza");
                  if(rs.getString("cadena").equals("4") && (rs.getString("turno").equals("C")))
                      ContIVC=rs.getDouble("pza");
                          
              }
              
              //******** aqui se le mueve el porcentaje de contigencia en reporte de excel pestaña Eficiencia Total ********
              
             //  modelo.setColumnIdentifiers(new Object[]{"CADENA", "TURNO", "HC NEC", "HORAS EMB.", "HORAS PAG", "EFICIENCIA", " HC CONT"," HC TOTAL"});
            // modelo.addRow(new Object[]{"1", "A", (int)(HrsPagIA/9) ,HrsEMBIA,  HrsPagIA,  EficIA,((double)Math.round((HrsPagIA/9)*(0.20)) )+ContIA,(int)(HrsPagIA/9)+ ContIA});
            // modelo.addRow(new Object[]{"2", "A", (int)(HrsPagIIA/9), HrsEMBIIA, HrsPagIIA, EficIIA, ((double)Math.round((HrsPagIIA/9)*(0.26))+ContIIA),(int)(HrsPagIIA/9)+ ContIIA});
            // modelo.addRow(new Object[]{"LPS", "A",(int)(HrsPagIIIA/9),  HrsEMBIIIA,  HrsPagIIIA,  EficIIIA, (double)Math.round((HrsPagIIIA/9)*(0.19)+ContIIIA),(int)(HrsPagIIIA/9)+ ContIIIA});
            // modelo.addRow(new Object[]{"4", "A", (int)(HrsPagCorteA/9),  HrsEmbCorteA,  HrsPagCorteA,  EficCorteA, ((double)Math.round((HrsPagCorteA/9)*(0.20))+ContIVA),(int)(HrsPagCorteA/9)+ ContIVA });
            // modelo.addRow(new Object[]{"5", "A", (int)(HrsPagVIIIA/9), HrsEMBVIIIA, HrsPagVIIIA, EficVIIIA, (double)Math.round((HrsPagVIIIA/9)*(0.15))+ContVIIIA,(int)(HrsPagVIIIA/9)+ ContVIIIA});
            // modelo.addRow(new Object[]{"6", "A", (int)(HrsPagVIA/9), HrsEMBVIA, HrsPagVIA, EficVIA, (double)Math.round((HrsPagVIA/9)*(0.07))+ContVIA ,(int)(HrsPagVIA/9)+ContVIA });
           // modelo.addRow(new Object[]{"8", "A", (int)(HrsPagVIA/9), HrsEMBVIA, HrsPagVIA, EficVIA, (double)Math.round((HrsPagVIA/9)*(0.15))+ContVIA  });

            // modelo.addRow(new Object[]{"1", "B", (double)Math.round(HrsPagIB/7.9),   HrsEMBIB, HrsPagIB,  EficIB, (double)Math.round((HrsPagIB/7.9)*(0.1577))+ContIB,(double)Math.round(HrsPagIB/7.9)+ ContIB});
            // modelo.addRow(new Object[]{"2", "B", (double)Math.round(HrsPagIIB/7.9),  HrsEMBIIB,  HrsPagIIB, EficIIB, (double)Math.round((HrsPagIIB/7.9)*(0.2174))+ContIIB,(double)Math.round(HrsPagIIB/7.9)+ ContIIB});
            // modelo.addRow(new Object[]{"LPS", "B",  (double)Math.round(HrsPagIIIB/7.9), HrsEMBIIIB,  HrsPagIIIB,  EficIIIB, (double)Math.round((HrsPagIIIB/7.9)*(0.1873)+ContIIIB),(double)Math.round(HrsPagIIIB/7.9)+ ContIIIB});
            // modelo.addRow(new Object[]{"4", "B", (double)Math.round(HrsPagCorteB/7.9), HrsEmbCorteB,  HrsPagCorteB,  EficCorteB,(double)Math.round((HrsPagCorteB/7.9)*(0.1983))+ContIVB,(double)Math.round(HrsPagCorteB/7.9)+ ContIVB });
           // modelo.addRow(new Object[]{"5", "B", (double)Math.round(HrsPagVIIIB/7.9), HrsEMBVIIIB, HrsPagVIIIB, EficVIIIB, (double)Math.round((HrsPagVIIIB/7.9)*(0.1688))+ContVIIIB,(double)Math.round(HrsPagVIIIB/7.9)+ ContVIIIB});
           //  modelo.addRow(new Object[]{"6", "B", (double)Math.round(HrsPagVIB/7.9), HrsEMBVIB, HrsPagVIB, EficVIB, (double)Math.round((HrsPagVIB/7.9)*(0.07))+ContVIB,(double)Math.round(HrsPagVIB/7.9)+ ContVIB});
           ///  modelo.addRo
              
              
              
             modelo.setColumnIdentifiers(new Object[]{"CADENA", "TURNO", "HC NEC", "HORAS EMB.", "HORAS PAG", "EFICIENCIA", " HC CONT"});
             modelo.addRow(new Object[]{"1", "A", (int)(HrsPagIA/9) ,HrsEMBIA+HorasEmbServicios,  HrsPagIA,  EficIA,((double)Math.round((HrsPagIA/9)*(0.144)) )+ContIA});
             modelo.addRow(new Object[]{"2", "A", (int)(HrsPagIIA/9), HrsEMBIIA, HrsPagIIA, EficIIA, ((double)Math.round((HrsPagIIA/9)*(0.148))+ContIIA)});
             modelo.addRow(new Object[]{"LPS","A",(int)(HrsPagIIIA/9),  HrsEMBIIIA,  HrsPagIIIA,  EficIIIA, (double)Math.round((HrsPagIIIA/9)*(0.131)+ContIIIA)});
             modelo.addRow(new Object[]{"4", "A", (int)(HrsPagCorteA/9),  HrsEmbCorteA,  HrsPagCorteA,  EficCorteA, ((double)Math.round((HrsPagCorteA/9)*(0.122))+ContIVA)});
           //modelo.addRow(new Object[]{"5", "A", (int)(HrsPagVIIIA/9), HrsEMBVIIIA, HrsPagVIIIA, EficVIIIA, (double)Math.round((HrsPagVIIIA/9)*(0.1849))+ContVIIIA});
             //modelo.addRow(new Object[]{"6", "A", (int)(HrsPagVIA/9), HrsEMBVIA, HrsPagVIA, EficVIA, (double)Math.round((HrsPagVIA/9)*(0.07))+ContVIA});
           //modelo.addRow(new Object[]{"8", "A", (int)(HrsPagVIA/9), HrsEMBVIA, HrsPagVIA, EficVIA, (double)Math.round((HrsPagVIA/9)*(0.15))+ContVIA  });
            
             modelo.addRow(new Object[]{"1", "B", (double)Math.round(HrsPagIB/7.9),   HrsEMBIB+HorasEmbServiciosB, HrsPagIB,  EficIB, (double)Math.round((HrsPagIB/7.9)*(0.145))+ContIB});
             modelo.addRow(new Object[]{"2", "B", (double)Math.round(HrsPagIIB/7.9),  HrsEMBIIB,  HrsPagIIB, EficIIB, (double)Math.round((HrsPagIIB/7.9)*(0.125))+ContIIB});
             modelo.addRow(new Object[]{"LPS","B",(double)Math.round(HrsPagIIIB/7.9), HrsEMBIIIB,  HrsPagIIIB,  EficIIIB, (double)Math.round((HrsPagIIIB/7.9)*(0.147)+ContIIIB)});
             modelo.addRow(new Object[]{"4", "B", (double)Math.round(HrsPagCorteB/7.9), HrsEmbCorteB,  HrsPagCorteB,  EficCorteB,(double)Math.round((HrsPagCorteB/7.9)*(0.165))+ContIVB});
          // modelo.addRow(new Object[]{"5", "B", (double)Math.round(HrsPagVIIIB/7.9), HrsEMBVIIIB, HrsPagVIIIB, EficVIIIB, (double)Math.round((HrsPagVIIIB/7.9)*(0.1285))+ContVIIIB});
            // modelo.addRow(new Object[]{"6", "B", (double)Math.round(HrsPagVIB/7.9), HrsEMBVIB, HrsPagVIB, EficVIB, (double)Math.round((HrsPagVIB/7.9)*(0.07))+ContVIB});
           ///  modelo.addRow(new Object[]{"8", "B", (double)Math.round(HrsPagVIB/7.9), HrsEMBVIB, HrsPagVIB, EficVIB, (double)Math.round((HrsPagVIB/7.9)*(0.15))+ContVIB  });

            // modelo.addRow(new Object[]{"4", "C", 0, 0,  0,  0,0 });
                         // modelo.addRow(new Object[]{"4", "C", (double)Math.round(HrsPagCorteC/7.32), HrsEmbCorteC,  HrsPagCorteC,  EficCorteC,(double)Math.round((HrsPagCorteC/7.9)*(0.07))+ContIVC });
             
             double ExtrasGA= kachirulesA/(kachirulesA*9);
             double ExtrasGB= kachirulesB/(kachirulesB*7.9);
              
          modelo.addRow(new Object[]{"EXTRAS GERENCIA",  "A", kachirulesA,0,  kachirulesA*9,   ExtrasGA, 0});
           modelo.addRow(new Object[]{"EXTRAS GERENCIA",  "B", kachirulesB,0,  kachirulesB*7.9, ExtrasGB, 0});
             
             //activar cuando selene te pida que no quiere ver kachirules en el excel pestana Eficiencia total
         // modelo.addRow(new Object[]{"EXTRAS GERENCIA",  "A", 0,0, 0, 0, 0});
           //modelo.addRow(new Object[]{"EXTRAS GERENCIA",  "B",0,0,  0, 0, 0});
           //  modelo.addRow(new Object[]{"SERVICIOS",  "A", ServiciosA, HorasEmbServicios,  ServiciosA*9,  EficServiciosA, 0});
             // modelo.addRow(new Object[]{"SERVICIOS",  "B", ServiciosB, HorasEmbServiciosB,  ServiciosB*7.9,  EficServiciosB, 0});
                       
                        
            //ExcelEficiencia excel=new ExcelEficiencia();
            //  excel.GetExceltotalPlantaJorge(modelo, "EFICIENCIA PLANTA");
          }catch(Exception e )
          {
            System.out.println(e.toString()+"get reportejorge");
          }
         return modelo;
      }
          
     public void GetRepDistGente(){
         try{
             DefaultTableModel modeloA=new DefaultTableModel();
             DefaultTableModel modeloB=new DefaultTableModel();
            // DefaultTableModel modelo=new DefaultTableModel();
              modeloA.setColumnIdentifiers(new Object[]{ "LINEA", "CODIGO", "TURNO", "CONV & POST& KITS", "CONTENSION", "SOPORTE", "TAB.INSP", "RUTAS.INT", "LPS", "SOP.LPS", "PILOTOS", "FTQ",  "SISTEMAS", "RUT.SUT."});
             ResultSet rs=Principal.cn.GetConsulta("SELECT \n" +
                "CAST(codigos.linea as UNSIGNED) as linea,  CODIGOS.CODIGO, codigos.TURNO,\n" +
                " SUM(MANUFACTURA.HCDIRLINEA+elinea+ estaciones+kits) AS HCLINEA, sum(manufactura.hcdirsoplps) as SopLPS, sum(manufactura.hcdirsoporte) as soporte,   Sum(manufactura.HCDIRLPS) as LPS, SUM(manufactura.HCDIRCONTE) AS CONTENSION, SUM(manufactura.HCDIRFTQ) AS FTQ, SUM(manufactura.HCDIRPILOTOS) AS PILOTO,\n" +
                " sum(manufactura.HCDIRSOPORTE) AS SOPORTE, SUM(manufactura.hcdirsistemas) as sistemas, \n" +
                "SUM(manufactura.HCDIRTABINSP) AS 'TAB.INSP', SUM(MANUFACTURA.HCRUTASINT) AS RUTASINTERNAS,\n" +
                "SUM(manufactura.HCINDRUTAS) AS RUTAS \n" +
                "FROM\n" +
                "manufactura ,\n" +
                "codigos\n" +
                "WHERE\n" +
                "manufactura.IDCODIGO = codigos.IDCODIGO AND MANUFACTURA.ACTIVO=1 and linea<>91 and turno='A' \n" +
                "GROUP BY CODIGOS.IDCODIGO");
             while(rs.next())
             {
               modeloA.addRow(new Object[]{ rs.getString("LINEA"), rs.getString("CODIGO"), rs.getString("turno"), rs.getString("HCLINEA"), rs.getString("contension"),  rs.getString("soporte"), rs.getString("tab.insp"), rs.getString("rutasinternas"), rs.getString("lps"), rs.getString("SopLPS"), rs.getString("piloto"), rs.getString("ftq"), rs.getString("sistemas"), rs.getString("rutas")});
             }
                    modeloB.setColumnIdentifiers(new Object[]{ "LINEA", "CODIGO", "TURNO", "CONV & POST& KITS", "CONTENSION", "SOPORTE", "TAB.INSP", "RUTAS.INT", "LPS", "SOP.LPS", "PILOTOS", "FTQ",  "SISTEMAS", "RUT.SUT."});
              rs=Principal.cn.GetConsulta("SELECT \n" +
                "CAST(codigos.linea as UNSIGNED) as linea,  CODIGOS.CODIGO, codigos.TURNO,\n" +
                " SUM(MANUFACTURA.HCDIRLINEA+elinea+ estaciones+kits) AS HCLINEA, sum(manufactura.hcdirsoplps) as SopLPS, sum(manufactura.hcdirsoporte) as soporte,   Sum(manufactura.HCDIRLPS) as LPS, SUM(manufactura.HCDIRCONTE) AS CONTENSION, SUM(manufactura.HCDIRFTQ) AS FTQ, SUM(manufactura.HCDIRPILOTOS) AS PILOTO,\n" +
                " sum(manufactura.HCDIRSOPORTE) AS SOPORTE, SUM(manufactura.hcdirsistemas) as sistemas, \n" +
                "SUM(manufactura.HCDIRTABINSP) AS 'TAB.INSP', SUM(MANUFACTURA.HCRUTASINT) AS RUTASINTERNAS,\n" +
                "SUM(manufactura.HCINDRUTAS) AS RUTAS \n" +
                "FROM\n" +
                "manufactura ,\n" +
                "codigos\n" +
                "WHERE\n" +
                "manufactura.IDCODIGO = codigos.IDCODIGO AND MANUFACTURA.ACTIVO=1 and linea<>91 and turno='B' \n" +
                "GROUP BY CODIGOS.IDCODIGO");
             while(rs.next())
             {
               modeloB.addRow(new Object[]{ rs.getString("LINEA"), rs.getString("CODIGO"), rs.getString("turno"), rs.getString("HCLINEA"), rs.getString("contension"),  rs.getString("soporte"), rs.getString("tab.insp"), rs.getString("rutasinternas"), rs.getString("lps"), rs.getString("SopLPS"), rs.getString("piloto"), rs.getString("ftq"), rs.getString("sistemas"), rs.getString("rutas")});
             }
            
             DefaultTableModel modeloTotales=new  DefaultTableModel();
             modeloTotales.setColumnIdentifiers(new Object[]{ "LINEA", "CODIGO", "TURNO", "CONV & POST& KITS", "CONTENSION", "SOPORTE", "TAB.INSP", "RUTAS.INT", "LPS", "SOP.LPS", "PILOTOS", "FTQ",  "SISTEMAS", "RUT.SUT."});
              rs= Principal.cn.GetConsulta("SELECT\n" +
                "CAST(codigos.linea as UNSIGNED) as linea,  CODIGOS.CODIGO, codigos.TURNO,\n" +
                "SUM(MANUFACTURA.HCDIRLINEA+elinea+ estaciones+kits) AS HCLINEA, sum(manufactura.hcdirsoplps) as SopLPS, sum(manufactura.hcdirsoporte) as soporte,   Sum(manufactura.HCDIRLPS) as LPS, SUM(manufactura.HCDIRCONTE) AS CONTENSION, SUM(manufactura.HCDIRFTQ) AS FTQ, SUM(manufactura.HCDIRPILOTOS) AS PILOTO,\n" +
                "sum(manufactura.HCDIRSOPORTE) AS SOPORTE, SUM(manufactura.hcdirsistemas) as sistemas, \n" +
                "SUM(manufactura.HCDIRTABINSP) AS 'TAB.INSP', SUM(MANUFACTURA.HCRUTASINT) AS RUTASINTERNAS,\n" +
                "SUM(manufactura.HCINDRUTAS) AS RUTAS \n" +
                "FROM\n" +
                "manufactura ,\n" +
                "codigos\n" +
                "WHERE\n" +
                "manufactura.IDCODIGO = codigos.IDCODIGO AND MANUFACTURA.ACTIVO=1 AND linea<>91 \n" +
                "GROUP BY CODIGOS.turno");
               while(rs.next())
             {
               modeloTotales.addRow(new Object[]{ "", "TURNO", rs.getString("turno"), rs.getString("HCLINEA"), rs.getString("contension"),  rs.getString("soporte"), rs.getString("tab.insp"), rs.getString("rutasinternas"), rs.getString("lps"), rs.getString("SopLPS"), rs.getString("piloto"), rs.getString("ftq"), rs.getString("sistemas"), rs.getString("rutas")});
             }
                rs= Principal.cn.GetConsulta("SELECT\n" +
                "CAST(codigos.linea as UNSIGNED) as linea,  CODIGOS.CODIGO, codigos.TURNO,\n" +
           " SUM(MANUFACTURA.HCDIRLINEA+elinea+ estaciones+kits) AS HCLINEA, sum(manufactura.hcdirsoplps) as SopLPS, sum(manufactura.hcdirsoporte) as soporte,   Sum(manufactura.HCDIRLPS) as LPS, SUM(manufactura.HCDIRCONTE) AS CONTENSION, SUM(manufactura.HCDIRFTQ) AS FTQ, SUM(manufactura.HCDIRPILOTOS) AS PILOTO,\n" +
                " sum(manufactura.HCDIRSOPORTE) AS SOPORTE, SUM(manufactura.hcdirsistemas) as sistemas, \n" +
                "SUM(manufactura.HCDIRTABINSP) AS 'TAB.INSP', SUM(MANUFACTURA.HCRUTASINT) AS RUTASINTERNAS,\n" +
                "SUM(manufactura.HCINDRUTAS) AS RUTAS \n" +
                "FROM\n" +
                "manufactura ,\n" +
                "codigos\n" +
                "WHERE\n" +
                "manufactura.IDCODIGO = codigos.IDCODIGO AND MANUFACTURA.ACTIVO=1 AND linea<>91 ");
                if(rs.next())
                {
                    modeloTotales.addRow(new Object[]{ "", "PLANTA", "", rs.getString("HCLINEA"), rs.getString("contension"),  rs.getString("soporte"), rs.getString("tab.insp"), rs.getString("rutasinternas"), rs.getString("lps"), rs.getString("SopLPS"), rs.getString("piloto"), rs.getString("ftq"), rs.getString("sistemas"), rs.getString("rutas")});
                }
             ExcelDistriHCPorCodigo excel=new ExcelDistriHCPorCodigo();
                     excel.GetDistriHCPorCodigo(modeloA, modeloB, modeloTotales, "HC");
             
         }catch(Exception e)
         {
             System.out.println(e.toString());
         }
     }
     
     
     
     
      public void REPORTEPORPLATAFORMAS(){
         try{
             DefaultTableModel GMT610=new DefaultTableModel();
             DefaultTableModel K2XX=new DefaultTableModel();
            // DefaultTableModel modelo=new DefaultTableModel();
              GMT610.setColumnIdentifiers(new Object[]{ "PLATAFORMA", "CODIGO", "CADENA", "LINEA", "TURNO", "HC", "HORASEMB", "HRSPAGADAS", "EFIC"});
             ResultSet rs=Principal.cn.GetConsulta("select DISTINCT manu.plataforma, manu.codigo,manu.CADENA,manu.LINEA, manu.turno, manu.hc,IF(manu.LINEA='CORTE' AND manu.CADENA=6,cortehrsemb.hrsEMBC,manu.horasemb)as horasemb, (manu.hrsPagadas) as hrsPagadas,ROUND(IF(manu.LINEA='CORTE' AND manu.CADENA=6,(cortehrsemb.hrsEMBC/manu.hrsPagadas)*100,(manu.horasemb/manu.hrsPagadas)*100),2)as efic   from \n" +
"(select c.plataforma,c.CODIGO,c.arnes,c.LINEA, c.CADENA, c.turno, sum(m.HCDIRLINEA) as hc,round( sum((m.PUNTOSPZAPOND*m.SALIDAENPIEZA/100)),2) as horasemb,  IF(c.TURNO='A', sum(((elinea+estaciones+ kits+HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*9)),\n" +
"sum(((elinea+estaciones+ kits+ HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*7.9))) as hrsPagadas, m.activo  from manufactura as m, codigos as c where m.idcodigo=c.IDCODIGO and c.CADENA<>4  and c.TURNO='A' and c.PLATAFORMA='GMT 610' GROUP BY c.idcodigo order by c.PLATAFORMA) as manu ,\n" +
"(SELECT SUM(TRUNCATE((((g.HCDIRCORTE*m.SALIDAENPIEZA)/100)),2)) as hrsEMBC FROM gsd as g, manufactura as m, codigos as c where c.IDCODIGO=m.IDCODIGO and c.IDCODIGO=g.IDCODIGO and m.activo=1 AND C.CADENA=6 and c.TURNO='A' GROUP BY c.TURNO order by c.PLATAFORMA) as cortehrsemb\n" +
"where manu.CADENA<>4 and manu.activo<>0  UNION select DISTINCT manu.plataforma,manu.codigo,manu.CADENA,manu.LINEA, manu.turno, manu.hc,IF(manu.LINEA='CORTE' AND manu.CADENA=6,cortehrsemb.hrsEMBC,manu.horasemb)as horasemb, (manu.hrsPagadas) as hrsPagadas,ROUND(IF(manu.LINEA='CORTE' AND manu.CADENA=6,(cortehrsemb.hrsEMBC/manu.hrsPagadas)*100,(manu.horasemb/manu.hrsPagadas)*100),2)as efic   from \n" +
"(select c.plataforma,c.CODIGO,c.arnes,c.LINEA, c.CADENA, c.turno, sum(m.HCDIRLINEA) as hc,round( sum((m.PUNTOSPZAPOND*m.SALIDAENPIEZA/100)),2) as horasemb,  IF(c.TURNO='A', sum(((elinea+estaciones+ kits+HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*9)),\n" +
"sum(((elinea+estaciones+ kits+ HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*7.9))) as hrsPagadas, m.activo  from manufactura as m, codigos as c where m.idcodigo=c.IDCODIGO and c.CADENA<>4 and c.TURNO='B' and c.PLATAFORMA='GMT 610' GROUP BY c.idcodigo order by c.PLATAFORMA) as manu,\n" +
"(SELECT SUM(TRUNCATE((((g.HCDIRCORTE*m.SALIDAENPIEZA)/100)),2)) as hrsEMBC FROM gsd as g, manufactura as m, codigos as c where c.IDCODIGO=m.IDCODIGO and c.IDCODIGO=g.IDCODIGO and m.activo=1 and c.TURNO='B'  GROUP BY c.TURNO order by c.PLATAFORMA) as cortehrsemb\n" +
"where  manu.activo<>0");
             while(rs.next())
             {
               GMT610.addRow(new Object[]{ rs.getString("PLATAFORMA"), rs.getString("CODIGO"), rs.getString("CADENA"), rs.getString("LINEA"), rs.getString("TURNO"),  rs.getString("HC"), rs.getString("HORASEMB"), rs.getString("HRSPAGADAS"), rs.getString("EFIC")});
             }
              K2XX.setColumnIdentifiers(new Object[]{ "PLATAFORMA", "CODIGO", "CADENA", "LINEA", "TURNO", "HC", "HORASEMB", "HRSPAGADAS", "EFIC"});
              rs=Principal.cn.GetConsulta("select DISTINCT manu.plataforma, manu.codigo,manu.CADENA,manu.LINEA, manu.turno, manu.hc,IF(manu.LINEA='CORTE' AND manu.CADENA=6,cortehrsemb.hrsEMBC,manu.horasemb)as horasemb, (manu.hrsPagadas) as hrsPagadas,ROUND(IF(manu.LINEA='CORTE' AND manu.CADENA=6,(cortehrsemb.hrsEMBC/manu.hrsPagadas)*100,(manu.horasemb/manu.hrsPagadas)*100),2)as efic   from \n" +
"(select c.plataforma,c.CODIGO,c.arnes,c.LINEA, c.CADENA, c.turno, sum(m.HCDIRLINEA) as hc,round( sum((m.PUNTOSPZAPOND*m.SALIDAENPIEZA/100)),2) as horasemb,  IF(c.TURNO='A', sum(((elinea+estaciones+ kits+HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*9)),\n" +
"sum(((elinea+estaciones+ kits+ HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*7.9))) as hrsPagadas, m.activo  from manufactura as m, codigos as c where m.idcodigo=c.IDCODIGO and c.CADENA<>4  and c.TURNO='A' and c.PLATAFORMA='K2XX' GROUP BY c.idcodigo order by c.PLATAFORMA) as manu ,\n" +
"(SELECT SUM(TRUNCATE((((g.HCDIRCORTE*m.SALIDAENPIEZA)/100)),2)) as hrsEMBC FROM gsd as g, manufactura as m, codigos as c where c.IDCODIGO=m.IDCODIGO and c.IDCODIGO=g.IDCODIGO and m.activo=1 AND C.CADENA=6 and c.TURNO='A' GROUP BY c.TURNO order by c.PLATAFORMA) as cortehrsemb\n" +
"where manu.CADENA<>4 and manu.activo<>0  UNION select DISTINCT manu.plataforma,manu.codigo,manu.CADENA,manu.LINEA, manu.turno, manu.hc,IF(manu.LINEA='CORTE' AND manu.CADENA=6,cortehrsemb.hrsEMBC,manu.horasemb)as horasemb, (manu.hrsPagadas) as hrsPagadas,ROUND(IF(manu.LINEA='CORTE' AND manu.CADENA=6,(cortehrsemb.hrsEMBC/manu.hrsPagadas)*100,(manu.horasemb/manu.hrsPagadas)*100),2)as efic   from \n" +
"(select c.plataforma,c.CODIGO,c.arnes,c.LINEA, c.CADENA, c.turno, sum(m.HCDIRLINEA) as hc,round( sum((m.PUNTOSPZAPOND*m.SALIDAENPIEZA/100)),2) as horasemb,  IF(c.TURNO='A', sum(((elinea+estaciones+ kits+HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*9)),\n" +
"sum(((elinea+estaciones+ kits+ HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*7.9))) as hrsPagadas, m.activo  from manufactura as m, codigos as c where m.idcodigo=c.IDCODIGO and c.CADENA<>4 and c.TURNO='B' and c.PLATAFORMA='K2XX' GROUP BY c.idcodigo order by c.PLATAFORMA) as manu,\n" +
"(SELECT SUM(TRUNCATE((((g.HCDIRCORTE*m.SALIDAENPIEZA)/100)),2)) as hrsEMBC FROM gsd as g, manufactura as m, codigos as c where c.IDCODIGO=m.IDCODIGO and c.IDCODIGO=g.IDCODIGO and m.activo=1  and c.TURNO='B'  GROUP BY c.TURNO order by c.PLATAFORMA) as cortehrsemb\n" +
"where  manu.activo<>0");
             while(rs.next())
             {
               K2XX.addRow(new Object[]{ rs.getString("PLATAFORMA"), rs.getString("CODIGO"), rs.getString("CADENA"), rs.getString("LINEA"), rs.getString("TURNO"),  rs.getString("HC"), rs.getString("HORASEMB"), rs.getString("HRSPAGADAS"), rs.getString("EFIC")});
             }
            
             DefaultTableModel modeloTotales=new  DefaultTableModel();
             modeloTotales.setColumnIdentifiers(new Object[]{ "LINEA", "CODIGO", "TURNO", "CONV & POST& KITS", "CONTENSION", "SOPORTE", "TAB.INSP", "RUTAS.INT", "LPS", "SOP.LPS", "PILOTOS", "FTQ",  "SISTEMAS", "RUT.SUT."});
              rs= Principal.cn.GetConsulta("SELECT\n" +
                "CAST(codigos.linea as UNSIGNED) as linea,  CODIGOS.CODIGO, codigos.TURNO,\n" +
                "SUM(MANUFACTURA.HCDIRLINEA+elinea+ estaciones+kits) AS HCLINEA, sum(manufactura.hcdirsoplps) as SopLPS, sum(manufactura.hcdirsoporte) as soporte,   Sum(manufactura.HCDIRLPS) as LPS, SUM(manufactura.HCDIRCONTE) AS CONTENSION, SUM(manufactura.HCDIRFTQ) AS FTQ, SUM(manufactura.HCDIRPILOTOS) AS PILOTO,\n" +
                "sum(manufactura.HCDIRSOPORTE) AS SOPORTE, SUM(manufactura.hcdirsistemas) as sistemas, \n" +
                "SUM(manufactura.HCDIRTABINSP) AS 'TAB.INSP', SUM(MANUFACTURA.HCRUTASINT) AS RUTASINTERNAS,\n" +
                "SUM(manufactura.HCINDRUTAS) AS RUTAS \n" +
                "FROM\n" +
                "manufactura ,\n" +
                "codigos\n" +
                "WHERE\n" +
                "manufactura.IDCODIGO = codigos.IDCODIGO AND MANUFACTURA.ACTIVO=1 AND linea<>91 \n" +
                "GROUP BY CODIGOS.turno");
               while(rs.next())
             {
               modeloTotales.addRow(new Object[]{ "", "TURNO", rs.getString("turno"), rs.getString("HCLINEA"), rs.getString("contension"),  rs.getString("soporte"), rs.getString("tab.insp"), rs.getString("rutasinternas"), rs.getString("lps"), rs.getString("SopLPS"), rs.getString("piloto"), rs.getString("ftq"), rs.getString("sistemas"), rs.getString("rutas")});
             }
                rs= Principal.cn.GetConsulta("SELECT\n" +
                "CAST(codigos.linea as UNSIGNED) as linea,  CODIGOS.CODIGO, codigos.TURNO,\n" +
           " SUM(MANUFACTURA.HCDIRLINEA+elinea+ estaciones+kits) AS HCLINEA, sum(manufactura.hcdirsoplps) as SopLPS, sum(manufactura.hcdirsoporte) as soporte,   Sum(manufactura.HCDIRLPS) as LPS, SUM(manufactura.HCDIRCONTE) AS CONTENSION, SUM(manufactura.HCDIRFTQ) AS FTQ, SUM(manufactura.HCDIRPILOTOS) AS PILOTO,\n" +
                " sum(manufactura.HCDIRSOPORTE) AS SOPORTE, SUM(manufactura.hcdirsistemas) as sistemas, \n" +
                "SUM(manufactura.HCDIRTABINSP) AS 'TAB.INSP', SUM(MANUFACTURA.HCRUTASINT) AS RUTASINTERNAS,\n" +
                "SUM(manufactura.HCINDRUTAS) AS RUTAS \n" +
                "FROM\n" +
                "manufactura ,\n" +
                "codigos\n" +
                "WHERE\n" +
                "manufactura.IDCODIGO = codigos.IDCODIGO AND MANUFACTURA.ACTIVO=1 AND linea<>91 ");
                if(rs.next())
                {
                    modeloTotales.addRow(new Object[]{ "", "PLANTA", "", rs.getString("HCLINEA"), rs.getString("contension"),  rs.getString("soporte"), rs.getString("tab.insp"), rs.getString("rutasinternas"), rs.getString("lps"), rs.getString("SopLPS"), rs.getString("piloto"), rs.getString("ftq"), rs.getString("sistemas"), rs.getString("rutas")});
                }
             ExcelDistriHCPorCodigo excel=new ExcelDistriHCPorCodigo();
                     excel.GetDistriHCPorCodigo(GMT610, K2XX, modeloTotales, "HC");
             
         }catch(Exception e)
         {
             System.out.println(e.toString());
         }
     }
     
    
}
