# Diseño del sistema – Fase 1  
## Proyecto: SoroFest

## 1. Tipo de sistema donde se ejecuta

El proyecto SoroFest está planteado para ejecutarse en un **PC de usuario**, ya sea un ordenador de sobremesa o un portátil de uso personal, ya que el alcance actual del proyecto está centrado en una aplicación sencilla que funciona en entorno local.

Esta decisión se justifica porque SoroFest es, en esta fase, una web informativa con interacción básica por parte del usuario, apoyada en una base de datos local. Por tanto, un equipo personal estándar es suficiente tanto para el desarrollo como para la ejecución y prueba del proyecto. No se contempla su ejecución directa en dispositivos móviles, ya que la aplicación no está desplegada en un servidor externo y depende de un entorno de desarrollo local.

## 2. Sistema operativo recomendado

El proyecto puede ejecutarse en distintos sistemas operativos, siempre que permitan usar un navegador web actualizado, un entorno de desarrollo compatible y una base de datos local.

Se considera compatible con:
- macOS
- Windows
- Linux

Aun así, el desarrollo del proyecto se ha realizado en **macOS**, por lo que ese es el entorno en el que se ha probado principalmente. Esto no impide que pueda adaptarse a otros sistemas operativos, siempre que cuenten con las herramientas necesarias.

### Sistema operativo principal recomendado
macOS, por ser el sistema utilizado durante el desarrollo del proyecto.

### Alternativas válidas
Windows 10/11 o una distribución Linux actual.

### Justificación
Se recomienda macOS porque ha sido el entorno real de trabajo durante el desarrollo, lo que facilita la instalación, prueba y depuración del proyecto en condiciones conocidas. Sin embargo, como la aplicación se apoya en tecnologías habituales y multiplataforma, también puede ejecutarse en otros sistemas operativos de uso común.

## 3. Recursos mínimos y recomendados

Como se trata de una aplicación sencilla que combina una web, una base de datos local y un entorno de desarrollo, no requiere un hardware especialmente potente. Aun así, conviene diferenciar entre unos requisitos mínimos y otros recomendados para trabajar de forma más fluida.

### Recursos mínimos
- **CPU:** procesador de 2 núcleos
- **RAM:** 4 GB
- **Almacenamiento disponible:** 10 GB
- **Pantalla:** resolución mínima de 1366 x 768
- **Conexión a Internet:** recomendable para descargas, actualizaciones y uso de documentación
- **Periféricos:** teclado y ratón o trackpad

### Recursos recomendados
- **CPU:** procesador de 4 núcleos o superior
- **RAM:** 8 GB o más
- **Almacenamiento disponible:** 20 GB o más, preferiblemente en SSD
- **Pantalla:** resolución Full HD o superior
- **Conexión a Internet:** estable
- **Periféricos:** teclado y ratón o trackpad

### Justificación de los recursos
Los requisitos mínimos permitirían abrir el navegador, trabajar con la base de datos y ejecutar el entorno de desarrollo básico. Sin embargo, los recursos recomendados mejoran considerablemente la experiencia de uso, especialmente al trabajar con varias herramientas abiertas al mismo tiempo, como el navegador, el editor de código, el gestor de base de datos y otros programas auxiliares.

## 4. Entorno de ejecución

El proyecto SoroFest se ejecutará en un **entorno local**, dentro de un **equipo personal**. El acceso a la aplicación se realizará mediante **navegador web**, y la información se apoyará en una **base de datos local**.

Esto significa que:
- la aplicación no dependerá de un servidor remoto en esta fase
- el usuario trabajará desde su propio equipo
- la web se visualizará localmente en el navegador
- la base de datos estará instalada y disponible en el mismo entorno de trabajo

Este enfoque es coherente con el alcance actual del proyecto, ya que permite desarrollar, probar y demostrar el funcionamiento de SoroFest de manera controlada y sencilla, sin necesidad de desplegar todavía una infraestructura externa.

## 5. Conclusión

En esta fase inicial, SoroFest se plantea como un proyecto que puede ejecutarse correctamente en un PC de usuario estándar, en entorno local, mediante navegador y con base de datos local. El desarrollo se ha realizado en macOS, aunque el proyecto puede adaptarse a otros sistemas operativos habituales. Los recursos necesarios no son elevados, por lo que el sistema propuesto resulta adecuado y coherente con las características y necesidades reales de la aplicación.