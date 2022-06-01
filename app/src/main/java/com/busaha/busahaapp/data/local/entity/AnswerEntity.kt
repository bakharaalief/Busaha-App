package com.busaha.busahaapp.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "answers")
class AnswerEntity(
    @ColumnInfo(name = "idQuestion")
    @PrimaryKey
    var idQuestion: Int = 0,

    @ColumnInfo(name = "idAnswer")
    val idAnswer: Int = 0,

    @ColumnInfo(name = "index")
    val index: Int? = null,
)