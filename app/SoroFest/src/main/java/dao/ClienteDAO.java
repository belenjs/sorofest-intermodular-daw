package dao;

import database.ConexionBD;
import database.SchemaBD;
import model.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public ClienteDAO(){
        connection = ConexionBD.getConnection();
    }

    public int insertarCliente(Cliente cliente) throws SQLException {
        String query = String.format(
                "INSERT INTO %s (%s, %s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?, ?)",
                SchemaBD.TAB_CLIENTE,
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

        return preparedStatement.executeUpdate();
    }

    public int actualizarCliente(Cliente cliente) {
        String query = String.format(
                "UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ?",
                SchemaBD.TAB_CLIENTE,
                SchemaBD.COL_NOMBRE,
                SchemaBD.COL_APELLIDOS,
                SchemaBD.COL_EMAIL,
                SchemaBD.COL_TELEFONO,
                SchemaBD.COL_FECHA_NACIMIENTO,
                SchemaBD.COL_DNI
        );

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, cliente.getNombre());
            preparedStatement.setString(2, cliente.getApellidos());
            preparedStatement.setString(3, cliente.getEmail());
            preparedStatement.setString(4, cliente.getTelefono());
            preparedStatement.setDate(5, java.sql.Date.valueOf(cliente.getFechaNacimiento()));
            preparedStatement.setString(6, cliente.getDni());

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error en la query");
            System.out.println(e.getMessage());
        }
        return -1;
    }

    public Cliente obtenerClientePorDni(String dniBuscado) {
        String query = String.format(
                "SELECT * FROM %s WHERE %s = ?",
                SchemaBD.TAB_CLIENTE,
                SchemaBD.COL_DNI
        );

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, dniBuscado);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int idCliente = resultSet.getInt(SchemaBD.COL_ID_CLIENTE);
                String dni = resultSet.getString(SchemaBD.COL_DNI);
                String nombre = resultSet.getString(SchemaBD.COL_NOMBRE);
                String apellidos = resultSet.getString(SchemaBD.COL_APELLIDOS);
                String email = resultSet.getString(SchemaBD.COL_EMAIL);
                String telefono = resultSet.getString(SchemaBD.COL_TELEFONO);
                Date fechaNacimientoSQL = resultSet.getDate(SchemaBD.COL_FECHA_NACIMIENTO);
                return new Cliente(
                        idCliente,
                        dni,
                        nombre,
                        apellidos,
                        email,
                        telefono,
                        fechaNacimientoSQL.toLocalDate()
                );
            }
        } catch (SQLException e) {
            System.out.println("Error en la SQL");
            System.out.println(e.getMessage());
        }
        return null;
    }

    public List<Cliente> obtenerClientes(){
        List<Cliente> listaClientes = new ArrayList<>();
        String query = String.format("SELECT * FROM %s", SchemaBD.TAB_CLIENTE);

        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int idCliente = resultSet.getInt(SchemaBD.COL_ID_CLIENTE);
                String dni = resultSet.getString(SchemaBD.COL_DNI);
                String nombre = resultSet.getString(SchemaBD.COL_NOMBRE);
                String apellidos = resultSet.getString(SchemaBD.COL_APELLIDOS);
                String email = resultSet.getString(SchemaBD.COL_EMAIL);
                String telefono = resultSet.getString(SchemaBD.COL_TELEFONO);
                Date fechaNacimientoSQL = resultSet.getDate(SchemaBD.COL_FECHA_NACIMIENTO);
                Cliente cliente = new Cliente(
                        idCliente,
                        dni,
                        nombre,
                        apellidos,
                        email,
                        telefono,
                        fechaNacimientoSQL.toLocalDate()
                );
                listaClientes.add(cliente);
            }
        } catch (SQLException e) {
            System.out.println("Error en la SQL");
            System.out.println(e.getMessage());
        }
        return listaClientes;
    }

    public int eliminarCliente(String dniCliente){
        String query = String.format("DELETE FROM %s WHERE %s = ?", SchemaBD.TAB_CLIENTE, SchemaBD.COL_DNI);

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, dniCliente);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al eliminar cliente");
            System.out.println(e.getMessage());
        }
        return -1;
    }
}
