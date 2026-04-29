package dao;

import database.ConexionBD;
import database.SchemaBD;
import model.Cliente;
import model.Compra;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompraDAO {
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public CompraDAO() {
        connection = ConexionBD.getConnection();
    }

    public int insertarCompra(Compra compra) throws SQLException {
        String query = String.format(
                "INSERT INTO %s (%s, %s, %s, %s) VALUES (?,?,?,?)",
                SchemaBD.TAB_COMPRA,
                SchemaBD.COL_ID_CLIENTE_FK,
                SchemaBD.COL_FECHA_COMPRA,
                SchemaBD.COL_IMPORTE_TOTAL,
                SchemaBD.COL_METODO_PAGO
        );

        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, compra.getCliente().getIdCliente());
        preparedStatement.setTimestamp(2, Timestamp.valueOf(compra.getFechaCompra()));
        preparedStatement.setDouble(3, compra.getImporteTotal());
        preparedStatement.setString(4, compra.getMetodoPago());

        return preparedStatement.executeUpdate();
    }

    public List<Compra> obtenerCompras() {
        List<Compra> compras = new ArrayList<>();

        String query = String.format(
                "SELECT co.*, c.%s, c.%s, c.%s, c.%s, c.%s, c.%s, c.%s " + "FROM %s co INNER JOIN %s c ON co.%s = c.%s",
                SchemaBD.COL_ID_CLIENTE,
                SchemaBD.COL_DNI,
                SchemaBD.COL_NOMBRE,
                SchemaBD.COL_APELLIDOS,
                SchemaBD.COL_EMAIL,
                SchemaBD.COL_TELEFONO,
                SchemaBD.COL_FECHA_NACIMIENTO,
                SchemaBD.TAB_COMPRA,
                SchemaBD.TAB_CLIENTE,
                SchemaBD.COL_ID_CLIENTE_FK,
                SchemaBD.COL_ID_CLIENTE
        );
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Cliente cliente = new Cliente(
                        resultSet.getInt(SchemaBD.COL_ID_CLIENTE),
                        resultSet.getString(SchemaBD.COL_DNI),
                        resultSet.getString(SchemaBD.COL_NOMBRE),
                        resultSet.getString(SchemaBD.COL_APELLIDOS),
                        resultSet.getString(SchemaBD.COL_EMAIL),
                        resultSet.getString(SchemaBD.COL_TELEFONO),
                        resultSet.getDate(SchemaBD.COL_FECHA_NACIMIENTO).toLocalDate()
                );
                Compra compra = new Compra(
                        resultSet.getInt(SchemaBD.COL_ID_COMPRA),
                        cliente,
                        resultSet.getTimestamp(SchemaBD.COL_FECHA_COMPRA).toLocalDateTime(),
                        resultSet.getDouble(SchemaBD.COL_IMPORTE_TOTAL),
                        resultSet.getString(SchemaBD.COL_METODO_PAGO)
                );
                compras.add(compra);
            }
        } catch (SQLException e) {
            System.out.println("Error en la SQL");
            System.out.println(e.getMessage());
        }
        return compras;
    }

    public Compra obtenerCompraPorId(int idCompraBuscado) {
        String query = String.format(
                "SELECT co.*, c.%s, c.%s, c.%s, c.%s, c.%s, c.%s, c.%s " +
                        "FROM %s co INNER JOIN %s c ON co.%s = c.%s " +
                        "WHERE co.%s = ?",
                SchemaBD.COL_ID_CLIENTE,
                SchemaBD.COL_DNI,
                SchemaBD.COL_NOMBRE,
                SchemaBD.COL_APELLIDOS,
                SchemaBD.COL_EMAIL,
                SchemaBD.COL_TELEFONO,
                SchemaBD.COL_FECHA_NACIMIENTO,
                SchemaBD.TAB_COMPRA,
                SchemaBD.TAB_CLIENTE,
                SchemaBD.COL_ID_CLIENTE_FK,
                SchemaBD.COL_ID_CLIENTE,
                SchemaBD.COL_ID_COMPRA
        );
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idCompraBuscado);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Cliente cliente = new Cliente(
                        resultSet.getInt(SchemaBD.COL_ID_CLIENTE),
                        resultSet.getString(SchemaBD.COL_DNI),
                        resultSet.getString(SchemaBD.COL_NOMBRE),
                        resultSet.getString(SchemaBD.COL_APELLIDOS),
                        resultSet.getString(SchemaBD.COL_EMAIL),
                        resultSet.getString(SchemaBD.COL_TELEFONO),
                        resultSet.getDate(SchemaBD.COL_FECHA_NACIMIENTO).toLocalDate()
                );
                return new Compra(
                        resultSet.getInt(SchemaBD.COL_ID_COMPRA),
                        cliente,
                        resultSet.getTimestamp(SchemaBD.COL_FECHA_COMPRA).toLocalDateTime(),
                        resultSet.getDouble(SchemaBD.COL_IMPORTE_TOTAL),
                        resultSet.getString(SchemaBD.COL_METODO_PAGO)
                );
            }
        } catch (SQLException e) {
            System.out.println("Error en la SQL");
            System.out.println(e.getMessage());
        }
        return null;
    }

    public int actualizarCompra(Compra compra) {
        String query = String.format(
                "UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ?",
                SchemaBD.TAB_COMPRA,
                SchemaBD.COL_ID_CLIENTE_FK,
                SchemaBD.COL_FECHA_COMPRA,
                SchemaBD.COL_IMPORTE_TOTAL,
                SchemaBD.COL_METODO_PAGO,
                SchemaBD.COL_ID_COMPRA
        );
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, compra.getCliente().getIdCliente());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(compra.getFechaCompra()));
            preparedStatement.setDouble(3, compra.getImporteTotal());
            preparedStatement.setString(4, compra.getMetodoPago());
            preparedStatement.setInt(5, compra.getIdCompra());

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al actualizar compra");
            System.out.println(e.getMessage());
        }

        return -1;
    }

    public int eliminarCompra(int idCompraBuscado) {
        String query = String.format(
                "DELETE FROM %s WHERE %s = ?",
                SchemaBD.TAB_COMPRA,
                SchemaBD.COL_ID_COMPRA
        );

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idCompraBuscado);

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al eliminar compra");
            System.out.println(e.getMessage());
        }

        return -1;
    }
}

