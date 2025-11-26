const BASE_URL = "http://localhost:8080/api";

let currentPage = 1;
let itemsPerPage = 10;
let allMeals = [];

let lastRandomMeal = null;


function showLoader() {
    document.getElementById("loader").style.display = "block";
}

function hideLoader() {
    document.getElementById("loader").style.display = "none";
}

function showToast(message, type = "info") {
    const toast = document.getElementById("toast");
    toast.innerText = message;
    toast.style.background = type === "error" ? "#e74c3c" : "#333";
    toast.classList.add("show");
    setTimeout(() => toast.classList.remove("show"), 3000);
}

function isValidSearch(text) {
    if (!text || text.trim().length < 2) return false;
    return /^[a-zA-Z\s]+$/.test(text.trim());
}

function isValidId(id) {
    return /^[0-9]+$/.test(id);
}

async function safeFetch(url) {
    try {
        const res = await fetch(url);
        if (!res.ok) return null;
        return res.json();
    } catch {
        return null;
    }
}

function searchMeals() {
    let name = document.getElementById("searchInput").value.trim();
    if (!isValidSearch(name)) {
        showToast("Enter at least 2 letters (alphabet only)", "error");
        return;
    }
    showLoader();
    safeFetch(`${BASE_URL}/search/${name}`).then(data => {
        hideLoader();
        if (!data || data.length === 0) {
            showToast("No meals found", "error");
            return;
        }
        displayMeals(data);
    });
}

function loadCategories() {
    safeFetch(`${BASE_URL}/categories`).then(categories => {
        const container = document.getElementById("categoryScroll");
        container.innerHTML = "";
        if (!categories) return;
        categories.forEach(cat => {
            const div = document.createElement("div");
            div.className = "category-chip";
            div.innerText = cat;
            div.onclick = () => fetchByCategory(cat);
            container.appendChild(div);
        });
    });
}

function fetchByCategory(category) {
    showLoader();
    safeFetch(`${BASE_URL}/category/${category}`).then(data => {
        hideLoader();
        if (!data || data.length === 0) {
            showToast("No meals found", "error");
            return;
        }
        displayMeals(data);
    });
}

function getRandomMeal() {
    showLoader();
    safeFetch(`${BASE_URL}/random`).then(meal => {
        hideLoader();
        if (!meal) {
            showToast("Error loading random meal", "error");
            return;
        }
        lastRandomMeal = meal;
        showMealDetails(meal);
    });
}

function reopenLastRandomMeal() {
    if (lastRandomMeal) showMealDetails(lastRandomMeal);
    else showToast("No recent meal stored", "error");
}

function displayMeals(meals) {
    allMeals = meals;
    currentPage = 1;
    renderPage();
}

function renderPage() {
    const container = document.getElementById("results");
    container.innerHTML = "";
    let start = (currentPage - 1) * itemsPerPage;
    let end = start + itemsPerPage;
    allMeals.slice(start, end).forEach(meal => {
        const card = document.createElement("div");
        card.className = "card";
        card.onclick = () => loadMealDetail(meal.id);
        card.innerHTML = `
            <img src="${meal.thumbnailUrl}">
            <div class="card-info"><h3>${meal.name}</h3></div>
        `;
        container.appendChild(card);
    });
    renderPagination();
}

function renderPagination() {
    const totalPages = Math.ceil(allMeals.length / itemsPerPage);
    const paginationBox = document.getElementById("pagination");
    paginationBox.innerHTML = "";
    for (let i = 1; i <= totalPages; i++) {
        const btn = document.createElement("button");
        btn.innerText = i;
        btn.className = i === currentPage ? "active-page" : "";
        btn.onclick = () => {
            currentPage = i;
            renderPage();
        };
        paginationBox.appendChild(btn);
    }
}

function loadMealDetail(id) {
    if (!isValidId(id)) {
        showToast("Invalid meal ID", "error");
        return;
    }
    showLoader();
    safeFetch(`${BASE_URL}/meal/${id}`).then(meal => {
        hideLoader();
        if (!meal) {
            showToast("Meal not found", "error");
            return;
        }
        showMealDetails(meal);
    });
}

function showMealDetails(meal) {
    if (!meal || !meal.name) return;
    document.getElementById("modalImage").src = meal.thumbnailUrl;
    document.getElementById("modalTitle").innerText = meal.name;
    document.getElementById("modalCategory").innerText = meal.category;
    document.getElementById("modalArea").innerText = meal.area;
    document.getElementById("modalInstructions").innerText = meal.instructions;

    const ingredientsList = document.getElementById("modalIngredients");
    ingredientsList.innerHTML = "";

    meal.ingredients.forEach(i => {
        const li = document.createElement("li");
        li.innerText = `${i.name} — ${i.measure}`;
        ingredientsList.appendChild(li);
    });

    document.getElementById("mealModal").style.display = "flex";
}


function closeModal() {
    document.getElementById("mealModal").style.display = "none";
    if (lastRandomMeal) showToast("You can reopen from button", "info");
}

loadCategories();
document.getElementById("mealModal").style.display = "none";