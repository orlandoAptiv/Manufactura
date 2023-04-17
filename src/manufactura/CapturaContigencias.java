/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package manufactura;

import Reportes.CeldaCheck;
import Reportes.ExcelContigencias;
import Reportes.Render_CheckBox;
import java.awt.Font;
import java.awt.Image;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author gzld6k
 */
public class CapturaContigencias extends javax.swing.JFrame {

    /**
     * Creates new form CapturaContigencias
     */
    DefaultTableModel modelo;
    public CapturaContigencias() {
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
        
         if(Principal.UsuarioLogeado.Cadena6.equals("1"))
        {
            cbxCadena.addItem("6");
        }
         
         if(Principal.UsuarioLogeado.Cadena8.equals("1"))
        {
            cbxCadena.addItem("5");
        }
         
        Image icon = new ImageIcon(getClass().getResource("/Images/competitors-icon.png")).getImage();
            setIconImage(icon);
        
    }

    public void EnlazarDgv(String Plataforma, String Arnes, String Cad1, String Cad2, String Cad3, String Cad4, String Cad7, String Cad6, String Cad5,  String Codigo, String Turno){
        try {
            
            //Object[][] datos=new Object[][]; 
            modelo =new DefaultTableModel( ){ 
    
                @Override
                public boolean isCellEditable(int row, int column) {
                     //all cells false
                        boolean rsp=true;
                  return rsp;
                }
                };
                                                            //1      2         3        4         5         6                      7       8            9           10         11        12        13         14        15      16                 17             18             19              20          21          22      23   
            modelo.setColumnIdentifiers(new Object[]{"IDCODIGO", "CADENA", "PLAT.", "CODIGO", "LINEA", "TURNO", "HCDIRECTO", "CONT.CALCULADA", "CONT. EXTRA"});   
            String query="select c.IDCODIGO, c.CADENA, c.PLATAFORMA, c.CODIGO, c.linea, c.TURNO,  (elinea+estaciones+ kits+HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)  as 'GENTEDIRECTA', \n" +
                    "if(((elinea+estaciones+ kits+HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*.105)>0.5,"
                    + "ROUND((elinea+estaciones+ kits+HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*.105), CAST((elinea+estaciones+ kits+HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*.105 as UNSIGNED )) as contigenciass,\n" +
                    " cont.hcagregada from "+
                    "(select * from codigos";
                   if((Cad1.equals("1")) || (Cad2.equals("1")) || (Cad3.equals("1")) || (Cad4.equals("1"))|| (Cad6.equals("1"))|| (Cad5.equals("1")))
                    {
                    query+=" where ";
                    if(Cad1.equals("1"))
                    {
                         query+= "CODIGOS.cadena='1' ";
                         if((Cad2.equals("1")) || (Cad3.equals("1")) || (Cad4.equals("1"))|| (Cad6.equals("1"))|| (Cad5.equals("1")))
                             query+="or ";
        
                    }
                    if(Cad2.equals("1"))
                    {
                        query+= "CODIGOS.cadena='2' ";
                          if( (Cad3.equals("1")) || (Cad4.equals("1"))|| (Cad6.equals("1"))|| (Cad5.equals("1")))
                             query+="or ";
                    }
                    if(Cad3.equals("1"))
                    {
                        query+= "CODIGOS.cadena='3' ";
                        if( (Cad4.equals("1"))|| (Cad6.equals("1"))|| (Cad5.equals("1")))
                             query+="or ";
                    }
                    
                    if(Cad4.equals("1"))
                    {
                        query+= "CODIGOS.cadena='4' ";
                         if((Cad6.equals("1"))|| (Cad5.equals("1")))
                             query+="or ";

                    }
                    
                     if(Cad6.equals("1"))
                    {
                        query+= "CODIGOS.cadena='6' ";
                        if( (Cad5.equals("1")))
                             query+="or ";
                    }
                    
                       if(Cad5.equals("1"))
                    {
                        query+= "CODIGOS.cadena='5' ";
                        
                    }
                    
                    }
            query+=") as c,\n" +
                    " MANUFACTURA AS m,  contigencias as cont where\n" +
                    "c.idcodigo=cont.idcodigo and \n" +
                    "c.IDCODIGO = m.IDCODIGO  and m.activo=1 and c.linea<>'91' and c.plataforma<>'SERVICIOS'  ";
            if(!Plataforma.equals("TODOS"))
            query+="AND c.PLATAFORMA='"+Plataforma+"'";
            if(!Arnes.equals("TODOS"))
            query+= "AND  c.ARNES='"+Arnes+"'";
            if(!Codigo.equals("TODOS"))
            query+= "AND  c.Codigo='"+Codigo+"'";
            if(!Turno.equals("TODOS"))
            query+= "AND  c.Turno='"+Turno+"'";
            ResultSet rs=Principal.cn.GetConsulta(query);
            //modelo=new DefaultTableModel();
            while(rs.next())
            {
               modelo.addRow(new Object[]{rs.getString("idcodigo"), rs.getString("CADENA"), rs.getString("PLATAFORMA"), rs.getString("CODIGO"), rs.getString("LINEA"), rs.getString("turno"), rs.getDouble("GENTEDIRECTA"), rs.getDouble("contigenciass"), rs.getDouble("hcagregada")});
            }
              tblCodigos.setModel(modelo);
              tblCodigos.getColumnModel().getColumn(0).setMaxWidth(0);
              tblCodigos.getColumnModel().getColumn(0).setMinWidth(0);
              tblCodigos.getColumnModel().getColumn(0).setPreferredWidth(0);
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
    
    public static DefaultTableModel getModeloCadena(){
        DefaultTableModel modeloT=new DefaultTableModel();
        try {
            modeloT.setColumnIdentifiers(new Object[]{"CADENA", "TURNO", "HCDIRECTO", "CONTIGENCIA"});
            ResultSet rs=Principal.cn.GetConsulta("select  c.CADENA,  c.TURNO, sum(HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)  as 'GENTEDIRECTA', \n" +
                        "if(((HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*.105)>0.5, sum(ROUND((HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*.105)), sum(ROUND(CAST((HCDIRLINEA+ hcdirconte+HCDIRSOPORTE+HCDIRTABINSP+hcrutasint+hcdirlps+HCDIRSOPLPS+ hcdirpilotos+hcdirftq+ hcdirsistemas)*.105 as UNSIGNED )))) as CONTIGENCIAS,\n" +
                        " sum(cont.hcagregada) as 'GENTEAGREGADA' from (select * from codigos  ) as c,\n" +
                        " MANUFACTURA AS m,  contigencias as cont where\n" +
                        "c.idcodigo=cont.idcodigo and \n" +
                        "c.IDCODIGO = m.IDCODIGO and m.activo=1  and c.plataforma<>'SERVICIOS' GROUP BY c.cadena, c.turno ");
            while(rs.next())
            {
                modeloT.addRow(new Object[]{rs.getString("CADENA"), rs.getString("TURNO"), rs.getString("GENTEDIRECTA"), rs.getDouble("CONTIGENCIAS")+ rs.getDouble("GENTEAGREGADA")});
             //   modelo.addRow(new Object[]{rs.getString("CADENA"), rs.getString("TURNO"), rs.getDouble("GENTEDIRECTA"), rs.getDouble("GENTEAGREGADA"), 0});
            }
        }catch(Exception e)
        {
            System.out.println(e.toString());
        }
        return modeloT;
    }
   
    public void DefinirParametros(){
          if(cbxCadena.getSelectedItem()!=null)
          {
              switch (cbxCadena.getSelectedItem().toString())
            {
                case "1":
                    EnlazarDgv(cbxPlataforma.getSelectedItem().toString(), cbxArnes.getSelectedItem().toString(), "1", "0", "0", "0", "0","0","0", "TODOS", cbxTurno.getSelectedItem().toString());
                    break;
                case "2":
                    EnlazarDgv(cbxPlataforma.getSelectedItem().toString(), cbxArnes.getSelectedItem().toString(), "0", "1", "0", "0", "0","0","0",  "TODOS", cbxTurno.getSelectedItem().toString());
                break;
                case "3":
                     EnlazarDgv(cbxPlataforma.getSelectedItem().toString(), cbxArnes.getSelectedItem().toString(), "0", "0", "1", "0", "0", "0","0", "TODOS", cbxTurno.getSelectedItem().toString());
                break;
                case "4":
                    EnlazarDgv(cbxPlataforma.getSelectedItem().toString(), cbxArnes.getSelectedItem().toString(), "0", "0", "0", "1","0", "0","0", "TODOS", cbxTurno.getSelectedItem().toString());
                break;
//                case "5":
//                    EnlazarDgv(cbxPlataforma.getSelectedItem().toString(), cbxArnes.getSelectedItem().toString(), "0", "0", "0", "0", "1","0","0", "TODOS", cbxTurno.getSelectedItem().toString());
//                break;
                    
                case "6":
                    EnlazarDgv(cbxPlataforma.getSelectedItem().toString(), cbxArnes.getSelectedItem().toString(), "0", "0", "0", "0", "0","1","0", "TODOS", cbxTurno.getSelectedItem().toString());
                break;
                        
               case "5":
                    EnlazarDgv(cbxPlataforma.getSelectedItem().toString(), cbxArnes.getSelectedItem().toString(), "0", "0", "0", "0", "0","0","1", "TODOS", cbxTurno.getSelectedItem().toString());
                break;
                        
                case "TODOS":
                    EnlazarDgv(cbxPlataforma.getSelectedItem().toString(), cbxArnes.getSelectedItem().toString(), Principal.UsuarioLogeado.Cadena1, Principal.UsuarioLogeado.Cadena2, Principal.UsuarioLogeado.Cadena3, Principal.UsuarioLogeado.Cadena4, Principal.UsuarioLogeado.Cadena5,Principal.UsuarioLogeado.Cadena6,Principal.UsuarioLogeado.Cadena8,"TODOS", cbxTurno.getSelectedItem().toString());
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
        jLabel6 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblCodigos = new Compille.RXTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cbxTurno = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        cbxPlataforma = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        cbxCadena = new javax.swing.JComboBox();
        jLabel7 = new javax.swing.JLabel();
        cbxArnes = new javax.swing.JComboBox();
        btnGuardar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        btnExportar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 204));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 255));
        jLabel6.setText("Contigencias ");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 10, 351, 70));

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
        tblCodigos.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tblCodigos.setSelectAllForEdit(true);
        tblCodigos.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tblCodigosPropertyChange(evt);
            }
        });
        jScrollPane2.setViewportView(tblCodigos);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 90, 819, 420));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "PARAMETROS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Agency FB", 0, 14), new java.awt.Color(51, 51, 255))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("TURNO:");

        cbxTurno.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cbxTurno.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TODOS", "A", "B", "C" }));
        cbxTurno.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxTurnoItemStateChanged(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("PLATAFORMA:");

        cbxPlataforma.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cbxPlataforma.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxPlataformaItemStateChanged(evt);
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
        cbxCadena.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbxCadenaActionPerformed(evt);
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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxPlataforma, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxArnes, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(cbxTurno, javax.swing.GroupLayout.Alignment.LEADING, 0, 120, Short.MAX_VALUE)
                        .addComponent(cbxCadena, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addComponent(btnGuardar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(30, Short.MAX_VALUE)
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
                .addGap(27, 27, 27)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxTurno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55)
                .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 47, 140, -1));

        btnSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/navigate-left48.png"))); // NOI18N
        btnSalir.setText("Regresar");
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
            }
        });
        jPanel1.add(btnSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 520, -1, 80));

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
        jPanel1.add(btnExportar, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 520, -1, 80));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1018, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblCodigosPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tblCodigosPropertyChange
        // TODO add your handling code here:
        if((evt.getOldValue()!=null) && (tblCodigos.getSelectedRow()>-1))
        {
            ArrayList<String> lista=new ArrayList<>();
            lista.add(tblCodigos.getValueAt(tblCodigos.getSelectedRow(), 7).toString());
            lista.add(tblCodigos.getValueAt(tblCodigos.getSelectedRow(), 8).toString());
            lista.add(tblCodigos.getValueAt(tblCodigos.getSelectedRow(), 0).toString());
            if(Principal.cn.EjecutarInsert("update contigencias set hccalculado=?, hcagregada=?  where contigencias.idcodigo=?", lista))
            DefinirParametros();
        }
    }//GEN-LAST:event_tblCodigosPropertyChange

    private void cbxTurnoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxTurnoItemStateChanged
        // TODO add your handling code here:
        if(cbxTurno.getSelectedItem()!=null)
        {
            DefinirParametros();
        }

    }//GEN-LAST:event_cbxTurnoItemStateChanged

    private void cbxPlataformaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxPlataformaItemStateChanged
        // TODO add your handling code here:
        if(cbxPlataforma.getSelectedItem()!=null)
        {
            EnlazarCbxArnes(cbxPlataforma.getSelectedItem().toString());
            DefinirParametros();
        }
    }//GEN-LAST:event_cbxPlataformaItemStateChanged

    private void cbxCadenaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxCadenaItemStateChanged
        // TODO add your handling code here:
        if((cbxCadena.getSelectedItem()!=null) )
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

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
        CapturaCodigos Cc=new  CapturaCodigos();
        this.setVisible(false);
        Cc.setLocationRelativeTo(null);
        Cc.setVisible(true);
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
        Principal p=new Principal(Principal.UsuarioLogeado);
        p.setLocationRelativeTo(null);
        p.setVisible(true);
    }//GEN-LAST:event_formWindowClosed

    private void btnExportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportarActionPerformed
        try{
                     Object[] possibilities = {"CADENA", "TURNO", "CODIGO"};
                    String s = (String) JOptionPane.showInputDialog(
                            null,
                            "¿QUE TIPO EXPORTACION?\n",
                            "CONTIGENCIA",
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            possibilities,
                          " ");
          ExcelContigencias exportar=null;
        if(s!=null)            
        {
            switch(s)
            {
                case "CADENA":
                     exportar=new ExcelContigencias(getModeloCadena(), "Reporte HC");
                    break;
                case "CODIGO":
                     exportar=new ExcelContigencias((DefaultTableModel)tblCodigos.getModel(), "Reporte HC");
                    break;
            }
        }
           
        }catch(Exception e )
        {
            System.out.println(e.toString());
        }
    }//GEN-LAST:event_btnExportarActionPerformed

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
            java.util.logging.Logger.getLogger(CapturaContigencias.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CapturaContigencias.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CapturaContigencias.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CapturaContigencias.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CapturaContigencias().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExportar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnSalir;
    private javax.swing.JComboBox cbxArnes;
    private javax.swing.JComboBox cbxCadena;
    private javax.swing.JComboBox cbxPlataforma;
    private javax.swing.JComboBox cbxTurno;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private Compille.RXTable tblCodigos;
    // End of variables declaration//GEN-END:variables
}
