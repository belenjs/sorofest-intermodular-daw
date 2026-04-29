package model;

import java.time.LocalDate;

public class Edicion {
    private int idEdicion;
    private String nombreEdicion;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String ciudad;
    private String recinto;
    private double precioEntrada;
    private int stockEntradas;

    public Edicion(){

    }

    public Edicion(int idEdicion, String nombreEdicion, LocalDate fechaInicio, LocalDate fechaFin, String ciudad, String recinto, double precioEntrada, int stockEntradas) {
        this.idEdicion = idEdicion;
        this.nombreEdicion = nombreEdicion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.ciudad = ciudad;
        this.recinto = recinto;
        this.precioEntrada = precioEntrada;
        this.stockEntradas = stockEntradas;
    }

    @Override
    public String toString() {
        return "\n--- EDICIÓN ---" +
                "\nID Edición: " + idEdicion +
                "\nNombre edición: " + nombreEdicion +
                "\nFecha inicio: " + fechaFin +
                "\nFecha fin: " + fechaFin +
                "\nCiudad: " + ciudad +
                "\nRecinto: " + recinto +
                "\nPrecio entradas: " + precioEntrada +
                "\nStock entradas: " + stockEntradas;
    }

    public int getIdEdicion() {
        return idEdicion;
    }

    public void setIdEdicion(int idEdicion) {
        this.idEdicion = idEdicion;
    }

    public String getNombreEdicion() {
        return nombreEdicion;
    }

    public void setNombreEdicion(String nombreEdicion) {
        this.nombreEdicion = nombreEdicion;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getRecinto() {
        return recinto;
    }

    public void setRecinto(String recinto) {
        this.recinto = recinto;
    }

    public double getPrecioEntrada() {
        return precioEntrada;
    }

    public void setPrecioEntrada(double precioEntrada) {
        this.precioEntrada = precioEntrada;
    }

    public int getStockEntradas() {
        return stockEntradas;
    }

    public void setStockEntradas(int stockEntradas) {
        this.stockEntradas = stockEntradas;
    }
}
