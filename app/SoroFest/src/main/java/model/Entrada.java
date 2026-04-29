package model;

public class Entrada {
    private int idEntrada;
    private Edicion edicion;
    private Compra compra;
    private String codigoEntrada;

    public Entrada(){

    }

    public Entrada(int idEntrada, Edicion edicion, Compra compra, String codigoEntrada) {
        this.idEntrada = idEntrada;
        this.edicion = edicion;
        this.compra = compra;
        this.codigoEntrada = codigoEntrada;
    }

    @Override
    public String toString() {
        return "\n--- Entrada ---" +
                "\nID Entrada: " + idEntrada +
                "\nEdición: " + edicion.getNombreEdicion() +
                "\nID Compra: " + compra.getIdCompra() +
                "\nCliente: " + compra.getCliente().getNombre() + " " + compra.getCliente().getApellidos() +
                "\nCódigo entrada: " + codigoEntrada;
    }

    public int getIdEntrada() {
        return idEntrada;
    }

    public void setIdEntrada(int idEntrada) {
        this.idEntrada = idEntrada;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public Edicion getEdicion() {
        return edicion;
    }

    public void setEdicion(Edicion edicion) {
        this.edicion = edicion;
    }

    public String getCodigoEntrada() {
        return codigoEntrada;
    }

    public void setCodigoEntrada(String codigoEntrada) {
        this.codigoEntrada = codigoEntrada;
    }
}
