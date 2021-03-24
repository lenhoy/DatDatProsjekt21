import java.sql.*;
import java.util.*;

public class SearchCtrl extends DBConn{
    

    public void search (String searchTerm) {
        try {
            Statement searchStmt = conn.createStatement();
            String query = "SELECT PostID FROM post WHERE content LIKE '%" + searchTerm + "%'"; 
            ResultSet rs = searchStmt.executeQuery(query);
            List<Integer> postID = new ArrayList<>();
            while (rs.next()) {
                postID.add(rs.getInt("PostID"));
            }
            searchStmt.close();
            System.out.println("Use case 4:");
            System.out.println("PostID of posts containing: " + searchTerm + " are: " + postID);
            System.out.println("");
        } catch (Exception e) {
            System.out.println("DB error during SELECT from Post" + e);
        }
    }
}
