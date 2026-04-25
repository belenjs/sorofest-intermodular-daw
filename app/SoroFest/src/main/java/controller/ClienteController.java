package controller;

import model.Cliente;
import view.ClienteView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClienteController {
    private Scanner scanner;
    private ClienteView clienteView;
    private List<Cliente> listaClientes;

    public ClienteController(){
        this.scanner = new Scanner(System.in);
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

    public void darAltaCliente(){
        System.out.println("Introduce DNI: ");
        String dni = scanner.nextLine();
        System.out.println("Introduce nombre: ");
        String nombre = scanner.nextLine();
        System.out.println("Introduce apellidos: ");
        String apellidos = scanner.nextLine();
        System.out.println("Introduce email: ");
        String email = scanner.nextLine();
        System.out.println("Introduce teléfono: ");
        String telefono = scanner.nextLine();
        System.out.println("Introduce fecha de nacimiento (yyyy-MM-dd)");
        LocalDate fechaNacimiento = LocalDate.parse(scanner.nextLine());

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
}
