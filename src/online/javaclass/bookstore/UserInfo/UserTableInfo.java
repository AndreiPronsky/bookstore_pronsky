package online.javaclass.bookstore.UserInfo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class UserTableInfo {
    public static void printTableInfo(String Url, String User, String Password) {
        try (Connection connection = DriverManager.getConnection(Url, User, Password)) {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM users");
            ResultSetMetaData meta = result.getMetaData();
            System.out.printf("------------------------------------------------------------------------%n");
            System.out.printf("                                " + meta.getTableName(1) + "%n");
            System.out.printf("------------------------------------------------------------------------%n");
            System.out.printf("| %-20s | %-12s | %-16s | %-11s |%n", "COLUMN", "TYPE", "IS AUTOINCREMENT", "IS NULLABLE");
            System.out.printf("------------------------------------------------------------------------%n");
            for (int i = 1; i <= meta.getColumnCount(); i++) {
                boolean isNullable = meta.isNullable(i) == ResultSetMetaData.columnNullable;
                System.out.printf("| %-20s | %-12s | %-16s | %-11b |%n", meta.getColumnName(i), meta.getColumnTypeName(i),
                        meta.isAutoIncrement(i), isNullable);
            }
            System.out.printf("------------------------------------------------------------------------%n");
        } catch (SQLException e) {
            throw new RuntimeException("Something went wrong!");
        }
    }
}
