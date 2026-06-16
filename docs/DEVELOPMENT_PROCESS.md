# Documented Development Process

## Project method

A small iterative process was used because the project had a fixed defence deadline. Each iteration produced a working vertical feature across UI, API and database rather than creating disconnected screens.

## Development log

### 16 June 2026 — Scope and validation

- Confirmed the project is for the real RideNShine motorcycle business.
- Defined two user groups: customers and the business owner.
- Documented the current phone/Facebook/Excel process.
- Compared online parts stores, marketplaces and service-booking alternatives.
- Chose not to include online payment in version one because product compatibility and fitting normally require a shop visit.

### 16 June 2026 — Architecture

- Selected React/Vite, Spring Boot and PostgreSQL because the project owner has used a similar stack before.
- Defined Controller–Service–Repository boundaries.
- Added public and administrator API boundaries.
- Designed product, service, booking, review, offer and error-event entities.

### 16 June 2026 — Customer workflow

- Implemented product catalogue with category/search and stock status.
- Implemented service list.
- Implemented booking request, generated reference code and status lookup.
- Implemented review submission and moderation.

### 16 June 2026 — Business operations

- Implemented protected administrator dashboard.
- Added product, service and offer management.
- Added booking-status workflow.
- Added review moderation and summary analytics.

### 16 June 2026 — Production engineering

- Added Dockerfiles and Docker Compose.
- Added Render Blueprint for production application and PostgreSQL.
- Added Spring Boot Actuator health endpoint.
- Added structured application logging and persistent error tracking.
- Added GitHub Actions build/test pipeline and CodeQL security workflow.
- Added deployment, architecture, operation, research and security documentation.

## Recommended Git commit sequence

Use separate commits so the development history is visible:

1. `chore: initialise RideNShine full-stack project`
2. `feat: add product and service catalogue`
3. `feat: add booking request and status workflow`
4. `feat: add moderated reviews and offers`
5. `feat: add protected admin dashboard`
6. `ops: add monitoring error tracking and Docker deployment`
7. `docs: add research architecture security and defence notes`

Because the first working version was produced under deadline pressure, future work should add more unit/integration tests and database migrations.
