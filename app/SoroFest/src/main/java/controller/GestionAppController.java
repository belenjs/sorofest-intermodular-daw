package controller;

import view.MenuGestion;

import java.util.List;
import java.util.Scanner;

public class GestionAppController {
    private Scanner scanner;
    private MenuGestion menuGestion;
    private ClienteController clienteController;
    private EdicionController edicionController;
    private ArtistaController artistaController;
    private ConciertoController conciertoController;
    private CompraController compraController;
    private EntradaController entradaController;

    public GestionAppController(){
        scanner = new Scanner(System.in);
        menuGestion = new MenuGestion();
        clienteController = new ClienteController(scanner);
        edicionController = new EdicionController(scanner);
        artistaController = new ArtistaController(scanner);
        conciertoController = new ConciertoController(
                scanner,
                artistaController.getListaArtistas(),
                edicionController.getEdicion()
        );
        compraController = new CompraController (
                scanner,
                clienteController.getListaClientes(),
                edicionController.getEdicion()
        );
        entradaController = new EntradaController(
                scanner,
                compraController.getListaCompras(),
                edicionController.getEdicion()
        );

        clienteController.setListaCompras(compraController.getListaCompras());
    }

    public void iniciar(){
        int opcion = -1;
        do {
            menuGestion.mostrarMenuPrincipal();
            if(scanner.hasNextInt()) {
                opcion = scanner.nextInt();;
                scanner.nextLine();

                switch (opcion) {
                    case 1 -> edicionController.iniciarMenuEdicion();
                    case 2 -> artistaController.iniciarMenuArtista();
                    case 3 -> conciertoController.iniciarMenuConciertos();
                    case 4 -> clienteController.iniciarMenuCliente();
                    case 5 -> entradaController.iniciarMenuEntradas();
                    case 6 -> compraController.iniciarMenuCompras();
                    case 0 -> System.out.println("Saliendo de la app");
                    default -> System.out.println("Opción no válida");
                }
            }
        } while(opcion != 0);
        scanner.close();
    }
}
