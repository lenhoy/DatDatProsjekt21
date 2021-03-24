import java.sql.*;
import java.util.*;

public class LoginCtrl extends DBConn{
    private String email; 
    private String name; 
    private String password; 
    private String anonAlias;

    public void login(String email, String password) {
        this.email = email; 
        this.password = password; 
        try {
            Statement loginStatement = conn.createStatement();
            String query = "SELECT * FROM user_ WHERE EMail='" + email + "'" + " AND Password_='" + password + "'"; 
            ResultSet rs = loginStatement.executeQuery(query);
            while (rs.next()) {
                System.out.println("Use case 1:");
                System.out.println("Innlogging vellykket");
                System.out.println("Logget inn som " + rs.getString("name_"));
                System.out.println("");
            }
            loginStatement.close();
        } catch (Exception e) {
            System.out.println("DB error during SELECT from User" + e);
        }
    }
}
