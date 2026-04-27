package controller;

import model.Compra;
import model.Edicion;
import model.Entrada;
import view.EntradaView;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EntradaController {
    private Scanner scanner;
    private EntradaView entradaView;
    private List<Entrada> listaEntradas;
    private List<Compra> listaCompras;
    private Edicion edicion;

    public EntradaController(){

    }

    public EntradaController(Scanner scanner, List<Compra> listaCompras, Edicion edicion){
        this.scanner = scanner;
        this.entradaView = new EntradaView();
        this.listaEntradas = new ArrayList<>();
        this.listaCompras = listaCompras;
        this.edicion = edicion;
    }

    public void iniciarMenuEntradas() {
        int opcion = -1;

        do {
            entradaView.mostrarMenuEntrada();

            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1 -> System.out.println("1. Dar de alta entrada");
                    case 2 -> System.out.println("2. Listar entradas");
                    case 3 -> System.out.println("3. Buscar entrada");
                    case 4 -> System.out.println("4. Modificar entrada");
                    case 5 -> System.out.println("5. Eliminar entrada");
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
