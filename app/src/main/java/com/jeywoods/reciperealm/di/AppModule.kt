package com.jeywoods.reciperealm.di

import com.jeywoods.reciperealm.data.repository.MealRepository
import com.jeywoods.reciperealm.data.remote.ApiService
import com.jeywoods.reciperealm.utils.AppSettingsManager
import com.jeywoods.reciperealm.utils.NetworkManager
import org.koin.android.ext.koin.androidContext
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

    single {
        com.jeywoods.reciperealm.data.local.AppDatabase.getInstance(androidContext())
    }

    single {
        MealRepository(
            apiService = get(),
            networkManager = get(),
            database = get()
        )
    }
}