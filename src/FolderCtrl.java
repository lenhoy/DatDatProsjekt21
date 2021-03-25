import java.sql.*;

public class FolderCtrl extends DBConn{
    private String email;
    private int courseID;
    
    public FolderCtrl(String email, int courseID) {
        try {
            Statement chechInstr = conn.createStatement();
            String query = "SELECT * FROM managedby WHERE managedby.EMail='" + email + "' AND managedby.CourseID='" + courseID + "'";
            ResultSet rs = chechInstr.executeQuery(query);
            if (rs.next()) {
                this.email = email;
                this.courseID = courseID;
            } else {
                System.out.println("Bruker har ikke adgang");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void createFolder(String name) {
        try {
            PreparedStatement createFolderStmt = conn.prepareStatement("INSERT INTO folder VALUES ((?), (?))");
            createFolderStmt.setString(1, name);
            createFolderStmt.setInt(2, courseID);
            createFolderStmt.execute();
        } catch (SQLException e) {
            System.out.println(e);
        }
    } 

    public void createSubFolder(String superName, String subName) {
        try {
            PreparedStatement createFolderStmt = conn.prepareStatement("INSERT INTO subfolder VALUES ((?), (?), (?), (?))");
            createFolderStmt.setString(1, superName);
            createFolderStmt.setInt(2, courseID);
            createFolderStmt.setString(1, subName);
            createFolderStmt.setInt(2, courseID);
            createFolderStmt.execute();
        } catch (SQLException e) {
            System.out.println(e);
        }
    } 
}
