import java.sql.*;

public class Connect {



       private static Connection connection = null;
       //private static Statement statement= null;




       private static final String jdbcUrl = "jdbc:mysql://localhost:3306/library";
        private static final String username = "root";
        private static final String password = "";



        public static Connection getConnection() {
            if (connection == null) {
                try {
                    // Create a new database connection if it doesn't exist
                    connection  = DriverManager.getConnection(jdbcUrl, username, password);
                    //Statement statement = connection.createStatement();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return connection;
        }

        public static void closeConnection() {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    connection=null;
                }

            }

        }
    }



