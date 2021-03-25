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

        // Innsette i Thread
        try {
            threadStatement = conn.prepareStatement("INSERT INTO Thread (PostID, Tag, Header) VALUES ((?), (?), (?))");
            threadStatement.setInt(1, newPostID);
            threadStatement.setString(2, tag);
            threadStatement.setString(3, header);
            threadStatement.execute();
        } catch (Exception e) {
            System.out.println("Feil ved innsetting i thread: " + e);
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
            threadInFolderStatement = conn.prepareStatement("INSERT INTO threadinfolder (CourseID, Name_, PostID) VALUES ((?), (?), (?))");
            threadInFolderStatement.setInt(1, courseID);
            threadInFolderStatement.setString(2, folderName);
            threadInFolderStatement.setInt(3, newPostID);
            threadInFolderStatement.execute();

            System.out.println("Use Case 2:");
            System.out.println(userID + " postet i mappen " + folderName + " med tag " + tag);
            System.out.println("");
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

    // Funksjon for å lenke til en post

    public void linkPost(int postID, int linkedID){
        try {
            PreparedStatement linkStmt = conn.prepareStatement("INSERT INTO linked VALUES (?), (?)");
            linkStmt.setInt(1, postID);
            linkStmt.setInt(2, linkedID);
            linkStmt.execute();
            linkStmt.close();
        } catch (Exception e) {
            System.out.println("Klarte ikke å lenke til innlegg " + e);
        }
    }

    // Funksjon for å "like" en post

    public void likePost(int postID) {
        try {
            Timestamp timestamp = Timestamp.from(Instant.now());
            PreparedStatement linkStmt = conn.prepareStatement("INSERT INTO linked VALUES (?), (?), (?)");
            linkStmt.setString(1, userID);
            linkStmt.setInt(2, postID);
            linkStmt.setTimestamp(3, timestamp);
            linkStmt.execute();
            linkStmt.close();
        } catch (Exception e) {
            System.out.println("Klarte ikke å like innlegg " + e);
        }
    }

    // Funksjon for oppretting av enten ny followup eller reply
    public void postAReply(int superPostID, String postContent) {
        
        PreparedStatement stmt; 
        String insertInQuery = "";
        
        // Lag selve posten
        makeNewPost(postContent);

        // Oppdaterer postedBy
        insertInPostedBy(userID, newPostID);

        // Sjekke om det blir en match i thread basert på postID
        try {
            Statement postTypeStmt = conn.createStatement();
            String query = "SELECT * FROM thread WHERE PostID='" + superPostID + "'";
            ResultSet searchThread = postTypeStmt.executeQuery(query);

            //Hvis det blir match, lages en ny followUp
            if (searchThread.next()){
                insertInQuery = "followupinthread (FollowUpID, ThreadID)";
                try {
                    PreparedStatement newFlwUpStmt = conn.prepareStatement("INSERT INTO followup VALUES (?)");
                    newFlwUpStmt.setInt(1, newPostID);
                    newFlwUpStmt.execute();
                    newFlwUpStmt.close();
                } catch (Exception e) {
                    System.out.println("klarte ikke å opprette FollowUp " + e);
                }
            } 

            postTypeStmt.close();

        } catch (Exception e) {
            System.out.println("Exception searchinThread " + e.getMessage());
        }

        // Sjekke om det blir en match i reply basert på PostID
        try {
            Statement postTypeStmt = conn.createStatement();
            String query = "SELECT * FROM reply WHERE PostID='" + superPostID + "'";
            ResultSet searchReply = postTypeStmt.executeQuery(query);

            //Hvis det blir det lages en ny reply
            if (searchReply.next()){
                insertInQuery = "replyinfollowup (ReplyID, FollowUpID)";
                try {
                    PreparedStatement newReplyStmt = conn.prepareStatement("INSERT INTO reply VALUES (?)");
                    newReplyStmt.setInt(1, newPostID);
                    newReplyStmt.execute();
                    newReplyStmt.close();
                } catch (Exception e) {
                    System.out.println("klarte ikke å opprette FollowUp " + e);
                }
            }
            
            postTypeStmt.close();

        } catch (Exception e) {
            System.out.println("Exception searchinReply " + e.getMessage());
        }

        // Opprett posten som follow up. Da skal tabellen
        // FollowUpInThread oppdateres 
        if (insertInQuery != ""){
            try {
                String query = "INSERT INTO " + insertInQuery + " VALUES ((?), (?))";
                stmt = conn.prepareStatement(query);
                stmt.setInt(1, newPostID);
                stmt.setInt(2, superPostID);
                stmt.execute();
                stmt.close();

                System.out.println("Use Case 3: ");
                System.out.println(userID + "  svarte på post");
                System.out.println("");
    
            } catch (Exception e) {
                System.out.println("Feil ved insetting i " + insertInQuery + ": " + e);
            }
        } else {
            System.out.println("Klarte ikke å initialisere query");
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

        // Utføre INSERT i post
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

         // Hente ut postID til nyeste post
         
         try {
            Statement stmt =  conn.createStatement();
            String query = "SELECT PostID from post WHERE PostID >= ALL (SELECT PostID from post)";
             
            ResultSet resSet = stmt.executeQuery(query);
            resSet.next();
            this.newPostID = resSet.getInt("PostID");
             
             
         } catch (Exception e) {
             System.out.println("Klarte ikke hente ut nyeste PostID " + e);
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
