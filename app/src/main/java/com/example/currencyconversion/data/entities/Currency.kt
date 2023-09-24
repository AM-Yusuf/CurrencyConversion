package com.example.currencyconversion.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Currency")
data class Currency(
    @PrimaryKey
    val currencyCode: String,
    @ColumnInfo
    val currencyName: String
)
