package com.example.footyapp.model.db

class MockFavDatabase private constructor(){

    var favDao = MockFavDao()
        private set
    companion object{
        @Volatile private var instance: MockFavDatabase? = null
        fun getInstance() =
            instance ?: synchronized(this){
                instance?: MockFavDatabase().also { instance = it }
            }
    }
}
