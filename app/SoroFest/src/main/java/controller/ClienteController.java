package controller;

import model.Cliente;
import view.ClienteView;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClienteController {
    private Scanner scanner;
    private ClienteView clienteView;
    private List<Cliente> listaClientes;

    public ClienteController(){

    }

    public ClienteController(Scanner scanner){
        this.scanner = scanner;
        this.clienteView = new ClienteView();
        this.listaClientes = new ArrayList<>();
    }

    public void iniciarMenuCliente(){
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

    public void darAltaCliente(){
        String dni = leerTexto("Introduce DNI: ");
        if (existeDni(dni)) {
            System.out.println("Ya existe un cliente con ese DNI.");
            return;
        }
        String nombre = leerTexto("Introduce nombre: ");
        String apellidos = leerTexto("Introduce apellidos: ");

        String email = leerTexto("Introduce email: ");
        if (existeEmail(email)) {
            System.out.println("Ya existe un cliente con ese email.");
            return;
        }
        System.out.print("Introduce teléfono: ");
        String telefono = scanner.nextLine().trim();

        LocalDate fechaNacimiento = leerFecha("Introduce fecha de nacimiento (yyyy-MM-dd): ");

        int idCliente = listaClientes.size() + 1;
        Cliente cliente = new Cliente(idCliente, dni, nombre, apellidos, email, telefono, fechaNacimiento);
        listaClientes.add(cliente);

        System.out.println("Cliente dado de alta correctamente");
        System.out.println(cliente);
    }

    public void listarClientes(){
        System.out.println("LISTADO DE CLIENTES");
        if(listaClientes.isEmpty()){
            System.out.println("No hay clientes registrados");
        } else {
            for(Cliente cliente : listaClientes) {
                System.out.println(cliente);
            }
        }
    }

    public void buscarCliente(){
        String dniCliente = leerTexto("Introduce el DNI del cliente: ");
        boolean encontrado = false;

        for(Cliente cliente : listaClientes) {
            if(cliente.getDni().equalsIgnoreCase(dniCliente)) {
                System.out.println(cliente);
                encontrado = true;
                break;
            }
        }
        if(!encontrado) {
            System.out.println("No se ha encontrado ningún cliente con dicho dni");
        }
    }

    public void modificarDatosCliente(){
        String dniCliente = leerTexto("Introduce el DNI del cliente a modificar: ");
        boolean encontrado = false;

        for(Cliente cliente : listaClientes) {
            if (cliente.getDni().equalsIgnoreCase(dniCliente)) {
                encontrado = true;
                String nuevoNombre = leerTexto("Nuevo nombre: ");
                String nuevosApellidos = leerTexto("Nuevos apellidos: ");
                String nuevoEmail = leerTexto("Nuevo email: ");

                if (!cliente.getEmail().equalsIgnoreCase(nuevoEmail) && existeEmail(nuevoEmail)) {
                    System.out.println("Ya existe otro cliente con ese email.");
                    return;
                }
                System.out.print("Nuevo teléfono: ");
                String nuevoTelefono = scanner.nextLine().trim();
                LocalDate nuevaFechaNacimiento = leerFecha("Nueva fecha de nacimiento (yyyy-MM-dd): ");
                cliente.setNombre(nuevoNombre);
                cliente.setApellidos(nuevosApellidos);
                cliente.setEmail(nuevoEmail);
                cliente.setTelefono(nuevoTelefono);
                cliente.setFechaNacimiento(nuevaFechaNacimiento);

                System.out.println("Cliente modificado correctamente");
                System.out.println(cliente);
                break;
            }
        }
        if(!encontrado) {
            System.out.println("No se ha encontrado ningún cliente con dicho dni");
        }
    }

    public void eliminarCliente(){
        String dniCliente = leerTexto("Introduce el DNI del cliente a eliminar: ");
        Cliente clienteAEliminar = null;

        for(Cliente cliente : listaClientes) {
            if (cliente.getDni().equalsIgnoreCase(dniCliente)) {
                clienteAEliminar = cliente;
                break;
            }
        }
        if(clienteAEliminar != null) {
            listaClientes.remove(clienteAEliminar);
            System.out.println("Cliente eliminado correctamente");
        } else {
            System.out.println("No se ha encontrado ningún cliente con dicho dni");
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

    private boolean existeDni(String dni) {
        for (Cliente cliente : listaClientes) {
            if (cliente.getDni().equalsIgnoreCase(dni)) {
                return true;
            }
        }
        return false;
    }

    private boolean existeEmail(String email) {
        for (Cliente cliente : listaClientes) {
            if (cliente.getEmail().equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }

    public List<Cliente> getListaClientes() {
        return listaClientes;
    }
}
