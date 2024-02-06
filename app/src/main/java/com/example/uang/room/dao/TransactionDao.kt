package com.example.uang.room.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.uang.room.entities.IncomeEntities
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Query("SELECT * FROM `Transaction`  ORDER BY year DESC,month DESC,day DESC,category DESC ")
    fun readTransaction(): Flow<List<IncomeEntities>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun  insertTransactionData(transaction: IncomeEntities)

    @Query("SELECT * FROM `Transaction` WHERE year=:year")
    fun getYearlyTransaction(year: Int): Flow<List<IncomeEntities>>

    @Query("SELECT * FROM `Transaction` WHERE month=:month AND year=:year")
    fun getMonthlyTransaction(month: Int,year: Int): Flow<List<IncomeEntities>>

    @Query("DELETE FROM `Transaction` WHERE id=:id")
    fun deleteTransactionData(id: Int)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateTransaction(transaction: IncomeEntities)
}