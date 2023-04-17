package manufactura;

import Clases.Conection;
import static java.awt.image.ImageObserver.WIDTH;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import javax.swing.table.TableModel;

public class layoff_A extends javax.swing.JDialog {

    /**
     * Creates new form interfaz
     */
    private database db = new database();
    private Object[][] dtPersona;

    public layoff_A(java.awt.Frame parent, boolean modal) {
        super(parent, modal);

        try {
            initComponents();

            Principal.cn = new Conection();

        } catch (Exception ex) {
            System.out.println(ex.toString() + " no ahi conection");

        }

        this.setTitle("LAY OFF TURNO A");

        Actualizar_Tabla();

        //oculta columna ID
        jTable1.getColumnModel().getColumn(0).setMaxWidth(0);
        jTable1.getColumnModel().getColumn(0).setMinWidth(0);
        jTable1.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
        jTable1.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);

        jTable1.getColumnModel().getColumn(1).setMaxWidth(110);
        jTable1.getColumnModel().getColumn(1).setMinWidth(110);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(110);
        jTable1.getColumnModel().getColumn(2).setMaxWidth(50);
        jTable1.getColumnModel().getColumn(2).setMinWidth(50);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(50);
        //editor de celdas
        jTable1.getColumnModel().getColumn(1).setCellEditor(new MyTableCellEditor(db, "CODIGO"));
        jTable1.getColumnModel().getColumn(2).setCellEditor(new MyTableCellEditor(db, "LINEA"));
        jTable1.getColumnModel().getColumn(3).setCellEditor(new MyTableCellEditor(db, "T_A_MOD"));
        jTable1.getColumnModel().getColumn(4).setCellEditor(new MyTableCellEditor(db, "LUNES"));
        jTable1.getColumnModel().getColumn(5).setCellEditor(new MyTableCellEditor(db, "MARTES"));
        jTable1.getColumnModel().getColumn(6).setCellEditor(new MyTableCellEditor(db, "MIERCOLES"));
        jTable1.getColumnModel().getColumn(7).setCellEditor(new MyTableCellEditor(db, "JUEVES"));
        jTable1.getColumnModel().getColumn(8).setCellEditor(new MyTableCellEditor(db, "VIERNES"));
        jTable1.getColumnModel().getColumn(9).setCellEditor(new MyTableCellEditor(db, "LUNES2"));
        jTable1.getColumnModel().getColumn(10).setCellEditor(new MyTableCellEditor(db, "MARTES2"));
        jTable1.getColumnModel().getColumn(11).setCellEditor(new MyTableCellEditor(db, "MIERCOLES2"));
        jTable1.getColumnModel().getColumn(12).setCellEditor(new MyTableCellEditor(db, "JUEVES2"));
        jTable1.getColumnModel().getColumn(13).setCellEditor(new MyTableCellEditor(db, "VIERNES2"));
    }

    public void exportarjTable(JTable tabla, String ficheroXLS) throws IOException {
        TableModel modelo = tabla.getModel();
        FileWriter fichero = new FileWriter(ficheroXLS);

        for (int i = 0; i < modelo.getColumnCount(); i++) {
            fichero.write(modelo.getColumnName(i) + "\t");
        }
        fichero.write("\n");
        for (int i = 0; i < modelo.getRowCount(); i++) {
            for (int j = 0; j < modelo.getColumnCount(); j++) {
                fichero.write(modelo.getValueAt(i, j).toString() + "\t");
            }
            fichero.write("\n");
        }
        fichero.close();

    }

    private void Actualizar_Tabla() {

        //actualiza los datos de la tabla realizando una consulta a la base de datos
        String[] columNames = {"IDCODIGO", "CODIGO", "LINEA", "T_A_MOD", "LUNES", "MARTES", "MIERCOLES", "JUEVES", "VIERNES", "LUNES", "MARTES", "MIERCOLES", "JUEVES", "VIERNES"};
        dtPersona = db.Select_Persona();
        // se colocan los datos en la tabla
        DefaultTableModel datos = new DefaultTableModel(dtPersona, columNames);
        jTable1.setModel(datos);
    }

    public void TOTAL_TA() {
        if ((JOptionPane.showConfirmDialog(null, "Esta seguro que desea actualizar?", "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)) {
            try {
                //HC NECESARIO
                ResultSet rs = Principal.cn.GetConsulta("SELECT sum(T_A_MOD) from manufactura.lay_off_a");
                ResultSet rs2 = Principal.cn.GetConsulta("SELECT  sum(T_A_MOD)AS TOTAL from manufactura.lay_off_a where IDCODIGO between '0' and '57' ");
                ResultSet rs3 = Principal.cn.GetConsulta("SELECT  sum(T_A_MOD)AS TOTAL from manufactura.lay_off_a where IDCODIGO between '59' and '89' ");

                //LUNES
                ResultSet rs4 = Principal.cn.GetConsulta("SELECT  sum(T_A_MOD*LUNES)AS TOTAL from manufactura.lay_off_a where idcodigo between '0' and '57'");
                ResultSet rs5 = Principal.cn.GetConsulta("SELECT  sum(T_A_MOD*LUNES)AS TOTAL from manufactura.lay_off_a where idcodigo between '59' and '89'");
                ResultSet rs6 = Principal.cn.GetConsulta("SELECT  sum(T_A_MOD*LUNES)AS TOTAL from manufactura.lay_off_a");
                //MARTES
                ResultSet rs7 = Principal.cn.GetConsulta("SELECT  sum(T_A_MOD*MARTES)AS TOTAL from manufactura.lay_off_a where idcodigo between '0' and '57'");
                ResultSet rs8 = Principal.cn.GetConsulta("SELECT  sum(T_A_MOD*MARTES)AS TOTAL from manufactura.lay_off_a where idcodigo between '59' and '89'");
                ResultSet rs9 = Principal.cn.GetConsulta("SELECT  sum(T_A_MOD*MARTES)AS TOTAL from manufactura.lay_off_a");

                //MIERCOLES
                ResultSet rs10 = Principal.cn.GetConsulta("SELECT  sum(T_A_MOD*MIERCOLES)AS TOTAL from manufactura.lay_off_a where idcodigo between '0' and '57'");
                ResultSet rs11 = Principal.cn.GetConsulta("SELECT  sum(T_A_MOD*MIERCOLES)AS TOTAL from manufactura.lay_off_a where idcodigo between '59' and '89'");
                ResultSet rs12 = Principal.cn.GetConsulta("SELECT  sum(T_A_MOD*MIERCOLES)AS TOTAL from manufactura.lay_off_a");

                ////JUEVES
                ResultSet rs13 = Principal.cn.GetConsulta("SELECT  sum(T_A_MOD*JUEVES)AS TOTAL from manufactura.lay_off_a where idcodigo between '0' and '57'");
                ResultSet rs14 = Principal.cn.GetConsulta("SELECT  sum(T_A_MOD*JUEVES)AS TOTAL from manufactura.lay_off_a where idcodigo between '59' and '89'");
                ResultSet rs15 = Principal.cn.GetConsulta("SELECT  sum(T_A_MOD*JUEVES)AS TOTAL from manufactura.lay_off_a");

                ////VIERNES
                ResultSet rs16 = Principal.cn.GetConsulta("SELECT  sum(T_A_MOD*VIERNES)AS TOTAL from manufactura.lay_off_a where idcodigo between '0' and '57'");
                ResultSet rs17 = Principal.cn.GetConsulta("SELECT  sum(T_A_MOD*VIERNES)AS TOTAL from manufactura.lay_off_a where idcodigo between '59' and '89'");
                ResultSet rs18 = Principal.cn.GetConsulta("SELECT  sum(T_A_MOD*VIERNES)AS TOTAL from manufactura.lay_off_a");

                //LUNES2
                ResultSet rs19 = Principal.cn.GetConsulta("SELECT  sum(T_A_MOD*LUNES2)AS TOTAL from manufactura.lay_off_a where idcodigo between '0' and '57'");
                ResultSet rs20 = Principal.cn.GetConsulta("SELECT  sum(T_A_MOD*LUNES2)AS TOTAL from manufactura.lay_off_a where idcodigo between '59' and '89'");
                ResultSet rs21 = Principal.cn.GetConsulta("SELECT  sum(T_A_MOD*LUNES2)AS TOTAL from manufactura.lay_off_a");

                //MARTES2
                ResultSet rs22 = Principal.cn.GetConsulta("SELECT  sum(T_A_MOD*MARTES2)AS TOTAL from manufactura.lay_off_a where idcodigo between '0' and '57'");
                ResultSet rs23 = Principal.cn.GetConsulta("SELECT  sum(T_A_MOD*MARTES2)AS TOTAL from manufactura.lay_off_a where idcodigo between '59' and '89'");
                ResultSet rs24 = Principal.cn.GetConsulta("SELECT  sum(T_A_MOD*MARTES2)AS TOTAL from manufactura.lay_off_a");

                //MIERCOLES2
                ResultSet rs25 = Principal.cn.GetConsulta("SELECT  sum(T_A_MOD*MIERCOLES2)AS TOTAL from manufactura.lay_off_a where idcodigo between '0' and '57'");
                ResultSet rs26 = Principal.cn.GetConsulta("SELECT  sum(T_A_MOD*MIERCOLES2)AS TOTAL from manufactura.lay_off_a where idcodigo between '59' and '89'");
                ResultSet rs27 = Principal.cn.GetConsulta("SELECT  sum(T_A_MOD*MIERCOLES2)AS TOTAL from manufactura.lay_off_a");

                ////JUEVES2
                ResultSet rs28 = Principal.cn.GetConsulta("SELECT  sum(T_A_MOD*JUEVES2)AS TOTAL from manufactura.lay_off_a where idcodigo between '0' and '57'");
                ResultSet rs29 = Principal.cn.GetConsulta("SELECT  sum(T_A_MOD*JUEVES2)AS TOTAL from manufactura.lay_off_a where idcodigo between '59' and '89'");
                ResultSet rs30 = Principal.cn.GetConsulta("SELECT  sum(T_A_MOD*JUEVES2)AS TOTAL from manufactura.lay_off_a");

                ////VIERNES2
                ResultSet rs31 = Principal.cn.GetConsulta("SELECT  sum(T_A_MOD*VIERNES2)AS TOTAL from manufactura.lay_off_a where idcodigo between '0' and '57'");
                ResultSet rs32 = Principal.cn.GetConsulta("SELECT  sum(T_A_MOD*VIERNES2)AS TOTAL from manufactura.lay_off_a where idcodigo between '59' and '89'");
                ResultSet rs33 = Principal.cn.GetConsulta("SELECT  sum(T_A_MOD*VIERNES2)AS TOTAL from manufactura.lay_off_a");

                if (rs.next() && rs2.next() && rs3.next() && rs4.next() && rs5.next() && rs6.next() && rs7.next() && rs8.next() && rs9.next() && rs10.next() && rs11.next() && rs12.next() && rs13.next() && rs14.next() && rs15.next() && rs16.next() && rs17.next()
                        && rs18.next() && rs19.next() && rs20.next() && rs21.next() && rs22.next() && rs23.next() && rs24.next() && rs25.next() && rs26.next() && rs27.next() && rs28.next() && rs29.next() && rs30.next() && rs31.next() && rs32.next() && rs33.next()) {
                    //HC NECESARIO   
                    lbl_totalTurno.setText(rs.getString(WIDTH));
                    dpo_25962.setText(rs2.getString(WIDTH));
                    dpto_25963.setText(rs3.getString(WIDTH));

                    //LUNES
                    dpo_62.setText(rs4.getString(WIDTH));
                    dpto_63.setText(rs5.getString(WIDTH));
                    total_tur.setText(rs6.getString(WIDTH));

                    //MARTES
                    dep62ma_loff.setText(rs7.getString(WIDTH));
                    dep63ma_loff.setText(rs8.getString(WIDTH));
                    Tdep_ma.setText(rs9.getString(WIDTH));

                    //MIERCOLES
                    dep62mi_loff.setText(rs10.getString(WIDTH));
                    dep63mi_loff.setText(rs11.getString(WIDTH));
                    Tdep_mi.setText(rs12.getString(WIDTH));

                    //JUEVES
                    dep62jue_loff.setText(rs13.getString(WIDTH));
                    dep63jue_loff.setText(rs14.getString(WIDTH));
                    Tdep_jue.setText(rs15.getString(WIDTH));

                    //VIERNES
                    dep62vie_loff.setText(rs16.getString(WIDTH));
                    dep63vie_loff.setText(rs17.getString(WIDTH));
                    Tdep_vie.setText(rs18.getString(WIDTH));

                    //LUNES2
                    dpo_62_2.setText(rs19.getString(WIDTH));
                    dpto_63_2.setText(rs20.getString(WIDTH));
                    total_tur_2.setText(rs21.getString(WIDTH));

                    //MARTES2
                    dep62ma_loff2.setText(rs22.getString(WIDTH));
                    dep63ma_loff2.setText(rs23.getString(WIDTH));
                    Tdep_ma2.setText(rs24.getString(WIDTH));

                    //MIERCOLES
                    dep62mi_loff2.setText(rs25.getString(WIDTH));
                    dep63mi_loff2.setText(rs26.getString(WIDTH));
                    Tdep_mi2.setText(rs27.getString(WIDTH));

                    //JUEVES
                    dep62jue_loff2.setText(rs28.getString(WIDTH));
                    dep63jue_loff2.setText(rs29.getString(WIDTH));
                    Tdep_jue2.setText(rs30.getString(WIDTH));

                    //VIERNES
                    dep62vie_loff2.setText(rs31.getString(WIDTH));
                    dep63vie_loff2.setText(rs32.getString(WIDTH));
                    Tdep_vie2.setText(rs33.getString(WIDTH));

                    //   
                    //saca valor de HC laborando en depa_25962    lunes 
                    String val1 = dpo_25962.getText();
                    String val2 = dpo_62.getText();
                    int x = Integer.parseInt(val1);
                    int y = Integer.parseInt(val2);
                    int resta = x - y;
                    String resultado = String.valueOf(resta);
                    depa_62.setText(resultado);

                    //saca valor de HC laborando en depa_25963 lunes
                    String val3 = dpto_25963.getText();
                    String val4 = dpto_63.getText();
                    int a = Integer.parseInt(val3);
                    int b = Integer.parseInt(val4);
                    int resta2 = a - b;
                    String resultado2 = String.valueOf(resta2);
                    depa_63.setText(resultado2);

                    //saca TOTAL de HC laborando lunes
                    String val5 = depa_62.getText();
                    String val6 = depa_63.getText();
                    int dep62 = Integer.parseInt(val5);
                    int dep63 = Integer.parseInt(val6);
                    int suma = dep62 + dep63;
                    String resultado3 = String.valueOf(suma);
                    total_turno.setText(resultado3);

                    //saca valor de HC laborando en depa_25962    martes
                    String val7 = dpo_25962.getText();
                    String val8 = dep62ma_loff.getText();
                    int x1 = Integer.parseInt(val7);
                    int y1 = Integer.parseInt(val8);
                    int resta3 = x1 - y1;
                    String resultado4 = String.valueOf(resta3);
                    depa62_marts.setText(resultado4);

                    String val9 = dpto_25963.getText();
                    String val10 = dep63ma_loff.getText();
                    int x2 = Integer.parseInt(val9);
                    int y2 = Integer.parseInt(val10);
                    int resta4 = x2 - y2;
                    String resultado5 = String.valueOf(resta4);
                    depa63_marts.setText(resultado5);

                    String val11 = depa62_marts.getText();
                    String val12 = depa63_marts.getText();
                    int x3 = Integer.parseInt(val11);
                    int y3 = Integer.parseInt(val12);
                    int suma2 = x3 + y3;
                    String resultado6 = String.valueOf(suma2);
                    total_turnoMarts.setText(resultado6);

                    //saca valor de HC laborando en depa_25962    miercoles
                    String val13 = dpo_25962.getText();
                    String val14 = dep62mi_loff.getText();
                    int x4 = Integer.parseInt(val13);
                    int y4 = Integer.parseInt(val14);
                    int resta5 = x4 - y4;
                    String resultado7 = String.valueOf(resta5);
                    depa62_mierc.setText(resultado7);

                    String val16 = dpto_25963.getText();
                    String val17 = dep63mi_loff.getText();
                    int x5 = Integer.parseInt(val16);
                    int y5 = Integer.parseInt(val17);
                    int resta6 = x5 - y5;
                    String resultado8 = String.valueOf(resta6);
                    depa63_mierc.setText(resultado8);

                    String val18 = depa62_mierc.getText();
                    String val19 = depa63_mierc.getText();
                    int x6 = Integer.parseInt(val18);
                    int y6 = Integer.parseInt(val19);
                    int suma3 = x6 + y6;
                    String resultado9 = String.valueOf(suma3);
                    total_turnoMierc.setText(resultado9);

                    //saca valor de HC laborando en depa_25962    jueves
                    String val20 = dpo_25962.getText();
                    String val21 = dep62jue_loff.getText();
                    int x7 = Integer.parseInt(val20);
                    int y7 = Integer.parseInt(val21);
                    int resta7 = x7 - y7;
                    String resultado10 = String.valueOf(resta7);
                    depa62_jue.setText(resultado10);

                    String val22 = dpto_25963.getText();
                    String val23 = dep63jue_loff.getText();
                    int x8 = Integer.parseInt(val22);
                    int y8 = Integer.parseInt(val23);
                    int resta8 = x8 - y8;
                    String resultado11 = String.valueOf(resta8);
                    depa63_jue.setText(resultado11);

                    String val24 = depa62_jue.getText();
                    String val25 = depa63_jue.getText();
                    int x9 = Integer.parseInt(val24);
                    int y9 = Integer.parseInt(val25);
                    int suma4 = x9 + y9;
                    String resultado12 = String.valueOf(suma4);
                    total_turnojue.setText(resultado12);

                    //saca valor de HC laborando en depa_25962   viernes
                    String val26 = dpo_25962.getText();
                    String val27 = dep62vie_loff.getText();
                    int x10 = Integer.parseInt(val26);
                    int y10 = Integer.parseInt(val27);
                    int resta9 = x10 - y10;
                    String resultado13 = String.valueOf(resta9);
                    depa62_vie.setText(resultado13);

                    String val28 = dpto_25963.getText();
                    String val29 = dep63vie_loff.getText();
                    int x11 = Integer.parseInt(val28);
                    int y11 = Integer.parseInt(val29);
                    int resta10 = x11 - y11;
                    String resultado14 = String.valueOf(resta10);
                    depa63_vie.setText(resultado14);

                    String val30 = depa62_vie.getText();
                    String val31 = depa63_vie.getText();
                    int x12 = Integer.parseInt(val30);
                    int y12 = Integer.parseInt(val31);
                    int suma5 = x12 + y12;
                    String resultado15 = String.valueOf(suma5);
                    total_turnovie.setText(resultado15);

                    //saca valor de HC laborando en depa_25962    lunes2
                    String val32 = dpo_25962.getText();
                    String val33 = dpo_62_2.getText();
                    int x13 = Integer.parseInt(val32);
                    int y13 = Integer.parseInt(val33);
                    int resta11 = x13 - y13;
                    String resultado16 = String.valueOf(resta11);
                    depa_62_2.setText(resultado16);

                    //saca valor de HC laborando en depa_25963 lunes2
                    String val34 = dpto_25963.getText();
                    String val35 = dpto_63_2.getText();
                    int x14 = Integer.parseInt(val34);
                    int y14 = Integer.parseInt(val35);
                    int resta12 = x14 - y14;
                    String resultado17 = String.valueOf(resta12);
                    depa_63_2.setText(resultado17);

                    //saca TOTAL de HC laborando lunes2
                    String val36 = depa_62_2.getText();
                    String val37 = depa_63_2.getText();
                    int x15 = Integer.parseInt(val36);
                    int y15 = Integer.parseInt(val37);
                    int suma6 = x15 + y15;
                    String resultado18 = String.valueOf(suma6);
                    total_turno_2.setText(resultado18);

                    //saca valor de HC laborando en depa_25962    martes
                    String val38 = dpo_25962.getText();
                    String val39 = dep62ma_loff2.getText();
                    int x16 = Integer.parseInt(val38);
                    int y16 = Integer.parseInt(val39);
                    int resta13 = x16 - y16;
                    String resultado19 = String.valueOf(resta13);
                    depa62_marts2.setText(resultado19);

                    String val40 = dpto_25963.getText();
                    String val41 = dep63ma_loff2.getText();
                    int x17 = Integer.parseInt(val40);
                    int y17 = Integer.parseInt(val41);
                    int resta14 = x17 - y17;
                    String resultado20 = String.valueOf(resta14);
                    depa63_marts2.setText(resultado20);

                    String val42 = depa62_marts2.getText();
                    String val43 = depa63_marts2.getText();
                    int x18 = Integer.parseInt(val42);
                    int y18 = Integer.parseInt(val43);
                    int suma7 = x18 + y18;
                    String resultado21 = String.valueOf(suma7);
                    total_turnoMarts2.setText(resultado21);

                    //saca valor de HC laborando en depa_25962    miercoles
                    String val144 = dpo_25962.getText();
                    String val145 = dep62mi_loff2.getText();
                    int x19 = Integer.parseInt(val144);
                    int y19 = Integer.parseInt(val145);
                    int resta15 = x19 - y19;
                    String resultado22 = String.valueOf(resta15);
                    depa62_mierc2.setText(resultado22);

                    String val46 = dpto_25963.getText();
                    String val47 = dep63mi_loff2.getText();
                    int x20 = Integer.parseInt(val46);
                    int y20 = Integer.parseInt(val47);
                    int resta16 = x20 - y20;
                    String resultado23 = String.valueOf(resta16);
                    depa63_mierc2.setText(resultado23);

                    String val48 = depa62_mierc2.getText();
                    String val49 = depa63_mierc2.getText();
                    int x21 = Integer.parseInt(val48);
                    int y21 = Integer.parseInt(val49);
                    int suma8 = x21 + y21;
                    String resultado24 = String.valueOf(suma8);
                    total_turnoMierc2.setText(resultado24);

                    //saca valor de HC laborando en depa_25962    jueves
                    String val50 = dpo_25962.getText();
                    String val51 = dep62jue_loff2.getText();
                    int x22 = Integer.parseInt(val50);
                    int y22 = Integer.parseInt(val51);
                    int resta17 = x22 - y22;
                    String resultado25 = String.valueOf(resta17);
                    depa62_jue2.setText(resultado25);

                    String val52 = dpto_25963.getText();
                    String val53 = dep63jue_loff2.getText();
                    int x23 = Integer.parseInt(val52);
                    int y23 = Integer.parseInt(val53);
                    int resta18 = x23 - y23;
                    String resultado26 = String.valueOf(resta18);
                    depa63_jue2.setText(resultado26);

                    String val54 = depa62_jue2.getText();
                    String val55 = depa63_jue2.getText();
                    int x24 = Integer.parseInt(val54);
                    int y24 = Integer.parseInt(val55);
                    int suma18 = x24 + y24;
                    String resultado27 = String.valueOf(suma18);
                    total_turnojue2.setText(resultado27);

                    //saca valor de HC laborando en depa_25962   viernes
                    String val56 = dpo_25962.getText();
                    String val57 = dep62vie_loff2.getText();
                    int x25 = Integer.parseInt(val56);
                    int y25 = Integer.parseInt(val57);
                    int resta19 = x25 - y25;
                    String resultado28 = String.valueOf(resta19);
                    depa62_vie2.setText(resultado28);

                    String val58 = dpto_25963.getText();
                    String val59 = dep63vie_loff2.getText();
                    int x26 = Integer.parseInt(val58);
                    int y26 = Integer.parseInt(val59);
                    int resta20 = x26 - y26;
                    String resultado29 = String.valueOf(resta20);
                    depa63_vie2.setText(resultado29);

                    String val60 = depa62_vie2.getText();
                    String val61 = depa63_vie2.getText();
                    int x27 = Integer.parseInt(val60);
                    int y27 = Integer.parseInt(val61);
                    int suma19 = x27 + y27;
                    String resultado30 = String.valueOf(suma19);
                    total_turnovie2.setText(resultado30);
                }
            } catch (Exception e) {
                System.out.println(e.toString());
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
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        lbl_totalTurno = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        dpto_25963 = new javax.swing.JLabel();
        dpo_25962 = new javax.swing.JLabel();
        btnActualizar = new javax.swing.JButton();
        jPanel29 = new javax.swing.JPanel();
        jLabel128 = new javax.swing.JLabel();
        jLabel129 = new javax.swing.JLabel();
        jLabel130 = new javax.swing.JLabel();
        jPanel41 = new javax.swing.JPanel();
        jLabel164 = new javax.swing.JLabel();
        jLabel165 = new javax.swing.JLabel();
        jLabel166 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        depa_63 = new javax.swing.JLabel();
        depa_62 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        total_turno = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        dpo_62 = new javax.swing.JLabel();
        total_tur = new javax.swing.JLabel();
        dpto_63 = new javax.swing.JLabel();
        jLabel146 = new javax.swing.JLabel();
        jPanel42 = new javax.swing.JPanel();
        jLabel167 = new javax.swing.JLabel();
        jLabel168 = new javax.swing.JLabel();
        jLabel169 = new javax.swing.JLabel();
        jPanel43 = new javax.swing.JPanel();
        jLabel170 = new javax.swing.JLabel();
        jLabel171 = new javax.swing.JLabel();
        jLabel172 = new javax.swing.JLabel();
        jPanel44 = new javax.swing.JPanel();
        jLabel173 = new javax.swing.JLabel();
        jLabel174 = new javax.swing.JLabel();
        jLabel175 = new javax.swing.JLabel();
        jPanel45 = new javax.swing.JPanel();
        jLabel176 = new javax.swing.JLabel();
        jLabel177 = new javax.swing.JLabel();
        jLabel178 = new javax.swing.JLabel();
        jPanel18 = new javax.swing.JPanel();
        jLabel73 = new javax.swing.JLabel();
        jLabel74 = new javax.swing.JLabel();
        jLabel75 = new javax.swing.JLabel();
        jLabel76 = new javax.swing.JLabel();
        dep63vie_loff = new javax.swing.JLabel();
        dep62vie_loff = new javax.swing.JLabel();
        jLabel90 = new javax.swing.JLabel();
        Tdep_vie = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jLabel68 = new javax.swing.JLabel();
        jLabel69 = new javax.swing.JLabel();
        jLabel70 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        dep63jue_loff = new javax.swing.JLabel();
        dep62jue_loff = new javax.swing.JLabel();
        jLabel85 = new javax.swing.JLabel();
        Tdep_jue = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        dep63mi_loff = new javax.swing.JLabel();
        dep62mi_loff = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        Tdep_mi = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        jLabel78 = new javax.swing.JLabel();
        jLabel79 = new javax.swing.JLabel();
        jLabel80 = new javax.swing.JLabel();
        jLabel81 = new javax.swing.JLabel();
        dep63ma_loff = new javax.swing.JLabel();
        dep62ma_loff = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        Tdep_ma = new javax.swing.JLabel();
        jPanel25 = new javax.swing.JPanel();
        jLabel108 = new javax.swing.JLabel();
        jLabel109 = new javax.swing.JLabel();
        jLabel110 = new javax.swing.JLabel();
        jLabel111 = new javax.swing.JLabel();
        depa63_marts = new javax.swing.JLabel();
        depa62_marts = new javax.swing.JLabel();
        jLabel125 = new javax.swing.JLabel();
        total_turnoMarts = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jLabel58 = new javax.swing.JLabel();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        depa63_mierc = new javax.swing.JLabel();
        depa62_mierc = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        total_turnoMierc = new javax.swing.JLabel();
        jPanel23 = new javax.swing.JPanel();
        jLabel98 = new javax.swing.JLabel();
        jLabel99 = new javax.swing.JLabel();
        jLabel100 = new javax.swing.JLabel();
        jLabel101 = new javax.swing.JLabel();
        depa63_jue = new javax.swing.JLabel();
        depa62_jue = new javax.swing.JLabel();
        jLabel115 = new javax.swing.JLabel();
        total_turnojue = new javax.swing.JLabel();
        jPanel24 = new javax.swing.JPanel();
        jLabel103 = new javax.swing.JLabel();
        jLabel104 = new javax.swing.JLabel();
        jLabel105 = new javax.swing.JLabel();
        jLabel106 = new javax.swing.JLabel();
        depa63_vie = new javax.swing.JLabel();
        depa62_vie = new javax.swing.JLabel();
        jLabel120 = new javax.swing.JLabel();
        total_turnovie = new javax.swing.JLabel();
        jPanel35 = new javax.swing.JPanel();
        jPanel22 = new javax.swing.JPanel();
        jLabel93 = new javax.swing.JLabel();
        jLabel94 = new javax.swing.JLabel();
        jLabel95 = new javax.swing.JLabel();
        jLabel96 = new javax.swing.JLabel();
        jLabel97 = new javax.swing.JLabel();
        depa_63_2 = new javax.swing.JLabel();
        depa_62_2 = new javax.swing.JLabel();
        jLabel147 = new javax.swing.JLabel();
        total_turno_2 = new javax.swing.JLabel();
        jPanel36 = new javax.swing.JPanel();
        jLabel148 = new javax.swing.JLabel();
        jLabel149 = new javax.swing.JLabel();
        jLabel150 = new javax.swing.JLabel();
        jLabel151 = new javax.swing.JLabel();
        depa63_marts2 = new javax.swing.JLabel();
        depa62_marts2 = new javax.swing.JLabel();
        jLabel152 = new javax.swing.JLabel();
        total_turnoMarts2 = new javax.swing.JLabel();
        jPanel37 = new javax.swing.JPanel();
        jLabel153 = new javax.swing.JLabel();
        jLabel154 = new javax.swing.JLabel();
        jLabel155 = new javax.swing.JLabel();
        jLabel156 = new javax.swing.JLabel();
        dep63ma_loff2 = new javax.swing.JLabel();
        dep62ma_loff2 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        Tdep_ma2 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel26 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel157 = new javax.swing.JLabel();
        jLabel315 = new javax.swing.JLabel();
        dpo_62_2 = new javax.swing.JLabel();
        total_tur_2 = new javax.swing.JLabel();
        dpto_63_2 = new javax.swing.JLabel();
        jLabel316 = new javax.swing.JLabel();
        jPanel85 = new javax.swing.JPanel();
        jLabel341 = new javax.swing.JLabel();
        jLabel342 = new javax.swing.JLabel();
        jLabel343 = new javax.swing.JLabel();
        jLabel344 = new javax.swing.JLabel();
        depa63_mierc2 = new javax.swing.JLabel();
        depa62_mierc2 = new javax.swing.JLabel();
        jLabel345 = new javax.swing.JLabel();
        total_turnoMierc2 = new javax.swing.JLabel();
        jPanel86 = new javax.swing.JPanel();
        jLabel346 = new javax.swing.JLabel();
        jLabel347 = new javax.swing.JLabel();
        jLabel348 = new javax.swing.JLabel();
        jLabel349 = new javax.swing.JLabel();
        dep63mi_loff2 = new javax.swing.JLabel();
        dep62mi_loff2 = new javax.swing.JLabel();
        jLabel350 = new javax.swing.JLabel();
        Tdep_mi2 = new javax.swing.JLabel();
        jPanel89 = new javax.swing.JPanel();
        jLabel357 = new javax.swing.JLabel();
        jLabel358 = new javax.swing.JLabel();
        jLabel359 = new javax.swing.JLabel();
        jLabel360 = new javax.swing.JLabel();
        depa63_jue2 = new javax.swing.JLabel();
        depa62_jue2 = new javax.swing.JLabel();
        jLabel361 = new javax.swing.JLabel();
        total_turnojue2 = new javax.swing.JLabel();
        jPanel90 = new javax.swing.JPanel();
        jLabel362 = new javax.swing.JLabel();
        jLabel363 = new javax.swing.JLabel();
        jLabel364 = new javax.swing.JLabel();
        jLabel365 = new javax.swing.JLabel();
        depa63_vie2 = new javax.swing.JLabel();
        depa62_vie2 = new javax.swing.JLabel();
        jLabel366 = new javax.swing.JLabel();
        total_turnovie2 = new javax.swing.JLabel();
        jPanel91 = new javax.swing.JPanel();
        jLabel367 = new javax.swing.JLabel();
        jLabel368 = new javax.swing.JLabel();
        jLabel369 = new javax.swing.JLabel();
        jLabel370 = new javax.swing.JLabel();
        dep63vie_loff2 = new javax.swing.JLabel();
        dep62vie_loff2 = new javax.swing.JLabel();
        jLabel371 = new javax.swing.JLabel();
        Tdep_vie2 = new javax.swing.JLabel();
        jPanel92 = new javax.swing.JPanel();
        jLabel372 = new javax.swing.JLabel();
        jLabel373 = new javax.swing.JLabel();
        jLabel374 = new javax.swing.JLabel();
        jLabel375 = new javax.swing.JLabel();
        dep63jue_loff2 = new javax.swing.JLabel();
        dep62jue_loff2 = new javax.swing.JLabel();
        jLabel376 = new javax.swing.JLabel();
        Tdep_jue2 = new javax.swing.JLabel();
        jPanel46 = new javax.swing.JPanel();
        jLabel179 = new javax.swing.JLabel();
        jLabel180 = new javax.swing.JLabel();
        jLabel181 = new javax.swing.JLabel();
        jPanel47 = new javax.swing.JPanel();
        jLabel182 = new javax.swing.JLabel();
        jLabel183 = new javax.swing.JLabel();
        jLabel184 = new javax.swing.JLabel();
        jPanel48 = new javax.swing.JPanel();
        jLabel185 = new javax.swing.JLabel();
        jLabel186 = new javax.swing.JLabel();
        jLabel187 = new javax.swing.JLabel();
        jPanel49 = new javax.swing.JPanel();
        jLabel188 = new javax.swing.JLabel();
        jLabel189 = new javax.swing.JLabel();
        jLabel190 = new javax.swing.JLabel();
        jPanel50 = new javax.swing.JPanel();
        jLabel191 = new javax.swing.JLabel();
        jLabel192 = new javax.swing.JLabel();
        jLabel193 = new javax.swing.JLabel();
        jPanel51 = new javax.swing.JPanel();
        jLabel194 = new javax.swing.JLabel();
        jLabel195 = new javax.swing.JLabel();
        jLabel196 = new javax.swing.JLabel();
        jPanel52 = new javax.swing.JPanel();
        jLabel197 = new javax.swing.JLabel();
        jLabel198 = new javax.swing.JLabel();
        jLabel199 = new javax.swing.JLabel();
        jPanel53 = new javax.swing.JPanel();
        jLabel200 = new javax.swing.JLabel();
        jLabel201 = new javax.swing.JLabel();
        jLabel202 = new javax.swing.JLabel();
        jPanel54 = new javax.swing.JPanel();
        jLabel203 = new javax.swing.JLabel();
        jLabel204 = new javax.swing.JLabel();
        jLabel205 = new javax.swing.JLabel();
        jPanel55 = new javax.swing.JPanel();
        jLabel206 = new javax.swing.JLabel();
        jLabel207 = new javax.swing.JLabel();
        jLabel208 = new javax.swing.JLabel();
        jPanel56 = new javax.swing.JPanel();
        jLabel209 = new javax.swing.JLabel();
        jLabel210 = new javax.swing.JLabel();
        jLabel211 = new javax.swing.JLabel();
        jPanel57 = new javax.swing.JPanel();
        jLabel212 = new javax.swing.JLabel();
        jLabel213 = new javax.swing.JLabel();
        jLabel214 = new javax.swing.JLabel();
        jPanel58 = new javax.swing.JPanel();
        jLabel215 = new javax.swing.JLabel();
        jLabel216 = new javax.swing.JLabel();
        jLabel217 = new javax.swing.JLabel();
        jPanel59 = new javax.swing.JPanel();
        jLabel218 = new javax.swing.JLabel();
        jLabel219 = new javax.swing.JLabel();
        jLabel220 = new javax.swing.JLabel();
        jPanel60 = new javax.swing.JPanel();
        jLabel221 = new javax.swing.JLabel();
        jLabel222 = new javax.swing.JLabel();
        jLabel223 = new javax.swing.JLabel();
        jPanel61 = new javax.swing.JPanel();
        jLabel224 = new javax.swing.JLabel();
        jLabel225 = new javax.swing.JLabel();
        jLabel226 = new javax.swing.JLabel();
        jPanel62 = new javax.swing.JPanel();
        jLabel227 = new javax.swing.JLabel();
        jLabel228 = new javax.swing.JLabel();
        jLabel229 = new javax.swing.JLabel();
        jPanel63 = new javax.swing.JPanel();
        jLabel230 = new javax.swing.JLabel();
        jLabel231 = new javax.swing.JLabel();
        jLabel232 = new javax.swing.JLabel();
        jPanel64 = new javax.swing.JPanel();
        jLabel233 = new javax.swing.JLabel();
        jLabel234 = new javax.swing.JLabel();
        jLabel235 = new javax.swing.JLabel();
        jPanel65 = new javax.swing.JPanel();
        jLabel236 = new javax.swing.JLabel();
        jLabel237 = new javax.swing.JLabel();
        jLabel238 = new javax.swing.JLabel();
        jPanel66 = new javax.swing.JPanel();
        jLabel239 = new javax.swing.JLabel();
        jLabel240 = new javax.swing.JLabel();
        jLabel241 = new javax.swing.JLabel();
        jPanel67 = new javax.swing.JPanel();
        jLabel242 = new javax.swing.JLabel();
        jLabel243 = new javax.swing.JLabel();
        jLabel244 = new javax.swing.JLabel();
        jPanel68 = new javax.swing.JPanel();
        jLabel245 = new javax.swing.JLabel();
        jLabel246 = new javax.swing.JLabel();
        jLabel247 = new javax.swing.JLabel();
        jPanel69 = new javax.swing.JPanel();
        jLabel248 = new javax.swing.JLabel();
        jLabel249 = new javax.swing.JLabel();
        jLabel250 = new javax.swing.JLabel();
        jPanel70 = new javax.swing.JPanel();
        jLabel251 = new javax.swing.JLabel();
        jLabel252 = new javax.swing.JLabel();
        jLabel253 = new javax.swing.JLabel();
        jPanel71 = new javax.swing.JPanel();
        jLabel254 = new javax.swing.JLabel();
        jLabel255 = new javax.swing.JLabel();
        jLabel256 = new javax.swing.JLabel();
        jPanel72 = new javax.swing.JPanel();
        jLabel257 = new javax.swing.JLabel();
        jLabel258 = new javax.swing.JLabel();
        jLabel259 = new javax.swing.JLabel();
        jPanel73 = new javax.swing.JPanel();
        jLabel260 = new javax.swing.JLabel();
        jLabel261 = new javax.swing.JLabel();
        jLabel262 = new javax.swing.JLabel();
        jPanel74 = new javax.swing.JPanel();
        jLabel263 = new javax.swing.JLabel();
        jLabel264 = new javax.swing.JLabel();
        jLabel265 = new javax.swing.JLabel();
        jPanel75 = new javax.swing.JPanel();
        jLabel266 = new javax.swing.JLabel();
        jLabel267 = new javax.swing.JLabel();
        jLabel268 = new javax.swing.JLabel();
        jPanel76 = new javax.swing.JPanel();
        jLabel269 = new javax.swing.JLabel();
        jLabel270 = new javax.swing.JLabel();
        jLabel271 = new javax.swing.JLabel();
        jPanel77 = new javax.swing.JPanel();
        jLabel272 = new javax.swing.JLabel();
        jLabel273 = new javax.swing.JLabel();
        jLabel274 = new javax.swing.JLabel();
        jPanel95 = new javax.swing.JPanel();
        jLabel275 = new javax.swing.JLabel();
        jLabel276 = new javax.swing.JLabel();
        jLabel277 = new javax.swing.JLabel();
        jPanel96 = new javax.swing.JPanel();
        jLabel278 = new javax.swing.JLabel();
        jLabel279 = new javax.swing.JLabel();
        jLabel280 = new javax.swing.JLabel();
        jPanel97 = new javax.swing.JPanel();
        jLabel281 = new javax.swing.JLabel();
        jLabel282 = new javax.swing.JLabel();
        jLabel283 = new javax.swing.JLabel();
        jPanel98 = new javax.swing.JPanel();
        jLabel284 = new javax.swing.JLabel();
        jLabel285 = new javax.swing.JLabel();
        jLabel286 = new javax.swing.JLabel();
        jPanel99 = new javax.swing.JPanel();
        jLabel287 = new javax.swing.JLabel();
        jLabel288 = new javax.swing.JLabel();
        jLabel289 = new javax.swing.JLabel();
        jPanel100 = new javax.swing.JPanel();
        jLabel290 = new javax.swing.JLabel();
        jLabel291 = new javax.swing.JLabel();
        jLabel292 = new javax.swing.JLabel();
        jPanel101 = new javax.swing.JPanel();
        jLabel293 = new javax.swing.JLabel();
        jLabel294 = new javax.swing.JLabel();
        jLabel295 = new javax.swing.JLabel();
        jPanel102 = new javax.swing.JPanel();
        jLabel296 = new javax.swing.JLabel();
        jLabel297 = new javax.swing.JLabel();
        jLabel298 = new javax.swing.JLabel();
        jPanel135 = new javax.swing.JPanel();
        jLabel463 = new javax.swing.JLabel();
        jLabel464 = new javax.swing.JLabel();
        jLabel465 = new javax.swing.JLabel();
        jPanel136 = new javax.swing.JPanel();
        jLabel466 = new javax.swing.JLabel();
        jLabel467 = new javax.swing.JLabel();
        jLabel468 = new javax.swing.JLabel();
        jPanel137 = new javax.swing.JPanel();
        jLabel469 = new javax.swing.JLabel();
        jLabel470 = new javax.swing.JLabel();
        jLabel471 = new javax.swing.JLabel();
        jPanel138 = new javax.swing.JPanel();
        jLabel472 = new javax.swing.JLabel();
        jLabel473 = new javax.swing.JLabel();
        jLabel474 = new javax.swing.JLabel();
        jPanel111 = new javax.swing.JPanel();
        jLabel391 = new javax.swing.JLabel();
        jLabel392 = new javax.swing.JLabel();
        jLabel393 = new javax.swing.JLabel();
        jPanel112 = new javax.swing.JPanel();
        jLabel394 = new javax.swing.JLabel();
        jLabel395 = new javax.swing.JLabel();
        jLabel396 = new javax.swing.JLabel();
        jPanel113 = new javax.swing.JPanel();
        jLabel397 = new javax.swing.JLabel();
        jLabel398 = new javax.swing.JLabel();
        jLabel399 = new javax.swing.JLabel();
        jPanel114 = new javax.swing.JPanel();
        jLabel400 = new javax.swing.JLabel();
        jLabel401 = new javax.swing.JLabel();
        jLabel402 = new javax.swing.JLabel();
        jPanel115 = new javax.swing.JPanel();
        jLabel403 = new javax.swing.JLabel();
        jLabel404 = new javax.swing.JLabel();
        jLabel405 = new javax.swing.JLabel();
        jPanel116 = new javax.swing.JPanel();
        jLabel406 = new javax.swing.JLabel();
        jLabel407 = new javax.swing.JLabel();
        jLabel408 = new javax.swing.JLabel();
        jPanel117 = new javax.swing.JPanel();
        jLabel409 = new javax.swing.JLabel();
        jLabel410 = new javax.swing.JLabel();
        jLabel411 = new javax.swing.JLabel();
        jPanel118 = new javax.swing.JPanel();
        jLabel412 = new javax.swing.JLabel();
        jLabel413 = new javax.swing.JLabel();
        jLabel414 = new javax.swing.JLabel();
        jPanel103 = new javax.swing.JPanel();
        jLabel299 = new javax.swing.JLabel();
        jLabel300 = new javax.swing.JLabel();
        jLabel301 = new javax.swing.JLabel();
        jPanel104 = new javax.swing.JPanel();
        jLabel302 = new javax.swing.JLabel();
        jLabel303 = new javax.swing.JLabel();
        jLabel304 = new javax.swing.JLabel();
        jPanel105 = new javax.swing.JPanel();
        jLabel305 = new javax.swing.JLabel();
        jLabel306 = new javax.swing.JLabel();
        jLabel307 = new javax.swing.JLabel();
        jPanel106 = new javax.swing.JPanel();
        jLabel308 = new javax.swing.JLabel();
        jLabel309 = new javax.swing.JLabel();
        jLabel310 = new javax.swing.JLabel();
        jPanel131 = new javax.swing.JPanel();
        jLabel451 = new javax.swing.JLabel();
        jLabel452 = new javax.swing.JLabel();
        jLabel453 = new javax.swing.JLabel();
        jPanel132 = new javax.swing.JPanel();
        jLabel454 = new javax.swing.JLabel();
        jLabel455 = new javax.swing.JLabel();
        jLabel456 = new javax.swing.JLabel();
        jPanel133 = new javax.swing.JPanel();
        jLabel457 = new javax.swing.JLabel();
        jLabel458 = new javax.swing.JLabel();
        jLabel459 = new javax.swing.JLabel();
        jPanel134 = new javax.swing.JPanel();
        jLabel460 = new javax.swing.JLabel();
        jLabel461 = new javax.swing.JLabel();
        jLabel462 = new javax.swing.JLabel();
        jPanel127 = new javax.swing.JPanel();
        jLabel439 = new javax.swing.JLabel();
        jLabel440 = new javax.swing.JLabel();
        jLabel441 = new javax.swing.JLabel();
        jPanel128 = new javax.swing.JPanel();
        jLabel442 = new javax.swing.JLabel();
        jLabel443 = new javax.swing.JLabel();
        jLabel444 = new javax.swing.JLabel();
        jPanel129 = new javax.swing.JPanel();
        jLabel445 = new javax.swing.JLabel();
        jLabel446 = new javax.swing.JLabel();
        jLabel447 = new javax.swing.JLabel();
        jPanel130 = new javax.swing.JPanel();
        jLabel448 = new javax.swing.JLabel();
        jLabel449 = new javax.swing.JLabel();
        jLabel450 = new javax.swing.JLabel();
        jPanel119 = new javax.swing.JPanel();
        jLabel415 = new javax.swing.JLabel();
        jLabel416 = new javax.swing.JLabel();
        jLabel417 = new javax.swing.JLabel();
        jPanel120 = new javax.swing.JPanel();
        jLabel418 = new javax.swing.JLabel();
        jLabel419 = new javax.swing.JLabel();
        jLabel420 = new javax.swing.JLabel();
        jPanel121 = new javax.swing.JPanel();
        jLabel421 = new javax.swing.JLabel();
        jLabel422 = new javax.swing.JLabel();
        jLabel423 = new javax.swing.JLabel();
        jPanel122 = new javax.swing.JPanel();
        jLabel424 = new javax.swing.JLabel();
        jLabel425 = new javax.swing.JLabel();
        jLabel426 = new javax.swing.JLabel();
        jPanel139 = new javax.swing.JPanel();
        jLabel475 = new javax.swing.JLabel();
        jLabel476 = new javax.swing.JLabel();
        jLabel477 = new javax.swing.JLabel();
        jPanel140 = new javax.swing.JPanel();
        jLabel478 = new javax.swing.JLabel();
        jLabel479 = new javax.swing.JLabel();
        jLabel480 = new javax.swing.JLabel();
        jPanel141 = new javax.swing.JPanel();
        jLabel481 = new javax.swing.JLabel();
        jLabel482 = new javax.swing.JLabel();
        jLabel483 = new javax.swing.JLabel();
        jPanel142 = new javax.swing.JPanel();
        jLabel484 = new javax.swing.JLabel();
        jLabel485 = new javax.swing.JLabel();
        jLabel486 = new javax.swing.JLabel();
        jPanel107 = new javax.swing.JPanel();
        jLabel311 = new javax.swing.JLabel();
        jLabel312 = new javax.swing.JLabel();
        jLabel313 = new javax.swing.JLabel();
        jPanel108 = new javax.swing.JPanel();
        jLabel314 = new javax.swing.JLabel();
        jLabel383 = new javax.swing.JLabel();
        jLabel384 = new javax.swing.JLabel();
        jPanel109 = new javax.swing.JPanel();
        jLabel385 = new javax.swing.JLabel();
        jLabel386 = new javax.swing.JLabel();
        jLabel387 = new javax.swing.JLabel();
        jPanel110 = new javax.swing.JPanel();
        jLabel388 = new javax.swing.JLabel();
        jLabel389 = new javax.swing.JLabel();
        jLabel390 = new javax.swing.JLabel();
        jPanel123 = new javax.swing.JPanel();
        jLabel427 = new javax.swing.JLabel();
        jLabel428 = new javax.swing.JLabel();
        jLabel429 = new javax.swing.JLabel();
        jPanel124 = new javax.swing.JPanel();
        jLabel430 = new javax.swing.JLabel();
        jLabel431 = new javax.swing.JLabel();
        jLabel432 = new javax.swing.JLabel();
        jPanel125 = new javax.swing.JPanel();
        jLabel433 = new javax.swing.JLabel();
        jLabel434 = new javax.swing.JLabel();
        jLabel435 = new javax.swing.JLabel();
        jPanel126 = new javax.swing.JPanel();
        jLabel436 = new javax.swing.JLabel();
        jLabel437 = new javax.swing.JLabel();
        jLabel438 = new javax.swing.JLabel();
        jPanel143 = new javax.swing.JPanel();
        jLabel487 = new javax.swing.JLabel();
        jLabel488 = new javax.swing.JLabel();
        jLabel489 = new javax.swing.JLabel();
        jPanel144 = new javax.swing.JPanel();
        jLabel490 = new javax.swing.JLabel();
        jLabel491 = new javax.swing.JLabel();
        jLabel492 = new javax.swing.JLabel();
        jPanel145 = new javax.swing.JPanel();
        jLabel493 = new javax.swing.JLabel();
        jLabel494 = new javax.swing.JLabel();
        jLabel495 = new javax.swing.JLabel();
        jPanel146 = new javax.swing.JPanel();
        jLabel496 = new javax.swing.JLabel();
        jLabel497 = new javax.swing.JLabel();
        jLabel498 = new javax.swing.JLabel();
        btnExportar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1285, 1025));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 51, 255));
        jLabel2.setText("PROYECCION DE LAY OFF TURNO A");
        jLabel2.setToolTipText("");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel1.setText("DELPHI");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jTable1.setGridColor(new java.awt.Color(204, 204, 204));
        jTable1.setRowMargin(0);
        jScrollPane2.setViewportView(jTable1);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 1260, 470));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel20.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(51, 51, 255));
        jLabel20.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel7.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel23.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(51, 51, 255));
        jLabel23.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel7.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(51, 51, 255));
        jLabel16.setText("Dpto. 25962: ");
        jPanel7.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        lbl_totalTurno.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lbl_totalTurno.setText("____");
        jPanel7.add(lbl_totalTurno, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 80, -1, -1));

        jLabel30.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(51, 51, 255));
        jLabel30.setText("Dpto. 25963: ");
        jPanel7.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, -1));

        jLabel32.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(51, 51, 255));
        jLabel32.setText("Total TURNO:");
        jPanel7.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, -1, -1));

        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(51, 51, 255));
        jLabel33.setText("HC NECESARIO");
        jPanel7.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, -1, -1));

        dpto_25963.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        dpto_25963.setText("_____");
        jPanel7.add(dpto_25963, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 60, -1, -1));

        dpo_25962.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        dpo_25962.setText("_____");
        jPanel7.add(dpo_25962, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 40, -1, -1));

        jPanel1.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 580, 150, 110));

        btnActualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/save-icon.png"))); // NOI18N
        btnActualizar.setText("ACTUALIZAR");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });
        jPanel1.add(btnActualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 570, 180, 80));

        jPanel29.setBackground(new java.awt.Color(255, 255, 255));
        jPanel29.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jPanel29.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel128.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel128.setForeground(new java.awt.Color(51, 51, 255));
        jLabel128.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel29.add(jLabel128, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel129.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel129.setForeground(new java.awt.Color(51, 51, 255));
        jLabel129.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel29.add(jLabel129, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel130.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel130.setForeground(new java.awt.Color(51, 51, 255));
        jLabel130.setText("HC EN LAY OFF");
        jPanel29.add(jLabel130, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, -1, 40));

        jPanel41.setBackground(new java.awt.Color(255, 255, 255));
        jPanel41.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel41.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel164.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel164.setForeground(new java.awt.Color(51, 51, 255));
        jLabel164.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel41.add(jLabel164, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel165.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel165.setForeground(new java.awt.Color(51, 51, 255));
        jLabel165.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel41.add(jLabel165, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel166.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel166.setForeground(new java.awt.Color(51, 51, 255));
        jLabel166.setText("HC EN LAY OFF");
        jPanel41.add(jLabel166, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, 40));

        jPanel29.add(jPanel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 410, 150, 200));

        jPanel1.add(jPanel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 690, 150, 120));

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));
        jPanel12.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jPanel12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel43.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(51, 51, 255));
        jLabel43.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel12.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel44.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(51, 51, 255));
        jLabel44.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel12.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel45.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(51, 51, 255));
        jLabel45.setText(" 25962: ");
        jPanel12.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        jLabel46.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(51, 51, 255));
        jLabel46.setText("25963: ");
        jPanel12.add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        jLabel47.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(51, 51, 255));
        jLabel47.setText("DEPARTAMENTO");
        jPanel12.add(jLabel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 30));

        depa_63.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        depa_63.setText("_____");
        jPanel12.add(depa_63, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, -1, -1));

        depa_62.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        depa_62.setText("_____");
        jPanel12.add(depa_62, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 40, -1, -1));

        jLabel40.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(51, 51, 255));
        jLabel40.setText("T. TUR:");
        jPanel12.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, -1));

        total_turno.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        total_turno.setText("_____");
        jPanel12.add(total_turno, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 100, -1, -1));

        jPanel1.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 810, 110, 130));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel19.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(51, 51, 255));
        jLabel19.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel6.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(51, 51, 255));
        jLabel22.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel6.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(51, 51, 255));
        jLabel31.setText("HC LABORANDO    ");
        jPanel6.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, -1));

        jPanel1.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 810, 150, 130));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(51, 51, 255));
        jLabel21.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel8.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel24.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(51, 51, 255));
        jLabel24.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel8.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(51, 51, 255));
        jLabel17.setText(" 25962: ");
        jPanel8.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        jLabel34.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(51, 51, 255));
        jLabel34.setText(" 25963: ");
        jPanel8.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        jLabel35.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(51, 51, 255));
        jLabel35.setText("DEPARTAMENTO");
        jPanel8.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 30));

        dpo_62.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        dpo_62.setText("_____");
        jPanel8.add(dpo_62, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 40, -1, 20));

        total_tur.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        total_tur.setText("_____");
        jPanel8.add(total_tur, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 90, -1, -1));

        dpto_63.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        dpto_63.setText("_____");
        jPanel8.add(dpto_63, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 60, 40, 20));

        jLabel146.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel146.setForeground(new java.awt.Color(51, 51, 255));
        jLabel146.setText("T. TUR:");
        jPanel8.add(jLabel146, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, -1));

        jPanel1.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 690, 110, 120));

        jPanel42.setBackground(new java.awt.Color(255, 255, 255));
        jPanel42.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jPanel42.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel167.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel167.setForeground(new java.awt.Color(51, 51, 255));
        jLabel167.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel42.add(jLabel167, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel168.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel168.setForeground(new java.awt.Color(51, 51, 255));
        jLabel168.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel42.add(jLabel168, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel169.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel169.setForeground(new java.awt.Color(51, 51, 255));
        jLabel169.setText("VIERNES");
        jPanel42.add(jLabel169, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, -1, 40));

        jPanel43.setBackground(new java.awt.Color(255, 255, 255));
        jPanel43.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel43.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel170.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel170.setForeground(new java.awt.Color(51, 51, 255));
        jLabel170.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel43.add(jLabel170, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel171.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel171.setForeground(new java.awt.Color(51, 51, 255));
        jLabel171.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel43.add(jLabel171, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel172.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel172.setForeground(new java.awt.Color(51, 51, 255));
        jLabel172.setText("LUNES");
        jPanel43.add(jLabel172, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel42.add(jPanel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 350, 140, 60));

        jPanel44.setBackground(new java.awt.Color(255, 255, 255));
        jPanel44.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel44.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel173.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel173.setForeground(new java.awt.Color(51, 51, 255));
        jLabel173.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel44.add(jLabel173, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel174.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel174.setForeground(new java.awt.Color(51, 51, 255));
        jLabel174.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel44.add(jLabel174, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel175.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel175.setForeground(new java.awt.Color(51, 51, 255));
        jLabel175.setText("LUNES");
        jPanel44.add(jLabel175, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel45.setBackground(new java.awt.Color(255, 255, 255));
        jPanel45.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel45.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel176.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel176.setForeground(new java.awt.Color(51, 51, 255));
        jLabel176.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel45.add(jLabel176, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel177.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel177.setForeground(new java.awt.Color(51, 51, 255));
        jLabel177.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel45.add(jLabel177, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel178.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel178.setForeground(new java.awt.Color(51, 51, 255));
        jLabel178.setText("LUNES");
        jPanel45.add(jLabel178, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel44.add(jPanel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 350, 140, 60));

        jPanel42.add(jPanel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 350, 140, 60));

        jPanel1.add(jPanel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 660, 110, 30));

        jPanel18.setBackground(new java.awt.Color(255, 255, 255));
        jPanel18.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jPanel18.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel73.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel73.setForeground(new java.awt.Color(51, 51, 255));
        jLabel73.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel18.add(jLabel73, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel74.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel74.setForeground(new java.awt.Color(51, 51, 255));
        jLabel74.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel18.add(jLabel74, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel75.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel75.setForeground(new java.awt.Color(51, 51, 255));
        jLabel75.setText("25962: ");
        jPanel18.add(jLabel75, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        jLabel76.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel76.setForeground(new java.awt.Color(51, 51, 255));
        jLabel76.setText("25963: ");
        jPanel18.add(jLabel76, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, -1));

        dep63vie_loff.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        dep63vie_loff.setText("_____");
        jPanel18.add(dep63vie_loff, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 60, -1, -1));

        dep62vie_loff.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        dep62vie_loff.setText("_____");
        jPanel18.add(dep62vie_loff, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, -1, -1));

        jLabel90.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel90.setForeground(new java.awt.Color(51, 51, 255));
        jLabel90.setText("T. TUR:");
        jPanel18.add(jLabel90, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, -1));

        Tdep_vie.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Tdep_vie.setText("_____");
        jPanel18.add(Tdep_vie, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 90, -1, -1));

        jPanel1.add(jPanel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 690, 110, 120));

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));
        jPanel17.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jPanel17.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel68.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel68.setForeground(new java.awt.Color(51, 51, 255));
        jLabel68.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel17.add(jLabel68, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel69.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel69.setForeground(new java.awt.Color(51, 51, 255));
        jLabel69.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel17.add(jLabel69, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel70.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel70.setForeground(new java.awt.Color(51, 51, 255));
        jLabel70.setText("25962: ");
        jPanel17.add(jLabel70, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        jLabel71.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel71.setForeground(new java.awt.Color(51, 51, 255));
        jLabel71.setText("25963: ");
        jPanel17.add(jLabel71, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        dep63jue_loff.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        dep63jue_loff.setText("_____");
        jPanel17.add(dep63jue_loff, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, -1, -1));

        dep62jue_loff.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        dep62jue_loff.setText("_____");
        jPanel17.add(dep62jue_loff, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, -1, -1));

        jLabel85.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel85.setForeground(new java.awt.Color(51, 51, 255));
        jLabel85.setText("T. TUR:");
        jPanel17.add(jLabel85, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, -1));

        Tdep_jue.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Tdep_jue.setText("_____");
        jPanel17.add(Tdep_jue, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 90, -1, -1));

        jPanel1.add(jPanel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 690, 110, 120));

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));
        jPanel13.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jPanel13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel48.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(51, 51, 255));
        jLabel48.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel13.add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel49.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(51, 51, 255));
        jLabel49.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel13.add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel50.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel50.setForeground(new java.awt.Color(51, 51, 255));
        jLabel50.setText("25962: ");
        jPanel13.add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        jLabel51.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(51, 51, 255));
        jLabel51.setText("25963: ");
        jPanel13.add(jLabel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        dep63mi_loff.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        dep63mi_loff.setText("_____");
        jPanel13.add(dep63mi_loff, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, -1, -1));

        dep62mi_loff.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        dep62mi_loff.setText("_____");
        jPanel13.add(dep62mi_loff, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, -1, -1));

        jLabel55.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(51, 51, 255));
        jLabel55.setText("T.TUR:");
        jPanel13.add(jLabel55, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, -1));

        Tdep_mi.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Tdep_mi.setText("_____");
        jPanel13.add(Tdep_mi, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 90, -1, -1));

        jPanel1.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 690, 110, 120));

        jPanel19.setBackground(new java.awt.Color(255, 255, 255));
        jPanel19.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jPanel19.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel78.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel78.setForeground(new java.awt.Color(51, 51, 255));
        jLabel78.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel19.add(jLabel78, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel79.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel79.setForeground(new java.awt.Color(51, 51, 255));
        jLabel79.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel19.add(jLabel79, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel80.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel80.setForeground(new java.awt.Color(51, 51, 255));
        jLabel80.setText("25962: ");
        jPanel19.add(jLabel80, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        jLabel81.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel81.setForeground(new java.awt.Color(51, 51, 255));
        jLabel81.setText("25963: ");
        jPanel19.add(jLabel81, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        dep63ma_loff.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        dep63ma_loff.setText("_____");
        jPanel19.add(dep63ma_loff, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, -1, -1));

        dep62ma_loff.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        dep62ma_loff.setText("_____");
        jPanel19.add(dep62ma_loff, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, -1, -1));

        jLabel18.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(51, 51, 255));
        jLabel18.setText("T. TUR:");
        jPanel19.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, -1));

        Tdep_ma.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Tdep_ma.setText("_____");
        jPanel19.add(Tdep_ma, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 90, -1, 20));

        jPanel1.add(jPanel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 690, 110, 120));

        jPanel25.setBackground(new java.awt.Color(255, 255, 255));
        jPanel25.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jPanel25.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel108.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel108.setForeground(new java.awt.Color(51, 51, 255));
        jLabel108.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel25.add(jLabel108, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel109.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel109.setForeground(new java.awt.Color(51, 51, 255));
        jLabel109.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel25.add(jLabel109, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel110.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel110.setForeground(new java.awt.Color(51, 51, 255));
        jLabel110.setText("25962: ");
        jPanel25.add(jLabel110, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        jLabel111.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel111.setForeground(new java.awt.Color(51, 51, 255));
        jLabel111.setText("25963: ");
        jPanel25.add(jLabel111, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        depa63_marts.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        depa63_marts.setText("_____");
        jPanel25.add(depa63_marts, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, -1, -1));

        depa62_marts.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        depa62_marts.setText("_____");
        jPanel25.add(depa62_marts, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, -1, -1));

        jLabel125.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel125.setForeground(new java.awt.Color(51, 51, 255));
        jLabel125.setText("T.TUR:");
        jPanel25.add(jLabel125, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, -1));

        total_turnoMarts.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        total_turnoMarts.setText("_____");
        jPanel25.add(total_turnoMarts, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 100, -1, -1));

        jPanel1.add(jPanel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 810, 110, 130));

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));
        jPanel15.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jPanel15.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel58.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel58.setForeground(new java.awt.Color(51, 51, 255));
        jLabel58.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel15.add(jLabel58, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel59.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel59.setForeground(new java.awt.Color(51, 51, 255));
        jLabel59.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel15.add(jLabel59, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel60.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel60.setForeground(new java.awt.Color(51, 51, 255));
        jLabel60.setText("25962: ");
        jPanel15.add(jLabel60, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        jLabel61.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel61.setForeground(new java.awt.Color(51, 51, 255));
        jLabel61.setText("25963: ");
        jPanel15.add(jLabel61, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        depa63_mierc.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        depa63_mierc.setText("_____");
        jPanel15.add(depa63_mierc, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, -1, -1));

        depa62_mierc.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        depa62_mierc.setText("_____");
        jPanel15.add(depa62_mierc, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, -1, -1));

        jLabel65.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel65.setForeground(new java.awt.Color(51, 51, 255));
        jLabel65.setText("T.TUR:");
        jPanel15.add(jLabel65, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, -1));

        total_turnoMierc.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        total_turnoMierc.setText("_____");
        jPanel15.add(total_turnoMierc, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 100, -1, -1));

        jPanel1.add(jPanel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 810, 110, 130));

        jPanel23.setBackground(new java.awt.Color(255, 255, 255));
        jPanel23.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jPanel23.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel98.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel98.setForeground(new java.awt.Color(51, 51, 255));
        jLabel98.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel23.add(jLabel98, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel99.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel99.setForeground(new java.awt.Color(51, 51, 255));
        jLabel99.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel23.add(jLabel99, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel100.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel100.setForeground(new java.awt.Color(51, 51, 255));
        jLabel100.setText("25962: ");
        jPanel23.add(jLabel100, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        jLabel101.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel101.setForeground(new java.awt.Color(51, 51, 255));
        jLabel101.setText("25963: ");
        jPanel23.add(jLabel101, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        depa63_jue.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        depa63_jue.setText("_____");
        jPanel23.add(depa63_jue, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, -1, -1));

        depa62_jue.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        depa62_jue.setText("_____");
        jPanel23.add(depa62_jue, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, -1, -1));

        jLabel115.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel115.setForeground(new java.awt.Color(51, 51, 255));
        jLabel115.setText("T.TUR:");
        jPanel23.add(jLabel115, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, -1));

        total_turnojue.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        total_turnojue.setText("_____");
        jPanel23.add(total_turnojue, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 100, -1, -1));

        jPanel1.add(jPanel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 810, 110, 130));

        jPanel24.setBackground(new java.awt.Color(255, 255, 255));
        jPanel24.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jPanel24.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel103.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel103.setForeground(new java.awt.Color(51, 51, 255));
        jLabel103.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel24.add(jLabel103, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel104.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel104.setForeground(new java.awt.Color(51, 51, 255));
        jLabel104.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel24.add(jLabel104, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel105.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel105.setForeground(new java.awt.Color(51, 51, 255));
        jLabel105.setText("25962: ");
        jPanel24.add(jLabel105, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        jLabel106.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel106.setForeground(new java.awt.Color(51, 51, 255));
        jLabel106.setText("25963: ");
        jPanel24.add(jLabel106, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        depa63_vie.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        depa63_vie.setText("_____");
        jPanel24.add(depa63_vie, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, -1, -1));

        depa62_vie.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        depa62_vie.setText("_____");
        jPanel24.add(depa62_vie, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, -1, -1));

        jLabel120.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel120.setForeground(new java.awt.Color(51, 51, 255));
        jLabel120.setText("T.TUR:");
        jPanel24.add(jLabel120, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, -1));

        total_turnovie.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        total_turnovie.setText("_____");
        jPanel24.add(total_turnovie, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 100, -1, -1));

        jPanel1.add(jPanel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 810, 110, 130));

        jPanel35.setBackground(new java.awt.Color(255, 255, 255));
        jPanel35.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jPanel35.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel1.add(jPanel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 660, 10, 280));

        jPanel22.setBackground(new java.awt.Color(255, 255, 255));
        jPanel22.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jPanel22.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel93.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel93.setForeground(new java.awt.Color(51, 51, 255));
        jLabel93.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel22.add(jLabel93, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel94.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel94.setForeground(new java.awt.Color(51, 51, 255));
        jLabel94.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel22.add(jLabel94, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel95.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel95.setForeground(new java.awt.Color(51, 51, 255));
        jLabel95.setText(" 25962: ");
        jPanel22.add(jLabel95, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        jLabel96.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel96.setForeground(new java.awt.Color(51, 51, 255));
        jLabel96.setText("25963: ");
        jPanel22.add(jLabel96, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        jLabel97.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel97.setForeground(new java.awt.Color(51, 51, 255));
        jLabel97.setText("DEPARTAMENTO");
        jPanel22.add(jLabel97, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 30));

        depa_63_2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        depa_63_2.setText("_____");
        jPanel22.add(depa_63_2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, -1, -1));

        depa_62_2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        depa_62_2.setText("_____");
        jPanel22.add(depa_62_2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 40, -1, -1));

        jLabel147.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel147.setForeground(new java.awt.Color(51, 51, 255));
        jLabel147.setText("T. TUR:");
        jPanel22.add(jLabel147, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, -1));

        total_turno_2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        total_turno_2.setText("_____");
        jPanel22.add(total_turno_2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 100, -1, -1));

        jPanel1.add(jPanel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 810, 110, 130));

        jPanel36.setBackground(new java.awt.Color(255, 255, 255));
        jPanel36.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jPanel36.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel148.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel148.setForeground(new java.awt.Color(51, 51, 255));
        jLabel148.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel36.add(jLabel148, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel149.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel149.setForeground(new java.awt.Color(51, 51, 255));
        jLabel149.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel36.add(jLabel149, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel150.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel150.setForeground(new java.awt.Color(51, 51, 255));
        jLabel150.setText("25962: ");
        jPanel36.add(jLabel150, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        jLabel151.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel151.setForeground(new java.awt.Color(51, 51, 255));
        jLabel151.setText("25963: ");
        jPanel36.add(jLabel151, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        depa63_marts2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        depa63_marts2.setText("_____");
        jPanel36.add(depa63_marts2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, -1, -1));

        depa62_marts2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        depa62_marts2.setText("_____");
        jPanel36.add(depa62_marts2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, -1, -1));

        jLabel152.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel152.setForeground(new java.awt.Color(51, 51, 255));
        jLabel152.setText("T.TUR:");
        jPanel36.add(jLabel152, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, -1));

        total_turnoMarts2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        total_turnoMarts2.setText("_____");
        jPanel36.add(total_turnoMarts2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 100, -1, -1));

        jPanel1.add(jPanel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 810, 110, 130));

        jPanel37.setBackground(new java.awt.Color(255, 255, 255));
        jPanel37.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jPanel37.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel153.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel153.setForeground(new java.awt.Color(51, 51, 255));
        jLabel153.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel37.add(jLabel153, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel154.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel154.setForeground(new java.awt.Color(51, 51, 255));
        jLabel154.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel37.add(jLabel154, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel155.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel155.setForeground(new java.awt.Color(51, 51, 255));
        jLabel155.setText("25962: ");
        jPanel37.add(jLabel155, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        jLabel156.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel156.setForeground(new java.awt.Color(51, 51, 255));
        jLabel156.setText("25963: ");
        jPanel37.add(jLabel156, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        dep63ma_loff2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        dep63ma_loff2.setText("_____");
        jPanel37.add(dep63ma_loff2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, -1, -1));

        dep62ma_loff2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        dep62ma_loff2.setText("_____");
        jPanel37.add(dep62ma_loff2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, -1, -1));

        jLabel25.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(51, 51, 255));
        jLabel25.setText("T. TUR:");
        jPanel37.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, -1));

        Tdep_ma2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Tdep_ma2.setText("_____");
        jPanel37.add(Tdep_ma2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 90, -1, 20));

        jPanel1.add(jPanel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 690, 110, 120));

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel26.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(51, 51, 255));
        jLabel26.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel9.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel29.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(51, 51, 255));
        jLabel29.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel9.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel36.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(51, 51, 255));
        jLabel36.setText(" 25962: ");
        jPanel9.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        jLabel157.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel157.setForeground(new java.awt.Color(51, 51, 255));
        jLabel157.setText(" 25963: ");
        jPanel9.add(jLabel157, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        jLabel315.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel315.setForeground(new java.awt.Color(51, 51, 255));
        jLabel315.setText("DEPARTAMENTO");
        jPanel9.add(jLabel315, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 30));

        dpo_62_2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        dpo_62_2.setText("_____");
        jPanel9.add(dpo_62_2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 40, -1, 20));

        total_tur_2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        total_tur_2.setText("_____");
        jPanel9.add(total_tur_2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 90, -1, -1));

        dpto_63_2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        dpto_63_2.setText("_____");
        jPanel9.add(dpto_63_2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 60, 40, 20));

        jLabel316.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel316.setForeground(new java.awt.Color(51, 51, 255));
        jLabel316.setText("T. TUR:");
        jPanel9.add(jLabel316, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, -1));

        jPanel1.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 690, 110, 120));

        jPanel85.setBackground(new java.awt.Color(255, 255, 255));
        jPanel85.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jPanel85.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel341.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel341.setForeground(new java.awt.Color(51, 51, 255));
        jLabel341.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel85.add(jLabel341, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel342.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel342.setForeground(new java.awt.Color(51, 51, 255));
        jLabel342.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel85.add(jLabel342, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel343.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel343.setForeground(new java.awt.Color(51, 51, 255));
        jLabel343.setText("25962: ");
        jPanel85.add(jLabel343, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        jLabel344.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel344.setForeground(new java.awt.Color(51, 51, 255));
        jLabel344.setText("25963: ");
        jPanel85.add(jLabel344, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        depa63_mierc2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        depa63_mierc2.setText("_____");
        jPanel85.add(depa63_mierc2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, -1, -1));

        depa62_mierc2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        depa62_mierc2.setText("_____");
        jPanel85.add(depa62_mierc2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, -1, -1));

        jLabel345.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel345.setForeground(new java.awt.Color(51, 51, 255));
        jLabel345.setText("T.TUR:");
        jPanel85.add(jLabel345, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, -1));

        total_turnoMierc2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        total_turnoMierc2.setText("_____");
        jPanel85.add(total_turnoMierc2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 100, -1, -1));

        jPanel1.add(jPanel85, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 810, 110, 130));

        jPanel86.setBackground(new java.awt.Color(255, 255, 255));
        jPanel86.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jPanel86.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel346.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel346.setForeground(new java.awt.Color(51, 51, 255));
        jLabel346.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel86.add(jLabel346, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel347.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel347.setForeground(new java.awt.Color(51, 51, 255));
        jLabel347.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel86.add(jLabel347, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel348.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel348.setForeground(new java.awt.Color(51, 51, 255));
        jLabel348.setText("25962: ");
        jPanel86.add(jLabel348, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        jLabel349.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel349.setForeground(new java.awt.Color(51, 51, 255));
        jLabel349.setText("25963: ");
        jPanel86.add(jLabel349, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        dep63mi_loff2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        dep63mi_loff2.setText("_____");
        jPanel86.add(dep63mi_loff2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, -1, -1));

        dep62mi_loff2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        dep62mi_loff2.setText("_____");
        jPanel86.add(dep62mi_loff2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, -1, -1));

        jLabel350.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel350.setForeground(new java.awt.Color(51, 51, 255));
        jLabel350.setText("T.TUR:");
        jPanel86.add(jLabel350, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, -1));

        Tdep_mi2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Tdep_mi2.setText("_____");
        jPanel86.add(Tdep_mi2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 90, -1, -1));

        jPanel1.add(jPanel86, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 690, 110, 120));

        jPanel89.setBackground(new java.awt.Color(255, 255, 255));
        jPanel89.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jPanel89.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel357.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel357.setForeground(new java.awt.Color(51, 51, 255));
        jLabel357.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel89.add(jLabel357, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel358.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel358.setForeground(new java.awt.Color(51, 51, 255));
        jLabel358.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel89.add(jLabel358, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel359.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel359.setForeground(new java.awt.Color(51, 51, 255));
        jLabel359.setText("25962: ");
        jPanel89.add(jLabel359, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        jLabel360.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel360.setForeground(new java.awt.Color(51, 51, 255));
        jLabel360.setText("25963: ");
        jPanel89.add(jLabel360, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        depa63_jue2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        depa63_jue2.setText("_____");
        jPanel89.add(depa63_jue2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, -1, -1));

        depa62_jue2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        depa62_jue2.setText("_____");
        jPanel89.add(depa62_jue2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, -1, -1));

        jLabel361.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel361.setForeground(new java.awt.Color(51, 51, 255));
        jLabel361.setText("T.TUR:");
        jPanel89.add(jLabel361, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, -1));

        total_turnojue2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        total_turnojue2.setText("_____");
        jPanel89.add(total_turnojue2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 100, -1, -1));

        jPanel1.add(jPanel89, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 810, 110, 130));

        jPanel90.setBackground(new java.awt.Color(255, 255, 255));
        jPanel90.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jPanel90.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel362.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel362.setForeground(new java.awt.Color(51, 51, 255));
        jLabel362.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel90.add(jLabel362, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel363.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel363.setForeground(new java.awt.Color(51, 51, 255));
        jLabel363.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel90.add(jLabel363, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel364.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel364.setForeground(new java.awt.Color(51, 51, 255));
        jLabel364.setText("25962: ");
        jPanel90.add(jLabel364, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        jLabel365.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel365.setForeground(new java.awt.Color(51, 51, 255));
        jLabel365.setText("25963: ");
        jPanel90.add(jLabel365, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        depa63_vie2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        depa63_vie2.setText("_____");
        jPanel90.add(depa63_vie2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, -1, -1));

        depa62_vie2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        depa62_vie2.setText("_____");
        jPanel90.add(depa62_vie2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, -1, -1));

        jLabel366.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel366.setForeground(new java.awt.Color(51, 51, 255));
        jLabel366.setText("T.TUR:");
        jPanel90.add(jLabel366, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, -1, -1));

        total_turnovie2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        total_turnovie2.setText("_____");
        jPanel90.add(total_turnovie2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 100, -1, -1));

        jPanel1.add(jPanel90, new org.netbeans.lib.awtextra.AbsoluteConstraints(1150, 810, 110, 130));

        jPanel91.setBackground(new java.awt.Color(255, 255, 255));
        jPanel91.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jPanel91.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel367.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel367.setForeground(new java.awt.Color(51, 51, 255));
        jLabel367.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel91.add(jLabel367, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel368.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel368.setForeground(new java.awt.Color(51, 51, 255));
        jLabel368.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel91.add(jLabel368, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel369.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel369.setForeground(new java.awt.Color(51, 51, 255));
        jLabel369.setText("25962: ");
        jPanel91.add(jLabel369, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        jLabel370.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel370.setForeground(new java.awt.Color(51, 51, 255));
        jLabel370.setText("25963: ");
        jPanel91.add(jLabel370, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, -1));

        dep63vie_loff2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        dep63vie_loff2.setText("_____");
        jPanel91.add(dep63vie_loff2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 60, -1, -1));

        dep62vie_loff2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        dep62vie_loff2.setText("_____");
        jPanel91.add(dep62vie_loff2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, -1, -1));

        jLabel371.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel371.setForeground(new java.awt.Color(51, 51, 255));
        jLabel371.setText("T. TUR:");
        jPanel91.add(jLabel371, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, -1));

        Tdep_vie2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Tdep_vie2.setText("_____");
        jPanel91.add(Tdep_vie2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 90, -1, -1));

        jPanel1.add(jPanel91, new org.netbeans.lib.awtextra.AbsoluteConstraints(1150, 690, 110, 120));

        jPanel92.setBackground(new java.awt.Color(255, 255, 255));
        jPanel92.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jPanel92.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel372.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel372.setForeground(new java.awt.Color(51, 51, 255));
        jLabel372.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel92.add(jLabel372, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel373.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel373.setForeground(new java.awt.Color(51, 51, 255));
        jLabel373.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel92.add(jLabel373, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel374.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel374.setForeground(new java.awt.Color(51, 51, 255));
        jLabel374.setText("25962: ");
        jPanel92.add(jLabel374, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, -1, -1));

        jLabel375.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel375.setForeground(new java.awt.Color(51, 51, 255));
        jLabel375.setText("25963: ");
        jPanel92.add(jLabel375, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, -1, -1));

        dep63jue_loff2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        dep63jue_loff2.setText("_____");
        jPanel92.add(dep63jue_loff2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 70, -1, -1));

        dep62jue_loff2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        dep62jue_loff2.setText("_____");
        jPanel92.add(dep62jue_loff2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, -1, -1));

        jLabel376.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel376.setForeground(new java.awt.Color(51, 51, 255));
        jLabel376.setText("T. TUR:");
        jPanel92.add(jLabel376, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, -1, -1));

        Tdep_jue2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        Tdep_jue2.setText("_____");
        jPanel92.add(Tdep_jue2, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 90, -1, -1));

        jPanel1.add(jPanel92, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 690, 110, 120));

        jPanel46.setBackground(new java.awt.Color(255, 255, 255));
        jPanel46.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jPanel46.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel179.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel179.setForeground(new java.awt.Color(51, 51, 255));
        jLabel179.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel46.add(jLabel179, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel180.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel180.setForeground(new java.awt.Color(51, 51, 255));
        jLabel180.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel46.add(jLabel180, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel181.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel181.setForeground(new java.awt.Color(51, 51, 255));
        jLabel181.setText("LUNES");
        jPanel46.add(jLabel181, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, -1, 40));

        jPanel47.setBackground(new java.awt.Color(255, 255, 255));
        jPanel47.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel47.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel182.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel182.setForeground(new java.awt.Color(51, 51, 255));
        jLabel182.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel47.add(jLabel182, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel183.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel183.setForeground(new java.awt.Color(51, 51, 255));
        jLabel183.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel47.add(jLabel183, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel184.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel184.setForeground(new java.awt.Color(51, 51, 255));
        jLabel184.setText("LUNES");
        jPanel47.add(jLabel184, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel46.add(jPanel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 350, 140, 60));

        jPanel48.setBackground(new java.awt.Color(255, 255, 255));
        jPanel48.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel48.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel185.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel185.setForeground(new java.awt.Color(51, 51, 255));
        jLabel185.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel48.add(jLabel185, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel186.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel186.setForeground(new java.awt.Color(51, 51, 255));
        jLabel186.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel48.add(jLabel186, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel187.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel187.setForeground(new java.awt.Color(51, 51, 255));
        jLabel187.setText("LUNES");
        jPanel48.add(jLabel187, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel49.setBackground(new java.awt.Color(255, 255, 255));
        jPanel49.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel49.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel188.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel188.setForeground(new java.awt.Color(51, 51, 255));
        jLabel188.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel49.add(jLabel188, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel189.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel189.setForeground(new java.awt.Color(51, 51, 255));
        jLabel189.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel49.add(jLabel189, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel190.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel190.setForeground(new java.awt.Color(51, 51, 255));
        jLabel190.setText("LUNES");
        jPanel49.add(jLabel190, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel48.add(jPanel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 350, 140, 60));

        jPanel46.add(jPanel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 350, 140, 60));

        jPanel1.add(jPanel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 660, 110, 30));

        jPanel50.setBackground(new java.awt.Color(255, 255, 255));
        jPanel50.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jPanel50.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel191.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel191.setForeground(new java.awt.Color(51, 51, 255));
        jLabel191.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel50.add(jLabel191, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel192.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel192.setForeground(new java.awt.Color(51, 51, 255));
        jLabel192.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel50.add(jLabel192, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel193.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel193.setForeground(new java.awt.Color(51, 51, 255));
        jLabel193.setText("MARTES");
        jPanel50.add(jLabel193, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, -1, 40));

        jPanel51.setBackground(new java.awt.Color(255, 255, 255));
        jPanel51.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel51.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel194.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel194.setForeground(new java.awt.Color(51, 51, 255));
        jLabel194.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel51.add(jLabel194, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel195.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel195.setForeground(new java.awt.Color(51, 51, 255));
        jLabel195.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel51.add(jLabel195, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel196.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel196.setForeground(new java.awt.Color(51, 51, 255));
        jLabel196.setText("LUNES");
        jPanel51.add(jLabel196, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel50.add(jPanel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 350, 140, 60));

        jPanel52.setBackground(new java.awt.Color(255, 255, 255));
        jPanel52.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel52.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel197.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel197.setForeground(new java.awt.Color(51, 51, 255));
        jLabel197.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel52.add(jLabel197, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel198.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel198.setForeground(new java.awt.Color(51, 51, 255));
        jLabel198.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel52.add(jLabel198, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel199.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel199.setForeground(new java.awt.Color(51, 51, 255));
        jLabel199.setText("LUNES");
        jPanel52.add(jLabel199, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel53.setBackground(new java.awt.Color(255, 255, 255));
        jPanel53.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel53.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel200.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel200.setForeground(new java.awt.Color(51, 51, 255));
        jLabel200.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel53.add(jLabel200, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel201.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel201.setForeground(new java.awt.Color(51, 51, 255));
        jLabel201.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel53.add(jLabel201, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel202.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel202.setForeground(new java.awt.Color(51, 51, 255));
        jLabel202.setText("LUNES");
        jPanel53.add(jLabel202, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel52.add(jPanel53, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 350, 140, 60));

        jPanel50.add(jPanel52, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 350, 140, 60));

        jPanel1.add(jPanel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 660, 110, 30));

        jPanel54.setBackground(new java.awt.Color(255, 255, 255));
        jPanel54.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jPanel54.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel203.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel203.setForeground(new java.awt.Color(51, 51, 255));
        jLabel203.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel54.add(jLabel203, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel204.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel204.setForeground(new java.awt.Color(51, 51, 255));
        jLabel204.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel54.add(jLabel204, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel205.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel205.setForeground(new java.awt.Color(51, 51, 255));
        jLabel205.setText("MIERCOLES");
        jPanel54.add(jLabel205, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, 40));

        jPanel55.setBackground(new java.awt.Color(255, 255, 255));
        jPanel55.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel55.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel206.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel206.setForeground(new java.awt.Color(51, 51, 255));
        jLabel206.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel55.add(jLabel206, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel207.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel207.setForeground(new java.awt.Color(51, 51, 255));
        jLabel207.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel55.add(jLabel207, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel208.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel208.setForeground(new java.awt.Color(51, 51, 255));
        jLabel208.setText("LUNES");
        jPanel55.add(jLabel208, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel54.add(jPanel55, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 350, 140, 60));

        jPanel56.setBackground(new java.awt.Color(255, 255, 255));
        jPanel56.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel56.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel209.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel209.setForeground(new java.awt.Color(51, 51, 255));
        jLabel209.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel56.add(jLabel209, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel210.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel210.setForeground(new java.awt.Color(51, 51, 255));
        jLabel210.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel56.add(jLabel210, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel211.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel211.setForeground(new java.awt.Color(51, 51, 255));
        jLabel211.setText("LUNES");
        jPanel56.add(jLabel211, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel57.setBackground(new java.awt.Color(255, 255, 255));
        jPanel57.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel57.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel212.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel212.setForeground(new java.awt.Color(51, 51, 255));
        jLabel212.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel57.add(jLabel212, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel213.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel213.setForeground(new java.awt.Color(51, 51, 255));
        jLabel213.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel57.add(jLabel213, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel214.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel214.setForeground(new java.awt.Color(51, 51, 255));
        jLabel214.setText("LUNES");
        jPanel57.add(jLabel214, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel56.add(jPanel57, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 350, 140, 60));

        jPanel54.add(jPanel56, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 350, 140, 60));

        jPanel1.add(jPanel54, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 660, 110, 30));

        jPanel58.setBackground(new java.awt.Color(255, 255, 255));
        jPanel58.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jPanel58.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel215.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel215.setForeground(new java.awt.Color(51, 51, 255));
        jLabel215.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel58.add(jLabel215, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel216.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel216.setForeground(new java.awt.Color(51, 51, 255));
        jLabel216.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel58.add(jLabel216, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel217.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel217.setForeground(new java.awt.Color(51, 51, 255));
        jLabel217.setText("LUNES");
        jPanel58.add(jLabel217, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, -1, 40));

        jPanel59.setBackground(new java.awt.Color(255, 255, 255));
        jPanel59.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel59.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel218.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel218.setForeground(new java.awt.Color(51, 51, 255));
        jLabel218.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel59.add(jLabel218, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel219.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel219.setForeground(new java.awt.Color(51, 51, 255));
        jLabel219.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel59.add(jLabel219, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel220.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel220.setForeground(new java.awt.Color(51, 51, 255));
        jLabel220.setText("LUNES");
        jPanel59.add(jLabel220, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel58.add(jPanel59, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 350, 140, 60));

        jPanel60.setBackground(new java.awt.Color(255, 255, 255));
        jPanel60.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel60.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel221.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel221.setForeground(new java.awt.Color(51, 51, 255));
        jLabel221.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel60.add(jLabel221, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel222.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel222.setForeground(new java.awt.Color(51, 51, 255));
        jLabel222.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel60.add(jLabel222, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel223.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel223.setForeground(new java.awt.Color(51, 51, 255));
        jLabel223.setText("LUNES");
        jPanel60.add(jLabel223, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel61.setBackground(new java.awt.Color(255, 255, 255));
        jPanel61.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel61.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel224.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel224.setForeground(new java.awt.Color(51, 51, 255));
        jLabel224.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel61.add(jLabel224, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel225.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel225.setForeground(new java.awt.Color(51, 51, 255));
        jLabel225.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel61.add(jLabel225, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel226.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel226.setForeground(new java.awt.Color(51, 51, 255));
        jLabel226.setText("LUNES");
        jPanel61.add(jLabel226, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel60.add(jPanel61, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 350, 140, 60));

        jPanel58.add(jPanel60, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 350, 140, 60));

        jPanel1.add(jPanel58, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 660, 110, 30));

        jPanel62.setBackground(new java.awt.Color(255, 255, 255));
        jPanel62.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jPanel62.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel227.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel227.setForeground(new java.awt.Color(51, 51, 255));
        jLabel227.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel62.add(jLabel227, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel228.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel228.setForeground(new java.awt.Color(51, 51, 255));
        jLabel228.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel62.add(jLabel228, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel229.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel229.setForeground(new java.awt.Color(51, 51, 255));
        jLabel229.setText("JUEVES");
        jPanel62.add(jLabel229, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, -1, 40));

        jPanel63.setBackground(new java.awt.Color(255, 255, 255));
        jPanel63.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel63.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel230.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel230.setForeground(new java.awt.Color(51, 51, 255));
        jLabel230.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel63.add(jLabel230, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel231.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel231.setForeground(new java.awt.Color(51, 51, 255));
        jLabel231.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel63.add(jLabel231, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel232.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel232.setForeground(new java.awt.Color(51, 51, 255));
        jLabel232.setText("LUNES");
        jPanel63.add(jLabel232, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel62.add(jPanel63, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 350, 140, 60));

        jPanel64.setBackground(new java.awt.Color(255, 255, 255));
        jPanel64.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel64.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel233.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel233.setForeground(new java.awt.Color(51, 51, 255));
        jLabel233.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel64.add(jLabel233, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel234.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel234.setForeground(new java.awt.Color(51, 51, 255));
        jLabel234.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel64.add(jLabel234, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel235.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel235.setForeground(new java.awt.Color(51, 51, 255));
        jLabel235.setText("LUNES");
        jPanel64.add(jLabel235, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel65.setBackground(new java.awt.Color(255, 255, 255));
        jPanel65.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel65.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel236.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel236.setForeground(new java.awt.Color(51, 51, 255));
        jLabel236.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel65.add(jLabel236, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel237.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel237.setForeground(new java.awt.Color(51, 51, 255));
        jLabel237.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel65.add(jLabel237, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel238.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel238.setForeground(new java.awt.Color(51, 51, 255));
        jLabel238.setText("LUNES");
        jPanel65.add(jLabel238, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel64.add(jPanel65, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 350, 140, 60));

        jPanel62.add(jPanel64, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 350, 140, 60));

        jPanel1.add(jPanel62, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 660, 110, 30));

        jPanel66.setBackground(new java.awt.Color(255, 255, 255));
        jPanel66.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jPanel66.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel239.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel239.setForeground(new java.awt.Color(51, 51, 255));
        jLabel239.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel66.add(jLabel239, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel240.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel240.setForeground(new java.awt.Color(51, 51, 255));
        jLabel240.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel66.add(jLabel240, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel241.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel241.setForeground(new java.awt.Color(51, 51, 255));
        jLabel241.setText("LUNES");
        jPanel66.add(jLabel241, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, -1, 40));

        jPanel67.setBackground(new java.awt.Color(255, 255, 255));
        jPanel67.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel67.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel242.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel242.setForeground(new java.awt.Color(51, 51, 255));
        jLabel242.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel67.add(jLabel242, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel243.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel243.setForeground(new java.awt.Color(51, 51, 255));
        jLabel243.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel67.add(jLabel243, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel244.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel244.setForeground(new java.awt.Color(51, 51, 255));
        jLabel244.setText("LUNES");
        jPanel67.add(jLabel244, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel66.add(jPanel67, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 350, 140, 60));

        jPanel68.setBackground(new java.awt.Color(255, 255, 255));
        jPanel68.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel68.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel245.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel245.setForeground(new java.awt.Color(51, 51, 255));
        jLabel245.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel68.add(jLabel245, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel246.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel246.setForeground(new java.awt.Color(51, 51, 255));
        jLabel246.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel68.add(jLabel246, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel247.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel247.setForeground(new java.awt.Color(51, 51, 255));
        jLabel247.setText("LUNES");
        jPanel68.add(jLabel247, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel69.setBackground(new java.awt.Color(255, 255, 255));
        jPanel69.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel69.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel248.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel248.setForeground(new java.awt.Color(51, 51, 255));
        jLabel248.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel69.add(jLabel248, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel249.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel249.setForeground(new java.awt.Color(51, 51, 255));
        jLabel249.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel69.add(jLabel249, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel250.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel250.setForeground(new java.awt.Color(51, 51, 255));
        jLabel250.setText("LUNES");
        jPanel69.add(jLabel250, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel68.add(jPanel69, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 350, 140, 60));

        jPanel66.add(jPanel68, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 350, 140, 60));

        jPanel1.add(jPanel66, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 660, 110, 30));

        jPanel70.setBackground(new java.awt.Color(255, 255, 255));
        jPanel70.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jPanel70.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel251.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel251.setForeground(new java.awt.Color(51, 51, 255));
        jLabel251.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel70.add(jLabel251, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel252.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel252.setForeground(new java.awt.Color(51, 51, 255));
        jLabel252.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel70.add(jLabel252, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel253.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel253.setForeground(new java.awt.Color(51, 51, 255));
        jLabel253.setText("MARTES");
        jPanel70.add(jLabel253, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, -1, 40));

        jPanel71.setBackground(new java.awt.Color(255, 255, 255));
        jPanel71.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel71.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel254.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel254.setForeground(new java.awt.Color(51, 51, 255));
        jLabel254.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel71.add(jLabel254, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel255.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel255.setForeground(new java.awt.Color(51, 51, 255));
        jLabel255.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel71.add(jLabel255, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel256.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel256.setForeground(new java.awt.Color(51, 51, 255));
        jLabel256.setText("LUNES");
        jPanel71.add(jLabel256, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel70.add(jPanel71, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 350, 140, 60));

        jPanel72.setBackground(new java.awt.Color(255, 255, 255));
        jPanel72.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel72.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel257.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel257.setForeground(new java.awt.Color(51, 51, 255));
        jLabel257.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel72.add(jLabel257, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel258.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel258.setForeground(new java.awt.Color(51, 51, 255));
        jLabel258.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel72.add(jLabel258, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel259.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel259.setForeground(new java.awt.Color(51, 51, 255));
        jLabel259.setText("LUNES");
        jPanel72.add(jLabel259, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel73.setBackground(new java.awt.Color(255, 255, 255));
        jPanel73.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel73.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel260.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel260.setForeground(new java.awt.Color(51, 51, 255));
        jLabel260.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel73.add(jLabel260, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel261.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel261.setForeground(new java.awt.Color(51, 51, 255));
        jLabel261.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel73.add(jLabel261, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel262.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel262.setForeground(new java.awt.Color(51, 51, 255));
        jLabel262.setText("LUNES");
        jPanel73.add(jLabel262, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel72.add(jPanel73, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 350, 140, 60));

        jPanel70.add(jPanel72, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 350, 140, 60));

        jPanel1.add(jPanel70, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 660, 110, 30));

        jPanel74.setBackground(new java.awt.Color(255, 255, 255));
        jPanel74.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jPanel74.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel263.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel263.setForeground(new java.awt.Color(51, 51, 255));
        jLabel263.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel74.add(jLabel263, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel264.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel264.setForeground(new java.awt.Color(51, 51, 255));
        jLabel264.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel74.add(jLabel264, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel265.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel265.setForeground(new java.awt.Color(51, 51, 255));
        jLabel265.setText("VIERNES");
        jPanel74.add(jLabel265, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, -1, 40));

        jPanel75.setBackground(new java.awt.Color(255, 255, 255));
        jPanel75.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel75.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel266.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel266.setForeground(new java.awt.Color(51, 51, 255));
        jLabel266.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel75.add(jLabel266, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel267.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel267.setForeground(new java.awt.Color(51, 51, 255));
        jLabel267.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel75.add(jLabel267, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel268.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel268.setForeground(new java.awt.Color(51, 51, 255));
        jLabel268.setText("LUNES");
        jPanel75.add(jLabel268, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel74.add(jPanel75, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 350, 140, 60));

        jPanel76.setBackground(new java.awt.Color(255, 255, 255));
        jPanel76.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel76.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel269.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel269.setForeground(new java.awt.Color(51, 51, 255));
        jLabel269.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel76.add(jLabel269, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel270.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel270.setForeground(new java.awt.Color(51, 51, 255));
        jLabel270.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel76.add(jLabel270, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel271.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel271.setForeground(new java.awt.Color(51, 51, 255));
        jLabel271.setText("LUNES");
        jPanel76.add(jLabel271, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel77.setBackground(new java.awt.Color(255, 255, 255));
        jPanel77.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel77.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel272.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel272.setForeground(new java.awt.Color(51, 51, 255));
        jLabel272.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel77.add(jLabel272, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel273.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel273.setForeground(new java.awt.Color(51, 51, 255));
        jLabel273.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel77.add(jLabel273, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel274.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel274.setForeground(new java.awt.Color(51, 51, 255));
        jLabel274.setText("LUNES");
        jPanel77.add(jLabel274, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel76.add(jPanel77, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 350, 140, 60));

        jPanel74.add(jPanel76, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 350, 140, 60));

        jPanel1.add(jPanel74, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 660, 110, 30));

        jPanel95.setBackground(new java.awt.Color(255, 255, 255));
        jPanel95.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jPanel95.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel275.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel275.setForeground(new java.awt.Color(51, 51, 255));
        jLabel275.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel95.add(jLabel275, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel276.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel276.setForeground(new java.awt.Color(51, 51, 255));
        jLabel276.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel95.add(jLabel276, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel277.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel277.setForeground(new java.awt.Color(51, 51, 255));
        jLabel277.setText("JUEVES");
        jPanel95.add(jLabel277, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, -1, 40));

        jPanel96.setBackground(new java.awt.Color(255, 255, 255));
        jPanel96.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel96.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel278.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel278.setForeground(new java.awt.Color(51, 51, 255));
        jLabel278.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel96.add(jLabel278, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel279.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel279.setForeground(new java.awt.Color(51, 51, 255));
        jLabel279.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel96.add(jLabel279, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel280.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel280.setForeground(new java.awt.Color(51, 51, 255));
        jLabel280.setText("LUNES");
        jPanel96.add(jLabel280, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel95.add(jPanel96, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 350, 140, 60));

        jPanel97.setBackground(new java.awt.Color(255, 255, 255));
        jPanel97.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel97.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel281.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel281.setForeground(new java.awt.Color(51, 51, 255));
        jLabel281.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel97.add(jLabel281, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel282.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel282.setForeground(new java.awt.Color(51, 51, 255));
        jLabel282.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel97.add(jLabel282, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel283.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel283.setForeground(new java.awt.Color(51, 51, 255));
        jLabel283.setText("LUNES");
        jPanel97.add(jLabel283, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel98.setBackground(new java.awt.Color(255, 255, 255));
        jPanel98.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel98.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel284.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel284.setForeground(new java.awt.Color(51, 51, 255));
        jLabel284.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel98.add(jLabel284, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel285.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel285.setForeground(new java.awt.Color(51, 51, 255));
        jLabel285.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel98.add(jLabel285, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel286.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel286.setForeground(new java.awt.Color(51, 51, 255));
        jLabel286.setText("LUNES");
        jPanel98.add(jLabel286, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel97.add(jPanel98, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 350, 140, 60));

        jPanel95.add(jPanel97, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 350, 140, 60));

        jPanel1.add(jPanel95, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 660, 110, 30));

        jPanel99.setBackground(new java.awt.Color(255, 255, 255));
        jPanel99.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jPanel99.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel287.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel287.setForeground(new java.awt.Color(51, 51, 255));
        jLabel287.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel99.add(jLabel287, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel288.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel288.setForeground(new java.awt.Color(51, 51, 255));
        jLabel288.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel99.add(jLabel288, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel289.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel289.setForeground(new java.awt.Color(51, 51, 255));
        jLabel289.setText("MIERCOLES");
        jPanel99.add(jLabel289, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, 40));

        jPanel100.setBackground(new java.awt.Color(255, 255, 255));
        jPanel100.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel100.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel290.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel290.setForeground(new java.awt.Color(51, 51, 255));
        jLabel290.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel100.add(jLabel290, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel291.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel291.setForeground(new java.awt.Color(51, 51, 255));
        jLabel291.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel100.add(jLabel291, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel292.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel292.setForeground(new java.awt.Color(51, 51, 255));
        jLabel292.setText("LUNES");
        jPanel100.add(jLabel292, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel99.add(jPanel100, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 350, 140, 60));

        jPanel101.setBackground(new java.awt.Color(255, 255, 255));
        jPanel101.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel101.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel293.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel293.setForeground(new java.awt.Color(51, 51, 255));
        jLabel293.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel101.add(jLabel293, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel294.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel294.setForeground(new java.awt.Color(51, 51, 255));
        jLabel294.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel101.add(jLabel294, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel295.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel295.setForeground(new java.awt.Color(51, 51, 255));
        jLabel295.setText("LUNES");
        jPanel101.add(jLabel295, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel102.setBackground(new java.awt.Color(255, 255, 255));
        jPanel102.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel102.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel296.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel296.setForeground(new java.awt.Color(51, 51, 255));
        jLabel296.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel102.add(jLabel296, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel297.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel297.setForeground(new java.awt.Color(51, 51, 255));
        jLabel297.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel102.add(jLabel297, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel298.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel298.setForeground(new java.awt.Color(51, 51, 255));
        jLabel298.setText("LUNES");
        jPanel102.add(jLabel298, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel101.add(jPanel102, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 350, 140, 60));

        jPanel99.add(jPanel101, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 350, 140, 60));

        jPanel1.add(jPanel99, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 660, 110, 30));

        jPanel135.setBackground(new java.awt.Color(255, 255, 255));
        jPanel135.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jPanel135.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel463.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel463.setForeground(new java.awt.Color(51, 51, 255));
        jLabel463.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel135.add(jLabel463, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel464.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel464.setForeground(new java.awt.Color(51, 51, 255));
        jLabel464.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel135.add(jLabel464, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel465.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel465.setForeground(new java.awt.Color(51, 51, 255));
        jLabel465.setText("VIERNES");
        jPanel135.add(jLabel465, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, -1, 40));

        jPanel136.setBackground(new java.awt.Color(255, 255, 255));
        jPanel136.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel136.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel466.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel466.setForeground(new java.awt.Color(51, 51, 255));
        jLabel466.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel136.add(jLabel466, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel467.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel467.setForeground(new java.awt.Color(51, 51, 255));
        jLabel467.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel136.add(jLabel467, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel468.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel468.setForeground(new java.awt.Color(51, 51, 255));
        jLabel468.setText("LUNES");
        jPanel136.add(jLabel468, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel135.add(jPanel136, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 350, 140, 60));

        jPanel137.setBackground(new java.awt.Color(255, 255, 255));
        jPanel137.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel137.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel469.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel469.setForeground(new java.awt.Color(51, 51, 255));
        jLabel469.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel137.add(jLabel469, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel470.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel470.setForeground(new java.awt.Color(51, 51, 255));
        jLabel470.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel137.add(jLabel470, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel471.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel471.setForeground(new java.awt.Color(51, 51, 255));
        jLabel471.setText("LUNES");
        jPanel137.add(jLabel471, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel138.setBackground(new java.awt.Color(255, 255, 255));
        jPanel138.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel138.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel472.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel472.setForeground(new java.awt.Color(51, 51, 255));
        jLabel472.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel138.add(jLabel472, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel473.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel473.setForeground(new java.awt.Color(51, 51, 255));
        jLabel473.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel138.add(jLabel473, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel474.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel474.setForeground(new java.awt.Color(51, 51, 255));
        jLabel474.setText("LUNES");
        jPanel138.add(jLabel474, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel137.add(jPanel138, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 350, 140, 60));

        jPanel135.add(jPanel137, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 350, 140, 60));

        jPanel1.add(jPanel135, new org.netbeans.lib.awtextra.AbsoluteConstraints(1150, 660, 110, 30));

        jPanel111.setBackground(new java.awt.Color(255, 255, 255));
        jPanel111.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jPanel111.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel391.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel391.setForeground(new java.awt.Color(51, 51, 255));
        jLabel391.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel111.add(jLabel391, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel392.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel392.setForeground(new java.awt.Color(51, 51, 255));
        jLabel392.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel111.add(jLabel392, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel393.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel393.setForeground(new java.awt.Color(51, 51, 255));
        jLabel393.setText("MARTES");
        jPanel111.add(jLabel393, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, -1, 40));

        jPanel112.setBackground(new java.awt.Color(255, 255, 255));
        jPanel112.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel112.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel394.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel394.setForeground(new java.awt.Color(51, 51, 255));
        jLabel394.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel112.add(jLabel394, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel395.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel395.setForeground(new java.awt.Color(51, 51, 255));
        jLabel395.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel112.add(jLabel395, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel396.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel396.setForeground(new java.awt.Color(51, 51, 255));
        jLabel396.setText("LUNES");
        jPanel112.add(jLabel396, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel111.add(jPanel112, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 350, 140, 60));

        jPanel113.setBackground(new java.awt.Color(255, 255, 255));
        jPanel113.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel113.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel397.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel397.setForeground(new java.awt.Color(51, 51, 255));
        jLabel397.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel113.add(jLabel397, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel398.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel398.setForeground(new java.awt.Color(51, 51, 255));
        jLabel398.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel113.add(jLabel398, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel399.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel399.setForeground(new java.awt.Color(51, 51, 255));
        jLabel399.setText("LUNES");
        jPanel113.add(jLabel399, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel114.setBackground(new java.awt.Color(255, 255, 255));
        jPanel114.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel114.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel400.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel400.setForeground(new java.awt.Color(51, 51, 255));
        jLabel400.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel114.add(jLabel400, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel401.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel401.setForeground(new java.awt.Color(51, 51, 255));
        jLabel401.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel114.add(jLabel401, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel402.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel402.setForeground(new java.awt.Color(51, 51, 255));
        jLabel402.setText("LUNES");
        jPanel114.add(jLabel402, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel113.add(jPanel114, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 350, 140, 60));

        jPanel111.add(jPanel113, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 350, 140, 60));

        jPanel1.add(jPanel111, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 660, 110, 30));

        jPanel115.setBackground(new java.awt.Color(255, 255, 255));
        jPanel115.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jPanel115.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel403.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel403.setForeground(new java.awt.Color(51, 51, 255));
        jLabel403.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel115.add(jLabel403, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel404.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel404.setForeground(new java.awt.Color(51, 51, 255));
        jLabel404.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel115.add(jLabel404, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel405.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel405.setForeground(new java.awt.Color(51, 51, 255));
        jLabel405.setText("MIERCOLES");
        jPanel115.add(jLabel405, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, 40));

        jPanel116.setBackground(new java.awt.Color(255, 255, 255));
        jPanel116.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel116.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel406.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel406.setForeground(new java.awt.Color(51, 51, 255));
        jLabel406.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel116.add(jLabel406, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel407.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel407.setForeground(new java.awt.Color(51, 51, 255));
        jLabel407.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel116.add(jLabel407, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel408.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel408.setForeground(new java.awt.Color(51, 51, 255));
        jLabel408.setText("LUNES");
        jPanel116.add(jLabel408, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel115.add(jPanel116, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 350, 140, 60));

        jPanel117.setBackground(new java.awt.Color(255, 255, 255));
        jPanel117.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel117.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel409.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel409.setForeground(new java.awt.Color(51, 51, 255));
        jLabel409.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel117.add(jLabel409, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel410.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel410.setForeground(new java.awt.Color(51, 51, 255));
        jLabel410.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel117.add(jLabel410, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel411.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel411.setForeground(new java.awt.Color(51, 51, 255));
        jLabel411.setText("LUNES");
        jPanel117.add(jLabel411, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel118.setBackground(new java.awt.Color(255, 255, 255));
        jPanel118.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel118.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel412.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel412.setForeground(new java.awt.Color(51, 51, 255));
        jLabel412.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel118.add(jLabel412, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel413.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel413.setForeground(new java.awt.Color(51, 51, 255));
        jLabel413.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel118.add(jLabel413, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel414.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel414.setForeground(new java.awt.Color(51, 51, 255));
        jLabel414.setText("LUNES");
        jPanel118.add(jLabel414, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel117.add(jPanel118, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 350, 140, 60));

        jPanel115.add(jPanel117, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 350, 140, 60));

        jPanel1.add(jPanel115, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 660, 110, 30));

        jPanel103.setBackground(new java.awt.Color(255, 255, 255));
        jPanel103.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jPanel103.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel299.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel299.setForeground(new java.awt.Color(51, 51, 255));
        jLabel299.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel103.add(jLabel299, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel300.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel300.setForeground(new java.awt.Color(51, 51, 255));
        jLabel300.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel103.add(jLabel300, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel301.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel301.setForeground(new java.awt.Color(51, 51, 255));
        jLabel301.setText("VIERNES");
        jPanel103.add(jLabel301, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, -1, 40));

        jPanel104.setBackground(new java.awt.Color(255, 255, 255));
        jPanel104.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel104.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel302.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel302.setForeground(new java.awt.Color(51, 51, 255));
        jLabel302.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel104.add(jLabel302, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel303.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel303.setForeground(new java.awt.Color(51, 51, 255));
        jLabel303.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel104.add(jLabel303, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel304.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel304.setForeground(new java.awt.Color(51, 51, 255));
        jLabel304.setText("LUNES");
        jPanel104.add(jLabel304, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel103.add(jPanel104, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 350, 140, 60));

        jPanel105.setBackground(new java.awt.Color(255, 255, 255));
        jPanel105.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel105.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel305.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel305.setForeground(new java.awt.Color(51, 51, 255));
        jLabel305.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel105.add(jLabel305, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel306.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel306.setForeground(new java.awt.Color(51, 51, 255));
        jLabel306.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel105.add(jLabel306, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel307.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel307.setForeground(new java.awt.Color(51, 51, 255));
        jLabel307.setText("LUNES");
        jPanel105.add(jLabel307, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel106.setBackground(new java.awt.Color(255, 255, 255));
        jPanel106.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel106.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel308.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel308.setForeground(new java.awt.Color(51, 51, 255));
        jLabel308.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel106.add(jLabel308, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel309.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel309.setForeground(new java.awt.Color(51, 51, 255));
        jLabel309.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel106.add(jLabel309, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel310.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel310.setForeground(new java.awt.Color(51, 51, 255));
        jLabel310.setText("LUNES");
        jPanel106.add(jLabel310, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel105.add(jPanel106, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 350, 140, 60));

        jPanel103.add(jPanel105, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 350, 140, 60));

        jPanel1.add(jPanel103, new org.netbeans.lib.awtextra.AbsoluteConstraints(1150, 660, 110, 30));

        jPanel131.setBackground(new java.awt.Color(255, 255, 255));
        jPanel131.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jPanel131.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel451.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel451.setForeground(new java.awt.Color(51, 51, 255));
        jLabel451.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel131.add(jLabel451, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel452.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel452.setForeground(new java.awt.Color(51, 51, 255));
        jLabel452.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel131.add(jLabel452, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel453.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel453.setForeground(new java.awt.Color(51, 51, 255));
        jLabel453.setText("MARTES");
        jPanel131.add(jLabel453, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, -1, 40));

        jPanel132.setBackground(new java.awt.Color(255, 255, 255));
        jPanel132.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel132.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel454.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel454.setForeground(new java.awt.Color(51, 51, 255));
        jLabel454.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel132.add(jLabel454, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel455.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel455.setForeground(new java.awt.Color(51, 51, 255));
        jLabel455.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel132.add(jLabel455, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel456.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel456.setForeground(new java.awt.Color(51, 51, 255));
        jLabel456.setText("LUNES");
        jPanel132.add(jLabel456, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel131.add(jPanel132, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 350, 140, 60));

        jPanel133.setBackground(new java.awt.Color(255, 255, 255));
        jPanel133.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel133.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel457.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel457.setForeground(new java.awt.Color(51, 51, 255));
        jLabel457.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel133.add(jLabel457, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel458.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel458.setForeground(new java.awt.Color(51, 51, 255));
        jLabel458.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel133.add(jLabel458, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel459.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel459.setForeground(new java.awt.Color(51, 51, 255));
        jLabel459.setText("LUNES");
        jPanel133.add(jLabel459, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel134.setBackground(new java.awt.Color(255, 255, 255));
        jPanel134.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel134.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel460.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel460.setForeground(new java.awt.Color(51, 51, 255));
        jLabel460.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel134.add(jLabel460, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel461.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel461.setForeground(new java.awt.Color(51, 51, 255));
        jLabel461.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel134.add(jLabel461, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel462.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel462.setForeground(new java.awt.Color(51, 51, 255));
        jLabel462.setText("LUNES");
        jPanel134.add(jLabel462, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel133.add(jPanel134, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 350, 140, 60));

        jPanel131.add(jPanel133, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 350, 140, 60));

        jPanel1.add(jPanel131, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 660, 110, 30));

        jPanel127.setBackground(new java.awt.Color(255, 255, 255));
        jPanel127.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jPanel127.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel439.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel439.setForeground(new java.awt.Color(51, 51, 255));
        jLabel439.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel127.add(jLabel439, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel440.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel440.setForeground(new java.awt.Color(51, 51, 255));
        jLabel440.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel127.add(jLabel440, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel441.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel441.setForeground(new java.awt.Color(51, 51, 255));
        jLabel441.setText("LUNES");
        jPanel127.add(jLabel441, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, -1, 40));

        jPanel128.setBackground(new java.awt.Color(255, 255, 255));
        jPanel128.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel128.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel442.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel442.setForeground(new java.awt.Color(51, 51, 255));
        jLabel442.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel128.add(jLabel442, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel443.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel443.setForeground(new java.awt.Color(51, 51, 255));
        jLabel443.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel128.add(jLabel443, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel444.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel444.setForeground(new java.awt.Color(51, 51, 255));
        jLabel444.setText("LUNES");
        jPanel128.add(jLabel444, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel127.add(jPanel128, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 350, 140, 60));

        jPanel129.setBackground(new java.awt.Color(255, 255, 255));
        jPanel129.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel129.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel445.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel445.setForeground(new java.awt.Color(51, 51, 255));
        jLabel445.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel129.add(jLabel445, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel446.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel446.setForeground(new java.awt.Color(51, 51, 255));
        jLabel446.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel129.add(jLabel446, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel447.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel447.setForeground(new java.awt.Color(51, 51, 255));
        jLabel447.setText("LUNES");
        jPanel129.add(jLabel447, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel130.setBackground(new java.awt.Color(255, 255, 255));
        jPanel130.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel130.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel448.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel448.setForeground(new java.awt.Color(51, 51, 255));
        jLabel448.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel130.add(jLabel448, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel449.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel449.setForeground(new java.awt.Color(51, 51, 255));
        jLabel449.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel130.add(jLabel449, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel450.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel450.setForeground(new java.awt.Color(51, 51, 255));
        jLabel450.setText("LUNES");
        jPanel130.add(jLabel450, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel129.add(jPanel130, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 350, 140, 60));

        jPanel127.add(jPanel129, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 350, 140, 60));

        jPanel1.add(jPanel127, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 660, 110, 30));

        jPanel119.setBackground(new java.awt.Color(255, 255, 255));
        jPanel119.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jPanel119.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel415.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel415.setForeground(new java.awt.Color(51, 51, 255));
        jLabel415.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel119.add(jLabel415, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel416.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel416.setForeground(new java.awt.Color(51, 51, 255));
        jLabel416.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel119.add(jLabel416, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel417.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel417.setForeground(new java.awt.Color(51, 51, 255));
        jLabel417.setText("LUNES");
        jPanel119.add(jLabel417, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, -1, 40));

        jPanel120.setBackground(new java.awt.Color(255, 255, 255));
        jPanel120.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel120.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel418.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel418.setForeground(new java.awt.Color(51, 51, 255));
        jLabel418.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel120.add(jLabel418, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel419.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel419.setForeground(new java.awt.Color(51, 51, 255));
        jLabel419.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel120.add(jLabel419, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel420.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel420.setForeground(new java.awt.Color(51, 51, 255));
        jLabel420.setText("LUNES");
        jPanel120.add(jLabel420, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel119.add(jPanel120, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 350, 140, 60));

        jPanel121.setBackground(new java.awt.Color(255, 255, 255));
        jPanel121.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel121.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel421.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel421.setForeground(new java.awt.Color(51, 51, 255));
        jLabel421.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel121.add(jLabel421, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel422.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel422.setForeground(new java.awt.Color(51, 51, 255));
        jLabel422.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel121.add(jLabel422, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel423.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel423.setForeground(new java.awt.Color(51, 51, 255));
        jLabel423.setText("LUNES");
        jPanel121.add(jLabel423, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel122.setBackground(new java.awt.Color(255, 255, 255));
        jPanel122.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel122.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel424.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel424.setForeground(new java.awt.Color(51, 51, 255));
        jLabel424.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel122.add(jLabel424, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel425.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel425.setForeground(new java.awt.Color(51, 51, 255));
        jLabel425.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel122.add(jLabel425, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel426.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel426.setForeground(new java.awt.Color(51, 51, 255));
        jLabel426.setText("LUNES");
        jPanel122.add(jLabel426, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel121.add(jPanel122, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 350, 140, 60));

        jPanel119.add(jPanel121, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 350, 140, 60));

        jPanel1.add(jPanel119, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 660, 110, 30));

        jPanel139.setBackground(new java.awt.Color(255, 255, 255));
        jPanel139.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jPanel139.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel475.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel475.setForeground(new java.awt.Color(51, 51, 255));
        jLabel475.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel139.add(jLabel475, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel476.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel476.setForeground(new java.awt.Color(51, 51, 255));
        jLabel476.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel139.add(jLabel476, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel477.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel477.setForeground(new java.awt.Color(51, 51, 255));
        jLabel477.setText("JUEVES");
        jPanel139.add(jLabel477, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, -1, 40));

        jPanel140.setBackground(new java.awt.Color(255, 255, 255));
        jPanel140.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel140.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel478.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel478.setForeground(new java.awt.Color(51, 51, 255));
        jLabel478.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel140.add(jLabel478, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel479.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel479.setForeground(new java.awt.Color(51, 51, 255));
        jLabel479.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel140.add(jLabel479, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel480.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel480.setForeground(new java.awt.Color(51, 51, 255));
        jLabel480.setText("LUNES");
        jPanel140.add(jLabel480, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel139.add(jPanel140, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 350, 140, 60));

        jPanel141.setBackground(new java.awt.Color(255, 255, 255));
        jPanel141.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel141.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel481.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel481.setForeground(new java.awt.Color(51, 51, 255));
        jLabel481.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel141.add(jLabel481, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel482.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel482.setForeground(new java.awt.Color(51, 51, 255));
        jLabel482.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel141.add(jLabel482, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel483.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel483.setForeground(new java.awt.Color(51, 51, 255));
        jLabel483.setText("LUNES");
        jPanel141.add(jLabel483, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel142.setBackground(new java.awt.Color(255, 255, 255));
        jPanel142.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel142.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel484.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel484.setForeground(new java.awt.Color(51, 51, 255));
        jLabel484.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel142.add(jLabel484, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel485.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel485.setForeground(new java.awt.Color(51, 51, 255));
        jLabel485.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel142.add(jLabel485, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel486.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel486.setForeground(new java.awt.Color(51, 51, 255));
        jLabel486.setText("LUNES");
        jPanel142.add(jLabel486, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel141.add(jPanel142, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 350, 140, 60));

        jPanel139.add(jPanel141, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 350, 140, 60));

        jPanel1.add(jPanel139, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 660, 110, 30));

        jPanel107.setBackground(new java.awt.Color(255, 255, 255));
        jPanel107.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jPanel107.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel311.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel311.setForeground(new java.awt.Color(51, 51, 255));
        jLabel311.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel107.add(jLabel311, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel312.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel312.setForeground(new java.awt.Color(51, 51, 255));
        jLabel312.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel107.add(jLabel312, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel313.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel313.setForeground(new java.awt.Color(51, 51, 255));
        jLabel313.setText("LUNES");
        jPanel107.add(jLabel313, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, -1, 40));

        jPanel108.setBackground(new java.awt.Color(255, 255, 255));
        jPanel108.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel108.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel314.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel314.setForeground(new java.awt.Color(51, 51, 255));
        jLabel314.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel108.add(jLabel314, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel383.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel383.setForeground(new java.awt.Color(51, 51, 255));
        jLabel383.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel108.add(jLabel383, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel384.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel384.setForeground(new java.awt.Color(51, 51, 255));
        jLabel384.setText("LUNES");
        jPanel108.add(jLabel384, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel107.add(jPanel108, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 350, 140, 60));

        jPanel109.setBackground(new java.awt.Color(255, 255, 255));
        jPanel109.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel109.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel385.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel385.setForeground(new java.awt.Color(51, 51, 255));
        jLabel385.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel109.add(jLabel385, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel386.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel386.setForeground(new java.awt.Color(51, 51, 255));
        jLabel386.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel109.add(jLabel386, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel387.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel387.setForeground(new java.awt.Color(51, 51, 255));
        jLabel387.setText("LUNES");
        jPanel109.add(jLabel387, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel110.setBackground(new java.awt.Color(255, 255, 255));
        jPanel110.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel110.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel388.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel388.setForeground(new java.awt.Color(51, 51, 255));
        jLabel388.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel110.add(jLabel388, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel389.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel389.setForeground(new java.awt.Color(51, 51, 255));
        jLabel389.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel110.add(jLabel389, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel390.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel390.setForeground(new java.awt.Color(51, 51, 255));
        jLabel390.setText("LUNES");
        jPanel110.add(jLabel390, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel109.add(jPanel110, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 350, 140, 60));

        jPanel107.add(jPanel109, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 350, 140, 60));

        jPanel1.add(jPanel107, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 660, 110, 30));

        jPanel123.setBackground(new java.awt.Color(255, 255, 255));
        jPanel123.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jPanel123.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel427.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel427.setForeground(new java.awt.Color(51, 51, 255));
        jLabel427.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel123.add(jLabel427, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel428.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel428.setForeground(new java.awt.Color(51, 51, 255));
        jLabel428.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel123.add(jLabel428, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel429.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel429.setForeground(new java.awt.Color(51, 51, 255));
        jLabel429.setText("JUEVES");
        jPanel123.add(jLabel429, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 0, -1, 40));

        jPanel124.setBackground(new java.awt.Color(255, 255, 255));
        jPanel124.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel124.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel430.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel430.setForeground(new java.awt.Color(51, 51, 255));
        jLabel430.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel124.add(jLabel430, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel431.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel431.setForeground(new java.awt.Color(51, 51, 255));
        jLabel431.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel124.add(jLabel431, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel432.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel432.setForeground(new java.awt.Color(51, 51, 255));
        jLabel432.setText("LUNES");
        jPanel124.add(jLabel432, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel123.add(jPanel124, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 350, 140, 60));

        jPanel125.setBackground(new java.awt.Color(255, 255, 255));
        jPanel125.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel125.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel433.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel433.setForeground(new java.awt.Color(51, 51, 255));
        jLabel433.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel125.add(jLabel433, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel434.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel434.setForeground(new java.awt.Color(51, 51, 255));
        jLabel434.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel125.add(jLabel434, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel435.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel435.setForeground(new java.awt.Color(51, 51, 255));
        jLabel435.setText("LUNES");
        jPanel125.add(jLabel435, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel126.setBackground(new java.awt.Color(255, 255, 255));
        jPanel126.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel126.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel436.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel436.setForeground(new java.awt.Color(51, 51, 255));
        jLabel436.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel126.add(jLabel436, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel437.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel437.setForeground(new java.awt.Color(51, 51, 255));
        jLabel437.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel126.add(jLabel437, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel438.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel438.setForeground(new java.awt.Color(51, 51, 255));
        jLabel438.setText("LUNES");
        jPanel126.add(jLabel438, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel125.add(jPanel126, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 350, 140, 60));

        jPanel123.add(jPanel125, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 350, 140, 60));

        jPanel1.add(jPanel123, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 660, 110, 30));

        jPanel143.setBackground(new java.awt.Color(255, 255, 255));
        jPanel143.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        jPanel143.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel487.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel487.setForeground(new java.awt.Color(51, 51, 255));
        jLabel487.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel143.add(jLabel487, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel488.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel488.setForeground(new java.awt.Color(51, 51, 255));
        jLabel488.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel143.add(jLabel488, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel489.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel489.setForeground(new java.awt.Color(51, 51, 255));
        jLabel489.setText("MIERCOLES");
        jPanel143.add(jLabel489, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, 40));

        jPanel144.setBackground(new java.awt.Color(255, 255, 255));
        jPanel144.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel144.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel490.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel490.setForeground(new java.awt.Color(51, 51, 255));
        jLabel490.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel144.add(jLabel490, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel491.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel491.setForeground(new java.awt.Color(51, 51, 255));
        jLabel491.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel144.add(jLabel491, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel492.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel492.setForeground(new java.awt.Color(51, 51, 255));
        jLabel492.setText("LUNES");
        jPanel144.add(jLabel492, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel143.add(jPanel144, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 350, 140, 60));

        jPanel145.setBackground(new java.awt.Color(255, 255, 255));
        jPanel145.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel145.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel493.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel493.setForeground(new java.awt.Color(51, 51, 255));
        jLabel493.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel145.add(jLabel493, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel494.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel494.setForeground(new java.awt.Color(51, 51, 255));
        jLabel494.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel145.add(jLabel494, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel495.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel495.setForeground(new java.awt.Color(51, 51, 255));
        jLabel495.setText("LUNES");
        jPanel145.add(jLabel495, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel146.setBackground(new java.awt.Color(255, 255, 255));
        jPanel146.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 255), 6, true));
        jPanel146.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel496.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel496.setForeground(new java.awt.Color(51, 51, 255));
        jLabel496.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel146.add(jLabel496, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel497.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel497.setForeground(new java.awt.Color(51, 51, 255));
        jLabel497.setText("Head Count (Necesario) /Turno:\t\t\t ");
        jPanel146.add(jLabel497, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, -1, -1));

        jLabel498.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel498.setForeground(new java.awt.Color(51, 51, 255));
        jLabel498.setText("LUNES");
        jPanel146.add(jLabel498, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, 40));

        jPanel145.add(jPanel146, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 350, 140, 60));

        jPanel143.add(jPanel145, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 350, 140, 60));

        jPanel1.add(jPanel143, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 660, 110, 30));

        btnExportar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/excel-xls-icon.png"))); // NOI18N
        btnExportar.setText("EXPORTAR");
        btnExportar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportarActionPerformed(evt);
            }
        });
        jPanel1.add(btnExportar, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 570, -1, 80));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1260, 1000));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        // TODO add your handling code here:

        try {
            TOTAL_TA();
        } catch (Exception e) {

        }
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnExportarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportarActionPerformed

        try {
            exportarjTable(jTable1, " calculo1000");
//                 excel_layoff exportar=new excel_layoff((DefaultTableModel)jTable1.getModel(), "CALCULO DE LAYOFF2");
            System.out.println(" archivo exportado");
        } catch (Exception e) {
            System.out.println(e.toString() + " archivo no exportado");
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
            java.util.logging.Logger.getLogger(layoff_A.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(layoff_A.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(layoff_A.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(layoff_A.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                layoff_A dialog = new layoff_A(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Tdep_jue;
    private javax.swing.JLabel Tdep_jue2;
    private javax.swing.JLabel Tdep_ma;
    private javax.swing.JLabel Tdep_ma2;
    private javax.swing.JLabel Tdep_mi;
    private javax.swing.JLabel Tdep_mi2;
    private javax.swing.JLabel Tdep_vie;
    private javax.swing.JLabel Tdep_vie2;
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnExportar;
    private javax.swing.JLabel dep62jue_loff;
    private javax.swing.JLabel dep62jue_loff2;
    private javax.swing.JLabel dep62ma_loff;
    private javax.swing.JLabel dep62ma_loff2;
    private javax.swing.JLabel dep62mi_loff;
    private javax.swing.JLabel dep62mi_loff2;
    private javax.swing.JLabel dep62vie_loff;
    private javax.swing.JLabel dep62vie_loff2;
    private javax.swing.JLabel dep63jue_loff;
    private javax.swing.JLabel dep63jue_loff2;
    private javax.swing.JLabel dep63ma_loff;
    private javax.swing.JLabel dep63ma_loff2;
    private javax.swing.JLabel dep63mi_loff;
    private javax.swing.JLabel dep63mi_loff2;
    private javax.swing.JLabel dep63vie_loff;
    private javax.swing.JLabel dep63vie_loff2;
    private javax.swing.JLabel depa62_jue;
    private javax.swing.JLabel depa62_jue2;
    private javax.swing.JLabel depa62_marts;
    private javax.swing.JLabel depa62_marts2;
    private javax.swing.JLabel depa62_mierc;
    private javax.swing.JLabel depa62_mierc2;
    private javax.swing.JLabel depa62_vie;
    private javax.swing.JLabel depa62_vie2;
    private javax.swing.JLabel depa63_jue;
    private javax.swing.JLabel depa63_jue2;
    private javax.swing.JLabel depa63_marts;
    private javax.swing.JLabel depa63_marts2;
    private javax.swing.JLabel depa63_mierc;
    private javax.swing.JLabel depa63_mierc2;
    private javax.swing.JLabel depa63_vie;
    private javax.swing.JLabel depa63_vie2;
    private javax.swing.JLabel depa_62;
    private javax.swing.JLabel depa_62_2;
    private javax.swing.JLabel depa_63;
    private javax.swing.JLabel depa_63_2;
    private javax.swing.JLabel dpo_25962;
    private javax.swing.JLabel dpo_62;
    private javax.swing.JLabel dpo_62_2;
    private javax.swing.JLabel dpto_25963;
    private javax.swing.JLabel dpto_63;
    private javax.swing.JLabel dpto_63_2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel100;
    private javax.swing.JLabel jLabel101;
    private javax.swing.JLabel jLabel103;
    private javax.swing.JLabel jLabel104;
    private javax.swing.JLabel jLabel105;
    private javax.swing.JLabel jLabel106;
    private javax.swing.JLabel jLabel108;
    private javax.swing.JLabel jLabel109;
    private javax.swing.JLabel jLabel110;
    private javax.swing.JLabel jLabel111;
    private javax.swing.JLabel jLabel115;
    private javax.swing.JLabel jLabel120;
    private javax.swing.JLabel jLabel125;
    private javax.swing.JLabel jLabel128;
    private javax.swing.JLabel jLabel129;
    private javax.swing.JLabel jLabel130;
    private javax.swing.JLabel jLabel146;
    private javax.swing.JLabel jLabel147;
    private javax.swing.JLabel jLabel148;
    private javax.swing.JLabel jLabel149;
    private javax.swing.JLabel jLabel150;
    private javax.swing.JLabel jLabel151;
    private javax.swing.JLabel jLabel152;
    private javax.swing.JLabel jLabel153;
    private javax.swing.JLabel jLabel154;
    private javax.swing.JLabel jLabel155;
    private javax.swing.JLabel jLabel156;
    private javax.swing.JLabel jLabel157;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel164;
    private javax.swing.JLabel jLabel165;
    private javax.swing.JLabel jLabel166;
    private javax.swing.JLabel jLabel167;
    private javax.swing.JLabel jLabel168;
    private javax.swing.JLabel jLabel169;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel170;
    private javax.swing.JLabel jLabel171;
    private javax.swing.JLabel jLabel172;
    private javax.swing.JLabel jLabel173;
    private javax.swing.JLabel jLabel174;
    private javax.swing.JLabel jLabel175;
    private javax.swing.JLabel jLabel176;
    private javax.swing.JLabel jLabel177;
    private javax.swing.JLabel jLabel178;
    private javax.swing.JLabel jLabel179;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel180;
    private javax.swing.JLabel jLabel181;
    private javax.swing.JLabel jLabel182;
    private javax.swing.JLabel jLabel183;
    private javax.swing.JLabel jLabel184;
    private javax.swing.JLabel jLabel185;
    private javax.swing.JLabel jLabel186;
    private javax.swing.JLabel jLabel187;
    private javax.swing.JLabel jLabel188;
    private javax.swing.JLabel jLabel189;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel190;
    private javax.swing.JLabel jLabel191;
    private javax.swing.JLabel jLabel192;
    private javax.swing.JLabel jLabel193;
    private javax.swing.JLabel jLabel194;
    private javax.swing.JLabel jLabel195;
    private javax.swing.JLabel jLabel196;
    private javax.swing.JLabel jLabel197;
    private javax.swing.JLabel jLabel198;
    private javax.swing.JLabel jLabel199;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel200;
    private javax.swing.JLabel jLabel201;
    private javax.swing.JLabel jLabel202;
    private javax.swing.JLabel jLabel203;
    private javax.swing.JLabel jLabel204;
    private javax.swing.JLabel jLabel205;
    private javax.swing.JLabel jLabel206;
    private javax.swing.JLabel jLabel207;
    private javax.swing.JLabel jLabel208;
    private javax.swing.JLabel jLabel209;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel210;
    private javax.swing.JLabel jLabel211;
    private javax.swing.JLabel jLabel212;
    private javax.swing.JLabel jLabel213;
    private javax.swing.JLabel jLabel214;
    private javax.swing.JLabel jLabel215;
    private javax.swing.JLabel jLabel216;
    private javax.swing.JLabel jLabel217;
    private javax.swing.JLabel jLabel218;
    private javax.swing.JLabel jLabel219;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel220;
    private javax.swing.JLabel jLabel221;
    private javax.swing.JLabel jLabel222;
    private javax.swing.JLabel jLabel223;
    private javax.swing.JLabel jLabel224;
    private javax.swing.JLabel jLabel225;
    private javax.swing.JLabel jLabel226;
    private javax.swing.JLabel jLabel227;
    private javax.swing.JLabel jLabel228;
    private javax.swing.JLabel jLabel229;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel230;
    private javax.swing.JLabel jLabel231;
    private javax.swing.JLabel jLabel232;
    private javax.swing.JLabel jLabel233;
    private javax.swing.JLabel jLabel234;
    private javax.swing.JLabel jLabel235;
    private javax.swing.JLabel jLabel236;
    private javax.swing.JLabel jLabel237;
    private javax.swing.JLabel jLabel238;
    private javax.swing.JLabel jLabel239;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel240;
    private javax.swing.JLabel jLabel241;
    private javax.swing.JLabel jLabel242;
    private javax.swing.JLabel jLabel243;
    private javax.swing.JLabel jLabel244;
    private javax.swing.JLabel jLabel245;
    private javax.swing.JLabel jLabel246;
    private javax.swing.JLabel jLabel247;
    private javax.swing.JLabel jLabel248;
    private javax.swing.JLabel jLabel249;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel250;
    private javax.swing.JLabel jLabel251;
    private javax.swing.JLabel jLabel252;
    private javax.swing.JLabel jLabel253;
    private javax.swing.JLabel jLabel254;
    private javax.swing.JLabel jLabel255;
    private javax.swing.JLabel jLabel256;
    private javax.swing.JLabel jLabel257;
    private javax.swing.JLabel jLabel258;
    private javax.swing.JLabel jLabel259;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel260;
    private javax.swing.JLabel jLabel261;
    private javax.swing.JLabel jLabel262;
    private javax.swing.JLabel jLabel263;
    private javax.swing.JLabel jLabel264;
    private javax.swing.JLabel jLabel265;
    private javax.swing.JLabel jLabel266;
    private javax.swing.JLabel jLabel267;
    private javax.swing.JLabel jLabel268;
    private javax.swing.JLabel jLabel269;
    private javax.swing.JLabel jLabel270;
    private javax.swing.JLabel jLabel271;
    private javax.swing.JLabel jLabel272;
    private javax.swing.JLabel jLabel273;
    private javax.swing.JLabel jLabel274;
    private javax.swing.JLabel jLabel275;
    private javax.swing.JLabel jLabel276;
    private javax.swing.JLabel jLabel277;
    private javax.swing.JLabel jLabel278;
    private javax.swing.JLabel jLabel279;
    private javax.swing.JLabel jLabel280;
    private javax.swing.JLabel jLabel281;
    private javax.swing.JLabel jLabel282;
    private javax.swing.JLabel jLabel283;
    private javax.swing.JLabel jLabel284;
    private javax.swing.JLabel jLabel285;
    private javax.swing.JLabel jLabel286;
    private javax.swing.JLabel jLabel287;
    private javax.swing.JLabel jLabel288;
    private javax.swing.JLabel jLabel289;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel290;
    private javax.swing.JLabel jLabel291;
    private javax.swing.JLabel jLabel292;
    private javax.swing.JLabel jLabel293;
    private javax.swing.JLabel jLabel294;
    private javax.swing.JLabel jLabel295;
    private javax.swing.JLabel jLabel296;
    private javax.swing.JLabel jLabel297;
    private javax.swing.JLabel jLabel298;
    private javax.swing.JLabel jLabel299;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel300;
    private javax.swing.JLabel jLabel301;
    private javax.swing.JLabel jLabel302;
    private javax.swing.JLabel jLabel303;
    private javax.swing.JLabel jLabel304;
    private javax.swing.JLabel jLabel305;
    private javax.swing.JLabel jLabel306;
    private javax.swing.JLabel jLabel307;
    private javax.swing.JLabel jLabel308;
    private javax.swing.JLabel jLabel309;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel310;
    private javax.swing.JLabel jLabel311;
    private javax.swing.JLabel jLabel312;
    private javax.swing.JLabel jLabel313;
    private javax.swing.JLabel jLabel314;
    private javax.swing.JLabel jLabel315;
    private javax.swing.JLabel jLabel316;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel341;
    private javax.swing.JLabel jLabel342;
    private javax.swing.JLabel jLabel343;
    private javax.swing.JLabel jLabel344;
    private javax.swing.JLabel jLabel345;
    private javax.swing.JLabel jLabel346;
    private javax.swing.JLabel jLabel347;
    private javax.swing.JLabel jLabel348;
    private javax.swing.JLabel jLabel349;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel350;
    private javax.swing.JLabel jLabel357;
    private javax.swing.JLabel jLabel358;
    private javax.swing.JLabel jLabel359;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel360;
    private javax.swing.JLabel jLabel361;
    private javax.swing.JLabel jLabel362;
    private javax.swing.JLabel jLabel363;
    private javax.swing.JLabel jLabel364;
    private javax.swing.JLabel jLabel365;
    private javax.swing.JLabel jLabel366;
    private javax.swing.JLabel jLabel367;
    private javax.swing.JLabel jLabel368;
    private javax.swing.JLabel jLabel369;
    private javax.swing.JLabel jLabel370;
    private javax.swing.JLabel jLabel371;
    private javax.swing.JLabel jLabel372;
    private javax.swing.JLabel jLabel373;
    private javax.swing.JLabel jLabel374;
    private javax.swing.JLabel jLabel375;
    private javax.swing.JLabel jLabel376;
    private javax.swing.JLabel jLabel383;
    private javax.swing.JLabel jLabel384;
    private javax.swing.JLabel jLabel385;
    private javax.swing.JLabel jLabel386;
    private javax.swing.JLabel jLabel387;
    private javax.swing.JLabel jLabel388;
    private javax.swing.JLabel jLabel389;
    private javax.swing.JLabel jLabel390;
    private javax.swing.JLabel jLabel391;
    private javax.swing.JLabel jLabel392;
    private javax.swing.JLabel jLabel393;
    private javax.swing.JLabel jLabel394;
    private javax.swing.JLabel jLabel395;
    private javax.swing.JLabel jLabel396;
    private javax.swing.JLabel jLabel397;
    private javax.swing.JLabel jLabel398;
    private javax.swing.JLabel jLabel399;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel400;
    private javax.swing.JLabel jLabel401;
    private javax.swing.JLabel jLabel402;
    private javax.swing.JLabel jLabel403;
    private javax.swing.JLabel jLabel404;
    private javax.swing.JLabel jLabel405;
    private javax.swing.JLabel jLabel406;
    private javax.swing.JLabel jLabel407;
    private javax.swing.JLabel jLabel408;
    private javax.swing.JLabel jLabel409;
    private javax.swing.JLabel jLabel410;
    private javax.swing.JLabel jLabel411;
    private javax.swing.JLabel jLabel412;
    private javax.swing.JLabel jLabel413;
    private javax.swing.JLabel jLabel414;
    private javax.swing.JLabel jLabel415;
    private javax.swing.JLabel jLabel416;
    private javax.swing.JLabel jLabel417;
    private javax.swing.JLabel jLabel418;
    private javax.swing.JLabel jLabel419;
    private javax.swing.JLabel jLabel420;
    private javax.swing.JLabel jLabel421;
    private javax.swing.JLabel jLabel422;
    private javax.swing.JLabel jLabel423;
    private javax.swing.JLabel jLabel424;
    private javax.swing.JLabel jLabel425;
    private javax.swing.JLabel jLabel426;
    private javax.swing.JLabel jLabel427;
    private javax.swing.JLabel jLabel428;
    private javax.swing.JLabel jLabel429;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel430;
    private javax.swing.JLabel jLabel431;
    private javax.swing.JLabel jLabel432;
    private javax.swing.JLabel jLabel433;
    private javax.swing.JLabel jLabel434;
    private javax.swing.JLabel jLabel435;
    private javax.swing.JLabel jLabel436;
    private javax.swing.JLabel jLabel437;
    private javax.swing.JLabel jLabel438;
    private javax.swing.JLabel jLabel439;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel440;
    private javax.swing.JLabel jLabel441;
    private javax.swing.JLabel jLabel442;
    private javax.swing.JLabel jLabel443;
    private javax.swing.JLabel jLabel444;
    private javax.swing.JLabel jLabel445;
    private javax.swing.JLabel jLabel446;
    private javax.swing.JLabel jLabel447;
    private javax.swing.JLabel jLabel448;
    private javax.swing.JLabel jLabel449;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel450;
    private javax.swing.JLabel jLabel451;
    private javax.swing.JLabel jLabel452;
    private javax.swing.JLabel jLabel453;
    private javax.swing.JLabel jLabel454;
    private javax.swing.JLabel jLabel455;
    private javax.swing.JLabel jLabel456;
    private javax.swing.JLabel jLabel457;
    private javax.swing.JLabel jLabel458;
    private javax.swing.JLabel jLabel459;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel460;
    private javax.swing.JLabel jLabel461;
    private javax.swing.JLabel jLabel462;
    private javax.swing.JLabel jLabel463;
    private javax.swing.JLabel jLabel464;
    private javax.swing.JLabel jLabel465;
    private javax.swing.JLabel jLabel466;
    private javax.swing.JLabel jLabel467;
    private javax.swing.JLabel jLabel468;
    private javax.swing.JLabel jLabel469;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel470;
    private javax.swing.JLabel jLabel471;
    private javax.swing.JLabel jLabel472;
    private javax.swing.JLabel jLabel473;
    private javax.swing.JLabel jLabel474;
    private javax.swing.JLabel jLabel475;
    private javax.swing.JLabel jLabel476;
    private javax.swing.JLabel jLabel477;
    private javax.swing.JLabel jLabel478;
    private javax.swing.JLabel jLabel479;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel480;
    private javax.swing.JLabel jLabel481;
    private javax.swing.JLabel jLabel482;
    private javax.swing.JLabel jLabel483;
    private javax.swing.JLabel jLabel484;
    private javax.swing.JLabel jLabel485;
    private javax.swing.JLabel jLabel486;
    private javax.swing.JLabel jLabel487;
    private javax.swing.JLabel jLabel488;
    private javax.swing.JLabel jLabel489;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel490;
    private javax.swing.JLabel jLabel491;
    private javax.swing.JLabel jLabel492;
    private javax.swing.JLabel jLabel493;
    private javax.swing.JLabel jLabel494;
    private javax.swing.JLabel jLabel495;
    private javax.swing.JLabel jLabel496;
    private javax.swing.JLabel jLabel497;
    private javax.swing.JLabel jLabel498;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JLabel jLabel98;
    private javax.swing.JLabel jLabel99;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel100;
    private javax.swing.JPanel jPanel101;
    private javax.swing.JPanel jPanel102;
    private javax.swing.JPanel jPanel103;
    private javax.swing.JPanel jPanel104;
    private javax.swing.JPanel jPanel105;
    private javax.swing.JPanel jPanel106;
    private javax.swing.JPanel jPanel107;
    private javax.swing.JPanel jPanel108;
    private javax.swing.JPanel jPanel109;
    private javax.swing.JPanel jPanel110;
    private javax.swing.JPanel jPanel111;
    private javax.swing.JPanel jPanel112;
    private javax.swing.JPanel jPanel113;
    private javax.swing.JPanel jPanel114;
    private javax.swing.JPanel jPanel115;
    private javax.swing.JPanel jPanel116;
    private javax.swing.JPanel jPanel117;
    private javax.swing.JPanel jPanel118;
    private javax.swing.JPanel jPanel119;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel120;
    private javax.swing.JPanel jPanel121;
    private javax.swing.JPanel jPanel122;
    private javax.swing.JPanel jPanel123;
    private javax.swing.JPanel jPanel124;
    private javax.swing.JPanel jPanel125;
    private javax.swing.JPanel jPanel126;
    private javax.swing.JPanel jPanel127;
    private javax.swing.JPanel jPanel128;
    private javax.swing.JPanel jPanel129;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel130;
    private javax.swing.JPanel jPanel131;
    private javax.swing.JPanel jPanel132;
    private javax.swing.JPanel jPanel133;
    private javax.swing.JPanel jPanel134;
    private javax.swing.JPanel jPanel135;
    private javax.swing.JPanel jPanel136;
    private javax.swing.JPanel jPanel137;
    private javax.swing.JPanel jPanel138;
    private javax.swing.JPanel jPanel139;
    private javax.swing.JPanel jPanel140;
    private javax.swing.JPanel jPanel141;
    private javax.swing.JPanel jPanel142;
    private javax.swing.JPanel jPanel143;
    private javax.swing.JPanel jPanel144;
    private javax.swing.JPanel jPanel145;
    private javax.swing.JPanel jPanel146;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel42;
    private javax.swing.JPanel jPanel43;
    private javax.swing.JPanel jPanel44;
    private javax.swing.JPanel jPanel45;
    private javax.swing.JPanel jPanel46;
    private javax.swing.JPanel jPanel47;
    private javax.swing.JPanel jPanel48;
    private javax.swing.JPanel jPanel49;
    private javax.swing.JPanel jPanel50;
    private javax.swing.JPanel jPanel51;
    private javax.swing.JPanel jPanel52;
    private javax.swing.JPanel jPanel53;
    private javax.swing.JPanel jPanel54;
    private javax.swing.JPanel jPanel55;
    private javax.swing.JPanel jPanel56;
    private javax.swing.JPanel jPanel57;
    private javax.swing.JPanel jPanel58;
    private javax.swing.JPanel jPanel59;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel60;
    private javax.swing.JPanel jPanel61;
    private javax.swing.JPanel jPanel62;
    private javax.swing.JPanel jPanel63;
    private javax.swing.JPanel jPanel64;
    private javax.swing.JPanel jPanel65;
    private javax.swing.JPanel jPanel66;
    private javax.swing.JPanel jPanel67;
    private javax.swing.JPanel jPanel68;
    private javax.swing.JPanel jPanel69;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel70;
    private javax.swing.JPanel jPanel71;
    private javax.swing.JPanel jPanel72;
    private javax.swing.JPanel jPanel73;
    private javax.swing.JPanel jPanel74;
    private javax.swing.JPanel jPanel75;
    private javax.swing.JPanel jPanel76;
    private javax.swing.JPanel jPanel77;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel85;
    private javax.swing.JPanel jPanel86;
    private javax.swing.JPanel jPanel89;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanel90;
    private javax.swing.JPanel jPanel91;
    private javax.swing.JPanel jPanel92;
    private javax.swing.JPanel jPanel95;
    private javax.swing.JPanel jPanel96;
    private javax.swing.JPanel jPanel97;
    private javax.swing.JPanel jPanel98;
    private javax.swing.JPanel jPanel99;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lbl_totalTurno;
    private javax.swing.JLabel total_tur;
    private javax.swing.JLabel total_tur_2;
    private javax.swing.JLabel total_turno;
    private javax.swing.JLabel total_turnoMarts;
    private javax.swing.JLabel total_turnoMarts2;
    private javax.swing.JLabel total_turnoMierc;
    private javax.swing.JLabel total_turnoMierc2;
    private javax.swing.JLabel total_turno_2;
    private javax.swing.JLabel total_turnojue;
    private javax.swing.JLabel total_turnojue2;
    private javax.swing.JLabel total_turnovie;
    private javax.swing.JLabel total_turnovie2;
    // End of variables declaration//GEN-END:variables
}
