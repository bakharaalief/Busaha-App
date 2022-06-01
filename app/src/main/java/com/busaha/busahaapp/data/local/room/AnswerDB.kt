package com.busaha.busahaapp.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.busaha.busahaapp.data.local.entity.AnswerEntity

@Database(entities = [AnswerEntity::class], version = 1, exportSchema = false)
abstract class AnswerDB : RoomDatabase() {

    abstract fun answerDao(): AnswerDao

    companion object {
        @Volatile
        private var instance: AnswerDB? = null

        fun getInstance(context: Context): AnswerDB =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    AnswerDB::class.java, "Answer.db"
                ).build()
            }
    }
}