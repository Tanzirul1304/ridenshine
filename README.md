# RideNShine Motorcycle Service and Parts Platform

RideNShine is a full-stack software project for a real motorcycle repair, spare-parts and bike-wash business in Bot-tola, Kayampur, Fatullah, Narayanganj, Bangladesh.

The platform helps customers check parts and stock status, view workshop services, request a servicing date, track the request using a reference code, and submit moderated reviews. The owner receives a protected dashboard for products, stock, services, offers, bookings, reviews, analytics and error events.

> Important: Product and service prices included in the seed data are demonstration values. Replace them with verified business data before public use.

## Main problem

Customers currently call, message the Facebook page or visit the shop to ask whether a part is available and to take a servicing serial. Customer records are stored through normal invoices and inventory is maintained in Excel. RideNShine needs a structured online channel without forcing online payment, because parts normally need compatibility checking and fitting at the workshop.

## Target users

1. Motorcycle owners in and around Fatullah and Narayanganj.
2. RideNShine owner/administrator who maintains stock, services and bookings.

## Differentiator

RideNShine connects a real local workshop with its own live parts catalogue, stock-out marking, service booking, booking-status workflow, moderated customer reviews, offers and a business dashboard. Unlike a general marketplace, the system is connected to workshop fitting and servicing. Unlike a brand-specific service portal, it is intended for a local multi-brand motorcycle business.

## Features

### Customer
- Browse and search motorcycle parts by name, brand and category.
- View live stock or stock-out information.
- View services, starting prices and estimated duration.
- Request a service date without online payment.
- Receive a unique booking reference and track status.
- View offers.
- Submit a rating, feedback and optional public photo URL.
- Call the business directly to confirm product compatibility or delivery.

### Administrator
- HTTP Basic authentication through Spring Security.
- Add, edit and remove products.
- Update stock quantity and featured products.
- Add, edit, activate and remove services.
- View bookings and move them through REQUESTED, CONFIRMED, IN_PROGRESS, COMPLETED or CANCELLED.
- Approve or remove reviews before public display.
- Publish and manage offers.
- View analytics counts.
- View the 50 latest unexpected application errors with request IDs.

## Architecture

```text
React/Vite user interface
        |
        | HTTPS / JSON REST
        v
Spring Boot Controllers
        |
        v
Service layer (business rules and logging)
        |
        v
Spring Data JPA repositories
        |
        v
PostgreSQL database
```

The backend follows Controller–Service–Repository separation. DTOs validate customer booking input. A global exception handler gives errors request IDs and stores unexpected events for administrator review. Public and admin endpoints are separated by Spring Security.

More detail: [`docs/ARCHITECTURE.md`](docs/ARCHITECTURE.md)

## Technology

- Frontend: React, Vite, React Router
- Backend: Java 21, Spring Boot, Spring Web, Spring Data JPA, Spring Security, Validation, Actuator
- Database: PostgreSQL
- Local infrastructure: Docker Compose
- Production: Docker image on Render with Render PostgreSQL
- Monitoring: Spring Boot Actuator health endpoint and platform logs
- Error tracking: persistent application error events plus request IDs and server logs
- CI: GitHub Actions backend tests and frontend production build
- Security scanning: GitHub CodeQL and npm audit

## Local development — easiest method

### Prerequisites
- Java 21 or newer
- Maven
- Node.js 20 or newer
- Docker Desktop

### 1. Start PostgreSQL only

```bash
docker compose up -d database
```

### 2. Run backend

Open a terminal in `backend`:

```bash
mvn spring-boot:run
```

Backend: `http://localhost:8080`
Health: `http://localhost:8080/actuator/health`

### 3. Run frontend

Open another terminal in `frontend`:

```bash
npm install
npm run dev
```

Frontend: `http://localhost:5173`

### Local admin login

- Username: `admin`
- Password: `ChangeMe123!`

Change the password before production.

## Run all three components with Docker

```bash
docker compose up --build
```

- Website: `http://localhost:3000`
- API: `http://localhost:8080/api`
- PostgreSQL: port `5432`

## Production deployment

The root `Dockerfile` builds the React frontend and copies it into the Spring Boot application. This gives one production web service while keeping frontend and backend as separate source modules. `render.yaml` provisions both the application and PostgreSQL.

See [`docs/DEPLOYMENT.md`](docs/DEPLOYMENT.md).

## Important environment variables

| Variable | Purpose |
|---|---|
| `DATABASE_URL` | Render PostgreSQL connection string |
| `SPRING_DATASOURCE_URL` | Local/alternative JDBC URL |
| `SPRING_DATASOURCE_USERNAME` | Database username |
| `SPRING_DATASOURCE_PASSWORD` | Database password |
| `ADMIN_USERNAME` | Production administrator username |
| `ADMIN_PASSWORD` | Strong production administrator password |
| `ALLOWED_ORIGINS` | Comma-separated frontend origins for CORS |
| `VITE_API_URL` | Frontend API base URL; `/api` in combined production build |

## API summary

### Public
- `GET /api/products`
- `GET /api/services`
- `POST /api/bookings`
- `GET /api/bookings/reference/{reference}`
- `GET /api/reviews`
- `POST /api/reviews`
- `GET /api/offers`

### Protected administrator endpoints
- `/api/admin/products`
- `/api/admin/services`
- `/api/admin/bookings`
- `/api/admin/reviews`
- `/api/admin/offers`
- `GET /api/admin/stats`
- `GET /api/admin/errors`

## Documentation

- [Architecture](docs/ARCHITECTURE.md)
- [Product and competitor research](docs/PRODUCT_RESEARCH.md)
- [User research record](docs/USER_RESEARCH.md)
- [Development process](docs/DEVELOPMENT_PROCESS.md)
- [Security review](docs/SECURITY_REVIEW.md)
- [Deployment and operation](docs/DEPLOYMENT.md)
- [Defence notes](docs/DEFENCE_GUIDE.md)

## Current limitations and next steps

- Replace demonstration prices and brands with verified business data.
- Add direct image upload through object storage instead of public image URLs.
- Add customer accounts and complete motorcycle service history.
- Add mechanic/staff role and job cards.
- Send SMS or WhatsApp status notifications.
- Synchronize inventory imports from the existing Excel workbook.
- Add courier requests while keeping workshop payment and fitting as the default process.

## Business contact

RideNShine — One stop biking solution for Bike & Biker

Bot-tola, Kayampur, Fatullah, Narayanganj, Bangladesh  
Phone: +880 1973-444139  
Email: contact.ridenshine@gmail.com
