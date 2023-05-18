import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class user {
    public static int add_user(Connection conn) throws SQLException {//只写了添加用户的jdbc,重复值警告
        String add_user="insert into social_network.user values(?,?,?,?,?,?,?) ";
        Scanner scan = main_console.scan;
        System.out.println("login email");
        String email= scan.nextLine();
        if(email.equals("") || email.equals(" ")) {
            System.out.println("请输入正确的邮箱");
            return 0;
        }
        System.out.println("name ");
        String name= scan.nextLine();
        System.out.println("gender (type in / to skip)");
        String gender= scan.nextLine();
        if(gender.equals("/")){
            gender= null;
        }
        System.out.println("birth ,the form is 2000-01-01 (type in / to skip)");
        String birth= scan.nextLine();
        if(birth.equals("/")){
            birth= null;
        }
        else {
            try {
                Date.valueOf(birth);
            } catch (Exception e) {
                System.out.println("日期无效！");
                return 0;
            }
        }
        System.out.println("address (type in / to skip)");
        String address= scan.nextLine();
        if(address.equals("/")){
            address= null;
        }
        System.out.println("password");
        String passwd= scan.nextLine();
        if(passwd.equals("") || passwd.equals(" ")) {
            System.out.println("请输入正确的密码");
            return 0;
        }
        System.out.println("email 2 (type in / to skip)");
        String email_2=scan.nextLine();
        if(email_2.equals("/")){
            email_2= null;
        }

        PreparedStatement pst=null;
        pst=conn.prepareStatement(add_user);
        pst.setString(1,email);
        pst.setString(2,name);
        pst.setString(3,gender);
        pst.setDate(4, birth==null?null:Date.valueOf(birth));
        pst.setString(5,address);
        pst.setString(6,passwd);
        pst.setString(7,email_2);
        try{
        pst.execute();}
        catch(Exception e){
            e.printStackTrace();
            System.out.println("邮箱不能重复！");
            return 0;
        }
        main_console.user_email=email;
        return 1;
    }
    public static void revise_user(Connection conn) throws SQLException {
        String revise_user="update social_network.user set email=?,name=?,gender=?,birthday=?,address=?,password=?,other_email=? where email=?";

        Scanner scan = main_console.scan;
        System.out.println("login email");
        String email= scan.nextLine();
        System.out.println("name");
        String name= scan.nextLine();
        System.out.println("gender");
        String gender= scan.nextLine();
        System.out.println("birth,the form is 2000-01-01");
        String birth= scan.nextLine();
        System.out.println("address");
        String address= scan.nextLine();
        System.out.println("password");
        String passwd= scan.nextLine();
        System.out.println("other email");
        String email_2=scan.nextLine();

        PreparedStatement pst=null;
        pst=conn.prepareStatement(revise_user);
        pst.setString(1,email);
        pst.setString(2,name);
        pst.setString(3,gender);
        pst.setDate(4, Date.valueOf(birth));
        pst.setString(5,address);
        pst.setString(6,passwd);
        pst.setString(7,email_2);
        pst.setString(8,main_console.user_email);

        main_console.user_email=email;
    }
}
