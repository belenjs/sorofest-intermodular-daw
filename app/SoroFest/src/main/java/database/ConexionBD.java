package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    private static Connection connection;

    public static Connection getConnection(){
        try {
            if(connection == null || connection.isClosed()){
                createConnection();
            }
        } catch (SQLException e) {
            System.out.println("Error al comprobar el estado de la conexión");
            System.out.println(e.getMessage());
        }
        return connection;
    }

    private static void createConnection(){
        String user = "root";
        String pass = "";
        String database = "SoroFest";
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+ database, user, pass);
        } catch (SQLException e) {
            System.out.println("Error en la conexión con la base de datos");
            System.out.println(e.getMessage());
        }
    }
}
