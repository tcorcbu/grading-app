package db;

public class MySQLDBUtil {
    private static final String HOSTNAME = "localhost";
    private static final String PORT_NUM = "3306"; // change it to your mysql port number
    public static final String DB_NAME = "gradingapp";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "lyz950222"; //change your password here
    public static final String URL = "jdbc:mysql://" + HOSTNAME + ":" + PORT_NUM + "/" + DB_NAME
            + "?user=" + USERNAME + "&password=" + PASSWORD + "&autoreconnect=true";
}