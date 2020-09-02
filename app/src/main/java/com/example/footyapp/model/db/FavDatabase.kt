package com.example.footyapp.model.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(Favorite::class), version = 1, exportSchema = false)
abstract class FavDatabase: RoomDatabase() {

    abstract fun favDAO(): FavoriteDao

    companion object{
        //Singleton prevents multiple instances of database opening at the
        //same time.
        @Volatile
        private var INSTANCE: FavDatabase? = null
        fun getInstance(context: Context,
                        scope:CoroutineScope): FavDatabase{
            val tempInstance = INSTANCE
            return tempInstance ?: synchronized(this){
                INSTANCE?: Room.databaseBuilder(
                    context.applicationContext,
                    FavDatabase::class.java,
                    "favorite_database"
                )
                    //.addCallback(FavoriteDatabaseCallback(scope))
                    .build().also {
                    INSTANCE = it
                }
            }
        }
    }
    private class FavoriteDatabaseCallback(
        private val scope: CoroutineScope
    ): RoomDatabase.Callback(){

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let {favDatabase ->
                scope.launch {
                    populateDatabase(favDatabase.favDAO())
                }
            }
        }

        suspend fun populateDatabase(favoriteDao: FavoriteDao) {
            var fav = Favorite(59, "Arsenal FC", 't')
            favoriteDao.insert(fav)
            fav = Favorite(93, "Manchester City FC", 't')
            favoriteDao.insert(fav)
            fav = Favorite(149, "Manchester United FC", 't')
            favoriteDao.insert(fav)
        }

    }
}
