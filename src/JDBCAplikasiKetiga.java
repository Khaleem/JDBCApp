
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JDBCAplikasiKetiga {

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
            ResultSet result = stmt.executeQuery("Select count(*) as jumlah from customer");
            while (result.next()) {
                int jumlah = result.getInt("Jumlah");
                
                System.out.println("Jumlah Customer : "+jumlah);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(JDBCApplication.class.getName()).log(Level.SEVERE,"Driver not found",ex);
            //System.out.println("Driver error");
        } catch (SQLException se) {
            Logger.getLogger(JDBCApplication.class.getName()).log(Level.SEVERE,"Database not found",se);
            //System.out.println("sql error");
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
