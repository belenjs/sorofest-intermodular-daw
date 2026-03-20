# Modelo de datos inicial de SoroFest

## Introducción

Este documento recoge la propuesta inicial del modelo de datos del proyecto **SoroFest**, un festival de música hecha por mujeres organizado por una empresa ficticia con enfoque cultural, inclusivo y orientado a la creación de comunidad.

El objetivo de esta base de datos es representar de forma coherente los elementos principales del festival: artistas, ediciones, escenarios, conciertos, tipos de entrada, clientes, compras, accesos y stands.

---

## Criterios de diseño

Para construir este modelo se han seguido estas ideas:

- reflejar el funcionamiento real del festival
- mantener una estructura clara y defendible para un proyecto de 1º DAW
- evitar entidades innecesariamente complejas
- separar bien los conceptos de edición, compra, tipo de entrada y acceso
- permitir que una compra pueda incluir varios tipos de entrada

Además, se ha decidido:
- mantener **Acceso** como entidad propia para representar el control real de entrada al festival
- recoger la idea de la entidad **TipoEntrada**
- modelar la relación entre **Compra** y **TipoEntrada** como **N:M**, resuelta mediante la entidad intermedia **DetalleCompra**
- añadir **Stand** como entidad para representar puestos o espacios de servicio dentro del recinto

---

# Entidades y atributos

## 1. Artista

Representa a cada cantante o banda participante en el festival.

**Atributos:**
- `id_artista` **PK**
- `nombre_artistico`
- `tipo_artista`
- `pais_origen`
- `genero_musical`
- `descripcion`
- `es_cabeza_cartel`

**Observación:**  
El atributo `tipo_artista` permite distinguir si la participante es una solista o una banda sin necesidad de crear subentidades.

---

## 2. EdicionFestival

Representa cada edición concreta de SoroFest.

**Atributos:**
- `id_edicion` **PK**
- `nombre_edicion`
- `ciudad`
- `fecha_inicio`
- `fecha_fin`
- `recinto`

**Observación:**  
Se mantiene el atributo `recinto` porque permite identificar el lugar general en el que se celebra una edición concreta del festival.

---

## 3. Escenario

Representa los escenarios o espacios donde se celebran los conciertos.

**Atributos:**
- `id_escenario` **PK**
- `nombre`
- `capacidad`
- `id_edicion` **FK**

**Observación:**  
Cada escenario pertenece a una edición concreta del festival.

---

## 4. Concierto

Representa una actuación concreta de una artista en una edición y escenario determinados.

**Atributos:**
- `id_concierto` **PK**
- `fecha`
- `hora_inicio`
- `hora_fin`
- `id_artista` **FK**
- `id_escenario` **FK**
- `id_edicion` **FK**

**Observación:**  
Se mantiene `id_edicion` en esta entidad para hacer más explícita la relación con la edición del festival y facilitar la interpretación del modelo.

---

## 5. TipoEntrada

Representa cada modalidad de entrada disponible para una edición del festival.

**Atributos:**
- `id_tipo_entrada` **PK**
- `nombre_tipo`
- `precio`
- `stock_disponible`
- `max_entradas_por_compra`
- `fecha_validez_inicio`
- `fecha_validez_fin`
- `descripcion`
- `id_edicion` **FK**

**Observación:**  
Esta entidad describe las modalidades de acceso ofrecidas por el festival, como por ejemplo entrada de un día, entrada VIP o abono completo.

El atributo `max_entradas_por_compra` indica cuántas entradas de ese tipo puede adquirir una persona en una sola operación.

---

## 6. Cliente

Representa a la persona que realiza compras en la plataforma del festival.

**Atributos:**
- `id_cliente` **PK**
- `nombre`
- `apellidos`
- `email`
- `telefono`
- `fecha_nacimiento`

---

## 7. Compra

Representa cada operación de compra realizada por un cliente.

**Atributos:**
- `id_compra` **PK**
- `fecha_compra`
- `importe_total`
- `metodo_pago`
- `id_cliente` **FK**

**Observación:**  
Se ha decidido que `Compra` tenga una clave primaria propia (`id_compra`) porque representa una operación real del sistema y no solo una relación entre entidades. Esto no rompe la 2FN, ya que al tener una clave primaria simple no existen dependencias parciales.

---

## 8. DetalleCompra

Representa cada línea de detalle dentro de una compra. Permite relacionar una compra con uno o varios tipos de entrada.

**Atributos:**
- `id_compra` **PK, FK**
- `id_tipo_entrada` **PK, FK**
- `cantidad`
- `subtotal`

**Observación:**  
Esta entidad intermedia resuelve la relación **N:M** entre `Compra` y `TipoEntrada`.

