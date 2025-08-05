# Products API

A REST API for managing the product catalog, built with Kotlin, Spring Boot, and H2 dabase, using coroutines for asynchronous operations.

## Architectural decisions

I chose hexagonal architecture to abstract the solution, isolating business logic from infrastructure to enable easy changes, such as swapping the persistence driver (e.g., from H2 to PostgreSQL) without modifying the core application. 
The ProductRepository port and adapters like ProductRepositoryAdapter facilitate this flexibility. 

Additionally, I used Kotlin Coroutines with CoroutineCrudRepository and Flow alongside Spring WebFlux (Project Reactor) 
to provide non-blocking features, allowing the API to handle more incoming requests efficiently. 
Coroutines simplify asynchronous programming, while WebFlux’s reactive stack ensures scalable, non-blocking I/O operations, 
making the application performant and maintainable.



## Features
- Hexagonal architecture
- REST endpoint to list products with pagination
- Filtering by category
- H2 database for simplicity.
- Sorting by SKU, price, description, or category
- Discount logic: 15% for Electronics, 25% for Home & Kitchen, 30% for SKUs ending in `5` (highest discount applied)
- Asynchronous request handling with Kotlin coroutines and r2dbc.

## Prerequisites
- Java 17
- Gradle 8.x
- Git

## Setup

./gradlew build
./gradlew bootRun

Example curl request (Unix systems)

curl --location 'http://localhost:8080/api/products?size=5&page=0&sortBy=price&order=asc&category=Electronics'