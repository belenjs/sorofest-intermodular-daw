package controller;

import model.Artista;
import view.ArtistaView;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ArtistaController {
    private Scanner scanner;
    private ArtistaView artistaView;
    private List<Artista> listaArtistas;

    public ArtistaController(Scanner scanner){
        this.scanner = scanner;
        this.artistaView = new ArtistaView();
        this.listaArtistas = new ArrayList<>();
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
                    case 2 -> System.out.println("2. Listar artistas");
                    case 3 -> System.out.println("3. Buscar artistas");
                    case 4 -> System.out.println("4. Modificar artista");
                    case 5 -> System.out.println("5. Eliminar rtista");
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
        System.out.print("Nombre artístico: ");
        String nombreArtista = scanner.nextLine();
        System.out.print("Tipo de artista: ");
        String tipoArtista = scanner.nextLine();
        System.out.print("Género musical: ");
        String generoMusical = scanner.nextLine();
        System.out.print("¿Es cabeza de cartel? (true/false): ");
        boolean esCabezaCartel = scanner.nextBoolean();
        scanner.nextLine();
        System.out.print("Descripción: ");
        String descripcion = scanner.nextLine();

        int idArtista = listaArtistas.size() + 1;
        Artista artista = new Artista(
                idArtista,
                nombreArtista,
                tipoArtista,
                generoMusical,
                esCabezaCartel,
                descripcion
        );
        listaArtistas.add(artista);

        System.out.println("Artista dada de alta correctamente.");
        System.out.println(artista);
    }
}
