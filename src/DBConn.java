import java.sql.*;
import java.util.Properties;

import javax.print.event.PrintEvent;

public abstract class DBConn {
  protected Connection conn;
  public DBConn () {
  }
  public void connect() {
    try {
          // Class.forName("com.mysql.jdbc.Driver").newInstance(); when you are using MySQL 5.7
    Class.forName("com.mysql.cj.jdbc.Driver"); 

            // Properties for user and password.
            Properties p = new Properties();
            p.put("user", "Lars");           

<<<<<<< HEAD
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/prosjekt?allowPublicKeyRetrieval=true&autoReconnect=true&useSSL=false",p);
=======
            conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/?user=lenhoy",p);
>>>>>>> c9859ae4dfadf279739c6de4124406d74bf06ad0
     
            System.out.println("Hello world 2!");

      } catch (Exception e)
    {
          throw new RuntimeException("Unable to connect", e);
    }
  }
}
