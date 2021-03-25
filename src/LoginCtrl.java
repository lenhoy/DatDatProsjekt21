import java.sql.*;

public class LoginCtrl extends DBConn{


    // Logger inn og sjekker om brukeren er i databasen
    public void login(String email, String password) {
        try {
            Statement loginStatement = conn.createStatement();
            String query = "SELECT * FROM user_ WHERE EMail='" + email + "'" + " AND Password_='" + password + "'"; 
            ResultSet rs = loginStatement.executeQuery(query);
            if (rs.next()){
                System.out.println("Use case 1:");
                System.out.println("Innlogging vellykket");
                System.out.println("Logget inn som " + rs.getString("name_"));
                System.out.println("");
            } else {
                System.out.println("Klarte ikke å logge inn.");
            };
            
            loginStatement.close();
        } catch (Exception e) {
            System.out.println("DB error during SELECT from User" + e);
        }
    }
}
