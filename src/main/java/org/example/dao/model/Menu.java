package org.example.dao.model;

import org.example.dao.repository.CardRepo;
import org.example.dao.repository.CustomerRepo;
import org.example.dao.repository.TransactionsRepo;

import java.sql.SQLException;
import java.util.Scanner;

public class Menu {
    public static String menu="1.Customers list and their cards\n"+
            "2.Add customer\n"+
            "3.Delete customer\n"+
            "4.Blocking the customer's card\n"+
            "5.Unblocking the customer's card\n"+
            "6.Create new card for customer\n"+
            "7.See money transaction\n"+
            "8.See cards by person\n"+
            "9.Money transfer\n"+
            "10.Log out.";

    public static void printMenu() throws SQLException, InterruptedException {
        while(true){
            Thread.sleep(3000);
            Scanner scanner= new Scanner(System.in);
            System.out.println("---MENU---");
            System.out.println(menu);
            System.out.println("-------------------");
            System.out.println("Please,enter option...");
            int option= scanner.nextInt();
            switch (option){
                case 1->CustomerRepo.CustomerListWithCards();
                case 2-> CustomerRepo.addCustomer();
                case 3-> CustomerRepo.deleteCustomer();
                case 4-> CardRepo.blockedCard();
                case 5->CardRepo.unblockedCard();
                case 6->CardRepo.addNewCard();
                case 7-> TransactionsRepo.getTransactionsList();
                case 8->CustomerRepo.cardOfCustomer();
                case 9->TransactionsRepo.moneyTransfer();
                case 10-> System.exit(0);
            }
        }
    }
}
