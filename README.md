** 📱 Social Media App — Java GUI + MongoDB

This is a my project built using the Advanced Database Systems concept. 
It is a desktop-based Java GUI application that connects to a MongoDB database (MongoDB Atlas + Compass) and enables full CRUD operations on social media entities such as users, posts, profiles, and sessions.

** 🚀 Features
🔐 User Login/Signup system (session management using local file)

🏠 Home Feed GUI with post visibility

👤 User Profile with post listings and follower data

✅ MongoDB Atlas backend for storing users, posts, and other social data

🛠️ Modular Java code using packages like:

auth – authentication

home – home feed GUI

profile – user profile GUI

db – database connection and utilities

session – current user session tracking

** 🛠️ Tech Stack
Java Swing GUI for desktop user interface

MongoDB Atlas (cloud) + Compass (local view)

MongoDB Java Driver:

bson-4.11.1.jar

mongodb-driver-core-4.11.1.jar

mongodb-driver-sync-4.11.1.jar

** 📁 Project Structure

SocialMediaApp/
│
├── src/
│   ├── Main.java
│   ├── auth/                # Login, Signup screens
│   ├── db/                  # MongoDB connection helper
│   ├── home/                # Home feed GUI
│   ├── profile/             # Profile GUI
│   └── session/             # Session manager
│
├── Lib/                    # Required MongoDB driver JARs
├── session.txt             # Stores active user session
└── README.md               # This file
⚙️ Setup Instructions
Clone the repository:

git clone https://github.com/your-username/social-media-app.git
Open in your Java IDE (e.g., IntelliJ, Eclipse)

Add MongoDB Driver JARs:

Include the JARs from Lib/ in your build path.

Configure MongoDB:

Set your MongoDB connection string in MongoUtil.java.

Run Main.java:

Start the application and interact with the GUI.

** 📸 Screenshots (Optional)
You can add GUI screenshots here by uploading them and using:


🧑‍💻 Author
Amna Bibi
📧 [amna.sparish@example.com]

📌 Notes
This project was built as a semester submission for the Advanced Database Systems course.

Built with a focus on connecting front-end GUI logic to a powerful NoSQL backend.
