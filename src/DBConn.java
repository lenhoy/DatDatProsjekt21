import java.sql.*;
import java.util.Properties;

public abstract class DBConn {
    protected Connection conn;
    public DBConn () {
    }
    public void connect() {
        try {
         	Class.forName("com.mysql.cj.jdbc.Driver"); 

          	//Properties for user and password.
          	Properties p = new Properties();
			p.put("user", "root");    
			p.put("password", "123456");       

			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/prosjekt?", p);
        } catch (Exception e) {
            throw new RuntimeException("Unable to connect ", e);
		}
    }
}
