/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package manufactura;


import java.awt.Font;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author gzld6k
 */
public class CapturaExcelPP extends javax.swing.JFrame {

    /**
     * Creates new form CapturaExcelGSD
     */
    DefaultTableModel modelo;
     public CapturaExcelPP() {
        initComponents();
        EnlazarCbxPlataforma();
        cbxCadena.addItem("TODOS");
        if(Principal.UsuarioLogeado.Cadena1.equals("1"))
        {
            cbxCadena.addItem("1");
        }
        if(Principal.UsuarioLogeado.Cadena2.equals("1"))
        {
            cbxCadena.addItem("2");
        }
        if(Principal.UsuarioLogeado.Cadena3.equals("1"))
        {
            cbxCadena.addItem("3");
        }
        if(Principal.UsuarioLogeado.Cadena4.equals("1"))
        {
            cbxCadena.addItem("4");
        }
        if(Principal.UsuarioLogeado.Cadena5.equals("1"))
        {
            cbxCadena.addItem("5");
        }
        
        if(Principal.UsuarioLogeado.Cadena6.equals("1"))
        {
            cbxCadena.addItem("6");
        }
        
        // if(Principal.UsuarioLogeado.Cadena8.equals("1"))
       // {
        //    cbxCadena.addItem("5");
      //  }
    }

     public void EnlazarDgv(String Plataforma, String Arnes, String Cad1, String Cad2, String Cad3, String Cad4, String Cad5,String Cad6, String Codigo, String Turno){
        try {
            modelo =new DefaultTableModel(){ 
                    @Override
                public boolean isCellEditable(int row, int column) {
                     //all cells false
                        boolean rsp=true;
                        return rsp;
                        }
            };
            modelo.setColumnIdentifiers(new Object[]{"IDENT", "PLAT.",  "CODIGO", "LINEA", "TURNO", "POND.CORTE", "POND.LPS", "POND.EF.", "HRS.EMB"});
            //modelo.setColumnIdentifiers(new Object[]{"IDCODIGO", "PLAT.", "ARNES", "LINEA", "TURNO", "LINEA", "LPS", "SOPORTE", "TAB.INSP", "CORTE", "FTQ", "PILOTOS", "SISTEMAS", "RUTAS", "PTOS.PIEZA", "CAP.UTIL.HTA", "SAL.EN.PZA", "HRS.EMB", "HRS.PAG"});   
            String query="SELECT\n" +
                    "c.IDCODIGO, c.PLATAFORMA, c.CADENA, c.CODIGO, c.LINEA,\n" +
                    "c.TURNO,\n" +
                    "g.HCDIRCORTE, g.HCDIRLPS, g.HCDIRENSFINAL,\n" +
                    "TRUNCATE(((g.HCDIRCORTE*m.SALIDAENPIEZA)/100),2) as hrsEmb from\n" +
                    "(select * from codigos";
                    if((Cad1.equals("1")) || (Cad2.equals("1")) || (Cad3.equals("1")) || (Cad4.equals("1"))|| (Cad5.equals("1"))|| (Cad6.equals("1")))
                    {
                    query+=" where ";
                    if(Cad1.equals("1"))
                    {
                         query+= "CODIGOS.cadena='1' ";
                         if((Cad2.equals("1")) || (Cad3.equals("1")) || (Cad4.equals("1"))|| (Cad5.equals("1"))|| (Cad6.equals("1")))
                             query+="or ";
        
                    }
                    if(Cad2.equals("1"))
                    {
                        query+= "CODIGOS.cadena='2' ";
                          if( (Cad3.equals("1")) || (Cad4.equals("1"))|| (Cad5.equals("1"))|| (Cad6.equals("1")))
                             query+="or ";
                    }
                    if(Cad3.equals("1"))
                    {
                        query+= "CODIGOS.cadena='3' ";
                        if( (Cad4.equals("1"))|| (Cad5.equals("1"))|| (Cad6.equals("1")))
                             query+="or ";
                    }
                    
                     if(Cad5.equals("1"))
                    {
                        query+= "CODIGOS.cadena='5' ";
                        if( (Cad4.equals("1"))|| (Cad6.equals("1")))
                             query+="or ";
                    }
                     
                     if(Cad6.equals("1"))
                    {
                        query+= "CODIGOS.cadena='6' ";
                        if( (Cad4.equals("1")))
                             query+="or ";
                    }
                     
                    
                    if(Cad4.equals("1"))
                    {
                        query+= "CODIGOS.cadena='4' ";

                    }
                    
                    }
            query+=") as c,\n" +
                    "gsd as g,\n" +
                    "manufactura as m where \n" +
                    "c.IDCODIGO = g.IDCODIGO  and m.idcodigo=c.idcodigo and m.activo=1 ";
            if(!Plataforma.equals("TODOS"))
            query+="AND c.PLATAFORMA='"+Plataforma+"'";
            if(!Arnes.equals("TODOS"))
            query+= "AND  c.ARNES='"+Arnes+"'";
            if(!Codigo.equals("TODOS"))
            query+= "AND  c.Codigo='"+Codigo+"'";
                  if(!Turno.equals("TODOS"))
            query+= "AND  c.Turno='"+Turno+"'";
            ResultSet rs=Principal.cn.GetConsulta(query);
            while(rs.next())
            {
               modelo.addRow(new Object[]{rs.getString("idcodigo"), rs.getString("PLATAFORMA"), rs.getString("codigo"), rs.getString("LINEA"), rs.getString("turno"),  rs.getString("HCDIRCORTE"), rs.getString("HCDIRLPS"), rs.getString("HCDIRENSFINAL"), rs.getString("hrsEmb")  });
            }
              tblCodigos.setModel(modelo);
              tblCodigos.getColumnModel().getColumn(0).setMaxWidth(0);
              tblCodigos.getColumnModel().getColumn(0).setMinWidth(0);
              tblCodigos.getColumnModel().getColumn(0).setPreferredWidth(0);
              tblCodigos.getColumnModel().getColumn(1).setMaxWidth(100);
              tblCodigos.getColumnModel().getColumn(1).setMinWidth(100);
              tblCodigos.getColumnModel().getColumn(1).setPreferredWidth(100);
              tblCodigos.getColumnModel().getColumn(2).setMaxWidth(80);
              tblCodigos.getColumnModel().getColumn(2).setMinWidth(80);
              tblCodigos.getColumnModel().getColumn(2).setPreferredWidth(80);
              tblCodigos.getColumnModel().getColumn(3).setMaxWidth(50);
              tblCodigos.getColumnModel().getColumn(3).setMinWidth(50);
              tblCodigos.getColumnModel().getColumn(3).setPreferredWidth(50);  
              tblCodigos.getColumnModel().getColumn(4).setMaxWidth(50);
              tblCodigos.getColumnModel().getColumn(4).setMinWidth(50);
              tblCodigos.getColumnModel().getColumn(4).setPreferredWidth(50); 
//              tblCodigos.getColumnModel().getColumn(5).setMaxWidth(50);
//              tblCodigos.getColumnModel().getColumn(5).setMinWidth(50);
//              tblCodigos.getColumnModel().getColumn(5).setPreferredWidth(50);  
            Font font = tblCodigos.getFont();
            font.deriveFont(48);
            tblCodigos.setFont(font);
        }catch(Exception e)
        {
            System.out.println(e.toString());
        }
    }  
     
