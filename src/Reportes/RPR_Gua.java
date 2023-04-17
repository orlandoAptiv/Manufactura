/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Reportes;

import Clases.Conection;
import Clases.PlataformaGenteCorte;
//import com.sun.org.apache.xml.internal.resolver.helpers.PublicId;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.ScrollPane;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import manufactura.EficienciaPlanta;
import manufactura.Principal;

/**
 *
 * @author gzld6k
 */
public class RPR_Gua extends javax.swing.JFrame {

    /**
     * Creates new form RPR
     */
    //AREA DE VARIABLES GLOBALES 
    ArrayList<PlataformaGenteCorte> gente =new ArrayList<PlataformaGenteCorte>();
    public int valorAnteriorLoc=1;
    public double  totalcutmfg=0.0, totalSlp_gsd=0.0,totalSlp_msd=0.0,totalSlp_mfg=0.0,totalcutGSD=0.0, totalcutMSD=0.0;
    public  double  totalF_assemblyGsd=0.0,totalF_assemblyMsd=0.0,totalF_assemblyMfg=0.0;
    public String TCutporc_msd,TCutporc_mfg ,Tslporc_msd,Tslporc_gsd,Tslporc_mfg,TCutporc_gsd;
    public String Tassemblyporc_gsd ,Tassemblyporc_msd ,Tassemblyporc_mfg ;
    public  double GAP=0.0, GAP_TOTAL=0.0,t=0.0,c2=0.0, kachirules=0.0, kachi=0.0,kachirules2=0.0, sum=0,totalhcplataformas=0.0;
    int soporte=0, sop_lps=0, tab_ins=0, cont=0, ftq=0, sist=0,pilotos=0,rutas_int=0;
    private String TOTALES;
   
   
      
    DefaultTableModel modelo2=new DefaultTableModel();
      JTable tabla2=new JTable();
      
       //DefaultTableModel modelo4=new DefaultTableModel();
      // tabla4=new JTable();
        
      
       

//         
    public RPR_Gua() {
        try {
            initComponents();
            Principal.cn=new Conection();
            
            this.setPreferredSize(new Dimension(1400, 720));
            Image icon = new ImageIcon(getClass().getResource("/Images/competitors-icon.png")).getImage();
            setIconImage(icon);
            CrearPaneles();
            CrearPanelestotales();
            crearpanelTotalGAP();
            crearpanelesGAP();
             
            
        } catch (Exception ex) {
            System.out.println(ex.toString());
        } 
    }
      
