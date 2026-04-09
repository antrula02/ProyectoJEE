package dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConection {

	private static final String URL = "jdbc:mysql://localhost:3306/ESCAPEROOM?useSSL=true&serverTimezone=UTC";
	private static final String USER = "root";
	private static final String PASS = "1234";

	/**
	 * metodo para obtener la conexion a base de datos
	 * @return Devuelve el objeto connection con la conexion
	 */
	public static Connection getConnection() {
		Connection con = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(URL, USER, PASS);
			System.out.println("Conexión correcta");
		} catch (Exception e) {
			System.out.println("Error en la conexión");
			e.printStackTrace();
		}

		return con;
	}
}