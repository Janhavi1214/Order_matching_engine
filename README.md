# 📈 Order Matching Engine

A stock exchange order matching backend that simulates real-time trade execution using the **Price-Time Priority** algorithm. Built with Spring Boot, MySQL, and Java PriorityQueues.

## 🎯 Overview

This system replicates the core logic of exchanges like NASDAQ and NSE — it accepts BUY/SELL limit orders via REST APIs, maintains an order book using dual heaps, automatically matches orders when prices cross, and executes trades atomically.

**Key Features:**
- ⚡ Real-time order matching with price-time priority
- 📊 Partial order filling with state management
- 🔥 Dual-heap architecture for O(1) best-price lookup
- 🛡️ RESTful APIs with request validation
- 💾 Persistent trade execution records
- 🚨 Structured exception handling

## 🏗️ Architecture

```
┌─────────────┐
│  REST API   │ (OrderController)
│  Layer      │
└──────┬──────┘
       │
┌──────▼──────────────┐
│  Service Layer      │ (OrderService)
│  - placeOrder()     │
│  - getAllOrders()   │
│  - getAllTrades()   │
└──────┬──────────────┘
       │
┌──────▼──────────────────────────────┐
│  Matching Engine (engine package)   │
│                                     │
│  ┌─────────────────────────────┐   │
│  │  OrderBook                  │   │
│  │  - buyOrders (max heap)     │   │
│  │  - sellOrders (min heap)    │   │
│  └──────────┬──────────────────┘   │
│             │                       │
│  ┌──────────▼──────────────────┐   │
│  │  MatchingEngine             │   │
│  │  - match() [core logic]     │   │
│  │  - Trade execution          │   │
│  └─────────────────────────────┘   │
└──────┬──────────────────────────────┘
       │
┌──────▼─────────────────────┐
│  Data Persistence Layer     │
│  - OrderRepository          │
│  - TradeRepository          │
│  - JPA/Hibernate ORM        │
└──────┬─────────────────────┘
       │
┌──────▼─────────────┐
│  MySQL Database    │
│  - orders table    │
│  - trades table    │
└────────────────────┘
```

## 🔄 The Matching Algorithm

### 💡 Core Concept: Price-Time Priority

Orders are matched when: **bestBuyPrice ≥ bestSellPrice**

**Example:**
```
BUY Orders              SELL Orders
₹105 (10 shares)        ₹98 (5 shares)   ← Best Sell 🎯
₹103 (5 shares)         ₹100 (8 shares)
₹100 (20 shares)        ₹104 (3 shares)
  ↑
Best Buy 🎯

Match found: 105 ≥ 98 → Trade 5 shares at ₹98 ✅
```

### 📚 Why Dual Heaps?

- **BUY side:** Max-heap → highest price buyer at O(1)
- **SELL side:** Min-heap → lowest price seller at O(1)
- Insert/delete: O(log n) ⚡
- Traditional array: O(n log n) per insertion (too slow) ❌

### 🔧 Execution Logic

1. Peek top of both heaps
2. If `buyPrice < sellPrice` → no match, stop
3. If match → execute trade for `min(buyQty, sellQty)` shares
4. Update order quantities and statuses
5. Partially filled orders go back into heaps
6. Fully filled orders are removed
7. Repeat until no more matches

## 🔌 API Endpoints

### 📤 Place an Order
```http
POST /api/v1/orders
Content-Type: application/json

{
  "type": "BUY",
  "price": 100.0,
  "quantity": 5
}
```

**Response (if trade executes):** ✅
```json
[
  {
    "id": 1,
    "buyOrderId": 2,
    "sellOrderId": 1,
    "executedPrice": 98.0,
    "executedQuantity": 5,
    "executedAt": "2026-06-01T22:51:47.405581"
  }
]
```

**Response (if no match):** ⏳
```json
[]
```

### 📥 Get All Orders
```http
GET /api/v1/orders
```

**Response:**
```json
[
  {
    "id": 1,
    "type": "SELL",
    "price": 98.0,
    "quantity": 5,
    "status": "OPEN",
    "createdAt": "2026-06-01T22:50:42.811159"
  },
  {
    "id": 2,
    "type": "BUY",
    "price": 100.0,
    "quantity": 5,
    "status": "FILLED",
    "createdAt": "2026-06-01T22:51:47.388998"
  }
]
```

