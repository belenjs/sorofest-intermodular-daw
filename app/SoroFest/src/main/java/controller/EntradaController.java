package controller;

import dao.CompraDAO;
import dao.EntradaDAO;
import model.Compra;
import model.Edicion;
import model.Entrada;
import view.EntradaView;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class EntradaController {
    private Scanner scanner;
    private EntradaView entradaView;
    private Edicion edicion;
    private EntradaDAO entradaDAO;
    private CompraDAO compraDAO;

    public EntradaController() {

    }

    public EntradaController(Scanner scanner, Edicion edicion) {
        this.scanner = scanner;
        this.entradaView = new EntradaView();
        this.edicion = edicion;
        this.entradaDAO = new EntradaDAO();
        this.compraDAO = new CompraDAO();
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
                    case 4 -> eliminarEntrada();
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
        if (existenEntradasCompra(compra)) {
            System.out.println("Ya se han generado entradas para dicha compra.");
            return;
        }

        int cantidadEntradas = calcularCantidadEntradas(compra);
        if (cantidadEntradas <= 0) {
            System.out.println("La compra no permite generar entradas válidas.");
            return;
        }
        int totalInsertadas = 0;
        for (int i = 0; i < cantidadEntradas; i++) {
            String codigoEntrada = generarCodigoEntrada();
            Entrada entrada = new Entrada(
                    0,
                    edicion,
                    compra,
                    codigoEntrada
            );

            try {
                int filasInsertadas = entradaDAO.insertarEntrada(entrada);
                if (filasInsertadas > 0) {
                    totalInsertadas++;
                }
            } catch (SQLException e) {
                System.out.println("Error al insertar entrada en la base de datos.");
                System.out.println(e.getMessage());
            }
        }
        System.out.println("Se han generado " + totalInsertadas + " entradas correctamente.");
    }

    public void listarEntradas() {
        System.out.println("LISTADO DE ENTRADAS");
        List<Entrada> entradas = entradaDAO.obtenerEntradas();
        if (entradas.isEmpty()) {
            System.out.println("No hay entradas registradas.");
        } else {
            for (Entrada entrada : entradas) {
                System.out.println(entrada);
            }
        }
    }

    public void buscarEntrada() {
        System.out.print("Introduce el id de la entrada: ");
        if (scanner.hasNextInt()) {
            int idEntrada = scanner.nextInt();
            scanner.nextLine();
            Entrada entrada = entradaDAO.obtenerEntradaPorId(idEntrada);

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

    public void eliminarEntrada(){
        System.out.print("Introduce el id de la entrada que quieres eliminar: ");
        if (scanner.hasNextInt()) {
            int idEntrada = scanner.nextInt();
            scanner.nextLine();
            Entrada entrada = entradaDAO.obtenerEntradaPorId(idEntrada);

            if (entrada == null) {
                System.out.println("No se ha encontrado ninguna entrada con dicho id.");
                return;
            }

            int filasEliminadas = entradaDAO.eliminarEntrada(idEntrada);
            if (filasEliminadas > 0) {
                System.out.println("Entrada eliminada correctamente.");
            } else if (filasEliminadas == 0) {
                System.out.println("No se ha encontrado ninguna entrada con dicho id.");
            } else {
                System.out.println("No se ha podido eliminar la entrada.");
            }
        } else {
            System.out.println("Debes introducir un número entero.");
            scanner.nextLine();
        }
    }

    private boolean hayComprasDisponibles() {
        return !compraDAO.obtenerCompras().isEmpty();
    }

    private boolean hayEdicionDisponible() {
        return edicion != null;
    }

    private void mostrarComprasDisponibles() {
        List<Compra> compras = compraDAO.obtenerCompras();
        System.out.println("Compras disponibles:");
        for (Compra compra : compras) {
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
            if (idCompra > 0) {
                return idCompra;
            } else {
                System.out.println("El id debe ser mayor que 0.");
                return -1;
            }
        } else {
            System.out.println("Debes introducir un número entero.");
            scanner.nextLine();
            return -1;
        }
    }

    private int calcularCantidadEntradas(Compra compra) {
        if (edicion == null || edicion.getPrecioEntrada() <= 0 || compra.getImporteTotal() <= 0) {
            return 0;
        }

        double cantidad = compra.getImporteTotal() / edicion.getPrecioEntrada();
        return (int) cantidad;
    }

    private String generarCodigoEntrada() {
        int numero = entradaDAO.obtenerEntradas().size() + 1;
        return String.format("SORO2026-%04d", numero);
    }

    private Compra buscarCompraPorId(int idCompra) {
        return compraDAO.obtenerCompraPorId(idCompra);
    }

    private boolean existenEntradasCompra(Compra compra) {
        List<Entrada> entradas = entradaDAO.obtenerEntradas();
        for (Entrada entrada : entradas) {
            if (entrada.getCompra().getIdCompra() == compra.getIdCompra()) {
                return true;
            }
        }
        return false;
    }
}