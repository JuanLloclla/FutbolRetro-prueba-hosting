package Config;

import java.sql.*;
import javax.swing.JOptionPane;

public class Conexion implements Parametros{
    public Connection con;
    public PreparedStatement ps;
    public Statement smt;
    public ResultSet rs;
    public Conexion(){
        try{
            Class.forName(DRIVER);
            con =  DriverManager.getConnection(URL,USER,CLAVE);
            smt = con.createStatement();
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, "ERROR al conectar a la base de datos..."+ex);
        }
    }
}
