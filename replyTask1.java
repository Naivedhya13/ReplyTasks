import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class replyTask1 {
    public static void main(String[] args) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/orders";
        String username = "root";
        String password = "Manitshah12?";
        
        String csvFilePath = "orders.csv";

        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
             Statement statement = connection.createStatement();
             FileWriter csvWriter = new FileWriter(csvFilePath)) {

            // Insert dummy data into the orders table
            String[] insertStatements = {
                "INSERT INTO orders_table (order_date, order_subtotal, order_total, taxAmount, deliveryCharges) VALUES ('2023-09-01', 100.00, 120.00, 10.00, 5.00)",
                "INSERT INTO orders_table (order_date, order_subtotal, order_total, taxAmount, deliveryCharges) VALUES ('2023-09-02', 150.00, 175.00, 12.50, 6.50)",
                "INSERT INTO orders_table (order_date, order_subtotal, order_total, taxAmount, deliveryCharges) VALUES ('2023-09-03', 75.50, 90.60, 7.50, 4.00)",
                "INSERT INTO orders_table (order_date, order_subtotal, order_total, taxAmount, deliveryCharges) VALUES ('2023-09-04', 200.00, 240.00, 20.00, 8.00)",
                "INSERT INTO orders_table (order_date, order_subtotal, order_total, taxAmount, deliveryCharges) VALUES ('2023-09-05', 50.00, 60.00, 5.00, 3.00)"
            };

            for (String insertStatement : insertStatements) {
                statement.executeUpdate(insertStatement);
            }

            // Write CSV header
            csvWriter.append("order_id,order_date,order_subtotal,order_total,taxAmount,deliveryCharges\n");

            // Retrieve data from the orders table
            ResultSet resultSet = statement.executeQuery("SELECT * FROM orders_table");

            // Write order data to CSV
            while (resultSet.next()) {
                int orderId = resultSet.getInt("order_id");
                String orderDate = resultSet.getString("order_date");
                double orderSubtotal = resultSet.getDouble("order_subtotal");
                double orderTotal = resultSet.getDouble("order_total");
                double taxAmount = resultSet.getDouble("taxAmount");
                double deliveryCharges = resultSet.getDouble("deliveryCharges");

                // Write the data as a CSV row
                csvWriter.append(orderId + "," + orderDate + "," + orderSubtotal + ","
                        + orderTotal + "," + taxAmount + "," + deliveryCharges + "\n");
            }

            System.out.println("Orders exported to CSV successfully.");

        } catch (IOException | java.sql.SQLException e) {
            e.printStackTrace();
        }
    }
}