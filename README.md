# 🍽️ RecipeRealm

[![Kotlin](https://img.shields.io/badge/Kotlin-2.0.0-blue.svg?style=flat&logo=kotlin)](https://kotlinlang.org)
[![Compose](https://img.shields.io/badge/Jetpack%20Compose-2024.12.00-green.svg?style=flat)](https://developer.android.com/jetpack/compose)
[![Material 3](https://img.shields.io/badge/Material%203-1.3.0-purple.svg?style=flat)](https://m3.material.io/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

**RecipeRealm** — это Android-приложение для поиска, просмотра и сохранения рецептов. Приложение позволяет пользователям искать блюда по категориям и управлять персональным профилем с авторизацией через Google.

<p align="center">
  <img src="screenshots/HomeScreen.jpg" width="30%" alt="Home Screen"/>
  <img src="screenshots/MealsScreen.jpg" width="30%" alt="Meals Screen"/>
  <img src="screenshots/ScreenMealDetails.jpg" width="30%" alt="Screen Meal Details"/>
  <img src="screenshots/ScreenMealDetailsWithYouTubeSection.jpg" width="30%" alt="Screen Meal Details With YouTubeSection"/>
  <img src="screenshots/SearchRecipe.jpg" width="30%" alt="Search Screen"/>
  <img src="screenshots/MyDishesScreen.jpg" width="30%" alt="My Dishes Screen"/>
  <img src="screenshots/ProfileScreen.jpg" width="30%" alt="Profile Screen"/>
  <img src="screenshots/SettingsScreen.jpg" width="30%" alt="Settings Screen"/>
  
</p>

---

## ✨ Возможности

- 🔐 **Аутентификация** — вход и регистрация через Google и Firebase Auth
- 📚 **Категории рецептов** — удобная навигация по типам блюд
- 🔍 **Поиск рецептов** — быстрый поиск по названию и ингредиентам
- 👤 **Личные рецепты** — каждый пользователь видит только свои добавленные рецепты
- 🎨 **Темы оформления** — поддержка светлой и тёмной темы
- 📱 **Material 3 Design** — современный интерфейс на Jetpack Compose

---

## 🛠️ Технологический стек

| Компонент | Технология | Назначение |
|-----------|------------|------------|
| **Язык** | Kotlin | Основной язык разработки |
| **UI** | Jetpack Compose + Material 3 | Декларативный интерфейс |
| **Архитектура** | MVVM | Разделение ответственности |
| **DI** | Koin | Внедрение зависимостей |
| **Сеть** | Retrofit| API запросы к рецептам |
| **База данных** | Room | Локальное хранение |
| **Аутентификация** | Firebase Auth + Google Sign-In | Вход через Google |
| **Cloud DB** | Firestore | Хранение пользовательских рецептов |
| **Асинхронность** | Kotlin Coroutines | Фоновые операции |
| **Навигация** | Navigation Compose | Переходы между экранами |
| **Загрузка изображений** | Coil | Загрузка картинок рецептов |
| **Хранение настроек** | DataStore | Сохранение темы и предпочтений |

---

## 📱 Требования

- **minSdk**: 24 (Android 7.0 Nougat)
- **targetSdk**: 35 (Android 15)
- **Java/Kotlin**: Java 17, Kotlin 2.0+
- **Google Play Services**: требуется для авторизации через Google

---

## 🚀 Установка и запуск

### 1. Клонирование репозитория
```bash
git clone https://github.com/YOUR_USERNAME/RecipeRealm.git
cd RecipeRealm
