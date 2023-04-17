/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package manufactura;

import Clases.Conection;
import Reportes.ExcelImpactos;
import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import static org.apache.poi.hssf.usermodel.HeaderFooter.file;
//import org.apache.poi.hssf.usermodel.HeaderFooter;
/**
 *
 * @author gzld6k
 */
public class CapturaImpactos extends javax.swing.JFrame {

    /**
     * Creates new form CapturaImpactos
     */
    DefaultTableModel  modeloCausas;
    ResultSet rs=null;
    ArrayList<String> idcausa=new ArrayList<String>();
    Double EficiencObjetivo=0.0;
    Double DifHoras=0.0, DifPorc=0.0;
    String fecha="";
    boolean BandActivarCbx=false;
    String FechaSemana="";
    public CapturaImpactos() {
        try {
            initComponents();
             Image icon = new ImageIcon(getClass().getResource("/Images/competitors-icon.png")).getImage();
            setIconImage(icon);
            Principal.cn=new Conection();
            setLocationRelativeTo(null);
            DatFeccha.setDate(new Date());
            EnlazarCbxCausas();
            Enlazardgv();
            EnlazarCbxCadena();
            SacarEficienciaObj();
            InsertarFechaImpacto();
            BandActivarCbx=true;
        } catch (Exception ex) {
            System.out.println(ex.toString()+" Line:48");
        } 
    }
    
    public void SacarTotal(){
        Double totalHoras=0.0;
        Double totalPuntos=0.0;
        for(int r=0; r<modeloCausas.getRowCount(); r++)
        {
            try{
                totalHoras+=(Double) modeloCausas.getValueAt(r, 2);
                totalPuntos+=(Double) modeloCausas.getValueAt(r, 3);
            }catch(Exception e){
                
            }
            lblTotHrsCausa.setText(Regresa2Decimales(totalHoras).toString());
            lblTotPtoCausa.setText(Regresa2Decimales(totalPuntos).toString());
        }
    }
    
    public void Actualizar(){
        try{
            ArrayList<Object> objetos=new ArrayList<Object>();
            objetos.add(EficiencObjetivo);
            objetos.add(txtHrsEmb.getValue());
            objetos.add(txtHrsPag.getValue());
            objetos.add(SacarPorcentajeEfic());
            objetos.add(DifHoras);
            objetos.add(txtComentarios.getText());
            objetos.add(RegresaFecha());
            objetos.add(cbxCadena.getSelectedItem());
            objetos.add(cbxTurno.getSelectedItem());
            Principal.cn.EjecutarInsertOb("UPDATE EFICIENCIALABOR SET eficienciaobjetivo=?, HRSEMB=?, HRSREALES=?, EFICIENCIALABOR=?, DIFERENCIA=?, COMENTARIO=? WHERE  DATE(FECHAHORA)=? AND CADENA=? AND TURNO=?", objetos);
                    
        }catch(Exception e)
        {
            System.out.println(e.toString()+"Line: 74");
        }
    }
    
