package controller;

import model.Artista;
import model.Concierto;
import model.Edicion;
import view.ConciertoView;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConciertoController {
    private Scanner scanner;
    private ConciertoView conciertoView;
    private List<Concierto> listaConciertos;
    private List<Artista> listaArtistas;
    private Edicion edicion;

    public ConciertoController(Scanner scanner, List<Artista> listaArtistas, Edicion edicion ){
        this.scanner = scanner;
        this.conciertoView = new ConciertoView();
        this.listaConciertos = new ArrayList<>();
        this.listaArtistas = listaArtistas;
        this.edicion = edicion;
    }

    public void iniciarMenuConciertos() {
        int opcion = -1;
        do {
            conciertoView.mostrarMenuConcierto();
            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1 -> darAltaConcierto();
                    case 2 -> listarConciertos();
                    case 3 -> buscarConcierto();
                    case 4 -> modificarConcierto();
                    case 5 -> eliminarConcierto();
                    case 0 -> System.out.println("Volviendo al menú principal...");
                    default -> System.out.println("Opción no válida.");
                }
            } else {
                System.out.println("Debes introducir un número.");
                scanner.nextLine();
                opcion = -1;
            }
        } while (opcion != 0);
    }

    public void darAltaConcierto(){
        if(!hayEdicionDisponible()){
            System.out.println("No hay ninguna edición de festival disponible");
            return;
        }
        if(!hayArtistasDisponibles()) {
            System.out.println("No hay artistas registradas. Debes dar de alta al menos a una artista");
            return;
        }
        mostrarArtistasDisponibles();

        int idArtista = pedirIdArtista();
        if(idArtista == -1) return;

        Artista artista = buscarArtistaPorId(idArtista);
        if(artista == null) {
            System.out.println("No existe ninguna artista con dicho id");
            return;
        }
        LocalDate fecha = leerFecha("Fecha del concierto (yyyy-MM-dd): ");
        if (fecha.isBefore(edicion.getFechaInicio()) || fecha.isAfter(edicion.getFechaFin())) {
            System.out.println("La fecha del concierto debe estar dentro de las fechas de la edición.");
            return;
        }
        LocalTime horaInicio = leerHora("Hora de inicio (HH:mm): ");
        LocalTime horaFin = leerHora("Hora de fin (HH:mm): ");
        if (!horaFin.isAfter(horaInicio)) {
            System.out.println("La hora de fin debe ser posterior a la hora de inicio.");
            return;
        }

        int idConcierto = generarNuevoConcierto();
        Concierto concierto = new Concierto(
                idConcierto,
                edicion,
                artista,
                fecha,
                horaInicio,
                horaFin
        );

        guardarConcierto(concierto);
        System.out.println("Concierto dado de alta correctamente.");
        System.out.println(concierto);
    }

    public void listarConciertos(){
        System.out.println("LISTADO DE CONCIERTOS");
        if (listaConciertos.isEmpty()) {
            System.out.println("No hay conciertos registrados.");
        } else {
            for (Concierto concierto : listaConciertos) {
                System.out.println(concierto);
            }
        }
    }

    public void buscarConcierto(){
        System.out.print("Introduce el id del concierto: ");
        if (scanner.hasNextInt()) {
            int idConcierto = scanner.nextInt();
            scanner.nextLine();
            Concierto concierto = buscarConciertoPorId(idConcierto);

            if (concierto != null) {
                System.out.println(concierto);
            } else {
                System.out.println("No se ha encontrado ningún concierto con dicho id.");
            }
        } else {
            System.out.println("Debes introducir un número entero.");
            scanner.nextLine();
        }
    }

    public void modificarConcierto(){
        System.out.print("Introduce el id del concierto que quieres modificar: ");

        if (scanner.hasNextInt()) {
            int idConcierto = scanner.nextInt();
            scanner.nextLine();
            Concierto concierto = buscarConciertoPorId(idConcierto);

            if (concierto == null) {
                System.out.println("No se ha encontrado ningún concierto con dicho id.");
                return;
            }
            System.out.println("Concierto encontrado:");
            System.out.println(concierto);

            if (!hayArtistasDisponibles()) {
                System.out.println("No hay artistas registradas.");
                return;
            }
            mostrarArtistasDisponibles();

            int nuevoIdArtista = pedirIdArtista();
            if (nuevoIdArtista == -1) {
                return;
            }

            Artista nuevaArtista = buscarArtistaPorId(nuevoIdArtista);
            if (nuevaArtista == null) {
                System.out.println("No existe ninguna artista con dicho id.");
                return;
            }

            System.out.print("Nueva fecha (yyyy-MM-dd): ");
            concierto.setFecha(LocalDate.parse(scanner.nextLine()));
            System.out.print("Nueva hora de inicio (HH:mm): ");
            concierto.setHoraInicio(LocalTime.parse(scanner.nextLine()));
            System.out.print("Nueva hora de fin (HH:mm): ");
            concierto.setHoraFin(LocalTime.parse(scanner.nextLine()));
            concierto.setArtista(nuevaArtista);
            System.out.println("Concierto modificado correctamente.");
            System.out.println(concierto);

        } else {
            System.out.println("Debes introducir un número entero.");
            scanner.nextLine();
        }
    }

    public void eliminarConcierto(){
        System.out.print("Introduce el id del concierto que quieres eliminar: ");

        if (scanner.hasNextInt()) {
            int idBuscado = scanner.nextInt();
            scanner.nextLine();
            Concierto concierto = buscarConciertoPorId(idBuscado);

            if (concierto != null) {
                listaConciertos.remove(concierto);
                System.out.println("Concierto eliminado correctamente.");
            } else {
                System.out.println("No se ha encontrado ningún concierto con dicho id.");
            }
        } else {
            System.out.println("Debes introducir un número entero.");
            scanner.nextLine();
        }
    }

    private boolean hayEdicionDisponible() {
        return edicion != null;
    }

    private boolean hayArtistasDisponibles() {
        return !listaArtistas.isEmpty();
    }

    private void mostrarArtistasDisponibles() {
        System.out.println("Artistas disponibles:");
        for (Artista artista : listaArtistas) {
            System.out.println("ID Artista: " + artista.getIdArtista() + " - Nombre artístico: " + artista.getNombreArtista());
        }
    }

    private int pedirIdArtista() {
        System.out.print("Introduce el id de la artista: ");
        if (scanner.hasNextInt()) {
            int idArtista = scanner.nextInt();
            scanner.nextLine();
            return idArtista;
        } else {
            System.out.println("Debes introducir un número entero.");
            scanner.nextLine();
            return -1;
        }
    }

    private int generarNuevoConcierto() {
        return listaConciertos.size() + 1;
    }

    private void guardarConcierto(Concierto concierto) {
        listaConciertos.add(concierto);
    }

    private Artista buscarArtistaPorId(int idBuscado) {
        for (Artista artista : listaArtistas) {
            if (artista.getIdArtista() == idBuscado) {
                return artista;
            }
        }
        return null;
    }

    private Concierto buscarConciertoPorId(int idConcierto) {
        for (Concierto concierto : listaConciertos) {
            if (concierto.getIdConcierto() == idConcierto) {
                return concierto;
            }
        }
        return null;
    }

    private LocalDate leerFecha(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String fechaTexto = scanner.nextLine().trim();

            try {
                return LocalDate.parse(fechaTexto);
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha no válido. Usa yyyy-MM-dd.");
            }
        }

    }

    private LocalTime leerHora(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String horaTexto = scanner.nextLine().trim();

            try {
                return LocalTime.parse(horaTexto);
            } catch (DateTimeParseException e) {
                System.out.println("Formato de hora no válido. Usa HH:mm.");
            }
        }
    }
}
