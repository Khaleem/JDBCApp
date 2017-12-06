
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
public class JDBCAplikasiKeempat {

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
            ResultSet result = stmt.executeQuery("select p.description, po.quantity, p.purchase_cost \n"
                    + "from product p join purchase_order po \n"
                    + "on p.PRODUCT_ID = po.PRODUCT_ID");

            double total = 0.0;

            while (result.next()) {
                String namaProduk = result.getString("description");
                int qty = result.getInt("quantity");
                double pc = result.getDouble("purchase_cost");
                double lineCost = qty * pc;

                System.out.printf("%s\t\t\t %d\t %.2f\t %.2f", namaProduk, qty, pc, lineCost);
                System.out.println("");
                total += lineCost;
            }
            System.out.println("Total Order Cost = $" +total);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(JDBCApplication.class.getName()).log(Level.SEVERE, "Driver not found", ex);
        } catch (SQLException se) {
            Logger.getLogger(JDBCApplication.class.getName()).log(Level.SEVERE, "Database not found", se);
        } finally {
            try {
                stmt.close();
                connection.close();
            } catch (SQLException e) {
                Logger.getLogger(JDBCApplication.class.getName()).log(Level.SEVERE, "", e);
            }
        }

    }

}
