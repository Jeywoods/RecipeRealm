<div align="center">

<img src="screenshots/HomeScreen.jpg" width="100px" height="100px" style="border-radius: 24px;" alt="RecipeRealm Logo"/>

# 🍽️ RecipeRealm

**Откройте мир вкусов — ищите, сохраняйте, готовьте.**

[![Kotlin](https://img.shields.io/badge/Kotlin-2.0.0-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)](https://kotlinlang.org)
[![Compose](https://img.shields.io/badge/Jetpack_Compose-2024.12.00-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white)](https://developer.android.com/jetpack/compose)
[![Material 3](https://img.shields.io/badge/Material_3-1.3.0-757575?style=for-the-badge&logo=material-design&logoColor=white)](https://m3.material.io/)
[![Firebase](https://img.shields.io/badge/Firebase-Auth_+_Firestore-FFCA28?style=for-the-badge&logo=firebase&logoColor=black)](https://firebase.google.com)
[![License](https://img.shields.io/badge/License-MIT-22C55E?style=for-the-badge)](LICENSE)

<br/>

<img src="screenshots/HomeScreen.jpg" width="19%" alt="Home"/>
<img src="screenshots/MealsScreen.jpg" width="19%" alt="Meals"/>
<img src="screenshots/ScreenMealDetails.jpg" width="19%" alt="Details"/>
<img src="screenshots/SearchRecipe.jpg" width="19%" alt="Search"/>
<img src="screenshots/ProfileScreen.jpg" width="19%" alt="Profile"/>

</div>

---

## ✨ О приложении

**RecipeRealm** — это современное Android-приложение для поиска, просмотра и хранения любимых рецептов. Вдохновляйтесь тысячами блюд со всего мира, сохраняйте свои находки и управляйте персональной кулинарной коллекцией — всё в одном красивом приложении.

---

## 🚀 Возможности

| Функция | Описание |
|---|---|
| 🔐 **Авторизация** | Быстрый вход через Google и Firebase Auth |
| 📚 **Категории** | Удобная навигация по типам блюд |
| 🔍 **Умный поиск** | Поиск по названию и ингредиентам |
| 👤 **Мои рецепты** | Личная коллекция только ваших рецептов |
| 🎨 **Темы** | Светлая и тёмная тема на ваш выбор |
| 📺 **YouTube** | Встроенный просмотр видеорецептов |
| 📱 **Material 3** | Современный интерфейс на Jetpack Compose |

---

## 📸 Скриншоты

<div align="center">

| Главная | Блюда | Детали | YouTube |
|:---:|:---:|:---:|:---:|
| <img src="screenshots/HomeScreen.jpg" width="180"/> | <img src="screenshots/MealsScreen.jpg" width="180"/> | <img src="screenshots/ScreenMealDetails.jpg" width="180"/> | <img src="screenshots/ScreenMealDetailsWithYouTubeSection.jpg" width="180"/> |

| Поиск | Мои блюда | Профиль | Настройки |
|:---:|:---:|:---:|:---:|
| <img src="screenshots/SearchRecipe.jpg" width="180"/> | <img src="screenshots/MyDishesScreen.jpg" width="180"/> | <img src="screenshots/ProfileScreen.jpg" width="180"/> | <img src="screenshots/SettingsScreen.jpg" width="180"/> |

</div>

---

## 🛠️ Технологический стек

```
RecipeRealm
├── 🎨  UI Layer
│   ├── Jetpack Compose       — декларативный интерфейс
│   ├── Material 3            — дизайн-система
│   └── Coil                  — загрузка изображений
│
├── 🧠  Logic Layer
│   ├── Kotlin Coroutines     — асинхронные операции
│   ├── MVVM Architecture     — разделение ответственности
│   └── Navigation Compose    — навигация между экранами
│
├── 💾  Data Layer
│   ├── Retrofit              — сетевые запросы к API рецептов
│   ├── Room                  — локальная база данных
│   ├── Firestore             — облачное хранение рецептов
│   └── DataStore             — настройки и предпочтения
│
└── 🔒  Auth & DI
    ├── Firebase Auth         — аутентификация
    ├── Google Sign-In        — вход через Google
    └── Koin                  — внедрение зависимостей
```

---

## 📋 Требования

- **Android**: минимум 7.0 Nougat (API 24+), цель — Android 15 (API 35)
- **Java**: 17+
- **Kotlin**: 2.0+
- **Google Play Services**: требуется для Google Sign-In

---

## ⚙️ Установка и запуск

### 1. Клонирование репозитория

```bash
git clone https://github.com/YOUR_USERNAME/RecipeRealm.git
cd RecipeRealm
```

### 2. Настройка Firebase

1. Создайте проект в [Firebase Console](https://console.firebase.google.com/)
2. Добавьте Android-приложение с вашим `applicationId`
3. Скачайте `google-services.json` и поместите в папку `app/`
4. Включите **Authentication** (Google Sign-In) и **Firestore** в консоли

### 3. Настройка Google Sign-In

Добавьте в `local.properties`:

```properties
WEB_CLIENT_ID=your_web_client_id_here
```

### 4. Сборка и запуск

```bash
./gradlew assembleDebug
```

Или откройте проект в **Android Studio** и нажмите ▶️ Run.

---

<div align="center">

Сделано с ❤️ и ☕ на Kotlin

⭐ Если проект понравился — поставьте звезду!

</div>
