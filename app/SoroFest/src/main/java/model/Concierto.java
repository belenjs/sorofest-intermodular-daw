package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Concierto {
    private int idConcierto;
    private Edicion edicion;
    private Artista artista;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;

    public Concierto(){

    }

    public Concierto(int idConcierto, Edicion edicion, Artista artista, LocalDate fecha, LocalTime horaInicio, LocalTime horaFin) {
        this.idConcierto = idConcierto;
        this.edicion = edicion;
        this.artista = artista;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    @Override
    public String toString() {
        return "Concierto{" +
                "idConcierto=" + idConcierto +
                ", edicion=" + edicion.getNombreEdicion() +
                ", artista=" + artista.getNombreArtista() +
                ", fecha=" + fecha +
                ", horaInicio=" + horaInicio +
                ", horaFin=" + horaFin +
                '}';
    }

    public int getIdConcierto() {
        return idConcierto;
    }

    public void setIdConcierto(int idConcierto) {
        this.idConcierto = idConcierto;
    }

    public Edicion getEdicion() {
        return edicion;
    }

    public void setEdicion(Edicion edicion) {
        this.edicion = edicion;
    }

    public Artista getArtista() {
        return artista;
    }

    public void setArtista(Artista artista) {
        this.artista = artista;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }
}
