package controller;

import model.Edicion;
import view.EdicionView;

import java.time.LocalDate;
import java.util.Scanner;

public class EdicionController {
    private Scanner scanner;
    private EdicionView edicionView;
    private Edicion edicion;

    public EdicionController(Scanner scanner){
        this.scanner = scanner;
        this.edicionView = new EdicionView();
        this.edicion = new Edicion(1,
                "SoroFest Cádiz 2026",
                LocalDate.of(2026, 7, 17),
                LocalDate.of(2026, 7, 19),
                "Cádiz",
                "Muelle Reina Victoria",
                40.0,
                1000
        );
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
        System.out.println(edicion);
    }

    public void modificarDatosEdicion(){
        System.out.print("Nuevo nombre de la edición: ");
        edicion.setNombreEdicion(scanner.nextLine());
        System.out.print("Nueva fecha de inicio (yyyy-MM-dd): ");
        edicion.setFechaInicio(LocalDate.parse(scanner.nextLine()));
        System.out.print("Nueva fecha de fin (yyyy-MM-dd): ");
        edicion.setFechaFin(LocalDate.parse(scanner.nextLine()));
        System.out.print("Nueva ciudad: ");
        edicion.setCiudad(scanner.nextLine());
        System.out.print("Nuevo recinto: ");
        edicion.setRecinto(scanner.nextLine());
        System.out.print("Nuevo precio de entrada: ");
        edicion.setPrecioEntrada(scanner.nextDouble());
        scanner.nextLine();
        System.out.print("Nuevo stock de entradas: ");
        edicion.setStockEntradas(scanner.nextInt());
        scanner.nextLine();

        System.out.println("Edición modificada correctamente.");
    }

    public Edicion getEdicion() {
        return edicion;
    }
}
