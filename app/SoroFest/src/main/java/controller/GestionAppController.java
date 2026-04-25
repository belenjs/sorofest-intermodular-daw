package controller;

import view.MenuGestion;

import java.util.Scanner;

public class GestionAppController {
    private Scanner scanner;
    private MenuGestion menuGestion;

    public GestionAppController(){
        scanner = new Scanner(System.in);
        menuGestion = new MenuGestion();
    }

    public void iniciar(){
        int opcion = -1;
        do {
            menuGestion.mostrarMenuPrincipal();
            if(scanner.hasNextInt()) {
                opcion = scanner.nextInt();;
                scanner.nextLine();

                switch (opcion) {
                    case 1 -> System.out.println("Gestión de ediciones");
                    case 2 -> System.out.println("Gestión de artistas");
                    case 3 -> System.out.println("Gestión de conciertos");
                    case 4 -> System.out.println("Gestión de clientes");
                    case 5 -> System.out.println("Gestión de entradas");
                    case 6 -> System.out.println("Gestión de compras");
                    case 0 -> System.out.println("Saliendo de la app");
                    default -> System.out.println("Opción no válida");

                }
            }
        } while(opcion != 0);
    }
}
