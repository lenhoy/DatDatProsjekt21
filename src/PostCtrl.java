import java.sql.*;
import java.time.Instant;
import java.util.*;

public class PostCtrl extends DBConn{

    // Lage variabler
    private String userID;
    private int newPostID;
    private PreparedStatement postStatement;
    private PreparedStatement threadStatement;
    private PreparedStatement threadInFolderStatement;


    

    // Konstruktør
    public PostCtrl (String userID) {
        this.userID = userID;
    }

    public void postAThread(int courseID, String folderName, String postContent, String tag, String header){

        // Forberede INSERT i post tablell
        try {
            postStatement = conn.prepareStatement("INSERT INTO Post (?)");
        } catch (Exception e) {
            System.out.println("Database error ved conn INSERT INTO Post:"+e);
        }

        // Utføre insert i post
        try {
            postStatement.setString(1, postContent);

            // Lagre inserted data for å hente ut postID
            ResultSet postedPost =  postStatement.executeQuery();
            postedPost.next();
            newPostID = postedPost.getInt("PostID");

        } catch (Exception e) {
            System.out.println("Database error ved INSERT i post"+postContent+"e:"+e);
        } finally {
            try {
             if (postStatement!= null) {
                 postStatement.close();
             }
            } catch (Exception e) {
                System.out.println(e.getStackTrace());
            }
         }



        // Insette i Thread
        try {
            threadStatement = conn.prepareStatement(
                "INSERT INTO Thread (PostID, Tag, Header) VALUES ((?), (?), (?))");
            threadStatement.setInt(1, newPostID);
            threadStatement.setString(2, tag);
            threadStatement.setString(3, header);
            threadStatement.execute();

        } catch (Exception e) {
            System.out.println("Feil ved insetting i thread: "+e);
        } finally {
            try {
             if (threadStatement != null) {
                 threadStatement.close();
             }
            } catch (Exception e) {
                System.out.println(e.getStackTrace());
            }
         }




        // Insette i Thread in folder
        try {
            threadInFolderStatement = conn.prepareStatement(
                "INSERT INTO ThreadInFolder (CourseID, FolderName, PostID) VALUES ((?), (?), (?))");
            threadInFolderStatement.setInt(1, courseID);
            threadInFolderStatement.setString(2, folderName);
            threadInFolderStatement.setInt(3, newPostID);
            threadInFolderStatement.execute();

        } catch (Exception e) {
            System.out.println("Feil ved insetting i threadInFolder: "+e);
        } finally {
            try {
             if (threadInFolderStatement != null) {
                 threadInFolderStatement.close();
             }
            } catch (Exception e) {
                System.out.println(e.getStackTrace());
            }
         }




        // Insette i Posted By
        insertInPostedBy(userID, newPostID);



    }

    public void postAReply(int superPostID, String postContent) {
        
    }

    private void insertInPostedBy(String userID, int newPostID){

        PreparedStatement postedByStatement = null;


        try {
            postedByStatement = conn.prepareStatement(
                "INSERT INTO PostedBy (Email, PostID, Date) VALUES ((?), (?), (?))");

            // Lage dato timestamp 
            Timestamp timestamp = Timestamp.from(Instant.now());

            // Utføre sql, legge til rad i Posted by
            postedByStatement.setString(1, userID);
            postedByStatement.setInt(2, newPostID);
            postedByStatement.setTimestamp(3, timestamp);
            postedByStatement.execute();

        } catch (Exception e) {
            System.out.println("Feil ved insetting i postedBy: "+e);
        } finally {
           try {
            if (postedByStatement != null) {
                postedByStatement.close();
            }
           } catch (Exception e) {
               System.out.println(e.getStackTrace());
           }
        }
    }




}
