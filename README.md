# Order Matching Engine

A stock exchange order matching backend that simulates real-time trade execution using the **Price-Time Priority** algorithm. Built with Spring Boot, MySQL, and Java PriorityQueues.

## Overview

This system replicates the core logic of exchanges like NASDAQ and NSE — it accepts BUY/SELL limit orders via REST APIs, maintains an order book using dual heaps, automatically matches orders when prices cross, and executes trades atomically.

**Key Features:**
- Real-time order matching with price-time priority
- Partial order filling with state management
- Dual-heap architecture for O(1) best-price lookup
- RESTful APIs with request validation
- Persistent trade execution records
- Structured exception handling

## Architecture