### 💹 Get All Trades
```http
GET /api/v1/trades
```

**Response:**
```json
[
  {
    "id": 1,
    "buyOrderId": 2,
    "sellOrderId": 1,
    "executedPrice": 98.0,
    "executedQuantity": 5,
    "executedAt": "2026-06-01T22:51:47.405581"
  }
]
```

## ✅ Validation

Orders must satisfy:
- `type`: BUY or SELL (enum validated) ✔️
- `price`: positive number (> 0) 💰
- `quantity`: at least 1 share 📊

**Example of invalid request:**
```json
{
  "type": "BUY",
  "price": -100.0,
  "quantity": 0
}
```

**Response (400 Bad Request):** ❌
```json
{
  "price": "must be greater than 0",
  "quantity": "must be greater than or equal to 1"
}
```

## 🚀 Setup & Run

### 📋 Prerequisites
- ☕ Java 21+
- 🗄️ MySQL 8.0+
- 🛠️ Maven 3.8+

### 📦 Installation

1. **Clone the repo**
```bash
git clone https://github.com/janhavi1214/Order_matching_engine.git
cd Order_matching_engine
```

2. **Create MySQL database**
```sql
CREATE DATABASE trading_engine;
```

3. **Configure database in `application.properties`**
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/trading_engine
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

4. **Run the application**
```bash
mvn clean install
mvn spring-boot:run
```

The app starts on `http://localhost:8080` 🎉

## 🛠️ Tech Stack

| Layer | Technology |
|-------|------------|
| **Language** | Java 21 |
| **Framework** | Spring Boot 3.3.5 |
| **ORM** | Hibernate JPA |
| **Database** | MySQL 8.0 |
| **Build** | Maven |
| **DSA** | PriorityQueue (Heaps) |
| **API** | REST with Spring Web |
| **Validation** | Jakarta Validation |

## 📁 Project Structure

```
src/main/java/com/trading/order_matching_engine/
├── entity/              📦
│   ├── Order.java
│   └── Trade.java
├── repository/          🗄️
│   ├── OrderRepository.java
│   └── TradeRepository.java
├── service/             ⚙️
│   └── OrderService.java
├── engine/              🔥
│   ├── OrderBook.java
│   └── MatchingEngine.java
├── controller/          🔌
│   └── OrderController.java
└── exception/           🚨
    └── GlobalExceptionHandler.java
```

## 💭 Design Decisions

### Why PriorityQueue over sorted ArrayList?
- **ArrayList:** Insert O(n log n), Find best O(1) ❌
- **PriorityQueue:** Insert O(log n), Find best O(1) ✅
- For high-frequency trading, O(log n) vs O(n log n) is massive

### Why separate OrderBook from MatchingEngine?
- **OrderBook:** Answers "what orders exist?" 📊
- **MatchingEngine:** Answers "can any be matched?" 🔄
- Single Responsibility Principle — each class does one thing well

### Why FILLED vs PARTIALLY_FILLED?
- Enables partial execution (core exchange behavior) 📈
- Buyer wants 10, only 6 available → 6 trade, 4 remain in book 🔄
- When next seller comes, remaining 4 can match ✅

## 💬 Interview Talking Points

**DSA:** "I chose PriorityQueue (max-heap for buys, min-heap for sells) to get O(1) best-price lookup with O(log n) inserts — the same approach real exchanges use." 🏆

**System Design:** "The OrderBook and MatchingEngine are decoupled by design. One manages state, the other executes matches. If I needed to add order cancellation or market orders, I can extend either independently." 🎯

**FinTech Domain:** "Price-time priority means if two buyers offer the same price, the one who placed first gets filled. I implemented that with a secondary comparator on `createdAt`." 📅

## 🚧 Future Enhancements

- [ ] 🚫 Order cancellation with cascade cleanup
- [ ] 📊 Market orders (execute at any price)
- [ ] 🔀 Multiple instrument support (multiple order books)
- [ ] 📡 WebSocket for real-time price feeds
- [ ] 🐳 Docker containerization for deployment
- [ ] ✔️ JUnit 5 test suite (matching algorithm unit tests)

## 📄 License

MIT License

---

## 👨‍💻 Author

**Janhavi Vaidya** 🚀
- GitHub: [@janhavi1214](https://github.com/janhavi1214) 
- Email: 1680.janhavi@gmail.com 📧
