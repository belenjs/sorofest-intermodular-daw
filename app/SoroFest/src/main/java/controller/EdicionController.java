package controller;

import dao.EdicionDAO;
import model.Edicion;
import view.EdicionView;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class EdicionController {
    private Scanner scanner;
    private EdicionView edicionView;
    private Edicion edicion;
    private EdicionDAO edicionDAO;

    public EdicionController(Scanner scanner){
        this.scanner = scanner;
        this.edicionView = new EdicionView();
        this.edicionDAO = new EdicionDAO();
        this.edicion = edicionDAO.obtenerEdicion();
    }

    public void iniciarMenuEdicion(){
        int opcion = -1;
        do {
            edicionView.mostrarMenuEdicion();
            if(scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1 -> verDatosEdicion();
                    case 2 -> modificarDatosEdicion();
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

    public void verDatosEdicion(){
        System.out.println("--- DATOS DE LA EDICIÓN ---");
        edicion = edicionDAO.obtenerEdicion();
        System.out.println(edicion);
    }

    public void modificarDatosEdicion(){
        String nuevoNombre = leerTexto("Nuevo nombre de la edición: ");
        LocalDate nuevaFechaInicio = leerFecha("Nueva fecha inicio de edición (yyyy-MM-dd): ");
        LocalDate nuevaFechaFin = leerFecha("Nueva fecha fin de edición (yyyy-MM-dd): ");

        if (!nuevaFechaFin.isAfter(nuevaFechaInicio)) {
            System.out.println("La fecha de fin debe ser posterior a la fecha de inicio.");
            return;
        }
        String nuevaCiudad = leerTexto("Nueva ciudad: ");
        String nuevoRecinto = leerTexto("Nuevo recinto: ");
        double nuevoPrecioEntrada = leerDoublePositivo("Nuevo precio de entrada: ");
        int nuevoStockEntradas = leerIntNoNegativo("Nuevo stock de entradas: ");

        edicion.setNombreEdicion(nuevoNombre);
        edicion.setFechaInicio(nuevaFechaInicio);
        edicion.setFechaFin(nuevaFechaFin);
        edicion.setCiudad(nuevaCiudad);
        edicion.setRecinto(nuevoRecinto);
        edicion.setPrecioEntrada(nuevoPrecioEntrada);
        edicion.setStockEntradas(nuevoStockEntradas);

        int filasActualizadas = edicionDAO.actualizarEdicion(edicion);
        if (filasActualizadas > 0) {
            System.out.println("Edición modificada correctamente.");
        } else {
            System.out.println("No se ha podido modificar la edición.");
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

    private double leerDoublePositivo(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            if (scanner.hasNextDouble()) {
                double valor = scanner.nextDouble();
                scanner.nextLine();

                if (valor > 0) {
                    return valor;
                } else {
                    System.out.println("El valor debe ser mayor que 0.");
                }
            } else {
                System.out.println("Debes introducir un número válido.");
                scanner.nextLine();
            }
        }
    }

    private int leerIntNoNegativo(String mensaje) {
        while (true) {
            System.out.print(mensaje);

            if (scanner.hasNextInt()) {
                int valor = scanner.nextInt();
                scanner.nextLine();

                if (valor >= 0) {
                    return valor;
                } else {
                    System.out.println("El valor no puede ser negativo.");
                }
            } else {
                System.out.println("Debes introducir un número entero.");
                scanner.nextLine();
            }
        }
    }

    public Edicion getEdicion() {
        return edicion;
    }
}
