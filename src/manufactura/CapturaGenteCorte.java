package manufactura;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CapturaGenteCorte extends javax.swing.JFrame {

    /**
     * Creates new form CapturaGenteCorte
     */
    String idcodigo = "";

    public CapturaGenteCorte(String Planta, String Turno) {
        initComponents();
        setLocationRelativeTo(null);
        if (Planta.equals("MOCHIS")) {
            Planta = "4";
        } else {
            Planta = "6";
        }
        Enlazar(Planta, Turno);
        EnlazarPorc(Planta, Turno);
    }

    public void Enlazar(String Planta, String turno) {
        try {
            ResultSet rs = Principal.cn.GetConsulta("SELECT"
                    + " codigos.idcodigo,\n"
                    + "codigos.CADENA,\n"
                    + "codigos.CODIGO,\n"
                    + "codigos.TURNO,\n"
                    + "tablacorte.operativoMaq,\n"
                    + "tablacorte.lideManufactura,\n"
                    + "tablacorte.SoportManufactura,\n"
                    + "tablacorte.RecuperacionDonas,\n"
                    + "tablacorte.bto,\n"
                    + "tablacorte.Capturista,\n"
                    + "tablacorte.Retrabajo,\n"
                    + "tablacorte.Contension,\n"
                    + "tablacorte.AudSistema,\n"
                    + "tablacorte.Servicios,\n"
                    + "tablacorte.Ataderos,\n"
                    + "tablacorte.OTROS,\n"
                    + " tablacorte.Recuperador,\n"
                    + "tablacorte.Covid,\n"
                    + " tablacorte.Ruteros\n"
                    + "FROM\n"
                    + "codigos ,\n"
                    + "tablacorte\n"
                    + "WHERE\n"
                    + "codigos.IDCODIGO = tablacorte.idcodigo\n"
                    + "and codigos.TURNO='" + turno + "' and codigos.cadena='" + Planta + "'");
            if (rs.next()) {
                idcodigo = rs.getString("idcodigo");
                hcOPmAQ.setValue(rs.getDouble("operativoMaq"));
                hcLidManu.setValue(rs.getDouble("lideManufactura"));
                SopManuf.setValue(rs.getDouble("SoportManufactura"));
                RecDonas.setValue(rs.getDouble("RecuperacionDonas"));
                Ataderos.setValue(rs.getDouble("Ataderos"));
                Servicios.setValue(rs.getDouble("Servicios"));
                audSistemas.setValue(rs.getDouble("AudSistema"));
                contension.setValue(rs.getDouble("Contension"));
                retrabajo.setValue(rs.getDouble("Retrabajo"));
                capturista.setValue(rs.getDouble("Capturista"));
                bto.setValue(rs.getDouble("bto"));
                ENTO.setValue(rs.getDouble("OTROS"));
                RECUPERADOR.setValue(rs.getDouble("Recuperador"));
                COVID.setValue(rs.getDouble("Covid"));
                RUTEROS.setValue(rs.getDouble("Ruteros"));
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void EnlazarPorc(String Planta, String Turno) {
        try {
            ResultSet rs = Principal.cn.GetConsulta("SELECT DISTINCT\n"
                    + "                corte.idcodigo,\n"
                    + "                hcpag.IDCODIGO, hcpag.HCDIRLINEA, corte.hrsEMBC, hcpag.horaspagadas, hcpag.TURNO, truncate((corte.hrsEMBC/hcpag.horaspagadas)*100,2) as eficiencia \n"
                    + "                from \n"
                    + "                (select c.IDCODIGO, c.LINEA, c.CODIGO, c.TURNO, m.SALIDAENPIEZA, g.HCDIRCORTE as cortee,  SUM(TRUNCATE((((g.HCDIRCORTE*m.SALIDAENPIEZA)/100)),2)) as hrsEMBC,   g.HCDIRLPS as 'POND.LPS', TRUNCATE( if(c.TURNO='B' or c.TURNO='C', ((g.HCDIRLPS*m.SALIDAENPIEZA)/7.9/100),((g.HCDIRLPS *m.SALIDAENPIEZA)/9/100)) ,2) as LPS, g.HCDIRENSFINAL as 'PUNTOS.EF', TRUNCATE( if(c.TURNO='B' or c.TURNO='C',((g.HCDIRENSFINAL*m.SALIDAENPIEZA)/7.9/100), ((g.HCDIRENSFINAL *m.SALIDAENPIEZA)/9/100)),2) as ENSFINAL  FROM codigos as c, manufactura as m, gsd  as g where c.IDCODIGO=m.IDCODIGO and c.IDCODIGO=g.IDCODIGO and m.activo=1   GROUP BY c.TURNO) as corte,\n"
                    + "                (select c.IDCODIGO, HCDIRLINEA, C.TURNO, "
                    + "case c.TURNO when'B' THEN SUM((M.HCDIRLPS+M.HCDIRSOPLPS +M.HCDIRLINEA+ m.hcdirconte+m.HCDIRSOPORTE+m.HCDIRTABINSP+m.hcrutasint+m.hcdirpilotos+m.hcdirftq+ m.hcdirsistemas)*7.9) "
                    + " WHEN 'A' THEN sum((M.HCDIRLPS+M.HCDIRSOPLPS +M.HCDIRLINEA+ m.hcdirconte+m.HCDIRSOPORTE+m.HCDIRTABINSP+m.hcrutasint+m.hcdirpilotos+m.hcdirftq+ m.hcdirsistemas)*9) "
                    + " WHEN 'C' THEN sum((M.HCDIRLPS+M.HCDIRSOPLPS +M.HCDIRLINEA+ m.hcdirconte+m.HCDIRSOPORTE+m.HCDIRTABINSP+m.hcrutasint+m.hcdirpilotos+m.hcdirftq+ m.hcdirsistemas)*7.32)  END "
                    + "as horaspagadas from manufactura as m, codigos as c where m.IDCODIGO=c.IDCODIGO and c.CADENA=4  and c.turno='" + Turno + "' and C.cadena='" + Planta + "' GROUP BY c.TURNO) as hcpag ");
            int cont = 1;

            while (rs.next()) {

                if ("A".equals(Turno)) {
                    if (cont == 1) {
                        lblhorasEmb1A.setText(rs.getString("hrsEMBC"));
                        lblHorasPagIA.setText(rs.getString("horaspagadas"));
                        lblEficManufIA.setText(rs.getString("eficiencia"));
                    }
                }

                if ("B".equals(Turno)) {
                    if (cont == 2) {
                        lblhorasEmb1A.setText(rs.getString("hrsEMBC"));
                        lblHorasPagIA.setText(rs.getString("horaspagadas"));
                        lblEficManufIA.setText(rs.getString("eficiencia"));
                    }
                }
                if ("C".equals(Turno)) {
                    if (cont == 3) {
                        lblhorasEmb1A.setText(rs.getString("hrsEMBC"));
                        lblHorasPagIA.setText(rs.getString("horaspagadas"));
                        lblEficManufIA.setText(rs.getString("eficiencia"));
                    }
                }

                cont++;
            }
        } catch (SQLException ex) {
            Logger.getLogger(CapturaGenteCorte.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void sacarTotal() {
        try {

            Double total = Double.parseDouble(hcOPmAQ.getValue().toString()) + Double.parseDouble(hcLidManu.getValue().toString()) + Double.parseDouble(SopManuf.getValue().toString()) + Double.parseDouble(RecDonas.getValue().toString()) + Double.parseDouble(Ataderos.getValue().toString()) + Double.parseDouble(bto.getValue().toString()) + Double.parseDouble(capturista.getValue().toString()) + Double.parseDouble(retrabajo.getValue().toString()) + Double.parseDouble(contension.getValue().toString()) + Double.parseDouble(audSistemas.getValue().toString()) + Double.parseDouble(Servicios.getValue().toString()) + Double.parseDouble(ENTO.getValue().toString()) + Double.parseDouble(RECUPERADOR.getValue().toString()) + Double.parseDouble(COVID.getValue().toString()) + Double.parseDouble(RUTEROS.getValue().toString());
            lblHCDIR.setText(String.valueOf(total));
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
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        hcOPmAQ = new javax.swing.JSpinner();
        jLabel9 = new javax.swing.JLabel();
        hcLidManu = new javax.swing.JSpinner();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        RecDonas = new javax.swing.JSpinner();
        SopManuf = new javax.swing.JSpinner();
        contension = new javax.swing.JSpinner();
        jLabel12 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        retrabajo = new javax.swing.JSpinner();
        capturista = new javax.swing.JSpinner();
        jLabel16 = new javax.swing.JLabel();
        bto = new javax.swing.JSpinner();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        audSistemas = new javax.swing.JSpinner();
        Servicios = new javax.swing.JSpinner();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        Ataderos = new javax.swing.JSpinner();
        jLabel21 = new javax.swing.JLabel();
        ENTO = new javax.swing.JSpinner();
        jLabel22 = new javax.swing.JLabel();
        COVID = new javax.swing.JSpinner();
        jLabel23 = new javax.swing.JLabel();
        RUTEROS = new javax.swing.JSpinner();
        RECUPERADOR = new javax.swing.JSpinner();
        jLabel24 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        lblHCDIR = new javax.swing.JLabel();
        panelCadIA = new javax.swing.JPanel();
        jLabel32 = new javax.swing.JLabel();
        lblEficManufIA = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        lblhorasEmb1A = new javax.swing.JLabel();
        lblHorasPagIA = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        pnlMenu = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(51, 51, 255));
        jLabel8.setText("OPERADOR MAQUINA:");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 20, -1, -1));

        hcOPmAQ.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        hcOPmAQ.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));
        hcOPmAQ.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                hcOPmAQStateChanged(evt);
            }
        });
        hcOPmAQ.addVetoableChangeListener(new java.beans.VetoableChangeListener() {
            public void vetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {
                hcOPmAQVetoableChange(evt);
            }
        });
        jPanel1.add(hcOPmAQ, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 20, 88, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(51, 51, 255));
        jLabel9.setText("LIDERES MANUFACTURA:");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, -1, -1));

        hcLidManu.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        hcLidManu.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));
        hcLidManu.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                hcLidManuStateChanged(evt);
            }
        });
        jPanel1.add(hcLidManu, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 80, 88, -1));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(51, 51, 255));
        jLabel10.setText("SOPORTE MANUFACTURA:");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, -1, -1));

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(51, 51, 255));
        jLabel11.setText("PILOTOS CORTE");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 200, -1, -1));

        RecDonas.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        RecDonas.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));
        RecDonas.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                RecDonasStateChanged(evt);
            }
        });
        jPanel1.add(RecDonas, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 200, 88, -1));

        SopManuf.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        SopManuf.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));
        SopManuf.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                SopManufStateChanged(evt);
            }
        });
        jPanel1.add(SopManuf, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 140, 88, -1));

        contension.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        contension.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));
        contension.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                contensionStateChanged(evt);
            }
        });
        jPanel1.add(contension, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 160, 88, -1));

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(51, 51, 255));
        jLabel12.setText("CONTENSION:");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 160, -1, -1));

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(51, 51, 255));
        jLabel15.setText("RETRABAJO:");
        jPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 110, -1, -1));

        retrabajo.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        retrabajo.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));
        retrabajo.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                retrabajoStateChanged(evt);
            }
        });
        jPanel1.add(retrabajo, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 110, 88, -1));

        capturista.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        capturista.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));
        capturista.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                capturistaStateChanged(evt);
            }
        });
        jPanel1.add(capturista, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 60, 88, -1));

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(51, 51, 255));
        jLabel16.setText("SISTEMAS:");
        jPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 10, -1, -1));

        bto.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        bto.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));
        bto.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                btoStateChanged(evt);
            }
        });
        jPanel1.add(bto, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 10, 88, -1));

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(51, 51, 255));
        jLabel17.setText("CAPTURISTAS:");
        jPanel1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 60, -1, -1));

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(51, 51, 255));
        jLabel18.setText("ENTTO:");
        jPanel1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 210, -1, -1));

        audSistemas.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        audSistemas.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));
        audSistemas.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                audSistemasStateChanged(evt);
            }
        });
        jPanel1.add(audSistemas, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 210, 88, -1));

        Servicios.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        Servicios.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));
        Servicios.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                ServiciosStateChanged(evt);
            }
        });
        jPanel1.add(Servicios, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 260, 88, -1));

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(51, 51, 255));
        jLabel19.setText("SERVICIOS:");
        jPanel1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 260, -1, -1));

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(51, 51, 255));
        jLabel20.setText("ATADEROS:");
        jPanel1.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 250, -1, -1));

        Ataderos.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        Ataderos.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));
        Ataderos.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                AtaderosStateChanged(evt);
            }
        });
        jPanel1.add(Ataderos, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 250, 88, -1));

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(51, 51, 255));
        jLabel21.setText("CAO:");
        jPanel1.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 370, -1, -1));

        ENTO.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        ENTO.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));
        ENTO.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                ENTOStateChanged(evt);
            }
        });
        ENTO.addVetoableChangeListener(new java.beans.VetoableChangeListener() {
            public void vetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {
                ENTOVetoableChange(evt);
            }
        });
        jPanel1.add(ENTO, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 370, 88, -1));

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(51, 51, 255));
        jLabel22.setText("COVID:");
        jPanel1.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 370, -1, -1));

        COVID.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        COVID.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));
        COVID.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                COVIDStateChanged(evt);
            }
        });
        COVID.addVetoableChangeListener(new java.beans.VetoableChangeListener() {
            public void vetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {
                COVIDVetoableChange(evt);
            }
        });
        jPanel1.add(COVID, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 370, 88, -1));

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(51, 51, 255));
        jLabel23.setText("RUTEROS:");
        jPanel1.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 370, -1, -1));

        RUTEROS.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        RUTEROS.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));
        RUTEROS.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                RUTEROSStateChanged(evt);
            }
        });
        RUTEROS.addVetoableChangeListener(new java.beans.VetoableChangeListener() {
            public void vetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {
                RUTEROSVetoableChange(evt);
            }
        });
        jPanel1.add(RUTEROS, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 370, 88, -1));

        RECUPERADOR.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        RECUPERADOR.setModel(new javax.swing.SpinnerNumberModel(Integer.valueOf(0), Integer.valueOf(0), null, Integer.valueOf(1)));
        RECUPERADOR.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                RECUPERADORStateChanged(evt);
            }
        });
        jPanel1.add(RECUPERADOR, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 310, 88, -1));

        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(51, 51, 255));
        jLabel24.setText("RECUPERADOR:");
        jPanel1.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 310, -1, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 980, 430));

        jPanel2.setBackground(new java.awt.Color(250, 238, 5));

        btnGuardar.setBackground(new java.awt.Color(255, 255, 255));
        btnGuardar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Image55.png"))); // NOI18N
        btnGuardar.setText("GUARDAR");
        btnGuardar.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        btnGuardar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnGuardar.setPreferredSize(new java.awt.Dimension(73, 75));
        btnGuardar.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnGuardar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnCancelar.setBackground(new java.awt.Color(255, 255, 255));
        btnCancelar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/navigate-left48.png"))); // NOI18N
        btnCancelar.setText("CANCELAR");
        btnCancelar.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        btnCancelar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCancelar.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnCancelar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(new javax.swing.border.MatteBorder(null));

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(51, 51, 51));
        jLabel13.setText("TOTAL GENTE:");

        lblHCDIR.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblHCDIR.setForeground(new java.awt.Color(51, 51, 51));
        lblHCDIR.setText("0");
        lblHCDIR.setPreferredSize(new java.awt.Dimension(13, 50));

        panelCadIA.setBackground(new java.awt.Color(255, 255, 255));
        panelCadIA.setBorder(javax.swing.BorderFactory.createTitledBorder("CORTE"));
        panelCadIA.setPreferredSize(new java.awt.Dimension(298, 109));
        panelCadIA.setLayout(new java.awt.GridBagLayout());

        jLabel32.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel32.setText("EFIC. MANUF.:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 16, 30, 0);
        panelCadIA.add(jLabel32, gridBagConstraints);

        lblEficManufIA.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblEficManufIA.setForeground(new java.awt.Color(255, 51, 51));
        lblEficManufIA.setText("0.0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 30, 58);
        panelCadIA.add(lblEficManufIA, gridBagConstraints);

        jLabel33.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel33.setText("HRS.EMB.:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(16, 34, 0, 0);
        panelCadIA.add(jLabel33, gridBagConstraints);

        lblhorasEmb1A.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblhorasEmb1A.setForeground(new java.awt.Color(255, 51, 51));
        lblhorasEmb1A.setText("0.0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(16, 6, 0, 0);
        panelCadIA.add(lblhorasEmb1A, gridBagConstraints);

        lblHorasPagIA.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblHorasPagIA.setForeground(new java.awt.Color(255, 51, 51));
        lblHorasPagIA.setText("0.0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        panelCadIA.add(lblHorasPagIA, gridBagConstraints);

        jLabel34.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel34.setText("HRS.PAG.:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 34, 0, 0);
        panelCadIA.add(jLabel34, gridBagConstraints);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(56, Short.MAX_VALUE)
                .addComponent(panelCadIA, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblHCDIR, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblHCDIR, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addContainerGap(43, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelCadIA, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(73, 73, 73)
                .addComponent(btnCancelar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 132, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(87, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 470, 980, 200));

        pnlMenu.setBackground(new java.awt.Color(255, 255, 153));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 255));
        jLabel6.setText("GENTE DE CORTE");

        javax.swing.GroupLayout pnlMenuLayout = new javax.swing.GroupLayout(pnlMenu);
        pnlMenu.setLayout(pnlMenuLayout);
        pnlMenuLayout.setHorizontalGroup(
            pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMenuLayout.createSequentialGroup()
                .addGap(227, 227, 227)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 523, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(230, Short.MAX_VALUE))
        );
        pnlMenuLayout.setVerticalGroup(
            pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMenuLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        getContentPane().add(pnlMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 980, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
        try {
            ArrayList<Object> objetos = new ArrayList<Object>();
            objetos.add(hcOPmAQ.getValue());
            objetos.add(hcLidManu.getValue());
            objetos.add(SopManuf.getValue());
            objetos.add(RecDonas.getValue());
            objetos.add(bto.getValue());
            objetos.add(capturista.getValue());
            objetos.add(retrabajo.getValue());
            objetos.add(contension.getValue());
            objetos.add(audSistemas.getValue());
            objetos.add(Servicios.getValue());
            objetos.add(Ataderos.getValue());
            objetos.add(ENTO.getValue());
            objetos.add(RECUPERADOR.getValue());
            objetos.add(COVID.getValue());
            objetos.add(RUTEROS.getValue());

            //  objetos.add(Principal.UsuarioLogeado.codigo);
            objetos.add(idcodigo);
            Principal.cn.EjecutarInsertOb("update tablacorte set operativomaq=?, lidemanufactura=?, soportmanufactura=?, recuperaciondonas=?,  bto=?, capturista=?,  retrabajo=?,  contension=?, audsistema=?, servicios=?, ataderos=?, OTROS=? , Recuperador=?, Covid=?, Ruteros=? where idcodigo=?", objetos);

        } catch (Exception e) {

        }

    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        this.dispose();


    }//GEN-LAST:event_btnCancelarActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
        Principal p = new Principal(Principal.UsuarioLogeado);
        p.setLocationRelativeTo(null);
        p.setVisible(true);
    }//GEN-LAST:event_formWindowClosed

    private void hcOPmAQVetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {//GEN-FIRST:event_hcOPmAQVetoableChange
        // TODO add your handling code here:
        if (Double.parseDouble(hcOPmAQ.getValue().toString()) < 0) {
            hcOPmAQ.setValue(0);
        }
    }//GEN-LAST:event_hcOPmAQVetoableChange

    private void hcOPmAQStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_hcOPmAQStateChanged
        // TODO add your handling code here:
        sacarTotal();
    }//GEN-LAST:event_hcOPmAQStateChanged

    private void hcLidManuStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_hcLidManuStateChanged
        // TODO add your handling code here:
        sacarTotal();
    }//GEN-LAST:event_hcLidManuStateChanged

    private void SopManufStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_SopManufStateChanged
        // TODO add your handling code here:
        sacarTotal();
    }//GEN-LAST:event_SopManufStateChanged

    private void RecDonasStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_RecDonasStateChanged
        // TODO add your handling code here:
        sacarTotal();
    }//GEN-LAST:event_RecDonasStateChanged

    private void AtaderosStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_AtaderosStateChanged
        // TODO add your handling code here:
        sacarTotal();
    }//GEN-LAST:event_AtaderosStateChanged

    private void btoStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_btoStateChanged
        // TODO add your handling code here:
        sacarTotal();
    }//GEN-LAST:event_btoStateChanged

    private void capturistaStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_capturistaStateChanged
        // TODO add your handling code here:
        sacarTotal();
    }//GEN-LAST:event_capturistaStateChanged

    private void retrabajoStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_retrabajoStateChanged
        // TODO add your handling code here:
        sacarTotal();
    }//GEN-LAST:event_retrabajoStateChanged

    private void contensionStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_contensionStateChanged
        // TODO add your handling code here:
        sacarTotal();
    }//GEN-LAST:event_contensionStateChanged

    private void audSistemasStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_audSistemasStateChanged
        // TODO add your handling code here:
        sacarTotal();
    }//GEN-LAST:event_audSistemasStateChanged

    private void ServiciosStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_ServiciosStateChanged
        // TODO add your handling code here:
        sacarTotal();
    }//GEN-LAST:event_ServiciosStateChanged

    private void ENTOStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_ENTOStateChanged
        // TODO add your handling code here:
        sacarTotal();
    }//GEN-LAST:event_ENTOStateChanged

    private void ENTOVetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {//GEN-FIRST:event_ENTOVetoableChange
        // TODO add your handling code here:

    }//GEN-LAST:event_ENTOVetoableChange

    private void COVIDStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_COVIDStateChanged
        // TODO add your handling code here:
        sacarTotal();
    }//GEN-LAST:event_COVIDStateChanged

    private void COVIDVetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {//GEN-FIRST:event_COVIDVetoableChange
        // TODO add your handling code here:
    }//GEN-LAST:event_COVIDVetoableChange

    private void RUTEROSStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_RUTEROSStateChanged
        // TODO add your handling code here:
        sacarTotal();
    }//GEN-LAST:event_RUTEROSStateChanged

    private void RUTEROSVetoableChange(java.beans.PropertyChangeEvent evt)throws java.beans.PropertyVetoException {//GEN-FIRST:event_RUTEROSVetoableChange
        // TODO add your handling code here:
    }//GEN-LAST:event_RUTEROSVetoableChange

    private void RECUPERADORStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_RECUPERADORStateChanged
        // TODO add your handling code here:
        sacarTotal();
    }//GEN-LAST:event_RECUPERADORStateChanged

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
            java.util.logging.Logger.getLogger(CapturaGenteCorte.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CapturaGenteCorte.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CapturaGenteCorte.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CapturaGenteCorte.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CapturaGenteCorte("", "").setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSpinner Ataderos;
    private javax.swing.JSpinner COVID;
    private javax.swing.JSpinner ENTO;
    private javax.swing.JSpinner RECUPERADOR;
    private javax.swing.JSpinner RUTEROS;
    private javax.swing.JSpinner RecDonas;
    private javax.swing.JSpinner Servicios;
    private javax.swing.JSpinner SopManuf;
    private javax.swing.JSpinner audSistemas;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JSpinner bto;
    private javax.swing.JSpinner capturista;
    private javax.swing.JSpinner contension;
    private javax.swing.JSpinner hcLidManu;
    private javax.swing.JSpinner hcOPmAQ;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblEficManufIA;
    private javax.swing.JLabel lblHCDIR;
    private javax.swing.JLabel lblHorasPagIA;
    private javax.swing.JLabel lblhorasEmb1A;
    private javax.swing.JPanel panelCadIA;
    private javax.swing.JPanel pnlMenu;
    private javax.swing.JSpinner retrabajo;
    // End of variables declaration//GEN-END:variables
}
