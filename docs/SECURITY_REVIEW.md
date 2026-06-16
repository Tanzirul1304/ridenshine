# Security Review

## Scope

The review covers authentication, public input, secrets, database access, browser access, dependencies and operational errors.

## Implemented controls

| Risk | Control |
|---|---|
| Unauthorized business changes | Every `/api/admin/**` endpoint requires the ADMIN role through Spring Security. |
| Password committed to Git | Production username/password are environment variables. The documented local password must be changed in production. |
| Cross-site request abuse | API uses a configured CORS allow-list. Production should include only the deployed website origin. |
| Invalid or harmful input | Jakarta Validation checks required values, phone format, rating range, price/stock limits and booking dates. |
| Database injection | Spring Data JPA parameterization is used; no SQL is assembled from user input. |
| Sensitive stack traces | Public error responses contain a safe message and request ID, not a stack trace. |
| Unnoticed production failures | Unexpected errors are logged and persisted in the error-event table for administrator review. |
| Review abuse | Public reviews are not displayed until approved by an administrator. |
| Secret exposure in frontend | Database credentials and admin password remain in backend environment variables. |
| Dependency vulnerabilities | CI includes npm audit and GitHub CodeQL. Dependabot should be enabled in repository settings. |
| Transport interception | Production must use the HTTPS URL provided by the hosting platform. |

## Known limitations

1. HTTP Basic authentication is appropriate for a course prototype only when used over HTTPS. A mature product should use a persistent administrator table, Argon2/bcrypt password management, MFA and short-lived sessions/tokens.
2. No rate limiter is implemented yet. Public booking and review endpoints could be spammed.
3. Review images use public URLs rather than controlled object storage and file scanning.
4. `spring.jpa.hibernate.ddl-auto=update` is convenient for the prototype; production evolution should use Flyway migrations.
5. The customer booking lookup uses the secret-like reference code but not a second factor such as phone verification.
6. The current product does not store payments, reducing payment-security scope.

## Manual checks before defence/deployment

- Change `ADMIN_PASSWORD` to a unique strong value.
- Confirm `.env` files are ignored and no secrets appear in Git history.
- Set `ALLOWED_ORIGINS` to the exact production HTTPS domain.
- Run `npm audit` in `frontend`.
- Run `mvn test` in `backend`.
- Confirm `/actuator/health` shows `UP`.
- Confirm an unauthenticated request to `/api/admin/stats` receives 401.
- Confirm a submitted review is not public before approval.
- Confirm negative price/stock and past booking dates are rejected.
