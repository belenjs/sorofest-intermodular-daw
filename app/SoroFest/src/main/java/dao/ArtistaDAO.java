package dao;

import database.ConexionBD;
import database.SchemaBD;
import model.Artista;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ArtistaDAO {
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public ArtistaDAO() {
        connection = ConexionBD.getConnection();
    }

    public int insertarArtista(Artista artista) throws SQLException {
        String query = String.format(
                "INSERT INTO %s (%s, %s, %s, %s, %s) VALUES (?,?,?,?,?)",
                SchemaBD.TAB_ARTISTA,
                SchemaBD.COL_NOMBRE_ARTISTA,
                SchemaBD.COL_TIPO_ARTISTA,
                SchemaBD.COL_GENERO_MUSICAL,
                SchemaBD.COL_ES_CABEZA_CARTEL,
                SchemaBD.COL_DESCRIPCION
        );
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, artista.getNombreArtista());
        preparedStatement.setString(2, artista.getTipoArtista());
        preparedStatement.setString(3, artista.getGeneroMusical());
        preparedStatement.setBoolean(4, artista.isEsCabezaCartel());
        preparedStatement.setString(5, artista.getDescripcion());

        return preparedStatement.executeUpdate();
    }

    public List<Artista> obtenerArtistas() {
        List<Artista> artistas = new ArrayList<>();
        String query = String.format("SELECT * FROM %s", SchemaBD.TAB_ARTISTA);

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
                artistas.add(artista);
            }
        } catch (SQLException e) {
            System.out.println("Error en la SQL");
            System.out.println(e.getMessage());
        }
        return artistas;
    }

    public Artista obtenerArtistaPorId(int idArtistaBuscado) {
        String query = String.format("SELECT * FROM %s WHERE %s = ?", SchemaBD.TAB_ARTISTA, SchemaBD.COL_ID_ARTISTA);

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idArtistaBuscado);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return new Artista(
                        resultSet.getInt(SchemaBD.COL_ID_ARTISTA),
                        resultSet.getString(SchemaBD.COL_NOMBRE_ARTISTA),
                        resultSet.getString(SchemaBD.COL_TIPO_ARTISTA),
                        resultSet.getString(SchemaBD.COL_GENERO_MUSICAL),
                        resultSet.getBoolean(SchemaBD.COL_ES_CABEZA_CARTEL),
                        resultSet.getString(SchemaBD.COL_DESCRIPCION)
                );
            }
        } catch (SQLException e) {
            System.out.println("Error en la SQL");
            System.out.println(e.getMessage());
        }
        return null;
    }

    public int actualizarArtista(Artista artista) {
        String query = String.format(
                "UPDATE %s SET %s = ?, %s = ?, %s = ?, %s = ?, %s = ? WHERE %s = ?",
                SchemaBD.TAB_ARTISTA,
                SchemaBD.COL_NOMBRE_ARTISTA,
                SchemaBD.COL_TIPO_ARTISTA,
                SchemaBD.COL_GENERO_MUSICAL,
                SchemaBD.COL_ES_CABEZA_CARTEL,
                SchemaBD.COL_DESCRIPCION,
                SchemaBD.COL_ID_ARTISTA
        );

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, artista.getNombreArtista());
            preparedStatement.setString(2, artista.getTipoArtista());
            preparedStatement.setString(3, artista.getGeneroMusical());
            preparedStatement.setBoolean(4, artista.isEsCabezaCartel());
            preparedStatement.setString(5, artista.getDescripcion());
            preparedStatement.setInt(6, artista.getIdArtista());
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al actualizar artista");
            System.out.println(e.getMessage());
        }
        return -1;
    }

    public int eliminarArtista(int idArtistaBuscado) {
        String query = String.format(
                "DELETE FROM %s WHERE %s = ?",
                SchemaBD.TAB_ARTISTA,
                SchemaBD.COL_ID_ARTISTA
        );

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, idArtistaBuscado);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al eliminar artista");
            System.out.println(e.getMessage());
        }
        return -1;
    }
}
