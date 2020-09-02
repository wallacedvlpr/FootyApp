package com.example.footyapp.model.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fav_table")
data class Favorite (
    @PrimaryKey
    @ColumnInfo(name = "_id")
    val _id: Int,
    @ColumnInfo(name = "fav_name")
    val favName: String,
    @ColumnInfo(name = "type")
    val type: Char)
{
    override fun toString(): String {
        return "$favName - id: $_id"
    }
}