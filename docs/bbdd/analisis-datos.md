### ANALISIS DE DATOS - BBDD

## Presentación del proyecto
    Este documento recoge el análisis de datos del proyecto “SoroFest”, una propuesta web centrada en la difusión y gestión básica de un festival de música femenina. El objetivo de este análisis es identificar qué información debe almacenarse en la base de datos para dar soporte a las funcionalidades principales de la web, así como definir de forma coherente las entidades, atributos y relaciones necesarias.      

    En esta fase del proyecto se ha optado por un enfoque simplificado y ajustado al alcance real de la aplicación. Por ello, el modelo de datos se ha centrado únicamente en los elementos que intervienen directamente en la estructura de la web y en la interacción básica del usuario: la información de la edición del festival, los conciertos programados, las artistas participantes y el proceso de compra de entradas.

    La intención no ha sido diseñar una base de datos excesivamente compleja, sino construir una estructura clara, funcional y defendible, que represente correctamente las necesidades del proyecto y permita su posterior traducción al modelo relacional y a la implementación en base de datos.

## Objetivo del modelo de datos
    El modelo de datos diseñado tiene como finalidad:
        - almacenar la información de los clientes que compran entradas
        - registrar las compras realizadas por cada cliente
        - representar cada entrada de forma individual
        - gestionar la información de cada edición del festival
        - almacenar los conciertos programados en cada edición
        - registrar las artistas participantes en el festival


## Resumen de las decisiones finales

    Tras revisar y simplificar el planteamiento inicial del proyecto, se decidió mantener únicamente las entidades esenciales para el funcionamiento de la web. De este modo, el modelo final queda formado por las entidades:
        - Cliente
        - Compra
        - Entrada 
        - Edición
        - Concierto 
        - Artista

    1. Separación entre compra y entrada:
    Una de las decisiones más importantes fue simplificar la parte de la compra de entradas, al redefinir el alcance del proyecto, se optó por trabajar con una única entidad Entrada, entendida como el producto que la web ofrece al usuario. Esto ha permitido eliminar estructuras más avanzadas y dejar un sistema de compra más sencillo y coherente con el objetivo del proyecto.

    También se decidió que la entidad Compra representase una operación completa realizada por un cliente, por lo que cuenta con identificador propio, mientras que una entrada representa cada ticket individual adquirido dentro de esa compra.

    Esta separación permite reflejar correctamente que:
        - un cliente puede realizar varias compras
        - una compra puede incluir varias entradas
        - cada entrada debe tener un identificador único propio

    2. Un único tipo de entrada
    Se ha decidido que, en esta primera versión del proyecto, solo exista un único tipo de entrada: entrada general.
    Debido a ello, no se ha creado una entidad adicional para tipos de entrada. En lugar de eso, el precio y el stock se han asociado directamente a la entidad Edición, ya que son datos generales de la edición del festival.

    3. El stock y el precio pertenecen a la edición
    Como solo existe una entrada general para cada edición, se ha decidido almacenar en Edición los atributos:
        - precio_entrada
        - stock_entradas

    La razón es que estos valores no dependen de cada entrada individual, sino que son características generales de la edición.

    4. No se contempla pasarela de pago
    En cuanto al proceso de pago, no se va a implementar una pasarela de pago real. Aun así, se ha mantenido el atributo “método de pago” dentro de la entidad Compra para simular el proceso de compra en la web y hacer la interacción del usuario más realista.

    5. Relación Artista y Concierto
    Por otro lado, la relación entre Artista y Concierto se ha definido como 1:N, ya que una artista puede participar en varios conciertos, mientras que cada concierto se asocia a una sola artista. 

    6. Relación Edición y Concierto
    Asimismo, la relación entre Edición y Concierto también se ha establecido como 1:N, puesto que en la edición se incluyen varios conciertos programados, y cada concierto pertenecen a esta primera edición del festival.

    En definitiva, las decisiones adoptadas buscan mantener un equilibrio entre simplicidad y funcionalidad, de forma que la base de datos responda al alcance real del proyecto y permita representar correctamente la información necesaria para la web de “SoroFest”.



## Tablas finales de la BBDD de SoroFest
    1. CLIENTE: Representa a la persona usuaria que realiza una compra en la web.
       Atributos:
        id_cliente (PK)
        nombre
        apellidos
        dni
        email
        telefono
        fecha_nacimiento

    2. COMPRA: Representa la operación de compra realizada por un cliente.
       Atributos:
        d_compra (PK)
        fecha_compra
        importe_total
        metodo_pago
        id_cliente (FK)

    3. ENTRADA: Representa la entrada que se vende en la web para una edición del festival.
       Atributos:
        id_entrada (PK)
        codigo_entrada
        id_edicion (FK)
        id_compra (FK)

    4. EDICION: Representa cada edición concreta del festival SoroFest.
       Atributos:
        id_edicion (PK)
        nombre_edicion
        ciudad
        fecha_inicio
        fecha_fin
        recinto
        precio_entrada
        stock_entradas

    5. CONCIERTO: Representa cada actuación concreta programada en la edición.
       Atributos:
        id_concierto (PK)
        fecha
        hora_inicio
        hora_fin
        id_edicion (FK)
        id_artista (FK)

    6. ARTISTA: Representa a cada cantante o banda que aparece en la web y participa en el  festival.
      Atributos:
        id_artista (PK)
        nombre_artista
        tipo_artista
        genero_musical
        descripcion
        es_cabeza_cartel



## Relaciones y cordialidades entre entidades

    A partir del análisis funcional del sistema, se han establecido las siguientes relaciones:
    CLIENTE — COMPRA: Un cliente puede realizar varias compras, pero cada compra pertenece a un único cliente.
    Cardinalidad: CLIENTE 1 : N COMPRA

    COMPRA — ENTRADA: Una compra puede incluir varias entradas, pero cada entrada pertenece a una única compra.
    Cardinalidad: COMPRA 1 : N ENTRADA

    EDICIÓN — ENTRADA: Una edición puede tener muchas entradas vendidas, pero cada entrada corresponde a una única edición.
    Cardinalidad: EDICIÓN 1 : N ENTRADA

    EDICIÓN — CONCIERTO: Una edición del festival puede ofrecer varios conciertos, pero cada concierto pertenece a una sola edición.
    Cardinalidad: EDICIÓN 1 : N CONCIERTO

    ARTISTA — CONCIERTO: Una artista puede participar en varios conciertos, pero cada concierto corresponde a una sola artista.
    Cardinalidad: ARTISTA 1 : N CONCIERTO

## Reglas de negocio
    - Identificación única de entradas: cada entrada dispone de:
        - un id_entrada único como clave primaria
        - un codigo_entrada único como identificador lógico

    - Límite de entradas por compra: se define como regla de negocio que un cliente puede comprar un máximo de 3 entradas por compra, pero puede realizar varias compras.
    
    - Control del stock: se establece que cada edición dispone de un número limitado de entradas, fijado inicialmente en 1000.

