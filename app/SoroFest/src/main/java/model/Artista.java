package model;

public class Artista {
    private int idArtista;
    private String nombreArtista;
    private String tipoArtista;
    private String generoMusical;
    private boolean esCabezaCartel;
    private String descripcion;

    public Artista(){

    }

    public Artista(int idArtista, String nombreArtista, String tipoArtista, String generoMusical, boolean esCabezaCartel, String descripcion) {
        this.idArtista = idArtista;
        this.nombreArtista = nombreArtista;
        this.tipoArtista = tipoArtista;
        this.generoMusical = generoMusical;
        this.esCabezaCartel = esCabezaCartel;
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "\n--- ARTISTA ---" +
                "\nID Artista: " + idArtista +
                "\nNombre artístico: " + nombreArtista +
                "\nTipo de artista: " + tipoArtista +
                "\nGénero musical: " + generoMusical +
                "\n¿Es cabeza de cartel?: " + esCabezaCartel +
                "\nDescripción: " + descripcion;
    }

    public int getIdArtista() {
        return idArtista;
    }

    public void setIdArtista(int idArtista) {
        this.idArtista = idArtista;
    }

    public String getNombreArtista() {
        return nombreArtista;
    }

    public void setNombreArtista(String nombreArtista) {
        this.nombreArtista = nombreArtista;
    }

    public String getTipoArtista() {
        return tipoArtista;
    }

    public void setTipoArtista(String tipoArtista) {
        this.tipoArtista = tipoArtista;
    }

    public boolean isEsCabezaCartel() {
        return esCabezaCartel;
    }

    public void setEsCabezaCartel(boolean esCabezaCartel) {
        this.esCabezaCartel = esCabezaCartel;
    }

    public String getGeneroMusical() {
        return generoMusical;
    }

    public void setGeneroMusical(String generoMusical) {
        this.generoMusical = generoMusical;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
