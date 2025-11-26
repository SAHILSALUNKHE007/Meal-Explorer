🍽️ TheMealDB Explorer

A simple meal browsing application built using:

Spring Boot (Java) – Backend REST API

HTML / CSS / JavaScript – Frontend UI

TheMealDB Public API (Test Key: 1)

This project allows users to search meals, explore categories, view random recipes, and open detailed recipe instructions.

📁 Project Structure
Meal-Explorer/
   ├── BackEnd/          # Spring Boot REST API
   ├── FrontEnd/         # HTML + CSS + JS UI
   ├── README.md
   └── .gitignore

🚀 How to Run the Project
1️⃣ Run Backend (Spring Boot)
Requirements

Java 17+

Maven installed

Commands

Open terminal:

cd BackEnd
mvn clean install
mvn spring-boot:run

Default Backend URL:
http://localhost:8080


Your backend exposes simplified REST endpoints like:

GET /api/meals/search?q=<meal>
GET /api/meals/random
GET /api/meals/category/<category>
GET /api/meals/<id>


Backend internally calls:

https://www.themealdb.com/api/json/v1/1/


Caching:
✔ In-memory cache
✔ Cache expiry
✔ Improves performance for repeated API calls

2️⃣ Run Frontend (HTML + CSS + JS)
Option A — Directly open the file

Just double-click:

FrontEnd/index.html

Option B — VS Code Live Server

Right-click index.html →

Open with Live Server

Frontend will automatically call backend APIs on:

http://localhost:8080/api/...

✨ Features Implemented
✔ Search Meals

Enter a meal name and fetch results.

✔ Browse Categories

List categories such as Chicken, Seafood, Vegetarian, etc.

✔ Random Meal

"I'm Feeling Hungry" button → shows a random recipe.

✔ Recipe Details Page

Shows:

Ingredients

Instructions

Thumbnail

YouTube video link

✔ Responsive UI

Works on both desktop and mobile.

🛠️ Tech Stack
Backend

Java 17

Spring Boot

Maven

RestTemplate / WebClient

In-memory caching

Frontend

HTML

CSS

JavaScript (Vanilla)

📦 Submission Notes

This project includes:

Full source code (backend + frontend)

README with run instructions

Clean folder structure

REST-compliant endpoints

Local environment setup

Public GitHub repository as required

👨‍💻 Developed by

Sahil Salunkhe