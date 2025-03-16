package org.example;

import org.example.dao.model.Menu;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException, InterruptedException {
        Menu.printMenu();
    }
}