    public void SacarTablaDesgloseCorte(String Plataforma, int index){
     DefaultTableModel modelo=null;
     int totalHCCorteManufA=0;
     Double totalhc=0.0;
     Double totalHrsEmbA=0.0;
     Double totalHrsEmbB=0.0;
     Double totalHCCorteManufB=0.0;
      
    try{
        
         
        //query que manda el valor del HCcorte
     ResultSet  rs=Principal.cn.GetConsulta("SELECT  codigos.CADENA,SUM(manufactura.HCDIRLINEA) as total FROM codigos ,manufactura WHERE codigos.CADENA = 4  AND codigos.IDCODIGO = manufactura.IDCODIGO ");
        while(rs.next())
        {
             //  totalHCCorteManufA=rs.getInt("total");
        }
              
        
        
        //query suma el HC de todas las plataformas
        rs=Principal.cn.GetConsulta  ("select SUM(totaleshc) AS total_plata FROM (SELECT plataforma ,SUM(MANUFACTURA.HCDIRLINEA) AS HCLINEA, sum(manufactura.hcdirsoplps) as SopLPS, sum(manufactura.hcdirsoporte) as soporte, Sum(manufactura.HCDIRLPS) as LPS, SUM(manufactura.HCDIRCONTE) AS CONTENSION, SUM(manufactura.HCDIRFTQ) AS FTQ, SUM(manufactura.HCDIRPILOTOS) AS PILOTO, SUM(manufactura.hcdirsistemas) as sistemas, SUM(manufactura.HCDIRTABINSP) AS 'TAB.INSP', SUM(MANUFACTURA.HCRUTASINT) AS RUTASINTERNAS, SUM(manufactura.HCDIRLINEA + manufactura.HCDIRSOPLPS + manufactura.HCDIRSOPORTE + manufactura.HCDIRLPS + manufactura.HCDIRCONTE + manufactura.HCDIRFTQ + manufactura.HCDIRPILOTOS + manufactura.HCDIRSISTEMAS + manufactura.HCDIRTABINSP + manufactura.HCRUTASINT) AS totaleshc FROM manufactura,codigos WHERE manufactura.IDCODIGO = codigos.IDCODIGO AND MANUFACTURA.ACTIVO=1 and codigos.CADENA<>4 and codigos.cadena<>7  and codigos.cadena=6   GROUP BY codigos.plataforma) AS totales");
        while(rs.next())
        {
          totalhcplataformas=rs.getInt("total_plata");
        }

                
       //query totales de hc por plataforma
       rs=Principal.cn.GetConsulta("SELECT plataforma ,SUM(MANUFACTURA.HCDIRLINEA) AS HCLINEA, sum(manufactura.hcdirsoplps) as SopLPS, sum(manufactura.hcdirsoporte) as soporte, Sum(manufactura.HCDIRLPS) as LPS, SUM(manufactura.HCDIRCONTE) AS CONTENSION, SUM(manufactura.HCDIRFTQ) AS FTQ, SUM(manufactura.HCDIRPILOTOS) AS PILOTO, SUM(manufactura.hcdirsistemas) as sistemas, SUM(manufactura.HCDIRTABINSP) AS 'TAB.INSP', SUM(MANUFACTURA.HCRUTASINT) AS RUTASINTERNAS, SUM(manufactura.HCDIRLINEA + manufactura.HCDIRSOPLPS + manufactura.HCDIRSOPORTE + manufactura.HCDIRLPS + manufactura.HCDIRCONTE + manufactura.HCDIRFTQ + manufactura.HCDIRPILOTOS + manufactura.HCDIRSISTEMAS + manufactura.HCDIRTABINSP + manufactura.HCRUTASINT) AS totaleshc FROM manufactura,codigos WHERE manufactura.IDCODIGO = codigos.IDCODIGO AND MANUFACTURA.ACTIVO=1 and codigos.CADENA<>4 and codigos.cadena<>7 and  codigos.cadena=6 AND codigos.plataforma='"+gente.get(index).NombrePlataforma+"'  GROUP BY codigos.plataforma");
        if(rs.next())
        {
           totalhc=rs.getDouble("totaleshc");
               
              Double Porciento=0.0;
              Double ModManufactura=0.0;
              Double ModInge=0.0;
              
          
              Porciento=totalhc/totalhcplataformas;
          
              ModManufactura=Porciento*totalHCCorteManufA;
              // ModManufactura=(double) Math.round(ModManufactura);
              gente.get(index).HCCorteManuf=ModManufactura;
              
              ModInge=Porciento;
              Porciento=EficienciaPlanta.Regresa2Decimales(Porciento);
              ModInge=EficienciaPlanta.Regresa2Decimales( ModInge);
               //ModInge  =(double) Math.round( ModInge);
             // gente.get(index).HCCorteMSD=ModInge;
        }
       
       //query GAP 
        rs=Principal.cn.GetConsulta(" SELECT  c.IDCODIGO, c.CADENA,PLATAFORMA, c.CODIGO,  c.LINEA, c.TURNO,   \n" +
                                    "sum(HCDIRSOPORTE) AS 'SOPORTE', sum(HCDIRSOPLPS) AS 'SOP. LPS', sum(HCDIRTABINSP) AS 'TAB.INSP.', sum(hcdirconte) AS 'CONTENSION', sum(hcdirftq) AS 'FTQ', sum(hcdirsistemas) AS 'SISTEMAS', sum(hcdirpilotos) AS 'PILOTOS', sum(hcrutasint) AS 'RUTAS INT.'\n" +
                                    "FROM codigos as c ,manufactura as m where m.IDCODIGO=c.IDCODIGO and c.cadena<>5 and c.cadena<>4 and c.cadena<>7 AND C.CADENA=6 and m.ACTIVO=1  GROUP BY c.PLATAFORMA");
         while(rs.next())
        {   
          
          for(int i=0; i<gente.size(); i++)
          {
                
               if(gente.get(i).NombrePlataforma.trim().equals(rs.getString("plataforma").trim()))
               {
               gente.get(i).soporte=rs.getDouble("SOPORTE");
               gente.get(i).sop_lps=rs.getDouble("SOP. LPS") ;
               gente.get(i).tablero=rs.getDouble("TAB.INSP.");
               gente.get(i).contension=rs.getDouble("CONTENSION");
               gente.get(i).ftq=rs.getDouble("FTQ");
               gente.get(i).sistemas=rs.getDouble("SISTEMAS");
               gente.get(i).pilotos=rs.getDouble("PILOTOS");
               gente.get(i).rutas_int=rs.getDouble("RUTAS INT."); 
               } 
           
          }
          
        }

    }
    catch(Exception e)
    {
        System.out.println(e.toString());
    }
   
    }
    
