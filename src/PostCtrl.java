import java.sql.*;
import java.time.Instant;
import java.util.*;

import com.ibm.dtfj.corereaders.NewAixDump;
import com.mysql.cj.Query;
import com.mysql.cj.conf.ConnectionUrl.Type;

public class PostCtrl extends DBConn{

    // Lage variabler
    private String userID;
    private int newPostID;

    

    // Konstruktør
    public PostCtrl (String userID) {
        this.userID = userID;
    }

    // Funksjon for oppretting av en ny thread
    public void postAThread(int courseID, String folderName, String postContent, String tag, String header){

        PreparedStatement threadStatement = null;
        PreparedStatement threadInFolderStatement = null;

        // Innsette i Post
        makeNewPost(postContent);



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
                "INSERT INTO threadinfolder (CourseID, Name_, PostID) VALUES ((?), (?), (?))");
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

    // Funksjon for oppretting av enten ny followup eller reply
    public void postAReply(int superPostID, String postContent) {

        PreparedStatement stmt; 

        // Lag selve posten
        makeNewPost(postContent);

        // Oppdatter postedBy
        insertInPostedBy(userID, newPostID);


        // Bestemm om posten er en follow up eller en reply
        int result = -1;
        
        
        // Sjekke om det blir en match i thread basert på postID
        try {
            Statement postTypeStmt = conn.createStatement();
            String query = "SELECT * FROM thread WHERE PostID="+superPostID;
            ResultSet searchThread = postTypeStmt.executeQuery(query);

            if (searchThread.next()){
                result = 1;
            } 

            postTypeStmt.close();

        } catch (Exception e) {
            System.out.println("Exception searchinThread"+e.getMessage());
        }
        

        // Sjekke om det blir en match i reply basert på PostID
        try {
            Statement postTypeStmt = conn.createStatement();
            String query = "SELECT * FROM reply WHERE PostID="+superPostID;
            ResultSet searchReply = postTypeStmt.executeQuery(query);

            if (searchReply.next()){
                result = 2;
            }
            
            postTypeStmt.close();

        } catch (Exception e) {
            System.out.println("Exception searchinReply"+e.getMessage());
        }
        

        String insertInQuery;
        if (result == 1){
            insertInQuery = "followupinthread (FollowUpID, ThreadID)";
        } else if (result == 2) {
            insertInQuery = "replyinfollowup (ReplyID, FollowUpID)";
        } else {
            throw new IllegalArgumentException("Finner ikke posten som skal svares på");
        }

        // Opprett posten som follow up. Da skal tabellen
        // FollowUpInThread oppdatteres 
        try {
            String query = "INSERT INTO " + insertInQuery + " VALUES ((?), (?))";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, newPostID);
            stmt.setInt(2, superPostID);
            stmt.execute();

            stmt.close();

        } catch (Exception e) {
            System.out.println("Feil ved insetting i "+insertInQuery+": "+e);
        }


    }

    // Intern funksjon for oppretting av rad i post tabell
    private void makeNewPost(String postContent){
    

        PreparedStatement postStatement = null;


        // Forberede INSERT i post tablell
        try {
            postStatement = conn.prepareStatement("INSERT INTO post (content) VALUES (?)");
        } catch (Exception e) {
            System.out.println("Database error ved conn INSERT INTO Post preparedStmt: "+e);
        }

        // Utføre insert i post
        try {
            postStatement.setString(1, postContent);
            postStatement.execute();
        } catch (Exception e) {
            System.out.println("Database error ved INSERT i post: "+postContent+"e:"+e);
        } finally {
            try {
             if (postStatement!= null) {
                 postStatement.close();
             }
            } catch (Exception e) {
                System.out.println(e.getStackTrace());
            }
         }

         // Hente ut postID til nyeste posten
         
         try {
            Statement stmt =  conn.createStatement();
            String query = "SELECT PostID from post WHERE PostID >= ALL (SELECT PostID from post)";
             
            ResultSet resSet = stmt.executeQuery(query);
            resSet.next();
            newPostID = resSet.getInt("PostID");
             
             
         } catch (Exception e) {
             //TODO: handle exception
         }
    }

    // Intern funksjon for oppretting av rad i postedby tabell
    private void insertInPostedBy(String userID, int newPostID){

        PreparedStatement postedByStatement = null;


        try {
            postedByStatement = conn.prepareStatement(
                "INSERT INTO postedby (Email, PostID, Date_) VALUES ((?), (?), (?))");

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
