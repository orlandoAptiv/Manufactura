/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

/**
 *
 * @author gzld6k
 */
public class DatosGSD {
    public PlatCodLinea Codigo;
   // public String HCDIRTOTAL="";
    public String HCINDRUTAS="";
    public String HCDIRLPS="";
    public String HCDIRCORTE="";
    public String HCDIRENSFINAL="";
    public String PUNTOSPZAPOND="";
    public String CAP_UTIL_HTA="";
    public DatosGSD(PlatCodLinea  codigo, String hcDirLPS, String hcDirCorte, String hcDirEnsFinal, String hcindRutas, String ptosPzaPond, String CapUtilHta){
        Codigo=codigo;
        HCDIRLPS=hcDirLPS;
        HCDIRCORTE=hcDirCorte;
        HCDIRENSFINAL=hcDirEnsFinal;
        HCINDRUTAS=hcindRutas;
        PUNTOSPZAPOND=ptosPzaPond;
        CAP_UTIL_HTA=CapUtilHta;
    }
}