    public void InsertarFechaImpacto(){
        try{
            //BandActivarCbx=false;
            rs=Principal.cn.GetConsulta("SELECT eficiencialabor.fechahora,  eficiencialabor.cadena, eficiencialabor.turno, eficiencialabor.eficienciaobjetivo,\n" +
            "eficiencialabor.hrsEmb, eficiencialabor.hrsReales, eficiencialabor.EficienciaLabor, eficiencialabor.Diferencia, eficiencialabor.comentario  \n" +
            "FROM eficiencialaboR  where  DATE(fechahora)='"+  RegresaFecha()+"' and cadena='"+cbxCadena.getSelectedItem().toString()+"' and Turno='"+cbxTurno.getSelectedItem().toString()+"'");
            if((rs.next())  && (rs.getString(1).length()>0))
            {
               txtHrsEmb.setValue(rs.getDouble("hrsEmb"));
               txtComentarios.setText(rs.getString("comentario"));
               txtHrsPag.setValue(rs.getDouble("hrsReales"));
               rs=Principal.cn.GetConsulta("SELECT\n" +
                "detalleeficiencialabor.fecha,\n" +
                "detalleeficiencialabor.cadena,\n" +
                "detalleeficiencialabor.Turno,\n" +
                "detalleeficiencialabor.idcausa,\n" +
                "detalleeficiencialabor.descripcion,\n" +
                "detalleeficiencialabor.horas,\n" +
                "detalleeficiencialabor.puntos\n" +
                "FROM\n" +
                "detalleeficiencialabor\n" +
                "WHERE\n" +
                "date(detalleeficiencialabor.fecha)='"+RegresaFecha()+"' and cadena='"+cbxCadena.getSelectedItem().toString()+"'\n" +
                " and Turno='"+cbxTurno.getSelectedItem().toString()+"'");
               Enlazardgv();
               while(rs.next())
               {
                   AgregarCausa(rs.getString("idcausa"), rs.getString("descripcion"), rs.getDouble("horas"), rs.getDouble("puntos"));
               }
               SacarTotal();
            }
            else
                
            {
             ArrayList<Object> objetos=new ArrayList<Object>();
             objetos.add(cbxCadena.getSelectedItem());
             objetos.add(cbxTurno.getSelectedItem());
             objetos.add(EficiencObjetivo);
             
             if(Principal.cn.EjecutarInsertOb("INSERT  INTO eficiencialabor (fechahora, cadena, turno, eficienciaObjetivo) VALUES ('"+RegresaFecha()+"', ?, ?, ?)", objetos))
             {
                 Enlazardgv();
                 Enlazardetalle();
                 txtHrsEmb.setValue(0.0);
                 txtHrsPag.setValue(0.0);
                 DifHoras=0.0;
                 DifPorc=0.0;
                 lblTotHrsCausa.setText("0.0");
                 lblTotPtoCausa.setText("0.0");
                 lblDifHras.setText("0");
                 lblDifPorc.setText("0 %");
                 txtComentarios.setText("");
             }
            }
        }catch(Exception e){
            System.out.println(e.toString()+" Line:InsertarFechaImpacto");
        }
    }
    
    public void InsertarCausaBD(String IDCAUSA, String Causa, Double Horas, Double puntos){
        try{
           ArrayList<Object> objetos=new ArrayList<Object>();
           objetos.add(RegresaFecha());
           objetos.add(cbxCadena.getSelectedItem());
           objetos.add(cbxTurno.getSelectedItem());
           objetos.add(IDCAUSA);
           objetos.add(Causa);
           objetos.add(Horas);
           objetos.add(puntos);
           Principal.cn.EjecutarInsertOb("insert into detalleeficiencialabor  (detalleeficiencialabor.fecha, detalleeficiencialabor.cadena, detalleeficiencialabor.Turno, detalleeficiencialabor.idcausa, detalleeficiencialabor.descripcion, detalleeficiencialabor.horas, detalleeficiencialabor.puntos) VALUES (?, ?, ?, ?, ?, ?, ?)", objetos);
//           Enlazardgv();
//           Enlazardetalle();
       }catch(Exception e)
       {
          System.out.println(e.toString()+" Line: InsertarCausaBD" );
       }
    }
            
    public void AgregarCausa(String IDCAUSA, String Causa, Double Horas, Double puntos){
        try{
            modeloCausas.addRow(new Object[]{IDCAUSA, Causa,  Horas, puntos});
        }catch(Exception e){
            
        }
    }
    
    public Double Regresa2Decimales(Double Valor){
        try{
         int aux = (int)(Valor*100);//1243
            Valor = aux/100d;// 
        }catch(Exception e)
        {
            System.out.println(e.toString()+" Line:149");
        }
        return Valor;
      }
    
    public void SacarEficienciaObj(){
        try{
            rs= Principal.cn.GetConsulta("SELECT\n" +
                "eficsemana.SEMANA,\n" +
                "DATEDIFF(eficsemana.SEMANA, now()),\n" +
                "eficsemana.CAD"+cbxCadena.getSelectedItem().toString()+cbxTurno.getSelectedItem().toString()+" \n" +
                "FROM\n" +
                "eficsemana \n" +
                "where \n" +
                "DATEDIFF(eficsemana.SEMANA, date('"+RegresaFecha()+"'))<1 and DATEDIFF(eficsemana.SEMANA, date('"+RegresaFecha()+"'))>=-6");
            if(rs.next())
            {
                FechaSemana=rs.getString("semana");
                EficiencObjetivo=rs.getDouble(3);
                lblObetivoCadena.setText(EficiencObjetivo.toString() + " %");
            }
        }catch(Exception e){
            System.out.println(e.toString()+ "LINE:SacarEficienciaObj");
        }
    }
     
