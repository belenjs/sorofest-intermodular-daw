-- INSERCIÓN DE QUERIES

-- 1) SELECT
-- 1.1 Mostrar todos los clientes
    SELECT * 
    FROM Cliente;

-- 1.2 Mostrar todos los artistas
    SELECT * 
    FROM Artista;

-- 1.3 Mostrar todas las compras
    SELECT * 
    FROM Compra;

-- 1.4 Mostrar todas las entradas vendidas
    SELECT * 
    FROM Entrada;


-- 2) WHERE
-- 2.1 Mostrar compras superiores a 80€
    SELECT *
    FROM Compra 
    WHERE importe_total > 80;

-- 2.2 Mostrar compras hechas por 'PayPal'
    SELECT * 
    FROM Compra 
    WHERE metodo_pago = 'PayPal';

-- 2.3 Mostrar clientes con apellido 'Ruíz'
    SELECT * 
    FROM Cliente 
    WHERE apellidos LIKE '%Ruiz%';

-- 2.4 Mostrar clientes nacidos a partir del 2000
    SELECT * 
    FROM Cliente 
    WHERE fecha_nacimiento >= '2000-01-01';

-- 2.5 Mostrar todas las artistas que son cabeza de cartel
    SELECT * 
    FROM Artista 
    WHERE es_cabeza_cartel = 1;


-- 3) GROUP BY / HAVING / COUNT / JOIN / ORDER BY
-- 3.1 Mostrar todos los clientes con más de una entrada comprada
    SELECT 
      c.id_cliente,
      c.nombre,
      c.apellidos,
      COUNT(e.id_entrada) AS total_entradas
    FROM Cliente c
    INNER JOIN Compra co 
    ON c.id_cliente = co.id_cliente
    INNER JOIN Entrada e 
    ON co.id_compra = e.id_compra
    GROUP BY c.id_cliente, c.nombre, c.apellidos
    HAVING COUNT(e.id_entrada) > 1;

-- 3.2 Mostrar los clientes que más han gastado ordenados de manera descendente:
    SELECT 
      c.id_cliente,
      c.nombre,
      c.apellidos,
      SUM(co.importe_total) AS total_gastado
    FROM Cliente c
    INNER JOIN Compra co 
    ON c.id_cliente = co.id_cliente
    GROUP BY c.id_cliente, c.nombre, c.apellidos
    ORDER BY total_gastado DESC;

-- 3.3 Recaudación por método de pago
    SELECT 
      metodo_pago,
      SUM(importe_total) AS total_recaudado
    FROM Compra
    GROUP BY metodo_pago;

-- 3.4 Clientes que han gastado más de 80€ en total
    SELECT 
      c.id_cliente,
      c.nombre,
      c.apellidos,
      SUM(co.importe_total) AS total_gastado
    FROM Cliente c
    INNER JOIN Compra co ON c.id_cliente = co.id_cliente
    GROUP BY c.id_cliente, c.nombre, c.apellidos
    HAVING SUM(co.importe_total) > 80;

-- 3.5 Métodos de pago usados más de una vez
    SELECT 
      metodo_pago,
      COUNT(*) AS total_compras
    FROM Compra
    GROUP BY metodo_pago
    HAVING COUNT(*) > 1;


-- 4) ORDER BY
-- 4.1 Mostrar artistas por nombre en orden alfabético
    SELECT * 
    FROM Artista 
    ORDER BY nombre_artista ASC;

-- 4.2 Mostrar conciertos que se celebran a las 20:30 ordenados por fecha
    SELECT *
    FROM Concierto
    WHERE hora_inicio = '20:30:00'
    ORDER BY fecha ASC;


-- 5) JOIN
-- 5.1 Mostrar todas las artistas que tocan el primer día
    SELECT a.nombre_artista, c.fecha, c.hora_inicio, c.hora_fin
    FROM Artista a
    INNER JOIN Concierto c 
    ON a.id_artista = c.id_artista
    WHERE c.fecha = '2026-07-17';   

-- 5.2 Mostrar todas las entradas vendidas asociadas a su compra
    SELECT e.id_entrada, e.codigo_entrada ,co.id_compra, co.fecha_compra 
    FROM Entrada e 
    INNER JOIN Compra co 
    ON e.id_compra = co.id_compra;


-- 6) SUM / AVG / DISTINCT
-- 6.1 Mostrar total recaudado
    SELECT SUM(importe_total) AS total_recaudado
    FROM Compra;

-- 6.2 Mostrar el importe medio de las compras realizadas
    SELECT AVG(importe_total) AS media_compras
    FROM Compra;

-- 6.3 Mostrar los distintos géneros musicales del festival
    SELECT DISTINCT genero_musical
    FROM Artista;

