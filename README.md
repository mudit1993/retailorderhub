# RetailOrderHub

Monolithic full-stack Java application built for the 5-day System Design training
program. This is the **Day 1 baseline** — a deliberately simple, single-deployable
Spring Boot app with one intentionally messy `OrderService` class, used as the
reference codebase for Day 1's Lab 1 (HLD vs LLD) and Lab 2 (SonarCloud) materials.

## Stack

- **Java 17**
- **Spring Boot 3.3** (Spring MVC + Thymeleaf server-rendered views — a true
  monolith, one JAR serves both UI and backend)
- **Spring Data JPA + H2** (in-memory, zero setup — resets on every restart)
- **Maven**

## Prerequisites

- JDK 17 or later
- Maven 3.9+ (or use your IDE's built-in Maven support)

## Running it

```bash
mvn spring-boot:run
```

Or build a JAR and run it directly:

```bash
mvn clean package
java -jar target/retailorderhub.jar
```

Then open:

- **http://localhost:8080/** — product catalog + order form
- **http://localhost:8080/orders** — list of placed orders
- **http://localhost:8080/h2-console** — H2 database console (JDBC URL:
  `jdbc:h2:mem:retailorderhub`, user `sa`, no password) — for poking at the data
  directly during training; note this is enabled here for teaching purposes only
  and should never be left on in a real deployment

## Placing a test order

On the home page, use one of the seeded product names exactly as shown in the
catalog (e.g. `Laptop, Mouse`), any customer ID, and any payment method. A
successful order will appear on the `/orders` page.

## About `OrderService`

`src/main/java/com/training/retailorderhub/service/OrderManager.java` is
**deliberately** written the way a real legacy class often looks, to give Day 1's
labs something concrete to analyze:

| Smell / Issue | Where |
|---|---|
| God Object / Long Method | `processOrder()` handles validation, inventory checks, payment, persistence, and inventory updates all in one method |
| Duplicated Code | `validateCustomer()` / `validateItems()` repeat logic already inline at the top of `processOrder()` |
| Primitive Obsession | `paymentMethod` is a raw `String` compared with `.equals()` instead of an enum or strategy |
| SQL Injection (Vulnerability) | `getInventoryQuantity()` and the inventory-update query in `processOrder()` build native SQL by directly concatenating `itemName` |

This is intentional and matches the code referenced in the Day 1 Lab 1 worksheet
and the Lab 2 SonarCloud demo/lab documents. **Do not use this class as a model
for production code** — Day 2 refactors it through the SOLID principles.

## Running the Day 1 SonarCloud scan against this project

```bash
mvn clean verify sonar:sonar \
  -Dsonar.projectKey=<your-project-key> \
  -Dsonar.organization=<your-org> \
  -Dsonar.host.url=https://sonarcloud.io \
  -Dsonar.login=$SONAR_TOKEN
```

See the Lab 2 documents for the full demo script and hands-on steps.

## Project structure

```
retailorderhub/
├── pom.xml
├── README.md
└── src/main/
    ├── java/com/training/retailorderhub/
    │   ├── RetailOrderHubApplication.java
    │   ├── controller/OrderController.java
    │   ├── model/Product.java
    │   ├── model/Order.java
    │   ├── repository/ProductRepository.java
    │   ├── repository/OrderRepository.java
    │   └── service/OrderManager.java
    └── resources/
        ├── application.properties
        ├── data.sql
        ├── static/css/style.css
        └── templates/
            ├── index.html
            └── orders.html
```
