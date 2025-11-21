Proyecto de Gestión de Huertos (Arquitectura DDD)
1. Descripción y Arquitectura
Este proyecto implementa un sistema de gestión de huertos y propietarios utilizando una arquitectura basada en Domain-Driven Design (DDD). El objetivo es desacoplar la lógica de negocio de la persistencia de datos, garantizando un código modular y mantenible.

La estructura del proyecto se divide en capas por responsabilidad:

Capa de Dominio (src/dominio): Contiene la lógica de negocio pura y las definiciones de las entidades. Esta capa es agnóstica a la tecnología de almacenamiento.

Capa de Repositorios (src/repositorios): Implementa el Patrón Repositorio para abstraer el acceso a datos. Actúa como una colección en memoria para el dominio, ocultando la complejidad del almacenamiento físico.

Capa de Infraestructura (src/gestorCSV): Clases utilitarias encargadas de la lectura y escritura física en ficheros CSV.

2. Modelo de Dominio
El modelo se basa en la distinción clara entre Entidades y Objetos de Valor, cumpliendo con los principios DDD:

Entidades
Objetos definidos por su identidad única (ID), independientemente de sus atributos.

Persona (Root Entity): Representa al propietario.

Huerto: Entidad dependiente asociada a una persona mediante idPersona.

Relación 1:N: Una Persona puede poseer múltiples instancias de Huerto. Esta relación se persiste almacenando el ID del propietario en el huerto (referencia por identidad).

Objetos de Valor (Value Objects)
Objetos definidos por sus atributos y no por un ID. Son inmutables.

Tamanio: Encapsula la lógica de superficie (valor y unidad). Se garantiza su validez desde la construcción (validaciones en el constructor para evitar tamaños negativos o unidades vacías), asegurando que el dominio siempre se encuentre en un estado consistente.

3. Estrategia de Persistencia (File System)
El sistema utiliza ficheros de texto plano (.csv) para asegurar la persistencia de datos entre ejecuciones.

Gestión de Ficheros (GestorCSV)
Se ha implementado una clase utilitaria estática que maneja BufferedReader y BufferedWriter. Se utiliza la estructura try-with-resources de Java para garantizar el cierre seguro de flujos y evitar bloqueos o fugas de memoria.

Implementación del CRUD en Repositorios
Los repositorios (RepoPersona, RepoHuerto) gestionan la persistencia mediante una estrategia de sobrescritura completa para garantizar la integridad en ficheros secuenciales:

Lectura (findAll): El repositorio carga todas las líneas del CSV y mapea los datos a objetos de dominio (Persona, Huerto). En el caso de Huerto, se reconstruye el objeto de valor Tamanio a partir de las columnas correspondientes.

Escritura (save / deleteById):

Se cargan todos los registros en memoria.

Se realiza la modificación (añadir, actualizar o borrar) sobre la lista.

Se sobrescribe el fichero CSV completo con el nuevo estado de la lista.

Nota de diseño: Aunque en entornos de alta concurrencia se utilizarían bases de datos, para este volumen de datos la sobrescritura secuencial es una solución robusta y eficiente que simplifica la gestión de la consistencia.

4. Funcionalidades Extendidas (Búsqueda Semántica)
Además de las operaciones CRUD estándar (Buscar por ID, Guardar, Borrar), los repositorios incluyen métodos semánticos específicos del dominio de negocio:

RepoPersona.findByApellido(String apellido): Permite localizar usuarios por su apellido, facilitando búsquedas naturales donde no se conoce el ID.

RepoHuerto.findByCultivo(String cultivo): Permite filtrar los huertos según el tipo de plantación (ej. obtener todos los huertos de "Tomates").

5. Ejecución y Pruebas (Main)
La clase Main actúa como punto de entrada para verificar el flujo completo del sistema. Ejecuta la siguiente secuencia de validación:

Inicialización: Limpieza de entornos previos (deleteAll).

Persistencia: Creación y guardado de instancias de Persona y Huerto.

Integridad de Datos: Verificación de conteos (count) y recuperación por ID.

Consultas de Negocio: Ejecución de métodos de búsqueda semántica (findByApellido, findByCultivo).

Eliminación: Borrado de entidades y verificación de persistencia en disco.
