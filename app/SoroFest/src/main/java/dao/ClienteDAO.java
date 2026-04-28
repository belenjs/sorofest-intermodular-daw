package dao;

import database.ConexionBD;
import database.SchemaBD;
import model.Cliente;

import java.sql.*;

public class ClienteDAO {
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public ClienteDAO(){
        connection = ConexionBD.getConnection();
    }

    public boolean insertarCliente(Cliente cliente) throws SQLException {
        String query = String.format(
                "INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?, ?, ?)",
                SchemaBD.COL_ID_CLIENTE,
                SchemaBD.COL_DNI,
                SchemaBD.COL_NOMBRE,
                SchemaBD.COL_APELLIDOS,
                SchemaBD.COL_EMAIL,
                SchemaBD.COL_TELEFONO,
                SchemaBD.COL_FECHA_NACIMIENTO
        );

        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, cliente.getDni());
        preparedStatement.setString(2, cliente.getNombre());
        preparedStatement.setString(3, cliente.getApellidos());
        preparedStatement.setString(4, cliente.getEmail());
        preparedStatement.setString(5, cliente.getTelefono());
        preparedStatement.setDate(6, Date.valueOf(cliente.getFechaNacimiento()));

        return preparedStatement.execute();
    }
}
