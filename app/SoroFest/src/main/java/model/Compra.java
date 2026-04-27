package model;

import java.time.LocalDateTime;

public class Compra {
    private int idCompra;
    private Cliente cliente;
    private LocalDateTime fechaCompra;
    private double importeTotal;
    private String metodoPago;

    public Compra(){

    }

    public Compra(int idCompra, Cliente cliente, LocalDateTime fechaCompra, double importeTotal, String metodoPago) {
        this.idCompra = idCompra;
        this.cliente = cliente;
        this.fechaCompra = fechaCompra;
        this.importeTotal = importeTotal;
        this.metodoPago = metodoPago;
    }

    @Override
    public String toString() {
        return "Compra{" +
                "idCompra=" + idCompra +
                "idCliente=" + cliente.getIdCliente() +
                ", cliente=" + cliente.getNombre() + " " + cliente.getApellidos() +
                ", fechaCompra=" + fechaCompra +
                ", importeTotal=" + importeTotal +
                ", metodoPago='" + metodoPago + '\'' +
                '}';
    }

    public int getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public LocalDateTime getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(LocalDateTime fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public double getImporteTotal() {
        return importeTotal;
    }

    public void setImporteTotal(double importeTotal) {
        this.importeTotal = importeTotal;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }
}
