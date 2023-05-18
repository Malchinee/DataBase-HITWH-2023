package top.malchinee;

import java.sql.*;
import java.util.Scanner;

public class CompanyQuery {
    private static String DB_URL = "jdbc:mysql://172.18.144.250:3306/company?useSSL=false&serverTimezone=UTC";
    private static String username = "malchinee";
    private static String password = "030628";

    private Connection conn;

    private PreparedStatement pstmt;

    private ResultSet resultSet;
    public CompanyQuery() {
        try {
            conn = DriverManager.getConnection(DB_URL, username, password);
            pstmt = null;
            resultSet = null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void queryOne(String argv) {
        String sql = "select essn from works_on where pno = ?";

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, argv);
            resultSet = pstmt.executeQuery();
            System.out.println("参加了项目编号为" + argv + "的员工号为：");
            while(resultSet.next()) {
                String essn = resultSet.getString("essn");
                System.out.println(essn);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void queryTwo(String argv) throws SQLException {
       String sql = "select ename from employee, project, works_on " +
               "where project.pname = ? " +
               "and project.pno = works_on.pno " +
               "and works_on.essn = employee.essn";
       pstmt = conn.prepareStatement(sql);
       pstmt.setString(1, argv);
        resultSet = pstmt.executeQuery();
        System.out.println("参加了项目名为" + argv + "的员工名字");
        while(resultSet.next()) {
            System.out.println(resultSet.getString("ename"));
        }
    }


    public void queryThree(String argv) {
        String sql = "select ename, address from employee, department " +
                "where department.dname = ?" +
                "and  employee.dno = department.dno";
        System.out.println("在" + argv +"工作的所有工作人员的名字和地址");
        System.out.println("ename" + "\t" + "address");
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, argv);
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getString("ename") + "\t" + resultSet.getString("address"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void queryFour(String dname, int salary) {
        String sql = " select ename, address from employee as e, department as d " +
                "where d.dname = ? " +
                "and d.dno = e.dno " +
                "and e.salary < ? ";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, dname);
            pstmt.setInt(2, salary);
            System.out.println("在" + dname + "工作且工资低于" + salary +"元的员工名字和地址");
            System.out.println("ename" + "\t | \t" + "address");
            resultSet = pstmt.executeQuery();
            while(resultSet.next()) {
                System.out.println(resultSet.getString("ename") + "\t | \t" + resultSet.getString("address"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void queryFive(String pno) {
        String sql = "select ename from employee " +
                "where not exists (" +
                        "select * from works_on " +
                        "where essn = employee.essn " +
                        "and pno = ? )";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, pno);
            resultSet = pstmt.executeQuery();
            System.out.println("没有参加项目编号为" + pno + "的项目的员工姓名");
            while(resultSet.next()) {
                System.out.println(resultSet.getString("ename"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void querySix(String ename) {
        String sql = "select ename, dname from employee " +
                "natural join department " +
                "where superssn in " +
                        "(select essn from employee " +
                        "where ename = ?)";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, ename);
            resultSet = pstmt.executeQuery();
            System.out.println("由" + ename +"领导的工作人员的姓名和所在部门的名字");
            System.out.println("ename" + "\t  | \t" + "dname");
            while(resultSet.next()) {
                System.out.println(resultSet.getString("ename") + "\t  | \t" + resultSet.getString("dname"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void querySeven(String pno1, String pno2) {
        String sql = " select essn from employee as e " +
                "where exists " +
                    "(select * from works_on " +
                    "where essn = e.essn and pno = ? ) " +
                "and  exists " +
                    "(select * from works_on" +
                    " where essn = e.essn and pno = ?)";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, pno1);
            pstmt.setString(2, pno2);
            System.out.println("至少参加了项目编号为" + pno1 + "和"+ pno2 +"的项目的员工号");
            resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getString("essn"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void queryEight(int salary) {
        String sql = "select dname from department " +
                "natural join employee " +
                "group by dno " +
                "having avg(salary) < ?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, salary);
            resultSet = pstmt.executeQuery();
            System.out.println("员工平均工资低于" + salary + "元的部门名称");
            while(resultSet.next()) {
                System.out.println(resultSet.getString("dname"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void queryNine(int n, int hour) {
        String sql = "select ename from employee " +
                "where essn in " +
                    "(select essn from works_on " +
                    "group by essn " +
                    "having count(distinct pno) >= ? " +
                    "and sum(hours) >= ?)";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, n);
            pstmt.setInt(2, hour);
            System.out.println("至少参与了" + n +"个项目且工作总时间不超过" + hour +"小时的员工名字");
            resultSet = pstmt.executeQuery();
            while(resultSet.next()) {
                System.out.println(resultSet.getString("ename"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
