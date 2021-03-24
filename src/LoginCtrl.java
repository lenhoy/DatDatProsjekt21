import java.sql.*;
import java.util.*;

public class LoginCtrl extends DBConn{
    private String email; 
    private String name; 
    private String password; 
    private String anonAlias; 
    private PreparedStatement loginStatement;

    public void Login(String email, String password) {
        this.email = email; 
        this.password = password; 
        try {
            loginStatement = conn.prepareStatement("SELECT * FROM User");
        } catch (Exception e) {
            System.out.println("DB error during prepare of select from User" + e);
        }
    }
}
