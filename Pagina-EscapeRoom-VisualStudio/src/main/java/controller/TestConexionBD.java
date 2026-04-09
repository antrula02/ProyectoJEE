package controller;

import java.sql.Connection;
import conexion.ConexionBD;

public class TestConexionBD {
    public static void main(String[] args) {
        try {
            Connection con = ConexionBD.getConnection();

            if (con != null) {
                System.out.println("OK conexión");
                con.close();
            } else {
                System.out.println("Conexión null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}