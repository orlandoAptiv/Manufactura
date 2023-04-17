/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package manufactura;
import Clases.Conection;
import static java.awt.image.ImageObserver.WIDTH;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author gzld6k
 */
public class calculo_layoff extends javax.swing.JFrame {

   DefaultTableModel  modelo;
   
    /**
     * Creates new form calculo_layoff
     */
    public calculo_layoff(String Cadena) {
       
        
         try {
            initComponents();
           
            Principal.cn=new Conection();
           
            
            } catch (Exception ex) {
            System.out.println(ex.toString()+" no ahi conection");
          
            }
        
        // cb_turno.setActionCommand(cb_turno.getActionCommand()+ Cadena);
        //Cadena =cb_turno.getSelectedItem().toString();
      
        // cb_turno.getSelectedItem().toString()+ Cadena;
          EnlazarDgvA(Cadena);
          cb_codigo.setSelectedIndex(0);
         // EnlazarDgvB(Cadena);
         
         
       
    }
    
       public void EnlazarDgvA(String Cadena)
    {
        try {
          modelo =new DefaultTableModel(){
          @Override
           public boolean isCellEditable(int row, int column) {
                     //all cells false
                return true;
              }
          };
          
          modelo.setColumnIdentifiers(new Object[]{"codigo", "linea","ta_mod", "lunes", "martes", "miercoles",  "jueves", "viernes","    ", "lunes", "martes", "miercoles", "jueves", "viernes"});
        // String query= "SELECT  DISTINCT codigos.CODIGO,codigos.LINEA,layoff_A.T_A_MOD,layoff_A.LUNES,layoff_A.MARTES,layoff_A.MIERCOLES,layoff_A.JUEVES,layoff_A.VIERNES,layoff_A.SEMANA, FROM codigos ,layoff_A"; 
          String query= "SELECT layoff_a.CODIGO,layoff_a.LINEA,layoff_a.T_A_MOD,layoff_a.LUNES,layoff_a.MARTES,layoff_a.MIERCOLES,layoff_a.JUEVES,layoff_a.VIERNES,layoff_a.SEMANA FROM layoff_a";
          tbl_layoff.setModel(modelo);
          
          tbl_layoff.getColumnModel().getColumn(0).setMaxWidth(110);
          tbl_layoff.getColumnModel().getColumn(0).setMinWidth(110);
          tbl_layoff.getColumnModel().getColumn(0).setPreferredWidth(110);
          tbl_layoff.getColumnModel().getColumn(1).setMaxWidth(50);
          tbl_layoff.getColumnModel().getColumn(1).setMinWidth(50);
          tbl_layoff.getColumnModel().getColumn(1).setPreferredWidth(50);
          tbl_layoff.getColumnModel().getColumn(8).setMaxWidth(40);
          tbl_layoff.getColumnModel().getColumn(8).setMinWidth(40);
          tbl_layoff.getColumnModel().getColumn(8).setPreferredWidth(40);
          
         ResultSet  rs=Principal.cn.GetConsulta(query);
            while(rs.next())
            {
               modelo.addRow(new Object[]{rs.getString("CODIGO"), rs.getString("LINEA"),rs.getString("T_A_MOD"), rs.getString("LUNES"),  rs.getString("MARTES"), rs.getString("MIERCOLES"), rs.getString("JUEVES"), rs.getString("VIERNES"), rs.getString("SEMANA")});
            }
          
        }catch(Exception e)
        {
            System.out.println(e.toString());
        }
    }
       
      
   
