package com.example.uang.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.uang.repository.LocalRepository
import com.example.uang.room.entities.IncomeEntities
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddTransactionViewModel @Inject constructor(
    private val repository: LocalRepository
) : ViewModel() {

    val readTransactionData: LiveData<List<IncomeEntities>> =
        repository.readTransaction().asLiveData()

    fun readMonthlyTransactionData(month: Int, year: Int): LiveData<List<IncomeEntities>> =
        repository.getMonthlyTransaction(month, year).asLiveData()

    fun readYearlyTransactionData(year: Int): LiveData<List<IncomeEntities>> =
        repository.getYearlyTransaction(year).asLiveData()




    fun insertTransactionData(transactionEntities: IncomeEntities) {
        viewModelScope.launch {
            repository.insertTransactionData(transactionEntities)
        }
    }

    fun deleteTransactionData(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTransactionData(id)

        }
    }

    fun updateTransaction(transaction: IncomeEntities) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTransactionData(transaction)
        }
    }


}