    public  void Enlazardgv(){
        modeloCausas=new DefaultTableModel(){

            @Override
            public boolean isCellEditable(int i, int i1) {
                return  false;//To change body of generated methods, choose Tools | Templates.
            }
            
        };
        modeloCausas.setColumnIdentifiers(new Object[]{"IDCAUSA", "CAUSA", "HORAS", "PUNTOS"});
        tblCodigos.setModel(modeloCausas);
        tblCodigos.getColumnModel().getColumn(0).setMaxWidth(0);
        tblCodigos.getColumnModel().getColumn(0).setMinWidth(0);
        tblCodigos.getColumnModel().getColumn(0).setPreferredWidth(0);
        tblCodigos.getColumnModel().getColumn(1).setMaxWidth(300);
        tblCodigos.getColumnModel().getColumn(1).setMinWidth(300);
        tblCodigos.getColumnModel().getColumn(1).setPreferredWidth(300);
    }
   
    public void Enlazardetalle(){
        try {
            rs=Principal.cn.GetConsulta("SELECT\n" +
                "detalleeficiencialabor.fecha,\n" +
                "detalleeficiencialabor.cadena,\n" +
                "detalleeficiencialabor.Turno,\n" +
                "detalleeficiencialabor.idcausa,\n" +
                "detalleeficiencialabor.descripcion,\n" +
                "detalleeficiencialabor.horas,\n" +
                "detalleeficiencialabor.puntos\n" +
                "FROM\n" +
                "detalleeficiencialabor\n" +
                "WHERE\n" +
                "date(detalleeficiencialabor.fecha)='"+RegresaFecha()+"' and cadena='"+cbxCadena.getSelectedItem().toString()+"'\n" +
                " and Turno='"+cbxTurno.getSelectedItem().toString()+"'");
            while(rs.next())
            {
             AgregarCausa(rs.getString("idcausa"), rs.getString("descripcion"), rs.getDouble("horas"), rs.getDouble("puntos"));
            }
            SacarTotal();
                
        }catch(Exception e)
        {
        }
    }
    
    public void EnlazarCbxCausas(){
        try{
           rs=Principal.cn.GetConsulta("SELECT * FROM CAUSAS");
           while(rs.next())
           {
               idcausa.add(rs.getString(1));
               cbxCausas.addItem(rs.getString(2));
           }
               
        }catch(Exception e)
        {
            System.out.println(e.toString()+ " Line:219");
        }
    }
    
    public void EnlazarCbxCadena(){
        try{
           rs=Principal.cn.GetConsulta("SELECT DISTINCT\n" +
                "codigos.CADENA\n" +
                "FROM\n" +
                "codigos");
           while(rs.next())
           {
              cbxCadena.addItem(rs.getString(1));
           }
               
        }catch(Exception e)
        {
            System.out.println(e.toString() +" Line:236");
        }
    }
    
    public boolean EstaEnTabla(){
        boolean rsp=false;
        try {
            for(int i=0; i<modeloCausas.getRowCount(); i++)
            {
             if(modeloCausas.getValueAt(i, 0).toString().equals(idcausa.get(cbxCausas.getSelectedIndex())))
             { 
                 rsp=true; 
                 break;
             }
            }
        }catch(Exception e)
        {
        }
        return rsp;
    }
     
