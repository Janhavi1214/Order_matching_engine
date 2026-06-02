Order Matching Engine
Trading Backend | Java, Spring Boot, MySQL, REST APIs
Overview
A stock exchange order matching backend that simulates real-time trade execution using the Price-Time Priority algorithm. Built with Spring Boot, MySQL, and Java PriorityQueues.
This system replicates the core logic of exchanges like NASDAQ and NSE — it accepts BUY/SELL limit orders via REST APIs, maintains an order book using dual heaps, automatically matches orders when prices cross, and executes trades atomically.
Key Features:
Real-time order matching with price-time priority
Partial order filling with state management
Dual-heap architecture for O(1) best-price lookup
RESTful APIs with request validation
Persistent trade execution records
Structured exception handling
Architecture
REST API Layer (OrderController)
Handles HTTP requests, validates input, returns JSON responses
Service Layer (OrderService)
Business logic - saves orders, triggers matching engine, retrieves records
Matching Engine (engine package)
Core trading logic:
OrderBook: Maintains buy (max-heap) and sell (min-heap) orders
MatchingEngine: Executes trades when prices match
Data Persistence (repository package)
JPA/Hibernate ORM with MySQL database
The Matching Algorithm
Core Concept: Price-Time Priority
Orders are matched when: bestBuyPrice ≥ bestSellPrice
Example:
BUY Orders              SELL Orders
₹105 (10 shares)        ₹98 (5 shares)   ← Best Sell
₹103 (5 shares)         ₹100 (8 shares)
₹100 (20 shares)        ₹104 (3 shares)
  ↑
Best Buy
Match found: 105 ≥ 98 → Trade 5 shares at ₹98
Why Dual Heaps?
BUY side: Max-heap → highest price buyer at O(1)
SELL side: Min-heap → lowest price seller at O(1)
Insert/delete: O(log n)
Traditional array: O(n log n) per insertion (too slow for real-time trading)
API Endpoints
Place an Order
POST /api/v1/orders
Request:
{   "type": "BUY",   "price": 100.0,   "quantity": 5 }
Response (if trade executes):
[   {     "id": 1,     "buyOrderId": 2,     "sellOrderId": 1,     "executedPrice": 98.0,     "executedQuantity": 5,     "executedAt": "2026-06-01T22:51:47.405581"   } ]
Get All Orders
GET /api/v1/orders
Get All Trades
GET /api/v1/trades
Tech Stack
Layer	Technology	Version	Purpose
Language	Java	21	Type system, OOP
Framework	Spring Boot	3.3.5	REST APIs, DI
ORM	Hibernate JPA	Latest	DB mapping
Database	MySQL	8.0	Data storage

Setup & Run
Prerequisites
Java 21+
MySQL 8.0+
Maven 3.8+
Installation
1. Clone the repository
git clone https://github.com/yourusername/Order_matching_engine.git
2. Create MySQL database
CREATE DATABASE trading_engine;
3. Configure application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/trading_engine spring.datasource.username=root spring.datasource.password=yourpassword
4. Run the application
mvn clean install mvn spring-boot:run
Interview Talking Points
DSA:
"I chose PriorityQueue (max-heap for buys, min-heap for sells) to get O(1) best-price lookup with O(log n) inserts — the same approach real exchanges use."
System Design:
"The OrderBook and MatchingEngine are decoupled by design. One manages state, the other executes matches. If I needed to add order cancellation, I can extend either independently."
FinTech Domain:
"Price-time priority means if two buyers offer the same price, the one who placed first gets filled. I implemented that with a secondary comparator on createdAt."
Author
Janhavi Vaidya
GitHub: github.com/janhavi1214
Email: 1680.janhavi@gmail.com
LinkedIn: linkedin.com/in/janhavi-vaidya
