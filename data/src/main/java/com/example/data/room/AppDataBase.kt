package com.example.data.room

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [ContactEntity::class],
    version = 1)
abstract class AppDataBase: RoomDatabase() {
    abstract fun contactDao(): ContactDao
}