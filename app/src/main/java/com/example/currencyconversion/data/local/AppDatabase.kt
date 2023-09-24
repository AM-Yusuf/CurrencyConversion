package com.example.currencyconversion.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.currencyconversion.data.entities.Currency
import com.example.currencyconversion.data.entities.Rates
import com.example.currencyconversion.util.DATABASE_NAME
import com.example.currencyconversion.util.DB_VERSION


@Database(
    entities = [Currency::class, Rates::class],
    version = DB_VERSION,
    exportSchema = false
)

abstract class AppDatabase: RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao
    abstract fun ratesDao(): RatesDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context)
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .allowMainThreadQueries()
                .build()
        }
    }
}