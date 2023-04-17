/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package manufactura;
import Clases.Usuario;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class CAPTURAINFORMACION extends javax.swing.JFrame {

    /**
     * Creates new form CAPTURAINFORMACION
     */
    //Conection cn;
    DefaultTableModel modeloDefault;
    Usuario Us;
    boolean  _act=false;
    public CAPTURAINFORMACION() {

            initComponents();  
            EnlazarDGV();
            tblUsuarios.setComponentPopupMenu(btnDerecho);
        }
    public void Actualizarusuario(ArrayList<Object> Lista){
       if(Principal.cn.EjecutarInsertOb("update usuarios set nombre=?, turno=?, puesto=?, usuario=?, contra=?, cadena1=?, cadena2=?, cadena3=?, cadena4=?, cadena5=?,cadena6=?, foto=? where codigo=?", Lista))
       {
           JOptionPane.showMessageDialog(null, "Datos actualizados correctamente", "Confirmacion", JOptionPane.INFORMATION_MESSAGE);
           Limpiar();
       }else
           JOptionPane.showMessageDialog(null, "Error actualizar...", "Confirmacion", JOptionPane.WARNING_MESSAGE);
    }
  
    public void Guardar(ArrayList<Object> Lista){
       if(Principal.cn.EjecutarInsertOb("INSERT INTO USUARIOS (NOMBRE, TURNO, PUESTO, USUARIO, CONTRA, cadena1, cadena2, cadena3, cadena4, cadena5,cadena6, Foto) VALUES (?,?,?,?,?, ?,?,?,?,?,?, ?)", Lista))
       {
           JOptionPane.showMessageDialog(null, "Datos insertados correctamente", "Confirmacion", JOptionPane.INFORMATION_MESSAGE);
           Limpiar();
       }
       else
           JOptionPane.showMessageDialog(null, "Error al guardar...", "Confirmacion", JOptionPane.WARNING_MESSAGE);
    }
    
    public void Limpiar(){
        _act=false;
        txtNombre.setText(null);
        txtPuesto.setText(null);
        txtUsuario.setText(null);
        txtContra.setText(null);
        cbxTurno.setSelectedIndex(0);
        txtNombre.requestFocus();
        chCad1.setSelected(false);
        chCad2.setSelected(false);
        chCad3.setSelected(false);
        chCad4.setSelected(false);
        chCad5.setSelected(false);
        chCad6.setSelected(false);
        lblFoto.setIcon(null);
        lblFoto.setText("Foto");
    }
    
    public void EnlazarDGV(){
        try        
        {
          ResultSet rs=Principal.cn.GetConsulta("SELECT * FROM USUARIOS");
          modeloDefault = new DefaultTableModel();
           //ASIGNAMOS NOMBRE Y COLUMNAS AL MODELO DE LA TABLA
                                                             //  1       2          3        4         5          6        7         8            9         10        11            12       13        14         15     16                                                                                                               
           modeloDefault.setColumnIdentifiers(new Object[]{"CODIGO", "NOMBRE", "TURNO", "PUESTO", "USUARIO", "CONTRA", "FECHA", "cadena1", "cadena2", "cadena3", "cadena4",  "CADENA5","CADENA6", "USUARIOS", "CODIGOS", "FOTO"});
             tblUsuarios.setModel(modeloDefault);  
              while(rs.next())
              {                                        
                modeloDefault.addRow(new Object[]{rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13),rs.getString(14), rs.getString(15), rs.getBlob("Foto")});
              }
              rs.close();
            tblUsuarios.getColumnModel().getColumn(2).setPreferredWidth(20);
            tblUsuarios.getColumnModel().getColumn(5).setMaxWidth(0);
            tblUsuarios.getColumnModel().getColumn(5).setMinWidth(0);
            tblUsuarios.getColumnModel().getColumn(5).setPreferredWidth(0);
            tblUsuarios.getColumnModel().getColumn(6).setMaxWidth(0);
            tblUsuarios.getColumnModel().getColumn(6).setMinWidth(0);
            tblUsuarios.getColumnModel().getColumn(6).setPreferredWidth(0);
            tblUsuarios.getColumnModel().getColumn(7).setMaxWidth(0);
            tblUsuarios.getColumnModel().getColumn(7).setMinWidth(0);
            tblUsuarios.getColumnModel().getColumn(7).setPreferredWidth(0);
            tblUsuarios.getColumnModel().getColumn(8).setMaxWidth(0);
            tblUsuarios.getColumnModel().getColumn(8).setMinWidth(0);
            tblUsuarios.getColumnModel().getColumn(8).setPreferredWidth(0);
            tblUsuarios.getColumnModel().getColumn(9).setMaxWidth(0);
            tblUsuarios.getColumnModel().getColumn(9).setMinWidth(0);
            tblUsuarios.getColumnModel().getColumn(9).setPreferredWidth(0);
            tblUsuarios.getColumnModel().getColumn(10).setMaxWidth(0);
            tblUsuarios.getColumnModel().getColumn(10).setMinWidth(0);
            tblUsuarios.getColumnModel().getColumn(10).setPreferredWidth(0);
            tblUsuarios.getColumnModel().getColumn(11).setMaxWidth(0);
            tblUsuarios.getColumnModel().getColumn(11).setMinWidth(0);
            tblUsuarios.getColumnModel().getColumn(11).setPreferredWidth(0);
            tblUsuarios.getColumnModel().getColumn(12).setMaxWidth(0);
            tblUsuarios.getColumnModel().getColumn(12).setMinWidth(0);
            tblUsuarios.getColumnModel().getColumn(12).setPreferredWidth(0);
            tblUsuarios.getColumnModel().getColumn(13).setMaxWidth(0);
            tblUsuarios.getColumnModel().getColumn(13).setMinWidth(0);
            tblUsuarios.getColumnModel().getColumn(13).setPreferredWidth(0);
            tblUsuarios.getColumnModel().getColumn(14).setMaxWidth(0);
            tblUsuarios.getColumnModel().getColumn(14).setMinWidth(0);
            tblUsuarios.getColumnModel().getColumn(14).setPreferredWidth(0);
        }catch(Exception e) 
        {
            System.out.println(e.toString());
        }
    }

    private void RellenarUsuario(int renglon) throws IOException, ClassNotFoundException{
    
            Blob blob= (Blob)modeloDefault.getValueAt(renglon, 14);
               ImageIcon icono=new ImageIcon();
            if(blob!=null){
                java.io.ObjectInputStream ois = null;
                try {
                    ois = new java.io.ObjectInputStream(blob.getBinaryStream());
                    icono = (javax.swing.ImageIcon) ois.readObject();
                } catch (SQLException ex) {
                    Logger.getLogger(CAPTURAINFORMACION.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    try {
                        ois.close();
                    } catch (IOException ex) {
                        Logger.getLogger(CAPTURAINFORMACION.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
      // Us =new Usuario(modeloDefault.getValueAt(renglon, 0).toString(), modeloDefault.getValueAt(renglon, 1).toString(), modeloDefault.getValueAt(renglon, 2).toString(), modeloDefault.getValueAt(renglon, 3).toString(), modeloDefault.getValueAt(renglon, 4).toString(), modeloDefault.getValueAt(renglon, 5).toString(), modeloDefault.getValueAt(renglon, 6).toString(), modeloDefault.getValueAt(renglon, 7).toString(), modeloDefault.getValueAt(renglon, 8).toString(), modeloDefault.getValueAt(renglon, 9).toString(), modeloDefault.getValueAt(renglon, 10).toString(), modeloDefault.getValueAt(renglon, 11).toString(),modeloDefault.getValueAt(renglon, 12).toString(), modeloDefault.getValueAt(renglon, 13).toString(), modeloDefault.getValueAt(renglon, 14).toString(), icono, (Boolean)modeloDefault.getValueAt(renglon, 15) );
        if(Us.Cadena1.equals("1"))
            chCad1.setSelected(true);
        else
            chCad1.setSelected(false);
       if(Us.Cadena2.equals("1"))
            chCad2.setSelected(true);
        else
            chCad2.setSelected(false);
        if(Us.Cadena3.equals("1"))
            chCad3.setSelected(true);
        else
            chCad3.setSelected(false);
        if(Us.Cadena4.equals("1"))
            chCad4.setSelected(true);
        else
            chCad4.setSelected(false);
        if(Us.Cadena5.equals("1"))
            chCad5.setSelected(true);
        else
            chCad5.setSelected(false);
        
        if(Us.Cadena6.equals("1"))
            chCad6.setSelected(true);
        else
            chCad6.setSelected(false);
        
         if(Us.Cadena8.equals("1"))
            chCad7.setSelected(true);
        else
            chCad7.setSelected(false);
        
        
        if(Us.Usuarios.equals("1"))
            chUsuarios.setSelected(true);
        else
            chUsuarios.setSelected(false);   
        if(Us.Codigos.equals("1"))
            chCodigos.setSelected(true);
        else
            chCodigos.setSelected(false);   
        txtNombre.setText(Us.nombre);
         cbxTurno.setSelectedItem(Us.turno);
         txtPuesto.setText(Us.puesto);
         txtUsuario.setText(Us.usuario);
         txtContra.setText(Us.contra);
      }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnDerecho = new javax.swing.JPopupMenu();
        Eliminar = new javax.swing.JMenuItem();
        pnlMenu = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        txtNombre = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtPuesto = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        cbxTurno = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        lblFoto = new javax.swing.JLabel();
        btnSeleccionar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        chCad1 = new javax.swing.JCheckBox();
        chCad2 = new javax.swing.JCheckBox();
        chCad3 = new javax.swing.JCheckBox();
        chCad4 = new javax.swing.JCheckBox();
        chCad5 = new javax.swing.JCheckBox();
        chCodigos = new javax.swing.JCheckBox();
        chUsuarios = new javax.swing.JCheckBox();
        chCad6 = new javax.swing.JCheckBox();
        chCad7 = new javax.swing.JCheckBox();
        jPanel4 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtContra = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblUsuarios = new javax.swing.JTable();
        btnGuardar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();

        btnDerecho.setToolTipText("");

        Eliminar.setText("jMenuItem2");
        Eliminar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                EliminarMouseClicked(evt);
            }
        });
        btnDerecho.add(Eliminar);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("CAPTURA DE INFORMACION USUARIOS");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        pnlMenu.setBackground(new java.awt.Color(255, 255, 153));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(51, 51, 255));
        jLabel6.setText("USUARIOS");

        javax.swing.GroupLayout pnlMenuLayout = new javax.swing.GroupLayout(pnlMenu);
        pnlMenu.setLayout(pnlMenuLayout);
        pnlMenuLayout.setHorizontalGroup(
            pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMenuLayout.createSequentialGroup()
                .addGap(447, 447, 447)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlMenuLayout.setVerticalGroup(
            pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMenuLayout.createSequentialGroup()
                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED), "USUARIOS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, null, new java.awt.Color(51, 51, 255)));

        txtNombre.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("NOMBRE:");

        txtPuesto.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel2.setText("TURNO");

        cbxTurno.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cbxTurno.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TODOS", "A", "B" }));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel3.setText("PUESTO:");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "FOTO", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 24), new java.awt.Color(51, 51, 255))); // NOI18N
        jPanel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        lblFoto.setText("jLabel4");

        btnSeleccionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/load-upload-icon (1).png"))); // NOI18N
        btnSeleccionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSeleccionarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(lblFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSeleccionar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnSeleccionar, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "PERMISOS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 24), new java.awt.Color(51, 51, 255))); // NOI18N
        jPanel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        chCad1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        chCad1.setText("CADENA 1 ");
        jPanel3.add(chCad1, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 31, -1, -1));

        chCad2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        chCad2.setText("CADENA 2 ");
        jPanel3.add(chCad2, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 65, -1, -1));

        chCad3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        chCad3.setText("CADENA 3");
        jPanel3.add(chCad3, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 99, -1, -1));

        chCad4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        chCad4.setText("CADENA 4");
        jPanel3.add(chCad4, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 30, -1, -1));

        chCad5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        chCad5.setText("CADENA 5");
        jPanel3.add(chCad5, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 60, -1, -1));

        chCodigos.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        chCodigos.setText("CODIGOS");
        jPanel3.add(chCodigos, new org.netbeans.lib.awtextra.AbsoluteConstraints(45, 130, -1, -1));

        chUsuarios.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        chUsuarios.setText("USUARIOS");
        jPanel3.add(chUsuarios, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 90, -1, -1));

        chCad6.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        chCad6.setText("CADENA 6");
        jPanel3.add(chCad6, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 120, -1, -1));

        chCad7.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        chCad7.setText("CADENA 8");
        jPanel3.add(chCad7, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 30, -1, -1));

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Datos Sesion", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Arial", 0, 24), new java.awt.Color(51, 51, 255))); // NOI18N
        jPanel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setText("USUARIO:");

        txtUsuario.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel8.setText("CONTRASEÑA:");

        txtContra.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, 268, Short.MAX_VALUE)
                    .addComponent(txtContra))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(txtContra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPuesto, javax.swing.GroupLayout.PREFERRED_SIZE, 377, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 377, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbxTurno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 180, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 531, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(20, 20, 20)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(284, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxTurno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(txtPuesto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(132, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addContainerGap(428, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(116, 116, 116)))
        );

        tblUsuarios.setModel(new javax.swing.table.DefaultTableModel(
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
        tblUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblUsuariosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblUsuarios);

        btnGuardar.setBackground(new java.awt.Color(204, 204, 204));
        btnGuardar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Image55.png"))); // NOI18N
        btnGuardar.setText("GUARDAR");
        btnGuardar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
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

        btnCancelar.setBackground(new java.awt.Color(204, 204, 204));
        btnCancelar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Image48.png"))); // NOI18N
        btnCancelar.setText("CANCELAR");
        btnCancelar.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnCancelar.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        btnCancelar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCancelar.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        btnCancelar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 431, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47)
                        .addComponent(btnCancelar)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnlMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 475, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(33, 33, 33)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tblUsuariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUsuariosMouseClicked
        // TODO add your handling code here:
         int fila = tblUsuarios.rowAtPoint(evt.getPoint());
         int columna = tblUsuarios.columnAtPoint(evt.getPoint());
         if ((fila > -1) && (columna > -1))
         { 
             try {
                 RellenarUsuario(fila);           
                 _act=true;
             } catch (IOException ex) {
                 Logger.getLogger(CAPTURAINFORMACION.class.getName()).log(Level.SEVERE, null, ex);
             } catch (ClassNotFoundException ex) {
                 Logger.getLogger(CAPTURAINFORMACION.class.getName()).log(Level.SEVERE, null, ex);
             }
         }
    }//GEN-LAST:event_tblUsuariosMouseClicked

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
       if((txtNombre.getText().length()>0) && (txtPuesto.getText().length()>0) && (txtUsuario.getText().length()>0))
       {
              String cad1="0";
              String cad2="0";
              String cad3="0";
              String cad4="0";
              String cad5="0";
              String cad6="0";
            if(chCad1.isSelected())
                    cad1="1";
            if(chCad2.isSelected())
                cad2="1";
            if(chCad3.isSelected())
                    cad3="1";
            if(chCad4.isSelected())
                cad4="1";
            if(chCad5.isSelected())
                cad5="1";
             if(chCad6.isSelected())
                cad6="1";
        if(_act)
        {
            ArrayList<Object> lista=new ArrayList<Object>();
            lista.add(txtNombre.getText());
            lista.add(cbxTurno.getSelectedItem().toString());
            lista.add(txtPuesto.getText());
            lista.add(txtUsuario.getText());
            lista.add(txtContra.getText());
            lista.add(cad1);
            lista.add(cad2);
            lista.add(cad3);
            lista.add(cad4);
            lista.add(cad5);
            lista.add(cad6);
            ImageIcon img=(ImageIcon)lblFoto.getIcon();
            lista.add(img);
            lista.add(Us.codigo);
            Actualizarusuario(lista);
            
            
        }
        else
        {
            ArrayList<Object> lista=new ArrayList<Object>();
            lista.add(txtNombre.getText());
            lista.add(cbxTurno.getSelectedItem().toString());
            lista.add(txtPuesto.getText());
            lista.add(txtUsuario.getText());
            lista.add(txtContra.getText());
            lista.add(cad1);
            lista.add(cad2);
            lista.add(cad3);
            lista.add(cad4);
            lista.add(cad5);
            lista.add(cad6);
            ImageIcon img=(ImageIcon)lblFoto.getIcon();
            lista.add(img);
            Guardar(lista);
        }
       }
      else
       {
           JOptionPane.showMessageDialog(null, "Error, Datos Pendientes....", "Error", JOptionPane.WARNING_MESSAGE);
       }
       EnlazarDGV();
       Limpiar();
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void EliminarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EliminarMouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_EliminarMouseClicked

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
        Principal P=new Principal(Principal.UsuarioLogeado);
        P.setLocationRelativeTo(null);
        P.setVisible(true);
    }//GEN-LAST:event_formWindowClosed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        Limpiar();
        
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnSeleccionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSeleccionarActionPerformed
        // TODO add your handling code here:
        JFileChooser f=new JFileChooser();
       if( f.showOpenDialog(this)==JFileChooser.APPROVE_OPTION)
       {
           //lblFoto.setText(f.getSelectedFile().toString());
          JOptionPane.showMessageDialog(null,"ADDED successfully");
          Toolkit toolkit = Toolkit.getDefaultToolkit();
          Image image = toolkit.getImage(f.getSelectedFile().toString());
          Image scaledImage = image.getScaledInstance(lblFoto.getWidth(), lblFoto.getHeight(), Image.SCALE_DEFAULT);
          ImageIcon icon=new ImageIcon(scaledImage);
          //my.jLabel10.setIcon(icon);}
           lblFoto.setIcon(icon);
       }
       
    }//GEN-LAST:event_btnSeleccionarActionPerformed

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
            java.util.logging.Logger.getLogger(CAPTURAINFORMACION.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CAPTURAINFORMACION.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CAPTURAINFORMACION.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CAPTURAINFORMACION.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CAPTURAINFORMACION().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem Eliminar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JPopupMenu btnDerecho;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnSeleccionar;
    private javax.swing.JComboBox cbxTurno;
    private javax.swing.JCheckBox chCad1;
    private javax.swing.JCheckBox chCad2;
    private javax.swing.JCheckBox chCad3;
    private javax.swing.JCheckBox chCad4;
    private javax.swing.JCheckBox chCad5;
    private javax.swing.JCheckBox chCad6;
    private javax.swing.JCheckBox chCad7;
    private javax.swing.JCheckBox chCodigos;
    private javax.swing.JCheckBox chUsuarios;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblFoto;
    private javax.swing.JPanel pnlMenu;
    private javax.swing.JTable tblUsuarios;
    private javax.swing.JTextField txtContra;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtPuesto;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
