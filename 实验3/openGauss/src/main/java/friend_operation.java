import utils.print;

import java.sql.*;
import java.util.Scanner;

public class friend_operation {
    public static void init(Connection conn) throws SQLException {
        while (true) {
            display(conn);
            Scanner scan = main_console.scan;
            System.out.println("A. delete a friend");
            System.out.println("B. add a friend");
            System.out.println("C. revise group name");
            System.out.println("D. show your friend's friend_count and work_count");
            System.out.println("E. quit");
            String option = scan.nextLine();
            if (option.equals("A")) {
                delete(conn);
            } else if (option.equals("B")) {
                add(conn);
            } else if (option.equals("C")) {
                revise_group(conn);
            }
            else if (option.equals("D")) {
                queryDetail(conn);
            }else if (option.equals("E")){
                break;
            }
        }
    }

    public static void display(Connection conn) throws SQLException {
        PreparedStatement pst;
        String check_view = "select group_name, email2 from social_network.my_group order by group_name";
        pst = conn.prepareStatement(check_view, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet rs = pst.executeQuery();
        print.printResults(rs);
    }

    public static void delete(Connection conn) throws SQLException {//通过好友的邮箱删除
        String delete_friend = "delete from social_network.friends_group where email2=?";
        String email2 ;
        Scanner scan = main_console.scan;

        System.out.println("type in delete email");
        email2 = scan.nextLine();
        PreparedStatement pst = null;
        pst = conn.prepareStatement(delete_friend);
        pst.setString(1, email2);
        if (pst.executeUpdate() == 1) {
            System.out.println("删除成功！");
        } else {
            System.out.println("好友中没有此人！");
        }
    }

    public static void add(Connection conn) throws SQLException {//通过好友的邮箱添加
        String add_friend = " INSERT into social_network.friends_group VALUES(?,?,?);";
        String email2 = null;
        String group = null;
        Scanner scan = main_console.scan;


        System.out.println("type in group (type in ”/“ to set group name to null)");
        group = scan.nextLine();
        if (group.equals("/")) {
            group = null;
        }
        System.out.println("type in the friend's email");
        email2 = scan.next();
        PreparedStatement pst = null;
        pst = conn.prepareStatement(add_friend);
        pst.setString(1, main_console.user_email);
        pst.setString(2, email2);
        pst.setString(3, group);
        try{
            if(pst.executeUpdate()==1){
                System.out.println("添加成功");
            }
        }
        catch (Exception e) {
        if (reply.receiverExist(conn, email2)) {
                System.out.println("已经添加过此人!");
            } else {
                System.out.println("查无此人");
            }
        }
    }

    public static void revise_group(Connection conn) throws SQLException {//修改分组;这里对原表进行修改
        String delete_group = "UPDATE social_network.friends_group set group_name=? where group_name=?";
        String delete_group_null="UPDATE social_network.friends_group set group_name=null where group_name is null";

        String group_name = null;
        String old_group_name = null;
        Scanner scan = main_console.scan;
        PreparedStatement pst = null;

        System.out.println("type in old group name (type in ”/“ to set old group name to null)");
        old_group_name = scan.nextLine();
        if (old_group_name.equals("/")) {
            old_group_name=null;
        }

        System.out.println("type in new group name (type in ”/“ to set new group name to null)");
        group_name = scan.nextLine();
        if (group_name.equals("/")) {
            group_name=null;
        }
        if (group_name!=null && old_group_name!=null) {
            pst=conn.prepareStatement(delete_group);
            pst.setString(1, group_name);
            pst.setString(2, old_group_name);
        }
        else if(group_name==null && old_group_name==null){
            pst=conn.prepareStatement(delete_group_null);
        }
        else if(group_name==null) {
            String delete_group_setnull = "UPDATE social_network.friends_group set group_name=null where group_name='"+old_group_name+"'";
            pst=conn.prepareStatement(delete_group_setnull);
            System.out.println(delete_group_setnull);

        }else {
            String delete_group_wherenull = "UPDATE social_network.friends_group set group_name="+"'"+group_name+"'"+" where group_name is null";
            pst=conn.prepareStatement(delete_group_wherenull);
            System.out.println(delete_group_wherenull);
        }

        try{
            pst.executeUpdate();
        }
        catch (SQLException e){
            System.out.println("此小组名不存在!");
        }
    }

    public static void queryDetail(Connection conn) throws SQLException {
        String sql = "SELECT u.email, u.name, " +
                "(SELECT COUNT(*) FROM friends_group " +
                "WHERE email1 = u.email OR email2 = u.email) AS friend_count, " +
                "(SELECT COUNT(*) FROM work_profile WHERE email = u.email) AS work_count " +
                "FROM user u";
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        while(resultSet.next()) {
            System.out.println("email: " + resultSet.getString("email") + ", name: " + resultSet.getString("name")+ ", friend_count: " +
                    resultSet.getString("friend_count") + ", work_count: " + resultSet.getString("work_count"));
        }
    }
}
