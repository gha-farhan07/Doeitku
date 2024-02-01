package com.example.uang.di

import android.content.Context
import androidx.room.Room
import com.example.uang.room.database.TransactionDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RoomModules {


    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context, ) = Room.databaseBuilder(
            context, TransactionDatabase::class.java, "Transaction_Database"
        ).build()

    @Singleton
    @Provides
    fun provideDao(database: TransactionDatabase) = database.transactionDao()

}