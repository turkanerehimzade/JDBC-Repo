package org.example.dao.repository;

import org.example.config.PostgresConfig;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Scanner;

public class CardRepo {
    public static void blockedCard() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter card's id you want to blocked");
        int id = scanner.nextInt();
        Connection connection = PostgresConfig.getConnection();
        String query = "select public.blocked_card(?)";
        PreparedStatement callableStatement = connection.prepareCall(query);
        callableStatement.setInt(1, id);
        callableStatement.execute();
        ResultSet resultSet = callableStatement.getResultSet();
        while (resultSet.next()) {
            System.out.println(resultSet);
            System.out.println(resultSet.getString(1));
        }
    }

    public static void unblockedCard() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter card's id you want to unblocked");
        int id = scanner.nextInt();
        Connection connection = PostgresConfig.getConnection();
        String query = "select public.unblocked_card(?)";
        PreparedStatement callableStatement = connection.prepareCall(query);
        callableStatement.setInt(1, id);
        callableStatement.execute();
        ResultSet resultSet = callableStatement.getResultSet();
        while (resultSet.next()) {
            System.out.println(resultSet);
            System.out.println(resultSet.getString(1));
        }
    }

    public static void addNewCard() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter customer's id you want to create a card:");
        Long customerId = scanner.nextLong();
        System.out.println("Enter card's card number:");
        String cardNumber = scanner.next();
        System.out.println("Enter card's holder name");
        String holderName = scanner.next();
        scanner.nextLine();
        System.out.println("Enter card's amount:");
        int amount = scanner.nextInt();
        System.out.println("Enter card's cvv:");
        String cvv = scanner.next();
        System.out.println("Enter card's expiredate:");
        String expireDate = scanner.next();
        scanner.nextLine();
        System.out.println("Enter created at:");
        String createdAt = scanner.nextLine();
        System.out.println("Enter updated at:");
        String updatedAt = scanner.nextLine();

        String query = "INSERT INTO bank_customer_schema.card(customer_id, card_number, holder_name, amount, cvv, expire_date, created_at,updated_at)\n" +
                "VALUES (?,?,?,?,?,? :: DATE,? ,? );";
        Timestamp createdAtTimestamp = Timestamp.valueOf(createdAt);
        Timestamp updatedAtTimestamp = Timestamp.valueOf(updatedAt);

        Connection connection = PostgresConfig.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, customerId);
            preparedStatement.setString(2, cardNumber);
            preparedStatement.setString(3, holderName);
            preparedStatement.setInt(4, amount);
            preparedStatement.setString(5, cvv);
            preparedStatement.setString(6, expireDate);
            preparedStatement.setTimestamp(7, createdAtTimestamp);//tip erroru geldi
            preparedStatement.setTimestamp(8, updatedAtTimestamp);

            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("New card successfully added!");
            }else{
                System.out.println("Failed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
