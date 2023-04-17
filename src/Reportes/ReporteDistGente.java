/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Reportes;

import Clases.Conection;
import java.awt.Image;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import manufactura.Principal;

public class ReporteDistGente extends javax.swing.JFrame {

    /**
     * Creates new form ReporteDistGente
     */
    //DefaultTableModel modelo;
    public ReporteDistGente() {
        try {
            initComponents();
            Principal.cn=new Conection();
            creartablePlataforma();
            creartableCadena();
            creartableCodigo();
            creartableTurno();
            Image icon = new ImageIcon(getClass().getResource("/Images/competitors-icon.png")).getImage();
            setIconImage(icon);
          //  panelprincipal.setViewportView(jpa);
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }
    
    public void creartableCadena(){
     DefaultTableModel  modelo=new DefaultTableModel();
        try{
              
            modelo.setColumnIdentifiers(new Object[]{"CADENA","CONV & POST& KITS", "CONTENSION", "SOPORTE", "TAB.INSP", "RUTAS.INT", "LPS", "SOP.LPS", "PILOTOS", "FTQ",  "SISTEMAS", "RUT.SUT."});
            ResultSet rs= Principal.cn.GetConsulta("SELECT\n" +
                "CADENa,\n" +
                " SUM(MANUFACTURA.HCDIRLINEA) AS HCLINEA, SUM(manufactura.HCDIRCONTE) AS CONTENSION, sum(manufactura.HCDIRSOPORTE) AS SOPORTE, SUM(manufactura.HCDIRTABINSP) AS 'TAB.INSP', SUM(MANUFACTURA.HCRUTASINT) AS RUTASINTERNAS,"+
                "sum(manufactura.hcdirlps) as LPS, sum(manufactura.hcdirsoplps) as 'SOP.LPS',  SUM(manufactura.HCDIRPILOTOS) AS PILOTOs,  SUM(manufactura.HCDIRFTQ) AS FTQ,\n" +
                "SUM(manufactura.hcdirsistemas) as sistemas,\n" +
                "SUM(manufactura.HCINDRUTAS) AS RUTAS \n" +
                "FROM\n" +
                "manufactura ,\n" +
                "codigos\n" +
                "WHERE\n" +
                "manufactura.IDCODIGO = codigos.IDCODIGO AND MANUFACTURA.ACTIVO=1 and codigos.CADENA<>7\n" +
                "GROUP BY\n" +
                "codigos.CADENA, codigos.turno");
            
            while(rs.next())
            {
                modelo.addRow(new Object[]{rs.getString("cadena"), rs.getString("HCLINEA"), rs.getString("contension"),  rs.getString("soporte"), rs.getString("tab.insp"), rs.getString("rutasinternas"), rs.getString("lps"), rs.getString("SOP.LPS"), rs.getString("pilotos"), rs.getString("ftq"), rs.getString("sistemas"), rs.getString("rutas")});
            }
            rs=Principal.cn.GetConsulta("SELECT\n" +
                "CADENa, plataforma,\n" +
                " SUM(MANUFACTURA.HCDIRLINEA) AS HCLINEA, SUM(manufactura.HCDIRCONTE) AS CONTENSION, sum(manufactura.HCDIRSOPORTE) AS SOPORTE, SUM(manufactura.HCDIRTABINSP) AS 'TAB.INSP', SUM(MANUFACTURA.HCRUTASINT) AS RUTASINTERNAS,sum(manufactura.hcdirlps) as LPS, sum(manufactura.hcdirsoplps) as 'SOP.LPS',  SUM(manufactura.HCDIRPILOTOS) AS PILOTOs,  SUM(manufactura.HCDIRFTQ) AS FTQ,\n" +
                "SUM(manufactura.hcdirsistemas) as sistemas,\n" +
                "SUM(manufactura.HCINDRUTAS) AS RUTAS \n" +
                "FROM\n" +
                "manufactura ,\n" +
                "codigos\n" +
                "WHERE\n" +
                "manufactura.IDCODIGO = codigos.IDCODIGO and codigos.PLATAFORMA='SERVICIOS'\n" +
                "GROUP BY\n" +
                "codigos.IDCODIGO");
            if(rs.next())
            {
                modelo.addRow(new Object[]{rs.getString("plataforma"), rs.getString("HCLINEA"), rs.getString("contension"),  rs.getString("soporte"), rs.getString("tab.insp"), rs.getString("rutasinternas"), rs.getString("lps"), rs.getString("SOP.LPS"), rs.getString("pilotos"), rs.getString("ftq"), rs.getString("sistemas"), rs.getString("rutas")});
                
            }
         tblCadena.setModel(modelo);
            
        }catch(Exception e)
        {
            System.out.println(e.toString());
        }
  
    }
    
    public void creartablePlataforma(){
      try{
          DefaultTableModel  modelo=new DefaultTableModel();
          modelo.setColumnIdentifiers(new Object[]{"PLATAFORMA",  "CONV & POST& KITS", "CONTENSION", "SOPORTE", "TAB.INSP", "RUTAS.INT", "LPS", "SOP.LPS", "PILOTOS", "FTQ",  "SISTEMAS", "RUT.SUT."});
            ResultSet rs= Principal.cn.GetConsulta("SELECT\n" +
                "plataforma,\n" +
                 " SUM(MANUFACTURA.HCDIRLINEA) AS HCLINEA, sum(manufactura.hcdirsoplps) as SopLPS, sum(manufactura.hcdirsoporte) as soporte,   Sum(manufactura.HCDIRLPS) as LPS, SUM(manufactura.HCDIRCONTE) AS CONTENSION, SUM(manufactura.HCDIRFTQ) AS FTQ, SUM(manufactura.HCDIRPILOTOS) AS PILOTOs,\n" +
                " sum(manufactura.HCDIRSOPORTE) AS SOPORTE, SUM(manufactura.hcdirsistemas) as sistemas,\n" +
                "SUM(manufactura.HCDIRTABINSP) AS 'TAB.INSP', SUM(MANUFACTURA.HCRUTASINT) AS RUTASINTERNAS,\n" +
                "SUM(manufactura.HCINDRUTAS) AS RUTAS \n" +
                "FROM\n" +
                "manufactura ,\n" +
                "codigos\n" +
                "WHERE\n" +
                "manufactura.IDCODIGO = codigos.IDCODIGO AND MANUFACTURA.ACTIVO=1 \n" +
                "GROUP BY\n" +
                "codigos.plataforma");
            
            while(rs.next())
            {
                modelo.addRow(new Object[]{rs.getString("plataforma"),rs.getString("HCLINEA"), rs.getString("contension"),  rs.getString("soporte"), rs.getString("tab.insp"), rs.getString("rutasinternas"), rs.getString("lps"), rs.getString("SopLPS"), rs.getString("pilotos"), rs.getString("ftq"), rs.getString("sistemas"), rs.getString("rutas")});
            }
            
         tblPlataforma.setModel(modelo);
            
        }catch(Exception e)
        {
            System.out.println(e.toString());
        }
    }
    
    public void creartableCodigo(){
      try{
           DefaultTableModel  modelo=new DefaultTableModel();
            modelo.setColumnIdentifiers(new Object[]{"CADENA",  "CODIGO", "PLATAFORMA","ARNES", "LINEA", "TURNO", "SALIDA", "CONV & POST& KITS", "CONTENSION", "SOPORTE", "TAB.INSP", "RUTAS.INT", "LPS", "SOP.LPS", "PILOTOS", "FTQ",  "SISTEMAS", "RUT.SUT."});
            ResultSet rs= Principal.cn.GetConsulta("SELECT\n" +
                "CODIGOS.CADENA, CAST(codigos.linea as UNSIGNED) as linea,  CODIGOS.CODIGO, codigos.plataforma, codigos.linea, codigos.arnes, codigos.TURNO, manufactura.salidaenpieza as salida, \n" +
              " SUM(MANUFACTURA.HCDIRLINEA) AS HCLINEA, sum(manufactura.hcdirsoplps) as SopLPS, sum(manufactura.hcdirsoporte) as soporte,   Sum(manufactura.HCDIRLPS) as LPS, SUM(manufactura.HCDIRCONTE) AS CONTENSION, SUM(manufactura.HCDIRFTQ) AS FTQ, SUM(manufactura.HCDIRPILOTOS) AS PILOTOs,\n" +
                " sum(manufactura.HCDIRSOPORTE) AS SOPORTE, SUM(manufactura.hcdirsistemas) as sistemas,\n" +
                "SUM(manufactura.HCDIRTABINSP) AS 'TAB.INSP', SUM(MANUFACTURA.HCRUTASINT) AS RUTASINTERNAS,\n" +
                "SUM(manufactura.HCINDRUTAS) AS RUTAS\n" +
                "FROM\n" +
                "manufactura ,\n" +
                "codigos\n" +
                "WHERE\n" +
                "manufactura.IDCODIGO = codigos.IDCODIGO AND MANUFACTURA.ACTIVO=1  and codigos.cadena<>'4' and codigos.cadena<>'7' \n" +
                "GROUP BY CODIGOS.IDCODIGO\n" +
                "ORDER BY linea");
            
            while(rs.next())
            {
                modelo.addRow(new Object[]{ rs.getString("cadena"),  rs.getString("codigo"), rs.getString("plataforma"), rs.getString("arnes"), rs.getString("linea"), rs.getString("turno"), rs.getString("salida"), rs.getString("HCLINEA"), rs.getString("contension"),  rs.getString("soporte"), rs.getString("tab.insp"), rs.getString("rutasinternas"), rs.getString("lps"), rs.getString("SopLPS"), rs.getString("pilotos"), rs.getString("ftq"), rs.getString("sistemas"), rs.getString("rutas")});
            }
            
         tblCodigos.setModel(modelo);
            
        }catch(Exception e)
        {
            System.out.println(e.toString());
        }
    }
    
    public void creartableTurno(){
      try{
           DefaultTableModel  modelo=new DefaultTableModel();
            modelo.setColumnIdentifiers(new Object[]{ "Turno", "CONV & POST& KITS", "CONTENSION", "SOPORTE", "TAB.INSP", "RUTAS.INT", "LPS", "SOP.LPS", "PILOTOS", "FTQ",  "SISTEMAS", "RUT.SUT."});
            ResultSet rs= Principal.cn.GetConsulta("SELECT\n" +
                "CAST(codigos.linea as UNSIGNED) as linea,  CODIGOS.CODIGO, codigos.TURNO, \n" +
           " SUM(MANUFACTURA.HCDIRLINEA) AS HCLINEA, sum(manufactura.hcdirsoplps) as SopLPS, sum(manufactura.hcdirsoporte) as soporte,   Sum(manufactura.HCDIRLPS) as LPS, SUM(manufactura.HCDIRCONTE) AS CONTENSION, SUM(manufactura.HCDIRFTQ) AS FTQ, SUM(manufactura.HCDIRPILOTOS) AS PILOTO,\n" +
                " sum(manufactura.HCDIRSOPORTE) AS SOPORTE, SUM(manufactura.hcdirsistemas) as sistemas, \n" +
                "SUM(manufactura.HCDIRTABINSP) AS 'TAB.INSP', SUM(MANUFACTURA.HCRUTASINT) AS RUTASINTERNAS,\n" +
                "SUM(manufactura.HCINDRUTAS) AS RUTAS \n" +
                "FROM\n" +
                "manufactura ,\n" +
                "codigos\n" +
                "WHERE\n" +
                "manufactura.IDCODIGO = codigos.IDCODIGO AND MANUFACTURA.ACTIVO=1 \n" +
                "GROUP BY CODIGOS.turno\n" +
                "ORDER BY linea");
            
            while(rs.next())
            {
                                modelo.addRow(new Object[]{rs.getString("turno"), rs.getString("HCLINEA"), rs.getString("contension"),  rs.getString("soporte"), rs.getString("tab.insp"), rs.getString("rutasinternas"), rs.getString("lps"), rs.getString("SopLPS"), rs.getString("piloto"), rs.getString("ftq"), rs.getString("sistemas"), rs.getString("rutas")});

            }
            
         tblTurnos.setModel(modelo);
            
        }catch(Exception e)
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

        panelPrincipal = new javax.swing.JPanel();
        pnlMenu = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCadena = new Compille.RXTable();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblPlataforma = new Compille.RXTable();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblTurnos = new Compille.RXTable();
        jScrollPane8 = new javax.swing.JScrollPane();
        tblCodigos = new Compille.RXTable();
        btnExportar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1532, 720));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelPrincipal.setPreferredSize(new java.awt.Dimension(1400, 720));
        panelPrincipal.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlMenu.setBackground(new java.awt.Color(255, 255, 153));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 255));
        jLabel6.setText("Dist. de Gente");

        tblCadena.setModel(new javax.swing.table.DefaultTableModel(
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
        tblCadena.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        tblCadena.setSelectAllForEdit(true);
        jScrollPane1.setViewportView(tblCadena);

        tblPlataforma.setModel(new javax.swing.table.DefaultTableModel(
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
        tblPlataforma.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        tblPlataforma.setSelectAllForEdit(true);
        jScrollPane6.setViewportView(tblPlataforma);

        tblTurnos.setModel(new javax.swing.table.DefaultTableModel(
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
        tblTurnos.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        tblTurnos.setSelectAllForEdit(true);
        jScrollPane7.setViewportView(tblTurnos);

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
        tblCodigos.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        tblCodigos.setSelectAllForEdit(true);
        jScrollPane8.setViewportView(tblCodigos);

        btnExportar.setBackground(new java.awt.Color(255, 204, 0));
        btnExportar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/filetype-xls-icon (2).png"))); // NOI18N
        btnExportar.setText("EXPORTAR");
        btnExportar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnExportar.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnExportar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnExportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlMenuLayout = new javax.swing.GroupLayout(pnlMenu);
        pnlMenu.setLayout(pnlMenuLayout);
        pnlMenuLayout.setHorizontalGroup(
            pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMenuLayout.createSequentialGroup()
                .addGroup(pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlMenuLayout.createSequentialGroup()
                        .addGap(473, 473, 473)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlMenuLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 730, Short.MAX_VALUE)
                            .addComponent(jScrollPane1))
                        .addGap(18, 18, 18)
                        .addGroup(pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 728, Short.MAX_VALUE)
                            .addComponent(jScrollPane6)))
                    .addGroup(pnlMenuLayout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addComponent(btnExportar, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        pnlMenuLayout.setVerticalGroup(
            pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMenuLayout.createSequentialGroup()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlMenuLayout.createSequentialGroup()
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25)
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(pnlMenuLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25)
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnExportar, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 16, Short.MAX_VALUE))))
        );

        panelPrincipal.add(pnlMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1530, 670));

        getContentPane().add(panelPrincipal, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1530, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnExportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportarActionPerformed
        // TODO add your handling code here:
        DefaultTableModel modelo=null;
        ArrayList<DefaultTableModel> modelos=new ArrayList<DefaultTableModel>();
        ArrayList<String> Titulos=new ArrayList<String>();
        Object[] possibilities = {"CADENA", "PLATAFORMA", "CODIGO", "TURNO", "TODOS"};
                    String s = (String) JOptionPane.showInputDialog(
                            null,
                            "¿QUE DESEA VER?\n",
                            "REPORTES",
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            possibilities,
                            " ");
       try{
           
           switch(s)
           {
               case "CADENA":
                   modelo=(DefaultTableModel) tblCadena.getModel();
                   
               break;
               case "PLATAFORMA":
                   modelo=(DefaultTableModel) tblPlataforma.getModel();
               break;
               case "CODIGO":
                   modelo=(DefaultTableModel) tblCodigos.getModel();
               break;
               case "TURNO":
                   modelo=(DefaultTableModel) tblTurnos.getModel();
               break;       
               case "TODOS":
                   Titulos.add("CADENA");
                   Titulos.add("PLATAFORMA");
                   Titulos.add("CODIGO");
                   Titulos.add("TURNO");
                   modelos.add((DefaultTableModel) tblCadena.getModel());
                   modelos.add((DefaultTableModel) tblPlataforma.getModel());
                   modelos.add((DefaultTableModel) tblCodigos.getModel());
                   modelos.add((DefaultTableModel) tblTurnos.getModel());
               break;     
           }
       if(modelo!=null)
       { 
           Excel e=new Excel(modelo, s );
       }
       else if(s.equals("TODOS"))
       {
          Excel e=new Excel(modelos, Titulos );
       }
       
       }catch(Exception e)
       {
           System.out.println(e.toString());
       }
           
    }//GEN-LAST:event_btnExportarActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
        Principal p=new Principal(Principal.UsuarioLogeado);
        p.setLocationRelativeTo(null);
        p.setVisible(true);
    }//GEN-LAST:event_formWindowClosed

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
            java.util.logging.Logger.getLogger(ReporteDistGente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ReporteDistGente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ReporteDistGente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ReporteDistGente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ReporteDistGente().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExportar;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JPanel panelPrincipal;
    private javax.swing.JPanel pnlMenu;
    private Compille.RXTable tblCadena;
    private Compille.RXTable tblCodigos;
    private Compille.RXTable tblPlataforma;
    private Compille.RXTable tblTurnos;
    // End of variables declaration//GEN-END:variables
}
