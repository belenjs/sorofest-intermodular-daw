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

    public EntradaController() {

    }

    public EntradaController(Scanner scanner, List<Compra> listaCompras, Edicion edicion) {
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
                    case 1 -> darAltaEntrada();
                    case 2 -> listarEntradas();
                    case 3 -> buscarEntrada();
                    case 4 -> System.out.println("5. Eliminar entrada");
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

    public void darAltaEntrada() {
        if (!hayComprasDisponibles()) {
            System.out.println("No hay compras registradas.");
            return;
        }
        if (!hayEdicionDisponible()) {
            System.out.println("No hay ninguna edición disponible.");
            return;
        }
        mostrarComprasDisponibles();

        int idCompra = pedirIdCompra();
        if (idCompra == -1) {
            return;
        }

        Compra compra = buscarCompraPorId(idCompra);
        if (compra == null) {
            System.out.println("No existe ninguna compra con dicho id.");
            return;
        }

        int cantidadEntradas = calcularCantidadEntradas(compra);
        if (cantidadEntradas <= 0) {
            System.out.println("La compra no permite generar entradas válidas.");
            return;
        }
        for (int i = 0; i < cantidadEntradas; i++) {
            int idEntrada = generarNuevoIdEntrada();
            String codigoEntrada = generarCodigoEntrada();

            Entrada entrada = new Entrada(
                    idEntrada,
                    edicion,
                    compra,
                    codigoEntrada
            );

            guardarEntrada(entrada);
        }
        System.out.println("Se han generado " + cantidadEntradas + " entradas correctamente.");
    }

    public void listarEntradas() {
        System.out.println("LISTADO DE ENTRADAS");
        if (listaEntradas.isEmpty()) {
            System.out.println("No hay entradas registradas.");
        } else {
            for (Entrada entrada : listaEntradas) {
                System.out.println(entrada);
            }
        }
    }

    private void buscarEntrada() {
        System.out.print("Introduce el id de la entrada: ");
        if (scanner.hasNextInt()) {
            int idEntrada = scanner.nextInt();
            scanner.nextLine();
            Entrada entrada = buscarEntradaPorId(idEntrada);

            if (entrada != null) {
                System.out.println("Entrada encontrada:");
                System.out.println(entrada);
            } else {
                System.out.println("No se ha encontrado ninguna entrada con dicho id.");
            }
        } else {
            System.out.println("Debes introducir un número entero.");
            scanner.nextLine();
        }
    }

    private boolean hayComprasDisponibles() {
        return !listaCompras.isEmpty();
    }

    private boolean hayEdicionDisponible() {
        return edicion != null;
    }

    private void mostrarComprasDisponibles() {
        System.out.println("Compras disponibles:");
        for (Compra compra : listaCompras) {
            System.out.println("ID Compra: " + compra.getIdCompra()
                    + " - Cliente: " + compra.getCliente().getNombre() + " " + compra.getCliente().getApellidos()
                    + " - Importe: " + compra.getImporteTotal() + " €");
        }
    }

    private int pedirIdCompra() {
        System.out.print("Introduce el id de la compra: ");
        if (scanner.hasNextInt()) {
            int idCompra = scanner.nextInt();
            scanner.nextLine();
            return idCompra;
        } else {
            System.out.println("Debes introducir un número entero.");
            scanner.nextLine();
            return -1;
        }
    }

    private int calcularCantidadEntradas(Compra compra) {
        if (edicion == null || edicion.getPrecioEntrada() <= 0) {
            return 0;
        }

        double cantidad = compra.getImporteTotal() / edicion.getPrecioEntrada();
        return (int) cantidad;
    }

    private int generarNuevoIdEntrada() {
        return listaEntradas.size() + 1;
    }

    private String generarCodigoEntrada() {
        int numero = listaEntradas.size() + 1;
        return String.format("SORO2026-%04d", numero);
    }

    private void guardarEntrada(Entrada entrada) {
        listaEntradas.add(entrada);
    }

    private Compra buscarCompraPorId(int idCompra) {
        for (Compra compra : listaCompras) {
            if (compra.getIdCompra() == idCompra) {
                return compra;
            }
        }
        return null;
    }

    private Entrada buscarEntradaPorId(int idEntrada) {
        for (Entrada entrada : listaEntradas) {
            if (entrada.getIdEntrada() == idEntrada) {
                return entrada;
            }
        }
        return null;
    }

}






