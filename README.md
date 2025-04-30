# 🚀 buyRide – Modern Motorcycle Marketplace App 🏍️📱

**buyRide** is a sleek, user-friendly Android application built with **Kotlin** and **Jetpack Compose**. It provides motorcycle enthusiasts with a modern platform to browse, favorite, and purchase bikes seamlessly. This project integrates backend and frontend technologies to deliver real-time inventory management, secure purchases, and persistent user experiences.

## ✨ Features

### 🔐 Authentication
- **Login & Registration** with full input validation
- Unique username enforcement
- User data stored securely using **SQLite**

### 🧠 Session Management
- **SharedPreferences** used to persist login state
- Auto-login functionality for improved user experience

### 🏠 Home Screen
- Highlights **top-selling bikes** in a responsive scrollable UI
- Uses **Jetpack Compose** for modern, declarative UI development

### 📦 Product Screen
- Real-time inventory fetched from a **Node.js backend** via **Retrofit**
- Each product card includes:
  - Bike image and details
  - Favorite toggle (locally saved)
  - Tap to view bike details and proceed to **Buy**

### 💳 Purchase Flow
- Users input card details to complete orders
- Orders saved locally in **SQLite**
- Confirmation email sent via **Gmail integration**

### ⚙️ Settings
- View and manage account details
- Track active and past orders
- Access "About App" and **Logout** options

## 🌐 Requirements
- Internet connection required for:
  - Fetching bike inventory
  - Purchase confirmation emails

## 🛠️ Tech Stack
- **Kotlin**
- **Jetpack Compose**
- **Retrofit** (API Communication)
- **SQLite** (Local data persistence)
- **SharedPreferences** (Login state management)
- **Node.js** (Backend service for products and orders)

## 📱 UI/UX
- Fully responsive layout
- Tested across various screen sizes and Android devices
- Modern and intuitive design for a smooth user experience

## 💡 Learning Outcomes
This project helped me gain hands-on experience with:
- Building a complete **Android app** using Kotlin & Jetpack Compose
- Integrating with a **custom backend** using Retrofit
- Designing **real-world features** like cart, checkout, and order tracking
- Managing local and session data efficiently
