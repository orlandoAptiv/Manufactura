/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

/**
 *
 * @author gzld6k
 */
public class DatosMSD {
    public PlatCodLinea Codigo;
    public String HCINDRUTAS="";
    public String HCDIRLPS="";
    public String HCDIRCORTE="";
    public String HCDIRENSFINAL="";
    //public String idcodigo="";
//    public String HCDIRLINEA="";
//    public String HCDIRLPS ="";
//    public String HCDIRSOPORTE="";
//    public String HCDIRTABINSP="";
//    public String HCDIRCONTE="";
//    public String HCDIRFTQ="";
//    public String HCDIRPILOTOS="";
//    public String HCDIRSISTEMAS="";
//    public String HCINDRUTAS="";
    public String PUNTOSPZAPOND="";
    public String CAP_UTIL_HTA="";
    public DatosMSD(PlatCodLinea  codigo,  String hcdirLps, String hcdirCorte, String hcdirEnsFinal,  String hcindRutas, String ptosPzaPond, String CapUtilHta){
          Codigo=codigo;
          HCDIRLPS=hcdirLps;
          HCDIRCORTE=hcdirCorte;
          HCDIRENSFINAL=hcdirEnsFinal;
//        HCDIRTOTAL=hcDirTotal;
//        HCDIRLINEA=hcdirlinea;
//        HCDIRLPS=hcdirlps;
//        HCDIRSOPORTE=hcdirsoporte;
//        HCDIRTABINSP=hcdirtabinsp;
//        HCDIRCONTE=hcdirconte;
//        HCDIRFTQ=hcdirftq;
//        HCDIRPILOTOS=hcdirpilotos;
//        HCDIRSISTEMAS=hcdirsistemas;
        HCINDRUTAS=hcindRutas;
        PUNTOSPZAPOND=ptosPzaPond;
        CAP_UTIL_HTA=CapUtilHta;
    }
    
}
