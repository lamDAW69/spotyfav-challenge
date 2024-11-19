# Gestión de Usuarios y Eventos

Este proyecto es una aplicación web que permite la gestión de usuarios y eventos mediante operaciones CRUD (Crear, Leer, Actualizar, Eliminar). Se ha desarrollado una interfaz intuitiva para la administración y edición de datos, mientras que el backend maneja la lógica y persistencia de datos.

## Tabla de Contenidos

1. [Características](#características)
2. [Tecnologías Utilizadas](#tecnologías-utilizadas)
3. [Estructura del Proyecto](#estructura-del-proyecto)
4. [Descripción de la Base de Datos](#descripción-de-la-base-de-datos)
5. [Uso](#uso)
6. [API Endpoints](#api-endpoints)
7. [Pruebas](#pruebas)
8. [Consideraciones de Diseño](#consideraciones-de-diseño)
9. [Bibliografía](#bibliografía)
10. [Conclusiones](#conclusiones)

## Características

- Administración de usuarios y eventos.
- CRUD completo de ambas entidades.
- Alternancia de tema oscuro/claro.
- Funcionalidad asíncrona mediante JavaScript.
- API REST con Spring Boot y persistencia con JPA.

## Tecnologías Utilizadas

- **Backend**: Java con Spring Boot, Spring Data JPA.
- **Frontend**: HTML, CSS y JavaScript, con Bootstrap para estilos y Bootstrap Icons.
- **API**: Comunicación asincrónica a través de Fetch API.

## Estructura del Proyecto

```plaintext
.
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com/example/eventos
│   │   │       ├── eventos
│   │   │       │   ├── proyecciones
│   │   │       │   │   └── EventosSinUsuarios.java
│   │   │       │   ├── Evento.java
│   │   │       │   ├── EventosController.java
│   │   │       │   ├── EventosRespository.java
│   │   │       │   └── EventosService.java
│   │   │       ├── usuarios
│   │   │       │   ├── proyecciones
│   │   │       │   │   └── UsuarioSinEventos.java
│   │   │       │   ├── Usuario.java
│   │   │       │   ├── UsuariosController.java
│   │   │       │   ├── UsuariosRepository.java
│   │   │       │   └── UsuariosService.java
│   │   │       └── EventosApplication.java
│   ├── resources
│   │   ├── static
│   │   ├── templates
│   │   └── application.yaml
├── public
│   ├── index.html
│   └── script.js
└── README.md
```

## Descripción de la Base de Datos

La base de datos cuenta con dos tablas principales: `Usuario` y `Evento`, relacionadas de manera `muchos a muchos` a través de una tabla intermedia `usuario_asiste_evento`.

La configuración de esta base de datos facilita que un usuario pueda inscribirse en múltiples eventos y un evento pueda tener varios usuarios inscritos.

### Estructura de las Tablas

1. **Tabla `Usuario`**  
   - **Campos**:
     - `id`: Identificador único del usuario (auto-incremental).
     - `nombre`: Nombre del usuario.
     - `correo`: Correo electrónico del usuario.
   - **Relación**: Muchos usuarios pueden asistir a múltiples eventos a través de la relación `usuario_asiste_evento`.

2. **Tabla `Evento`**  
   - **Campos**:
     - `id`: Identificador único del evento (auto-incremental).
     - `titulo`: Título del evento.
     - `descripcion`: Descripción del evento.
     - `precio`: Precio del evento.
     - `fecha`: Fecha del evento.
   - **Relación**: Múltiples eventos pueden tener muchos asistentes.

3. **Tabla `usuario_asiste_evento` (Tabla intermedia)**  
   - Esta tabla une la relación `muchos a muchos` entre `Usuario` y `Evento`:
     - `usuario`: ID del usuario (clave ajena).
     - `evento`: ID del evento (clave ajena).

## Uso

### Interfaz de Usuario

1. **Gestión de Usuarios**: Añadir, editar y eliminar usuarios con campos para nombre y correo electrónico.
2. **Gestión de Eventos**: Administrar eventos con información detallada, incluyendo título, descripción, precio y fecha.
3. **Alternancia de Tema**: Cambia entre temas claro y oscuro mediante un botón en la esquina superior derecha.

### Funcionalidad Asíncrona

Las operaciones de añadir, editar y eliminar usuarios o eventos se realizan sin necesidad de recargar la página, gracias a Fetch API en `script.js`.

## API Endpoints

La API REST incluye los siguientes endpoints:

### Usuarios

- **GET** `/usuarios` - Listado de usuarios.
- **GET** `/usuarios/{id}` - Información de un usuario específico.
- **POST** `/usuarios` - Crear un usuario nuevo.
- **PUT** `/usuarios/{id}` - Actualizar un usuario existente.
- **DELETE** `/usuarios/{id}` - Eliminar un usuario.

### Eventos

- **GET** `/eventos` - Listado de eventos.
- **GET** `/eventos/{id}` - Información de un evento específico.
- **POST** `/eventos` - Crear un evento nuevo.
- **PUT** `/eventos/{id}` - Actualizar un evento existente.
- **DELETE** `/eventos/{id}` - Eliminar un evento.

## Pruebas

### Pruebas con Postman

Se han realizado pruebas exhaustivas de la API utilizando **Postman** para validar el correcto funcionamiento de cada endpoint. Estas pruebas se ejecutaron en la aplicación externa de Postman, probando las operaciones de CRUD para usuarios y eventos, así como la correcta respuesta y formato de datos.

Además, se utilizó la extensión de **Postman en VS Code** para facilitar las pruebas directamente desde el entorno de desarrollo. Esto permitió iterar rápidamente en los casos de prueba y verificar la funcionalidad de la API sin cambiar de aplicación.

## Consideraciones de Diseño

### Backend

- **Persistencia con JPA**: Las entidades `Usuario` y `Evento` se mapean en tablas de la base de datos y sus operaciones se manejan a través de `UsuariosRepository` y `EventosRepository`.
- **Proyecciones**: El uso de `UsuarioSinEventos` y `EventoSinUsuarios` en los paquetes `proyecciones` permite optimizar ciertas consultas excluyendo datos innecesarios de la relación.

### Frontend

- **Interactividad**: El archivo `script.js` maneja la interacción en la interfaz, incluyendo la carga inicial y el manejo de eventos de usuario.
- **Estilo y Responsividad**: Uso de Bootstrap para un diseño responsivo.
- **Tema Oscuro/Claro**: El tema puede alternarse dinámicamente.

## Bibliografía

- **Spring Boot**: Documentación oficial de Spring Boot para comprender mejor la configuración de proyectos, la creación de APIs REST y la integración de JPA. Disponible en: [https://spring.io/projects/spring-boot](https://spring.io/projects/spring-boot).
- **Java Persistence (JPA)**: Guía para la implementación de JPA en aplicaciones Java Spring, proporcionando un mapeo ORM (Object-Relational Mapping) para la persistencia de datos. Disponible en: [https://docs.spring.io/spring-data/jpa/reference/jpa.html](https://docs.spring.io/spring-data/jpa/reference/jpa.html).
- **Postman**: Guía y tutoriales para el uso de Postman en la creación, prueba y documentación de APIs RESTful. Disponible en: [https://learning.postman.com](https://learning.postman.com).
- **VS Code Marketplace – Extensión Postman**: Información sobre la extensión de Postman para Visual Studio Code, que facilita la integración de pruebas de API en el entorno de desarrollo. Disponible en: [https://marketplace.visualstudio.com/items?itemName=Postman.postman-for-vscode](https://marketplace.visualstudio.com/items?itemName=Postman.postman-for-vscode).
- **Bootstrap**: Referencia oficial de Bootstrap para la implementación de un diseño responsivo y estético en aplicaciones web. Disponible en: [https://getbootstrap.com](https://getbootstrap.com).

## Conclusiones

Este proyecto de gestión de usuarios y eventos demuestra cómo una aplicación web puede beneficiarse de un diseño basado en microservicios y operaciones CRUD, que facilita una interacción eficiente y permite una administración intuitiva de datos. Gracias a la utilización de tecnologías como Spring Boot, JPA y una interfaz con Bootstrap, se logra un equilibrio entre funcionalidad y usabilidad.

El uso de pruebas exhaustivas con Postman, tanto en su aplicación independiente como en la extensión para VS Code, permitió verificar la correcta funcionalidad de los endpoints, garantizando que la API responde de manera consistente y segura ante diversas peticiones. Esto refuerza la confiabilidad de la aplicación en entornos reales y facilita su futura expansión.

Además, el enfoque en una arquitectura modular y la optimización mediante proyecciones de datos en el backend representan prácticas recomendadas en el desarrollo de software. Estos métodos no solo mejoran el rendimiento de la aplicación, sino que también ofrecen un diseño más flexible y escalable, abriendo la posibilidad de integrar nuevas funcionalidades en el futuro con menor esfuerzo de desarrollo.

En conclusión, este proyecto es una base sólida para la gestión de eventos y usuarios, proporcionando una infraestructura que puede adaptarse y ampliarse fácilmente para cumplir con requisitos adicionales ante posibles actualizaciones y mejoras.
