package com.example.uang.room.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Entity(tableName = "Transaction")
@Parcelize
data class IncomeEntities(

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,

    var type: String,
    var category: String,
    var title: String,
    var amount: String,
    var date: String,
    var day:Int,
    var month:Int,
    var year:Int,
    var desc: String

) : Parcelable