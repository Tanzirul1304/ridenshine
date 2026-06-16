# Architecture Guideline

## 1. Architectural goals

The application must remain understandable to the project owner even when AI coding tools are used. New code should respect clear module boundaries, keep business logic outside the React UI and controllers, validate external input, and avoid direct database access from controllers.

## 2. Components

### Frontend

The React frontend contains pages, reusable components and a single API service. Pages are responsible for user interaction and display. They do not connect directly to PostgreSQL or implement backend business rules.

### Backend

- **Controllers:** define REST endpoints and HTTP status codes.
- **DTOs:** define validated external request structures.
- **Services:** implement booking creation, status transitions, review moderation, product management and logging.
- **Repositories:** provide persistence through Spring Data JPA.
- **Models:** represent domain entities.
- **Configuration:** security, CORS and seed data.
- **Exception handling:** converts failures into stable API responses and tracks unexpected errors.

### Data layer

PostgreSQL stores products, services, bookings, reviews, offers and error events. Hibernate manages the schema for this course prototype.

## 3. Domain model

```text
WorkshopService 1 ---- * Booking
Product                  (independent inventory item)
Review                   (moderated customer content)
Offer                    (time-limited communication)
ApplicationError         (operational error event)
```

A booking cannot exist without a service. It receives a unique public reference code and a controlled status enum.

## 4. Main workflows

### Service booking

1. Frontend loads active services.
2. Customer sends name, phone, bike model, service, preferred date and notes.
3. Backend validates all required data and future/present date.
4. Service layer finds the selected service and creates a REQUESTED booking.
5. Backend generates a unique `RNS-XXXXXXXX` reference.
6. Administrator confirms and updates the booking through its workflow.
7. Customer tracks the result by reference.

### Review moderation

1. Customer submits a review.
2. Backend always saves it as unapproved.
3. Administrator reviews it.
4. Only approved reviews appear on the public endpoint.

### Error tracking

1. Known validation/not-found errors return controlled 400/404 responses.
2. Unexpected exceptions receive a UUID request ID.
3. Full details are written to logs.
4. A safe error record is stored in PostgreSQL.
5. Administrator can inspect recent events without exposing stack traces publicly.

## 5. Security boundaries

Public read endpoints and booking/review submission are available without an account. All `/api/admin/**` routes require an ADMIN role through Spring Security. Credentials are loaded from environment variables and must not be committed. Production must use HTTPS.

## 6. Deployment architecture

```text
Browser
   |
   | HTTPS
   v
Render Web Service (Docker)
   |-- React static build
   |-- Spring Boot REST API
   |-- Actuator health endpoint
   |
   | private database connection
   v
Render PostgreSQL
```

The source remains split into frontend and backend modules. The production image combines them to reduce operational risk and make one-night deployment possible.

## 7. Rules for future development and AI agents

1. Controllers must not contain repository calls or complex business logic.
2. Frontend components must not contain database credentials or secrets.
3. New external input must use validation.
4. Admin functionality must remain under protected routes.
5. Unexpected errors must be logged, but stack traces and secrets must never be returned to customers.
6. Database changes should later move from `ddl-auto=update` to Flyway migrations.
7. Every new feature should include a test and documentation update.
8. No generated code should be merged unless the project owner can explain it.
