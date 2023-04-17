
package CapturasEffort;

import Clases.Depto;
import Clases.Modulos;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import manufactura.Principal;

public class PorcentajesHC extends javax.swing.JFrame {

    /**
     * Creates new form PorcentajesHC
     */
    public Double hcRecHumanos62 = 0.0;
    public Double hcRecHumanos63 = 0.0;
    public Double hcRecHumanos28 = 0.0;
    public Double hcManuf62 = 0.0, kachirules = 0.0;
    public Double hcManuf63 = 0.0;
    public Double hcManuf28 = 0.0;

    public PorcentajesHC() {
        try {
            initComponents();
            setLocationRelativeTo(null);
//            Principal.cn = new Conection();
            IniValoresRecursos();
            tblHC62.setModel(cargarHCDepto("25962", getKachirules()));
            tblHC63.setModel(cargarHCDepto("25963", 0.0));
            hcManuf62 = SacarTotalHC("25962") + getKachirules();
            hcManuf63 = SacarTotalHC("25963");
            lblHCTotal62.setText("HC TOTAL: " + hcManuf62);
            lblHCTotal63.setText("HC TOTAL: " + hcManuf63);
            creartablaCoincidencias();

            if (hcRecHumanos28 > 0) {

                HCInduccion();
            }
            inicializartotales();
            inicializarPorcentaje();
        } catch (Exception ex) {
            Logger.getLogger(PorcentajesHC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public double getKachirules() {
        Double rsp = 0.0;
        try {
            ResultSet rs2 = Principal.cn.GetConsulta("select c.IDCODIGO, sum(m.HCDIRLINEA) as KACHIRULES from codigos as c, manufactura as m where c.IDCODIGO=m.IDCODIGO and c.CADENA=7");

            if (rs2.next()) {
                rsp = rs2.getDouble("KACHIRULES");
            }
        } catch (Exception e) {

        }
        return rsp;
    }

    public String DoubleFormat(double parDouble) {
        DecimalFormat formatter = new DecimalFormat("###,##0.000");
        String myNumero = formatter.format(parDouble);
        return myNumero;
    }

    public DefaultTableModel cargarHCDepto(String Depto, Double Kachirules) {

        DefaultTableModel modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; //To change body of generated methods, choose Tools | Templates.
            }
        };
        try {
            modelo.setColumnIdentifiers(new Object[]{"PLATAFORMA", "HC"});
            ResultSet rs = Principal.cn.GetConsulta("select c.PLATAFORMA,  sum(c.hcdirecto)  as  total from manufactura as m, personas as c where  m.IDCODIGO=c.IDCODIGO AND M.ACTIVO=1 and  m.bcable<>0 AND DEPTO='" + Depto + "' GROUP BY m.bcable");
            while (rs.next()) {
                modelo.addRow(new Object[]{"BCABLE", rs.getDouble(2)});
            }
            rs = Principal.cn.GetConsulta("select c.PLATAFORMA,  sum(c.hcdirecto)  as  total from manufactura as m, personas  as c where  m.IDCODIGO=c.IDCODIGO AND M.ACTIVO=0 AND PLATAFORMA='SERVICIOS' and  DEPTO='" + Depto + "' GROUP BY m.bcable");
            while (rs.next()) {
                modelo.addRow(new Object[]{"SERVICIOS", rs.getDouble(2)});
            }
            rs = Principal.cn.GetConsulta("select c.PLATAFORMA,  sum(c.hcdirecto)  as  total  from manufactura as m, personas as c where  m.IDCODIGO=c.IDCODIGO AND M.ACTIVO=1 and  m.bcable<>1  AND DEPTO='" + Depto + "' GROUP BY c.PLATAFORMA");
            while (rs.next()) {
                String plat = rs.getString("PLATAFORMA");
                if (plat.trim().equals("K2XX")) {
                    modelo.addRow(new Object[]{rs.getString(1), Double.parseDouble(rs.getString(2).toString()) + Kachirules});
                } else {
                    modelo.addRow(new Object[]{rs.getString(1), rs.getString(2)});
                }
            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return modelo;
    }

    public double SacarTotalHC(String Depto) {
        double rsp = 0;
        try {
            ResultSet rs = Principal.cn.GetConsulta("select c.PLATAFORMA,  sum(c.hcdirecto)  as  total from manufactura as m, personas as c where  m.IDCODIGO=c.IDCODIGO AND M.ACTIVO=1   AND DEPTO='" + Depto + "'");
            if (rs.next()) {
                rsp = rs.getDouble(2);
            }
            rs = Principal.cn.GetConsulta("select c.PLATAFORMA,    sum(c.hcdirecto) as  total from manufactura as m, personas as c where  m.IDCODIGO=c.IDCODIGO AND M.ACTIVO=0 AND PLATAFORMA='SERVICIOS'  and  DEPTO='" + Depto + "' GROUP BY m.bcable");
            if (rs.next()) {
                rsp += rs.getDouble(2);
            }
        } catch (Exception e) {

        }
        return rsp;
    }

    public void IniValoresRecursos() {
        try {
            ResultSet rs = Principal.cn.GetConsulta("SELECT * FROM RECURSOSHC WHERE MONTHNAME(now()) =MONTHNAME(fecha)");
            if (rs.next()) {
                hcRecHumanos62 = rs.getDouble(1);
                hcRecHumanos63 = rs.getDouble(2);
                hcRecHumanos28 = rs.getDouble(3);
                lblHCRec62.setText(lblHCRec62.getText() + " " + hcRecHumanos62.toString());
                lblHCRec63.setText(lblHCRec63.getText() + " " + hcRecHumanos63.toString());
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void creartablaCoincidencias() {
        try {
            boolean flag = false;
            DefaultTableModel modelo = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;//To change body of generated methods, choose Tools | Templates.
                }
            };
            modelo.addColumn("DEPTO");
            modelo.addColumn("HC RH");
            for (int i = 0; i < tblHC62.getModel().getRowCount(); i++) {
                modelo.addColumn(tblHC62.getModel().getValueAt(i, 0));
            }
            //COLUMNAS DEL  DEPTO 63
            for (int i = 1; i < tblHC63.getModel().getRowCount(); i++) {
                String COLUMNA63 = tblHC63.getModel().getValueAt(i, 0).toString();
                for (int c = 0; c < modelo.getColumnCount(); c++) {
                    String COLUMNA = modelo.getColumnName(c);
                    if (COLUMNA.trim().equals(COLUMNA63.trim())) {
                        flag = true;
                        break;
                    }

                }
                if (!flag) {
                    modelo.addColumn(tblHC63.getModel().getValueAt(i, 0));
                }
                flag = false;
            }

            modelo.addRow(new Object[]{"25962", hcRecHumanos62, 0, 0, 0, 0, 0, 0, 0, 0, 0});
            modelo.addRow(new Object[]{"25963", hcRecHumanos63, 0, 0, 0, 0, 0, 0, 0, 0, 0});
            for (int i = 0; i < tblHC62.getModel().getRowCount(); i++) {
                Double GNTE = Double.parseDouble(tblHC62.getModel().getValueAt(i, 1).toString());
                GNTE = (hcRecHumanos62 * GNTE) / hcManuf62;
//                if ((GNTE % 1) > 0.5) {
//                    GNTE = Double.parseDouble(String.valueOf(Math.round(GNTE)));
//                } else {
//                    GNTE = (GNTE) - (GNTE % 1);
//                }
                modelo = regresaModeloEditado(modelo, tblHC62.getModel().getValueAt(i, 0).toString(), GNTE, 0);
            }
            for (int i = 0; i < tblHC63.getModel().getRowCount(); i++) {
                Double GENTECORTE = Double.parseDouble(tblHC63.getModel().getValueAt(0, 1).toString());

                String GENTEPROMEDIO = tblHC63.getModel().getValueAt(i, 1).toString();
                Double GNTE = Double.parseDouble(GENTEPROMEDIO);
                GNTE += (GNTE / (hcManuf63 - GENTECORTE)) * GENTECORTE;
                Double GNTEF = (hcRecHumanos63 * GNTE) / hcManuf63;
//                if ((GNTEF % 1) > 0.5) {
//                    GNTEF = Double.parseDouble(String.valueOf(Math.round(GNTEF)));
//                } else {
//                    GNTEF = (GNTEF) - (GNTEF % 1);
//                }
                modelo = regresaModeloEditado(modelo, tblHC63.getModel().getValueAt(i, 0).toString(), GNTEF, 1);
            }
            tblHCdistribucion.setModel(modelo);
        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }

    public DefaultTableModel regresaModeloEditado(DefaultTableModel modelo, String Plat, Double valor, int renglon) {

        for (int i = 0; i < modelo.getColumnCount(); i++) {
            if (modelo.getColumnName(i).trim().equals(Plat.trim())) {
                modelo.setValueAt(valor, renglon, i);
            }
        }
        return modelo;
    }

    public void HCInduccion() {
        try {
            Double ValorHCPla = 0.0;
            Double ValorTotal = hcRecHumanos62 + hcRecHumanos63;
            DefaultTableModel modelo = (DefaultTableModel) tblHCdistribucion.getModel();
            modelo.addRow(new Object[]{"25928", hcRecHumanos28, 0, 0, 0, 0, 0, 0, 0, 0, 0});
            for (int c = 2; c < modelo.getColumnCount(); c++) {
                for (int r = 0; r < modelo.getRowCount(); r++) {
                    ValorHCPla += Double.parseDouble(modelo.getValueAt(r, c).toString());
                }
                ValorHCPla = (ValorHCPla / ValorTotal) * hcRecHumanos28;
                modelo = regresaModeloEditado(modelo, modelo.getColumnName(c), ValorHCPla, 2);
                ValorHCPla = 0.0;
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void inicializartotales() {
        if (hcRecHumanos28 > 0) {
            try {
                DefaultTableModel modelo = new DefaultTableModel() {
                };
                for (int i = 0; i < tblHCdistribucion.getModel().getColumnCount(); i++) {
                    modelo.addColumn(tblHCdistribucion.getModel().getColumnName(i));
                }
                modelo.addRow(new Object[]{"TOTALES", 0, 0, 0, 0, 0, 0, 0, 0, 0});
                tblHCTotales.setModel(modelo);
                for (int c = 2; c < tblHCdistribucion.getColumnCount(); c++) {
                    double suma = Double.parseDouble(tblHCdistribucion.getModel().getValueAt(0, c).toString());
                    suma += Double.parseDouble(tblHCdistribucion.getModel().getValueAt(1, c).toString());
                    suma += Double.parseDouble(tblHCdistribucion.getModel().getValueAt(2, c).toString());
                    tblHCTotales.getModel().setValueAt(suma, 0, c);
                }//           tblHCTotales.getColumnModel().getColumn(0).setPreferredWidth(0);
            } catch (Exception e) {

            }
        } else {

            try {
                DefaultTableModel modelo = new DefaultTableModel() {
                };
                for (int i = 0; i < tblHCdistribucion.getModel().getColumnCount(); i++) {
                    modelo.addColumn(tblHCdistribucion.getModel().getColumnName(i));
                }
                modelo.addRow(new Object[]{"TOTALES", 0, 0, 0, 0, 0, 0, 0, 0, 0});
                tblHCTotales.setModel(modelo);
                for (int c = 2; c < tblHCdistribucion.getColumnCount(); c++) {
                    double suma = Double.parseDouble(tblHCdistribucion.getModel().getValueAt(0, c).toString());
                    suma += Double.parseDouble(tblHCdistribucion.getModel().getValueAt(1, c).toString());

                    tblHCTotales.getModel().setValueAt(suma, 0, c);
                }//           tblHCTotales.getColumnModel().getColumn(0).setPreferredWidth(0);
            } catch (Exception e) {

            }

        }
    }

    public void inicializarPorcentaje() {
        try {
            DefaultTableModel modelo = new DefaultTableModel() {
            };
            modelo.addColumn("DEPTO");
            for (int i = 2; i < tblHCTotales.getModel().getColumnCount(); i++) {
                modelo.addColumn(tblHCTotales.getModel().getColumnName(i));
            }
            modelo.addRow(new Object[]{"25962", 0, 0, 0, 0, 0, 0, 0, 0, 0});
            modelo.addRow(new Object[]{"25963", 0, 0, 0, 0, 0, 0, 0, 0, 0});
            modelo.addRow(new Object[]{"25928", 0, 0, 0, 0, 0, 0, 0, 0, 0});
            modelo.addRow(new Object[]{"TOTAL PLAT.", 0, 0, 0, 0, 0, 0, 0, 0, 0});
            for (int r = 0; r < tblHCdistribucion.getModel().getRowCount(); r++) {
                for (int c = 2; c < tblHCdistribucion.getModel().getColumnCount(); c++) {
                    Double hcPlataforma = Double.parseDouble(tblHCdistribucion.getModel().getValueAt(r, c).toString());
                    Double hcTotalDepto = Double.parseDouble(tblHCdistribucion.getModel().getValueAt(r, 1).toString());
                    Double Porcentaje = (hcPlataforma / hcTotalDepto) * 100;
                    Porcentaje = Double.parseDouble(DoubleFormat(Porcentaje));
                    modelo.setValueAt(Porcentaje, r, c - 1);
                }
            }
            Double SacasumaGente = 0.0;
            Double CantidadGente = hcRecHumanos28 + hcRecHumanos62 + hcRecHumanos63;
            for (int i = 2; i < tblHCdistribucion.getModel().getColumnCount(); i++) {
                for (int ren = 0; ren < tblHCdistribucion.getModel().getRowCount(); ren++) {
                    SacasumaGente += Double.parseDouble(tblHCdistribucion.getModel().getValueAt(ren, i).toString());
                }
                Double porcentaje = (SacasumaGente / CantidadGente) * 100;
                SacasumaGente = 0.0;
                modelo.setValueAt(DoubleFormat(porcentaje), 3, i - 1);
            }
            tblHCPorcentajes.setModel(modelo);
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

        jScrollPane1 = new javax.swing.JScrollPane();
        tblHC62 = new Compille.RXTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblHCTotales = new Compille.RXTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lblHCTotal63 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        lblHCTotal62 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        lblHCRec63 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblHC63 = new Compille.RXTable();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblHCdistribucion = new Compille.RXTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblHCPorcentajes = new Compille.RXTable();
        jLabel5 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        lblHCRec62 = new javax.swing.JLabel();
        btnExportar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblHC62.setModel(new javax.swing.table.DefaultTableModel(
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
        tblHC62.setSelectAllForEdit(true);
        tblHC62.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHC62MouseClicked(evt);
            }
        });
        tblHC62.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tblHC62PropertyChange(evt);
            }
        });
        tblHC62.addVetoableChangeListener(new java.beans.VetoableChangeListener() {
            public void vetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {
                tblHC62VetoableChange(evt);
            }
        });
        jScrollPane1.setViewportView(tblHC62);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 56, 210, 228));

        tblHCTotales.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblHCTotales.setSelectAllForEdit(true);
        tblHCTotales.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHCTotalesMouseClicked(evt);
            }
        });
        tblHCTotales.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tblHCTotalesPropertyChange(evt);
            }
        });
        tblHCTotales.addVetoableChangeListener(new java.beans.VetoableChangeListener() {
            public void vetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {
                tblHCTotalesVetoableChange(evt);
            }
        });
        jScrollPane2.setViewportView(tblHCTotales);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(471, 166, 913, 50));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setText("HC DEPTO 25962");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel3.setText("% PLATAFORMA");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(471, 222, 280, -1));

        jPanel1.setBackground(new java.awt.Color(0, 0, 204));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "MANUFACTURA", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, new java.awt.Color(255, 255, 255)));
        jPanel1.setMaximumSize(new java.awt.Dimension(210, 50));
        jPanel1.setMinimumSize(new java.awt.Dimension(210, 50));

        lblHCTotal63.setBackground(new java.awt.Color(0, 0, 255));
        lblHCTotal63.setFont(new java.awt.Font("Tahoma", 0, 22)); // NOI18N
        lblHCTotal63.setForeground(new java.awt.Color(255, 255, 255));
        lblHCTotal63.setText("HC TOTAL:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblHCTotal63, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblHCTotal63)
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(249, 299, -1, -1));

        jPanel2.setBackground(new java.awt.Color(0, 0, 204));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "MANUFACTURA", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, new java.awt.Color(255, 255, 255)));
        jPanel2.setMaximumSize(new java.awt.Dimension(210, 50));
        jPanel2.setMinimumSize(new java.awt.Dimension(210, 50));
        jPanel2.setPreferredSize(new java.awt.Dimension(210, 50));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblHCTotal62.setBackground(new java.awt.Color(0, 0, 255));
        lblHCTotal62.setFont(new java.awt.Font("Tahoma", 0, 22)); // NOI18N
        lblHCTotal62.setForeground(new java.awt.Color(255, 255, 255));
        lblHCTotal62.setText("HC TOTAL:");
        jPanel2.add(lblHCTotal62, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 26, 198, 17));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 290, -1, -1));

        jPanel3.setBackground(new java.awt.Color(255, 255, 51));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("RECURSOS HUMANOS"));
        jPanel3.setMaximumSize(new java.awt.Dimension(210, 50));
        jPanel3.setMinimumSize(new java.awt.Dimension(210, 50));
        jPanel3.setPreferredSize(new java.awt.Dimension(210, 50));

        lblHCRec63.setBackground(new java.awt.Color(0, 0, 255));
        lblHCRec63.setFont(new java.awt.Font("Tahoma", 0, 22)); // NOI18N
        lblHCRec63.setText("HC TOTAL:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblHCRec63, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblHCRec63, javax.swing.GroupLayout.DEFAULT_SIZE, 51, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(249, 373, 206, 74));

        tblHC63.setModel(new javax.swing.table.DefaultTableModel(
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
        tblHC63.setSelectAllForEdit(true);
        tblHC63.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHC63MouseClicked(evt);
            }
        });
        tblHC63.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tblHC63PropertyChange(evt);
            }
        });
        tblHC63.addVetoableChangeListener(new java.beans.VetoableChangeListener() {
            public void vetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {
                tblHC63VetoableChange(evt);
            }
        });
        jScrollPane3.setViewportView(tblHC63);

        getContentPane().add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(249, 60, 206, 228));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel4.setText("HC DEPTO 25963");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(249, 30, 216, -1));

        tblHCdistribucion.setModel(new javax.swing.table.DefaultTableModel(
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
        tblHCdistribucion.setSelectAllForEdit(true);
        tblHCdistribucion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHCdistribucionMouseClicked(evt);
            }
        });
        tblHCdistribucion.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tblHCdistribucionPropertyChange(evt);
            }
        });
        tblHCdistribucion.addVetoableChangeListener(new java.beans.VetoableChangeListener() {
            public void vetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {
                tblHCdistribucionVetoableChange(evt);
            }
        });
        jScrollPane4.setViewportView(tblHCdistribucion);

        getContentPane().add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(471, 60, 913, 100));

        tblHCPorcentajes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblHCPorcentajes.setSelectAllForEdit(true);
        tblHCPorcentajes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHCPorcentajesMouseClicked(evt);
            }
        });
        tblHCPorcentajes.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tblHCPorcentajesPropertyChange(evt);
            }
        });
        tblHCPorcentajes.addVetoableChangeListener(new java.beans.VetoableChangeListener() {
            public void vetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {
                tblHCPorcentajesVetoableChange(evt);
            }
        });
        jScrollPane5.setViewportView(tblHCPorcentajes);

        getContentPane().add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 260, 910, 110));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel5.setText("GENTE PLATAFORMA:");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(471, 25, 280, -1));

        jPanel4.setBackground(new java.awt.Color(255, 255, 51));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("RECURSOS HUMANOS"));
        jPanel4.setMaximumSize(new java.awt.Dimension(210, 50));
        jPanel4.setMinimumSize(new java.awt.Dimension(210, 50));

        lblHCRec62.setBackground(new java.awt.Color(0, 0, 255));
        lblHCRec62.setFont(new java.awt.Font("Tahoma", 0, 22)); // NOI18N
        lblHCRec62.setText("HC TOTAL:");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblHCRec62, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblHCRec62, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 384, 206, -1));

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
        getContentPane().add(btnExportar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1084, 383, 105, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblHC62MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHC62MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblHC62MouseClicked

    private void tblHC62PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tblHC62PropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_tblHC62PropertyChange

    private void tblHC62VetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {//GEN-FIRST:event_tblHC62VetoableChange
        // TODO add your handling code here:
    }//GEN-LAST:event_tblHC62VetoableChange

    private void tblHCTotalesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHCTotalesMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblHCTotalesMouseClicked

    private void tblHCTotalesPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tblHCTotalesPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_tblHCTotalesPropertyChange

    private void tblHCTotalesVetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {//GEN-FIRST:event_tblHCTotalesVetoableChange
        // TODO add your handling code here:
    }//GEN-LAST:event_tblHCTotalesVetoableChange

    private void tblHC63MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHC63MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblHC63MouseClicked

    private void tblHC63PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tblHC63PropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_tblHC63PropertyChange

    private void tblHC63VetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {//GEN-FIRST:event_tblHC63VetoableChange
        // TODO add your handling code here:
    }//GEN-LAST:event_tblHC63VetoableChange

    private void tblHCdistribucionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHCdistribucionMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblHCdistribucionMouseClicked

    private void tblHCdistribucionPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tblHCdistribucionPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_tblHCdistribucionPropertyChange

    private void tblHCdistribucionVetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {//GEN-FIRST:event_tblHCdistribucionVetoableChange
        // TODO add your handling code here:
    }//GEN-LAST:event_tblHCdistribucionVetoableChange

    private void tblHCPorcentajesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHCPorcentajesMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblHCPorcentajesMouseClicked

    private void tblHCPorcentajesPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tblHCPorcentajesPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_tblHCPorcentajesPropertyChange

    private void tblHCPorcentajesVetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {//GEN-FIRST:event_tblHCPorcentajesVetoableChange
        // TODO add your handling code here:
    }//GEN-LAST:event_tblHCPorcentajesVetoableChange

    private void btnExportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportarActionPerformed
        // TODO add your handling code here:
        ArrayList<Depto> depto = new ArrayList<Depto>();
        ArrayList<Modulos> Modulos = new ArrayList<Modulos>();
        try {
            ResultSet rs = Principal.cn.GetConsulta("SELECT * FROM DEPARTAMENTOS");
            while (rs.next()) {
                depto.add(new Depto(rs.getString(1), rs.getString(2)));
            }
            ExcelEffort effort = new ExcelEffort((DefaultTableModel) tblHCPorcentajes.getModel(), depto);
            // ExcelEffort effort=new ExcelEffort();
//            rs=Principal.cn.GetConsulta("SELECT CASE WHEN MONTH(NOW()) = 1 THEN 'ENERO' WHEN MONTH(NOW()) = 2 THEN 'FEBRERO'  WHEN MONTH(NOW()) = 3 THEN 'MARZO' WHEN MONTH(NOW()) = 4 THEN 'ABRIL' WHEN MONTH(NOW()) = 5 THEN 'MAYO'\n" +
//                "WHEN MONTH(NOW()) = 6 THEN 'JUNIO' WHEN MONTH(NOW()) = 7 THEN 'JULIO' WHEN MONTH(NOW()) = 8 THEN 'AGOSTO' WHEN MONTH(NOW()) = 9 THEN 'SEPTIEMBRE' WHEN MONTH(NOW()) = 10 THEN 'OCTUBRE' WHEN MONTH(NOW()) = 11 THEN 'NOVIEMBRE'\n" +
//                "WHEN MONTH(NOW()) = 12 THEN 'DICIEMBRE' ELSE 'esto no es un mes' END AS MESespañol from concentradoeffort ");
            rs = Principal.cn.GetConsulta("SELECT CASE WHEN MONTH(NOW()) = 1 THEN 'DICIEMBRE' WHEN MONTH(NOW()) = 2 THEN 'ENERO'  WHEN MONTH(NOW()) = 3 THEN 'FEBRERO' WHEN MONTH(NOW()) = 4 THEN 'MARZO' WHEN MONTH(NOW()) = 5 THEN 'ABRIL'\n"
                    + "                WHEN MONTH(NOW()) = 6 THEN 'MAYO' WHEN MONTH(NOW()) = 7 THEN 'JUNIO' WHEN MONTH(NOW()) = 8 THEN 'JULIO' WHEN MONTH(NOW()) = 9 THEN 'AGOSTO' WHEN MONTH(NOW()) = 10 THEN 'SEPTIEMBRE' WHEN MONTH(NOW()) = 11 THEN 'OCTUBRE'\n"
                    + "                WHEN MONTH(NOW()) = 12 THEN 'NOVIEMBRE' ELSE 'esto no es un mes' END AS MESespañol from concentradoeffort");

            String nombreMes = "";
            if (rs.next()) {
                nombreMes = rs.getString(1);
            }
            rs = Principal.cn.GetConsulta("SELECT nombre, " + nombreMes + " FROM concentradoeffort where ano=year(now()) AND NOMBRE='SCRAP'");
            while (rs.next()) {
                Modulos.add(new Modulos(rs.getString(1), rs.getDouble(2)));
            }
            rs = Principal.cn.GetConsulta("SELECT nombre, " + nombreMes + " FROM concentradoeffort where ano=year(now()) AND NOMBRE='TLO' ");
            while (rs.next()) {
                Modulos.add(new Modulos(rs.getString(1), rs.getDouble(2)));
            }
            DefaultTableModel modeloPorc = new DefaultTableModel();
            modeloPorc.setColumnIdentifiers(new Object[]{"MODULO", "BCABLE", "SERVICIOS", "E2XX", "D2XX", "GMT610", "ISUZU", "K2XX", "GMX521", "TOTAL"});
            rs = Principal.cn.GetConsulta("SELECT concentradoeffort.nombre, porcentajes.BCABLE,  porcentajes.SERVICIOS, porcentajes.BB, porcentajes.D2XX, \n"
                    + "porcentajes.GMT610, porcentajes.ISUZU_,porcentajes.K2XX,porcentajes.GMX521 , concentradoeffort." + nombreMes + " FROM  concentradoeffort , \n"
                    + "porcentajes WHERE concentradoeffort.nombre = porcentajes.modulo and  ano=YEAR(NOW())");
            while (rs.next()) {
                modeloPorc.addRow(new Object[]{rs.getString(1), rs.getDouble(2), rs.getDouble(3), rs.getDouble(4), rs.getDouble(5), rs.getDouble(6), rs.getDouble(7), rs.getDouble(8), rs.getDouble(9), rs.getDouble(10)});
            }
            effort.gethojaScrapSq((DefaultTableModel) tblHCPorcentajes.getModel(), Modulos, modeloPorc);
            effort.getPorcentajesHC((DefaultTableModel) tblHCdistribucion.getModel(), (DefaultTableModel) tblHCPorcentajes.getModel());
        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }//GEN-LAST:event_btnExportarActionPerformed

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
            java.util.logging.Logger.getLogger(PorcentajesHC.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PorcentajesHC.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PorcentajesHC.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PorcentajesHC.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PorcentajesHC().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExportar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel lblHCRec62;
    private javax.swing.JLabel lblHCRec63;
    private javax.swing.JLabel lblHCTotal62;
    private javax.swing.JLabel lblHCTotal63;
    private Compille.RXTable tblHC62;
    private Compille.RXTable tblHC63;
    private Compille.RXTable tblHCPorcentajes;
    private Compille.RXTable tblHCTotales;
    private Compille.RXTable tblHCdistribucion;
    // End of variables declaration//GEN-END:variables
}
