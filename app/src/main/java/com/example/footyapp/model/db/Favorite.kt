package com.example.footyapp.model.db

data class Favorite (
    val _id: Int,
    val favName: String,
    val type: Char)
{
    override fun toString(): String {
        return "$favName - id: $_id"
    }
}