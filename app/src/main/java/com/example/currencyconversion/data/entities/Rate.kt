package com.example.currencyconversion.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

// Database entity
@Entity(tableName = "rates")
data class Rate(
    @PrimaryKey(autoGenerate = false)
    val currencyCode: String,
    val currencyRate: Float
)