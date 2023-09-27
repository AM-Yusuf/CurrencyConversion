package com.example.currencyconversion.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Rates")
data class Rates(
    @PrimaryKey(autoGenerate = false)
    val currencyCode: String,
    val currencyRate: Float
)