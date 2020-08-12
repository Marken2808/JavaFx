package resources;

import java.sql.Connection;
import java.sql.DriverManager;

public class mySQLconnection {

    Connection connection = null;

    public static Connection ConnectDataBase(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/cardfx","root","zxcvbnm");
           //JOptionPane.showMessageDialog(null,"Connection Established");
            return connection;
        }
        catch (Exception e){
            //JOptionPane.showMessageDialog(null,e);
            return null;

        }

    }
}