         public void actualizar_tabla()
         {
             
              if((JOptionPane.showConfirmDialog(null, "Esta seguro que desea actualizar?", "Confirmar", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION))
        {
            ArrayList<String> listaInsert=new ArrayList<String>();
            listaInsert.add(txt_mod.getText().toString());
            listaInsert.add(txt_lunes.getText().toString());
            listaInsert.add(txt_martes.getText().toString());
            listaInsert.add(txt_miercoles.getText().toString());
            listaInsert.add(txt_jueves.getText().toString());
            listaInsert.add(txt_viernes.getText().toString());
            //listaInsert.add ();
           
        //  listaInsert.add(Principal.UsuarioLogeado.codigo);
          //  listaInsert.add(dm.Codigo.Idcodigo);
           if( Principal.cn.EjecutarInsert("update  layoff_a SET T_A_MOD=?, LUNES=?, MARTES=?, MIERCOLES=?, JUEVES=?, VIERNES=?  WHERE CODIGO='" +cb_codigo.getSelectedItem().toString()+"'", listaInsert))
           {
             //  JOptionPane.showMessageDialog(null, "Datos Actualizados", "Confirmacion", JOptionPane.INFORMATION_MESSAGE);
               //this.dispose();
               EnlazarDgvA(null);
               txt_mod.setText(null);
               txt_lunes.setText(null);
               txt_martes.setText(null);
               txt_miercoles.setText(null);
               txt_jueves.setText(null);
               txt_viernes.setText(null);
           }
           else
           {
               JOptionPane.showMessageDialog(null, "Error, al guardar", "Confirmacion", JOptionPane.WARNING_MESSAGE);
           }
           
        }

             
             
         }
//      
//         private String RegresaFecha(){
//        return new SimpleDateFormat("yyyy-MM-dd").format(DatFeccha.getDate()).toString();
//    }
         
         public void TOTAL_TA()
         {
            String val1, val2,val3,val4,val5,val6;
            
             try{
                 //HC NECESARIO
            ResultSet rs=Principal.cn.GetConsulta("SELECT sum(T_A_MOD) from manufactura.layoff_a");
            ResultSet rs2=Principal.cn.GetConsulta("SELECT  sum(T_A_MOD)AS TOTAL from manufactura.layoff_a where idcodigo between '0' and '57' ");
           ResultSet rs3=Principal.cn.GetConsulta("SELECT  sum(T_A_MOD)AS TOTAL from manufactura.layoff_a where idcodigo between '59' and '89' ");
          //LUNES
           ResultSet rs4=Principal.cn.GetConsulta("SELECT  sum(T_A_MOD*LUNES)AS TOTAL from manufactura.layoff_a where idcodigo between '0' and '57'");
           ResultSet rs5=Principal.cn.GetConsulta("SELECT  sum(T_A_MOD*LUNES)AS TOTAL from manufactura.layoff_a where idcodigo between '59' and '89'");
           ResultSet rs6=Principal.cn.GetConsulta("SELECT  sum(T_A_MOD*LUNES)AS TOTAL from manufactura.layoff_a");
           //MARTES
          ResultSet rs7=Principal.cn.GetConsulta  ("SELECT  sum(T_A_MOD*MARTES)AS TOTAL from manufactura.layoff_a where idcodigo between '0' and '57'"); 
          ResultSet rs8=Principal.cn.GetConsulta  ("SELECT  sum(T_A_MOD*MARTES)AS TOTAL from manufactura.layoff_a where idcodigo between '59' and '89'"); 
          ResultSet rs9=Principal.cn.GetConsulta  ("SELECT  sum(T_A_MOD*MARTES)AS TOTAL from manufactura.layoff_a"); 
              
          //MIERCOLES
          ResultSet rs10=Principal.cn.GetConsulta  ("SELECT  sum(T_A_MOD*MIERCOLES)AS TOTAL from manufactura.layoff_a where idcodigo between '0' and '57'"); 
          ResultSet rs11=Principal.cn.GetConsulta  ("SELECT  sum(T_A_MOD*MIERCOLES)AS TOTAL from manufactura.layoff_a where idcodigo between '59' and '89'"); 
          ResultSet rs12=Principal.cn.GetConsulta  ("SELECT  sum(T_A_MOD*MIERCOLES)AS TOTAL from manufactura.layoff_a"); 
          
          ////JUEVES
          ResultSet rs13=Principal.cn.GetConsulta  ("SELECT  sum(T_A_MOD*JUEVES)AS TOTAL from manufactura.layoff_a where idcodigo between '0' and '57'"); 
          ResultSet rs14=Principal.cn.GetConsulta  ("SELECT  sum(T_A_MOD*JUEVES)AS TOTAL from manufactura.layoff_a where idcodigo between '59' and '89'"); 
          ResultSet rs15=Principal.cn.GetConsulta  ("SELECT  sum(T_A_MOD*JUEVES)AS TOTAL from manufactura.layoff_a");
          
           ////VIERNES
          ResultSet rs16=Principal.cn.GetConsulta  ("SELECT  sum(T_A_MOD*VIERNES)AS TOTAL from manufactura.layoff_a where idcodigo between '0' and '57'"); 
          ResultSet rs17=Principal.cn.GetConsulta  ("SELECT  sum(T_A_MOD*VIERNES)AS TOTAL from manufactura.layoff_a where idcodigo between '59' and '89'"); 
          ResultSet rs18=Principal.cn.GetConsulta  ("SELECT  sum(T_A_MOD*VIERNES)AS TOTAL from manufactura.layoff_a");
           if(rs.next() && rs2.next() && rs3.next() && rs4.next()&& rs5.next()&& rs6.next()&& rs7.next() && rs8.next() && rs9.next()&& rs10.next()&& rs11.next()&& rs12.next()&& rs13.next()&& rs14.next()&& rs15.next()&& rs16.next()&& rs17.next()&& rs18.next())
          {
           //HC NECESARIO   
                 lbl_totalTurno.setText(rs.getString(WIDTH));
                dpo_25962.setText(rs2.getString(WIDTH));
                dpto_25963.setText(rs3.getString(WIDTH));
                
                //LUNES
                dpo_62.setText(rs4.getString(WIDTH));
                dpto_63.setText(rs5.getString(WIDTH));
                total_tur.setText(rs6.getString(WIDTH));
                
                //MARTES
                dep62ma_loff.setText(rs7.getString(WIDTH));
                dep63ma_loff.setText(rs8.getString(WIDTH));
                Tdep_ma.setText(rs9.getString(WIDTH));
                
                //MIERCOLES
                dep62mi_loff.setText(rs10.getString(WIDTH));
                dep63mi_loff.setText(rs11.getString(WIDTH));
                Tdep_mi.setText(rs12.getString(WIDTH));
                
                //JUEVES
                dep62jue_loff.setText(rs13.getString(WIDTH));
                dep63jue_loff.setText(rs14.getString(WIDTH));
                Tdep_jue.setText(rs15.getString(WIDTH));
                
                 //VIERNES
                dep62vie_loff.setText(rs16.getString(WIDTH));
                dep63vie_loff.setText(rs17.getString(WIDTH));
                Tdep_vie.setText(rs18.getString(WIDTH));
                
                
           //   
           //saca valor de HC laborando en depa_25962    lunes 
           val1=dpo_25962.getText();
           val2=dpo_62.getText();
           int x = Integer.parseInt(val1);
           int y = Integer.parseInt(val2);
           int resta= x-y;
           String resultado=String.valueOf(resta);
           depa_62.setText(resultado);
           
           //saca valor de HC laborando en depa_25963 lunes
             val3=dpto_25963.getText();
           val4=dpto_63.getText();
           int a = Integer.parseInt(val3);
           int b = Integer.parseInt(val4);
           int resta2= a-b;
           String resultado2=String.valueOf(resta2);
           depa_63.setText(resultado2);
           
           
            //saca TOTAL de HC laborando lunes
           val5=depa_62.getText();
           val6=depa_63.getText();
           int dep62 = Integer.parseInt(val5);
           int dep63 = Integer.parseInt(val6);
           int suma= dep62+dep63;
           String resultado3=String.valueOf(suma);
           total_turno.setText(resultado3);
           
           //saca valor de HC laborando en depa_25962    martes
           String val7=dpo_25962.getText();
            String val8=dep62ma_loff.getText();
           int x1 = Integer.parseInt(val7);
           int y1 = Integer.parseInt(val8);
           int resta3= x1-y1;
           String resultado4=String.valueOf(resta3);
           depa62_marts.setText(resultado4);
               
           String val9=dpto_25963.getText();
           String val10=dep63ma_loff.getText();
           int x2 = Integer.parseInt(val9);
           int y2 = Integer.parseInt(val10);
           int resta4= x2-y2;
           String resultado5=String.valueOf(resta4);
           depa63_marts.setText(resultado5);
           
           String val11=depa62_marts.getText();
           String val12=depa63_marts.getText();
           int x3 = Integer.parseInt(val11);
           int y3 = Integer.parseInt(val12);
           int suma2= x3+y3;
           String resultado6=String.valueOf(suma2);
           total_turnoMarts.setText(resultado6);
           
           
            //saca valor de HC laborando en depa_25962    miercoles
           String val13=dpo_25962.getText();
            String val14=dep62mi_loff.getText();
           int x4 = Integer.parseInt(val13);
           int y4 = Integer.parseInt(val14);
           int resta5= x4-y4;
           String resultado7=String.valueOf(resta5);
           depa62_mierc.setText(resultado7);
               
           String val16=dpto_25963.getText();
           String val17=dep63mi_loff.getText();
           int x5 = Integer.parseInt(val16);
           int y5 = Integer.parseInt(val17);
           int resta6= x5-y5;
           String resultado8=String.valueOf(resta6);
           depa63_mierc.setText(resultado8);
           
           String val18=depa62_mierc.getText();
           String val19=depa63_mierc.getText();
           int x6 = Integer.parseInt(val18);
           int y6 = Integer.parseInt(val19);
           int suma3= x6+y6;
           String resultado9=String.valueOf(suma3);
           total_turnoMierc.setText(resultado9);
           
           
             //saca valor de HC laborando en depa_25962    jueves
           String val20=dpo_25962.getText();
            String val21=dep62jue_loff.getText();
           int x7 = Integer.parseInt(val20);
           int y7 = Integer.parseInt(val21);
           int resta7= x7-y7;
           String resultado10=String.valueOf(resta7);
           depa62_jue.setText(resultado10);
               
           String val22=dpto_25963.getText();
           String val23=dep63jue_loff.getText();
           int x8 = Integer.parseInt(val22);
           int y8 = Integer.parseInt(val23);
           int resta8= x8-y8;
           String resultado11=String.valueOf(resta8);
           depa63_jue.setText(resultado11);
           
           String val24=depa62_jue.getText();
           String val25=depa63_jue.getText();
           int x9 = Integer.parseInt(val24);
           int y9 = Integer.parseInt(val25);
           int suma4= x9+y9;
           String resultado12=String.valueOf(suma4);
           total_turnojue.setText(resultado12);
           
           
           
             //saca valor de HC laborando en depa_25962   viernes
           String val26=dpo_25962.getText();
            String val27=dep62vie_loff.getText();
           int x10 = Integer.parseInt(val26);
           int y10 = Integer.parseInt(val27);
           int resta9= x10-y10;
           String resultado13=String.valueOf(resta9);
           depa62_vie.setText(resultado13);
               
           String val28=dpto_25963.getText();
           String val29=dep63vie_loff.getText();
           int x11 = Integer.parseInt(val28);
           int y11 = Integer.parseInt(val29);
           int resta10= x11-y11;
           String resultado14=String.valueOf(resta10);
           depa63_vie.setText(resultado14);
           
           String val30=depa62_vie.getText();
           String val31=depa63_vie.getText();
           int x12 = Integer.parseInt(val30);
           int y12 = Integer.parseInt(val31);
           int suma5= x12+y12;
           String resultado15=String.valueOf(suma5);
           total_turnovie.setText(resultado15);
           
           
           
           
           
            }
        }catch(Exception e)
       {
            System.out.println(e.toString());
        }
             
         }
         
         public void total_dept62(){
             
    
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
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_layoff = new javax.swing.JTable();
        btnActualizar = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txt_lunes = new javax.swing.JTextField();
        txt_martes = new javax.swing.JTextField();
        txt_miercoles = new javax.swing.JTextField();
        txt_jueves = new javax.swing.JTextField();
        txt_viernes = new javax.swing.JTextField();
        txt_mod = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        lbl_totalTurno = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        dpto_25963 = new javax.swing.JLabel();
        dpo_25962 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        dpo_62 = new javax.swing.JLabel();
        total_tur = new javax.swing.JLabel();
        dpto_63 = new javax.swing.JLabel();
        jLabel146 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jPanel30 = new javax.swing.JPanel();
        jLabel131 = new javax.swing.JLabel();
        jLabel132 = new javax.swing.JLabel();
        jLabel133 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        depa_63 = new javax.swing.JLabel();
        depa_62 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        total_turno = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        dep63mi_loff = new javax.swing.JLabel();
        dep62mi_loff = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        Tdep_mi = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        depa63_mierc = new javax.swing.JLabel();
        depa62_mierc = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        total_turnoMierc = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jLabel68 = new javax.swing.JLabel();
        jLabel69 = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        dep63jue_loff = new javax.swing.JLabel();
        dep62jue_loff = new javax.swing.JLabel();
        jLabel85 = new javax.swing.JLabel();
        Tdep_jue = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        jLabel73 = new javax.swing.JLabel();
        jLabel74 = new javax.swing.JLabel();
        jLabel75 = new javax.swing.JLabel();
        jLabel76 = new javax.swing.JLabel();
        dep63vie_loff = new javax.swing.JLabel();
        dep62vie_loff = new javax.swing.JLabel();
        jLabel90 = new javax.swing.JLabel();
        Tdep_vie = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        jLabel78 = new javax.swing.JLabel();
        jLabel79 = new javax.swing.JLabel();
        jLabel80 = new javax.swing.JLabel();
        jLabel81 = new javax.swing.JLabel();
        dep63ma_loff = new javax.swing.JLabel();
        dep62ma_loff = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        Tdep_ma = new javax.swing.JLabel();
        jPanel23 = new javax.swing.JPanel();
        jLabel98 = new javax.swing.JLabel();
        jLabel99 = new javax.swing.JLabel();
        jLabel100 = new javax.swing.JLabel();
        jLabel101 = new javax.swing.JLabel();
        depa63_jue = new javax.swing.JLabel();
        depa62_jue = new javax.swing.JLabel();
        jLabel115 = new javax.swing.JLabel();
        total_turnojue = new javax.swing.JLabel();
        jPanel24 = new javax.swing.JPanel();
        jLabel103 = new javax.swing.JLabel();
        jLabel104 = new javax.swing.JLabel();
        jLabel105 = new javax.swing.JLabel();
        jLabel106 = new javax.swing.JLabel();
        depa63_vie = new javax.swing.JLabel();
        depa62_vie = new javax.swing.JLabel();
        jLabel120 = new javax.swing.JLabel();
        total_turnovie = new javax.swing.JLabel();
        jPanel25 = new javax.swing.JPanel();
        jLabel108 = new javax.swing.JLabel();
        jLabel109 = new javax.swing.JLabel();
        jLabel110 = new javax.swing.JLabel();
        jLabel111 = new javax.swing.JLabel();
        depa63_marts = new javax.swing.JLabel();
        depa62_marts = new javax.swing.JLabel();
        jLabel125 = new javax.swing.JLabel();
        total_turnoMarts = new javax.swing.JLabel();
        jPanel29 = new javax.swing.JPanel();
        jLabel128 = new javax.swing.JLabel();
        jLabel129 = new javax.swing.JLabel();
        jLabel130 = new javax.swing.JLabel();
        jPanel41 = new javax.swing.JPanel();
        jLabel164 = new javax.swing.JLabel();
        jLabel165 = new javax.swing.JLabel();
        jLabel166 = new javax.swing.JLabel();
        jPanel31 = new javax.swing.JPanel();
        jLabel134 = new javax.swing.JLabel();
        jLabel135 = new javax.swing.JLabel();
        jLabel136 = new javax.swing.JLabel();
        jPanel32 = new javax.swing.JPanel();
        jLabel137 = new javax.swing.JLabel();
        jLabel138 = new javax.swing.JLabel();
        jLabel139 = new javax.swing.JLabel();
        jPanel33 = new javax.swing.JPanel();
        jLabel140 = new javax.swing.JLabel();
        jLabel141 = new javax.swing.JLabel();
        jLabel142 = new javax.swing.JLabel();
        jPanel34 = new javax.swing.JPanel();
        jLabel143 = new javax.swing.JLabel();
        jLabel144 = new javax.swing.JLabel();
        jLabel145 = new javax.swing.JLabel();
        jPanel93 = new javax.swing.JPanel();
        jLabel377 = new javax.swing.JLabel();
        jLabel378 = new javax.swing.JLabel();
        jLabel379 = new javax.swing.JLabel();
        jPanel94 = new javax.swing.JPanel();
        jLabel380 = new javax.swing.JLabel();
        jLabel381 = new javax.swing.JLabel();
        jLabel382 = new javax.swing.JLabel();
        jPanel39 = new javax.swing.JPanel();
        jLabel158 = new javax.swing.JLabel();
        jLabel159 = new javax.swing.JLabel();
        jLabel160 = new javax.swing.JLabel();
        jPanel40 = new javax.swing.JPanel();
        jLabel161 = new javax.swing.JLabel();
        jLabel162 = new javax.swing.JLabel();
        jLabel163 = new javax.swing.JLabel();
        cb_codigo = new javax.swing.JComboBox();
        jPanel42 = new javax.swing.JPanel();
        jLabel167 = new javax.swing.JLabel();
        jLabel168 = new javax.swing.JLabel();
        jLabel169 = new javax.swing.JLabel();
        jPanel43 = new javax.swing.JPanel();
        jLabel170 = new javax.swing.JLabel();
        jLabel171 = new javax.swing.JLabel();
        jLabel172 = new javax.swing.JLabel();
        jPanel44 = new javax.swing.JPanel();
        jLabel173 = new javax.swing.JLabel();
        jLabel174 = new javax.swing.JLabel();
        jLabel175 = new javax.swing.JLabel();
        jPanel45 = new javax.swing.JPanel();
        jLabel176 = new javax.swing.JLabel();
        jLabel177 = new javax.swing.JLabel();
        jLabel178 = new javax.swing.JLabel();
        jPanel35 = new javax.swing.JPanel();
        jPanel22 = new javax.swing.JPanel();
        jLabel93 = new javax.swing.JLabel();
        jLabel94 = new javax.swing.JLabel();
        jLabel95 = new javax.swing.JLabel();
        jLabel96 = new javax.swing.JLabel();
        jLabel97 = new javax.swing.JLabel();
        depa_66 = new javax.swing.JLabel();
        depa_67 = new javax.swing.JLabel();
        jLabel147 = new javax.swing.JLabel();
        total_turno2 = new javax.swing.JLabel();
        jPanel36 = new javax.swing.JPanel();
        jLabel148 = new javax.swing.JLabel();
        jLabel149 = new javax.swing.JLabel();
        jLabel150 = new javax.swing.JLabel();
        jLabel151 = new javax.swing.JLabel();
        depa63_marts2 = new javax.swing.JLabel();
        depa62_marts2 = new javax.swing.JLabel();
        jLabel152 = new javax.swing.JLabel();
        total_turnoMarts2 = new javax.swing.JLabel();
        jPanel37 = new javax.swing.JPanel();
        jLabel153 = new javax.swing.JLabel();
        jLabel154 = new javax.swing.JLabel();
        jLabel155 = new javax.swing.JLabel();
        jLabel156 = new javax.swing.JLabel();
        dep63ma_loff2 = new javax.swing.JLabel();
        dep62ma_loff2 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        Tdep_ma2 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel157 = new javax.swing.JLabel();
        jLabel315 = new javax.swing.JLabel();
        dpo_64 = new javax.swing.JLabel();
        total_tur2 = new javax.swing.JLabel();
        dpto_65 = new javax.swing.JLabel();
        jLabel316 = new javax.swing.JLabel();
        jPanel78 = new javax.swing.JPanel();
        jLabel317 = new javax.swing.JLabel();
        jLabel318 = new javax.swing.JLabel();
        jLabel319 = new javax.swing.JLabel();
        jPanel79 = new javax.swing.JPanel();
        jLabel320 = new javax.swing.JLabel();
        jLabel321 = new javax.swing.JLabel();
        jLabel322 = new javax.swing.JLabel();
        jPanel80 = new javax.swing.JPanel();
        jLabel323 = new javax.swing.JLabel();
        jLabel324 = new javax.swing.JLabel();
        jLabel325 = new javax.swing.JLabel();
        jPanel81 = new javax.swing.JPanel();
        jLabel326 = new javax.swing.JLabel();
        jLabel327 = new javax.swing.JLabel();
        jLabel328 = new javax.swing.JLabel();
        jPanel38 = new javax.swing.JPanel();
        jLabel329 = new javax.swing.JLabel();
        jLabel330 = new javax.swing.JLabel();
        jLabel331 = new javax.swing.JLabel();
        jPanel82 = new javax.swing.JPanel();
        jLabel332 = new javax.swing.JLabel();
        jLabel333 = new javax.swing.JLabel();
        jLabel334 = new javax.swing.JLabel();
        jPanel83 = new javax.swing.JPanel();
        jLabel335 = new javax.swing.JLabel();
        jLabel336 = new javax.swing.JLabel();
        jLabel337 = new javax.swing.JLabel();
        jPanel84 = new javax.swing.JPanel();
        jLabel338 = new javax.swing.JLabel();
        jLabel339 = new javax.swing.JLabel();
        jLabel340 = new javax.swing.JLabel();
        jPanel85 = new javax.swing.JPanel();
        jLabel341 = new javax.swing.JLabel();
        jLabel342 = new javax.swing.JLabel();
        jLabel343 = new javax.swing.JLabel();
        jLabel344 = new javax.swing.JLabel();
        depa63_mierc2 = new javax.swing.JLabel();
        depa62_mierc2 = new javax.swing.JLabel();
        jLabel345 = new javax.swing.JLabel();
        total_turnoMierc2 = new javax.swing.JLabel();
        jPanel86 = new javax.swing.JPanel();
        jLabel346 = new javax.swing.JLabel();
        jLabel347 = new javax.swing.JLabel();
        jLabel348 = new javax.swing.JLabel();
        jLabel349 = new javax.swing.JLabel();
        dep63mi_loff2 = new javax.swing.JLabel();
        dep62mi_loff2 = new javax.swing.JLabel();
        jLabel350 = new javax.swing.JLabel();
        Tdep_mi2 = new javax.swing.JLabel();
        jPanel87 = new javax.swing.JPanel();
        jLabel351 = new javax.swing.JLabel();
        jLabel352 = new javax.swing.JLabel();
        jLabel353 = new javax.swing.JLabel();
        jPanel88 = new javax.swing.JPanel();
        jLabel354 = new javax.swing.JLabel();
        jLabel355 = new javax.swing.JLabel();
        jLabel356 = new javax.swing.JLabel();
        jPanel89 = new javax.swing.JPanel();
        jLabel357 = new javax.swing.JLabel();
        jLabel358 = new javax.swing.JLabel();
        jLabel359 = new javax.swing.JLabel();
        jLabel360 = new javax.swing.JLabel();
        depa63_jue2 = new javax.swing.JLabel();
        depa62_jue2 = new javax.swing.JLabel();
        jLabel361 = new javax.swing.JLabel();
        total_turnojue2 = new javax.swing.JLabel();
        jPanel90 = new javax.swing.JPanel();
        jLabel362 = new javax.swing.JLabel();
        jLabel363 = new javax.swing.JLabel();
        jLabel364 = new javax.swing.JLabel();
        jLabel365 = new javax.swing.JLabel();
        depa63_vie2 = new javax.swing.JLabel();
        depa62_vie2 = new javax.swing.JLabel();
        jLabel366 = new javax.swing.JLabel();
        total_turnovie2 = new javax.swing.JLabel();
        jPanel91 = new javax.swing.JPanel();
        jLabel367 = new javax.swing.JLabel();
        jLabel368 = new javax.swing.JLabel();
        jLabel369 = new javax.swing.JLabel();
        jLabel370 = new javax.swing.JLabel();
        dep63vie_loff2 = new javax.swing.JLabel();
        dep62vie_loff2 = new javax.swing.JLabel();
        jLabel371 = new javax.swing.JLabel();
        Tdep_vie2 = new javax.swing.JLabel();
        jPanel92 = new javax.swing.JPanel();
        jLabel372 = new javax.swing.JLabel();
        jLabel373 = new javax.swing.JLabel();
        jLabel374 = new javax.swing.JLabel();
        jLabel375 = new javax.swing.JLabel();
        dep63jue_loff2 = new javax.swing.JLabel();
        dep62jue_loff2 = new javax.swing.JLabel();
        jLabel376 = new javax.swing.JLabel();
        Tdep_jue2 = new javax.swing.JLabel();
        jPanel95 = new javax.swing.JPanel();
        jLabel383 = new javax.swing.JLabel();
        jLabel384 = new javax.swing.JLabel();
        jLabel385 = new javax.swing.JLabel();
        jPanel96 = new javax.swing.JPanel();
        jLabel386 = new javax.swing.JLabel();
        jLabel387 = new javax.swing.JLabel();
        jLabel388 = new javax.swing.JLabel();
        jPanel97 = new javax.swing.JPanel();
        jLabel389 = new javax.swing.JLabel();
        jLabel390 = new javax.swing.JLabel();
        jLabel391 = new javax.swing.JLabel();
        jPanel98 = new javax.swing.JPanel();
        jLabel392 = new javax.swing.JLabel();
        jLabel393 = new javax.swing.JLabel();
        jLabel394 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel1.setText("DELPHI");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 255));
        jLabel2.setText("PROYECCION DE LAY OFF TURNO A");
        jLabel2.setToolTipText("");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbl_layoff.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tbl_layoff);

        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 920, 440));

        btnActualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/save-icon.png"))); // NOI18N
        btnActualizar.setText("ACTUALIZAR");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });
        jPanel2.add(btnActualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 370, -1, 90));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 255));
        jLabel6.setText("MARTES:");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 160, -1, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(51, 51, 255));
        jLabel7.setText("MIERCOLES:");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 210, -1, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 51, 255));
        jLabel8.setText("LUNES:");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 120, -1, -1));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(51, 51, 255));
        jLabel10.setText("JUEVES:");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 260, -1, -1));

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(51, 51, 255));
        jLabel11.setText("VIERNES:");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 310, -1, -1));
        jPanel2.add(txt_lunes, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 110, 170, 40));
        jPanel2.add(txt_martes, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 160, 170, 40));
        jPanel2.add(txt_miercoles, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 210, 170, 40));
        jPanel2.add(txt_jueves, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 260, 170, 40));
        jPanel2.add(txt_viernes, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 310, 170, 40));
        jPanel2.add(txt_mod, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 60, 170, 40));

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(51, 51, 255));
        jLabel12.setText("CODIGO");
        jPanel2.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 20, -1, -1));

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(51, 51, 255));
        jLabel13.setText("T.A MOD:");
        jPanel2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 70, -1, -1));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(51, 51, 255));
        jLabel19.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel6.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(51, 51, 255));
        jLabel22.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel6.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(51, 51, 255));
        jLabel31.setText("HC LABORANDO    ");
        jPanel6.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, -1));

        jPanel2.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 700, 150, 130));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(51, 51, 255));
        jLabel20.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel7.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(51, 51, 255));
        jLabel23.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel7.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(51, 51, 255));
        jLabel16.setText("Dpto. 25962: ");
        jPanel7.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        lbl_totalTurno.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lbl_totalTurno.setText("____");
        jPanel7.add(lbl_totalTurno, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 80, -1, -1));

        jLabel30.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(51, 51, 255));
        jLabel30.setText("Dpto. 25963: ");
        jPanel7.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, -1));

        jLabel32.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(51, 51, 255));
        jLabel32.setText("Total TURNO:");
        jPanel7.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, -1, -1));

        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(51, 51, 255));
        jLabel33.setText("HC NECESARIO");
        jPanel7.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, -1, -1));

        dpto_25963.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        dpto_25963.setText("_____");
        jPanel7.add(dpto_25963, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 60, -1, -1));

        dpo_25962.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        dpo_25962.setText("_____");
        jPanel7.add(dpo_25962, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 40, -1, -1));

        jPanel2.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 480, 150, 100));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(51, 51, 255));
        jLabel21.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel8.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(51, 51, 255));
        jLabel24.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel8.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(51, 51, 255));
        jLabel17.setText(" 25962: ");
        jPanel8.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        jLabel34.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(51, 51, 255));
        jLabel34.setText(" 25963: ");
        jPanel8.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        jLabel35.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(51, 51, 255));
        jLabel35.setText("DEPARTAMENTO");
        jPanel8.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 30));

        dpo_62.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        dpo_62.setText("_____");
        jPanel8.add(dpo_62, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 40, -1, 20));

        total_tur.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        total_tur.setText("_____");
        jPanel8.add(total_tur, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 90, -1, -1));

        dpto_63.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        dpto_63.setText("_____");
        jPanel8.add(dpto_63, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 60, 40, 20));

        jLabel146.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel146.setForeground(new java.awt.Color(51, 51, 255));
        jLabel146.setText("T. TUR:");
        jPanel8.add(jLabel146, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, -1));

        jPanel2.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 580, 110, 120));

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel27.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(51, 51, 255));
        jLabel27.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel10.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel28.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(51, 51, 255));
        jLabel28.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel10.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel37.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(51, 51, 255));
        jLabel37.setText("MARTES");
        jPanel10.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, -1, 40));

        jPanel30.setBackground(new java.awt.Color(255, 255, 255));
        jPanel30.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel30.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel131.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel131.setForeground(new java.awt.Color(51, 51, 255));
        jLabel131.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel30.add(jLabel131, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel132.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel132.setForeground(new java.awt.Color(51, 51, 255));
        jLabel132.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel30.add(jLabel132, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel133.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel133.setForeground(new java.awt.Color(51, 51, 255));
        jLabel133.setText("LUNES");
        jPanel30.add(jLabel133, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel10.add(jPanel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 350, 140, 60));

        jPanel2.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 520, 110, 60));

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel43.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(51, 51, 255));
        jLabel43.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel12.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel44.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(51, 51, 255));
        jLabel44.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel12.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel45.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(51, 51, 255));
        jLabel45.setText(" 25962: ");
        jPanel12.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        jLabel46.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(51, 51, 255));
        jLabel46.setText("25963: ");
        jPanel12.add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        jLabel47.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(51, 51, 255));
        jLabel47.setText("DEPARTAMENTO");
        jPanel12.add(jLabel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 30));

        depa_63.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        depa_63.setText("_____");
        jPanel12.add(depa_63, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, -1, -1));

        depa_62.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        depa_62.setText("_____");
        jPanel12.add(depa_62, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 40, -1, -1));

        jLabel40.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(51, 51, 255));
        jLabel40.setText("T. TUR:");
        jPanel12.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, -1));

        total_turno.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        total_turno.setText("_____");
        jPanel12.add(total_turno, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 100, -1, -1));

        jPanel2.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 700, 110, 130));

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));
        jPanel13.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel48.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(51, 51, 255));
        jLabel48.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel13.add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel49.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(51, 51, 255));
        jLabel49.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel13.add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel50.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel50.setForeground(new java.awt.Color(51, 51, 255));
        jLabel50.setText("25962: ");
        jPanel13.add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        jLabel51.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(51, 51, 255));
        jLabel51.setText("25963: ");
        jPanel13.add(jLabel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        dep63mi_loff.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        dep63mi_loff.setText("_____");
        jPanel13.add(dep63mi_loff, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, -1, -1));

        dep62mi_loff.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        dep62mi_loff.setText("_____");
        jPanel13.add(dep62mi_loff, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, -1, -1));

        jLabel55.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(51, 51, 255));
        jLabel55.setText("T.TUR:");
        jPanel13.add(jLabel55, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, -1));

        Tdep_mi.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Tdep_mi.setText("_____");
        jPanel13.add(Tdep_mi, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 90, -1, -1));

        jPanel2.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 580, 110, 120));

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));
        jPanel15.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel15.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel58.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel58.setForeground(new java.awt.Color(51, 51, 255));
        jLabel58.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel15.add(jLabel58, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel59.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel59.setForeground(new java.awt.Color(51, 51, 255));
        jLabel59.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel15.add(jLabel59, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel60.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel60.setForeground(new java.awt.Color(51, 51, 255));
        jLabel60.setText("25962: ");
        jPanel15.add(jLabel60, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        jLabel61.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel61.setForeground(new java.awt.Color(51, 51, 255));
        jLabel61.setText("25963: ");
        jPanel15.add(jLabel61, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        depa63_mierc.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        depa63_mierc.setText("_____");
        jPanel15.add(depa63_mierc, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, -1, -1));

        depa62_mierc.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        depa62_mierc.setText("_____");
        jPanel15.add(depa62_mierc, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, -1, -1));

        jLabel65.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel65.setForeground(new java.awt.Color(51, 51, 255));
        jLabel65.setText("T.TUR:");
        jPanel15.add(jLabel65, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, -1));

        total_turnoMierc.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        total_turnoMierc.setText("_____");
        jPanel15.add(total_turnoMierc, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 100, -1, -1));

        jPanel2.add(jPanel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 700, 110, 130));

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));
        jPanel17.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel17.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel68.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel68.setForeground(new java.awt.Color(51, 51, 255));
        jLabel68.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel17.add(jLabel68, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel69.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel69.setForeground(new java.awt.Color(51, 51, 255));
        jLabel69.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel17.add(jLabel69, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel70.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel70.setForeground(new java.awt.Color(51, 51, 255));
        jLabel70.setText("25962: ");
        jPanel17.add(jLabel70, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        jLabel71.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel71.setForeground(new java.awt.Color(51, 51, 255));
        jLabel71.setText("25963: ");
        jPanel17.add(jLabel71, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        dep63jue_loff.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        dep63jue_loff.setText("_____");
        jPanel17.add(dep63jue_loff, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, -1, -1));

        dep62jue_loff.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        dep62jue_loff.setText("_____");
        jPanel17.add(dep62jue_loff, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, -1, -1));

        jLabel85.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel85.setForeground(new java.awt.Color(51, 51, 255));
        jLabel85.setText("T. TUR:");
        jPanel17.add(jLabel85, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, -1));

        Tdep_jue.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Tdep_jue.setText("_____");
        jPanel17.add(Tdep_jue, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 90, -1, -1));

        jPanel2.add(jPanel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 580, 110, 120));

        jPanel18.setBackground(new java.awt.Color(255, 255, 255));
        jPanel18.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel18.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel73.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel73.setForeground(new java.awt.Color(51, 51, 255));
        jLabel73.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel18.add(jLabel73, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel74.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel74.setForeground(new java.awt.Color(51, 51, 255));
        jLabel74.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel18.add(jLabel74, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel75.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel75.setForeground(new java.awt.Color(51, 51, 255));
        jLabel75.setText("25962: ");
        jPanel18.add(jLabel75, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        jLabel76.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel76.setForeground(new java.awt.Color(51, 51, 255));
        jLabel76.setText("25963: ");
        jPanel18.add(jLabel76, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, -1));

        dep63vie_loff.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        dep63vie_loff.setText("_____");
        jPanel18.add(dep63vie_loff, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 60, -1, -1));

        dep62vie_loff.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        dep62vie_loff.setText("_____");
        jPanel18.add(dep62vie_loff, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, -1, -1));

        jLabel90.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel90.setForeground(new java.awt.Color(51, 51, 255));
        jLabel90.setText("T. TUR:");
        jPanel18.add(jLabel90, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, -1));

        Tdep_vie.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Tdep_vie.setText("_____");
        jPanel18.add(Tdep_vie, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 90, -1, -1));

        jPanel2.add(jPanel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 580, 110, 120));

        jPanel19.setBackground(new java.awt.Color(255, 255, 255));
        jPanel19.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel19.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel78.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel78.setForeground(new java.awt.Color(51, 51, 255));
        jLabel78.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel19.add(jLabel78, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel79.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel79.setForeground(new java.awt.Color(51, 51, 255));
        jLabel79.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel19.add(jLabel79, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel80.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel80.setForeground(new java.awt.Color(51, 51, 255));
        jLabel80.setText("25962: ");
        jPanel19.add(jLabel80, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        jLabel81.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel81.setForeground(new java.awt.Color(51, 51, 255));
        jLabel81.setText("25963: ");
        jPanel19.add(jLabel81, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        dep63ma_loff.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        dep63ma_loff.setText("_____");
        jPanel19.add(dep63ma_loff, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, -1, -1));

        dep62ma_loff.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        dep62ma_loff.setText("_____");
        jPanel19.add(dep62ma_loff, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, -1, -1));

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(51, 51, 255));
        jLabel18.setText("T. TUR:");
        jPanel19.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, -1));

        Tdep_ma.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Tdep_ma.setText("_____");
        jPanel19.add(Tdep_ma, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 90, -1, 20));

        jPanel2.add(jPanel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 580, 110, 120));

        jPanel23.setBackground(new java.awt.Color(255, 255, 255));
        jPanel23.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel23.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel98.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel98.setForeground(new java.awt.Color(51, 51, 255));
        jLabel98.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel23.add(jLabel98, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel99.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel99.setForeground(new java.awt.Color(51, 51, 255));
        jLabel99.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel23.add(jLabel99, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel100.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel100.setForeground(new java.awt.Color(51, 51, 255));
        jLabel100.setText("25962: ");
        jPanel23.add(jLabel100, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        jLabel101.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel101.setForeground(new java.awt.Color(51, 51, 255));
        jLabel101.setText("25963: ");
        jPanel23.add(jLabel101, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        depa63_jue.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        depa63_jue.setText("_____");
        jPanel23.add(depa63_jue, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, -1, -1));

        depa62_jue.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        depa62_jue.setText("_____");
        jPanel23.add(depa62_jue, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, -1, -1));

        jLabel115.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel115.setForeground(new java.awt.Color(51, 51, 255));
        jLabel115.setText("T.TUR:");
        jPanel23.add(jLabel115, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, -1));

        total_turnojue.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        total_turnojue.setText("_____");
        jPanel23.add(total_turnojue, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 100, -1, -1));

        jPanel2.add(jPanel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 700, 110, 130));

        jPanel24.setBackground(new java.awt.Color(255, 255, 255));
        jPanel24.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel24.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel103.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel103.setForeground(new java.awt.Color(51, 51, 255));
        jLabel103.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel24.add(jLabel103, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel104.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel104.setForeground(new java.awt.Color(51, 51, 255));
        jLabel104.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel24.add(jLabel104, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel105.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel105.setForeground(new java.awt.Color(51, 51, 255));
        jLabel105.setText("25962: ");
        jPanel24.add(jLabel105, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        jLabel106.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel106.setForeground(new java.awt.Color(51, 51, 255));
        jLabel106.setText("25963: ");
        jPanel24.add(jLabel106, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        depa63_vie.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        depa63_vie.setText("_____");
        jPanel24.add(depa63_vie, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, -1, -1));

        depa62_vie.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        depa62_vie.setText("_____");
        jPanel24.add(depa62_vie, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, -1, -1));

        jLabel120.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel120.setForeground(new java.awt.Color(51, 51, 255));
        jLabel120.setText("T.TUR:");
        jPanel24.add(jLabel120, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, -1));

        total_turnovie.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        total_turnovie.setText("_____");
        jPanel24.add(total_turnovie, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 100, -1, -1));

        jPanel2.add(jPanel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 700, 110, 130));

        jPanel25.setBackground(new java.awt.Color(255, 255, 255));
        jPanel25.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel25.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel108.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel108.setForeground(new java.awt.Color(51, 51, 255));
        jLabel108.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel25.add(jLabel108, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel109.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel109.setForeground(new java.awt.Color(51, 51, 255));
        jLabel109.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel25.add(jLabel109, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel110.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel110.setForeground(new java.awt.Color(51, 51, 255));
        jLabel110.setText("25962: ");
        jPanel25.add(jLabel110, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        jLabel111.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel111.setForeground(new java.awt.Color(51, 51, 255));
        jLabel111.setText("25963: ");
        jPanel25.add(jLabel111, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        depa63_marts.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        depa63_marts.setText("_____");
        jPanel25.add(depa63_marts, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, -1, -1));

        depa62_marts.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        depa62_marts.setText("_____");
        jPanel25.add(depa62_marts, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, -1, -1));

        jLabel125.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel125.setForeground(new java.awt.Color(51, 51, 255));
        jLabel125.setText("T.TUR:");
        jPanel25.add(jLabel125, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, -1));

        total_turnoMarts.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        total_turnoMarts.setText("_____");
        jPanel25.add(total_turnoMarts, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 100, -1, -1));

        jPanel2.add(jPanel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 700, 110, 130));

        jPanel29.setBackground(new java.awt.Color(255, 255, 255));
        jPanel29.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel29.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel128.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel128.setForeground(new java.awt.Color(51, 51, 255));
        jLabel128.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel29.add(jLabel128, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel129.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel129.setForeground(new java.awt.Color(51, 51, 255));
        jLabel129.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel29.add(jLabel129, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel130.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel130.setForeground(new java.awt.Color(51, 51, 255));
        jLabel130.setText("HC EN LAY OFF");
        jPanel29.add(jLabel130, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, -1, 40));

        jPanel41.setBackground(new java.awt.Color(255, 255, 255));
        jPanel41.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel41.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel164.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel164.setForeground(new java.awt.Color(51, 51, 255));
        jLabel164.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel41.add(jLabel164, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel165.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel165.setForeground(new java.awt.Color(51, 51, 255));
        jLabel165.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel41.add(jLabel165, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel166.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel166.setForeground(new java.awt.Color(51, 51, 255));
        jLabel166.setText("HC EN LAY OFF");
        jPanel41.add(jLabel166, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, 40));

        jPanel29.add(jPanel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 410, 150, 200));

        jPanel2.add(jPanel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 580, 150, 120));

        jPanel31.setBackground(new java.awt.Color(255, 255, 255));
        jPanel31.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel31.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel134.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel134.setForeground(new java.awt.Color(51, 51, 255));
        jLabel134.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel31.add(jLabel134, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel135.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel135.setForeground(new java.awt.Color(51, 51, 255));
        jLabel135.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel31.add(jLabel135, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel136.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel136.setForeground(new java.awt.Color(51, 51, 255));
        jLabel136.setText("MIERCOLES");
        jPanel31.add(jLabel136, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 40));

        jPanel32.setBackground(new java.awt.Color(255, 255, 255));
        jPanel32.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel32.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel137.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel137.setForeground(new java.awt.Color(51, 51, 255));
        jLabel137.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel32.add(jLabel137, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel138.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel138.setForeground(new java.awt.Color(51, 51, 255));
        jLabel138.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel32.add(jLabel138, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel139.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel139.setForeground(new java.awt.Color(51, 51, 255));
        jLabel139.setText("LUNES");
        jPanel32.add(jLabel139, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel31.add(jPanel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 350, 140, 60));

        jPanel2.add(jPanel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 520, 110, 60));

        jPanel33.setBackground(new java.awt.Color(255, 255, 255));
        jPanel33.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel33.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel140.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel140.setForeground(new java.awt.Color(51, 51, 255));
        jLabel140.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel33.add(jLabel140, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel141.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel141.setForeground(new java.awt.Color(51, 51, 255));
        jLabel141.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel33.add(jLabel141, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel142.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel142.setForeground(new java.awt.Color(51, 51, 255));
        jLabel142.setText("VIERNES");
        jPanel33.add(jLabel142, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, 40));

        jPanel34.setBackground(new java.awt.Color(255, 255, 255));
        jPanel34.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel34.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel143.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel143.setForeground(new java.awt.Color(51, 51, 255));
        jLabel143.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel34.add(jLabel143, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel144.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel144.setForeground(new java.awt.Color(51, 51, 255));
        jLabel144.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel34.add(jLabel144, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel145.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel145.setForeground(new java.awt.Color(51, 51, 255));
        jLabel145.setText("LUNES");
        jPanel34.add(jLabel145, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel33.add(jPanel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 350, 140, 60));

        jPanel93.setBackground(new java.awt.Color(255, 255, 255));
        jPanel93.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel93.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel377.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel377.setForeground(new java.awt.Color(51, 51, 255));
        jLabel377.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel93.add(jLabel377, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel378.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel378.setForeground(new java.awt.Color(51, 51, 255));
        jLabel378.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel93.add(jLabel378, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel379.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel379.setForeground(new java.awt.Color(51, 51, 255));
        jLabel379.setText("VIERNES");
        jPanel93.add(jLabel379, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, 40));

        jPanel94.setBackground(new java.awt.Color(255, 255, 255));
        jPanel94.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel94.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel380.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel380.setForeground(new java.awt.Color(51, 51, 255));
        jLabel380.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel94.add(jLabel380, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel381.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel381.setForeground(new java.awt.Color(51, 51, 255));
        jLabel381.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel94.add(jLabel381, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel382.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel382.setForeground(new java.awt.Color(51, 51, 255));
        jLabel382.setText("LUNES");
        jPanel94.add(jLabel382, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel93.add(jPanel94, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 350, 140, 60));

        jPanel33.add(jPanel93, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 350, 110, 60));

        jPanel2.add(jPanel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(1160, 520, 110, 60));

        jPanel39.setBackground(new java.awt.Color(255, 255, 255));
        jPanel39.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel39.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel158.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel158.setForeground(new java.awt.Color(51, 51, 255));
        jLabel158.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel39.add(jLabel158, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel159.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel159.setForeground(new java.awt.Color(51, 51, 255));
        jLabel159.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel39.add(jLabel159, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel160.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel160.setForeground(new java.awt.Color(51, 51, 255));
        jLabel160.setText("JUEVES");
        jPanel39.add(jLabel160, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, -1, 40));

        jPanel40.setBackground(new java.awt.Color(255, 255, 255));
        jPanel40.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel40.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel161.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel161.setForeground(new java.awt.Color(51, 51, 255));
        jLabel161.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel40.add(jLabel161, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel162.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel162.setForeground(new java.awt.Color(51, 51, 255));
        jLabel162.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel40.add(jLabel162, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel163.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel163.setForeground(new java.awt.Color(51, 51, 255));
        jLabel163.setText("LUNES");
        jPanel40.add(jLabel163, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel39.add(jPanel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 350, 140, 60));

        jPanel2.add(jPanel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 520, 110, 60));

        cb_codigo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "610 JUMPER", "AW071E", "AW066E", "AW062E", "AW63", "AW61", "AW62", "AW04 TAB.BODY", "AW04", "AB033", "AB033", "AB009", "AA032", "AB019", "AB019", "AA019", "GM 610", "GM 610 P", "GM 611", "ISUZU", "AB010", "AB012", "AB015", "AW14", "AW01", "AW10", "AA007", "AA014", "AB011", "GF08", "AB014", "AW07", "AW16", "GF09", "AW19", "AW11", "GF010", "GF011", "CORTE", "SERV", "AA014", "KACHIRULES", " " }));
        jPanel2.add(cb_codigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 10, 170, 40));

        jPanel42.setBackground(new java.awt.Color(255, 255, 255));
        jPanel42.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel42.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel167.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel167.setForeground(new java.awt.Color(51, 51, 255));
        jLabel167.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel42.add(jLabel167, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel168.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel168.setForeground(new java.awt.Color(51, 51, 255));
        jLabel168.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel42.add(jLabel168, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel169.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel169.setForeground(new java.awt.Color(51, 51, 255));
        jLabel169.setText("LUNES");
        jPanel42.add(jLabel169, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, -1, 40));

        jPanel43.setBackground(new java.awt.Color(255, 255, 255));
        jPanel43.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel43.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel170.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel170.setForeground(new java.awt.Color(51, 51, 255));
        jLabel170.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel43.add(jLabel170, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel171.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel171.setForeground(new java.awt.Color(51, 51, 255));
        jLabel171.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel43.add(jLabel171, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel172.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel172.setForeground(new java.awt.Color(51, 51, 255));
        jLabel172.setText("LUNES");
        jPanel43.add(jLabel172, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel42.add(jPanel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 350, 140, 60));

        jPanel44.setBackground(new java.awt.Color(255, 255, 255));
        jPanel44.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel44.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel173.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel173.setForeground(new java.awt.Color(51, 51, 255));
        jLabel173.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel44.add(jLabel173, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel174.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel174.setForeground(new java.awt.Color(51, 51, 255));
        jLabel174.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel44.add(jLabel174, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel175.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel175.setForeground(new java.awt.Color(51, 51, 255));
        jLabel175.setText("LUNES");
        jPanel44.add(jLabel175, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel45.setBackground(new java.awt.Color(255, 255, 255));
        jPanel45.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel45.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel176.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel176.setForeground(new java.awt.Color(51, 51, 255));
        jLabel176.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel45.add(jLabel176, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel177.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel177.setForeground(new java.awt.Color(51, 51, 255));
        jLabel177.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel45.add(jLabel177, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel178.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel178.setForeground(new java.awt.Color(51, 51, 255));
        jLabel178.setText("LUNES");
        jPanel45.add(jLabel178, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel44.add(jPanel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 350, 140, 60));

        jPanel42.add(jPanel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 350, 140, 60));

        jPanel2.add(jPanel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 520, 110, 60));

        jPanel35.setBackground(new java.awt.Color(255, 255, 255));
        jPanel35.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel35.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel2.add(jPanel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 520, 20, 310));

        jPanel22.setBackground(new java.awt.Color(255, 255, 255));
        jPanel22.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel22.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel93.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel93.setForeground(new java.awt.Color(51, 51, 255));
        jLabel93.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel22.add(jLabel93, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel94.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel94.setForeground(new java.awt.Color(51, 51, 255));
        jLabel94.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel22.add(jLabel94, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel95.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel95.setForeground(new java.awt.Color(51, 51, 255));
        jLabel95.setText(" 25962: ");
        jPanel22.add(jLabel95, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        jLabel96.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel96.setForeground(new java.awt.Color(51, 51, 255));
        jLabel96.setText("25963: ");
        jPanel22.add(jLabel96, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        jLabel97.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel97.setForeground(new java.awt.Color(51, 51, 255));
        jLabel97.setText("DEPARTAMENTO");
        jPanel22.add(jLabel97, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 30));

        depa_66.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        depa_66.setText("_____");
        jPanel22.add(depa_66, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, -1, -1));

        depa_67.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        depa_67.setText("_____");
        jPanel22.add(depa_67, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 40, -1, -1));

        jLabel147.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel147.setForeground(new java.awt.Color(51, 51, 255));
        jLabel147.setText("T. TUR:");
        jPanel22.add(jLabel147, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, -1));

        total_turno2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        total_turno2.setText("_____");
        jPanel22.add(total_turno2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 100, -1, -1));

        jPanel2.add(jPanel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 700, 110, 130));

        jPanel36.setBackground(new java.awt.Color(255, 255, 255));
        jPanel36.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel36.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel148.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel148.setForeground(new java.awt.Color(51, 51, 255));
        jLabel148.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel36.add(jLabel148, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel149.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel149.setForeground(new java.awt.Color(51, 51, 255));
        jLabel149.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel36.add(jLabel149, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel150.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel150.setForeground(new java.awt.Color(51, 51, 255));
        jLabel150.setText("25962: ");
        jPanel36.add(jLabel150, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        jLabel151.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel151.setForeground(new java.awt.Color(51, 51, 255));
        jLabel151.setText("25963: ");
        jPanel36.add(jLabel151, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        depa63_marts2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        depa63_marts2.setText("_____");
        jPanel36.add(depa63_marts2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, -1, -1));

        depa62_marts2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        depa62_marts2.setText("_____");
        jPanel36.add(depa62_marts2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, -1, -1));

        jLabel152.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel152.setForeground(new java.awt.Color(51, 51, 255));
        jLabel152.setText("T.TUR:");
        jPanel36.add(jLabel152, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, -1));

        total_turnoMarts2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        total_turnoMarts2.setText("_____");
        jPanel36.add(total_turnoMarts2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 100, -1, -1));

        jPanel2.add(jPanel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 700, 110, 130));

        jPanel37.setBackground(new java.awt.Color(255, 255, 255));
        jPanel37.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel37.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel153.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel153.setForeground(new java.awt.Color(51, 51, 255));
        jLabel153.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel37.add(jLabel153, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel154.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel154.setForeground(new java.awt.Color(51, 51, 255));
        jLabel154.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel37.add(jLabel154, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel155.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel155.setForeground(new java.awt.Color(51, 51, 255));
        jLabel155.setText("25962: ");
        jPanel37.add(jLabel155, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        jLabel156.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel156.setForeground(new java.awt.Color(51, 51, 255));
        jLabel156.setText("25963: ");
        jPanel37.add(jLabel156, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        dep63ma_loff2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        dep63ma_loff2.setText("_____");
        jPanel37.add(dep63ma_loff2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, -1, -1));

        dep62ma_loff2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        dep62ma_loff2.setText("_____");
        jPanel37.add(dep62ma_loff2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, -1, -1));

        jLabel25.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(51, 51, 255));
        jLabel25.setText("T. TUR:");
        jPanel37.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, -1));

        Tdep_ma2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Tdep_ma2.setText("_____");
        jPanel37.add(Tdep_ma2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 90, -1, 20));

        jPanel2.add(jPanel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 580, 110, 120));

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel26.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(51, 51, 255));
        jLabel26.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel9.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel29.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(51, 51, 255));
        jLabel29.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel9.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel36.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(51, 51, 255));
        jLabel36.setText(" 25962: ");
        jPanel9.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        jLabel157.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel157.setForeground(new java.awt.Color(51, 51, 255));
        jLabel157.setText(" 25963: ");
        jPanel9.add(jLabel157, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        jLabel315.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel315.setForeground(new java.awt.Color(51, 51, 255));
        jLabel315.setText("DEPARTAMENTO");
        jPanel9.add(jLabel315, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 30));

        dpo_64.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        dpo_64.setText("_____");
        jPanel9.add(dpo_64, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 40, -1, 20));

        total_tur2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        total_tur2.setText("_____");
        jPanel9.add(total_tur2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 90, -1, -1));

        dpto_65.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        dpto_65.setText("_____");
        jPanel9.add(dpto_65, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 60, 40, 20));

        jLabel316.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel316.setForeground(new java.awt.Color(51, 51, 255));
        jLabel316.setText("T. TUR:");
        jPanel9.add(jLabel316, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, -1));

        jPanel2.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 580, 110, 120));

        jPanel78.setBackground(new java.awt.Color(255, 255, 255));
        jPanel78.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel78.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel317.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel317.setForeground(new java.awt.Color(51, 51, 255));
        jLabel317.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel78.add(jLabel317, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel318.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel318.setForeground(new java.awt.Color(51, 51, 255));
        jLabel318.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel78.add(jLabel318, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel319.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel319.setForeground(new java.awt.Color(51, 51, 255));
        jLabel319.setText("LUNES");
        jPanel78.add(jLabel319, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, -1, 40));

        jPanel79.setBackground(new java.awt.Color(255, 255, 255));
        jPanel79.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel79.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel320.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel320.setForeground(new java.awt.Color(51, 51, 255));
        jLabel320.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel79.add(jLabel320, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel321.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel321.setForeground(new java.awt.Color(51, 51, 255));
        jLabel321.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel79.add(jLabel321, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel322.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel322.setForeground(new java.awt.Color(51, 51, 255));
        jLabel322.setText("LUNES");
        jPanel79.add(jLabel322, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel78.add(jPanel79, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 350, 140, 60));

        jPanel80.setBackground(new java.awt.Color(255, 255, 255));
        jPanel80.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel80.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel323.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel323.setForeground(new java.awt.Color(51, 51, 255));
        jLabel323.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel80.add(jLabel323, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel324.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel324.setForeground(new java.awt.Color(51, 51, 255));
        jLabel324.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel80.add(jLabel324, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel325.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel325.setForeground(new java.awt.Color(51, 51, 255));
        jLabel325.setText("LUNES");
        jPanel80.add(jLabel325, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel81.setBackground(new java.awt.Color(255, 255, 255));
        jPanel81.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel81.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel326.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel326.setForeground(new java.awt.Color(51, 51, 255));
        jLabel326.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel81.add(jLabel326, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel327.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel327.setForeground(new java.awt.Color(51, 51, 255));
        jLabel327.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel81.add(jLabel327, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel328.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel328.setForeground(new java.awt.Color(51, 51, 255));
        jLabel328.setText("LUNES");
        jPanel81.add(jLabel328, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel80.add(jPanel81, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 350, 140, 60));

        jPanel78.add(jPanel80, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 350, 140, 60));

        jPanel2.add(jPanel78, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 520, 110, 60));

        jPanel38.setBackground(new java.awt.Color(255, 255, 255));
        jPanel38.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel38.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel329.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel329.setForeground(new java.awt.Color(51, 51, 255));
        jLabel329.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel38.add(jLabel329, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel330.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel330.setForeground(new java.awt.Color(51, 51, 255));
        jLabel330.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel38.add(jLabel330, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel331.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel331.setForeground(new java.awt.Color(51, 51, 255));
        jLabel331.setText("MARTES");
        jPanel38.add(jLabel331, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, -1, 40));

        jPanel82.setBackground(new java.awt.Color(255, 255, 255));
        jPanel82.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel82.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel332.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel332.setForeground(new java.awt.Color(51, 51, 255));
        jLabel332.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel82.add(jLabel332, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel333.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel333.setForeground(new java.awt.Color(51, 51, 255));
        jLabel333.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel82.add(jLabel333, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel334.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel334.setForeground(new java.awt.Color(51, 51, 255));
        jLabel334.setText("LUNES");
        jPanel82.add(jLabel334, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel38.add(jPanel82, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 350, 140, 60));

        jPanel2.add(jPanel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(830, 520, 110, 60));

        jPanel83.setBackground(new java.awt.Color(255, 255, 255));
        jPanel83.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel83.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel335.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel335.setForeground(new java.awt.Color(51, 51, 255));
        jLabel335.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel83.add(jLabel335, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel336.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel336.setForeground(new java.awt.Color(51, 51, 255));
        jLabel336.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel83.add(jLabel336, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel337.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel337.setForeground(new java.awt.Color(51, 51, 255));
        jLabel337.setText("MIERCOLES");
        jPanel83.add(jLabel337, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 40));

        jPanel84.setBackground(new java.awt.Color(255, 255, 255));
        jPanel84.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel84.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel338.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel338.setForeground(new java.awt.Color(51, 51, 255));
        jLabel338.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel84.add(jLabel338, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel339.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel339.setForeground(new java.awt.Color(51, 51, 255));
        jLabel339.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel84.add(jLabel339, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel340.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel340.setForeground(new java.awt.Color(51, 51, 255));
        jLabel340.setText("LUNES");
        jPanel84.add(jLabel340, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel83.add(jPanel84, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 350, 140, 60));

        jPanel2.add(jPanel83, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 520, 110, 60));

        jPanel85.setBackground(new java.awt.Color(255, 255, 255));
        jPanel85.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel85.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel341.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel341.setForeground(new java.awt.Color(51, 51, 255));
        jLabel341.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel85.add(jLabel341, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel342.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel342.setForeground(new java.awt.Color(51, 51, 255));
        jLabel342.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel85.add(jLabel342, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel343.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel343.setForeground(new java.awt.Color(51, 51, 255));
        jLabel343.setText("25962: ");
        jPanel85.add(jLabel343, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        jLabel344.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel344.setForeground(new java.awt.Color(51, 51, 255));
        jLabel344.setText("25963: ");
        jPanel85.add(jLabel344, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        depa63_mierc2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        depa63_mierc2.setText("_____");
        jPanel85.add(depa63_mierc2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, -1, -1));

        depa62_mierc2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        depa62_mierc2.setText("_____");
        jPanel85.add(depa62_mierc2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, -1, -1));

        jLabel345.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel345.setForeground(new java.awt.Color(51, 51, 255));
        jLabel345.setText("T.TUR:");
        jPanel85.add(jLabel345, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, -1));

        total_turnoMierc2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        total_turnoMierc2.setText("_____");
        jPanel85.add(total_turnoMierc2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 100, -1, -1));

        jPanel2.add(jPanel85, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 700, 110, 130));

        jPanel86.setBackground(new java.awt.Color(255, 255, 255));
        jPanel86.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel86.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel346.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel346.setForeground(new java.awt.Color(51, 51, 255));
        jLabel346.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel86.add(jLabel346, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel347.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel347.setForeground(new java.awt.Color(51, 51, 255));
        jLabel347.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel86.add(jLabel347, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel348.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel348.setForeground(new java.awt.Color(51, 51, 255));
        jLabel348.setText("25962: ");
        jPanel86.add(jLabel348, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        jLabel349.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel349.setForeground(new java.awt.Color(51, 51, 255));
        jLabel349.setText("25963: ");
        jPanel86.add(jLabel349, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        dep63mi_loff2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        dep63mi_loff2.setText("_____");
        jPanel86.add(dep63mi_loff2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, -1, -1));

        dep62mi_loff2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        dep62mi_loff2.setText("_____");
        jPanel86.add(dep62mi_loff2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, -1, -1));

        jLabel350.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel350.setForeground(new java.awt.Color(51, 51, 255));
        jLabel350.setText("T.TUR:");
        jPanel86.add(jLabel350, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, -1));

        Tdep_mi2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Tdep_mi2.setText("_____");
        jPanel86.add(Tdep_mi2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 90, -1, -1));

        jPanel2.add(jPanel86, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 580, 110, 120));

        jPanel87.setBackground(new java.awt.Color(255, 255, 255));
        jPanel87.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel87.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel351.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel351.setForeground(new java.awt.Color(51, 51, 255));
        jLabel351.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel87.add(jLabel351, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel352.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel352.setForeground(new java.awt.Color(51, 51, 255));
        jLabel352.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel87.add(jLabel352, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel353.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel353.setForeground(new java.awt.Color(51, 51, 255));
        jLabel353.setText("JUEVES");
        jPanel87.add(jLabel353, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, -1, 40));

        jPanel88.setBackground(new java.awt.Color(255, 255, 255));
        jPanel88.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel88.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel354.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel354.setForeground(new java.awt.Color(51, 51, 255));
        jLabel354.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel88.add(jLabel354, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel355.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel355.setForeground(new java.awt.Color(51, 51, 255));
        jLabel355.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel88.add(jLabel355, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel356.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel356.setForeground(new java.awt.Color(51, 51, 255));
        jLabel356.setText("LUNES");
        jPanel88.add(jLabel356, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel87.add(jPanel88, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 350, 140, 60));

        jPanel2.add(jPanel87, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 520, 110, 60));

        jPanel89.setBackground(new java.awt.Color(255, 255, 255));
        jPanel89.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel89.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel357.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel357.setForeground(new java.awt.Color(51, 51, 255));
        jLabel357.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel89.add(jLabel357, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel358.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel358.setForeground(new java.awt.Color(51, 51, 255));
        jLabel358.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel89.add(jLabel358, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel359.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel359.setForeground(new java.awt.Color(51, 51, 255));
        jLabel359.setText("25962: ");
        jPanel89.add(jLabel359, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        jLabel360.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel360.setForeground(new java.awt.Color(51, 51, 255));
        jLabel360.setText("25963: ");
        jPanel89.add(jLabel360, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        depa63_jue2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        depa63_jue2.setText("_____");
        jPanel89.add(depa63_jue2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, -1, -1));

        depa62_jue2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        depa62_jue2.setText("_____");
        jPanel89.add(depa62_jue2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, -1, -1));

        jLabel361.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel361.setForeground(new java.awt.Color(51, 51, 255));
        jLabel361.setText("T.TUR:");
        jPanel89.add(jLabel361, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, -1));

        total_turnojue2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        total_turnojue2.setText("_____");
        jPanel89.add(total_turnojue2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 100, -1, -1));

        jPanel2.add(jPanel89, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 700, 110, 130));

        jPanel90.setBackground(new java.awt.Color(255, 255, 255));
        jPanel90.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel90.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel362.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel362.setForeground(new java.awt.Color(51, 51, 255));
        jLabel362.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel90.add(jLabel362, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel363.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel363.setForeground(new java.awt.Color(51, 51, 255));
        jLabel363.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel90.add(jLabel363, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel364.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel364.setForeground(new java.awt.Color(51, 51, 255));
        jLabel364.setText("25962: ");
        jPanel90.add(jLabel364, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        jLabel365.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel365.setForeground(new java.awt.Color(51, 51, 255));
        jLabel365.setText("25963: ");
        jPanel90.add(jLabel365, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        depa63_vie2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        depa63_vie2.setText("_____");
        jPanel90.add(depa63_vie2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, -1, -1));

        depa62_vie2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        depa62_vie2.setText("_____");
        jPanel90.add(depa62_vie2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, -1, -1));

        jLabel366.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel366.setForeground(new java.awt.Color(51, 51, 255));
        jLabel366.setText("T.TUR:");
        jPanel90.add(jLabel366, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, -1));

        total_turnovie2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        total_turnovie2.setText("_____");
        jPanel90.add(total_turnovie2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 100, -1, -1));

        jPanel2.add(jPanel90, new org.netbeans.lib.awtextra.AbsoluteConstraints(1160, 700, 110, 130));

        jPanel91.setBackground(new java.awt.Color(255, 255, 255));
        jPanel91.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel91.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel367.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel367.setForeground(new java.awt.Color(51, 51, 255));
        jLabel367.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel91.add(jLabel367, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel368.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel368.setForeground(new java.awt.Color(51, 51, 255));
        jLabel368.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel91.add(jLabel368, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel369.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel369.setForeground(new java.awt.Color(51, 51, 255));
        jLabel369.setText("25962: ");
        jPanel91.add(jLabel369, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        jLabel370.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel370.setForeground(new java.awt.Color(51, 51, 255));
        jLabel370.setText("25963: ");
        jPanel91.add(jLabel370, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, -1));

        dep63vie_loff2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        dep63vie_loff2.setText("_____");
        jPanel91.add(dep63vie_loff2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 60, -1, -1));

        dep62vie_loff2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        dep62vie_loff2.setText("_____");
        jPanel91.add(dep62vie_loff2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, -1, -1));

        jLabel371.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel371.setForeground(new java.awt.Color(51, 51, 255));
        jLabel371.setText("T. TUR:");
        jPanel91.add(jLabel371, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, -1));

        Tdep_vie2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Tdep_vie2.setText("_____");
        jPanel91.add(Tdep_vie2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 90, -1, -1));

        jPanel2.add(jPanel91, new org.netbeans.lib.awtextra.AbsoluteConstraints(1160, 580, 110, 120));

        jPanel92.setBackground(new java.awt.Color(255, 255, 255));
        jPanel92.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel92.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel372.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel372.setForeground(new java.awt.Color(51, 51, 255));
        jLabel372.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel92.add(jLabel372, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel373.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel373.setForeground(new java.awt.Color(51, 51, 255));
        jLabel373.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel92.add(jLabel373, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel374.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel374.setForeground(new java.awt.Color(51, 51, 255));
        jLabel374.setText("25962: ");
        jPanel92.add(jLabel374, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        jLabel375.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel375.setForeground(new java.awt.Color(51, 51, 255));
        jLabel375.setText("25963: ");
        jPanel92.add(jLabel375, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        dep63jue_loff2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        dep63jue_loff2.setText("_____");
        jPanel92.add(dep63jue_loff2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, -1, -1));

        dep62jue_loff2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        dep62jue_loff2.setText("_____");
        jPanel92.add(dep62jue_loff2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, -1, -1));

        jLabel376.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel376.setForeground(new java.awt.Color(51, 51, 255));
        jLabel376.setText("T. TUR:");
        jPanel92.add(jLabel376, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, -1));

        Tdep_jue2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Tdep_jue2.setText("_____");
        jPanel92.add(Tdep_jue2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 90, -1, -1));

        jPanel2.add(jPanel92, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 580, 110, 120));

        jPanel95.setBackground(new java.awt.Color(255, 255, 255));
        jPanel95.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel95.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel383.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel383.setForeground(new java.awt.Color(51, 51, 255));
        jLabel383.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel95.add(jLabel383, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel384.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel384.setForeground(new java.awt.Color(51, 51, 255));
        jLabel384.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel95.add(jLabel384, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel385.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel385.setForeground(new java.awt.Color(51, 51, 255));
        jLabel385.setText("VIERNES");
        jPanel95.add(jLabel385, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, 40));

        jPanel96.setBackground(new java.awt.Color(255, 255, 255));
        jPanel96.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel96.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel386.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel386.setForeground(new java.awt.Color(51, 51, 255));
        jLabel386.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel96.add(jLabel386, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel387.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel387.setForeground(new java.awt.Color(51, 51, 255));
        jLabel387.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel96.add(jLabel387, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel388.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel388.setForeground(new java.awt.Color(51, 51, 255));
        jLabel388.setText("LUNES");
        jPanel96.add(jLabel388, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel95.add(jPanel96, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 350, 140, 60));

        jPanel97.setBackground(new java.awt.Color(255, 255, 255));
        jPanel97.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel97.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel389.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel389.setForeground(new java.awt.Color(51, 51, 255));
        jLabel389.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel97.add(jLabel389, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel390.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel390.setForeground(new java.awt.Color(51, 51, 255));
        jLabel390.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel97.add(jLabel390, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel391.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel391.setForeground(new java.awt.Color(51, 51, 255));
        jLabel391.setText("VIERNES");
        jPanel97.add(jLabel391, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, 40));

        jPanel98.setBackground(new java.awt.Color(255, 255, 255));
        jPanel98.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel98.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel392.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel392.setForeground(new java.awt.Color(51, 51, 255));
        jLabel392.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel98.add(jLabel392, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel393.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel393.setForeground(new java.awt.Color(51, 51, 255));
        jLabel393.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel98.add(jLabel393, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel394.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel394.setForeground(new java.awt.Color(51, 51, 255));
        jLabel394.setText("LUNES");
        jPanel98.add(jLabel394, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel97.add(jPanel98, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 350, 140, 60));

        jPanel95.add(jPanel97, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 350, 110, 60));

        jPanel2.add(jPanel95, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 520, 110, 60));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 1470, 840));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1482, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        // TODO add your handling code here:
        try{
           actualizar_tabla();
           TOTAL_TA();

        } catch(Exception e)
        {

        }

    }//GEN-LAST:event_btnActualizarActionPerformed

    
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
            java.util.logging.Logger.getLogger(calculo_layoff.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(calculo_layoff.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(calculo_layoff.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(calculo_layoff.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new calculo_layoff(null).setVisible(true);
            }
        });
        
        
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Tdep_jue;
    private javax.swing.JLabel Tdep_jue2;
    private javax.swing.JLabel Tdep_ma;
    private javax.swing.JLabel Tdep_ma2;
    private javax.swing.JLabel Tdep_mi;
    private javax.swing.JLabel Tdep_mi2;
    private javax.swing.JLabel Tdep_vie;
    private javax.swing.JLabel Tdep_vie2;
    private javax.swing.JButton btnActualizar;
    private javax.swing.JComboBox cb_codigo;
    private javax.swing.JLabel dep62jue_loff;
    private javax.swing.JLabel dep62jue_loff2;
    private javax.swing.JLabel dep62ma_loff;
    private javax.swing.JLabel dep62ma_loff2;
    private javax.swing.JLabel dep62mi_loff;
    private javax.swing.JLabel dep62mi_loff2;
    private javax.swing.JLabel dep62vie_loff;
    private javax.swing.JLabel dep62vie_loff2;
    private javax.swing.JLabel dep63jue_loff;
    private javax.swing.JLabel dep63jue_loff2;
    private javax.swing.JLabel dep63ma_loff;
    private javax.swing.JLabel dep63ma_loff2;
    private javax.swing.JLabel dep63mi_loff;
    private javax.swing.JLabel dep63mi_loff2;
    private javax.swing.JLabel dep63vie_loff;
    private javax.swing.JLabel dep63vie_loff2;
    private javax.swing.JLabel depa62_jue;
    private javax.swing.JLabel depa62_jue2;
    private javax.swing.JLabel depa62_marts;
    private javax.swing.JLabel depa62_marts2;
    private javax.swing.JLabel depa62_mierc;
    private javax.swing.JLabel depa62_mierc2;
    private javax.swing.JLabel depa62_vie;
    private javax.swing.JLabel depa62_vie2;
    private javax.swing.JLabel depa63_jue;
    private javax.swing.JLabel depa63_jue2;
    private javax.swing.JLabel depa63_marts;
    private javax.swing.JLabel depa63_marts2;
    private javax.swing.JLabel depa63_mierc;
    private javax.swing.JLabel depa63_mierc2;
    private javax.swing.JLabel depa63_vie;
    private javax.swing.JLabel depa63_vie2;
    private javax.swing.JLabel depa_62;
    private javax.swing.JLabel depa_63;
    private javax.swing.JLabel depa_66;
    private javax.swing.JLabel depa_67;
    private javax.swing.JLabel dpo_25962;
    private javax.swing.JLabel dpo_62;
    private javax.swing.JLabel dpo_64;
    private javax.swing.JLabel dpto_25963;
    private javax.swing.JLabel dpto_63;
    private javax.swing.JLabel dpto_65;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel100;
    private javax.swing.JLabel jLabel101;
    private javax.swing.JLabel jLabel103;
    private javax.swing.JLabel jLabel104;
    private javax.swing.JLabel jLabel105;
    private javax.swing.JLabel jLabel106;
    private javax.swing.JLabel jLabel108;
    private javax.swing.JLabel jLabel109;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel110;
    private javax.swing.JLabel jLabel111;
    private javax.swing.JLabel jLabel115;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel120;
    private javax.swing.JLabel jLabel125;
    private javax.swing.JLabel jLabel128;
    private javax.swing.JLabel jLabel129;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel130;
    private javax.swing.JLabel jLabel131;
    private javax.swing.JLabel jLabel132;
    private javax.swing.JLabel jLabel133;
    private javax.swing.JLabel jLabel134;
    private javax.swing.JLabel jLabel135;
    private javax.swing.JLabel jLabel136;
    private javax.swing.JLabel jLabel137;
    private javax.swing.JLabel jLabel138;
    private javax.swing.JLabel jLabel139;
    private javax.swing.JLabel jLabel140;
    private javax.swing.JLabel jLabel141;
    private javax.swing.JLabel jLabel142;
    private javax.swing.JLabel jLabel143;
    private javax.swing.JLabel jLabel144;
    private javax.swing.JLabel jLabel145;
    private javax.swing.JLabel jLabel146;
    private javax.swing.JLabel jLabel147;
    private javax.swing.JLabel jLabel148;
    private javax.swing.JLabel jLabel149;
    private javax.swing.JLabel jLabel150;
    private javax.swing.JLabel jLabel151;
    private javax.swing.JLabel jLabel152;
    private javax.swing.JLabel jLabel153;
    private javax.swing.JLabel jLabel154;
    private javax.swing.JLabel jLabel155;
    private javax.swing.JLabel jLabel156;
    private javax.swing.JLabel jLabel157;
    private javax.swing.JLabel jLabel158;
    private javax.swing.JLabel jLabel159;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel160;
    private javax.swing.JLabel jLabel161;
    private javax.swing.JLabel jLabel162;
    private javax.swing.JLabel jLabel163;
    private javax.swing.JLabel jLabel164;
    private javax.swing.JLabel jLabel165;
    private javax.swing.JLabel jLabel166;
    private javax.swing.JLabel jLabel167;
    private javax.swing.JLabel jLabel168;
    private javax.swing.JLabel jLabel169;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel170;
    private javax.swing.JLabel jLabel171;
    private javax.swing.JLabel jLabel172;
    private javax.swing.JLabel jLabel173;
    private javax.swing.JLabel jLabel174;
    private javax.swing.JLabel jLabel175;
    private javax.swing.JLabel jLabel176;
    private javax.swing.JLabel jLabel177;
    private javax.swing.JLabel jLabel178;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel315;
    private javax.swing.JLabel jLabel316;
    private javax.swing.JLabel jLabel317;
    private javax.swing.JLabel jLabel318;
    private javax.swing.JLabel jLabel319;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel320;
    private javax.swing.JLabel jLabel321;
    private javax.swing.JLabel jLabel322;
    private javax.swing.JLabel jLabel323;
    private javax.swing.JLabel jLabel324;
    private javax.swing.JLabel jLabel325;
    private javax.swing.JLabel jLabel326;
    private javax.swing.JLabel jLabel327;
    private javax.swing.JLabel jLabel328;
    private javax.swing.JLabel jLabel329;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel330;
    private javax.swing.JLabel jLabel331;
    private javax.swing.JLabel jLabel332;
    private javax.swing.JLabel jLabel333;
    private javax.swing.JLabel jLabel334;
    private javax.swing.JLabel jLabel335;
    private javax.swing.JLabel jLabel336;
    private javax.swing.JLabel jLabel337;
    private javax.swing.JLabel jLabel338;
    private javax.swing.JLabel jLabel339;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel340;
    private javax.swing.JLabel jLabel341;
    private javax.swing.JLabel jLabel342;
    private javax.swing.JLabel jLabel343;
    private javax.swing.JLabel jLabel344;
    private javax.swing.JLabel jLabel345;
    private javax.swing.JLabel jLabel346;
    private javax.swing.JLabel jLabel347;
    private javax.swing.JLabel jLabel348;
    private javax.swing.JLabel jLabel349;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel350;
    private javax.swing.JLabel jLabel351;
    private javax.swing.JLabel jLabel352;
    private javax.swing.JLabel jLabel353;
    private javax.swing.JLabel jLabel354;
    private javax.swing.JLabel jLabel355;
    private javax.swing.JLabel jLabel356;
    private javax.swing.JLabel jLabel357;
    private javax.swing.JLabel jLabel358;
    private javax.swing.JLabel jLabel359;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel360;
    private javax.swing.JLabel jLabel361;
    private javax.swing.JLabel jLabel362;
    private javax.swing.JLabel jLabel363;
    private javax.swing.JLabel jLabel364;
    private javax.swing.JLabel jLabel365;
    private javax.swing.JLabel jLabel366;
    private javax.swing.JLabel jLabel367;
    private javax.swing.JLabel jLabel368;
    private javax.swing.JLabel jLabel369;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel370;
    private javax.swing.JLabel jLabel371;
    private javax.swing.JLabel jLabel372;
    private javax.swing.JLabel jLabel373;
    private javax.swing.JLabel jLabel374;
    private javax.swing.JLabel jLabel375;
    private javax.swing.JLabel jLabel376;
    private javax.swing.JLabel jLabel377;
    private javax.swing.JLabel jLabel378;
    private javax.swing.JLabel jLabel379;
    private javax.swing.JLabel jLabel380;
    private javax.swing.JLabel jLabel381;
    private javax.swing.JLabel jLabel382;
    private javax.swing.JLabel jLabel383;
    private javax.swing.JLabel jLabel384;
    private javax.swing.JLabel jLabel385;
    private javax.swing.JLabel jLabel386;
    private javax.swing.JLabel jLabel387;
    private javax.swing.JLabel jLabel388;
    private javax.swing.JLabel jLabel389;
    private javax.swing.JLabel jLabel390;
    private javax.swing.JLabel jLabel391;
    private javax.swing.JLabel jLabel392;
    private javax.swing.JLabel jLabel393;
    private javax.swing.JLabel jLabel394;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JLabel jLabel98;
    private javax.swing.JLabel jLabel99;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel42;
    private javax.swing.JPanel jPanel43;
    private javax.swing.JPanel jPanel44;
    private javax.swing.JPanel jPanel45;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel78;
    private javax.swing.JPanel jPanel79;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel80;
    private javax.swing.JPanel jPanel81;
    private javax.swing.JPanel jPanel82;
    private javax.swing.JPanel jPanel83;
    private javax.swing.JPanel jPanel84;
    private javax.swing.JPanel jPanel85;
    private javax.swing.JPanel jPanel86;
    private javax.swing.JPanel jPanel87;
    private javax.swing.JPanel jPanel88;
    private javax.swing.JPanel jPanel89;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanel90;
    private javax.swing.JPanel jPanel91;
    private javax.swing.JPanel jPanel92;
    private javax.swing.JPanel jPanel93;
    private javax.swing.JPanel jPanel94;
    private javax.swing.JPanel jPanel95;
    private javax.swing.JPanel jPanel96;
    private javax.swing.JPanel jPanel97;
    private javax.swing.JPanel jPanel98;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbl_totalTurno;
    private javax.swing.JTable tbl_layoff;
    private javax.swing.JLabel total_tur;
    private javax.swing.JLabel total_tur2;
    private javax.swing.JLabel total_turno;
    private javax.swing.JLabel total_turno2;
    private javax.swing.JLabel total_turnoMarts;
    private javax.swing.JLabel total_turnoMarts2;
    private javax.swing.JLabel total_turnoMierc;
    private javax.swing.JLabel total_turnoMierc2;
    private javax.swing.JLabel total_turnojue;
    private javax.swing.JLabel total_turnojue2;
    private javax.swing.JLabel total_turnovie;
    private javax.swing.JLabel total_turnovie2;
    private javax.swing.JTextField txt_jueves;
    private javax.swing.JTextField txt_lunes;
    private javax.swing.JTextField txt_martes;
    private javax.swing.JTextField txt_miercoles;
    private javax.swing.JTextField txt_mod;
    private javax.swing.JTextField txt_viernes;
    // End of variables declaration//GEN-END:variables
}
