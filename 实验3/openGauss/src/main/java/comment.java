import utils.print;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class comment {
    public static void comment(Connection conn,int log_id) throws SQLException {
        //view(conn,log_id);
        String update_comment="INSERT INTO social_network.comment VALUES(?,?,LOCALTIME)";
        String comment=null;
        Scanner sc=main_console.scan;
        System.out.println("type in comment");
        comment=sc.nextLine();

        PreparedStatement pst=null;
        pst=conn.prepareStatement(update_comment);
        pst.setInt(1,log_id);
        pst.setString(2,comment);
    }

    public static void view(Connection conn,int log_id) throws SQLException {
        String view_comment="SELECT * from social_network.comment where log_id="+log_id;

        PreparedStatement pst;
        pst=conn.prepareStatement(view_comment,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        ResultSet rs=pst.executeQuery();
        if(rs.next())
            print.printResults(rs);
        else{
            System.out.println("this log have no comment!");
        }
    }
}