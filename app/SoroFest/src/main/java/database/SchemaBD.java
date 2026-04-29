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

    // TABLA EDICION
    String TAB_EDICION = "Edicion";
    String COL_ID_EDICION = "id_edicion";
    String COL_NOMBRE_EDICION = "nombre_edicion";
    String COL_FECHA_INICIO = "fecha_inicio";
    String COL_FECHA_FIN = "fecha_fin";
    String COL_CIUDAD = "ciudad";
    String COL_RECINTO = "recinto";
    String COL_PRECIO_ENTRADA = "precio_entrada";
    String COL_STOCK_ENTRADAS = "stock_entradas";

    // TABLA COMPRA
    String TAB_COMPRA = "Compra";
    String COL_ID_COMPRA = "id_compra";
    String COL_ID_CLIENTE_FK = "id_cliente";
    String COL_FECHA_COMPRA = "fecha_compra";
    String COL_IMPORTE_TOTAL = "importe_total";
    String COL_METODO_PAGO = "metodo_pago";

    // TABLA ENTRADA
    String TAB_ENTRADA = "Entrada";
    String COL_ID_ENTRADA = "id_entrada";
    String COL_ID_EDICION_ENTRADA_FK = "id_edicion";
    String COL_ID_COMPRA_FK = "id_compra";
    String COL_CODIGO_ENTRADA = "codigo_entrada";
}

