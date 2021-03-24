import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ThreadCtrl extends DBConn {
    // getPoster(PostId) -> Person that posted thread
    // endre til selve innholdet 
    // done av Torkil 



    public String getPoster(int postID) { // trøbbel med å få personID til å oppdateres inne i try statement
        String poster = null; 
        Statement stmt;

        try {

            stmt = conn.createStatement();
            String query = "select * from post, person where postedBy = " + Integer.toString(postID);

            ResultSet rSet = stmt.executeQuery(query);
            while (rSet.next()) {
               poster = rSet.getString("firstname") + " " + rSet.getString("lastName"); 

            }
            stmt.close();

        } catch (Exception e) {
            System.out.println("Error");
        }

        return poster; 

    }

    // getReplies(PostId) -> All replies in given thread
    //endre til innhold 
    // done av Torkil 
    public ArrayList<String> getReplies(int postID) { // returnerer en arraylist med replyID
        ArrayList<String> replies = new ArrayList<String>();
        Statement stmt;

        try {
            stmt = conn.createStatement();
            String query = "select * from reply, post where reply.postID = " + Integer.toString(postID);

            ResultSet rSet = stmt.executeQuery(query);
            while (rSet.next()) {
                replies.add(rSet.getString("content")); 

            }
            stmt.close();

        } catch (Exception e) {
            System.out.println("Error");
        }

        return replies;
    }

    // getPostColor(PostId) -> Returns color status of given post
    // red, yellow, green;  green = instruktør, yellow = student, red  er ingen.   grrr. liker ikke overvåkende sjefer :( ) 
    public String getPostColor(int postID) {
        String postColor = null;
        Statement stmt;

        try {
            stmt = conn.createStatement();
            String query = "SELECT * FROM POST where postid =" + Integer.toString(postID);

            ResultSet rSet = stmt.executeQuery(query);
            while (rSet.next()) {
                postColor = rSet.getString("postColor");
                System.out.println(postColor);

            }
            stmt.close();
        } catch (Exception e) {
            System.out.println("Error");
        }
        return postColor;

    }


    // getPost(PostId) -> All info on post with given id
    // 
    public String getPost(int postID) {
        String post = null;
        Statement stmt = null;

        try {
            stmt = conn.createStatement();
            String query = "SELECT * FROM POST where postid =" + Integer.toString(postID);
            

            ResultSet rSet = stmt.executeQuery(query);
            String postString = null;
            while (rSet.next()) {

                String dateString = new SimpleDateFormat("yyyyMMdd").format(rSet.getTimestamp("timedate"));
                String posterName = getPoster(rSet.getInt("PostedBy"));
                postString = 
                    "   PostId: " + rSet.getString("postId") 
                    + "   Title: " + rSet.getString("title") 
                    + "   Tag: " + rSet.getString("Tag")
                    + "   Time:" + dateString 
                    + "   Color Status:" + rSet.getString("ColorStatus") 
                    + "   Posted By:" + posterName
                    + "   Content:\n" + rSet.getString("Content");

            }
            stmt.close();
            return postString;
        } catch (Exception e) {
            System.out.println("Error");
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (Exception e) {
                System.out.println(e.getStackTrace());
            }
        }

        return post;

    }

    // createPost(Title, Content, Tag, PostedBy, containedInFolder)
    public void createPost(String title, String content, String tag, int postedBy, int containedInFolder) {
        PreparedStatement regStatement; 
        try {
            regStatement = conn.prepareStatement(
                    "INSERT INTO Post (Title, Content, Tag, Colorstatus, PostedBy, Containedinfolder) values ((?), (?), (?), (?), (?), (?))");
            regStatement.setString(1, title);
            regStatement.setString(2, content);
            regStatement.setString(3, tag);
            regStatement.setString(4, "red");    //red er ubesvart; så starter der. 
            regStatement.setInt(5, postedBy);
            regStatement.setInt(6, containedInFolder);
            regStatement.execute();
        } catch (Exception e) {
            System.out.println("Error during registration of Person");
        } finally {
            try {
                if (regStatement != null) {
                    regStatement.close();
                }
            } catch (Exception e) {
                System.out.println(e.getStackTrace());
            }
        }
    }

    // Adds a new reply to database, belongs to a post
    public void createReply(int postId, int personId, String content) {
        PreparedStatement regStatement = null;
        try {
            // TODO: Find reply-type; instructor or student
            // This should be a method in CourseCtrl
            String replyType;
            if (CourseCtrl.isInstructor(personId)) {
                replyType = "INSTRUCTOR";
            } else {
                replyType = "STUDENT";
            }

            // ReplyId doesn't auto-increment, so this statement won't run until we fix DB
            regStatement = conn.prepareStatement(
                    "INSERT INTO Reply (PostId, RepliedBy, ReplyType, Content) VALUES ((?), (?), (?), (?))");

            regStatement.setInt(1, postId);
            regStatement.setInt(2, personId);
            regStatement.setString(3, replyType);
            regStatement.setString(4, content);
        } catch (Exception e) {
            System.out.println(e.getStackTrace());
        } finally {
            try {
                if (regStatement != null) {
                    regStatement.close();
                }
            } catch (Exception e) {
                System.out.println(e.getStackTrace());
            }
        }
    }

    // Handles all actions for when a user opens a post
    public void personViewsPost(int personId, int postId) {
        PreparedStatement regStatement = null;
        try {
            regStatement = conn.prepareStatement(
                    "INSERT INTO PersonPostStatus (Person, Post, Viewed, Liked) VALUES ( (?), (?), TRUE, FALSE )");
            regStatement.setInt(1, personId);
            regStatement.setInt(2, postId);
            regStatement.execute();
        } catch (Exception e) {
            System.out.println("Error when registrating person " + String.valueOf(personId) + " to have viewed post "
                    + String.valueOf(postId));
        } finally {
            try {
                if (regStatement != null) {
                    regStatement.close();
                }
            } catch (Exception e) {
                System.out.println(e.getStackTrace());
            }
    public Integer getFolderId(String folderName) {
        Statement stmt; 
        Integer folderId = null; 
        try {
            stmt = conn.createStatement();
            String query = "select * from folder where folderName =" + folderName; 

            ResultSet rSet = stmt.executeQuery(query);
            while (rSet.next()) {
                folderId = rSet.getInt("folderID");  

            }
            stmt.close();

        } catch (Exception e) {
            System.out.println("Error");
        }


        return folderId; 

    }

    
    // createReply(PostId, PersonId, Content) -> Adds a new reply to database
    // torkil fikser, husk å finne kode for autroinkrementering av svak ID. 
    // done,  autorinkremnet fiksa på linje 136 
    public void createReply(int postId, int personId, String content, boolean isInstructor) {
        PreparedStatement regStatement; 
        
        try {
            regStatement = conn.prepareStatement(
                    "INSERT INTO reply (replyId, postID, content, replytype, repliedby) values ((?), (?), (?), (?), (?))");
            regStatement.setInt(1, getReplies(postId).size() + 1); 
            regStatement.setInt(2, postId); 
            regStatement.setString(3, content);
            regStatement.setString(4, "replytype"); //hvordan bestemmes replytype; 
            regStatement.setInt(5, personId);
            regStatement.execute();
            if(isInstructor) 
            changeColorStatus(postId, "green");
            else
            changeColorStatus(postId, "yellow");
        }
        catch (Exception e) {
            System.out.println("Error during registration of Person");
        }
    }

    public void changeColorStatus(int poistID, String color) {
        PreparedStatement regStatement; 

        try {
            regStatement = conn.prepareStatement(
                        "update post set colorstatur =" + color); 
            regStatement.execute(); 

        }
        catch (Exception e) {
            System.out.println("Error"); 
        }
    }
        

    // Returns whether user has seen post before
    public boolean hasPersonSeenPost(int personId, int postId) {

        String personString = String.valueOf(personId);
        String postString = String.valueOf(postId);

        boolean personSeenPost = false;
        Statement stmt = null;
        String sqlQuery = "SELECT Viewed FROM PersonPostStatus WHERE (Person = " + personString + " AND Post = "
                + postString + ");";

        try {

            stmt = conn.createStatement();
            ResultSet rSet = stmt.executeQuery(sqlQuery);
            personSeenPost = rSet.getBoolean("Viewed");
            stmt.close();

        } catch (Exception e) {
            System.out.println(e.getStackTrace());

        } finally {

            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (Exception e) {
                System.out.println(e.getStackTrace());
            }
        }

        return personSeenPost;
    }

    // Handles all actions for when a user likes a post
    public void personLikesPost(int personId, int postId) {
        PreparedStatement regStatement = null;
        try {
            regStatement = conn
                    .prepareStatement("UPDATE PersonPostStatus SET (Person=?, Post=?, Viewed=TRUE, Liked=TRUE)");
            regStatement.setInt(1, personId);
            regStatement.setInt(2, postId);
            regStatement.executeUpdate();
        } catch (Exception e) {
            System.out.println("Error when registrating person " + String.valueOf(personId) + " to have viewed post "
                    + String.valueOf(postId));
        } finally {
            try {
                if (regStatement != null) {
                    regStatement.close();
                }
            } catch (Exception e) {
                System.out.println(e.getStackTrace());
            }
        }
    }

    // searchForPostInCourse(CourseId, keyword) -> All posts with matches in title
    // or content from current course
    // torkil fikser 
    public ArrayList<Integer> searchForPostInCourse(int courseId, String keyword) {
        ArrayList<Integer> posts = new ArrayList<Integer>();
        Statement stmt;

        try {
            stmt = conn.createStatement();
            String query = "SELECT * FROM POST natural inner join folder natural inner join course where content like %"+keyword+"% or title like %" + keyword + "%"  ;

            ResultSet rSet = stmt.executeQuery(query);
            while (rSet.next()) {
                if(rSet.getInt("courseID") == courseId) {
                    posts.add(rSet.getInt("postId")); 
                }

            }
            stmt.close();

        } catch (Exception e) {
            System.out.println("Error");
        }

        

        return posts; 

        
    }


}
