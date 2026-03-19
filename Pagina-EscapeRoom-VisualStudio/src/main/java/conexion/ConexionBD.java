package conexion;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionBD {
    

    private static final String URL = "jdbc:mysql://localhost:3306/escaperoom";
    private static final String USER = "root";
    private static final String PASS = "1234";

    public static Connection getConnection() {
        Connection con = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("Conexión correcta a la base de datos");
            System.out.println(con.getCatalog());
        } catch (Exception e) {
            System.out.println("Error al conectar con la base de datos");
            e.printStackTrace();
        }

        return con;
    }
}