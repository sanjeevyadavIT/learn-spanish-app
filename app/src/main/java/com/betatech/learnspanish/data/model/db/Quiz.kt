package com.betatech.learnspanish.data.model.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

/**
 * [Quiz] class contain one question for [Exercise]
 * Each exercise contain 5-10 questions
 */
@Entity(
    tableName = "quizzes",
    foreignKeys = [
        ForeignKey(
            entity = Exercise::class,
            parentColumns = ["id"],
            childColumns = ["exerciseId"],
            onDelete = CASCADE
        )
    ]
)
data class Quiz(
    @PrimaryKey
    val id: String,
    val exerciseId: String,
    @ColumnInfo(name = "question_number")
    val questionNumber: Int,
    val question: String,
    @ColumnInfo(name = "question_type")
    val questionType: String,
    val hint: String,
    @ColumnInfo(name = "correct_answer")
    val correctAnswer: String,
    val options: List<String>
)