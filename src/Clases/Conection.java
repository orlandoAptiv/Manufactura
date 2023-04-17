package Clases;

import java.sql.*;
import java.util.ArrayList;

public class Conection {

    //URL DEL SERVIDOR
    String driver = "com.mysql.jdbc.Driver";
    public Connection con = null;

    public Conection() throws SQLException, ClassNotFoundException {
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(getTexturl(), getTextusr(), getTextpass());
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public static String getTexturl() {
        String url = "jdbc:mysql://DL9XDZM83/manufactura"; //Servidor Mochis
        return (url);
    }

    //USUARIO DE MYSQL
    public static String getTextusr() {
        String url = "root";
        //  String url = "planta";
        return (url);
    }

    //PASSWORD DE MYSQL
    public static String getTextpass() {

        String url = "root";
        // String url = "99552499";

        return (url);
    }

    //NOMBRE DE LA BASE DE DATOS
    public static String getTextdb() {
        String db = "manufactura";
        return (db);
    }

    //URL PARA GUARDAR LOS DOCUMENTOS XLS
    public static String getTextxml() {
        String xml = "DL9HZ14W1//C:/RESPALDO/FILES/";
        return (xml);
    }

    //URL PARA ENCONTRAR EL RESPALDO
    public static String getTextbkup() {
        String xml = "DL9HZ14W1//C:/RESPALDO/BACKUP/";
        return (xml);
    }

    public ResultSet GetConsulta(String Consulta) {
        ResultSet rsp = null;
        try {
            java.sql.PreparedStatement st = con.prepareStatement(Consulta);
            rsp = st.executeQuery();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return rsp;
    }

    //METODO PARA INSERTAR DATOS EN TABLA
    public boolean EjecutarInsert(String Consulta, ArrayList<String> listaValores) {
        boolean rsp = false;
        try {
            java.sql.PreparedStatement st = con.prepareStatement(Consulta);
            if (listaValores.size() > 0) {
                for (int i = 0; i < listaValores.size(); i++) {
                    st.setObject(i + 1, listaValores.get(i));
                }
            }
            if (st.executeUpdate() > 0) {
                rsp = true;
            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return rsp;
    }

    public Boolean EjecutarInsertOb(String Consulta, ArrayList<Object> lista) {
        boolean rsp = false;
        try {
            java.sql.PreparedStatement st = con.prepareStatement(Consulta);
            if (lista.size() > 0) {
                for (int i = 0; i < lista.size(); i++) {
                    st.setObject(i + 1, lista.get(i));
                }
            }
            if (st.executeUpdate() > 0) {
                rsp = true;
            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return rsp;
    }

    //METODO PARA INSERTAR DATOS EN TABLA
    public boolean Ejecutardelete(String Consulta, ArrayList<String> listaValores) {
        boolean rsp = false;
        try {
            java.sql.PreparedStatement st = con.prepareStatement(Consulta);
            if (listaValores.size() > 0) {
                for (int i = 0; i < listaValores.size(); i++) {
                    st.setObject(i + 1, listaValores.get(i));
                }
            }
            if (st.executeUpdate() > 0) {
                rsp = true;
            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return rsp;
    }

    public void abrir() {
        try {
            if (con.isClosed()) {
                con = DriverManager.getConnection(getTexturl(), getTextusr(), getTextpass());
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());//  Logger.getLogger(Conection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cerrar() {
        try {
            if (!con.isClosed()) {
                con.close();
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }

}
