/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package manufactura;

import Reportes.CeldaCheck;
import Reportes.Render_CheckBox;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Felipe
 */
public class CapturaDetalladaConv extends javax.swing.JFrame {

    /**
     * Creates new form CapturaDetalladaConv
     */
    String Turno, Cadena;
    public CapturaDetalladaConv() {
        initComponents();
        Image icon = new ImageIcon(getClass().getResource("/Images/competitors-icon.png")).getImage();
            setIconImage(icon);
        enlazarDgv("1", "A");
        GetCambiarCalorBoton("1A");
    }

    public void  GetCambiarCalorBoton(String NombreBoton){
//        String color=BtnCad1B.getBackground().toString();
        BtnCad1A.setBackground(Color.getColor("control"));
        BtnCad2A.setBackground(Color.getColor("control"));
        BtnCad3A.setBackground(Color.getColor("control"));
        BtnCad4A.setBackground(Color.getColor("control"));
        BtnCad6A.setBackground(Color.getColor("control"));
        BtnCad1B.setBackground(Color.getColor("control"));
        BtnCad2B.setBackground(Color.getColor("control"));
        BtnCad3B.setBackground(Color.getColor("control"));
        BtnCad4B.setBackground(Color.getColor("control"));
        BtnCad6B.setBackground(Color.getColor("control"));
        BtnCad8B.setBackground(Color.getColor("control"));
        BtnCad8A.setBackground(Color.getColor("control"));
        switch(NombreBoton)
        {
            case "1A":
                BtnCad1A.setBackground(Color.GREEN);
                break;
            case "2A":
                BtnCad2A.setBackground(Color.GREEN);
                break;
            case "3A":
                BtnCad3A.setBackground(Color.GREEN);
                break;
            case "4A":
                BtnCad4A.setBackground(Color.GREEN);
                break;
            case "6A":
                BtnCad6A.setBackground(Color.GREEN);
                break;
            case "1B":
                BtnCad1B.setBackground(Color.GREEN);
                break;
            case "2B":
                BtnCad2B.setBackground(Color.GREEN);
                break;
            case "3B":
                BtnCad3B.setBackground(Color.GREEN);
                break;
            case "4B":
                BtnCad4B.setBackground(Color.GREEN);
                break;
            case "6B":
                BtnCad6B.setBackground(Color.GREEN);
                break;
                  case "5B":
                BtnCad8B.setBackground(Color.GREEN);
                break;
            case "5A":
                BtnCad8A.setBackground(Color.GREEN);
                break;
                        
        }
        
           
    }
    
