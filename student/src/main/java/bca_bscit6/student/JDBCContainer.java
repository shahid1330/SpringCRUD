//package bca_bscit6.student;
//
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.AbstractApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//import org.springframework.jdbc.core.JdbcTemplate;
//
//public class JDBCContainer {
//	public static void main( String[] args )
//    {
//    	String configFile = "bca_bscit6/student/JDBCconfig.xml";
//        ApplicationContext context = new ClassPathXmlApplicationContext(configFile);
//        
//        JdbcTemplate template = (JdbcTemplate)context.getBean("template");
//        
//        String query = "insert into customer values(?,?,?)";
//        
//        int response = template.update(query,4,"Virat Kohli","Bangalore");
//        
//        System.out.println(response +" rows affected");
//        
//    }
//
//}
//

package bca_bscit6.student;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

public class JDBCContainer {
    public static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        String configFile = "bca_bscit6/student/JDBCconfig.xml";
        ApplicationContext context = new ClassPathXmlApplicationContext(configFile);

        JdbcTemplate template = (JdbcTemplate) context.getBean("template");

        boolean continueMenu = true;

        while (continueMenu) {
            System.out.println("Menu:");
            System.out.println("1. Create");
            System.out.println("2. Read");
            System.out.println("3. Update");
            System.out.println("4. Delete");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    createRecord(template);
                    break;
                case 2:
                    readRecord(template);
                    break;
                case 3:
                    updateRecord(template);
                    break;
                case 4:
                    deleteRecord(template);
                    break;
                case 5:
                    continueMenu = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 5.");
            }
        }

        scanner.close();
    }

    
    public static void createRecord(JdbcTemplate template) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter ID:");
        int id = scanner.nextInt();
        scanner.nextLine(); 
        System.out.println("Enter Name:");
        String name = scanner.nextLine();
        System.out.println("Enter Address:");
        String address = scanner.nextLine();

        String query = "INSERT INTO customer (id, name, address) VALUES (?, ?, ?)";
        int rowsAffected = template.update(query, id, name, address);
        System.out.println(rowsAffected + " row(s) affected");
        scanner.close();
    }
    
    public static void readRecord(JdbcTemplate template) {
        String query = "SELECT * FROM customer";
        List<Map<String, Object>> records = template.queryForList(query);

        if (records.isEmpty()) {
            System.out.println("No records found.");
        } else {
            System.out.println("ID\tName\tAddress");
            for (Map<String, Object> record : records) {
                int id = (int) record.get("id");
                String name = (String) record.get("name");
                String address = (String) record.get("address");
                System.out.println(id + "\t" + name + "\t" + address);
            }
        }
    }

    public static void updateRecord(JdbcTemplate template) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter ID of the record to update:");
        int id = scanner.nextInt();
        scanner.nextLine(); 
        System.out.println("Enter new Name:");
        String name = scanner.nextLine();
        System.out.println("Enter new Address:");
        String address = scanner.nextLine();

        String query = "UPDATE customer SET name=?, address=? WHERE id=?";
        int rowsAffected = template.update(query, name, address, id);
        System.out.println(rowsAffected + " row(s) updated");
        scanner.close();
    }
    
    public static void deleteRecord(JdbcTemplate template) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter ID of the record to delete:");
        int id = scanner.nextInt();

        String query = "DELETE FROM customer WHERE id=?";
        int rowsAffected = template.update(query, id);
        System.out.println(rowsAffected + " row(s) deleted");
        scanner.close();
    }
    
}
