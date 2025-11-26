🍽️ TheMealDB Explorer

A simple meal browsing application built using:

Spring Boot (Java) – Backend REST API

HTML / CSS / JavaScript – Frontend UI

TheMealDB Public API (Test Key: 1)

This project allows users to search meals, explore categories, view random recipes, and view full recipe details with ingredients and instructions.

📁 Project Structure
Meal-Explorer/
 ├── Backend/          # Spring Boot REST API
 ├── Frontend/         # HTML + CSS + JavaScript UI
 ├── README.md
 └── .gitignore

🚀 How to Run the Project
1️⃣ Run Backend (Spring Boot)
Requirements

Java 17+

Maven installed

Commands

Open a terminal inside the Backend folder:

cd Backend
mvn clean install
mvn spring-boot:run

Backend runs at:
http://localhost:8080

🔗 Backend API Endpoints (Correct)
Purpose	Endpoint
Search meals by name	GET /api/search/{name}
Get meals by category	GET /api/category/{name}
Get meal details	GET /api/meal/{id}
Get random meal	GET /api/random
Get all categories	GET /api/categories

The backend internally consumes:

https://www.themealdb.com/api/json/v1/1/

Caching Implemented

✔ In-memory cache
✔ Cache expiry
✔ Performance optimized

2️⃣ Run Frontend (HTML + CSS + JS)
Option A — Direct Run

Simply open:

Frontend/index.html

Option B — Run with Live Server (Recommended)

Open the Frontend folder in VS Code

Install the Live Server extension

Right-click index.html

Select Open with Live Server

Frontend automatically calls backend using:
http://localhost:8080/api/...

✨ Features Implemented
✔ Search Meals

Search meals by name using a search bar.

✔ Browse Categories

View meals categorized by type (Chicken, Vegan, Seafood, etc.)

✔ Random Meal

An "I'm Feeling Hungry" button fetches a random recipe.

✔ Recipe Details Page

Displays:

Ingredients

Instructions

Thumbnail image

YouTube tutorial link

✔ Responsive UI

Optimized for mobile and desktop screens.

🛠️ Tech Stack
Backend

Java 17

Spring Boot

Maven

RestTemplate

In-memory caching

Frontend

HTML

CSS

JavaScript (Vanilla JS)

📦 Submission Notes

This project includes:

Complete source code (Backend + Frontend)

Fully functional REST API

Working frontend UI

Clean folder structure

README with detailed setup instructions

Public GitHub repository as required

Endpoints fully tested locally

👨‍💻 Developed By

Sahil Salunkhe
