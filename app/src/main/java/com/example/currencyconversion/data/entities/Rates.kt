package com.example.currencyconversion.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Rates")
data class Rates(
    @PrimaryKey
    val currencyCode: String,
    @ColumnInfo
    val currencyRate: Double
)