    public void enlazarDgv(String Cadena, String Turno){
            try   { 
               
                this.Turno=Turno;
                this.Cadena=Cadena;
                DefaultTableModel modelotemp=null;
         
              modelotemp=new DefaultTableModel();
              modelotemp.setColumnIdentifiers(new Object[]{"IDCODIGO", "PLAT.", "CODIGO", "LINEA", "TURNO", "POST-OPERA", "KITS", "G.LINEA", "ESTACIONES",  "ACTIVO"});   
             ResultSet rs=Principal.cn.GetConsulta("SELECT c.IDCODIGO, c.codigo, c.ARNES, c.linea, c.plataforma,  HCDIRLINEA, c.turno,  kits as KITS, Elinea AS HCLINEA,  estaciones  AS ESTACION, m.activo FROM manufactura AS m, codigos as c where m.IDCODIGO=c.IDCODIGO and c.turno='"+Turno+"' and cadena='"+Cadena+"'");
           
            while(rs.next())
            {
                modelotemp.addRow(new Object[]{rs.getString("idcodigo"), rs.getString("PLATAFORMA"), rs.getString("CODIGO"), rs.getString("LINEA"), rs.getString("turno"), rs.getDouble("HCDIRLINEA"), rs.getDouble("KITS"), rs.getDouble("HCLINEA"), rs.getDouble("ESTACION"),   rs.getBoolean("ACTIVO")});
            }
             tblCodigos.setModel(modelotemp);
             tblCodigos.getColumnModel().getColumn(9).setCellEditor(new CeldaCheck());
              tblCodigos.getColumnModel().getColumn(9).setCellRenderer(new Render_CheckBox());
              tblCodigos.getColumnModel().getColumn(9).setMaxWidth(30);
                Font font = tblCodigos.getFont();
              font.deriveFont(48);
              tblCodigos.setFont(font);
              tblCodigos.getColumnModel().getColumn(0).setMaxWidth(35);
              tblCodigos.getColumnModel().getColumn(0).setMinWidth(35);
              tblCodigos.getColumnModel().getColumn(0).setPreferredWidth(35);
            }  
            catch(Exception e)
                    {
                         System.out.println(e);
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
        jLabel9 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCodigos = new Compille.RXTable();
        jPanel2 = new javax.swing.JPanel();
        BtnCad1A = new javax.swing.JButton();
        BtnCad2A = new javax.swing.JButton();
        BtnCad3A = new javax.swing.JButton();
        BtnCad4A = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        BtnCad6A = new javax.swing.JButton();
        BtnCad8A = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        BtnCad1B = new javax.swing.JButton();
        BtnCad2B = new javax.swing.JButton();
        BtnCad3B = new javax.swing.JButton();
        BtnCad4B = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        BtnCad6B = new javax.swing.JButton();
        BtnCad8B = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(0, 0, 255));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("CAPTURA DETALLADA LINEA");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1255, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addContainerGap(372, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 553, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(330, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addContainerGap(28, Short.MAX_VALUE)
                    .addComponent(jLabel9)
                    .addContainerGap(28, Short.MAX_VALUE)))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1255, -1));

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
        tblCodigos.setSelectAllForEdit(true);
        tblCodigos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCodigosMouseClicked(evt);
            }
        });
        tblCodigos.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tblCodigosPropertyChange(evt);
            }
        });
        tblCodigos.addVetoableChangeListener(new java.beans.VetoableChangeListener() {
            public void vetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {
                tblCodigosVetoableChange(evt);
            }
        });
        jScrollPane1.setViewportView(tblCodigos);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(137, 111, 1039, 289));

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        BtnCad1A.setBackground(new java.awt.Color(204, 204, 204));
        BtnCad1A.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        BtnCad1A.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/CAD1.png"))); // NOI18N
        BtnCad1A.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        BtnCad1A.setFocusable(false);
        BtnCad1A.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        BtnCad1A.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        BtnCad1A.setPreferredSize(new java.awt.Dimension(97, 75));
        BtnCad1A.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        BtnCad1A.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCad1AActionPerformed(evt);
            }
        });
        jPanel2.add(BtnCad1A, new org.netbeans.lib.awtextra.AbsoluteConstraints(27, 82, 90, 79));

        BtnCad2A.setBackground(new java.awt.Color(204, 204, 204));
        BtnCad2A.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        BtnCad2A.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/CAD2.png"))); // NOI18N
        BtnCad2A.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        BtnCad2A.setFocusable(false);
        BtnCad2A.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        BtnCad2A.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        BtnCad2A.setPreferredSize(new java.awt.Dimension(97, 75));
        BtnCad2A.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        BtnCad2A.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCad2AActionPerformed(evt);
            }
        });
        jPanel2.add(BtnCad2A, new org.netbeans.lib.awtextra.AbsoluteConstraints(123, 82, 87, 79));

        BtnCad3A.setBackground(new java.awt.Color(204, 204, 204));
        BtnCad3A.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        BtnCad3A.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/CAD3.png"))); // NOI18N
        BtnCad3A.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        BtnCad3A.setFocusable(false);
        BtnCad3A.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        BtnCad3A.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        BtnCad3A.setPreferredSize(new java.awt.Dimension(97, 75));
        BtnCad3A.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        BtnCad3A.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCad3AActionPerformed(evt);
            }
        });
        jPanel2.add(BtnCad3A, new org.netbeans.lib.awtextra.AbsoluteConstraints(216, 82, 85, 79));

        BtnCad4A.setBackground(new java.awt.Color(204, 204, 204));
        BtnCad4A.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        BtnCad4A.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/cad4.png"))); // NOI18N
        BtnCad4A.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        BtnCad4A.setFocusable(false);
        BtnCad4A.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        BtnCad4A.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        BtnCad4A.setPreferredSize(new java.awt.Dimension(97, 75));
        BtnCad4A.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        BtnCad4A.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCad4AActionPerformed(evt);
            }
        });
        jPanel2.add(BtnCad4A, new org.netbeans.lib.awtextra.AbsoluteConstraints(307, 82, 79, 79));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 255));
        jLabel10.setText("A");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 10, -1, -1));

        BtnCad6A.setBackground(new java.awt.Color(204, 204, 204));
        BtnCad6A.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        BtnCad6A.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/cad6.png"))); // NOI18N
        BtnCad6A.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        BtnCad6A.setFocusable(false);
        BtnCad6A.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        BtnCad6A.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        BtnCad6A.setPreferredSize(new java.awt.Dimension(97, 75));
        BtnCad6A.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        BtnCad6A.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCad6AActionPerformed(evt);
            }
        });
        jPanel2.add(BtnCad6A, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 80, 79, 79));

        BtnCad8A.setBackground(new java.awt.Color(204, 204, 204));
        BtnCad8A.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        BtnCad8A.setForeground(new java.awt.Color(51, 51, 255));
        BtnCad8A.setText("CADENA 5");
        BtnCad8A.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        BtnCad8A.setFocusable(false);
        BtnCad8A.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        BtnCad8A.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        BtnCad8A.setPreferredSize(new java.awt.Dimension(97, 75));
        BtnCad8A.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        BtnCad8A.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCad8AActionPerformed(evt);
            }
        });
        jPanel2.add(BtnCad8A, new org.netbeans.lib.awtextra.AbsoluteConstraints(473, 80, 79, 79));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 450, 570, -1));

        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        BtnCad1B.setBackground(new java.awt.Color(204, 204, 204));
        BtnCad1B.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        BtnCad1B.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/CAD1.png"))); // NOI18N
        BtnCad1B.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        BtnCad1B.setFocusable(false);
        BtnCad1B.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        BtnCad1B.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        BtnCad1B.setPreferredSize(new java.awt.Dimension(97, 75));
        BtnCad1B.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        BtnCad1B.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCad1BActionPerformed(evt);
            }
        });
        jPanel3.add(BtnCad1B, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 90, 79));

        BtnCad2B.setBackground(new java.awt.Color(204, 204, 204));
        BtnCad2B.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        BtnCad2B.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/CAD2.png"))); // NOI18N
        BtnCad2B.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        BtnCad2B.setFocusable(false);
        BtnCad2B.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        BtnCad2B.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        BtnCad2B.setPreferredSize(new java.awt.Dimension(97, 75));
        BtnCad2B.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        BtnCad2B.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCad2BActionPerformed(evt);
            }
        });
        jPanel3.add(BtnCad2B, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 80, 87, 79));

        BtnCad3B.setBackground(new java.awt.Color(204, 204, 204));
        BtnCad3B.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        BtnCad3B.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/CAD3.png"))); // NOI18N
        BtnCad3B.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        BtnCad3B.setFocusable(false);
        BtnCad3B.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        BtnCad3B.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        BtnCad3B.setPreferredSize(new java.awt.Dimension(97, 75));
        BtnCad3B.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        BtnCad3B.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCad3BActionPerformed(evt);
            }
        });
        jPanel3.add(BtnCad3B, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 80, 85, 79));

        BtnCad4B.setBackground(new java.awt.Color(204, 204, 204));
        BtnCad4B.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        BtnCad4B.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/cad4.png"))); // NOI18N
        BtnCad4B.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        BtnCad4B.setFocusable(false);
        BtnCad4B.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        BtnCad4B.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        BtnCad4B.setPreferredSize(new java.awt.Dimension(97, 75));
        BtnCad4B.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        BtnCad4B.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCad4BActionPerformed(evt);
            }
        });
        jPanel3.add(BtnCad4B, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 80, 79, 79));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 255));
        jLabel11.setText("B");
        jPanel3.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(221, 14, -1, -1));

        BtnCad6B.setBackground(new java.awt.Color(204, 204, 204));
        BtnCad6B.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        BtnCad6B.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/cad6.png"))); // NOI18N
        BtnCad6B.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        BtnCad6B.setFocusable(false);
        BtnCad6B.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        BtnCad6B.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        BtnCad6B.setPreferredSize(new java.awt.Dimension(97, 75));
        BtnCad6B.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        BtnCad6B.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCad6BActionPerformed(evt);
            }
        });
        jPanel3.add(BtnCad6B, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 80, 79, 79));

        BtnCad8B.setBackground(new java.awt.Color(204, 204, 204));
        BtnCad8B.setFont(new java.awt.Font("Trebuchet MS", 1, 14)); // NOI18N
        BtnCad8B.setForeground(new java.awt.Color(51, 51, 255));
        BtnCad8B.setText("CADENA 5");
        BtnCad8B.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        BtnCad8B.setFocusable(false);
        BtnCad8B.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        BtnCad8B.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        BtnCad8B.setPreferredSize(new java.awt.Dimension(97, 75));
        BtnCad8B.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        BtnCad8B.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCad8BActionPerformed(evt);
            }
        });
        jPanel3.add(BtnCad8B, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 80, 79, 79));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(628, 450, 550, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblCodigosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCodigosMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_tblCodigosMouseClicked

    private void tblCodigosPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tblCodigosPropertyChange
        // TODO add your handling code here:
        try{
            if( (tblCodigos.getSelectedRow()>-1))
            {

                ArrayList<Object> lista=new ArrayList<Object>();
                
                lista.add(tblCodigos.getValueAt(tblCodigos.getSelectedRow(), 5).toString());
                lista.add(tblCodigos.getValueAt(tblCodigos.getSelectedRow(), 6).toString());
                lista.add(tblCodigos.getValueAt(tblCodigos.getSelectedRow(), 7).toString());
                lista.add(tblCodigos.getValueAt(tblCodigos.getSelectedRow(), 8).toString());
                lista.add(Principal.UsuarioLogeado.codigo);
                if(tblCodigos.getValueAt(tblCodigos.getSelectedRow(), 9).toString().equals("true"))
                lista.add(1);
                else
                lista.add(0);
                lista.add(tblCodigos.getValueAt(tblCodigos.getSelectedRow(), 0).toString());
                Principal.cn.EjecutarInsertOb("update MANUFACTURA as m set m.hcdirlinea=?,  m.kits=?,  m.elinea=?,  m.estaciones=?, m.usuariomodif=?, m.activo=?  where m.idcodigo=? ", lista);
                //DefinirParametros();
                enlazarDgv(this.Cadena, this.Turno);
                //enlazarPorc();

            }
            
            
            
            
            
            
            
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
    }//GEN-LAST:event_tblCodigosPropertyChange

    private void tblCodigosVetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {//GEN-FIRST:event_tblCodigosVetoableChange
        // TODO add your handling code here:

    }//GEN-LAST:event_tblCodigosVetoableChange

    private void BtnCad1AActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCad1AActionPerformed
        // TODO add your handling code here:
        //        CapturaExcelManufactura cEG=new CapturaExcelManufactura();
        if((!this.Cadena.equals("1"))||(!this.Turno.equals("A")))
        {
            enlazarDgv("1", "A");
            GetCambiarCalorBoton("1A");
            
        }
    }//GEN-LAST:event_BtnCad1AActionPerformed

    private void BtnCad2AActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCad2AActionPerformed
        // TODO add your handling code here:
        if((!this.Cadena.equals("2"))||(!this.Turno.equals("A")))
        {
            enlazarDgv("2", "A");
            GetCambiarCalorBoton("2A");
        }
        
    }//GEN-LAST:event_BtnCad2AActionPerformed

    private void BtnCad3AActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCad3AActionPerformed
        // TODO add your handling code here:
        if((!this.Cadena.equals("3"))||(!this.Turno.equals("A")))
        {
            enlazarDgv("3", "A");
            GetCambiarCalorBoton("3A");
        }
    }//GEN-LAST:event_BtnCad3AActionPerformed

    private void BtnCad4AActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCad4AActionPerformed
        // TODO add your handling code here:
        if((!this.Cadena.equals("4"))||(!this.Turno.equals("A")))
        {
            enlazarDgv("4", "A");
            GetCambiarCalorBoton("4A");
        }
    }//GEN-LAST:event_BtnCad4AActionPerformed

    private void BtnCad1BActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCad1BActionPerformed
        // TODO add your handling code here:
        if((!this.Cadena.equals("1"))||(!this.Turno.equals("B")))
        {
            enlazarDgv("1", "B");
            GetCambiarCalorBoton("1B");
        }
    }//GEN-LAST:event_BtnCad1BActionPerformed

    private void BtnCad2BActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCad2BActionPerformed
        // TODO add your handling code here:
        if((!this.Cadena.equals("2"))||(!this.Turno.equals("B")))
        {
            enlazarDgv("2", "B");
            GetCambiarCalorBoton("2B");
        }
    }//GEN-LAST:event_BtnCad2BActionPerformed

    private void BtnCad3BActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCad3BActionPerformed
        // TODO add your handling code here:
        if((!this.Cadena.equals("3"))||(!this.Turno.equals("B")))
        {
            enlazarDgv("3", "B");
            GetCambiarCalorBoton("3B");
        }
    }//GEN-LAST:event_BtnCad3BActionPerformed

    private void BtnCad4BActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCad4BActionPerformed
        // TODO add your handling code here:
        if((!this.Cadena.equals("4"))||(!this.Turno.equals("B")))
        {
            enlazarDgv("4", "B");
            GetCambiarCalorBoton("4B");
        }
    }//GEN-LAST:event_BtnCad4BActionPerformed

    private void BtnCad6AActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCad6AActionPerformed
        // TODO add your handling code here:
        if((!this.Cadena.equals("6"))||(!this.Turno.equals("A")))
        {
            enlazarDgv("6", "A");
            GetCambiarCalorBoton("6A");
        }
    }//GEN-LAST:event_BtnCad6AActionPerformed

    private void BtnCad6BActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCad6BActionPerformed
        // TODO add your handling code here:
        if((!this.Cadena.equals("6"))||(!this.Turno.equals("B")))
        {
            enlazarDgv("6", "B");
            GetCambiarCalorBoton("6B");
        }
    }//GEN-LAST:event_BtnCad6BActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
        Principal   p=new Principal(Principal.UsuarioLogeado);
        p.setLocationRelativeTo(null);
        p.setVisible(true);
    }//GEN-LAST:event_formWindowClosed

    private void BtnCad8AActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCad8AActionPerformed
        // TODO add your handling code here:
         if((!this.Cadena.equals("5"))||(!this.Turno.equals("A")))
        {
            enlazarDgv("5", "A");
            GetCambiarCalorBoton("5A");
            
        }
    }//GEN-LAST:event_BtnCad8AActionPerformed

    private void BtnCad8BActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCad8BActionPerformed
        // TODO add your handling code here:
         if((!this.Cadena.equals("5"))||(!this.Turno.equals("B")))
        {
            enlazarDgv("5", "B");
            GetCambiarCalorBoton("5B");
            
        }
    }//GEN-LAST:event_BtnCad8BActionPerformed

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
            java.util.logging.Logger.getLogger(CapturaDetalladaConv.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CapturaDetalladaConv.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CapturaDetalladaConv.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CapturaDetalladaConv.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CapturaDetalladaConv().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnCad1A;
    private javax.swing.JButton BtnCad1B;
    private javax.swing.JButton BtnCad2A;
    private javax.swing.JButton BtnCad2B;
    private javax.swing.JButton BtnCad3A;
    private javax.swing.JButton BtnCad3B;
    private javax.swing.JButton BtnCad4A;
    private javax.swing.JButton BtnCad4B;
    private javax.swing.JButton BtnCad6A;
    private javax.swing.JButton BtnCad6B;
    private javax.swing.JButton BtnCad8A;
    private javax.swing.JButton BtnCad8B;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private Compille.RXTable tblCodigos;
    // End of variables declaration//GEN-END:variables
}
