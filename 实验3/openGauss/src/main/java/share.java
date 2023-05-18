import utils.print;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class share {
    public static void share(Connection conn, int log_id) throws SQLException {
        //view(conn,log_id);
        String update_share="INSERT INTO social_network.share (log_id, share, s_time) VALUES(?,?,current_timestamp)";
        String share=null;
        Scanner sc=main_console.scan;
        System.out.println("type in comment about your share (type / to skip)");
        share=sc.nextLine();
        if(share.equals("/"))
            share=null;

        PreparedStatement pst=null;
        pst=conn.prepareStatement(update_share);
        pst.setInt(1,log_id);
        pst.setString(2,share);
        System.out.println(pst);
        if(pst.executeUpdate()==1)
            System.out.println("share success");
    }

    public static void view(Connection conn,int log_id) throws SQLException {
        String view_share="SELECT * from social_network.share where log_id="+log_id;

        PreparedStatement pst=null;
        pst=conn.prepareStatement(view_share,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        ResultSet rs=pst.executeQuery();
        if(rs.next())
            print.printResults(rs);
        else{
            System.out.println("this log have no share!");
        }
    }
}
