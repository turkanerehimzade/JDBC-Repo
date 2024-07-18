package org.example.dao.repository;

import org.example.config.PostgresConfig;
import org.example.dao.model.Transactions;
import org.example.enums.TransferStatus;

import java.sql.Timestamp;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public  class TransactionsRepo<Timestamp> {

    public static void getTransactionsList() throws SQLException, SQLException {

        String query = "select * from transactions";
        List<Transactions> transactionsList = new ArrayList<>();
        Connection connection = PostgresConfig.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            Long id = resultSet.getLong("id");
            String sender = resultSet.getString("sender");
            String receiver = resultSet.getString("receiver");
            BigDecimal amount=resultSet.getBigDecimal("amount");
            String ccy = resultSet.getString("ccy");
            String status = resultSet.getString("status");
           java.sql.Timestamp createdAt = resultSet.getTimestamp("created_at");
           java.sql.Timestamp updatedAt =  resultSet.getTimestamp("updated_at");
           Transactions transactions=new Transactions(id,sender,receiver,amount,ccy, TransferStatus.valueOf(status),createdAt,updatedAt);
           transactionsList.add(transactions);
        }
        System.out.println(transactionsList);
    }

    public static void moneyTransfer() throws SQLException {
        Scanner scanner=new Scanner(System.in);
        String query = null;
        option(query);
        System.out.println("Enter sender:");
        int sender=scanner.nextInt();
        System.out.println("Enter receiver:");
        int receiver=scanner.nextInt();
        System.out.println("Enter amount:");
        int amount= scanner.nextInt();
        Connection connection = PostgresConfig.getConnection();
        PreparedStatement callableStatement = connection.prepareCall(query);
        callableStatement.setInt(1, sender);
        callableStatement.setInt(2, receiver);
        callableStatement.setInt(3, amount);
        callableStatement.execute();
        ResultSet resultSet = callableStatement.getResultSet();
        while (resultSet.next()) {
            System.out.println(resultSet);
            System.out.println(resultSet.getString(1));
        }
    }
    private static  void option(String query){
        Scanner scanner=new Scanner(System.in);

        System.out.println("Transfer to card(1) or transfer to phone number(2)?");
        int option=scanner.nextInt();
        if(option==1){
            query = "select public.transfer(?,?,?)";
        }
        else if(option==2){
            query = "select public.transfer_with_phone(?,? :: varchar,?)";
        }
        else{
            System.out.println("Coose from the option!");
          option( query);
        }
    }
}
