package dao;

import database.ConexionBD;
import database.SchemaBD;
import model.Cliente;
import model.Compra;
import model.Edicion;
import model.Entrada;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EntradaDAO {
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public EntradaDAO() {
        connection = ConexionBD.getConnection();
    }

    public int insertarEntrada(Entrada entrada) throws SQLException {
        String query = String.format(
                "INSERT INTO %s (%s, %s, %s) VALUES (?,?,?)",
                SchemaBD.TAB_ENTRADA,
                SchemaBD.COL_ID_EDICION_ENTRADA_FK,
                SchemaBD.COL_ID_COMPRA_FK,
                SchemaBD.COL_CODIGO_ENTRADA
        );
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, entrada.getEdicion().getIdEdicion());
        preparedStatement.setInt(2, entrada.getCompra().getIdCompra());
        preparedStatement.setString(3, entrada.getCodigoEntrada());

        return preparedStatement.executeUpdate();
    }

    public List<Entrada> obtenerEntradas() {
        List<Entrada> entradas = new ArrayList<>();

        String query = String.format(
                "SELECT en.*, " +
                        "ed.%s, ed.%s, ed.%s, ed.%s, ed.%s, ed.%s, ed.%s, ed.%s, " +
                        "co.%s, co.%s, co.%s, co.%s, " +
                        "cl.%s, cl.%s, cl.%s, cl.%s, cl.%s, cl.%s, cl.%s " +
                        "FROM %s en " +
                        "INNER JOIN %s ed ON en.%s = ed.%s " +
                        "INNER JOIN %s co ON en.%s = co.%s " +
                        "INNER JOIN %s cl ON co.%s = cl.%s",
                SchemaBD.COL_ID_EDICION,
                SchemaBD.COL_NOMBRE_EDICION,
                SchemaBD.COL_FECHA_INICIO,
                SchemaBD.COL_FECHA_FIN,
                SchemaBD.COL_CIUDAD,
                SchemaBD.COL_RECINTO,
                SchemaBD.COL_PRECIO_ENTRADA,
                SchemaBD.COL_STOCK_ENTRADAS,

                SchemaBD.COL_ID_COMPRA,
                SchemaBD.COL_FECHA_COMPRA,
                SchemaBD.COL_IMPORTE_TOTAL,
                SchemaBD.COL_METODO_PAGO,

                SchemaBD.COL_ID_CLIENTE,
                SchemaBD.COL_DNI,
                SchemaBD.COL_NOMBRE,
                SchemaBD.COL_APELLIDOS,
                SchemaBD.COL_EMAIL,
                SchemaBD.COL_TELEFONO,
                SchemaBD.COL_FECHA_NACIMIENTO,

                SchemaBD.TAB_ENTRADA,
                SchemaBD.TAB_EDICION,
                SchemaBD.COL_ID_EDICION_ENTRADA_FK,
                SchemaBD.COL_ID_EDICION,
                SchemaBD.TAB_COMPRA,
                SchemaBD.COL_ID_COMPRA_FK,
                SchemaBD.COL_ID_COMPRA,
                SchemaBD.TAB_CLIENTE,
                SchemaBD.COL_ID_CLIENTE_FK,
                SchemaBD.COL_ID_CLIENTE
        );
        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Edicion edicion = new Edicion(
                        resultSet.getInt(SchemaBD.COL_ID_EDICION),
                        resultSet.getString(SchemaBD.COL_NOMBRE_EDICION),
                        resultSet.getDate(SchemaBD.COL_FECHA_INICIO).toLocalDate(),
                        resultSet.getDate(SchemaBD.COL_FECHA_FIN).toLocalDate(),
                        resultSet.getString(SchemaBD.COL_CIUDAD),
                        resultSet.getString(SchemaBD.COL_RECINTO),
                        resultSet.getDouble(SchemaBD.COL_PRECIO_ENTRADA),
                        resultSet.getInt(SchemaBD.COL_STOCK_ENTRADAS)
                );

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

                Entrada entrada = new Entrada(
                        resultSet.getInt(SchemaBD.COL_ID_ENTRADA),
                        edicion,
                        compra,
                        resultSet.getString(SchemaBD.COL_CODIGO_ENTRADA)
                );
                entradas.add(entrada);
            }
        } catch (SQLException e) {
            System.out.println("Error en la SQL");
            System.out.println(e.getMessage());
        }
        return entradas;
    }

    public Entrada obtenerEntradaPorId(int idEntradaBuscado) {
        String query = String.format(
                "SELECT en.*, " +
                        "ed.%s, ed.%s, ed.%s, ed.%s, ed.%s, ed.%s, ed.%s, ed.%s, " +
                        "co.%s, co.%s, co.%s, co.%s, " +
                        "cl.%s, cl.%s, cl.%s, cl.%s, cl.%s, cl.%s, cl.%s " +
                        "FROM %s en " +
                        "INNER JOIN %s ed ON en.%s = ed.%s " +
                        "INNER JOIN %s co ON en.%s = co.%s " +
                        "INNER JOIN %s cl ON co.%s = cl.%s " +
                        "WHERE en.%s = ?",
                SchemaBD.COL_ID_EDICION,
                SchemaBD.COL_NOMBRE_EDICION,
                SchemaBD.COL_FECHA_INICIO,
                SchemaBD.COL_FECHA_FIN,
                SchemaBD.COL_CIUDAD,
                SchemaBD.COL_RECINTO,
                SchemaBD.COL_PRECIO_ENTRADA,
                SchemaBD.COL_STOCK_ENTRADAS,

                SchemaBD.COL_ID_COMPRA,
                SchemaBD.COL_FECHA_COMPRA,
                SchemaBD.COL_IMPORTE_TOTAL,
                SchemaBD.COL_METODO_PAGO,

                SchemaBD.COL_ID_CLIENTE,
                SchemaBD.COL_DNI,
                SchemaBD.COL_NOMBRE,
                SchemaBD.COL_APELLIDOS,
                SchemaBD.COL_EMAIL,
                SchemaBD.COL_TELEFONO,
                SchemaBD.COL_FECHA_NACIMIENTO,

                SchemaBD.TAB_ENTRADA,
                SchemaBD.TAB_EDICION,
                SchemaBD.COL_ID_EDICION_ENTRADA_FK,
                SchemaBD.COL_ID_EDICION,
                SchemaBD.TAB_COMPRA,
                SchemaBD.COL_ID_COMPRA_FK,
                SchemaBD.COL_ID_COMPRA,
                SchemaBD.TAB_CLIENTE,
                SchemaBD.COL_ID_CLIENTE_FK,
                SchemaBD.COL_ID_CLIENTE,
                SchemaBD.COL_ID_ENTRADA
        );
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idEntradaBuscado);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Edicion edicion = new Edicion(
                        resultSet.getInt(SchemaBD.COL_ID_EDICION),
                        resultSet.getString(SchemaBD.COL_NOMBRE_EDICION),
                        resultSet.getDate(SchemaBD.COL_FECHA_INICIO).toLocalDate(),
                        resultSet.getDate(SchemaBD.COL_FECHA_FIN).toLocalDate(),
                        resultSet.getString(SchemaBD.COL_CIUDAD),
                        resultSet.getString(SchemaBD.COL_RECINTO),
                        resultSet.getDouble(SchemaBD.COL_PRECIO_ENTRADA),
                        resultSet.getInt(SchemaBD.COL_STOCK_ENTRADAS)
                );
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
                return new Entrada(
                        resultSet.getInt(SchemaBD.COL_ID_ENTRADA),
                        edicion,
                        compra,
                        resultSet.getString(SchemaBD.COL_CODIGO_ENTRADA)
                );
            }
        } catch (SQLException e) {
            System.out.println("Error en la SQL");
            System.out.println(e.getMessage());
        }
        return null;
    }

    public int eliminarEntrada(int idEntradaBuscado) {
        String query = String.format(
                "DELETE FROM %s WHERE %s = ?",
                SchemaBD.TAB_ENTRADA,
                SchemaBD.COL_ID_ENTRADA
        );

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idEntradaBuscado);

            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al eliminar entrada");
            System.out.println(e.getMessage());
        }
        return -1;
    }

    public boolean existeEntradaPorCompra(int idCompra) {
        String query = String.format(
                "SELECT * FROM %s WHERE %s = ?",
                SchemaBD.TAB_ENTRADA,
                SchemaBD.COL_ID_COMPRA_FK
        );

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idCompra);
            resultSet = preparedStatement.executeQuery();

            return resultSet.next();

        } catch (SQLException e) {
            System.out.println("Error al comprobar entradas de la compra");
            System.out.println(e.getMessage());
        }
        return false;
    }

    public int obtenerSiguienteNumeroEntrada() {
        String query = String.format(
                "SELECT MAX(%s) AS ultimo_id FROM %s",
                SchemaBD.COL_ID_ENTRADA,
                SchemaBD.TAB_ENTRADA
        );

        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int ultimoId = resultSet.getInt("ultimo_id");
                return ultimoId + 1;
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener el siguiente número de entrada");
            System.out.println(e.getMessage());
        }
        return 1;
    }
}