    public Double SacarPorcentajeEfic(){ 
         Double resp=0.0;
        try{
            if(((Double)txtHrsPag.getValue())>0)
            {
                resp=Regresa2Decimales(((Double)txtHrsEmb.getValue()/(Double)txtHrsPag.getValue())*100);
                DifPorc=resp-EficiencObjetivo;
                DifHoras=((Double)txtHrsEmb.getValue()/( EficiencObjetivo/100))-(Double)txtHrsPag.getValue();
                lblDifPorc.setText(Regresa2Decimales(DifPorc).toString() + " %");
                lblDifHras.setText(Regresa2Decimales(DifHoras).toString() );
                if(resp<EficiencObjetivo)
                {
                    txtEfic.setBackground(Color.RED);
                }else if(resp==EficiencObjetivo)
                {
                    txtEfic.setBackground(Color.ORANGE);
                }else if(resp>EficiencObjetivo)
                {
                    txtEfic.setBackground(Color.GREEN);
                }
            }
            
            
        }catch(Exception e){
        }
         return resp;
     }
    
    public void Limpiar(){
        cbxCadena.setSelectedIndex(0);
        txtHoras.setValue(0.0);
    }
    
    public void LimpiarHoras(){
        lblDifHras.setText(DifHoras.toString());
        lblDifPorc.setText(DifPorc+ " %");
        txtPuntos.setValue(0);
        txtHrsEmb.setValue(0);
        txtHrsPag.setValue(0);
        EficiencObjetivo=0.0;
        txtEfic.setText("0 %" );
        lblObetivoCadena.setText("0 %");
    }
    
    public void eliminar(String fecha, String idcausa, String Cadena, String Turno){
        try{
            ArrayList<String> objetos=new  ArrayList<String>();
            objetos.add(fecha);
            objetos.add(Cadena);
            objetos.add(Turno);
            objetos.add(idcausa);
            Principal.cn.EjecutarInsert("delete from detalleeficiencialabor where date(detalleeficiencialabor.fecha)=? and cadena=? and turno=? and detalleeficiencialabor.idcausa=?", objetos);
            
        }catch(Exception e)
        {
         System.out.println(e.toString());   
        }
    }
    
    private String RegresaFecha(){
        return new SimpleDateFormat("yyyy-MM-dd").format(DatFeccha.getDate()).toString();
    }
    
    public void Exportar(String Ruta){
        DefaultTableModel modeloExportar;
        try{
           rs=Principal.cn.GetConsulta("SELECT\n" +
                "date(eficiencialabor.fechahora) as fecha,\n" +
                "eficiencialabor.cadena,\n" +
                "eficiencialabor.turno,\n" +
                "eficiencialabor.eficienciaObjetivo,\n" +
                "eficiencialabor.hrsEmb,\n" +
                "eficiencialabor.hrsReales,\n" +
                "eficiencialabor.EficienciaLabor,\n" +
                "eficiencialabor.Diferencia,\n" +
                "eficiencialabor.comentario,\n" +
                "DATEDIFF((fechahora), '2014-10-06')\n" +
                "FROM\n" +
                "eficiencialabor\n" +
                "where \n" +
                "DATEDIFF((fechahora), '"+FechaSemana+"')>=0 and DATEDIFF((fechahora), '"+FechaSemana+"')<=5 AND CADENA='"+cbxCadena.getSelectedItem().toString()+"' AND TURNO ='"+cbxTurno.getSelectedItem().toString()+"'");
               modeloExportar=new DefaultTableModel();
            modeloExportar.setColumnIdentifiers(new Object[]{"FECHA", "CADENA", "TURNO", "EFICIENCIAOBJETIVO", "hrsEmb", "hrsReales", "EficienciaLabor", "Diferencia", "COMENTARIO"});               
           while(rs.next())
           {
            modeloExportar.addRow(new Object[]{rs.getDate("fecha"), rs.getString("cadena"), rs.getString("turno"), rs.getString("eficienciaObjetivo"), rs.getString("hrsEmb"), rs.getString("hrsReales"), rs.getString("EficienciaLabor"), rs.getString("Diferencia"), rs.getString("comentario")});
           }
            ExcelImpactos excelImpactos=new ExcelImpactos();
            excelImpactos.ExcelImpactos(Ruta, FechaSemana, modeloExportar);
        }catch(Exception e)
        {
            System.out.println(e.toString()+"LINE: EXPORTAR");
        }
    }
    
