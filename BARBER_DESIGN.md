# Barber App Backend Design

## Overview
A Spring Boot 3.5.3, Java 21, Thymeleaf, and PostgreSQL 17.5 backend for a barber shop, supporting:
- Order creation and listing
- Loyalty points (1 point per order)
- Sales recording
- Free haircut redemption (10 points)
- 15% birthday discount (no stacking with points)

## Layered Architecture

### 1. Presentation Layer (Controller)
- Handles HTTP requests and responses
- Maps endpoints to service methods
- Example: `OrderController`, `CustomerController`, `ServiceController`

### 2. Service Layer
- Contains business logic and rules
- Coordinates between controllers and repositories
- Example: `OrderService`, `CustomerService`, `ServiceService`

### 3. Data Access Layer (Repository)
- Handles database operations
- Uses Spring Data JPA repositories
- Example: `OrderRepository`, `CustomerRepository`, `ServiceRepository`

### 4. Domain Layer (Models/Entities)
- Contains entity classes representing business objects
- Example: `Order`, `Customer`, `Service`, `Sale`

## Entity Definitions

### Customer
- `id`: Long
- `name`: String
- `phoneNo`: String
- `birthday`: LocalDate
- `point`: Integer

### Service
- `id`: Long
- `name`: String
- `price`: BigDecimal

### Order
- `id`: Long
- `customer`: Customer
- `service`: Service
- `price`: BigDecimal
- `date`: LocalDateTime
- `isRedeemed`: Boolean
- `isBirthdayDiscount`: Boolean

### Sale
- `id`: Long
- `order`: Order
- `amount`: BigDecimal
- `date`: LocalDateTime

## Business Rules
- **Loyalty Points**: 1 point per order
- **Redemption**: 10 points for a free haircut (no birthday discount if redeemed)
- **Birthday Discount**: 15% off on exact birthdate (no stacking with points)
- **Sales Recording**: Every order is a sale

## Package Structure
- `controller/` – Presentation layer
- `service/` – Business logic
- `repository/` – Data access
- `models/` – Domain entities

## Next Steps
1. Define entity classes in `models/`
2. Create JPA repositories in `repository/`
3. Implement service classes in `service/`
4. Create controller classes in `controller/`

---
*This design ensures maintainability, scalability, and clear separation of concerns for the barber app backend.*

