package controller;

import model.Artista;
import model.Concierto;
import view.ArtistaView;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ArtistaController {
    private Scanner scanner;
    private ArtistaView artistaView;
    private List<Artista> listaArtistas;
    private List<Concierto> listaConciertos;

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

    public void listarArtistas(){
        System.out.println("LISTADO DE ARTISTAS");
        if (listaArtistas.isEmpty()) {
            System.out.println("No hay artistas registradas.");
        } else {
            for (Artista artista : listaArtistas) {
                System.out.println(artista);
            }
        }
    }

    private Artista buscarArtistaPorId(int idArtista) {
        for (Artista artista : listaArtistas) {
            if (artista.getIdArtista() == idArtista) {
                return artista;
            }
        }
        return null;
    }

    public void buscarArtista(){
        System.out.println("Introduce el id de la artista a buscar: ");

        if(scanner.hasNextInt()) {
            int idArtista = scanner.nextInt();
            scanner.nextLine();
            Artista artista = buscarArtistaPorId(idArtista);

            if (artista != null) {
                System.out.println(artista);
            } else {
                System.out.println("No se ha encontrado ninguna artista con dicho id.");
            }
        } else {
            System.out.println("Debes introducir un número entero.");
            scanner.nextLine();
        }
    }

    public void modificarArtista(){
        System.out.println("Introduce el id de la artista a modificar: ");

        if(scanner.hasNextInt()) {
            int idArtista = scanner.nextInt();
            scanner.nextLine();
            Artista artista = buscarArtistaPorId(idArtista);

            if (artista != null) {
                artista.setNombreArtista(leerTexto("Nuevo nombre artístico: "));
                artista.setTipoArtista(leerTexto("Nuevo tipo de artista: "));
                artista.setGeneroMusical(leerTexto("Nuevo género musical: "));
                artista.setEsCabezaCartel(leerBoolean("¿Es cabeza de cartel? (true/false): "));
                artista.setDescripcion(leerTexto("Nueva descripción: "));

                System.out.println("Artista modificada correctamente");
                System.out.println(artista);
            } else {
                System.out.println("No se ha encontrado ninguna artista con dicho id.");
            }
        } else {
            System.out.println("Debes introducir un número entero.");
            scanner.nextLine();
        }
    }

    public void eliminarArtista(){
        System.out.print("Introduce el id de la artista a eliminar: ");

        if(scanner.hasNextInt()) {
            int idArtista = scanner.nextInt();
            scanner.nextLine();
            Artista artista = buscarArtistaPorId(idArtista);

            if (artista != null) {
                if (tieneConciertosAsociados(artista)) {
                    System.out.println("No se puede eliminar la artista porque tiene conciertos asociados.");
                    return;
                }
                listaArtistas.remove(artista);
                System.out.println("Artista eliminada correctamente.");
            } else {
                System.out.println("No se ha encontrado ninguna artista con dicho nombre artístico.");
            }
        } else {
            System.out.println("Debes introducir un número entero.");
            scanner.nextLine();
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
        if (listaConciertos == null) {
            return false;
        }
        for (Concierto concierto : listaConciertos) {
            if (concierto.getArtista().getIdArtista() == artista.getIdArtista()) {
                return true;
            }
        }
        return false;
    }

    public List<Artista> getListaArtistas() {
        return listaArtistas;
    }

    public void setListaConciertos(List<Concierto> listaConciertos) {
        this.listaConciertos = listaConciertos;
    }
}

