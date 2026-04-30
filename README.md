# SoroFest – Proyecto Intermodular DAW

SoroFest es un proyecto intermodular de 1º de DAW centrado en el diseño y desarrollo de la web corporativa de un festival de música femenina organizado por una empresa ficticia.

  ## Descripción del proyecto

  SoroFest es un festival cultural que pone el talento femenino en el centro del escenario. 
  La propuesta nace con el objetivo de visibilizar a artistas mujeres, apoyar la diversidad en la industria musical y construir una comunidad alrededor de la música, la cultura y la inclusión.
  
  La web del proyecto funciona como plataforma oficial del festival, reuniendo en un solo lugar toda la información clave del evento: 
  cartel, artistas, programación, venta de entradas, información práctica, manifiesto y contenido relacionado con la experiencia del festival.


  ## Problema que resuelve
  
  El proyecto responde a una desigualdad histórica en la presencia de mujeres dentro de los grandes carteles musicales. 
  Muchas artistas emergentes o consolidadas no reciben la misma visibilidad que los grupos masculinos en festivales nacionales e internacionales.

  SoroFest plantea una alternativa cultural clara: un festival con identidad propia, enfoque inclusivo y compromiso con la representación femenina en la música.


  ## Público objetivo

  La web está dirigida principalmente a personas jóvenes y adultas interesadas en:
  - música y festivales
  - cultura contemporánea
  - propuestas inclusivas y con identidad
  - eventos que promueven la visibilidad femenina

  Los menores podrán asistir acompañados por personas adultas mayores de 18 años.


  ## Objetivos del proyecto

  - Diseñar un portal web corporativo coherente con la empresa ficticia
  - Modelar una base de datos relacionada con la organización del festival
  - Desarrollar una aplicación Java con JDBC para gestionar datos reales del negocio
  - Documentar el proyecto de forma profesional y estructurada
  - Utilizar Git y GitHub como sistema de control de versiones durante todo el desarrollo


  ## Tecnologías previstas

  - HTML5
  - CSS3
  - Java
  - JDBC
  - SQL
  - Git y GitHub
  - MySQL o MariaDB


  ## Estructura del repositorio

  - `/web` → portal web del festival
  - `/app` → aplicación Java para gestión interna
  - `/sql` → scripts de base de datos
  - `/diagrams` → diagramas E/R, clases y esquemas
  - `/docs` → documentación técnica y profesional por módulos


  # Secciones de la web

  - Inicio
  - Manifiesto
  - Cartel
  - Artistas
  - Horarios
  - Información práctica
  - Contacto
  - Entradas


  ## Estado actual

  Proyecto en fase inicial. Actualmente se ha definido la idea general, la identidad del festival y la estructura base del repositorio.


  ## Cómo visualizar el proyecto

  ### Parte web
  Abrir `web/index.html` en el navegador.

  ### Parte Java
  Entregado y finalizado módulo Java del proyecto, implementado como aplicación de consola para la gestión del festival.  Incluye gestión de:
  - edición
  - artistas
  - conciertos
  - clientes
  - compras
  - entradas
  La aplicación está estructurada en paquetes (`model`, `view`, `controller`, `dao`, `database`) y conectada a base de datos mediante JDBC.
  Para ejecutar el proyecto, lanzar la clase `Main.java`, teniendo previamente configurada la base de datos y la conexión en `ConexionBD.java`.

  ### Base de datos
  Entregado y finalizado parte de BBDD, organizado de tal manera:
  - /docs/bbdd: archivo analisis-datos.md donde se describe las decisiones tomadas para llevar a cabo la organizacion de la base de datos del festival.
  - /diagrams: donde se encuentran los diagramas entidad-relación y relacional.
  - /sql: donde se encuentran los scripts para la creación de bbdd y tablas, la inserción de datos y diferentes queries.

  ### MPO
  Arquitectura de la aplicación:
  Se trata de una aplicación manejada por consola y desarrollada en Java. La aplicación permite gestionar distintas entidades. El proyecto está organizado por capas y paquetes, separando responsabilidades:
  `model`:
  Contiene las clases del dominio de la aplicación, es decir, las entidades principales del sistema.
  ### `view`:
  Contiene las clases encargadas de mostrar los menús y la salida básica por consola. Su responsabilidad es únicamente la interacción visual con el usuario.
  `controller`:
  Contiene la lógica de la aplicación y el flujo de los menús. Los controladores coordinan:
  - la lectura de datos por consola
  - las validaciones
  - las operaciones del sistema
  - la comunicación con la capa DAO
  `dao`:
  Contiene las clases DAO (*Data Access Object*), encargadas del acceso a la base de datos.  Cada DAO gestiona las operaciones CRUD de una entidad:
  - insertar
  - obtener
  - actualizar
  - eliminar
  Además, algunos DAO incluyen comprobaciones de integridad, como verificar si una entidad tiene elementos asociados.
  `database`:
  Contiene la configuración de la base de datos:
  - `ConexionBD`, para gestionar la conexión JDBC con MySQL
  - `SchemaBD`, para centralizar los nombres de tablas y columnas
  ----
  Mejora estructural correspondiente a MPO:
  La mejora principal ha sido una **refactorización estructural** del programa en sí. Al principio el programa se basaba en listas en memoria y en lógica más simple y después se ha reorganizado para que siga estructura más clara y profesional. Las mejoras principales han sido:
  - separación del proyecto en capas (`model`, `view`, `controller`, `dao`, `database`)
  - sustitución de gran parte de la gestión en memoria por persistencia real en base de datos MySQL
  - uso de JDBC y `PreparedStatement`
  - creación de una clase DAO por entidad
  - separación entre lógica de presentación, lógica de control y acceso a datos
  - mejora de la mantenibilidad del código
  ----
  Buenas prácticas:
  Durante el desarrollo de la aplicación se han seguido buenas prácticas relacionadas con programación orientada a objetos y calidad del código:
  - encapsulación de atributos con getters y setters
  - constructores adecuados en las entidades
  - métodos con responsabilidad clara
  - separación de responsabilidades
  - validaciones de datos introducidos por consola
  - control de errores básicos en la interacción con base de datos
  - mejora de la legibilidad mediante `toString()` más claros
  - organización del código en paquetes con sentido
  ----
  Reglas de negocio:
  Se han añadido algunas reglas de negocio que dan coherencia a la aplicación:
  - no se puede eliminar un cliente si tiene compras asociadas
  - no se puede eliminar una artista si tiene conciertos asociados
  - no se puede eliminar una compra si tiene entradas asociadas
  - no se pueden generar entradas dos veces para la misma compra
  - validación de fechas, horas, cantidades e identificadores

  ## Módulos implicados

  - Entornos de Desarrollo
  - Bases de Datos
  - Lenguajes de Marcas y Sistemas de Gestión de Información
  - Programación
  - Sistemas Informáticos
  - MPO – Ampliación de Programación
  - Itinerario personal para la empleabilidad I

  ## Autoría

  Proyecto desarrollado por Belén Jiménez Sánchez.