    public void CrearPaneles(){
        inicializarArray();
         Double cut_gcsd=0.0, cut_msd=0.0, cut_mfg=0.0, slp_gsd=0.0,slp_msd=0.0,slp_mfg=0.0;
         Double  F_AssemblyGsd=0.0,F_AssemblyMsd=0.0,F_AssemblyMfg=0.0,efic_msd=0.0, efic_mfg=0.0 ; 
         double peoplecut_gsd=0.0,peoplecut_msd=0.0,peoplecut_mfg=0.0; 
        
//         // suma  kachirules solamente  ala plataforma k2xx
//           ResultSet rs2=Principal.cn.GetConsulta("select c.IDCODIGO, sum(m.HCDIRLINEA) as KACHIRULES from codigos as c, manufactura as m where c.IDCODIGO=m.IDCODIGO and c.CADENA=7");       
//        try {
//            if(rs2.next())
//            {            
//              kachirules2=rs2.getDouble("KACHIRULES");
//            }
//       
//         for(int i=0; i<gente.size(); i++){      
//               if( gente.get(i).NombrePlataforma.trim().equals("K2XX"))
//                gente.get(i).kachirulesS=kachirules2;
//         } 
//              } catch (SQLException ex) {
//            Logger.getLogger(RPR_Gua.class.getName()).log(Level.SEVERE, null, ex);
//        }
           
     
        
         for(int i=0; i<gente.size(); i++)
      {
          SacarTablaDesgloseCorte(gente.get(i).NombrePlataforma, i);
          getGCSD(gente.get(i).NombrePlataforma, i);
          getMSDyEnsFinal(gente.get(i).NombrePlataforma, i);
          
      }
      for(int i=0; i<gente.size(); i++)
      {   
          cut_gcsd=(gente.get(i).HCCorteGCSD);
         //cut_gcsd=EficienciaPlanta.Regresa2Decimales(cut_gcsd);
          cut_gcsd=(double) Math.round(cut_gcsd);
        
          cut_msd=(gente.get(i).HCCorteMSD);
          cut_msd=(double)Math.round((cut_msd));
     
               
          cut_mfg=(gente.get(i).HCCorteManuf);
          cut_mfg=EficienciaPlanta.Regresa2Decimales(cut_mfg);
          //cut_mfg=(double) Math.round(cut_mfg);
          
          slp_gsd=(double) Math.round(gente.get(i).HCLPSGCSD);
          slp_msd=EficienciaPlanta.Regresa2Decimales(gente.get(i).HCLPSMSD);
          slp_mfg=EficienciaPlanta.Regresa2Decimales(gente.get(i).HCLPSManuf);
          
          F_AssemblyGsd=EficienciaPlanta.Regresa2Decimales(gente.get(i).HCEnsFinalGCSD);
          F_AssemblyMsd=EficienciaPlanta.Regresa2Decimales(gente.get(i).HCEnsFinalMSD);
          F_AssemblyMfg=EficienciaPlanta.Regresa2Decimales(gente.get(i).HCEnsFinalManuf);
          /// total gente
          peoplecut_gsd=(double) Math.round(cut_gcsd+slp_gsd+F_AssemblyGsd);
         // peoplecut_gsd=(double) Math.round(peoplecut_gsd);
          peoplecut_msd= (double) Math.round( cut_msd+slp_msd+F_AssemblyMsd);
          
          peoplecut_mfg=(double) Math.round(cut_mfg+slp_mfg+F_AssemblyMfg+gente.get(i).kachirulesS);
          GAP=peoplecut_mfg-peoplecut_msd;
          GAP=(double) Math.round(GAP);
           efic_msd=EficienciaPlanta.Regresa2Decimales((peoplecut_gsd/peoplecut_msd)*100);
           efic_mfg=EficienciaPlanta.Regresa2Decimales((peoplecut_gsd/peoplecut_mfg)*100);                                
          /// GCSD  
         Object[] eficiencia=new Object[]{"Eficiencia",((EficienciaPlanta.Regresa2Decimales(peoplecut_gsd/peoplecut_gsd)*100)+"%"),efic_msd +"%",efic_mfg+"%","","" };
         Object[] people=new Object[]{"T. people",peoplecut_gsd,peoplecut_msd,peoplecut_mfg,GAP,"" };
         Object[] PorcCuting=new Object[]{"% CUTING", EficienciaPlanta.Regresa2Decimales((gente.get(i).HCCorteGCSD/gente.get(i).HCCorteGCSD)*100).toString()+" %", EficienciaPlanta.Regresa2Decimales((gente.get(i).HCCorteGCSD)/gente.get(i).HCCorteMSD*100).toString() +" %", EficienciaPlanta.Regresa2Decimales((gente.get(i).HCCorteGCSD/gente.get(i).HCCorteManuf)*100).toString()+" %",""};
         Object[] cuting=new Object[]{"CUTING",cut_gcsd, cut_msd, cut_mfg,""};
         Object[] PorcSPL=new Object[]{"% SLP",EficienciaPlanta.Regresa2Decimales((gente.get(i).HCLPSGCSD/gente.get(i).HCLPSGCSD)*100).toString()+ " %", EficienciaPlanta.Regresa2Decimales((gente.get(i).HCLPSGCSD)/gente.get(i).HCLPSMSD*100).toString()+" %", EficienciaPlanta.Regresa2Decimales((gente.get(i).HCLPSGCSD/gente.get(i).HCLPSManuf)*100).toString()+" %",""};
         Object[] SPL=new Object[]{"SLP",Math.round(gente.get(i).HCLPSGCSD), EficienciaPlanta.Regresa2Decimales(gente.get(i).HCLPSMSD).toString(), EficienciaPlanta.Regresa2Decimales(gente.get(i).HCLPSManuf).toString(),""};
         Object[] ENSFinal=new Object[]{"Final Assembly",(double) Math.round(gente.get(i).HCEnsFinalGCSD), EficienciaPlanta.Regresa2Decimales(gente.get(i).HCEnsFinalMSD).toString(), EficienciaPlanta.Regresa2Decimales(gente.get(i).HCEnsFinalManuf).toString(),""};
         Object[] PorcENSFinal=new Object[]{"% Final Assembly", EficienciaPlanta.Regresa2Decimales((gente.get(i).HCEnsFinalGCSD/gente.get(i).HCEnsFinalGCSD)*100).toString()+ " %", EficienciaPlanta.Regresa2Decimales((gente.get(i).HCEnsFinalGCSD)/gente.get(i).HCEnsFinalMSD*100).toString()+ " %", EficienciaPlanta.Regresa2Decimales((gente.get(i).HCEnsFinalGCSD)/gente.get(i).HCEnsFinalManuf*100).toString()+ " %",""};
         this.getContentPane().add(CrearPanel(gente.get(i).NombrePlataforma,eficiencia,people, cuting, PorcCuting, SPL, PorcSPL, ENSFinal, PorcENSFinal, i+10));
         
         totalcutGSD=(double)Math.round(totalcutGSD+cut_gcsd);
         totalcutMSD=(double)(totalcutMSD+cut_msd);
         totalcutmfg=(double)(totalcutmfg+cut_mfg);
         
         totalSlp_gsd=(double)(totalSlp_gsd+slp_gsd);
         totalSlp_msd=(double)(totalSlp_msd+slp_msd);
         totalSlp_mfg=(double)(totalSlp_mfg+slp_mfg);
         
         totalF_assemblyGsd=(double)Math.round(totalF_assemblyGsd+F_AssemblyGsd);
         totalF_assemblyMsd=(double)(totalF_assemblyMsd+F_AssemblyMsd);
         totalF_assemblyMfg=(double)(totalF_assemblyMfg+F_AssemblyMfg);
         
        TCutporc_gsd=EficienciaPlanta.Regresa2Decimales((totalcutGSD/totalcutGSD)*100).toString() + "%";
        TCutporc_msd= EficienciaPlanta.Regresa2Decimales((totalcutGSD/totalcutMSD)*100).toString() + "%";
        TCutporc_mfg=EficienciaPlanta.Regresa2Decimales((totalcutGSD/totalcutmfg)*100).toString() + "%";
        
        Tslporc_gsd=EficienciaPlanta.Regresa2Decimales((totalSlp_gsd/totalSlp_gsd)*100).toString() + "%" ;
        Tslporc_msd=EficienciaPlanta.Regresa2Decimales((totalSlp_gsd/totalSlp_msd)*100).toString() + "%";
        Tslporc_mfg=EficienciaPlanta.Regresa2Decimales((totalSlp_gsd/totalSlp_mfg)*100).toString() + "%";
         
        Tassemblyporc_gsd=EficienciaPlanta.Regresa2Decimales((totalF_assemblyGsd/totalF_assemblyGsd)*100).toString() + "%";
               // Tassemblyporc_gsd=(double)Math.round((totalF_assemblyGsd/totalF_assemblyGsd)*100) + "%";

        Tassemblyporc_msd=EficienciaPlanta.Regresa2Decimales((totalF_assemblyGsd/totalF_assemblyMsd)*100).toString() + "%";
        Tassemblyporc_mfg=EficienciaPlanta.Regresa2Decimales((totalF_assemblyGsd/totalF_assemblyMfg)*100).toString() + "%";
         
      }
    }
   
