# Project Defence Guide

## 60-second introduction

RideNShine is a full-stack platform for my brother’s real motorcycle parts, servicing and bike-wash business in Fatullah, Narayanganj. Customers currently call, use Facebook or visit the shop to ask about stock and take a service serial, while stock is maintained in Excel. My solution provides a searchable parts catalogue with current stock, service booking and status tracking, offers and moderated reviews. The owner has a protected dashboard to manage the business information. I used React, Spring Boot and PostgreSQL, deployed through Docker, with CI, health monitoring, logs, security controls and built-in error tracking.

## Recommended live demo order

1. Open the production homepage and explain the real business.
2. Open Parts, search for a product and show an out-of-stock item.
3. Open Services and select a booking.
4. Submit a booking and copy the generated reference.
5. Track the same reference and show REQUESTED.
6. Sign in to Admin.
7. Show statistics and products.
8. Change the booking to CONFIRMED or IN_PROGRESS.
9. Return to the customer tracker and show the updated status.
10. Submit a review, show that it is hidden, approve it in Admin, then show it publicly.
11. Open `/actuator/health` and show production health.
12. Show GitHub Actions and architecture/documentation.

## Problem and users

**Problem:** Repeated stock-availability calls, manual service serials, Excel inventory, no structured website and no controlled public feedback.

**Users:** Motorcycle owners/customers and the RideNShine owner/administrator.

## Why no payment?

The first version intentionally avoids online payment. Customers usually need to visit the shop so staff can confirm compatibility and assemble the part. Home delivery is arranged manually when needed. This was a business decision, not a missing feature.

## Why this is not basic CRUD

The system includes interacting frontend, REST backend, PostgreSQL data layer, protected administration, booking workflow with reference tracking, stock logic, moderation workflow, analytics, offers, health monitoring, structured logging, persistent error tracking, security controls, Docker deployment and CI/security scanning.

## Architecture explanation

The React UI calls JSON endpoints. Controllers handle HTTP, services hold business logic, repositories handle PostgreSQL, and entities represent the domain. Spring Security protects administrator endpoints. The global exception handler gives unexpected failures a request ID, logs them and stores an error event. The production Docker build combines frontend assets with the backend while the code remains modular.

## Likely questions and answers

### Why did you choose this project?
It solves a real problem for an existing family business and can continue after the course. I can get direct owner and customer feedback instead of inventing a hypothetical user.

### What is the main differentiator?
It combines local workshop service booking with owner-controlled parts stock, status tracking, moderated reviews and shop operations. General marketplaces sell products, while brand portals are usually limited to one manufacturer.

### What design pattern did you use?
The backend uses a layered Controller–Service–Repository pattern. It separates HTTP concerns, business logic and persistence. Dependency injection connects the layers.

### How is the application secured?
Admin endpoints require Spring Security authentication, credentials are environment variables, CORS is restricted, inputs are validated, JPA avoids manual SQL injection risks, review content is moderated, and production uses HTTPS. The security review also documents prototype limitations.

### How do you monitor it?
Spring Boot Actuator provides a health endpoint. Hosting logs show startup and business events. Unexpected errors receive request IDs and are saved to an error table visible to the administrator.

### What is CI/CD here?
GitHub Actions builds/tests the backend and builds the frontend on pushes and pull requests. CodeQL performs security analysis. The hosting platform automatically redeploys the connected branch.

### What feedback changed the design?
The owner explained that customers often call to ask whether products are available, so stock status became a core feature. He also explained that fitting happens in the shop, so I used call-to-order and booking confirmation instead of unnecessary online payment. Add the actual changes from two customer sessions before the defence.

### What would you improve next?
Customer accounts, motorcycle service history, mechanic job cards, image upload, Excel inventory import, WhatsApp/SMS notifications, rate limiting, stronger authentication, Flyway migrations and more automated tests.

### What did you learn?
A technically possible feature is not always the right business feature. The important decision was to model the actual shop workflow, including confirmation and fitting, instead of copying a generic e-commerce checkout.

## Honest limitations to mention

- Seed prices must be verified by the business owner.
- Current image support uses URLs, not direct upload.
- Authentication is suitable for a prototype but should mature for long-term use.
- More customer feedback and automated test coverage are needed.

Being clear about limitations is stronger than claiming unfinished features are complete.
