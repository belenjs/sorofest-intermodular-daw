package controller;

import model.Cliente;
import model.Compra;
import view.CompraView;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CompraController {
    private Scanner scanner;
    private CompraView compraView;
    private List<Compra> listaCompras;
    private List<Cliente> listaClientes;

    public CompraController(){

    }

    public CompraController(Scanner scanner, List<Cliente> listaClientes){
        this.scanner = scanner;
        this.compraView = new CompraView();
        this.listaCompras = new ArrayList<>();
        this.listaClientes = listaClientes;
    }

    public void iniciarMenuCompras() {
        int opcion = -1;
        do {
            compraView.mostrarMenuCompra();
            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1 -> System.out.println("1. Dar de alta compra de entradas\""); ;
                    case 2 -> System.out.println("2. Listar compras de entradas");
                    case 3 -> System.out.println("3. Buscar compra");;
                    case 4 -> System.out.println("4. Modificar compra");;
                    case 5 -> System.out.println("5. Eliminar compra");;
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
