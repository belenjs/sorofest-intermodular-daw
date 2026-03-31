-- INSERCIÓN DE DATOS

-- TABLA EDICION
INSERT INTO Edicion (
  nombre_edicion,
  fecha_inicio,
  fecha_fin,
  ciudad,
  recinto,
  precio_entrada,
  stock_entradas
) VALUES (
  'SoroFest Cádiz 2026',
  '2026-07-17',
  '2026-07-19',
  'Cádiz',
  'Muelle Reina Victoria',
  40.00,
  1000
);

-- TABLA ARTISTA
INSERT INTO Artista (nombre_artista, tipo_artista, genero_musical, es_cabeza_cartel, descripcion) VALUES
('Casandra en Llamas', 'Dúo', 'Pop folk', TRUE, 'Dúo con raíces tradicionales reinterpretadas con una estética moderna y una fuerte carga emocional.'),
('Las Bayas Ácidas', 'Banda', 'Indie pop', FALSE, 'Grupo con actitud desenfadada, melodías pegadizas y letras irónicas sobre la vida cotidiana.'),
('Paula Matices', 'Solista', 'Pop', FALSE, 'Cantautora con sensibilidad pop y letras introspectivas que conectan con una audiencia joven.'),
('Niña Katana', 'Solista', 'Pop rock', FALSE, 'Artista con carácter fuerte que fusiona pop alternativo con tintes electrónicos y rock.'),
('Las Píldoras Rosas', 'Dúo', 'Indie', FALSE, 'Dúo indie con sonido suave, estética nostálgica y composiciones delicadas.'),
('Doña Bandolina', 'Solista', 'Pop electrónico', TRUE, 'Figura excéntrica del pop con letras provocadoras y una puesta en escena muy teatral.'),
('Bruma Digital', 'Solista', 'Bedroom pop', FALSE, 'Proyecto íntimo con producción casera, sonidos envolventes y atmósferas relajadas.'),
('Edea', 'Solista', 'Indie folk', FALSE, 'Cantautora de estilo minimalista con letras poéticas y un enfoque emocional.'),
('Ama IA', 'Solista', 'Pop indie', FALSE, 'Artista versátil con gran presencia escénica y una mezcla de sonidos modernos y clásicos.'),
('Neón 9', 'Solista', 'Electropop', FALSE, 'Proyecto electrónico con estética futurista, ritmos bailables y una identidad sonora muy marcada.'),
('Voltaje Emocional', 'Solista', 'Indie pop', FALSE, 'Artista con narrativa intensa y letras emocionales que exploran la vulnerabilidad y la identidad.'),
('Zafira', 'Solista', 'Pop alternativo', TRUE, 'Cantautora con evolución sonora constante, combinando electrónica, pop y letras profundas.');

-- TABLA CLIENTES
INSERT INTO Cliente (
  dni,
  nombre,
  apellidos,
  email,
  telefono,
  fecha_nacimiento
) VALUES
('45872136A', 'Lucía', 'Martín Romero', 'lucia.martin@example.com', '612345678', '1998-04-12'),
('38291475B', 'Alejandro', 'Sánchez Ruiz', 'alejandro.sanchez@example.com', '645789123', '1987-09-23'),
('51928364C', 'Marta', 'Gómez Navarro', 'marta.gomez@example.com', '678234561', '2005-01-17'),
('60481729D', 'Nuria', 'Pérez Castillo', 'nuria.perez@example.com', '699112233', '1999-08-05'),
('73194582E', 'Carmen', 'Ruiz Delgado', 'carmen.ruiz@example.com', '655443322', '1982-11-30'),
('29481736F', 'Hugo', 'Fernández Gómez', 'hugo.fernandez@example.com', NULL, '1995-06-14'),
('48392017G', 'Elena', 'Torres Medina', 'elena.torres@example.com', '622334455', '1993-03-08'),
('57283910H', 'Javier', 'Ortega Campos', 'javier.ortega@example.com', '611223344', '2001-12-19'),
('68123495J', 'Sofía', 'Vega Molina', 'sofia.vega@example.com', '688776655', '1991-02-27'),
('39012847K', 'Ariadne', 'Castro Jiménez', 'ariadne.castro@example.com', '633221100', '1982-07-09');

-- TABLA COMPRA:
INSERT INTO Compra (
  id_cliente,
  fecha_compra,
  importe_total,
  metodo_pago
) VALUES
(1, '2026-05-10 18:35:00', 80.00, 'Tarjeta'),
(2, '2026-05-11 12:20:00', 40.00, 'Bizum'),
(3, '2026-05-12 19:10:00', 80.00, 'PayPal'),
(5, '2026-05-13 17:45:00', 120.00, 'Tarjeta'),
(6, '2026-05-14 11:30:00', 40.00, 'Transferencia'),
(7, '2026-05-15 20:05:00', 80.00, 'Bizum'),
(8, '2026-05-16 16:50:00', 120.00, 'Tarjeta'),
(9, '2026-05-17 13:15:00', 40.00, 'PayPal'),
(10, '2026-05-18 18:00:00', 80.00, 'Bizum');

-- TABLA CONCIERTO
INSERT INTO Concierto (id_edicion, id_artista, fecha, hora_inicio, hora_fin) VALUES
(1, 7,  '2026-07-17', '18:00:00', '19:00:00'),
(1, 5,  '2026-07-17', '19:15:00', '20:15:00'),
(1, 3,  '2026-07-17', '20:30:00', '21:30:00'),
(1, 1,  '2026-07-17', '22:00:00', '23:30:00'),

(1, 8,  '2026-07-18', '18:00:00', '19:00:00'),
(1, 2,  '2026-07-18', '19:15:00', '20:15:00'),
(1, 10, '2026-07-18', '20:30:00', '21:30:00'),
(1, 6,  '2026-07-18', '22:00:00', '23:30:00'),

(1, 11, '2026-07-19', '17:30:00', '18:30:00'),
(1, 4,  '2026-07-19', '18:45:00', '19:45:00'),
(1, 9,  '2026-07-19', '20:00:00', '21:00:00'),
(1, 12, '2026-07-19', '22:00:00', '23:30:00');

-- TABLA ENTRADA
INSERT INTO Entrada (
  id_edicion,
  id_compra,
  codigo_entrada
) VALUES
(1, 1, 'SORO2026-0001'),
(1, 1, 'SORO2026-0002'),

(1, 2, 'SORO2026-0003'),

(1, 3, 'SORO2026-0004'),
(1, 3, 'SORO2026-0005'),

(1, 4, 'SORO2026-0006'),
(1, 4, 'SORO2026-0007'),
(1, 4, 'SORO2026-0008'),

(1, 5, 'SORO2026-0009'),

(1, 6, 'SORO2026-0010'),
(1, 6, 'SORO2026-0011'),

(1, 7, 'SORO2026-0012'),
(1, 7, 'SORO2026-0013'),
(1, 7, 'SORO2026-0014'),

(1, 8, 'SORO2026-0015'),

(1, 9, 'SORO2026-0016'),
(1, 9, 'SORO2026-0017');
