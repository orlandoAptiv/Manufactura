/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author gzld6k
 */
public class PlataformaGenteCorte {
   public  String NombrePlataforma;
   public Double HCEnsFinalMSD=0.0;
   public Double HCEnsFinalGCSD=0.0;
   public  Double HCEnsFinalManuf=0.0;
   public Double HCLPSMSD=0.0;
   public Double HCLPSGCSD=0.0;
   public  Double HCLPSManuf=0.0;
   public Double HCCorteMSD=0.0;
   public Double HCCorteGCSD=0.0;
   public  Double HCCorteManuf=0.0;
   public Double soporte=0.0;
   public Double sop_lps=0.0;
   public Double tablero=0.0;
   public Double contension=0.0;
   public Double ftq=0.0;
   public Double kachirulesS=0.0;
   public Double sistemas=0.0;
   public Double  pilotos=0.0;
   public Double rutas_int=0.0;
   public  DefaultTableModel modeloExportar;
   public  DefaultTableModel modeloExportarGAP;
   public  DefaultTableModel modeloGap;
   public DefaultTableModel totalGap;  
    public PlataformaGenteCorte(String Nombre, Double HCEnsFManuf, Double HCLPSManuf,  Double HCcorteManuf, Double HCEnsFMSD, Double HCLPSMSD, Double HCcorteMSD, Double HCEnsFGCSD, Double HCLPSGCSD, Double HCcorteGCSD,Double soporteGAP, Double soporteLPS, Double tab_ins, Double conte,Double ftqq, Double kachis, Double sistem, Double pilot, Double rutas){
        NombrePlataforma=Nombre;
        HCCorteMSD=HCcorteMSD;
        HCCorteManuf=HCcorteManuf;
        HCCorteGCSD=HCcorteGCSD;
        this.HCLPSMSD=HCLPSMSD;
        this.HCLPSGCSD=HCLPSGCSD;
        this.HCLPSManuf=HCLPSManuf;
        this.HCEnsFinalGCSD=HCEnsFGCSD;
        this.HCEnsFinalMSD=HCEnsFMSD;
        this.HCEnsFinalManuf=HCEnsFManuf;
        this.soporte=soporteGAP;
        this.sop_lps=soporteLPS;
        this.tablero=tab_ins;
        this.contension=conte;
        this.ftq=ftqq;
        this.kachirulesS=kachis;
        this.sistemas=sistem;
        this.pilotos=pilot;
        this.rutas_int=rutas;
    }
   
    
}