    public  void crearpanelesGAP(){
        try {
//            ResultSet rs=Principal.cn.GetConsulta("select c.IDCODIGO, sum(m.HCDIRLINEA) as KACHIRULES from codigos as c, manufactura as m where c.IDCODIGO=m.IDCODIGO and c.CADENA=7");       
//           if(rs.next())
//           {            
//             kachirules=rs.getDouble("KACHIRULES");            
//           }
//           for(int i=0; i<gente.size(); i++){      
//               if( gente.get(i).NombrePlataforma.trim().equals("K2XX"))
//                gente.get(i).kachirulesS=kachirules;
//                  
//             }
             for(int i=0; i<gente.size(); i++)
         {  
           Object[] blanco=new Object[]{""," ","","" };

            Object[] gap=new Object[]{gente.get(i).soporte,gente.get(i).sop_lps,gente.get(i).tablero,gente.get(i).contension,gente.get(i).ftq,gente.get(i).kachirulesS,gente.get(i).sistemas,gente.get(i).pilotos,"",gente.get(i).rutas_int};
             this.getContentPane().add(panelGAP(gente.get(i).NombrePlataforma,blanco,gap,i+10));
       
        }
        }
        catch (Exception ex) {
            Logger.getLogger(RPR_Gua.class.getName()).log(Level.SEVERE, null, ex);
        }
     }

