package com.example.currencyconversion.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Rates")
data class Rates(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val currencyCode: String,
    val currencyRate: Double
)