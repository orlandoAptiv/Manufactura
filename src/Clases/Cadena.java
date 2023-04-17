/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

/**
 *
 * @author gzld6k
 */
public class Cadena {

    public String Cadena="";
    public Double totalGenteA;
    public Double totalGenteB;
    public Double TotalHrsEmbA;
    public Double TotalHrsEmbB;
    public Double TotalHrsPagA;
    public Double TotalHrsPagB;
    public Double EficienciaA;
    public Double EficienciaB;
    public Double TotalCadena;
    public Cadena(String Cad, Double totGenA, Double totGenB, Double totHrsEmbA, Double totHrsEmbB, Double totHrsPagA, Double totHrsPagB, Double EficA, Double EficB, Double TotalCad)
    {
            Cadena=Cad;
            totalGenteA=totGenA;
            totalGenteB=totGenB;
            TotalHrsEmbA=totHrsEmbA;
            TotalHrsEmbB=totHrsEmbB;
            TotalHrsPagA=totHrsPagA;
            TotalHrsPagB=totHrsPagB;
            EficienciaA=EficA;
            EficienciaB=EficB;
            TotalCadena=TotalCad;
    }
}
