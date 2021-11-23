package com.example.gps_git.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Locations")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val longitude: Float,
    val latitude: Float
)