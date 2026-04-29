package database;

public interface SchemaBD {
    // TABLA CLIENTE
    String TAB_CLIENTE = "Cliente";
    String COL_ID_CLIENTE = "id_cliente";
    String COL_DNI = "dni";
    String COL_NOMBRE = "nombre";
    String COL_APELLIDOS = "apellidos";
    String COL_EMAIL = "email";
    String COL_TELEFONO = "telefono";
    String COL_FECHA_NACIMIENTO = "fecha_nacimiento";

    // TABLA COMPRA
    String TAB_COMPRA = "Compra";
    String COL_ID_COMPRA = "id_compra";
    String COL_ID_CLIENTE_FK = "id_cliente";
    String COL_FECHA_COMPRA = "fecha_compra";
    String COL_IMPORTE_TOTAL = "importe_total";
    String COL_METODO_PAGO = "metodo_pago";

}
