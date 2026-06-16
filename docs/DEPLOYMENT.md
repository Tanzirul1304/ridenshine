# Deployment and Operation Guide

## Production design

The root Dockerfile performs three stages:

1. Build the React frontend with `/api` as its API base.
2. Build the Spring Boot backend and include the React files as static resources.
3. Run the resulting JAR in a small Java 21 runtime image.

PostgreSQL remains a separate managed component. This is still a frontend, backend and data-layer architecture, but one web service reduces deployment risk.

## Deploy to Render from GitHub

1. Create a GitHub repository named `ridenshine`.
2. Push the entire project, including `render.yaml`.
3. Create a Render account using GitHub.
4. In Render, choose **New + → Blueprint**.
5. Select the `ridenshine` repository.
6. Render reads `render.yaml` and proposes:
   - `ridenshine-db` PostgreSQL database
   - `ridenshine-platform` Docker web service
7. Apply the Blueprint.
8. Wait for the Docker build and database deployment.
9. Open the web-service URL.
10. Open `/actuator/health` and confirm `{"status":"UP"}`.

## Administrator credentials on Render

`render.yaml` creates `ADMIN_PASSWORD` as a generated secret. Open the web service’s **Environment** page to replace it with a strong password you know, then redeploy. The username defaults to `admin` unless changed.

## Production checks

- Homepage and images load.
- Products and services load from PostgreSQL.
- Submit a booking and save its reference.
- Track the booking by reference.
- Sign in to `/admin`.
- Change booking status and verify the public tracker changes.
- Submit a review, approve it, and verify it becomes public.
- Set one product to stock `0` and verify “Stock out”.
- Open `/actuator/health`.
- Check Render **Logs** for startup and business events.

## CI/CD

- Every push and pull request runs backend tests and the frontend production build through GitHub Actions.
- CodeQL scans Java and JavaScript code.
- Render automatically rebuilds and redeploys the connected branch after successful pushes. For stricter production control, configure Render to deploy only after CI passes.

## Backup and recovery

For a long-term real business deployment:

- Select a paid database plan with backups or export PostgreSQL regularly.
- Keep product images in managed object storage.
- Test restoring the database.
- Keep environment variables outside GitHub.
- Retain an export of the current Excel inventory until import and backup processes are proven.

## Routine operation

### Daily
- Check new bookings and confirm/cancel them.
- Update completed jobs.
- Review submitted customer feedback.
- Mark products stock out as soon as inventory is unavailable.

### Weekly
- Review `/api/admin/errors` through the dashboard.
- Check hosting logs and health.
- Review dependency/security alerts in GitHub.
- Correct outdated offers, product prices and service information.

### Incident response

1. Copy the visible request ID or timestamp.
2. Check the administrator Errors tab.
3. Search Render logs for the same request ID.
4. Reproduce the error safely.
5. Create a GitHub issue and fix through a branch/pull request.
6. Verify CI before deployment.
