//STEP 1. Import required packages
import java.sql.*;
import java.util.Scanner;

public class Travelinfo {

   // Set JDBC driver name and database URL
   static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
   static final String DB_URL = "jdbc:mysql://localhost:3306/travelinfo";

   // Database credentials
   static final String USER = "root";// add your user
   static final String PASSWORD = "prosqler27";// add password

   public static void main(String[] args) {
      Connection conn = null;

      Scanner s = new Scanner(System.in);

      // STEP 2. Connecting to the Database
      try {
         // STEP 2a: Register JDBC driver
         Class.forName(JDBC_DRIVER);
         // STEP 2b: Open a connection
         // System.out.println("Connecting to database...");
         conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);

         System.out.println("Login as:\n1. User\n2. Admin");
         int choice = s.nextInt();
         s.nextLine();

         if (choice == 1) {
            userInterface(conn, s);
         } else if (choice == 2) {
            adminInterface(conn, s);
         } else {
            System.out.println("Invalid choice.");
         }
         conn.close();
      } catch (SQLException se) { // Handle errors for JDBC
         se.printStackTrace();
      } catch (Exception e) { // Handle errors for Class.forName
         e.printStackTrace();
      } finally { // finally block used to close resources regardless of whether an exception was
                  // thrown or not
         try {
            if (conn != null)
               conn.close();
         } catch (SQLException se) {
            se.printStackTrace();
         }
         s.close(); // end finally 
      } // end try
      // System.out.println("End of Code");
   } // end main



   public static void userInterface(Connection conn, Scanner s) throws SQLException{
      
      System.out.println("Enter Start City:");
      String start = s.nextLine();
      System.out.println("Enter Destination City:");
      String dest = s.nextLine();

      showAvailableBuses(conn, start, dest);

      while(true){
         System.out.println("\nOptions:\n1. Filter by Operator\n2. Sort (all from src to dest)\n3. Exit\n");
         int option = s.nextInt();
         s.nextLine();

         if(option == 1){
            showAllOperators(conn);
            System.out.println("Enter Operator ID:");
            int opid = s.nextInt();
            s.nextLine();
            filterByOperator(conn, start, dest, opid);
         }

         else if(option == 2){
            System.out.println("Sort by:\n1. Price\n2. Departure Time");
            int sortchoice = s.nextInt();
            s.nextLine();
            if(sortchoice!=1 && sortchoice!=2){

            }
            sorter(conn, start, dest, sortchoice);
         }
         else if(option == 3){
            System.out.println("Goodbye!");
            break;
         }
         else System.out.println("Invalid choice");
      }
   }

   public static void adminInterface(Connection conn, Scanner s) throws SQLException {
      while(true) {
         System.out.println("1. Get Contact Info of an Operator by Vehicle Number");
         System.out.println("2. Track Operator's Performance");
         System.out.println("3. Exit");

         int choice = s.nextInt();
         s.nextLine();

         if(choice == 1){
            System.out.println("Enter Vehicle Number:");
            String vehicleNumber = s.nextLine();
            getOperatorContactInfo(conn, vehicleNumber);
         }
         else if(choice == 2){
            showAllOperators(conn);
            System.out.println("Enter Operator ID:");
            int opId = s.nextInt();
            s.nextLine();
            trackOperatorPerformance(conn, opId);
         }

         else if (choice == 3){
            System.out.println("Goodbye!");
            break;
         }
         else{
         System.out.println("Invalid choice");
         }
      }
   }
 

   public static void showAvailableBuses(Connection conn, String start, String dest) throws SQLException{
      String sql = "SELECT b.vehicle_number, b.ac_type, b.seat_type, s.departure_time, s.price, o.opname FROM schedule s JOIN bus b ON s.bus_id = b.bus_id JOIN operator o ON b.operator_id = o.op_id JOIN route r ON s.route_id = r.route_id WHERE r.start_city = ? AND r.dest_city = ?";

      PreparedStatement stmt1 = conn.prepareStatement(sql);
      stmt1.setString(1, start);
      stmt1.setString(2, dest);
      ResultSet rs = stmt1.executeQuery();

      boolean found = false;

      System.out.println("Available Buses:");
      while(rs.next()){
         found = true;
         System.out.printf("Vehicle Number: %s | Type: %s %s | Departure Time: %s | Price: %.2f | Operator: %s\n",
         rs.getString("vehicle_number"),
         rs.getString("ac_type"),
         rs.getString("seat_type"),
         rs.getString("departure_time"),
         rs.getDouble("price"),
         rs.getString("opname"));
      }

      if (!found) {
         System.out.println("No available buses found from " + start + " to " + dest);
   }

      rs.close();
      stmt1.close();
   }

   public static void showAllOperators(Connection conn) throws SQLException {
      String sql = "SELECT op_id, opname FROM operator";
      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery(sql);

      System.out.println("List of Operators:");
      while(rs.next()){
         System.out.printf("%d: %s\n", rs.getInt("op_id"), rs.getString("opname"));
      }
         
      rs.close();
      stmt.close();
   }

   
   public static void filterByOperator(Connection conn, String start, String dest, int opid) throws SQLException {
      String sql = "SELECT b.vehicle_number, b.ac_type, b.seat_type, s.departure_time, s.price, o.opname FROM schedule s JOIN bus b ON s.bus_id = b.bus_id JOIN operator o ON b.operator_id = o.op_id JOIN route r ON s.route_id = r.route_id WHERE r.start_city = ? AND r.dest_city = ? AND o.op_id = ?";


      PreparedStatement stmt1 = conn.prepareStatement(sql);
      stmt1.setString(1, start);
      stmt1.setString(2, dest);
      stmt1.setInt(3, opid);
      ResultSet rs = stmt1.executeQuery();

      boolean found = false;
      System.out.println("Filtered by Operator:");
      while(rs.next()){
         found = true;
         System.out.printf("Vehicle Number: %s | Type: %s %s | Departure Time: %s | Price: %.2f | Operator: %s\n",
         rs.getString("vehicle_number"),
         rs.getString("ac_type"),
         rs.getString("seat_type"),
         rs.getString("departure_time"),
         rs.getDouble("price"),
         rs.getString("opname"));
      }

      if (!found) {
         System.out.println("No available buses found from " + start + " to " + dest + " from the selected operator");
   }

      rs.close();
      stmt1.close();
   }


   public static void sorter(Connection conn, String start, String dest, int sortchoice) throws SQLException {
      String sortBy;
      if(sortchoice == 1){
         sortBy = "s.price";
      }
      else sortBy = "s.departure_time";

      String sql = "SELECT b.vehicle_number, b.ac_type, b.seat_type, s.departure_time, s.price, o.opname FROM schedule s JOIN bus b ON s.bus_id = b.bus_id JOIN operator o ON b.operator_id = o.op_id JOIN route r ON s.route_id = r.route_id WHERE r.start_city = ? AND r.dest_city = ? ORDER BY " + sortBy;

      PreparedStatement stmt1 = conn.prepareStatement(sql);
      stmt1.setString(1, start);
      stmt1.setString(2, dest);
      ResultSet rs = stmt1.executeQuery();

      boolean found = false;

      System.out.println("Available Buses:");
      while(rs.next()){
         found = true;
         System.out.printf("Vehicle Number: %s | Type: %s %s | Departure Time: %s | Price: %.2f | Operator: %s\n",
         rs.getString("vehicle_number"),
         rs.getString("ac_type"),
         rs.getString("seat_type"),
         rs.getString("departure_time"),
         rs.getDouble("price"),
         rs.getString("opname"));
      }

      if (!found) {
         System.out.println("No available buses found from " + start + " to " + dest);
   }

      rs.close();
      stmt1.close();
   }

   public static void getOperatorContactInfo(Connection conn, String vehicleNumber) throws SQLException {
      String sql = "SELECT o.opname, o.contactno, o.email FROM operator o JOIN bus b ON o.op_id = b.operator_id WHERE b.vehicle_number = ?";
      PreparedStatement stmt1 = conn.prepareStatement(sql);
      stmt1.setString(1, vehicleNumber);
      ResultSet rs = stmt1.executeQuery();
  
      if(rs.next()) {
         System.out.println("Operator Name: " + rs.getString("opname"));
         System.out.println("Contact Number: " + rs.getString("contactno"));
         System.out.println("Email: " + rs.getString("email"));
      }
      else{
         System.out.println("Invalid vehicle number");
      }
  
      rs.close();
      stmt1.close();
  }

   public static void trackOperatorPerformance(Connection conn, int opId) throws SQLException {
      String sql = "SELECT (SELECT COUNT(*) FROM bus WHERE operator_id = ?) AS num_buses, (SELECT COUNT(*) FROM schedule s JOIN bus b ON s.bus_id = b.bus_id WHERE b.operator_id = ?) AS num_schedules";
  
      PreparedStatement stmt1 = conn.prepareStatement(sql);
      stmt1.setInt(1, opId);
      stmt1.setInt(2, opId);
  
      ResultSet rs = stmt1.executeQuery();
  
      if(rs.next()){
          System.out.println("Number of Buses: " + rs.getInt("num_buses"));
          System.out.println("Number of Schedules: " + rs.getInt("num_schedules"));
      }

      rs.close();
      stmt1.close();
  }
} // end class 