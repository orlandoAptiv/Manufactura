/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package effortGUA;

import CapturasEffort.*;
import Clases.Conection;
import Clases.RenderCambioLetra;
import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import manufactura.Principal;

/**
 *
 * @author Felipe M
 */
public class CapturaTotales2 extends javax.swing.JFrame {

    /**
     * Creates new form CapturaTotales2
     */
    public CapturaTotales2() {
        try {
            initComponents();
//            Principal.cn=new Conection();
            VerificarAno();
            inicializarDgv();
        } catch (Exception ex) {
            Logger.getLogger(CapturaTotales2.class.getName()).log(Level.SEVERE, null, ex);
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

        jScrollPane1 = new javax.swing.JScrollPane();
        tblConc = new Compille.RXTable();
        jLabel1 = new javax.swing.JLabel();
        GUAMUCHIL = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        tblConc.setModel(new javax.swing.table.DefaultTableModel(
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
        tblConc.setSelectAllForEdit(true);
        tblConc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblConcMouseClicked(evt);
            }
        });
        tblConc.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tblConcPropertyChange(evt);
            }
        });
        tblConc.addVetoableChangeListener(new java.beans.VetoableChangeListener() {
            public void vetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {
                tblConcVetoableChange(evt);
            }
        });
        jScrollPane1.setViewportView(tblConc);

        jLabel1.setFont(new java.awt.Font("Arial", 1, 30)); // NOI18N
        jLabel1.setText("CAPTURA DE CONCENTRADO MENSUALES PARA EFFORT ");

        GUAMUCHIL.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        GUAMUCHIL.setText("GUAMUCHIL");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1241, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(123, 123, 123)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1115, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(556, 556, 556)
                        .addComponent(GUAMUCHIL)))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(GUAMUCHIL)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void VerificarAno(){
        try {
            ResultSet rs=Principal.cn.GetConsulta("SELECT * FROM concentradoeffort_GUA WHERE ano=YEAR(NOW())");
            if(!rs.isBeforeFirst())
            {
                ArrayList<Object> lista=new ArrayList<Object>();
                Principal.cn.EjecutarInsertOb("INSERT INTO concentradoeffort_GUA (nombre, ano) VALUES ('EMPAQUE', (SELECT YEAR(NOW()) ))", lista);
                Principal.cn.EjecutarInsertOb("INSERT INTO concentradoeffort_GUA(nombre, ano) VALUES ('SCRAP', (SELECT YEAR(NOW()) ))", lista);
                Principal.cn.EjecutarInsertOb("INSERT INTO concentradoeffort_GUA (nombre, ano) VALUES ('SORTEO', (SELECT YEAR(NOW()) ))", lista);
                Principal.cn.EjecutarInsertOb("INSERT INTO concentradoeffort_GUA (nombre, ano) VALUES ('SQ.FT', (SELECT YEAR(NOW()) ))", lista);
                Principal.cn.EjecutarInsertOb("INSERT INTO concentradoeffort_GUA (nombre, ano) VALUES ('TLO', (SELECT YEAR(NOW()) ))", lista);
            }else{
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(CapturaTotales2.class.getName()).log(Level.SEVERE, null, ex);
        }
                
    }
   
    public  void inicializarDgv(){
       DefaultTableModel modelo =new DefaultTableModel();
    try
    {
        modelo.setColumnIdentifiers(new Object[]{"MODULO", "ENE", "FEB", "MAR", "ABR", "MAY", "JUN", "JUL", "AGOS", "SEP", "OCT", "NOV", "DIC", "ANO", "USUARIO", "FECHA"  });
          ResultSet rs= Principal.cn.GetConsulta("SELECT concentradoeffort_GUA.nombre, concentradoeffort_GUA.ENERO,  concentradoeffort_GUA.FEBRERO,  concentradoeffort_GUA.MARZO, concentradoeffort_GUA.ABRIL, concentradoeffort_GUA.MAYO,\n" +
            "concentradoeffort_GUA.JUNIO, concentradoeffort_GUA.JULIO, concentradoeffort_GUA.AGOSTO, concentradoeffort_GUA.SEPTIEMBRE,  concentradoeffort_GUA.OCTUBRE, concentradoeffort_GUA.NOVIEMBRE,\n" +
            "concentradoeffort_GUA.DICIEMBRE, concentradoeffort_GUA.ano, concentradoeffort_GUA.usuariomodifico, concentradoeffort_GUA.fechamodifico FROM concentradoeffort_GUA where ano=YEAR(NOW())");
           while(rs.next())
            {
              modelo.addRow(new Object[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13), rs.getString(14), rs.getString(15), rs.getString(16)});
            }
              tblConc.setModel(modelo);
              tblConc.getColumnModel().getColumn(0).setCellRenderer(new RenderCambioLetra(Color.LIGHT_GRAY));
              tblConc.getColumnModel().getColumn(0).setMaxWidth(120);
              tblConc.getColumnModel().getColumn(0).setMinWidth(120);
              tblConc.getColumnModel().getColumn(0).setPreferredWidth(120);
              tblConc.getColumnModel().getColumn(13).setMaxWidth(0);
              tblConc.getColumnModel().getColumn(13).setMinWidth(0);
              tblConc.getColumnModel().getColumn(13).setPreferredWidth(0);
              tblConc.getColumnModel().getColumn(14).setMaxWidth(0);
              tblConc.getColumnModel().getColumn(14).setMinWidth(0);
              tblConc.getColumnModel().getColumn(14).setPreferredWidth(0);
              tblConc.getColumnModel().getColumn(15).setMaxWidth(0);
              tblConc.getColumnModel().getColumn(15).setMinWidth(0);
              tblConc.getColumnModel().getColumn(15).setPreferredWidth(0);
    }catch(Exception e)
    {
        System.out.println(e.toString());
    }
        
    }
    
    private void tblConcMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblConcMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_tblConcMouseClicked

    private void tblConcPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tblConcPropertyChange
        // TODO add your handling code here:
        try{
         if((tblConc.getSelectedRow()>-1))
            {
            ArrayList<Object> lista=new ArrayList<Object>();
            lista.add(tblConc.getValueAt(tblConc.getSelectedRow(), 1).toString());
            lista.add(tblConc.getValueAt(tblConc.getSelectedRow(), 2).toString());
            lista.add(tblConc.getValueAt(tblConc.getSelectedRow(), 3).toString());
            lista.add(tblConc.getValueAt(tblConc.getSelectedRow(), 4).toString());
            lista.add(tblConc.getValueAt(tblConc.getSelectedRow(), 5).toString());
            lista.add(tblConc.getValueAt(tblConc.getSelectedRow(), 6).toString());
            lista.add(tblConc.getValueAt(tblConc.getSelectedRow(), 7).toString());
            lista.add(tblConc.getValueAt(tblConc.getSelectedRow(), 8).toString());
            lista.add(tblConc.getValueAt(tblConc.getSelectedRow(), 9).toString());
            lista.add(tblConc.getValueAt(tblConc.getSelectedRow(), 10).toString());
            lista.add(tblConc.getValueAt(tblConc.getSelectedRow(), 11).toString());
            lista.add(tblConc.getValueAt(tblConc.getSelectedRow(), 12).toString());
  //          lista.add(Principal.UsuarioLogeado.nombre);
            lista.add(1);
            lista.add(tblConc.getValueAt(tblConc.getSelectedRow(), 0).toString());
            lista.add(tblConc.getValueAt(tblConc.getSelectedRow(), 13).toString());
            Principal.cn.EjecutarInsertOb("update concentradoeffort_GUA set enero=?, febrero=?, marzo=?, abril=?, mayo=?, junio=?, julio=?, agosto=?, septiembre=?, octubre=?, noviembre=?, diciembre=?, usuariomodifico=?, fechamodifico=now() where nombre=? and ano=? ", lista);
            inicializarDgv();
            }    
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
    }//GEN-LAST:event_tblConcPropertyChange

    private void tblConcVetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {//GEN-FIRST:event_tblConcVetoableChange
        // TODO add your handling code here:
    }//GEN-LAST:event_tblConcVetoableChange

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
            java.util.logging.Logger.getLogger(CapturaTotales2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CapturaTotales2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CapturaTotales2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CapturaTotales2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CapturaTotales2().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel GUAMUCHIL;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private Compille.RXTable tblConc;
    // End of variables declaration//GEN-END:variables
}
