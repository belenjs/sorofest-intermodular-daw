package controller;

import dao.ClienteDAO;
import dao.CompraDAO;
import model.Cliente;
import view.ClienteView;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class ClienteController {
    private Scanner scanner;
    private ClienteView clienteView;
    private ClienteDAO clienteDAO;
    private CompraDAO compraDAO;

    public ClienteController() {

    }

    public ClienteController(Scanner scanner) {
        this.scanner = scanner;
        this.clienteView = new ClienteView();
        this.clienteDAO = new ClienteDAO();
        this.compraDAO = new CompraDAO();
    }

    public void iniciarMenuCliente() {
        int opcion;
        do {
            clienteView.mostrarMenuClientes();
            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                scanner.nextLine();

                switch (opcion) {
                    case 1 -> darAltaCliente();
                    case 2 -> listarClientes();
                    case 3 -> buscarCliente();
                    case 4 -> modificarDatosCliente();
                    case 5 -> eliminarCliente();
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

    public void darAltaCliente() {
        String dni = leerTexto("Introduce DNI: ");
        if (clienteDAO.obtenerClientePorDni(dni) != null) {
            System.out.println("Ya existe un cliente con ese DNI.");
            return;
        }
        String nombre = leerTexto("Introduce nombre: ");
        String apellidos = leerTexto("Introduce apellidos: ");
        String email = leerTexto("Introduce email: ");
        System.out.print("Introduce teléfono: ");
        String telefono = scanner.nextLine().trim();
        LocalDate fechaNacimiento = leerFecha("Introduce fecha de nacimiento (yyyy-MM-dd): ");

        Cliente cliente = new Cliente(0, dni, nombre, apellidos, email, telefono, fechaNacimiento);

        try {
            int filasInsertadas = clienteDAO.insertarCliente(cliente);
            if (filasInsertadas > 0) {
                System.out.println("Cliente dado de alta correctamente.");
            } else {
                System.out.println("No se ha podido dar de alta el cliente.");
            }
        } catch (SQLException e) {
            System.out.println("Error al insertar nuevo cliente en la base de datos");
            System.out.println(e.getMessage());
        }
    }

    public void listarClientes() {
        System.out.println("LISTADO DE CLIENTES");
        List<Cliente> clientes = clienteDAO.obtenerClientes();
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados");
        } else {
            for (Cliente cliente : clientes) {
                System.out.println(cliente);
            }
        }
    }

    public void buscarCliente() {
        String dniCliente = leerTexto("Introduce el DNI del cliente: ");
        Cliente cliente = clienteDAO.obtenerClientePorDni(dniCliente);

        if (cliente != null) {
            System.out.println(cliente);
        } else {
            System.out.println("No se ha encontrado ningún cliente con dicho dni");
        }

    }

    public void modificarDatosCliente() {
        String dniCliente = leerTexto("Introduce el DNI del cliente a modificar: ");
        Cliente cliente = clienteDAO.obtenerClientePorDni(dniCliente);
        if (cliente == null) {
            System.out.println("No se ha encontrado ningún cliente con dicho DNI");
            return;
        }

        String nuevoNombre = leerTexto("Nuevo nombre: ");
        String nuevosApellidos = leerTexto("Nuevos apellidos: ");
        String nuevoEmail = leerTexto("Nuevo email: ");
        System.out.print("Nuevo teléfono: ");
        String nuevoTelefono = scanner.nextLine().trim();
        LocalDate nuevaFechaNacimiento = leerFecha("Nueva fecha de nacimiento (yyyy-MM-dd): ");

        cliente.setNombre(nuevoNombre);
        cliente.setApellidos(nuevosApellidos);
        cliente.setEmail(nuevoEmail);
        cliente.setTelefono(nuevoTelefono);
        cliente.setFechaNacimiento(nuevaFechaNacimiento);

        int filasActualizadas = clienteDAO.actualizarCliente(cliente);

        if (filasActualizadas > 0) {
            System.out.println("Cliente modificado correctamente");
            System.out.println(cliente);
        } else {
            System.out.println("No se ha podido modificar el cliente.");
        }
    }

    public void eliminarCliente() {
        String dniCliente = leerTexto("Introduce el DNI del cliente a eliminar: ");
        Cliente cliente = clienteDAO.obtenerClientePorDni(dniCliente);
        if (cliente == null) {
            System.out.println("No se ha encontrado ningún cliente con dicho DNI");
            return;
        }

        if (tieneComprasAsociadas(cliente)) {
            System.out.println("No se puede eliminar el cliente porque tiene compras asociadas.");
            return;
        }

        int filasEliminadas = clienteDAO.eliminarCliente(dniCliente);

        if (filasEliminadas > 0) {
            System.out.println("Cliente eliminado correctamente");
        } else if (filasEliminadas == 0) {
            System.out.println("No se ha encontrado ningún cliente con ese DNI");
        } else {
            System.out.println("No se ha podido eliminar el cliente");
        }
    }

    private String leerTexto(String mensaje) {
        String texto;
        do {
            System.out.print(mensaje);
            texto = scanner.nextLine().trim();

            if (texto.isEmpty()) {
                System.out.println("Este campo no puede estar vacío.");
            }
        } while (texto.isEmpty());

        return texto;
    }

    private LocalDate leerFecha(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String fechaTexto = scanner.nextLine().trim();

            try {
                return LocalDate.parse(fechaTexto);
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha no válido. Usa yyyy-MM-dd.");
            }
        }
    }

    private boolean tieneComprasAsociadas(Cliente cliente) {
        return compraDAO.existeCompraPorCliente(cliente.getIdCliente());
    }
}
