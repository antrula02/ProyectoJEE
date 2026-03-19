package controller;

import java.sql.Connection;
import java.sql.DriverManager;

public class TestConexion {

    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/escaperoom";
        String user = "root";
        String pass = "1234";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection con = DriverManager.getConnection(url, user, pass);

            System.out.println("Conexión exitosa 🔥");

            con.close();

        } catch (Exception e) {
            System.out.println("Error en la conexión ❌");
            e.printStackTrace();
        }
    }
}