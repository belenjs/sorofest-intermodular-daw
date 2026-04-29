package controller;

import dao.ClienteDAO;
import dao.CompraDAO;
import model.Cliente;
import model.Compra;
import model.Edicion;
import model.Entrada;
import view.CompraView;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class CompraController {
    private Scanner scanner;
    private CompraView compraView;
    private List<Cliente> listaClientes;
    private Edicion edicion;
    private List<Entrada> listaEntradas;
    private CompraDAO compraDAO;
    private ClienteDAO clienteDAO;

    public CompraController(){

    }

    public CompraController(Scanner scanner, List<Cliente> listaClientes, Edicion edicion){
        this.scanner = scanner;
        this.compraView = new CompraView();
        this.listaClientes = listaClientes;
        this.edicion = edicion;
        this.compraDAO = new CompraDAO();
        this.clienteDAO = new ClienteDAO();
    }

    public void iniciarMenuCompras() {
        int opcion = -1;
        do {
            compraView.mostrarMenuCompra();
            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1 -> darAltaCompra();
                    case 2 -> listarCompras();
                    case 3 -> buscarCompra();
                    case 4 -> modificarCompra();
                    case 5 -> eliminarCompra();
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

    public void darAltaCompra(){
        if (!hayClientesDisponibles()) {
            System.out.println("No hay clientes registrados. Debes dar de alta al menos a un cliente.");
            return;
        }
        if (!hayEdicionDisponible()) {
            System.out.println("No hay ninguna edición disponible.");
            return;
        }

        mostrarClientesDisponibles();
        int idCliente = pedirIdCliente();
        if (idCliente == -1) {
            return;
        }

        Cliente cliente = buscarClientePorId(idCliente);
        if (cliente == null) {
            System.out.println("No existe ningún cliente con dicho id.");
            return;
        }

        LocalDateTime fechaCompra = leerFechaHora("Fecha y hora de compra (yyyy-MM-ddTHH:mm): ");

        System.out.print("Cantidad de entradas: ");
        if (!scanner.hasNextInt()) {
            System.out.println("Debes introducir un número entero.");
            scanner.nextLine();
            return;
        }
        int cantidadEntradas = scanner.nextInt();
        scanner.nextLine();
        if (cantidadEntradas <= 0) {
            System.out.println("La cantidad de entradas debe ser mayor que 0.");
            return;
        }

        double importeTotal = cantidadEntradas * edicion.getPrecioEntrada();
        System.out.println("Importe total calculado: " + importeTotal + " €");

        String metodoPago = leerMetodoPago("Método de pago (Tarjeta, Bizum, PayPal, Transferencia): ");
        Compra compra = new Compra(
                0,
                cliente,
                fechaCompra,
                importeTotal,
                metodoPago
        );
        try {
            int filasInsertadas = compraDAO.insertarCompra(compra);
            if (filasInsertadas > 0) {
                System.out.println("Compra dada de alta correctamente.");
            } else {
                System.out.println("No se ha podido dar de alta la compra.");
            }
        } catch (SQLException e) {
            System.out.println("Error al insertar la compra en la base de datos.");
            System.out.println(e.getMessage());
        }
    }

    public void listarCompras(){
        System.out.println("LISTADO DE COMPRAS");
        List<Compra> compras = compraDAO.obtenerCompras();

        if (compras.isEmpty()) {
            System.out.println("No hay compras registradas.");
        } else {
            for (Compra compra : compras) {
                System.out.println(compra);
            }
        }
    }

    public void buscarCompra(){
        System.out.print("Introduce el id de la compra: ");
        if (scanner.hasNextInt()) {
            int idCompra = scanner.nextInt();
            scanner.nextLine();
            Compra compra = compraDAO.obtenerCompraPorId(idCompra);

            if (compra != null) {
                System.out.println(compra);
            } else {
                System.out.println("No se ha encontrado ninguna compra con dicho id.");
            }
        } else {
            System.out.println("Debes introducir un número entero.");
            scanner.nextLine();
        }
    }

    public void modificarCompra(){
        System.out.print("Introduce el id de la compra que quieres modificar: ");
        if (scanner.hasNextInt()) {
            int idCompra = scanner.nextInt();
            scanner.nextLine();
            Compra compra = compraDAO.obtenerCompraPorId(idCompra);

            if (compra == null) {
                System.out.println("No se ha encontrado ninguna compra con dicho id.");
                return;
            }
            System.out.println(compra);

            if (!hayClientesDisponibles()) {
                System.out.println("No hay clientes registrados.");
                return;
            }
            if (!hayEdicionDisponible()) {
                System.out.println("No hay ninguna edición disponible.");
                return;
            }
            mostrarClientesDisponibles();

            int nuevoIdCliente = pedirIdCliente();
            if (nuevoIdCliente == -1) {
                return;
            }

            Cliente nuevoCliente = buscarClientePorId(nuevoIdCliente);
            if (nuevoCliente == null) {
                System.out.println("No existe ningún cliente con dicho id.");
                return;
            }
            LocalDateTime nuevaFechaCompra = leerFechaHora("Nueva fecha y hora de compra (yyyy-MM-ddTHH:mm): ");
            System.out.print("Nueva cantidad de entradas: ");
            if (!scanner.hasNextInt()) {
                System.out.println("Debes introducir un número entero.");
                scanner.nextLine();
                return;
            }

            int cantidadEntradas = scanner.nextInt();
            scanner.nextLine();
            if (cantidadEntradas <= 0) {
                System.out.println("La cantidad de entradas debe ser mayor que 0.");
                return;
            }

            double nuevoImporteTotal = cantidadEntradas * edicion.getPrecioEntrada();

            System.out.println("Nuevo importe total calculado: " + nuevoImporteTotal + " €");
            String nuevoMetodoPago = leerMetodoPago("Nuevo método de pago (Tarjeta, Bizum, PayPal, Transferencia): ");
            compra.setCliente(nuevoCliente);
            compra.setFechaCompra(nuevaFechaCompra);
            compra.setImporteTotal(nuevoImporteTotal);
            compra.setMetodoPago(nuevoMetodoPago);

            int filasActualizadas = compraDAO.actualizarCompra(compra);
            if (filasActualizadas > 0) {
                System.out.println("Compra modificada correctamente.");
                System.out.println(compra);
            } else {
                System.out.println("No se ha podido modificar la compra.");
            }
        } else {
            System.out.println("Debes introducir un número entero.");
            scanner.nextLine();
        }
    }

    public void eliminarCompra(){
        System.out.print("Introduce el id de la compra que quieres eliminar: ");
        if (scanner.hasNextInt()) {
            int idCompra = scanner.nextInt();
            scanner.nextLine();
            Compra compra = compraDAO.obtenerCompraPorId(idCompra);

            if (compra == null) {
                System.out.println("No se ha encontrado ninguna compra con dicho id.");
                return;
            }
            if (tieneEntradasAsociadas(compra)) {
                System.out.println("No se puede eliminar la compra porque tiene entradas asociadas.");
                return;
            }
            int filasEliminadas = compraDAO.eliminarCompra(idCompra);
            if (filasEliminadas > 0) {
                System.out.println("Compra eliminada correctamente.");
            } else if (filasEliminadas == 0) {
                System.out.println("No se ha encontrado ninguna compra con dicho id.");
            } else {
                System.out.println("No se ha podido eliminar la compra.");
            }
        } else {
            System.out.println("Debes introducir un número entero.");
            scanner.nextLine();
        }
    }

    private boolean hayClientesDisponibles() {
        return !clienteDAO.obtenerClientes().isEmpty();
    }

    private boolean hayEdicionDisponible() {
        return edicion != null;
    }

    private void mostrarClientesDisponibles() {
        List<Cliente> clientes = clienteDAO.obtenerClientes();
        System.out.println("Clientes disponibles:");
        for (Cliente cliente : clientes) {
            System.out.println("ID Cliente: " + cliente.getIdCliente() + " - Nombre: " + cliente.getNombre() + " " + cliente.getApellidos());
        }
    }

    private int pedirIdCliente() {
        System.out.print("Introduce el id del cliente: ");
        if (scanner.hasNextInt()) {
            int idCliente = scanner.nextInt();
            scanner.nextLine();
            return idCliente;
        } else {
            System.out.println("Debes introducir un número entero.");
            scanner.nextLine();
            return -1;
        }
    }

    private Cliente buscarClientePorId(int idBuscado) {
        List<Cliente> clientes = clienteDAO.obtenerClientes();
        for (Cliente cliente : clientes) {
            if (cliente.getIdCliente() == idBuscado) {
                return cliente;
            }
        }
        return null;
    }

    private LocalDateTime leerFechaHora(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String fechaHoraTexto = scanner.nextLine().trim();

            try {
                return LocalDateTime.parse(fechaHoraTexto);
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha y hora no válido. Usa yyyy-MM-ddTHH:mm.");
            }
        }
    }

    private String leerMetodoPago(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String metodoPago = scanner.nextLine().trim();

            if (metodoPago.equalsIgnoreCase("Tarjeta")
                    || metodoPago.equalsIgnoreCase("Bizum")
                    || metodoPago.equalsIgnoreCase("PayPal")
                    || metodoPago.equalsIgnoreCase("Transferencia")) {
                return metodoPago;
            } else {
                System.out.println("Método de pago no válido.");
            }
        }
    }

    private boolean tieneEntradasAsociadas(Compra compra) {
        if (listaEntradas == null) {
            return false;
        }
        for (Entrada entrada : listaEntradas) {
            if (entrada.getCompra().getIdCompra() == compra.getIdCompra()) {
                return true;
            }
        }
        return false;
    }

    public void setListaEntradas(List<Entrada> listaEntradas) {
        this.listaEntradas = listaEntradas;
    }

}
