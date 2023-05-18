import utils.print;

import java.sql.*;
import java.util.Scanner;

public class user_profile {
    public static void user_profile_init(Connection conn) throws SQLException {
        String search_edu="select * from social_network.edu_profile where email=?";
        String search_work="select * from social_network.work_profile where email=?";
        PreparedStatement pst=null;

        while (true) {
            pst = conn.prepareStatement(search_edu, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            pst.setString(1, main_console.user_email);
            ResultSet rs = pst.executeQuery();

            pst = conn.prepareStatement(search_work, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            pst.setString(1, main_console.user_email);
            ResultSet rs2 = pst.executeQuery();
            if (!rs.next() && !rs2.next()) {
                System.out.println("You have no profile!");
            } else {
                print.printResults(rs);
                print.printResults(rs2);
            }
            Scanner scan = main_console.scan;
            //press A,utils.print out your own profile
            System.out.println("A. add your education profile");
            System.out.println("B. add your work profile");
            System.out.println("C. revise your education profile");
            System.out.println("D. revise your work profile");
            System.out.println("E. show your schoolmate with school name");
            System.out.println("G. quit");
            String option = scan.nextLine();
            if (option.equals("A")) {
                add_edu_profile(conn);
            } else if(option.equals("B")){
                add_work_profile(conn);
            }else if (option.equals("C")) {
                System.out.println("type in edu_id");
                int edu_id = Integer.parseInt(scan.nextLine());;
                if(edu_exist(conn,edu_id))
                    revise_edu_profile(conn, edu_id);
                else{
                    System.out.println("档案不存在!");
                }
            } else if (option.equals("D")) {
                System.out.println("type in work_id");
                int work_id = Integer.parseInt(scan.nextLine());;
                if(work_exist(conn,work_id))
                    revise_work_profile(conn, work_id);
                else{
                    System.out.println("档案不存在!");
                }
            } else if (option.equals("E")) {
                System.out.println("输入要查询的学校名称");
                String schoolName = scan.nextLine();
                selectInner(conn, schoolName);

            }else {
                break;
            }
        }
    }


    public static boolean edu_exist(Connection conn,int edu_id) throws SQLException {
        String s="select * from social_network.edu_profile where edu_id="+edu_id+" AND email="+"'"+main_console.user_email+"'";
        PreparedStatement stmt=null;
        stmt=conn.prepareStatement(s);
        ResultSet rs=stmt.executeQuery();
        return rs.next();
    }

    public static boolean work_exist(Connection conn,int work_id) throws SQLException {
        String s="select * from social_network.edu_profile where edu_id="+work_id+" AND email="+"'"+main_console.user_email+"'";
        PreparedStatement stmt=null;
        stmt=conn.prepareStatement(s);
        ResultSet rs=stmt.executeQuery();
        return rs.next();
    }

    public static void add_edu_profile(Connection conn) throws SQLException {
        PreparedStatement pst=null;
        Scanner scan = main_console.scan;
        String add_edu="insert into social_network.edu_profile(email, school, degree, beginning, ending) values(?,?,?,?,?)";

        System.out.println("education organization name");
        String school=scan.nextLine();
        System.out.println("degree");
        String degree=scan.nextLine();
        System.out.println("start date,(type / to skip)");
        String start= scan.nextLine();
        if(start.equals("/")){
            start= null;
        }
        else {
            try {
                Date.valueOf(start);
            } catch (Exception e) {
                System.out.println("日期无效！");
                return;
            }
        }

        System.out.println("end date,(type / to skip)");
        String end = scan.nextLine();
        if(end.equals("/")){
            end= null;
        }
        else {
            try {
                Date.valueOf(end);
            } catch (Exception e) {
                System.out.println("日期无效！");
                return ;
            }
        }


        pst=conn.prepareStatement(add_edu);
        pst.setString(1, main_console.user_email);
        pst.setString(2,school);
        pst.setString(3,degree);
        pst.setDate(4, start==null?null:Date.valueOf(start));
        pst.setDate(5, end==null?null:Date.valueOf(end));
        if(pst.executeUpdate()==1){
            System.out.println("添加成功！");
        }
    }

    public static void add_work_profile(Connection conn) throws SQLException {
        PreparedStatement pst = null;
        Scanner scan = main_console.scan;
        String add_work = "insert into social_network.work_profile(email, organization, position, beginning, ending) values(?,?,?,?,?)";

        System.out.println("work organization");
        String organization = scan.nextLine();
        System.out.println("title");
        String title = scan.nextLine();
        System.out.println("start date,(type / to skip)");
        String start= scan.nextLine();
        if(start.equals("/")){
            start= null;
        }
        else {
            try {
                Date.valueOf(start);
            } catch (Exception e) {
                System.out.println("日期无效！");
                return;
            }
        }

        System.out.println("end date,(type / to skip)");
        String end = scan.nextLine();
        if(end.equals("/")){
            end= null;
        }
        else {
            try {
                Date.valueOf(end);
            } catch (Exception e) {
                System.out.println("日期无效！");
                return ;
            }
        }

        pst = conn.prepareStatement(add_work);
        pst.setString(1, main_console.user_email);
        pst.setString(2, organization);
        pst.setString(3, title);
        pst.setDate(4, start==null?null:Date.valueOf(start));
        pst.setDate(5, end==null?null:Date.valueOf(end));

        if (pst.executeUpdate() == 1) {
            System.out.println("添加成功！");
        }
    }

    public static void revise_edu_profile(Connection conn,int edu_id) throws SQLException {
        PreparedStatement pst=null;
        Scanner scan = main_console.scan;
/*        String delete_origin="delete from social_network.edu_profile where email=? AND edu_id=?";
        pst=conn.prepareStatement(delete_origin);
        pst.setString(1,main_console.user_email);
        pst.setInt(2,edu_id);
        pst.execute();*/
        String update_edu = "update social_network.edu_profile set beginning=?,ending=?,school=?,degree=?  where edu_id="+edu_id;
        //String add_edu="insert into social_network.edu_profile values(?,?,?,?)";

        System.out.println("education organization name");
        String school=scan.nextLine();
        System.out.println("degree");
        String degree=scan.nextLine();
        System.out.println("start date,(type / to skip)");
        String start= scan.nextLine();
        if(start.equals("/")){
            start= null;
        }
        else {
            try {
                Date.valueOf(start);
            } catch (Exception e) {
                System.out.println("日期无效！");
                return;
            }
        }

        System.out.println("end date,(type / to skip)");
        String end = scan.nextLine();
        if(end.equals("/")){
            end= null;
        }
        else {
            try {
                Date.valueOf(end);
            } catch (Exception e) {
                System.out.println("日期无效！");
                return ;
            }
        }

        pst=conn.prepareStatement(update_edu);
        pst.setDate(1, start==null?null:Date.valueOf(start));
        pst.setDate(2, end==null?null:Date.valueOf(end));
        pst.setString(3,school);
        pst.setString(4,degree);
        if(pst.executeUpdate()==1){
            System.out.println("修改成功！");
        }
    }

    public static void revise_work_profile(Connection conn,int work_id) throws SQLException {
        PreparedStatement pst=null;
        Scanner scan = main_console.scan;

        String update_work= "update social_network.work_profile set organization=?,position=?,beginning=?,ending=?  where work_id="+work_id;

        System.out.println("work organization");
        String organization= scan.nextLine();
        System.out.println("title");
        String title = scan.nextLine();
        System.out.println("start date,(type / to skip)");
        String start= scan.nextLine();
        if(start.equals("/")){
            start= null;
        }
        else {
            try {
                Date.valueOf(start);
            } catch (Exception e) {
                System.out.println("日期无效！");
                return;
            }
        }

        System.out.println("end date,(type / to skip)");
        String end = scan.nextLine();
        if(end.equals("/")){
            end= null;
        }
        else {
            try {
                Date.valueOf(end);
            } catch (Exception e) {
                System.out.println("日期无效！");
                return ;
            }
        }

        pst=conn.prepareStatement(update_work);
        pst.setString(1,organization);
        pst.setString(2,title);
        pst.setDate(4, start==null?null:Date.valueOf(start));
        pst.setDate(4, end==null?null:Date.valueOf(end));

        if(pst.executeUpdate()==1){
            System.out.println("修改成功！");
        }
    }

    public static void selectInner(Connection conn, String schoolName) throws SQLException {
        String sql = "SELECT u.name, e.email AS email_from_edu_profile  " +
                "FROM user u  " +
                "INNER join social_network.edu_profile e " +
                "ON u.email = e.email  " +
                "WHERE e.school = ? ";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, schoolName);
        ResultSet resultSet = pst.executeQuery();

        while(resultSet.next()) {
            System.out.println("name: " + resultSet.getString("name") + ", email: " + resultSet.getString("email_from_edu_profile"));
        }
    }
}
