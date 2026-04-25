package controller;

import view.ClienteView;

import java.util.Scanner;

public class ClienteController {
    private Scanner scanner;
    private ClienteView clienteView;

    public ClienteController(){
        this.scanner = new Scanner(System.in);
        this.clienteView = new ClienteView();
    }

    public void iniciarMenuCliente(){
        int opcion;
        do {
            clienteView.mostrarMenuClientes();
            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1 -> System.out.println("Alta de cliente");
                    case 2 -> System.out.println("Listar clientes");
                    case 3 -> System.out.println("Buscar cliente");
                    case 4 -> System.out.println("Modificar cliente");
                    case 5 -> System.out.println("Eliminar cliente");
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
