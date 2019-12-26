package com.betatech.learnspanish.data.model.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * [Exercise] class represent one exercise,
 * which will contain 5 to 10 [Lesson]
 * each lesson comprising one spanish word
 * to learn.
 *
 * After reading all the lessons, user can
 * take [Quiz] to complete the exercise,
 * and unlock next exercise and earn points.
 */
@Entity(tableName = "exercises")
data class Exercise(
    @PrimaryKey
    val id: String,
    @ColumnInfo(name = "exercise_number")
    val exerciseNumber: Int,
    val title: String,
    val description: String,
    val isCompleted: Boolean
)