package controller;

import dao.ArtistaDAO;
import dao.ConciertoDAO;
import model.Artista;
import view.ArtistaView;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class ArtistaController {
    private Scanner scanner;
    private ArtistaView artistaView;
    private ArtistaDAO artistaDAO;
    private ConciertoDAO conciertoDAO;

    public ArtistaController(){

    }

    public ArtistaController(Scanner scanner){
        this.scanner = scanner;
        this.artistaView = new ArtistaView();
        this.artistaDAO = new ArtistaDAO();
        this.conciertoDAO = new ConciertoDAO();
    }

    public void iniciarMenuArtista(){
        int opcion = -1;

        do {
            artistaView.mostrarMenuArtista();

            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1 -> darAltaArtista();
                    case 2 -> listarArtistas();
                    case 3 -> buscarArtista();
                    case 4 -> modificarArtista();
                    case 5 -> eliminarArtista();
                    case 0 -> System.out.println("0. Volviendo al menú principal...");
                    default -> System.out.println("Opción no válida.");
                }
            } else {
                System.out.println("Debes introducir un número.");
                scanner.nextLine();
                opcion = -1;
            }

        } while (opcion != 0);
    }

    public void darAltaArtista(){
        String nombreArtista = leerTexto("Nombre artístico: ");
        String tipoArtista = leerTexto("Tipo de artista: ");
        String generoMusical = leerTexto("Género musical: ");
        boolean esCabezaCartel = leerBoolean("¿Es cabeza de cartel? (true/false): ");
        String descripcion = leerTexto("Descripción: ");

        Artista artista = new Artista(
                0,
                nombreArtista,
                tipoArtista,
                generoMusical,
                esCabezaCartel,
                descripcion
        );

        try {
            int filasInsertadas = artistaDAO.insertarArtista(artista);
            if (filasInsertadas > 0) {
                System.out.println("Artista dada de alta correctamente.");
            } else {
                System.out.println("No se ha podido dar de alta la artista.");
            }
        } catch (SQLException e) {
            System.out.println("Error al insertar artista en la base de datos.");
            System.out.println(e.getMessage());
        }
    }

    public void listarArtistas(){
        System.out.println("LISTADO DE ARTISTAS");
        List<Artista> artistas = artistaDAO.obtenerArtistas();
        if (artistas.isEmpty()) {
            System.out.println("No hay artistas registradas.");
        } else {
            for (Artista artista : artistas) {
                System.out.println(artista);
            }
        }
    }

    public void buscarArtista(){
        String nombreArtista = leerTexto("Introduce el nombre artístico a buscar: ");
        Artista artista = artistaDAO.obtenerArtistaPorNombre(nombreArtista);

        if (artista != null) {
            System.out.println(artista);
        } else {
            System.out.println("No se ha encontrado ninguna artista con ese nombre artístico.");
        }
    }

    public void modificarArtista() {
        String nombreArtista = leerTexto("Introduce el nombre artístico de la artista a modificar: ");
        Artista artista = artistaDAO.obtenerArtistaPorNombre(nombreArtista);

        if (artista != null) {
            artista.setNombreArtista(leerTexto("Nuevo nombre artístico: "));
            artista.setTipoArtista(leerTexto("Nuevo tipo de artista: "));
            artista.setGeneroMusical(leerTexto("Nuevo género musical: "));
            artista.setEsCabezaCartel(leerBoolean("¿Es cabeza de cartel? (true/false): "));
            artista.setDescripcion(leerTexto("Nueva descripción: "));

            int filasActualizadas = artistaDAO.actualizarArtista(artista);
            if (filasActualizadas > 0) {
                System.out.println("Artista modificada correctamente.");
                System.out.println(artista);
            } else {
                System.out.println("No se ha podido modificar la artista.");
            }
        } else {
            System.out.println("No se ha encontrado ninguna artista con ese nombre artístico.");
        }
    }

    public void eliminarArtista() {
        String nombreArtista = leerTexto("Introduce el nombre artístico de la artista a eliminar: ");
        Artista artista = artistaDAO.obtenerArtistaPorNombre(nombreArtista);

        if (artista != null) {
            if (tieneConciertosAsociados(artista)) {
                System.out.println("No se puede eliminar la artista porque tiene conciertos asociados.");
                return;
            }

            int filasEliminadas = artistaDAO.eliminarArtista(artista.getIdArtista());

            if (filasEliminadas > 0) {
                System.out.println("Artista eliminada correctamente.");
            } else if (filasEliminadas == 0) {
                System.out.println("No se ha encontrado ninguna artista con ese nombre artístico.");
            } else {
                System.out.println("No se ha podido eliminar la artista.");
            }
        } else {
            System.out.println("No se ha encontrado ninguna artista con ese nombre artístico.");
        }
    }

    private String leerTexto(String mensaje) {
        String texto;
        do {
            System.out.print(mensaje);
            texto = scanner.nextLine().trim();
            if (texto.isEmpty()) {
                System.out.println("Este campo no puede estar vacío.");
            }
        } while (texto.isEmpty());
        return texto;
    }

    private boolean leerBoolean(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String valor = scanner.nextLine().trim().toLowerCase();
            if (valor.equals("true")) {
                return true;
            } else if (valor.equals("false")) {
                return false;
            } else {
                System.out.println("Debes introducir true o false.");
            }
        }
    }

    private boolean tieneConciertosAsociados(Artista artista) {
        return conciertoDAO.existeConciertoPorArtista(artista.getIdArtista());
    }
}

