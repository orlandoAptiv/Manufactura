package manufactura;

import java.awt.Font;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

public class SelectCodigo extends javax.swing.JFrame {

    /**
     * Creates new form CapturaManufactura
     */
    //ComboBoxModel<Object> modelo;
    String idcodigo = "";
    DefaultTableModel modelo;

    public SelectCodigo() {
        initComponents();
        EnlazarCbxPlataforma();
//        EnlazarCbxCodigo();
        // EnlazarCbxLinea();
        cbxCadena.addItem("TODOS");
        if (Principal.UsuarioLogeado.Cadena1.equals("1")) {
            cbxCadena.addItem("1");
        }
        if (Principal.UsuarioLogeado.Cadena2.equals("1")) {
            cbxCadena.addItem("2");
        }
        if (Principal.UsuarioLogeado.Cadena3.equals("1")) {
            cbxCadena.addItem("3");
        }
        if (Principal.UsuarioLogeado.Cadena4.equals("1")) {
            cbxCadena.addItem("4");
        }
        if (Principal.UsuarioLogeado.Cadena5.equals("1")) {
            cbxCadena.addItem("5");
        }

        if (Principal.UsuarioLogeado.Cadena5.equals("1")) {
            cbxCadena.addItem("6");
        }
        if (Principal.UsuarioLogeado.turno.equals("TODOS")) {
            cbxTurno.addItem("TODOS");
            cbxTurno.addItem("A");
            cbxTurno.addItem("B");
        }
        if (Principal.UsuarioLogeado.turno.equals("A")) {
            cbxTurno.addItem("A");
        }
        if (Principal.UsuarioLogeado.turno.equals("B")) {
            cbxTurno.addItem("B");
        }
        // EnlazarDgv("TODOS", "TODOS", Principal.UsuarioLogeado.Cadena1, Principal.UsuarioLogeado.Cadena2, Principal.UsuarioLogeado.Cadena3, Principal.UsuarioLogeado.Cadena4, "TODOS", "TODOS");
    }

    public void DefinirParametros() {
        if ((cbxCadena.getSelectedItem() != null) && (cbxTurno.getSelectedItem() != null)) {
            switch (cbxCadena.getSelectedItem().toString()) {
                case "1":
                    EnlazarDgv(cbxPlataforma.getSelectedItem().toString(), cbxArnes.getSelectedItem().toString(), "1", "0", "0", "0", "0", "0", "TODOS", cbxTurno.getSelectedItem().toString());
                    break;
                case "2":
                    EnlazarDgv(cbxPlataforma.getSelectedItem().toString(), cbxArnes.getSelectedItem().toString(), "0", "1", "0", "0", "0", "0", "TODOS", cbxTurno.getSelectedItem().toString());
                    break;
                case "3":
                    EnlazarDgv(cbxPlataforma.getSelectedItem().toString(), cbxArnes.getSelectedItem().toString(), "0", "0", "1", "0", "0", "0", "TODOS", cbxTurno.getSelectedItem().toString());
                    break;
                case "4":
                    EnlazarDgv(cbxPlataforma.getSelectedItem().toString(), cbxArnes.getSelectedItem().toString(), "0", "0", "0", "1", "0", "0", "TODOS", cbxTurno.getSelectedItem().toString());
                    break;
                case "5":
                    EnlazarDgv(cbxPlataforma.getSelectedItem().toString(), cbxArnes.getSelectedItem().toString(), "0", "0", "0", "0", "1", "0", "TODOS", cbxTurno.getSelectedItem().toString());
                    break;
                case "6":
                    EnlazarDgv(cbxPlataforma.getSelectedItem().toString(), cbxArnes.getSelectedItem().toString(), "0", "0", "0", "0", "0", "1", "TODOS", cbxTurno.getSelectedItem().toString());
                    break;
                case "TODOS":
                    EnlazarDgv(cbxPlataforma.getSelectedItem().toString(), cbxArnes.getSelectedItem().toString(), Principal.UsuarioLogeado.Cadena1, Principal.UsuarioLogeado.Cadena2, Principal.UsuarioLogeado.Cadena3, Principal.UsuarioLogeado.Cadena4, Principal.UsuarioLogeado.Cadena5, Principal.UsuarioLogeado.Cadena6, "TODOS", cbxTurno.getSelectedItem().toString());
                    break;
            }
        }
    }

