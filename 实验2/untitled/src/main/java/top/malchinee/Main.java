package top.malchinee;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        CompanyQuery CompanyQuery = new CompanyQuery();
        Scanner scanner = new Scanner(System.in);
        while(true) {
            String s = scanner.nextLine();
            if(s.equals("exit")) break;
            String[] argv = s.split(" ");
            String number = argv[2];
            switch (number) {
                case "1": {
                    for(int i = 5; i < argv.length; i ++) {
                        argv[4] += " " +argv[i];
                    }
                    CompanyQuery.queryOne(argv[4]);
                    continue;
                }
                case "2": {
                    for(int i = 5; i < argv.length; i ++) {
                        argv[4] += " " +argv[i];
                    }
                    CompanyQuery.queryTwo(argv[4]);
                    continue;
                }
                case "3": {
                    for(int i = 5; i < argv.length; i ++) {
                        argv[4] += " " +argv[i];
                    }
                    CompanyQuery.queryThree(argv[4]);
                    continue;
                }
                case "4": {
                    for(int i = 5; i < argv.length - 1; i ++) {
                        argv[4] += " "+ argv[i];
                    }
                    CompanyQuery.queryFour(argv[4], Integer.parseInt(argv[argv.length - 1]));

                    continue;
                }
                case "5": {
                    for(int i = 5; i < argv.length; i ++) {
                        argv[4] += " " +argv[i];
                    }
                    CompanyQuery.queryFive(argv[4]);
                    continue;
                }
                case "6": {
                    for(int i = 5; i < argv.length; i ++) {
                        argv[4] += " " +argv[i];
                    }
                    CompanyQuery.querySix(argv[4]);
                    continue;
                }
                case "7": {
                    CompanyQuery.querySeven(argv[4], argv[5]);
                    continue;
                }
                case "8": {
                    for(int i = 5; i < argv.length; i ++) {
                        argv[4] += " " +argv[i];
                    }
                    CompanyQuery.queryEight(Integer.parseInt(argv[4]));
                    continue;
                }
                case "9": {
                    CompanyQuery.queryNine(Integer.parseInt(argv[4]), Integer.parseInt(argv[5]));
                    continue;
                }
                default: {
                    System.out.println("repeat !!!");
                }
            }
        }
    }
}
