package com.example.currencyconversion.di

import android.app.Application
import com.example.currencyconversion.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppDatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(appContext: Application): AppDatabase = AppDatabase.getInstance(appContext)

    @Provides
    @Singleton
    fun provideCurrencyDao(db: AppDatabase) = db.currencyDao()

    @Provides
    @Singleton
    fun provideRateDao(db: AppDatabase) = db.rateDao()
}