    public void CrearPanelestotales(){
         double Tpeoplecut_gsd=0.0,Tpeoplecut_msd=0.0,Tpeoplecut_mfg=0.0, Tefic_msd=0.0,Tefic_mfg=0.0;
         
         // suma 8 kachirules solamente  ala plataforma k2xx
         // ResultSet rs2=Principal.cn.GetConsulta("select c.IDCODIGO, sum(m.HCDIRLINEA) as KACHIRULES from codigos as c, manufactura as m where c.IDCODIGO=m.IDCODIGO and c.CADENA=7");       
//        try {
//          //  if(rs2.next())
//           // {            
//          //    kachirules2=rs2.getDouble("KACHIRULES");
//           // }
//              } catch (Exception ex) {
//            Logger.getLogger(RPR_Gua.class.getName()).log(Level.SEVERE, null, ex);
//        }
           
         
         totalcutmfg=EficienciaPlanta.Regresa2Decimales(totalcutmfg);
         totalcutmfg=(double) Math.round(totalcutmfg);
         
          totalcutMSD=EficienciaPlanta.Regresa2Decimales(totalcutMSD);
         totalcutMSD=(double) Math.round(totalcutMSD);
         
          Tpeoplecut_gsd=(double) Math.round(totalcutGSD+totalSlp_gsd+totalF_assemblyGsd);
          Tpeoplecut_msd=totalcutMSD+totalSlp_msd+totalF_assemblyMsd;
          Tpeoplecut_mfg=totalcutmfg+totalSlp_mfg+totalF_assemblyMfg+kachirules2;
          GAP_TOTAL=Tpeoplecut_mfg-Tpeoplecut_msd;
          GAP_TOTAL=(double) Math.round(GAP_TOTAL);
          
          
          Tefic_msd=EficienciaPlanta.Regresa2Decimales((Tpeoplecut_gsd/Tpeoplecut_msd)*100);
          Tefic_mfg=EficienciaPlanta.Regresa2Decimales((Tpeoplecut_gsd/Tpeoplecut_mfg)*100);   
          
       
         Object[] eficTotal=new Object[]{"Eficiencia",((Tpeoplecut_gsd/Tpeoplecut_gsd)*100+"%"),Tefic_msd+"%",EficienciaPlanta.Regresa2Decimales(Tefic_mfg)+"%","",""};
         Object[] people=new Object[]{"T.people",Tpeoplecut_gsd,Tpeoplecut_msd,Tpeoplecut_mfg,GAP_TOTAL,""};
         Object[] PorcCuting=new Object[]{"% CUTING",TCutporc_gsd,TCutporc_msd,TCutporc_mfg,""};
         Object[] cuting=new Object[]{"CUTING",totalcutGSD,totalcutMSD,totalcutmfg,""};
         Object[] PorcSPL=new Object[]{"% SLP",Tslporc_gsd,Tslporc_msd,Tslporc_mfg,""};
         Object[] SPL=new Object[]{"SLP",totalSlp_gsd,totalSlp_msd,totalSlp_mfg,""};
         Object[] ENSFinal=new Object[]{"Final Assembly", totalF_assemblyGsd,totalF_assemblyMsd,totalF_assemblyMfg,""};
         Object[] PorcENSFinal=new Object[]{"% Final Assembly",Tassemblyporc_gsd,Tassemblyporc_msd,Tassemblyporc_mfg,""};
         this.getContentPane().add(CrearTOTALPANEL(eficTotal,people,cuting, PorcCuting, SPL, PorcSPL, ENSFinal, PorcENSFinal));
     }
    
     
    public void  crearpanelTotalGAP(){
         double soporte=0.0,TsopLPS=0.0,Ttablero=0.0, Tcontension=0.0, Tftq=0.0, Tsistemas=0.0,Tpilotos=0.0, Trutas=0.0;
           // suma 8 kachirules solamente  ala plataforma k2xx
           //ResultSet rs2=Principal.cn.GetConsulta("select c.IDCODIGO, sum(m.HCDIRLINEA) as KACHIRULES from codigos as c, manufactura as m where c.IDCODIGO=m.IDCODIGO and c.CADENA=7");       
//        try {
//            if(rs2.next())
//            {            
//              kachirules2=rs2.getDouble("KACHIRULES");
//            }
//       
//     
//            } catch (SQLException ex) {
//            Logger.getLogger(RPR_Gua.class.getName()).log(Level.SEVERE, null, ex);
//        }
           
         for(int i=0; i<gente.size(); i++)
             {      
         soporte=soporte+gente.get(i).soporte;
         TsopLPS = TsopLPS+gente.get(i).sop_lps;
         Ttablero= Ttablero+gente.get(i).tablero; 
         Tcontension=Tcontension+gente.get(i).contension;
         Tftq=Tftq+gente.get(i).ftq;
      
         Tsistemas=Tsistemas+gente.get(i).sistemas;
         Tpilotos=Tpilotos+gente.get(i).pilotos;
         Trutas=Trutas+gente.get(i).rutas_int;
         }
          for(int i=0; i<gente.size(); i++)
         {   
                     
         Object[] peopleGAPtotal=new Object[]{soporte,TsopLPS,Ttablero,Tcontension,Tftq,kachirules2,Tsistemas,Tpilotos,"",Trutas };
         this.getContentPane().add(CrearTOTALPANELGAP(peopleGAPtotal));  
         }
     }
    
   
    public JPanel CrearTOTALPANEL( Object[] eficienciaTotal,Object[] people,Object[] cutting, Object[] PorcCuting,  Object[] lps, Object[] PorcLPS, Object[] ensamble, Object[] Porcensamble ){
        String Nombre="Total Planta ";
        Random rand2 = new Random();
        tabla2.setModel(modelo2);
       
         modelo2.setColumnIdentifiers(new  Object[]{"         ", "GCSD", "MSD", "MFG", "GAP",""});
         modelo2.addRow(eficienciaTotal);
         modelo2.addRow(people);
         modelo2.addRow(PorcCuting);
         modelo2.addRow(cutting);
         modelo2.addRow(PorcLPS);
         modelo2.addRow(lps);
         modelo2.addRow(Porcensamble); 
         modelo2.addRow(ensamble);
       

     
         tabla2.setSize(300,150);
         JScrollPane scrollPane2 = new JScrollPane(tabla2);
         tabla2.getColumnModel().getColumn(0).setPreferredWidth(20);
         tabla2.getColumnModel().getColumn(1).setPreferredWidth(15);
         tabla2.getColumnModel().getColumn(2).setPreferredWidth(15);
         tabla2.getColumnModel().getColumn(3).setPreferredWidth(15); 
         tabla2.getColumnModel().getColumn(4).setPreferredWidth(15);
          
           tabla2.getColumnModel().getColumn(5).setMaxWidth(5);
       tabla2.getColumnModel().getColumn(5).setMinWidth(5);
        tabla2.getColumnModel().getColumn(5).setPreferredWidth(5);
        
      
                 
        ScrollPane panelScroll2=new ScrollPane();
        panelScroll2.setWheelScrollingEnabled(true);
        JPanel jpanel2=new JPanel();
        jpanel2.setVisible(true);
        panelScroll2.setLocation(5, valorAnteriorLoc);
        jpanel2.setLocation(5, valorAnteriorLoc);
        
       
        jpanel2.setSize(450, 90);
        panelScroll2.setSize(500, 90);
        jpanel2.setBackground(Color.RED);
        jpanel2.setLayout(new BoxLayout(jpanel2, BoxLayout.Y_AXIS));
        Button b=new   Button("     "+Nombre);
        b.setBackground(getColorAleaorio( rand2.nextFloat(), rand2.nextFloat(), rand2.nextFloat()));
        b.setSize(500, 5);
        jpanel2.add(b);
        
        panelScroll2.add(jpanel2);
        panelScroll2.add(scrollPane2);
        jpanel2.add(panelScroll2);
        return jpanel2;
    } 

