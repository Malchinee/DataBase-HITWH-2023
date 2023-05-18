import java.sql.*;
import java.util.Objects;
import java.util.Scanner;


public class main_console {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://172.18.144.250:3306/social_network";
    static final String USER = "malchinee";
    static final String PASS = "030628";
    static String user_email = null;
    static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        Connection conn = null;
        try {
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);

            // 打开链接
            System.out.println("连接数据库...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            while(true) {
                if(login.login(conn)) {
                    break;
                }
            }

            System.out.println("press Q to quit,press C to continue");

            String option = scan.nextLine();
            while (!Objects.equals(option, "Q")) {
                System.out.println("A. check profile");
                System.out.println("B. check log");
                System.out.println("C. reply");
                System.out.println("D. check friend group");
                option = scan.nextLine();
                if (option.equals("A")) {
                    user_profile.user_profile_init(conn);
                } else if (Objects.equals(option, "B")) {
                    update_log.log_init(conn);
                } else if (Objects.equals(option, "C")) {
                    reply.init(conn);
                }
                else if(Objects.equals(option, "D")){
                    friend_operation.init(conn);
                }

            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