     public void EnlazarCbxPlataforma(){
     cbxPlataforma.removeAllItems();
     cbxPlataforma.addItem("TODOS");
        try {
            ResultSet rs= Principal.cn.GetConsulta("select DISTINCT(codigos.PLATAFORMA) as plataforma from codigos");
            while(rs.next())
            {
                cbxPlataforma.addItem(rs.getString("plataforma"));
                //modelo.addListDataListener(rs);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }
     
      public void EnlazarCbxArnes(String Plataforma){
     cbxArnes.removeAllItems();
     cbxArnes.addItem("TODOS");
        try {
            ResultSet rs= Principal.cn.GetConsulta("select DISTINCT(codigos.arnes) as ARNES from codigos where codigos.PLATAFORMA='"+Plataforma+"'");
            while(rs.next())
            {
                cbxArnes.addItem(rs.getString("ARNES"));
                //modelo.addListDataListener(rs);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }
     
      public void DefinirParametros(){
          if(cbxCadena.getSelectedItem()!=null)
          {
              switch (cbxCadena.getSelectedItem().toString())
            {
                case "1":
                    EnlazarDgv(cbxPlataforma.getSelectedItem().toString(), cbxArnes.getSelectedItem().toString(), "1", "0", "0", "0", "0", "0", "TODOS", cbxTurno.getSelectedItem().toString());
                    break;
                case "2":
                    EnlazarDgv(cbxPlataforma.getSelectedItem().toString(), cbxArnes.getSelectedItem().toString(), "0", "1", "0", "0", "0", "0",  "TODOS", cbxTurno.getSelectedItem().toString());
                break;
                case "3":
                     EnlazarDgv(cbxPlataforma.getSelectedItem().toString(), cbxArnes.getSelectedItem().toString(), "0", "0", "1", "0", "0", "0",  "TODOS", cbxTurno.getSelectedItem().toString());
                break;
                case "4":
                    EnlazarDgv(cbxPlataforma.getSelectedItem().toString(), cbxArnes.getSelectedItem().toString(), "0", "0", "0", "1","0", "0",  "TODOS", cbxTurno.getSelectedItem().toString());
                break;
                case "5":
                    EnlazarDgv(cbxPlataforma.getSelectedItem().toString(), cbxArnes.getSelectedItem().toString(), "0", "0", "0", "0", "1", "0", "TODOS", cbxTurno.getSelectedItem().toString());
                break;
                    
                    case "6":
                    EnlazarDgv(cbxPlataforma.getSelectedItem().toString(), cbxArnes.getSelectedItem().toString(), "0", "0", "0", "0", "0", "1", "TODOS", cbxTurno.getSelectedItem().toString());
                break;
                    
                case "TODOS":
                    EnlazarDgv(cbxPlataforma.getSelectedItem().toString(), cbxArnes.getSelectedItem().toString(), Principal.UsuarioLogeado.Cadena1, Principal.UsuarioLogeado.Cadena2, Principal.UsuarioLogeado.Cadena3, Principal.UsuarioLogeado.Cadena4, Principal.UsuarioLogeado.Cadena5,Principal.UsuarioLogeado.Cadena5,"TODOS", cbxTurno.getSelectedItem().toString());
                break;
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
        jLabel2 = new javax.swing.JLabel();
        cbxTurno = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        cbxPlataforma = new javax.swing.JComboBox();
        btnGuardar = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        cbxCadena = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        cbxArnes = new javax.swing.JComboBox();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblCodigos = new Compille.RXTable();
        btnSalir = new javax.swing.JButton();
        btnImportar = new javax.swing.JButton();
        btn_ajuste = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("CAPTURA DE GSD TODOS");
        setFocusTraversalPolicyProvider(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "PARAMETROS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Agency FB", 0, 14), new java.awt.Color(51, 51, 255))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("TURNO");

        cbxTurno.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cbxTurno.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TODOS", "A", "B", "C" }));
        cbxTurno.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxTurnoItemStateChanged(evt);
            }
        });
        cbxTurno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxTurnoActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("puntos pieza rma");

        cbxPlataforma.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cbxPlataforma.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxPlataformaItemStateChanged(evt);
            }
        });

        btnGuardar.setBackground(new java.awt.Color(204, 204, 204));
        btnGuardar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Image55.png"))); // NOI18N
        btnGuardar.setText("NUEVA");
        btnGuardar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnGuardar.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        btnGuardar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnGuardar.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnGuardar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setText("CADENA:");

        cbxCadena.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cbxCadena.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxCadenaItemStateChanged(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setText("ARNES:");

        cbxArnes.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cbxArnes.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxArnesItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 258, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxTurno, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxArnes, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxPlataforma, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(cbxCadena, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(89, 89, 89)
                        .addComponent(btnGuardar)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxPlataforma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxArnes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cbxCadena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxTurno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 99, Short.MAX_VALUE)
                .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

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
        tblCodigos.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tblCodigosPropertyChange(evt);
            }
        });
        jScrollPane2.setViewportView(tblCodigos);

        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/navigate-left48.png"))); // NOI18N
        btnSalir.setText("Regresar");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });

        btnImportar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/tests48.png"))); // NOI18N
        btnImportar.setText("CAPTURA DESDE EXCEL");
        btnImportar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnImportar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnImportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImportarActionPerformed(evt);
            }
        });

        btn_ajuste.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/ajuste.png"))); // NOI18N
        btn_ajuste.setText("Ajustar Puntos Pza.");
        btn_ajuste.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ajusteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(218, 218, 218)
                        .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnImportar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btn_ajuste))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 819, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 90, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 510, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnImportar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnSalir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_ajuste, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbxTurnoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxTurnoItemStateChanged
        // TODO add your handling code here:
           if(cbxTurno.getSelectedItem()!=null)
              DefinirParametros();
    }//GEN-LAST:event_cbxTurnoItemStateChanged

    private void cbxPlataformaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxPlataformaItemStateChanged
        // TODO add your handling code here:
        // cbxCodigo.removeAllItems();
        if(cbxPlataforma.getSelectedItem()!=null)
        {
            EnlazarCbxArnes(cbxPlataforma.getSelectedItem().toString());
            DefinirParametros();
        }
    }//GEN-LAST:event_cbxPlataformaItemStateChanged

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
        CapturaCodigos Cc=new  CapturaCodigos();
        this.setVisible(false);
        Cc.setLocationRelativeTo(null);
        Cc.setVisible(true);
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void cbxCadenaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxCadenaItemStateChanged
        // TODO add your handling code here:
        if(cbxCadena.getSelectedItem()!=null)
        {
            DefinirParametros();
        }
    }//GEN-LAST:event_cbxCadenaItemStateChanged

    private void cbxArnesItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxArnesItemStateChanged
        // TODO add your handling code here:
        if((cbxArnes.getSelectedItem()!=null) )
        {
            DefinirParametros();
        }
    }//GEN-LAST:event_cbxArnesItemStateChanged

    private void tblCodigosPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tblCodigosPropertyChange
        // TODO add your handling code here:
        if((evt.getOldValue()!=null) && (tblCodigos.getSelectedRow()>-1))
        {
            //JOptionPane.showMessageDialog(null, tblCodigos.getValueAt(tblCodigos.getSelectedRow(), 0));
            ArrayList<String> lista=new ArrayList<>();
            lista.add(tblCodigos.getValueAt(tblCodigos.getSelectedRow(), 5).toString());
            lista.add(tblCodigos.getValueAt(tblCodigos.getSelectedRow(), 6).toString());
            lista.add(tblCodigos.getValueAt(tblCodigos.getSelectedRow(), 7).toString());
            lista.add(Principal.UsuarioLogeado.codigo);
            lista.add(tblCodigos.getValueAt(tblCodigos.getSelectedRow(), 0).toString());        
            if(Principal.cn.EjecutarInsert("update gsd set  hcdircorte=?, hcdirlps=?, hcdirensfinal=?,  usuariomodif=?  where gsd.idcodigo=?", lista))
                DefinirParametros();
        }
    }//GEN-LAST:event_tblCodigosPropertyChange

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
        ArrayList<Object> listas=new ArrayList<Object>();
                Principal.cn.EjecutarInsertOb("update manufactura as a\n" +
                "INNER JOIN gsd as b on\n" +
                "    a.idcodigo = b.idcodigo\n" +
                "set \n" +
                "    a.PUNTOSPZAPOND = b.hcdirlps+b.hcdirensfinal", listas);
        Principal p=new Principal(Principal.UsuarioLogeado);
        p.setLocationRelativeTo(null);
        p.setVisible(true);
    }//GEN-LAST:event_formWindowClosed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnImportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImportarActionPerformed
        // TODO add your handling code here:
        this.hide();
        LectorPP pp=new LectorPP();
        pp.setVisible(true);
        pp.setLocationRelativeTo(null);
    }//GEN-LAST:event_btnImportarActionPerformed

    private void btn_ajusteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ajusteActionPerformed
         
        // TODO add your handling code here:
        try{
           ArrayList<String> lista=new ArrayList<>();
           if(Principal.cn.EjecutarInsert("update manufactura as a\n" +
                                            "INNER JOIN gsd as b on\n" +
                                               "    a.idcodigo = b.idcodigo\n" +
                                                 "set\n" +
                                                  "    PUNTOSPZAPOND = b.hcdirlps+b.hcdirensfinal", lista))
                DefinirParametros();
           JOptionPane.showMessageDialog(null, "Operación realizada OK");
        }catch (ExceptionInInitializerError ex) {
            
            System.out.println(ex.toString());
                     
        }
       
        
    }//GEN-LAST:event_btn_ajusteActionPerformed

    private void cbxTurnoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxTurnoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxTurnoActionPerformed

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
            java.util.logging.Logger.getLogger(CapturaExcelPP.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CapturaExcelPP.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CapturaExcelPP.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CapturaExcelPP.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CapturaExcelPP().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnImportar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JButton btn_ajuste;
    private javax.swing.JComboBox cbxArnes;
    private javax.swing.JComboBox cbxCadena;
    private javax.swing.JComboBox cbxPlataforma;
    private javax.swing.JComboBox cbxTurno;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private Compille.RXTable tblCodigos;
    // End of variables declaration//GEN-END:variables
}
