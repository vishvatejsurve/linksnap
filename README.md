<div align="center">

# 🔗 LinkSnap

### Distributed URL Shortener — redirects that never wait on non-critical work

Redis caches hot links, click counting runs asynchronously in the background, and Redis-backed rate limiting protects link creation from abuse.

![Java](https://img.shields.io/badge/Java-Spring%20Boot-6DB33F?logo=springboot&logoColor=white)
![React](https://img.shields.io/badge/React-Vite-61DAFB?logo=react&logoColor=black)
![Tailwind](https://img.shields.io/badge/Tailwind-CSS-06B6D4?logo=tailwindcss&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-Aiven-4479A1?logo=mysql&logoColor=white)
![Redis](https://img.shields.io/badge/Redis-Cache%20%26%20Rate%20Limit-DC382D?logo=redis&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-Multi--stage-2496ED?logo=docker&logoColor=white)
![Render](https://img.shields.io/badge/Deployed-Render-46E3B7?logo=render&logoColor=black)

### 🌐 [**Live Demo → linksnap-frontend-o0zh.onrender.com**](https://linksnap-frontend-o0zh.onrender.com)

*Free-tier hosting: the first request may take 10–30s to wake up.*

![LinkSnap screenshot](./url-shortner-frontend/src/screenshots/Dashboard.png)

[Features](#-features) • [Architecture](#-architecture) • [How It Works](#-how-it-works) • [API](#-api) • [Tradeoffs](#-key-tradeoffs) • [Run Locally](#-running-locally)

</div>

---

## ✨ Features

| Feature | Details |
|---------|---------|
| **Base62 short codes** | Encodes MySQL's auto-increment ID, so no collision checking is needed |
| **Cache-aside with Redis** | Sub-millisecond redirects on a cache hit |
| **Async click tracking** | Spring `@Async`: the redirect never blocks on the DB write |
| **Rate limiting** | IP-based, Redis atomic `INCR`, fixed window, on link creation only |
| **Analytics** | Click count and metadata per short link |
| **Production setup** | Dockerized with multi-stage builds, deployed on Render, MySQL on Aiven |

---

## 🏗️ Architecture

```text
                        ┌────────────────────┐
                        │  React + Tailwind  │
                        │   (Vite, Nginx)    │
                        └─────────┬──────────┘
                                  │
                                  ▼
                        ┌────────────────────┐
                        │   Spring Boot API  │
                        └───┬────────────┬───┘
                            │            │
          ┌─────────────────┘            └──────────────────┐
          ▼                                                 ▼
┌───────────────────┐                             ┌───────────────────┐
│       Redis       │                             │       MySQL       │
│  • URL cache      │ ◀── miss: fall back ──────▶ │  • URLs (source   │
│  • Rate limiting  │                             │    of truth)      │
└───────────────────┘                             │  • Click counts   │
                                                  └───────────────────┘
                                                            ▲
                                      @Async click count ───┘
                                      (off the redirect path)
```

---

## ⚙️ How It Works

### 1. Create a short link
```text
Save row → Base62-encode the ID into a short code → Write to Redis immediately
```

### 2. Redirect
```text
Check Redis → (miss) fall back to MySQL → fire async click count → return redirect instantly
```

### 3. Rate limit
```text
Atomic Redis INCR per IP → 1-minute window → 429 past the limit
```
Applies to the creation endpoint only. Redirects are unaffected.

---

## 🔌 API

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/shorten` | Create a short link. Body: `{ "longUrl": "..." }` |
| `GET` | `/{code}` | Redirect to the original URL |
| `GET` | `/api/analytics/{code}` | Click count and metadata |

**Example**

```bash
curl -X POST http://localhost:8080/api/shorten \
  -H "Content-Type: application/json" \
  -d '{"longUrl": "https://example.com/some/very/long/path"}'
```

---

## 🧰 Tech Stack

| Layer | Technology |
|-------|-----------|
| **Backend** | Java, Spring Boot, Spring Data JPA, Spring Data Redis, Maven |
| **Data** | MySQL, Redis |
| **Frontend** | React, Tailwind CSS, Vite |
| **Infra** | Docker, Nginx, Render, Aiven |

---

## ⚖️ Key Tradeoffs

**`@Async` over Kafka**
Click tracking is async, but I stayed within a stack I can fully explain instead of adding a queue I'd only shallowly understand. *Cost:* a crash mid-write can drop a click. That's acceptable here, but not for stricter durability needs.

**Cache-aside, not write-through**
Redis stays a pure performance layer. If it goes down, requests fall back to MySQL and still work, just more slowly.

---

## 🚀 Running Locally

**Prerequisites:** Java, Node.js, and local MySQL + Redis instances.

```bash
# Backend
./mvnw spring-boot:run

# Frontend
cd url-shortner-frontend
npm install && npm run dev
```

---

## 🗺️ Next Up

- [ ] Kafka for durable async processing
- [ ] Sliding-window rate limiting
- [ ] Integration tests
- [ ] CI/CD pipeline

---

<div align="center">

**Built by Vishvatej Surve** · [LinkedIn](https://linkedin.com/in/vishvatej-surve-4136a1252)

⭐ If you found this project useful, consider giving it a star!

</div>
