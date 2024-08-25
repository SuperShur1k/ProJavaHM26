package org.example.lesson26.hw;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ShowOrders {
    public static void main(String[] args) {
        getOrders(2002, 10_000);
    }
    public static void getOrders(int cnum, int amt) {
        String url = "jdbc:sqlite:shop.db"; // sqlite драйвер, локальный файл shop.db в корне
        // загрузить поддержку jdbc
        // создать соединение с базой данных по url
        try (
            Connection connection = DriverManager.getConnection(url);

            // Напишите метод который принимает идентификатор
            // покупателя (cnum) и стоимость заказа (amt) и распечатывает все
            // заказы этого покупателя стоимостью большей чем amt. Для заказа
            // распечатывайте его идентификатор, стоимость, идентификаторы
            // покупателя и продавца и дату заказа.
            // скомпилированный запрос
            PreparedStatement pstmt = connection.prepareStatement(
                "select * from orders where cnum = ? and amt > ? " // and
            );
        ) {
            pstmt.setInt(1, cnum);
            pstmt.setInt(2, amt);
            try (
                ResultSet rs = pstmt.executeQuery();
                )
            {
                while (rs.next()) {
                    System.out.printf(
                        "|%5d|%5d|%5d|%5d|%10s|\n",
                        rs.getInt("onum"),
                        rs.getInt("amt"),
                        rs.getInt("cnum"),
                        rs.getInt("snum"),
                        rs.getString("odate")
                    );
                }
            }

        }
        catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
        }
    }
}
