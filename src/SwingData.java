
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class SwingData extends javax.swing.JFrame {

    public SwingData() {
        initComponents();
        showPurchaseData();
    }

    public void showPurchaseData() {
        DecimalFormat df = new DecimalFormat("###,###.##");
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

            Object[] row = new Object[4];
            DefaultTableModel tableModel = (DefaultTableModel) purchaseTable.getModel();
            while (result.next()) {
                String namaProduk = result.getString("description");
                int quantity = result.getInt("quantity");
                double cost = result.getDouble("purchase_cost");
                double lineCost = quantity * cost;
                row[0] = namaProduk;
                row[1] = quantity;
                row[2] = cost;
                row[3] = lineCost;
                tableModel.addRow(row);
                total += lineCost;
            }
            DefaultTableCellRenderer rendar = new DefaultTableCellRenderer();
            purchaseTable.getColumnModel().getColumn(0).setCellRenderer(rendar);
            
            totalLabel.setText("Total Purchase : " + String.valueOf(df.format(total)));
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();
        jScrollPane1 = new javax.swing.JScrollPane();
        purchaseTable = new javax.swing.JTable();
        totalLabel = new javax.swing.JLabel();

        jCheckBoxMenuItem1.setSelected(true);
        jCheckBoxMenuItem1.setText("jCheckBoxMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        purchaseTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nama Produk", "Quantity", "Cost", "Line Cost"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(purchaseTable);

        totalLabel.setText("Total");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 546, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(224, 224, 224)
                .addComponent(totalLabel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(totalLabel)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SwingData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SwingData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SwingData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SwingData.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SwingData().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable purchaseTable;
    private javax.swing.JLabel totalLabel;
    // End of variables declaration//GEN-END:variables
}