    public void copyFile(File sourceFile, File destFile) throws IOException {
    if(!destFile.exists()) {
        destFile.createNewFile();
    }
    FileChannel origen = null;
    FileChannel destino = null;
    try {
        origen = new FileInputStream(sourceFile).getChannel();
        destino = new FileOutputStream(destFile).getChannel();
 
        long count = 0;
        long size = origen.size();              
        while((count += destino.transferFrom(origen, count, size-count))<size);
    }
    finally {
        if(origen != null) {
            origen.close();
        }
        if(destino != null) {
            destino.close();
        }
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

        jPanel1 = new javax.swing.JPanel();
        pnlMenu = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        lblObetivoCadena = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblCodigos = new Compille.RXTable();
        jLabel21 = new javax.swing.JLabel();
        lblTotPtoCausa = new javax.swing.JLabel();
        lblTotHrsCausa = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtHrsPag = new javax.swing.JSpinner();
        jLabel16 = new javax.swing.JLabel();
        txtEfic = new javax.swing.JTextField();
        txtEfiCrEAL = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txtPuntos = new javax.swing.JSpinner();
        txtHoras = new javax.swing.JSpinner();
        jLabel19 = new javax.swing.JLabel();
        cbxCausas = new javax.swing.JComboBox();
        lblDifPorc = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        lblDifHras = new javax.swing.JLabel();
        btnGuardar = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtComentarios = new javax.swing.JTextArea();
        jLabel18 = new javax.swing.JLabel();
        cbxTurno = new javax.swing.JComboBox();
        cbxCadena = new javax.swing.JComboBox();
        DatFeccha = new com.toedter.calendar.JDateChooser();
        btnActualizar = new javax.swing.JButton();
        btnExportar = new javax.swing.JButton();
        txtHrsEmb = new javax.swing.JSpinner();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("CAPTURA DE IMPACTOS DIARIOS");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlMenu.setBackground(new java.awt.Color(51, 204, 255));

        jLabel6.setFont(new java.awt.Font("Cambria", 1, 68)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("REV.EFICIENCIA LABOR");

        javax.swing.GroupLayout pnlMenuLayout = new javax.swing.GroupLayout(pnlMenu);
        pnlMenu.setLayout(pnlMenuLayout);
        pnlMenuLayout.setHorizontalGroup(
            pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMenuLayout.createSequentialGroup()
                .addGap(82, 82, 82)
                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 1026, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlMenuLayout.setVerticalGroup(
            pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMenuLayout.createSequentialGroup()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 8, Short.MAX_VALUE))
        );

        jPanel1.add(pnlMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1120, 60));

        lblObetivoCadena.setBackground(new java.awt.Color(51, 255, 255));
        lblObetivoCadena.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        lblObetivoCadena.setAutoscrolls(true);
        lblObetivoCadena.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(51, 255, 255), new java.awt.Color(51, 255, 255), new java.awt.Color(51, 255, 255), new java.awt.Color(0, 255, 255)));
        jPanel1.add(lblObetivoCadena, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 70, 200, 40));

        jLabel5.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel5.setText("CADENA:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 80, 110, -1));

        jLabel3.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel3.setText("Fecha:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 100, 273, 30));

        jLabel8.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel8.setText("TURNO:");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 80, 100, -1));

        jLabel10.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel10.setText("OBJETIVO CAD:");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 200, -1));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
        tblCodigos.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tblCodigos.setSelectAllForEdit(true);
        tblCodigos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCodigosMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblCodigos);

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(41, 19, 488, 261));

        jLabel21.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel21.setText("TOTAL:");
        jPanel2.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 290, 90, -1));

        lblTotPtoCausa.setBackground(new java.awt.Color(51, 255, 255));
        lblTotPtoCausa.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        lblTotPtoCausa.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTotPtoCausa.setAutoscrolls(true);
        lblTotPtoCausa.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(51, 255, 255), new java.awt.Color(51, 255, 255), new java.awt.Color(51, 255, 255), new java.awt.Color(0, 255, 255)));
        jPanel2.add(lblTotPtoCausa, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 280, 110, 40));

        lblTotHrsCausa.setBackground(new java.awt.Color(51, 255, 255));
        lblTotHrsCausa.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        lblTotHrsCausa.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTotHrsCausa.setAutoscrolls(true);
        lblTotHrsCausa.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(51, 255, 255), new java.awt.Color(51, 255, 255), new java.awt.Color(51, 255, 255), new java.awt.Color(0, 255, 255)));
        jPanel2.add(lblTotHrsCausa, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 280, 110, 40));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 250, 560, 330));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));

        jLabel12.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel12.setText("HRS REALES:");

        jLabel15.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel15.setText("HRS EMB.:");

        txtHrsPag.setFont(new java.awt.Font("Cambria", 1, 24)); // NOI18N
        txtHrsPag.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), Double.valueOf(0.0d), null, Double.valueOf(5.0d)));
        txtHrsPag.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                txtHrsPagStateChanged(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel16.setText("EF. LABOR:");

        txtEfic.setEditable(false);
        txtEfic.setBackground(new java.awt.Color(255, 51, 51));
        txtEfic.setFont(new java.awt.Font("Cambria", 1, 24)); // NOI18N
        txtEfic.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtEfic.setFocusable(false);

        txtEfiCrEAL.setEditable(false);
        txtEfiCrEAL.setBackground(new java.awt.Color(255, 51, 51));
        txtEfiCrEAL.setFont(new java.awt.Font("Cambria", 1, 24)); // NOI18N
        txtEfiCrEAL.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtEfiCrEAL.setFocusable(false);

        jLabel17.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel17.setText("OBJ. REAL: ");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtHrsPag, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtEfic, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtEfiCrEAL, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(txtHrsPag, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel17)
                                .addComponent(txtEfiCrEAL, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel16)
                                .addComponent(txtEfic, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(34, 34, 34))))
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 130, 1090, -1));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel13.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel13.setText("CAUSA:");
        jPanel4.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 105, 96, -1));

        jLabel14.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel14.setText("HORAS:");
        jPanel4.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(135, 147, -1, -1));

        txtPuntos.setFont(new java.awt.Font("Cambria", 1, 24)); // NOI18N
        txtPuntos.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), Double.valueOf(0.0d), null, Double.valueOf(5.0d)));
        txtPuntos.setEnabled(false);
        txtPuntos.setFocusable(false);
        jPanel4.add(txtPuntos, new org.netbeans.lib.awtextra.AbsoluteConstraints(238, 187, 152, -1));

        txtHoras.setFont(new java.awt.Font("Cambria", 1, 24)); // NOI18N
        txtHoras.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), Double.valueOf(0.0d), null, Double.valueOf(5.0d)));
        txtHoras.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                txtHorasStateChanged(evt);
            }
        });
        jPanel4.add(txtHoras, new org.netbeans.lib.awtextra.AbsoluteConstraints(238, 144, 152, -1));

        jLabel19.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel19.setText("PUNTOS:");
        jPanel4.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 190, -1, -1));

        cbxCausas.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cbxCausas.setAutoscrolls(true);
        cbxCausas.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxCausasItemStateChanged(evt);
            }
        });
        jPanel4.add(cbxCausas, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 100, 385, 29));

        lblDifPorc.setBackground(new java.awt.Color(51, 255, 255));
        lblDifPorc.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        lblDifPorc.setAutoscrolls(true);
        lblDifPorc.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(51, 255, 255), new java.awt.Color(51, 255, 255), new java.awt.Color(51, 255, 255), new java.awt.Color(0, 255, 255)));
        jPanel4.add(lblDifPorc, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 50, 140, 40));

        jLabel11.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel11.setText("%");
        jPanel4.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 60, 70, -1));

        lblDifHras.setBackground(new java.awt.Color(51, 255, 255));
        lblDifHras.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        lblDifHras.setAutoscrolls(true);
        lblDifHras.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(51, 255, 255), new java.awt.Color(51, 255, 255), new java.awt.Color(51, 255, 255), new java.awt.Color(0, 255, 255)));
        jPanel4.add(lblDifHras, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 10, 140, 40));

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/success-icon.png"))); // NOI18N
        btnGuardar.setText("AGREGAR");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel4.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 250, -1, -1));

        jLabel20.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel20.setText("DIFERENCIA:");
        jPanel4.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 20, 150, -1));

        jLabel22.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel22.setText("HRS.");
        jPanel4.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 10, 70, -1));

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(51, 51, 255));
        jLabel23.setText("HC NECESARIO");
        jPanel4.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, -1));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 250, 510, 330));

        txtComentarios.setColumns(20);
        txtComentarios.setRows(5);
        jScrollPane1.setViewportView(txtComentarios);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 590, 570, 100));

        jLabel18.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel18.setText("COMENTARIOS:");
        jPanel1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 610, -1, -1));

        cbxTurno.setBackground(new java.awt.Color(51, 255, 255));
        cbxTurno.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cbxTurno.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "A", "B" }));
        cbxTurno.setAutoscrolls(true);
        cbxTurno.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxTurnoItemStateChanged(evt);
            }
        });
        jPanel1.add(cbxTurno, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 70, 70, 40));

        cbxCadena.setBackground(new java.awt.Color(51, 255, 255));
        cbxCadena.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cbxCadena.setAutoscrolls(true);
        cbxCadena.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxCadenaItemStateChanged(evt);
            }
        });
        cbxCadena.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxCadenaActionPerformed(evt);
            }
        });
        jPanel1.add(cbxCadena, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 70, 70, 40));

        DatFeccha.setDateFormatString("yyyy, MMM d");
        DatFeccha.setFocusCycleRoot(true);
        DatFeccha.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                DatFecchaPropertyChange(evt);
            }
        });
        jPanel1.add(DatFeccha, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 80, 170, 30));

        btnActualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/save-icon.png"))); // NOI18N
        btnActualizar.setText("ACTUALIZAR");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });
        jPanel1.add(btnActualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 590, -1, 90));

        btnExportar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/excel-xls-icon.png"))); // NOI18N
        btnExportar.setText("EXPORTAR");
        btnExportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportarActionPerformed(evt);
            }
        });
        jPanel1.add(btnExportar, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 590, -1, 90));

        txtHrsEmb.setFont(new java.awt.Font("Cambria", 1, 24)); // NOI18N
        txtHrsEmb.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(0.0d), Double.valueOf(0.0d), null, Double.valueOf(5.0d)));
        txtHrsEmb.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                txtHrsEmbStateChanged(evt);
            }
        });
        txtHrsEmb.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtHrsEmbFocusLost(evt);
            }
        });
        jPanel1.add(txtHrsEmb, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 160, 152, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 710, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbxCausasItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxCausasItemStateChanged
        // TODO add your handling code here:
     //   JOptionPane.showMessageDialog(null, idcausa.get(cbxCausas.getSelectedIndex()), "", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_cbxCausasItemStateChanged

    private void cbxTurnoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxTurnoItemStateChanged
        // TODO add your handling code here:
          try{
            //  if(BandActivarCbx)
                    InsertarFechaImpacto();
        }catch(Exception e){
            //System.out.println(e.toString());
        }
    }//GEN-LAST:event_cbxTurnoItemStateChanged

    private void cbxCadenaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxCadenaItemStateChanged
        // TODO add your handling code here:
        try{
            if(BandActivarCbx) 
            {
                InsertarFechaImpacto();
              //  Limpiar();
               
            }
        }catch(Exception e){
            //System.out.println(e.toString());
        }
    }//GEN-LAST:event_cbxCadenaItemStateChanged

    private void txtHrsEmbStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_txtHrsEmbStateChanged
        // TODO add your handling code here:
        txtEfic.setText(SacarPorcentajeEfic().toString() + " %");
    }//GEN-LAST:event_txtHrsEmbStateChanged

    private void txtHrsPagStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_txtHrsPagStateChanged
        // TODO add your handling code here:
        txtEfic.setText(SacarPorcentajeEfic().toString() + " %");
    }//GEN-LAST:event_txtHrsPagStateChanged

    private void txtHrsEmbFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHrsEmbFocusLost
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txtHrsEmbFocusLost

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
           if(((Double)txtHoras.getValue()>0) && (lblDifHras.getText().length()>0) && (!EstaEnTabla())) {
               {  
                  InsertarCausaBD(idcausa.get(cbxCausas.getSelectedIndex()), cbxCausas.getSelectedItem().toString(), Double.parseDouble(txtHoras.getValue().toString()), Double.parseDouble(txtPuntos.getValue().toString()));
                  Enlazardgv();
                  Enlazardetalle();
                  txtHoras.setValue(0.0);
                  txtPuntos.setValue(0.0);
                  txtHoras.requestFocus();
               }
           }else
           {
               JOptionPane.showMessageDialog(null, "Algunos datos estan vacios o ya se encuentran en tabla...", "Error", JOptionPane.WARNING_MESSAGE);
           }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void txtHorasStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_txtHorasStateChanged
        // TODO add your handling code here:
        if((((Double)txtHrsPag.getValue())>0) && (((Double)txtHoras.getValue())>0))
        {
            txtPuntos.setValue(Regresa2Decimales(((Double)txtHoras.getValue()/(Double)txtHrsPag.getValue())*100));
            SacarTotal();
        }
    }//GEN-LAST:event_txtHorasStateChanged

    private void tblCodigosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCodigosMouseClicked
        // TODO add your handling code here:
        if(evt.getClickCount()==2)
        {
           int rsp = JOptionPane.showConfirmDialog(null, "DESEA ELIMINAR LA CAUSA?", "CONFIRMACION", JOptionPane.YES_NO_OPTION);
           if(rsp==JOptionPane.YES_OPTION)
           {
               eliminar(RegresaFecha(), modeloCausas.getValueAt(tblCodigos.rowAtPoint(evt.getPoint()),0) .toString(), cbxCadena.getSelectedItem().toString(), cbxTurno.getSelectedItem().toString());           
               lblTotHrsCausa.setText("0");
               lblTotPtoCausa.setText("0");
               Enlazardgv();
               Enlazardetalle();
               
           }  
        }
    }//GEN-LAST:event_tblCodigosMouseClicked

    private void DatFecchaPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_DatFecchaPropertyChange
        // TODO add your handling code here:
        try{
            if(BandActivarCbx)
                InsertarFechaImpacto();
        }catch(Exception e){
            System.out.println(e.toString() +" Line:798");
        }
    }//GEN-LAST:event_DatFecchaPropertyChange

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        // TODO add your handling code here:
        try{
            Actualizar();
            
        } catch(Exception e)
        {
            
        }
           
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnExportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportarActionPerformed
        // TODO add your handling code here:
        String curDir = System.getProperty("user.dir")+"\\dist\\lib\\Archivo Impactos.xls";
       File file=new File(curDir);
        if(file.exists())
       { 
            try {
                copyFile(new File(curDir), new File(System.getProperty("user.home")+"/desktop/Impactos.xls"));
                Exportar(System.getProperty("user.home")+"/desktop/Impactos.xls");
            } catch (IOException ex) {
                System.out.println(ex.toString()+ "BTNEXPORTAR");
            }
       }else
           JOptionPane.showMessageDialog(null, "ERROR AL CARGAR EL FORMATO ARCHIVO IMPACTOS...", "", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnExportarActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        if(JOptionPane.showConfirmDialog (null, "Desea guardar los cambios realizados?","Warning",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)
        {
            Actualizar();
        }
    }//GEN-LAST:event_formWindowClosing

    private void cbxCadenaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxCadenaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxCadenaActionPerformed

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
            java.util.logging.Logger.getLogger(CapturaImpactos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CapturaImpactos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CapturaImpactos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CapturaImpactos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CapturaImpactos().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser DatFeccha;
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnExportar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JComboBox cbxCadena;
    private javax.swing.JComboBox cbxCausas;
    private javax.swing.JComboBox cbxTurno;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblDifHras;
    private javax.swing.JLabel lblDifPorc;
    private javax.swing.JLabel lblObetivoCadena;
    private javax.swing.JLabel lblTotHrsCausa;
    private javax.swing.JLabel lblTotPtoCausa;
    private javax.swing.JPanel pnlMenu;
    private Compille.RXTable tblCodigos;
    private javax.swing.JTextArea txtComentarios;
    private javax.swing.JTextField txtEfiCrEAL;
    private javax.swing.JTextField txtEfic;
    private javax.swing.JSpinner txtHoras;
    private javax.swing.JSpinner txtHrsEmb;
    private javax.swing.JSpinner txtHrsPag;
    private javax.swing.JSpinner txtPuntos;
    // End of variables declaration//GEN-END:variables
}
