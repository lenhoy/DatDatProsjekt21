import java.sql.*;
import java.util.*;

public class PostCtrl extends DBConn{

    // Lage variabler
    private int userID;
    private int newPostID;
    private PreparedStatement postStatement;

    

    // Autentiser om bruker har tilgang
    // Konstruktør
    public PostCtrl (int userID) {
        this.userID = userID;
    }

    public void startThread(int courseID, String folderName, String postContent, String tag, String header){

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
        }



        // Insette i Thread
        try {
            PreparedStatement threadStatement = conn.prepareStatement(
                "INSERT INTO Thread (PostID, Tag, Header) VALUES ((?), (?), (?))");
            threadStatement.setString(1, newPostID);
            threadStatement.setString(2, tag);
            threadStatement.setString(3, header);
            threadStatement.execute();

        } catch (Exception e) {
            System.out.println("Feil ved insetting i thread: "+e);
        }


        // Insette i Thread in folder
        try {
            PreparedStatement threadInFolderStatement = conn.prepareStatement(
                "INSERT INTO ThreadInFolder (CourseID, FolderName, PostID) VALUES ((?), (?), (?))");
            threadInFolderStatement.setString(1, courseID);
            threadInFolderStatement.setString(2, folderName);
            threadInFolderStatement.setString(3, newPostID);
            threadInFolderStatement.execute();

        } catch (Exception e) {
            System.out.println("Feil ved insetting i threadInFolder: "+e);
        }


        // Forberede insert i postedby

        // Utføre neste


    }

    public void postPost () {

        if (userID != IKKE_LOGGET_INN){

            

        }

    }




}
