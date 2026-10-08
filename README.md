# VibeConnect - Social Media Management System

A desktop-based social media application developed using **Java Swing and MongoDB** for the **Advanced Database Systems** core idea.

The application demonstrates the integration of a Java GUI with a **NoSQL database** to manage social media data. Users can create accounts, log in, view posts, manage profiles, and interact with stored social media data through a simple desktop interface.

## Features

* User Login and Signup
* Session Management
* Home Feed with Post Visibility
* User Profile and Post Listings
* Follower Data Management
* CRUD Operations for Social Media Data
* MongoDB Atlas Database Integration
* Modular Java Application Structure

## Technology Stack

* **Java**
* **Java Swing** – Desktop GUI
* **MongoDB Atlas** – Cloud Database
* **MongoDB Compass** – Database Management
* **MongoDB Java Driver**

## Project Structure

```text
SocialMediaApp/
│
├── src/
│   ├── Main.java
│   ├── auth/        # Authentication
│   ├── db/          # Database connection and utilities
│   ├── home/        # Home feed
│   ├── profile/     # User profile
│   └── session/     # Session management
│
├── Lib/             # MongoDB Driver Libraries
├── session.txt      # Active user session
└── README.md
```

## Purpose

The main purpose of this project was to apply **Advanced Database Systems concepts** in a practical application. It provides hands-on experience with **NoSQL database design, CRUD operations, database connectivity, authentication, session management, and Java GUI development**.

## Database

The application uses **MongoDB Atlas** to store and manage social media data, including:

* Users
* Profiles
* Posts
* Follower information
* Session-related data

## Getting Started

1. Clone the repository.
2. Open the project in a Java IDE such as IntelliJ IDEA or Eclipse.
3. Add the required MongoDB Java Driver libraries.
4. Configure your MongoDB Atlas connection string.
5. Run `Main.java` to launch the application.

## Author

**Amna Bibi**
