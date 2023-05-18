package utils;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class print {
    public static int getLength(String s){
        int length=0;//保存字符串长度
        String chinese="[\u0391-\uFFE5]";//用来判断字符是否为中文字符
        for (int i = 0; i < s.length(); i++){
            String temp = s.substring(i, i + 1);//拆分字符串得到第i个字符
            if (temp.matches(chinese)){
                length=length+2;//判断为中文字符，记录字符串总长度加二
            }else{
                length=length+1;//判断非中文字符，记录字符串总长度加一
            }
        }
        return length;//返回字符串长度
    }

    public static void printResults(ResultSet rs) {
        try {
            ResultSetMetaData md = rs.getMetaData();
            //为了使该方法适用于任意表，所以需要程序自己读取列信息，ResultSetMetaData 正是服务于此
            int columnCount = md.getColumnCount(); // 获取查询结果集的列数
            int[] maxlength = new int[100];// 保存每一列的最大长度，一般情况下不会有超过100列的表

            while (rs.next()) {//遍历查询结果，获取每一列字符串长度的最大值，保存在数组中
                for (int i = 0; i < columnCount; i++) {
                    int n = getLength(String.valueOf(rs.getObject(i + 1)));//getLenght为自定义方法
                    if (n > maxlength[i]) {
                        maxlength[i] = n;
                    }
                }
            }
            rs.first();//遍历一遍得到每一列的最大长度之后将光标移至起始位置，接下来输出还要遍历一遍

            String colname = "";// 保存每一列的列名
            String line="";//用于构造分隔线
            for (int i = 0; i < columnCount; i++) {
                String name = md.getColumnName(i + 1);//获取列名
                int n1 = getLength(name);//获取列名的长度，与该列最大长度对比
                if (n1 > maxlength[i]) {
                    maxlength[i] = n1;//如果列名长度比该列最大长度还大，则该列最大长度设为列名长度
                }
                int n2 = maxlength[i] - getLength(name) + 3;//最大长度减去列名长度加3作为补充空格的数量
                for (int k = 1; k <= n2; k++) {//循环来补充空格
                    name = name + " ";
                }
                int n3=maxlength[i]+3;//分隔线补充减号的数量
                String subline="";
                for(int k=1;k<=n3;k++){
                    subline = subline + "-";
                }
                colname = colname + "| " + name;//每行的每个元素之间用竖线分隔
                line=line+"+-"+subline;//分隔线在与竖线对齐处添加“+”
            }

            //System.out.println("\nReturn Massage :");//输出内容提示
            System.out.println(line+"+");//输出第一行分隔线
            System.out.println(colname+"|");//输出列名
            System.out.println(line+"+");//输出第二行分隔线

            while (true) {//遍历输出所有元素,每次循环读取一整行
                String out = "";//用于构造每行的输出内容
                String row = null;//用于在循环中临时保存新得到的内容
                for (int i = 0; i < columnCount; i++) {//每次循环读取该行下一列的元素
                    row = String.valueOf(rs.getObject(i + 1));//获取内容并转为字符串
                    int n = maxlength[i] - getLength(row) + 3;//添加空格的数量
                    for (int k = 1; k <= n; k++) {//循环添加空格
                        row = row + " ";
                    }
                    out = out + "| " + row;//构造该行输出内容
                }
                System.out.println(out+"|");//输出该行所有内容
                if(!rs.next())break;//当读取完所有行之后结束循环
                //注：此处的rs.next()不能放到 while 的括号里，因为上面rs.first()光标移动到首位
                //如果 while 执行一次括号里的条件，光标会变到第二行，第一行会被忽略
            }
            System.out.println(line+"+");//输出最后的分隔线
        } catch (Exception e) {
            //System.out.println("wrong!");
            //添加你对错误消息的处理,这里省略
        }
    }
}
