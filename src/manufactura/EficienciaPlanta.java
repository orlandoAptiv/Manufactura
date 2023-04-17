package manufactura;

import Clases.Cadena;
import Clases.Conection;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EficienciaPlanta extends javax.swing.JFrame {

    /**
     * Creates new form EficienciaPlanta
     */
    Double HrsEmbCA, HrsEmbCB, HcCA, HcCB, HrsPagCA, HrsPagCB, EficCA, EficCB, EficCadCorte;
    Double HrsEmbIA, HrsEmbIB, HcIA, HcIB, HrsPagIA, HrsPagIB, EficIA, EficIB, EficCadI;
    Double HrsEmbIIA, HrsEmbIIB, HcIIA, HcIIB, HrsPagIIA, HrsPagIIB, EficIIA, EficIIB, EficCadII;
    Double HrsEmbIIIA, HrsEmbIIIB, HcIIIA, HcIIIB, HrsPagIIIA, HrsPagIIIB, EficIIIA, EficIIIB, EficCadIII;
    Double HrsEmbSA, HcSA, HrsPagSA, HrsPagSB, EficSA, EficCadS;
    Double HrsEmbSB, HcSB, EficSB;
    Double totalHCTurnoA = 0.0;
    Double totalHCTurnoB = 0.0;
    Double totalHrsEMbA = 0.0;
    Double totalHrsEMbB = 0.0;
    Double totalHrsPagA = 0.0;
    Double totalHrsPagB = 0.0;
    Double TotaleficA = 0.0;
    Double TotaleficB = 0.0;
    Double TotalHC = 0.0;
    Double TotalHrsEMB = 00.0;
    Double TotalHrsPag = 0.0;
    Double TotalEficiencia;

    public EficienciaPlanta() {
        try {
            initComponents();
            Principal.cn = new Conection();
            ObtenerCorte();
            ObtenerCadenas();
            SacarTotales();
        } catch (SQLException ex) {
            Logger.getLogger(EficienciaPlanta.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EficienciaPlanta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Double Regresa2Decimales(Double Valor) {
        try {
            int aux = (int) (Valor * 100);//1243
            Valor = aux / 100d;// 

        } catch (Exception e) {
            System.out.println(e.toString());
        }
        System.out.println(Valor.toString());
        return Valor;

    }

    public void ObtenerCorte() {
        try {
            ResultSet st = Principal.cn.GetConsulta("SELECT DISTINCT\n"
                    + "corte.idcodigo,\n"
                    + "hcpag.IDCODIGO, hcpag.HCDIRLINEA, corte.hrsEMBC, hcpag.horaspagadas, hcpag.TURNO, truncate((corte.hrsEMBC/hcpag.horaspagadas)*100,2) as eficiencia \n"
                    + "from \n"
                    + "(select c.IDCODIGO, c.LINEA, c.CODIGO, c.TURNO, m.SALIDAENPIEZA, g.HCDIRCORTE as cortee,  SUM(TRUNCATE((((g.HCDIRCORTE*m.SALIDAENPIEZA)/100)),2)) as hrsEMBC,   g.HCDIRLPS as 'POND.LPS', TRUNCATE( if(c.TURNO='A', ((g.HCDIRLPS *m.SALIDAENPIEZA)/9/100), ((g.HCDIRLPS*m.SALIDAENPIEZA)/7.9/100)),2) as LPS, g.HCDIRENSFINAL as 'PUNTOS.EF', TRUNCATE( if(c.TURNO='A', ((g.HCDIRENSFINAL *m.SALIDAENPIEZA)/9/100),  ((g.HCDIRENSFINAL*m.SALIDAENPIEZA)/7.9/100)),2) as ENSFINAL  FROM codigos as c, manufactura as m, gsd  as g where c.IDCODIGO=m.IDCODIGO and c.IDCODIGO=g.IDCODIGO   GROUP BY c.TURNO) as corte,\n"
                    + "(select c.IDCODIGO, HCDIRLINEA, C.TURNO, if(c.TURNO='A', sum((M.HCDIRLPS+M.HCDIRSOPLPS +M.HCDIRLINEA+ m.hcdirconte+m.HCDIRSOPORTE+m.HCDIRTABINSP+m.hcrutasint+m.hcdirpilotos+m.hcdirftq+ m.hcdirsistemas)*9), SUM((M.HCDIRLPS+M.HCDIRSOPLPS +M.HCDIRLINEA+ m.hcdirconte+m.HCDIRSOPORTE+m.HCDIRTABINSP+m.hcrutasint+m.hcdirpilotos+m.hcdirftq+ m.hcdirsistemas)*7.9)) as horaspagadas from manufactura as m, codigos as c where m.IDCODIGO=c.IDCODIGO and c.CADENA=4 GROUP BY c.TURNO) as hcpag");
            int cont = 1;
            while (st.next()) {
                if (cont == 1) {
                    HrsEmbCA = st.getDouble("hrsEMBC");
                    HrsPagCA = st.getDouble("horaspagadas");
                    HcCA = st.getDouble("hcdirlinea");
                    EficCA = st.getDouble("eficiencia");
                }
                if (cont == 4) {
                    HrsEmbCB = st.getDouble("hrsEMBC");
                    HrsPagCB = st.getDouble("horaspagadas");
                    HcCB = st.getDouble("hcdirlinea");
                    EficCB = st.getDouble("eficiencia");
                }
                cont++;
            }
            EficCadCorte = (((HrsEmbCA + HrsEmbCB) / (HrsPagCA + HrsPagCB)) * 100);
            EficCadCorte = Regresa2Decimales(EficCadCorte);
            lblEficCADCA.setText(EficCadCorte.toString());
            lblEmbCA.setText(HrsEmbCA.toString());
            lblHrsPagCA.setText(HrsPagCA.toString());
            lblGenteCA.setText(HcCA.toString());
            lblEmbCB.setText(HrsEmbCB.toString());
            lblEficCA.setText(EficCA.toString());
            lblEficCB.setText(EficCB.toString());
            lblHrsPagCB.setText(HrsPagCB.toString());
            lblGenteCB.setText(HcCB.toString());
        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }

    public void ObtenerCadenas() {
        try {
            HrsEmbSB = 0.0;
            HrsPagSB = 0.0;
            EficSB = 0.0;
            HcSB = 0.0;
            ResultSet rs = Principal.cn.GetConsulta("select  manu.CADENA, manu.turno, manu.hc,  ROUND((manu.horasemb),2) as horasemb, ROUND((manu.hrsPagadas),2) as hrsPagadas,  ( manu.horasemb/manu.hrsPagadas)*100 as efic from \n"
                    + "(select c.LINEA, c.CADENA, c.turno, sum((M.HCDIRLPS+M.HCDIRSOPLPS +M.HCDIRLINEA+ m.hcdirconte+m.HCDIRSOPORTE+m.HCDIRTABINSP+m.hcrutasint+m.hcdirpilotos+m.hcdirftq+ m.hcdirsistemas)) as hc, sum((m.PUNTOSPZAPOND*m.SALIDAENPIEZA/100)) as horasemb,  IF(c.TURNO='A',sum(((M.HCDIRLPS+M.HCDIRSOPLPS +M.HCDIRLINEA+ m.hcdirconte+m.HCDIRSOPORTE+m.HCDIRTABINSP+m.hcrutasint+m.hcdirpilotos+m.hcdirftq+ m.hcdirsistemas)*9)),sum(((M.HCDIRLPS+M.HCDIRSOPLPS +M.HCDIRLINEA+ m.hcdirconte+m.HCDIRSOPORTE+m.HCDIRTABINSP+m.hcrutasint+m.hcdirpilotos+m.hcdirftq+ m.hcdirsistemas)*7.9))) as hrsPagadas  from manufactura as m, codigos as c where m.idcodigo=c.IDCODIGO GROUP BY c.CADENA, c.TURNO) as manu ");
            while (rs.next()) {
                if ((rs.getString("Cadena").equals("1")) && (rs.getString("TURNO").equals("A"))) {
                    HcIA = rs.getDouble("hc");
                    HrsEmbIA = rs.getDouble("horasemb");
                    HrsPagIA = rs.getDouble("hrspagadas");
                    EficIA = Regresa2Decimales(rs.getDouble("efic"));
                }
                if ((rs.getString("Cadena").equals("2")) && (rs.getString("TURNO").equals("A"))) {
                    HcIIA = rs.getDouble("hc");
                    HrsEmbIIA = rs.getDouble("horasemb");
                    HrsPagIIA = rs.getDouble("hrspagadas");
                    EficIIA = Regresa2Decimales(rs.getDouble("efic"));
                }
                if ((rs.getString("Cadena").equals("3")) && (rs.getString("TURNO").equals("A"))) {
                    HcIIIA = rs.getDouble("hc");
                    HrsEmbIIIA = rs.getDouble("horasemb");
                    HrsPagIIIA = rs.getDouble("hrspagadas");
                    EficIIIA = Regresa2Decimales(rs.getDouble("efic"));
                }
                if ((rs.getString("Cadena").equals("1")) && (rs.getString("TURNO").equals("B"))) {
                    HcIB = rs.getDouble("hc");
                    HrsEmbIB = rs.getDouble("horasemb");
                    HrsPagIB = rs.getDouble("hrspagadas");
                    EficIB = Regresa2Decimales(rs.getDouble("efic"));
                }
                if ((rs.getString("Cadena").equals("2")) && (rs.getString("TURNO").equals("B"))) {
                    HcIIB = rs.getDouble("hc");
                    HrsEmbIIB = rs.getDouble("horasemb");
                    HrsPagIIB = rs.getDouble("hrspagadas");
                    EficIIB = Regresa2Decimales(rs.getDouble("efic"));
                }
                if ((rs.getString("Cadena").equals("3")) && (rs.getString("TURNO").equals("B"))) {
                    HcIIIB = rs.getDouble("hc");
                    HrsEmbIIIB = rs.getDouble("horasemb");
                    HrsPagIIIB = rs.getDouble("hrspagadas");
                    EficIIIB = Regresa2Decimales(rs.getDouble("efic"));

                }
                if ((rs.getString("Cadena").equals("5")) && (rs.getString("TURNO").equals("A"))) {
                    HcSA = rs.getDouble("hc");
                    HrsEmbSA = rs.getDouble("horasemb");
                    HrsPagSA = rs.getDouble("hrspagadas");
                    EficSA = Regresa2Decimales(rs.getDouble("efic"));
                }
            }
            lblGenteIA.setText(HcIA.toString());
            lblGenteIIA.setText(HcIIA.toString());
            lblGenteIIIA.setText(HcIIIA.toString());
            lblGenteIB.setText(HcIB.toString());
            lblGenteIIB.setText(HcIIB.toString());
            lblGenteIIIB.setText(HcIIIB.toString());
            lblEmbIA.setText(HrsEmbIA.toString());
            lblEmbIIA.setText(HrsEmbIIA.toString());
            lblEmbIIIA.setText(HrsEmbIIIA.toString());
            lblEmbIB.setText(HrsEmbIB.toString());
            lblEmbIIB.setText(HrsEmbIIB.toString());
            lblEmbIIIB.setText(HrsEmbIIIB.toString());
            lblHrsPagIA.setText(HrsPagIA.toString());
            lblHrsPagIIA.setText(HrsPagIIA.toString());
            lblHrsPagIIIA.setText(HrsPagIIIA.toString());
            lblHrsPagIB.setText(HrsPagIB.toString());
            lblHrsPagIIB.setText(HrsPagIIB.toString());
            lblHrsPagIIIB.setText(HrsPagIIIB.toString());
            lblEficIA.setText(EficIA.toString());
            lblEficIIA.setText(EficIIA.toString());
            lblEficIIIA.setText(EficIIIA.toString());
            lblEficIB.setText(EficIB.toString());
            lblEficIIB.setText(EficIIB.toString());
            lblEficIIIB.setText(EficIIIB.toString());
            EficCadI = Regresa2Decimales(((HrsEmbIA + HrsEmbIB) / (HrsPagIA + HrsPagIB)) * 100);
            EficCadII = Regresa2Decimales(((HrsEmbIIA + HrsEmbIIB) / (HrsPagIIA + HrsPagIIB)) * 100);
            EficCadIII = Regresa2Decimales(((HrsEmbIIIA + HrsEmbIIIB) / (HrsPagIIIA + HrsPagIIIB)) * 100);
            EficCadS = Regresa2Decimales((HrsEmbSA / HrsPagSA) * 100);
            lblEficCADIA.setText(EficCadI.toString());
            lblEficCADIIA.setText(EficCadII.toString());
            lblEficCADIIIA.setText(EficCadIII.toString());
            lblEmbSA.setText(HrsEmbSA.toString());
            lblGenteSA.setText(HcSA.toString());
            lblHrsPagSA.setText(HrsPagSA.toString());
            lblEficSA.setText(Regresa2Decimales(EficSA).toString());
            lblEficCADSA.setText(EficCadS.toString());
            lblEmbSB.setText(HrsEmbSB.toString());
            lblHrsPagSB.setText(HrsPagSB.toString());
            lblEficSB.setText(EficSB.toString());
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void SacarTotales() {
        try {
            totalHCTurnoA = Regresa2Decimales(HcCA + HcIA + HcIIA + HcIIIA + HcSA);
            totalHCTurnoB = Regresa2Decimales(HcCB + HcIB + HcIIB + HcIIIB + HcSB);
            totalHrsEMbA = Regresa2Decimales(HrsEmbCA + HrsEmbIA + HrsEmbIIA + HrsEmbIIIA + HrsEmbSA);
            totalHrsEMbB = Regresa2Decimales(HrsEmbCB + HrsEmbIB + HrsEmbIIB + HrsEmbIIIB + HrsEmbSB);
            totalHrsPagA = Regresa2Decimales(HrsPagCA + HrsPagIA + HrsPagIIA + HrsPagIIIA + HrsPagSA);
            totalHrsPagB = Regresa2Decimales(HrsPagCB + HrsPagIB + HrsPagIIB + HrsPagIIIB + HrsPagSB);
            TotaleficA = Regresa2Decimales((totalHrsEMbA / totalHrsPagA) * 100);
            TotaleficB = Regresa2Decimales((totalHrsEMbB / totalHrsPagB) * 100);
            TotalHC = Regresa2Decimales(totalHCTurnoA + totalHCTurnoB);
            TotalHrsEMB = Regresa2Decimales(totalHrsEMbA + totalHrsEMbB);
            TotalHrsPag = totalHrsPagA + totalHrsPagB;
            TotalEficiencia = Regresa2Decimales((TotalHrsEMB / TotalHrsPag) * 100);
            lblGenteTA.setText(totalHCTurnoA.toString());
            lblGenteTB.setText(totalHCTurnoB.toString());
            lblHrsPagTotalA.setText(totalHrsPagA.toString());
            lblHrsPagTotalB.setText(totalHrsPagB.toString());
            lblEmbTotalA.setText(totalHrsEMbA.toString());
            lblEmbTotalB.setText(totalHrsEMbB.toString());
            lblEficTotalA.setText(Regresa2Decimales(TotaleficA).toString());
            lblEficTotalB.setText(Regresa2Decimales(TotaleficB).toString());
            lbltotalGenteAB.setText(TotalHC.toString());
            lblTotalHrsPagadasAB.setText(TotalHrsPag.toString());
            lblTotalEmbAB.setText(TotalHrsEMB.toString());
            lbleficienciaTurnos.setText(TotalEficiencia.toString());
            lblEficPlanta.setText(lbleficienciaTurnos.getText());

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

        jButton11 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        lblEficSA = new javax.swing.JButton();
        lblHrsPagCA = new javax.swing.JButton();
        lblEficTotalA = new javax.swing.JButton();
        lblHrsPagIIIB = new javax.swing.JButton();
        lblEficCB = new javax.swing.JButton();
        lblEficSB = new javax.swing.JButton();
        jButton41 = new javax.swing.JButton();
        lblHrsPagSB = new javax.swing.JButton();
        lblEficTotalB = new javax.swing.JButton();
        lblHrsPagSA = new javax.swing.JButton();
        lblHrsPagCB = new javax.swing.JButton();
        jButton42 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        btnCadCorte = new javax.swing.JButton();
        btnCad3 = new javax.swing.JButton();
        btnCad2 = new javax.swing.JButton();
        btnCad1 = new javax.swing.JButton();
        lblTotalEmbAB = new javax.swing.JButton();
        lblEmbTotalA = new javax.swing.JButton();
        lblEmbTotalB = new javax.swing.JButton();
        lblHrsPagTotalA = new javax.swing.JButton();
        lblHrsPagTotalB = new javax.swing.JButton();
        lblEficienciaTurnoAB = new javax.swing.JButton();
        lblTotalHrsPagadasAB = new javax.swing.JButton();
        lblEmbSA = new javax.swing.JButton();
        lblEmbIIIB = new javax.swing.JButton();
        lblGenteIA = new javax.swing.JButton();
        lblEmbIIB = new javax.swing.JButton();
        lblEmbSB = new javax.swing.JButton();
        lblEmbCB = new javax.swing.JButton();
        jButton31 = new javax.swing.JButton();
        jButton32 = new javax.swing.JButton();
        lblHrsPagIIA = new javax.swing.JButton();
        lblHrsPagIIB = new javax.swing.JButton();
        lblHrsPagIIIA = new javax.swing.JButton();
        lblGenteSA = new javax.swing.JButton();
        lblGenteIIB = new javax.swing.JButton();
        lblGenteIIIA = new javax.swing.JButton();
        lblGenteCA = new javax.swing.JButton();
        lblEficCADIA = new javax.swing.JButton();
        lblEficCADIIIA = new javax.swing.JButton();
        lblEficIB = new javax.swing.JButton();
        jButton73 = new javax.swing.JButton();
        lblHrsPagIB = new javax.swing.JButton();
        lblEficIA = new javax.swing.JButton();
        lblEmbIB = new javax.swing.JButton();
        lblHrsPagIA = new javax.swing.JButton();
        lblGenteIB = new javax.swing.JButton();
        lblEmbIA = new javax.swing.JButton();
        lblEmbCA = new javax.swing.JButton();
        lblEmbIIIA = new javax.swing.JButton();
        lblEmbIIA = new javax.swing.JButton();
        lblGenteSB = new javax.swing.JButton();
        lblGenteIIIB = new javax.swing.JButton();
        lblGenteCB = new javax.swing.JButton();
        btnCadSer = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        lblEficIIIA = new javax.swing.JButton();
        lblEficIIIB = new javax.swing.JButton();
        lblEficCA = new javax.swing.JButton();
        lbleficienciaTurnos = new javax.swing.JButton();
        lblEficPlanta = new javax.swing.JButton();
        lblEficIIA = new javax.swing.JButton();
        jButton52 = new javax.swing.JButton();
        lblEficIIB = new javax.swing.JButton();
        btnTotalesTurnoo = new javax.swing.JButton();
        lblEficCADIIA = new javax.swing.JButton();
        lblEficCADCA = new javax.swing.JButton();
        lblEficCADSA = new javax.swing.JButton();
        lblGenteTA = new javax.swing.JButton();
        lblGenteTB = new javax.swing.JButton();
        lbltotalGenteAB = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        dsa = new javax.swing.JButton();
        lblGenteIIA = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        lblEmbIBSDA = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("EFICIENCIA DIARIAS GENERAL");
        setLocationByPlatform(true);
        setPreferredSize(new java.awt.Dimension(1140, 600));
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jButton11.setBackground(new java.awt.Color(255, 255, 102));
        jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/filetype-xls-icon (2).png"))); // NOI18N
        jButton11.setText("EXPORTAR");
        jButton11.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton11.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jButton11.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 51, 255), 4, true));

        lblEficSA.setBackground(new java.awt.Color(204, 204, 204));
        lblEficSA.setText("0");
        lblEficSA.setSelected(true);
        lblEficSA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblEficSAActionPerformed(evt);
            }
        });

        lblHrsPagCA.setBackground(new java.awt.Color(204, 204, 204));
        lblHrsPagCA.setText("0");
        lblHrsPagCA.setSelected(true);
        lblHrsPagCA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblHrsPagCAActionPerformed(evt);
            }
        });

        lblEficTotalA.setBackground(new java.awt.Color(204, 204, 204));
        lblEficTotalA.setText("0");
        lblEficTotalA.setSelected(true);
        lblEficTotalA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblEficTotalAActionPerformed(evt);
            }
        });

        lblHrsPagIIIB.setBackground(new java.awt.Color(204, 204, 204));
        lblHrsPagIIIB.setText("0");
        lblHrsPagIIIB.setSelected(true);
        lblHrsPagIIIB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblHrsPagIIIBActionPerformed(evt);
            }
        });

        lblEficCB.setBackground(new java.awt.Color(204, 204, 204));
        lblEficCB.setText("0");
        lblEficCB.setPreferredSize(new java.awt.Dimension(50, 50));
        lblEficCB.setSelected(true);
        lblEficCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblEficCBActionPerformed(evt);
            }
        });

        lblEficSB.setBackground(new java.awt.Color(204, 204, 204));
        lblEficSB.setText("0");
        lblEficSB.setPreferredSize(new java.awt.Dimension(50, 50));
        lblEficSB.setSelected(true);
        lblEficSB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblEficSBActionPerformed(evt);
            }
        });

        jButton41.setBackground(new java.awt.Color(255, 255, 102));
        jButton41.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jButton41.setText("A");
        jButton41.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 51), 2));
        jButton41.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton41ActionPerformed(evt);
            }
        });

        lblHrsPagSB.setBackground(new java.awt.Color(204, 204, 204));
        lblHrsPagSB.setText("0");
        lblHrsPagSB.setSelected(true);
        lblHrsPagSB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblHrsPagSBActionPerformed(evt);
            }
        });

        lblEficTotalB.setBackground(new java.awt.Color(204, 204, 204));
        lblEficTotalB.setText("0");
        lblEficTotalB.setPreferredSize(new java.awt.Dimension(50, 50));
        lblEficTotalB.setSelected(true);
        lblEficTotalB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblEficTotalBActionPerformed(evt);
            }
        });

        lblHrsPagSA.setBackground(new java.awt.Color(204, 204, 204));
        lblHrsPagSA.setText("0");
        lblHrsPagSA.setSelected(true);
        lblHrsPagSA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblHrsPagSAActionPerformed(evt);
            }
        });

        lblHrsPagCB.setBackground(new java.awt.Color(204, 204, 204));
        lblHrsPagCB.setText("0");
        lblHrsPagCB.setSelected(true);
        lblHrsPagCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblHrsPagCBActionPerformed(evt);
            }
        });

        jButton42.setBackground(new java.awt.Color(255, 255, 102));
        jButton42.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jButton42.setText("B");
        jButton42.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 51), 2));
        jButton42.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton42ActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(255, 255, 102));
        jButton1.setText("CADENA ");

        btnCadCorte.setBackground(new java.awt.Color(255, 255, 102));
        btnCadCorte.setText("CORTE");
        btnCadCorte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadCorteActionPerformed(evt);
            }
        });

        btnCad3.setBackground(new java.awt.Color(255, 255, 102));
        btnCad3.setText("III");
        btnCad3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCad3ActionPerformed(evt);
            }
        });

        btnCad2.setBackground(new java.awt.Color(255, 255, 102));
        btnCad2.setText("II");
        btnCad2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCad2ActionPerformed(evt);
            }
        });

        btnCad1.setBackground(new java.awt.Color(255, 255, 102));
        btnCad1.setText("I");
        btnCad1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCad1ActionPerformed(evt);
            }
        });

        lblTotalEmbAB.setBackground(new java.awt.Color(204, 204, 204));
        lblTotalEmbAB.setText("0");
        lblTotalEmbAB.setSelected(true);
        lblTotalEmbAB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblTotalEmbABActionPerformed(evt);
            }
        });

        lblEmbTotalA.setBackground(new java.awt.Color(204, 204, 204));
        lblEmbTotalA.setText("0");
        lblEmbTotalA.setSelected(true);
        lblEmbTotalA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblEmbTotalAActionPerformed(evt);
            }
        });

        lblEmbTotalB.setBackground(new java.awt.Color(204, 204, 204));
        lblEmbTotalB.setText("0");
        lblEmbTotalB.setSelected(true);
        lblEmbTotalB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblEmbTotalBActionPerformed(evt);
            }
        });

        lblHrsPagTotalA.setBackground(new java.awt.Color(204, 204, 204));
        lblHrsPagTotalA.setText("0");
        lblHrsPagTotalA.setSelected(true);
        lblHrsPagTotalA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblHrsPagTotalAActionPerformed(evt);
            }
        });

        lblHrsPagTotalB.setBackground(new java.awt.Color(204, 204, 204));
        lblHrsPagTotalB.setText("0");
        lblHrsPagTotalB.setSelected(true);
        lblHrsPagTotalB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblHrsPagTotalBActionPerformed(evt);
            }
        });

        lblEficienciaTurnoAB.setBackground(new java.awt.Color(204, 204, 204));
        lblEficienciaTurnoAB.setText("0");
        lblEficienciaTurnoAB.setSelected(true);
        lblEficienciaTurnoAB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblEficienciaTurnoABActionPerformed(evt);
            }
        });

        lblTotalHrsPagadasAB.setBackground(new java.awt.Color(204, 204, 204));
        lblTotalHrsPagadasAB.setText("0");
        lblTotalHrsPagadasAB.setSelected(true);
        lblTotalHrsPagadasAB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblTotalHrsPagadasABActionPerformed(evt);
            }
        });

        lblEmbSA.setBackground(new java.awt.Color(204, 204, 204));
        lblEmbSA.setText("0");
        lblEmbSA.setSelected(true);
        lblEmbSA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblEmbSAActionPerformed(evt);
            }
        });

        lblEmbIIIB.setBackground(new java.awt.Color(204, 204, 204));
        lblEmbIIIB.setText("0");
        lblEmbIIIB.setSelected(true);
        lblEmbIIIB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblEmbIIIBActionPerformed(evt);
            }
        });

        lblGenteIA.setBackground(new java.awt.Color(204, 204, 204));
        lblGenteIA.setText("0");
        lblGenteIA.setSelected(true);
        lblGenteIA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblGenteIAActionPerformed(evt);
            }
        });

        lblEmbIIB.setBackground(new java.awt.Color(204, 204, 204));
        lblEmbIIB.setText("0");
        lblEmbIIB.setSelected(true);
        lblEmbIIB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblEmbIIBActionPerformed(evt);
            }
        });

        lblEmbSB.setBackground(new java.awt.Color(204, 204, 204));
        lblEmbSB.setText("0");
        lblEmbSB.setSelected(true);
        lblEmbSB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblEmbSBActionPerformed(evt);
            }
        });

        lblEmbCB.setBackground(new java.awt.Color(204, 204, 204));
        lblEmbCB.setText("0");
        lblEmbCB.setSelected(true);
        lblEmbCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblEmbCBActionPerformed(evt);
            }
        });

        jButton31.setBackground(new java.awt.Color(255, 255, 102));
        jButton31.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jButton31.setText("B");
        jButton31.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 51), 2));
        jButton31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton31ActionPerformed(evt);
            }
        });

        jButton32.setBackground(new java.awt.Color(255, 255, 102));
        jButton32.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jButton32.setText("A");
        jButton32.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 51), 2));
        jButton32.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton32ActionPerformed(evt);
            }
        });

        lblHrsPagIIA.setBackground(new java.awt.Color(204, 204, 204));
        lblHrsPagIIA.setText("0");
        lblHrsPagIIA.setSelected(true);
        lblHrsPagIIA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblHrsPagIIAActionPerformed(evt);
            }
        });

        lblHrsPagIIB.setBackground(new java.awt.Color(204, 204, 204));
        lblHrsPagIIB.setText("0");
        lblHrsPagIIB.setSelected(true);
        lblHrsPagIIB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblHrsPagIIBActionPerformed(evt);
            }
        });

        lblHrsPagIIIA.setBackground(new java.awt.Color(204, 204, 204));
        lblHrsPagIIIA.setText("0");
        lblHrsPagIIIA.setSelected(true);
        lblHrsPagIIIA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblHrsPagIIIAActionPerformed(evt);
            }
        });

        lblGenteSA.setBackground(new java.awt.Color(204, 204, 204));
        lblGenteSA.setText("0");
        lblGenteSA.setSelected(true);
        lblGenteSA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblGenteSAActionPerformed(evt);
            }
        });

        lblGenteIIB.setBackground(new java.awt.Color(204, 204, 204));
        lblGenteIIB.setText("0");
        lblGenteIIB.setSelected(true);
        lblGenteIIB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblGenteIIBActionPerformed(evt);
            }
        });

        lblGenteIIIA.setBackground(new java.awt.Color(204, 204, 204));
        lblGenteIIIA.setText("0");
        lblGenteIIIA.setSelected(true);
        lblGenteIIIA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblGenteIIIAActionPerformed(evt);
            }
        });

        lblGenteCA.setBackground(new java.awt.Color(204, 204, 204));
        lblGenteCA.setText("0");
        lblGenteCA.setSelected(true);
        lblGenteCA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblGenteCAActionPerformed(evt);
            }
        });

        lblEficCADIA.setBackground(new java.awt.Color(204, 204, 204));
        lblEficCADIA.setText("0");
        lblEficCADIA.setSelected(true);
        lblEficCADIA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblEficCADIAActionPerformed(evt);
            }
        });

        lblEficCADIIIA.setBackground(new java.awt.Color(204, 204, 204));
        lblEficCADIIIA.setText("0");
        lblEficCADIIIA.setSelected(true);
        lblEficCADIIIA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblEficCADIIIAActionPerformed(evt);
            }
        });

        lblEficIB.setBackground(new java.awt.Color(204, 204, 204));
        lblEficIB.setText("00");
        lblEficIB.setPreferredSize(new java.awt.Dimension(50, 50));
        lblEficIB.setSelected(true);
        lblEficIB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblEficIBActionPerformed(evt);
            }
        });

        jButton73.setBackground(new java.awt.Color(255, 255, 102));
        jButton73.setText("EFICIENCIA X TURNO");
        jButton73.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton73ActionPerformed(evt);
            }
        });

        lblHrsPagIB.setBackground(new java.awt.Color(204, 204, 204));
        lblHrsPagIB.setText("0");
        lblHrsPagIB.setSelected(true);
        lblHrsPagIB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblHrsPagIBActionPerformed(evt);
            }
        });

        lblEficIA.setBackground(new java.awt.Color(204, 204, 204));
        lblEficIA.setText("0");
        lblEficIA.setSelected(true);
        lblEficIA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblEficIAActionPerformed(evt);
            }
        });

        lblEmbIB.setBackground(new java.awt.Color(204, 204, 204));
        lblEmbIB.setText("0");
        lblEmbIB.setSelected(true);
        lblEmbIB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblEmbIBActionPerformed(evt);
            }
        });

        lblHrsPagIA.setBackground(new java.awt.Color(204, 204, 204));
        lblHrsPagIA.setText("0");
        lblHrsPagIA.setSelected(true);
        lblHrsPagIA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblHrsPagIAActionPerformed(evt);
            }
        });

        lblGenteIB.setBackground(new java.awt.Color(204, 204, 204));
        lblGenteIB.setText("0");
        lblGenteIB.setSelected(true);
        lblGenteIB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblGenteIBActionPerformed(evt);
            }
        });

        lblEmbIA.setBackground(new java.awt.Color(204, 204, 204));
        lblEmbIA.setText("0");
        lblEmbIA.setSelected(true);
        lblEmbIA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblEmbIAActionPerformed(evt);
            }
        });

        lblEmbCA.setBackground(new java.awt.Color(204, 204, 204));
        lblEmbCA.setText("0");
        lblEmbCA.setSelected(true);
        lblEmbCA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblEmbCAActionPerformed(evt);
            }
        });

        lblEmbIIIA.setBackground(new java.awt.Color(204, 204, 204));
        lblEmbIIIA.setText("0");
        lblEmbIIIA.setSelected(true);
        lblEmbIIIA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblEmbIIIAActionPerformed(evt);
            }
        });

        lblEmbIIA.setBackground(new java.awt.Color(204, 204, 204));
        lblEmbIIA.setText("0");
        lblEmbIIA.setSelected(true);
        lblEmbIIA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblEmbIIAActionPerformed(evt);
            }
        });

        lblGenteSB.setBackground(new java.awt.Color(204, 204, 204));
        lblGenteSB.setText("0");
        lblGenteSB.setSelected(true);
        lblGenteSB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblGenteSBActionPerformed(evt);
            }
        });

        lblGenteIIIB.setBackground(new java.awt.Color(204, 204, 204));
        lblGenteIIIB.setText("0");
        lblGenteIIIB.setSelected(true);
        lblGenteIIIB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblGenteIIIBActionPerformed(evt);
            }
        });

        lblGenteCB.setBackground(new java.awt.Color(204, 204, 204));
        lblGenteCB.setText("0");
        lblGenteCB.setSelected(true);
        lblGenteCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblGenteCBActionPerformed(evt);
            }
        });

        btnCadSer.setBackground(new java.awt.Color(255, 255, 102));
        btnCadSer.setText("SERVICIO");

        jButton7.setBackground(new java.awt.Color(255, 255, 102));
        jButton7.setText("TOTAL GENTE");

        jButton8.setBackground(new java.awt.Color(255, 255, 102));
        jButton8.setText("EMBARCADAS X DIA");

        lblEficIIIA.setBackground(new java.awt.Color(204, 204, 204));
        lblEficIIIA.setText("0");
        lblEficIIIA.setSelected(true);
        lblEficIIIA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblEficIIIAActionPerformed(evt);
            }
        });

        lblEficIIIB.setBackground(new java.awt.Color(204, 204, 204));
        lblEficIIIB.setText("0");
        lblEficIIIB.setPreferredSize(new java.awt.Dimension(50, 50));
        lblEficIIIB.setSelected(true);
        lblEficIIIB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblEficIIIBActionPerformed(evt);
            }
        });

        lblEficCA.setBackground(new java.awt.Color(204, 204, 204));
        lblEficCA.setText("0");
        lblEficCA.setSelected(true);
        lblEficCA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblEficCAActionPerformed(evt);
            }
        });

        lbleficienciaTurnos.setBackground(new java.awt.Color(204, 204, 204));
        lbleficienciaTurnos.setText("0");
        lbleficienciaTurnos.setSelected(true);
        lbleficienciaTurnos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lbleficienciaTurnosActionPerformed(evt);
            }
        });

        lblEficPlanta.setBackground(new java.awt.Color(204, 204, 204));
        lblEficPlanta.setText("0");
        lblEficPlanta.setSelected(true);
        lblEficPlanta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblEficPlantaActionPerformed(evt);
            }
        });

        lblEficIIA.setBackground(new java.awt.Color(204, 204, 204));
        lblEficIIA.setText("0");
        lblEficIIA.setSelected(true);
        lblEficIIA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblEficIIAActionPerformed(evt);
            }
        });

        jButton52.setBackground(new java.awt.Color(255, 255, 102));
        jButton52.setText("TOTAL A Y B");

        lblEficIIB.setBackground(new java.awt.Color(204, 204, 204));
        lblEficIIB.setText("0");
        lblEficIIB.setPreferredSize(new java.awt.Dimension(50, 50));
        lblEficIIB.setSelected(true);
        lblEficIIB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblEficIIBActionPerformed(evt);
            }
        });

        btnTotalesTurnoo.setBackground(new java.awt.Color(255, 255, 102));
        btnTotalesTurnoo.setText("TOTAL");

        lblEficCADIIA.setBackground(new java.awt.Color(204, 204, 204));
        lblEficCADIIA.setText("0");
        lblEficCADIIA.setSelected(true);
        lblEficCADIIA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblEficCADIIAActionPerformed(evt);
            }
        });

        lblEficCADCA.setBackground(new java.awt.Color(204, 204, 204));
        lblEficCADCA.setText("0");
        lblEficCADCA.setSelected(true);
        lblEficCADCA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblEficCADCAActionPerformed(evt);
            }
        });

        lblEficCADSA.setBackground(new java.awt.Color(204, 204, 204));
        lblEficCADSA.setText("0");
        lblEficCADSA.setSelected(true);
        lblEficCADSA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblEficCADSAActionPerformed(evt);
            }
        });

        lblGenteTA.setBackground(new java.awt.Color(204, 204, 204));
        lblGenteTA.setText("0");
        lblGenteTA.setSelected(true);
        lblGenteTA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblGenteTAActionPerformed(evt);
            }
        });

        lblGenteTB.setBackground(new java.awt.Color(204, 204, 204));
        lblGenteTB.setText("0");
        lblGenteTB.setSelected(true);
        lblGenteTB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblGenteTBActionPerformed(evt);
            }
        });

        lbltotalGenteAB.setBackground(new java.awt.Color(204, 204, 204));
        lbltotalGenteAB.setText("0");
        lbltotalGenteAB.setSelected(true);
        lbltotalGenteAB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lbltotalGenteABActionPerformed(evt);
            }
        });

        jButton14.setBackground(new java.awt.Color(255, 255, 102));
        jButton14.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jButton14.setText("A");
        jButton14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 51), 2));
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        dsa.setBackground(new java.awt.Color(255, 255, 102));
        dsa.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        dsa.setText("A");
        dsa.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 51), 2));
        dsa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dsaActionPerformed(evt);
            }
        });

        lblGenteIIA.setBackground(new java.awt.Color(204, 204, 204));
        lblGenteIIA.setText("0");
        lblGenteIIA.setSelected(true);
        lblGenteIIA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblGenteIIAActionPerformed(evt);
            }
        });

        jButton10.setBackground(new java.awt.Color(255, 255, 102));
        jButton10.setText("EFICIENCIA X TURNO");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton9.setBackground(new java.awt.Color(255, 255, 102));
        jButton9.setText("TOTAL HRS PAGADAS X DIA");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton12.setBackground(new java.awt.Color(255, 255, 102));
        jButton12.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jButton12.setText("B");
        jButton12.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 51), 2));
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        lblEmbIBSDA.setBackground(new java.awt.Color(255, 255, 102));
        lblEmbIBSDA.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        lblEmbIBSDA.setText("B");
        lblEmbIBSDA.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 51, 51), 2));
        lblEmbIBSDA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lblEmbIBSDAActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCad1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCad2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCad3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCadCorte, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCadSer, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnTotalesTurnoo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton52, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(lblGenteIA, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(lblGenteIB, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(lblEmbIA, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(14, 14, 14)
                            .addComponent(lblEmbIB, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(lblHrsPagIA, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(lblHrsPagIB, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(lblEficIA, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(lblEficIB, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addGap(97, 97, 97)
                                            .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(dsa, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGap(16, 16, 16)
                                    .addComponent(lblEmbIBSDA, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jButton32, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jButton31, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(0, 0, Short.MAX_VALUE)
                                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jButton41, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jButton42, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblGenteIIA, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblGenteIIB, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblEmbIIA, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblEmbIIB, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblGenteIIIA, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblGenteIIIB, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblEmbIIIA, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblEmbIIIB, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblGenteCA, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblGenteCB, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblEmbCA, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblEmbCB, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblGenteSA, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblGenteSB, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblEmbSA, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lblEmbSB, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(lbltotalGenteAB, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                        .addComponent(lblGenteTA, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(lblGenteTB, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(lblEmbTotalA, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(lblEmbTotalB, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(lblTotalEmbAB, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(lblHrsPagTotalA, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblHrsPagTotalB, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(lblTotalHrsPagadasAB, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblEficienciaTurnoAB, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(lblEficTotalA, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblEficTotalB, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblHrsPagIIA, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(5, 5, 5)
                                .addComponent(lblHrsPagIIB, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblEficIIA, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblEficIIB, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(8, 8, 8))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(9, 9, 9)
                                        .addComponent(lblHrsPagIIIA, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblHrsPagIIIB, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblEficIIIA, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblEficIIIB, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(9, 9, 9)
                                        .addComponent(lblHrsPagCA, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblHrsPagCB, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblEficCA, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblEficCB, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addComponent(lblHrsPagSA, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(5, 5, 5)
                                        .addComponent(lblHrsPagSB, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblEficSA, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(lblEficSB, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton73, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEficCADIA, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEficCADIIIA, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEficCADIIA, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEficCADCA, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEficCADSA, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbleficienciaTurnos, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEficPlanta, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jButton12, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(dsa, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jButton31, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton32, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton42, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton41, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblEmbIBSDA, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jButton73, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCad1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblGenteIA, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblGenteIB, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEmbIA, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEmbIB, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblHrsPagIB, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblHrsPagIA, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEficIB, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEficIA, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEficCADIA, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnCad2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblGenteIIA, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblGenteIIB, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblEmbIIA, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblEmbIIB, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblHrsPagIIB, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblHrsPagIIA, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblEficCADIIA, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblEficIIB, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblEficIIA, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCad3, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblGenteIIIA, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblGenteIIIB, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEmbIIIA, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEmbIIIB, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblHrsPagIIIB, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblHrsPagIIIA, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEficCADIIIA, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEficIIIB, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEficIIIA, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCadCorte, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblGenteCA, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblGenteCB, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEmbCA, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEmbCB, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblHrsPagCB, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblHrsPagCA, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEficCADCA, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEficCB, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEficCA, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCadSer, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblGenteSA, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblGenteSB, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEmbSA, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEmbSB, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblHrsPagSB, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblHrsPagSA, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEficCADSA, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEficSB, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEficSA, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnTotalesTurnoo, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblGenteTA, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblGenteTB, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jButton52, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lbltotalGenteAB, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblEmbTotalA, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblEmbTotalB, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(lblTotalEmbAB, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblHrsPagTotalA, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblHrsPagTotalB, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lblTotalHrsPagadasAB, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblEficienciaTurnoAB, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbleficienciaTurnos, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblEficTotalB, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblEficTotalA, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblEficPlanta, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(85, 85, 85)
                        .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(147, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(41, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCad1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCad1ActionPerformed
        // TODO add your handling code here:
        DetalleEficiencia DE = new DetalleEficiencia("1");
        DE.setLocationRelativeTo(null);
        DE.setVisible(true);
    }//GEN-LAST:event_btnCad1ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton10ActionPerformed

    private void lblEmbIBSDAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblEmbIBSDAActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblEmbIBSDAActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton12ActionPerformed

    private void dsaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dsaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dsaActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton14ActionPerformed

    private void lblGenteIIAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblGenteIIAActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblGenteIIAActionPerformed

    private void lblGenteIIIAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblGenteIIIAActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblGenteIIIAActionPerformed

    private void lblGenteCAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblGenteCAActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblGenteCAActionPerformed

    private void lblGenteSAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblGenteSAActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblGenteSAActionPerformed

    private void lblGenteIIBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblGenteIIBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblGenteIIBActionPerformed

    private void lblGenteCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblGenteCBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblGenteCBActionPerformed

    private void lblGenteIIIBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblGenteIIIBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblGenteIIIBActionPerformed

    private void lblGenteSBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblGenteSBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblGenteSBActionPerformed

    private void lblEmbIIAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblEmbIIAActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblEmbIIAActionPerformed

    private void lblEmbIIIAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblEmbIIIAActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblEmbIIIAActionPerformed

    private void lblEmbCAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblEmbCAActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblEmbCAActionPerformed

    private void lblEmbSAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblEmbSAActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblEmbSAActionPerformed

    private void lblEmbIIBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblEmbIIBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblEmbIIBActionPerformed

    private void lblEmbIIIBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblEmbIIIBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblEmbIIIBActionPerformed

    private void lblEmbCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblEmbCBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblEmbCBActionPerformed

    private void lblEmbSBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblEmbSBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblEmbSBActionPerformed

    private void jButton31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton31ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton31ActionPerformed

    private void jButton32ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton32ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton32ActionPerformed

    private void lblHrsPagIIAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblHrsPagIIAActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblHrsPagIIAActionPerformed

    private void lblHrsPagIIBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblHrsPagIIBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblHrsPagIIBActionPerformed

    private void lblHrsPagIIIAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblHrsPagIIIAActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblHrsPagIIIAActionPerformed

    private void lblHrsPagIIIBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblHrsPagIIIBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblHrsPagIIIBActionPerformed

    private void lblHrsPagCAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblHrsPagCAActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblHrsPagCAActionPerformed

    private void lblHrsPagCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblHrsPagCBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblHrsPagCBActionPerformed

    private void lblHrsPagSAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblHrsPagSAActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblHrsPagSAActionPerformed

    private void lblHrsPagSBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblHrsPagSBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblHrsPagSBActionPerformed

    private void jButton41ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton41ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton41ActionPerformed

    private void jButton42ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton42ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton42ActionPerformed

    private void lblGenteTAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblGenteTAActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblGenteTAActionPerformed

    private void lblGenteTBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblGenteTBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblGenteTBActionPerformed

    private void lbltotalGenteABActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lbltotalGenteABActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lbltotalGenteABActionPerformed

    private void lblTotalEmbABActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblTotalEmbABActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblTotalEmbABActionPerformed

    private void lblEmbTotalBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblEmbTotalBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblEmbTotalBActionPerformed

    private void lblEmbTotalAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblEmbTotalAActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblEmbTotalAActionPerformed

    private void lblHrsPagTotalBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblHrsPagTotalBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblHrsPagTotalBActionPerformed

    private void lblHrsPagTotalAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblHrsPagTotalAActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblHrsPagTotalAActionPerformed

    private void lblTotalHrsPagadasABActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblTotalHrsPagadasABActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblTotalHrsPagadasABActionPerformed

    private void lblEficienciaTurnoABActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblEficienciaTurnoABActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblEficienciaTurnoABActionPerformed

    private void lblGenteIAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblGenteIAActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblGenteIAActionPerformed

    private void lblGenteIBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblGenteIBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblGenteIBActionPerformed

    private void lblEmbIAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblEmbIAActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblEmbIAActionPerformed

    private void lblEmbIBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblEmbIBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblEmbIBActionPerformed

    private void lblHrsPagIAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblHrsPagIAActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblHrsPagIAActionPerformed

    private void lblHrsPagIBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblHrsPagIBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblHrsPagIBActionPerformed

    private void lblEficIAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblEficIAActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblEficIAActionPerformed

    private void lblEficIBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblEficIBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblEficIBActionPerformed

    private void jButton73ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton73ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton73ActionPerformed

    private void lblEficCADIAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblEficCADIAActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblEficCADIAActionPerformed

    private void lblEficCADIIIAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblEficCADIIIAActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblEficCADIIIAActionPerformed

    private void lblEficCADIIAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblEficCADIIAActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblEficCADIIAActionPerformed

    private void lblEficCADCAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblEficCADCAActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblEficCADCAActionPerformed

    private void lblEficCADSAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblEficCADSAActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblEficCADSAActionPerformed

    private void lbleficienciaTurnosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lbleficienciaTurnosActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lbleficienciaTurnosActionPerformed

    private void lblEficPlantaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblEficPlantaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblEficPlantaActionPerformed

    private void lblEficIIAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblEficIIAActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblEficIIAActionPerformed

    private void lblEficIIBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblEficIIBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblEficIIBActionPerformed

    private void lblEficIIIAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblEficIIIAActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblEficIIIAActionPerformed

    private void lblEficIIIBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblEficIIIBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblEficIIIBActionPerformed

    private void lblEficCAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblEficCAActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblEficCAActionPerformed

    private void lblEficCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblEficCBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblEficCBActionPerformed

    private void lblEficSBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblEficSBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblEficSBActionPerformed

    private void lblEficSAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblEficSAActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblEficSAActionPerformed

    private void lblEficTotalAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblEficTotalAActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblEficTotalAActionPerformed

    private void lblEficTotalBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lblEficTotalBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_lblEficTotalBActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        try {
            // TODO add your handling code here:
            ArrayList<Cadena> Cadenas = new ArrayList<Cadena>();
            Cadenas.add(new Cadena("1", HcIA, HcIB, HrsEmbIA, HrsEmbIB, HrsPagIA, HrsPagIB, EficIA, EficIB, EficCadI));
            Cadenas.add(new Cadena("2", HcIIA, HcIIB, HrsEmbIIA, HrsEmbIIB, HrsPagIIA, HrsPagIIB, EficIIA, EficIIB, EficCadII));
            Cadenas.add(new Cadena("3", HcIIIA, HcIIIB, HrsEmbIIIA, HrsEmbIIIB, HrsPagIIIA, HrsPagIIIB, EficIIIA, EficIIIB, EficCadIII));
            Cadenas.add(new Cadena("Corte", HcCA, HcCB, HrsEmbCA, HrsEmbCB, HrsPagCA, HrsPagCB, EficCA, EficCB, EficCadCorte));
            Cadenas.add(new Cadena("Servicio", HcSA, HcSB, HrsEmbSA, HrsEmbSB, HrsPagSA, HrsPagSB, EficSA, EficSB, EficCadS));
            Cadenas.add(new Cadena("Total", totalHCTurnoA, totalHCTurnoB, totalHrsEMbA, totalHrsEMbB, totalHrsPagA, totalHrsPagB, TotaleficA, TotaleficB, TotalEficiencia));
            Cadena total = new Cadena("TOTALES", TotalHC, 0.0, TotalHrsEMB, 0.0, TotalHrsPag, 0.0, TotalEficiencia, 0.0, TotalEficiencia);
            Excel E = new Excel(Cadenas, total);
        } catch (IOException ex) {
            Logger.getLogger(EficienciaPlanta.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_jButton11ActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
        Principal p = new Principal(Principal.UsuarioLogeado);
        p.setLocationRelativeTo(null);
        p.setVisible(true);
    }//GEN-LAST:event_formWindowClosed

    private void btnCad2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCad2ActionPerformed
        // TODO add your handling code here:
        DetalleEficiencia DE = new DetalleEficiencia("2");
        DE.setLocationRelativeTo(null);
        DE.setVisible(true);
    }//GEN-LAST:event_btnCad2ActionPerformed

    private void btnCad3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCad3ActionPerformed
        // TODO add your handling code here:
        DetalleEficiencia DE = new DetalleEficiencia("3");
        DE.setLocationRelativeTo(null);
        DE.setVisible(true);
    }//GEN-LAST:event_btnCad3ActionPerformed

    private void btnCadCorteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadCorteActionPerformed
        // TODO add your handling code here:
        DetalleEficiencia DE = new DetalleEficiencia("4");
        DE.setLocationRelativeTo(null);
        DE.setVisible(true);
    }//GEN-LAST:event_btnCadCorteActionPerformed
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
            java.util.logging.Logger.getLogger(EficienciaPlanta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EficienciaPlanta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EficienciaPlanta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EficienciaPlanta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EficienciaPlanta().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCad1;
    private javax.swing.JButton btnCad2;
    private javax.swing.JButton btnCad3;
    private javax.swing.JButton btnCadCorte;
    private javax.swing.JButton btnCadSer;
    private javax.swing.JButton btnTotalesTurnoo;
    private javax.swing.JButton dsa;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton31;
    private javax.swing.JButton jButton32;
    private javax.swing.JButton jButton41;
    private javax.swing.JButton jButton42;
    private javax.swing.JButton jButton52;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton73;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton lblEficCA;
    private javax.swing.JButton lblEficCADCA;
    private javax.swing.JButton lblEficCADIA;
    private javax.swing.JButton lblEficCADIIA;
    private javax.swing.JButton lblEficCADIIIA;
    private javax.swing.JButton lblEficCADSA;
    private javax.swing.JButton lblEficCB;
    private javax.swing.JButton lblEficIA;
    private javax.swing.JButton lblEficIB;
    private javax.swing.JButton lblEficIIA;
    private javax.swing.JButton lblEficIIB;
    private javax.swing.JButton lblEficIIIA;
    private javax.swing.JButton lblEficIIIB;
    private javax.swing.JButton lblEficPlanta;
    private javax.swing.JButton lblEficSA;
    private javax.swing.JButton lblEficSB;
    private javax.swing.JButton lblEficTotalA;
    private javax.swing.JButton lblEficTotalB;
    private javax.swing.JButton lblEficienciaTurnoAB;
    private javax.swing.JButton lblEmbCA;
    private javax.swing.JButton lblEmbCB;
    private javax.swing.JButton lblEmbIA;
    private javax.swing.JButton lblEmbIB;
    private javax.swing.JButton lblEmbIBSDA;
    private javax.swing.JButton lblEmbIIA;
    private javax.swing.JButton lblEmbIIB;
    private javax.swing.JButton lblEmbIIIA;
    private javax.swing.JButton lblEmbIIIB;
    private javax.swing.JButton lblEmbSA;
    private javax.swing.JButton lblEmbSB;
    private javax.swing.JButton lblEmbTotalA;
    private javax.swing.JButton lblEmbTotalB;
    private javax.swing.JButton lblGenteCA;
    private javax.swing.JButton lblGenteCB;
    private javax.swing.JButton lblGenteIA;
    private javax.swing.JButton lblGenteIB;
    private javax.swing.JButton lblGenteIIA;
    private javax.swing.JButton lblGenteIIB;
    private javax.swing.JButton lblGenteIIIA;
    private javax.swing.JButton lblGenteIIIB;
    private javax.swing.JButton lblGenteSA;
    private javax.swing.JButton lblGenteSB;
    private javax.swing.JButton lblGenteTA;
    private javax.swing.JButton lblGenteTB;
    private javax.swing.JButton lblHrsPagCA;
    private javax.swing.JButton lblHrsPagCB;
    private javax.swing.JButton lblHrsPagIA;
    private javax.swing.JButton lblHrsPagIB;
    private javax.swing.JButton lblHrsPagIIA;
    private javax.swing.JButton lblHrsPagIIB;
    private javax.swing.JButton lblHrsPagIIIA;
    private javax.swing.JButton lblHrsPagIIIB;
    private javax.swing.JButton lblHrsPagSA;
    private javax.swing.JButton lblHrsPagSB;
    private javax.swing.JButton lblHrsPagTotalA;
    private javax.swing.JButton lblHrsPagTotalB;
    private javax.swing.JButton lblTotalEmbAB;
    private javax.swing.JButton lblTotalHrsPagadasAB;
    private javax.swing.JButton lbleficienciaTurnos;
    private javax.swing.JButton lbltotalGenteAB;
    // End of variables declaration//GEN-END:variables
}
