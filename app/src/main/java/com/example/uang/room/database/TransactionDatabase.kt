package com.example.uang.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.uang.room.dao.TransactionDao
import com.example.uang.room.entities.IncomeEntities


@Database(
    entities = [IncomeEntities::class],
    version = 1
)
abstract class TransactionDatabase : RoomDatabase() {

    abstract fun transactionDao(): TransactionDao

}