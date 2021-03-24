import java.sql.*;
import java.util.*;

public class StatsCtrl extends DBConn{
    public void getStats(String instructorID) {
        try {
            Statement isInstructorStmt = conn.createStatement();
            String query1 = "SELECT CourseID FROM managedBy WHERE instructorID='" + instructorID + "'"; 
            ResultSet rs = isInstructorStmt.executeQuery(query1);
            if (rs.next()){
                try {
                    Statement statsStmt = conn.createStatement();
                    String query2 = "SELECT name_, postedby.count(*), readby.count(*)";
                } 
            };
            isInstructorStmt.close();
            
        } catch (Exception e) {
            System.out.println("DB error during SELECT from Post" + e);
        }
    }
}
