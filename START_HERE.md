# Start Here — Fast Defence Setup

## Phase 1: run locally

1. Extract the project to a simple path, for example `C:\Projects\RideNShine`.
2. Open Docker Desktop and wait until it says it is running.
3. Open PowerShell in the project root.
4. Run:

```powershell
docker compose up -d database
```

5. Open `backend` in IntelliJ. Let Maven download dependencies. Run `RideNShineApplication.java`.
6. Confirm in a browser: `http://localhost:8080/actuator/health`.
7. Open `frontend` in VS Code.
8. Open a terminal in `frontend` and run:

```powershell
npm install
npm run dev
```

9. Open the local address shown by Vite, normally `http://localhost:5173`.
10. Admin page: `/admin`; local credentials are `admin` / `ChangeMe123!`.

## Phase 2: GitHub

Create one empty public/private repository named `ridenshine`, then run from the project root:

```powershell
git init
git add .
git commit -m "chore: initialise RideNShine full-stack platform"
git branch -M main
git remote add origin YOUR_GITHUB_REPOSITORY_URL
git push -u origin main
```

## Phase 3: production

Follow `docs/DEPLOYMENT.md`. The fastest route is Render Blueprint using the included `render.yaml`.

## Phase 4: evidence before defence

- Complete two more genuine feedback sessions in `docs/USER_RESEARCH.md`.
- Replace at least three demo products/prices with verified shop information.
- Take screenshots of the homepage, product stock, booking reference, admin dashboard, health endpoint, GitHub Actions and Render deployment.
- Practise the demo order in `docs/DEFENCE_GUIDE.md`.
