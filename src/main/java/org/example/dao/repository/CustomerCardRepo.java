package org.example.dao.repository;

import org.example.config.PostgresConfig;
import org.example.dao.model.Card;
import org.example.dao.model.Customer;
import org.example.enums.CardStatus;

import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class CustomerCardRepo {
    public List<Customer> getCustomerWithCard() throws SQLException {
        String query = "select bank_customer_schema.customer. *,customer.id as cust_id,customer.created_at as cust_created_at,\n" +
                "       customer.updated_at as cust_updated_at,\n" +
                "\n" +
                "       bank_customer_schema.card. * ,card.id as c_id,card.created_at as c_created_at,\n" +
                "        card.updated_at as c_updated_at\n" +
                "from bank_customer_schema.customer inner join bank_customer_schema.card\n" +
                "    on customer.id=card.customer_id;";
        List<Customer> customerList = new ArrayList<>();
        Connection connection = PostgresConfig.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        while (resultSet.next()) {
            customerList.add(buildCustomer(resultSet));
        }
        return prepareCustomerList(customerList);
    }


    private Customer buildCustomer(ResultSet resultSet) throws SQLException {

        Long id = resultSet.getLong("cust_id");
        String name = resultSet.getString("name");
        String surName = resultSet.getString("surname");
        LocalDate birthDate = resultSet.getDate("birth_date").toLocalDate();
        String mail = resultSet.getString("mail");
        String pin = resultSet.getString("pin");
        boolean isActive = resultSet.getBoolean("is_active");
        Timestamp createdAt = resultSet.getTimestamp("cust_created_at");
        Timestamp updatedAt = resultSet.getTimestamp("cust_updated_at");
        List<Card> cards=new ArrayList<>();
        cards.add(buildCard(resultSet));
        return new Customer(id, name, surName, birthDate, mail, pin, isActive, createdAt, updatedAt,cards);
    }

    public static Card buildCard(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong("c_id");
        Long customerId = resultSet.getLong("customer_id");
        String cardNumber = resultSet.getString("card_number");
        String holderName = resultSet.getString("holder_name");
        BigDecimal amount=resultSet.getBigDecimal("amount");
        String cvv = resultSet.getString("cvv");
        LocalDate expireDate = resultSet.getDate("expire_date").toLocalDate();
        String status=resultSet.getString("status");
        Timestamp createdAt = resultSet.getTimestamp("c_created_at");
        Timestamp updatedAt = resultSet.getTimestamp("c_updated_at");
        return new Card(id,customerId, cardNumber,holderName,amount,cvv,expireDate,CardStatus.valueOf(status),createdAt,updatedAt);
    }
    private List<Customer> prepareCustomerList(List<Customer> customerList) {
        return customerList.stream()
                .collect(Collectors.toMap(
                        customer -> customer,
                        Customer::getCards,
                        (cards1, cards2) -> {
                            cards1.addAll(cards2);
                            return cards1;
                        }))
                .entrySet()
                .stream()
                .peek(entry -> entry.getKey().setCards(entry.getValue()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
