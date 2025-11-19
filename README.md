# 🛡️ GUÍA DE DEFENSA - PRÁCTICA 1: ACCESO A DATOS (DDD & REPOSITORIOS)

## 1. Conceptos Clave (Teoría DDD)

### ¿Qué arquitectura habéis usado?
Hemos utilizado una arquitectura basada en **DDD (Domain-Driven Design)** separando las capas por responsabilidad:
* **Dominio (`src/dominio`):** Contiene la lógica de negocio pura. No sabe nada de ficheros ni de CSVs.
* **Repositorios (`src/repositorios`):** Implementan el patrón repositorio para acceder a los datos.
* **Infraestructura (`src/gestorCSV`):** Clase utilitaria para el manejo físico de ficheros.

### Diferencia entre Entidad y Objeto Valor
* **Entidad (`Persona`, `Huerto`):** Son objetos definidos por su **identidad** (tienen un ID único). Aunque dos personas se llamen igual, si tienen distinto ID, son distintas.
* **Objeto Valor (`Tamanio`):** Son objetos definidos por sus **atributos**. No tienen ID. Si dos tamaños son `50 m2`, son el mismo valor. Además, son inmutables (sus campos son `final`).

### ¿Qué es el Patrón Repositorio?
Es un patrón que abstrae la capa de persistencia. El dominio "pide" guardar o buscar objetos, y al repositorio no le importa si por debajo hay una base de datos SQL, un fichero CSV o una API. En este caso, usamos CSV.

---

## 2. Estructura del Código y Relaciones

### Las Entidades (Justificación de "Al menos dos")
El enunciado pedía "al menos dos clases de entidades con relación uno a muchos".
1.  **`Persona`:** La entidad fuerte (Raíz).
2.  **`Huerto`:** La entidad que depende de la persona.
* **Relación 1:N:** Una Persona puede tener muchos Huertos. En código, esto se refleja guardando el `idPersona` dentro de la clase `Huerto` (como una Foreign Key en BBDD).

### Manejo de Ficheros (`GestorCSV`)
* Hemos creado una clase estática para reutilizar el código de lectura/escritura.
* Usamos `BufferedReader` y `BufferedWriter` envueltos en un `try-with-resources` para asegurar que el fichero **siempre se cierra**, incluso si hay errores, evitando fugas de memoria o bloqueos del archivo.

---

## 3. Explicación de los Métodos del Repositorio

### `findAll()` (Lectura)
1.  El `GestorCSV` lee todas las líneas del fichero como texto.
2.  El Repositorio recorre esas líneas y las **mapea** (convierte): rompe la cadena por las comas (`split(",")`) y hace un `new Persona(...)` o `new Huerto(...)`.
    * *Detalle Huerto:* Al leer un huerto, también reconstruimos su Objeto Valor `Tamanio` leyendo las columnas correspondientes.

### `save(Entidad)` (Escritura/Actualización)
Nuestra estrategia es **sobrescritura completa** (sencilla y robusta para ficheros pequeños):
1.  Cargamos **todos** los datos en memoria (`findAll`).
2.  Si la entidad ya existe (mismo ID), la borramos de la lista (`removeIf`).
3.  Añadimos la nueva versión a la lista.
4.  Sobrescribimos el fichero entero con la nueva lista.

### `deleteById(ID)`
Similar al `save`: Carga todo -> Borra el que coincide con el ID -> Reescribe todo el fichero.

---

## 4. Métodos Semánticos (Requisito Clave)

El enunciado exigía "al menos un método propio que pertenezca semánticamente a dicho repositorio".

* **En `RepoPersona`:** Implementamos `findByApellido(String apellido)`. Es útil para búsquedas naturales de usuarios, ya que el ID no suele ser conocido por el humano.
* **En `RepoHuerto`:** Implementamos `findByCultivo(String cultivo)`. Permite filtrar huertos según lo que se haya plantado (ej. buscar todos los "Tomates").

---

## 5. Posibles Preguntas Trampa y Respuestas

**P: ¿Por qué no usasteis una tercera entidad?**
R: El enunciado especifica "al menos dos clases de entidades". Preferimos centrarnos en implementar una arquitectura sólida y limpia con dos entidades y un Value Object (`Tamanio`), asegurando que la relación 1:N y la persistencia funcionaran perfectamente, en lugar de añadir complejidad innecesaria.

**P: ¿Es eficiente reescribir todo el fichero cada vez que guardáis (`save`)?**
R: Para el volumen de datos de una práctica académica, es perfectamente válido y simplifica la consistencia de datos. En un entorno real de Big Data, usaríamos acceso aleatorio (`RandomAccessFile`) o una Base de Datos real, pero para ficheros de texto secuenciales, este es el enfoque estándar.

**P: ¿Por qué `Tamanio` tiene validaciones en el constructor?**
R: Porque es un objeto de dominio. Según DDD, un objeto no debería poder crearse en un estado inválido. No tiene sentido un tamaño negativo o sin unidad, así que lanzamos `IllegalArgumentException` al instante.

**P: Veo que usáis `List<T> findAll()` pero la interfaz `IRepositorio` dice `Iterable<T>`.**
R: En Java, `List` extiende de `Iterable`, por lo que cumplimos el contrato de la interfaz. Usamos `List` internamente porque necesitamos métodos como `.add()` o `.removeIf()` para gestionar la persistencia en memoria antes de guardar.

---

## 6. Checklist para la Demo en Vivo (Main)
Al ejecutar el `Main`, demostraremos:
1.  **Limpieza:** Se borran los ficheros previos (`deleteAll`).
2.  **Persistencia:** Se crean Personas y Huertos y se guardan en disco.
3.  **Integridad:** Se muestra que el conteo (`count()`) es correcto.
4.  **Búsqueda Semántica:** Usamos `findByApellido` y `findByCultivo` para demostrar que no solo buscamos por ID.
5.  **Borrado:** Eliminamos una entidad y verificamos que desaparece.