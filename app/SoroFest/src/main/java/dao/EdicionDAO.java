package dao;

import database.ConexionBD;
import database.SchemaBD;
import model.Edicion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EdicionDAO {
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public EdicionDAO() {
        connection = ConexionBD.getConnection();
    }

    public Edicion obtenerEdicion() {
        String query = String.format(
                "SELECT * FROM %s LIMIT 1",
                SchemaBD.TAB_EDICION
        );

        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new Edicion(
                        resultSet.getInt(SchemaBD.COL_ID_EDICION),
                        resultSet.getString(SchemaBD.COL_NOMBRE_EDICION),
                        resultSet.getDate(SchemaBD.COL_FECHA_INICIO).toLocalDate(),
                        resultSet.getDate(SchemaBD.COL_FECHA_FIN).toLocalDate(),
                        resultSet.getString(SchemaBD.COL_CIUDAD),
                        resultSet.getString(SchemaBD.COL_RECINTO),
                        resultSet.getDouble(SchemaBD.COL_PRECIO_ENTRADA),
                        resultSet.getInt(SchemaBD.COL_STOCK_ENTRADAS)
                );
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener la edición");
            System.out.println(e.getMessage());
        }
        return null;
    }

    public int actualizarEdicion(Edicion edicion) {
        String query = String.format(
                "UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ?",
                SchemaBD.TAB_EDICION,
                SchemaBD.COL_NOMBRE_EDICION,
                SchemaBD.COL_FECHA_INICIO,
                SchemaBD.COL_FECHA_FIN,
                SchemaBD.COL_CIUDAD,
                SchemaBD.COL_RECINTO,
                SchemaBD.COL_PRECIO_ENTRADA,
                SchemaBD.COL_STOCK_ENTRADAS,
                SchemaBD.COL_ID_EDICION
        );

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, edicion.getNombreEdicion());
            preparedStatement.setDate(2, java.sql.Date.valueOf(edicion.getFechaInicio()));
            preparedStatement.setDate(3, java.sql.Date.valueOf(edicion.getFechaFin()));
            preparedStatement.setString(4, edicion.getCiudad());
            preparedStatement.setString(5, edicion.getRecinto());
            preparedStatement.setDouble(6, edicion.getPrecioEntrada());
            preparedStatement.setInt(7, edicion.getStockEntradas());
            preparedStatement.setInt(8, edicion.getIdEdicion());

            return preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al actualizar la edición");
            System.out.println(e.getMessage());
        }
        return -1;
    }
}
