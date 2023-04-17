/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

/**
 *
 * @author gzld6k
 */
public class Usuario {
  public String codigo="";
  public  String nombre="";
  public String turno="";
  public  String puesto="";
  public  String usuario="";
  public  String contra="";
  public  String fechaRegistro="";
  public String Cadena1="";
  public String Cadena2="";
  public String Cadena3="";
  public String Cadena4="";
  public String Cadena5="";
    public String Cadena6="";
    public String Cadena8="";
  public String Usuarios="";
  public String Codigos="";
  public boolean  ReadOnly;
  public ImageIcon Foto;
    public Usuario(String Codigo, String Nombre, String Turno, String Puesto, String Usuario, String Contra,  String Fecha, String Cad1, String Cad2, String Cad3, String Cad4, String Cad5,String Cad6,String Cad8,String Us, String codigos, ImageIcon foto, boolean  readonly)
    {
        codigo=Codigo;
        nombre=Nombre;
        puesto=Puesto;
        turno=Turno;
        usuario=Usuario;
        contra=Contra;
        fechaRegistro=Fecha;
        Cadena1=Cad1;
        Cadena2=Cad2;
        Cadena3=Cad3;
        Cadena4=Cad4;
        Cadena5=Cad5;
        Cadena6=Cad6;
        Cadena8=Cad8;
        Usuarios=Us;
        Codigos=codigos;
        Foto=foto;
        ReadOnly=readonly;
    }
    
}
