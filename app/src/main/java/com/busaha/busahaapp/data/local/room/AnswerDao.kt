package com.busaha.busahaapp.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.busaha.busahaapp.data.local.entity.AnswerEntity

@Dao
interface AnswerDao {
    @Query("SELECT * FROM answers ")
    fun getAnswers(): LiveData<List<AnswerEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(answerEntity: AnswerEntity)

    @Update
    suspend fun update(answerEntity: AnswerEntity)

    @Query("SELECT EXISTS(SELECT * FROM answers WHERE idQuestion = :id)")
    fun isAnswerSaved(id: Int): LiveData<Boolean>

    @Query("DELETE FROM answers")
    suspend fun deleteAllRecord()

    @Query("SELECT * FROM answers WHERE idQuestion = :id")
    fun getAnswerSaved(id: Int): LiveData<AnswerEntity>
}