    public JPanel CrearPanel(String Nombre,Object[]eficiencia,Object[] people, Object[] cutting, Object[] PorcCuting,  Object[] lps, Object[] PorcLPS, Object[] ensamble, Object[] Porcensamble, int locY){
         Random rand = new Random();
         DefaultTableModel modelo = new  DefaultTableModel();
      
         modelo.setColumnIdentifiers(new  Object[]{"         ", "GCSD", "MSD", "MFG", "GAP",""});
         modelo.addRow(eficiencia);
         modelo.addRow(people);
         modelo.addRow(PorcCuting);
         modelo.addRow(cutting);
         modelo.addRow(PorcLPS);
         modelo.addRow(lps);
         modelo.addRow(Porcensamble); 
         modelo.addRow(ensamble);
         
         for(PlataformaGenteCorte g:gente)
         {
             if(g.NombrePlataforma.trim().equals(Nombre.trim()))
             {
                 g.modeloExportar=modelo;
                break;
             }
         }
         JTable tabla=new JTable(modelo);
         tabla.setSize(300,150);
         JScrollPane scrollPane = new JScrollPane(tabla);
         tabla.getColumnModel().getColumn(0).setPreferredWidth(20);
         tabla.getColumnModel().getColumn(1).setPreferredWidth(15);
         tabla.getColumnModel().getColumn(2).setPreferredWidth(15);
         tabla.getColumnModel().getColumn(3).setPreferredWidth(15); 
         tabla.getColumnModel().getColumn(4).setPreferredWidth(15);
         
         tabla.getColumnModel().getColumn(5).setMaxWidth(5);
         tabla.getColumnModel().getColumn(5).setMinWidth(5);
          tabla.getColumnModel().getColumn(5).setPreferredWidth(5);
        
        ScrollPane panelScroll=new ScrollPane();
        panelScroll.setWheelScrollingEnabled(true);
        JPanel jpanel=new JPanel();
        jpanel.setVisible(true);
        panelScroll.setLocation(5, valorAnteriorLoc);
        jpanel.setLocation(5, valorAnteriorLoc);
        jpanel.setSize(450, 90);
        panelScroll.setSize(500, 90);
        valorAnteriorLoc+=jpanel.getSize().height+locY;
        jpanel.setBackground(Color.WHITE);
        jpanel.setLayout(new BoxLayout(jpanel, BoxLayout.Y_AXIS));
        Button b=new   Button("          "+Nombre);
        b.setBackground(getColorAleaorio( rand.nextFloat(), rand.nextFloat(), rand.nextFloat()));
        jpanel.add(b);
        panelScroll.add(jpanel);
        panelScroll.add(scrollPane);
        jpanel.add(panelScroll);
       
        return jpanel;
    }
    
    
    public JPanel panelGAP(String Nombre,Object[]blanco, Object[] peopleGAP, int locY){
        Random rand3 = new Random();
         DefaultTableModel modelo3=new DefaultTableModel();
         modelo3.setColumnIdentifiers(new  Object[]{"Supports FA", " Supports LPS ", " Tab. Inspect.", " Contensiones", "   FTQ"," Kachirules","   Sistemas"," Pilots & Serv.","   CORTE","RUTAS INT" });
         modelo3.addRow(blanco);
         modelo3.addRow(peopleGAP);
        
         
               for(PlataformaGenteCorte g2:gente)
         {
             if(g2.NombrePlataforma.trim().equals(Nombre.trim()))
             {
                 g2.modeloExportarGAP=modelo3;
                break;
             }
         }

         JTable tabla3=new JTable(modelo3);
         tabla3.setSize(300,150);
         JScrollPane scrollPane3 = new JScrollPane(tabla3);
         tabla3.getColumnModel().getColumn(0).setPreferredWidth(20);
         tabla3.getColumnModel().getColumn(1).setPreferredWidth(15);
         tabla3.getColumnModel().getColumn(2).setPreferredWidth(15);
         tabla3.getColumnModel().getColumn(3).setPreferredWidth(15); 
         tabla3.getColumnModel().getColumn(4).setPreferredWidth(15);
         tabla3.getColumnModel().getColumn(5).setPreferredWidth(15);
         tabla3.getColumnModel().getColumn(6).setPreferredWidth(15);
         tabla3.getColumnModel().getColumn(7).setPreferredWidth(15);
         tabla3.getColumnModel().getColumn(8).setPreferredWidth(15);
         tabla3.getColumnModel().getColumn(9).setPreferredWidth(15);

        ScrollPane panelScroll3=new ScrollPane();
        panelScroll3.setWheelScrollingEnabled(true);
        JPanel jpanel3=new JPanel();
        jpanel3.setVisible(true);
        panelScroll3.setLocation(460, valorAnteriorLoc-300);
        jpanel3.setLocation(460, valorAnteriorLoc-90);
        jpanel3.setSize(900, 90);
        panelScroll3.setSize(900, 190);
        valorAnteriorLoc+=jpanel3.getSize().height+locY;
        jpanel3.setBackground(Color.WHITE);
        jpanel3.setLayout(new BoxLayout(jpanel3, BoxLayout.Y_AXIS));
        //Button b=new   Button("     "+Nombre);
       // b.setBackground(getColorAleaorio( rand3.nextFloat(), rand3.nextFloat(), rand3.nextFloat()));
       // jpanel3.add(b);
        panelScroll3.add(jpanel3);
        panelScroll3.add(scrollPane3);
        jpanel3.add(panelScroll3);
        return jpanel3;
    } 
      
