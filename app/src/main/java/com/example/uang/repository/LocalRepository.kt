package com.example.uang.repository

import com.example.uang.room.dao.TransactionDao
import com.example.uang.room.entities.IncomeEntities
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


@ViewModelScoped
class LocalRepository @Inject constructor(
    private val transactionDao: TransactionDao
) {

    fun readTransaction(): Flow<List<IncomeEntities>> {
        return transactionDao.readTransaction()
    }


    suspend fun insertTransactionData(transactionEntities: IncomeEntities) {
        return transactionDao.insertTransactionData(transactionEntities)
    }

    fun deleteTransactionData(id: Int) {
        return transactionDao.deleteTransactionData(id)
    }

    fun updateTransactionData(incomeEntities: IncomeEntities) {
        return transactionDao.updateTransaction(incomeEntities)
    }



}