Gracias a ella, una misma compra puede incluir varios tipos de entrada distintos, y un tipo de entrada puede aparecer en muchas compras.
El subtotal representa la cantidad de entradas * precio de cada tipo de entrada que se refleja en esa línea de compra. 
Ambos atributos, cantidad y subtotal dependen de ambas PK, por lo que no rompe la regla de normalizacion 2FN.

---

## 9. Acceso

Representa el registro de validación de acceso al festival.

**Atributos:**
- `id_acceso` **PK**
- `fecha_acceso`
- `hora_acceso`
- `puerta_entrada`
- `tipo_acceso`
- `estado_acceso`
- `id_compra` **FK**
- `id_edicion` **FK**

**Observación:**  
`Acceso` se ha modelado como entidad propia porque representa un evento real del sistema: la validación o uso de una compra para entrar al recinto o a una zona concreta del festival.

No se ha modelado como relación con `Concierto`, porque en este proyecto se entiende como acceso general al festival y no como asistencia a una actuación concreta.

---

## 10. Stand

Representa un puesto o espacio de servicio dentro del festival.

**Atributos:**
- `id_stand` **PK**
- `nombre`
- `tipo_stand`
- `ubicacion`
- `horario_apertura`
- `horario_cierre`
- `id_edicion` **FK**

**Observación:**  
Esta entidad permite representar puestos de comida, merchandising, información u otros servicios presentes en una edición del festival.

---

# Relaciones y cardinalidades

## 1. EdicionFestival — Escenario

**Relación:** `1:N`

Una edición del festival puede tener varios escenarios.  
Cada escenario pertenece a una sola edición.

---

## 2. EdicionFestival — Concierto

**Relación:** `1:N`

Una edición puede incluir muchos conciertos.  
Cada concierto pertenece a una sola edición.

---

## 3. EdicionFestival — TipoEntrada

**Relación:** `1:N`

Una edición puede ofrecer varios tipos de entrada.  
Cada tipo de entrada pertenece a una sola edición.

---

## 4. EdicionFestival — Stand

**Relación:** `1:N`

Una edición puede tener varios stands.  
Cada stand pertenece a una sola edición.

---

## 5. EdicionFestival — Acceso

**Relación:** `1:N`

Una edición registra muchos accesos.  
Cada acceso pertenece a una sola edición.

---

## 6. Artista — Concierto

**Relación:** `1:N`

Una artista puede participar en varios conciertos.  
Cada concierto corresponde a una sola artista.

---

## 7. Escenario — Concierto

**Relación:** `1:N`

Un escenario puede albergar varios conciertos.  
Cada concierto se celebra en un solo escenario.

---

## 8. Cliente — Compra

**Relación:** `1:N`

Un cliente puede realizar varias compras.  
Cada compra pertenece a un solo cliente.

---

## 9. Compra — DetalleCompra

**Relación:** `1:N`

Una compra puede contener varias líneas de detalle.  
Cada línea de detalle pertenece a una sola compra.

---

## 10. TipoEntrada — DetalleCompra

**Relación:** `1:N`

Un tipo de entrada puede aparecer en muchas líneas de detalle.  
Cada línea de detalle corresponde a un solo tipo de entrada.

---

## 11. Compra — TipoEntrada

**Relación conceptual:** `N:M`

Una compra puede incluir varios tipos de entrada.  
Un tipo de entrada puede aparecer en muchas compras.

**Resolución:**  
Esta relación se resuelve mediante la entidad intermedia **DetalleCompra**.

---

## 12. Compra — Acceso

**Relación:** `1:N`

Una compra puede dar lugar a uno o varios accesos.  
Cada acceso queda asociado a una sola compra.

**Observación:**  
Esta relación permite modelar el uso posterior de la compra dentro del festival.

---

# Resumen del modelo

El modelo final de SoroFest queda compuesto por las siguientes entidades:

- `Artista`
- `EdicionFestival`
- `Escenario`
- `Concierto`
- `TipoEntrada`
- `Cliente`
- `Compra`
- `DetalleCompra`
- `Acceso`
- `Stand`

Este diseño permite representar:

- la organización de distintas ediciones del festival
- la programación musical y los escenarios
- los tipos de entrada disponibles
- las compras realizadas por clientes
- el detalle de cada compra
- el control de accesos al festival
- y los servicios o espacios complementarios del recinto

---

# Conclusión

La propuesta de modelo busca ser realista, clara y adecuada al alcance del proyecto. Se ha intentado equilibrar simplicidad y coherencia, evitando tanto un diseño demasiado pobre como una estructura excesivamente compleja para esta fase del desarrollo.

Este documento servirá como base para la elaboración posterior del diagrama E/R y del modelo relacional.