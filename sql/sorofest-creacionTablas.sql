-- CREAR BASE DE DATOS 

CREATE DATABASE SoroFest;
USE SoroFest;

-- 1) TABLA EDICION
CREATE TABLE Edicion (
  id_edicion INT AUTO_INCREMENT PRIMARY KEY,
  nombre_edicion VARCHAR(50) NOT NULL UNIQUE,
  fecha_inicio DATE NOT NULL,
  fecha_fin DATE NOT NULL CHECK (fecha_fin > fecha_inicio), 
  ciudad VARCHAR(50) NOT NULL,
  recinto VARCHAR(50) NOT NULL,
  precio_entrada DECIMAL(10,2) NOT NULL CHECK(precio_entrada > 0),
  stock_entradas INT NOT NULL CHECK(stock_entradas >= 0)
);

-- 2) TABLA ARTISTA
CREATE TABLE Artista (
  id_artista INT AUTO_INCREMENT PRIMARY KEY,
  nombre_artista VARCHAR(50) NOT NULL,
  tipo_artista VARCHAR(25) NOT NULL,
  genero_musical VARCHAR(25) NOT NULL,
  es_cabeza_cartel BOOLEAN NOT NULL DEFAULT FALSE,
  descripcion TEXT NOT NULL  
);

-- 3) TABLA CLIENTE
CREATE TABLE Cliente (
  id_cliente INT AUTO_INCREMENT PRIMARY KEY,
  dni VARCHAR(15) NOT NULL UNIQUE,
  nombre VARCHAR(50) NOT NULL,
  apellidos VARCHAR(80) NOT NULL,
  email VARCHAR(100) NOT NULL UNIQUE,
  telefono VARCHAR(25) NULL,
  fecha_nacimiento DATE NOT NULL
);

-- 4) TABLA COMPRA
CREATE TABLE Compra (
  id_compra INT AUTO_INCREMENT PRIMARY KEY,
  id_cliente INT NOT NULL,
  fecha_compra DATETIME NOT NULL,
  importe_total DECIMAL(10,2) NOT NULL CHECK (importe_total >= 0),
  metodo_pago VARCHAR(30) NOT NULL CHECK ((metodo_pago IN ('Tarjeta', 'Bizum', 'PayPal', 'Transferencia'))) ,

  CONSTRAINT fk_compra_cliente
    FOREIGN KEY (id_cliente) REFERENCES Cliente(id_cliente)
    ON UPDATE CASCADE
    ON DELETE RESTRICT
);

-- 5) TABLA CONCIERTO
CREATE TABLE Concierto (
  id_concierto INT AUTO_INCREMENT PRIMARY KEY,
  id_edicion INT NOT NULL,
  id_artista INT NOT NULL,
  fecha DATE NOT NULL,
  hora_inicio TIME NOT NULL,
  hora_fin TIME NOT NULL CHECK (hora_fin > hora_inicio), 

  CONSTRAINT fk_concierto_edicion
    FOREIGN KEY (id_edicion) REFERENCES Edicion(id_edicion)
    ON UPDATE CASCADE
    ON DELETE RESTRICT, 

  CONSTRAINT fk_concierto_artista
    FOREIGN KEY (id_artista) REFERENCES Artista(id_artista)
    ON UPDATE CASCADE
    ON DELETE RESTRICT
);

-- 6) TABLA ENTRADA
CREATE TABLE Entrada (
  id_entrada INT AUTO_INCREMENT PRIMARY KEY,
  id_edicion INT NOT NULL,
  id_compra INT NOT NULL,
  codigo_entrada VARCHAR(30) NOT NULL UNIQUE,
  
  CONSTRAINT fk_entrada_edicion
    FOREIGN KEY (id_edicion) REFERENCES Edicion(id_edicion)
    ON UPDATE CASCADE
    ON DELETE RESTRICT,
  
  CONSTRAINT fk_entrada_compra
    FOREIGN KEY (id_compra) REFERENCES Compra(id_compra)
    ON UPDATE CASCADE
    ON DELETE RESTRICT
);
