package controller;

import model.Concierto;
import view.ConciertoView;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConciertoController {
    private Scanner scanner;
    private ConciertoView conciertoView;
    private List<Concierto> listaConciertos;
    private EdicionController edicionController;
    private ArtistaController artistaController;

    public ConciertoController(Scanner scanner, EdicionController edicionController, ArtistaController artistaController){
        this.scanner = scanner;
        this.conciertoView = new ConciertoView();
        this.listaConciertos = new ArrayList<>();
        this.edicionController = edicionController;
        this.artistaController = artistaController;
    }

    public void iniciarMenuConciertos() {
        int opcion = -1;
        do {
            conciertoView.mostrarMenuConcierto();
            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1 -> System.out.println("1. Dar de alta concierto");
                    case 2 -> System.out.println("2. Listar conciertos");
                    case 3 -> System.out.println("3. Buscar concierto");
                    case 4 -> System.out.println("4. Modificar concierto");
                    case 5 -> System.out.println("5. Eliminar concierto");
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
}