    public JPanel CrearTOTALPANELGAP(Object[] peopleGAPtotal){
        String Nombre="Total GAP ";
         Random rand4 = new Random();
        // tabla4.setModel(modelo4);
         DefaultTableModel modelo4=new DefaultTableModel();
     
         modelo4.setColumnIdentifiers(new  Object[]{"Supports FA", " Supports LPS ", " Tab. Inspect.", " Contensiones", "        FTQ"," Kachirules","   Sistemas"," Pilots & Serv.","   CORTE","RUTAS INT" });
        
         modelo4.addRow(peopleGAPtotal);
         gente.get(0).totalGap=modelo4;
         JTable tabla4=new JTable(modelo4);
         tabla4.setSize(300,150);
         JScrollPane scrollPane4 = new JScrollPane(tabla4);
         tabla4.getColumnModel().getColumn(0).setPreferredWidth(20);
         tabla4.getColumnModel().getColumn(1).setPreferredWidth(15);
         tabla4.getColumnModel().getColumn(2).setPreferredWidth(15);
         tabla4.getColumnModel().getColumn(3).setPreferredWidth(15); 
         tabla4.getColumnModel().getColumn(4).setPreferredWidth(15); 
         tabla4.getColumnModel().getColumn(5).setPreferredWidth(15);
         tabla4.getColumnModel().getColumn(6).setPreferredWidth(15);
         tabla4.getColumnModel().getColumn(7).setPreferredWidth(15);
         tabla4.getColumnModel().getColumn(8).setPreferredWidth(15);
         tabla4.getColumnModel().getColumn(9).setPreferredWidth(15);
       
         
        ScrollPane panelScroll4=new ScrollPane();
        panelScroll4.setWheelScrollingEnabled(true);
        JPanel jpanel4=new JPanel();
        jpanel4.setVisible(true);
        panelScroll4.setLocation(460, valorAnteriorLoc+12);
        jpanel4.setLocation(460, valorAnteriorLoc+12);
        jpanel4.setSize(900, 70);
        panelScroll4.setSize(900, 50);
        jpanel4.setBackground(Color.RED);
        jpanel4.setLayout(new BoxLayout(jpanel4, BoxLayout.Y_AXIS));
        Button b=new   Button("     "+Nombre);
        b.setBackground(getColorAleaorio( rand4.nextFloat(), rand4.nextFloat(), rand4.nextFloat()));
//        b.getFont(fo);
        b.setSize(500, 5);
        jpanel4.add(b);
        
        panelScroll4.add(jpanel4);
        panelScroll4.add(scrollPane4);
        jpanel4.add(panelScroll4);
        return jpanel4;
    } 

