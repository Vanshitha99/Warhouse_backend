# TGSWC Warehouse Backend — Deployment Guide (Render)

This guide explains how to deploy the Spring Boot backend on **Render** (or Docker/VM cloud providers) connected to **Aiven MySQL**.

---

## 1. Prerequisites
* GitHub repository connected to Render.
* Aiven MySQL instance running with database `tgswc_warehouse`.
* Java runtime: Java 21+ / Java 26.

---

## 2. Render Web Service Configuration

When creating a new **Web Service** on Render:

| Field | Recommended Value |
| :--- | :--- |
| **Name** | `tgswc-warehouse-backend` |
| **Region** | Oregon (US West) or closest to your database |
| **Branch** | `main` |
| **Root Directory** | `.` (or `warehouse-backend` if deploying from monorepo) |
| **Runtime** | `Docker` or `Java` |
| **Build Command** | `./mvnw clean package -DskipTests` |
| **Start Command** | `java -jar target/warehouse-backend-0.0.1-SNAPSHOT.jar` |

---

## 3. Environment Variables

Configure the following environment variables in the **Render Dashboard -> Environment**:

| Variable | Example Value | Description |
| :--- | :--- | :--- |
| `DB_HOST` | `mysql-1fcaf5d-vanchavanshithareddy-e875.a.aivencloud.com` | Aiven MySQL hostname |
| `DB_PORT` | `15896` | Aiven MySQL port |
| `DB_NAME` | `tgswc_warehouse` | Dedicated application database name |
| `DB_USERNAME` | `avnadmin` | Aiven MySQL admin username |
| `DB_PASSWORD` | *(your Aiven password)* | Aiven MySQL password |
| `DB_SSL_MODE` | `REQUIRED` | Enforces SSL/TLS encryption |
| `PORT` | `8080` | Server port (Render sets this automatically) |
| `CORS_ALLOWED_ORIGINS` | `https://your-frontend.vercel.app,http://localhost:5173` | Comma-separated list of allowed frontend origins |

---

## 4. Local Development vs Production

* **Local Development**:
  If no environment variables are provided, the backend falls back to local MySQL (`localhost:3306`, user `root`, DB `tgswc_warehouse`, `sslMode=PREFERRED`).
* **Production**:
  When environment variables are provided, Spring Boot connects securely to Aiven MySQL over SSL (`sslMode=REQUIRED`).

---

## 5. Health Check & Verification

Once deployed on Render, verify the service by requesting:
```bash
curl https://<your-render-app>.onrender.com/api/trucks
```
You should receive a JSON response with the migrated truck records.
