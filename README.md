
---

---

# 📦 2️⃣ NOTIFICATION SERVICE — README.md

```markdown
# Notification Service 📬

A Redis Stream consumer microservice built using:

- Spring Boot
- Redis Streams (Consumer Groups)
- Idempotent Consumer Pattern
- Database-level uniqueness
- At-Least-Once Processing

---

## 📌 Overview

This service:

1. Listens to `order-events` Redis Stream
2. Uses Consumer Groups
3. Processes events idempotently
4. Acknowledges messages only after successful processing

---

## 🧠 Architecture

Redis Stream (`order-events`)
↓
Consumer Group (`notification-group`)
↓
NotificationStreamConsumer
↓
NotificationService (business logic)
↓
processed_events table
↓
ACK

---

## 🔥 Key Features

### ✅ Consumer Group
- Scales horizontally
- Ensures coordinated message consumption
- Requires explicit ACK

### ✅ Idempotent Consumer
- Uses DB-level UNIQUE constraint
- Prevents duplicate notification sending
- Safe against retries & crashes

### ✅ At-Least-Once Processing
- Message acknowledged only after successful processing
- Safe against consumer crash

---

---

## 🗄 Database Table

### `processed_events`

- id
- event_id (UNIQUE)
- processed_at

---

## 🔄 Processing Flow

1. Read message from stream
2. Extract eventId
3. Attempt DB insert
4. If duplicate → ignore
5. If new → process notification
6. ACK message

---

## ▶️ How To Run

### 1️⃣ Ensure Redis Is Running

```bash
docker ps

mvn spring-boot:run

```

