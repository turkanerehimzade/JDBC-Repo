package org.example.dao.repository;

import lombok.ToString;
import org.example.config.PostgresConfig;
import org.example.dao.model.Card;
import org.example.dao.model.Customer;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class CustomerRepo {
    public static void addCustomer() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter customer's name:");
        String name = scanner.next();
        System.out.println("Enter customer's surname:");
        String surName = scanner.next();
        System.out.println("Enter customer's birthDate");
        String birthDate = scanner.next();
        System.out.println("Enter customer's mail:");
        String mail = scanner.next();
        System.out.println("Enter customer's pin:");
        String pin = scanner.next();
        scanner.nextLine();
        System.out.println("Enter created at:");
        String createdAt= scanner.nextLine();
        System.out.println("Enter updated at:");
        String updatedAt=scanner.nextLine();


        String query = "INSERT INTO bank_customer_schema.customer(NAME, SURNAME, BIRTH_DATE, MAIL, PIN, CREATED_AT,UPDATED_AT)\n" +
                "VALUES (?,?,? :: DATE,?,?,?,? );";
        Timestamp createdAtTimestamp = Timestamp.valueOf(createdAt);
        Timestamp updatedAtTimestamp = Timestamp.valueOf(updatedAt);

        Connection connection = PostgresConfig.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, surName);
        preparedStatement.setString(3, birthDate);
        preparedStatement.setString(4, mail);
        preparedStatement.setString(5, pin);
        preparedStatement.setTimestamp(6,createdAtTimestamp );
        preparedStatement.setTimestamp(7, updatedAtTimestamp);

        int rowsInserted = preparedStatement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("New customer successfully added!");
        }
    }


    public static void deleteCustomer() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter customer's id you want to delete:");
        int id = scanner.nextInt();
        String query = "delete from bank_customer_schema.customer where id=? ";
        Connection connection = PostgresConfig.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, id);
        int rowsInserted = preparedStatement.executeUpdate();
        if (rowsInserted > 0) {
            System.out.println("The customer with this id deleted!");
        } else {
            System.out.println("This id is not owned by the customer");
        }
    }

    public static void cardOfCustomer() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the id of the customer whose carts you want to see:");
        int id = scanner.nextInt();
        String query ="select  *, card.id as c_id,card.created_at as c_created_at, card.updated_at as c_updated_at from bank_customer_schema.card where customer_id=?;";
        Connection connection = PostgresConfig.getConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Card> cards = new ArrayList<>();
            while (resultSet.next()) {
                Card card = CustomerCardRepo.buildCard(resultSet);
                cards.add(card);
            }
            for (Card card : cards) {
                System.out.println(card);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public static void CustomerListWithCards() throws SQLException {
        CustomerCardRepo customerCardRepo=new CustomerCardRepo();
        System.out.println(customerCardRepo.getCustomerWithCard());
    }
}
