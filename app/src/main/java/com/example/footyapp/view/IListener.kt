package com.example.footyapp.view

import com.example.footyapp.model.db.Favorite

interface IListener {
    fun deleteFavorite(favorite: Favorite)
}
