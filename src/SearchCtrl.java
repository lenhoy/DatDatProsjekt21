import java.sql.*;
import java.util.*;

public class SearchCtrl extends DBConn{
    
    // Søker i databasen etter 'searchTerm', IDene til postene som inneholder 'searchTerm' returneres. 
    public List<Integer> search (String searchTerm) {
        try {
            Statement searchStmt = conn.createStatement();
            String query = "SELECT PostID FROM post WHERE content LIKE '%" + searchTerm + "%'"; 
            ResultSet rs = searchStmt.executeQuery(query);
            List<Integer> postID = new ArrayList<>();

            // Itererer over resultatet av SQL-spørringen og legger til PostIDer i listen. 
            while (rs.next()) {
                postID.add(rs.getInt("PostID"));
            }
            searchStmt.close();

            //Skriver ut listen av PostIDer som inneholder søket, gitt at det er noen
            if (postID.size() > 0) {
                System.out.println("Use case 4:");
                System.out.println("PostID til poster som inneholder: " + searchTerm + " er: " + postID);
                System.out.println("");
                return postID;
            } else {
                System.out.println("Ingen poster innholder, " + searchTerm);
                return null;
            }
        } catch (Exception e) {
            System.out.println("DB error during SELECT from Post " + e);
            return null;
        }
    }
}