    public void getGCSD(String Plataforma, int index){
        try {
            ResultSet rs =Principal.cn.GetConsulta("select c.PLATAFORMA, C.CODIGO, C.TURNO, sum(m.SALIDAENPIEZA) as 'SALIDA', sum(m.PUNTOSPZAPOND) as 'ptos.pza', SUM(g.HCDIRCORTE) as 'POND.CORTE', \n" +
                "truncate( SUM( if(c.TURNO='A', ((g.HCDIRCORTE*m.SALIDAENPIEZA)/9/100), ((g.HCDIRCORTE*m.SALIDAENPIEZA)/7.9/100))),2) aS 'GENTECORTE', \n" +
                "SUM(g.HCDIRLPS) as 'PONDLPS',  truncate (SUM( if(c.TURNO='A', ((g.HCDIRLPS *m.SALIDAENPIEZA)/9/100), ((g.HCDIRLPS*m.SALIDAENPIEZA)/7.9/100))),2) as 'GENTELPS',\n" +
                "SUM(g.HCDIRENSFINAL) as 'POND.ENSFINAL', \n" +
                "truncate(SUM( if(c.TURNO='A', ((g.HCDIRENSFINAL *m.SALIDAENPIEZA)/9/100), ((g.HCDIRENSFINAL*m.SALIDAENPIEZA)/7.9/100))),2) as 'GENTEENSFINAL'  \n" +
                "FROM codigos as c, manufactura as m, gsd  as g where c.IDCODIGO=m.IDCODIGO and c.IDCODIGO=g.IDCODIGO AND  c.PLATAFORMA<>'CORTE' and  m.activo=1 and  c.plataforma='"+Plataforma+"' AND c.LINEA<>'91'  AND c.CADENA=6  GROUP BY c.PLATAFORMA");
               if(rs.next())
               {
                  // gente.get(index).HCCorteGCSD=rs.getDouble("GENTECORTE");
                  //  gente.get(index).HCCorteGCSD  =(double) Math.round( gente.get(index).HCCorteGCSD);
                   gente.get(index).HCLPSGCSD=rs.getDouble("GENTELPS");
                  // gente.get(index).HCLPSGCSD  =(double) Math.round( gente.get(index).HCLPSGCSD);
                   gente.get(index).HCEnsFinalGCSD=rs.getDouble("GENTEENSFINAL");
                   //gente.get(index).HCEnsFinalGCSD =(double) Math.round( gente.get(index).HCEnsFinalGCSD);

                   
               }
        }catch(Exception e )
        {
          System.out.println(e.toString());
        }
    }
    
    public void getMSDyEnsFinal(String Plataforma, int index){
        try {
         ResultSet rs =Principal.cn.GetConsulta("SELECT\n" +
                    "c.PLATAFORMA, c.CODIGO, c.linea, c.TURNO,\n" +
                    "sum(m.HCDIRLPS) as 'LPSMSD', \n" +
                    "SUM(M.HCDIRLPS) AS 'LPS.MANUF',\n" +
                    "sum(m.HCDIRLINEA+m.kits+m.estaciones+m.elinea) as 'ENS.FINAL.MSD',\n" +
                    "SUM(c.hcdirecto) AS 'ENS.FINALMANUF',\n" +
                    "sum(m.HCDIRLINEA+m.kits+m.estaciones+m.elinea) as 'gentedirectamsd', \n" +
                    "SUM(M.HCDIRLPS+M.HCDIRSOPLPS +HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirpilotos+hcdirftq+ hcdirsistemas) as 'gentedirectamanuf'\n" +
                    "FROM \n" +
                    "personas as c ,\n" +
                    "manufactura AS M \n" +
                    "\n" +
                    "WHERE\n" +
                    "c.IDCODIGO =  M.IDCODIGO  and m.ACTIVO=1 and c.plataforma='"+Plataforma+"' AND c.LINEA<>'91' AND c.CADENA=6   \n" +
                    "GROUP BY\n" +
                    "PLATAFORMA");
               if(rs.next())
               {
                   gente.get(index).HCLPSMSD=rs.getDouble("LPSMSD");
                   gente.get(index).HCLPSManuf=rs.getDouble("LPS.MANUF");
                   gente.get(index).HCEnsFinalMSD=rs.getDouble("ENS.FINAL.MSD");
                   gente.get(index).HCEnsFinalManuf=rs.getDouble("ENS.FINALMANUF");
               }
        }catch(Exception e )
        {
          System.out.println(e.toString());
        }
    }
    
    public void inicializarArray(){
     try {
             //query que manda las plataformas    
             ResultSet rs=Principal.cn.GetConsulta("SELECT DISTINCT\n" +
              "codigos.PLATAFORMA\n" +
              "FROM\n" +
              "codigos where codigos.cadena=6  AND cODIGOS.LINEA<>'91'AND cODIGOS.LINEA<>'29A'   GROUP BY codigos.plataforma");
            while(rs.next())
            {
                gente.add(new PlataformaGenteCorte(rs.getString("plataforma"), 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0));
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
          
    }
    //color aleatorio de los titulos de las plataformas
    public Color getColorAleaorio(float  r, float g, float b){
        Color c=new Color(r, g, b);
        //c.brighter();
        return c;
    }        
    /**
     * This method is  called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Arial Black", 1, 11)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/filetype-xls-icon (2).png"))); // NOI18N
        jButton1.setText("EXPORTA");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(800, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(31, 31, 31))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(527, Short.MAX_VALUE)
                .addComponent(jButton1))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
        Principal p=new Principal(Principal.UsuarioLogeado);
        p.setLocationRelativeTo(null);
        p.setVisible(true);
    }//GEN-LAST:event_formWindowClosed

 
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
       try{
//           for(PlataformaGenteCorte g:gente){
               ExcelRPR_gua exportar=new ExcelRPR_gua(gente,  "RPR", (DefaultTableModel) tabla2.getModel(),gente, gente.get(0).totalGap);
//           }
         //ExcelRPR exportar=new ExcelRPR((DefaultTableModel)tabla2.getModel(), "RPR");
       }catch(Exception e )
       {  
             
           System.out.println(e.toString());
       }
      
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
            java.util.logging.Logger.getLogger(RPR_Gua.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RPR_Gua.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RPR_Gua.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RPR_Gua.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RPR_Gua().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    // End of variables declaration//GEN-END:variables
}
