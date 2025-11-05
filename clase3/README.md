# Tarea 3 · Refuerzo de arquitectura, validaciones y relación 1:N

## Objetivo
Consolidar el product-service aplicando las buenas prácticas vistas en clase: capas bien definidas, DTOs validados, manejo uniforme de errores y categorías persistidas.

## Pasos
1. Levantar Docker Compose (docker compose up -d)  
2. Crear categorias, relación 1:N producto-categoria
3. Prueba de validaciones-casos de error (400, 404, 409)
4. Prueba de endpoints con Postman (capturas en screenshot/).

## Resultado

![Imagen de contenedor descargada](screenshot/inicia_docker.png)

### Crear categorias, relación 1:N producto-categoria

POST http://localhost:9494/api/categories crea categoria

![Imagen de contenedor descargada](screenshot/crea_categoria.png)

![Imagen de contenedor descargada](screenshot/select_crea_categoria.png)


POST http://localhost:9494/api/products crea producto-categoria

![Imagen de contenedor descargada](screenshot/crea_producto_categoria.png)

![Imagen de contenedor descargada](screenshot/select_producto_categoria.png)

### Validaciones casos de error - pruebas

ERROR 400 (precio negativo)

![Imagen de contenedor descargada](screenshot/error_400.png)

ERROR 404 (categoria no existe)

![Imagen de contenedor descargada](screenshot/eror_404.png)

ERROR 409 (categoria duplicada)

![Imagen de contenedor descargada](screenshot/error_409.png)


## Autor
Judith Quelca - Curso Spring Boot & Kafka