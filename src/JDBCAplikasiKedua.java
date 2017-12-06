
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author user
 */
public class JDBCAplikasiKedua {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Connection connection = null;
        Statement stmt = null;
        try {
            String dburl = "jdbc:derby://localhost:1527/sample";
            String username = "app";
            String password = "app";
            
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            
            connection = DriverManager.getConnection(dburl, username, password);
            stmt = connection.createStatement();
            ResultSet result = stmt.executeQuery("Select * from customer");
            while (result.next()) {
                String name = result.getString("name");
                String alamat = result.getString("addressline1") + ", "+("addressline2");
                int lamaCredit = result.getInt("credit_limit");
                System.out.println("Nama : "+name +" tinggal di "+alamat+" lama kredit "+lamaCredit);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(JDBCApplication.class.getName()).log(Level.SEVERE,"Driver not found",ex);
            
        } catch (SQLException se) {
            Logger.getLogger(JDBCApplication.class.getName()).log(Level.SEVERE,"Database not found",se);
            
        } finally {
            try {
                stmt.close();
                connection.close();
            } catch (SQLException e) {
                Logger.getLogger(JDBCApplication.class.getName()).log(Level.SEVERE,"",e);
            }
        }
    }
    
}
