package manufactura;

import java.sql.*;

public class database {

    /* DATOS PARA LA CONEXION */
    private String bd = "manufactura";//BASE DE DATOS
    private String login = "root"; //USUARIO
    private String password = "root"; //CONTRASEÑA
    private String url = "jdbc:mysql://localhost/manufactura";
    private Connection conn = null;

    /* Constructor de clase: Se conecta a la base de datos
     */
    public database() {

        try {
            //obtenemos el driver de para mysql
            Class.forName("com.mysql.jdbc.Driver");
            //obtenemos la conexión
            conn = DriverManager.getConnection(url, login, password);
            if (conn != null) {
                System.out.println("OK base de datos " + bd + " listo");
            }
        } catch (SQLException e) {
            System.out.println(e);
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        }
    }

    public Connection getConnection() {
        return this.conn;
    }

    /* Realiza una consulta a la base de datos, retorna un Object[][] con los
 * datos de la tabla persona
     */
    public Object[][] Select_Persona() {
        int registros = 0;
        String consulta = "select IDCODIGO,CODIGO,LINEA,T_A_MOD,LUNES,MARTES,MIERCOLES,JUEVES,VIERNES,LUNES2,MARTES2,MIERCOLES2,JUEVES2,VIERNES2 FROM lay_off_a";
        String consulta2 = "Select count(*) as total from lay_off_a";
        //obtenemos la cantidad de registros existentes en la tabla
        try {

            PreparedStatement pstm = conn.prepareStatement(consulta2);
            ResultSet res = pstm.executeQuery();
            res.next();
            registros = res.getInt("total");
            res.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        //se crea una matriz con tantas filas y columnas que necesite
        Object[][] data = new String[registros][14];
        //realizamos la consulta sql y llenamos los datos en la matriz "Object"
        try {
            PreparedStatement pstm = conn.prepareStatement(consulta);
            ResultSet res = pstm.executeQuery();
            int i = 0;
            while (res.next()) {
                data[i][0] = res.getString("IDCODIGO");
                data[i][1] = res.getString("CODIGO");
                data[i][2] = res.getString("LINEA");
                data[i][3] = res.getString("T_A_MOD");
                data[i][4] = res.getString("LUNES");
                data[i][5] = res.getString("MARTES");
                data[i][6] = res.getString("MIERCOLES");
                data[i][7] = res.getString("JUEVES");
                data[i][8] = res.getString("VIERNES");
                data[i][9] = res.getString("LUNES2");
                data[i][10] = res.getString("MARTES2");
                data[i][11] = res.getString("MIERCOLES2");
                data[i][12] = res.getString("JUEVES2");
                data[i][13] = res.getString("VIERNES2");

                i++;
            }
            res.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return data;
    }

    /* Ejecuta la actualizacion de la tabla persona dado los valores de actualizacion
 * y el ID del registro a afectar
     */
    public boolean update(String valores, String id) {
        boolean res = false;

        String q = " UPDATE lay_off_a SET " + valores + " WHERE IDCODIGO= " + id;
        try {
            PreparedStatement pstm = conn.prepareStatement(q);
            pstm.execute();
            pstm.close();
            res = true;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return res;
    }

}
