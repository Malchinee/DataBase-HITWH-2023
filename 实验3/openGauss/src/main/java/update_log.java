import utils.print;

import java.sql.*;
import java.util.Scanner;

public class update_log {
    public static void log_init(Connection conn) throws SQLException {
        String option;
        while(true){
            Scanner scan = main_console.scan;
            System.out.println("A. check my log");
            System.out.println("B. check my friends' log");
            System.out.println("C. delete my log");
            System.out.println("D. write my log");
            System.out.println("E. type in log_id to share and comment");
            System.out.println("F: show your log_count and comment_count");
            System.out.println("Q. quit!");
            option = scan.nextLine();
            if (option.equals("A")) {
                search(conn);
            } else if (option.equals("B")) {
                search_friends(conn);
            } else if (option.equals("C")) {
                delete(conn);
            } else if (option.equals("D")) {
                release(conn);
            } else if (option.equals("E")) {
                System.out.println("type in log_id to select a log");
                int log_id;
                log_id= Integer.parseInt(scan.nextLine());
                if (exist(conn, log_id) == 0) {
                    break;
                }
                share.view(conn,log_id);
                comment.view(conn,log_id);
                System.out.println("A. share");
                System.out.println("B. comment");
                option = scan.nextLine();
                if (option.equals("A")) {
                    share.share(conn, log_id);
                } else if (option.equals("B")) {
                    comment.comment(conn, log_id);
                }
            }else if(option.equals("F")) {
                System.out.println("你的logCount和commentCount");
                queryCount(conn);
            }
            else if (option.equals("Q")){
                break;
            }
        }
    }

    //嵌套
    public static void search_friends(Connection conn) throws SQLException {
        String search_log="select * from social_network.log where email IN " +
                "(SELECT email2 from social_network.friends_group where email1=?)";
        PreparedStatement pst=null;
        pst=conn.prepareStatement(search_log,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        pst.setString(1,main_console.user_email);
        ResultSet rs=pst.executeQuery();
        //输出结果
        print.printResults(rs);
    }//B

    public static void search(Connection conn) throws SQLException {
        String search_log="SELECT * from social_network.log where email=?";
        PreparedStatement pst;
        pst=conn.prepareStatement(search_log,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        pst.setString(1,main_console.user_email);
        ResultSet rs=pst.executeQuery();

        //utils.print
        print.printResults(rs);
    }//A

    public static void release(Connection conn) throws SQLException {
        String update_sql="INSERT INTO social_network.log(email,last_update,content) VALUES(?,current_timestamp,?)";
        PreparedStatement pst=null;
        pst=conn.prepareStatement(update_sql);

        Scanner scan=main_console.scan;
        String content=null;

        System.out.println("type in log content");
        content=scan.nextLine();
        pst.setString(1,main_console.user_email);
        pst.setString(2,content);
        if(pst.executeUpdate()>=1){
            System.out.println("upload log success");
        }
        else{
            System.out.println("upload log failed");
        }
    }//D

    public static void delete(Connection conn) throws SQLException {
        String delete_sql="DELETE FROM social_network.log where log_id=?";
        PreparedStatement pst=null;
        pst=conn.prepareStatement(delete_sql);

        int log_id;
        System.out.println("type in log_id");
        log_id= Integer.parseInt(main_console.scan.nextLine());
        pst.setInt(1,log_id);
        int count = pst.executeUpdate();
        if(count==1){
            System.out.println("删除成功!");
        }
        else{
            System.out.println("不存在该日志");
        }
    }//C

    public static int exist(Connection conn,int log_id) throws SQLException {
        String exist_sql="SELECT * FROM social_network.log where log_id=?";
        PreparedStatement pst=null;
        pst=conn.prepareStatement(exist_sql);

        pst.setInt(1,log_id);
        ResultSet rs = pst.executeQuery();
        if(!rs.next()) {
            System.out.println("不存在该日志！");
            return 0;
        }
        return 1;
    }

    public static void update(Connection conn)throws SQLException {
        String update_sql="UPDATE social_network.log SET content=? where log_id=?";
        PreparedStatement pst=null;
        pst=conn.prepareStatement(update_sql);

        String log_id=null;
        String content=null;
        pst.setString(1,content);
        pst.setString(2,log_id);
    }



    public static void queryCount(Connection conn) throws SQLException {
        String sql = "SELECT u.email, COUNT(l.log_id) AS log_count, COUNT(c.comment) AS comment_count " +
                "FROM social_network.user as u " +
                "LEFT JOIN log l ON u.email = l.email " +
                "LEFT JOIN comment c ON l.log_id = c.log_id " +
                "WHERE u.email = " + main_console.user_email +
                " GROUP BY u.email ";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);;
        while (rs.next()) {
            String email = rs.getString("email");
            int logCount = rs.getInt("log_count");
            int commentCount = rs.getInt("comment_count");
            System.out.println("Email: " + email + ", Log Count: " + logCount + ", Comment Count: " + commentCount);
        }
    }
}
