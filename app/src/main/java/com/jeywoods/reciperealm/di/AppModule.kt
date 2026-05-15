package com.jeywoods.reciperealm.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.jeywoods.reciperealm.data.local.AppDatabase
import com.jeywoods.reciperealm.data.remote.ApiService
import com.jeywoods.reciperealm.data.repository.FavoritesRepository
import com.jeywoods.reciperealm.data.repository.MealRepository
import com.jeywoods.reciperealm.ui.viewModel.*
import com.jeywoods.reciperealm.utils.AppSettingsManager
import com.jeywoods.reciperealm.utils.NetworkManager
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    single { NetworkManager(androidContext()) }
    single { AppSettingsManager(androidContext()) }

    single {
        Retrofit.Builder()
            .baseUrl("https://www.themealdb.com/api/json/v1/1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    single { AppDatabase.getInstance(androidContext()) }

    single {
        MealRepository(
            apiService = get(),
            networkManager = get(),
            database = get()
        )
    }

    single { get<AppDatabase>().favoriteMealDao() }

    single {
        FavoritesRepository(
            dao = get(),
            auth = FirebaseAuth.getInstance(),
            firestore = FirebaseFirestore.getInstance()
        )
    }

    viewModel { FavoritesViewModel(get()) }
    viewModel { CategoriesViewModel(get()) }
    viewModel { SearchViewModel(get()) }

    viewModel { params ->
        MealsViewModel(repository = get(), categoryName = params.get())
    }
    viewModel { params ->
        MealDetailViewModel(repository = get(), mealId = params.get())
    }

    viewModel { ProfileViewModel() }

    viewModel { AuthViewModel() }
}