package dao;

import database.ConexionBD;
import database.SchemaBD;
import model.Artista;
import model.Concierto;
import model.Edicion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConciertoDAO {
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public ConciertoDAO() {
        connection = ConexionBD.getConnection();
    }

    public int insertarConcierto(Concierto concierto) throws SQLException {
        String query = String.format(
                "INSERT INTO %s (%s, %s, %s, %s, %s) VALUES (?,?,?,?,?)",
                SchemaBD.TAB_CONCIERTO,
                SchemaBD.COL_ID_EDICION_FK,
                SchemaBD.COL_ID_ARTISTA_FK,
                SchemaBD.COL_FECHA,
                SchemaBD.COL_HORA_INICIO,
                SchemaBD.COL_HORA_FIN
        );
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setInt(1, concierto.getEdicion().getIdEdicion());
        preparedStatement.setInt(2, concierto.getArtista().getIdArtista());
        preparedStatement.setDate(3, Date.valueOf(concierto.getFecha()));
        preparedStatement.setTime(4, Time.valueOf(concierto.getHoraInicio()));
        preparedStatement.setTime(5, Time.valueOf(concierto.getHoraFin()));

        return preparedStatement.executeUpdate();
    }

    public List<Concierto> obtenerConciertos() {
        List<Concierto> conciertos = new ArrayList<>();
        String query = String.format(
                "SELECT co.*, " +
                        "ar.%s, ar.%s, ar.%s, ar.%s, ar.%s, ar.%s, " +
                        "ed.%s, ed.%s, ed.%s, ed.%s, ed.%s, ed.%s, ed.%s, ed.%s " +
                        "FROM %s co " +
                        "INNER JOIN %s ar ON co.%s = ar.%s " +
                        "INNER JOIN %s ed ON co.%s = ed.%s",
                SchemaBD.COL_ID_ARTISTA,
                SchemaBD.COL_NOMBRE_ARTISTA,
                SchemaBD.COL_TIPO_ARTISTA,
                SchemaBD.COL_GENERO_MUSICAL,
                SchemaBD.COL_ES_CABEZA_CARTEL,
                SchemaBD.COL_DESCRIPCION,

                SchemaBD.COL_ID_EDICION,
                SchemaBD.COL_NOMBRE_EDICION,
                SchemaBD.COL_FECHA_INICIO,
                SchemaBD.COL_FECHA_FIN,
                SchemaBD.COL_CIUDAD,
                SchemaBD.COL_RECINTO,
                SchemaBD.COL_PRECIO_ENTRADA,
                SchemaBD.COL_STOCK_ENTRADAS,

                SchemaBD.TAB_CONCIERTO,
                SchemaBD.TAB_ARTISTA,
                SchemaBD.COL_ID_ARTISTA_FK,
                SchemaBD.COL_ID_ARTISTA,
                SchemaBD.TAB_EDICION,
                SchemaBD.COL_ID_EDICION_FK,
                SchemaBD.COL_ID_EDICION
        );

        try {
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Artista artista = new Artista(
                        resultSet.getInt(SchemaBD.COL_ID_ARTISTA),
                        resultSet.getString(SchemaBD.COL_NOMBRE_ARTISTA),
                        resultSet.getString(SchemaBD.COL_TIPO_ARTISTA),
                        resultSet.getString(SchemaBD.COL_GENERO_MUSICAL),
                        resultSet.getBoolean(SchemaBD.COL_ES_CABEZA_CARTEL),
                        resultSet.getString(SchemaBD.COL_DESCRIPCION)
                );

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

                Concierto concierto = new Concierto(
                        resultSet.getInt(SchemaBD.COL_ID_CONCIERTO),
                        edicion,
                        artista,
                        resultSet.getDate(SchemaBD.COL_FECHA).toLocalDate(),
                        resultSet.getTime(SchemaBD.COL_HORA_INICIO).toLocalTime(),
                        resultSet.getTime(SchemaBD.COL_HORA_FIN).toLocalTime()
                );
                conciertos.add(concierto);
            }
        } catch (SQLException e) {
            System.out.println("Error en la SQL");
            System.out.println(e.getMessage());
        }
        return conciertos;
    }

    public Concierto obtenerConciertoPorId(int idConciertoBuscado) {
        String query = String.format(
                "SELECT co.*, " +
                        "ar.%s, ar.%s, ar.%s, ar.%s, ar.%s, ar.%s, " +
                        "ed.%s, ed.%s, ed.%s, ed.%s, ed.%s, ed.%s, ed.%s, ed.%s " +
                        "FROM %s co " +
                        "INNER JOIN %s ar ON co.%s = ar.%s " +
                        "INNER JOIN %s ed ON co.%s = ed.%s " +
                        "WHERE co.%s = ?",
                SchemaBD.COL_ID_ARTISTA,
                SchemaBD.COL_NOMBRE_ARTISTA,
                SchemaBD.COL_TIPO_ARTISTA,
                SchemaBD.COL_GENERO_MUSICAL,
                SchemaBD.COL_ES_CABEZA_CARTEL,
                SchemaBD.COL_DESCRIPCION,

                SchemaBD.COL_ID_EDICION,
                SchemaBD.COL_NOMBRE_EDICION,
                SchemaBD.COL_FECHA_INICIO,
                SchemaBD.COL_FECHA_FIN,
                SchemaBD.COL_CIUDAD,
                SchemaBD.COL_RECINTO,
                SchemaBD.COL_PRECIO_ENTRADA,
                SchemaBD.COL_STOCK_ENTRADAS,

                SchemaBD.TAB_CONCIERTO,
                SchemaBD.TAB_ARTISTA,
                SchemaBD.COL_ID_ARTISTA_FK,
                SchemaBD.COL_ID_ARTISTA,
                SchemaBD.TAB_EDICION,
                SchemaBD.COL_ID_EDICION_FK,
                SchemaBD.COL_ID_EDICION,
                SchemaBD.COL_ID_CONCIERTO
        );

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idConciertoBuscado);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Artista artista = new Artista(
                        resultSet.getInt(SchemaBD.COL_ID_ARTISTA),
                        resultSet.getString(SchemaBD.COL_NOMBRE_ARTISTA),
                        resultSet.getString(SchemaBD.COL_TIPO_ARTISTA),
                        resultSet.getString(SchemaBD.COL_GENERO_MUSICAL),
                        resultSet.getBoolean(SchemaBD.COL_ES_CABEZA_CARTEL),
                        resultSet.getString(SchemaBD.COL_DESCRIPCION)
                );

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

                return new Concierto(
                        resultSet.getInt(SchemaBD.COL_ID_CONCIERTO),
                        edicion,
                        artista,
                        resultSet.getDate(SchemaBD.COL_FECHA).toLocalDate(),
                        resultSet.getTime(SchemaBD.COL_HORA_INICIO).toLocalTime(),
                        resultSet.getTime(SchemaBD.COL_HORA_FIN).toLocalTime()
                );
            }
        } catch (SQLException e) {
            System.out.println("Error en la SQL");
            System.out.println(e.getMessage());
        }
        return null;
    }

    public int actualizarConcierto(Concierto concierto) {
        String query = String.format(
                "UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ?",
                SchemaBD.TAB_CONCIERTO,
                SchemaBD.COL_ID_EDICION_FK,
                SchemaBD.COL_ID_ARTISTA_FK,
                SchemaBD.COL_FECHA,
                SchemaBD.COL_HORA_INICIO,
                SchemaBD.COL_HORA_FIN,
                SchemaBD.COL_ID_CONCIERTO
        );

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, concierto.getEdicion().getIdEdicion());
            preparedStatement.setInt(2, concierto.getArtista().getIdArtista());
            preparedStatement.setDate(3, Date.valueOf(concierto.getFecha()));
            preparedStatement.setTime(4, Time.valueOf(concierto.getHoraInicio()));
            preparedStatement.setTime(5, Time.valueOf(concierto.getHoraFin()));
            preparedStatement.setInt(6, concierto.getIdConcierto());

            return preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al actualizar concierto");
            System.out.println(e.getMessage());
        }
        return -1;
    }

    public int eliminarConcierto(int idConciertoBuscado) {
        String query = String.format(
                "DELETE FROM %s WHERE %s = ?",
                SchemaBD.TAB_CONCIERTO,
                SchemaBD.COL_ID_CONCIERTO
        );

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idConciertoBuscado);

            return preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al eliminar concierto");
            System.out.println(e.getMessage());
        }
        return -1;
    }

    public boolean existeConciertoPorArtista(int idArtista) {
        String query = String.format(
                "SELECT 1 FROM %s WHERE %s = ?",
                SchemaBD.TAB_CONCIERTO,
                SchemaBD.COL_ID_ARTISTA_FK
        );

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idArtista);
            resultSet = preparedStatement.executeQuery();

            return resultSet.next();
        } catch (SQLException e) {
            System.out.println("Error al comprobar conciertos de la artista");
            System.out.println(e.getMessage());
        }
        return false;
    }

}
