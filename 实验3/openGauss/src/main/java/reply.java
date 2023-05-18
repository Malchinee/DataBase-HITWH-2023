import utils.print;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class reply {//可以进行回复
    public static void init(Connection conn) throws SQLException{
        while(true) {
            display(conn);
            Scanner scan = main_console.scan;
            System.out.println("A. reply");
            System.out.println("B. quit");
            String option = scan.nextLine();
            if (option.equals("A")) {
                reply(conn);
            }
            else if (option.equals("B")){
                break;
            }
        }
    }

    public static void display(Connection conn) throws SQLException {
        //reply I receive
        System.out.println("reply I receive");
        String display="SELECT reply,r_time,sender from social_network.reply where receiver=? ;";
        PreparedStatement pst=null;
        pst=conn.prepareStatement(display,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        pst.setString(1,main_console.user_email);
        print.printResults(pst.executeQuery());

        System.out.println("reply I send");
        String display_2="SELECT reply,r_time,receiver from social_network.reply where sender=?";
        pst=conn.prepareStatement(display_2,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        pst.setString(1,main_console.user_email);
        print.printResults(pst.executeQuery());
    }

    public static void reply(Connection conn) throws SQLException {
        String update_share="INSERT INTO social_network.reply(reply,r_time,sender,receiver) VALUES(?,current_timestamp,?,?)";
        String reply=null;
        String receiver=null;
        Scanner scan=main_console.scan;

        System.out.println("receiver");
        receiver=scan.nextLine();
        if(!receiverExist(conn,receiver)){
            System.out.println("不存在该用户！");
            return;
        }
        System.out.println("reply content");
        reply=scan.nextLine();
        PreparedStatement pst=null;
        pst=conn.prepareStatement(update_share);
        pst.setString(1,reply);
        pst.setString(2,main_console.user_email);
        pst.setString(3,receiver);
        if(pst.executeUpdate()==1){
            System.out.println("回复成功！");
        }
    }

    public static boolean receiverExist(Connection conn,String receiver) throws SQLException {
        String exist="SELECT * FROM social_network.user where email=?";
        PreparedStatement pst=null;
        pst=conn.prepareStatement(exist);

        pst.setString(1,receiver);
        ResultSet rs = pst.executeQuery();
        if(!rs.next()) {
            return false;
        }
        return true;
    }
}
