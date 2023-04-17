/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Reportes;

import Clases.PlataformaGenteCorte;
import java.awt.Color;
import java.awt.Image;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import manufactura.EficienciaPlanta;
import manufactura.Principal;

public class DesgloseCorte extends javax.swing.JFrame {

    /**
     * Creates new form DesgloseCorte
     */
    DefaultTableModel modelo;
    ArrayList<PlataformaGenteCorte> gente = new ArrayList<PlataformaGenteCorte>();

    public DesgloseCorte() {
        try {
            initComponents();
            //   Principal.cn=new Conection();
            Thread t = new Thread(new Runnable() {

                @Override
                public void run() {
                    SacarTablaDesgloseCorte();
                    crearPanelTotal(modelo);
                    Image icon = new ImageIcon(getClass().getResource("/Images/competitors-icon.png")).getImage();
                    setIconImage(icon);
                }
            });
            t.start();
            // crearPanelTotal2();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

    public void inicializarArray() {
        try {
            ResultSet rs = Principal.cn.GetConsulta("SELECT DISTINCT\n"
                    + "codigos.PLATAFORMA\n"
                    + "FROM\n"
                    + "codigos where codigos.cadena<>4 and codigos.PLATAFORMA<>'SERVICIOS' and codigos.linea<>'91'");
            while (rs.next()) {
                gente.add(new PlataformaGenteCorte(rs.getString("plataforma"), 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0));
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }

    }

    public void crearPanelTotal(DefaultTableModel modelo) {
        int t = 200;
        inicializarArray();
        for (int r = 0; r < modelo.getRowCount(); r++) {

            for (int i = 0; i < gente.size(); i++) {
                if (modelo.getValueAt(r, 2).equals(gente.get(i).NombrePlataforma)) {
                    gente.get(i).HCCorteMSD += Double.parseDouble(modelo.getValueAt(r, 8).toString());
                }
                gente.get(i).HCCorteManuf += Double.parseDouble(modelo.getValueAt(r, 9).toString());
                break;
            }

        }
        for (int c = 0; c < gente.size(); c++) {
            JPanel panel1 = new JPanel();
            panel1.setBounds(t, 0, 100, 100);
            panel1.add(new JLabel(gente.get(c).NombrePlataforma));
            panel1.add(new JLabel(gente.get(c).HCCorteMSD.toString()));
            panel1.add(new JLabel(gente.get(c).HCCorteManuf.toString()));
            panel1.setBackground(Color.ORANGE);
            t += 200;
            Paneltotales.add(panel1);
        }

    }

    public void SacarTablaDesgloseCorte() {
        Double totalHrsEmbA = 0.0;
        Double totalHrsEmbB = 0.0;
        Double totalHCCorteManufA = 0.0;
        Double totalHCCorteManufB = 0.0;
        try {
            ResultSet rs = Principal.cn.GetConsulta("SELECT codigos.turno, sum((manufactura.SALIDAENPIEZA*manufactura.PUNTOSPZAPOND)/100) as hrsemb\n"
                    + "FROM\n"
                    + "codigos ,\n"
                    + "manufactura\n"
                    + "WHERE\n"
                    + "codigos.IDCODIGO = manufactura.IDCODIGO and codigos.CADENA<>4 and codigos.CADENA<>5 and codigos.linea<>91 \n"
                    + "GROUP BY\n"
                    + "codigos.TURNO ");
            while (rs.next()) {
                if (rs.getString("TURNO").equals("A")) {
                    totalHrsEmbA = rs.getDouble("hrsemb");
                } else {
                    totalHrsEmbB = rs.getDouble("hrsemb");
                }
            }
            rs = Principal.cn.GetConsulta("SELECT\n"
                    + "codigos.CADENA, codigos.turno,\n"
                    + "manufactura.HCDIRLINEA\n"
                    + "FROM\n"
                    + "codigos ,\n"
                    + "manufactura\n"
                    + "WHERE\n"
                    + "codigos.CADENA = 4 AND\n"
                    + "codigos.IDCODIGO = manufactura.IDCODIGO");
            while (rs.next()) {
                if (rs.getString("TURNO").equals("A")) {
                    totalHCCorteManufA = rs.getDouble("HCDIRLINEA");
                } else {
                    totalHCCorteManufB = rs.getDouble("HCDIRLINEA");
                }
            }
            modelo = new DefaultTableModel();
            modelo.setColumnIdentifiers(new Object[]{"CAD", "LINEA", "PLAT.", "CODIGO", "PUNTOS.PIEZA", "SALIDA", "HRS.EMB", "HRS. EMB. CORTE", "PORC.DE HE", "MOD. MAFRA", "MOD. ING"});
//          rs=Principal.cn.GetConsulta("SELECT c.CADENA, c.linea, c.PLATAFORMA, c.CODIGO, c.TURNO, m.SALIDAENPIEZA, m.PUNTOSPZAPOND, ROUND(( m.SALIDAENPIEZA*m.PUNTOSPZAPOND)/100) as horasEMB,\n" +
//                "sum((g.hcdircorte*m.salidaenpieza)/100) as 'HORASEMBCORTE'    FROM\n" +
//                "codigos  as c, \n" +
//                "manufactura as m, gsd as g \n" +
//                  "WHERE \n" +
//                "c.IDCODIGO = m.IDCODIGO and  c.idcodigo=g.idcodigo and c.linea<>'91' and c.cadena<>4 \n" +
//                "ORDER BY cadena, turno ");
            rs = Principal.cn.GetConsulta("select manuf.CADENA, manuf.TURNO, manuf.linea, manuf.PLATAFORMA, manuf.CODIGO, manuf.horasEMB, manuf.SALIDAENPIEZA, manuf.PUNTOSPZAPOND, g.HCDIRCORTE, (manuf.SALIDAENPIEZA*g.HCDIRCORTE)/100 as HRSEMBCORTE  from (select IDCODIGO, HCDIRCORTE from gsd) as g INNER JOIN \n"
                    + "(SELECT  c.IDCODIGO, c.CADENA, c.linea, c.PLATAFORMA, c.CODIGO, c.TURNO, m.SALIDAENPIEZA, m.PUNTOSPZAPOND, ROUND(( m.SALIDAENPIEZA*m.PUNTOSPZAPOND)/100) as horasEMB\n"
                    + "  FROM\n"
                    + "codigos  as c, \n"
                    + "manufactura as m, gsd as g \n"
                    + "WHERE \n"
                    + "c.IDCODIGO = m.IDCODIGO and c.idcodigo=g.idcodigo and m.ACTIVO=1\n"
                    + "ORDER BY cadena, turno ) as manuf on g.idcodigo=manuf.IDCODIGO");
            while (rs.next()) {
                Double Porciento = 0.0;
                Double ModManufactura = 0.0;
                Double ModInge = 0.0;
                if (rs.getString("TURNO").equals("A")) {
                    Porciento = rs.getDouble("horasemb") / totalHrsEmbA;
                    ModManufactura = Porciento * totalHCCorteManufA;

                } else {
                    Porciento = rs.getDouble("horasemb") / totalHrsEmbB;
                    ModManufactura = Porciento * totalHCCorteManufB;

                }
                ModInge = Porciento * 96;
                ModInge = EficienciaPlanta.Regresa2Decimales(ModInge);
                Porciento = EficienciaPlanta.Regresa2Decimales(Porciento * 100);
                modelo.addRow(new Object[]{rs.getString("CADENA") + rs.getString("TURNO"), rs.getString("linea"), rs.getString("plataforma"), rs.getString("codigo"), rs.getString("puntospzapond"), rs.getString("salidaenpieza"), rs.getString("horasemb"), EficienciaPlanta.Regresa2Decimales(rs.getDouble("HRSEMBCORTE")), Porciento, ModManufactura, ModInge});
            }
            tblTablaCorte.setModel(modelo);
            tblTablaCorte.getColumnModel().getColumn(0).setMaxWidth(40);
            tblTablaCorte.getColumnModel().getColumn(0).setMinWidth(40);
            tblTablaCorte.getColumnModel().getColumn(0).setPreferredWidth(40);
            //   crearPanelTotal(modelo);      
        } catch (Exception e) {
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

        jScrollPane2 = new javax.swing.JScrollPane();
        tblTablaCorte = new Compille.RXTable();
        jLabel1 = new javax.swing.JLabel();
        Paneltotales = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblTablaCorte.setModel(new javax.swing.table.DefaultTableModel(
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
        tblTablaCorte.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblTablaCorte.setSelectAllForEdit(true);
        tblTablaCorte.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tblTablaCortePropertyChange(evt);
            }
        });
        jScrollPane2.setViewportView(tblTablaCorte);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 90, 980, -1));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel1.setText("DISTRIBUCION DE MOD DE CORTE A LINEAS DE ENSAMBLE FINAL\t\t\t\t\t\t\t\t\t ");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, -1, -1));

        javax.swing.GroupLayout PaneltotalesLayout = new javax.swing.GroupLayout(Paneltotales);
        Paneltotales.setLayout(PaneltotalesLayout);
        PaneltotalesLayout.setHorizontalGroup(
            PaneltotalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 980, Short.MAX_VALUE)
        );
        PaneltotalesLayout.setVerticalGroup(
            PaneltotalesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 110, Short.MAX_VALUE)
        );

        getContentPane().add(Paneltotales, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 540, 980, 110));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblTablaCortePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tblTablaCortePropertyChange
        // TODO add your handling code here
    }//GEN-LAST:event_tblTablaCortePropertyChange

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
        Principal p = new Principal(Principal.UsuarioLogeado);
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
            java.util.logging.Logger.getLogger(DesgloseCorte.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DesgloseCorte.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DesgloseCorte.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DesgloseCorte.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DesgloseCorte().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Paneltotales;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private Compille.RXTable tblTablaCorte;
    // End of variables declaration//GEN-END:variables
}
