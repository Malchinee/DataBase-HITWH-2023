import utils.print;

import java.sql.*;
public class MySQLDemo {

    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://172.18.144.250:3306/social_network";
    // 数据库的用户名与密码，需要根据自己的设置
    static final String USER = "malchinee";
    static final String PASS = "030628";
    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        try{
            // 注册 JDBC 驱动
            Class.forName(JDBC_DRIVER);

            // 打开链接
            System.out.println("连接数据库...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);

            // 执行查询
            System.out.println(" 实例化Statement对象...");

            String view = "create or replace view social_network.my_group " +
                    "as select * from social_network.friends_group where email1="+"\""+ main_console.user_email+"\"";
            PreparedStatement pst = conn.prepareStatement(view);
            System.out.println(view);
            pst.execute();

            String check_view = "select group_name,email2 from social_network.my_group order by group_name";
            pst = conn.prepareStatement(check_view, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = pst.executeQuery();
            print.printResults(rs);

        }catch(SQLException se){
            // 处理 JDBC 错误
            se.printStackTrace();
        }catch(Exception e){
            // 处理 Class.forName 错误
            e.printStackTrace();
        }finally{
            // 关闭资源
            try{
                if(stmt!=null) stmt.close();
            }catch(SQLException se2){
            }// 什么都不做
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
        }
        System.out.println("Goodbye!");
    }
}