    public void EnlazarDgv(String Plataforma, String Arnes, String Cad1, String Cad2, String Cad3, String Cad4, String Cad5, String Cad6, String Codigo, String Turno) {
        try {
            modelo = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int row, int column) {
                    //all cells false
                    boolean rsp = true;
                    if ((column == 17) || (column == 18)) {
                        rsp = false;

                    }
                    return rsp;
                }
            };
            modelo.setColumnIdentifiers(new Object[]{"IDENT", "PLAT.", "ARNES", "CODIGO", "LINEA", "TURNO", "HRSEMB", "HRSPAGADAS", "EFIC.MANUF."});
            //modelo.setColumnIdentifiers(new Object[]{"IDCODIGO", "PLAT.", "ARNES", "LINEA", "TURNO", "LINEA", "LPS", "SOPORTE", "TAB.INSP", "CORTE", "FTQ", "PILOTOS", "SISTEMAS", "RUTAS", "PTOS.PIEZA", "CAP.UTIL.HTA", "SAL.EN.PZA", "HRS.EMB", "HRS.PAG"});   
            String query = "SELECT\n"
                    + "codigos.IDCODIGO,\n"
                    + "codigos.CADENA,\n"
                    + "PLATAFORMA, ARNES, codigo, LINEA, TURNO, \n"
                    + "ROUND((manufactura.SALIDAENPIEZA* manufactura.PUNTOSPZAPOND)/100) as HRSEMB, "
                    + "if(codigos.TURNO='A', ROUND((manufactura.hcdirlps+HCDIRLINEA)*9), ROUND((manufactura.hcdirlps*HCDIRLINEA)*7.9))  as hrsPag, \n"
                    + "if(codigos.TURNO='A', TRUNCATE( ((manufactura.SALIDAENPIEZA* manufactura.PUNTOSPZAPOND)/((manufactura.hcdirlps+manufactura.HCDIRLINEA)*9)),2), TRUNCATE( ((manufactura.SALIDAENPIEZA* manufactura.PUNTOSPZAPOND)/((manufactura.hcdirlps+manufactura.HCDIRLINEA)*7.9)),2))  as EFIC\n"
                    + "FROM\n"
                    + "(select * from codigos";
            if ((Cad1.equals("1")) || (Cad2.equals("1")) || (Cad3.equals("1")) || (Cad4.equals("1")) || (Cad6.equals("1"))) {
                query += " where ";
                if (Cad1.equals("1")) {
                    query += "CODIGOS.cadena='1' ";
                    if ((Cad2.equals("1")) || (Cad3.equals("1")) || (Cad4.equals("1"))) {
                        query += "or ";
                    }

                }
                if (Cad2.equals("1")) {
                    query += "CODIGOS.cadena='2' ";
                    if ((Cad3.equals("1")) || (Cad4.equals("1"))) {
                        query += "or ";
                    }
                }
                if (Cad3.equals("1")) {
                    query += "CODIGOS.cadena='3' ";
                    if ((Cad4.equals("1"))) {
                        query += "or ";
                    }
                }

                if (Cad6.equals("1")) {
                    query += "CODIGOS.cadena='6' ";
                    if ((Cad5.equals("1"))) {
                        query += "or ";
                    }
                }

                if (Cad4.equals("1")) {
                    query += "CODIGOS.cadena='4' ";

                }

            }
            query += ") as codigos INNER JOIN\n"
                    + "manufactura\n"
                    + "ON \n"
                    + "codigos.IDCODIGO = manufactura.IDCODIGO ";
            if (!Plataforma.equals("TODOS")) {
                query += "AND CODIGOS.PLATAFORMA='" + Plataforma + "'";
            }
            if (!Arnes.equals("TODOS")) {
                query += "AND  CODIGOS.ARNES='" + Arnes + "'";
            }
            if (!Codigo.equals("TODOS")) {
                query += "AND  CODIGOS.Codigo='" + Codigo + "'";
            }
            if (!Turno.equals("TODOS")) {
                query += "AND  CODIGOS.Turno='" + Turno + "'";
            }
            ResultSet rs = Principal.cn.GetConsulta(query);
            //modelo=new DefaultTableModel();
            while (rs.next()) {
                modelo.addRow(new Object[]{rs.getString("idcodigo"), rs.getString("PLATAFORMA"), rs.getString("ARNES"), rs.getString("codigo"), rs.getString("LINEA"), rs.getString("turno"), rs.getString("HRSEMB"), rs.getString("HRSPAG"), rs.getString("EFIC")});
            }
            tblCodigos.setModel(modelo);
            tblCodigos.getColumnModel().getColumn(0).setMaxWidth(0);
            tblCodigos.getColumnModel().getColumn(0).setMinWidth(0);
            tblCodigos.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblCodigos.getColumnModel().getColumn(3).setMaxWidth(100);
            tblCodigos.getColumnModel().getColumn(3).setMinWidth(100);
            tblCodigos.getColumnModel().getColumn(3).setPreferredWidth(100);
            tblCodigos.getColumnModel().getColumn(4).setMaxWidth(50);
            tblCodigos.getColumnModel().getColumn(4).setMinWidth(50);
            tblCodigos.getColumnModel().getColumn(4).setPreferredWidth(50);
            tblCodigos.getColumnModel().getColumn(5).setMaxWidth(50);
            tblCodigos.getColumnModel().getColumn(5).setMinWidth(50);
            tblCodigos.getColumnModel().getColumn(5).setPreferredWidth(50);
            Font font = tblCodigos.getFont();
            font.deriveFont(48);
            tblCodigos.setFont(font);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void EnlazarCbxPlataforma() {
        cbxPlataforma.removeAllItems();
        cbxPlataforma.addItem("TODOS");
        try {
            ResultSet rs = Principal.cn.GetConsulta("select DISTINCT(codigos.PLATAFORMA) as plataforma from codigos");
            while (rs.next()) {
                cbxPlataforma.addItem(rs.getString("plataforma"));
                //modelo.addListDataListener(rs);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }

//    public void EnlazarCbxCodigo(){
//     cbxCodigo.removeAllItems();
//     cbxCodigo.addItem("TODOS");
//        try {
//            ResultSet rs= Principal.cn.GetConsulta("select DISTINCT(codigos.CODIGO) as codigo from codigos");
//            while(rs.next())
//            {
//                cbxCodigo.addItem(rs.getString("codigo"));
//                //modelo.addListDataListener(rs);
//            }
//        } catch (SQLException ex) {
//            System.out.println(ex.toString());
//        }
//    }
    public void EnlazarCbxArnes(String Plataforma) {
        cbxArnes.removeAllItems();
        cbxArnes.addItem("TODOS");
        try {
            ResultSet rs = Principal.cn.GetConsulta("select DISTINCT(codigos.arnes) as ARNES from codigos where codigos.PLATAFORMA='" + Plataforma + "'");
            while (rs.next()) {
                cbxArnes.addItem(rs.getString("ARNES"));
                //modelo.addListDataListener(rs);
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
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

        pnlMenu = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
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
        btnBuscar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCodigos = new javax.swing.JTable();
        btnEntrar = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("CAPTURA DE MANUFACTURA");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        pnlMenu.setBackground(new java.awt.Color(255, 255, 153));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 255));
        jLabel6.setText("SELECCIONE...");

        javax.swing.GroupLayout pnlMenuLayout = new javax.swing.GroupLayout(pnlMenu);
        pnlMenu.setLayout(pnlMenuLayout);
        pnlMenuLayout.setHorizontalGroup(
            pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMenuLayout.createSequentialGroup()
                .addContainerGap(384, Short.MAX_VALUE)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 547, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(81, 81, 81))
        );
        pnlMenuLayout.setVerticalGroup(
            pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMenuLayout.createSequentialGroup()
                .addGap(0, 11, Short.MAX_VALUE)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "PARAMETROS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Agency FB", 0, 14), new java.awt.Color(51, 51, 255))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("TURNO:");

        cbxTurno.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
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

        btnBuscar.setBackground(new java.awt.Color(204, 204, 204));
        btnBuscar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Image61.jpg"))); // NOI18N
        btnBuscar.setText("BUSCAR");
        btnBuscar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnBuscar.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        btnBuscar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnBuscar.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnBuscar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
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
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxArnes, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(btnBuscar)
                        .addGap(18, 18, 18)
                        .addComponent(btnGuardar))
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
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbxTurno, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(22, Short.MAX_VALUE))
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
                .addGap(29, 29, 29)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxTurno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        tblCodigos.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tblCodigos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "CADENA", "PLATAFORMA", "CODIGO", "LINEA", "TURNO"
            }
        ));
        tblCodigos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCodigosMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblCodigosMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tblCodigos);
        tblCodigos.getColumnModel().getColumn(0).setMinWidth(0);
        tblCodigos.getColumnModel().getColumn(0).setPreferredWidth(0);
        tblCodigos.getColumnModel().getColumn(0).setMaxWidth(0);

        btnEntrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/success-icon.png"))); // NOI18N
        btnEntrar.setText("SELECCIONAR");
        btnEntrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEntrarActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/navigate-left48.png"))); // NOI18N
        jButton2.setText("REGRESAR");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlMenu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addComponent(btnEntrar)
                        .addGap(27, 27, 27)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 711, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 443, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnEntrar, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
        CapturaCodigos Cc = new CapturaCodigos();
        this.setVisible(false);
        Cc.setLocationRelativeTo(null);
        Cc.setVisible(true);
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void cbxCadenaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxCadenaItemStateChanged
        // TODO add your handling code here:
        if (cbxCadena.getSelectedItem() != null) {
            DefinirParametros();

        }
    }//GEN-LAST:event_cbxCadenaItemStateChanged

    private void cbxTurnoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxTurnoItemStateChanged
        // TODO add your handling code here:
        if (cbxTurno.getSelectedItem() != null)
            DefinirParametros();
    }//GEN-LAST:event_cbxTurnoItemStateChanged

    private void btnEntrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEntrarActionPerformed
        // TODO add your handling code here:
        if (!idcodigo.equals("")) {
            this.setVisible(false);
            SeleccioneMODELO SM = new SeleccioneMODELO(idcodigo);
            //this.dispose();
            SM.setLocationRelativeTo(null);
            SM.setVisible(true);
        }
    }//GEN-LAST:event_btnEntrarActionPerformed

    private void tblCodigosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCodigosMouseClicked
        // TODO add your handling code here:

    }//GEN-LAST:event_tblCodigosMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
        Principal p = new Principal(Principal.UsuarioLogeado);
        p.setLocationRelativeTo(null);
        p.setVisible(true);
    }//GEN-LAST:event_formWindowClosed

    private void cbxArnesItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxArnesItemStateChanged
        // TODO add your handling code here:
        if ((cbxArnes.getSelectedItem() != null)) {
            DefinirParametros();
//            EnlazarTabla(cbxPlataforma.getSelectedItem().toString(), cbxArnes.getSelectedItem().toString(), cbxCadena.getSelectedItem().toString(), cbxCodigo.getSelectedItem().toString(),  cbxTurno.getSelectedItem().toString());
        }
    }//GEN-LAST:event_cbxArnesItemStateChanged

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void tblCodigosMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCodigosMousePressed
        // TODO add your handling code here:

        int d = evt.getClickCount();
        if (d != 1) {
            int fila = tblCodigos.rowAtPoint(evt.getPoint());
            int columna = tblCodigos.columnAtPoint(evt.getPoint());
            if ((fila > -1) && (columna > -1)) {
                idcodigo = modelo.getValueAt(fila, 0).toString();
                btnEntrarActionPerformed(null);

            }
        }
    }//GEN-LAST:event_tblCodigosMousePressed

    private void cbxPlataformaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxPlataformaItemStateChanged
        // TODO add your handling code here:
        // cbxCodigo.removeAllItems();
        if (cbxPlataforma.getSelectedItem() != null) {
            EnlazarCbxArnes(cbxPlataforma.getSelectedItem().toString());
            DefinirParametros();

        }
    }//GEN-LAST:event_cbxPlataformaItemStateChanged

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
            java.util.logging.Logger.getLogger(SelectCodigo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SelectCodigo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SelectCodigo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SelectCodigo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SelectCodigo().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnEntrar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JComboBox cbxArnes;
    private javax.swing.JComboBox cbxCadena;
    private javax.swing.JComboBox cbxPlataforma;
    private javax.swing.JComboBox cbxTurno;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel pnlMenu;
    private javax.swing.JTable tblCodigos;
    // End of variables declaration//GEN-END:variables
}
