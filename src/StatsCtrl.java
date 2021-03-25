import java.sql.*;

public class StatsCtrl extends DBConn{
    //Sjekker om brukeren som ser etter statistikk er en instruktør.
    //Hvis den er det, skrives det ut statistikk om alle brukere.
    public void getStats(String instructorID) {
        try {
            Statement isInstructorStmt = conn.createStatement();
            String query1 = "SELECT CourseID FROM managedby WHERE EMail='" + instructorID + "'"; 
            ResultSet rs = isInstructorStmt.executeQuery(query1);
            if (rs.next()){
                //Spørringen teller antallet leste og skrevne poster for hver bruker
                //Men tar også med brukere som ikke har lest eller skrevet posts
                try {
                    Statement statsStmt = conn.createStatement();
                    String query2 = "SELECT pread.name_, pread.postsRead, posts.postsBy FROM (SELECT name_, COUNT(readby.EMail) AS postsRead FROM user_ LEFT JOIN readby ON user_.EMail = readby.EMail GROUP BY user_.EMail) AS pread INNER JOIN (SELECT name_, COUNT(postedby.EMail) AS postsBy FROM  user_ LEFT JOIN postedby ON user_.EMail = postedby.EMail GROUP BY user_.EMail) AS posts ON pread.name_ = posts.name_ ORDER BY pread.postsRead DESC";
                    ResultSet rs2 = statsStmt.executeQuery(query2);

                    //Skriver ut resultatet av spørringen, hvor den brukerene kommer i synkende
                    //rekkefølge etter hvor mange poster de har lest
                    System.out.println("Use Case 5:");
                    while(rs2.next()){
                        System.out.println("Bruker: " + rs2.getString("pread.name_") + ", Antall innlegg lest: " + rs2.getInt("pread.postsRead") + ", Antall innlegg skrevet: " + rs2.getInt("posts.postsBy"));
                    }
                    statsStmt.close();
                    System.out.println("");
                } catch (Exception e) {
                    System.out.println("DB error during SELECT from postsRead, postedBy " + e);
                } 
            } else {
                System.out.println("Brukeren er ikke en instruktør");
            };
            isInstructorStmt.close();
            
        } catch (Exception e) {
            System.out.println("DB error during SELECT from managedBy " + e);
        }
    }
}
