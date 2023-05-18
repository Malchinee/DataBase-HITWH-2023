import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class login {
    public static boolean login(Connection conn) throws SQLException {
        String option;
        while (true) {
            System.out.println("type in C to create new account, type in L to login,type in Q to quit");
            option=main_console.scan.nextLine();
            if(option.equals("C")){
                int flag = user.add_user(conn);
                if(flag == 1) {
                    System.out.println("register success!");
                    return true;
                }else if(flag == 0) {
                    System.out.println("注册失败");
                    return false;
                }
            }
            else if(option.equals("L")) {
                System.out.println("请输入用户邮箱");
                String email = main_console.scan.nextLine();

                System.out.println("请输入密码");
                String passwd = main_console.scan.nextLine();

                String login = "select * from social_network.user where email = ? and password = ?";
                PreparedStatement pst  = conn.prepareStatement(login);
                pst.setString(1, email);
                pst.setString(2, passwd);

                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    System.out.println("login success!");
                    main_console.user_email = email;
                    return true;
                } else
                    System.out.println("login failed,try again!");
            }
            else if(option.equals("Q")){
                System.exit(0);
            }
        }
    }
}

